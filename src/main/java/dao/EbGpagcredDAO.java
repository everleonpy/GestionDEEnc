package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGpagcred;

public class EbGpagcredDAO {

    public static ApplicationMessage addRow ( EbGpagcred o, Connection conn ) {
        PreparedStatement ps = null;
        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GPAGCRED (");
            buffer.append(" CAMCOND_ID, CREATED_BY, CREATED_ON, DCUOTAS,");
            buffer.append(" DDCONDCRED, DMONENT, DPLAZOCRE, ICONDCRED,");
            buffer.append(" IDENTIFIER, MODIFIED_BY, MODIFIED_ON, ORG_ID,");
            buffer.append(" UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setLong(1, o.getCamcondId());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getDcuotas() != 0) {
                ps.setInt(4, o.getDcuotas());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setString(5, o.getDdcondcred());
            if (o.getDmonent() != 0.0) {
                ps.setDouble(6, o.getDmonent());
            } else {
                ps.setNull(6, java.sql.Types.DOUBLE);
            }
            if (o.getDplazocre() != null) {
                ps.setString(7, o.getDplazocre());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            ps.setInt(8, o.getIcondcred());
            ps.setLong(9, o.getIdentifier());
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
            ApplicationMessage m = new ApplicationMessage ( "EbGpagcredDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGpagcredDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
