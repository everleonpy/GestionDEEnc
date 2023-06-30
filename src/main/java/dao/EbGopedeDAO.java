package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGopede;

public class EbGopedeDAO {
    public static ApplicationMessage addRow ( EbGopede o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GOPEDE (");
            buffer.append(" CREATED_BY, CREATED_ON, DCODSEG, DDESTIPEMI,");
            buffer.append(" DE_ID, DINFOEMI, DINFOFISC, IDENTIFIER,");
            buffer.append(" ITIPEMI, MODIFIED_BY, MODIFIED_ON, ORG_ID,");
            buffer.append(" UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(3, o.getDcodseg());
            ps.setString(4, o.getDdestipemi());
            ps.setLong(5, o.getDeId());
            if (o.getDinfoemi() != null) {
                ps.setString(6, o.getDinfoemi());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            if (o.getDinfofisc() != null) {
                ps.setString(7, o.getDinfofisc());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            ps.setLong(8, o.getIdentifier());
            ps.setInt(9, o.getItipemi());
            if (o.getModifiedBy() != null) {
                ps.setString(10, o.getModifiedBy());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(11, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(11, java.sql.Types.DATE);
            }
            ps.setLong(12, o.getOrgId());
            ps.setLong(13, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGopedeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGopedeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
