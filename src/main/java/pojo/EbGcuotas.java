package pojo;

import java.io.Serializable;

public class EbGcuotas implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GCUOTAS
	 */
	private static final long serialVersionUID = 1L;
	private String cmonecuo;
    private String createdBy;
    private java.util.Date createdOn;
    private String ddmonecuo;
    private double dmoncuota;
    private java.util.Date dvenccuo;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long pagcredId;
    private long unitId;

    public String getCmonecuo() {
        return this.cmonecuo;
    }
    public void setCmonecuo ( String cmonecuo ) {
        this.cmonecuo = cmonecuo;
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

    public String getDdmonecuo() {
        return this.ddmonecuo;
    }
    public void setDdmonecuo ( String ddmonecuo ) {
        this.ddmonecuo = ddmonecuo;
    }

    public double getDmoncuota() {
        return this.dmoncuota;
    }
    public void setDmoncuota ( double dmoncuota ) {
        this.dmoncuota = dmoncuota;
    }

    public java.util.Date getDvenccuo() {
        return this.dvenccuo;
    }
    public void setDvenccuo ( java.util.Date dvenccuo ) {
        this.dvenccuo = dvenccuo;
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

    public long getPagcredId() {
        return this.pagcredId;
    }
    public void setPagcredId ( long pagcredid ) {
        this.pagcredId = pagcredid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}