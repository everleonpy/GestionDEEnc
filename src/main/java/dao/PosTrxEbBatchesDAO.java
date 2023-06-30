package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.GenericStringsList;
import pojo.PosTrxEbBatch;
import pojo.SendGroup;
import util.UtilPOS;

public class PosTrxEbBatchesDAO {
	
	public static int addRow ( PosTrxEbBatch o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into POS_TRX_EB_BATCHES (");
	        buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, BATCH_NUMBER,");
	        buffer.append(" TRANSMISS_DATE, CREATED_BY, CREATED_ON, TRX_TYPE,");
	        buffer.append(" TRX_DATE, ITEMS_QTY, QUERIED_FLAG, REF_NUMBER )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	        buffer.append(" ?, ? )");

	        ps = conn.prepareStatement(buffer.toString());

	        ps.setLong(1, o.getIdentifier());
	        ps.setLong(2, o.getOrgId());
	        ps.setLong(3, o.getUnitId());
	        ps.setString(4, o.getBatchNumber());
	        
	        ps.setTimestamp(5, new Timestamp(o.getTransmissDate().getTime()));
	        ps.setString(6, o.getCreatedBy());
	        ps.setTimestamp(7, new Timestamp(o.getCreatedOn().getTime()));    
	        ps.setString(8, o.getTrxType());
	        
	        ps.setTimestamp(9, new Timestamp(o.getTrxDate().getTime()));
	        ps.setInt(10, o.getItemsQty());
	        ps.setString(11, "N");
	        ps.setString(12, o.getRefNumber());

	        inserted = ps.executeUpdate();
	        return inserted;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        Util.closeStatement(ps);
	    }
	}

