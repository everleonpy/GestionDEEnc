package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import pojo.PosEbEventLog;

public class PosEbEventsLogDAO {
	
	public static int addRow ( PosEbEventLog o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into POS_EB_EVENTS_LOG (");
	        buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, CREATED_BY,");
	        	buffer.append(" CREATED_ON, RESULT_STATUS, EVENT_TRX_ID )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ? )");

	        ps = conn.prepareStatement(buffer.toString());

	        ps.setLong(1, o.getIdentifier());
	        ps.setLong(2, o.getOrgId());
	        ps.setLong(3, o.getUnitId());
	        ps.setString(4, o.getCreatedBy());
	        
	        ps.setTimestamp(5, new Timestamp(o.getCreatedOn().getTime()));    
	        ps.setString(6, o.getResultStatus());
	        ps.setLong(7, o.getEventTrxId());
	        
	        inserted = ps.executeUpdate();
	        return inserted;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        Util.closeStatement(ps);
	    }
	}

}
