package pojo;

import java.io.Serializable;

public class PosTransaction implements Serializable {
    /**
	 * POJO 	que representa una fila de la table POS_TRANSACTIONS
	 */
	private static final long serialVersionUID = 1L;
    private String address;
    private double amount;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String buildingNumber;
    private String cancelledBy;
    private java.util.Date cancelledOn;
    private long cancelHeadtllrId;
    private long cashControlId;
    private long cashId;
    private long cityId;
    private long collectionId;
    private String comments;
    private long countryId;
    private long countyId;
    private String couponsDocNo;
    private String couponsName;
    private String couponsPhone;
    private double couponsQty;
    private String createdBy;
    private java.util.Date createdOn;
    private long currencyId;
    private long customerId;
    private String customerName;
    private String customerNumber;
    private long customerSiteId;
    private double custPointsQty;
    private double discount10Amount;
    private double discount5Amount;
    private double discountAmount;
    private long ebCdmReasonId;
    private String ebControlCode;
    private long ebOperTypeId;
    private long ebSaleModeId;
    private String ebSecurityCode;
    private java.util.Date ebSendDate;
    private java.util.Date ebSignDate;
    private long ebTaxTypeId;
    private long ebTxTypeId;
    private double excemptAmount;
    private double exchangeRate;
    private String exportedFlag;
    private String eMail;
    private long foreignTrxId;
    private double globalDiscPct;
    private long identifier;
    private String identityNumber;
    private String identityType;
    private double initialPayment;
    private long installmentsQty;
    private long instDaysInterval;
    private long invoiceTypeId;
    private long invJournalId;
    private long issuePointId;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private String orgType;
    private double paidAmount;
    private String phone;
    private long pmtTermId;
    private String printedBy;
    private java.util.Date printingDate;
    private String program;
    private long rcvTransId;
    private String readyToSend;
    private double realAmount;
    private String refNumber;
    private java.util.Date remissionDate;
    private double residualAmount;
    private double roundingAmount;
    private long salesmanId;
    private String sentToServer;
    private long siteId;
    private long sleCancelId;
    private long sleCostCancId;
    private long sleCostId;
    private long sleSalesId;
    private long stampId;
    private String stampNumber;
    private long stateId;
    private double tax10Amount;
    private double tax5Amount;
    private double taxable10Amount;
    private double taxable5Amount;
    private double taxableAmount;
    private double taxAmount;
    private String taxPayerName;
    private String taxPayerNumber;
    private long transTypeId;
    private long trnBaseNumber;
    private java.util.Date trnDate;
    private String trnNumber;
    private String trxCondition;
    private long unitId;
    private String xpertVersion;

