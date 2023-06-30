package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pojo.PosCollection;

public class PosCollectionsDAO {

	public static PosCollection getRow ( long invoiceId, long controlId, long cashId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, COLL_TYPE_ID,");
	        buffer.append(" to_char(COLL_DATE, 'dd/mm/yyyy hh24:mi:ss') COLL_DATE, COLLECTION_NO, CASH_CONTROL_ID, SITE_ID,");
	        buffer.append(" CASH_REGISTER_ID, CASHIER_ID, CREATED_BY, to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON,");
	        buffer.append(" CURRENCY_ID, WORK_SHIFT_ID, EXCHANGE_RATE, CASH_AMOUNT,");
	        buffer.append(" CHECKS_AMOUNT, OTHERS_AMOUNT, CHANGE_AMOUNT, ROUNDING_AMOUNT,");
	        buffer.append(" INVOICE_AMOUNT, REAL_AMOUNT, CHANGE_DONATION_AMT, CUSTOMER_ID,");
	        buffer.append(" CUSTOMER_SITE_ID, INVOICE_ID, ATTRIBUTE1, ATTRIBUTE2,");
	        buffer.append(" ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, RESIDUAL_DISCOUNT,");
	        buffer.append(" ROUNDED_CHGE_AMOUNT, CHANGE_COMPL_AMOUNT");
	        buffer.append(" from POS_COLLECTIONS");	        
	        buffer.append(" where CASH_REGISTER_ID = ?");
	        buffer.append(" and CASH_CONTROL_ID = ?");
	        buffer.append(" and INVOICE_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, cashId);
	        ps.setLong(2, controlId);
	        ps.setLong(3, invoiceId);
	        rs = ps.executeQuery();

	        PosCollection c = new PosCollection();

	        if (rs.next()) {
	        	dataFound = true;
	            c.setIdentifier(rs.getLong("IDENTIFIER"));
	            c.setOrgId(rs.getLong("ORG_ID"));
	            c.setUnitId(rs.getLong("UNIT_ID"));
	            c.setCollTypeId(rs.getLong("COLL_TYPE_ID"));
	            d = sdf.parse(rs.getString("COLL_DATE"));
	            c.setCollDate(d);
	            c.setCollectionNo(rs.getString("COLLECTION_NO"));
	            c.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
	            c.setSiteId(rs.getLong("SITE_ID"));
	            c.setCashRegisterId(rs.getLong("CASH_REGISTER_ID"));
	            c.setCashierId(rs.getLong("CASHIER_ID"));
	            c.setCreatedBy(rs.getString("CREATED_BY"));
	            d = sdf.parse(rs.getString("CREATED_ON"));
	            c.setCreatedOn(d);
	            c.setCurrencyId(rs.getLong("CURRENCY_ID"));
	            c.setWorkShiftId(rs.getLong("WORK_SHIFT_ID"));
	            c.setExchangeRate(rs.getDouble("EXCHANGE_RATE"));
	            c.setCashAmount(rs.getDouble("CASH_AMOUNT"));
	            c.setChecksAmount(rs.getDouble("CHECKS_AMOUNT"));
	            c.setOthersAmount(rs.getDouble("OTHERS_AMOUNT"));
	            c.setChangeAmount(rs.getDouble("CHANGE_AMOUNT"));
	            c.setRoundingAmount(rs.getDouble("ROUNDING_AMOUNT"));
	            c.setInvoiceAmount(rs.getDouble("INVOICE_AMOUNT"));
	            c.setRealAmount(rs.getDouble("REAL_AMOUNT"));
	            c.setChangeDonationAmt(rs.getDouble("CHANGE_DONATION_AMT"));
	            c.setCustomerId(rs.getLong("CUSTOMER_ID"));
	            c.setCustomerSiteId(rs.getLong("CUSTOMER_SITE_ID"));
	            c.setInvoiceId(rs.getLong("INVOICE_ID"));
	            c.setAttribute1(rs.getString("ATTRIBUTE1"));
	            c.setAttribute2(rs.getString("ATTRIBUTE2"));
	            c.setAttribute3(rs.getString("ATTRIBUTE3"));
	            c.setAttribute4(rs.getString("ATTRIBUTE4"));
	            c.setAttribute5(rs.getString("ATTRIBUTE5"));
	            c.setResidualDiscount(rs.getDouble("RESIDUAL_DISCOUNT"));
	            c.setRoundedChgeAmount(rs.getDouble("ROUNDED_CHGE_AMOUNT"));
	            c.setChangeComplAmount(rs.getDouble("CHANGE_COMPL_AMOUNT"));
	        }
	        if (dataFound == true) {
	            return c;
	        } else {
	        	return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	    }	
	}
	
}
