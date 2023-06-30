package pojo;

import java.io.Serializable;

public class PosEbInvoiceItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cashcontrolid;
    private long cashid;
    private String createdBy;
    private java.util.Date createdOn;
    private String dcodint;
    private String ddesproser;
    private double discountamount;
    private double discountpct;
    private long identifier;
    private long productid;
    private String qtyhandling;
    private double quantity;
    private double taxrate;
    private long taxtypeid;
    private long transactionid;
    private double unitdiscamount;
    private double unitprice;

    public long getCashcontrolid() {
        return this.cashcontrolid;
    }
    public void setCashcontrolid ( long cashcontrolid ) {
        this.cashcontrolid = cashcontrolid;
    }

    public long getCashid() {
        return this.cashid;
    }
    public void setCashid ( long cashid ) {
        this.cashid = cashid;
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

    public String getDcodint() {
        return this.dcodint;
    }
    public void setDcodint ( String dcodint ) {
        this.dcodint = dcodint;
    }

    public String getDdesproser() {
        return this.ddesproser;
    }
    public void setDdesproser ( String ddesproser ) {
        this.ddesproser = ddesproser;
    }

    public double getDiscountamount() {
        return this.discountamount;
    }
    public void setDiscountamount ( double discountamount ) {
        this.discountamount = discountamount;
    }

    public double getDiscountpct() {
        return this.discountpct;
    }
    public void setDiscountpct ( double discountpct ) {
        this.discountpct = discountpct;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public long getProductid() {
        return this.productid;
    }
    public void setProductid ( long productid ) {
        this.productid = productid;
    }

    public String getQtyhandling() {
        return this.qtyhandling;
    }
    public void setQtyhandling ( String qtyhandling ) {
        this.qtyhandling = qtyhandling;
    }

    public double getQuantity() {
        return this.quantity;
    }
    public void setQuantity ( double quantity ) {
        this.quantity = quantity;
    }

    public double getTaxrate() {
        return this.taxrate;
    }
    public void setTaxrate ( double taxrate ) {
        this.taxrate = taxrate;
    }

    public long getTaxtypeid() {
        return this.taxtypeid;
    }
    public void setTaxtypeid ( long taxtypeid ) {
        this.taxtypeid = taxtypeid;
    }

    public long getTransactionid() {
        return this.transactionid;
    }
    public void setTransactionid ( long transactionid ) {
        this.transactionid = transactionid;
    }

    public double getUnitdiscamount() {
        return this.unitdiscamount;
    }
    public void setUnitdiscamount ( double unitdiscamount ) {
        this.unitdiscamount = unitdiscamount;
    }

    public double getUnitprice() {
        return this.unitprice;
    }
    public void setUnitprice ( double unitprice ) {
        this.unitprice = unitprice;
    }

}