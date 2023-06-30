package pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que representa un Documento Electrónico, incluyendo todos los campos disponibles en un DE en formato XML.
 */
public class DocElectronico {
    private String Id;
    private String dDVId;
    private String dFecFirma;    // se debe serializar en el formato: yyyy-mm-ddThh:mi:ss
    private short dSisFact;             // sistema que factura (1. Sistema del Cliente, 2. Sistema de facturación gratuita de la SET)

    private XgOpeDE gOpeDE;             // campos de operación del documento electrónico
    private XgTimb gTimb;               // campos del timbrado del documento
    private XdDatGralOpe gDatGralOpe;   // datos generales de la operación
    private XgDtipDE gDtipDE;
    private XgTotSub gTotSub;
    private TgCamGen gCamGen;
    private List<XgCamDEAsoc> gCamDEAsocList;

    private String enlaceQR;
    private final static Logger logger = Logger.getLogger(DocElectronico.class.toString());

    /**
     * Constructor base del Documento Electrónico.
     */
    public DocElectronico() {
    }


    public String getId() {
        return Id;
    }

    // este metodo setter se le agrega para poder asignarle un valor de CDC generado en forma previa al
    // momento de la generacion y el envio del archivo xml
    public void setId( String Id ) {
        this.Id = Id;
    }

    public String getdDVId() {
        return dDVId;
    }

    // idem metodo "setId"
    public void setdDVId ( String dDVId ) {
    	    this.dDVId = dDVId;
    }
    
    public String getdFecFirma() {
        return dFecFirma;
    }

    public void setdFecFirma(String dFecFirma) {
        this.dFecFirma = dFecFirma;
    }

    public short getdSisFact() {
        return dSisFact;
    }

    public void setdSisFact(short dSisFact) {
        this.dSisFact = dSisFact;
    }

    public XgOpeDE getgOpeDE() {
        return gOpeDE;
    }

    public void setgOpeDE(XgOpeDE gOpeDE) {
        this.gOpeDE = gOpeDE;
    }

    public XgTimb getgTimb() {
        return gTimb;
    }

    public void setgTimb(XgTimb gTimb) {
        this.gTimb = gTimb;
    }

    public XdDatGralOpe getgDatGralOpe() {
        return gDatGralOpe;
    }

    public void setgDatGralOpe(XdDatGralOpe gDatGralOpe) {
        this.gDatGralOpe = gDatGralOpe;
    }

    public XgDtipDE getgDtipDE() {
        return gDtipDE;
    }

    public void setgDtipDE(XgDtipDE gDtipDE) {
        this.gDtipDE = gDtipDE;
    }

    public XgTotSub getgTotSub() {
        return gTotSub;
    }

    public void setgTotSub(XgTotSub gTotSub) {
        this.gTotSub = gTotSub;
    }

    public TgCamGen getgCamGen() {
        return gCamGen;
    }

    public void setgCamGen(TgCamGen gCamGen) {
        this.gCamGen = gCamGen;
    }

    public List<XgCamDEAsoc> getgCamDEAsocList() {
        return gCamDEAsocList;
    }

    public void setgCamDEAsocList(List<XgCamDEAsoc> gCamDEAsocList) {
        this.gCamDEAsocList = gCamDEAsocList;
    }

    public String getEnlaceQR() {
        return enlaceQR;
    }

    public void setEnlaceQR(String enlaceQR) {
        this.enlaceQR = enlaceQR;
    }
}