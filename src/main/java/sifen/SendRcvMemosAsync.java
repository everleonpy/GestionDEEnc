package sifen;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.addon.gResProcLote;
import com.roshka.sifen.addon.rResEnviConsLoteDe;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionLoteDE;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.de.TdDatGralOpe;
import com.roshka.sifen.core.fields.request.de.TgActEco;
import com.roshka.sifen.core.fields.request.de.TgCamDEAsoc;
import com.roshka.sifen.core.fields.request.de.TgCamIVA;
import com.roshka.sifen.core.fields.request.de.TgCamItem;
import com.roshka.sifen.core.fields.request.de.TgCamNCDE;
import com.roshka.sifen.core.fields.request.de.TgDatRec;
import com.roshka.sifen.core.fields.request.de.TgDtipDE;
import com.roshka.sifen.core.fields.request.de.TgEmis;
import com.roshka.sifen.core.fields.request.de.TgOpeCom;
import com.roshka.sifen.core.fields.request.de.TgOpeDE;
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
import com.roshka.sifen.core.types.TiMotEmi;
import com.roshka.sifen.core.types.TiNatRec;
import com.roshka.sifen.core.types.TiTIpoDoc;
import com.roshka.sifen.core.types.TiTiOpe;
import com.roshka.sifen.core.types.TiTipCont;
import com.roshka.sifen.core.types.TiTipDocAso;
import com.roshka.sifen.core.types.TiTipDocRec;

import business.ApplicationMessage;
import dao.NotaRcvElectronicaDAO;
import dao.RcvCustomersTrxDAO;
import dao.RcvTrxEbBatchItemsDAO;
import dao.RcvTrxEbBatchesDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.CamposActivEconomica;
import pojo.CamposDEAsociado;
import pojo.CamposDescuentosItem;
import pojo.CamposEmisorDE;
import pojo.CamposItemsOperacion;
import pojo.CamposNotaElectronica;
import pojo.CamposOperacionComercial;
import pojo.CamposReceptorDE;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;
import pojo.RcvInvoice;
import pojo.RcvTrxEbBatch;
import pojo.RcvTrxEbBatchItem;
import util.UtilPOS;

public class SendRcvMemosAsync {

	private final static Logger logger = Logger.getLogger(SendRcvMemosAsync.class.toString());

	public static void setupSifenConfig() throws SifenException {
		SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
		logger.info("Using CONFIG: " + sifenConfig);
		Sifen.setSifenConfig(sifenConfig);
	}

