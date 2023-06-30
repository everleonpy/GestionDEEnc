package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGcamfe;

public class EbGcamfeDAO {

    public static ApplicationMessage addRow ( EbGcamfe o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GCAMFE (");
            buffer.append(" CREATED_BY, CREATED_ON, DDESINDPRES, DFECEMNR,");
            buffer.append(" IDENTIFIER, IINDPRES, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, TIPDE_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(3, o.getDdesindpres());
            if (o.getDfecemnr() != null) {
                ps.setTimestamp(4, new Timestamp(o.getDfecemnr().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setLong(5, o.getIdentifier());
            ps.setShort(6, o.getIindpres());
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
            ps.setLong(10, o.getTipdeId());
            ps.setLong(11, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGcamfeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGcamfeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
	
}
