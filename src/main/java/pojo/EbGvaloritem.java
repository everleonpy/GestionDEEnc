package pojo;

import java.io.Serializable;

public class EbGvaloritem implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GVALORITEM
	 */
	private static final long serialVersionUID = 1L;
	private long camitemId;
    private String createdBy;
    private java.util.Date createdOn;
    private double dpuniproser;
    private double dticamit;
    private double dtotbruopeitem;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long unitId;

    public long getCamitemId() {
        return this.camitemId;
    }
    public void setCamitemId ( long camitemid ) {
        this.camitemId = camitemid;
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

    public double getDpuniproser() {
        return this.dpuniproser;
    }
    public void setDpuniproser ( double dpuniproser ) {
        this.dpuniproser = dpuniproser;
    }

    public double getDticamit() {
        return this.dticamit;
    }
    public void setDticamit ( double dticamit ) {
        this.dticamit = dticamit;
    }

    public double getDtotbruopeitem() {
        return this.dtotbruopeitem;
    }
    public void setDtotbruopeitem ( double dtotbruopeitem ) {
        this.dtotbruopeitem = dtotbruopeitem;
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