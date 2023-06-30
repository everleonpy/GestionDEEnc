package pojo;

import java.io.Serializable;

public class RcvCustomerTrx implements Serializable {
    private String address1;
    private String address2;
    private String address3;
    private double amount;
    private double appliedAmount;
    private double applyingAmount;
    private long archingSheetId;
    private String areaCode;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String cancellatedBy;
    private java.util.Date cancellatedOn;
    private String cancelComments;
    private long cancelJrnlId;
    private long cancelReasonId;
    private long cashControlId;
    private long cashRegisterId;
    private double collectionsAmount;
    private String collectGroup;
    private String collectMethod;
    private String comments;
    private String costCenter;
    private long costCenterId;
    private String createdBy;
    private java.util.Date createdOn;
    private long currencyId;
    private long customerCcid;
    private long customerId;
    private String customerName;
	private String identityNumber;
    private String identityType;
	private long custSiteId;
    private double discountAmt;
    private double distributionAmt;
    private java.util.Date dueDate;
    private long dueDayOfMonth;
    private double excemptAmount;
    private double exchangeRate;
    private long expensesConceptId;
    private long fiscalPeriodId;
    private long fiscalYearId;
    private long gainedCouponsQty;
    private long gainedPointsQty;
    private long gleJournalId;
    private long identifier;
    private long installmentsQty;
    private long instDaysInterval;
    private long invoiceId;
    private long invoiceStampId;
    private long invIntfaceId;
    private long issueJrnlId;
    private long issuePointId;
    private java.util.Date lastPaymentDate;
    private double lcAmount;
    private double lcAppliedAmount;
    private double lcApplyingAmount;
    private double lcCollectAmount;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private String phone1;
    private String phone2;
    private String phone3;
    private long posTransactionId;
    private long pricesListId;
    private String printedBy;
    private java.util.Date printingDate;
    private String program;
    private long projectId;
    private long raCancelId;
    private long raCollectId;
    private long raJournalId;
    private String receivablesType;
    private String refNumber;
    private double residualAmount;
    private long salesmanId;
    private long salesOrderId;
    private long sectionId;
    private long siteId;
    private long sleCancelId;
    private long sleCostCancId;
    private long sleCostId;
    private long sleSalesId;
    private long stampId;
    private String status;
    private long taskId;
    private double tax10Amt;
    private double tax5Amt;
    private double taxable10Amt;
    private double taxable5Amt;
    private double taxableAmount;
    private double taxAmount;
    private String taxPayerName;
    private String taxPayerNumber;
    private String trxCondition;
    private long trxVersion;
    private long txBaseNumber;
    private java.util.Date txDate;
    private String txNumber;
    private long txTypeId;
    private long unitId;
    private long warehouseId;
    // atributos derivados
    private String txTypeName;
    private String siteName;
    private String currencyname;
    private String stampNo;

