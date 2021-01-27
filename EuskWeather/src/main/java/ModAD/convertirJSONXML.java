package ModAD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class convertirJSONXML {

	    public static String leerArchivo(String ruta, String codificacion) {
	    	FileInputStream archJson;
	    	String acumString = "";
			try {
				StringBuilder acum = new StringBuilder();
				archJson = new FileInputStream(ruta);
				
		    	InputStreamReader isr = new InputStreamReader(archJson, Charset.forName(codificacion)); //Windows-1252
		    	int cont = 0;
		    	while ((cont = isr.read()) != -1) {
		    		char ch = (char) cont;
		    		acum.append(ch);
		    	}	
		    	acumString = acum.toString();
		    	archJson.close();
		    	isr.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
	    	
	    	return acumString;
	    }

	    public static String repararJSONSinCabecera(String archJson, String nomNodo) {
	    	String contenidoSINCabecera = "", contenidoSINPiePagina = "", jsonReparado = "";
	    	boolean cabeceraEncontrada = false;
	    	String[] acumStrings;

	    	for (int i = 0; i <= archJson.length()-1; i++) {
	    		/*
	    		 * Encuentra el JSONCallback y con el booleano lo controla para borrarlo y salir del for
	    		 */
	    		if (archJson.charAt(i) == '{' && !cabeceraEncontrada) {
	    			contenidoSINCabecera = archJson.substring(i);
	    			cabeceraEncontrada = true;
	    		}
	    	}
		
	    	acumStrings = contenidoSINCabecera.split("}");
	    	contenidoSINPiePagina += acumStrings[0];
	    	
	    	for (int i = 1; i < acumStrings.length - 1; i++) {
	    		/*Ira cargando el String 'contenidoSINPiePagina' con el contenido del String
	    		 * con la cabecera quitada
	    		 */
	    		contenidoSINPiePagina += "\n}" + acumStrings[i];
	    	}

	    	if (archJson.charAt(0) == '{') {
	    		//FINALIZA LA ESTRUCTURA DEL NUEVO JSON TRATADO
	    		jsonReparado = contenidoSINPiePagina + "}\n]\n}";
	    	} else {
	    		/*
	    		 * En caso de no iniciar con una '{' le daremos una cabecera con
	    		 * el nombre del nodo de su futuro XML para una mejor identificacion
	    		 */
	    		jsonReparado = "{\n" + "\"" + nomNodo + "\"" + ": [ \n" + contenidoSINPiePagina + "\n}\n]\n}";
	    		/*
	    		 * TODOS LOS \n son necesarios ya que si no al intentar reparar los que tengan alguna
	    		 * etiqueta repetida dara error de formato y nos dira que hace falta un ',' o '{'
	    		 */
	    	}
	    	return jsonReparado;
	    }
	    

	    public static String distinguirEtiquetasRepes(String archJson) {
	    	String contenidoSinRepes = "", jsonDef = "";
	    	String[] acumString, nodosJson;
	    	int distintivoEtiRepetida = 0;
	    	
	    	acumString = archJson.split("}");
	    	for (int i = 0; i < acumString.length; i++) {
	    		nodosJson = acumString[i].split(" : ");
	    		for (int j = 0; j < nodosJson.length; j++) {
	    			if (nodosJson[j].toString().contains("\"turismDescription\"") && distintivoEtiRepetida > 0) {
	    				contenidoSinRepes += "\"turismDescription" + distintivoEtiRepetida + "\"" + " : ";
	    				distintivoEtiRepetida++;
	    			} else if (nodosJson[j].toString().contains("\"turismDescription\"") && distintivoEtiRepetida == 0) {
	    				contenidoSinRepes += nodosJson[j] + " : " + nodosJson[j + 1].substring(0, nodosJson[j + 1].length() - 19);
	    				distintivoEtiRepetida++;
	    			} else {
	    				contenidoSinRepes += nodosJson[j];
	    				if (nodosJson[j].charAt(nodosJson[j].length() - 1) != (char) 10) {
	    					contenidoSinRepes += " : ";
	    				}
	    			}
	    		}
	    		contenidoSinRepes = contenidoSinRepes.substring(0, contenidoSinRepes.length() - 2);
	    		nodosJson = null; //Vaciamos el array para cada iteracion
	    		distintivoEtiRepetida = 0;
	    		contenidoSinRepes += "}"; //Finalizamos el nodo con su pertinente caracter
	    	}
	    	contenidoSinRepes = contenidoSinRepes.substring(0, contenidoSinRepes.length() - 1);
	    	contenidoSinRepes += "]\n}";
	    	nodosJson = contenidoSinRepes.split(",");
	    	for (int i = 0; i < nodosJson.length; i++) {
	    		if (!nodosJson[i].toString().contains("\"\"")) {
	    			//Si encuentra una etiqueta vacia aqui le agregamos una ','
	    			jsonDef += nodosJson[i] + ",";
	    		}
	    	}
	    	/*
	    	 * Esta linea sera necesaria ya que debido al procedimiento anterior al final tendremos
	    	 * una ',' que tendremos que eliminar para no dar fallo a la hora de convertirlo a XML
	    	 */
	    	jsonDef = jsonDef.substring(0, jsonDef.length() - 1);

	    	return jsonDef;
	    }
}
