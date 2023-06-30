package nider;

import java.math.BigDecimal;

public class TmpFactuDE_E81 {
	/**
	 * gValorItem
	 * Campos que describen los precios, descuentos y valor total por item
	 */
	private int idConfig;
	private int idMov;
	private BigDecimal dPUniProSer;
	private BigDecimal dTiCamIt;
	private BigDecimal dTotBruOpeItem;
	// obtener el elemento gValorRestaItem
	private TmpFactuDE_E811 gValorRestaItem;
	
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
	public BigDecimal getdPUniProSer() {
		return dPUniProSer;
	}
	public void setdPUniProSer(BigDecimal dPUniProSer) {
		this.dPUniProSer = dPUniProSer;
	}
	public BigDecimal getdTiCamIt() {
		return dTiCamIt;
	}
	public void setdTiCamIt(BigDecimal dTiCamIt) {
		this.dTiCamIt = dTiCamIt;
	}
	public BigDecimal getdTotBruOpeItem() {
		return dTotBruOpeItem;
	}
	public void setdTotBruOpeItem(BigDecimal dTotBruOpeItem) {
		this.dTotBruOpeItem = dTotBruOpeItem;
	}
	public TmpFactuDE_E811 getgValorRestaItem() {
		return gValorRestaItem;
	}
	public void setgValorRestaItem(TmpFactuDE_E811 gValorRestaItem) {
		this.gValorRestaItem = gValorRestaItem;
	}

}
