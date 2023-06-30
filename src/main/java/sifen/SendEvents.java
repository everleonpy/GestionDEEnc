package sifen;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.addon.gResProc;
import com.roshka.sifen.addon.gResProcEVe;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.EventosDE;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionEvento;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.event.TgGroupTiEvt;
import com.roshka.sifen.core.fields.request.event.TrGeVeCan;
import com.roshka.sifen.core.fields.request.event.TrGeVeInu;
import com.roshka.sifen.core.fields.request.event.TrGesEve;
import com.roshka.sifen.core.types.TTiDE;


import business.ApplicationMessage;
import business.UserAttributes;
import dao.PosEbEventsLogDAO;
import dao.RcvEbEventItemsLogDAO;
import dao.RcvEbEventsLogDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.CancelationEvent;
import pojo.DisabledNumber;
import pojo.DisablingEvent;
import pojo.PosEbEventItemLog;
import pojo.PosEbEventLog;
import pojo.RcvEbEventItemLog;
import pojo.RcvEbEventLog;

public class SendEvents {
    private final static Logger logger = Logger.getLogger(SendEvents.class.toString());

    public static void setupSifenConfig() throws SifenException {
        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
        logger.info("Using CONFIG: " + sifenConfig);
        Sifen.setSifenConfig(sifenConfig);
    }

