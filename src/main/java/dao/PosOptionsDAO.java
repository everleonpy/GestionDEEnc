package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import pojo.PosOption;

public class PosOptionsDAO {
	
	public static PosOption getRow ( long unitId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, FROM_DATE,");
	        buffer.append(" CREATED_BY, CREATED_ON, TO_DATE, MODIFIED_BY,");
	        buffer.append(" MODIFIED_ON, EB_BATCH_TX_QTY");
	        buffer.append(" from POS_OPTIONS");
	        buffer.append(" where UNIT_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, unitId);
	        rs = ps.executeQuery();

	        PosOption c = new PosOption();

	        if (rs.next()) {
	            dataFound = true;
	            c.setIdentifier(rs.getLong("IDENTIFIER"));
	            c.setOrgId(rs.getLong("ORG_ID"));
	            c.setUnitId(rs.getLong("UNIT_ID"));
	            c.setFromDate(rs.getDate("FROM_DATE"));
	            c.setCreatedBy(rs.getString("CREATED_BY"));
	            c.setCreatedOn(rs.getDate("CREATED_ON"));
	            c.setToDate(rs.getDate("TO_DATE"));
	            c.setModifiedBy(rs.getString("MODIFIED_BY"));
	            c.setModifiedOn(rs.getDate("MODIFIED_ON"));
	            c.setEbBatchTxQty(rs.getInt("EB_BATCH_TX_QTY"));	    
	            System.out.println("Filas por lote: " + c.getEbBatchTxQty());
	        }
	        if (dataFound == true) {
	            return c;
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
	
    public static int addRow ( PosOption o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into POS_OPTIONS (");
            buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, FROM_DATE,");
            buffer.append(" CREATED_BY, CREATED_ON, TO_DATE, MODIFIED_BY,");
            buffer.append(" MODIFIED_ON, EB_BATCH_TX_QTY )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_POS_OPTIONS", conn);

            ps.setLong(1, o.getIdentifier());
            ps.setLong(2, o.getOrgId());
            ps.setLong(3, o.getUnitId());
            ps.setTimestamp(4, new Timestamp(o.getFromDate().getTime()));

            ps.setString(5, o.getCreatedBy());
            ps.setTimestamp(6, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getToDate() != null) {
                ps.setTimestamp(7, new Timestamp(o.getToDate().getTime()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            ps.setString(8, o.getModifiedBy());
            
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(9, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }
            if (o.getEbBatchTxQty() != 0) {
                ps.setLong(10, o.getEbBatchTxQty());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }
            
            int rowsQty = ps.executeUpdate();
            return rowsQty;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Util.closeStatement(ps);
        }
    }

}
