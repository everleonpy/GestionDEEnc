package pojo;

import java.math.BigDecimal;

public class CamposItemsOperacion {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E8                                                                         |
	// | ID          | E700                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gCamItem                                                                   |
	// | Descripcion | Campos que describen los ítems de la operacion                             |
	// +------------------------------------------------------------------------------------------+
    private String dCodInt;
    private short dParAranc;
    private int dNCM;
    private String dDncpG;
    private String dDncpE;
    private long dGtin;
    private long dGtinPq;
    private String dDesProSer;
    private short cUniMed;
    private String dDesUniMed;
    private BigDecimal dCantProSer;
    private String cPaisOrig;
    private String dDesPaisOrig;
    private String dInfItem;
    private short cRelMerc;
    private String dDesRelMerc;
    private BigDecimal dCanQuiMer;
    private BigDecimal dPorQuiMer;
    private String dCDCAnticipo;
    private CamposValoresItem gValorItem;
    private CamposIvaItem gCamIVA;
    
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
		this.dDncpG = dDncpG;
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
	public String getdDesUniMed() {
		return dDesUniMed;
	}
	public void setdDesUniMed(String dDesUniMed) {
		this.dDesUniMed = dDesUniMed;
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
	public String getdDesPaisOrig() {
		return dDesPaisOrig;
	}
	public void setdDesPaisOrig(String dDesPaisOrig) {
		this.dDesPaisOrig = dDesPaisOrig;
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
	public String getdDesRelMerc() {
		return dDesRelMerc;
	}
	public void setdDesRelMerc(String dDesRelMerc) {
		this.dDesRelMerc = dDesRelMerc;
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
	public CamposValoresItem getgValorItem() {
		return gValorItem;
	}
	public void setgValorItem(CamposValoresItem gValorItem) {
		this.gValorItem = gValorItem;
	}
	public CamposIvaItem getgCamIVA() {
		return gCamIVA;
	}
	public void setgCamIVA(CamposIvaItem gCamIVA) {
		this.gCamIVA = gCamIVA;
	}
    
}
