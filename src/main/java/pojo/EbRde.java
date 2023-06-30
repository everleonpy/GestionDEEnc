package pojo;

import java.io.Serializable;

public class EbRde implements Serializable {
    /**
	 * POJO que representa a una fila de la tabla EB_RDE
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private long cashControlId;
    private long cashRegisterId;
    private String createdBy;
    private java.util.Date createdOn;
    private String dverfor;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long transactionId;
    private String trxType;
    private long unitId;

    public long getCashControlId() {
        return this.cashControlId;
    }
    public void setCashControlId ( long cashcontrolid ) {
        this.cashControlId = cashcontrolid;
    }

    public long getCashRegisterId() {
        return this.cashRegisterId;
    }
    public void setCashRegisterId ( long cashregisterid ) {
        this.cashRegisterId = cashregisterid;
    }

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

    public String getDverfor() {
        return this.dverfor;
    }
    public void setDverfor ( String dverfor ) {
        this.dverfor = dverfor;
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

    public long getTransactionId() {
        return this.transactionId;
    }
    public void setTransactionId ( long transactionid ) {
        this.transactionId = transactionid;
    }

    public String getTrxType() {
        return this.trxType;
    }
    public void setTrxType ( String trxtype ) {
        this.trxType = trxtype;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}