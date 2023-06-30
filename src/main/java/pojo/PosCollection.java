package pojo;

import java.io.Serializable;

public class PosCollection implements Serializable {
    /**
	 * POJO que representa una fila de la tabla POS_COLLECTIONS
	 */
	private static final long serialVersionUID = 1L;
	private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String cancelledBy;
    private java.util.Date cancelledOn;
    private long cashierId;
    private double cashAmount;
    private long cashControlId;
    private long cashRegisterId;
    private double changeAmount;
    private double changeComplAmount;
    private double changeDonationAmt;
    private double checksAmount;
    private String collectionNo;
    private java.util.Date collDate;
    private long collTypeId;
    private String createdBy;
    private java.util.Date createdOn;
    private long currencyId;
    private long customerId;
    private long customerSiteId;
    private double exchangeRate;
    private String exportedFlag;
    private long foreignItemId;
    private long foreignTrxId;
    private long identifier;
    private double invoiceAmount;
    private long invoiceId;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private double othersAmount;
    private long payrollTrxId;
    private double realAmount;
    private double residualDiscount;
    private double roundedChgeAmount;
    private double roundingAmount;
    private String sendToServer;
    private long siteId;
    private long unitId;
    private long workShiftId;
    private String xpertVersion;

    public String getAttribute1() {
        return this.attribute1;
    }
    public void setAttribute1 ( String attribute1 ) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return this.attribute2;
    }
    public void setAttribute2 ( String attribute2 ) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return this.attribute3;
    }
    public void setAttribute3 ( String attribute3 ) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return this.attribute4;
    }
    public void setAttribute4 ( String attribute4 ) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return this.attribute5;
    }
    public void setAttribute5 ( String attribute5 ) {
        this.attribute5 = attribute5;
    }

    public String getCancelledBy() {
        return this.cancelledBy;
    }
    public void setCancelledBy ( String cancelledby ) {
        this.cancelledBy = cancelledby;
    }

    public java.util.Date getCancelledOn() {
        return this.cancelledOn;
    }
    public void setCancelledOn ( java.util.Date cancelledon ) {
        this.cancelledOn = cancelledon;
    }

    public long getCashierId() {
        return this.cashierId;
    }
    public void setCashierId ( long cashierid ) {
        this.cashierId = cashierid;
    }

    public double getCashAmount() {
        return this.cashAmount;
    }
    public void setCashAmount ( double cashamount ) {
        this.cashAmount = cashamount;
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

    public double getChangeAmount() {
        return this.changeAmount;
    }
    public void setChangeAmount ( double changeamount ) {
        this.changeAmount = changeamount;
    }

    public double getChangeComplAmount() {
        return this.changeComplAmount;
    }
    public void setChangeComplAmount ( double changecomplamount ) {
        this.changeComplAmount = changecomplamount;
    }

    public double getChangeDonationAmt() {
        return this.changeDonationAmt;
    }
    public void setChangeDonationAmt ( double changedonationamt ) {
        this.changeDonationAmt = changedonationamt;
    }

    public double getChecksAmount() {
        return this.checksAmount;
    }
    public void setChecksAmount ( double checksamount ) {
        this.checksAmount = checksamount;
    }

    public String getCollectionNo() {
        return this.collectionNo;
    }
    public void setCollectionNo ( String collectionno ) {
        this.collectionNo = collectionno;
    }

    public java.util.Date getCollDate() {
        return this.collDate;
    }
    public void setCollDate ( java.util.Date colldate ) {
        this.collDate = colldate;
    }

    public long getCollTypeId() {
        return this.collTypeId;
    }
    public void setCollTypeId ( long colltypeid ) {
        this.collTypeId = colltypeid;
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

    public long getCurrencyId() {
        return this.currencyId;
    }
    public void setCurrencyId ( long currencyid ) {
        this.currencyId = currencyid;
    }

    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId ( long customerid ) {
        this.customerId = customerid;
    }

    public long getCustomerSiteId() {
        return this.customerSiteId;
    }
    public void setCustomerSiteId ( long customersiteid ) {
        this.customerSiteId = customersiteid;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }
    public void setExchangeRate ( double exchangerate ) {
        this.exchangeRate = exchangerate;
    }

    public String getExportedFlag() {
        return this.exportedFlag;
    }
    public void setExportedFlag ( String exportedflag ) {
        this.exportedFlag = exportedflag;
    }

    public long getForeignItemId() {
        return this.foreignItemId;
    }
    public void setForeignItemId ( long foreignitemid ) {
        this.foreignItemId = foreignitemid;
    }

    public long getForeignTrxId() {
        return this.foreignTrxId;
    }
    public void setForeignTrxId ( long foreigntrxid ) {
        this.foreignTrxId = foreigntrxid;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public double getInvoiceAmount() {
        return this.invoiceAmount;
    }
    public void setInvoiceAmount ( double invoiceamount ) {
        this.invoiceAmount = invoiceamount;
    }

    public long getInvoiceId() {
        return this.invoiceId;
    }
    public void setInvoiceId ( long invoiceid ) {
        this.invoiceId = invoiceid;
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

    public double getOthersAmount() {
        return this.othersAmount;
    }
    public void setOthersAmount ( double othersamount ) {
        this.othersAmount = othersamount;
    }

    public long getPayrollTrxId() {
        return this.payrollTrxId;
    }
    public void setPayrollTrxId ( long payrolltrxid ) {
        this.payrollTrxId = payrolltrxid;
    }

    public double getRealAmount() {
        return this.realAmount;
    }
    public void setRealAmount ( double realamount ) {
        this.realAmount = realamount;
    }

    public double getResidualDiscount() {
        return this.residualDiscount;
    }
    public void setResidualDiscount ( double residualdiscount ) {
        this.residualDiscount = residualdiscount;
    }

    public double getRoundedChgeAmount() {
        return this.roundedChgeAmount;
    }
    public void setRoundedChgeAmount ( double roundedchgeamount ) {
        this.roundedChgeAmount = roundedchgeamount;
    }

    public double getRoundingAmount() {
        return this.roundingAmount;
    }
    public void setRoundingAmount ( double roundingamount ) {
        this.roundingAmount = roundingamount;
    }

    public String getSendToServer() {
        return this.sendToServer;
    }
    public void setSendToServer ( String sendtoserver ) {
        this.sendToServer = sendtoserver;
    }

    public long getSiteId() {
        return this.siteId;
    }
    public void setSiteId ( long siteid ) {
        this.siteId = siteid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

    public long getWorkShiftId() {
        return this.workShiftId;
    }
    public void setWorkShiftId ( long workshiftid ) {
        this.workShiftId = workshiftid;
    }

    public String getXpertVersion() {
        return this.xpertVersion;
    }
    public void setXpertVersion ( String xpertversion ) {
        this.xpertVersion = xpertversion;
    }

}