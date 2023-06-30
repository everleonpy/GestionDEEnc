package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGvalorrestaitem;

public class EbGvalorrestaitemDAO {

    public static ApplicationMessage addRow ( EbGvalorrestaitem o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GVALORRESTAITEM (");
            buffer.append(" CREATED_BY, CREATED_ON, DANTGLOPREUNIIT, DANTPREUNIIT,");
            buffer.append(" DDESCGLOITEM, DDESCITEM, DPORCDESIT, DTOTOPEGS,");
            buffer.append(" DTOTOPEITEM, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, UNIT_ID, VALORITEM_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GVALORRESTAITEM", conn);

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getDantglopreuniit() != 0.0) {
                ps.setDouble(3, o.getDantglopreuniit());
            } else {
                ps.setNull(3, java.sql.Types.DOUBLE);
            }
            if (o.getDantpreuniit() != 0.0) {
                ps.setDouble(4, o.getDantpreuniit());
            } else {
                ps.setNull(4, java.sql.Types.DOUBLE);
            }
            if (o.getDdescgloitem() != 0.0) {
                ps.setDouble(5, o.getDdescgloitem());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
            }
            if (o.getDdescitem() != 0.0) {
                ps.setDouble(6, o.getDdescitem());
            } else {
                ps.setNull(6, java.sql.Types.DOUBLE);
            }
            if (o.getDporcdesit() != 0.0) {
                ps.setDouble(7, o.getDporcdesit());
            } else {
                ps.setNull(7, java.sql.Types.DOUBLE);
            }
            if (o.getDtotopegs() != 0.0) {
                ps.setDouble(8, o.getDtotopegs());
            } else {
                ps.setNull(8, java.sql.Types.DOUBLE);
            }
            ps.setDouble(9, o.getDtotopeitem());
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
            ps.setLong(15, o.getValoritemId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGvalorrestaitemDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGvalorrestaitemDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
