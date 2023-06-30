package pojo;

import java.util.ArrayList;

public class TicketHeader {
	private String businessName;
	private String tradingName;
	private String taxNumber;
	private String economicActivity;
	private ArrayList<String> secEconActivities;
	// datos de la casa matriz
	private String headquartersName;
	private String headquartersAddress;
	private String headquartersPhone;
	// datos de la sucursal especifica
	private String siteName;
	private String siteAddress;
	private String sitePhone;	
	private String stampNumber;
	private java.util.Date stampExpiryDate;
	private java.util.Date stampStartDate;
	private java.util.Date stampEndDate;
	private java.util.Date stampAuthorDate;	
	private String issueSite;
	private String issuePoint;
	private java.util.Date printingDate;
	private String invoiceNumber;
	private String dayOfWeek;
	private String transactionNumber;
	private String sourceTransNumber;
	//
	private String taxLabel;
	private String cashRegisterNo;
	private String cashierName;
	private String invoiceType;
	private short paymentDays;
	//
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getTradingName() {
		return tradingName;
	}
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}
	public String getTaxNumber() {
		return taxNumber;
	}
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	public String getHeadquartersName() {
		return headquartersName;
	}
	public void setHeadquartersName(String headquartersName) {
		this.headquartersName = headquartersName;
	}
	public String getHeadquartersAddress() {
		return headquartersAddress;
	}
	public void setEconomicActivity(String economicActivity) {
		this.economicActivity = economicActivity;
	}
	public ArrayList<String> getSecEconActivities() {
		return secEconActivities;
	}
	public void setSecEconActivities(ArrayList<String> secEconActivities) {
		this.secEconActivities = secEconActivities;
	}
	public String getHeadquartersPhone() {
		return headquartersPhone;
	}
	public void setHeadquartersAddress(String headquartersAddress) {
		this.headquartersAddress = headquartersAddress;
	}
	public String getEconomicActivity() {
		return economicActivity;
	}
	public void setHeadquartersPhone(String headquartersPhone) {
		this.headquartersPhone = headquartersPhone;
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
	public String getStampNumber() {
		return stampNumber;
	}
	public void setStampNumber(String stampNumber) {
		this.stampNumber = stampNumber;
	}
	public java.util.Date getStampExpiryDate() {
		return stampExpiryDate;
	}
	public void setStampExpiryDate(java.util.Date stampExpiryDate) {
		this.stampExpiryDate = stampExpiryDate;
	}
	public java.util.Date getStampStartDate() {
		return stampStartDate;
	}
	public void setStampStartDate(java.util.Date stampStartDate) {
		this.stampStartDate = stampStartDate;
	}
	public java.util.Date getStampEndDate() {
		return stampEndDate;
	}
	public void setStampEndDate(java.util.Date stampEndDate) {
		this.stampEndDate = stampEndDate;
	}
	public java.util.Date getStampAuthorDate() {
		return stampAuthorDate;
	}
	public void setStampAuthorDate(java.util.Date stampAuthorDate) {
		this.stampAuthorDate = stampAuthorDate;
	}
	public String getIssueSite() {
		return issueSite;
	}
	public void setIssueSite(String issueSite) {
		this.issueSite = issueSite;
	}
	public String getIssuePoint() {
		return issuePoint;
	}
	public void setIssuePoint(String issuePoint) {
		this.issuePoint = issuePoint;
	}
	public java.util.Date getPrintingDate() {
		return printingDate;
	}
	public void setPrintingDate(java.util.Date printingDate) {
		this.printingDate = printingDate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getSourceTransNumber() {
		return sourceTransNumber;
	}
	public void setSourceTransNumber(String sourceTransNumber) {
		this.sourceTransNumber = sourceTransNumber;
	}
	public String getTaxLabel() {
		return taxLabel;
	}
	public void setTaxLabel(String taxLabel) {
		this.taxLabel = taxLabel;
	}
	public String getCashRegisterNo() {
		return cashRegisterNo;
	}
	public void setCashRegisterNo(String cashRegisterNo) {
		this.cashRegisterNo = cashRegisterNo;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public long getPaymentDays() {
		return paymentDays;
	}
	public void setPaymentDays(short paymentDays) {
		this.paymentDays = paymentDays;
	}

}
