package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.Util;
import dao.ProblemaDatosException;
import pojo.BusinessUnit;
import pojo.FndOrgAttribute;
import pojo.Organization;
import pojo.Site;
import pojo.FndUser;

public class UserCredentialsDAO {
	/**
	 * @return User
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */

	public static FndUser getUser( String userName ) throws ProblemaDatosException, Exception {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
	        
			String dbProd = conn.getMetaData().getDatabaseProductName();
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select ACTIVE_FLAG, CONC_SESSIONS, CREATED_BY,  to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON,");
	        buffer.append(" CUSTOMER_ID, DFLT_WHSE_ID, EMPLOYEE_ID, FULL_NAME,");
	        buffer.append(" IDENTIFIER, LANGUAGE_ID, MODIFIED_BY,  to_char(MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON,");
	        buffer.append(" MULTIPLE_BU_FLAG, NAME, ORG_ID, PASSWORD,");
	        buffer.append(" PASS_VALIDITY_DAYS, POS_FLAG, SESSION_FLAG, SITE_ID,");
	        buffer.append(" SYSTEM_ITEM, UNIT_ID, VENDOR_ID");
	        buffer.append(" from FND_USERS");
	        if (dbProd.equalsIgnoreCase("Oracle")) {
	            buffer.append(" where upper(NAME) = upper(?)");
	        }
	        if (dbProd.equalsIgnoreCase("Postgresql")) {
	            buffer.append(" where upper(NAME) = upper(?)");
	        }
	        ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, userName);
	        rs = ps.executeQuery();

	        FndUser o = new FndUser();

	        if (rs.next()) {
	            o.setACTIVE_FLAG(rs.getString("ACTIVE_FLAG"));
	            o.setCONC_SESSIONS(rs.getLong("CONC_SESSIONS"));
	            o.setCREATED_BY(rs.getString("CREATED_BY"));
	            d = sdf.parse(rs.getString("CREATED_ON"));
	            o.setCREATED_ON(d);
	            o.setCUSTOMER_ID(rs.getLong("CUSTOMER_ID"));
	            o.setDFLT_WHSE_ID(rs.getLong("DFLT_WHSE_ID"));
	            o.setEMPLOYEE_ID(rs.getLong("EMPLOYEE_ID"));
	            o.setFULL_NAME(rs.getString("FULL_NAME"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setLANGUAGE_ID(rs.getLong("LANGUAGE_ID"));
	            o.setMODIFIED_BY(rs.getString("MODIFIED_BY"));
	            if ( rs.getString("MODIFIED_ON") != null ) {
	                d = sdf.parse(rs.getString("MODIFIED_ON"));
	                o.setMODIFIED_ON(d);
	            }
	            o.setMULTIPLE_BU_FLAG(rs.getString("MULTIPLE_BU_FLAG"));
	            o.setNAME(rs.getString("NAME"));
	            o.setORG_ID(rs.getLong("ORG_ID"));
	            o.setPASSWORD(rs.getString("PASSWORD"));
	            o.setPASS_VALIDITY_DAYS(rs.getLong("PASS_VALIDITY_DAYS"));
	            o.setPOS_FLAG(rs.getString("POS_FLAG"));
	            o.setSESSION_FLAG(rs.getString("SESSION_FLAG"));
	            o.setSITE_ID(rs.getLong("SITE_ID"));
	            o.setSYSTEM_ITEM(rs.getString("SYSTEM_ITEM"));
	            o.setUNIT_ID(rs.getLong("UNIT_ID"));
	            o.setVENDOR_ID(rs.getLong("VENDOR_ID"));
	        }
	        return o;

	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw e;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
	    }
	}

	public static FndUser getUser( String userName, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
			String dbProd = conn.getMetaData().getDatabaseProductName();
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select ACTIVE_FLAG, CONC_SESSIONS, CREATED_BY,  to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON,");
	        buffer.append(" CUSTOMER_ID, DFLT_WHSE_ID, EMPLOYEE_ID, FULL_NAME,");
	        buffer.append(" IDENTIFIER, LANGUAGE_ID, MODIFIED_BY,  to_char(MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON,");
	        buffer.append(" MULTIPLE_BU_FLAG, NAME, ORG_ID, PASSWORD,");
	        buffer.append(" PASS_VALIDITY_DAYS, POS_FLAG, SESSION_FLAG, SITE_ID,");
	        buffer.append(" SYSTEM_ITEM, UNIT_ID, VENDOR_ID");
	        buffer.append(" from FND_USERS");
	        if (dbProd.equalsIgnoreCase("Oracle")) {
	            buffer.append(" where upper(NAME) = upper(?)");
	        }
	        if (dbProd.equalsIgnoreCase("Postgresql")) {
	            buffer.append(" where upper(NAME) = upper(?)");
	        }
	        ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, userName);
	        rs = ps.executeQuery();