    public ApplicationMessage sendPosCancelEvent( ArrayList<CancelationEvent> lst) throws SifenException {
    	    ApplicationMessage am = new ApplicationMessage();
        LocalDateTime currentDate = LocalDateTime.now();
        ArrayList<TrGesEve> evtLst = new ArrayList<TrGesEve>();
        //
		Connection conn = null;
		final short CANCELACION = 1;
		long eventId = 0;
		long itemId = 0;
		String usrName = UserAttributes.userName;
		long orgId = UserAttributes.userOrg.getIDENTIFIER();
		long unitId = UserAttributes.userUnit.getIDENTIFIER();
        

		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR); 
			return am;
		}

		try {
		    conn = Util.getConnection();
		    if (conn == null) {
				am.setMessage("SEND-INV", "No se pudo obtener la conexion a la base de datos", ApplicationMessage.ERROR); 
				return am;
		    }
		} catch ( Exception e ) {
			am.setMessage("SEND-INV", "Conexion BD: " + e.getMessage(), ApplicationMessage.ERROR); 
			return am;
		}
		
        Iterator itr1 = lst.iterator();
        while (itr1.hasNext()) {
           	CancelationEvent x = (CancelationEvent) itr1.next();
        	    //
            TrGeVeCan trGeVeCan = new TrGeVeCan();
            trGeVeCan.setId(x.getControlCode());
            trGeVeCan.setmOtEve(x.getCancelReason());
            //
            TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
            tgGroupTiEvt.setrGeVeCan(trGeVeCan);
            //
            TrGesEve rGesEve1 = new TrGesEve();
            //rGesEve1.setId(String.valueOf(x.getCashId() + "-" + String.valueOf(x.getCashControlId() + "-" + x.getTransactionId())));
            rGesEve1.setId(String.valueOf(x.getTransactionId()));
            rGesEve1.setdFecFirma(currentDate);
            rGesEve1.setgGroupTiEvt(tgGroupTiEvt);
        	    //
            System.out.println("Evento: " + trGeVeCan.getId() + " - " + trGeVeCan.getmOtEve());
            evtLst.add(rGesEve1);
        }
        //
        EventosDE eventosDE = new EventosDE();
        eventosDE.setrGesEveList(evtLst);

        RespuestaRecepcionEvento ret = Sifen.recepcionEvento(eventosDE);
        logger.info(ret.toString());
		logger.info("CODIGO DE ESTADO: " + ret.getCodigoEstado());
		logger.info("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
		logger.info("COD. RESPUESTA..: " + ret.getdCodRes());
		logger.info("MSG RESPUESTA...: " + ret.getdMsgRes());
		System.out.println("XML : " + ret.getRespuestaBruta());
		//
		System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
		System.out.println("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
		System.out.println("COD. RESPUESTA..: " + ret.getdCodRes());
		System.out.println("MSG RESPUESTA...: " + ret.getdMsgRes());
		System.out.println(ret.getRespuestaBruta().toString());

		//if (ret.getdCodRes().equalsIgnoreCase("Aceptado")) {
			try {
			    // registrar la cabecera del lote de eventos
			    PosEbEventLog v = new PosEbEventLog();
			    v.setCreatedBy(usrName);
			    v.setCreatedOn(new java.util.Date());
			    v.setEventTrxId(0);
		        eventId = UtilitiesDAO.getNextval("SQ_POS_EB_EVENTS_LOG", conn);
			    v.setIdentifier(eventId);
			    v.setOrgId(orgId);
			    v.setResultStatus(null);
			    v.setUnitId(unitId);
			    PosEbEventsLogDAO.addRow(v, conn);
		        // registrar el evento de cada transaccion en la base de datos
		        Iterator itr2 = lst.iterator();
		        while (itr2.hasNext()) {
		    	        CancelationEvent x = (CancelationEvent) itr2.next();
			        PosEbEventItemLog i = new PosEbEventItemLog();
			        i.setCreatedBy(usrName);
			        i.setCreatedOn(new java.util.Date());
			        i.setEbControlCode(x.getControlCode());
			        i.setEventLogId(eventId);
			        i.setEventReason(x.getCancelReason());
			        i.setEventTypeId(CANCELACION);
			        itemId = UtilitiesDAO.getNextval("SQ_POS_EB_EVENT_ITEMS_LOG", conn);
			        i.setIdentifier(itemId);
			        i.setOrgId(orgId);
			        i.setTransactionId(x.getTransactionId());
			        i.setCashControlId(x.getCashControlId());
			        i.setCashRegisterId(Long.valueOf(x.getCashId()));
			        i.setUnitId(unitId);
			        PosEbEventItemsLogDAO.addRow(i, conn);
		        }
			} catch (Exception e) {
				am.setMessage("SEND-EVT", "Generar log de envio: " + e.getMessage(), ApplicationMessage.MESSAGE);
				return am;				
			} finally {
				Util.closeJDBCConnection(conn);
			}
		//}
		
		am.setMessage("SEND-EVT", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
		return am;
		
    }
    
    public ApplicationMessage sendPosDisablingEvent( ArrayList<DisablingEvent> lst) throws SifenException {
    	
	    // el servicio esta orientado a recibir un rango de numeros, sin embargo el requerimiento
	    // del POS consiste en inutilizar los numeros de factura que no tienen importe ni han
	    // sido cobrados, por tanto no fueron entregados al cliente ni informados a sifen
	    // Estos numeros de facturas no son contiguos, por lo tanto deben ser enviados de a uno

	    ApplicationMessage am = new ApplicationMessage();
	    LocalDateTime currentDate = LocalDateTime.now();
	    //
	    Connection conn = null;
	    final short INUTILIZACION = 2;
	    long eventId = 0;
	    long itemId = 0;
	    long eventTrxId = 0;
	    String usrName = UserAttributes.userName;
	    long orgId = UserAttributes.userOrg.getIDENTIFIER();
	    long unitId = UserAttributes.userUnit.getIDENTIFIER();
	    int counter = 0;

	    // establecer la configuracion Sifen
	    try {
		    setupSifenConfig();
	    } catch ( SifenException e1 ) {
		    am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
		    return am;
	    } catch ( Exception e2 ) {
		    am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR); 
		    return am;
	    }

	    try {
		    conn = Util.getConnection();
		    if (conn == null) {
			    am.setMessage("SEND-INV", "No se pudo obtener la conexion a la base de datos", ApplicationMessage.ERROR); 
			    return am;
		    }
	    } catch ( Exception e ) {
		    am.setMessage("SEND-INV", "Conexion BD: " + e.getMessage(), ApplicationMessage.ERROR); 
		    return am;
	    }

	    Iterator itr1 = lst.iterator();
	    while (itr1.hasNext()) {
	    	    /**
	    	     +-------------------------------------------------------------+
	    	     | Construir las estructura de datos para envio de los eventos |
	    	     +-------------------------------------------------------------+
	    	     */
		    DisablingEvent x = (DisablingEvent) itr1.next();
		    //
		    ArrayList<TrGesEve> evtLst = new ArrayList<TrGesEve>();
		    //
	        TrGeVeInu trGeVeInu = new TrGeVeInu();
	        trGeVeInu.setdNumTim(x.getStampNo());
	        trGeVeInu.setdEst(x.getEstabCode());
	        trGeVeInu.setdPunExp(x.getIssuePointCode());
	        trGeVeInu.setdNumIn(x.getFirstNumber());
	        trGeVeInu.setdNumFin(x.getLastNumber());
	        trGeVeInu.setiTiDE(TTiDE.getByVal(x.getTxTypeId()));
	        trGeVeInu.setmOtEve(x.getDisablingReason());
	        //
	        TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
	        tgGroupTiEvt.setrGeVeInu(trGeVeInu);
	        //        
	        TrGesEve rGesEve1 = new TrGesEve();
	        // Corresponde al id autogenerado por el emisor, para identificar cada evento
			eventTrxId = UtilitiesDAO.getNextval("SQ_POS_EVENT_TRX_ID", conn);
	        rGesEve1.setId(String.valueOf(eventTrxId));
	        rGesEve1.setdFecFirma(currentDate);
	        rGesEve1.setgGroupTiEvt(tgGroupTiEvt);
		    //
		    System.out.println("dNumTim: " + trGeVeInu.getdNumTim() + " dEst: "  + trGeVeInu.getdEst() + " dPunExp: " + trGeVeInu.getdPunExp());
		    System.out.println(" dNumIn: " + trGeVeInu.getdNumIn() + " dNumFin: " + trGeVeInu.getdNumFin());
		    System.out.println(" iTide: " + trGeVeInu.getiTiDE().getVal() + " mOtEve: " + trGeVeInu.getmOtEve());
		    evtLst.add(rGesEve1);
		    //
		    EventosDE eventosDE = new EventosDE();
		    eventosDE.setrGesEveList(evtLst);

    	        /**
   	         +-------------------------------------------------------------+
   	         | Invocar al servicio de recepcion de eventos                 |
   	         +-------------------------------------------------------------+
   	        */
	    	    RespuestaRecepcionEvento ret = Sifen.recepcionEvento(eventosDE);
		    logger.info(ret.toString());
		    logger.info("CODIGO DE ESTADO: " + ret.getCodigoEstado());
		    logger.info("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
		    logger.info("COD. RESPUESTA..: " + ret.getdCodRes());
		    logger.info("MSG RESPUESTA...: " + ret.getdMsgRes());
		    //
		    System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
		    System.out.println("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
		    System.out.println("COD. RESPUESTA..: " + ret.getdCodRes());
		    System.out.println("MSG RESPUESTA...: " + ret.getdMsgRes());
		    System.out.println(ret.getRespuestaBruta().toString());

	        /**
	         +-------------------------------------------------------------+
	         | Decodificar la respuesta del servicio                       |
	         +-------------------------------------------------------------+
	        */
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			// XmlMapper xmlMapper = new XmlMapper(module);
			XmlMapper xmlMapper = new XmlMapper(module);

			try {
				Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(),Envelope.class);
				if (tmp != null) {
				    System.out.println("=============================== CONSULTA DE EVENTOS =====================================");
					ArrayList<gResProcEVe> res;
					res = (ArrayList<gResProcEVe>) tmp.getBody().rRetEnviEventoDe.getgResProcEVe();
				    Iterator itr2 = res.iterator();
				    while (itr2.hasNext()) {
					    gResProcEVe r = (gResProcEVe) itr2.next();
					    System.out.println("Id. Evento.......: " + r.getId());
					    System.out.println("Codigo Resultado.: " + r.getdCodRes());
					    System.out.println("Estado Resultado.: " + r.getdEstRes());
					    System.out.println("Msj. Resultado...: " + r.getdMsgRes());
					    ArrayList<gResProc> itm;
					    itm = (ArrayList<gResProc>) r.getgResProc();
					    Iterator itr3 = itm.iterator();
					    while (itr3.hasNext()) {
					    	    gResProc a = (gResProc) itr3.next();
							System.out.println("Codigo Resultado.: " + a.getdCodRes());
						    System.out.println("Mensaje Resultado: " + a.getdMsgRes());
						    // generar el log del envio del evento
						    counter++;
							try {
								if (counter == 1) {
								    // registrar la cabecera del lote de eventos
								    PosEbEventLog v = new PosEbEventLog();
								    v.setCreatedBy(usrName);
								    v.setCreatedOn(new java.util.Date());
								    v.setEventTrxId(0);
								    eventId = UtilitiesDAO.getNextval("SQ_POS_EB_EVENTS_LOG", conn);
								    v.setIdentifier(eventId);
								    v.setOrgId(orgId);
								    v.setResultStatus(null);
								    v.setUnitId(unitId);
								    PosEbEventsLogDAO.addRow(v, conn);
								}
								// registrar el evento de cada transaccion en la base de datos
								PosEbEventItemLog i = new PosEbEventItemLog();
								i.setCreatedBy(usrName);
								i.setCreatedOn(new java.util.Date());
								i.setEbControlCode(null);
								i.setEventLogId(eventId);
								i.setEventReason(x.getDisablingReason());
								i.setEventTypeId(INUTILIZACION);
								itemId = UtilitiesDAO.getNextval("SQ_POS_EB_EVENT_ITEMS_LOG", conn);
								i.setIdentifier(itemId);
								i.setEventTrxId(eventTrxId);
								i.setCashControlId(x.getCashControlId());
								i.setCashRegisterId(x.getCashId());
								i.setOrgId(orgId);
								i.setTransactionId(x.getTransactionId());
								i.setUnitId(unitId);
								i.setResultCode(a.getdCodRes());
								i.setResultMessage(a.getdMsgRes());
								i.setResultStatus(r.getdEstRes());
								PosEbEventItemsLogDAO.addRow(i, conn);
							} catch (Exception e) {
								System.out.println("Error al generar log...");
								e.printStackTrace();
							}
					    }
				    }
				    System.out.println("=============================== CONSULTA DE EVENTOS =====================================");
				    
				} else {
					am = new ApplicationMessage();
					am.setMessage("DECODE-RESP", "El servicio no responde o se perdio la conexion", ApplicationMessage.ERROR);
					return am;							
				}

			} catch (JsonProcessingException e) {
				e.printStackTrace();
				am = new ApplicationMessage();
				am.setMessage("DECODE-RESP", "Error al leer respuesta: " + e.getMessage(), ApplicationMessage.ERROR);
				return am;
			}

	    }

        am.setMessage("SEND-EVT", "Eventos enviados: " + counter, ApplicationMessage.MESSAGE);
        return am;

    }
    

    public ApplicationMessage sendRcvCancelEvent( ArrayList<CancelationEvent> lst) throws SifenException {
    	    ApplicationMessage am = new ApplicationMessage();
    	    LocalDateTime currentDate = LocalDateTime.now();
    	    ArrayList<TrGesEve> evtLst = new ArrayList<TrGesEve>();
    	    //
    	    Connection conn = null;
    	    final short CANCELACION = 1;
    	    long eventId = 0;
    	    long itemId = 0;
    	    String usrName = UserAttributes.userName;
    	    long orgId = UserAttributes.userOrg.getIDENTIFIER();
    	    long unitId = UserAttributes.userUnit.getIDENTIFIER();

    	    // establecer la configuracion Sifen
    	    try {
    		    setupSifenConfig();
    	    } catch ( SifenException e1 ) {
    		    am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
    		    return am;
    	    } catch ( Exception e2 ) {
    		    am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR); 
    		    return am;
    	    }

    	    try {
    		    conn = Util.getConnection();
    		    if (conn == null) {
    			    am.setMessage("SEND-INV", "No se pudo obtener la conexion a la base de datos", ApplicationMessage.ERROR); 
    			    return am;
    		    }
    	    } catch ( Exception e ) {
    		    am.setMessage("SEND-INV", "Conexion BD: " + e.getMessage(), ApplicationMessage.ERROR); 
    		    return am;
    	    }

    	    Iterator itr1 = lst.iterator();
    	    while (itr1.hasNext()) {
    		    CancelationEvent x = (CancelationEvent) itr1.next();
    		    //
    		    TrGeVeCan trGeVeCan = new TrGeVeCan();
    		    trGeVeCan.setId(x.getControlCode());
    		    trGeVeCan.setmOtEve(x.getCancelReason());
    		    //
    		    TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
    		    tgGroupTiEvt.setrGeVeCan(trGeVeCan);
    		    //
    		    TrGesEve rGesEve1 = new TrGesEve();
    		    rGesEve1.setId(String.valueOf(x.getTransactionId()));
    		    rGesEve1.setdFecFirma(currentDate);
    		    rGesEve1.setgGroupTiEvt(tgGroupTiEvt);
    		    //
    		    System.out.println("CDC: " + trGeVeCan.getId() + " Mot.: " + trGeVeCan.getmOtEve() + " Tx. Id.: " + rGesEve1.getId());
    		    evtLst.add(rGesEve1);
    	    }
    	    //
    	    EventosDE eventosDE = new EventosDE();
    	    eventosDE.setrGesEveList(evtLst);

        	RespuestaRecepcionEvento ret = Sifen.recepcionEvento(eventosDE);
    	    logger.info(ret.toString());
    	    logger.info("CODIGO DE ESTADO: " + ret.getCodigoEstado());
    	    logger.info("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
    	    logger.info("COD. RESPUESTA..: " + ret.getdCodRes());
    	    logger.info("MSG RESPUESTA...: " + ret.getdMsgRes());
    	    System.out.println("XML : " + ret.getRespuestaBruta());
    	    //
    	    System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
    	    System.out.println("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
    	    System.out.println("COD. RESPUESTA..: " + ret.getdCodRes());
    	    System.out.println("MSG RESPUESTA...: " + ret.getdMsgRes());
    	    System.out.println(ret.getRespuestaBruta().toString());

    	    if (ret.getdCodRes().equalsIgnoreCase("Aceptado")) {
    		    try {
    			    // registrar la cabecera del lote de eventos
    			    RcvEbEventLog v = new RcvEbEventLog();
    			    v.setCreatedBy(usrName);
    			    v.setCreatedOn(new java.util.Date());
    			    v.setEventTrxId(0);
    			    eventId = UtilitiesDAO.getNextval("SQ_RCV_EB_EVENTS_LOG", conn);
    			    v.setIdentifier(eventId);
    			    v.setOrgId(orgId);
    			    v.setResultStatus(null);
    			    v.setUnitId(unitId);
    			    RcvEbEventsLogDAO.addRow(v, conn);
    			    // registrar el evento de cada transaccion en la base de datos
    			    Iterator itr2 = lst.iterator();
    			    while (itr2.hasNext()) {
    				    CancelationEvent x = (CancelationEvent) itr2.next();
    				    RcvEbEventItemLog i = new RcvEbEventItemLog();
    				    i.setCreatedBy(usrName);
    				    i.setCreatedOn(new java.util.Date());
    				    i.setEbControlCode(x.getControlCode());
    				    i.setEventLogId(eventId);
    				    i.setEventReason(x.getCancelReason());
    				    i.setEventTypeId(CANCELACION);
    				    itemId = UtilitiesDAO.getNextval("SQ_RCV_EB_EVENT_ITEMS_LOG", conn);
    				    i.setIdentifier(itemId);
    				    i.setOrgId(orgId);
    				    i.setTransactionId(x.getTransactionId());
    				    i.setUnitId(unitId);
    				    RcvEbEventItemsLogDAO.addRow(i, conn);
    			    }
    		    } catch (Exception e) {
    			    am.setMessage("SEND-EVT", "Generar log de envio: " + e.getMessage(), ApplicationMessage.MESSAGE);
    			    return am;				
    		    }
    	    }
	
	    am.setMessage("SEND-EVT", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
	    return am;
	
    }
    
    public ApplicationMessage sendRcvDisablingEvent( ArrayList<DisablingEvent> lst) throws SifenException {
	    ApplicationMessage am = new ApplicationMessage();
	    LocalDateTime currentDate = LocalDateTime.now();
	    ArrayList<TrGesEve> evtLst = new ArrayList<TrGesEve>();
	    //
	    Connection conn = null;
	    final short INUTILIZACION = 2;
	    long eventId = 0;
	    long itemId = 0;
	    String usrName = UserAttributes.userName;
	    long orgId = UserAttributes.userOrg.getIDENTIFIER();
	    long unitId = UserAttributes.userUnit.getIDENTIFIER();

	    // establecer la configuracion Sifen
	    try {
		    setupSifenConfig();
	    } catch ( SifenException e1 ) {
		    am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
		    return am;
	    } catch ( Exception e2 ) {
		    am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR); 
		    return am;
	    }

	    try {
		    conn = Util.getConnection();
		    if (conn == null) {
			    am.setMessage("SEND-INV", "No se pudo obtener la conexion a la base de datos", ApplicationMessage.ERROR); 
			    return am;
		    }
	    } catch ( Exception e ) {
		    am.setMessage("SEND-INV", "Conexion BD: " + e.getMessage(), ApplicationMessage.ERROR); 
		    return am;
	    }

	    Iterator itr1 = lst.iterator();
	    while (itr1.hasNext()) {
		    DisablingEvent x = (DisablingEvent) itr1.next();
		    //
	        TrGeVeInu trGeVeInu = new TrGeVeInu();
	        trGeVeInu.setdNumTim(x.getStampNo());
	        trGeVeInu.setdEst(x.getEstabCode());
	        trGeVeInu.setdPunExp(x.getIssuePointCode());
	        trGeVeInu.setdNumIn(x.getFirstNumber());
	        trGeVeInu.setdNumFin(x.getLastNumber());
	        trGeVeInu.setiTiDE(TTiDE.getByVal(x.getTxTypeId()));
	        trGeVeInu.setmOtEve(x.getDisablingReason());
	        //
	        TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
	        tgGroupTiEvt.setrGeVeInu(trGeVeInu);
	        //        
	        TrGesEve rGesEve1 = new TrGesEve();
	        rGesEve1.setId(String.valueOf(x.getTransactionId()));
	        rGesEve1.setdFecFirma(currentDate);
	        rGesEve1.setgGroupTiEvt(tgGroupTiEvt);
		    //
		    System.out.println("Evento: " + trGeVeInu.getdEst() + "-" + trGeVeInu.getdPunExp() + "-" + trGeVeInu.getdNumIn());
		    evtLst.add(rGesEve1);
	    }
	    //
	    EventosDE eventosDE = new EventosDE();
	    eventosDE.setrGesEveList(evtLst);

    	    RespuestaRecepcionEvento ret = Sifen.recepcionEvento(eventosDE);
	    logger.info(ret.toString());
	    logger.info("CODIGO DE ESTADO: " + ret.getCodigoEstado());
	    logger.info("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
	    logger.info("COD. RESPUESTA..: " + ret.getdCodRes());
	    logger.info("MSG RESPUESTA...: " + ret.getdMsgRes());
	    System.out.println("XML : " + ret.getRespuestaBruta());
	    //
	    System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
	    System.out.println("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());
	    System.out.println("COD. RESPUESTA..: " + ret.getdCodRes());
	    System.out.println("MSG RESPUESTA...: " + ret.getdMsgRes());
	    System.out.println(ret.getRespuestaBruta().toString());

		try {
			// registrar la cabecera del lote de eventos
			RcvEbEventLog v = new RcvEbEventLog();
			v.setCreatedBy(usrName);
			v.setCreatedOn(new java.util.Date());
			v.setEventTrxId(0);
			eventId = UtilitiesDAO.getNextval("SQ_RCV_EB_EVENTS_LOG", conn);
			v.setIdentifier(eventId);
			v.setOrgId(orgId);
			v.setResultStatus(null);
			v.setUnitId(unitId);
			RcvEbEventsLogDAO.addRow(v, conn);
			// registrar el evento de cada transaccion en la base de datos
			Iterator itr2 = lst.iterator();
			while (itr2.hasNext()) {
				DisablingEvent x = (DisablingEvent) itr2.next();
				RcvEbEventItemLog i = new RcvEbEventItemLog();
				i.setCreatedBy(usrName);
				i.setCreatedOn(new java.util.Date());
				i.setEbControlCode(String.valueOf(x.getTransactionId()));
				i.setEventLogId(eventId);
				i.setEventReason(x.getDisablingReason());
				i.setEventTypeId(INUTILIZACION);
				itemId = UtilitiesDAO.getNextval("SQ_RCV_EB_EVENT_ITEMS_LOG", conn);
				i.setIdentifier(itemId);
				i.setOrgId(orgId);
				i.setTransactionId(x.getTransactionId());
				i.setUnitId(unitId);
				RcvEbEventItemsLogDAO.addRow(i, conn);
			}
		} catch (Exception e) {
			am.setMessage("SEND-EVT", "Generar log de envio: " + e.getMessage(), ApplicationMessage.MESSAGE);
			return am;				
		}

        am.setMessage("SEND-EVT", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
        return am;

    }
    
    public void sendDisablingEvent( DisabledNumber d ) throws SifenException {
        LocalDateTime currentDate = LocalDateTime.now();

        TrGeVeInu trGeVeInu = new TrGeVeInu();
        trGeVeInu.setdEst(d.getdEst());
        trGeVeInu.setdNumFin(d.getdNumFin());
        trGeVeInu.setdNumIn(d.getdNumIn());
        trGeVeInu.setdNumTim(d.getdNumTim());
        trGeVeInu.setdPunExp(d.getdPunExp());
        trGeVeInu.setiTiDE(d.getiTiDE());
        trGeVeInu.setmOtEve(d.getmOtEve());
        //
        TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
        tgGroupTiEvt.setrGeVeInu(trGeVeInu);
        //        
        TrGesEve rGesEve1 = new TrGesEve();
        rGesEve1.setId("1");
        rGesEve1.setdFecFirma(currentDate);
        rGesEve1.setgGroupTiEvt(tgGroupTiEvt);

        EventosDE eventosDE = new EventosDE();
        eventosDE.setrGesEveList(Collections.singletonList(rGesEve1));

        RespuestaRecepcionEvento ret = Sifen.recepcionEvento(eventosDE);
        logger.info(ret.toString());
    }

}
