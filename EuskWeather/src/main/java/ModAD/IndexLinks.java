package ModAD;

public class IndexLinks {

	private int idIndex;
	private String nombreMuni;
	private String enlace;
	
	public IndexLinks() {
		
	}
	
	public IndexLinks(int idIndex, String nombreMuni, String enlace) {
		this.idIndex = idIndex;
		this.nombreMuni = nombreMuni;
		this.enlace = enlace;
	}

	public int getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(int idIndex) {
		this.idIndex = idIndex;
	}

	public String getNombreMuni() {
		return nombreMuni;
	}

	public void setNombreMuni(String nombreMuni) {
		this.nombreMuni = nombreMuni;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
	
}
