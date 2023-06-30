package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import pojo.PosEbSendLog;

public class PosEbSendLogDAO {
	public static int createTransmissionLog ( PosEbSendLog tLog ) {
		Connection conn = null;
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("insert into POS_EB_SEND_LOG (");
			buffer.append(" ESTABLISHMENT_CODE, ISSUE_POINT_CODE, TX_NUMBER, EVENT_ID,");
			buffer.append(" ERROR_CODE, ERROR_MSG, CREATED_ON, ORG_ID,");
			buffer.append(" UNIT_ID )");
			buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, tLog.getEstabilshCode());
			stmtUpdate.setString(2, tLog.getIssuePointCode());
			stmtUpdate.setString(3, tLog.getTxNumber());
			stmtUpdate.setInt(4, tLog.getEventId());
			stmtUpdate.setString(5, tLog.getErrorCode());
			stmtUpdate.setString(6, tLog.getErrorMsg());
			stmtUpdate.setTimestamp(7, new Timestamp (new java.util.Date().getTime()));
			stmtUpdate.setLong(8, tLog.getOrgId());
			stmtUpdate.setLong(9, tLog.getUnitId());
			updated = stmtUpdate.executeUpdate();
			return updated;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
	        Util.closeJDBCConnection(conn);
		}
	}

}
