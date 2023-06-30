package pojo;

import java.io.Serializable;

public class EbGpagcheq implements Serializable {
    /**
	 * POJO 	que representa una fila de la tabla EB_GPAGCHEQ
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String dbcoemi;
    private String dnumcheq;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long paconeiniId;
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

    public String getDbcoemi() {
        return this.dbcoemi;
    }
    public void setDbcoemi ( String dbcoemi ) {
        this.dbcoemi = dbcoemi;
    }

    public String getDnumcheq() {
        return this.dnumcheq;
    }
    public void setDnumcheq ( String dnumcheq ) {
        this.dnumcheq = dnumcheq;
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

    public long getPaconeiniId() {
        return this.paconeiniId;
    }
    public void setPaconeiniId ( long paconeiniid ) {
        this.paconeiniId = paconeiniid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}