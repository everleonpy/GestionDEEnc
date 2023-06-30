package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbRde;

public class EbRdeDAO {
	
    public static ApplicationMessage addRow ( EbRde o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_RDE (");
            buffer.append(" CASH_CONTROL_ID, CASH_REGISTER_ID, CREATED_BY, CREATED_ON,");
            buffer.append(" DVERFOR, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, TRANSACTION_ID, TRX_TYPE, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            if (o.getCashControlId() != 0) {
                ps.setLong(1, o.getCashControlId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            if (o.getCashRegisterId() != 0) {
                ps.setLong(2, o.getCashRegisterId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setString(3, o.getCreatedBy());
            ps.setTimestamp(4, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(5, o.getDverfor());
            ps.setLong(6, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(7, o.getModifiedBy());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(8, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }
            ps.setLong(9, o.getOrgId());
            ps.setLong(10, o.getTransactionId());
            ps.setString(11, o.getTrxType());
            ps.setLong(12, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbRdeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbRdeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }

}
