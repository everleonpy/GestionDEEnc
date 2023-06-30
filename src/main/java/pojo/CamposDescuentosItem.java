package pojo;

import java.math.BigDecimal;

public class CamposDescuentosItem {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E8.1.1                                                                     |
	// | ID          | EA001                                                                      |
	// | Padre       | E720                                                                       |
	// | Campo       | gValorRestaItem                                                            |
	// | Descripcion | Campos que describen los descuentos, anticipos valor total por ítem        |
	// +------------------------------------------------------------------------------------------+
	// EA002
	private BigDecimal dDescItem;
	// EA003
	private BigDecimal dPorcDesIt;
	// EA004
	private BigDecimal dDescGloItem;
	// EA006
	private BigDecimal dAntPreUniIt;
	// EA007
	private BigDecimal dAntGloPreUniIt;
	// EA008
	private BigDecimal dTotOpeItem;
	// EA009
	private BigDecimal dTotOpeGs;
	
	public BigDecimal getdDescItem() {
		return dDescItem;
	}
	public void setdDescItem(BigDecimal dDescItem) {
		this.dDescItem = dDescItem;
	}
	public BigDecimal getdPorcDesIt() {
		return dPorcDesIt;
	}
	public void setdPorcDesIt(BigDecimal dPorcDesIt) {
		this.dPorcDesIt = dPorcDesIt;
	}
	public BigDecimal getdDescGloItem() {
		return dDescGloItem;
	}
	public void setdDescGloItem(BigDecimal dDescGloItem) {
		this.dDescGloItem = dDescGloItem;
	}
	public BigDecimal getdAntPreUniIt() {
		return dAntPreUniIt;
	}
	public void setdAntPreUniIt(BigDecimal dAntPreUniIt) {
		this.dAntPreUniIt = dAntPreUniIt;
	}
	public BigDecimal getdAntGloPreUniIt() {
		return dAntGloPreUniIt;
	}
	public void setdAntGloPreUniIt(BigDecimal dAntGloPreUniIt) {
		this.dAntGloPreUniIt = dAntGloPreUniIt;
	}
	public BigDecimal getdTotOpeItem() {
		return dTotOpeItem;
	}
	public void setdTotOpeItem(BigDecimal dTotOpeItem) {
		this.dTotOpeItem = dTotOpeItem;
	}
	public BigDecimal getdTotOpeGs() {
		return dTotOpeGs;
	}
	public void setdTotOpeGs(BigDecimal dTotOpeGs) {
		this.dTotOpeGs = dTotOpeGs;
	}
	
}
