package pojo;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CamposOperacionCredito {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7.2                                                                       |
	// | ID          | E640                                                                       |
	// | Padre       | E600                                                                       |
	// | Campo       | gPagCred                                                                   |
	// | Descripcion | Campos que describen la operacion a credito                                |
	// +------------------------------------------------------------------------------------------+
	private short iCondCred;
	private String dDCondCred;
	private String dPlazoCre;
	private short dCuotas;
	private BigDecimal dMonEnt;
	private ArrayList<CamposCuotas> gCuotas;
	
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
	public ArrayList<CamposCuotas> getgCuotas() {
		return gCuotas;
	}
	public void setgCuotas(ArrayList<CamposCuotas> gCuotas) {
		this.gCuotas = gCuotas;
	}

}
