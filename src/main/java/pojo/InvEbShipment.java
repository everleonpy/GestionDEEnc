package pojo;

public class InvEbShipment {
    private String controlcode;
    private String ebControlCode;
	private String createdBy;
    private java.util.Date createdOn;
    private java.util.Date canceledOn;
	private String customername;
    private String defeemide;
    private String dest;
    private java.util.Date dfeinit;
    private String dnumdoc;
    private String dnumtim;
    private String dpunexp;
    private String dserienum;
    private String txNumber;
    private long identifier;
    private long orgId;
    private long unitId;
    private long itiope;
    private String refNumber;
    private long fromSiteId;
    private String fromSiteName;
    private long toSiteId;
    private String toSiteName;
	private long batchId;
    private long ebBatchId;
    private java.util.Date ebSendDate;
	private double amount;
    private String shipmentType;
    private String shipmentReason;
    private String comments;
    private String coldChainFlag;
    private String dangerLoadFlag;
    private short iMotEmiNR; 
    private String dDesMotEmiNR; 
    private short dKmR; 
    private long invoice_id;
    private java.util.Date dFecEmNR;
    
    private String taxnumber;
    private String taxnumbercd;
    private String identitynumber;
    private String identitytype;
    private String customerName;
    private String orgtype;
    private String email;
	private String custDocType;
    private String custDocNumber;

    private String depAddress;
    private String depAddress2;
    private String depAddress3;
    private String depBuildingno;  
    private String depCountryCode;
    private String depCountryName;
    private short depStateCode;
    private String depStateName;
    private short depCountyCode;
    private String depCountyName;
    private short depCityCode;
    private String depCityName;
    private String depPhone;

    private String arrAddress;
    private String arrAddress2;
    private String arrAddress3;
    private String arrBuildingno;  
    private String arrCountryCode;
    private String arrCountryName;
    private short arrStateCode;
    private String arrStateName;
    private short arrCountyCode;
    private String arrCountyName;
    private short arrCityCode;
    private String arrCityName;
    private String arrPhone;

    private java.util.Date dIniTrans;
    private java.util.Date dFinTrans;
    private String dTiVeTras;
    private String dMarVeh; 
    private short dTipIdenVeh; 
    private String dNroIdVeh; 
    private String dNroMatVeh;
    private short iNatTrans; 
    private String dNomTrans; 
    private String dRucTrans; 
    private String dDVTrans; 
    private short iTipIdTrans; 
    private String dDTipIdTrans; 
    private String dNumIdTrans; 
    private String dDomFisc;
	private String dNumIDChof; 
    private String dNomChof;
    private String dDirChof;
    
