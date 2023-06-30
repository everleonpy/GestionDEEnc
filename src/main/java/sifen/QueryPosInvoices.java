package sifen;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.addon.gResProcLote;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaRUC;
import com.roshka.sifen.core.exceptions.SifenException;

import business.ApplicationMessage;
import business.UserAttributes;
import dao.PosTrxEbBatchItemsDAO;
import dao.PosTrxEbBatchesDAO;
import dao.RcvTaxPayersDAO;
import dao.RcvTrxEbBatchItemsDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.GenericIntList;
import pojo.PosInvoice;
import pojo.PosTrxEbBatch;
import pojo.PosTrxEbBatchItem;
import pojo.RcvTaxPayer;

public class QueryPosInvoices {

	private final static Logger logger = Logger.getLogger(QueryPosInvoices.class.toString());

	public static void setupSifenConfig() throws SifenException {
		SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		logger.info("Using CONFIG: " + sifenConfig);
		Sifen.setSifenConfig(sifenConfig);
	}
	
	public ApplicationMessage querySingleBatch( String batchNumber, long batchId, int itemsQty ) throws SifenException {
		Connection conn = null;
		ApplicationMessage am = new ApplicationMessage();
		int respCode = 0;
	    int itemsCounter = 0;
		int approvedQty = 0;
		int rejectedQty = 0;
		//

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

		// obtener la conexion a la base de datos
		conn = Util.getConnection();
		if (conn == null) {
			am.setMessage("SEND-INV", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
			return am;
		}
		
		// determinar si el lote tiene elementos cuyos resultados no han sido actualizados
		boolean existsNotUpdated = PosTrxEbBatchItemsDAO.existsNotUpdated(batchId, conn);
		if (existsNotUpdated == true) {
		    // invocar los servcios de Sifen para el envio de la consulta del lote
		    System.out.println("Enviando consulta lote...");
		    RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(batchNumber);
		    System.out.println(ret.toString());
		    System.out.println(ret.toString());
		    System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
		    System.out.println(ret.getRespuestaBruta().toString());

		    //System.out.println("1");
		    JacksonXmlModule module = new JacksonXmlModule();
            //System.out.println("2");
		    module.setDefaultUseWrapper(false);
		    //System.out.println("3");
		    XmlMapper xmlMapper = new XmlMapper(module);

		    try {
			    //System.out.println("4");
			    Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
                //System.out.println("5.1");
			    respCode = tmp.getBody().getrResEnviConsLoteDe().getdCodResLot();
			    //System.out.println("5.4");
			    ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
			    if (deRest != null) {
			        Iterator itr1 = deRest.iterator();
			        while (itr1.hasNext()) {
                        //System.out.println("7");
				        gResProcLote x = (gResProcLote) itr1.next();
				        System.out.println("ID: " + x.getId() + " | " +
	                           "Estado: " + x.getdEstRes() + " | " +
				               "Cod. Rpta.: " + x.getgResProc().getdCodRes() + " | " +
				               "Msj. Rpta.: " + x.getgResProc().getdMsgRes() );
                         //System.out.println("8");
				        PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				        o.setBatchId(batchId);
				        o.setControlCode(x.getId());
				        o.setResultCode(x.getgResProc().getdCodRes());
				        o.setResultMessage(x.getgResProc().getdMsgRes());
				        o.setResultStatus(x.getdEstRes());
				        PosTrxEbBatchItemsDAO.updateBatchItem(o, conn);
				        itemsCounter++;
				        if (x.getdEstRes().equalsIgnoreCase("APROBADO")) {
				        	    approvedQty++;
				        } else {
				        	    rejectedQty++;
				        }
			        }
		        }
			    // actualizar el estado del lote cuando fueron consultados todos sus elementos
			    if (itemsCounter == itemsQty) {
				    PosTrxEbBatchesDAO.updateQueriedFlag(batchId, conn);
		  	    }
		    } catch (JsonProcessingException e) {
			    //throw new RuntimeException(e);
			    e.printStackTrace();
		    } catch (Exception ex) {
			    ex.printStackTrace();
		    }
		
		    String msg = "Importacion finalizada. Cantidad de documentos: " + itemsCounter +
		    		         " Aprobados: " + approvedQty + " Rechazados:" + rejectedQty;
		    am.setMessage("QUERY-BATCH", msg, ApplicationMessage.MESSAGE);
		    return am;
		} else {
		    am.setMessage("QUERY-BATCH", "Este lote no contiene items pendientes de registrar", ApplicationMessage.MESSAGE);
		    return am;
		}
	}

	public ApplicationMessage queryBatchesList( java.util.Date trxDate ) throws SifenException {
		Connection conn = null;
		ApplicationMessage am = null;
		long batchId = 0;
		int itemsQty = 0;
		int itemsCounter = 0;
		int respCode = 0;
		int batchesCount = 0;
		int approvedCount = 0;
		int rejectedCount = 0;
		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);   
			return am;
		}

