package ModAD;

import java.util.ArrayList;

import org.hibernate.Session;

public class DescargarIndex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml = convertirJSONXML.leerArchivo("./ficherosXML//index.xml", "utf-8"); 
	
		ArrayList<IndexLinks> listaEnlaces = lecturaDatos(xml);
		
		volcarIndex(listaEnlaces);
	}
	
	public static ArrayList<IndexLinks> lecturaDatos(String archivo) {
		IndexLinks[] indexLinks;
		ArrayList<IndexLinks> listaIndex = new ArrayList<IndexLinks>();
		String nomMuni = "", url = "";
		String[] index, nodos, nombreMuni = null, nombreMuniDef = null, links = null, linkDef = null, linkNoNull = null, nomMuniNoNull = null;

		index = archivo.split("</aggregated>");

		nombreMuni = new String[index.length - 1];
		links = new String[index.length -1];
		indexLinks = new IndexLinks[index.length-1];
		nombreMuniDef = new String[index.length -1];
		nomMuniNoNull = new String[index.length -1];
		linkDef = new String[index.length-1];
		
		for (int i = 0; i < index.length; i++) {
			nodos = index[i].split("/");
			for (int j = 0; j < nodos.length; j++) {
				if (nodos[j].contains("<name>")) {
					for (int k = 0; k < nodos[j].length(); k++) {		
						if (nodos[j].charAt(k) == 'e') {
							nomMuni = nodos[j].substring(k, nodos[j].length() - 1);
							if (nomMuni.contains(">")) {
								nombreMuni[i] = nomMuni.substring(2);
							}
						}
					}
				}
			}
		}
		
		String[] aux;
		for(int i = 0; i < index.length-1; i++) {
			nodos = index[i].split("<url>");		
			for(int j = 0; j < nodos.length; j++) {
				aux = nodos[j].split("</url>");
				if(nodos[j].contains("</url>")){
					links[i] = aux[0];
				}
			}System.out.println(links[i]);
		}

		
		
		for(int i = 0; i <= nombreMuni.length-2; i++) {
				if(nombreMuni[i].contentEquals(nombreMuni[i+1])) {
					nombreMuniDef[i] = nombreMuni[i];
					nombreMuni[i+1]= "";
				}
		}
		
		for(int i = 0; i < nombreMuniDef.length; i+=3) {
			nomMuniNoNull[i] = nombreMuniDef[i];
			//System.out.println(nomMuniNoNull[i]);
		}
		

		for (int i = 0; i < index.length-1; i++) {
			indexLinks[i] = new IndexLinks((i+1), nomMuniNoNull[i], links[i]);
			listaIndex.add(indexLinks[i]);
		}
		
		return listaIndex;
	}

	public static void volcarIndex(ArrayList<IndexLinks> objetos) {
		
		for (int i = 0; i < objetos.size(); i++) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(objetos.get(i));
			session.getTransaction().commit();
			session.close();
			
		}
		HibernateUtil.shutdown();
	}

}