	        FndUser o = new FndUser();

	        if (rs.next()) {
	        	    dataFound = true;
	            o.setACTIVE_FLAG(rs.getString("ACTIVE_FLAG"));
	            o.setCONC_SESSIONS(rs.getLong("CONC_SESSIONS"));
	            o.setCREATED_BY(rs.getString("CREATED_BY"));
	            d = sdf.parse(rs.getString("CREATED_ON"));
	            o.setCREATED_ON(d);
	            o.setCUSTOMER_ID(rs.getLong("CUSTOMER_ID"));
	            o.setDFLT_WHSE_ID(rs.getLong("DFLT_WHSE_ID"));
	            o.setEMPLOYEE_ID(rs.getLong("EMPLOYEE_ID"));
	            o.setFULL_NAME(rs.getString("FULL_NAME"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setLANGUAGE_ID(rs.getLong("LANGUAGE_ID"));
	            o.setMODIFIED_BY(rs.getString("MODIFIED_BY"));
	            if ( rs.getString("MODIFIED_ON") != null ) {
	                d = sdf.parse(rs.getString("MODIFIED_ON"));
	                o.setMODIFIED_ON(d);
	            }
	            o.setMULTIPLE_BU_FLAG(rs.getString("MULTIPLE_BU_FLAG"));
	            o.setNAME(rs.getString("NAME"));
	            o.setORG_ID(rs.getLong("ORG_ID"));
	            o.setPASSWORD(rs.getString("PASSWORD"));
	            o.setPASS_VALIDITY_DAYS(rs.getLong("PASS_VALIDITY_DAYS"));
	            o.setPOS_FLAG(rs.getString("POS_FLAG"));
	            o.setSESSION_FLAG(rs.getString("SESSION_FLAG"));
	            o.setSITE_ID(rs.getLong("SITE_ID"));
	            o.setSYSTEM_ITEM(rs.getString("SYSTEM_ITEM"));
	            o.setUNIT_ID(rs.getLong("UNIT_ID"));
	            o.setVENDOR_ID(rs.getLong("VENDOR_ID"));
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
	    }
	}

	public static long getCashierId ( String userName, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    long itemId = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER");
	        buffer.append(" from POS_CASHIERS");
	        buffer.append(" where upper(USER_NAME) = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, userName.toUpperCase());
	        rs = ps.executeQuery();

	        if (rs.next()) {
	        	itemId = rs.getLong(1);
	        }
	        return itemId;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	    }
	}
	
	public static long getHeadtellerId ( String userName, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    long itemId = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER");
	        buffer.append(" from POS_HEAD_TELLERS");
	        buffer.append(" where upper(USER_NAME) = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, userName.toUpperCase());
	        rs = ps.executeQuery();

	        if (rs.next()) {
	        	itemId = rs.getLong(1);
	        }
	        return itemId;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	    }
	}

	public static long getSellerId ( String userName, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    long itemId = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER");
	        buffer.append(" from POS_SELLERS");
	        buffer.append(" where upper(USER_NAME) = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, userName.toUpperCase());
	        rs = ps.executeQuery();

	        if (rs.next()) {
	        	itemId = rs.getLong(1);
	        }
	        return itemId;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	    }
	}

	/**
	 * 
	 * @param unitId
	 * @return BusinessUnit
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */
	public static BusinessUnit getBusinessUnit ( long unitId, Connection conn ) {
	    PreparedStatement stmtConsulta = null;
	    ResultSet rsConsulta = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, NAME, ORG_ID, DESCRIPTION,");
	        buffer.append(" OFFICIAL_CURRENCY_ID, LANGUAGE_ID, CREATED_BY, CREATED_ON,");
	        buffer.append(" MODIFIED_BY, MODIFIED_ON, ALTERNATIVE_NAME, ADDRESS1,");
	        buffer.append(" ADDRESS2, ADDRESS3, AREA_CODE, PHONE1,");
	        buffer.append(" PHONE2, PHONE3, TAX_NUMBER, IDENTITY_NUMBER,");
	        buffer.append(" CITY, COUNTRY, E_MAIL, WEB_SITE,");
	        buffer.append(" SYSTEM_ITEM, ITEM_NUMBER, PERIOD_CLOSURE_DAY, SECOND_CURRENCY_ID,");
	        buffer.append(" THIRD_CURRENCY_ID, SEC_LANGUAGE_ID");
	        buffer.append(" from FND_BUSINESS_UNITS");
	        buffer.append(" where IDENTIFIER = ?");

	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setLong(1, unitId);
	        rsConsulta = stmtConsulta.executeQuery();

	        BusinessUnit c = new BusinessUnit();

	        if (rsConsulta.next()) {
	        	    dataFound = true;
	            c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
	            c.setNAME(rsConsulta.getString("NAME"));
	            c.setORG_ID(rsConsulta.getLong("ORG_ID"));
	            c.setDESCRIPTION(rsConsulta.getString("DESCRIPTION"));
	            c.setOFFICIAL_CURRENCY_ID(rsConsulta.getLong("OFFICIAL_CURRENCY_ID"));
	            c.setLANGUAGE_ID(rsConsulta.getLong("LANGUAGE_ID"));
	            c.setCREATED_BY(rsConsulta.getString("CREATED_BY"));
	            c.setCREATED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setMODIFIED_BY(rsConsulta.getString("MODIFIED_BY"));
	            c.setMODIFIED_ON(rsConsulta.getDate("MODIFIED_ON"));
	            c.setALTERNATIVE_NAME(rsConsulta.getString("ALTERNATIVE_NAME"));
	            c.setADDRESS1(rsConsulta.getString("ADDRESS1"));
	            c.setAREA_CODE(rsConsulta.getString("AREA_CODE"));
	            c.setPHONE1(rsConsulta.getString("PHONE1"));
	            c.setTAX_NUMBER(rsConsulta.getString("TAX_NUMBER"));
	            c.setIDENTITY_NUMBER(rsConsulta.getString("IDENTITY_NUMBER"));
	            c.setCITY(rsConsulta.getString("CITY"));
	            c.setCOUNTRY(rsConsulta.getString("COUNTRY"));
	            c.setSYSTEM_ITEM(rsConsulta.getString("SYSTEM_ITEM"));
	            c.setITEM_NUMBER(rsConsulta.getLong("ITEM_NUMBER"));
	            c.setPERIOD_CLOSURE_DAY(rsConsulta.getString("PERIOD_CLOSURE_DAY"));
	            c.setSECOND_CURRENCY_ID(rsConsulta.getLong("SECOND_CURRENCY_ID"));
	            c.setTHIRD_CURRENCY_ID(rsConsulta.getLong("THIRD_CURRENCY_ID"));
	            c.setSEC_LANGUAGE_ID(rsConsulta.getLong("SEC_LANGUAGE_ID"));
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
	        Util.closeResultSet(rsConsulta);
	        Util.closeStatement(stmtConsulta);
	    }
	}

	/**
	 * @param orgId
	 * @return Organization
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */
	
	public static Organization getOrganization ( long orgId, Connection conn ) {
	    PreparedStatement stmtConsulta = null;
	    ResultSet rsConsulta = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, NAME, OFFICIAL_CURRENCY_ID, LANGUAGE_ID,");
	        buffer.append(" CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON,");
	        buffer.append(" IS_WITHHOLDING_AGENT, IS_SELF_PRINTER, ALTERNATIVE_NAME, ADDRESS1,");
	        buffer.append(" ADDRESS2, ADDRESS3, AREA_CODE, PHONE1,");
	        buffer.append(" PHONE2, PHONE3, TAX_NUMBER, IDENTITY_NUMBER,");
	        buffer.append(" CITY, COUNTRY, PRN_UNIT_ID, E_MAIL,");
	        buffer.append(" WEB_SITE, SYSTEM_ITEM, SECOND_CURRENCY_ID, THIRD_CURRENCY_ID,");
	        buffer.append(" SEC_LANGUAGE_ID, ECONOMIC_ACTIVITY");
	        buffer.append(" from FND_ORGANIZATIONS");
	        buffer.append(" where IDENTIFIER = ?");

	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setLong(1, orgId);
	        rsConsulta = stmtConsulta.executeQuery();

	        Organization c = new Organization();

	        if (rsConsulta.next()) {
	        	    dataFound = true;
	            c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
	            c.setNAME(rsConsulta.getString("NAME"));
	            c.setOFFICIAL_CURRENCY_ID(rsConsulta.getLong("OFFICIAL_CURRENCY_ID"));
	            c.setLANGUAGE_ID(rsConsulta.getLong("LANGUAGE_ID"));
	            c.setCREATED_BY(rsConsulta.getString("CREATED_BY"));
	            c.setCREATED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setMODIFIED_BY(rsConsulta.getString("MODIFIED_BY"));
	            c.setMODIFIED_ON(rsConsulta.getDate("MODIFIED_ON"));
	            c.setIS_WITHHOLDING_AGENT(rsConsulta.getString("IS_WITHHOLDING_AGENT"));
	            c.setIS_SELF_PRINTER(rsConsulta.getString("IS_SELF_PRINTER"));
	            c.setALTERNATIVE_NAME(rsConsulta.getString("ALTERNATIVE_NAME"));
	            c.setADDRESS1(rsConsulta.getString("ADDRESS1"));
	            c.setAREA_CODE(rsConsulta.getString("AREA_CODE"));
	            c.setPHONE1(rsConsulta.getString("PHONE1"));
	            c.setTAX_NUMBER(rsConsulta.getString("TAX_NUMBER"));
	            c.setIDENTITY_NUMBER(rsConsulta.getString("IDENTITY_NUMBER"));
	            c.setCITY(rsConsulta.getString("CITY"));
	            c.setCOUNTRY(rsConsulta.getString("COUNTRY"));
	            c.setPRN_UNIT_ID(rsConsulta.getLong("PRN_UNIT_ID"));
	            c.setSYSTEM_ITEM(rsConsulta.getString("SYSTEM_ITEM"));
	            c.setSECOND_CURRENCY_ID(rsConsulta.getLong("SECOND_CURRENCY_ID"));
	            c.setTHIRD_CURRENCY_ID(rsConsulta.getLong("THIRD_CURRENCY_ID"));
	            c.setSEC_LANGUAGE_ID(rsConsulta.getLong("SEC_LANGUAGE_ID"));
	            c.setECONOMIC_ACTIVITY(rsConsulta.getString("ECONOMIC_ACTIVITY"));
	            // obtener la lista de actividades economicas secundarias
	            c.setSEC_ACTIVITIES(UserCredentialsDAO.getOrgAttributes(orgId, "ACT-ECON-SECUND", conn));
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
	        Util.closeResultSet(rsConsulta);
	        Util.closeStatement(stmtConsulta);
	    }
	}

	/**
	 * @param orgId
	 * @return Organization
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */
	
	public static ArrayList<FndOrgAttribute> getOrgAttributes ( long orgId, String attrType, Connection conn ) {
	    PreparedStatement stmtConsulta = null;
	    ResultSet rsConsulta = null;
	    FndOrgAttribute c;
	    ArrayList<FndOrgAttribute> l;
	    boolean dataFound = false;

	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, ORG_ID, ATTR_TYPE, ATTR_VALUE,");
	        buffer.append(" CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON");
	        buffer.append(" from FND_ORG_ATTRIBUTES");
	        buffer.append(" where ATTR_TYPE = ?");
	        buffer.append(" and ORG_ID = ?");

	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setString(1, attrType);
	        stmtConsulta.setLong(2, orgId);
	        rsConsulta = stmtConsulta.executeQuery();

	        l = new ArrayList<FndOrgAttribute>();

	        while (rsConsulta.next()) {
	        	c = new FndOrgAttribute();
	            c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
	            c.setORG_ID(rsConsulta.getLong("ORG_ID"));
	            c.setATTR_TYPE(rsConsulta.getString("ATTR_TYPE"));
	            c.setATTR_VALUE(rsConsulta.getString("ATTR_VALUE"));
	            c.setCREATED_BY(rsConsulta.getString("CREATED_BY"));
	            c.setCREATED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setMODIFIED_BY(rsConsulta.getString("MODIFIED_BY"));
	            c.setMODIFIED_ON(rsConsulta.getDate("MODIFIED_ON"));
	            l.add(c);
	            dataFound = true;
	        }
	        if (dataFound == true) {
	            return l;
	        } else {
	        	    return null;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rsConsulta);
	        Util.closeStatement(stmtConsulta);
	    }
	}
	
