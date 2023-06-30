package pojo;

import java.io.Serializable;

public class EbDatgralope implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_DATGRALOPE
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private long deId;
    private java.util.Date dfeemide;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
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

    public long getDeId() {
        return this.deId;
    }
    public void setDeId ( long deid ) {
        this.deId = deid;
    }

    public java.util.Date getDfeemide() {
        return this.dfeemide;
    }
    public void setDfeemide ( java.util.Date dfeemide ) {
        this.dfeemide = dfeemide;
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

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}