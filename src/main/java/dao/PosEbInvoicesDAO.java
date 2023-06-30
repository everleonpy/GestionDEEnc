package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import business.ApplicationMessage;
import pojo.GenericStringsList;
import pojo.PrepareTxParams;
import pojo.PosEbInvoice;
import util.UtilPOS;

public class PosEbInvoicesDAO {

	public static ArrayList<PosEbInvoice> getList ( int trxType, 
			                                        java.util.Date txDate, 
			                                        long siteId, 
			                                        String txNumber ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		ArrayList<PosEbInvoice> l;
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
			buffer.append(" x.DEFEEMIDE, x.ITIPTRA, x.TAXNUMBER, x.TAXNUMBERCD,");
			buffer.append(" x.IDENTITYTYPE, x.IDENTITYNUMBER, x.CUSTOMERNAME, s.NAME SITE_NAME,");
			buffer.append(" x.TRXCONDITION, x.AMOUNT, x.COLLECTIONS_AMT");

			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_EB_INVOICES x");

			buffer.append(" where s.IDENTIFIER = x.SITEID");
		    buffer.append(" and x.EB_BATCH_ID is null");
		    buffer.append(" and x.ITIPTRA = ?");
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");
			
			if (siteId != 0) {
				buffer.append(" and x.SITEID = ?");
			}
			if (txNumber != null) {
				buffer.append(" and DEST || '-' || DPUNEXP || '-' || DNUMDOC = ?");
			}
			//
			System.out.println("parametros: " + trxType + " - " + txDate + " - " + siteId + " - " + txNumber);
			System.out.println(buffer.toString());
			ps = conn.prepareStatement(buffer.toString());
			//
			index++;
			ps.setLong(index, trxType);
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

			l = new ArrayList<PosEbInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosEbInvoice o = new PosEbInvoice();
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
                o.setItiptra(rs.getInt("ITIPTRA"));
                o.setTaxnumber(rs.getString("TAXNUMBER"));
                o.setTaxnumbercd(rs.getString("TAXNUMBERCD"));
                o.setIdentitytype(rs.getString("IDENTITYTYPE"));
                o.setIdentitynumber(rs.getString("IDENTITYNUMBER"));
                o.setCustomername(rs.getString("CUSTOMERNAME"));
                o.setSiteName(rs.getString("SITE_NAME"));  
                o.setTrxcondition(rs.getString("TRXCONDITION"));
                o.setAmount(rs.getDouble("AMOUNT"));
                o.setCollectionsAmt(rs.getDouble("COLLECTIONS_AMT"));
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
	
	public static int preparedInvoicesQty ( int iTipTra, 
			                                java.util.Date trxDate ) {

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
			buffer.append(" from POS_EB_INVOICES x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from POS_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.CASH_REGISTER_ID = x.CASHID");
		    buffer.append(" and b.CASH_CONTROL_ID = x.CASHCONTROLID");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");

		    buffer.append(" and x.ITIPTRA = ?");
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, iTipTra );			
			ps.setTimestamp(2, new Timestamp(toDate.getTime()));
			ps.setTimestamp(3, new Timestamp(fromDate.getTime()));

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
			buffer.append(" from POS_EB_INVOICES x");
			buffer.append(" where x.EB_BATCH_ID is null");
			buffer.append(" and x.ITIPTRA = ?");
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, iTipTra );			
			ps.setTimestamp(2, new Timestamp(toDate.getTime()));
			ps.setTimestamp(3, new Timestamp(fromDate.getTime()));

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
	
	public static PrepareTxParams prepareInvoices ( java.util.Date trxDate, 
			                                        String fromTime,
			                                        String toTime ) {
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}
			
			System.out.println("Preparando tx POS: " + trxDate + " - " + fromTime + " - " + toTime);
			/*
			   procedure p_get_invoices ( p_date in date, 
                             p_from_time in varchar2, 
                             p_to_time in varchar2,
                             p_batch_id out number,
                             p_rows_qty out number, 
                             p_from_group out number, 
                             p_to_group out number ) is
			 */
			PrepareTxParams o = new PrepareTxParams();
			cs = conn.prepareCall("{call pos_eb_data.p_get_invoices(?, ?, ?, ?, ?, ?, ?)}");
			cs.setTimestamp(1, new Timestamp(trxDate.getTime()));
			cs.setString(2, fromTime);
			cs.setString(3, toTime);
			//
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.executeQuery();
			o.setBatchId(cs.getInt(4));
			o.setRowsQty(cs.getInt(5));
			o.setFirstGroup(cs.getInt(6));
			o.setLastGroup(cs.getInt(7));
			System.out.println("Resultados: " + o.getBatchId() + " - " + o.getFirstGroup() + " - " + o.getLastGroup());
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
	
	public static int deleteInvoices ( java.util.Date trxDate ) {
		Connection conn = null;
		CallableStatement cs = null;
		int deletedQty = 0;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			System.out.println("Eliminando tx POS: " + trxDate );
			/*
             procedure p_delete_invoices ( p_date in date ) is
			*/
			cs = conn.prepareCall("{call pos_eb_data.p_delete_invoices(?, ?)}");
			cs.setTimestamp(1, new Timestamp(trxDate.getTime()));
			//
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.executeQuery();
			deletedQty = cs.getInt(2);
			//
			return deletedQty;
		} catch (Exception e) {
			return 0;
		} finally {
			Util.closeCallableStatement(cs);
			Util.closeJDBCConnection(conn);
		}
	}

	public static GenericStringsList getFirstLastGroup ( int iTipTra, 
			                                             java.util.Date fromDate, 
			                                             java.util.Date toDate ) {

		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		GenericStringsList o = new GenericStringsList();
		java.util.Date d = null;    

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select min(to_number(x.REF_NUMBER)), max(to_number(x.REF_NUMBER))");
			buffer.append(" from POS_EB_INVOICES x");
			buffer.append(" where x.ITIPTRA = ?");
			buffer.append(" and x.DEFEEMIDE < ?");
			buffer.append(" and x.DEFEEMIDE >= ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, iTipTra );			
			d = UtilPOS.addDaysToDate(toDate, 1);
			ps.setTimestamp(2, new Timestamp(d.getTime()));
			ps.setTimestamp(3, new Timestamp(fromDate.getTime()));

			rs = ps.executeQuery();
			if (rs.next()) { 
				o.setElement1(String.valueOf(rs.getLong(1)));
				o.setElement2(String.valueOf(rs.getLong(2)));
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

	public static int updateBatchId ( long transactionId, 
			String controlCode,
			long ebBatchId,
			Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update POS_EB_INVOICES");
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
