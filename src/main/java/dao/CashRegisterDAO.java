package dao;
  import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import business.AppConfig;
import pojo.CashRegister;
import util.ListOfValuesItem;

  public class CashRegisterDAO {

	/**
	 * Este metodo tiene como objetivo obtener los datos de una caja 
	 * accediendo por el identificador de caja
	 * 
	 * @author jLcc
	 *
	 */
	public static CashRegister getRow ( long cashRegisterId ) {
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

	        buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, CR_NUMBER,");
	        buffer.append(" DESCRIPTION, SITE_ID, WORKSTATION_NAME, ACTIVE_FLAG,");
	        buffer.append(" CREATED_BY, CREATED_ON, MODIFIED_ON, MODIFIED_BY,");
	        buffer.append(" ISSUE_POINT_ID, CR_GROUP_CODE, SEQUENCE_NUMBER, WAREHOUSE_ID,");
	        buffer.append(" SALES_ROOM_AREA, ADVERTISING_FLAG, GEN_CUST_ORDER_NO");
	        buffer.append(" from POS_CASH_REGISTERS");
	        buffer.append(" where IDENTIFIER = ?");

	        stmtConsulta = conn.prepareStatement(buffer.toString());
	        stmtConsulta.setLong(1, cashRegisterId);	        
	        rsConsulta = stmtConsulta.executeQuery();

	        CashRegister c = new CashRegister();

	        if (rsConsulta.next()) {
	        	dataFound = true;
	            c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
	            c.setORG_ID(rsConsulta.getLong("ORG_ID"));
	            c.setUNIT_ID(rsConsulta.getLong("UNIT_ID"));
	            c.setCR_NUMBER(rsConsulta.getString("CR_NUMBER"));
	            c.setDESCRIPTION(rsConsulta.getString("DESCRIPTION"));
	            c.setSITE_ID(rsConsulta.getLong("SITE_ID"));
	            c.setWORKSTATION_NAME(rsConsulta.getString("WORKSTATION_NAME"));
	            c.setACTIVE_FLAG(rsConsulta.getString("ACTIVE_FLAG"));
	            c.setCREATED_BY(rsConsulta.getString("CREATED_BY"));
	            c.setCREATED_ON(rsConsulta.getDate("CREATED_ON"));
	            c.setMODIFIED_ON(rsConsulta.getDate("MODIFIED_ON"));
	            c.setMODIFIED_BY(rsConsulta.getString("MODIFIED_BY"));
	            c.setISSUE_POINT_ID(rsConsulta.getLong("ISSUE_POINT_ID"));
	            c.setCR_GROUP_CODE(rsConsulta.getString("CR_GROUP_CODE"));
	            c.setSEQUENCE_NUMBER(rsConsulta.getLong("SEQUENCE_NUMBER"));
	            c.setWAREHOUSE_ID(rsConsulta.getLong("WAREHOUSE_ID"));
	            c.setSALES_ROOM_AREA(rsConsulta.getString("SALES_ROOM_AREA"));
	            if (rsConsulta.getString("ADVERTISING_FLAG") == null) {
	            	c.setADVERTISING_FLAG("N");
	            } else {
	            	c.setADVERTISING_FLAG(rsConsulta.getString("ADVERTISING_FLAG"));
	            }
	            if (rsConsulta.getString("GEN_CUST_ORDER_NO") == null) {
	            	c.setGEN_CUST_ORDER_NO("N");
	            } else {
	            	c.setGEN_CUST_ORDER_NO(rsConsulta.getString("GEN_CUST_ORDER_NO"));
	            }
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
	        Util.closeJDBCConnection(conn);
	    }	
	}

	public static ArrayList<CashRegister> getListByName ( String cashName, long unitId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        String s = cashName + "%";
	        
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("select IDENTIFIER, CR_NUMBER, DESCRIPTION");
	        buffer.append(" from POS_CASH_REGISTERS");
	        buffer.append(" where UNIT_ID = ?");
	        buffer.append(" and upper(DESCRIPTION) like ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, unitId);
	        ps.setString(2, s);
	        rs = ps.executeQuery();

	        ArrayList<CashRegister> l = new ArrayList<CashRegister>();
	        while (rs.next()) {
	            dataFound = true;
	            CashRegister o = new CashRegister();
	            o.setDESCRIPTION(rs.getString("DESCRIPTION"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setCR_NUMBER(rs.getString("CR_NUMBER"));
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
			    buffer.append("select v.DESCRIPTION, v.CR_NUMBER, v.IDENTIFIER" );
			    buffer.append(" from POS_CASH_REGISTERS v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    buffer.append(" order by v.DESCRIPTION");
			} else {
				searchString = searchString.toUpperCase();
			    buffer.append("select v.DESCRIPTION, v.CR_NUMBER, v.IDENTIFIER" );
			    buffer.append(" from POS_CASH_REGISTERS v" );
			    buffer.append(" where v.UNIT_ID = " + String.valueOf(unitId));
			    if (AppConfig.dbEngine.equalsIgnoreCase("ORACLE")) {
				    buffer.append(" and upper(v.DESCRIPTION) like '%" + searchString + "%'");
			    }
			    if (AppConfig.dbEngine.equalsIgnoreCase("POSTGRESQL")) {
				    buffer.append(" and upper(v.DESCRIPTION) like '%" + searchString + "%'");
			    }
			    buffer.append(" order by v.DESCRIPTION");				
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
