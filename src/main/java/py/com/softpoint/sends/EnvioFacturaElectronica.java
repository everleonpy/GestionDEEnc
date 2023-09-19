package py.com.softpoint.sends;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionLoteDE;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.de.TdDatGralOpe;
import com.roshka.sifen.core.fields.request.de.TgActEco;
import com.roshka.sifen.core.fields.request.de.TgCamCond;
import com.roshka.sifen.core.fields.request.de.TgCamFE;
import com.roshka.sifen.core.fields.request.de.TgCamIVA;
import com.roshka.sifen.core.fields.request.de.TgCamItem;
import com.roshka.sifen.core.fields.request.de.TgCuotas;
import com.roshka.sifen.core.fields.request.de.TgDatRec;
import com.roshka.sifen.core.fields.request.de.TgDtipDE;
import com.roshka.sifen.core.fields.request.de.TgEmis;
import com.roshka.sifen.core.fields.request.de.TgOpeCom;
import com.roshka.sifen.core.fields.request.de.TgOpeDE;
import com.roshka.sifen.core.fields.request.de.TgPaConEIni;
import com.roshka.sifen.core.fields.request.de.TgPagCheq;
import com.roshka.sifen.core.fields.request.de.TgPagCred;
import com.roshka.sifen.core.fields.request.de.TgPagTarCD;
import com.roshka.sifen.core.fields.request.de.TgTimb;
import com.roshka.sifen.core.fields.request.de.TgTotSub;
import com.roshka.sifen.core.fields.request.de.TgValorItem;
import com.roshka.sifen.core.fields.request.de.TgValorRestaItem;
import com.roshka.sifen.core.types.CMondT;
import com.roshka.sifen.core.types.PaisType;
import com.roshka.sifen.core.types.TDepartamento;
import com.roshka.sifen.core.types.TTImp;
import com.roshka.sifen.core.types.TTiDE;
import com.roshka.sifen.core.types.TTipEmi;
import com.roshka.sifen.core.types.TTipReg;
import com.roshka.sifen.core.types.TTipTra;
import com.roshka.sifen.core.types.TcRelMerc;
import com.roshka.sifen.core.types.TcUniMed;
import com.roshka.sifen.core.types.TdCondTiCam;
import com.roshka.sifen.core.types.TiAfecIVA;
import com.roshka.sifen.core.types.TiCondCred;
import com.roshka.sifen.core.types.TiCondOpe;
import com.roshka.sifen.core.types.TiDenTarj;
import com.roshka.sifen.core.types.TiForProPa;
import com.roshka.sifen.core.types.TiIndPres;
import com.roshka.sifen.core.types.TiNatRec;
import com.roshka.sifen.core.types.TiTiOpe;
import com.roshka.sifen.core.types.TiTiPago;
import com.roshka.sifen.core.types.TiTipCont;
import com.roshka.sifen.core.types.TiTipDocRec;
import dao.FacturaElectronicaDAO;
import dao.RcvTrxEbBatchItemsDAO;
import dao.RcvTrxEbBatchesDAO;
import dao.Util;
import nider.TmpFactuDE_A;
import nider.TmpFactuDE_C;
import nider.TmpFactuDE_D1;
import nider.TmpFactuDE_D2;
import nider.TmpFactuDE_D21;
import nider.TmpFactuDE_D3;
import nider.TmpFactuDE_E;
import nider.TmpFactuDE_E7;
import nider.TmpFactuDE_E71;
import nider.TmpFactuDE_E72;
import nider.TmpFactuDE_E721;
import nider.TmpFactuDE_E8;
import nider.TmpFactuDE_E811;
import pojo.RcvInvoice;
import pojo.RcvTrxEbBatch;
import pojo.RcvTrxEbBatchItem;
import py.com.softpoint.context.ContextDataApp;
import util.MsgApp;

/**
* Clase Encargada del envio de Facturas Electronicas al SIFEN
* @author eleon
*
*/
public class EnvioFacturaElectronica 
{
	private SifenConfig sifenConfig = null;
	private Connection conn = null;
	private MsgApp msgApp = null;
	
