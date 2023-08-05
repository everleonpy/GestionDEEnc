package test;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaRUC;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.response.ruc.TxContRuc;

public class ConsultaRUC {

	public static void main(String[] args) throws SifenException 
	{
		 
		SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		Sifen.setSifenConfig(sifenConfig);
		
		RespuestaConsultaRUC ret = Sifen.consultaRUC("1362536");
		TxContRuc txContRuc = ret.getxContRUC();
		
		System.out.println("**********************************************************");
		System.out.println("*** "+txContRuc.getdRazCons());
		System.out.println("*** Facturador Electronico :"+txContRuc.getdRUCFactElec());
		System.out.println("*** "+txContRuc.getdDesEstCons());
		System.out.println("*** "+txContRuc.getdRUCCons());
		System.out.println("**********************************************************");
		
	}

}
