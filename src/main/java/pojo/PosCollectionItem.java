package pojo;

import java.io.Serializable;

public class PosCollectionItem implements Serializable {
    /**
	 * POJO que representa una fila de la tabla POS_COLLECTION_ITEMS
	 */
	private static final long serialVersionUID = 1L;
	private long cashControlId;
    private long cashRegisterId;
    private long collectionId;
    private String createdBy;
    private java.util.Date createdOn;
    private long creditMemoId;
    private long currencyId;
    private long custVoucherId;
    private double discountAmount;
    private double discountPct;
    private double documAmount;
    private java.util.Date documDate;
    private java.util.Date documExpiryDate;
    private String documName;
    private String documNumber;
    private String documRef;
    private long employeeId;
    private long empVoucherId;
    private double enteredAmount;
    private long entityId;
    private String entityType;
    private double exchangeRate;
    private String exportedFlag;
    private long foreignItemId;
    private long foreignTrxId;
    private long identifier;
    private double initialAmount;
    private long invoiceId;
    private long itemNumber;
    private double lcAmount;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private long orgId;
    private String paymentName;
    private long pmtTermId;
    private long unitId;
    private double amount;
    private String cardAccountNo;
    private String cardAuthorCode;
    private long cardEntityId;
    private long cardFeesQty;
    private long cardMarkId;
    private String cardNumber;
    private long cardProcessorId;
    private String cardVoucherNo;

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

    public long getCollectionId() {
        return this.collectionId;
    }
    public void setCollectionId ( long collectionid ) {
        this.collectionId = collectionid;
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

    public long getCreditMemoId() {
        return this.creditMemoId;
    }
    public void setCreditMemoId ( long creditmemoid ) {
        this.creditMemoId = creditmemoid;
    }

    public long getCurrencyId() {
        return this.currencyId;
    }
    public void setCurrencyId ( long currencyid ) {
        this.currencyId = currencyid;
    }

    public long getCustVoucherId() {
        return this.custVoucherId;
    }
    public void setCustVoucherId ( long custvoucherid ) {
        this.custVoucherId = custvoucherid;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }
    public void setDiscountAmount ( double discountamount ) {
        this.discountAmount = discountamount;
    }

    public double getDiscountPct() {
        return this.discountPct;
    }
    public void setDiscountPct ( double discountpct ) {
        this.discountPct = discountpct;
    }

    public double getDocumAmount() {
        return this.documAmount;
    }
    public void setDocumAmount ( double documamount ) {
        this.documAmount = documamount;
    }

    public java.util.Date getDocumDate() {
        return this.documDate;
    }
    public void setDocumDate ( java.util.Date documdate ) {
        this.documDate = documdate;
    }

    public java.util.Date getDocumExpiryDate() {
        return this.documExpiryDate;
    }
    public void setDocumExpiryDate ( java.util.Date documexpirydate ) {
        this.documExpiryDate = documexpirydate;
    }

    public String getDocumName() {
        return this.documName;
    }
    public void setDocumName ( String documname ) {
        this.documName = documname;
    }

    public String getDocumNumber() {
        return this.documNumber;
    }
    public void setDocumNumber ( String documnumber ) {
        this.documNumber = documnumber;
    }

    public String getDocumRef() {
        return this.documRef;
    }
    public void setDocumRef ( String documref ) {
        this.documRef = documref;
    }

    public long getEmployeeId() {
        return this.employeeId;
    }
    public void setEmployeeId ( long employeeid ) {
        this.employeeId = employeeid;
    }

    public long getEmpVoucherId() {
        return this.empVoucherId;
    }
    public void setEmpVoucherId ( long empvoucherid ) {
        this.empVoucherId = empvoucherid;
    }

    public double getEnteredAmount() {
        return this.enteredAmount;
    }
    public void setEnteredAmount ( double enteredamount ) {
        this.enteredAmount = enteredamount;
    }

    public long getEntityId() {
        return this.entityId;
    }
    public void setEntityId ( long entityid ) {
        this.entityId = entityid;
    }

    public String getEntityType() {
        return this.entityType;
    }
    public void setEntityType ( String entitytype ) {
        this.entityType = entitytype;
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

    public double getInitialAmount() {
        return this.initialAmount;
    }
    public void setInitialAmount ( double initialamount ) {
        this.initialAmount = initialamount;
    }

    public long getInvoiceId() {
        return this.invoiceId;
    }
    public void setInvoiceId ( long invoiceid ) {
        this.invoiceId = invoiceid;
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

    public String getPaymentName() {
        return this.paymentName;
    }
    public void setPaymentName ( String paymentname ) {
        this.paymentName = paymentname;
    }

    public long getPmtTermId() {
        return this.pmtTermId;
    }
    public void setPmtTermId ( long pmttermid ) {
        this.pmtTermId = pmttermid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount ( double amount ) {
        this.amount = amount;
    }

    public String getCardAccountNo() {
        return this.cardAccountNo;
    }
    public void setCardAccountNo ( String cardaccountno ) {
        this.cardAccountNo = cardaccountno;
    }

    public String getCardAuthorCode() {
        return this.cardAuthorCode;
    }
    public void setCardAuthorCode ( String cardauthorcode ) {
        this.cardAuthorCode = cardauthorcode;
    }

    public long getCardEntityId() {
        return this.cardEntityId;
    }
    public void setCardEntityId ( long cardentityid ) {
        this.cardEntityId = cardentityid;
    }

    public long getCardFeesQty() {
        return this.cardFeesQty;
    }
    public void setCardFeesQty ( long cardfeesqty ) {
        this.cardFeesQty = cardfeesqty;
    }

    public long getCardMarkId() {
        return this.cardMarkId;
    }
    public void setCardMarkId ( long cardmarkid ) {
        this.cardMarkId = cardmarkid;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }
    public void setCardNumber ( String cardnumber ) {
        this.cardNumber = cardnumber;
    }

    public long getCardProcessorId() {
        return this.cardProcessorId;
    }
    public void setCardProcessorId ( long cardprocessorid ) {
        this.cardProcessorId = cardprocessorid;
    }

    public String getCardVoucherNo() {
        return this.cardVoucherNo;
    }
    public void setCardVoucherNo ( String cardvoucherno ) {
        this.cardVoucherNo = cardvoucherno;
    }

}