	public ApplicationMessage sendDeBatch ( String trxType, 
			                                java.util.Date trxDate,
			                                int groupFrom, 
			                                int groupTo,
			                                long orgId, 
			                                long unitId, 
			                                String userName ) throws SifenException {
		ApplicationMessage am = null;
		Connection conn = null;
		int counter = 0;

		int rowsQty = 50;

		String batchNo = null;
		int respCode = 0;
		String fileName;
		boolean fileCreated;
		//
		java.util.Date batchDate = new java.util.Date();
		int resultCode = 0;
		String resultMsg = null;
		int processTime = 0;

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

		// obtener una conexion a la base de datos
		conn = Util.getConnection();
		if (conn == null) {
			am = new ApplicationMessage();
			am.setMessage("SEND-INV", "No se ha podido obtener conexion a la base de datos", ApplicationMessage.ERROR);
			return am;
		}

		try {
			int groupNo = groupFrom;
			while (groupNo <= groupTo) {
				counter = 0;
				// arreglo para guardar la lista de transacciones que pudieron ser enviadas
				ArrayList<RcvInvoice> sentList = new ArrayList<RcvInvoice>();
				// arreglo para guardar la lista de documentos electronicos
				ArrayList<DocumentoElectronico> deList = new ArrayList<DocumentoElectronico>();
				// arreglo para guardar la lista de documentos auxiliares
				ArrayList<DocumElectronico> daList = NotaRcvElectronicaDAO.getRcvMemos( trxDate, groupNo, rowsQty );
				if (daList != null) {
					Iterator itr1 = daList.iterator();
					while (itr1.hasNext()) {
						DocumElectronico x = (DocumElectronico) itr1.next();
						DocumentoElectronico DE = this.mapAuxDocToEd(x, x.getTransactionId(), conn);
						if (DE != null) {
							if (DE.getId() != null) {
								System.out.println("Registrando documento electronico: " + x.getTransactionId() + " - " + DE.getId());
								deList.add(DE);
								//System.out.println("1");
								fileName = "/Users/jota_ce/Documents/cacique-sifen/xml/notas-credito/" + 
										String.valueOf(x.getTransactionId()) + ".xml";
								//System.out.println("2");
								try {
									fileCreated = DE.generarXml(fileName);
								} catch ( Exception e) {
									e.printStackTrace();
								}
								//System.out.println("3");
								// agregar a la lista de transacciones cuyo DE fue generado y podran ser enviadas
								//System.out.println("4");
								RcvInvoice sentTx = new RcvInvoice();
								sentTx.setInvoiceId(x.getTransactionId());
								sentTx.setControlCode(DE.getId());
								sentTx.setOrgId(orgId);
								sentTx.setUnitId(unitId);
								sentTx.setQrCode(DE.getEnlaceQR());
								//System.out.println("6");
								sentList.add(sentTx);
								counter++;
							} else {
								System.out.println("No se pudo generar el CDC para: " + x.getTransactionId() );							
							}
						}
					}
				}
				System.out.println("Grupo " + groupNo + " Total de transacciones: " + counter);
				//counter = 0;
				// invocar los servcios de Sifen para el envio de la nota
				if (counter > 0) {
					System.out.println("Enviando lote...");
					RespuestaRecepcionLoteDE ret = Sifen.recepcionLoteDE(deList);
					logger.info(ret.toString());
					logger.info("CODIGO DE ESTADO: " + ret.getCodigoEstado());
					logger.info("COD RESP........: " + ret.getdCodRes());
					logger.info("MSG RESP........: " + ret.getdMsgRes());
					logger.info("RESPUESTA BRUTA.: " + ret.getRespuestaBruta());

					System.out.println(ret.toString());
					System.out.println("CODIGO DE ESTADO: " + ret.getCodigoEstado());
					System.out.println("COD RESP........: " + ret.getdCodRes());
					System.out.println("MSG RESP........: " + ret.getdMsgRes());
					System.out.println("XML.............: ");
					System.out.println(ret.getRespuestaBruta());

					/**
					 * Si tuvo exito el envio del lote, en este punto crear el lote y asignarlo
					 * a las transacciones procesadas.
					 */
					JacksonXmlModule module = new JacksonXmlModule();
					module.setDefaultUseWrapper(false);
					// XmlMapper xmlMapper = new XmlMapper(module);
					XmlMapper xmlMapper = new XmlMapper(module);

					try {
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
							am.setMessage("DECODE-RESP", "El servicio no responde o se perdio la conexion con el mismo", ApplicationMessage.ERROR);
							return am;							
						}

					} catch (JsonProcessingException e) {
						e.printStackTrace();
						am = new ApplicationMessage();
						am.setMessage("DECODE-RESP", "Error al leer respuesta: " + e.getMessage(), ApplicationMessage.ERROR);
						return am;
					}

					if (respCode == 300) {
						try {
							// crear una entrada en la tabla de lotes enviados
							RcvTrxEbBatch batch = new RcvTrxEbBatch();
							long batchId = UtilitiesDAO.getNextval("SQ_RCV_TRX_EB_BATCHES", conn);
							batch.setIdentifier(batchId);
							batch.setBatchNumber(batchNo.toString());
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
							int rows = RcvTrxEbBatchesDAO.addRow(batch, conn);
							// crear la lista de notas que fueron incluidas en el lote
							long itemId = 0;
							Iterator itr2 = sentList.iterator();
							while (itr2.hasNext()) {
								RcvInvoice x = (RcvInvoice) itr2.next();
								RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
								itemId = UtilitiesDAO.getNextval("SQ_RCV_TRX_EB_BATCH_ITEMS", conn);
								o.setIdentifier(itemId);
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
				groupNo++;
			}
			am = new ApplicationMessage();
			am.setMessage("SEND-BATCH", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
			return am;
		} catch (Exception e) {
			e.printStackTrace();
			am = new ApplicationMessage();
			am.setMessage("SEND-BATCH", e.getMessage(), ApplicationMessage.ERROR);
			return am;
		} finally {
			Util.closeJDBCConnection(conn);
		}
	}
	
	private DocumentoElectronico mapAuxDocToEd ( DocumElectronico da, long invoiceId, Connection conn ) {
		//System.out.println("Cargando documento auxiliar: " + da.getDE().getId());
		DocumentoElectronico DE = new DocumentoElectronico();
		LocalDateTime currentDate = LocalDateTime.now();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s;
		BigDecimal bd;
		final short CONTADO = 1;
		final short CREDITO = 2;
		boolean dataFound = false;
		String codePlace = "Inicio";

		try {
		// Grupo A
		if (da.getDE().getId() != null) {
			codePlace = "Asignar CDC";
			// Este codigo sera ejecutado solamente si el valor del CDC fue asignado en forma previa a
			// la preparacion del documento electronico para el envio.
			// Por ejemplo cuando ya se generan el CDC y el codigo QR en el acto de confirmar la transaccion
			// para poder emitir el KuDE en dicho acto.
			DE.setId(da.getDE().getId());
			DE.setdDVId(da.getDE().getdDVId());
			//System.out.println("CDC pre-generado: " + DE.getId() + " - " + DE.getdDVId());
		}
		codePlace = "Fecha Firma";
		DE.setdFecFirma(currentDate);
		DE.setdSisFact(da.getDE().getdSisFact());
		dataFound = true;

		// Grupo B
		codePlace = "Asignar gOpeDE";
		TgOpeDE gOpeDE;
		if (da.getDE().getId() != null) {
		    gOpeDE = new TgOpeDE(da.getDE().getId());
		} else {
		    gOpeDE = new TgOpeDE(null);
		}
		gOpeDE.setiTipEmi(TTipEmi.getByVal(da.getDE().getgOpeDE().getiTipEmi()));
		// esta informacion la completa el paquete de Roshka
		//gOpeDE.setdInfoEmi(da.getDE().getgOpeDE().getdInfoEmi());
		//gOpeDE.setdInfoFisc(da.getDE().getgOpeDE().getdInfoFisc());
		DE.setgOpeDE(gOpeDE);

		// Grupo C
		/**
         +-----------------------------------------------------------------------------+
         | gTimb - Datos del timbrado                                                  |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gTimb";
		TgTimb gTimb = new TgTimb();
		CamposTimbrado t = da.getDE().getgTimb();
		gTimb.setiTiDE(TTiDE.getByVal(t.getiTiDE()));
		gTimb.setdNumTim(t.getdNumTim());
		gTimb.setdEst(t.getdEst());
		gTimb.setdPunExp(t.getdPunExp());
		gTimb.setdNumDoc(t.getdNumDoc());
		if (t.getdSerieNum() != null) {
			gTimb.setdSerieNum(t.getdSerieNum());
		}
		s = sdf.format(t.getdFeIniT());
		gTimb.setdFeIniT(LocalDate.parse(s));
		DE.setgTimb(gTimb);

		// Grupo D
		codePlace = "Asignar gDatGralOpe";
		TdDatGralOpe dDatGralOpe = new TdDatGralOpe();
		LocalDateTime ldt = LocalDateTime.ofInstant(da.getDE().getgDatGralOpe().getdFeEmiDE().toInstant(),
				ZoneId.systemDefault());
		dDatGralOpe.setdFeEmiDE(ldt);
		/**
         +-----------------------------------------------------------------------------+
         | gOpeCom - Datos de la operacion comercial                                   |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gOpeCom";
		TgOpeCom gOpeCom = new TgOpeCom();
		CamposOperacionComercial oc = da.getDE().getgDatGralOpe().getgOpeComer();
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
		CamposEmisorDE em = da.getDE().getgDatGralOpe().getgEmis();
		gEmis.setdRucEm(em.getdRucEm());
		gEmis.setdDVEmi(em.getdDVEmi());
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
		gEmis.setdEmailE(em.getdEmailE());
		if (em.getdDenSuc() != null) {
			gEmis.setdDenSuc(em.getdDenSuc());
		}

		List<TgActEco> gActEcoList = new ArrayList<>();
		ArrayList<CamposActivEconomica> la = em.getgActEco();
		codePlace = "Asignar gActEco";
		Iterator itr1 = la.iterator();
		while (itr1.hasNext()) {
			CamposActivEconomica x = (CamposActivEconomica) itr1.next();
			if (x.getcActEco().equalsIgnoreCase("46301") | x.getcActEco().equalsIgnoreCase("46302") |
					x.getcActEco().equalsIgnoreCase("46304")) {
				TgActEco gActEco = new TgActEco();
				gActEco.setcActEco(x.getcActEco());
				gActEco.setdDesActEco(x.getdDesActEco());
				gActEcoList.add(gActEco);
				//System.out.println(gActEco.getcActEco() + ": " + gActEco.getdDesActEco());
			}
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
		CamposReceptorDE rd = da.getDE().getgDatGralOpe().getgDatRec();
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
			gDatRec.setdNumIDRec(rd.getdNumIDRec());
		}
		gDatRec.setdNomRec(rd.getdNomRec());
		if (rd.getdNomFanRec() != null) {
			gDatRec.setdNomFanRec(rd.getdNomFanRec());
		}
		if (rd.getdDirRec() != null) {
			gDatRec.setdDirRec(rd.getdDirRec());
		}
		if (rd.getdNumCasRec() != 0) {
			gDatRec.setdNumCasRec(rd.getdNumCasRec());
		}
		if (rd.getcDepRec() != 0) {
			gDatRec.setcDepRec(TDepartamento.getByVal(rd.getcDepRec()));
		}
		if (rd.getcDisRec() != 0) {
			gDatRec.setcDisRec(rd.getcDisRec());
			gDatRec.setdDesDisRec(rd.getdDesDisRec());
		}
		if (rd.getcCiuRec() != 0) {
			gDatRec.setcCiuRec(rd.getcCiuRec());
			gDatRec.setdDesCiuRec(rd.getdDesCiuRec());
		}
		if (rd.getdTelRec() != null) {
			gDatRec.setdTelRec(rd.getdTelRec());
		}
		if (rd.getdCelRec() != null) {
			gDatRec.setdCelRec(rd.getdCelRec());
		}
		if (rd.getdEmailRec() != null) {
			gDatRec.setdEmailRec(rd.getdEmailRec());
		}
		if (rd.getdCodCliente() != null) {
			gDatRec.setdCodCliente(rd.getdCodCliente());
		}

		dDatGralOpe.setgDatRec(gDatRec);
		DE.setgDatGralOpe(dDatGralOpe);

		// Grupo E
		TgDtipDE gDtipDE = new TgDtipDE();

		/**
         +-----------------------------------------------------------------------------+
         | gCamNCDE - Documento tipo nota electronica                                 |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar gCamNCDE";
		TgCamNCDE gCamNCDE = new TgCamNCDE();
		CamposNotaElectronica ne = da.getDE().getgDtipDE().getgCamNCDE();
		gCamNCDE.setiMotEmi(TiMotEmi.getByVal(ne.getiMotEmi()));
		gDtipDE.setgCamNCDE(gCamNCDE);

		/**
         +-----------------------------------------------------------------------------+
         | gCamItem - Items de la operacion                                            |
         +-----------------------------------------------------------------------------+
		 */
		codePlace = "Asignar lista items";
		List<TgCamItem> gCamItemList = new ArrayList<>();
		ArrayList<CamposItemsOperacion> im = da.getDE().getgDtipDE().getgCamItem();
		Iterator itr4 = im.iterator();
		while (itr4.hasNext()) {
			CamposItemsOperacion x = (CamposItemsOperacion) itr4.next();
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
				gCamItem.setdDncpG(x.getdDncpG());
			}
			if (x.getdDncpE() != null) {
				gCamItem.setdDncpE(x.getdDncpE());
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
				gCamItem.setdInfItem(x.getdInfItem());
			}
			if (x.getcRelMerc() != 0) {
				gCamItem.setcRelMerc(TcRelMerc.getByVal(x.getcRelMerc()));
			}
			if (x.getdCanQuiMer() != null) {
				if (x.getdCanQuiMer().doubleValue() != 0.0) {
					gCamItem.setdCanQuiMer(x.getdCanQuiMer());
				}
			}
			if (x.getdPorQuiMer() != null) {
				if (x.getdPorQuiMer().doubleValue() != 0.0) {
					gCamItem.setdPorQuiMer(x.getdPorQuiMer());
				}
			}
			if (x.getdCDCAnticipo() != null) {
				gCamItem.setdCDCAnticipo(x.getdCDCAnticipo());
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
			// el valor del campo "dTotBruOpeItem" es asignado en el momeno de la generacion del xml

			// valores de descuentos por item
			if (x.getgValorItem().getgValorRestaItem() != null) {
				CamposDescuentosItem d = x.getgValorItem().getgValorRestaItem();
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
		
		codePlace = "Asignar documentos asociados";
		List<TgCamDEAsoc> gCamDEAsocList = new ArrayList<>();
		ArrayList<CamposDEAsociado> dea = da.getDE().getgCamDEAsoc();
		Iterator itr5 = dea.iterator();
		while (itr5.hasNext()) {
			CamposDEAsociado x = (CamposDEAsociado) itr5.next();
			TgCamDEAsoc gCamDEAsoc = new TgCamDEAsoc();
			System.out.println("x.getiTipDocAso(): " + x.getiTipDocAso());
			gCamDEAsoc.setiTipDocAso(TiTipDocAso.getByVal(x.getiTipDocAso()));
			if (gCamDEAsoc.getiTipDocAso().getVal() == TiTipDocAso.ELECTRONICO.getVal()) {
				codePlace = "Asignar item " + x.getdCdCDERef();
			    gCamDEAsoc.setdCdCDERef(x.getdCdCDERef());
			}
			if (gCamDEAsoc.getiTipDocAso().getVal() == TiTipDocAso.IMPRESO.getVal()) {
				codePlace = "Asignar item " + x.getdEstDocAso() + "-" + x.getdPExpDocAso() + "-" + x.getdNumDocAso();
			    gCamDEAsoc.setdEstDocAso(x.getdEstDocAso());
			    s = sdf.format(x.getdFecEmiDI());
			    gCamDEAsoc.setdFecEmiDI(LocalDate.parse(s));
			    gCamDEAsoc.setdNTimDI(x.getdNTimDI());
				gCamDEAsoc.setdNumDocAso(x.getdNumDocAso());
				gCamDEAsoc.setdPExpDocAso(x.getdPExpDocAso());
				gCamDEAsoc.setiTipoDocAso(TiTIpoDoc.getByVal(x.getiTipoDocAso()));
			}
			gCamDEAsoc.setdNumComRet(null);
			gCamDEAsoc.setdNumCons(null);
			gCamDEAsoc.setdNumControl(null);
			gCamDEAsoc.setdNumResCF(null);
			gCamDEAsoc.setiTipCons(null);
			//
			gCamDEAsocList.add(gCamDEAsoc);
		}
		DE.setgCamDEAsocList(gCamDEAsocList);

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


		
		
		// generar el CDC si la transaccion aun no la tiene
		if (da.getDE().getId() == null) {
			codePlace = "Obtener CDC";
			try {
				String controlCode = DE.obtenerCDC(null);
				String qrCode = DE.getEnlaceQR();
				// actualizar en la tabla de transacciones los CDC generados en el momento del envio
				RcvCustomersTrxDAO.updateControlCode(invoiceId, controlCode, qrCode, conn);
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
	
	public ApplicationMessage queryBatches ( String trxType, 
			                                 java.util.Date trxDate ) throws SifenException {
		ApplicationMessage am = new ApplicationMessage();
		Connection conn =  null;
		long batchId = 0;
		String batchNo = null;
		int itemsQty = 0;
		java.util.Date fromDate;
		java.util.Date toDate;	    

		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			am.setMessage("QRY-MEMO-BATCH", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			am.setMessage("QTY-MEMO-BATCH", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
		}

		try {
			conn = Util.getConnection();
			if (conn == null) {
				am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
				return am;
			}
		} catch ( Exception e ) {
			am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
			return am;
		}

		fromDate = trxDate;
		toDate = UtilPOS.addDaysToDate(fromDate, 1);
		ArrayList<RcvTrxEbBatch> batches = RcvTrxEbBatchesDAO.getNotQueriedList(trxType, fromDate, toDate);

		if (batches != null) {
			Iterator itr1 = batches.iterator();
			while (itr1.hasNext()) {
				RcvTrxEbBatch x = (RcvTrxEbBatch) itr1.next();
				batchId = x.getIdentifier();
				batchNo = x.getBatchNumber();
				itemsQty = x.getItemsQty();
				// invocar los servicios de Sifen para el envio de la consulta del lote
				System.out.println("Enviando consulta lote...");
				RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(batchNo);
				//System.out.println(ret.toString());
				//System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
				//System.out.println(ret.getRespuestaBruta().toString());

				//System.out.println("1");
				JacksonXmlModule module = new JacksonXmlModule();
				//System.out.println("2");
				module.setDefaultUseWrapper(false);
				//System.out.println("3");
				XmlMapper xmlMapper = new XmlMapper(module);

				try {
					int itemsCount = 0;
					Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
					ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
					Iterator itr2 = deRest.iterator();
					while (itr2.hasNext()) {
						gResProcLote r = (gResProcLote) itr2.next();
						System.out.println("ID: " + r.getId() + " | " +
								"Estado: " + r.getdEstRes() + " | " +
								"Cod. Rpta.: " + r.getgResProc().getdCodRes() + " | " +
								"Msj. Rpta.: " + r.getgResProc().getdMsgRes() );
						//System.out.println("8");
						// actualizar el item de lote con los datos de la respuesta
						RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
						o.setBatchId(batchId);
						o.setControlCode(r.getId());
						o.setResultCode(r.getgResProc().getdCodRes());
						o.setResultMessage(r.getgResProc().getdMsgRes());
						o.setResultStatus(r.getdEstRes());
						o.setResultTxNumber(r.getdProtAut());
						RcvTrxEbBatchItemsDAO.updateRow(o, conn);
						itemsCount++;
					}
					// actualizar el indicador de estado consultado del lote
					if (itemsCount >= itemsQty) {
						RcvTrxEbBatchesDAO.updateQueriedFlag(batchId, conn);
					}

				} catch (JsonProcessingException e) {
					//throw new RuntimeException(e);
					e.printStackTrace();
					am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
					return am;
				} catch (Exception ex) {
					ex.printStackTrace();
					am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
					return am;
				}
			}
		}


		am.setMessage("QUERY-BATCH", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
		return am;
	}

	public ApplicationMessage queryCDC ( String controlCode ) throws SifenException {
		ApplicationMessage am = new ApplicationMessage();
		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			am.setMessage("QUERY-CDC", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			am.setMessage("QUERY-CDC", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
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

		am.setMessage("QUERY-CDC", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
		return am;
	}

	public ApplicationMessage querySingleBatch ( String batchNumber ) throws SifenException {
		ApplicationMessage am = new ApplicationMessage();
		Connection conn =  null;
		long batchId = 0;
		int itemsCount = 0;
		int itemsQty = 0;
		String batchNo = null;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
				return am;
			}
		} catch ( Exception e ) {
			am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
			return am;
		}

		RcvTrxEbBatch b = RcvTrxEbBatchesDAO.getRowByNumber(batchNumber);

		if (b != null) {
			batchId = b.getIdentifier();
			boolean existsItems = RcvTrxEbBatchItemsDAO.existsNoQueriedItems(batchId);
			if (existsItems == true) {
				// establecer la configuracion Sifen
				try {
					setupSifenConfig();
				} catch ( SifenException e1 ) {
					am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
					return am;
				} catch ( Exception e2 ) {
					am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
				}

				batchNo = b.getBatchNumber();
				itemsQty = b.getItemsQty();
				// invocar los servicios de Sifen para el envio de la consulta del lote
				System.out.println("Enviando consulta de lote " + batchNo + "...");
				RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(batchNo);
				//System.out.println(ret.toString());
				//System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
				//System.out.println(ret.getRespuestaBruta().toString());

				//System.out.println("1");
				JacksonXmlModule module = new JacksonXmlModule();
				//System.out.println("2");
				module.setDefaultUseWrapper(false);
				//System.out.println("3");
				XmlMapper xmlMapper = new XmlMapper(module);

				try {
					//System.out.println("4");
					Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
					//System.out.println("5");
					rResEnviConsLoteDe batchResp = tmp.getBody().getrResEnviConsLoteDe();
					if (batchResp != null) {
						System.out.println(batchResp.dCodResLot + " - " + batchResp.dMsgResLot + " - " + batchResp.dFecProc);
					}
					ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
					if (deRest != null) {
						Iterator itr2 = deRest.iterator();
						while (itr2.hasNext()) {
							//System.out.println("7");
							gResProcLote r = (gResProcLote) itr2.next();
							System.out.println("ID: " + r.getId() + " | " +
									"Estado: " + r.getdEstRes() + " | " +
									"Cod. Rpta.: " + r.getgResProc().getdCodRes() + " | " +
									"Msj. Rpta.: " + r.getgResProc().getdMsgRes() );
							//System.out.println("8");
							// actualizar el item de lote con los datos de la respuesta
							RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
							o.setBatchId(batchId);
							o.setControlCode(r.getId());
							o.setResultCode(r.getgResProc().getdCodRes());
							o.setResultMessage(r.getgResProc().getdMsgRes());
							o.setResultStatus(r.getdEstRes());
							o.setResultTxNumber(r.getdProtAut());
							RcvTrxEbBatchItemsDAO.updateRow(o, conn);
							itemsCount++;
						}
						// actualizar el indicador de estado consultado del lote
						if (itemsCount >= itemsQty) {
							RcvTrxEbBatchesDAO.updateQueriedFlag(batchId, conn);
						}
					} else {
						am.setMessage("MSG-DECODE", "No se ha podido obtener la lista de transacciones del lote", ApplicationMessage.ERROR);
						return am;				    	
					}

				} catch (JsonProcessingException e) {
					//throw new RuntimeException(e);
					e.printStackTrace();
					am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
					return am;
				} catch (Exception ex) {
					ex.printStackTrace();
					am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
					return am;
				}
			}
		}
		am.setMessage("QUERY-BATCH", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
		return am;
	}

	public ApplicationMessage queryBatchesList ( String trxType, 
			java.util.Date fromDate, 
			java.util.Date toDate ) throws SifenException {
		ApplicationMessage am = new ApplicationMessage();
		Connection conn =  null;
		long batchId = 0;
		String batchNo = null;

		// establecer la configuracion Sifen
		try {
			setupSifenConfig();
		} catch ( SifenException e1 ) {
			am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
			return am;
		} catch ( Exception e2 ) {
			am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
		}

		try {
			conn = Util.getConnection();
			if (conn == null) {
				am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
				return am;
			}
		} catch ( Exception e ) {
			am.setMessage("DB-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
			return am;
		}

		ArrayList<RcvTrxEbBatch> batches = RcvTrxEbBatchesDAO.getList(trxType, null, fromDate, toDate);
		if (batches != null) {
			Iterator itr1 = batches.iterator();
			while (itr1.hasNext()) {
				RcvTrxEbBatch x = (RcvTrxEbBatch) itr1.next();
				batchId = x.getIdentifier();
				boolean existsItems = RcvTrxEbBatchItemsDAO.existsNoQueriedItems(batchId);
				if (existsItems == true) {
					batchNo = x.getBatchNumber();
					// invocar los servicios de Sifen para el envio de la consulta del lote
					System.out.println("Enviando consulta lote...");
					RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(batchNo);
					//System.out.println(ret.toString());
					//System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
					//System.out.println(ret.getRespuestaBruta().toString());

					//System.out.println("1");
					JacksonXmlModule module = new JacksonXmlModule();
					//System.out.println("2");
					module.setDefaultUseWrapper(false);
					//System.out.println("3");
					XmlMapper xmlMapper = new XmlMapper(module);

					try {
						//System.out.println("4");
						Envelope tmp = xmlMapper.readValue(ret.getRespuestaBruta(), Envelope.class);
						//System.out.println("5");
						ArrayList<gResProcLote> deRest = (ArrayList<gResProcLote>) tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
						Iterator itr2 = deRest.iterator();
						while (itr2.hasNext()) {
							//System.out.println("7");
							gResProcLote r = (gResProcLote) itr2.next();
							System.out.println("ID: " + r.getId() + " | " +
									"Estado: " + r.getdEstRes() + " | " +
									"Cod. Rpta.: " + r.getgResProc().getdCodRes() + " | " +
									"Msj. Rpta.: " + r.getgResProc().getdMsgRes() );
							//System.out.println("8");
							// actualizar el item de lote con los datos de la respuesta
							RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
							o.setBatchId(batchId);
							o.setControlCode(r.getId());
							o.setResultCode(r.getgResProc().getdCodRes());
							o.setResultMessage(r.getgResProc().getdMsgRes());
							o.setResultStatus(r.getdEstRes());
							o.setResultTxNumber(r.getdProtAut());
							RcvTrxEbBatchItemsDAO.updateRow(o, conn);
						}
					} catch (JsonProcessingException e) {
						//throw new RuntimeException(e);
						e.printStackTrace();
						am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
						return am;
					} catch (Exception ex) {
						ex.printStackTrace();
						am.setMessage("MSG-DECODE", "No se ha podido recibir el mensaje del servicio externo", ApplicationMessage.ERROR);
						return am;
					}
				}
			}
		}


		am.setMessage("QUERY-BATCH", "Actividad realizada con exito", ApplicationMessage.MESSAGE);
		return am;
	}

}
