package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.PosTrxEbBatchItem;

public class PosTrxEbBatchItemsDAO {
	
	/**
	 * Obtener la lista de lotes de documentos enviados correspondiente a un tango de fechas.
	 * @param batchId - Identificador interno de lote a consultar
	 * @return ArrayList<PosTrxEbBatchItem> - Lista de elemetos del lote
	 */
	public static ArrayList<PosTrxEbBatchItem> getList ( long batchId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		ArrayList<PosTrxEbBatchItem> l;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.BATCH_ID, x.CASH_CONTROL_ID, x.CASH_REGISTER_ID, x.CONTROL_CODE,");
			buffer.append(" x.CREATED_BY, x.CREATED_ON, x.IDENTIFIER, x.ORG_ID,");
			buffer.append(" x.RESULT_CODE, x.RESULT_MESSAGE, x.RESULT_STATUS, x.RESULT_TX_NUMBER,");
			buffer.append(" x.TRANSACTION_ID, x.UNIT_ID");
			buffer.append(" from POS_TRX_EB_BATCH_ITEMS x");
			buffer.append(" where BATCH_ID = ?");

			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, batchId);
			rs = ps.executeQuery();

			l = new ArrayList<PosTrxEbBatchItem>();

			while (rs.next()) {
				dataFound = true;
				PosTrxEbBatchItem o = new PosTrxEbBatchItem();
				//
				o.setBatchId(rs.getLong("BATCH_ID"));
				o.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setCashRegisterId(rs.getLong("CASH_REGISTER_ID"));
				o.setControlCode(rs.getString("CONTROL_CODE"));
				String n = o.getControlCode().substring(11, 14) + "-" +
						   o.getControlCode().substring(14, 17) + "-" +
						   o.getControlCode().substring(17, 24);
				o.setLocalDocNumber(n);
				o.setCreatedBy(rs.getString("CREATED_BY"));
				o.setCreatedOn(rs.getDate("CREATED_ON"));
				o.setIdentifier(rs.getLong("IDENTIFIER"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setResultCode(rs.getInt("RESULT_CODE"));
				o.setResultMessage(rs.getString("RESULT_MESSAGE"));
				o.setResultStatus(rs.getString("RESULT_STATUS"));
				o.setResultTxNumber(rs.getLong("RESULT_TX_NUMBER"));
				o.setTransactionId(rs.getLong("TRANSACTION_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));				
				//System.out.println("agregando: " + o.getTxNumber());
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
			Util.closeJDBCConnection(conn);
		}
	}
	
    public static int addRow ( PosTrxEbBatchItem o, String xmlFile, Connection conn ) {
	    //Connection conn =  null;
        PreparedStatement ps = null;

        try {
			//conn = Util.getConnection();
			//if (conn == null) {
			//	return 0;
			//}
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into POS_TRX_EB_BATCH_ITEMS (");
            buffer.append(" BATCH_ID,CASH_CONTROL_ID,CASH_REGISTER_ID,CONTROL_CODE,");
            buffer.append(" CREATED_BY,CREATED_ON,IDENTIFIER,ORG_ID,");
            buffer.append(" RESULT_CODE,RESULT_MESSAGE,RESULT_STATUS,RESULT_TX_NUMBER,");
            buffer.append(" TRANSACTION_ID,UNIT_ID )");
            buffer.append(" values ( ?,?,?,?,?,?,?,?,?,?,");
            buffer.append(" ?,?,?,? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setLong(1, o.getBatchId());
            if (o.getCashControlId() != 0) {
                ps.setLong(2, o.getCashControlId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            if (o.getCashRegisterId() != 0) {
                ps.setLong(3, o.getCashRegisterId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, o.getControlCode());
            ps.setString(5, o.getCreatedBy());
            ps.setTimestamp(6, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(7, o.getIdentifier());
            ps.setLong(8, o.getOrgId());
            if (o.getResultCode() != 0) {
                ps.setLong(9, o.getResultCode());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            if (o.getResultMessage() != null) {
                ps.setString(10, o.getResultMessage());
            } else {
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            if (o.getResultStatus() != null) {
                ps.setString(11, o.getResultStatus());
            } else {
                ps.setNull(11, java.sql.Types.VARCHAR);
            }
            if (o.getResultTxNumber() != 0) {
                ps.setLong(12, o.getResultTxNumber());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }
            ps.setLong(13, o.getTransactionId());
            ps.setLong(14, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            //System.out.println("Creando log de lote de transacciones: " + o.getIdentifier() + "-" + o.getCashControlId() +
            //		"-" + o.getCashRegisterId());
		    // asignar a la transaccion el identificador de lote
            /*
             * Por ahora, la actualizacion de las facturas se realizara en un proceso posterior
             * que lea los registros "POS_TRX_EB_BATCH_ITEMS" y haga dicha actualizacion con los
             * valores registrados en este procedimiento
             */
		    PosTransactionsDAO.updateBatchId ( o.getTransactionId(), 
		    		                               o.getCashControlId(), 
		    		                               o.getCashRegisterId(), 
		    		                               o.getControlCode(),
		    		                               o.getBatchId(), 
		    		                               xmlFile, 
		    		                               conn );
            return rowsQty;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Util.closeStatement(ps);
        }
    }

    public static int updateBatchItem ( PosTrxEbBatchItem o, Connection conn ) {
       	PreparedStatement stmtUpdate = null;
        int updated = 0;
    	    try {
    		    StringBuffer buffer = new StringBuffer();
    		    buffer.append("update POS_TRX_EB_BATCH_ITEMS");
    		    buffer.append(" set RESULT_CODE = ?,");
    		    buffer.append(" RESULT_MESSAGE = ?,");
    		    buffer.append(" RESULT_STATUS = ?,");
    		    buffer.append(" RESULT_TX_NUMBER = ?");
    		    buffer.append(" where BATCH_ID = ?");
    		    buffer.append(" and CONTROL_CODE = ?");

    		    stmtUpdate = conn.prepareStatement(buffer.toString());
    		    stmtUpdate.setLong(1, o.getResultCode());
    		    stmtUpdate.setString(2, o.getResultMessage());
    		    stmtUpdate.setString(3, o.getResultStatus());
    		    stmtUpdate.setLong(4, o.getResultTxNumber());
    		    stmtUpdate.setLong(5, o.getBatchId());
    		    stmtUpdate.setString(6, o.getControlCode());
    		
    		    updated = stmtUpdate.executeUpdate();
    		    //System.out.println("actualizado datos transmision: " + transactionId);
    		    return updated;

        	} catch (Exception e) {
    		    e.printStackTrace();
    		    return 0;
    	    } finally {
    		    Util.closeStatement(stmtUpdate);
    	    }
    }
    
    public static boolean existsNotUpdated ( long batchId, Connection conn  ) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;
        try {
            StringBuffer buffer = new StringBuffer();              
            
            buffer.append("select IDENTIFIER");
            buffer.append(" from POS_TRX_EB_BATCH_ITEMS");
            buffer.append(" where RESULT_MESSAGE is null");
            buffer.append(" and BATCH_ID = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, batchId);
            rs = ps.executeQuery();

            //System.out.println("Id. Lote: " + batchId);
            if (rs.next()) {
                dataFound = true;
            }
            if (dataFound == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
        }
    }
    
}
