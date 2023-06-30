package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGvaloritem;

public class EbGvaloritemDAO {

    public static ApplicationMessage addRow ( EbGvaloritem o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GVALORITEM (");
            buffer.append(" CAMITEM_ID, CREATED_BY, CREATED_ON, DPUNIPROSER,");
            buffer.append(" DTICAMIT, DTOTBRUOPEITEM, IDENTIFIER, MODIFIED_BY,");
            buffer.append(" MODIFIED_ON, ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setLong(1, o.getCamitemId());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            ps.setDouble(4, o.getDpuniproser());
            if (o.getDticamit() != 0.0) {
                ps.setDouble(5, o.getDticamit());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
            }
            ps.setDouble(6, o.getDtotbruopeitem());
            ps.setLong(7, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(8, o.getModifiedBy());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(9, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }
            ps.setLong(10, o.getOrgId());
            ps.setLong(11, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGvaloritemDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGvaloritemDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
