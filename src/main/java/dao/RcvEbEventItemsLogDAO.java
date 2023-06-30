package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import pojo.RcvEbEventItemLog;

public class RcvEbEventItemsLogDAO {
	
	public static int addRow ( RcvEbEventItemLog o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into RCV_EB_EVENT_ITEMS_LOG (");
	        buffer.append(" IDENTIFIER, EVENT_LOG_ID, ORG_ID, UNIT_ID,");
	        buffer.append(" TRANSACTION_ID, EB_CONTROL_CODE, EVENT_TYPE_ID, CREATED_BY,");
	        buffer.append(" CREATED_ON, EVENT_REASON, RESULT_STATUS, EVENT_TRX_ID,");
	        buffer.append(" RESULT_CODE, RESULT_MESSAGE )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	        buffer.append(" ?, ?, ?, ? )");

	        ps = conn.prepareStatement(buffer.toString());

	        ps.setLong(1, o.getIdentifier());
	        ps.setLong(2, o.getEventLogId());
	        ps.setLong(3, o.getOrgId());
	        ps.setLong(4, o.getUnitId());
	        
	        ps.setLong(5, o.getTransactionId());
	        ps.setString(6, o.getEbControlCode());
	        ps.setShort(7, o.getEventTypeId());
	        ps.setString(8, o.getCreatedBy());
	        
	        ps.setTimestamp(9, new Timestamp(o.getCreatedOn().getTime()));    
	        ps.setString(10, o.getEventReason());
	        ps.setString(11, o.getResultStatus());
	        ps.setLong(12, o.getEventTrxId());

	        ps.setLong(13, o.getResultCode());
	        ps.setString(14, o.getResultMessage());
	        
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
