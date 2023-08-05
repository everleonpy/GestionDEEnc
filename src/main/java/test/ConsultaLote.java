package test;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.exceptions.SifenException;

public class ConsultaLote {

	public static void main(String[] args) throws SifenException 
	{
		 SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		 Sifen.setSifenConfig(sifenConfig);
		 
		 RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE("111701966314934697");
		 System.out.println("RESP : "+ret.getRespuestaBruta());		

	}

}
