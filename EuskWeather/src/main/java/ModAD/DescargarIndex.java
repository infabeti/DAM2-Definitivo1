package ModAD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.json.JSONObject;

public class DescargarIndex {
	
	//static String[] nombreMuniDef;
	
	public static void main(String[] args) throws IOException {
		String xml = convertirJSONXML.leerArchivo("./ficherosXML//index.xml", "utf-8"); 
		procesarDatosAtmosfericos(xml);
	}
	
	public static void procesarDatosAtmosfericos(String archivo) throws IOException {
		String nomMuni = "";
		String[] index, nodos, nombreMuni = null;

		index = archivo.split("</aggregated>");

		nombreMuni = new String[index.length - 1];
		//nombreMuniDef = new String[index.length -1];
		
		for (int i = 0; i < index.length; i++) {
			nodos = index[i].split("/");
			for (int j = 0; j < nodos.length; j++) {
				if (nodos[j].contains("<name>")) {
					for (int k = 0; k < nodos[j].length(); k++) {		
						if (nodos[j].charAt(k) == 'e') {
							nomMuni = nodos[j].substring(k, nodos[j].length() - 1);
							if (nomMuni.contains(">")) {
								nombreMuni[i] = nomMuni.substring(2);
								//System.out.println(nombreMuni[i]);
							}
						}
					}
				}
			}
		}		
		
		for(int i = 0; i < nombreMuni.length; i+=3) {
//			String aux = "";
//			if(nombreMuni[i].contentEquals("3_DE_MARZO")) {
//				aux = "3 DE MARZO";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ALGORTA_BBIZI2")) {
//				aux = "ALGORTA (BBIZI2)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ARRAIZ_Monte")) {
//				aux = "ARRAIZ (Monte)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("AV_GASTEIZ")) {
//				aux = "AV. GASTEIZ";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("AVDA_TOLOSA")) {
//				aux = "AVDA. TOLOSA";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ANORGA")) {
//				aux = "AÑORGA";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("BANDERAS_meteo")) {
//				aux = "BANDERAS (meteo)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("BOROA_METEO")) {
//				aux = "BOROA METEO";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("FERIA_meteo")) {
//				aux = "FERIA (meteo)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ALGORTA_BBIZI2")) {
//				aux = "ALGORTA (BBIZI2)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("LAS_CARRERAS")) {
//				aux = "LAS CARRERAS";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("LOS_HERRAN")) {
//				aux = "LOS HERRAN";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("M_DIAZ_HARO")) {
//				aux = "Mª DIAZ HARO";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("SAN_JULIAN")) {
//				aux = "SAN JULIAN";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("SAN_MIGUEL")) {
//				aux = "SAN MIGUEL";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ZELAIETA_PARQUE")) {
//				aux = "ZELAIETA PARQUE";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ZIERBENA_Puerto")) {
//				aux = "ZIERBENA (Puerto)";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else if(nombreMuni[i].contentEquals("ZUBIETA_METEO")) {
//				aux = "ZUBIETA METEO";
//				nombreMuniDef[i] = aux;
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + aux + ".json");
//			} else {
//				nombreMuniDef[i] = nombreMuni[i];
//				//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + nombreMuni[i] + ".json");
//			}
			//DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + nombreMuni[i] + ".json");
		}
		
		System.out.println("\nINICIADA CONVERSION A XML:\n");
	    String archivoJSON = "", archivoXML = "", archJson = "", archJsonSinCabecera = "", archJsonDefinitivo = "";
	    String contXML = "";
	    FileWriter ficheroXML = null;
    	for (int i = 0; i < nombreMuni.length; i+=3) {
    		archivoJSON = "./pruebaFich//" + nombreMuni[i] + ".json";
    		archivoXML = "./ficherosXML//" + nombreMuni[i] + ".xml";
    		
    		//INICIO DE LA PREPARACION DE NUESTRO JSON
    		archJson = convertirJSONXML.leerArchivo(archivoJSON, "Windows-1252"); // Lee el archivo
    		archJsonSinCabecera = convertirJSONXML.repararJSONSinCabecera(archJson, nombreMuni[i]);
    		archJsonDefinitivo = convertirJSONXML.distinguirEtiquetasRepes(archJsonSinCabecera);
    		//AQUI YA TENDREMOS NUESTRO JSON PERTINENTE PREPARADO
    		
    		//INICIO DE LA CONVERSION JSON-->XML
    		JSONObject objetoJson = new JSONObject(archJsonDefinitivo);
    		contXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><nodoRaiz>" + org.json.XML.toString(objetoJson) + "</nodoRaiz>";

    		ficheroXML = new FileWriter(archivoXML);
	    	try (BufferedWriter out = new BufferedWriter(ficheroXML)) {
	    	    out.write(contXML);
	    	}
	    	ficheroXML.close();
			//FIN CONVERSION JSON-->XML
    		System.out.println("Archivo " + nombreMuni[i]  + ".json convertido a " + nombreMuni[i] + ".xml correctamente");
    	}
	}

}
