package pojo;

public class TicketDetail {
	/*
	 * Esta clase tiene como objetivo proveer el contenido de las lineas
	 * de detalle de un comprobante para las rutinas de impresion
	 */
	private int itemNumber;
	private String productCode;
	private String barCode;
	private String prodDescription;
	private String uomDescription;
	private double itemQuantity;
	private double unitPrice;
	private double excemptAmount;
	private double taxableAmount;
	private double taxable5Amount;
	private double taxable10Amount;	
	private double taxAmount;
	private double discountAmount;
	private double itemAmount;
	private double taxTypeRate;
	private double taxPercent;
	private double discountPercent;
	private String itemType;
	private String serviceCode;
	private String serviceDescription;
	
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getProdDescription() {
		return prodDescription;
	}
	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}
	public String getUomDescription() {
		return uomDescription;
	}
	public void setUomDescription(String uomDescription) {
		this.uomDescription = uomDescription;
	}
	public double getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getExcemptAmount() {
		return excemptAmount;
	}
	public void setExcemptAmount(double excemptAmount) {
		this.excemptAmount = excemptAmount;
	}
	public double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public double getTaxable5Amount() {
		return taxable5Amount;
	}
	public void setTaxable5Amount(double taxable5Amount) {
		this.taxable5Amount = taxable5Amount;
	}
	public double getTaxable10Amount() {
		return taxable10Amount;
	}
	public void setTaxable10Amount(double taxable10Amount) {
		this.taxable10Amount = taxable10Amount;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(double itemAmount) {
		this.itemAmount = itemAmount;
	}
	public double getTaxTypeRate() {
		return taxTypeRate;
	}
	public void setTaxTypeRate(double taxTypeRate) {
		this.taxTypeRate = taxTypeRate;
	}
	public double getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(double taxPercent) {
		this.taxPercent = taxPercent;
	}
	public double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
}
