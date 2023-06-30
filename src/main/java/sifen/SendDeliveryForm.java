package sifen;

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

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionDE;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.de.TdDatGralOpe;
import com.roshka.sifen.core.fields.request.de.TgActEco;
import com.roshka.sifen.core.fields.request.de.TgCamItem;
import com.roshka.sifen.core.fields.request.de.TgCamNRE;
import com.roshka.sifen.core.fields.request.de.TgDatRec;
import com.roshka.sifen.core.fields.request.de.TgDtipDE;
import com.roshka.sifen.core.fields.request.de.TgEmis;
import com.roshka.sifen.core.fields.request.de.TgOpeDE;
import com.roshka.sifen.core.fields.request.de.TgTimb;
import com.roshka.sifen.core.fields.request.de.TgTotSub;
import com.roshka.sifen.core.types.PaisType;
import com.roshka.sifen.core.types.TDepartamento;
import com.roshka.sifen.core.types.TTiDE;
import com.roshka.sifen.core.types.TTipEmi;
import com.roshka.sifen.core.types.TTipReg;
import com.roshka.sifen.core.types.TcRelMerc;
import com.roshka.sifen.core.types.TcUniMed;
import com.roshka.sifen.core.types.TiMotivTras;
import com.roshka.sifen.core.types.TiNatRec;
import com.roshka.sifen.core.types.TiRespEmiNR;
import com.roshka.sifen.core.types.TiTiOpe;
import com.roshka.sifen.core.types.TiTipCont;
import com.roshka.sifen.core.types.TiTipDocRec;
//import com.roshka.sifen.test.soap.SOAPTestsRemisiones;

import dao.InvInternalShipmentsDAO;
import dao.XRemisionElectronicaDAO;
import dao.UtilitiesDAO;
import pojo.CamposActivEconomica;
import pojo.CamposEmisorDE;
import pojo.CamposItemsOperacion;
import pojo.CamposReceptorDE;
import pojo.CamposRemisionElectronica;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;
import pojo.InvEbTransmissionLog;
import util.TransmissionLog;

public class SendDeliveryForm {
    private final static Logger logger = Logger.getLogger(SendDeliveryForm.class.toString());

    //@BeforeClass
    public static void setupSifenConfig() throws SifenException {
        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
        logger.info("Using CONFIG: " + sifenConfig);
        Sifen.setSifenConfig(sifenConfig);
    }

    public void sendDE( ) throws SifenException {

    	    int updated = 0;
    	    
        // construir el documento electronico a partir de los datos almacenados en el sistema
    	    // que corresponden a la factura cuyo ID se recibe como parametro
    	    long shipmentId = 217883;
    	    long orgId = 1;
    	    long unitId = 1;
        DocumentoElectronico DE = getDeliveryForm ( shipmentId );
    	
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
        
        // de acuerdo a la respuesta del sifen, aqui se deben actualizar los datos de firma
        // y emision del documento, el codigo de seguridad, el CDC, el codigo QR y generar
        // el log de transmision del documento
        if (ret.getCodigoEstado() == 200) {
        	    // el codigo de seguridad esta dentro del CDC en la subcadena delimitada por las
        	    // posiciones desde la 34 hasta la 42
        	    String securityCode = CDC.substring(34, 43);
        	    LocalDateTime d = DE.getdFecFirma();
        	    //Timestamp timestamp = Timestamp.valueOf(d.format(DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss")));
        	    Timestamp timeStamp = Timestamp.valueOf(d);
        	    java.util.Date signDate = new java.util.Date(timeStamp.getTime());
        	    // actualizar CDC, codigo de seguridad, fecha de envio y fecha de firma de la factura informada
			updated = InvInternalShipmentsDAO.updateSigantureInfo(shipmentId, securityCode, CDC, signDate);
			// actualizar el enlace QR de la factura informada
			updated = InvInternalShipmentsDAO.updateQrCode(shipmentId, orgId, unitId, DE.getEnlaceQR());
			// generar el log del evento
			InvEbTransmissionLog tLog = new InvEbTransmissionLog();
			long logId = UtilitiesDAO.getNextval("SQ_INV_EB_TRANSMISSION_LOG");
			tLog.setErrorCode(ret.getdCodRes());
			tLog.setErrorMsg(ret.getdMsgRes());
			tLog.setEventId(TransmissionLog.ENVIO_TRANSACCION.getVal());
			tLog.setIdentifier(logId);
			tLog.setOrgId(orgId);
			tLog.setShipmentId(shipmentId);
			tLog.setUnitId(unitId);
			updated = InvInternalShipmentsDAO.createTransmissionLog(tLog);
        } else {
			// generar el log del evento
			InvEbTransmissionLog tLog = new InvEbTransmissionLog();
			long logId = UtilitiesDAO.getNextval("SQ_INV_EB_TRANSMISSION_LOG");
			tLog.setErrorCode(ret.getdCodRes());
			tLog.setErrorMsg(ret.getdMsgRes());
			tLog.setEventId(TransmissionLog.ENVIO_TRANSACCION.getVal());
			tLog.setIdentifier(logId);
			tLog.setOrgId(orgId);
			tLog.setShipmentId(shipmentId);
			tLog.setUnitId(unitId);
			updated = InvInternalShipmentsDAO.createTransmissionLog(tLog);        	
        } 
    }

    private DocumentoElectronico getDeliveryForm ( long shipmentId ) {
    	    DocumentoElectronico DE = new DocumentoElectronico();
        LocalDateTime currentDate = LocalDateTime.now();
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    String s;
    	    //
    	    System.out.println("Buscando datos de remision: " + shipmentId);
    	    DocumElectronico da = XRemisionElectronicaDAO.getInvShipment(shipmentId);
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
   	         | gCamNRE - Documento tipo remision electronica                               |
   	         +-----------------------------------------------------------------------------+
   	        */
    	        TgCamNRE gCamNRE = new TgCamNRE();
    	        CamposRemisionElectronica re = da.getDE().getgDtipDE().getgCamNRE();
    	        gCamNRE.setiMotEmiNR(TiMotivTras.getByVal(re.getiMotEmiNR()));
    	        gCamNRE.setiRespEmiNR(TiRespEmiNR.getByVal(re.getiRespEmiNR()));
    	        if (re.getdKmR() > 0) {
    	        	    gCamNRE.setdKmR(re.getdKmR());
    	        }
    	        if (re.getdFecEm() != null) {
        	        Instant instant = re.getdFecEm().toInstant();
        	        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        	        LocalDate invoiceDate = zdt.toLocalDate();
    	        	    gCamNRE.setdFecEm(invoiceDate);
    	        }
    	        gDtipDE.setgCamNRE(gCamNRE);

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
