package sifen;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionDE;
import com.roshka.sifen.core.exceptions.SifenException;

import business.AppConfig;
import business.ApplicationMessage;
import dao.PosTransactionsDAO;
import dao.UtilitiesDAO;
import pojo.PosEbTransmissionLog;
import pojo.RespuestaSifen;
import util.TransmissionLog;

public class SendPosInvoice {

	private final static Logger logger = Logger.getLogger(SendPosInvoice.class.toString());

    public RespuestaSifen sendDE (  DocumentoElectronico DE, 
    		                               long invoiceId, 
    		                               long controlId, 
    		                               long cashId, 
    		                               long orgId, 
    		                               long unitId, 
    		                               String usrName ) throws SifenException {
       	ApplicationMessage am = new ApplicationMessage();
     	Envelope respBody = null;

     	// crear la instancia del objeto respuesta
     	RespuestaSifen resp = new RespuestaSifen();
     	
    	    // establecer la configuracion Sifen
    	    try {
    	        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
    	        logger.info("Using CONFIG: " + sifenConfig);
    	        Sifen.setSifenConfig(sifenConfig);
    	        
    	        // procesar el documento electronico recibido
    	     	String CDC = DE.obtenerCDC(null);
        	    logger.info("CDC del Documento Electrónico -> " + CDC);
         	System.out.println("CDC del Documento Electrónico -> " + CDC);

           	// invocar los servcios de Sifen para el envio de la factura
        	    RespuestaRecepcionDE ret = Sifen.recepcionDE(DE);
        	    logger.info(ret.toString());
        	    System.out.println(ret.toString());
        	    System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
        	    System.out.println(ret.getRespuestaBruta().toString());

        	    logger.info("CODIGO DE ESTADO : "+ret.getCodigoEstado());
        	    logger.info("RESPUESTA BRUTA  : "+ret.getRespuestaBruta());
        	    logger.info("COD RES :"+ret.getdCodRes());
        	    logger.info("MSG RESP :"+ret.getdMsgRes());
        	    System.out.println("XML : "+ret.getRespuestaBruta());


        	    // descargar el archivo xml generado en la carpeta destinada para el efecto
        	    String xmlFile = DE.getgTimb().getdNumDoc() + "_fa_envio.xml";
        	    String fileName = AppConfig.xmlFolder + xmlFile;
        	    DE.generarXml(fileName);


        	    XmlMapper xmlMapper = new XmlMapper();
          	try {
        		    respBody = xmlMapper.readValue(ret.getRespuestaBruta(),Envelope.class);
            		// completar los datos de estados y mensajes del objeto respuesta
            		resp.setCodigo(respBody.getBody().getrRetEnviDe().getrProtDe().getgResProc().getdCodRes());
            		resp.setEstado(respBody.getBody().getrRetEnviDe().getrProtDe().getdEstRes());
            		resp.setMensaje(respBody.getBody().getrRetEnviDe().getrProtDe().getgResProc().getdMsgRes());
        		    System.out.println("===================================================================");
        		    System.out.println("Estado  : " + resp.getEstado());
        		    System.out.println("Codigo  : " + resp.getCodigo());
        		    System.out.println("Mensaje : " + resp.getMensaje());
        		    System.out.println("-------------------------------------------------------------------");
        		    // de acuerdo a la respuesta del sifen, aqui se deben actualizar los datos de firma
        		    // y emision del documento, el codigo de seguridad, el CDC, el codigo QR y generar
        		    // el log de transmision del documento
        		    if (resp.getEstado().equalsIgnoreCase("Aprobado")) {
        		    	    // completar los datos del codigo de control y el codigo qr del objeto respuesta
        		    	    resp.setCodigoControl(CDC);
        		    	    resp.setCodigoQr(DE.getEnlaceQR());
        		        LocalDateTime d = DE.getdFecFirma();
        		        Timestamp timeStamp = Timestamp.valueOf(d);
        		        java.util.Date signDate = new java.util.Date(timeStamp.getTime());
        		        // completar los datos de fecha de firma y archivo xml de la respuesta
        		        resp.setFechaFirma(signDate);
        		        resp.setArchivoXml(xmlFile);
        		    } 
        		    // generar el log del evento
        		    PosEbTransmissionLog tLog = new PosEbTransmissionLog();
        		    long logId = UtilitiesDAO.getNextval("SQ_POS_EB_TRANSMISSION_LOG");
        		    tLog.setIdentifier(logId);
        		    tLog.setErrorCode(String.valueOf(resp.getCodigo()));
        		    tLog.setErrorMsg(resp.getMensaje());
        		    tLog.setEventId(TransmissionLog.ENVIO_TRANSACCION.getVal());
        		    tLog.setIdentifier(logId);
        		    tLog.setOrgId(orgId);
        		    tLog.setTransactionId(invoiceId);
        		    tLog.setCashControlId(controlId);
        		    tLog.setCashId(cashId);
        		    tLog.setUnitId(unitId);
        		    int updated = PosTransactionsDAO.createTransmissionLog(tLog);
        		    //
        		    if (resp.getEstado().equalsIgnoreCase("Aprobado")) {
        		        am.setMessage("SEND-INV", "El documento enviado ha sido aprobado", ApplicationMessage.MESSAGE);
        		    } else {
        		        am.setMessage("SEND-INV", "El documento enviado ha sido " + resp.getEstado(), ApplicationMessage.ERROR);            	
        		    }
        		    resp.setMensajeApp(am);
        		    return resp;
    	        } catch (JsonProcessingException e) {
    		        e.printStackTrace();
    		        am.setMessage("SEND-INV", "No se pudo determinar informacion de la respuesta", ApplicationMessage.ERROR);
    		        resp.setMensajeApp(am);
    		        return resp;
    	        }       

    	    } catch ( SifenException e1 ) {
    		    am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
    		    resp.setMensajeApp(am);
    		    return resp;
     	} catch ( Exception e2 ) {
    		    am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
    		    resp.setMensajeApp(am);
    		    return resp;
    	    }

    }
        
}
