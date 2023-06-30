package pojo;

import java.io.Serializable;

public class EbDe implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_DE
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String ddvid;
    private java.util.Date dfecfirma;
    private short dsisfact;
    private String id;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long rdeId;
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

    public String getDdvid() {
        return this.ddvid;
    }
    public void setDdvid ( String ddvid ) {
        this.ddvid = ddvid;
    }

    public java.util.Date getDfecfirma() {
        return this.dfecfirma;
    }
    public void setDfecfirma ( java.util.Date dfecfirma ) {
        this.dfecfirma = dfecfirma;
    }

    public short getDsisfact() {
        return this.dsisfact;
    }
    public void setDsisfact ( short dsisfact ) {
        this.dsisfact = dsisfact;
    }

    public String getId() {
        return this.id;
    }
    public void setId ( String id ) {
        this.id = id;
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

    public long getRdeId() {
        return this.rdeId;
    }
    public void setRdeId ( long rdeid ) {
        this.rdeId = rdeid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}