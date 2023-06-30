package pojo;

import java.io.Serializable;

public class EbGvalorrestaitem implements Serializable {
    /**
	 * POJO que representa una fila de la tabla EB_GVALORRESTAITEM
	 * @author jotaCe
	 */
	private static final long serialVersionUID = 1L;
	private String createdBy;
    private java.util.Date createdOn;
    private double dantglopreuniit;
    private double dantpreuniit;
    private double ddescgloitem;
    private double ddescitem;
    private double dporcdesit;
    private double dtotopegs;
    private double dtotopeitem;
    private long identifier;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private long unitId;
    private long valoritemId;

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

    public double getDantglopreuniit() {
        return this.dantglopreuniit;
    }
    public void setDantglopreuniit ( double dantglopreuniit ) {
        this.dantglopreuniit = dantglopreuniit;
    }

    public double getDantpreuniit() {
        return this.dantpreuniit;
    }
    public void setDantpreuniit ( double dantpreuniit ) {
        this.dantpreuniit = dantpreuniit;
    }

    public double getDdescgloitem() {
        return this.ddescgloitem;
    }
    public void setDdescgloitem ( double ddescgloitem ) {
        this.ddescgloitem = ddescgloitem;
    }

    public double getDdescitem() {
        return this.ddescitem;
    }
    public void setDdescitem ( double ddescitem ) {
        this.ddescitem = ddescitem;
    }

    public double getDporcdesit() {
        return this.dporcdesit;
    }
    public void setDporcdesit ( double dporcdesit ) {
        this.dporcdesit = dporcdesit;
    }

    public double getDtotopegs() {
        return this.dtotopegs;
    }
    public void setDtotopegs ( double dtotopegs ) {
        this.dtotopegs = dtotopegs;
    }

    public double getDtotopeitem() {
        return this.dtotopeitem;
    }
    public void setDtotopeitem ( double dtotopeitem ) {
        this.dtotopeitem = dtotopeitem;
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

    public long getValoritemId() {
        return this.valoritemId;
    }
    public void setValoritemId ( long valoritemid ) {
        this.valoritemId = valoritemid;
    }

}