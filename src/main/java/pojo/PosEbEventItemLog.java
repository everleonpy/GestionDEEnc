package pojo;

public class PosEbEventItemLog {
	private long identifier;       
	private long eventLogId;     
	private long orgId;           
	private long unitId;          
	private long transactionId;   
	private long cashControlId;  
	private long cashRegisterId; 
	private String ebControlCode;  
	private short eventTypeId;    
	private String createdBy;       
	private java.util.Date createdOn;       
	private String eventReason;     
	private String resultStatus;    
	private long eventTrxId;     
	private long resultCode;      
	private String resultMessage;
	
	public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	public long getEventLogId() {
		return eventLogId;
	}
	public void setEventLogId(long eventLogId) {
		this.eventLogId = eventLogId;
	}
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public long getCashControlId() {
		return cashControlId;
	}
	public void setCashControlId(long cashControlId) {
		this.cashControlId = cashControlId;
	}
	public long getCashRegisterId() {
		return cashRegisterId;
	}
	public void setCashRegisterId(long cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}
	public String getEbControlCode() {
		return ebControlCode;
	}
	public void setEbControlCode(String ebControlCode) {
		this.ebControlCode = ebControlCode;
	}
	public short getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(short eventTypeId) {
		this.eventTypeId = eventTypeId;
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
	public String getEventReason() {
		return eventReason;
	}
	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public long getEventTrxId() {
		return eventTrxId;
	}
	public void setEventTrxId(long eventTrxId) {
		this.eventTrxId = eventTrxId;
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
	
}
