package pojo;

import java.io.Serializable;

public class EbGcamcond implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GCAMCOND
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String ddcondope;
    private short icondope;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long tipdeId;
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

    public String getDdcondope() {
        return this.ddcondope;
    }
    public void setDdcondope ( String ddcondope ) {
        this.ddcondope = ddcondope;
    }

    public short getIcondope() {
        return this.icondope;
    }
    public void setIcondope ( short icondope ) {
        this.icondope = icondope;
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

    public long getTipdeId() {
        return this.tipdeId;
    }
    public void setTipdeId ( long tipdeid ) {
        this.tipdeId = tipdeid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}