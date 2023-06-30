package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import business.AppConfig;
import dao.ProblemaDatosException;
import pojo.RcvCustomerSite;
import util.ListOfValuesItem;

public class RcvCustomerSitesDAO {

	/**
	 * Este metodo tiene como objetivo obtener los datos de un cliente 
	 * accediendo por identificador
	 * 
	 * @author jLcc
	 *
	 */
    public static RcvCustomerSite getRow ( long siteId ) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        RcvCustomerSite o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();

            buffer.append("select ACTIVE_FLAG, ADDRESS1, ADDRESS2, ADDRESS3,");
            buffer.append(" ALTERNATIVE_NAME, AMOUNTS_INCL_TAX, AREA_CODE, BILLING_SITE_ID,");
            buffer.append(" CITY, CODE, COLLECTIONS_FLAG, COLLECT_CCY_ID,");
            buffer.append(" COLLECT_GROUP, COLLECT_METHOD, COLLECT_PRIORITY, COUNTRY,");
            buffer.append(" CREATED_BY, to_char(CREATED_ON, 'DD/MM/YYYY HH24:MI:SS') CREATED_ON, CREDIT_LIMIT_AMT, CUSTOMER_CCID,");
            buffer.append(" CUSTOMER_ID, E_MAIL, FC_CUSTOMER_CCID, FORMATION_DATE,");
            buffer.append(" FREIGHT_CARRIER_ID, IDENTIFIER, IDENTITY_NUMBER, INVOICES_CCY_ID,");
            buffer.append(" INVOICE_MAX_AMOUNT, MODIFIED_BY, to_char(MODIFIED_ON, 'DD/MM/YYYY HH24:MI:SS') MODIFIED_ON, NAME,");
            buffer.append(" ORG_ID, PHONE1, PHONE2, PHONE3,");
            buffer.append(" REPORTING_CODE, REPORTING_NAME, SALES_FLAG, SALES_ORDERS_FLAG,");
            buffer.append(" SHIPPING_FLAG, SHIPPING_SITE_ID, SO_MAX_AMOUNT, SO_MIN_AMOUNT,");
            buffer.append(" SUBSTITUTE_RCPT_FLAG, TAX_CALC_FLAG, TAX_NUMBER, TAX_ROUNDING_RULE,");
            buffer.append(" UNIT_ID, WEB_SITE, WITHHOLDING_FLAG ");
            buffer.append(" from RCV_CUSTOMER_SITES");
            buffer.append(" where IDENTIFIER = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, siteId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dataFound = true;
                o = new RcvCustomerSite();
                o.setActiveFlag(rs.getString("ACTIVE_FLAG"));
                o.setAddress1(rs.getString("ADDRESS1"));
                o.setAddress2(rs.getString("ADDRESS2"));
                o.setAddress3(rs.getString("ADDRESS3"));
                o.setAlternativeName(rs.getString("ALTERNATIVE_NAME"));
                o.setAmountsInclTax(rs.getString("AMOUNTS_INCL_TAX"));
                o.setAreaCode(rs.getString("AREA_CODE"));
                o.setBillingSiteId(rs.getLong("BILLING_SITE_ID"));
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
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setEMail(rs.getString("E_MAIL"));
                o.setFcCustomerCcid(rs.getLong("FC_CUSTOMER_CCID"));
                o.setFormationDate(rs.getDate("FORMATION_DATE"));
                o.setFreightCarrierId(rs.getLong("FREIGHT_CARRIER_ID"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
                o.setInvoicesCcyId(rs.getLong("INVOICES_CCY_ID"));
                o.setInvoiceMaxAmount(rs.getDouble("INVOICE_MAX_AMOUNT"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setName(rs.getString("NAME"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setPhone1(rs.getString("PHONE1"));
                o.setPhone2(rs.getString("PHONE2"));
                o.setPhone3(rs.getString("PHONE3"));
                o.setReportingCode(rs.getString("REPORTING_CODE"));
                o.setReportingName(rs.getString("REPORTING_NAME"));
                o.setSalesFlag(rs.getString("SALES_FLAG"));
                o.setSalesOrdersFlag(rs.getString("SALES_ORDERS_FLAG"));
                o.setShippingFlag(rs.getString("SHIPPING_FLAG"));
                o.setShippingSiteId(rs.getLong("SHIPPING_SITE_ID"));
                o.setSoMaxAmount(rs.getDouble("SO_MAX_AMOUNT"));
                o.setSoMinAmount(rs.getDouble("SO_MIN_AMOUNT"));
                o.setSubstituteRcptFlag(rs.getString("SUBSTITUTE_RCPT_FLAG"));
                o.setTaxCalcFlag(rs.getString("TAX_CALC_FLAG"));
                o.setTaxNumber(rs.getString("TAX_NUMBER"));
                o.setTaxRoundingRule(rs.getString("TAX_ROUNDING_RULE"));
                o.setUnitId(rs.getLong("UNIT_ID"));
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

    public static RcvCustomerSite getRow ( String code, long customerId ) {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        RcvCustomerSite o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();

            buffer.append("select ACTIVE_FLAG, ADDRESS1, ADDRESS2, ADDRESS3,");
            buffer.append(" ALTERNATIVE_NAME, AMOUNTS_INCL_TAX, AREA_CODE, BILLING_SITE_ID,");
            buffer.append(" CITY, CODE, COLLECTIONS_FLAG, COLLECT_CCY_ID,");
            buffer.append(" COLLECT_GROUP, COLLECT_METHOD, COLLECT_PRIORITY, COUNTRY,");
            buffer.append(" CREATED_BY, to_char(CREATED_ON, 'DD/MM/YYYY HH24:MI:SS') CREATED_ON, CREDIT_LIMIT_AMT, CUSTOMER_CCID,");
            buffer.append(" CUSTOMER_ID, E_MAIL, FC_CUSTOMER_CCID, FORMATION_DATE,");
            buffer.append(" FREIGHT_CARRIER_ID, IDENTIFIER, IDENTITY_NUMBER, INVOICES_CCY_ID,");
            buffer.append(" INVOICE_MAX_AMOUNT, MODIFIED_BY, to_char(MODIFIED_ON, 'DD/MM/YYYY HH24:MI:SS') MODIFIED_ON, NAME,");
            buffer.append(" ORG_ID, PHONE1, PHONE2, PHONE3,");
            buffer.append(" REPORTING_CODE, REPORTING_NAME, SALES_FLAG, SALES_ORDERS_FLAG,");
            buffer.append(" SHIPPING_FLAG, SHIPPING_SITE_ID, SO_MAX_AMOUNT, SO_MIN_AMOUNT,");
            buffer.append(" SUBSTITUTE_RCPT_FLAG, TAX_CALC_FLAG, TAX_NUMBER, TAX_ROUNDING_RULE,");
            buffer.append(" UNIT_ID, WEB_SITE, WITHHOLDING_FLAG ");
            buffer.append(" from RCV_CUSTOMER_SITES");
            buffer.append(" where CUSTOMER_ID = ?");
            buffer.append(" and CODE = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, customerId);
	        ps.setString(2, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                dataFound = true;
                o = new RcvCustomerSite();
                o.setActiveFlag(rs.getString("ACTIVE_FLAG"));
                o.setAddress1(rs.getString("ADDRESS1"));
                o.setAddress2(rs.getString("ADDRESS2"));
                o.setAddress3(rs.getString("ADDRESS3"));
                o.setAlternativeName(rs.getString("ALTERNATIVE_NAME"));
                o.setAmountsInclTax(rs.getString("AMOUNTS_INCL_TAX"));
                o.setAreaCode(rs.getString("AREA_CODE"));
                o.setBillingSiteId(rs.getLong("BILLING_SITE_ID"));
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
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setEMail(rs.getString("E_MAIL"));
                o.setFcCustomerCcid(rs.getLong("FC_CUSTOMER_CCID"));
                o.setFormationDate(rs.getDate("FORMATION_DATE"));
                o.setFreightCarrierId(rs.getLong("FREIGHT_CARRIER_ID"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
                o.setInvoicesCcyId(rs.getLong("INVOICES_CCY_ID"));
                o.setInvoiceMaxAmount(rs.getDouble("INVOICE_MAX_AMOUNT"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setName(rs.getString("NAME"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setPhone1(rs.getString("PHONE1"));
                o.setPhone2(rs.getString("PHONE2"));
                o.setPhone3(rs.getString("PHONE3"));
                o.setReportingCode(rs.getString("REPORTING_CODE"));
                o.setReportingName(rs.getString("REPORTING_NAME"));
                o.setSalesFlag(rs.getString("SALES_FLAG"));
                o.setSalesOrdersFlag(rs.getString("SALES_ORDERS_FLAG"));
                o.setShippingFlag(rs.getString("SHIPPING_FLAG"));
                o.setShippingSiteId(rs.getLong("SHIPPING_SITE_ID"));
                o.setSoMaxAmount(rs.getDouble("SO_MAX_AMOUNT"));
                o.setSoMinAmount(rs.getDouble("SO_MIN_AMOUNT"));
                o.setSubstituteRcptFlag(rs.getString("SUBSTITUTE_RCPT_FLAG"));
                o.setTaxCalcFlag(rs.getString("TAX_CALC_FLAG"));
                o.setTaxNumber(rs.getString("TAX_NUMBER"));
                o.setTaxRoundingRule(rs.getString("TAX_ROUNDING_RULE"));
                o.setUnitId(rs.getLong("UNIT_ID"));
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
			buffer.append("delete from RCV_CUSTOMER_SITES");
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
		    buffer.append(" from RCV_CUSTOMER_SITES");
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
			    buffer.append("select v.CODE, v.NAME" );
			    buffer.append(" from RCV_CUSTOMER_SITES v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    buffer.append(" order by v.NAME");
			} else {
				searchString = searchString.toUpperCase();
				buffer.append("Select v.CODE, v.NAME" );
			    buffer.append(" from RCV_CUSTOMERS_SITES v" );
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
