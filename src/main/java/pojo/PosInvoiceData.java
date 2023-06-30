package pojo;

public class PosInvoiceData {
	private String orgName;
	private String tradingName;
	private String orgTaxNumber;
	private String orgAddress;
	private String orgPhone;	
	private String [] activList;
	private java.util.Date txDate;
	private String stampNo;
	private java.util.Date stampFromDate;
	private java.util.Date stampToDate;
	private java.util.Date stampExpDate;
	private java.util.Date stampAuthDate;
	private String cashierName;
	private String siteName;
	private String siteAddress;
	private String sitePhone;
	private String siteBenefName;
	private String cashNumber;
	private String cashName;
	private String issuePointCode;
	private String issuePointName;
	private String txTypeName;
	private String invCondition;
	private short paymentDays;
	private String trnNumber;
	private long currencyId;
	private String currencyCode;
	private int ccyDecimals;
	private long orgId;
	private long unitId;
	private long invoiceId;
	private long controlId;
	private long cashId;
	//
	private double invoiceAmount;
	private double residualAmount;
	private double exemptAmount;
	private double taxableAmount;
	private double taxable10Amount;
	private double taxable5Amount;
	private double taxAmount;
	private double tax10Amount;
	private double tax5Amount;
	private double discountAmount;
	private double discount10Amount;
	private double discount5Amount;
	private int itemsQuantity;
	//
	private String customerNumber;
	private String customerName;
	private String taxNumber;
	private String taxName;
	private String qrCode;
	private String ebControlCode;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTradingName() {
		return tradingName;
	}
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}
	public String getOrgTaxNumber() {
		return orgTaxNumber;
	}
	public void setOrgTaxNumber(String orgTaxNumber) {
		this.orgTaxNumber = orgTaxNumber;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getOrgPhone() {
		return orgPhone;
	}
	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}
	public String[] getActivList() {
		return activList;
	}
	public void setActivList(String[] activList) {
		this.activList = activList;
	}
	public java.util.Date getTxDate() {
		return txDate;
	}
	public void setTxDate(java.util.Date txDate) {
		this.txDate = txDate;
	}
	public String getStampNo() {
		return stampNo;
	}
	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}
	public java.util.Date getStampFromDate() {
		return stampFromDate;
	}
	public void setStampFromDate(java.util.Date stampFromDate) {
		this.stampFromDate = stampFromDate;
	}
	public java.util.Date getStampToDate() {
		return stampToDate;
	}
	public void setStampToDate(java.util.Date stampToDate) {
		this.stampToDate = stampToDate;
	}
	public java.util.Date getStampExpDate() {
		return stampExpDate;
	}
	public void setStampExpDate(java.util.Date stampExpDate) {
		this.stampExpDate = stampExpDate;
	}
	public java.util.Date getStampAuthDate() {
		return stampAuthDate;
	}
	public void setStampAuthDate(java.util.Date stampAuthDate) {
		this.stampAuthDate = stampAuthDate;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public String getSitePhone() {
		return sitePhone;
	}
	public void setSitePhone(String sitePhone) {
		this.sitePhone = sitePhone;
	}
	public String getSiteBenefName() {
		return siteBenefName;
	}
	public void setSiteBenefName(String siteBenefName) {
		this.siteBenefName = siteBenefName;
	}	
	public String getCashNumber() {
		return cashNumber;
	}
	public void setCashNumber(String cashNumber) {
		this.cashNumber = cashNumber;
	}
	public String getCashName() {
		return cashName;
	}
	public void setCashName(String cashName) {
		this.cashName = cashName;
	}
	public String getIssuePointCode() {
		return issuePointCode;
	}
	public void setIssuePointCode(String issuePointCode) {
		this.issuePointCode = issuePointCode;
	}
	public String getIssuePointName() {
		return issuePointName;
	}
	public void setIssuePointName(String issuePointName) {
		this.issuePointName = issuePointName;
	}
	public String getTxTypeName() {
		return txTypeName;
	}
	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}
	public String getInvCondition() {
		return invCondition;
	}
	public void setInvCondition(String invCondition) {
		this.invCondition = invCondition;
	}
	public short getPaymentDays() {
		return paymentDays;
	}
	public void setPaymentDays(short paymentDays) {
		this.paymentDays = paymentDays;
	}
	public String getTrnNumber() {
		return trnNumber;
	}
	public void setTrnNumber(String trnNumber) {
		this.trnNumber = trnNumber;
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public int getCcyDecimals() {
		return ccyDecimals;
	}
	public void setCcyDecimals(int ccyDecimals) {
		this.ccyDecimals = ccyDecimals;
	}	
	public long getOrgId() {
		return orgId;
	}
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getControlId() {
		return controlId;
	}
	public void setControlId(long controlId) {
		this.controlId = controlId;
	}
	public long getCashId() {
		return cashId;
	}
	public void setCashId(long cashId) {
		this.cashId = cashId;
	}	
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public double getResidualAmount() {
		return residualAmount;
	}
	public void setResidualAmount(double residualAmount) {
		this.residualAmount = residualAmount;
	}
	public double getExemptAmount() {
		return exemptAmount;
	}
	public void setExemptAmount(double exemptAmount) {
		this.exemptAmount = exemptAmount;
	}
	public double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public double getTaxable10Amount() {
		return taxable10Amount;
	}
	public void setTaxable10Amount(double taxable10Amount) {
		this.taxable10Amount = taxable10Amount;
	}
	public double getTaxable5Amount() {
		return taxable5Amount;
	}
	public void setTaxable5Amount(double taxable5Amount) {
		this.taxable5Amount = taxable5Amount;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getTax10Amount() {
		return tax10Amount;
	}
	public void setTax10Amount(double tax10Amount) {
		this.tax10Amount = tax10Amount;
	}
	public double getTax5Amount() {
		return tax5Amount;
	}
	public void setTax5Amount(double tax5Amount) {
		this.tax5Amount = tax5Amount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getDiscount10Amount() {
		return discount10Amount;
	}
	public void setDiscount10Amount(double discount10Amount) {
		this.discount10Amount = discount10Amount;
	}
	public double getDiscount5Amount() {
		return discount5Amount;
	}
	public void setDiscount5Amount(double discount5Amount) {
		this.discount5Amount = discount5Amount;
	}
	public int getItemsQuantity() {
		return itemsQuantity;
	}
	public void setItemsQuantity(int itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTaxNumber() {
		return taxNumber;
	}
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getEbControlCode() {
		return ebControlCode;
	}
	public void setEbControlCode(String ebControlCode) {
		this.ebControlCode = ebControlCode;
	}

}
