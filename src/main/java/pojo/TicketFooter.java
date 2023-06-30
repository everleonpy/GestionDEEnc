package pojo;

import java.util.Vector;

public class TicketFooter {
	private String currencyCode;
	private int ccyDecimals;
	private double EXCEMPT_TOTAL;
	private double TAXABLE_TOTAL;
	private double TAXABLE5_TOTAL;
	private double TAXABLE10_TOTAL;
	private double TAX_TOTAL;
	private double TAX5_TOTAL;
	private double TAX10_TOTAL;
	private double DISCOUNT_TOTAL;
	private double AMOUNT_TOTAL;
	//
	private String CUSTOMER_NUMBER;
	private String CUSTOMER_NAME;
	private String TAX_PAYER_NUMBER;
	private String TAX_PAYER_NAME;
	private String TRX_CONDITION;
	private double PRODUCTS_QUANTITY;
	private double GROSS_TOTAL;
	private double NET_TOTAL;
	private double DONATION_AMOUNT;
	private String DONATION_CONCEPT;
	private double RESIDUAL_AMOUNT;
	private Vector<GenericElement> collectionDetail;
	private String EMPLOYEE_NO;
	private String EMPLOYEE_NAME;
	private Vector<GenericElement> discountDetail;
	private Vector<String> promoDetail; 
	private Vector<DrawingCoupon> drawingCoupons;
	//
	private long KITCHEN_ORDER_NO;
	
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
    //
	public double getEXCEMPT_TOTAL() {
		return EXCEMPT_TOTAL;
	}
	public void setEXCEMPT_TOTAL(double eXCEMPT_TOTAL) {
		EXCEMPT_TOTAL = eXCEMPT_TOTAL;
	}
	public double getTAXABLE_TOTAL() {
		return TAXABLE_TOTAL;
	}
	public void setTAXABLE_TOTAL(double tAXABLE_TOTAL) {
		TAXABLE_TOTAL = tAXABLE_TOTAL;
	}
	public double getTAXABLE5_TOTAL() {
		return TAXABLE5_TOTAL;
	}
	public void setTAXABLE5_TOTAL(double tAXABLE5_TOTAL) {
		TAXABLE5_TOTAL = tAXABLE5_TOTAL;
	}
	public double getTAXABLE10_TOTAL() {
		return TAXABLE10_TOTAL;
	}
	public void setTAXABLE10_TOTAL(double tAXABLE10_TOTAL) {
		TAXABLE10_TOTAL = tAXABLE10_TOTAL;
	}
	public double getTAX_TOTAL() {
		return TAX_TOTAL;
	}
	public void setTAX_TOTAL(double tAX_TOTAL) {
		TAX_TOTAL = tAX_TOTAL;
	}
	public double getTAX5_TOTAL() {
		return TAX5_TOTAL;
	}
	public void setTAX5_TOTAL(double tAX5_TOTAL) {
		TAX5_TOTAL = tAX5_TOTAL;
	}
	public double getTAX10_TOTAL() {
		return TAX10_TOTAL;
	}
	public void setTAX10_TOTAL(double tAX10_TOTAL) {
		TAX10_TOTAL = tAX10_TOTAL;
	}
	public double getDISCOUNT_TOTAL() {
		return DISCOUNT_TOTAL;
	}
	public void setDISCOUNT_TOTAL(double dISCOUNT_TOTAL) {
		DISCOUNT_TOTAL = dISCOUNT_TOTAL;
	}
	public double getAMOUNT_TOTAL() {
		return AMOUNT_TOTAL;
	}
	public void setAMOUNT_TOTAL(double aMOUNT_TOTAL) {
		AMOUNT_TOTAL = aMOUNT_TOTAL;
	}
	public String getCUSTOMER_NUMBER() {
		return CUSTOMER_NUMBER;
	}
	public void setCUSTOMER_NUMBER(String cUSTOMER_NUMBER) {
		CUSTOMER_NUMBER = cUSTOMER_NUMBER;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getTAX_PAYER_NUMBER() {
		return TAX_PAYER_NUMBER;
	}
	public void setTAX_PAYER_NUMBER(String tAX_PAYER_NUMBER) {
		TAX_PAYER_NUMBER = tAX_PAYER_NUMBER;
	}
	public String getTAX_PAYER_NAME() {
		return TAX_PAYER_NAME;
	}
	public void setTAX_PAYER_NAME(String tAX_PAYER_NAME) {
		TAX_PAYER_NAME = tAX_PAYER_NAME;
	}
	public String getTRX_CONDITION() {
		return TRX_CONDITION;
	}
	public void setTRX_CONDITION(String tRX_CONDITION) {
		TRX_CONDITION = tRX_CONDITION;
	}
	public double getPRODUCTS_QUANTITY() {
		return PRODUCTS_QUANTITY;
	}
	public void setPRODUCTS_QUANTITY(double pRODUCTS_QUANTITY) {
		PRODUCTS_QUANTITY = pRODUCTS_QUANTITY;
	}
	public double getGROSS_TOTAL() {
		return GROSS_TOTAL;
	}
	public void setGROSS_TOTAL(double gROSS_TOTAL) {
		GROSS_TOTAL = gROSS_TOTAL;
	}
	public double getNET_TOTAL() {
		return NET_TOTAL;
	}
	public void setNET_TOTAL(double nET_TOTAL) {
		NET_TOTAL = nET_TOTAL;
	}
	public double getDONATION_AMOUNT() {
		return DONATION_AMOUNT;
	}
	public void setDONATION_AMOUNT(double dONATION_AMOUNT) {
		DONATION_AMOUNT = dONATION_AMOUNT;
	}
	public String getDONATION_CONCEPT() {
		return DONATION_CONCEPT;
	}
	public void setDONATION_CONCEPT(String dONATION_CONCEPT) {
		DONATION_CONCEPT = dONATION_CONCEPT;
	}
	public double getRESIDUAL_AMOUNT() {
		return RESIDUAL_AMOUNT;
	}
	public void setRESIDUAL_AMOUNT(double rESIDUAL_AMOUNT) {
		RESIDUAL_AMOUNT = rESIDUAL_AMOUNT;
	}
	public Vector<GenericElement> getCollectionDetail() {
		return collectionDetail;
	}
	public void setCollectionDetail(Vector<GenericElement> collectionDetail) {
		this.collectionDetail = collectionDetail;
	}
	public Vector<GenericElement> getDiscountDetail() {
		return discountDetail;
	}
	public void setDiscountDetail(Vector<GenericElement> discountDetail) {
		this.discountDetail = discountDetail;
	}
	public Vector<String> getPromoDetail() {
		return promoDetail;
	}
	public void setPromoDetail(Vector<String> promoDetail) {
		this.promoDetail = promoDetail;
	}
	public String getEMPLOYEE_NO() {
		return EMPLOYEE_NO;
	}
	public void setEMPLOYEE_NO(String eMPLOYEE_NO) {
		EMPLOYEE_NO = eMPLOYEE_NO;
	}
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	public void setEMPLOYEE_NAME(String eMPLOYEE_NAME) {
		EMPLOYEE_NAME = eMPLOYEE_NAME;
	}
	public Vector<DrawingCoupon> getDrawingCoupons() {
		return drawingCoupons;
	}
	public void setDrawingCoupons(Vector<DrawingCoupon> drawingCoupons) {
		this.drawingCoupons = drawingCoupons;
	}
	public long getKITCHEN_ORDER_NO() {
		return KITCHEN_ORDER_NO;
	}
	public void setKITCHEN_ORDER_NO(long kITCHEN_ORDER_NO) {
		KITCHEN_ORDER_NO = kITCHEN_ORDER_NO;
	}

}
