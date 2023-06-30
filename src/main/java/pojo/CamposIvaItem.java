package pojo;

import java.math.BigDecimal;

public class CamposIvaItem {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E8.2                                                                       |
	// | ID          | E730                                                                       |
	// | Padre       | E700                                                                       |
	// | Campo       | gCamIVA                                                                    |
	// | Descripcion | Campos que describen el IVA de la operacion                                |
	// +------------------------------------------------------------------------------------------+
	// E731
	private short iAfecIVA;
	// E732
	private String dDesAfecIVA;
	// E733
	private BigDecimal dPropIVA;
	// E734
	private short dTasaIVA;
	// E735
	private BigDecimal dBasGravIVA;
	// E736
	private BigDecimal dLiqIVAItem;
	
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
