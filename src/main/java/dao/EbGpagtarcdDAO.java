package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGpagtarcd;

public class EbGpagtarcdDAO {
	
    public static ApplicationMessage addRow ( EbGpagtarcd o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GPAGTARCD (");
            buffer.append(" CREATED_BY, CREATED_ON, DCODAUOPE, DDESDENTARJ,");
            buffer.append(" DDVPROTAR, DNOMTIT, DNUMTARJ, DRSPROTAR,");
            buffer.append(" DRUCPROTAR, IDENTARJ, IDENTIFIER, IFORPROPA,");
            buffer.append(" MODIFIED_BY, MODIFIED_ON, ORG_ID, PACONEINI_ID,");
            buffer.append(" UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,?,?,?,");
            buffer.append(" ?,?,?,?,?,?,? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GPAGTARCD", conn);

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getDcodauope() != 0) {
                ps.setLong(3, o.getDcodauope());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, o.getDdesdentarj());
            if (o.getDdvprotar() != 0) {
                ps.setShort(5, o.getDdvprotar());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if (o.getDnomtit() != null) {
                ps.setString(6, o.getDnomtit());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            if (o.getDnumtarj() != 0) {
                ps.setInt(7, o.getDnumtarj());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            if (o.getDrsprotar() != null) {
                ps.setString(8, o.getDrsprotar());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getDrucprotar() != null) {
                ps.setString(9, o.getDrucprotar());
            } else {
                ps.setNull(9, java.sql.Types.VARCHAR);
            }
            ps.setInt(10, o.getIdentarj());
            ps.setLong(11, o.getIdentifier());
            ps.setInt(12, o.getIforpropa());
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
            ps.setLong(16, o.getPaconeiniId());
            ps.setLong(17, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGpagtarcdDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGpagtarcdDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
