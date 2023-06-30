package pojo;

import java.math.BigDecimal;

import util.UtilPOS;


public class XgCamItem {
    private String dCodInt;
    private short dParAranc;
    private int dNCM;
    private String dDncpG;
    private String dDncpE;
    private long dGtin;
    private long dGtinPq;
    private String dDesProSer;
    private short cUniMed;
    private BigDecimal dCantProSer;
    private String cPaisOrig;
    private String dInfItem;
    private short cRelMerc;
    private BigDecimal dCanQuiMer;
    private BigDecimal dPorQuiMer;
    private String dCDCAnticipo;
    private XgValorItem gValorItem;
    private XgCamIVA gCamIVA;
    private XgRasMerc gRasMerc;
    private XgVehNuevo gVehNuevo;

    public String getdCodInt() {
        return dCodInt;
    }

    public void setdCodInt(String dCodInt) {
        this.dCodInt = dCodInt;
    }

    public short getdParAranc() {
        return dParAranc;
    }

    public void setdParAranc(short dParAranc) {
        this.dParAranc = dParAranc;
    }

    public int getdNCM() {
        return dNCM;
    }

    public void setdNCM(int dNCM) {
        this.dNCM = dNCM;
    }

    public String getdDncpG() {
        return dDncpG;
    }

    public void setdDncpG(String dDncpG) {
        //this.dDncpG = SifenUtil.leftPad(dDncpG, '0', 8);
        this.dDncpG = UtilPOS.paddingString(dDncpG, 8, '0', true);
    }

    public String getdDncpE() {
        return dDncpE;
    }

    public void setdDncpE(String dDncpE) {
        this.dDncpE = dDncpE;
    }

    public long getdGtin() {
        return dGtin;
    }

    public void setdGtin(long dGtin) {
        this.dGtin = dGtin;
    }

    public long getdGtinPq() {
        return dGtinPq;
    }

    public void setdGtinPq(long dGtinPq) {
        this.dGtinPq = dGtinPq;
    }

    public String getdDesProSer() {
        return dDesProSer;
    }

    public void setdDesProSer(String dDesProSer) {
        this.dDesProSer = dDesProSer;
    }

    public short getcUniMed() {
        return cUniMed;
    }

    public void setcUniMed(short cUniMed) {
        this.cUniMed = cUniMed;
    }

    public BigDecimal getdCantProSer() {
        return dCantProSer;
    }

    public void setdCantProSer(BigDecimal dCantProSer) {
        this.dCantProSer = dCantProSer;
    }

    public String getcPaisOrig() {
        return cPaisOrig;
    }

    public void setcPaisOrig(String cPaisOrig) {
        this.cPaisOrig = cPaisOrig;
    }

    public String getdInfItem() {
        return dInfItem;
    }

    public void setdInfItem(String dInfItem) {
        this.dInfItem = dInfItem;
    }

    public short getcRelMerc() {
        return cRelMerc;
    }

    public void setcRelMerc(short cRelMerc) {
        this.cRelMerc = cRelMerc;
    }

    public BigDecimal getdCanQuiMer() {
        return dCanQuiMer;
    }

    public void setdCanQuiMer(BigDecimal dCanQuiMer) {
        this.dCanQuiMer = dCanQuiMer;
    }

    public BigDecimal getdPorQuiMer() {
        return dPorQuiMer;
    }

    public void setdPorQuiMer(BigDecimal dPorQuiMer) {
        this.dPorQuiMer = dPorQuiMer;
    }

    public String getdCDCAnticipo() {
        return dCDCAnticipo;
    }

    public void setdCDCAnticipo(String dCDCAnticipo) {
        this.dCDCAnticipo = dCDCAnticipo;
    }

    public XgValorItem getgValorItem() {
        return gValorItem;
    }

    public void setgValorItem(XgValorItem gValorItem) {
        this.gValorItem = gValorItem;
    }

    public XgCamIVA getgCamIVA() {
        return gCamIVA;
    }

    public void setgCamIVA(XgCamIVA gCamIVA) {
        this.gCamIVA = gCamIVA;
    }

    public XgRasMerc getgRasMerc() {
        return gRasMerc;
    }

    public void setgRasMerc(XgRasMerc gRasMerc) {
        this.gRasMerc = gRasMerc;
    }

    public XgVehNuevo getgVehNuevo() {
        return gVehNuevo;
    }

    public void setgVehNuevo(XgVehNuevo gVehNuevo) {
        this.gVehNuevo = gVehNuevo;
    }
}
