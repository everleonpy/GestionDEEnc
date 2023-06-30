package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGtotsub;

public class EbGtotsubDAO {

    public static ApplicationMessage addRow ( EbGtotsub o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GTOTSUB (");
            buffer.append(" CREATED_BY, CREATED_ON, DANTICIPO, DBASEGRAV10,");
            buffer.append(" DBASEGRAV5, DCOMI, DDESCTOTAL, DE_ID,");
            buffer.append(" DIVA10, DIVA5, DIVACOMI, DLIQTOTIVA10,");
            buffer.append(" DLIQTOTIVA5, DPORCDESCTOTAL, DREDON, DSUB10,");
            buffer.append(" DSUB5, DSUBEXE, DSUBEXO, DTBASGRAIVA,");
            buffer.append(" DTOTALGS, DTOTANT, DTOTANTITEM, DTOTDESC,");
            buffer.append(" DTOTDESCGLOTEM, DTOTGRALOPE, DTOTIVA, DTOTOPE,");
            buffer.append(" IDENTIFIER, MODIFIED_BY, MODIFIED_ON, ORG_ID,");
            buffer.append(" UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GTOTSUB", conn);

            ps.setString(1, o.getCreatedBy());
            ps.setTimestamp(2, new Timestamp(o.getCreatedOn().getTime()));
            ps.setDouble(3, o.getDanticipo());
            if (o.getDbasegrav10() != 0) {
                ps.setDouble(4, o.getDbasegrav10());
            } else {
                ps.setNull(4, java.sql.Types.DOUBLE);
            }
            if (o.getDbasegrav5() != 0) {
                ps.setDouble(5, o.getDbasegrav5());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
            }
            if (o.getDcomi() != 0) {
                ps.setDouble(6, o.getDcomi());
            } else {
                ps.setNull(6, java.sql.Types.DOUBLE);
            }
            ps.setDouble(7, o.getDdesctotal());
            ps.setLong(8, o.getDeId());
            if (o.getDiva10() != 0) {
                ps.setDouble(9, o.getDiva10());
            } else {
                ps.setNull(9, java.sql.Types.DOUBLE);
            }
            if (o.getDiva5() != 0) {
                ps.setDouble(10, o.getDiva5());
            } else {
                ps.setNull(10, java.sql.Types.DOUBLE);
            }
            if (o.getDivacomi() != 0) {
                ps.setDouble(11, o.getDivacomi());
            } else {
                ps.setNull(11, java.sql.Types.DOUBLE);
            }
            if (o.getDliqtotiva10() != 0) {
                ps.setDouble(12, o.getDliqtotiva10());
            } else {
                ps.setNull(12, java.sql.Types.DOUBLE);
            }
            if (o.getDliqtotiva5() != 0) {
                ps.setDouble(13, o.getDliqtotiva5());
            } else {
                ps.setNull(13, java.sql.Types.DOUBLE);
            }
            ps.setDouble(14, o.getDporcdesctotal());
            ps.setDouble(15, o.getDredon());
            if (o.getDsub10() != 0.0) {
                ps.setDouble(16, o.getDsub10());
            } else {
                ps.setNull(16, java.sql.Types.DOUBLE);
            }
            if (o.getDsub5() != 0.0) {
                ps.setDouble(17, o.getDsub5());
            } else {
                ps.setNull(17, java.sql.Types.DOUBLE);
            }
            if (o.getDsubexe() != 0.0) {
                ps.setDouble(18, o.getDsubexe());
            } else {
                ps.setNull(18, java.sql.Types.DOUBLE);
            }
            if (o.getDsubexo() != 0.0) {
                ps.setDouble(19, o.getDsubexo());
            } else {
                ps.setNull(19, java.sql.Types.DOUBLE);
            }
            if (o.getDtbasgraiva() != 0) {
                ps.setDouble(20, o.getDtbasgraiva());
            } else {
                ps.setNull(20, java.sql.Types.DOUBLE);
            }
            if (o.getDtotalgs() != 0) {
                ps.setDouble(21, o.getDtotalgs());
            } else {
                ps.setNull(21, java.sql.Types.DOUBLE);
            }
            ps.setDouble(22, o.getDtotant());
            ps.setDouble(23, o.getDtotantitem());
            ps.setDouble(24, o.getDtotdesc());
            ps.setDouble(25, o.getDtotdescglotem());
            if (o.getDtotgralope() != 0) {
                ps.setDouble(26, o.getDtotgralope());
            } else {
                ps.setNull(26, java.sql.Types.DOUBLE);
            }
            if (o.getDtotiva() != 0) {
                ps.setDouble(27, o.getDtotiva());
            } else {
                ps.setNull(27, java.sql.Types.INTEGER);
            }
            ps.setDouble(28, o.getDtotope());
            ps.setDouble(29, itemId);
            if (o.getModifiedBy() != null) {
                ps.setString(30, o.getModifiedBy());
            } else {
                ps.setNull(30, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(31, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(31, java.sql.Types.DATE);
            }
            ps.setLong(32, o.getOrgId());
            ps.setLong(33, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGtotsubDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGtotsubDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
