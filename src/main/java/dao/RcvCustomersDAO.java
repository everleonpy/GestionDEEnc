package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import business.AppConfig;
import dao.ProblemaDatosException;
import pojo.RcvCustomer;
import util.ListOfValuesItem;

public class RcvCustomersDAO {

	/**
	 * Este metodo tiene como objetivo obtener los datos de un cliente 
	 * accediendo por identificador
	 * 
	 * @author jLcc
	 *
	 */
    public static RcvCustomer getRow ( long customerId ) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        RcvCustomer o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();

            buffer.append("select ACCRUED_POINTS_QTY, ACTIVE_FLAG, ADDRESS1, ADDRESS2,");
            buffer.append(" ADDRESS3, ALTERNATIVE_NAME, AMOUNTS_INCL_TAX, AREA_CODE,");
            buffer.append(" BILLING_SITE_ID, CATEGORY_ID, CITY, CODE,");
            buffer.append(" COLLECTIONS_FLAG, COLLECT_CCY_ID, COLLECT_GROUP, COLLECT_METHOD,");
            buffer.append(" COLLECT_PRIORITY, COUNTRY, CREATED_BY, to_char(CREATED_ON, 'DD/MM/YYYY HH24:MI:SS') CREATED_ON,");
            buffer.append(" CREDIT_LIMIT_AMT, CUSTOMER_CCID, CUSTOMER_ORIGIN, CUST_GROUP_ID,");
            buffer.append(" EMPLOYEE_ID, E_MAIL, FC_CUSTOMER_CCID, FIRST_NAME,");
            buffer.append(" FORMATION_DATE, FREIGHT_CARRIER_ID, IDENTIFIER, IDENTITY_NUMBER,");
            buffer.append(" INSTALLMENTS_QTY, INST_DAYS_INTERVAL, INVOICES_CCY_ID, INVOICE_MAX_AMOUNT,");
            buffer.append(" LAST_NAME, LOYALTY_INSCR_DATE, LOYAL_CUSTOMER_FLAG, MODIFIED_BY,");
            buffer.append(" to_char(MODIFIED_ON, 'DD/MM/YYYY HH24:MI:SS') MODIFIED_ON, NAME, NEIGHBORHOOD, ORG_ID,");
            buffer.append(" ORG_TYPE, OWNER_NAME, PHONE1, PHONE2,");
            buffer.append(" PHONE3, REPORTING_CODE, REPORTING_NAME, SALESMAN_ID,");
            buffer.append(" SALES_FLAG, SALES_ORDERS_FLAG, SALES_TERM, SHIPPING_SITE_ID,");
            buffer.append(" SO_MAX_AMOUNT, SO_MIN_AMOUNT, SUBSTITUTE_RCPT_FLAG, TAX_CALC_FLAG,");
            buffer.append(" TAX_INCLUDED_FLAG, TAX_NUMBER, TAX_PAYER_FLAG, TAX_ROUNDING_RULE,");
            buffer.append(" UNIT_ID, VENDOR_ID, WEB_SITE, WITHHOLDING_FLAG )");
            buffer.append(" from RCV_CUSTOMERS");
            buffer.append(" where IDENTIFIER = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, customerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dataFound = true;
                o = new RcvCustomer();
                o.setAccruedPointsQty(rs.getInt("ACCRUED_POINTS_QTY"));
                o.setActiveFlag(rs.getString("ACTIVE_FLAG"));
                o.setAddress1(rs.getString("ADDRESS1"));
                o.setAddress2(rs.getString("ADDRESS2"));
                o.setAddress3(rs.getString("ADDRESS3"));
                o.setAlternativeName(rs.getString("ALTERNATIVE_NAME"));
                o.setAmountsInclTax(rs.getString("AMOUNTS_INCL_TAX"));
                o.setAreaCode(rs.getString("AREA_CODE"));
                o.setBillingSiteId(rs.getLong("BILLING_SITE_ID"));
                o.setCategoryId(rs.getLong("CATEGORY_ID"));
                o.setCity(rs.getString("CITY"));
                o.setCode(rs.getString("CODE"));
                o.setCollectionsFlag(rs.getString("COLLECTIONS_FLAG"));
                o.setCollectCcyId(rs.getLong("COLLECT_CCY_ID"));
                o.setCollectGroup(rs.getString("COLLECT_GROUP"));
                o.setCollectMethod(rs.getString("COLLECT_METHOD"));
                o.setCollectPriority(rs.getInt("COLLECT_PRIORITY"));
                o.setCountry(rs.getString("COUNTRY"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                d = sdf.parse(rs.getString("CREATED_ON"));
                o.setCreatedOn(d);
                o.setCreditLimitAmt(rs.getDouble("CREDIT_LIMIT_AMT"));
                o.setCustomerCcid(rs.getLong("CUSTOMER_CCID"));
                o.setCustomerOrigin(rs.getString("CUSTOMER_ORIGIN"));
                o.setCustGroupId(rs.getLong("CUST_GROUP_ID"));
                o.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
                o.setEMail(rs.getString("E_MAIL"));
                o.setFcCustomerCcid(rs.getLong("FC_CUSTOMER_CCID"));
                o.setFirstName(rs.getString("FIRST_NAME"));
                o.setFormationDate(rs.getDate("FORMATION_DATE"));
                o.setFreightCarrierId(rs.getLong("FREIGHT_CARRIER_ID"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
                o.setInstallmentsQty(rs.getInt("INSTALLMENTS_QTY"));
                o.setInstDaysInterval(rs.getInt("INST_DAYS_INTERVAL"));
                o.setInvoicesCcyId(rs.getLong("INVOICES_CCY_ID"));
                o.setInvoiceMaxAmount(rs.getDouble("INVOICE_MAX_AMOUNT"));
                o.setLastName(rs.getString("LAST_NAME"));
                o.setLoyaltyInscrDate(rs.getDate("LOYALTY_INSCR_DATE"));
                o.setLoyalCustomerFlag(rs.getString("LOYAL_CUSTOMER_FLAG"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setName(rs.getString("NAME"));
                o.setNeighborhood(rs.getString("NEIGHBORHOOD"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setOrgType(rs.getString("ORG_TYPE"));
                o.setOwnerName(rs.getString("OWNER_NAME"));
                o.setPhone1(rs.getString("PHONE1"));
                o.setPhone2(rs.getString("PHONE2"));
                o.setPhone3(rs.getString("PHONE3"));
                o.setReportingCode(rs.getString("REPORTING_CODE"));
                o.setReportingName(rs.getString("REPORTING_NAME"));
                o.setSalesmanId(rs.getLong("SALESMAN_ID"));
                o.setSalesFlag(rs.getString("SALES_FLAG"));
                o.setSalesOrdersFlag(rs.getString("SALES_ORDERS_FLAG"));
                o.setSalesTerm(rs.getString("SALES_TERM"));
                o.setShippingSiteId(rs.getLong("SHIPPING_SITE_ID"));
                o.setSoMaxAmount(rs.getDouble("SO_MAX_AMOUNT"));
                o.setSoMinAmount(rs.getDouble("SO_MIN_AMOUNT"));
                o.setSubstituteRcptFlag(rs.getString("SUBSTITUTE_RCPT_FLAG"));
                o.setTaxCalcFlag(rs.getString("TAX_CALC_FLAG"));
                o.setTaxIncludedFlag(rs.getString("TAX_INCLUDED_FLAG"));
                o.setTaxNumber(rs.getString("TAX_NUMBER"));
                o.setTaxPayerFlag(rs.getString("TAX_PAYER_FLAG"));
                o.setTaxRoundingRule(rs.getString("TAX_ROUNDING_RULE"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.setVendorId(rs.getLong("VENDOR_ID"));
                o.setWebSite(rs.getString("WEB_SITE"));
                o.setWithholdingFlag(rs.getString("WITHHOLDING_FLAG"));
            }
            if (dataFound == true) {
                return o;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
        }
    }
	
	/**
	 * Este metodo tiene como objetivo obtener los datos de un cliente 
	 * accediendo por numero de identidad
	 * 
	 * @author jLcc
	 *
	 */
	public static RcvCustomer getRowByCode ( String identityNumber, long unitId ) throws ProblemaDatosException, Exception{
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        RcvCustomer o = null;
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        StringBuffer buffer = new StringBuffer();

	          buffer.append("select ACCRUED_POINTS_QTY, ACTIVE_FLAG, ADDRESS1, ADDRESS2,");
	            buffer.append(" ADDRESS3, ALTERNATIVE_NAME, AMOUNTS_INCL_TAX, AREA_CODE,");
	            buffer.append(" BILLING_SITE_ID, CATEGORY_ID, CITY, CODE,");
	            buffer.append(" COLLECTIONS_FLAG, COLLECT_CCY_ID, COLLECT_GROUP, COLLECT_METHOD,");
	            buffer.append(" COLLECT_PRIORITY, COUNTRY, CREATED_BY, to_char(CREATED_ON, 'DD/MM/YYYY HH24:MI:SS') CREATED_ON,");
	            buffer.append(" CREDIT_LIMIT_AMT, CUSTOMER_CCID, CUSTOMER_ORIGIN, CUST_GROUP_ID,");
	            buffer.append(" EMPLOYEE_ID, E_MAIL, FC_CUSTOMER_CCID, FIRST_NAME,");
	            buffer.append(" FORMATION_DATE, FREIGHT_CARRIER_ID, IDENTIFIER, IDENTITY_NUMBER,");
	            buffer.append(" INSTALLMENTS_QTY, INST_DAYS_INTERVAL, INVOICES_CCY_ID, INVOICE_MAX_AMOUNT,");
	            buffer.append(" LAST_NAME, LOYALTY_INSCR_DATE, LOYAL_CUSTOMER_FLAG, MODIFIED_BY,");
	            buffer.append(" to_char(MODIFIED_ON, 'DD/MM/YYYY HH24:MI:SS') MODIFIED_ON, NAME, NEIGHBORHOOD, ORG_ID,");
	            buffer.append(" ORG_TYPE, OWNER_NAME, PHONE1, PHONE2,");
	            buffer.append(" PHONE3, REPORTING_CODE, REPORTING_NAME, SALESMAN_ID,");
	            buffer.append(" SALES_FLAG, SALES_ORDERS_FLAG, SALES_TERM, SHIPPING_SITE_ID,");
	            buffer.append(" SO_MAX_AMOUNT, SO_MIN_AMOUNT, SUBSTITUTE_RCPT_FLAG, TAX_CALC_FLAG,");
	            buffer.append(" TAX_INCLUDED_FLAG, TAX_NUMBER, TAX_PAYER_FLAG, TAX_ROUNDING_RULE,");
	            buffer.append(" UNIT_ID, VENDOR_ID, WEB_SITE, WITHHOLDING_FLAG )");
	            buffer.append(" from RCV_CUSTOMERS");
	            buffer.append(" where UNIT_ID = ?");
	            buffer.append(" and IDENTITY_NUMBER = ?");

	            ps = conn.prepareStatement(buffer.toString());
		        ps.setLong(1, unitId);
		        ps.setString(2, identityNumber);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                dataFound = true;
	                o = new RcvCustomer();
	                o.setAccruedPointsQty(rs.getInt("ACCRUED_POINTS_QTY"));
	                o.setActiveFlag(rs.getString("ACTIVE_FLAG"));
	                o.setAddress1(rs.getString("ADDRESS1"));
	                o.setAddress2(rs.getString("ADDRESS2"));
	                o.setAddress3(rs.getString("ADDRESS3"));
	                o.setAlternativeName(rs.getString("ALTERNATIVE_NAME"));
	                o.setAmountsInclTax(rs.getString("AMOUNTS_INCL_TAX"));
	                o.setAreaCode(rs.getString("AREA_CODE"));
	                o.setBillingSiteId(rs.getLong("BILLING_SITE_ID"));
	                o.setCategoryId(rs.getLong("CATEGORY_ID"));
	                o.setCity(rs.getString("CITY"));
	                o.setCode(rs.getString("CODE"));
	                o.setCollectionsFlag(rs.getString("COLLECTIONS_FLAG"));
	                o.setCollectCcyId(rs.getLong("COLLECT_CCY_ID"));
	                o.setCollectGroup(rs.getString("COLLECT_GROUP"));
	                o.setCollectMethod(rs.getString("COLLECT_METHOD"));
	                o.setCollectPriority(rs.getInt("COLLECT_PRIORITY"));
	                o.setCountry(rs.getString("COUNTRY"));
	                o.setCreatedBy(rs.getString("CREATED_BY"));
	                d = sdf.parse(rs.getString("CREATED_ON"));
	                o.setCreatedOn(d);
	                o.setCreditLimitAmt(rs.getDouble("CREDIT_LIMIT_AMT"));
	                o.setCustomerCcid(rs.getLong("CUSTOMER_CCID"));
	                o.setCustomerOrigin(rs.getString("CUSTOMER_ORIGIN"));
	                o.setCustGroupId(rs.getLong("CUST_GROUP_ID"));
	                o.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
	                o.setEMail(rs.getString("E_MAIL"));
	                o.setFcCustomerCcid(rs.getLong("FC_CUSTOMER_CCID"));
	                o.setFirstName(rs.getString("FIRST_NAME"));
	                o.setFormationDate(rs.getDate("FORMATION_DATE"));
	                o.setFreightCarrierId(rs.getLong("FREIGHT_CARRIER_ID"));
	                o.setIdentifier(rs.getLong("IDENTIFIER"));
	                o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
	                o.setInstallmentsQty(rs.getInt("INSTALLMENTS_QTY"));
	                o.setInstDaysInterval(rs.getInt("INST_DAYS_INTERVAL"));
	                o.setInvoicesCcyId(rs.getLong("INVOICES_CCY_ID"));
	                o.setInvoiceMaxAmount(rs.getDouble("INVOICE_MAX_AMOUNT"));
	                o.setLastName(rs.getString("LAST_NAME"));
	                o.setLoyaltyInscrDate(rs.getDate("LOYALTY_INSCR_DATE"));
	                o.setLoyalCustomerFlag(rs.getString("LOYAL_CUSTOMER_FLAG"));
	                o.setModifiedBy(rs.getString("MODIFIED_BY"));
	                if (rs.getString("MODIFIED_ON") != null) {
	                    d = sdf.parse(rs.getString("MODIFIED_ON"));
	                    o.setModifiedOn(d);
	                }
	                o.setName(rs.getString("NAME"));
	                o.setNeighborhood(rs.getString("NEIGHBORHOOD"));
	                o.setOrgId(rs.getLong("ORG_ID"));
	                o.setOrgType(rs.getString("ORG_TYPE"));
	                o.setOwnerName(rs.getString("OWNER_NAME"));
	                o.setPhone1(rs.getString("PHONE1"));
	                o.setPhone2(rs.getString("PHONE2"));
	                o.setPhone3(rs.getString("PHONE3"));
	                o.setReportingCode(rs.getString("REPORTING_CODE"));
	                o.setReportingName(rs.getString("REPORTING_NAME"));
	                o.setSalesmanId(rs.getLong("SALESMAN_ID"));
	                o.setSalesFlag(rs.getString("SALES_FLAG"));
	                o.setSalesOrdersFlag(rs.getString("SALES_ORDERS_FLAG"));
	                o.setSalesTerm(rs.getString("SALES_TERM"));
	                o.setShippingSiteId(rs.getLong("SHIPPING_SITE_ID"));
	                o.setSoMaxAmount(rs.getDouble("SO_MAX_AMOUNT"));
	                o.setSoMinAmount(rs.getDouble("SO_MIN_AMOUNT"));
	                o.setSubstituteRcptFlag(rs.getString("SUBSTITUTE_RCPT_FLAG"));
	                o.setTaxCalcFlag(rs.getString("TAX_CALC_FLAG"));
	                o.setTaxIncludedFlag(rs.getString("TAX_INCLUDED_FLAG"));
	                o.setTaxNumber(rs.getString("TAX_NUMBER"));
	                o.setTaxPayerFlag(rs.getString("TAX_PAYER_FLAG"));
	                o.setTaxRoundingRule(rs.getString("TAX_ROUNDING_RULE"));
	                o.setUnitId(rs.getLong("UNIT_ID"));
	                o.setVendorId(rs.getLong("VENDOR_ID"));
	                o.setWebSite(rs.getString("WEB_SITE"));
	                o.setWithholdingFlag(rs.getString("WITHHOLDING_FLAG"));
	            }

	        if (dataFound == true) {
	            return o;
	        } else {
	        	return null;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw e;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
	    }
	}

	public static int deleteRow ( long itemId ) throws Exception {
		Connection conn = null;
		PreparedStatement stmtUpdate = null;
		int deleted = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("delete from RCV_CUSTOMERS");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setLong(1, itemId);
			deleted = stmtUpdate.executeUpdate();
			return deleted;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtUpdate);
	        Util.closeJDBCConnection(conn);
		}
	}
	
	public static boolean existsObject ( long objectId ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select IDENTIFIER");
		    buffer.append(" from RCV_CUSTOMERS");
		    buffer.append(" where IDENTIFIER = ?");

		    stmtConsulta = conn.prepareStatement(buffer.toString());
		    stmtConsulta.setLong(1, objectId );			

		    rsConsulta = stmtConsulta.executeQuery();
		    if (rsConsulta.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}
	
	public static ArrayList<ListOfValuesItem> getList ( String searchString, long unitId ) throws ProblemaDatosException, Exception { 
		
		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		ListOfValuesItem item;
		
		try {
			
			conn = Util.getConnection();
			if ( conn == null ) {
				throw new SQLException("No hay conexion con Base de Datos");
			}

			StringBuffer buffer = new StringBuffer();
			ArrayList<ListOfValuesItem> s = new ArrayList<ListOfValuesItem>();			
			buffer = new StringBuffer();
			if (searchString == null) {
			    buffer.append("select v.IDENTITY_NUMBER, v.NAME" );
			    buffer.append(" from RCV_CUSTOMERS v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    buffer.append(" order by v.NAME");
			} else {
				searchString = searchString.toUpperCase();
				buffer.append("Select v.IDENTITY_NUMBER, v.NAME" );
			    buffer.append(" from RCV_CUSTOMERS v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    if (AppConfig.dbEngine.equalsIgnoreCase("ORACLE")) {
				    buffer.append(" and upper(v.NAME) like '%" + searchString + "%'");
			    }
			    if (AppConfig.dbEngine.equalsIgnoreCase("POSTGRESQL")) {
				    buffer.append(" and upper(v.NAME) like '%" + searchString + "%'");
			    }
			    buffer.append(" order by v.NAME");				
			}	
			stmtConsulta = conn.prepareStatement(buffer.toString());
			rsConsulta = stmtConsulta.executeQuery();
			
			while ( rsConsulta.next() ) {
				item = new ListOfValuesItem();
				item.setCode(rsConsulta.getString(1));
				item.setDescription(rsConsulta.getString(2));
				s.add(item);
			}			
			return s;
						
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}
	
}
