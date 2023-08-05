package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.exceptions.SifenException;

import dao.RcvTrxEbBatchItemsDAO;
import dto.RcvTrxEbBatchItemDTO;

public class ConsultaCDC {
	
	private final static Logger logger = Logger.getLogger(ConsultaCDC.class.toString());

	public static void main(String[] args) throws SifenException, ParseException 
	{
		 
		 SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		 Sifen.setSifenConfig(sifenConfig);
		 
		 RespuestaConsultaDE ret = Sifen.consultaDE("01800805534001002000000722021040613265708133");
		 System.out.println("**** RESPUESTA DE    :"+ret.getCodigoEstado());
		// System.out.println("**** RESPUESTA BRUTA :"+ret.getRespuestaBruta());
		 System.out.println("**** : "+ret.getdMsgRes());
		 
		/* RcvTrxEbBatchItemsDAO dao = new RcvTrxEbBatchItemsDAO();
		 
		 
		 String txtDate = "28/06/2023";
		 Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(txtDate);  
		 
		 List<RcvTrxEbBatchItemDTO> resp = dao.getCDC(date1);
		 
		 for (RcvTrxEbBatchItemDTO rsp : resp) {
			System.out.println(rsp.getCdc());
		}*/

	}

}
