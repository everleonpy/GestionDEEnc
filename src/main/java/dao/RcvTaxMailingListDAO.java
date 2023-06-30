package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.RcvTaxMailingList;

public class RcvTaxMailingListDAO {
	
    public static RcvTaxMailingList getRowByTaxNumber ( String taxNumber ) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        RcvTaxMailingList o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();              
            
            buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, E_MAIL,");
            buffer.append(" CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" CUSTOMER_ID, TAX_PAYER_NO, IDENTITY_NUMBER, FULL_NAME"); 
            buffer.append(" from RCV_TAX_MAILING_LIST");
            buffer.append(" where TAX_PAYER_NO = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, taxNumber);
            rs = ps.executeQuery();

            System.out.println("contribuyente: " + taxNumber);
            if (rs.next()) {
                dataFound = true;
                o = new RcvTaxMailingList();
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.seteMail(rs.getString("E_MAIL"));
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setTaxPayerNo(rs.getString("TAX_PAYER_NO"));
                o.setFullName(rs.getString("FULL_NAME"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                o.setCreatedOn(rs.getDate("CREATED_ON"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                o.setModifiedOn(rs.getDate("MODIFIED_ON"));     
                System.out.println(o.geteMail());
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
	
    public static RcvTaxMailingList getRowByIdNumber ( String idNumber ) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        RcvTaxMailingList o = null;
        try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
            StringBuffer buffer = new StringBuffer();              
            
            buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, E_MAIL,");
            buffer.append(" CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" CUSTOMER_ID, TAX_PAYER_NO, IDENTITY_NUMBER, FULL_NAME"); 
            buffer.append(" from RCV_TAX_MAILING_LIST");
            buffer.append(" where IDENTITY_NUMBER = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setString(1, idNumber);
            rs = ps.executeQuery();

            System.out.println("identificacion: " + idNumber);
            if (rs.next()) {
                dataFound = true;
                o = new RcvTaxMailingList();
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.seteMail(rs.getString("E_MAIL"));
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setTaxPayerNo(rs.getString("TAX_PAYER_NO"));
                o.setFullName(rs.getString("FULL_NAME"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                o.setCreatedOn(rs.getDate("CREATED_ON"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                o.setModifiedOn(rs.getDate("MODIFIED_ON"));     
                System.out.println(o.geteMail());
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

    public static String getTaxNumberEmail ( String taxNumber, Connection conn ) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		String eMail = null;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.E_MAIL");
			buffer.append(" from RCV_TAX_MAILING_LIST x");
			buffer.append(" where x.TAX_PAYER_NO = ?");			    	
			
			ps = conn.prepareStatement(buffer.toString());			
			ps.setString(1, taxNumber);
			rs = ps.executeQuery();

			if (rs.next()) { 
				dataFound = true;
				eMail = rs.getString("E_MAIL");
			}
			if (dataFound == true) {
				return eMail;
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

	public static String getIdentityNoEmail ( String identityNo, Connection conn ) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		String eMail = null;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.E_MAIL");
			buffer.append(" from RCV_TAX_MAILING_LIST x");
		    buffer.append(" where x.IDENTITY_NUMBER = ?");			    	
			
		    ps = conn.prepareStatement(buffer.toString());			
		    ps.setString(1, identityNo);
			rs = ps.executeQuery();

			if (rs.next()) { 
				dataFound = true;
				eMail = rs.getString("E_MAIL");
			}
			if (dataFound == true) {
				return eMail;
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
