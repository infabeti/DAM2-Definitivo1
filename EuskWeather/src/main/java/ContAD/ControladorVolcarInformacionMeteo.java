package ContAD;

import java.util.ArrayList;

import ModAD.InfoMeteorologica;
import ModAD.VolcarInfoMeteorologica;

public class ControladorVolcarInformacionMeteo {
	private VolcarInfoMeteorologica volcarInfo;
		
	public ControladorVolcarInformacionMeteo(VolcarInfoMeteorologica volcarInfo) {
		this.volcarInfo = volcarInfo;
	}

	public void volcarInfor() {
		ArrayList<InfoMeteorologica> listadoInfoMeteorologica = new ArrayList<InfoMeteorologica>();
		listadoInfoMeteorologica = volcarInfo.lecturaDatos();
		volcarInfo.volcarInformacion(listadoInfoMeteorologica);
	}

}
