package pojo;

public class PosEbSendLog {
	private String estabilshCode;
	private String issuePointCode;
	private String txNumber;
	private long orgId;
	private long unitId;
	private int eventId; 
	private String errorCode;
	private String errorMsg;
	private java.util.Date createdOn;
	private String httpCode;
	
	public String getEstabilshCode() {
		return estabilshCode;
	}
	public void setEstabilshCode(String estabilshCode) {
		this.estabilshCode = estabilshCode;
	}
	public String getIssuePointCode() {
		return issuePointCode;
	}
	public void setIssuePointCode(String issuePointCode) {
		this.issuePointCode = issuePointCode;
	}
	public String getTxNumber() {
		return txNumber;
	}
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
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
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
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
	public java.util.Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

}
