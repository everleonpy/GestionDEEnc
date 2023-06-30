package pojo;

import java.io.Serializable;

public class PosOption implements Serializable {
	/**
	 * POJO que representa una fila de la tabla POS_OPTIONS
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
	private java.util.Date createdOn;
	private int ebBatchTxQty;
	private java.util.Date fromDate;
	private long identifier;
	private String modifiedBy;
	private java.util.Date modifiedOn;
	private long orgId;
	private java.util.Date toDate;
	private long unitId;

	public String getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy ( String createdby ) {
		this.createdBy = createdby;
	}

	public java.util.Date getCreatedOn() {
		return this.createdOn;
	}
	public void setCreatedOn ( java.util.Date createdon ) {
		this.createdOn = createdon;
	}

	public int getEbBatchTxQty() {
		return this.ebBatchTxQty;
	}
	public void setEbBatchTxQty ( int ebbatchtxqty ) {
		this.ebBatchTxQty = ebbatchtxqty;
	}

	public java.util.Date getFromDate() {
		return this.fromDate;
	}
	public void setFromDate ( java.util.Date fromdate ) {
		this.fromDate = fromdate;
	}

	public long getIdentifier() {
		return this.identifier;
	}
	public void setIdentifier ( long identifier ) {
		this.identifier = identifier;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}
	public void setModifiedBy ( String modifiedby ) {
		this.modifiedBy = modifiedby;
	}

	public java.util.Date getModifiedOn() {
		return this.modifiedOn;
	}
	public void setModifiedOn ( java.util.Date modifiedon ) {
		this.modifiedOn = modifiedon;
	}

	public long getOrgId() {
		return this.orgId;
	}
	public void setOrgId ( long orgid ) {
		this.orgId = orgid;
	}

	public java.util.Date getToDate() {
		return this.toDate;
	}
	public void setToDate ( java.util.Date todate ) {
		this.toDate = todate;
	}

	public long getUnitId() {
		return this.unitId;
	}
	public void setUnitId ( long unitid ) {
		this.unitId = unitid;
	}

}
