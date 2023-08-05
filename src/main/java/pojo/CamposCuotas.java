package pojo;

import org.apache.commons.math3.util.Precision;

public class CamposCuotas {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7.2.1                                                                     |
	// | ID          | E650                                                                       |
	// | Padre       | E640                                                                       |
	// | Campo       | gCuotas                                                                    |
	// | Descripcion | Campos que describen las cuotas                                            |
	// +------------------------------------------------------------------------------------------+
	private String cMoneCuo;
	private String dDMoneCuo;
	private double dMonCuota;
	private java.util.Date dVencCuo;
	
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
		return Precision.round(dMonCuota,4);
	}
	public void setdMonCuota(double dMonCuota) {
		this.dMonCuota = Precision.round(dMonCuota,4);
	}
	public java.util.Date getdVencCuo() {
		return dVencCuo;
	}
	public void setdVencCuo(java.util.Date dVencCuo) {
		this.dVencCuo = dVencCuo;
	}
	
}
