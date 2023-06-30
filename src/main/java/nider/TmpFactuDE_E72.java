package nider;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TmpFactuDE_E72 {
	/**
	 * gPagCred
	 * Campos que describen la operación a credito
	 */
	private int idConfig;
	private int idMov;
	private short iCondCred;
	private String dDCondCred;
	private String dPlazoCre;
	private short dCuotas;
    private BigDecimal dMonEnt;
    // lista de cuotas
    private ArrayList<TmpFactuDE_E721> instsList;
    
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
	public short getiCondCred() {
		return iCondCred;
	}
	public void setiCondCred(short iCondCred) {
		this.iCondCred = iCondCred;
	}
	public String getdDCondCred() {
		return dDCondCred;
	}
	public void setdDCondCred(String dDCondCred) {
		this.dDCondCred = dDCondCred;
	}
	public String getdPlazoCre() {
		return dPlazoCre;
	}
	public void setdPlazoCre(String dPlazoCre) {
		this.dPlazoCre = dPlazoCre;
	}
	public short getdCuotas() {
		return dCuotas;
	}
	public void setdCuotas(short dCuotas) {
		this.dCuotas = dCuotas;
	}
	public BigDecimal getdMonEnt() {
		return dMonEnt;
	}
	public void setdMonEnt(BigDecimal dMonEnt) {
		this.dMonEnt = dMonEnt;
	}
	public ArrayList<TmpFactuDE_E721> getInstsList() {
		return instsList;
	}
	public void setInstsList(ArrayList<TmpFactuDE_E721> instsList) {
		this.instsList = instsList;
	}

}
