package EuskWeather;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ModAD.InfoMeteorologica;
import ModAD.VolcarInfoMeteorologica;
import ModAD.convertirJSONXML;

public class VolcarInfoMeteorologicaTest {

	private VolcarInfoMeteorologica vim = new VolcarInfoMeteorologica();
	private InfoMeteorologica im = new InfoMeteorologica();
	private ArrayList<InfoMeteorologica> lista = new ArrayList<InfoMeteorologica>();
	
	@Test
	public void testCambiarNombreEstacion() {
		vim.cambiarNombreEstacion(lista);
	}
	
	@Test
	public void testObtenerNombreMunicipios() {
		String xml = convertirJSONXML.leerArchivo("./ficherosTest//index.xml", "utf-8");
		String[] resultado = vim.obtenerNombreMunicipios(xml);
		String[] resultadoEsperado = {"ABANTO", "ZUMARRAGA"};
		assertArrayEquals(resultado, resultadoEsperado);
	}
	
	@Test
	public void testLecturaDatos() {
		ArrayList<InfoMeteorologica> resultado = vim.lecturaDatos();
		ArrayList<InfoMeteorologica> resultadoEsperado = resultado;
		assertEquals(resultado, resultadoEsperado);
	}

}
