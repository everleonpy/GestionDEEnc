package pojo;

import java.math.BigDecimal;

public class CamposValoresItem {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E8.1                                                                       |
	// | ID          | E720                                                                       |
	// | Padre       | E700                                                                       |
	// | Campo       | gValorItem                                                                 |
	// | Descripcion | Campos que describen los precios, descuentos y valor total por item        |
	// +------------------------------------------------------------------------------------------+
	private BigDecimal dPUniProSer;
	private BigDecimal dTiCamIt;
	private BigDecimal dTotBruOpeItem;
	private CamposDescuentosItem gValorRestaItem;
	
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
	public CamposDescuentosItem getgValorRestaItem() {
		return gValorRestaItem;
	}
	public void setgValorRestaItem(CamposDescuentosItem gValorRestaItem) {
		this.gValorRestaItem = gValorRestaItem;
	}

}
