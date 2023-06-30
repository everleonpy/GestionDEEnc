package pojo;

import java.io.Serializable;

public class EbGacteco implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GACTECO
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String cacteco;
    private String createdBy;
    private java.util.Date createdOn;
    private String ddesacteco;
    private long emisId;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long unitId;

    public String getCacteco() {
        return this.cacteco;
    }
    public void setCacteco ( String cacteco ) {
        this.cacteco = cacteco;
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

    public String getDdesacteco() {
        return this.ddesacteco;
    }
    public void setDdesacteco ( String ddesacteco ) {
        this.ddesacteco = ddesacteco;
    }

    public long getEmisId() {
        return this.emisId;
    }
    public void setEmisId ( long emisid ) {
        this.emisId = emisid;
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