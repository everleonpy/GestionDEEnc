package pojo;

public class RcvTrxEbBatch {
    /**
	 * POJO que representa una fila de la tabla RCV_TRX_EB_BATCH
	 */
	private static final long serialVersionUID = 1L;
	private String batchNumber;
    private String createdBy;
    private java.util.Date createdOn;
    private long identifier;
    private long orgId;
    private java.util.Date transmissDate;
    private long unitId;
    private int resultCode;
    private String resultMessage;
    private int processTime;
    private String queriedFlag;
    private String trxType;
    private java.util.Date trxDate;
    private int itemsQty;
    
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
	public java.util.Date getTransmissDate() {
		return transmissDate;
	}
	public void setTransmissDate(java.util.Date transmissDate) {
		this.transmissDate = transmissDate;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	public String getQueriedFlag() {
		return queriedFlag;
	}
	public void setQueriedFlag(String queriedFlag) {
		this.queriedFlag = queriedFlag;
	}
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public java.util.Date getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}
	public int getItemsQty() {
		return itemsQty;
	}
	public void setItemsQty(int itemsQty) {
		this.itemsQty = itemsQty;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
