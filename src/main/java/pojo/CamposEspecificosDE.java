package pojo;

import java.util.ArrayList;

public class CamposEspecificosDE {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E                                                                          |
	// | ID          | E001                                                                       |
	// | Padre       | A001                                                                       |
	// | Campo       | gDtipDE                                                                    |
	// | Descripcion | Campos especificos por tipo de documento electronico                       |
	// +------------------------------------------------------------------------------------------+
	private CamposFacturaElectronica gCamFE;
	private CamposAutofacturaElectronica gCamAE;
	private CamposNotaElectronica gCamNCDE;
	private CamposRemisionElectronica gCamNRE;
	private CamposCondicionOperacion gCamCond;
	private ArrayList<CamposItemsOperacion> gCamItem;
	private CamposTranspMercaderias gTransp;
	
	public CamposFacturaElectronica getgCamFE() {
		return gCamFE;
	}
	public void setgCamFE(CamposFacturaElectronica gCamFE) {
		this.gCamFE = gCamFE;
	}
	public CamposAutofacturaElectronica getgCamAE() {
		return gCamAE;
	}
	public void setgCamAE(CamposAutofacturaElectronica gCamAE) {
		this.gCamAE = gCamAE;
	}
	public CamposNotaElectronica getgCamNCDE() {
		return gCamNCDE;
	}
	public void setgCamNCDE(CamposNotaElectronica gCamNCDE) {
		this.gCamNCDE = gCamNCDE;
	}
	public CamposRemisionElectronica getgCamNRE() {
		return gCamNRE;
	}
	public void setgCamNRE(CamposRemisionElectronica gCamNRE) {
		this.gCamNRE = gCamNRE;
	}
	public CamposCondicionOperacion getgCamCond() {
		return gCamCond;
	}
	public void setgCamCond(CamposCondicionOperacion gCamCond) {
		this.gCamCond = gCamCond;
	}
	public ArrayList<CamposItemsOperacion> getgCamItem() {
		return gCamItem;
	}
	public void setgCamItem(ArrayList<CamposItemsOperacion> gCamItem) {
		this.gCamItem = gCamItem;
	}
	public CamposTranspMercaderias getgTransp() {
		return gTransp;
	}
	public void setgTransp(CamposTranspMercaderias gTransp) {
		this.gTransp = gTransp;
	}
	
}
