package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.PrepareTxParams;
import pojo.InvEbShipment;
import util.UtilPOS;

public class InvEbShipmentsDAO {

	public static ArrayList<InvEbShipment> getList ( java.util.Date txDate, 
			                                         long siteId, 
			                                         String txNumber ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		ArrayList<InvEbShipment> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = txDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.IDENTIFIER, x.BATCH_ID, x.REF_NUMBER, x.EB_BATCH_ID,");
			buffer.append(" x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CANCELED_ON, x.DNUMTIM,");
			buffer.append(" x.DEST, x.DPUNEXP, x.DNUMDOC, x.DSERIENUM,");
			buffer.append(" x.FROMSITEID, f.NAME FROMSITENAME, x.TOSITEID, t.NAME TOSITENAME,");
			buffer.append(" x.DEFEEMIDE, x.TAXNUMBER, x.TAXNUMBERCD, x.IDENTITYTYPE,");
			buffer.append(" x.IDENTITYNUMBER, x.CUSTOMERNAME, x.SHIPMENT_REASON, x.AMOUNT,");
			buffer.append(" x.SHIPMENT_TYPE, x.SHIPMENT_REASON");

			buffer.append(" from FND_SITES t,");
			buffer.append(" FND_SITES f,");
			buffer.append(" INV_EB_SHIPMENTS x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from INV_SHP_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and t.IDENTIFIER = x.TOSITEID");
			buffer.append(" and f.IDENTIFIER = x.FROMSITEID");
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

			if (siteId != 0) {
				buffer.append(" and x.FROMSITEID = ?");
			}
			if (txNumber != null) {
				buffer.append(" and DEST || '-' || DPUNEXP || '-' || DNUMDOC = ?");
			}
			//
			System.out.println("parametros: " + txDate + " - " + siteId + " - " + txNumber);
			System.out.println(buffer.toString());
			ps = conn.prepareStatement(buffer.toString());
			//
			index++;
			ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			index++;
			ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (txNumber != null) {
				index++;
				ps.setString(index, txNumber);
			}
			rs = ps.executeQuery();

			l = new ArrayList<InvEbShipment>();

			while (rs.next()) {
				dataFound = true;
				InvEbShipment o = new InvEbShipment();
				//
				o.setIdentifier(rs.getLong("IDENTIFIER"));	
				o.setBatchId(rs.getLong("BATCH_ID"));
				o.setRefNumber(rs.getString("REF_NUMBER"));
				o.setEbBatchId(rs.getLong("EB_BATCH_ID"));
				o.setEbSendDate(rs.getDate("EB_SEND_DATE"));
				o.setEbControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setCanceledOn(rs.getDate("CANCELED_ON"));
				o.setDnumtim(rs.getString("DNUMTIM"));
				o.setDest(rs.getString("DEST"));
				o.setDpunexp(rs.getString("DPUNEXP"));
				o.setDnumdoc(rs.getString("DNUMDOC"));
				o.setDserienum(rs.getString("DSERIENUM"));
				if (o.getDserienum() != null) {
					o.setTxNumber(o.getDserienum() + "/" + o.getDest() + "-" + o.getDpunexp() + "-" + o.getDnumdoc());
				} else {
					o.setTxNumber(o.getDest() + "-" + o.getDpunexp() + "-" + o.getDnumdoc());
				}
				String s = sdf.format(rs.getDate("DEFEEMIDE"));
				o.setDefeemide(s);
				o.setShipmentType(rs.getString("SHIPMENT_TYPE"));
				o.setShipmentReason(rs.getString("SHIPMENT_REASON"));
				o.setTaxnumber(rs.getString("TAXNUMBER"));
				o.setTaxnumbercd(rs.getString("TAXNUMBERCD"));
				o.setIdentitytype(rs.getString("IDENTITYTYPE"));
				o.setIdentitynumber(rs.getString("IDENTITYNUMBER"));
				o.setCustomername(rs.getString("CUSTOMERNAME"));
				//
				o.setFromSiteId(rs.getLong("FROMSITEID"));
				o.setFromSiteName(rs.getString("FROMSITENAME"));  
				o.setToSiteId(rs.getLong("TOSITEID"));
				o.setToSiteName(rs.getString("TOSITENAME"));  
				o.setAmount(rs.getDouble("AMOUNT"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				o.setCustDocType(o.getIdentitytype());
				o.setCustDocNumber(o.getIdentitynumber());
				if (o.getTaxnumber() != null) {
					o.setCustDocType("RUC");
					o.setCustDocNumber(o.getTaxnumber());
				}
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
	
	public static int preparedShipmentsQty ( java.util.Date trxDate ) {

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
			buffer.append(" from INV_EB_SHIPMENTS x");
			
		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from INV_SHP_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");

		    buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

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

	public static PrepareTxParams findPreparedInfo ( int iTipTra, 
			java.util.Date trxDate ) {

		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
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
			buffer.append("select min(to_number(REF_NUMBER)) FIRST_GRP,");
			buffer.append(" max(to_number(REF_NUMBER)) LAST_GRP");
			buffer.append(" from INV_EB_SHIPMENTS x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from INV_SHP_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));

			PrepareTxParams o = new PrepareTxParams();
			rs = ps.executeQuery();
			if (rs.next()) { 
				o.setFirstGroup(rs.getInt("FIRST_GRP"));
				o.setLastGroup(rs.getInt("LAST_GRP"));
			}
			return o;	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}

	public static void defaultValues ( String trxDate ) {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}
			cs = conn.prepareCall("{call inv_eb_shp_data.p_default_values(?)}");
			cs.setString(1, trxDate);
			cs.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.closeCallableStatement(cs);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static PrepareTxParams prepareShipments ( String trxDate, long shipmentId ) {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			PrepareTxParams o = new PrepareTxParams();
			cs = conn.prepareCall("{call inv_eb_shp_data.p_get_shipments(?, ?, ?, ?, ?, ?)}");
			cs.setString(1, trxDate);
			if (shipmentId > 0) {
				cs.setLong(2, shipmentId);
			} else {
				cs.setNull(2, java.sql.Types.INTEGER);
			}
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.executeQuery();
			o.setBatchId(cs.getInt(3));
			o.setRowsQty(cs.getInt(4));
			o.setFirstGroup(cs.getInt(5));
			o.setLastGroup(cs.getInt(6));
			//
			return o;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeCallableStatement(cs);
			Util.closeJDBCConnection(conn);
		}
	}

	public static int updateBatchId ( long transactionId, 
			String controlCode,
			long ebBatchId,
			Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update INV_EB_SHIPMENTS");
			buffer.append(" set CONTROLCODE = ?,");
			buffer.append(" EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			stmtUpdate.setLong(3, ebBatchId);
			stmtUpdate.setLong(4, transactionId);
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
