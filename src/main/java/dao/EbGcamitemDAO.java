package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGcamitem;

public class EbGcamitemDAO {

    public static ApplicationMessage addRow ( EbGcamitem o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GCAMITEM (");
            buffer.append(" CPAISORIG, CREATED_BY, CREATED_ON, CRELMERC,");
            buffer.append(" CUNIMED, DCANQUIMER, DCANTPROSER, DCDCANTICIPO,");
            buffer.append(" DCODINT, DDESPAISORIG, DDESPROSER, DDESRELMERC,");
            buffer.append(" DDESUNIMED, DDNCPE, DDNCPG, DGTIN,");
            buffer.append(" DGTINPQ, DINFITEM, DNCM, DPARARANC,");
            buffer.append(" DPORQUIMER, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, TIPDE_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GCAMITEM", conn);

            if (o.getCpaisorig() != null) {
                ps.setString(1, o.getCpaisorig());
            } else {
                ps.setNull(1, java.sql.Types.VARCHAR);
            }
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getCrelmerc() != 0) {
                ps.setInt(4, o.getCrelmerc());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setInt(5, o.getCunimed());
            if (o.getDcanquimer() != 0.0) {
                ps.setDouble(6, o.getDcanquimer());
            } else {
                ps.setNull(6, java.sql.Types.DOUBLE);
            }
            ps.setDouble(7, o.getDcantproser());
            if (o.getDcdcanticipo() != null) {
                ps.setString(8, o.getDcdcanticipo());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            ps.setString(9, o.getDcodint());
            if (o.getDdespaisorig() != null) {
                ps.setString(10, o.getDdespaisorig());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            ps.setString(11, o.getDdesproser());
            if (o.getDdesrelmerc() != null) {
                ps.setString(12, o.getDdesrelmerc());
            } else {
                ps.setNull(12, java.sql.Types.VARCHAR);
            }
            ps.setString(13, o.getDdesunimed());
            if (o.getDdncpe() != null) {
                ps.setString(14, o.getDdncpe());
            } else {
                ps.setNull(14, java.sql.Types.VARCHAR);
            }
            if (o.getDdncpg() != null) {
                ps.setString(15, o.getDdncpg());
            } else {
                ps.setNull(15, java.sql.Types.VARCHAR);
            }
            if (o.getDgtin() != 0) {
                ps.setLong(16, o.getDgtin());
            } else {
                ps.setNull(16, java.sql.Types.INTEGER);
            }
            if (o.getDgtinpq() != 0) {
                ps.setLong(17, o.getDgtinpq());
            } else {
                ps.setNull(17, java.sql.Types.INTEGER);
            }
            if (o.getDinfitem() != null) {
                ps.setString(18, o.getDinfitem());
            } else {
                ps.setNull(18, java.sql.Types.VARCHAR);
            }
            if (o.getDncm() != 0) {
                ps.setLong(19, o.getDncm());
            } else {
                ps.setNull(19, java.sql.Types.INTEGER);
            }
            if (o.getDpararanc() != 0) {
                ps.setInt(20, o.getDpararanc());
            } else {
                ps.setNull(20, java.sql.Types.INTEGER);
            }
            if (o.getDporquimer() != 0.0) {
                ps.setDouble(21, o.getDporquimer());
            } else {
                ps.setNull(21, java.sql.Types.DOUBLE);
            }
            ps.setLong(22, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(23, o.getModifiedBy());
            } else {
                ps.setNull(23, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(24, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(24, java.sql.Types.DATE);
            }
            ps.setLong(25, o.getOrgId());
            ps.setLong(26, o.getTipdeId());
            ps.setLong(27, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGcamitemDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGcamitemDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
