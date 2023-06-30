package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import business.ApplicationMessage;
import pojo.EbGcuotas;

public class EbGcuotasDAO {

    public static ApplicationMessage addRow ( EbGcuotas o, Connection conn ) {
        PreparedStatement ps = null;
        long itemId = -1;

        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into EB_GCUOTAS (");
            buffer.append(" CMONECUO, CREATED_BY, CREATED_ON, DDMONECUO,");
            buffer.append(" DMONCUOTA, DVENCCUO, IDENTIFIER, MODIFIED_BY,");
            buffer.append(" MODIFIED_ON, ORG_ID, PAGCRED_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ?, ? )");

            ps = conn.prepareStatement(buffer.toString());
            itemId = UtilitiesDAO.getNextval("SQ_EB_GCUOTAS", conn);

            ps.setString(1, o.getCmonecuo());
            ps.setString(2, o.getCreatedBy());
            ps.setTimestamp(3, new Timestamp(o.getCreatedOn().getTime()));
            ps.setString(4, o.getDdmonecuo());
            ps.setDouble(5, o.getDmoncuota());
            if (o.getDvenccuo() != null) {
                ps.setTimestamp(6, new Timestamp(o.getDvenccuo().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            ps.setLong(7, o.getIdentifier());
            if (o.getModifiedBy() != null) {
                ps.setString(8, o.getModifiedBy());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getModifiedOn() != null) {
                ps.setTimestamp(9, new Timestamp(o.getModifiedOn().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }
            ps.setLong(10, o.getOrgId());
            ps.setLong(11, o.getPagcredId());
            ps.setLong(12, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage ( "EbGcuotasDAO SQL", 
            		                                            "Error BD. Codigo: " + e.getErrorCode() + " - " + e.getMessage(), 
            		                                            ApplicationMessage.ERROR);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationMessage m = new ApplicationMessage("EbGcuotasDAO JVM", "Error JVM: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
        } finally {
            Util.closeStatement(ps);
        }
    }
}
