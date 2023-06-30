package pojo;

import java.io.Serializable;

public class EbGopede implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GOPEDE
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String dcodseg;
    private String ddestipemi;
    private long deId;
    private String dinfoemi;
    private String dinfofisc;
    private long identifier;
    private short itipemi;
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

    public String getDcodseg() {
        return this.dcodseg;
    }
    public void setDcodseg ( String dcodseg ) {
        this.dcodseg = dcodseg;
    }

    public String getDdestipemi() {
        return this.ddestipemi;
    }
    public void setDdestipemi ( String ddestipemi ) {
        this.ddestipemi = ddestipemi;
    }

    public long getDeId() {
        return this.deId;
    }
    public void setDeId ( long deid ) {
        this.deId = deid;
    }

    public String getDinfoemi() {
        return this.dinfoemi;
    }
    public void setDinfoemi ( String dinfoemi ) {
        this.dinfoemi = dinfoemi;
    }

    public String getDinfofisc() {
        return this.dinfofisc;
    }
    public void setDinfofisc ( String dinfofisc ) {
        this.dinfofisc = dinfofisc;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public short getItipemi() {
        return this.itipemi;
    }
    public void setItipemi ( short itipemi ) {
        this.itipemi = itipemi;
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