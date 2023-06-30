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
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionLoteDE;
import com.roshka.sifen.core.exceptions.SifenException;

import business.ApplicationMessage;
import dao.FacturaPosElectronicaDAO;
import dao.PosTrxEbBatchItemsDAO;
import dao.PosTrxEbBatchesDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.DocumElectronico;
import pojo.GenericIntList;
import pojo.PosInvoice;
import pojo.PosTrxEbBatch;
import pojo.PosTrxEbBatchItem;

public class SendPosInvoicesAsync extends Thread {
	
	private final static Logger logger = Logger.getLogger(SendPosInvoicesAsync.class.toString());
	String trxType = null;
    java.util.Date trxDate = null;
    int groupFrom = 0;
    int groupTo = 0;
    int qtyPerGroup = 0;
    long orgId = 0;
    long unitId = 0;
    String userName	= null;
	String processName = "Envio de Transacciones POS";
	String currentTask = "Iniciando...";
	String endMessage = null;
	
	public String getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}

	public String getEndMessage() {
		return endMessage;
	}

	public void setEndMessage(String endMessage) {
		this.endMessage = endMessage;
	}

	public SendPosInvoicesAsync ( String trxType,
                                  java.util.Date trxDate,
                                  int groupFrom, 
                                  int groupTo,
                                  int qtyPerGroup,
                                  long orgId, 
                                  long unitId, 
                                  String userName ) {
		this.trxType = trxType;
        this.trxDate = trxDate;
        this.groupFrom = groupFrom;
        this.groupTo = groupTo;
        this.qtyPerGroup = qtyPerGroup;
        this.orgId = orgId;
        this.unitId = unitId;
        this.userName = userName;
	}

    public void run () {
		try {
			ApplicationMessage aMsg = sendDeBatch ( trxType,
                                                     trxDate,
                                                     groupFrom, 
                                                     groupTo,
                                                     qtyPerGroup,
                                                     orgId, 
                                                     unitId, 
                                                     userName );
			endMessage = aMsg.getText();
			currentTask = aMsg.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public static void setupSifenConfig() throws SifenException {
		SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		logger.info("Using CONFIG: " + sifenConfig);
		Sifen.setSifenConfig(sifenConfig);
	}

	public ApplicationMessage sendDeBatch ( String trxType,
			                                java.util.Date trxDate,
			                                int groupFrom, 
			                                int groupTo,
			                                int qtyPerGroup,
			                                long orgId, 
			                                long unitId, 
			                                String userName ) throws SifenException {
		Connection conn = null;
		ApplicationMessage am = null;
		int successCounter = 0;
		int failCounter = 0;
		String batchNo = null;
		java.util.Date batchDate = new java.util.Date();
		int respCode = 0;
		int resultCode = 0;
		String resultMsg = null;
		int processTime = 0;
		String task;
		boolean generateXml = false;
		String fileName;
		boolean fileCreated;
		int batchesCount = 0;
		int approvedCount = 0;
		int rejectedCount = 0;
		int queryCount = 0;
		int queryQty = 10;
		ArrayList<PosTrxEbBatch> batchesList = null;

		// establecer la configuracion Sifen
		try {
			task = "Config. Sifen";
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

		// obtener una conexion a la base de datos
		task = "Conexion BD";
		conn = Util.getConnection();
		if (conn == null) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
			return am;
		}

		try {
			int groupNo = groupFrom;
			while (groupNo <= groupTo) {
				successCounter = 0;
				// arreglo para guardar la lista de transacciones que pudieron ser enviadas
				ArrayList<PosInvoice> sentList = new ArrayList<PosInvoice>();
				// arreglo para guardar la lista de transacciones que no pudieron ser enviadas
				ArrayList<PosInvoice> notSentList = new ArrayList<PosInvoice>();
				// arreglo para guardar la lista de documentos electronicos
				ArrayList<DocumentoElectronico> deList = new ArrayList<DocumentoElectronico>();
				// arreglo para guardar la lista de documentos auxiliares
				task = "Obtener grupo No. " + groupNo;
				ArrayList<DocumElectronico> daList = FacturaPosElectronicaDAO.getPosInvoices( trxDate, groupNo, qtyPerGroup, orgId );
				if (daList != null) {
					Iterator itr1 = daList.iterator();
					while (itr1.hasNext()) {
						DocumElectronico x = (DocumElectronico) itr1.next();
						PosInvoicesMapper mapper = new PosInvoicesMapper();
						DocumentoElectronico DE = mapper.mapInvoice(x, x.getCashId(), x.getControlId(), x.getTransactionId(), conn);
						if (DE == null) {
							currentTask = "No se pudo generar DE para " +  x.getDE().getgTimb().getdNumDoc();
							System.out.println("No se pudo generar DE para: " + x.getCashId() + " - " + x.getControlId() + " - " + x.getTransactionId());
						}
						if (DE != null) {
							if (DE.getId() != null) {
								currentTask = "Cargando " + DE.getId();
								System.out.println("Cargando al arreglo: " + x.getCashId() + "-" + x.getControlId() + "-" + x.getTransactionId() + " - " + DE.getId());
								deList.add(DE);
								if (generateXml == true) {
						            fileName = "/Users/jota_ce/Documents/cacique-sifen/xml/pos/" + String.valueOf(x.getCashId()) + "_" + String.valueOf(x.getControlId()) + "_" +
					     		               String.valueOf(x.getTransactionId()) + ".xml";
						            try {
					                    fileCreated = DE.generarXml(fileName);
						            } catch ( Exception e) {
							            e.printStackTrace();
						            }
								}
								// agregar a la lista de transacciones cuyo DE fue generado y podran ser enviadas
								PosInvoice sentTx = new PosInvoice();
								sentTx.setInvoiceId(x.getTransactionId());
								sentTx.setCashId(x.getCashId());
								sentTx.setControlId(x.getControlId());
								sentTx.setControlCode(DE.getId());
								sentTx.setOrgId(orgId);
								sentTx.setUnitId(unitId);
								sentList.add(sentTx);
								successCounter++;
							} else {
								// agregar a la lista de transacciones cuyo DE no pudo ser generado
								PosInvoice notSentTx = new PosInvoice();
								notSentTx.setInvoiceId(x.getTransactionId());
								notSentTx.setCashId(x.getCashId());
								notSentTx.setControlId(x.getControlId());
								notSentTx.setControlCode(null);
								notSentTx.setOrgId(orgId);
								notSentTx.setUnitId(unitId);
								notSentList.add(notSentTx);
								failCounter++;							
								System.out.println("No se pudo generar el CDC para: " + 
										x.getTransactionId() + " - " + x.getControlId() + " - " + x.getCashId());							
							}
						}
					}
				}
				System.out.println("Grupo " + groupNo + " Total de transacciones: " + successCounter);
				//counter = 0;
				// invocar los servcios de Sifen para el envio del lote de facturas
				if (successCounter > 0) {
					System.out.println("Enviando lote...");
					currentTask = "Invocando RespuestaRecepcionLoteDE";
					task = "Invocando RespuestaRecepcionLoteDE";
					RespuestaRecepcionLoteDE ret = Sifen.recepcionLoteDE(deList);
					logger.info(ret.toString());
					logger.info("CODIGO DE ESTADO : "+ret.getCodigoEstado());
					logger.info("RESPUESTA BRUTA  : "+ret.getRespuestaBruta());
					logger.info("COD RES :"+ret.getdCodRes());
					logger.info("MSG RESP :"+ret.getdMsgRes());
					//System.out.println(ret.toString());
					//System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
					//System.out.println(ret.getRespuestaBruta().toString());
					//System.out.println("XML : "+ret.getRespuestaBruta());

					/**
					 * Si tuvo exito el envio del lote, en este punto crear el lote y asignarlo
					 * a las transacciones procesadas.
					 */
					task = "Decodificacion Respuesta";
					JacksonXmlModule module = new JacksonXmlModule();
					module.setDefaultUseWrapper(false);
					// XmlMapper xmlMapper = new XmlMapper(module);
					XmlMapper xmlMapper = new XmlMapper(module);

					try {
						currentTask = "Decodificando respuesta";
						Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(),Envelope.class);
						if (tmp != null) {
						    respCode = tmp.getBody().getrResEnviLoteDe().dCodRes;
						    batchNo = tmp.getBody().getrResEnviLoteDe().getdProtConsLote();
						    batchDate = tmp.getBody().getrResEnviLoteDe().getdFecProc();
						    resultCode = tmp.getBody().getrResEnviLoteDe().dCodRes;
						    resultMsg = tmp.getBody().getrResEnviLoteDe().getdMsgRes();
						    processTime = tmp.getBody().getrResEnviLoteDe().getdTpoProces();
						    System.out.println("=============================== CONSULTA DE LOTES =====================================");
						    System.out.println("Codigo Resultado.: " + tmp.getBody().getrResEnviLoteDe().dCodRes);
						    System.out.println("Mensaje Resultado: " + tmp.getBody().getrResEnviLoteDe().getdMsgRes());
						    System.out.println("Numero de Lote...: " + tmp.getBody().getrResEnviLoteDe().getdProtConsLote());
						    System.out.println("Tiempo Proc......: " + tmp.getBody().getrResEnviLoteDe().getdTpoProces());
						    System.out.println("Fec/Hora Recep...: " + tmp.getBody().getrResEnviLoteDe().getdFecProc());
						    System.out.println("=============================== CONSULTA DE LOTES =====================================");
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

					if (respCode == 300) {
						// crear una entrada en la tabla de lotes enviados
						currentTask = "Creando lote de transacciones";
						PosTrxEbBatch batch = new PosTrxEbBatch();
						long batchId = UtilitiesDAO.getNextval("SQ_POS_TRX_EB_BATCHES", conn);
						batch.setIdentifier(batchId);
						batch.setBatchNumber(batchNo);
						batch.setCreatedBy(userName);
						batch.setCreatedOn(new java.util.Date());
						batch.setOrgId(orgId);
						batch.setTransmissDate(batchDate);
						batch.setUnitId(unitId);
						batch.setResultCode(resultCode);
						batch.setResultMessage(resultMsg);
						batch.setProcessTime(processTime);
						batch.setTrxType(trxType);
						batch.setTrxDate(trxDate);
						batch.setItemsQty(successCounter);
						batch.setRefNumber(String.valueOf(groupNo));
						// crear el log detallado de transacciones que fueron enviadas
						currentTask = "Creando historial de enviados";
						task = "Historial Enviados";
						ApplicationMessage m1 = this.createSentLogs(batch, sentList, userName, conn);
						// registrar en lote enviado en la lista de lotes recientes para consulta
						if (queryCount == 0) {
							batchesList = new ArrayList<PosTrxEbBatch>();
						}
						batchesList.add(batch);
						queryCount++;
						if (queryCount == queryQty) {
						    // realizar la consulta de los lotes enviados cada "queryQty" lotes
						    GenericIntList gl = queryRecentList ( batchesList, conn );
						    batchesCount = batchesCount + gl.getElement1();
						    approvedCount = approvedCount + gl.getElement2();
						    rejectedCount = rejectedCount + gl.getElement3();
						    queryCount = 0;
						}
						/**
						 +----------------------------------------------------------------------------------------+
						 | Esta consulta inmediata del estado del lote enviado no funciona porque sifen no        |
						 | procesa el lote lo suficientemente rapido como para que ni bien finalice el envio ya   |
						 | se pueda obtener el detalle de sus transacciones componentes.                          |
						 | Asi que lo unico que queda es procesar completamente un grupo de lotes y luego entrar  |
						 | en un ciclo para consultar el estado de cada lote de dicho grupo.                      |
						 +----------------------------------------------------------------------------------------+
						// ejecutar la consulta del lote para determinar los resultados de la
						// recepcion de cada transaccion del mismo
						int queriedItems = this.queryBatch ( batchId, batch.getBatchNumber(), batch.getItemsQty(), conn );
                         */
						// crear el log detallado de transacciones que no pudieron ser enviadas
						if (notSentList.isEmpty() == false) {
							currentTask = "Creando historial de no enviados";
							task = "Historial No Enviados";
							ApplicationMessage m2 = this.createNotSentLogs(batchId, notSentList, userName, conn);
						}
						if (m1 != null) {
							return m1;
						}
					}
				} 
				groupNo++;
			}
			// consultar los lotes de la ultima lista de lotes recientemente enviados
			currentTask = "Consultando resultados de lotes";
		    GenericIntList gl = queryRecentList ( batchesList, conn );
		    batchesCount = batchesCount + gl.getElement1();
		    approvedCount = approvedCount + gl.getElement2();
		    rejectedCount = rejectedCount + gl.getElement3();

			am = new ApplicationMessage();
			String txt = "Lotes enviados: " + batchesCount + " Documentos aprobados: " + approvedCount +
					     " Documentos rechazados: " + rejectedCount;
			am.setMessage("SEND-BATCH", txt, ApplicationMessage.MESSAGE);
			return am;
		} catch (Exception e) {
			e.printStackTrace();
			am.setMessage("SEND-BATCH", task + ": " + e.getMessage(), ApplicationMessage.ERROR);
			return am;
		} finally {
			Util.closeJDBCConnection(conn);
		}
	}
	
    /**
     * Este metodo tiene como objetivo consultar los lotes que han sido enviados en forma 
     * reciente dentro de un proceso de envio de todas las transacciones de una fecha
     * @param lst - ArrayList<PosTrxEbBatch> - Lista de lotes a consultar
     * @param batchNumber
     * @param itemsQty
     * @param conn
     * @return GenericIntList - Elemento 1: cantidad de lotes consultados
     *                          Elemento 2: cantidad de documentos aprobados
     *                          Elemento 3: cantidad de documentos rechazados
     */
	public GenericIntList queryRecentList( ArrayList<PosTrxEbBatch> lst, Connection conn ) throws SifenException {
		long batchId = 0;
		int itemsQty = 0;
		int itemsCounter = 0;
		int respCode = 0;
		int batchesCount = 0;
		int approvedCount = 0;
		int rejectedCount = 0;

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
		    	    currentTask = "Enviando consulta de lote No. " + x.getBatchNumber();
		        System.out.println("Enviando consulta lote...");
		        RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(x.getBatchNumber());
		        System.out.println(ret.toString());
		        System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
		        System.out.println(ret.getRespuestaBruta().toString());
		        JacksonXmlModule module = new JacksonXmlModule();
		        module.setDefaultUseWrapper(false);
		        XmlMapper xmlMapper = new XmlMapper(module);
		        try {
		          	currentTask = "Decodificando respuesta de lote No. " + x.getBatchNumber();
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
		GenericIntList res = new GenericIntList();
		res.setElement1(batchesCount);
		res.setElement2(approvedCount);
		res.setElement3(rejectedCount);
	    return res;
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
