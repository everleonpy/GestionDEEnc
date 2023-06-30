package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGdtipde;

public class EbGdtipdeDAO {

    public static ApplicationMessage addRow ( EbGdtipde o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GDTIPDE (");
            buffer.append(" CREATED_BY, CREATED_ON, DE_ID, IDENTIFIER,");
            buffer.append(" MODIFIED_BY, MODIFIED_ON, ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(3, o.getDeId());
            ps.setLong(4, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(5, o.getModifiedBy());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(6, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            ps.setLong(7, o.getOrgId());
            ps.setLong(8, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGdtipdeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGdtipdeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
