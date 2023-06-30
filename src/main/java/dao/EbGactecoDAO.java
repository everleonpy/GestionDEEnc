package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import business.ApplicationMessage;
import pojo.EbGacteco;

public class EbGactecoDAO {

    public static ApplicationMessage addRow ( EbGacteco o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GACTECO (");
            buffer.append(" CACTECO, CREATED_BY, CREATED_ON, DDESACTECO,");
            buffer.append(" EMIS_ID, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GACTECO", conn);

            ps.setString(1, o.getCacteco());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(4, o.getDdesacteco());
            ps.setLong(5, o.getEmisId());
            ps.setLong(6, itemId);
            if (o.getModifiedBy() != null) {
                ps.setString(7, o.getModifiedBy());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(8, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }
            ps.setLong(9, o.getOrgId());
            ps.setLong(10, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGactecoDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGactecoDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
	
    public static ArrayList<EbGacteco> getList ( long emisId, Connection conn ) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        EbGacteco o = null;
        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("select CACTECO, CREATED_BY, CREATED_ON, DDESACTECO,");
            buffer.append(" EMIS_ID, IDENTIFIER, MODIFIED_BY, MODIFIED_ON,");
            buffer.append(" ORG_ID, UNIT_ID ");
            buffer.append(" from EB_GACTECO");
            buffer.append(" where EMIS_ID = ?");

            ps = conn.prepareStatement(buffer.toString());
            ps.setLong(1, emisId);
            rs = ps.executeQuery();

            ArrayList<EbGacteco> l = new ArrayList<EbGacteco>();
            while (rs.next()) {
                dataFound = true;
                o = new EbGacteco();
                o.setCacteco(rs.getString("CACTECO"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                d = sdf.parse(rs.getString("CREATED_ON"));
                o.setCreatedOn(d);
                o.setDdesacteco(rs.getString("DDESACTECO"));
                o.setEmisId(rs.getLong("EMIS_ID"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                l.add(o);
            }
            if (dataFound == true) {
                return l;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
        }
    }
    
}
