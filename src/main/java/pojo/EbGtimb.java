package pojo;

import java.io.Serializable;

public class EbGtimb implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GTIMB
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private String ddestide;
    private String dest;
    private long deId;
    private java.util.Date dfeinit;
    private String dnumdoc;
    private long dnumtim;
    private String dpunexp;
    private String dserienum;
    private long identifier;
    private short itide;
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

    public String getDdestide() {
        return this.ddestide;
    }
    public void setDdestide ( String ddestide ) {
        this.ddestide = ddestide;
    }

    public String getDest() {
        return this.dest;
    }
    public void setDest ( String dest ) {
        this.dest = dest;
    }

    public long getDeId() {
        return this.deId;
    }
    public void setDeId ( long deid ) {
        this.deId = deid;
    }

    public java.util.Date getDfeinit() {
        return this.dfeinit;
    }
    public void setDfeinit ( java.util.Date dfeinit ) {
        this.dfeinit = dfeinit;
    }

    public String getDnumdoc() {
        return this.dnumdoc;
    }
    public void setDnumdoc ( String dnumdoc ) {
        this.dnumdoc = dnumdoc;
    }

    public long getDnumtim() {
        return this.dnumtim;
    }
    public void setDnumtim ( long dnumtim ) {
        this.dnumtim = dnumtim;
    }

    public String getDpunexp() {
        return this.dpunexp;
    }
    public void setDpunexp ( String dpunexp ) {
        this.dpunexp = dpunexp;
    }

    public String getDserienum() {
        return this.dserienum;
    }
    public void setDserienum ( String dserienum ) {
        this.dserienum = dserienum;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public short getItide() {
        return this.itide;
    }
    public void setItide ( short itide ) {
        this.itide = itide;
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