package pojo;

public class PosEbEventLog {
	private long identifier;   
	private long orgId;       
	private long unitId;      
	private String createdBy;   
	private java.util.Date createdOn;   
	private String resultStatus;
	private long eventTrxId;
	
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
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
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

}
