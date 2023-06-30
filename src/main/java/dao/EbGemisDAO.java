package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGemis;

public class EbGemisDAO {

    public static ApplicationMessage addRow ( EbGemis o, Connection conn ) {
        PreparedStatement ps = null;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GEMIS (");
            buffer.append(" CCIUEMI, CDEPEMI, CDISEMI, CREATED_BY,");
            buffer.append(" CREATED_ON, CTIPREG, DATGRALOPE_ID, DCOMPDIR1,");
            buffer.append(" DCOMPDIR2, DDENSUC, DDESCIUEMI, DDESDEPEMI,");
            buffer.append(" DDESDISEMI, DDIREMI, DDVEMI, DEMAILE,");
            buffer.append(" DNOMEMI, DNOMFANEMI, DNUMCAS, DRUCEM,");
            buffer.append(" DTELEMI, IDENTIFIER, ITIPCONT, MODIFIED_BY,");
            buffer.append(" MODIFIED_ON, ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setInt(1, o.getCciuemi());
            ps.setInt(2, o.getCdepemi());
            if (o.getCdisemi() != 0) {
                ps.setInt(3, o.getCdisemi());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, o.getCreatedBy());
            ps.setTimestamp(5, new Timestamp(o.getCreatedOn().getTime()));
            if (o.getCtipreg() != 0) {
                ps.setShort(6, o.getCtipreg());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.setLong(7, o.getDatgralopeId());
            if (o.getDcompdir1() != null) {
                ps.setString(8, o.getDcompdir1());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getDcompdir2() != null) {
                ps.setString(9, o.getDcompdir2());
            } else {
                ps.setNull(9, java.sql.Types.VARCHAR);
            }
            if (o.getDdensuc() != null) {
                ps.setString(10, o.getDdensuc());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            ps.setString(11, o.getDdesciuemi());
            ps.setString(12, o.getDdesdepemi());
            if (o.getDdesdisemi() != null) {
                ps.setString(13, o.getDdesdisemi());
            } else {
                ps.setNull(13, java.sql.Types.VARCHAR);
            }
            if (o.getDdiremi() != null) {
                ps.setString(14, o.getDdiremi());
            } else {
                ps.setNull(14, java.sql.Types.VARCHAR);
            }
            ps.setString(15, o.getDdvemi());
            ps.setString(16, o.getDemaile());
            ps.setString(17, o.getDnomemi());
            if (o.getDnomfanemi() != null) {
                ps.setString(18, o.getDnomfanemi());
            } else {
                ps.setNull(18, java.sql.Types.VARCHAR);
            }
            ps.setString(19, o.getDnumcas());
            ps.setString(20, o.getDrucem());
            ps.setString(21, o.getDtelemi());
            ps.setLong(22, o.getIdentifier());
            ps.setInt(23, o.getItipcont());
            if (o.getModifiedBy() != null) {
                ps.setString(24, o.getModifiedBy());
            } else {
                ps.setNull(24, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(25, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(25, java.sql.Types.DATE);
            }
            ps.setLong(26, o.getOrgId());
            ps.setLong(27, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGemisDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGemisDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
