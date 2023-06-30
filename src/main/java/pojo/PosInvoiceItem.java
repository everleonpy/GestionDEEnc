package pojo;

public class PosInvoiceItem {
	private long itemId;
	private long productId;
	private String internalCode;
	private String itemDescription;
	private String qtyHandling;
	private double quantity;
	private double unitPrice;
	private double discountAmt;
	private double discountPct;
	private double unitDiscount;
	private long taxTypeId;
	private double taxRate;
	private short uomCode;
	private String uomName;
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getInternalCode() {
		return internalCode;
	}
	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getQtyHandling() {
		return qtyHandling;
	}
	public void setQtyHandling(String qtyHandling) {
		this.qtyHandling = qtyHandling;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(double discountAmt) {
		this.discountAmt = discountAmt;
	}
	public double getDiscountPct() {
		return discountPct;
	}
	public void setDiscountPct(double discountPct) {
		this.discountPct = discountPct;
	}
	public double getUnitDiscount() {
		return unitDiscount;
	}
	public void setUnitDiscount(double unitDiscount) {
		this.unitDiscount = unitDiscount;
	}
	public long getTaxTypeId() {
		return taxTypeId;
	}
	public void setTaxTypeId(long taxTypeId) {
		this.taxTypeId = taxTypeId;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public short getUomCode() {
		return uomCode;
	}
	public void setUomCode(short uomCode) {
		this.uomCode = uomCode;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	
}
