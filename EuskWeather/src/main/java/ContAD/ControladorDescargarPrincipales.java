package ContAD;

import ModAD.DescargarPrincipales;

public class ControladorDescargarPrincipales {

	private DescargarPrincipales descPrinc;
	
	public ControladorDescargarPrincipales(DescargarPrincipales descPrinc) {
		this.descPrinc = descPrinc;
	}

	public void descPrinciples() {
		descPrinc.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/index.json", "./archJSON//index.json");
		descPrinc.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/estaciones.json", "./archJSON//estaciones.json");
		descPrinc.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_recursos_turisticos/playas_de_euskadi/opendata/espacios-naturales.json", "./archJSON//espacios-naturales.json");
		descPrinc.descargarFicheros("https://opendata.euskadi.eus/contenidos/ds_registros/registro_entidades_locales/opendata/entidades.json", "./archJSON//municipios.json");
	}
	
}
