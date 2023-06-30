package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGcamcond;

public class EbGcamcondDAO {

    public static ApplicationMessage addRow ( EbGcamcond o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GCAMCOND (");
            buffer.append(" CREATED_BY, CREATED_ON, DDCONDOPE, ICONDOPE,");
            buffer.append(" IDENTIFIER, MODIFIED_BY, MODIFIED_ON, ORG_ID,");
            buffer.append(" TIPDE_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GCAMCOND", conn);

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(3, o.getDdcondope());
            ps.setInt(4, o.getIcondope());
            ps.setLong(5, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(6, o.getModifiedBy());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(7, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            ps.setLong(8, o.getOrgId());
            ps.setLong(9, o.getTipdeId());
            ps.setLong(10, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGcamcondDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGcamcondDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }

}
