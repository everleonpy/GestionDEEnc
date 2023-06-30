package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGtimb;

public class EbGtimbDAO {
	
    public static ApplicationMessage addRow ( EbGtimb o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GTIMB (");
            buffer.append(" CREATED_BY, CREATED_ON, DDESTIDE, DEST,");
            buffer.append(" DE_ID, DFEINIT, DNUMDOC, DNUMTIM,");
            buffer.append(" DPUNEXP, DSERIENUM, IDENTIFIER, ITIDE,");
            buffer.append(" MODIFIED_BY, MODIFIED_ON, ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,?,?,?,");
            buffer.append(" ?,?,?,?,?,? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GTIMB", conn);

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(3, o.getDdestide());
            ps.setString(4, o.getDest());
            ps.setLong(5, o.getDeId());
            if (o.getDfeinit() != null) {
                ps.setTimestamp(6, new Timestamp(o.getDfeinit().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            ps.setString(7, o.getDnumdoc());
            ps.setLong(8, o.getDnumtim());
            ps.setString(9, o.getDpunexp());
            if (o.getDserienum() != null) {
                ps.setString(10, o.getDserienum());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            ps.setLong(11, itemId);
            ps.setInt(12, o.getItide());
            if (o.getModifiedBy() != null) {
                ps.setString(13, o.getModifiedBy());
            } else {
                ps.setNull(13, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(14, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(14, java.sql.Types.DATE);
            }
            ps.setLong(15, o.getOrgId());
            ps.setLong(16, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGtimbDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGtimbDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
