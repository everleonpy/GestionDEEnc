package sifen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import dao.Util;
import pojo.PosEbEventItemLog;

public class PosEbEventItemsLogDAO {
	
	public static int addRow ( PosEbEventItemLog o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into POS_EB_EVENT_ITEMS_LOG (");
	        buffer.append(" IDENTIFIER, EVENT_LOG_ID, ORG_ID, UNIT_ID,");
	        buffer.append(" TRANSACTION_ID, CASH_CONTROL_ID, CASH_REGISTER_ID, EB_CONTROL_CODE,");
	        buffer.append(" EVENT_TYPE_ID, CREATED_BY, CREATED_ON, EVENT_REASON,");
	        buffer.append(" RESULT_STATUS, EVENT_TRX_ID, RESULT_CODE, RESULT_MESSAGE )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	        buffer.append(" ?, ?, ?, ?, ?, ? )");

	        ps = conn.prepareStatement(buffer.toString());

	        ps.setLong(1, o.getIdentifier());
	        ps.setLong(2, o.getEventLogId());
	        ps.setLong(3, o.getOrgId());
	        ps.setLong(4, o.getUnitId());
	        
	        ps.setLong(5, o.getTransactionId());
	        ps.setLong(6, o.getCashControlId());
	        ps.setLong(7, o.getCashRegisterId());
	        ps.setString(8, o.getEbControlCode());

	        ps.setShort(9, o.getEventTypeId());
	        ps.setString(10, o.getCreatedBy());
	        ps.setTimestamp(11, new Timestamp(o.getCreatedOn().getTime()));    
	        ps.setString(12, o.getEventReason());
	        
	        ps.setString(13, o.getResultStatus());
	        ps.setLong(14, o.getEventTrxId());
	        ps.setLong(15, o.getResultCode());
	        ps.setString(16, o.getResultMessage());
	        
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
