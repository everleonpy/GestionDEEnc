package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGopecom;

public class EbGopecomDAO {
	
    public static ApplicationMessage addRow ( EbGopecom o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GOPECOM (");
            buffer.append(" CMONEOPE, CREATED_BY, CREATED_ON, DATGRALOPE_ID,");
            buffer.append(" DCONDTICAM, DDESCONDANT, DDESMONEOPE, DDESTIMP,");
            buffer.append(" DDESTIPTRA, DTICAM, ICONDANT, IDENTIFIER,");
            buffer.append(" ITIMP, ITIPTRA, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setString(1, o.getCmoneope());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(4, o.getDatgralopeId());
            if (o.getDcondticam() != 0) {
                ps.setInt(5, o.getDcondticam());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if (o.getDdescondant() != null) {
                ps.setString(6, o.getDdescondant());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            ps.setString(7, o.getDdesmoneope());
            ps.setString(8, o.getDdestimp());
            if (o.getDdestiptra() != null) {
                ps.setString(9, o.getDdestiptra());
            } else {
                ps.setNull(9, java.sql.Types.VARCHAR);
            }
            if (o.getDticam() != 0.0) {
                ps.setDouble(10, o.getDticam());
            } else {
                ps.setNull(10, java.sql.Types.DOUBLE);
            }
            if (o.getIcondant() != 0) {
                ps.setInt(11, o.getIcondant());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }
            ps.setLong(12, o.getIdentifier());
            ps.setInt(13, o.getItimp());
            if (o.getItiptra() != 0) {
                ps.setInt(14, o.getItiptra());
            } else {
                ps.setNull(14, java.sql.Types.INTEGER);
            }
            if (o.getModifiedBy() != null) {
                ps.setString(15, o.getModifiedBy());
            } else {
                ps.setNull(15, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(16, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(16, java.sql.Types.DATE);
            }
            ps.setLong(17, o.getOrgId());
            ps.setLong(18, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGopecomDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGopecomDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
