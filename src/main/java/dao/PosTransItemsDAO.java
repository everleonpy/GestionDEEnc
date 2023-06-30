package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import pojo.GenericElement;
import pojo.PosInvoiceItemData;

public class PosTransItemsDAO {
	
	public static ArrayList<PosInvoiceItemData> listaPlana ( long invoiceId, long controlId, long cashId, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			ArrayList<PosInvoiceItemData> l = new ArrayList<PosInvoiceItemData>();
			StringBuffer buffer = new StringBuffer();

			buffer.append("select p.INTERNAL_CODE, p.BAR_CODE, p.SHORT_NAME, p.DESCRIPTION,");
			buffer.append(" p.QTY_HANDLING, x.QUANTITY, x.UNIT_PRICE, x.AMOUNT, x.TAX_RATE,");
			buffer.append(" x.IDENTIFIER, x.PRODUCT_ID");
			buffer.append(" from INV_MATERIALS p,");
			buffer.append(" POS_TRANS_ITEMS x");

			buffer.append(" where p.IDENTIFIER = x.PRODUCT_ID");
			buffer.append(" and x.ITEM_TYPE = 'PRODUCTO'");
			buffer.append(" and x.CASH_REGISTER_ID = ?");
			buffer.append(" and x.CASH_CONTROL_ID = ?");
			buffer.append(" and x.TRANSACTION_ID = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, invoiceId);
			rs = ps.executeQuery();

			// System.out.println("buscando items de: " + cashId + " - " + controlId + " - "
			// + invoiceId);
			while (rs.next()) {
				dataFound = true;
				PosInvoiceItemData o = new PosInvoiceItemData();
				o.setBarCode(rs.getString("BAR_CODE"));
				o.setCashControlId(controlId);
				o.setCashId(cashId);
				o.setInternalCode(rs.getString("INTERNAL_CODE"));
				o.setItemAmount(rs.getDouble("QUANTITY"));
				o.setItemDescription(rs.getString("DESCRIPTION"));
				o.setItemId(rs.getLong("IDENTIFIER"));
				o.setItemQuantity(rs.getDouble("QUANTITY"));
				o.setProductId(rs.getLong("PRODUCT_ID"));
				o.setShortName(rs.getString("SHORT_NAME"));
				o.setTaxRate(rs.getDouble("TAX_RATE"));
				o.setTransactionId(invoiceId);
				o.setUnitPrice(rs.getDouble("QUANTITY"));
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
		}
	}

	public static double getInvoiceAmount ( long invoiceId, long controlId, long cashId ) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amount = 0.0;
		boolean dataFound = false;
		//
		try {
			conn = Util.getConnection();
			if ( conn == null ) {
				return 0.0;
			}
			StringBuffer buffer = new StringBuffer();

			buffer.append("select sum(x.AMOUNT) AMOUNT");
			buffer.append(" from POS_TRANS_ITEMS x");
			buffer.append(" where x.CASH_REGISTER_ID = ?");
			buffer.append(" and x.CASH_CONTROL_ID = ?");
			buffer.append(" and x.TRANSACTION_ID = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, invoiceId);
			rs = ps.executeQuery();

			// System.out.println("buscando items de: " + cashId + " - " + controlId + " - "
			// + invoiceId);
			if (rs.next()) {
				amount = rs.getDouble("AMOUNT");
			}
			return amount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}

	public static Vector<GenericElement> getDiscountList ( long transactionId, long controlId, long cashId, Connection conn ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;

		try {
			StringBuffer buffer = new StringBuffer();
					
		    buffer.append("select p.BAR_CODE,");
		    buffer.append(" p.DESCRIPTION,");
		    buffer.append(" sum(i.DISCOUNT_AMOUNT),");
		    buffer.append(" max(i.UNIT_PRICE),");
		    buffer.append(" max(i.UNIT_PRICE - nvl(i.UNIT_DISC_AMOUNT, 0)),");
		    buffer.append(" max(i.UNIT_DISC_AMOUNT),");
		    buffer.append(" sum(i.AMOUNT),");
		    buffer.append(" min(i.ITEM_NUMBER)");
		    buffer.append(" FROM POS_PRODUCTS p, POS_TRANS_ITEMS i");
		    buffer.append(" WHERE p.IDENTIFIER = i.PRODUCT_ID");
		    buffer.append(" AND i.CASH_REGISTER_ID = ?");
		    buffer.append(" AND i.CASH_CONTROL_ID = ?");
		    buffer.append(" AND i.TRANSACTION_ID = ?");
		    buffer.append(" GROUP BY p.BAR_CODE, p.DESCRIPTION");
		    buffer.append(" HAVING sum(i.DISCOUNT_AMOUNT) > 0");		    
		    buffer.append(" ORDER BY 8");		    
			
			ps = conn.prepareStatement(buffer.toString());			
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, transactionId);
			rs = ps.executeQuery();
			
			Vector<GenericElement> lista = new Vector<GenericElement>();
			while (rs.next()) { 
				GenericElement datos = new GenericElement();
				datos.setCODE(rs.getString(1));
				datos.setDESCRIPTION(rs.getString(2));
				datos.setAMOUNT(rs.getDouble(3));
				datos.setDISCOUNT_AMOUNT(rs.getDouble(3));
				datos.setUNIT_PRICE(rs.getDouble(4));
				datos.setNET_UNIT_PRICE(rs.getDouble(5));
				datos.setUNIT_DISCOUNT_AMOUNT(rs.getDouble(6));
				datos.setGROSS_AMOUNT(rs.getDouble(7) + rs.getDouble(3));
				datos.setNET_AMOUNT(rs.getDouble(7));
				//System.out.println("******promoList: " + rsConsulta.getDouble(3) + " - " + rsConsulta.getDouble(6) + " - " + rsConsulta.getDouble(5));
				lista.add(datos);
			    dataFound = true;
		    }
			
			if (dataFound == true) {
				return lista;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}
	
}