    public String getAddress1() {
        return this.address1;
    }
    public void setAddress1 ( String address1 ) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }
    public void setAddress2 ( String address2 ) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return this.address3;
    }
    public void setAddress3 ( String address3 ) {
        this.address3 = address3;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount ( double amount ) {
        this.amount = amount;
    }

    public double getAppliedAmount() {
        return this.appliedAmount;
    }
    public void setAppliedAmount ( double appliedamount ) {
        this.appliedAmount = appliedamount;
    }

    public double getApplyingAmount() {
        return this.applyingAmount;
    }
    public void setApplyingAmount ( double applyingamount ) {
        this.applyingAmount = applyingamount;
    }

    public long getArchingSheetId() {
        return this.archingSheetId;
    }
    public void setArchingSheetId ( long archingsheetid ) {
        this.archingSheetId = archingsheetid;
    }

    public String getAreaCode() {
        return this.areaCode;
    }
    public void setAreaCode ( String areacode ) {
        this.areaCode = areacode;
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

    public String getCancellatedBy() {
        return this.cancellatedBy;
    }
    public void setCancellatedBy ( String cancellatedby ) {
        this.cancellatedBy = cancellatedby;
    }

    public java.util.Date getCancellatedOn() {
        return this.cancellatedOn;
    }
    public void setCancellatedOn ( java.util.Date cancellatedon ) {
        this.cancellatedOn = cancellatedon;
    }

    public String getCancelComments() {
        return this.cancelComments;
    }
    public void setCancelComments ( String cancelcomments ) {
        this.cancelComments = cancelcomments;
    }

    public long getCancelJrnlId() {
        return this.cancelJrnlId;
    }
    public void setCancelJrnlId ( long canceljrnlid ) {
        this.cancelJrnlId = canceljrnlid;
    }

    public long getCancelReasonId() {
        return this.cancelReasonId;
    }
    public void setCancelReasonId ( long cancelreasonid ) {
        this.cancelReasonId = cancelreasonid;
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

    public double getCollectionsAmount() {
        return this.collectionsAmount;
    }
    public void setCollectionsAmount ( double collectionsamount ) {
        this.collectionsAmount = collectionsamount;
    }

    public String getCollectGroup() {
        return this.collectGroup;
    }
    public void setCollectGroup ( String collectgroup ) {
        this.collectGroup = collectgroup;
    }

    public String getCollectMethod() {
        return this.collectMethod;
    }
    public void setCollectMethod ( String collectmethod ) {
        this.collectMethod = collectmethod;
    }

    public String getComments() {
        return this.comments;
    }
    public void setComments ( String comments ) {
        this.comments = comments;
    }

    public String getCostCenter() {
        return this.costCenter;
    }
    public void setCostCenter ( String costcenter ) {
        this.costCenter = costcenter;
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

    public long getCurrencyId() {
        return this.currencyId;
    }
    public void setCurrencyId ( long currencyid ) {
        this.currencyId = currencyid;
    }

    public long getCustomerCcid() {
        return this.customerCcid;
    }
    public void setCustomerCcid ( long customerccid ) {
        this.customerCcid = customerccid;
    }

    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId ( long customerid ) {
        this.customerId = customerid;
    }

    public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
    
    public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	
    public long getCustSiteId() {
        return this.custSiteId;
    }
    public void setCustSiteId ( long custsiteid ) {
        this.custSiteId = custsiteid;
    }

    public double getDiscountAmt() {
        return this.discountAmt;
    }
    public void setDiscountAmt ( double discountamt ) {
        this.discountAmt = discountamt;
    }

    public double getDistributionAmt() {
        return this.distributionAmt;
    }
    public void setDistributionAmt ( double distributionamt ) {
        this.distributionAmt = distributionamt;
    }

    public java.util.Date getDueDate() {
        return this.dueDate;
    }
    public void setDueDate ( java.util.Date duedate ) {
        this.dueDate = duedate;
    }

    public long getDueDayOfMonth() {
        return this.dueDayOfMonth;
    }
    public void setDueDayOfMonth ( long duedayofmonth ) {
        this.dueDayOfMonth = duedayofmonth;
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

    public long getExpensesConceptId() {
        return this.expensesConceptId;
    }
    public void setExpensesConceptId ( long expensesconceptid ) {
        this.expensesConceptId = expensesconceptid;
    }

    public long getFiscalPeriodId() {
        return this.fiscalPeriodId;
    }
    public void setFiscalPeriodId ( long fiscalperiodid ) {
        this.fiscalPeriodId = fiscalperiodid;
    }

    public long getFiscalYearId() {
        return this.fiscalYearId;
    }
    public void setFiscalYearId ( long fiscalyearid ) {
        this.fiscalYearId = fiscalyearid;
    }

    public long getGainedCouponsQty() {
        return this.gainedCouponsQty;
    }
    public void setGainedCouponsQty ( long gainedcouponsqty ) {
        this.gainedCouponsQty = gainedcouponsqty;
    }

    public long getGainedPointsQty() {
        return this.gainedPointsQty;
    }
    public void setGainedPointsQty ( long gainedpointsqty ) {
        this.gainedPointsQty = gainedpointsqty;
    }

    public long getGleJournalId() {
        return this.gleJournalId;
    }
    public void setGleJournalId ( long glejournalid ) {
        this.gleJournalId = glejournalid;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
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

    public long getInvoiceId() {
        return this.invoiceId;
    }
    public void setInvoiceId ( long invoiceid ) {
        this.invoiceId = invoiceid;
    }

    public long getInvoiceStampId() {
        return this.invoiceStampId;
    }
    public void setInvoiceStampId ( long invoicestampid ) {
        this.invoiceStampId = invoicestampid;
    }

    public long getInvIntfaceId() {
        return this.invIntfaceId;
    }
    public void setInvIntfaceId ( long invintfaceid ) {
        this.invIntfaceId = invintfaceid;
    }

    public long getIssueJrnlId() {
        return this.issueJrnlId;
    }
    public void setIssueJrnlId ( long issuejrnlid ) {
        this.issueJrnlId = issuejrnlid;
    }

    public long getIssuePointId() {
        return this.issuePointId;
    }
    public void setIssuePointId ( long issuepointid ) {
        this.issuePointId = issuepointid;
    }

    public java.util.Date getLastPaymentDate() {
        return this.lastPaymentDate;
    }
    public void setLastPaymentDate ( java.util.Date lastpaymentdate ) {
        this.lastPaymentDate = lastpaymentdate;
    }

    public double getLcAmount() {
        return this.lcAmount;
    }
    public void setLcAmount ( double lcamount ) {
        this.lcAmount = lcamount;
    }

    public double getLcAppliedAmount() {
        return this.lcAppliedAmount;
    }
    public void setLcAppliedAmount ( double lcappliedamount ) {
        this.lcAppliedAmount = lcappliedamount;
    }

    public double getLcApplyingAmount() {
        return this.lcApplyingAmount;
    }
    public void setLcApplyingAmount ( double lcapplyingamount ) {
        this.lcApplyingAmount = lcapplyingamount;
    }

    public double getLcCollectAmount() {
        return this.lcCollectAmount;
    }
    public void setLcCollectAmount ( double lccollectamount ) {
        this.lcCollectAmount = lccollectamount;
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

    public String getPhone1() {
        return this.phone1;
    }
    public void setPhone1 ( String phone1 ) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return this.phone2;
    }
    public void setPhone2 ( String phone2 ) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return this.phone3;
    }
    public void setPhone3 ( String phone3 ) {
        this.phone3 = phone3;
    }

    public long getPosTransactionId() {
        return this.posTransactionId;
    }
    public void setPosTransactionId ( long postransactionid ) {
        this.posTransactionId = postransactionid;
    }

    public long getPricesListId() {
        return this.pricesListId;
    }
    public void setPricesListId ( long priceslistid ) {
        this.pricesListId = priceslistid;
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

    public long getProjectId() {
        return this.projectId;
    }
    public void setProjectId ( long projectid ) {
        this.projectId = projectid;
    }

    public long getRaCancelId() {
        return this.raCancelId;
    }
    public void setRaCancelId ( long racancelid ) {
        this.raCancelId = racancelid;
    }

    public long getRaCollectId() {
        return this.raCollectId;
    }
    public void setRaCollectId ( long racollectid ) {
        this.raCollectId = racollectid;
    }

    public long getRaJournalId() {
        return this.raJournalId;
    }
    public void setRaJournalId ( long rajournalid ) {
        this.raJournalId = rajournalid;
    }

    public String getReceivablesType() {
        return this.receivablesType;
    }
    public void setReceivablesType ( String receivablestype ) {
        this.receivablesType = receivablestype;
    }

    public String getRefNumber() {
        return this.refNumber;
    }
    public void setRefNumber ( String refnumber ) {
        this.refNumber = refnumber;
    }

    public double getResidualAmount() {
        return this.residualAmount;
    }
    public void setResidualAmount ( double residualamount ) {
        this.residualAmount = residualamount;
    }

    public long getSalesmanId() {
        return this.salesmanId;
    }
    public void setSalesmanId ( long salesmanid ) {
        this.salesmanId = salesmanid;
    }

    public long getSalesOrderId() {
        return this.salesOrderId;
    }
    public void setSalesOrderId ( long salesorderid ) {
        this.salesOrderId = salesorderid;
    }

    public long getSectionId() {
        return this.sectionId;
    }
    public void setSectionId ( long sectionid ) {
        this.sectionId = sectionid;
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

    public String getStatus() {
        return this.status;
    }
    public void setStatus ( String status ) {
        this.status = status;
    }

    public long getTaskId() {
        return this.taskId;
    }
    public void setTaskId ( long taskid ) {
        this.taskId = taskid;
    }

    public double getTax10Amt() {
        return this.tax10Amt;
    }
    public void setTax10Amt ( double tax10amt ) {
        this.tax10Amt = tax10amt;
    }

    public double getTax5Amt() {
        return this.tax5Amt;
    }
    public void setTax5Amt ( double tax5amt ) {
        this.tax5Amt = tax5amt;
    }

    public double getTaxable10Amt() {
        return this.taxable10Amt;
    }
    public void setTaxable10Amt ( double taxable10amt ) {
        this.taxable10Amt = taxable10amt;
    }

    public double getTaxable5Amt() {
        return this.taxable5Amt;
    }
    public void setTaxable5Amt ( double taxable5amt ) {
        this.taxable5Amt = taxable5amt;
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

    public String getTrxCondition() {
        return this.trxCondition;
    }
    public void setTrxCondition ( String trxcondition ) {
        this.trxCondition = trxcondition;
    }

    public long getTrxVersion() {
        return this.trxVersion;
    }
    public void setTrxVersion ( long trxversion ) {
        this.trxVersion = trxversion;
    }

    public long getTxBaseNumber() {
        return this.txBaseNumber;
    }
    public void setTxBaseNumber ( long txbasenumber ) {
        this.txBaseNumber = txbasenumber;
    }

    public java.util.Date getTxDate() {
        return this.txDate;
    }
    public void setTxDate ( java.util.Date txdate ) {
        this.txDate = txdate;
    }

    public String getTxNumber() {
        return this.txNumber;
    }
    public void setTxNumber ( String txnumber ) {
        this.txNumber = txnumber;
    }

    public long getTxTypeId() {
        return this.txTypeId;
    }
    public void setTxTypeId ( long txtypeid ) {
        this.txTypeId = txtypeid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

    public long getWarehouseId() {
        return this.warehouseId;
    }
    public void setWarehouseId ( long warehouseid ) {
        this.warehouseId = warehouseid;
    }
	public String getTxTypeName() {
		return txTypeName;
	}
	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getCurrencyname() {
		return currencyname;
	}
	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}
	public String getStampNo() {
		return stampNo;
	}
	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}

}