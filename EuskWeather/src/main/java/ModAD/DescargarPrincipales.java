package ModAD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class DescargarPrincipales {
	
	public static void main(String[] args) {
		String fichWeb = leerURL("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/estaciones.json");
		
		String aux = convertirJSONXML.leerArchivo("./archJSON//estaciones.json", "Windows-1252");
		String cifrado1 ="";
		String h = "HOLA", h2 = "HOLA";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			byte dataBytes[] = fichWeb.getBytes();
			md.update(dataBytes);
			byte resumen[] = md.digest();
			for(byte b: resumen) {
				cifrado1 += String.format("%02x", b);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(cifrado1);
	}
	
		private static void trustEveryone() {
			try {
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null, new X509TrustManager[] { new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				} }, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	    public static void descargarFicheros(String direccion, String archivoSalida) {
	    	try {
	    		trustEveryone();
	    		URL url = new URL(direccion);
	    		URLConnection urlCon = url.openConnection();

	    		System.out.println("Descargado fichero: " + archivoSalida);

	    		InputStream is = urlCon.getInputStream();
	    		FileOutputStream fos = new FileOutputStream(archivoSalida);

	    		byte[] array = new byte[1000];
	    		int leido = is.read(array);
	    		while (leido > 0) {
	    			fos.write(array, 0, leido);
	    			leido = is.read(array);
	    		}
	    		is.close();
	    		fos.close();	
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    public static String leerURL(String direccion) {
	    	String cadena = "";
	    	try {
	    		trustEveryone();
	    		URL url = new URL(direccion);
	    		URLConnection urlCon = url.openConnection();
	    		InputStream is = urlCon.getInputStream();	    		
	    				
	    		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("Windows-1252")));
	    		StringBuilder acum = new StringBuilder();
	    		
	    		int cont = 0;
	    		while((cont = br.read()) != -1) {
	    			char ch = (char) cont;
	    			acum.append(ch);
	    		}
	    		cadena = acum.toString();
	    		
	    		is.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    	return cadena;
	    }
}
