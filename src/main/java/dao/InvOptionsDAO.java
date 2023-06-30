package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ProblemaDatosException;
import pojo.InvOptions;

public class InvOptionsDAO {

	/**
	 * Este metodo tiene como objetivo obtener los datos de un sitio de proveedor 
	 * accediendo por nombre mas id. de proveedor
	 * 
	 * @author jLcc
	 *
	 */
	public static InvOptions getRow ( java.util.Date currentDate, long unitId ) throws ProblemaDatosException, Exception{
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

	        buffer.append("select SHPMNT_RCV_MAX_DAYS, POS_QTY_GROUPING, WHSE_TRANSF_SCHEME, WH_TRANSF_APPR_REQ,");
	        buffer.append(" REPL_APPROVE_REQ, REPL_MAX_ITEMS, REPL_MAX_DAYS, PHYS_INV_FAMILY_MATCH,");
	        buffer.append(" to_char(MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON, to_char(TO_DATE, 'dd/mm/yyyy hh24:mi:ss') TO_DATE, ITEMS_DISP_CODE, AUTONUM_FAMILIES,");
	        buffer.append(" AUTONUM_RECEIVINGS, AUTONUM_ITEMS, AUTONUM_TRANS, LOTS_ISSUE_CRITERIA,");
	        buffer.append(" IMAGES_FORMAT, IMAGES_LOCAL_DIR, RECEIPT_PATH, AUTO_RECEIPT_FLAG,");
	        buffer.append(" SHPMNT_MAX_ITEMS, IDENTIFIER, ORG_ID, UNIT_ID,");
	        buffer.append(" to_char(FROM_DATE, 'dd/mm/yyyy hh24:mi:ss') FROM_DATE, CREATED_BY, to_char(CREATED_ON, 'dd/mm/yyyy hh24:mi:ss') CREATED_ON, MODIFIED_BY");
	        buffer.append(" from INV_OPTIONS");
	        buffer.append(" where UNIT_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, unitId);
	        rs = ps.executeQuery();

	        InvOptions o = new InvOptions();

	        if (rs.next()) {
	            dataFound = true;
	            o.setSHPMNT_RCV_MAX_DAYS(rs.getLong("SHPMNT_RCV_MAX_DAYS"));
	            o.setPOS_QTY_GROUPING(rs.getString("POS_QTY_GROUPING"));
	            o.setWHSE_TRANSF_SCHEME(rs.getString("WHSE_TRANSF_SCHEME"));
	            o.setWH_TRANSF_APPR_REQ(rs.getString("WH_TRANSF_APPR_REQ"));
	            o.setREPL_APPROVE_REQ(rs.getString("REPL_APPROVE_REQ"));
	            o.setREPL_MAX_ITEMS(rs.getLong("REPL_MAX_ITEMS"));
	            o.setREPL_MAX_DAYS(rs.getLong("REPL_MAX_DAYS"));
	            o.setPHYS_INV_FAMILY_MATCH(rs.getString("PHYS_INV_FAMILY_MATCH"));
	            if ( rs.getString("MODIFIED_ON") != null ) {
	                d = sdf.parse(rs.getString("MODIFIED_ON"));
	                o.setMODIFIED_ON(d);
	            }
	            if ( rs.getString("TO_DATE") != null ) {
	                d = sdf.parse(rs.getString("TO_DATE"));
	                o.setTO_DATE(d);
	            }
	            o.setITEMS_DISP_CODE(rs.getString("ITEMS_DISP_CODE"));
	            o.setAUTONUM_FAMILIES(rs.getString("AUTONUM_FAMILIES"));
	            o.setAUTONUM_RECEIVINGS(rs.getString("AUTONUM_RECEIVINGS"));
	            o.setAUTONUM_ITEMS(rs.getString("AUTONUM_ITEMS"));
	            o.setAUTONUM_TRANS(rs.getString("AUTONUM_TRANS"));
	            o.setLOTS_ISSUE_CRITERIA(rs.getString("LOTS_ISSUE_CRITERIA"));
	            o.setIMAGES_FORMAT(rs.getString("IMAGES_FORMAT"));
	            o.setIMAGES_LOCAL_DIR(rs.getString("IMAGES_LOCAL_DIR"));
	            o.setRECEIPT_PATH(rs.getString("RECEIPT_PATH"));
	            o.setAUTO_RECEIPT_FLAG(rs.getString("AUTO_RECEIPT_FLAG"));
	            o.setSHPMNT_MAX_ITEMS(rs.getLong("SHPMNT_MAX_ITEMS"));
	            o.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            o.setORG_ID(rs.getLong("ORG_ID"));
	            o.setUNIT_ID(rs.getLong("UNIT_ID"));
	            d = sdf.parse(rs.getString("FROM_DATE"));
	            o.setFROM_DATE(d);
	            o.setCREATED_BY(rs.getString("CREATED_BY"));
	            d = sdf.parse(rs.getString("CREATED_ON"));
	            o.setCREATED_ON(d);
	            o.setMODIFIED_BY(rs.getString("MODIFIED_BY"));

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
		    buffer.append(" from INV_OPTIONS");
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
	
}
