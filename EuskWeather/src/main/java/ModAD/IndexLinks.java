package ModAD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "indexlinks", uniqueConstraints = {@UniqueConstraint(columnNames = "idIndex"),@UniqueConstraint(columnNames = "nombreEst"),
		@UniqueConstraint(columnNames = "enlace")})

public class IndexLinks implements Serializable{

	@Id
	@Column(name = "idIndex", unique = true, nullable = false)
	private int idIndex;
	
	@Column(name = "nombreEst", unique = true, nullable = false)
	private String nombreMuni;
	
	@Column(name = "enlace", unique = true, nullable = false)
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
