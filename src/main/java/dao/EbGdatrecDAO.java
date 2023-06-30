package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGdatrec;

public class EbGdatrecDAO {

    public static ApplicationMessage addRow ( EbGdatrec o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GDATREC (");
            buffer.append(" CCIUREC, CDEPREC, CDISREC, CPAISREC,");
            buffer.append(" CREATED_BY, CREATED_ON, DATGRALOPE_ID, DCELREC,");
            buffer.append(" DCODCLIENTE, DDESCIUREC, DDESDEPREC, DDESDISREC,");
            buffer.append(" DDESPAISRE, DDIRREC, DDTIPIDREC, DDVREC,");
            buffer.append(" DEMAILREC, DNOMFANREC, DNOMREC, DNUMCASREC,");
            buffer.append(" DNUMIDREC, DRUCREC, DTELREC, IDENTIFIER,");
            buffer.append(" INATREC, ITICONTREC, ITIOPE, ITIPIDREC,");
            buffer.append(" MODIFIED_BY, MODIFIED_ON, ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GDATREC", conn);

            if (o.getCciurec() != 0) {
                ps.setInt(1, o.getCciurec());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            if (o.getCdeprec() != 0) {
                ps.setInt(2, o.getCdeprec());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            if (o.getCdisrec() != 0) {
                ps.setInt(3, o.getCdisrec());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, o.getCpaisrec());
            ps.setString(5, o.getCreatedBy());
            ps.setTimestamp(6, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(7, o.getDatgralopeId());
            if (o.getDcelrec() != null) {
                ps.setString(8, o.getDcelrec());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getDcodcliente() != null) {
                ps.setString(9, o.getDcodcliente());
            } else {
                ps.setNull(9, java.sql.Types.VARCHAR);
            }
            if (o.getDdesciurec() != null) {
                ps.setString(10, o.getDdesciurec());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            if (o.getDdesdeprec() != null) {
                ps.setString(11, o.getDdesdeprec());
            } else {
                ps.setNull(11, java.sql.Types.VARCHAR);
            }
            if (o.getDdesdisrec() != null) {
                ps.setString(12, o.getDdesdisrec());
            } else {
                ps.setNull(12, java.sql.Types.VARCHAR);
            }
            ps.setString(13, o.getDdespaisre());
            if (o.getDdirrec() != null) {
                ps.setString(14, o.getDdirrec());
            } else {
                ps.setNull(14, java.sql.Types.VARCHAR);
            }
            if (o.getDdtipidrec() != null) {
                ps.setString(15, o.getDdtipidrec());
            } else {
                ps.setNull(15, java.sql.Types.VARCHAR);
            }
            if (o.getDdvrec() != 0) {
                ps.setInt(16, o.getDdvrec());
            } else {
                ps.setNull(16, java.sql.Types.INTEGER);
            }
            if (o.getDemailrec() != null) {
                ps.setString(17, o.getDemailrec());
            } else {
                ps.setNull(17, java.sql.Types.VARCHAR);
            }
            if (o.getDnomfanrec() != null) {
                ps.setString(18, o.getDnomfanrec());
            } else {
                ps.setNull(18, java.sql.Types.VARCHAR);
            }
            if (o.getDnomrec() != null) {
                ps.setString(19, o.getDnomrec());
            } else {
                ps.setNull(19, java.sql.Types.VARCHAR);
            }
            if (o.getDnumcasrec() != 0) {
                ps.setInt(20, o.getDnumcasrec());
            } else {
                ps.setNull(20, java.sql.Types.INTEGER);
            }
            if (o.getDnumidrec() != null) {
                ps.setString(21, o.getDnumidrec());
            } else {
                ps.setNull(21, java.sql.Types.VARCHAR);
            }
            if (o.getDrucrec() != null) {
                ps.setString(22, o.getDrucrec());
            } else {
                ps.setNull(22, java.sql.Types.VARCHAR);
            }
            if (o.getDtelrec() != null) {
                ps.setString(23, o.getDtelrec());
            } else {
                ps.setNull(23, java.sql.Types.VARCHAR);
            }
            ps.setLong(24, itemId);
            ps.setInt(25, o.getInatrec());
            if (o.getIticontrec() != 0) {
                ps.setInt(26, o.getIticontrec());
            } else {
                ps.setNull(26, java.sql.Types.INTEGER);
            }
            ps.setInt(27, o.getItiope());
            if (o.getItipidrec() != 0) {
                ps.setInt(28, o.getItipidrec());
            } else {
                ps.setNull(28, java.sql.Types.INTEGER);
            }
            if (o.getModifiedBy() != null) {
                ps.setString(29, o.getModifiedBy());
            } else {
                ps.setNull(29, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(30, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(30, java.sql.Types.DATE);
            }
            ps.setLong(31, o.getOrgId());
            ps.setLong(32, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGdatrecDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGdatrecDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }	
}
