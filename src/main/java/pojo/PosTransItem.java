package pojo;

import java.io.Serializable;

public class PosTransItem implements Serializable {
    /**
	 * POJO que representa una fila de la table POS_TRANS_ITEMS
	 */
	private static final long serialVersionUID = 1L;
	private double amount;
    private String amountIncludesTax;
    private long cashControlId;
    private long cashRegisterId;
    private long cmemoGroup;
    private double cmQuantity;
    private String createdBy;
    private java.util.Date createdOn;
    private double discountAmount;
    private double discountPct;
    private double excemptAmount;
    private String exportedFlag;
    private long foreignItemId;
    private long foreignTrxId;
    private long identifier;
    private long invJournalId;
    private long itemNumber;
    private String itemType;
    private String modifiedBy;
    private java.util.Date modifiedOn;
    private double netUnitCost;
    private double netUnitCostTax;
    private long orgId;
    private long productId;
    private long prodFamilyId;
    private long prodGroupId;
    private String prodGroupNo;
    private double quantity;
    private double quantityToRet;
    private String readyToSend;
    private long returnHeadtId;
    private String sentToServer;
    private long serviceId;
    private double taxableAmount;
    private double taxAmount;
    private double taxRate;
    private long taxTypeId;
    private long transactionId;
    private double unitCost;
    private double unitCostTax;
    private double unitDiscAmount;
    private long unitId;
    private double unitPrice;
    private double unitPriceTax;
    private double unitTaxAmount;
    private long uomId;

    public double getAmount() {
        return this.amount;
    }
    public void setAmount ( double amount ) {
        this.amount = amount;
    }

    public String getAmountIncludesTax() {
        return this.amountIncludesTax;
    }
    public void setAmountIncludesTax ( String amountincludestax ) {
        this.amountIncludesTax = amountincludestax;
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

    public long getCmemoGroup() {
        return this.cmemoGroup;
    }
    public void setCmemoGroup ( long cmemogroup ) {
        this.cmemoGroup = cmemogroup;
    }

    public double getCmQuantity() {
        return this.cmQuantity;
    }
    public void setCmQuantity ( double cmquantity ) {
        this.cmQuantity = cmquantity;
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

    public double getExcemptAmount() {
        return this.excemptAmount;
    }
    public void setExcemptAmount ( double excemptamount ) {
        this.excemptAmount = excemptamount;
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

    public long getInvJournalId() {
        return this.invJournalId;
    }
    public void setInvJournalId ( long invjournalid ) {
        this.invJournalId = invjournalid;
    }

    public long getItemNumber() {
        return this.itemNumber;
    }
    public void setItemNumber ( long itemnumber ) {
        this.itemNumber = itemnumber;
    }

    public String getItemType() {
        return this.itemType;
    }
    public void setItemType ( String itemtype ) {
        this.itemType = itemtype;
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

    public double getNetUnitCost() {
        return this.netUnitCost;
    }
    public void setNetUnitCost ( double netunitcost ) {
        this.netUnitCost = netunitcost;
    }

    public double getNetUnitCostTax() {
        return this.netUnitCostTax;
    }
    public void setNetUnitCostTax ( double netunitcosttax ) {
        this.netUnitCostTax = netunitcosttax;
    }

    public long getOrgId() {
        return this.orgId;
    }
    public void setOrgId ( long orgid ) {
        this.orgId = orgid;
    }

    public long getProductId() {
        return this.productId;
    }
    public void setProductId ( long productid ) {
        this.productId = productid;
    }

    public long getProdFamilyId() {
        return this.prodFamilyId;
    }
    public void setProdFamilyId ( long prodfamilyid ) {
        this.prodFamilyId = prodfamilyid;
    }

    public long getProdGroupId() {
        return this.prodGroupId;
    }
    public void setProdGroupId ( long prodgroupid ) {
        this.prodGroupId = prodgroupid;
    }

    public String getProdGroupNo() {
        return this.prodGroupNo;
    }
    public void setProdGroupNo ( String prodgroupno ) {
        this.prodGroupNo = prodgroupno;
    }

    public double getQuantity() {
        return this.quantity;
    }
    public void setQuantity ( double quantity ) {
        this.quantity = quantity;
    }

    public double getQuantityToRet() {
        return this.quantityToRet;
    }
    public void setQuantityToRet ( double quantitytoret ) {
        this.quantityToRet = quantitytoret;
    }

    public String getReadyToSend() {
        return this.readyToSend;
    }
    public void setReadyToSend ( String readytosend ) {
        this.readyToSend = readytosend;
    }

    public long getReturnHeadtId() {
        return this.returnHeadtId;
    }
    public void setReturnHeadtId ( long returnheadtid ) {
        this.returnHeadtId = returnheadtid;
    }

    public String getSentToServer() {
        return this.sentToServer;
    }
    public void setSentToServer ( String senttoserver ) {
        this.sentToServer = senttoserver;
    }

    public long getServiceId() {
        return this.serviceId;
    }
    public void setServiceId ( long serviceid ) {
        this.serviceId = serviceid;
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

    public double getUnitCost() {
        return this.unitCost;
    }
    public void setUnitCost ( double unitcost ) {
        this.unitCost = unitcost;
    }

    public double getUnitCostTax() {
        return this.unitCostTax;
    }
    public void setUnitCostTax ( double unitcosttax ) {
        this.unitCostTax = unitcosttax;
    }

    public double getUnitDiscAmount() {
        return this.unitDiscAmount;
    }
    public void setUnitDiscAmount ( double unitdiscamount ) {
        this.unitDiscAmount = unitdiscamount;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }
    public void setUnitPrice ( double unitprice ) {
        this.unitPrice = unitprice;
    }

    public double getUnitPriceTax() {
        return this.unitPriceTax;
    }
    public void setUnitPriceTax ( double unitpricetax ) {
        this.unitPriceTax = unitpricetax;
    }

    public double getUnitTaxAmount() {
        return this.unitTaxAmount;
    }
    public void setUnitTaxAmount ( double unittaxamount ) {
        this.unitTaxAmount = unittaxamount;
    }

    public long getUomId() {
        return this.uomId;
    }
    public void setUomId ( long uomid ) {
        this.uomId = uomid;
    }

}