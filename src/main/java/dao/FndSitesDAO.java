package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import business.AppConfig;
import dao.ProblemaDatosException;
import pojo.FndSite;
import util.ListOfValuesItem;

public class FndSitesDAO {

	/**
	 * Este metodo tiene como objetivo obtener los datos de un sitio de proveedor 
	 * accediendo por nombre mas id. de proveedor
	 * 
	 * @author jLcc
	 *
	 */
	public static FndSite getRow ( long siteId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select ADDRESS1, ADDRESS2, ADDRESS3, AREA,");
	        buffer.append(" AREA_CODE, AREA_UOM_ID, ATTRIBUTE1, ATTRIBUTE2,");
	        buffer.append(" ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, BRANCH_ACCT_ID,");
	        buffer.append(" CITY, CODE, COUNTRY, CREATED_BY,");
	        buffer.append("  to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON, DESCRIPTION, DFLT_ISSUE_WHSE_ID, DFLT_RECEIPT_WHSE_ID,");
	        buffer.append(" DONATION_BENEF_NAME, ENABLE_IN_TRANSIT, IDENTIFIER, INT_SHIP_ISSUE_POINT,");
	        buffer.append(" INT_SHIP_STAMP_ID, INVOICING_CODE, LOGISTICS_PROV_ID, MAIN_FLAG,");
	        buffer.append(" MAIN_WHSE_ID, MODIFIED_BY,  to_char(MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON, NAME,");
	        buffer.append(" ORDINAL_NO, ORG_ID, PHONE1, PHONE2,");
	        buffer.append(" PHONE3, PRICES_SITE_ID, PROPAGATE_PRICES, REPORTING_CODE,");
	        buffer.append(" SCALE_FILE_DEF, SYSTEM_ITEM, UNIT_ID");
	        buffer.append(" from FND_SITES");
	        buffer.append(" where IDENTIFIER = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, siteId);
	        rs = ps.executeQuery();

	        FndSite o = new FndSite();

	        if (rs.next()) {
	            dataFound = true;
	            o.setADDRESS1(rs.getString("ADDRESS1"));
	            o.setADDRESS2(rs.getString("ADDRESS2"));
	            o.setADDRESS3(rs.getString("ADDRESS3"));
	            o.setAREA(rs.getDouble("AREA"));
	            o.setAREA_CODE(rs.getString("AREA_CODE"));
	            o.setAREA_UOM_ID(rs.getLong("AREA_UOM_ID"));
	            o.setATTRIBUTE1(rs.getString("ATTRIBUTE1"));
	            o.setATTRIBUTE2(rs.getString("ATTRIBUTE2"));
	            o.setATTRIBUTE3(rs.getString("ATTRIBUTE3"));
	            o.setATTRIBUTE4(rs.getString("ATTRIBUTE4"));
	            o.setATTRIBUTE5(rs.getString("ATTRIBUTE5"));
	            o.setBRANCH_ACCT_ID(rs.getLong("BRANCH_ACCT_ID"));
	            o.setCITY(rs.getString("CITY"));
	            o.setCODE(rs.getString("CODE"));
	            o.setCOUNTRY(rs.getString("COUNTRY"));
	            o.setCREATED_BY(rs.getString("CREATED_BY"));
	            if ( rs.getString("CREATED_ON") != null ) {
	                d = sdf.parse(rs.getString("CREATED_ON"));
	                o.setCREATED_ON(d);
	            }
	            o.setDESCRIPTION(rs.getString("DESCRIPTION"));
	            o.setDFLT_ISSUE_WHSE_ID(rs.getLong("DFLT_ISSUE_WHSE_ID"));
	            o.setDFLT_RECEIPT_WHSE_ID(rs.getLong("DFLT_RECEIPT_WHSE_ID"));
	            o.setDONATION_BENEF_NAME(rs.getString("DONATION_BENEF_NAME"));
	            o.setENABLE_IN_TRANSIT(rs.getString("ENABLE_IN_TRANSIT"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setINT_SHIP_ISSUE_POINT(rs.getString("INT_SHIP_ISSUE_POINT"));
	            o.setINT_SHIP_STAMP_ID(rs.getLong("INT_SHIP_STAMP_ID"));
	            o.setINVOICING_CODE(rs.getString("INVOICING_CODE"));
	            o.setLOGISTICS_PROV_ID(rs.getLong("LOGISTICS_PROV_ID"));
	            o.setMAIN_FLAG(rs.getString("MAIN_FLAG"));
	            o.setMAIN_WHSE_ID(rs.getLong("MAIN_WHSE_ID"));
	            o.setMODIFIED_BY(rs.getString("MODIFIED_BY"));
	            if ( rs.getString("MODIFIED_ON") != null ) {
	                d = sdf.parse(rs.getString("MODIFIED_ON"));
	                o.setMODIFIED_ON(d);
	            }
	            o.setNAME(rs.getString("NAME"));
	            o.setORDINAL_NO(rs.getLong("ORDINAL_NO"));
	            o.setORG_ID(rs.getLong("ORG_ID"));
	            o.setPHONE1(rs.getString("PHONE1"));
	            o.setPHONE2(rs.getString("PHONE2"));
	            o.setPHONE3(rs.getString("PHONE3"));
	            o.setPRICES_SITE_ID(rs.getLong("PRICES_SITE_ID"));
	            o.setPROPAGATE_PRICES(rs.getString("PROPAGATE_PRICES"));
	            o.setREPORTING_CODE(rs.getString("REPORTING_CODE"));
	            o.setSCALE_FILE_DEF(rs.getString("SCALE_FILE_DEF"));
	            o.setSYSTEM_ITEM(rs.getString("SYSTEM_ITEM"));
	            o.setUNIT_ID(rs.getLong("UNIT_ID"));
	        }
	        if (dataFound == true) {
	            return o;
	        } else {
	        	return null;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
	    }
	}
	
	/**
	 * Este metodo tiene como objetivo obtener los datos de un sitio de proveedor 
	 * accediendo por nombre mas id. de proveedor
	 * 
	 * @author jLcc
	 *
	 */
	public static FndSite getRowByName ( String name, long unitId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select ADDRESS1, ADDRESS2, ADDRESS3, AREA,");
	        buffer.append(" AREA_CODE, AREA_UOM_ID, ATTRIBUTE1, ATTRIBUTE2,");
	        buffer.append(" ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, BRANCH_ACCT_ID,");
	        buffer.append(" CITY, CODE, COUNTRY, CREATED_BY,");
	        buffer.append(" to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON, DESCRIPTION, DFLT_ISSUE_WHSE_ID, DFLT_RECEIPT_WHSE_ID,");
	        buffer.append(" DONATION_BENEF_NAME, ENABLE_IN_TRANSIT, IDENTIFIER, INT_SHIP_ISSUE_POINT,");
	        buffer.append(" INT_SHIP_STAMP_ID, INVOICING_CODE, LOGISTICS_PROV_ID, MAIN_FLAG,");
	        buffer.append(" MAIN_WHSE_ID, MODIFIED_BY,  to_char(MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON, NAME,");
	        buffer.append(" ORDINAL_NO, ORG_ID, PHONE1, PHONE2,");
	        buffer.append(" PHONE3, PRICES_SITE_ID, PROPAGATE_PRICES, REPORTING_CODE,");
	        buffer.append(" SCALE_FILE_DEF, SYSTEM_ITEM, UNIT_ID");
	        buffer.append(" from FND_SITES");
	        buffer.append(" where UNIT_ID = ?");
	        if (AppConfig.dbEngine.equalsIgnoreCase("ORACLE")) {
	            buffer.append(" and upper(NAME) like ?");
	        }
	        if (AppConfig.dbEngine.equalsIgnoreCase("POSTGRESQL")) {
	            buffer.append(" and upper(NAME) like ?");
	        }

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, unitId);
	        ps.setString(2, name.toUpperCase() + "%");
	        rs = ps.executeQuery();

	        FndSite o = new FndSite();

	        if (rs.next()) {
	            dataFound = true;
	            o.setADDRESS1(rs.getString("ADDRESS1"));
	            o.setADDRESS2(rs.getString("ADDRESS2"));
	            o.setADDRESS3(rs.getString("ADDRESS3"));
	            o.setAREA(rs.getDouble("AREA"));
	            o.setAREA_CODE(rs.getString("AREA_CODE"));
	            o.setAREA_UOM_ID(rs.getLong("AREA_UOM_ID"));
	            o.setATTRIBUTE1(rs.getString("ATTRIBUTE1"));
	            o.setATTRIBUTE2(rs.getString("ATTRIBUTE2"));
	            o.setATTRIBUTE3(rs.getString("ATTRIBUTE3"));
	            o.setATTRIBUTE4(rs.getString("ATTRIBUTE4"));
	            o.setATTRIBUTE5(rs.getString("ATTRIBUTE5"));
	            o.setBRANCH_ACCT_ID(rs.getLong("BRANCH_ACCT_ID"));
	            o.setCITY(rs.getString("CITY"));
	            o.setCODE(rs.getString("CODE"));
	            o.setCOUNTRY(rs.getString("COUNTRY"));
	            o.setCREATED_BY(rs.getString("CREATED_BY"));
	            if ( rs.getString("CREATED_ON") != null ) {
	                d = sdf.parse(rs.getString("CREATED_ON"));
	                o.setCREATED_ON(d);
	            }
	            o.setDESCRIPTION(rs.getString("DESCRIPTION"));
	            o.setDFLT_ISSUE_WHSE_ID(rs.getLong("DFLT_ISSUE_WHSE_ID"));
	            o.setDFLT_RECEIPT_WHSE_ID(rs.getLong("DFLT_RECEIPT_WHSE_ID"));
	            o.setDONATION_BENEF_NAME(rs.getString("DONATION_BENEF_NAME"));
	            o.setENABLE_IN_TRANSIT(rs.getString("ENABLE_IN_TRANSIT"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setINT_SHIP_ISSUE_POINT(rs.getString("INT_SHIP_ISSUE_POINT"));
	            o.setINT_SHIP_STAMP_ID(rs.getLong("INT_SHIP_STAMP_ID"));
	            o.setINVOICING_CODE(rs.getString("INVOICING_CODE"));
	            o.setLOGISTICS_PROV_ID(rs.getLong("LOGISTICS_PROV_ID"));
	            o.setMAIN_FLAG(rs.getString("MAIN_FLAG"));
	            o.setMAIN_WHSE_ID(rs.getLong("MAIN_WHSE_ID"));
	            o.setMODIFIED_BY(rs.getString("MODIFIED_BY"));
	            if ( rs.getString("MODIFIED_ON") != null ) {
	                d = sdf.parse(rs.getString("MODIFIED_ON"));
	                o.setMODIFIED_ON(d);
	            }
	            o.setNAME(rs.getString("NAME"));
	            o.setORDINAL_NO(rs.getLong("ORDINAL_NO"));
	            o.setORG_ID(rs.getLong("ORG_ID"));
	            o.setPHONE1(rs.getString("PHONE1"));
	            o.setPHONE2(rs.getString("PHONE2"));
	            o.setPHONE3(rs.getString("PHONE3"));
	            o.setPRICES_SITE_ID(rs.getLong("PRICES_SITE_ID"));
	            o.setPROPAGATE_PRICES(rs.getString("PROPAGATE_PRICES"));
	            o.setREPORTING_CODE(rs.getString("REPORTING_CODE"));
	            o.setSCALE_FILE_DEF(rs.getString("SCALE_FILE_DEF"));
	            o.setSYSTEM_ITEM(rs.getString("SYSTEM_ITEM"));
	            o.setUNIT_ID(rs.getLong("UNIT_ID"));
	        }
	        if (dataFound == true) {
	            return o;
	        } else {
	        	return null;
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
	    }
	}
	
	public static boolean existsObject ( long objectId ) {
		
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
		    buffer.append(" from FND_SITES");
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
			return true;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}
	
	/**
	 * Este metodo tiene como objetivo obtener los datos de un sitio de proveedor 
	 * accediendo por nombre mas id. de proveedor
	 * 
	 * @author jLcc
	 *
	 */
	public static ArrayList<FndSite> getListByName ( String siteName, long unitId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        String s = siteName + "%";
	        
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("select IDENTIFIER, CODE, NAME, DESCRIPTION");
	        buffer.append(" from FND_SITES");
	        buffer.append(" where UNIT_ID = ?");
	        buffer.append(" and upper(DESCRIPTION) like ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, unitId);
	        ps.setString(2, s);
	        rs = ps.executeQuery();

	        ArrayList<FndSite> l = new ArrayList<FndSite>();
	        while (rs.next()) {
	            dataFound = true;
		        FndSite o = new FndSite();
	            o.setDESCRIPTION(rs.getString("DESCRIPTION"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setNAME(rs.getString("NAME"));
	            o.setCODE(rs.getString("CODE"));
	            //
	            l.add(o);
	        }
	        if (dataFound == true) {
	            return l;
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
	
	public static ArrayList<ListOfValuesItem> getList ( String searchString, long unitId ) { 
		
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
			    buffer.append("select v.NAME, v.CODE, v.IDENTIFIER" );
			    buffer.append(" from FND_SITES v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    buffer.append(" order by v.NAME");
			} else {
				searchString = searchString.toUpperCase();
			    buffer.append("select v.NAME, v.CODE, v.IDENTIFIER" );
			    buffer.append(" from FND_SITES v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    if (AppConfig.dbEngine.equalsIgnoreCase("ORACLE")) {
				    buffer.append(" and upper(v.NAME) like '%" + searchString + "%'");
			    }
			    if (AppConfig.dbEngine.equalsIgnoreCase("POSTGRESQL")) {
				    buffer.append(" and upper(v.NAME) like '%" + searchString + "%'");
			    }
			    buffer.append(" order by v.NAME");				
			}	
			//System.out.println(buffer.toString());
			stmtConsulta = conn.prepareStatement(buffer.toString());
			rsConsulta = stmtConsulta.executeQuery();
			
			while ( rsConsulta.next() ) {
				item = new ListOfValuesItem();
				item.setDescription(rsConsulta.getString(1));
				item.setCode(rsConsulta.getString(2));
				item.setIdentifier(rsConsulta.getLong(3));
				s.add(item);
			}			
			return s;
						
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}

}
