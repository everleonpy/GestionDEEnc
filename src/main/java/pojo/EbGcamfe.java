package pojo;

import java.io.Serializable;

public class EbGcamfe implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GCAMFE
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String ddesindpres;
    private java.util.Date dfecemnr;
    private long identifier;
    private short iindpres;
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

    public String getDdesindpres() {
        return this.ddesindpres;
    }
    public void setDdesindpres ( String ddesindpres ) {
        this.ddesindpres = ddesindpres;
    }

    public java.util.Date getDfecemnr() {
        return this.dfecemnr;
    }
    public void setDfecemnr ( java.util.Date dfecemnr ) {
        this.dfecemnr = dfecemnr;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public short getIindpres() {
        return this.iindpres;
    }
    public void setIindpres ( short iindpres ) {
        this.iindpres = iindpres;
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