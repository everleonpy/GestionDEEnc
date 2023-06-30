package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbDatgralope;

public class EbDatgralopeDAO {
    public static ApplicationMessage addRow ( EbDatgralope o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_DATGRALOPE (");
            buffer.append(" CREATED_BY, CREATED_ON, DE_ID, DFEEMIDE,");
            buffer.append(" IDENTIFIER, MODIFIED_BY, MODIFIED_ON, ORG_ID,");
            buffer.append(" UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(3, o.getDeId());
            ps.setTimestamp(4, new Timestamp(o.getDfeemide().getTime()));
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
            ps.setLong(9, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbDatgralopeDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbDatgralopeDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
