package pojo;

public class CamposOperacionDE {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | B                                                                          |
	// | ID          | B001                                                                       |
	// | Padre       | A001                                                                       |
	// | Campo       | gOpeDE                                                                     |
	// | Descripcion | Campos inherentes a la operación de DE                                     |
	// +------------------------------------------------------------------------------------------+
	private short iTipEmi;
	private String dDesTipEmi;
	private String dCodSeg;
	private String dInfoEmi;
	private String dInfoFisc;
	
	public short getiTipEmi() {
		return iTipEmi;
	}
	public void setiTipEmi(short iTipEmi) {
		this.iTipEmi = iTipEmi;
	}
	public String getdDesTipEmi() {
		return dDesTipEmi;
	}
	public void setdDesTipEmi(String dDesTipEmi) {
		this.dDesTipEmi = dDesTipEmi;
	}
	public String getdCodSeg() {
		return dCodSeg;
	}
	public void setdCodSeg(String dCodSeg) {
		this.dCodSeg = dCodSeg;
	}
	public String getdInfoEmi() {
		return dInfoEmi;
	}
	public void setdInfoEmi(String dInfoEmi) {
		this.dInfoEmi = dInfoEmi;
	}
	public String getdInfoFisc() {
		return dInfoFisc;
	}
	public void setdInfoFisc(String dInfoFisc) {
		this.dInfoFisc = dInfoFisc;
	}
	
}