	private int rowsQty = 50;
	private int groupCounter = 0;
	private String fileName;
	private boolean fileCreated;
	private int allSent = 0;
	private int allFailed = 0;
	
	
	/**
	* El constructor recibe como parametro el archivo properties que define configuraciones basicas de entorno
	* @param pFileProp
	* @throws SifenException
	*/
	public EnvioFacturaElectronica(String pFileProp) throws SifenException 
	{
		if( pFileProp != null ) 
		{
			sifenConfig = SifenConfig.cargarConfiguracion(pFileProp);
			Sifen.setSifenConfig(sifenConfig);
			
		}
	}
	
	
	/**
	* 
	* @param trxDate
	* @param trxType
	* @return
	*/
	public MsgApp enviarLotes(Date trxDate, String trxType) 
	{
	
		// obtener una conexion a la base de datos
		conn = Util.getConnection();
		if( conn == null ) 
		{
			 msgApp = new MsgApp();
			 msgApp.ErrorMessage("No hay conexion a la BASE DE DATOS");
			 return msgApp;
		}
		
		/* Lista de DE a enviar */
		ArrayList<DocumentoElectronico> deList = null;
		/* Lista de DE enviados */
		ArrayList<RcvInvoice> sentList = null;
		
		/**
		 +---------------------------------------------------------------------+
		 | procesar los lotes cuyo numero esta comprendido entre groupFrom y   |
		 | groupTo                                                             |
		 +---------------------------------------------------------------------+		 
		*/
		
		try {
		    // Arreglo para guardar la lista de documentos auxiliares provenientes del
		    // sistema emisor
		    // Se recuperaran todas las transacciones del tipo y la fecha especificadas
		    // y el ciclo while se encargara de agrupar las transacciones de a "rowsQty"
		    ArrayList<TmpFactuDE_A> daList = FacturaElectronicaDAO.getDEList(trxDate);
		    if (daList != null) 
		    {
		    	//dataFound = true;
		        Iterator itr1 = daList.iterator();
		        while (itr1.hasNext()) {
		        	
		    	        if (groupCounter == 0) 
		    	        {
						    // arreglo para guardar la lista de documentos electronicos
						    deList = new ArrayList<DocumentoElectronico>();
					        // arreglo para guardar la lista de transacciones que pudieron ser enviadas
					        sentList = new ArrayList<RcvInvoice>();
		    	        }
		    	        
		    	        TmpFactuDE_A x = (TmpFactuDE_A) itr1.next();
		    	        
				    DocumentoElectronico DE = this.mapAuxDocToEd(x, x.getIdMov(), conn);
			        if (DE != null) {
				        if (DE.getId() != null) {
					        System.out.println("Registrando documento electronico: " + x.getIdMov() + " - " + DE.getId());
					        deList.add(DE);
					        
					        //fileName = "/Users/jota_ce/Documents/jl-sifen/xml/facturas/" + 
						    //           String.valueOf(x.getIdMov()) + ".xml";
					        
					        fileName = "c:/xml/rcv/" + 
					        		String.valueOf(x.getIdMov()) + ".xml";
					        
					        try 
					        {
						        fileCreated = DE.generarXml(fileName);
					        } catch ( Exception e) {
						        e.printStackTrace();
					        }
					        
					        // agregar a la lista de transacciones cuyo DE fue generado y podran ser enviadas
					        RcvInvoice sentTx = new RcvInvoice();
					        sentTx.setInvoiceId(x.getIdMov());
					        sentTx.setControlCode(DE.getId());
					        sentTx.setQrCode(DE.getEnlaceQR());
							sentTx.setOrgId(ContextDataApp.getDataContext().getOrgId());
							sentTx.setUnitId(ContextDataApp.getDataContext().getUnitId());
					        sentList.add(sentTx);
					        allSent++;
				        } else {
				    	        allFailed++;
					        System.out.println("No se pudo generar el CDC para: " + x.getIdMov() );							
				        }
		    	            groupCounter++;
		    	            
		    	            if (groupCounter == rowsQty) 
		    	            {
		    	        	        // ejecutar la llamada al servicio de envio de datos por lote
		    	            	    //sendBatch ( deList, sentList, trxType, trxDate, orgId, unitId, userName, conn );
		    	            	
			    	            	sendBatch ( deList, sentList, trxType, trxDate, ContextDataApp.getDataContext().getOrgId(), 
			    	            			ContextDataApp.getDataContext().getUnitId(),ContextDataApp.getDataContext().getUsuario()
			    	            			, conn );
		    	            	
		    	        	        groupCounter = 0;		    	        	
		    	            }
			        }
		        }
   	            // ejecutar la llamada al servicio de envio de datos para el ultimo lotetrxType
   	            //sendBatch ( deList, sentList, trxType, trxDate, orgId, unitId, userName, conn );
		        sendBatch ( deList, sentList, trxType, trxDate, ContextDataApp.getDataContext().getOrgId(), 
            			ContextDataApp.getDataContext().getUnitId(),ContextDataApp.getDataContext().getUsuario()
            			, conn );
		        
		    }
		    
		    // TODO aca se envia un mensaje a la gui....
			/*am = new ApplicationMessage();
			if (dataFound == true) {
			    String res = "Actividad realizada con exito. Enviados con exito: " + allSent + " No enviados: " + allFailed; 
			    am.setMessage("SEND-BATCH", res, ApplicationMessage.MESSAGE);
			} else {
			    am.setMessage("SEND-BATCH", "No se encontraron transacciones para enviar", ApplicationMessage.ERROR);				
			}
			return am;  */
		    msgApp.InfoMessage("Actividad realizada con exito. Enviados con exito: " + allSent + " No enviados: " + allFailed );
		    return null;
		    
		} catch (Exception e) {
			e.printStackTrace();
			/*am = new ApmsgplicationMessage();
			am.setMessage("SEND-BATCH", e.getMessage(), ApplicationMessage.ERROR);
			return am;*/
			msgApp.ErrorMessage(e);
		} finally {
			Util.closeJDBCConnection(conn);
		}
		
		return null;
	}


	
	/**
	* 
	* @param deList
	* @param sentList
	* @param trxType
	* @param trxDate
	* @param orgId
	* @param unitId
	* @param userName
	* @param conn
	*/
	private void sendBatch(ArrayList<DocumentoElectronico> deList, ArrayList<RcvInvoice> sentList, String trxType,
		java.util.Date trxDate, long orgId, long unitId, String userName, Connection conn) 
	{
		boolean sendOk = false;
		RespuestaRecepcionLoteDE ret = null;
		int respCode = 0;
		String batchNo = null;
		java.util.Date batchDate = new java.util.Date();
		int resultCode = 0;
		String resultMsg = null;
		int processTime = 0;
		int itemsQty = deList.size();

		try {
			sendOk = true;
			System.out.println("Enviando lote...");
			ret = Sifen.recepcionLoteDE(deList);
			System.out.println(ret.toString());
			
			System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
			System.out.println("COD RESP........: " + ret.getdCodRes());
			System.out.println("MSG RESP........: " + ret.getdMsgRes());
			System.out.println("XML.............: ");
			System.out.println(ret.getRespuestaBruta());
			
		} catch (Exception e1) {
			sendOk = false;
			e1.printStackTrace();
		}
		/**
		 * Si tuvo exito el envio del lote, en este punto crear el lote y asignarlo a
		 * las transacciones procesadas.
		 */
		if (sendOk == true) {
			respCode = -1;
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			XmlMapper xmlMapper = new XmlMapper(module);
			
			try {
				Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
				respCode = tmp.getBody().getrResEnviLoteDe().dCodRes;
				batchNo = tmp.getBody().getrResEnviLoteDe().getdProtConsLote();
				batchDate = tmp.getBody().getrResEnviLoteDe().getdFecProc();
				resultCode = tmp.getBody().getrResEnviLoteDe().dCodRes;
				resultMsg = tmp.getBody().getrResEnviLoteDe().getdMsgRes();
				processTime = tmp.getBody().getrResEnviLoteDe().getdTpoProces();
				System.out.println(
						"=============================== CONSULTA DE LOTES =====================================");
				System.out.println("Codigo Resultado.: " + tmp.getBody().getrResEnviLoteDe().dCodRes);
				System.out.println("Mensaje Resultado: " + tmp.getBody().getrResEnviLoteDe().getdMsgRes());
				System.out.println("Numero de Lote...: " + tmp.getBody().getrResEnviLoteDe().getdProtConsLote());
				System.out.println("Tiempo Proc......: " + tmp.getBody().getrResEnviLoteDe().getdTpoProces());
				System.out.println("Fec/Hora Recep...: " + tmp.getBody().getrResEnviLoteDe().getdFecProc());
				System.out.println(
						"=============================== CONSULTA DE LOTES =====================================");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			if (respCode == 300) {
				try {
					long batchId = 0;
					// crear una entrada en la tabla de lotes enviados
					RcvTrxEbBatch batch = new RcvTrxEbBatch();
					// long batchId = UtilitiesDAO.getNextval("SQ_RCV_TRX_EB_BATCHES");
					// batch.setIdentifier(batchId);
					batch.setBatchNumber(batchNo);
					batch.setTrxType(trxType);
					batch.setTrxDate(trxDate);
					batch.setCreatedBy(userName);
					batch.setCreatedOn(new java.util.Date());
					batch.setOrgId(orgId);
					batch.setTransmissDate(batchDate);
					batch.setUnitId(unitId);
					batch.setResultCode(resultCode);
					batch.setResultMessage(resultMsg);
					batch.setProcessTime(processTime);
					batch.setItemsQty(itemsQty);
					int rows = RcvTrxEbBatchesDAO.addRow(batch, conn);
					batchId = RcvTrxEbBatchesDAO.getMaxId(conn);
					// crear la lista de facturas que fueron incluidas en el lote
					long itemId = 0;
					Iterator itr2 = sentList.iterator();
					while (itr2.hasNext()) {
						RcvInvoice x = (RcvInvoice) itr2.next();
						RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
						// itemId = UtilitiesDAO.getNextval("SQ_RCV_TRX_EB_BATCH_ITEMS",
						// conn.getConnection());
						// o.setIdentifier(itemId);
						o.setBatchId(batchId);
						o.setControlCode(x.getControlCode());
						o.setCreatedBy(userName);
						o.setCreatedOn(new java.util.Date());
						o.setOrgId(x.getOrgId());
						o.setTransactionId(x.getInvoiceId());
						o.setUnitId(x.getUnitId());
						o.setXmlFile(String.valueOf(x.getInvoiceId()) + ".xml");
						o.setQrCode(x.getQrCode());
						RcvTrxEbBatchItemsDAO.addRow(o, conn);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	* 
	* @param da
	* @param invoiceId
	* @param conn
	* @return
	*/
	private DocumentoElectronico mapAuxDocToEd ( TmpFactuDE_A da, long invoiceId, Connection conn ) {
		//System.out.println("Cargando documento auxiliar: " + da.getDE().getId());
		DocumentoElectronico DE = new DocumentoElectronico();
		LocalDateTime currentDate = LocalDateTime.now();
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String s;
		BigDecimal bd;
		final short CONTADO = 1;
		final short CREDITO = 2;
		boolean dataFound = false;
		String codePlace = "Inicio";

		try {
		// Grupo A
		if (da.getId() != null) {
			codePlace = "Asignar CDC";
			// Este codigo sera ejecutado solamente si el valor del CDC fue asignado en forma previa a
			// la preparacion del documento electronico para el envio.
			// Por ejemplo cuando ya se generan el CDC y el codigo QR en el acto de confirmar la transaccion
			// para poder emitir el KuDE en dicho acto.
			DE.setId(da.getId());
			DE.setdDVId(String.valueOf(da.getdDVId()));
			//System.out.println("CDC pre-generado: " + DE.getId() + " - " + DE.getdDVId());
		}
		codePlace = "Fecha Firma";
		DE.setdFecFirma(currentDate);
		DE.setdSisFact(da.getdSisFact());
		dataFound = true;

		// Grupo B
		codePlace = "Asignar gOpeDE";
		TgOpeDE gOpeDE;
		if (da.getId() != null) {
		    gOpeDE = new TgOpeDE(da.getId());
		} else {
		    gOpeDE = new TgOpeDE(null);
		}
		gOpeDE.setiTipEmi(TTipEmi.getByVal(da.getgOpeDe().getiTipEmi()));
		// esta informacion la completa el paquete de Roshka
		//gOpeDE.setdInfoEmi(da.getDE().getgOpeDE().getdInfoEmi());
		//gOpeDE.setdInfoFisc(da.getDE().getgOpeDE().getdInfoFisc());
		gOpeDE.setdInfoFisc(da.getgOpeDe().getdInfoFisc());
		DE.setgOpeDE(gOpeDE);

		// Grupo C
		/**
         +-----------------------------------------------------------------------------+
         | gTimb - Datos del timbrado                                                  |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gTimb";
		TgTimb gTimb = new TgTimb();
		TmpFactuDE_C t = da.getgTimb();
		gTimb.setiTiDE(TTiDE.getByVal(t.getiTiDE()));
		gTimb.setdNumTim(t.getdNumTim());
		gTimb.setdEst(t.getdEst());
		gTimb.setdPunExp(t.getdPunExp());
		gTimb.setdNumDoc(String.valueOf(t.getdNumDoc()));
		if (t.getdSerieNum() != null) {
			if (t.getdSerieNum().trim().isEmpty() == false) {
			    gTimb.setdSerieNum(t.getdSerieNum());
			}
		}
		s = sdf2.format(t.getdFeIniT());
		gTimb.setdFeIniT(LocalDate.parse(s));
		DE.setgTimb(gTimb);

		// Grupo D
		codePlace = "Asignar gDatGralOpe";
		TdDatGralOpe dDatGralOpe = new TdDatGralOpe();
		LocalDateTime ldt = LocalDateTime.ofInstant(da.getgDatGralOpe().getdFeEmiDE().toInstant(),
				ZoneId.systemDefault());
		dDatGralOpe.setdFeEmiDE(ldt);
		/**
         +-----------------------------------------------------------------------------+
         | gOpeCom - Datos de la operacion comercial                                   |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gOpeCom";
		TgOpeCom gOpeCom = new TgOpeCom();
		TmpFactuDE_D1 oc = da.getgDatGralOpe().getgOpeCom();
		gOpeCom.setiTipTra(TTipTra.getByVal(oc.getiTipTra()));
		gOpeCom.setiTImp(TTImp.getByVal(oc.getiTImp()));
		gOpeCom.setcMoneOpe(CMondT.getByName(oc.getcMoneOpe()));
		if (oc.getcMoneOpe().equalsIgnoreCase("PYG") == false) {
			gOpeCom.setdCondTiCam(TdCondTiCam.getByVal(oc.getdCondTiCam()));
			gOpeCom.setdTiCam(oc.getdTiCam());
		}
		dDatGralOpe.setgOpeCom(gOpeCom);

		/**
         +-----------------------------------------------------------------------------+
         | gEmis - Emisor del documento electronico                                    |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gEmis";
		TgEmis gEmis = new TgEmis();
		TmpFactuDE_D2 em = da.getgDatGralOpe().getgEmis();
		gEmis.setdRucEm(em.getdRucEm());
		gEmis.setdDVEmi(String.valueOf(em.getdDVEmi()));
		// por alguna razon, no toma el valor que viene de la organizacion, asi que por
		// premura de tiempo, le asignamos directamente el codigo de persona juridica
		//gEmis.setiTipCont(TiTipCont.getByVal(em.getiTipCont()));
		gEmis.setiTipCont(TiTipCont.PERSONA_JURIDICA);

		gEmis.setcTipReg(TTipReg.getByVal(em.getcTipReg()));
		gEmis.setdNomEmi(em.getdNomEmi());
		if (em.getdNomFanEmi() != null) {
			gEmis.setdNomFanEmi(em.getdNomFanEmi());
		}
		gEmis.setdDirEmi(em.getdDirEmi());
		if (em.getdNumCas() != null) {
			gEmis.setdNumCas(em.getdNumCas());
		}
		if (em.getdCompDir1() != null) {
			gEmis.setdCompDir1(em.getdCompDir1());
		}
		if (em.getdCompDir2() != null) {
			gEmis.setdCompDir2(em.getdCompDir2());
		}
		gEmis.setcDepEmi(TDepartamento.getByVal(em.getcDepEmi()));
		if (em.getcDisEmi() != 0) {
			gEmis.setcDisEmi(em.getcDisEmi());
			gEmis.setdDesDisEmi(em.getdDesDisEmi());
		}
		gEmis.setcCiuEmi(em.getcCiuEmi());
		gEmis.setdDesCiuEmi(em.getdDesCiuEmi());
		gEmis.setdTelEmi(em.getdTelEmi());
		gEmis.setdEmailE(em.getdEmailE().trim());
		if (em.getdDenSuc() != null) {
			gEmis.setdDenSuc(em.getdDenSuc());
		}

		List<TgActEco> gActEcoList = new ArrayList<>();
		ArrayList<TmpFactuDE_D21> la = em.getEconActivList();
		codePlace = "Asignar gActEco";
		Iterator itr1 = la.iterator();
		while (itr1.hasNext()) {
			TmpFactuDE_D21 x = (TmpFactuDE_D21) itr1.next();
			TgActEco gActEco = new TgActEco();
			gActEco.setcActEco(String.valueOf(x.getcActEco()));
			gActEco.setdDesActEco(x.getdDesActEco());
			gActEcoList.add(gActEco);
			//System.out.println(gActEco.getcActEco() + ": " + gActEco.getdDesActEco());
		}    	        
		gEmis.setgActEcoList(gActEcoList);
		dDatGralOpe.setgEmis(gEmis);

		/**
         +-----------------------------------------------------------------------------+
         | gDatRec - Receptor del documento electronico                                |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gDatRec";
		TgDatRec gDatRec = new TgDatRec();
		TmpFactuDE_D3 rd = da.getgDatGralOpe().getgDatRec();
		gDatRec.setiNatRec(TiNatRec.getByVal(rd.getiNatRec()));
		gDatRec.setiTiOpe(TiTiOpe.getByVal(rd.getiTiOpe()));
		gDatRec.setcPaisRec(PaisType.getByName(rd.getcPaisRec()));
		if (rd.getiTiContRec() != 0) {
			gDatRec.setiTiContRec(TiTipCont.getByVal(rd.getiTiContRec()));
		}
		if (rd.getdRucRec() != null) {
			gDatRec.setdRucRec(rd.getdRucRec());
			gDatRec.setdDVRec(rd.getdDVRec());
		}
		if (rd.getiTipIDRec() != 0) {
			gDatRec.setiTipIDRec(TiTipDocRec.getByVal(rd.getiTipIDRec()));
		}
		if (rd.getdNumIDRec() != null) {
			gDatRec.setdNumIDRec(rd.getdNumIDRec().trim());
		}
		gDatRec.setdNomRec(rd.getdNomRec());
		if (rd.getdNomFanRec() != null) {
			gDatRec.setdNomFanRec(rd.getdNomFanRec().trim());
		}
		if (rd.getdDirRec() != null) {
			if (rd.getdDirRec().trim().isEmpty() == false) {
			    gDatRec.setdDirRec(rd.getdDirRec().trim());
			}
		}
		if (rd.getdNumCasRec() != 0) {
			gDatRec.setdNumCasRec(rd.getdNumCasRec());
		}
		if (rd.getcDepRec() != 0) {
			gDatRec.setcDepRec(TDepartamento.getByVal(rd.getcDepRec()));
		}
		if (rd.getcDisRec() != 0) {
			System.out.println("cDisRec: " + rd.getcDisRec() + " - " + rd.getcDisRec());
			gDatRec.setcDisRec(rd.getcDisRec());
			gDatRec.setdDesDisRec(rd.getdDesDisRec().trim());
		}
		if (rd.getcCiuRec() != 0) {
			gDatRec.setcCiuRec(rd.getcCiuRec());
			gDatRec.setdDesCiuRec(rd.getdDesCiuRec());
		}
		if (rd.getdTelRec() != null) {
			if (rd.getdTelRec().isEmpty() == false) {
			    gDatRec.setdTelRec(rd.getdTelRec());
			}
		}
		if (rd.getdCelRec() != null) {
			if (rd.getdCelRec().isEmpty() == false) {
			    gDatRec.setdCelRec(rd.getdCelRec());
			}
		}
		if (rd.getdEmailRec() != null) {
			if (rd.getdEmailRec().isEmpty() == false) {
			    gDatRec.setdEmailRec(rd.getdEmailRec().trim());
			}
		}
		if (rd.getdCodCliente() != null) {
			if (rd.getdCodCliente().isEmpty() == false) {
			    gDatRec.setdCodCliente(rd.getdCodCliente());
			}
		}

		dDatGralOpe.setgDatRec(gDatRec);
		DE.setgDatGralOpe(dDatGralOpe);

		// Grupo E
		TgDtipDE gDtipDE = new TgDtipDE();

		/**
         +-----------------------------------------------------------------------------+
         | gCamFE - Documento tipo factura electronica                                 |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gCamFE";
		TgCamFE gCamFE = new TgCamFE();
		TmpFactuDE_E fe = da.getgTipDE().getgCamFE();
		gCamFE.setiIndPres(TiIndPres.getByVal(fe.getiIndPres()));
		
		if (fe.getdFecEmNR() != null) {
			//s = sdf1.format(fe.getdFecEmNR());
			gCamFE.setdFecEmNR(LocalDate.parse(fe.getdFecEmNR()));
		}
		gDtipDE.setgCamFE(gCamFE);

		codePlace = "Asignar gCamCond";
		TgCamCond gCamCond = new TgCamCond();
		TmpFactuDE_E7 co = da.getgTipDE().getgCamCond();
		gCamCond.setiCondOpe(TiCondOpe.getByVal(co.getiCondOpe()));

		// Operciones contado
		// System.out.println("condicion operacion: " + co.getiCondOpe());
		if (co.getiCondOpe() == CONTADO) {
			if (co.getTrmList() != null) {
				codePlace = "Asignar gPaConEIni";
				List<TgPaConEIni> gPaConEIniList = new ArrayList<>();
				ArrayList<TmpFactuDE_E71> cn = co.getTrmList();
				Iterator itr2 = cn.iterator();
				while (itr2.hasNext()) {
					TmpFactuDE_E71 x = (TmpFactuDE_E71) itr2.next();
					System.out.println("forma pago: " + x.getcMoneTiPag() + " - " + x.getdDesTiPag() + " - " + x.getdMonTiPag());
					TgPaConEIni gPaConEIni = new TgPaConEIni();
					gPaConEIni.setiTiPago(TiTiPago.getByVal(x.getiTiPago()));
					bd = new BigDecimal(x.getdMonTiPag());
					bd = bd.setScale(4, RoundingMode.HALF_UP);
					gPaConEIni.setdMonTiPag(bd);
					gPaConEIni.setcMoneTiPag(CMondT.getByName(x.getcMoneTiPag()));
					
					if (x.getcMoneTiPag().equalsIgnoreCase("PYG") == false) 
					{
						System.out.println(" [  COTIZACION   ] "+x.getdTiCamTiPag());
						gPaConEIni.setdTiCamTiPag(new BigDecimal(x.getdTiCamTiPag()));
						System.out.println(" [ ASIGNADO  ] "+gPaConEIni.getdTiCamTiPag());
					}
					
					// tarjetas de credito o debito
					if (x.getgPagTarCD() != null) {
						codePlace = "Asignar gPagTarDC";
						TgPagTarCD gPagTarCD = new TgPagTarCD();
						gPagTarCD.setiDenTarj(TiDenTarj.getByVal(x.getgPagTarCD().getiDenTarj()));
						if (x.getgPagTarCD().getdRSProTar() != null) {
							gPagTarCD.setdRSProTar(x.getgPagTarCD().getdRSProTar());
						}
						if (x.getgPagTarCD().getdRUCProTar() != null) {
							gPagTarCD.setdRUCProTar(x.getgPagTarCD().getdRUCProTar());
							gPagTarCD.setdDVProTar(x.getgPagTarCD().getdDVProTar());
						}
						gPagTarCD.setiForProPa(TiForProPa.getByVal(x.getgPagTarCD().getiForProPa()));
						if (x.getgPagTarCD().getdCodAuOpe() != 0) {
							gPagTarCD.setdCodAuOpe(x.getgPagTarCD().getdCodAuOpe());
						}
						if (x.getgPagTarCD().getdNomTit() != null) {
							System.out.println("getdNomTit: " + x.getgPagTarCD().getdNomTit());
							s = x.getgPagTarCD().getdNomTit().trim();
							if (s.isEmpty() == false) {
								if (s.length() >= 4) {
							        gPagTarCD.setdNomTit(s);
								}
							}
						}
						if (x.getgPagTarCD().getdNumTarj() != 0) {
							gPagTarCD.setdNumTarj(x.getgPagTarCD().getdNumTarj());
						}
						gPaConEIni.setgPagTarCD(gPagTarCD);
					}
					// cheques
					if (x.getgPagCheq() != null) {
						codePlace = "Asignar gPagCheq";
						TgPagCheq gPagCheq = new TgPagCheq();
						gPagCheq.setdNumCheq(x.getgPagCheq().getdNumCheq());
						gPagCheq.setdBcoEmi(x.getgPagCheq().getdBcoEmi());
						gPaConEIni.setgPagCheq(gPagCheq);
					}
					gPaConEIniList.add(gPaConEIni);
				}
				codePlace = "Asignar lista gPaConEIni";
				gCamCond.setgPaConEIniList(gPaConEIniList);
			}
		}

		// Operaciones credito
		if (co.getiCondOpe() == CREDITO) {
			codePlace = "Asignar gPagCred";
			TmpFactuDE_E72 cr = co.getgPagCred();
			TgPagCred gPagCred = new TgPagCred();
			
			gPagCred.setiCondCred(TiCondCred.getByVal(cr.getiCondCred()));
			if (cr.getdPlazoCre() != null) {
				gPagCred.setdPlazoCre(cr.getdPlazoCre());
			}
			if (cr.getdCuotas() > 0) {
				gPagCred.setdCuotas(cr.getdCuotas());
			}
			if (cr.getdMonEnt().doubleValue() > 0.0) {
				gPagCred.setdMonEnt(cr.getdMonEnt());
			}
			// cuotas de la operacion credito
			if (cr.getInstsList() != null) {
				codePlace = "Asignar gCuotas";
				List<TgCuotas> gTgCuotasList = new ArrayList<>();
				ArrayList<TmpFactuDE_E721> cn = cr.getInstsList();
				Iterator itr3 = cn.iterator();
				
				while (itr3.hasNext()) {
					TmpFactuDE_E721 x = (TmpFactuDE_E721) itr3.next();
					TgCuotas gCuota = new TgCuotas();
					gCuota.setcMoneCuo(CMondT.getByName(x.getcMoneCuo()));
					gCuota.setdMonCuota(new BigDecimal(x.getdMonCuota()).setScale(4, RoundingMode.CEILING));
					
					System.out.println("*********************************************************");
					System.out.println("** MONTO CUOTO      : "+gCuota.getdMonCuota());
					System.out.println("** MONTO REDONDEADO : "+gCuota.getdMonCuota().setScale(4, RoundingMode.CEILING));
					System.out.println("*********************************************************");
					
					if (x.getdVencCuo() != null) {
						//s = sdf2.format(x.getdVencCuo());
						System.out.println("*********************************************************");
						System.out.println("Fecha de Cuota : "+x.getdVencCuo());
						System.out.println("*********************************************************");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						
						//
						//TODO Pasar de String a Date ya que el campo que viene de la Base de datos es String
						//-----
						gCuota.setdVencCuo(LocalDate.parse(x.getdVencCuo(), formatter));
					}
					gTgCuotasList.add(gCuota);
				}
				gPagCred.setgCuotasList(gTgCuotasList);
			}
			gCamCond.setgPagCred(gPagCred);
		}
		codePlace = "Asignar gCamCond";
		gDtipDE.setgCamCond(gCamCond);

		/**
        +-----------------------------------------------------------------------------+
        | gCamItem - Items de la operacion                                            |
        +-----------------------------------------------------------------------------+
		*/
		codePlace = "Asignar lista items";
		List<TgCamItem> gCamItemList = new ArrayList<>();
		ArrayList<TmpFactuDE_E8> im = da.getgTipDE().getItemsList();
		Iterator itr4 = im.iterator();
		while (itr4.hasNext()) {
			TmpFactuDE_E8 x = (TmpFactuDE_E8) itr4.next();
			codePlace = "Asignar item " + x.getdDesProSer();
			TgCamItem gCamItem = new TgCamItem();
			gCamItem.setdCodInt(x.getdCodInt());
			if (x.getdParAranc() != 0) {
				gCamItem.setdParAranc(x.getdParAranc());
			}
			if (x.getdNCM() != 0) {
				gCamItem.setdNCM(x.getdNCM());
			}
			if (x.getdDncpG() != null) {
				//System.out.println("getdDncpG: " + x.getdNCM());
				if (x.getdDncpG().trim().isEmpty() == false) {
				    gCamItem.setdDncpG(x.getdDncpG());
				}
			}
			if (x.getdDncpE() != null) {
				//System.out.println("getdDncpE: " + x.getdDncpE());
				if (x.getdDncpE().trim().isEmpty() == false) {
				    gCamItem.setdDncpE(x.getdDncpE());
				}
			}
			if (x.getdGtin() != 0) {
				gCamItem.setdGtin(x.getdGtin());
			}
			if (x.getdGtinPq() != 0) {
				gCamItem.setdGtinPq(x.getdGtinPq());
			}
			gCamItem.setdDesProSer(x.getdDesProSer());
			gCamItem.setcUniMed(TcUniMed.getByVal(x.getcUniMed()));
			gCamItem.setdCantProSer(x.getdCantProSer());
			if (x.getcPaisOrig() != null) {
				gCamItem.setcPaisOrig(PaisType.getByName(x.getcPaisOrig()));
			}
			if (x.getdInfItem() != null) {
				//System.out.println("getdInfItem: " + x.getdInfItem());
				if (x.getdInfItem().trim().isEmpty() == false) {
				    gCamItem.setdInfItem(x.getdInfItem());
				}
			}
			if (x.getcRelMerc() != 0) {
				gCamItem.setcRelMerc(TcRelMerc.getByVal(x.getcRelMerc()));
			}
			if (x.getdCanQuiMer() != null) {
				//System.out.println("getdCanQuiMer: " + x.getdCanQuiMer());
				if (x.getdCanQuiMer().doubleValue() != 0.0) {
					gCamItem.setdCanQuiMer(x.getdCanQuiMer());
				}
			}
			if (x.getdPorQuiMer() != null) {
				//System.out.println("getdPorQuiMer: " + x.getdPorQuiMer());
				if (x.getdPorQuiMer().doubleValue() != 0.0) {
					gCamItem.setdPorQuiMer(x.getdPorQuiMer());
				}
			}
			if (x.getdCDCAnticipo() != null) {
				//System.out.println("getdCDCAnticipo: " + x.getdCDCAnticipo());
				if (x.getdCDCAnticipo().trim().isEmpty() == false) {
				    gCamItem.setdCDCAnticipo(x.getdCDCAnticipo());
				}
			}

			// valores del item
			TgValorItem gValorItem = new TgValorItem();
			gValorItem.setdPUniProSer(x.getgValorItem().getdPUniProSer());
			codePlace = "Asignar gValorItem";
			if (x.getgValorItem().getdTiCamIt() != null) {
				if (x.getgValorItem().getdTiCamIt().doubleValue() != 0.0) {
					gValorItem.setdTiCamIt(x.getgValorItem().getdTiCamIt()); 
				}
			}
			// el valor del campo "dTotBruOpeItem" es asignado en el momento de la generacion del xml

			// valores de descuentos por item
			if (x.getgValorItem().getgValorRestaItem() != null) {
				TmpFactuDE_E811 d = x.getgValorItem().getgValorRestaItem();
				TgValorRestaItem gValorRestaItem = new TgValorRestaItem();
				codePlace = "Asignar resta item";
				if (d.getdDescItem().doubleValue() != 0.0) {
					gValorRestaItem.setdDescItem(d.getdDescItem());
				}
				//System.out.println("gValorRestaItem.getdDescItem(): " + gValorRestaItem.getdDescItem());
				// el valor del campo "dPorcDesIt" es asignado en el momento de la generacion del xml
				if (d.getdDescGloItem() != null) {
					gValorRestaItem.setdDescGloItem(d.getdDescGloItem());
				}
				if (d.getdAntPreUniIt() != null) {
					gValorRestaItem.setdAntPreUniIt(d.getdAntPreUniIt());
				}
				if (d.getdAntGloPreUniIt() != null) {
					gValorRestaItem.setdAntGloPreUniIt(d.getdAntGloPreUniIt());
				}
				// los valores de los campos "dTotOpeItem" y "dTotOpeGs" son asignados en el momento
				// de la generacion del xml
				gValorItem.setgValorRestaItem(gValorRestaItem);
			}

			gCamItem.setgValorItem(gValorItem);

			// datos del IVA
			if (x.getgCamIVA() != null) {
				TgCamIVA gCamIVA = new TgCamIVA();
				codePlace = "Asignar gCamIVA";
				gCamIVA.setiAfecIVA(TiAfecIVA.getByVal(x.getgCamIVA().getiAfecIVA()));
				gCamIVA.setdPropIVA(x.getgCamIVA().getdPropIVA());
				gCamIVA.setdTasaIVA(new BigDecimal(x.getgCamIVA().getdTasaIVA()));
				// los valores de los campos "dBasGravIVA" y "dLiqIVAItem" son asignados en el momento
				// de la generacion del xml
				gCamItem.setgCamIVA(gCamIVA);
			}

			gCamItemList.add(gCamItem);
		}

		codePlace = "Asignar lista lineas";
		gDtipDE.setgCamItemList(gCamItemList);
		DE.setgDtipDE(gDtipDE);

		// Grupo E
		DE.setgTotSub(new TgTotSub());

		// Grupo J
		// Este codigo sera ejecutado solamente si el valor del codigo QR es asignado en forma previa a
		// la preparacion del documento electronico para el envio.
		// Por ejemplo cuando ya se generan el CDC y el codigo QR en el acto de confirmar la transaccion
		// para poder emitir el KuDE en dicho acto.
		//if (da.getgCamFuFD().getdCarQR() != null) {
		//    DE.setEnlaceQR(da.getgCamFuFD().getdCarQR());
		//}

		// aqui realizaremos algunas validaciones para evitar que lleguen objetos DE que no tengan algunos
		// de sus atributos obligatorios
		if (gCamCond.getiCondOpe().getVal() == CONTADO) {
		    if (gCamCond.getgPaConEIniList() == null) {
			    return null;
		    } else {
			    if (gCamCond.getgPaConEIniList().isEmpty() == true) {
				    return null;
			    }			
		    }
		}
		if (gCamCond.getiCondOpe().getVal() == CREDITO) {
			if (gCamCond.getgPagCred() == null) {
				return null;
			}
		}

		
		// generar el CDC si la transaccion aun no la tiene
		if (da.getId() == null) {
			codePlace = "Obtener CDC";
			try {
				String controlCode = DE.obtenerCDC(null);
				String qrCode = DE.getEnlaceQR();
				// actualizar en la tabla de transacciones los CDC generados en el momento del envio
				// desactivado para la aplicacion de Nider
				//RcvCustomersTrxDAO.updateControlCode(invoiceId, controlCode, qrCode, conn);
			} catch (SifenException e1) {
				System.out.println(invoiceId + ": " + e1.getMessage());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		
		
		//System.out.println("Finalizando carga de documento auxiliar: " + da.getDE().getId());
		return DE;

		} catch ( Exception e) {
			System.out.println("Error en " + codePlace);
			e.printStackTrace();
			return null;
		}
	}

}