	private String xmlFile;
    private String qrCode;
    
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
	public long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(long identifier) {
		this.identifier = identifier;
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
	public long getItiope() {
		return itiope;
	}
	public void setItiope(long itiope) {
		this.itiope = itiope;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public long getFromSiteId() {
		return fromSiteId;
	}
	public void setFromSiteId(long fromSiteId) {
		this.fromSiteId = fromSiteId;
	}
	public String getFromSiteName() {
		return fromSiteName;
	}
	public void setFromSiteName(String fromSiteName) {
		this.fromSiteName = fromSiteName;
	}
	public long getToSiteId() {
		return toSiteId;
	}
	public void setToSiteId(long toSiteId) {
		this.toSiteId = toSiteId;
	}
	public String getToSiteName() {
		return toSiteName;
	}
	public void setToSiteName(String toSiteName) {
		this.toSiteName = toSiteName;
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
	public String getShipmentType() {
		return shipmentType;
	}
	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}
	public String getShipmentReason() {
		return shipmentReason;
	}
	public void setShipmentReason(String shipmentReason) {
		this.shipmentReason = shipmentReason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getColdChainFlag() {
		return coldChainFlag;
	}
	public void setColdChainFlag(String coldChainFlag) {
		this.coldChainFlag = coldChainFlag;
	}
	public String getDangerLoadFlag() {
		return dangerLoadFlag;
	}
	public void setDangerLoadFlag(String dangerLoadFlag) {
		this.dangerLoadFlag = dangerLoadFlag;
	}
	public short getiMotEmiNR() {
		return iMotEmiNR;
	}
	public void setiMotEmiNR(short iMotEmiNR) {
		this.iMotEmiNR = iMotEmiNR;
	}
	public String getdDesMotEmiNR() {
		return dDesMotEmiNR;
	}
	public void setdDesMotEmiNR(String dDesMotEmiNR) {
		this.dDesMotEmiNR = dDesMotEmiNR;
	}
	public short getdKmR() {
		return dKmR;
	}
	public void setdKmR(short dKmR) {
		this.dKmR = dKmR;
	}
	public long getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}
	public java.util.Date getdFecEmNR() {
		return dFecEmNR;
	}
	public void setdFecEmNR(java.util.Date dFecEmNR) {
		this.dFecEmNR = dFecEmNR;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getDepAddress() {
		return depAddress;
	}
	public void setDepAddress(String depAddress) {
		this.depAddress = depAddress;
	}
	public String getDepAddress2() {
		return depAddress2;
	}
	public void setDepAddress2(String depAddress2) {
		this.depAddress2 = depAddress2;
	}
	public String getDepAddress3() {
		return depAddress3;
	}
	public void setDepAddress3(String depAddress3) {
		this.depAddress3 = depAddress3;
	}
	public String getDepBuildingno() {
		return depBuildingno;
	}
	public void setDepBuildingno(String depBuildingno) {
		this.depBuildingno = depBuildingno;
	}
	public String getDepCountryCode() {
		return depCountryCode;
	}
	public void setDepCountryCode(String depCountryCode) {
		this.depCountryCode = depCountryCode;
	}
	public String getDepCountryName() {
		return depCountryName;
	}
	public void setDepCountryName(String depCountryName) {
		this.depCountryName = depCountryName;
	}
	public short getDepStateCode() {
		return depStateCode;
	}
	public void setDepStateCode(short depStateCode) {
		this.depStateCode = depStateCode;
	}
	public String getDepStateName() {
		return depStateName;
	}
	public void setDepStateName(String depStateName) {
		this.depStateName = depStateName;
	}
	public short getDepCountyCode() {
		return depCountyCode;
	}
	public void setDepCountyCode(short depCountyCode) {
		this.depCountyCode = depCountyCode;
	}
	public String getDepCountyName() {
		return depCountyName;
	}
	public void setDepCountyName(String depCountyName) {
		this.depCountyName = depCountyName;
	}
	public short getDepCityCode() {
		return depCityCode;
	}
	public void setDepCityCode(short depCityCode) {
		this.depCityCode = depCityCode;
	}
	public String getDepCityName() {
		return depCityName;
	}
	public void setDepCityName(String depCityName) {
		this.depCityName = depCityName;
	}
	public String getDepPhone() {
		return depPhone;
	}
	public void setDepPhone(String depPhone) {
		this.depPhone = depPhone;
	}
	public String getArrAddress() {
		return arrAddress;
	}
	public void setArrAddress(String arrAddress) {
		this.arrAddress = arrAddress;
	}
	public String getArrAddress2() {
		return arrAddress2;
	}
	public void setArrAddress2(String arrAddress2) {
		this.arrAddress2 = arrAddress2;
	}
	public String getArrAddress3() {
		return arrAddress3;
	}
	public void setArrAddress3(String arrAddress3) {
		this.arrAddress3 = arrAddress3;
	}
	public String getArrBuildingno() {
		return arrBuildingno;
	}
	public void setArrBuildingno(String arrBuildingno) {
		this.arrBuildingno = arrBuildingno;
	}
	public String getArrCountryCode() {
		return arrCountryCode;
	}
	public void setArrCountryCode(String arrCountryCode) {
		this.arrCountryCode = arrCountryCode;
	}
	public String getArrCountryName() {
		return arrCountryName;
	}
	public void setArrCountryName(String arrCountryName) {
		this.arrCountryName = arrCountryName;
	}
	public short getArrStateCode() {
		return arrStateCode;
	}
	public void setArrStateCode(short arrStateCode) {
		this.arrStateCode = arrStateCode;
	}
	public String getArrStateName() {
		return arrStateName;
	}
	public void setArrStateName(String arrStateName) {
		this.arrStateName = arrStateName;
	}
	public short getArrCountyCode() {
		return arrCountyCode;
	}
	public void setArrCountyCode(short arrCountyCode) {
		this.arrCountyCode = arrCountyCode;
	}
	public String getArrCountyName() {
		return arrCountyName;
	}
	public void setArrCountyName(String arrCountyName) {
		this.arrCountyName = arrCountyName;
	}
	public short getArrCityCode() {
		return arrCityCode;
	}
	public void setArrCityCode(short arrCityCode) {
		this.arrCityCode = arrCityCode;
	}
	public String getArrCityName() {
		return arrCityName;
	}
	public void setArrCityName(String arrCityName) {
		this.arrCityName = arrCityName;
	}
	public String getArrPhone() {
		return arrPhone;
	}
	public void setArrPhone(String arrPhone) {
		this.arrPhone = arrPhone;
	}
	public java.util.Date getdIniTrans() {
		return dIniTrans;
	}
	public void setdIniTrans(java.util.Date dIniTrans) {
		this.dIniTrans = dIniTrans;
	}
	public java.util.Date getdFinTrans() {
		return dFinTrans;
	}
	public void setdFinTrans(java.util.Date dFinTrans) {
		this.dFinTrans = dFinTrans;
	}
	public String getdTiVeTras() {
		return dTiVeTras;
	}
	public void setdTiVeTras(String dTiVeTras) {
		this.dTiVeTras = dTiVeTras;
	}
	public String getdMarVeh() {
		return dMarVeh;
	}
	public void setdMarVeh(String dMarVeh) {
		this.dMarVeh = dMarVeh;
	}
	public short getdTipIdenVeh() {
		return dTipIdenVeh;
	}
	public void setdTipIdenVeh(short dTipIdenVeh) {
		this.dTipIdenVeh = dTipIdenVeh;
	}
	public String getdNroIdVeh() {
		return dNroIdVeh;
	}
	public void setdNroIdVeh(String dNroIdVeh) {
		this.dNroIdVeh = dNroIdVeh;
	}
	public String getdNroMatVeh() {
		return dNroMatVeh;
	}
	public void setdNroMatVeh(String dNroMatVeh) {
		this.dNroMatVeh = dNroMatVeh;
	}
	public short getiNatTrans() {
		return iNatTrans;
	}
	public void setiNatTrans(short iNatTrans) {
		this.iNatTrans = iNatTrans;
	}
	public String getdNomTrans() {
		return dNomTrans;
	}
	public void setdNomTrans(String dNomTrans) {
		this.dNomTrans = dNomTrans;
	}
	public String getdRucTrans() {
		return dRucTrans;
	}
	public void setdRucTrans(String dRucTrans) {
		this.dRucTrans = dRucTrans;
	}
	public String getdDVTrans() {
		return dDVTrans;
	}
	public void setdDVTrans(String dDVTrans) {
		this.dDVTrans = dDVTrans;
	}
	public short getiTipIdTrans() {
		return iTipIdTrans;
	}
	public void setiTipIdTrans(short iTipIdTrans) {
		this.iTipIdTrans = iTipIdTrans;
	}
	public String getdDTipIdTrans() {
		return dDTipIdTrans;
	}
	public void setdDTipIdTrans(String dDTipIdTrans) {
		this.dDTipIdTrans = dDTipIdTrans;
	}
	public String getdNumIdTrans() {
		return dNumIdTrans;
	}
	public void setdNumIdTrans(String dNumIdTrans) {
		this.dNumIdTrans = dNumIdTrans;
	}
    public String getdDomFisc() {
		return dDomFisc;
	}
	public void setdDomFisc(String dDomFisc) {
		this.dDomFisc = dDomFisc;
	}	
	public String getdNumIDChof() {
		return dNumIDChof;
	}
	public void setdNumIDChof(String dNumIDChof) {
		this.dNumIDChof = dNumIDChof;
	}
	public String getdNomChof() {
		return dNomChof;
	}
	public void setdNomChof(String dNomChof) {
		this.dNomChof = dNomChof;
	}
    public String getdDirChof() {
		return dDirChof;
	}
	public void setdDirChof(String dDirChof) {
		this.dDirChof = dDirChof;
	}	
	public String getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
        
}