	/**
	 * Obtener la lista de facturas que aun no han sido transmitidas a la entidad recaudadora
	 * de impuestos correspondiente a una caja dentro de un tango de fechas.
	 * @param trxDate - Fecha de ls tranacciones a enviar
	 * @param fromNumber - Numero de grupo inicial
	 * @param toNumber - Numero de grupo final
	 * @return ArrayList<SendGroup> - Lista de grupos de facturas pendientes de transmision
	 */
	public static ArrayList<SendGroup> getTrxGroupsList ( 
			java.util.Date trxDate, 
			int fromNumber, 
			int toNumber,
			int rowsPerGroup ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		ArrayList<SendGroup> l;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("select to_number(x.REF_NUMBER) REF_NUMBER, COUNT(*) TX_QUANTITY, sum(decode(x.EB_BATCH_ID, null, 1, 0)) NOTSENT_QTY");
			buffer.append(" from POS_EB_INVOICES x");
			buffer.append(" where x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");
			buffer.append(" and to_number(x.REF_NUMBER) <= ?");
			buffer.append(" and to_number(x.REF_NUMBER) >= ?");
			buffer.append(" group by to_number(x.REF_NUMBER)");
			buffer.append(" having sum(decode(x.EB_BATCH_ID, null, 1, 0)) > 0");
			buffer.append(" order by to_number(x.REF_NUMBER)");

			//System.out.println("**********getTrxGroupsList");
			//System.out.println(buffer.toString());
			//System.out.println("param. en el DAO: " + trxDate + " - " + fromNumber + " - " + toNumber);
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));			
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));			
			ps.setLong(3, toNumber);
			ps.setLong(4, fromNumber);
			rs = ps.executeQuery();

			l = new ArrayList<SendGroup>();

			while (rs.next()) {
				dataFound = true;
				SendGroup o = new SendGroup();
				//
				o.setGroupNumber(rs.getInt("REF_NUMBER"));
				o.setNotSentQuantity(rs.getInt("NOTSENT_QTY"));
				o.setTxQuantity(rs.getInt("TX_QUANTITY"));
				
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
	
	/**
	 * Obtener la lista de lotes de documentos enviados correspondiente a un tango de fechas.
	 * @param trxType - Tipo de transaccion que contiene el lote
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @param batchNumber - Numero de lote especifico a consultar
	 * @return ArrayList<PosTrxEbBatch> - Lista de lotes transmitidos
	 */
	public static ArrayList<PosTrxEbBatch> getList ( String trxType,
			                                         java.util.Date fromDate, 
			                                         java.util.Date toDate,
			                                         String batchNumber ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<PosTrxEbBatch> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.ORG_ID, x.UNIT_ID, to_char(x.TRANSMISS_DATE, 'dd/mm/yyyy hh24:mi:ss') TRANSMISS_DATE,");
			buffer.append(" x.CREATED_BY, x.CREATED_ON, x.BATCH_NUMBER, x.RESULT_CODE,");
			buffer.append(" x.RESULT_MESSAGE, x.PROCESS_TIME, x.QUERIED_FLAG, x.TRX_TYPE,");
			buffer.append(" x.TRX_DATE, x.ITEMS_QTY, x.REF_NUMBER");
			buffer.append(" from POS_TRX_EB_BATCHES x");

			buffer.append(" where x.TRX_TYPE = ?");
			// variable parameters
			if (batchNumber != null) {
				buffer.append(" and x.BATCH_NUMBER = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRX_DATE >= ?");
			}
			// order by
			buffer.append(" order by x.TRX_DATE, IDENTIFIER");

			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			index++;
			ps.setString(index, trxType);
			if (batchNumber != null) {
				index++;
				ps.setString(index, batchNumber);
			}
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<PosTrxEbBatch>();

			while (rs.next()) {
				dataFound = true;
				PosTrxEbBatch o = new PosTrxEbBatch();
				//
				o.setBatchNumber(rs.getString("BATCH_NUMBER"));
				o.setCreatedBy(rs.getString("CREATED_BY"));
				o.setCreatedOn(rs.getDate("CREATED_ON"));
				o.setIdentifier(rs.getLong("IDENTIFIER"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setProcessTime(rs.getInt("PROCESS_TIME"));
				o.setQueriedFlag(rs.getString("QUERIED_FLAG"));
				o.setResultCode(rs.getInt("RESULT_CODE"));
				o.setResultMessage(rs.getString("RESULT_MESSAGE"));
				d = sdf.parse(rs.getString("TRANSMISS_DATE"));
				o.setTransmissDate(d);
				o.setUnitId(rs.getLong("UNIT_ID"));	
				o.setTrxType(rs.getString("TRX_TYPE"));
				o.setTrxDate(rs.getDate("TRX_DATE"));
				o.setItemsQty(rs.getInt("ITEMS_QTY"));
				o.setRefNumber(rs.getString("REF_NUMBER"));
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
	
	/**
	 * Obtener la lista de lotes de documentos enviados correspondiente a un rango de fechas
	 * y que aun no han sido consultados.
	 * @param trxType - Tipo de transaccion que contiene el lote
	 * @param txDate - Fecha de las transacciones qeu estan contenidas en los lotes buscados
	 * @return ArrayList<PosTrxEbBatch> - Lista de lotes transmitidos
	 */
	public static ArrayList<PosTrxEbBatch> getNotQueriedList ( String trxType,
			                                                   java.util.Date txDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<PosTrxEbBatch> l;
		int index = 0;

		try {
			
			fromDate = txDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.ORG_ID, x.UNIT_ID, to_char(x.TRANSMISS_DATE, 'dd/mm/yyyy hh24:mi:ss') TRANSMISS_DATE,");
			buffer.append(" x.CREATED_BY, x.CREATED_ON, x.BATCH_NUMBER, x.RESULT_CODE,");
			buffer.append(" x.RESULT_MESSAGE, x.PROCESS_TIME, x.QUERIED_FLAG, x.TRX_TYPE,");
			buffer.append(" x.TRX_DATE, x.ITEMS_QTY, x.REF_NUMBER");
			buffer.append(" from POS_TRX_EB_BATCHES x");

			buffer.append(" where x.TRX_TYPE = ?");
			buffer.append(" and ( x.QUERIED_FLAG is null or x.QUERIED_FLAG = 'N' )");
			// variable parameters
			if (toDate != null) {
				buffer.append(" and x.TRX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRX_DATE >= ?");
			}
			// order by
			buffer.append(" order by x.TRX_DATE, IDENTIFIER");

			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			index++;
			ps.setString(index, trxType);
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<PosTrxEbBatch>();

			while (rs.next()) {
				dataFound = true;
				PosTrxEbBatch o = new PosTrxEbBatch();
				//
				o.setBatchNumber(rs.getString("BATCH_NUMBER"));
				o.setCreatedBy(rs.getString("CREATED_BY"));
				o.setCreatedOn(rs.getDate("CREATED_ON"));
				o.setIdentifier(rs.getLong("IDENTIFIER"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setProcessTime(rs.getInt("PROCESS_TIME"));
				o.setQueriedFlag(rs.getString("QUERIED_FLAG"));
				o.setResultCode(rs.getInt("RESULT_CODE"));
				o.setResultMessage(rs.getString("RESULT_MESSAGE"));
				d = sdf.parse(rs.getString("TRANSMISS_DATE"));
				o.setTransmissDate(d);
				o.setUnitId(rs.getLong("UNIT_ID"));	
				o.setTrxType(rs.getString("TRX_TYPE"));
				o.setTrxDate(rs.getDate("TRX_DATE"));
				o.setItemsQty(rs.getInt("ITEMS_QTY"));
				o.setRefNumber(rs.getString("REF_NUMBER"));
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

	/**
	 * Obtener un par de valores que corresponde al primer y al ultimo numero de lote que 
	 * contienen facturas aun no enviadas a la entidad de impuestos.
	 * @param trxType - Tipo de transaccion que contiene el lote
	 * @param txDate - Fecha de las transacciones qeu estan contenidas en los lotes buscados
	 * @return GenericStringsList - Par que representa el rango de lotes aun no procesados
	 */
	public static GenericStringsList getNotSendList ( int trxType,
			                                          java.util.Date txDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		GenericStringsList x = null;
		int index = 0;

		try {
			
			fromDate = txDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select min(to_number(x.REF_NUMBER)) GRP_FROM, max(to_number(x.REF_NUMBER)) GRP_TO");
			buffer.append(" from POS_EB_INVOICES x");

			buffer.append(" where x.ITIPTRA = ?");
			buffer.append(" and x.EB_BATCH_ID is null");
			// variable parameters
			if (toDate != null) {
				buffer.append(" and x.DEFEEMIDE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.DEFEEMIDE >= ?");
			}

			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			index++;
			ps.setInt(index, trxType);
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
				x = new GenericStringsList();
				x.setElement1(String.valueOf(rs.getLong("GRP_FROM")));
				x.setElement2(String.valueOf(rs.getLong("GRP_TO")));
			}
			if (dataFound == true) {
				return x;
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
	
	public static int updateQueriedFlag ( long batchId, Connection conn ) {
	    PreparedStatement stmtUpdate = null;
	    int updated = 0;
	    	try {
	    		StringBuffer buffer = new StringBuffer();
	    		buffer.append("update POS_TRX_EB_BATCHES");
	    		buffer.append(" set QUERIED_FLAG = 'S'");
	    		buffer.append(" where IDENTIFIER = ?");

	    		stmtUpdate = conn.prepareStatement(buffer.toString());
	    		stmtUpdate.setLong(1, batchId);
	    		
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
