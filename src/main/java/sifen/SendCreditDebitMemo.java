package sifen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import com.roshka.sifen.core.types.TdTipCons;
import com.roshka.sifen.core.types.TiAfecIVA;
import com.roshka.sifen.core.types.TiMotEmi;
import com.roshka.sifen.core.types.TiNatRec;
import com.roshka.sifen.core.types.TiTiOpe;
import com.roshka.sifen.core.types.TiTipCont;
import com.roshka.sifen.core.types.TiTipDocAso;
import com.roshka.sifen.core.types.TiTipDocRec;
//import com.roshka.sifen.test.soap.SOAPTestsNotas;

import business.AppConfig;
import business.ApplicationMessage;
import dao.NotaElectronicaDAO;
import dao.RcvCustomersTrxDAO;
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
import pojo.RcvEbTransmissionLog;
import util.TransmissionLog;

public class SendCreditDebitMemo {
    private final static Logger logger = Logger.getLogger(SendCreditDebitMemo.class.toString());

    //@BeforeClass
    public static void setupSifenConfig() throws SifenException {
        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
        logger.info("Using CONFIG: " + sifenConfig);
        Sifen.setSifenConfig(sifenConfig);
    }

    //@Test
    //@Ignore
    //public void testRecepcionDE( long invoiceId, long orgId, long unitId ) throws SifenException {
    public ApplicationMessage sendDE( long memoId, long orgId, long unitId, String usrName ) throws SifenException {

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
    	
        DocumentoElectronico DE = getMemo ( memoId );
        // solicitar al paquete Sifen el codigo de control para la factura
        // el CDC solo se debe generar una vez porque incluye un codigo de seguridad que se genera
        // en forma aleatoria que nunca se podra reproducir por mas que los datos que se utilizan
        // para construir el CDC sean los mismos
        String CDC = DE.obtenerCDC(null);
        logger.info("CDC del Documento Electrónico -> " + CDC);
        System.out.println("CDC del Documento Electrónico -> " + CDC);

        // invocar los servcios de Sifen para el envio de la factura
        System.out.println("******** enviando peticion...");
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
        String xmlFile = DE.getgTimb().getdNumDoc() + "_nc_envio.xml";
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
    			    updated = RcvCustomersTrxDAO.updateSignatureInfo ( memoId, 
    			    		                                               securityCode, 
    			    		                                               CDC, 
    			    		                                               DE.getEnlaceQR(),
    			    		                                               signDate,
    			    		                                               xmlFile );
    			    // actualizar el enlace QR de la factura informada
    			    updated = RcvCustomersTrxDAO.updateQrCode(memoId, orgId, unitId, DE.getEnlaceQR());
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
    		    tLog.setTransactionId(memoId);
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
    private DocumentoElectronico getMemo ( long memoId ) {
    	    DocumentoElectronico DE = new DocumentoElectronico();
        LocalDateTime currentDate = LocalDateTime.now();
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    String s;
    	    //
    	    System.out.println("Buscando datos de nota: " + memoId);
    	    DocumElectronico da = NotaElectronicaDAO.getRcvMemo(memoId);
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
    	        System.out.println("pais leido: " + rd.getcPaisRec() + " pais asignado: " + gDatRec.getcPaisRec());
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
    	        TgCamNCDE gCamNCDE = new TgCamNCDE();
    	        CamposNotaElectronica ne = da.getDE().getgDtipDE().getgCamNCDE();
    	        gCamNCDE.setiMotEmi(TiMotEmi.getByVal(ne.getiMotEmi()));
    	        gDtipDE.setgCamNCDE(gCamNCDE);

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
                if (x.getgValorItem().getdTiCamIt().doubleValue() != 0.0) {
                    gValorItem.setdTiCamIt(x.getgValorItem().getdTiCamIt());
                }
                // el valor del campo "dTotBruOpeItem" es asignado en el momeno de la generacion del xml
                
                // valores de descuentos por item
                if (x.getgValorItem().getgValorRestaItem() != null) {
                	    CamposDescuentosItem d = x.getgValorItem().getgValorRestaItem();
                    TgValorRestaItem gValorRestaItem = new TgValorRestaItem();
                    if (d.getdDescItem().doubleValue() != 0.0) {
                        gValorRestaItem.setdDescItem(d.getdDescItem());
                    }
                    // el valor del campo "dPorcDesIt" es asignaco en el momento de la generacion del xml
                    if (d.getdDescGloItem().doubleValue() != 0.0) {
                        gValorRestaItem.setdDescGloItem(d.getdDescGloItem());
                    }
                    if (d.getdAntPreUniIt().doubleValue() != 0.0) {
                        gValorRestaItem.setdAntPreUniIt(d.getdAntPreUniIt());
                    }
                    if (d.getdAntGloPreUniIt().doubleValue() != 0.0) {
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
                    gCamIVA.setdTasaIVA(new BigDecimal(x.getgCamIVA().getdTasaIVA()));
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
    	        
    	        // Grupo H
    	        List<TgCamDEAsoc> gCamDEAsocList = new ArrayList<TgCamDEAsoc>();
    	        if (da.getDE().getgCamDEAsoc() != null) {
        	        Iterator itr5 = da.getDE().getgCamDEAsoc().iterator();
                while (itr5.hasNext()) {
                    	CamposDEAsociado a = (CamposDEAsociado) itr5.next();
    	    	            TgCamDEAsoc gCamDEAsoc = new TgCamDEAsoc();
    	        	        gCamDEAsoc.setiTipDocAso(TiTipDocAso.getByVal(a.getiTipDocAso()));
    	        	        gCamDEAsoc.setdCdCDERef(a.getdCdCDERef());
    	        	        gCamDEAsoc.setdNTimDI(a.getdNTimDI());
    	        	        gCamDEAsoc.setdEstDocAso(a.getdEstDocAso());
    	        	        gCamDEAsoc.setdPExpDocAso(a.getdPExpDocAso());
    	        	        gCamDEAsoc.setdNumDocAso(a.getdNumDocAso());
    	        	        //
    	        	        Instant instant = a.getdFecEmiDI().toInstant();
    	        	        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
    	        	        LocalDate issueDate = zdt.toLocalDate();
    	        	        gCamDEAsoc.setdFecEmiDI(issueDate);
    	        	        //
    	        	        gCamDEAsoc.setdNumComRet(a.getdNumComRet());
    	        	        gCamDEAsoc.setdNumResCF(a.getdNumResCF());
    	        	        gCamDEAsoc.setiTipCons(TdTipCons.getByVal(a.getiTipCons()));
    	        	        gCamDEAsoc.setdNumCons(BigInteger.valueOf(a.getdNumCons()));
    	        	        gCamDEAsoc.setdNumControl(a.getdNumControl());
    	        	        gCamDEAsocList.add(gCamDEAsoc);
                }
    	        	    //
    	        	    DE.setgCamDEAsocList(gCamDEAsocList);
    	        }
 
    	    }
    	    return DE;
    }
    
}
