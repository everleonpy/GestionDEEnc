package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbDe;

public class EbDeDAO {

    public static ApplicationMessage addRow ( EbDe o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_DE (");
            buffer.append(" CREATED_BY,CREATED_ON,DDVID,DFECFIRMA,");
            buffer.append(" DSISFACT,ID,IDENTIFIER,MODIFIED_BY,");
            buffer.append(" MODIFIED_ON,ORG_ID,RDE_ID,UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,?,?,?,");
            buffer.append(" ?,? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(3, o.getDdvid());
            ps.setTimestamp(4, new Timestamp(o.getDfecfirma().getTime()));
            ps.setShort(5, o.getDsisfact());
            ps.setString(6, o.getId());
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
            ps.setLong(11, o.getRdeId());
            ps.setLong(12, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbDeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbDeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
