package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGpaconeini;

public class EbGpaconeiniDAO {
	
    public static ApplicationMessage addRow ( EbGpaconeini o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GPACONEINI (");
            buffer.append(" CAMCOND_ID,CMONETIPAG,CREATED_BY,CREATED_ON,");
            buffer.append(" DDESTIPAG,DDMONETIPAG,DMONTIPAG,DTICAMTIPAG,");
            buffer.append(" IDENTIFIER,ITIPAGO,MODIFIED_BY,MODIFIED_ON,");
            buffer.append(" ORG_ID,UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,?,?,?,");
            buffer.append(" ?,?,?,? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GPACONEINI", conn);

            ps.setLong(1, o.getCamcondId());
            ps.setString(2, o.getCmonetipag());
            ps.setString(3, o.getCreatedBy());
            ps.setTimestamp(4, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(5, o.getDdestipag());
            ps.setString(6, o.getDdmonetipag());
            ps.setDouble(7, o.getDmontipag());
            if (o.getDticamtipag() != 0.0) {
                ps.setDouble(8, o.getDticamtipag());
            } else {
                ps.setNull(8, java.sql.Types.DOUBLE);
            }
            ps.setLong(9, o.getIdentifier());
            ps.setInt(10, o.getItipago());
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
            ApplicationMessage m = new ApplicationMessage ( "EbGpaconeiniDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGpaconeiniDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
