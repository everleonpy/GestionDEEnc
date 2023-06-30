package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import pojo.RcvTaxPayer;
import pojo.RcvTrxEbBatch;

public class RcvTaxPayersDAO {
	
	public static int addRow ( RcvTaxPayer o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into RCV_TAX_PAYERS (");
	        buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, TAX_PAYER_NO,");
	        buffer.append(" OLD_TAX_PAYER_NO, FULL_NAME, CREATED_BY, CREATED_ON )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ? )");

	        ps = conn.prepareStatement(buffer.toString());

	        ps.setLong(1, o.getIdentifier());
	        ps.setLong(2, o.getOrgId());
	        ps.setLong(3, o.getUnitId());
	        ps.setString(4, o.getTaxPayerNo());
	        
	        ps.setString(5, o.getOldTaxPayerNo());
	        ps.setString(6, o.getFullName());
	        ps.setString(7, o.getCreatedBy());
	        ps.setTimestamp(8, new Timestamp(o.getCreatedOn().getTime()));
	        
	        inserted = ps.executeUpdate();
	        return inserted;

	    } catch (Exception e) {
	     	e.printStackTrace();
	        throw e;
	    } finally {
	        Util.closeStatement(ps);
	    }
	}

    public static RcvTaxPayer getRow ( String taxNumber ) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        RcvTaxPayer o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();              
            
            buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, TAX_PAYER_NO,");
            buffer.append(" OLD_TAX_PAYER_NO, FULL_NAME, CREATED_BY, CREATED_ON,");
            buffer.append(" MODIFIED_BY, MODIFIED_ON");
            buffer.append(" from RCV_TAX_PAYERS");
            buffer.append(" where TAX_PAYER_NO = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, taxNumber);
            rs = ps.executeQuery();

            System.out.println("contribuyente: " + taxNumber);
            if (rs.next()) {
                dataFound = true;
                o = new RcvTaxPayer();
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.setTaxPayerNo(rs.getString("TAX_PAYER_NO"));
                o.setFullName(rs.getString("FULL_NAME"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                o.setCreatedOn(rs.getDate("CREATED_ON"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                o.setModifiedOn(rs.getDate("MODIFIED_ON"));     
                System.out.println(o.getFullName());
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

}
