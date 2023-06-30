package pojo;

public class PosEbTransmissionLog {
	private long identifier;
	private long transactionId;
	private long cashControlId;
	private long cashId;
	private long orgId;
	private long unitId;
	private short eventId; 
	private String errorCode;
	private String errorMsg;
	
	public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
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
	public long getCashId() {
		return cashId;
	}
	public void setCashId(long cashId) {
		this.cashId = cashId;
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
	public short getEventId() {
		return eventId;
	}
	public void setEventId(short eventId) {
		this.eventId = eventId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
