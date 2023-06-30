package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dao.Util;
import pojo.InvShipmentEbBatch;
import pojo.RcvTrxEbBatch;
import pojo.SendGroup;
import util.UtilPOS;

public class InvShipmentsEbBatchesDAO {
	
	public static int addRow ( InvShipmentEbBatch o, Connection conn ) throws Exception {
	    PreparedStatement ps = null;
	    int inserted = 0;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("insert into INV_SHIPMENTS_EB_BATCHES (");
	        buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, BATCH_NUMBER,");
	        buffer.append(" TRANSMISS_DATE, CREATED_BY, CREATED_ON, TRX_TYPE,");
	        buffer.append(" TRX_DATE, QUERIED_FLAG, ITEMS_QTY )");
	        buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	        buffer.append(" ? )");

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
	        ps.setString(10, "N");
	        ps.setInt(11, 0);

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
	 * Obtener los datos de un lote de documentos accediendo por numero delote Sifen.
	 * @param batchNumber - Numero de lote a consultar
	 * @return InvShipmentEbBatch - Pojo que representa los datos del lote de documentos
	 */
	public static InvShipmentEbBatch getRowByNumber ( String batchNumber ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		try {
			conn =   Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.ORG_ID, x.UNIT_ID, to_char(x.TRANSMISS_DATE, 'dd/mm/yyyy hh24:mi:ss') TRANSMISS_DATE,");
			buffer.append(" x.CREATED_BY, x.CREATED_ON, x.BATCH_NUMBER, x.RESULT_CODE,");
			buffer.append(" x.RESULT_MESSAGE, x.PROCESS_TIME, x.QUERIED_FLAG, x.TRX_TYPE,");
			buffer.append(" x.TRX_DATE, x.ITEMS_QTY");
			buffer.append(" from INV_SHIPMENTS_EB_BATCHES x");

			buffer.append(" where x.BATCH_NUMBER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setString(1, batchNumber);
			rs = ps.executeQuery();

			InvShipmentEbBatch o = new InvShipmentEbBatch();

			if (rs.next()) {
				dataFound = true;
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
			}
			if (dataFound == true) {
				return o;
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
	 * Obtener la lista de transacciones Receivable que aun no han sido transmitidas a la 
	 * entidad recaudadora de impuestos correspondiente a un tipo de transaccion dentro de 
	 * un rango de fechas.
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
			buffer.append(" from INV_EB_SHIPMENTS x");
			buffer.append(" where x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");
			buffer.append(" and to_number(x.REF_NUMBER) <= ?");
			buffer.append(" and to_number(x.REF_NUMBER) >= ?");
			buffer.append(" group by to_number(x.REF_NUMBER)");
			buffer.append(" having sum(decode(x.EB_BATCH_ID, null, 1, 0)) > 0");
			buffer.append(" order by to_number(x.REF_NUMBER)");

			System.out.println("**********getTrxGroupsList");
			System.out.println(buffer.toString());
			System.out.println("param. en el DAO: " + trxDate + " - " + fromNumber + " - " + toNumber);
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
	 * Obtener la lista de lotes de documentos enviados correspondiente a un rango de fechas.
	 * @param batchNumber - Numero de lote a consultar
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<InvShipmentEbBatch> - Lista de lotes transmitidos
	 */
	public static ArrayList<InvShipmentEbBatch> getList ( String batchNumber,
			                                              java.util.Date fromDate, 
			                                              java.util.Date toDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<InvShipmentEbBatch> l;
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
			buffer.append(" x.TRX_DATE, x.ITEMS_QTY");
			buffer.append(" from INV_SHIPMENTS_EB_BATCHES x");

			buffer.append(" where 1 = 1");
			// variable parameters
			if (toDate != null) {
				buffer.append(" and x.TRX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRX_DATE >= ?");
			}
			if (batchNumber != null) {
				buffer.append(" and x.BATCH_NUMBER = ?");				
			}
			// order by
			buffer.append(" order by x.TRX_DATE, IDENTIFIER");

			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			if (batchNumber != null) {
				index++;
				ps.setString(index, batchNumber);
			}
			rs = ps.executeQuery();

			l = new ArrayList<InvShipmentEbBatch>();

			while (rs.next()) {
				dataFound = true;
				InvShipmentEbBatch o = new InvShipmentEbBatch();
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
	 * Obtener la lista de lotes de documentos correspondientes a un tipo de transaccion y
	 * un rango de fechas que aun no han sido consultados por la aplicacion.
	 * @param trxType - Tipo de transaccion que contiene el lote
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<InvShipmentEbBatch> - Lista de lotes transmitidos
	 */
	public static ArrayList<InvShipmentEbBatch> getNotQueriedList ( java.util.Date fromDate, 
			                                                        java.util.Date toDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<InvShipmentEbBatch> l;
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
			buffer.append(" x.TRX_DATE, x.ITEMS_QTY");
			buffer.append(" from INV_SHIPMENTS_EB_BATCHES x");

			buffer.append(" where x.QUERIED_FLAG = 'N'");
			// variable parameters
			if (fromDate != null) {
				buffer.append(" and x.TRX_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRX_DATE < ?");
			}
			// order by
			buffer.append(" order by x.TRX_DATE, IDENTIFIER");

			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<InvShipmentEbBatch>();

			while (rs.next()) {
				dataFound = true;
				InvShipmentEbBatch o = new InvShipmentEbBatch();
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

	public static long getMaxId ( Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long maxId = 0;

		try {
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select max(x.IDENTIFIER)");
			buffer.append(" from INV_SHIPMENTS_EB_BATCHES x");

			ps = conn.prepareStatement(buffer.toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				maxId = rs.getLong(1);
			}
			return maxId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}
	
    public static int updateQueriedFlag ( long batchId, Connection conn ) {
	    //Connection conn =  null;
        PreparedStatement ps = null;

        try {
			//conn = Util.getConnection();
			//if (conn == null) {
			//	return 0;
			//}
            StringBuffer buffer = new StringBuffer();

            buffer.append("update INV_SHIPMENTS_EB_BATCHES");
            buffer.append(" set QUERIED_FLAG = 'S'");
            buffer.append(" where IDENTIFIER = ?");

            ps = conn.prepareStatement(buffer.toString());

            ps.setLong(1, batchId);
            int rowsQty = ps.executeUpdate();
            System.out.println("Actualizando flag consulta lote: " + batchId);
            return rowsQty;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Util.closeStatement(ps);
        }
    }

}
