package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.RcvTrxEbBatchItem;

public class RcvTrxEbBatchItemsDAO {
	
	public static ArrayList<RcvTrxEbBatchItem> getList ( long batchId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<RcvTrxEbBatchItem> l;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.IDENTIFIER, x.ORG_ID, x.UNIT_ID, x.BATCH_ID,");
			buffer.append(" x.TRANSACTION_ID, x.CREATED_BY, x.CREATED_ON, x.CONTROL_CODE,");
			buffer.append(" x.RESULT_STATUS, x.RESULT_TX_NUMBER, x.RESULT_CODE, x.RESULT_MESSAGE");
			buffer.append(" from RCV_TRX_EB_BATCH_ITEMS x");
			buffer.append(" where x.BATCH_ID = ?");
			buffer.append(" order by x.CONTROL_CODE");

			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, batchId);

		    rs = ps.executeQuery();

			l = new ArrayList<RcvTrxEbBatchItem>();

			while (rs.next()) {
				dataFound = true;
				RcvTrxEbBatchItem o = new RcvTrxEbBatchItem();
				//
				o.setBatchId(batchId);
				o.setControlCode(rs.getString("CONTROL_CODE"));
				o.setCreatedBy(rs.getString("CREATED_BY"));
				//d = sdf.parse(rs.getString("CREATED_ON"));
				o.setCreatedOn(rs.getDate("CREATED_ON"));
				o.setIdentifier(rs.getLong("IDENTIFIER"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setResultCode(rs.getInt("RESULT_CODE"));
				o.setResultMessage(rs.getString("RESULT_MESSAGE"));
				o.setResultStatus(rs.getString("RESULT_STATUS"));
				o.setResultTxNumber(rs.getLong("RESULT_TX_NUMBER"));
				o.setTransactionId(rs.getLong("TRANSACTION_ID"));
				String txNo = o.getControlCode().substring(11, 14) + "-" +
						      o.getControlCode().substring(14, 17) + "-" +
						      o.getControlCode().substring(17, 24);
				o.setTxNumber(txNo);
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
	
	public static boolean existsNoQueriedItems ( long batchId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.IDENTIFIER");
			buffer.append(" from RCV_TRX_EB_BATCH_ITEMS x");
			buffer.append(" where x.RESULT_MESSAGE is null");
			buffer.append(" and x.BATCH_ID = ?");

			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, batchId);

		    rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
			}
			return dataFound;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static boolean existsApprovedLog ( String controlCode ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.IDENTIFIER");
			buffer.append(" from RCV_TRX_EB_BATCH_ITEMS x");
			buffer.append(" where upper(nvl(x.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
			buffer.append(" and x.CONTROL_CODE = ?");

			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
		    ps.setString(1, controlCode);

		    rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
			}
			return dataFound;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
    public static int addRow ( RcvTrxEbBatchItem o, Connection conn ) {
		/**
		 * La implementacion de esta intancia se realiza sobre Sql Server, que genera 
		 * automaticamente el valor del identificador, por tanto la columna "identifier"
		 * no es mencionada en el insert
		 */
	    //Connection conn =  null;
        PreparedStatement ps = null;

        try {
			//conn = Util.getConnection();
			//if (conn == null) {
			//	return 0;
			//}
            StringBuffer buffer = new StringBuffer();

            buffer.append("insert into RCV_TRX_EB_BATCH_ITEMS (");
            buffer.append(" BATCH_ID, CONTROL_CODE, CREATED_BY, CREATED_ON,");
            buffer.append(" ORG_ID, RESULT_CODE, RESULT_MESSAGE, RESULT_STATUS,");
            buffer.append(" RESULT_TX_NUMBER, TRANSACTION_ID, UNIT_ID )");
            buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
            buffer.append(" ? )");

            ps = conn.prepareStatement(buffer.toString());

            ps.setLong(1, o.getBatchId());
            ps.setString(2, o.getControlCode());
            ps.setString(3, o.getCreatedBy());
            ps.setTimestamp(4, new Timestamp(o.getCreatedOn().getTime()));
            ps.setLong(5, o.getOrgId());
            if (o.getResultCode() != 0) {
                ps.setLong(6, o.getResultCode());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            if (o.getResultMessage() != null) {
                ps.setString(7, o.getResultMessage());
            } else {
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            if (o.getResultStatus() != null) {
                ps.setString(8, o.getResultStatus());
            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if (o.getResultTxNumber() != 0) {
                ps.setLong(9, o.getResultTxNumber());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            ps.setLong(10, o.getTransactionId());
            ps.setLong(11, o.getUnitId());
            int rowsQty = ps.executeUpdate();
            System.out.println("Creando log de lote de transacciones: " + o.getIdentifier() );
		    // asignar a la transaccion el identificador de lote
            /*
            RcvEbInvoicesDAO.updateBatchId ( o.getTransactionId(), 
                                             o.getControlCode(),
                                             o.getBatchId(), 
                                             conn );
		    RcvCustomersTrxDAO.updateBatchId ( o.getTransactionId(), 
		    		                               o.getControlCode(),
		    		                               o.getBatchId(), 
		    		                               o.getXmlFile(), 
		    		                               o.getQrCode(),
		    		                               conn );
		    	*/
            return rowsQty;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Util.closeStatement(ps);
        }
    }

    public static int updateRow ( RcvTrxEbBatchItem o, Connection conn ) {
	    //Connection conn =  null;
        PreparedStatement ps = null;

        try {
			//conn = Util.getConnection();
			//if (conn == null) {
			//	return 0;
			//}
            StringBuffer buffer = new StringBuffer();

            buffer.append("update RCV_TRX_EB_BATCH_ITEMS");
            buffer.append(" set RESULT_CODE = ?,");
            buffer.append(" RESULT_MESSAGE = ?,");
            buffer.append(" RESULT_STATUS = ?,");
            buffer.append(" RESULT_TX_NUMBER = ?");
            buffer.append(" where CONTROL_CODE = ?");
            buffer.append(" and BATCH_ID = ?");

            ps = conn.prepareStatement(buffer.toString());

            if (o.getResultCode() != 0) {
                ps.setLong(1, o.getResultCode());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            if (o.getResultMessage() != null) {
                ps.setString(2, o.getResultMessage());
            } else {
                ps.setNull(2, java.sql.Types.VARCHAR);
            }
            if (o.getResultStatus() != null) {
                ps.setString(3, o.getResultStatus());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }
            if (o.getResultTxNumber() != 0) {
                ps.setLong(4, o.getResultTxNumber());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            //
            ps.setString(5, o.getControlCode());
            ps.setLong(6, o.getBatchId());
            int rowsQty = ps.executeUpdate();
            System.out.println("Actualizando item de lote: " + o.getControlCode() + " - " + o.getBatchId());
            return rowsQty;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Util.closeStatement(ps);
        }
    }

	public static int updateBatchId ( long transactionId, 
			                          String controlCode,
			                          long ebBatchId,
			                          String fileName, 
			                          Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update RCV_EB_INVOICES");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?,");
			buffer.append(" EB_XML_FILE = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			stmtUpdate.setLong(3, ebBatchId);
			stmtUpdate.setString(4, fileName);
			stmtUpdate.setLong(5, transactionId);
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
    
}
