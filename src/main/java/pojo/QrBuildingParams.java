package pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QrBuildingParams {
    private LocalDateTime dFeEmiDE;
    private String Id;
    private short iNatRec;
    private String dRucRec;
    private short iTiOpe;
    private String dNumIDRec;
    private short iTiDE;
    private short iTImp;
    private short cantidadItems;
    private BigDecimal dTotGralOpe = BigDecimal.ZERO;
    private BigDecimal dTotIVA = BigDecimal.ZERO;
    
	public LocalDateTime getdFeEmiDE() {
		return dFeEmiDE;
	}
	public void setdFeEmiDE(LocalDateTime dFeEmiDE) {
		this.dFeEmiDE = dFeEmiDE;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public short getiNatRec() {
		return iNatRec;
	}
	public void setiNatRec(short iNatRec) {
		this.iNatRec = iNatRec;
	}
	public String getdRucRec() {
		return dRucRec;
	}
	public void setdRucRec(String dRucRec) {
		this.dRucRec = dRucRec;
	}
	public short getiTiOpe() {
		return iTiOpe;
	}
	public void setiTiOpe(short iTiOpe) {
		this.iTiOpe = iTiOpe;
	}
	public String getdNumIDRec() {
		return dNumIDRec;
	}
	public void setdNumIDRec(String dNumIDRec) {
		this.dNumIDRec = dNumIDRec;
	}
	public short getiTiDE() {
		return iTiDE;
	}
	public void setiTiDE(short iTiDE) {
		this.iTiDE = iTiDE;
	}
	public short getiTImp() {
		return iTImp;
	}
	public void setiTImp(short iTImp) {
		this.iTImp = iTImp;
	}
	public short getCantidadItems() {
		return cantidadItems;
	}
	public void setCantidadItems(short cantidadItems) {
		this.cantidadItems = cantidadItems;
	}
	public BigDecimal getdTotGralOpe() {
		return dTotGralOpe;
	}
	public void setdTotGralOpe(BigDecimal dTotGralOpe) {
		this.dTotGralOpe = dTotGralOpe;
	}
	public BigDecimal getdTotIVA() {
		return dTotIVA;
	}
	public void setdTotIVA(BigDecimal dTotIVA) {
		this.dTotIVA = dTotIVA;
	}
    
}
