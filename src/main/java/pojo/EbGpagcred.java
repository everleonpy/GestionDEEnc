package pojo;

import java.io.Serializable;

public class EbGpagcred implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GPAGCRED
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private long camcondId;
    private String createdBy;
    private java.util.Date createdOn;
    private short dcuotas;
    private String ddcondcred;
    private double dmonent;
    private String dplazocre;
    private short icondcred;
    private long identifier;
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

    public short getDcuotas() {
        return this.dcuotas;
    }
    public void setDcuotas ( short dcuotas ) {
        this.dcuotas = dcuotas;
    }

    public String getDdcondcred() {
        return this.ddcondcred;
    }
    public void setDdcondcred ( String ddcondcred ) {
        this.ddcondcred = ddcondcred;
    }

    public double getDmonent() {
        return this.dmonent;
    }
    public void setDmonent ( double dmonent ) {
        this.dmonent = dmonent;
    }

    public String getDplazocre() {
        return this.dplazocre;
    }
    public void setDplazocre ( String dplazocre ) {
        this.dplazocre = dplazocre;
    }

    public short getIcondcred() {
        return this.icondcred;
    }
    public void setIcondcred ( short icondcred ) {
        this.icondcred = icondcred;
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