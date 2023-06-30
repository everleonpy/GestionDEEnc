package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RcvCustomerTrxLinesDAO {

	public static double getInvoiceAmount ( long invoiceId, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		double amount = 0.0;
		//
		try {
			conn = Util.getConnection();
			if ( conn == null ) {
				return 0.0;
			}
			StringBuffer buffer = new StringBuffer();

			buffer.append("select sum(x.AMOUNT) AMOUNT");
			buffer.append(" from RCV_CUSTOMER_TRX_LINES x");
			buffer.append(" where x.CUSTOMER_TRX_ID = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, invoiceId);
			rs = ps.executeQuery();

			// System.out.println("buscando items de: " + invoiceId);
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
		}
	}
	
}
