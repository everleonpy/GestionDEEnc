package pojo;

import java.io.Serializable;

public class EbGcamiva implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GCAMIVA
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private long camitemId;
    private String createdBy;
    private java.util.Date createdOn;
    private double dbasgraviva;
    private String ddesafeciva;
    private double dliqivaitem;
    private long dpropiva;
    private short dtasaiva;
    private short iafeciva;
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

    public double getDbasgraviva() {
        return this.dbasgraviva;
    }
    public void setDbasgraviva ( double dbasgraviva ) {
        this.dbasgraviva = dbasgraviva;
    }

    public String getDdesafeciva() {
        return this.ddesafeciva;
    }
    public void setDdesafeciva ( String ddesafeciva ) {
        this.ddesafeciva = ddesafeciva;
    }

    public double getDliqivaitem() {
        return this.dliqivaitem;
    }
    public void setDliqivaitem ( double dliqivaitem ) {
        this.dliqivaitem = dliqivaitem;
    }

    public long getDpropiva() {
        return this.dpropiva;
    }
    public void setDpropiva ( long dpropiva ) {
        this.dpropiva = dpropiva;
    }

    public short getDtasaiva() {
        return this.dtasaiva;
    }
    public void setDtasaiva ( short dtasaiva ) {
        this.dtasaiva = dtasaiva;
    }

    public short getIafeciva() {
        return this.iafeciva;
    }
    public void setIafeciva ( short iafeciva ) {
        this.iafeciva = iafeciva;
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