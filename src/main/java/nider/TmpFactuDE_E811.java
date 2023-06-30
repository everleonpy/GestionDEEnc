package nider;

import java.math.BigDecimal;

public class TmpFactuDE_E811 {
	/**
	 * gValorRestaItem
	 * Campos que describen los descuentos, anticipos valor total por item
	 */
	private int idConfig;
	private int idMov;
	private BigDecimal dDescItem;
	private BigDecimal dPorcDesIt;
	private BigDecimal dDescGloItem;
	private BigDecimal dAntPreUniIt;
	private BigDecimal dAntGloPreUniIt;
	private BigDecimal dTotOpeItem;
	private BigDecimal dTotOpeGs;
	public int getIdConfig() {
		return idConfig;
	}
	public void setIdConfig(int idConfig) {
		this.idConfig = idConfig;
	}
	public int getIdMov() {
		return idMov;
	}
	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}
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
