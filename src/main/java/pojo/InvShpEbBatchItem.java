package pojo;

public class InvShpEbBatchItem {
    /**
	 * POJO que representa una fila de la tabla INV_SHP_EB_BATCH_ITEM
	 */
	private static final long serialVersionUID = 1L;
	private long batchId;
    private String controlCode;
    private String createdBy;
    private java.util.Date createdOn;
    private long identifier;
    private long orgId;
    private long resultCode;
    private String resultMessage;
    private String resultStatus;
    private long resultTxNumber;
    private long transactionId;
    private long unitId;
    private String txNumber;
    // los siguientes atributos no forman parte de la taba representada por el POJO
    private String xmlFile;
    private String qrCode;
    
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public String getControlCode() {
		return controlCode;
	}
	public void setControlCode(String controlCode) {
		this.controlCode = controlCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getResultCode() {
		return resultCode;
	}
	public void setResultCode(long resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public long getResultTxNumber() {
		return resultTxNumber;
	}
	public void setResultTxNumber(long resultTxNumber) {
		this.resultTxNumber = resultTxNumber;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public String getTxNumber() {
		return txNumber;
	}
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}
	public String getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
