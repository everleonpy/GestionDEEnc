package pojo;

import java.io.Serializable;

public class EbGpaconeini implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GPACONEINI
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private long camcondId;
    private String cmonetipag;
    private String createdBy;
    private java.util.Date createdOn;
    private String ddestipag;
    private String ddmonetipag;
    private double dmontipag;
    private double dticamtipag;
    private long identifier;
    private short itipago;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long unitId;

    public long getCamcondId() {
        return this.camcondId;
    }
    public void setCamcondId ( long camcondid ) {
        this.camcondId = camcondid;
    }

    public String getCmonetipag() {
        return this.cmonetipag;
    }
    public void setCmonetipag ( String cmonetipag ) {
        this.cmonetipag = cmonetipag;
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

    public String getDdestipag() {
        return this.ddestipag;
    }
    public void setDdestipag ( String ddestipag ) {
        this.ddestipag = ddestipag;
    }

    public String getDdmonetipag() {
        return this.ddmonetipag;
    }
    public void setDdmonetipag ( String ddmonetipag ) {
        this.ddmonetipag = ddmonetipag;
    }

    public double getDmontipag() {
        return this.dmontipag;
    }
    public void setDmontipag ( double dmontipag ) {
        this.dmontipag = dmontipag;
    }

    public double getDticamtipag() {
        return this.dticamtipag;
    }
    public void setDticamtipag ( double dticamtipag ) {
        this.dticamtipag = dticamtipag;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public short getItipago() {
        return this.itipago;
    }
    public void setItipago ( short itipago ) {
        this.itipago = itipago;
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