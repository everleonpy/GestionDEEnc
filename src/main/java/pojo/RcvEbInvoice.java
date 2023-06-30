package pojo;

import java.io.Serializable;

public class RcvEbInvoice implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
    private String buildingnumber;
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
    private String dserienum;
    private String txNumber;
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
    private String receivablesType;
    private long cmInvoiceId;
    private long cmCashId; 
    private long cmControlId;
    private long siteId;
    private String siteName;
	private long batchId;
    private long ebBatchId;
    private java.util.Date ebSendDate;
	private double amount;
    private double collectionsAmt;
	private String custDocType;
    private String custDocNumber;
    
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
    public String getTxNumber() {
		return txNumber;
	}
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    public String getReceivablesType() {
		return receivablesType;
	}
	public void setReceivablesType(String receivablesType) {
		this.receivablesType = receivablesType;
	}
	public long getCmInvoiceId() {
		return cmInvoiceId;
	}
	public void setCmInvoiceId(long cmInvoiceId) {
		this.cmInvoiceId = cmInvoiceId;
	}
	public long getCmCashId() {
		return cmCashId;
	}
	public void setCmCashId(long cmCashId) {
		this.cmCashId = cmCashId;
	}
	public long getCmControlId() {
		return cmControlId;
	}
	public void setCmControlId(long cmControlId) {
		this.cmControlId = cmControlId;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
    public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

}