package pojo;

public class CamposComplUsoGeneral {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | G                                                                          |
	// | ID          | G001                                                                       |
	// | Padre       | A001                                                                       |
	// | Campo       | gCamGen                                                                    |
	// | Descripcion | Campos de uso general                                                      |
	// +------------------------------------------------------------------------------------------+
	private String dOrdCompra;
	private String dOrdVta;
	private String dAsiento;
	private CamposGeneralesCarga gCamCarg;
	
	public String getdOrdCompra() {
		return dOrdCompra;
	}
	public void setdOrdCompra(String dOrdCompra) {
		this.dOrdCompra = dOrdCompra;
	}
	public String getdOrdVta() {
		return dOrdVta;
	}
	public void setdOrdVta(String dOrdVta) {
		this.dOrdVta = dOrdVta;
	}
	public String getdAsiento() {
		return dAsiento;
	}
	public void setdAsiento(String dAsiento) {
		this.dAsiento = dAsiento;
	}
	public CamposGeneralesCarga getgCamCarg() {
		return gCamCarg;
	}
	public void setgCamCarg(CamposGeneralesCarga gCamCarg) {
		this.gCamCarg = gCamCarg;
	}
	
}
