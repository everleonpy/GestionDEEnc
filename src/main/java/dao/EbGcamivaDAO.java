package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGcamiva;

public class EbGcamivaDAO {

    public static ApplicationMessage addRow ( EbGcamiva o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GCAMIVA (");
            buffer.append(" CAMITEM_ID, CREATED_BY, CREATED_ON, DBASGRAVIVA,");
            buffer.append(" DDESAFECIVA, DLIQIVAITEM, DPROPIVA, DTASAIVA,");
            buffer.append(" IAFECIVA, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GCAMIVA", conn);

            ps.setLong(1, o.getCamitemId());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            ps.setDouble(4, o.getDbasgraviva());
            ps.setString(5, o.getDdesafeciva());
            ps.setDouble(6, o.getDliqivaitem());
            ps.setLong(7, o.getDpropiva());
            ps.setInt(8, o.getDtasaiva());
            ps.setInt(9, o.getIafeciva());
            ps.setLong(10, itemId);
            if (o.getModifiedBy() != null) {
                ps.setString(11, o.getModifiedBy());
            } else {
                ps.setNull(11, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(12, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(12, java.sql.Types.DATE);
            }
            ps.setLong(13, o.getOrgId());
            ps.setLong(14, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGcamivaDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGcamivaDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }

}
