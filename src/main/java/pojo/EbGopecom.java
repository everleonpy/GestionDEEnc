package pojo;

import java.io.Serializable;

public class EbGopecom implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GOPECOM
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String cmoneope;
    private String createdBy;
    private java.util.Date createdOn;
    private long datgralopeId;
    private short dcondticam;
    private String ddescondant;
    private String ddesmoneope;
    private String ddestimp;
    private String ddestiptra;
    private double dticam;
    private short icondant;
    private long identifier;
    private short itimp;
    private short itiptra;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long unitId;

    public String getCmoneope() {
        return this.cmoneope;
    }
    public void setCmoneope ( String cmoneope ) {
        this.cmoneope = cmoneope;
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

    public long getDatgralopeId() {
        return this.datgralopeId;
    }
    public void setDatgralopeId ( long datgralopeid ) {
        this.datgralopeId = datgralopeid;
    }

    public short getDcondticam() {
        return this.dcondticam;
    }
    public void setDcondticam ( short dcondticam ) {
        this.dcondticam = dcondticam;
    }

    public String getDdescondant() {
        return this.ddescondant;
    }
    public void setDdescondant ( String ddescondant ) {
        this.ddescondant = ddescondant;
    }

    public String getDdesmoneope() {
        return this.ddesmoneope;
    }
    public void setDdesmoneope ( String ddesmoneope ) {
        this.ddesmoneope = ddesmoneope;
    }

    public String getDdestimp() {
        return this.ddestimp;
    }
    public void setDdestimp ( String ddestimp ) {
        this.ddestimp = ddestimp;
    }

    public String getDdestiptra() {
        return this.ddestiptra;
    }
    public void setDdestiptra ( String ddestiptra ) {
        this.ddestiptra = ddestiptra;
    }

    public double getDticam() {
        return this.dticam;
    }
    public void setDticam ( double dticam ) {
        this.dticam = dticam;
    }

    public short getIcondant() {
        return this.icondant;
    }
    public void setIcondant ( short icondant ) {
        this.icondant = icondant;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public short getItimp() {
        return this.itimp;
    }
    public void setItimp ( short itimp ) {
        this.itimp = itimp;
    }

    public short getItiptra() {
        return this.itiptra;
    }
    public void setItiptra ( short itiptra ) {
        this.itiptra = itiptra;
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