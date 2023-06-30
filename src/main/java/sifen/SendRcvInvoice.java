package sifen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionDE;
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
//import com.roshka.sifen.test.soap.SOAPTestsFacturas;

import business.AppConfig;
import business.ApplicationMessage;
import dao.FacturaElectronicaDAOOld;
import dao.RcvCustomersTrxDAO;
import dao.UtilitiesDAO;
import pojo.CamposActivEconomica;
import pojo.CamposCondicionOperacion;
import pojo.CamposCuotas;
import pojo.CamposDescuentosItem;
import pojo.CamposEmisorDE;
import pojo.CamposFacturaElectronica;
import pojo.CamposItemsOperacion;
import pojo.CamposOperacionComercial;
import pojo.CamposOperacionContado;
import pojo.CamposOperacionCredito;
import pojo.CamposReceptorDE;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;
import pojo.RcvEbTransmissionLog;
import util.TransmissionLog;

public class SendRcvInvoice {
    private final static Logger logger = Logger.getLogger(SendRcvInvoice.class.toString());

    //@BeforeClass
    public static void setupSifenConfig() throws SifenException {
        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
        logger.info("Using CONFIG: " + sifenConfig);
        Sifen.setSifenConfig(sifenConfig);
    }

    //@Test
    //@Ignore
    public ApplicationMessage sendDE( long invoiceId, long orgId, long unitId, String usrName ) throws SifenException {

    	    ApplicationMessage am = new ApplicationMessage();
    	    int updated = 0;
    	    Envelope respBody;
    	    int respCode;
    	    String respMsg;
    	    String sendStatus = null;
    	    
    	    // establecer la configuracion Sifen
    	    try {
    	        setupSifenConfig();
    	    } catch ( SifenException e1 ) {
    		    am.setMessage("SEND-INV", "No se ha podido establecer la configuracion Sifen", ApplicationMessage.ERROR);
    	    } catch ( Exception e2 ) {
    		    am.setMessage("SEND-INV", "Configuracion Sifen: " + e2.getMessage(), ApplicationMessage.ERROR);    	    	
    	    }
    	    
        // construir el documento electronico a partir de los datos almacenados en el sistema
    	    // que corresponden a la factura cuyo ID se recibe como parametro
    	    //String usrName = "APPMGR";
    	    //long invoiceId = 217823;
    	    //long orgId = 1;
    	    //long unitId = 1;
        DocumentoElectronico DE = getInvoice ( invoiceId );
    	
        // solicitar al paquete Sifen el codigo de control para la factura
        // el CDC solo se debe generar una vez porque incluye un codigo de seguridad que se genera
        // en forma aleatoria que nunca se podra reproducir por mas que los datos que se utilizan
        // para construir el CDC sean los mismos
        String CDC = DE.obtenerCDC(null);
        logger.info("CDC del Documento Electrónico -> " + CDC);
        System.out.println("CDC del Documento Electrónico -> " + CDC);

        // invocar los servcios de Sifen para el envio de la factura
        RespuestaRecepcionDE ret = Sifen.recepcionDE(DE);
        logger.info(ret.toString());
        System.out.println(ret.toString());
        System.out.println("codEstado: " + ret.getCodigoEstado() + " dCodRes: " + ret.getdCodRes() + " dMsgRes: " + ret.getdMsgRes());
        //System.out.println(ret.getRespuestaBruta().toString());
        
        logger.info("CODIGO DE ESTADO : "+ret.getCodigoEstado());
        logger.info("RESPUESTA BRUTA  : "+ret.getRespuestaBruta());
        logger.info("COD RES :"+ret.getdCodRes());
        logger.info("MSG RESP :"+ret.getdMsgRes());
        System.out.println("XML : "+ret.getRespuestaBruta());
        
        
        // descargar el archivo xml generado en la carpeta destinada para el efecto
        String xmlFile = DE.getgTimb().getdNumDoc() + "_fa_envio.xml";
        String fileName = AppConfig.xmlFolder + xmlFile;
        DE.generarXml(fileName);
        
        
        //
        XmlMapper xmlMapper = new XmlMapper();
        try {
            respBody = xmlMapper.readValue(ret.getRespuestaBruta(),Envelope.class);
            respCode = respBody.getBody().getrRetEnviDe().getrProtDe().getgResProc().getdCodRes();
            respMsg = respBody.getBody().getrRetEnviDe().getrProtDe().getgResProc().getdMsgRes();
            System.out.println("===================================================================");
            System.out.println("Estado  : " + respBody.getBody().getrRetEnviDe().getrProtDe().getdEstRes());
            System.out.println("Codigo  : " + respCode);
            System.out.println("Mensaje : " + respMsg);
            System.out.println("--------------------------------------------------------------------");
            // de acuerdo a la respuesta del sifen, aqui se deben actualizar los datos de firma
            // y emision del documento, el codigo de seguridad, el CDC, el codigo QR y generar
            // el log de transmision del documento
            sendStatus = respBody.getBody().getrRetEnviDe().getrProtDe().getdEstRes();
            if (sendStatus.equalsIgnoreCase("Aprobado")) {
            	    // el codigo de seguridad esta dentro del CDC en la subcadena delimitada por las
            	    // posiciones desde la 34 hasta la 42
            	    String securityCode = CDC.substring(34, 43);
            	    LocalDateTime d = DE.getdFecFirma();
            	    //Timestamp timestamp = Timestamp.valueOf(d.format(DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss")));
            	    Timestamp timeStamp = Timestamp.valueOf(d);
            	    java.util.Date signDate = new java.util.Date(timeStamp.getTime());
            	    // actualizar CDC, codigo de seguridad, fecha de envio y fecha de firma de la factura informada
    			    updated = RcvCustomersTrxDAO.updateSignatureInfo ( invoiceId, 
    			    		                                               securityCode, 
    			    		                                               CDC, 
    			    		                                               DE.getEnlaceQR(),
    			    		                                               signDate,
    			    		                                               xmlFile );
    			    // actualizar el enlace QR de la factura informada
    			    updated = RcvCustomersTrxDAO.updateQrCode(invoiceId, orgId, unitId, DE.getEnlaceQR());
    			    // crear la definicion de la imagen del XML enviado en la base de datos
    			    //ApplicationMessage m = FacturaElectronicaDAO.registrarDE(DE, usrName, invoiceId, orgId, unitId);
    			    //if ( m != null) {
    			    //    System.out.println("Error en registro DE: " + m.getText());
    			    //}
            } 
         	// generar el log del evento
    		    RcvEbTransmissionLog tLog = new RcvEbTransmissionLog();
    		    long logId = UtilitiesDAO.getNextval("SQ_RCV_EB_TRANSMISSION_LOG");
    		    tLog.setIdentifier(logId);
    		    tLog.setErrorCode(String.valueOf(respCode));
    		    tLog.setErrorMsg(respMsg);
    		    tLog.setEventId(TransmissionLog.ENVIO_TRANSACCION.getVal());
    		    tLog.setIdentifier(logId);
    		    tLog.setOrgId(orgId);
    		    tLog.setTransactionId(invoiceId);
    		    tLog.setUnitId(unitId);
    		    updated = RcvCustomersTrxDAO.createTransmissionLog(tLog);     
    		    //
            if (sendStatus.equalsIgnoreCase("Aprobado")) {
                am.setMessage("SEND-INV", "El documento enviado ha sido aprobado", ApplicationMessage.MESSAGE);
            } else {
                am.setMessage("SEND-INV", "El documento enviado ha sido " + sendStatus, ApplicationMessage.ERROR);            	
            }
    		    return am;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            am.setMessage("SEND-INV", "No se pudo determinar el estado del envio", ApplicationMessage.ERROR);
            return am;
        } 
    }

