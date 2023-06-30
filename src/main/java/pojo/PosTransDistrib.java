package pojo;

import java.io.Serializable;

public class PosTransDistrib implements Serializable {
    /**
	 * POJO que representa una fila de la tabla POS_TRANS_DISTRIB
	 */
	private static final long serialVersionUID = 1L;
	private double amount;
    private long cashControlId;
    private long cashRegisterId;
    private long costCenterId;
    private String createdBy;
    private java.util.Date createdOn;
    private String description;
    private long distributionCcid;
    private java.util.Date distribDate;
    private String distribType;
    private double exchangeRate;
    private long identifier;
    private long itemNumber;
    private double lcAmount;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private String readyToSend;
    private String sentToServer;
    private double taxRate;
    private long taxTypeId;
    private long transactionId;
    private long unitId;

    public double getAmount() {
        return this.amount;
    }
    public void setAmount ( double amount ) {
        this.amount = amount;
    }

    public long getCashControlId() {
        return this.cashControlId;
    }
    public void setCashControlId ( long cashcontrolid ) {
        this.cashControlId = cashcontrolid;
    }

    public long getCashRegisterId() {
        return this.cashRegisterId;
    }
    public void setCashRegisterId ( long cashregisterid ) {
        this.cashRegisterId = cashregisterid;
    }

    public long getCostCenterId() {
        return this.costCenterId;
    }
    public void setCostCenterId ( long costcenterid ) {
        this.costCenterId = costcenterid;
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

    public String getDescription() {
        return this.description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }

    public long getDistributionCcid() {
        return this.distributionCcid;
    }
    public void setDistributionCcid ( long distributionccid ) {
        this.distributionCcid = distributionccid;
    }

    public java.util.Date getDistribDate() {
        return this.distribDate;
    }
    public void setDistribDate ( java.util.Date distribdate ) {
        this.distribDate = distribdate;
    }

    public String getDistribType() {
        return this.distribType;
    }
    public void setDistribType ( String distribtype ) {
        this.distribType = distribtype;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }
    public void setExchangeRate ( double exchangerate ) {
        this.exchangeRate = exchangerate;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public long getItemNumber() {
        return this.itemNumber;
    }
    public void setItemNumber ( long itemnumber ) {
        this.itemNumber = itemnumber;
    }

    public double getLcAmount() {
        return this.lcAmount;
    }
    public void setLcAmount ( double lcamount ) {
        this.lcAmount = lcamount;
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

    public String getReadyToSend() {
        return this.readyToSend;
    }
    public void setReadyToSend ( String readytosend ) {
        this.readyToSend = readytosend;
    }

    public String getSentToServer() {
        return this.sentToServer;
    }
    public void setSentToServer ( String senttoserver ) {
        this.sentToServer = senttoserver;
    }

    public double getTaxRate() {
        return this.taxRate;
    }
    public void setTaxRate ( double taxrate ) {
        this.taxRate = taxrate;
    }

    public long getTaxTypeId() {
        return this.taxTypeId;
    }
    public void setTaxTypeId ( long taxtypeid ) {
        this.taxTypeId = taxtypeid;
    }

    public long getTransactionId() {
        return this.transactionId;
    }
    public void setTransactionId ( long transactionid ) {
        this.transactionId = transactionid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

}