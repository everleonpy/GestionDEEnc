package nider;

import java.math.BigDecimal;

public class TmpFactuDE_E82 {
	/**
	 * gCamIVA
	 * Campos que describen el IVA de la operación
	 */
	private int idConfig;
	private int idMov;
	private short iAfecIVA;
	private String dDesAfecIVA;
	private BigDecimal dPropIVA;
	private short dTasaIVA;
	private BigDecimal dBasGravIVA;
	private BigDecimal dLiqIVAItem;
	
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
	public short getiAfecIVA() {
		return iAfecIVA;
	}
	public void setiAfecIVA(short iAfecIVA) {
		this.iAfecIVA = iAfecIVA;
	}
	public String getdDesAfecIVA() {
		return dDesAfecIVA;
	}
	public void setdDesAfecIVA(String dDesAfecIVA) {
		this.dDesAfecIVA = dDesAfecIVA;
	}
	public BigDecimal getdPropIVA() {
		return dPropIVA;
	}
	public void setdPropIVA(BigDecimal dPropIVA) {
		this.dPropIVA = dPropIVA;
	}
	public short getdTasaIVA() {
		return dTasaIVA;
	}
	public void setdTasaIVA(short dTasaIVA) {
		this.dTasaIVA = dTasaIVA;
	}
	public BigDecimal getdBasGravIVA() {
		return dBasGravIVA;
	}
	public void setdBasGravIVA(BigDecimal dBasGravIVA) {
		this.dBasGravIVA = dBasGravIVA;
	}
	public BigDecimal getdLiqIVAItem() {
		return dLiqIVAItem;
	}
	public void setdLiqIVAItem(BigDecimal dLiqIVAItem) {
		this.dLiqIVAItem = dLiqIVAItem;
	}

}
