package pojo;

public class DocumElectronico {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | AA                                                                         |
	// | ID          | AA001                                                                      |
	// | Padre       | Raiz                                                                       |
	// | Campo       | rDE                                                                        |
	// | Descripcion | Documento Electronico raiz                                                 |
	// +------------------------------------------------------------------------------------------+
	private short dVerFor;
	private CamposFirmados DE;
	private CamposFueraFirma gCamFuFD;
	private long cashId;
	private long controlId;
	private long transactionId;
	
	public short getdVerFor() {
		return dVerFor;
	}
	public void setdVerFor(short dVerFor) {
		this.dVerFor = dVerFor;
	}
	public CamposFirmados getDE() {
		return DE;
	}
	public void setDE(CamposFirmados dE) {
		DE = dE;
	}
	public CamposFueraFirma getgCamFuFD() {
		return gCamFuFD;
	}
	public void setgCamFuFD(CamposFueraFirma gCamFuFD) {
		this.gCamFuFD = gCamFuFD;
	}
	public long getCashId() {
		return cashId;
	}
	public void setCashId(long cashId) {
		this.cashId = cashId;
	}
	public long getControlId() {
		return controlId;
	}
	public void setControlId(long controlId) {
		this.controlId = controlId;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
}
