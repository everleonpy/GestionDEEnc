package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.InvEbTransmissionLog;
import pojo.InvShipment;
import pojo.InvShipmentEbBatch;
import util.UtilPOS;

public class InvInternalShipmentsDAO {
	
	/**
	 * Obtener la lista de facturas que aun no han sido transmitidas a la entidad recaudadora
	 * de impuestos correspondiente a una caja dentro de un tango de fechas.
	 * @param siteId - Identificador de sucursal origen
	 * @param trxDate - Fecha para la consulta de las transacciones
	 * @return ArrayList<InvShipment> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<InvShipment> getShipmentsNotSentList ( long siteId, 
			                                                       java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<InvShipment> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.WAYBILL_NO, to_char(x.SHIPMENT_DATE, 'dd/mm/yyyy hh24:mi:ss') SHIPMENT_DATE, x.SHIPMENT_TYPE,");
			buffer.append(" x.LAST_COST_AMT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.STAMP_ID, x.UNIT_ID,");
			buffer.append(" x.ORG_ID, x.ISSUE_POINT_ID, x.SHIPMENT_REASON,");
			buffer.append(" x.FROM_SITE_ID, f.NAME FROM_SITE_NAME, x.TO_SITE_ID, t.NAME TO_SITE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.SHIPMENT_STATUS,");
			buffer.append(" x.EB_BATCH_ID, x.CANCELED_ON, x.IDENTITY_TYPE, x.IDENTITY_NUMBER");
			buffer.append(" from FND_SITES t,");
			buffer.append(" FND_SITES f,");
			buffer.append(" INV_INTERNAL_SHIPMENTS x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from INV_SHP_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and t.IDENTIFIER = x.TO_SITE_ID");
			buffer.append(" and f.IDENTIFIER = x.FROM_SITE_ID");
            buffer.append(" and x.SHIPMENT_TYPE = 'ENVIO-LOCALES'");

			if (siteId != 0) {
				buffer.append(" and x.FROM_SITE_ID = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.SHIPMENT_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.SHIPMENT_DATE >= ?");
			}
			//
			System.out.println("**********getNotSentList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<InvShipment>();

			while (rs.next()) {
				dataFound = true;
				InvShipment o = new InvShipment();
				//
				o.setShipmentId(rs.getLong("IDENTIFIER"));
				o.setWaybillNo(rs.getString("WAYBILL_NO"));
				d = sdf.parse(rs.getString("SHIPMENT_DATE"));
				o.setShipmentDate(d);
				o.setShipmentReason(rs.getString("SHIPMENT_REASON"));
				o.setAmount(rs.getDouble("LAST_COST_AMT"));
				//
				o.setCustomerId(rs.getLong("CUSTOMER_ID"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIdentityType(rs.getString("IDENTITY_TYPE"));
				o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				//
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("FROM_SITE_ID"));
				o.setSiteName(rs.getString("FROM_SITE_NAME"));
				o.setToSiteId(rs.getLong("TO_SITE_ID"));
				o.setToSiteName(rs.getString("TO_SITE_NAME"));
				o.setShipmentType(rs.getString("SHIPMENT_TYPE"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setShipmentStatus(rs.getString("SHIPMENT_STATUS"));
				o.setEbBatchId(rs.getLong("EB_BATCH_ID"));
				o.setCanceledOn(rs.getDate("CANCELED_ON"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				o.setCustDocType(o.getIdentityType());
				o.setCustDocNumber(o.getIdentityNumber());
				if (o.getTaxNumber() != null) {
					o.setCustDocType("RUC");
					o.setCustDocNumber(o.getTaxNumber());					
				}
				//
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
	
	public static int updateSigantureInfo (long transactionId, String securityCode, String controlCode, java.util.Date signDate ) {
		Connection conn = null;
	    PreparedStatement stmtUpdate = null;
	    int updated = 0;
	    try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("update INV_INTERNAL_SHIPMENTS");
	        buffer.append(" set EB_SEND_DATE = ?,");
	        buffer.append(" EB_SIGN_DATE = ?,");
	        buffer.append(" EB_SECURITY_CODE = ?,");
	        buffer.append(" EB_CONTROL_CODE = ?");
	        buffer.append(" where IDENTIFIER = ?");

	        stmtUpdate = conn.prepareStatement(buffer.toString());
	        stmtUpdate.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
	        stmtUpdate.setTimestamp(2, new Timestamp(signDate.getTime()));
	        stmtUpdate.setString(3, securityCode);
	        stmtUpdate.setString(4, controlCode);
	        stmtUpdate.setLong(5, transactionId);
	        updated = stmtUpdate.executeUpdate();
	        System.out.println("actualizado: " + transactionId);
	        return updated;

	    } catch (Exception e) {
	    	    e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
	    }
	}
	
	public static int updateQrCode ( long transactionId, long orgId, long unitId, String qrCode ) {
		Connection conn = null;
		PreparedStatement ps = null;
		int updated = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("insert into INV_SHP_EB_QR_CODE (");
			buffer.append(" SHIPMENT_ID, ORG_ID, UNIT_ID, QR_CODE )");
			buffer.append(" values ( ?, ?, ?, ? )");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, transactionId);
			ps.setLong(2, orgId);
			ps.setLong(3, unitId);
			ps.setString(4, qrCode);
			updated = ps.executeUpdate();
			return updated;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
		}
	}
	
	public static int createTransmissionLog ( InvEbTransmissionLog tLog ) {
		Connection conn = null;
		PreparedStatement stmtUpdate = null;
		long logId = 0;
		int updated = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("insert into INV_EB_TRANSMISSION_LOG (");
			buffer.append(" IDENTIFIER, SHIPMENT_ID, ORG_ID, UNIT_ID,");
			buffer.append(" EVENT_ID, ERROR_CODE, ERROR_MSG )");
			buffer.append(" values ( ?, ?, ?, ?, ?, ?, ? )");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setLong(1, logId);
			stmtUpdate.setLong(2, tLog.getShipmentId());
			stmtUpdate.setLong(3, tLog.getOrgId());
			stmtUpdate.setLong(4, tLog.getUnitId());
			stmtUpdate.setShort(5, tLog.getEventId());
			stmtUpdate.setString(6, tLog.getErrorCode());
			stmtUpdate.setString(7, tLog.getErrorMsg());
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
	
	public static int shipmentsQty ( java.util.Date trxDate ) {
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsQty = 0;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select count(*)");
		    buffer.append(" from INV_INTERNAL_SHIPMENTS x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from INV_SHP_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
		    
	    	    buffer.append(" and x.STAMP_ID in (60033, 60034, 60035)");
		    buffer.append(" and x.SHIPMENT_TYPE in ('ENVIO-LOCALES', 'ENVIO-A-CLIENTE')");
		    buffer.append(" and x.SHIPMENT_STATUS in ('EN-TRANSITO', 'RECIBIDO', 'CERRADO')");
		    buffer.append(" and x.SHIPMENT_DATE < ?");
		    buffer.append(" and x.SHIPMENT_DATE >= ?");

		    ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));

		    rs = ps.executeQuery();
		    if (rs.next()) { 
		        rowsQty = rs.getInt(1);
		    }
			return rowsQty;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}	
	
	/**
	 * Obtener la lista de transacciones que no cumplen los requisitos para ser enviadas
	 */
	public static int unavailablesQty ( java.util.Date trxDate ) {
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsQty = 0;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select count(*)");
		    buffer.append(" from INV_INTERNAL_SHIPMENTS x");

		    buffer.append(" where ( x.STAMP_ID not in (60033, 60034, 60035)");
		    buffer.append(" or x.SHIPMENT_TYPE not in ('ENVIO-LOCALES', 'ENVIO-A-CLIENTE')");
		    buffer.append(" or x.SHIPMENT_STATUS not in ('EN-TRANSITO', 'RECIBIDO', 'CERRADO') )");
		    buffer.append(" and x.SHIPMENT_DATE < ?");
		    buffer.append(" and x.SHIPMENT_DATE >= ?");

		    ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));

		    rs = ps.executeQuery();
		    if (rs.next()) { 
		        rowsQty = rs.getInt(1);
		    }
			return rowsQty;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}	
	
	
	/**
	 * Obtener la lista de lotes de documentos correspondientes a un tipo de transaccion y
	 * un rango de fechas que aun no han sido consultados por la aplicacion.
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<InvShipmentEbBatch> - Lista de lotes transmitidos
	 */
	public static ArrayList<InvShipmentEbBatch> getNotQueriedList ( String trxType,
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
			if (trxType != null) {
				index++;
				ps.setString(index, trxType);
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

    public static int updateQueriedFlag ( long batchId, Connection conn ) {
	    //Connection conn =  null;
        PreparedStatement ps = null;

        try {
			//conn = Util.getConnection();
			//if (conn == null) {
			//	return 0;
			//}
            StringBuffer buffer = new StringBuffer();

            buffer.append("update RCV_TRX_EB_BATCHES");
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
	
	
	public static int updateControlCode ( long shipmentId, String ctrlCode, String qrCode, Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("update INV_INTERNAL_SHIPMENTS");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_QR_CODE = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, ctrlCode);
			stmtUpdate.setString(2, qrCode);
			stmtUpdate.setLong(3, shipmentId);
			updated = stmtUpdate.executeUpdate();
			//System.out.println("actualizado archivo KuDE: " + transactionId);
			return updated;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
		}
	}
	
	public static int updateBatchId ( long transactionId, 
			                          String controlCode,
			                          long ebBatchId,
			                          String xmlFile,
			                          String qrCode,
			                          Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update INV_INTERNAL_SHIPMENTS");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?,");
			buffer.append(" EB_XML_FILE = ?,");
			buffer.append(" EB_QR_CODE = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			stmtUpdate.setLong(3, ebBatchId);
			stmtUpdate.setString(4, xmlFile);
			stmtUpdate.setString(5, qrCode);
			stmtUpdate.setLong(6, transactionId);
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
	
	/**
	 * Este metodo tiene como objetivo determinar si existe uan transaccion con un numero
	 * determinado en una fecha.
	 */
	public static long existsTx ( java.util.Date txDate, 
			                      String txNumber, 
			                      long siteId ) {

		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		boolean dataFound = false;
		long txId = 0;
		int idx = 0;

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = txDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.IDENTIFIER");
			buffer.append(" from INV_INTERNAL_SHIPMENTS x");
			buffer.append(" where x.SHIPMENT_DATE < ?");
			buffer.append(" and x.SHIPMENT_DATE >= ?");
			buffer.append(" and x.SHIPMENT_NO = ?");
			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");				
			}

			ps = conn.prepareStatement(buffer.toString());
			idx++;
			ps.setTimestamp(idx, new Timestamp(toDate.getTime()));
			idx++;
			ps.setTimestamp(idx, new Timestamp(fromDate.getTime()));
			if (siteId != 0) {
				idx++;
				ps.setLong(idx, siteId);
			}

			rs = ps.executeQuery();
			if (rs.next()) { 
				dataFound = true;
				txId = rs.getLong(1);
			}
			return txId;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}	
	
	public static boolean existsObject ( long objectId ) {
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select IDENTIFIER");
		    buffer.append(" from INV_INTERNAL_SHIPMENTS");
		    buffer.append(" where IDENTIFIER = ?");

		    stmtConsulta = conn.prepareStatement(buffer.toString());
		    stmtConsulta.setLong(1, objectId );			

		    rsConsulta = stmtConsulta.executeQuery();
		    if (rsConsulta.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}

	public static boolean existsSecurityCode ( long codeValue ) {
		
		Connection conn =  null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select EB_SECURITY_CODE");
		    buffer.append(" from INV_INTERNAL_SHIPMENTS");
		    buffer.append(" where IDENTIFIER = ?");

		    stmt = conn.prepareStatement(buffer.toString());
		    stmt.setLong(1, codeValue );			

		    rs = stmt.executeQuery();
		    if (rs.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(stmt);
			Util.closeJDBCConnection(conn);
		}	
	}

}