    //@Ignore
    private DocumentoElectronico getInvoice ( long invoiceId ) {
    	    DocumentoElectronico DE = new DocumentoElectronico();
        LocalDateTime currentDate = LocalDateTime.now();
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    String s;
    	    BigDecimal bd;
    	    //
    	    System.out.println("Buscando datos de factura: " + invoiceId);
    	    DocumElectronico da = FacturaElectronicaDAOOld.getRcvInvoice(invoiceId);
    	    if (da != null) {
    	    	    System.out.println("********** datos obtenidos. " + da.getDE().getId());
    	    	    // asignar el contenido del documento Avanza al documento Sifen

    	        // Grupo A
    	        DE.setdFecFirma(currentDate);
    	    	    DE.setdSisFact(da.getDE().getdSisFact());
    	    	    // el valor del campo "dFecFirma" es asignado en el procedimiento de firma del xml
    	    	    // lo anterior, al parecer, no es asi

    	        // Grupo B
    	        TgOpeDE gOpeDE = new TgOpeDE(null);
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
    	        TdDatGralOpe dDatGralOpe = new TdDatGralOpe();
    	        dDatGralOpe.setdFeEmiDE(currentDate);

    	        /**
   	         +-----------------------------------------------------------------------------+
   	         | gOpeCom - Datos de la operacion comercial                                   |
   	         +-----------------------------------------------------------------------------+
   	        */
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
    	        TgEmis gEmis = new TgEmis();
    	        CamposEmisorDE em = da.getDE().getgDatGralOpe().getgEmis();
    	        gEmis.setdRucEm(em.getdRucEm());
    	        gEmis.setdDVEmi(em.getdDVEmi());
    	        gEmis.setiTipCont(TiTipCont.getByVal(em.getiTipCont()));
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
    	        Iterator itr1 = la.iterator();
            while (itr1.hasNext()) {
                	CamposActivEconomica x = (CamposActivEconomica) itr1.next();
                	if (x.getcActEco().equalsIgnoreCase("46301") | x.getcActEco().equalsIgnoreCase("46302") |
                			x.getcActEco().equalsIgnoreCase("46304")) {
                	TgActEco gActEco = new TgActEco();
                	gActEco.setcActEco(x.getcActEco());
                	gActEco.setdDesActEco(x.getdDesActEco());
        	        gActEcoList.add(gActEco);
        	        System.out.println(gActEco.getcActEco() + ": " + gActEco.getdDesActEco());
                	}
            }    	        
    	        
    	        gEmis.setgActEcoList(gActEcoList);
    	        dDatGralOpe.setgEmis(gEmis);

    	        /**
   	         +-----------------------------------------------------------------------------+
   	         | gDatRec - Receptor del documento electronico                                |
   	         +-----------------------------------------------------------------------------+
   	        */
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
   	         | gCamFE - Documento tipo factura electronica                                 |
   	         +-----------------------------------------------------------------------------+
   	        */
    	        TgCamFE gCamFE = new TgCamFE();
    	        CamposFacturaElectronica fe = da.getDE().getgDtipDE().getgCamFE();
    	        gCamFE.setiIndPres(TiIndPres.getByVal(fe.getiIndPres()));
    	        if (fe.getdFecEmNR() != null) {
        	        s = sdf.format(fe.getdFecEmNR());
    	            gCamFE.setdFecEmNR(LocalDate.parse(s));
    	        }
    	        gDtipDE.setgCamFE(gCamFE);

    	        TgCamCond gCamCond = new TgCamCond();
    	        CamposCondicionOperacion co = da.getDE().getgDtipDE().getgCamCond();
    	        gCamCond.setiCondOpe(TiCondOpe.getByVal(co.getiCondOpe()));
    	        
    	        // Operciones contado
    	        if (gCamCond.getiCondOpe() == TiCondOpe.CONTADO) {
    	            List<TgPaConEIni> gPaConEIniList = new ArrayList<>();
    	        	    ArrayList<CamposOperacionContado> cn = co.getgPaConEIni();
    	    	        Iterator itr2 = cn.iterator();
    	            while (itr1.hasNext()) {
    	                	CamposOperacionContado x = (CamposOperacionContado) itr2.next();
    	                	TgPaConEIni gPaConEIni = new TgPaConEIni();
    	                	gPaConEIni.setiTiPago(TiTiPago.getByVal(x.getiTiPago()));
        	            bd = new BigDecimal(x.getdMonTiPag());
        	            bd = bd.setScale(4, RoundingMode.HALF_UP);
    	                	gPaConEIni.setdMonTiPag(bd);
    	                	gPaConEIni.setcMoneTiPag(CMondT.getByName(x.getcMoneTiPag()));
    	                	if (x.getcMoneTiPag().equalsIgnoreCase("PYG") == false) {
    	                	    gPaConEIni.setdTiCamTiPag(BigDecimal.valueOf(x.getdTiCamTiPag()));
    	                	}
    	                	// tarjetas de credito o debito
    	                	if (x.getgPagTarCD() != null) {
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
    	                		    gPagTarCD.setdNomTit(x.getgPagTarCD().getdNomTit());
    	                		}
    	                		if (x.getgPagTarCD().getdNumTarj() != 0) {
    	                		    gPagTarCD.setdNumTarj(x.getgPagTarCD().getdNumTarj());
    	                		}
    	                		gPaConEIni.setgPagTarCD(gPagTarCD);
    	                	}
    	                	// cheques
    	                	if (x.getgPagCheq() != null) {
    	                	    TgPagCheq gPagCheq = new TgPagCheq();
    	                	    gPagCheq.setdNumCheq(x.getgPagCheq().getdNumCheq());
    	                	    gPagCheq.setdBcoEmi(x.getgPagCheq().getdBcoEmi());
    	                	    gPaConEIni.setgPagCheq(gPagCheq);
    	                	}
    	                	gPaConEIniList.add(gPaConEIni);
    	            }
        	        gCamCond.setgPaConEIniList(gPaConEIniList);
    	        }

    	        // Operaciones credito
    	        if (gCamCond.getiCondOpe() == TiCondOpe.CREDITO) {
	        	    CamposOperacionCredito cr = co.getgPagCred();
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
	            if (cr.getgCuotas() != null) {
    	                List<TgCuotas> gTgCuotasList = new ArrayList<>();
	        	        ArrayList<CamposCuotas> cn = cr.getgCuotas();
	    	            Iterator itr3 = cn.iterator();
	                while (itr3.hasNext()) {
	                    CamposCuotas x = (CamposCuotas) itr3.next();
	                	    TgCuotas gCuota = new TgCuotas();
	            	        gCuota.setcMoneCuo(CMondT.getByName(x.getcMoneCuo()));
	            	        gCuota.setdMonCuota(BigDecimal.valueOf(x.getdMonCuota()));
	            	        if (x.getdVencCuo() != null) {
	            	            s = sdf.format(x.getdVencCuo());
	            	            gCuota.setdVencCuo(LocalDate.parse(s));
	            	        }
	            	        gTgCuotasList.add(gCuota);
	                }
	                gPagCred.setgCuotasList(gTgCuotasList);
	            }
    	            gCamCond.setgPagCred(gPagCred);
	        }
    	        
    	        gDtipDE.setgCamCond(gCamCond);

    	        /**
   	         +-----------------------------------------------------------------------------+
   	         | gCamItem - Items de la operacion                                            |
   	         +-----------------------------------------------------------------------------+
   	        */
    	        List<TgCamItem> gCamItemList = new ArrayList<>();
        	    ArrayList<CamposItemsOperacion> im = da.getDE().getgDtipDE().getgCamItem();
    	        Iterator itr4 = im.iterator();
            while (itr4.hasNext()) {
            	    CamposItemsOperacion x = (CamposItemsOperacion) itr4.next();
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
	            //System.out.println("gCamItem.getdCantProSer: " + gCamItem.getdCantProSer());
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
                //System.out.println("gValorItem.getdPUniProSer: " + gValorItem.getdPUniProSer());
                if (x.getgValorItem().getdTiCamIt() != null) {
                    if (x.getgValorItem().getdTiCamIt().doubleValue() != 0.0) {
                        gValorItem.setdTiCamIt(x.getgValorItem().getdTiCamIt());
                    }
                }
                // el valor del campo "dTotBruOpeItem" es asignado en el momento de la generacion del xml
                
                // valores de descuentos por item
                if (x.getgValorItem().getgValorRestaItem() != null) {
                	    CamposDescuentosItem d = x.getgValorItem().getgValorRestaItem();
                    TgValorRestaItem gValorRestaItem = new TgValorRestaItem();
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
                    gCamIVA.setiAfecIVA(TiAfecIVA.getByVal(x.getgCamIVA().getiAfecIVA()));
                    gCamIVA.setdPropIVA(x.getgCamIVA().getdPropIVA());
                    gCamIVA.setdTasaIVA(BigDecimal.valueOf(x.getgCamIVA().getdTasaIVA()));
                    // los valores de los campos "dBasGravIVA" y "dLiqIVAItem" son asignados en el momento
                    // de la generacion del xml
	                gCamItem.setgCamIVA(gCamIVA);
	            }

	            gCamItemList.add(gCamItem);
            }
    	        
    	        gDtipDE.setgCamItemList(gCamItemList);
    	        DE.setgDtipDE(gDtipDE);

    	        // Grupo E
    	        DE.setgTotSub(new TgTotSub());
    	    
    	    }
    	    return DE;
    }
    
}
