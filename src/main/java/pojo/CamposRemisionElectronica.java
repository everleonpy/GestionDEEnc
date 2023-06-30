package pojo;

public class CamposRemisionElectronica {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E6                                                                         |
	// | ID          | E500                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gCamNRE                                                                    |
	// | Descripcion | Campos que componen la Nota de Remision Electronica                        |
	// +------------------------------------------------------------------------------------------+
	private short iMotEmiNR;
	private String dDesMotEmiNR;
	private short iRespEmiNR;
	private String dDesRespEmiNR;
	private short dKmR;
	private java.util.Date dFecEm;
	
	public short getiMotEmiNR() {
		return iMotEmiNR;
	}
	public void setiMotEmiNR(short iMotEmiNR) {
		this.iMotEmiNR = iMotEmiNR;
	}
	public String getdDesMotEmiNR() {
		return dDesMotEmiNR;
	}
	public void setdDesMotEmiNR(String dDesMotEmiNR) {
		this.dDesMotEmiNR = dDesMotEmiNR;
	}
	public short getiRespEmiNR() {
		return iRespEmiNR;
	}
	public void setiRespEmiNR(short iRespEmiNR) {
		this.iRespEmiNR = iRespEmiNR;
	}
	public String getdDesRespEmiNR() {
		return dDesRespEmiNR;
	}
	public void setdDesRespEmiNR(String dDesRespEmiNR) {
		this.dDesRespEmiNR = dDesRespEmiNR;
	}
	public short getdKmR() {
		return dKmR;
	}
	public void setdKmR(short dKmR) {
		this.dKmR = dKmR;
	}
	public java.util.Date getdFecEm() {
		return dFecEm;
	}
	public void setdFecEm(java.util.Date dFecEm) {
		this.dFecEm = dFecEm;
	}
	
}