		// obtener la conexion a la base de datos
		conn = Util.getConnection();
		if (conn == null) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
			return am;
		}
		
		// obtener la lista de lotes no verificados de la fecha
		ArrayList<PosTrxEbBatch> lst = PosTrxEbBatchesDAO.getNotQueriedList("FACTURA", trxDate);
		if (lst != null) {
			Iterator itr1 = lst.iterator();
			while (itr1.hasNext()) {
				PosTrxEbBatch x = (PosTrxEbBatch) itr1.next();
				batchId = x.getIdentifier();
				itemsQty = x.getItemsQty();
				batchesCount++;
		        // determinar si el lote tiene elementos cuyos resultados no han sido actualizados
		        boolean existsNotUpdated = PosTrxEbBatchItemsDAO.existsNotUpdated(batchId, conn);
		        if (existsNotUpdated == true) {
		            // invocar los servcios de Sifen para el envio de la consulta del lote
		            System.out.println("Enviando consulta lote...");
		            RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(x.getBatchNumber());
		            System.out.println(ret.toString());
		            System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
		            System.out.println(ret.getRespuestaBruta().toString());
		            JacksonXmlModule module = new JacksonXmlModule();
		            module.setDefaultUseWrapper(false);
		            XmlMapper xmlMapper = new XmlMapper(module);
		            try {
			            Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
			            respCode = tmp.getBody().getrResEnviConsLoteDe().getdCodResLot();
			            itemsCounter = 0;
			            ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
			            if (deRest != null) {
			                Iterator itr2 = deRest.iterator();
			                while (itr2.hasNext()) {
				                gResProcLote d = (gResProcLote) itr2.next();
				                //System.out.println("ID: " + d.getId() + " | " +
	                            //       "Estado: " + d.getdEstRes() + " | " +
				                //       "Cod. Rpta.: " + d.getgResProc().getdCodRes() + " | " +
				                //       "Msj. Rpta.: " + d.getgResProc().getdMsgRes() );
				                PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				                o.setBatchId(batchId);
				                o.setControlCode(d.getId());
				                o.setResultCode(d.getgResProc().getdCodRes());
				                o.setResultMessage(d.getgResProc().getdMsgRes());
				                o.setResultStatus(d.getdEstRes());
				                PosTrxEbBatchItemsDAO.updateBatchItem(o, conn);
				                itemsCounter++;
    	    				            if (d.getdEstRes().equalsIgnoreCase("APROBADO") == true) {
    	    					            approvedCount++;
    	    				            } else {
    	    					            rejectedCount++;
    	    				            }
			                }
			                System.out.println(itemsCounter + " de " + itemsQty + " actualizados");
		                }
			            // actualizar el estado del lote cuando fueron consultados todos sus elementos
			            if (itemsCounter == itemsQty) {
				            PosTrxEbBatchesDAO.updateQueriedFlag(batchId, conn);
		  	            }
		            } catch (JsonProcessingException e) {
			            e.printStackTrace();
		            } catch (Exception ex) {
			            ex.printStackTrace();
		            }
		        }
			}
			am = new ApplicationMessage();
			String msgText = "Lotes consultados: " + batchesCount + " | Documentos aprobados: " + approvedCount + " | Documentos rechazados: "+ rejectedCount;
			am.setMessage("QUERY-BATCH", msgText, ApplicationMessage.MESSAGE);
		    return am;
		} else {
			am = new ApplicationMessage();
		    am.setMessage("QUERY-BATCH", "Este lote no contiene items pendientes de registrar", ApplicationMessage.MESSAGE);
		    return am;
		}
	}
	
	public int queryBatch ( long batchId, String batchNumber, int itemsQty, Connection conn ) {
		int itemsCounter = 0;
		int respCode = 0;
		// invocar los servcios de Sifen para el envio de la consulta del lote
		try {
            System.out.println("Enviando consulta lote...");
		    RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(batchNumber);
		    System.out.println(ret.toString());
	        System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
		    System.out.println(ret.getRespuestaBruta().toString());
		    //
		    JacksonXmlModule module = new JacksonXmlModule();
		    module.setDefaultUseWrapper(false);
		    XmlMapper xmlMapper = new XmlMapper(module);
			Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
			respCode = tmp.getBody().getrResEnviConsLoteDe().getdCodResLot();
			itemsCounter = 0;
			ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
			if (deRest != null) {
			     Iterator itr2 = deRest.iterator();
			     while (itr2.hasNext()) {
				     gResProcLote d = (gResProcLote) itr2.next();
				     PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				     o.setBatchId(batchId);
				     o.setControlCode(d.getId());
				     o.setResultCode(d.getgResProc().getdCodRes());
				     o.setResultMessage(d.getgResProc().getdMsgRes());
				     o.setResultStatus(d.getdEstRes());
				     PosTrxEbBatchItemsDAO.updateBatchItem(o, conn);
				     itemsCounter++;
			     }
			     System.out.println(itemsCounter + " de " + itemsQty + " actualizados");
		     }
			 // actualizar el estado del lote cuando fueron consultados todos sus elementos
			 if (itemsCounter == itemsQty) {
				  PosTrxEbBatchesDAO.updateQueriedFlag(batchId, conn);
		  	 }
			 return itemsCounter;
		 } catch (JsonProcessingException e) {
			 e.printStackTrace();
			 return 0;
		 } catch (Exception ex) {
			 ex.printStackTrace();
			 return 0;
		 }
	}
	
	public DocumentoElectronico queryCDC ( String controlCode, 
			                               long transactionId, 
			                               long controlId, 
			                               long cashId, 
			                               String txNumber ) throws SifenException {
		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			return null;
		} catch ( Exception e2 ) {
			return null;    	    	
		}
	    RespuestaConsultaDE ret = Sifen.consultaDE(controlCode);
	    System.out.println(ret.toString());
	    System.out.println("CODIGO ESTADO...: " + ret.getCodigoEstado());
	    System.out.println("CODIGO RESULTADO: " + ret.getdCodRes());
	    System.out.println("MSJ RESPUESTA...: " + ret.getdMsgRes());
	    System.out.println("RESP. BRUTA.....: " + ret.getRespuestaBruta());
	    DocumentoElectronico DE = ret.getxContenDE().getDE();
	    System.out.println("Tipo....: " + DE.getgTimb().getiTiDE().getDescripcion());
	    System.out.println("Numero..: " + DE.getgTimb().getdEst() + "-" + DE.getgTimb().getdPunExp() + "-" + DE.getgTimb().getdNumDoc());
	    System.out.println("Receptor: " + DE.getgDatGralOpe().getgDatRec().getdNomRec());
				
	    String usrName = UserAttributes.userName;
	    long orgId = UserAttributes.userOrg.getIDENTIFIER();
	    long unitId = UserAttributes.userUnit.getIDENTIFIER();
	    ApplicationMessage am = checkSendingLog ( DE, transactionId, controlId, cashId, txNumber, usrName, orgId, unitId );
	    return DE;
	}

	public ApplicationMessage queryTaxNumbers ( ArrayList<String> l) {
		ApplicationMessage am;
		String taxNo = null;
		String userName = UserAttributes.userName;
		long unitId = UserAttributes.userUnit.getIDENTIFIER();
		long orgId = UserAttributes.userOrg.getIDENTIFIER();
		long payerId = 0;
		int insertedRows = 0;
		int rows = 0;
		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			e1.printStackTrace();
			am = new ApplicationMessage();
			am.setMessage("GET-TAXPYRS", "Error SifenConfig: " + e1.getMessage(), ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			e2.printStackTrace();
			am = new ApplicationMessage();
			am.setMessage("GET-TAXPYRS", "Error SifenConfig: " + e2.getMessage(), ApplicationMessage.ERROR);
			return am;
		}
		// obtener una conexion a la base de datos
		Connection conn = Util.getConnection();
		if (conn == null) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
			return am;
		}
		// iterar el arreglo para determinar los rucs no existentes en la base de datos de la
		// aplicacion y crearlos
		Iterator itr1 = l.iterator();
		while (itr1.hasNext()) {
			String x = (String) itr1.next();
			//1494393-0
			//012345678
			if (x.indexOf('-') == -1) {
				taxNo = x;
			} else {
				taxNo = x.substring(0, x.indexOf('-'));
			}
			try {
		        RespuestaConsultaRUC ret = Sifen.consultaRUC(taxNo);
		        System.out.println("RUC: " + x);
			    System.out.println("CODIGO ESTADO...: " + ret.getCodigoEstado());
			    System.out.println("CODIGO RESULTADO: " + ret.getdCodRes());
			    System.out.println("MSJ RESPUESTA...: " + ret.getdMsgRes());
			    //System.out.println("RESP. BRUTA.....: " + ret.getRespuestaBruta());
			    if (ret.getxContRUC() != null) {
			        System.out.println("dCodEstCons.: " + ret.getxContRUC().getdCodEstCons());
			        System.out.println("dDesEstCons.: " + ret.getxContRUC().getdDesEstCons());
			        System.out.println("dRazCons....: " + ret.getxContRUC().getdRazCons());
			        System.out.println("dRUCCons....: " + ret.getxContRUC().getdRUCCons());
			        System.out.println("dRUCFactElec: " + ret.getxContRUC().getdRUCFactElec());
			        //
			        if (ret.getxContRUC().getdDesEstCons().equalsIgnoreCase("ACTIVO")) {
			            RcvTaxPayer tp = new RcvTaxPayer();
			            tp.setCreatedBy(userName);
			            tp.setCreatedOn(new java.util.Date());
			            tp.setFullName(ret.getxContRUC().getdRazCons());
					    payerId = UtilitiesDAO.getNextval("SQ_RCV_TAX_PAYERS", conn);
			            tp.setIdentifier(payerId);
			            tp.setOldTaxPayerNo(null);
			            tp.setOrgId(orgId);
			            tp.setTaxPayerNo(x);
			            tp.setUnitId(unitId);
			            // crear el registro del contribuyente en la base de datos de la aplicacion
			            rows = RcvTaxPayersDAO.addRow(tp, conn);
			            if (rows == 1) {
			        	        insertedRows++;
			            }
			        }
			    }
			} catch ( Exception e) {
				e.printStackTrace();
			}
	    }
		am = new ApplicationMessage();
		am.setMessage("GET-TAXPYRS", "Contribuyentes creados: " + insertedRows, ApplicationMessage.MESSAGE);
		return am;
	}
	
	private ApplicationMessage checkSendingLog ( DocumentoElectronico DE, 
			                                     long transactionId, 
			                                     long controlId,
			                                     long cashId,
			                                     String txNumber, 
			                                     String usrName, 
			                                     long orgId, 
			                                     long unitId ) {
		ApplicationMessage am;
		Connection conn = null;
		String cdc = DE.getId();
		String xmlFile = String.valueOf(transactionId) + ".xml";
		//
		try {
			// obtener una conexion a la base de datos
			conn = Util.getConnection();
			if (conn == null) {
				am = new ApplicationMessage();
				am.setMessage("CHK-ITM-LOG", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
				return am;
			}
			//
			boolean approvedFlag = RcvTrxEbBatchItemsDAO.existsApprovedLog(cdc);
			// en este punto, si no existe ningun registro de log correspondiente a la aprobacion
			// del documento, es porque no se pudo realizar en su momento la consulta del lote,
			// por tanto se debe registrar el log correspondiente
			if (approvedFlag == false) {
				PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				long itemId = UtilitiesDAO.getNextval("SQ_pos_TRX_EB_BATCH_ITEMS", conn);
				o.setBatchId(-1);
				o.setControlCode(cdc);
				o.setCreatedBy(usrName);
				o.setCreatedOn(new java.util.Date());
				o.setIdentifier(itemId);
				o.setOrgId(orgId);
				o.setResultCode(260);
				o.setResultMessage("Aprobado");
				o.setResultStatus("Aprobado");
				o.setResultTxNumber(0);
				o.setTransactionId(transactionId);
				o.setLocalDocNumber(txNumber);
				o.setUnitId(unitId);
				//o.setXmlFile(String.valueOf(transactionId) + ".xml");
				//o.setQrCode(DE.getEnlaceQR());
				PosTrxEbBatchItemsDAO.addRow(o, null, conn);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			am = new ApplicationMessage();
			am.setMessage("CHK-ITM-LOG", "Error al crear log: " + e.getMessage(), ApplicationMessage.ERROR);
			return am;
		} finally {
			Util.closeJDBCConnection(conn);
		}
	}
	
    private ApplicationMessage createSentLogs ( PosTrxEbBatch batch, 
    		                                        ArrayList<PosInvoice> sentList,
    		                                        String userName,
    		                                        Connection conn ) {
    	    String fileName = null;
		try {
		    // crear una entrada en la tabla de lotes enviados
		    System.out.println("createSentLogs: " + "crear lote");
		    int rows = PosTrxEbBatchesDAO.addRow(batch, conn);
			//
			long itemId = 0;
			// crear la lista de facturas que fueron incluidas en el lote
			Iterator itr2 = sentList.iterator();
			while (itr2.hasNext()) {
				PosInvoice x = (PosInvoice) itr2.next();
				PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				//System.out.println("createProcessLogs: " + "obtener secuencia item");
				itemId = UtilitiesDAO.getNextval("SQ_POS_TRX_EB_BATCH_ITEMS", conn);
				o.setIdentifier(itemId);
				o.setBatchId(batch.getIdentifier());
				o.setCashControlId(x.getControlId());
				o.setCashRegisterId(x.getCashId());
				o.setControlCode(x.getControlCode());
				o.setCreatedBy(userName);
				o.setCreatedOn(new java.util.Date());
				o.setOrgId(x.getOrgId());
				o.setTransactionId(x.getInvoiceId());
				o.setUnitId(x.getUnitId());
				fileName = String.valueOf(x.getCashId()) + "_" + String.valueOf(x.getControlId()) + "_" +
					     	String.valueOf(x.getInvoiceId()) + ".xml";
				//System.out.println("createProcessLogs: " + "crear item");
				PosTrxEbBatchItemsDAO.addRow(o, fileName, conn);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationMessage am = new ApplicationMessage();
			am.setMessage("PROC-LOG", "Error al registrar lotes: " + e.getMessage(), ApplicationMessage.ERROR);
			return am;
		} finally {
		}
    }
    
    private ApplicationMessage createNotSentLogs ( long batchId, 
    		                                           ArrayList<PosInvoice> notSentList,
    		                                           String userName, 
    		                                           Connection conn ) {
    	    String fileName = null;
    	    try {
    		    long itemId = 0;
    		    // crear la lista de facturas que fueron incluidas en el lote
    		    Iterator itr2 = notSentList.iterator();
    		    while (itr2.hasNext()) {
    			    PosInvoice x = (PosInvoice) itr2.next();
    			    PosTrxEbBatchItem o = new PosTrxEbBatchItem();
    			    //System.out.println("createProcessLogs: " + "obtener secuencia item");
    			    itemId = UtilitiesDAO.getNextval("SQ_POS_TRX_EB_BATCH_ITEMS", conn);
    			    o.setIdentifier(itemId);
    			    o.setBatchId(batchId);
    			    o.setCashControlId(x.getControlId());
    			    o.setCashRegisterId(x.getCashId());
    			    o.setControlCode(null);
    			    o.setCreatedBy(userName);
    			    o.setCreatedOn(new java.util.Date());
    			    o.setOrgId(x.getOrgId());
    			    o.setTransactionId(x.getInvoiceId());
    			    o.setUnitId(x.getUnitId());
    			    fileName = String.valueOf(x.getCashId()) + "_" + String.valueOf(x.getControlId()) + "_" +
    				           String.valueOf(x.getInvoiceId()) + ".xml";
    			    //System.out.println("createProcessLogs: " + "crear item");
    			    PosTrxEbBatchItemsDAO.addRow(o, fileName, conn);
    		    }
    		    return null;
    	    } catch (Exception e) {
    		    e.printStackTrace();
    		    ApplicationMessage am = new ApplicationMessage();
    		    am.setMessage("PROC-LOG", "Error al registrar lotes: " + e.getMessage(), ApplicationMessage.ERROR);
    		    return am;
    	    } finally {
       	}
    }

}
