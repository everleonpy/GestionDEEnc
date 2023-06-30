package pojo;

import java.io.Serializable;

public class PosEbInvoice implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
    private String buildingnumber;
    private long cashcontrolid;
    private long cashid;
    private String cmoneope;
    private long collectionid;
    private String controlcode;
    private String ebControlCode;
	private String createdBy;
    private java.util.Date createdOn;
    private java.util.Date canceledOn;
	private String customername;
    private String ddesindpres;
    private String ddesmoneope;
    private String ddestimp;
    private String ddestiptra;
    private String defeemide;
    private String dest;
    private java.util.Date dfeinit;
    private String dnumdoc;
    private String dnumtim;
    private String dpunexp;
    private String txNumber;
    private String dserienum;
    private double dticam;
    private String email;
    private long identifier;
    private String identitynumber;
    private String identitytype;
    private long iindpres;
    private double initialpayment;
    private long installmentsqty;
    private long instdaysinterval;
    private long itimp;
    private long itiope;
    private long itiptra;
    private String orgtype;
    private String phone;
    private String refNumber;
    private String remissiondate;
    private String staddress;
    private String staddress2;
    private String staddress3;
    private String stbuildingno;  
    private String stCountryCode;
    private String stCountryName;
    private short stStateCode;
    private String stStateName;
    private short stCountyCode;
    private String stCountyName;
    private short stCityCode;
    private String stCityName;
    private String txCountryCode;
    private String txCountryName;
    private short txStateCode;
    private String txStateName;
    private short txCountyCode;
    private String txCountyName;
    private short txCityCode;
    private String txCityName;
    private String stemail;
    private String stname;
    private String stphone;
    private String taxnumber;
    private String taxnumbercd;
    private String trxcondition;
	private long batchId;
    private long ebBatchId;
    private java.util.Date ebSendDate;
	private double amount;
    private double collectionsAmt;
	private String custDocType;
    private String custDocNumber;    
    private String siteName;
	//
    private short paymentTerm1;
    private double paymentAmt1;
    private short cardMark1;
    private String checkNumber1;
    private String checkBank1;
    private short paymentTerm2;
    private double paymentAmt2;
    private short cardMark2;
    private String checkNumber2;
    private String checkBank2;
    private short paymentTerm3;
    private double paymentAmt3;
    private short cardMark3;
    private String checkNumber3;
    private String checkBank3;
    private short paymentTerm4;
    private double paymentAmt4;
    private short cardMark4;
    private String checkNumber4;
    private String checkBank4;
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBuildingnumber() {
		return buildingnumber;
	}
	public void setBuildingnumber(String buildingnumber) {
		this.buildingnumber = buildingnumber;
	}
	public long getCashcontrolid() {
		return cashcontrolid;
	}
	public void setCashcontrolid(long cashcontrolid) {
		this.cashcontrolid = cashcontrolid;
	}
	public long getCashid() {
		return cashid;
	}
	public void setCashid(long cashid) {
		this.cashid = cashid;
	}
	public String getCmoneope() {
		return cmoneope;
	}
	public void setCmoneope(String cmoneope) {
		this.cmoneope = cmoneope;
	}
	public long getCollectionid() {
		return collectionid;
	}
	public void setCollectionid(long collectionid) {
		this.collectionid = collectionid;
	}
	public String getControlcode() {
		return controlcode;
	}
	public void setControlcode(String controlcode) {
		this.controlcode = controlcode;
	}
    public String getEbControlCode() {
		return ebControlCode;
	}
	public void setEbControlCode(String ebControlCode) {
		this.ebControlCode = ebControlCode;
	}	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}
    public java.util.Date getCanceledOn() {
		return canceledOn;
	}
	public void setCanceledOn(java.util.Date canceledOn) {
		this.canceledOn = canceledOn;
	}	
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getDdesindpres() {
		return ddesindpres;
	}
	public void setDdesindpres(String ddesindpres) {
		this.ddesindpres = ddesindpres;
	}
	public String getDdesmoneope() {
		return ddesmoneope;
	}
	public void setDdesmoneope(String ddesmoneope) {
		this.ddesmoneope = ddesmoneope;
	}
	public String getDdestimp() {
		return ddestimp;
	}
	public void setDdestimp(String ddestimp) {
		this.ddestimp = ddestimp;
	}
	public String getDdestiptra() {
		return ddestiptra;
	}
	public void setDdestiptra(String ddestiptra) {
		this.ddestiptra = ddestiptra;
	}
	public String getDefeemide() {
		return defeemide;
	}
	public void setDefeemide(String defeemide) {
		this.defeemide = defeemide;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public java.util.Date getDfeinit() {
		return dfeinit;
	}
	public void setDfeinit(java.util.Date dfeinit) {
		this.dfeinit = dfeinit;
	}
	public String getDnumdoc() {
		return dnumdoc;
	}
	public void setDnumdoc(String dnumdoc) {
		this.dnumdoc = dnumdoc;
	}
	public String getTxNumber() {
		return txNumber;
	}
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}
	public String getDnumtim() {
		return dnumtim;
	}
	public void setDnumtim(String dnumtim) {
		this.dnumtim = dnumtim;
	}
	public String getDpunexp() {
		return dpunexp;
	}
	public void setDpunexp(String dpunexp) {
		this.dpunexp = dpunexp;
	}
	public String getDserienum() {
		return dserienum;
	}
	public void setDserienum(String dserienum) {
		this.dserienum = dserienum;
	}
	public double getDticam() {
		return dticam;
	}
	public void setDticam(double dticam) {
		this.dticam = dticam;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	public String getIdentitynumber() {
		return identitynumber;
	}
	public void setIdentitynumber(String identitynumber) {
		this.identitynumber = identitynumber;
	}
	public String getIdentitytype() {
		return identitytype;
	}
	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}
	public long getIindpres() {
		return iindpres;
	}
	public void setIindpres(long iindpres) {
		this.iindpres = iindpres;
	}
	public double getInitialpayment() {
		return initialpayment;
	}
	public void setInitialpayment(double initialpayment) {
		this.initialpayment = initialpayment;
	}
	public long getInstallmentsqty() {
		return installmentsqty;
	}
	public void setInstallmentsqty(long installmentsqty) {
		this.installmentsqty = installmentsqty;
	}
	public long getInstdaysinterval() {
		return instdaysinterval;
	}
	public void setInstdaysinterval(long instdaysinterval) {
		this.instdaysinterval = instdaysinterval;
	}
	public long getItimp() {
		return itimp;
	}
	public void setItimp(long itimp) {
		this.itimp = itimp;
	}
	public long getItiope() {
		return itiope;
	}
	public void setItiope(long itiope) {
		this.itiope = itiope;
	}
	public long getItiptra() {
		return itiptra;
	}
	public void setItiptra(long itiptra) {
		this.itiptra = itiptra;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getRemissiondate() {
		return remissiondate;
	}
	public void setRemissiondate(String remissiondate) {
		this.remissiondate = remissiondate;
	}
	public String getStaddress() {
		return staddress;
	}
	public void setStaddress(String staddress) {
		this.staddress = staddress;
	}
	public String getStaddress2() {
		return staddress2;
	}
	public void setStaddress2(String staddress2) {
		this.staddress2 = staddress2;
	}
	public String getStaddress3() {
		return staddress3;
	}
	public void setStaddress3(String staddress3) {
		this.staddress3 = staddress3;
	}
	public String getStbuildingno() {
		return stbuildingno;
	}
	public void setStbuildingno(String stbuildingno) {
		this.stbuildingno = stbuildingno;
	}
	public String getStCountryCode() {
		return stCountryCode;
	}
	public void setStCountryCode(String stCountryCode) {
		this.stCountryCode = stCountryCode;
	}
	public String getStCountryName() {
		return stCountryName;
	}
	public void setStCountryName(String stCountryName) {
		this.stCountryName = stCountryName;
	}
	public short getStStateCode() {
		return stStateCode;
	}
	public void setStStateCode(short stStateCode) {
		this.stStateCode = stStateCode;
	}
	public String getStStateName() {
		return stStateName;
	}
	public void setStStateName(String stStateName) {
		this.stStateName = stStateName;
	}
	public short getStCountyCode() {
		return stCountyCode;
	}
	public void setStCountyCode(short stCountyCode) {
		this.stCountyCode = stCountyCode;
	}
	public String getStCountyName() {
		return stCountyName;
	}
	public void setStCountyName(String stCountyName) {
		this.stCountyName = stCountyName;
	}
	public short getStCityCode() {
		return stCityCode;
	}
	public void setStCityCode(short stCityCode) {
		this.stCityCode = stCityCode;
	}
	public String getStCityName() {
		return stCityName;
	}
	public void setStCityName(String stCityName) {
		this.stCityName = stCityName;
	}
	public String getTxCountryCode() {
		return txCountryCode;
	}
	public void setTxCountryCode(String txCountryCode) {
		this.txCountryCode = txCountryCode;
	}
	public String getTxCountryName() {
		return txCountryName;
	}
	public void setTxCountryName(String txCountryName) {
		this.txCountryName = txCountryName;
	}
	public short getTxStateCode() {
		return txStateCode;
	}
	public void setTxStateCode(short txStateCode) {
		this.txStateCode = txStateCode;
	}
	public String getTxStateName() {
		return txStateName;
	}
	public void setTxStateName(String txStateName) {
		this.txStateName = txStateName;
	}
	public short getTxCountyCode() {
		return txCountyCode;
	}
	public void setTxCountyCode(short txCountyCode) {
		this.txCountyCode = txCountyCode;
	}
	public String getTxCountyName() {
		return txCountyName;
	}
	public void setTxCountyName(String txCountyName) {
		this.txCountyName = txCountyName;
	}
	public short getTxCityCode() {
		return txCityCode;
	}
	public void setTxCityCode(short txCityCode) {
		this.txCityCode = txCityCode;
	}
	public String getTxCityName() {
		return txCityName;
	}
	public void setTxCityName(String txCityName) {
		this.txCityName = txCityName;
	}
	public String getStemail() {
		return stemail;
	}
	public void setStemail(String stemail) {
		this.stemail = stemail;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getStphone() {
		return stphone;
	}
	public void setStphone(String stphone) {
		this.stphone = stphone;
	}
	public String getTaxnumber() {
		return taxnumber;
	}
	public void setTaxnumber(String taxnumber) {
		this.taxnumber = taxnumber;
	}
	public String getTaxnumbercd() {
		return taxnumbercd;
	}
	public void setTaxnumbercd(String taxnumbercd) {
		this.taxnumbercd = taxnumbercd;
	}
	public String getTrxcondition() {
		return trxcondition;
	}
	public void setTrxcondition(String trxcondition) {
		this.trxcondition = trxcondition;
	}
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public long getEbBatchId() {
		return ebBatchId;
	}
	public void setEbBatchId(long ebBatchId) {
		this.ebBatchId = ebBatchId;
	}
	public java.util.Date getEbSendDate() {
		return ebSendDate;
	}
	public void setEbSendDate(java.util.Date ebSendDate) {
		this.ebSendDate = ebSendDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getCollectionsAmt() {
		return collectionsAmt;
	}
	public void setCollectionsAmt(double collectionsAmt) {
		this.collectionsAmt = collectionsAmt;
	}
	public String getCustDocType() {
		return custDocType;
	}
	public void setCustDocType(String custDocType) {
		this.custDocType = custDocType;
	}
	public String getCustDocNumber() {
		return custDocNumber;
	}
	public void setCustDocNumber(String custDocNumber) {
		this.custDocNumber = custDocNumber;
	}
    public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public short getPaymentTerm1() {
		return paymentTerm1;
	}
	public void setPaymentTerm1(short paymentTerm1) {
		this.paymentTerm1 = paymentTerm1;
	}
	public double getPaymentAmt1() {
		return paymentAmt1;
	}
	public void setPaymentAmt1(double paymentAmt1) {
		this.paymentAmt1 = paymentAmt1;
	}
	public short getCardMark1() {
		return cardMark1;
	}
	public void setCardMark1(short cardMark1) {
		this.cardMark1 = cardMark1;
	}
	public String getCheckNumber1() {
		return checkNumber1;
	}
	public void setCheckNumber1(String checkNumber1) {
		this.checkNumber1 = checkNumber1;
	}
	public String getCheckBank1() {
		return checkBank1;
	}
	public void setCheckBank1(String checkBank1) {
		this.checkBank1 = checkBank1;
	}
	public short getPaymentTerm2() {
		return paymentTerm2;
	}
	public void setPaymentTerm2(short paymentTerm2) {
		this.paymentTerm2 = paymentTerm2;
	}
	public double getPaymentAmt2() {
		return paymentAmt2;
	}
	public void setPaymentAmt2(double paymentAmt2) {
		this.paymentAmt2 = paymentAmt2;
	}
	public short getCardMark2() {
		return cardMark2;
	}
	public void setCardMark2(short cardMark2) {
		this.cardMark2 = cardMark2;
	}
	public String getCheckNumber2() {
		return checkNumber2;
	}
	public void setCheckNumber2(String checkNumber2) {
		this.checkNumber2 = checkNumber2;
	}
	public String getCheckBank2() {
		return checkBank2;
	}
	public void setCheckBank2(String checkBank2) {
		this.checkBank2 = checkBank2;
	}
	public short getPaymentTerm3() {
		return paymentTerm3;
	}
	public void setPaymentTerm3(short paymentTerm3) {
		this.paymentTerm3 = paymentTerm3;
	}
	public double getPaymentAmt3() {
		return paymentAmt3;
	}
	public void setPaymentAmt3(double paymentAmt3) {
		this.paymentAmt3 = paymentAmt3;
	}
	public short getCardMark3() {
		return cardMark3;
	}
	public void setCardMark3(short cardMark3) {
		this.cardMark3 = cardMark3;
	}
	public String getCheckNumber3() {
		return checkNumber3;
	}
	public void setCheckNumber3(String checkNumber3) {
		this.checkNumber3 = checkNumber3;
	}
	public String getCheckBank3() {
		return checkBank3;
	}
	public void setCheckBank3(String checkBank3) {
		this.checkBank3 = checkBank3;
	}
	public short getPaymentTerm4() {
		return paymentTerm4;
	}
	public void setPaymentTerm4(short paymentTerm4) {
		this.paymentTerm4 = paymentTerm4;
	}
	public double getPaymentAmt4() {
		return paymentAmt4;
	}
	public void setPaymentAmt4(double paymentAmt4) {
		this.paymentAmt4 = paymentAmt4;
	}
	public short getCardMark4() {
		return cardMark4;
	}
	public void setCardMark4(short cardMark4) {
		this.cardMark4 = cardMark4;
	}
	public String getCheckNumber4() {
		return checkNumber4;
	}
	public void setCheckNumber4(String checkNumber4) {
		this.checkNumber4 = checkNumber4;
	}
	public String getCheckBank4() {
		return checkBank4;
	}
	public void setCheckBank4(String checkBank4) {
		this.checkBank4 = checkBank4;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}