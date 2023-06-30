package pojo;

public class CamposFacturaElectronica {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E1                                                                         |
	// | ID          | E010                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gCamFE                                                                     |
	// | Descripcion | Campos que componen la FE                                                  |
	// +------------------------------------------------------------------------------------------+
	private short iIndPres;
	private String dDesIndPres;
	private java.util.Date dFecEmNR;
	
	public short getiIndPres() {
		return iIndPres;
	}
	public void setiIndPres(short iIndPres) {
		this.iIndPres = iIndPres;
	}
	public String getdDesIndPres() {
		return dDesIndPres;
	}
	public void setdDesIndPres(String dDesIndPres) {
		this.dDesIndPres = dDesIndPres;
	}
	public java.util.Date getdFecEmNR() {
		return dFecEmNR;
	}
	public void setdFecEmNR(java.util.Date dFecEmNR) {
		this.dFecEmNR = dFecEmNR;
	}
	
}
