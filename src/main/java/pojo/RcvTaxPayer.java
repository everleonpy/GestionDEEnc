package pojo;

public class RcvTaxPayer {
	private long identifier;
	private long orgId;
	private long unitId;
	private String taxPayerNo;
	private String oldTaxPayerNo;
	private String fullName;
	private String createdBy;
	private java.util.Date createdOn;
	private String modifiedBy;
	private java.util.Date modifiedOn;
	
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
	public String getTaxPayerNo() {
		return taxPayerNo;
	}
	public void setTaxPayerNo(String taxPayerNo) {
		this.taxPayerNo = taxPayerNo;
	}
	public String getOldTaxPayerNo() {
		return oldTaxPayerNo;
	}
	public void setOldTaxPayerNo(String oldTaxPayerNo) {
		this.oldTaxPayerNo = oldTaxPayerNo;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public java.util.Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(java.util.Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
