package nider;

import org.apache.commons.math3.util.Precision;

public class TmpFactuDE_E721 {
	/**
	 * gCuotas
	 * Campos que describen las cuotas
	 */
	private int idConfig;
	private int idMov;
	private String cMoneCuo;
	private String dDMoneCuo;
	private double dMonCuota;
	private String dVencCuo;
	
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
	public String getcMoneCuo() {
		return cMoneCuo;
	}
	public void setcMoneCuo(String cMoneCuo) {
		this.cMoneCuo = cMoneCuo;
	}
	public String getdDMoneCuo() {
		return dDMoneCuo;
	}
	public void setdDMoneCuo(String dDMoneCuo) {
		this.dDMoneCuo = dDMoneCuo;
	}
	public double getdMonCuota() {
		Double resp = Precision.round(dMonCuota, 4);
		return resp;
	}
	public void setdMonCuota(double dMonCuota) {
		Double resp = Precision.round(dMonCuota, 4);
		this.dMonCuota = resp;
	}
	public String getdVencCuo() {
		return dVencCuo;
	}
	public void setdVencCuo(String dVencCuo) {
		this.dVencCuo = dVencCuo;
	}

}
