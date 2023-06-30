package pojo;

import java.util.ArrayList;

public class CamposCondicionOperacion {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7                                                                         |
	// | ID          | E600                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gCamCond                                                                   |
	// | Descripcion | Campos que describen la condicion de la operacion                          |
	// +------------------------------------------------------------------------------------------+
	private short iCondOpe;
	private String dDCondOpe;
	private ArrayList<CamposOperacionContado> gPaConEIni;
	private CamposOperacionCredito gPagCred;
	
	public short getiCondOpe() {
		return iCondOpe;
	}
	public void setiCondOpe(short iCondOpe) {
		this.iCondOpe = iCondOpe;
	}
	public String getdDCondOpe() {
		return dDCondOpe;
	}
	public void setdDCondOpe(String dDCondOpe) {
		this.dDCondOpe = dDCondOpe;
	}
	public ArrayList<CamposOperacionContado> getgPaConEIni() {
		return gPaConEIni;
	}
	public void setgPaConEIni(ArrayList<CamposOperacionContado> gPaConEIni) {
		this.gPaConEIni = gPaConEIni;
	}
	public CamposOperacionCredito getgPagCred() {
		return gPagCred;
	}
	public void setgPagCred(CamposOperacionCredito gPagCred) {
		this.gPagCred = gPagCred;
	}
	
}