    public String getAddress() {
        return this.address;
    }
    public void setAddress ( String address ) {
        this.address = address;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount ( double amount ) {
        this.amount = amount;
    }

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

    public String getBuildingNumber() {
        return this.buildingNumber;
    }
    public void setBuildingNumber ( String buildingnumber ) {
        this.buildingNumber = buildingnumber;
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

    public long getCancelHeadtllrId() {
        return this.cancelHeadtllrId;
    }
    public void setCancelHeadtllrId ( long cancelheadtllrid ) {
        this.cancelHeadtllrId = cancelheadtllrid;
    }

    public long getCashControlId() {
        return this.cashControlId;
    }
    public void setCashControlId ( long cashcontrolid ) {
        this.cashControlId = cashcontrolid;
    }

    public long getCashId() {
        return this.cashId;
    }
    public void setCashId ( long cashid ) {
        this.cashId = cashid;
    }

    public long getCityId() {
        return this.cityId;
    }
    public void setCityId ( long cityid ) {
        this.cityId = cityid;
    }

    public long getCollectionId() {
        return this.collectionId;
    }
    public void setCollectionId ( long collectionid ) {
        this.collectionId = collectionid;
    }

    public String getComments() {
        return this.comments;
    }
    public void setComments ( String comments ) {
        this.comments = comments;
    }

    public long getCountryId() {
        return this.countryId;
    }
    public void setCountryId ( long countryid ) {
        this.countryId = countryid;
    }

    public long getCountyId() {
        return this.countyId;
    }
    public void setCountyId ( long countyid ) {
        this.countyId = countyid;
    }

    public String getCouponsDocNo() {
        return this.couponsDocNo;
    }
    public void setCouponsDocNo ( String couponsdocno ) {
        this.couponsDocNo = couponsdocno;
    }

    public String getCouponsName() {
        return this.couponsName;
    }
    public void setCouponsName ( String couponsname ) {
        this.couponsName = couponsname;
    }

    public String getCouponsPhone() {
        return this.couponsPhone;
    }
    public void setCouponsPhone ( String couponsphone ) {
        this.couponsPhone = couponsphone;
    }

    public double getCouponsQty() {
        return this.couponsQty;
    }
    public void setCouponsQty ( double couponsqty ) {
        this.couponsQty = couponsqty;
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

    public String getCustomerName() {
        return this.customerName;
    }
    public void setCustomerName ( String customername ) {
        this.customerName = customername;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }
    public void setCustomerNumber ( String customernumber ) {
        this.customerNumber = customernumber;
    }

    public long getCustomerSiteId() {
        return this.customerSiteId;
    }
    public void setCustomerSiteId ( long customersiteid ) {
        this.customerSiteId = customersiteid;
    }

    public double getCustPointsQty() {
        return this.custPointsQty;
    }
    public void setCustPointsQty ( double custpointsqty ) {
        this.custPointsQty = custpointsqty;
    }

    public double getDiscount10Amount() {
        return this.discount10Amount;
    }
    public void setDiscount10Amount ( double discount10amount ) {
        this.discount10Amount = discount10amount;
    }

    public double getDiscount5Amount() {
        return this.discount5Amount;
    }
    public void setDiscount5Amount ( double discount5amount ) {
        this.discount5Amount = discount5amount;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }
    public void setDiscountAmount ( double discountamount ) {
        this.discountAmount = discountamount;
    }

    public long getEbCdmReasonId() {
        return this.ebCdmReasonId;
    }
    public void setEbCdmReasonId ( long ebcdmreasonid ) {
        this.ebCdmReasonId = ebcdmreasonid;
    }

    public String getEbControlCode() {
        return this.ebControlCode;
    }
    public void setEbControlCode ( String ebcontrolcode ) {
        this.ebControlCode = ebcontrolcode;
    }

    public long getEbOperTypeId() {
        return this.ebOperTypeId;
    }
    public void setEbOperTypeId ( long ebopertypeid ) {
        this.ebOperTypeId = ebopertypeid;
    }

    public long getEbSaleModeId() {
        return this.ebSaleModeId;
    }
    public void setEbSaleModeId ( long ebsalemodeid ) {
        this.ebSaleModeId = ebsalemodeid;
    }

    public String getEbSecurityCode() {
        return this.ebSecurityCode;
    }
    public void setEbSecurityCode ( String ebsecuritycode ) {
        this.ebSecurityCode = ebsecuritycode;
    }

    public java.util.Date getEbSendDate() {
        return this.ebSendDate;
    }
    public void setEbSendDate ( java.util.Date ebsenddate ) {
        this.ebSendDate = ebsenddate;
    }

    public java.util.Date getEbSignDate() {
        return this.ebSignDate;
    }
    public void setEbSignDate ( java.util.Date ebsigndate ) {
        this.ebSignDate = ebsigndate;
    }

    public long getEbTaxTypeId() {
        return this.ebTaxTypeId;
    }
    public void setEbTaxTypeId ( long ebtaxtypeid ) {
        this.ebTaxTypeId = ebtaxtypeid;
    }

    public long getEbTxTypeId() {
        return this.ebTxTypeId;
    }
    public void setEbTxTypeId ( long ebtxtypeid ) {
        this.ebTxTypeId = ebtxtypeid;
    }

    public double getExcemptAmount() {
        return this.excemptAmount;
    }
    public void setExcemptAmount ( double excemptamount ) {
        this.excemptAmount = excemptamount;
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

    public String getEMail() {
        return this.eMail;
    }
    public void setEMail ( String email ) {
        this.eMail = email;
    }

    public long getForeignTrxId() {
        return this.foreignTrxId;
    }
    public void setForeignTrxId ( long foreigntrxid ) {
        this.foreignTrxId = foreigntrxid;
    }

    public double getGlobalDiscPct() {
        return this.globalDiscPct;
    }
    public void setGlobalDiscPct ( double globaldiscpct ) {
        this.globalDiscPct = globaldiscpct;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public String getIdentityNumber() {
        return this.identityNumber;
    }
    public void setIdentityNumber ( String identitynumber ) {
        this.identityNumber = identitynumber;
    }

    public String getIdentityType() {
        return this.identityType;
    }
    public void setIdentityType ( String identitytype ) {
        this.identityType = identitytype;
    }

    public double getInitialPayment() {
        return this.initialPayment;
    }
    public void setInitialPayment ( double initialpayment ) {
        this.initialPayment = initialpayment;
    }

    public long getInstallmentsQty() {
        return this.installmentsQty;
    }
    public void setInstallmentsQty ( long installmentsqty ) {
        this.installmentsQty = installmentsqty;
    }

    public long getInstDaysInterval() {
        return this.instDaysInterval;
    }
    public void setInstDaysInterval ( long instdaysinterval ) {
        this.instDaysInterval = instdaysinterval;
    }

    public long getInvoiceTypeId() {
        return this.invoiceTypeId;
    }
    public void setInvoiceTypeId ( long invoicetypeid ) {
        this.invoiceTypeId = invoicetypeid;
    }

    public long getInvJournalId() {
        return this.invJournalId;
    }
    public void setInvJournalId ( long invjournalid ) {
        this.invJournalId = invjournalid;
    }

    public long getIssuePointId() {
        return this.issuePointId;
    }
    public void setIssuePointId ( long issuepointid ) {
        this.issuePointId = issuepointid;
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

    public String getOrgType() {
        return this.orgType;
    }
    public void setOrgType ( String orgtype ) {
        this.orgType = orgtype;
    }

    public double getPaidAmount() {
        return this.paidAmount;
    }
    public void setPaidAmount ( double paidamount ) {
        this.paidAmount = paidamount;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setPhone ( String phone ) {
        this.phone = phone;
    }

    public long getPmtTermId() {
        return this.pmtTermId;
    }
    public void setPmtTermId ( long pmttermid ) {
        this.pmtTermId = pmttermid;
    }

    public String getPrintedBy() {
        return this.printedBy;
    }
    public void setPrintedBy ( String printedby ) {
        this.printedBy = printedby;
    }

    public java.util.Date getPrintingDate() {
        return this.printingDate;
    }
    public void setPrintingDate ( java.util.Date printingdate ) {
        this.printingDate = printingdate;
    }

    public String getProgram() {
        return this.program;
    }
    public void setProgram ( String program ) {
        this.program = program;
    }

    public long getRcvTransId() {
        return this.rcvTransId;
    }
    public void setRcvTransId ( long rcvtransid ) {
        this.rcvTransId = rcvtransid;
    }

    public String getReadyToSend() {
        return this.readyToSend;
    }
    public void setReadyToSend ( String readytosend ) {
        this.readyToSend = readytosend;
    }

    public double getRealAmount() {
        return this.realAmount;
    }
    public void setRealAmount ( double realamount ) {
        this.realAmount = realamount;
    }

    public String getRefNumber() {
        return this.refNumber;
    }
    public void setRefNumber ( String refnumber ) {
        this.refNumber = refnumber;
    }

    public java.util.Date getRemissionDate() {
        return this.remissionDate;
    }
    public void setRemissionDate ( java.util.Date remissiondate ) {
        this.remissionDate = remissiondate;
    }

    public double getResidualAmount() {
        return this.residualAmount;
    }
    public void setResidualAmount ( double residualamount ) {
        this.residualAmount = residualamount;
    }

    public double getRoundingAmount() {
        return this.roundingAmount;
    }
    public void setRoundingAmount ( double roundingamount ) {
        this.roundingAmount = roundingamount;
    }

    public long getSalesmanId() {
        return this.salesmanId;
    }
    public void setSalesmanId ( long salesmanid ) {
        this.salesmanId = salesmanid;
    }

    public String getSentToServer() {
        return this.sentToServer;
    }
    public void setSentToServer ( String senttoserver ) {
        this.sentToServer = senttoserver;
    }

    public long getSiteId() {
        return this.siteId;
    }
    public void setSiteId ( long siteid ) {
        this.siteId = siteid;
    }

    public long getSleCancelId() {
        return this.sleCancelId;
    }
    public void setSleCancelId ( long slecancelid ) {
        this.sleCancelId = slecancelid;
    }

    public long getSleCostCancId() {
        return this.sleCostCancId;
    }
    public void setSleCostCancId ( long slecostcancid ) {
        this.sleCostCancId = slecostcancid;
    }

    public long getSleCostId() {
        return this.sleCostId;
    }
    public void setSleCostId ( long slecostid ) {
        this.sleCostId = slecostid;
    }

    public long getSleSalesId() {
        return this.sleSalesId;
    }
    public void setSleSalesId ( long slesalesid ) {
        this.sleSalesId = slesalesid;
    }

    public long getStampId() {
        return this.stampId;
    }
    public void setStampId ( long stampid ) {
        this.stampId = stampid;
    }

    public String getStampNumber() {
        return this.stampNumber;
    }
    public void setStampNumber ( String stampnumber ) {
        this.stampNumber = stampnumber;
    }

    public long getStateId() {
        return this.stateId;
    }
    public void setStateId ( long stateid ) {
        this.stateId = stateid;
    }

    public double getTax10Amount() {
        return this.tax10Amount;
    }
    public void setTax10Amount ( double tax10amount ) {
        this.tax10Amount = tax10amount;
    }

    public double getTax5Amount() {
        return this.tax5Amount;
    }
    public void setTax5Amount ( double tax5amount ) {
        this.tax5Amount = tax5amount;
    }

    public double getTaxable10Amount() {
        return this.taxable10Amount;
    }
    public void setTaxable10Amount ( double taxable10amount ) {
        this.taxable10Amount = taxable10amount;
    }

    public double getTaxable5Amount() {
        return this.taxable5Amount;
    }
    public void setTaxable5Amount ( double taxable5amount ) {
        this.taxable5Amount = taxable5amount;
    }

    public double getTaxableAmount() {
        return this.taxableAmount;
    }
    public void setTaxableAmount ( double taxableamount ) {
        this.taxableAmount = taxableamount;
    }

    public double getTaxAmount() {
        return this.taxAmount;
    }
    public void setTaxAmount ( double taxamount ) {
        this.taxAmount = taxamount;
    }

    public String getTaxPayerName() {
        return this.taxPayerName;
    }
    public void setTaxPayerName ( String taxpayername ) {
        this.taxPayerName = taxpayername;
    }

    public String getTaxPayerNumber() {
        return this.taxPayerNumber;
    }
    public void setTaxPayerNumber ( String taxpayernumber ) {
        this.taxPayerNumber = taxpayernumber;
    }

    public long getTransTypeId() {
        return this.transTypeId;
    }
    public void setTransTypeId ( long transtypeid ) {
        this.transTypeId = transtypeid;
    }

    public long getTrnBaseNumber() {
        return this.trnBaseNumber;
    }
    public void setTrnBaseNumber ( long trnbasenumber ) {
        this.trnBaseNumber = trnbasenumber;
    }

    public java.util.Date getTrnDate() {
        return this.trnDate;
    }
    public void setTrnDate ( java.util.Date trndate ) {
        this.trnDate = trndate;
    }

    public String getTrnNumber() {
        return this.trnNumber;
    }
    public void setTrnNumber ( String trnnumber ) {
        this.trnNumber = trnnumber;
    }

    public String getTrxCondition() {
        return this.trxCondition;
    }
    public void setTrxCondition ( String trxcondition ) {
        this.trxCondition = trxcondition;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

    public String getXpertVersion() {
        return this.xpertVersion;
    }
    public void setXpertVersion ( String xpertversion ) {
        this.xpertVersion = xpertversion;
    }

}