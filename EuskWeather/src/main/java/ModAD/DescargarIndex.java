package ModAD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.json.JSONObject;

import ContAD.ControladorPrincipal;
import VistaAD.vistaActualizarBBDD;

public class DescargarIndex {
	
//	public static void main(String[] args) {
//		DescargarIndex d = new DescargarIndex();
//		d.verificarDatosMeteorologicos("3_DE_MARZO");
//	}

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
		
//		for(int i = 0; i < nombreMuni.length; i+=3) {
//			DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nombreMuni[i] + ".json", "./pruebaFich/" + nombreMuni[i] + ".json");
//		}
		
		for(int i = 0; i < nombreMuni.length; i+= 3) {
			verificarDatosMeteorologicos(nombreMuni[i]);
			File f = new File("./ficherosXML//" + nombreMuni[i] + ".xml");
			if(f.exists()) {
				System.out.println("EL XML DE " + nombreMuni[i] + " YA ESTA ACTUALIZADO");
			} else {
				System.out.println("\nINICIADA CONVERSION A XML:\n");
			    String archivoJSON = "", archivoXML = "", archJson = "", archJsonSinCabecera = "", archJsonDefinitivo = "";
			    String contXML = "";
			    FileWriter ficheroXML = null;
		    		archivoJSON = "./pruebaFich//" + nombreMuni[i] + ".json";
		    		archivoXML = "./ficherosXML//" + nombreMuni[i] + ".xml";
		    		
		    		//INICIO DE LA PREPARACION DE NUESTRO JSON
		    		archJson = convertirJSONXML.leerArchivo(archivoJSON, "Windows-1252"); // Lee el archivo
		    		System.out.println("LEYENDO " + archivoJSON);
		    		
		    		//if(archJson.length()>0) {
		    			archJsonSinCabecera = convertirJSONXML.repararJSONSinCabecera(archJson, nombreMuni[i]);
		        		System.out.println("REPARANDO " + archivoJSON);
		        		
		        		//INICIO DE LA CONVERSION JSON-->XML
		        		JSONObject objetoJson = new JSONObject(archJsonSinCabecera);
		        		contXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><nodoRaiz>" + org.json.XML.toString(objetoJson) + "</nodoRaiz>";
		        		
		        		ficheroXML = new FileWriter(archivoXML);
		    	    	try (BufferedWriter out = new BufferedWriter(ficheroXML)) {
		    	    	    out.write(contXML);
		    	    	}
		    	    	ficheroXML.close();
		    			//FIN CONVERSION JSON-->XML
		        		System.out.println("Archivo " + nombreMuni[i]  + ".json convertido a " + nombreMuni[i] + ".xml correctamente");
		    		//}  		
		    	
			}
		}
		
//		System.out.println("\nINICIADA CONVERSION A XML:\n");
//	    String archivoJSON = "", archivoXML = "", archJson = "", archJsonSinCabecera = "", archJsonDefinitivo = "";
//	    String contXML = "";
//	    FileWriter ficheroXML = null;
//    	for (int i = 0; i < nombreMuni.length; i+=3) {
//    		archivoJSON = "./pruebaFich//" + nombreMuni[i] + ".json";
//    		archivoXML = "./ficherosXML//" + nombreMuni[i] + ".xml";
//    		
//    		//INICIO DE LA PREPARACION DE NUESTRO JSON
//    		archJson = convertirJSONXML.leerArchivo(archivoJSON, "Windows-1252"); // Lee el archivo
//    		System.out.println("LEYENDO " + archivoJSON);
//    		
//    		//if(archJson.length()>0) {
//    			archJsonSinCabecera = convertirJSONXML.repararJSONSinCabecera(archJson, nombreMuni[i]);
//        		System.out.println("REPARANDO " + archivoJSON);
//        		
//        		//INICIO DE LA CONVERSION JSON-->XML
//        		JSONObject objetoJson = new JSONObject(archJsonSinCabecera);
//        		contXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><nodoRaiz>" + org.json.XML.toString(objetoJson) + "</nodoRaiz>";
//        		
//        		ficheroXML = new FileWriter(archivoXML);
//    	    	try (BufferedWriter out = new BufferedWriter(ficheroXML)) {
//    	    	    out.write(contXML);
//    	    	}
//    	    	ficheroXML.close();
//    			//FIN CONVERSION JSON-->XML
//        		System.out.println("Archivo " + nombreMuni[i]  + ".json convertido a " + nombreMuni[i] + ".xml correctamente");
//    		//}
//    		
//    		
//    	}
	}
	
	public static void verificarDatosMeteorologicos(String nomFichero) {
		String fichWeb = "";
		fichWeb = DescargarPrincipales.leerURL("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nomFichero + ".json");
		
		String aux = convertirJSONXML.leerArchivo("./pruebaFich//" + nomFichero + ".json", "Windows-1252");

		MessageDigest md;
		String cifrado1 = "", cifrado2 = "";
		try {
			md = MessageDigest.getInstance("SHA");
			byte dataBytes[] = aux.getBytes();
			md.update(dataBytes);
			byte resumen[] = md.digest();
			for(byte b: resumen) {
				cifrado1 += String.format("%02x", b);
			}
			
			byte dataWeb[] = fichWeb.getBytes();
			md.update(dataWeb);
			byte resumenWeb[] = md.digest();
			for(byte b: resumenWeb) {
				cifrado2 += String.format("%02x", b);
			}
//			System.out.println(nomFichero + "local: " + cifrado1);
//			System.out.println(nomFichero + "web: " + cifrado2);
			File f = new File("./ficherosXML//" + nomFichero + ".xml");
			if(cifrado1.contentEquals(cifrado2)) {
				System.out.println("ESTA ACTUALIZADO");
			} else {
				f.delete();
				System.out.println("HAY QUE ACTUALIZAR el fichero " + nomFichero);
				DescargarPrincipales.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/datos_indice/" + nomFichero + ".json", "./pruebaFich/" + nomFichero + ".json");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
