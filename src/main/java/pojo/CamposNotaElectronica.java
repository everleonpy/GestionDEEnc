package pojo;

public class CamposNotaElectronica {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E5                                                                         |
	// | ID          | E400                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gCamNCDE                                                                   |
	// | Descripcion | Campos de la Nota de Credito/Debito Electronica                            |
	// +------------------------------------------------------------------------------------------+
	private short iMotEmi;
	private String dDesMotEmi;
	
	public short getiMotEmi() {
		return iMotEmi;
	}
	public void setiMotEmi(short iMotEmi) {
		this.iMotEmi = iMotEmi;
	}
	public String getdDesMotEmi() {
		return dDesMotEmi;
	}
	public void setdDesMotEmi(String dDesMotEmi) {
		this.dDesMotEmi = dDesMotEmi;
	}
	
}