	/**
	 * @param siteId
	 * @return Site
	 * @throws ProblemaDatosException
	 * @throws Exception
	 **/

	public static Site getSite ( long siteId, Connection conn ) {
	    PreparedStatement stmtConsulta = null;
	    ResultSet rsConsulta = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select CREATED_ON, MODIFIED_BY, MODIFIED_ON, PHONE3,");
	        buffer.append(" CITY, COUNTRY, INVOICING_CODE, SYSTEM_ITEM,");
	        buffer.append(" ENABLE_IN_TRANSIT, AREA, AREA_UOM_ID, REPORTING_CODE,");
	        buffer.append(" ATTRIBUTE1, ATTRIBUTE2, ATTRIBUTE3, ATTRIBUTE4,");
	        buffer.append(" ATTRIBUTE5, IDENTIFIER, NAME, ORG_ID,");
	        buffer.append(" UNIT_ID, CODE, CREATED_BY, DESCRIPTION,");
	        buffer.append(" ADDRESS1, ADDRESS2, ADDRESS3, AREA_CODE,");
	        buffer.append(" PHONE1, PHONE2");
	        buffer.append(" from FND_SITES");
	        buffer.append(" where IDENTIFIER = ?");

	        System.out.println("UserCredentialsDAO.getSite: " + siteId );
	        
	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setLong(1, siteId);
	        rsConsulta = stmtConsulta.executeQuery();

	        Site c = new Site();

	        if (rsConsulta.next()) {
	            c.setCREATED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setMODIFIED_BY(rsConsulta.getString("MODIFIED_BY"));
	            c.setMODIFIED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setCITY(rsConsulta.getString("CITY"));
	            c.setCOUNTRY(rsConsulta.getString("COUNTRY"));
	            c.setINVOICING_CODE(rsConsulta.getString("INVOICING_CODE"));
	            c.setSYSTEM_ITEM(rsConsulta.getString("SYSTEM_ITEM"));
	            c.setREPORTING_CODE(rsConsulta.getString("REPORTING_CODE"));
	            c.setATTRIBUTE1(rsConsulta.getString("ATTRIBUTE1"));
	            c.setATTRIBUTE2(rsConsulta.getString("ATTRIBUTE2"));
	            c.setATTRIBUTE3(rsConsulta.getString("ATTRIBUTE3"));
	            c.setATTRIBUTE4(rsConsulta.getString("ATTRIBUTE4"));
	            c.setATTRIBUTE5(rsConsulta.getString("ATTRIBUTE5"));
	            c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
	            c.setNAME(rsConsulta.getString("NAME"));
	            c.setORG_ID(rsConsulta.getLong("ORG_ID"));
	            c.setUNIT_ID(rsConsulta.getLong("UNIT_ID"));
	            c.setCODE(rsConsulta.getString("CODE"));
	            c.setCREATED_BY(rsConsulta.getString("CREATED_BY"));
	            c.setDESCRIPTION(rsConsulta.getString("DESCRIPTION"));
	            c.setADDRESS1(rsConsulta.getString("ADDRESS1"));
	            c.setAREA_CODE(rsConsulta.getString("AREA_CODE"));
	            c.setPHONE1(rsConsulta.getString("PHONE1"));
	        }
	        System.out.println("UserCredentialsDAO found site: " + c.getIDENTIFIER() + " - " + c.getNAME());
	        if (dataFound == true) {
	            return c;
	        } else {
	        	    return null;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rsConsulta);
	        Util.closeStatement(stmtConsulta);
	    }
	}

	public static int updatePassword ( String userName, String password ) throws Exception {

		Connection conn =  null;
		PreparedStatement stmtUpdate = null;
		int rows = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			String dbProd = conn.getMetaData().getDatabaseProductName();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update FND_USERS");
			buffer.append(" set PASSWORD = ?");   
			if (dbProd.equalsIgnoreCase("Oracle")) {
			    buffer.append(" where upper(NAME) = upper(?)");			
			}
			if (dbProd.equalsIgnoreCase("Postgresql")) {
			    buffer.append(" where upper(NAME) = upper(?)");			
			}
			stmtUpdate = conn.prepareStatement(buffer.toString());

			stmtUpdate.setString(1, password);
			stmtUpdate.setString(2, userName.toUpperCase());
			rows = stmtUpdate.executeUpdate();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static boolean validPassword ( String userName, String password ) throws ProblemaDatosException, Exception {
	    Connection conn =  null;
	    PreparedStatement stmtConsulta = null;
	    ResultSet rsConsulta = null;
	    String pass = null;

	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
	        
			String dbProd = conn.getMetaData().getDatabaseProductName();
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select PASSWORD");
	        buffer.append(" from FND_USERS");
			if (dbProd.equalsIgnoreCase("Oracle")) {
			    buffer.append(" where upper(NAME) = upper(?)");			
			}
			if (dbProd.equalsIgnoreCase("Postgresql")) {
			    buffer.append(" where upper(NAME) = upper(?)");			
			}

	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setString(1, userName);
	        rsConsulta = stmtConsulta.executeQuery();

	        if (rsConsulta.next()) {
	            pass = rsConsulta.getString("PASSWORD");
	        }
	        if (pass == null) {
	        	return false;
	        } else {
	        	if (pass.equals(password)) {
	        		return true;
	        	} else {
	        		return false;
	        	}
	        }

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
