package nider;

import java.util.ArrayList;

public class TmpFactuDE_E7 {
	/**
	 * gCamCond
	 * Campos que describen la condicion de la operacion
	 */
	private int idConfig;
	private int idMov;
	private short iCondOpe;
	private String dDCondOpe;
	// lista de elementos gPaConEIni
	private ArrayList<TmpFactuDE_E71> trmList;
	// datos del credito
	private TmpFactuDE_E72 gPagCred;
	
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
	public ArrayList<TmpFactuDE_E71> getTrmList() {
		return trmList;
	}
	public void setTrmList(ArrayList<TmpFactuDE_E71> trmList) {
		this.trmList = trmList;
	}
	public TmpFactuDE_E72 getgPagCred() {
		return gPagCred;
	}
	public void setgPagCred(TmpFactuDE_E72 gPagCred) {
		this.gPagCred = gPagCred;
	}

}
