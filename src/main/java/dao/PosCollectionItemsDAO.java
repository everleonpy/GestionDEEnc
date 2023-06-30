package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import pojo.Employee;
import pojo.GenericElement;

public class PosCollectionItemsDAO {

	public static Vector<GenericElement> getItemsList ( long invoiceId, 
			                                            long controlId,
			                                            long cashId,
			                                            boolean summarized, 
			                                            boolean includeChange,
			                                            Connection conn ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		double a1 = 0.0;
		double a2 = 0.0;
		
		try {
			StringBuffer buffer = new StringBuffer();
					
			if (summarized == true) {
			    buffer.append("select f.NAME TERM_NAME, sum(i.ENTERED_AMOUNT) TERM_AMOUNT");
			    buffer.append(" FROM POS_PAYMENT_TERMS f, POS_COLLECTIONS c, POS_COLLECTION_ITEMS i");
			    buffer.append(" WHERE f.IDENTIFIER = i.PMT_TERM_ID");
			    buffer.append(" AND i.CASH_REGISTER_ID = c.CASH_REGISTER_ID");
			    buffer.append(" AND i.CASH_CONTROL_ID = c.CASH_CONTROL_ID");
			    buffer.append(" AND i.COLLECTION_ID = c.IDENTIFIER");
			    buffer.append(" AND c.CASH_REGISTER_ID = ?");
			    buffer.append(" AND c.CASH_CONTROL_ID = ?");
			    buffer.append(" AND c.INVOICE_ID = ?");
			    buffer.append(" GROUP BY f.NAME");
			} else {
			    buffer.append("select f.NAME TERM_NAME, i.ENTERED_AMOUNT TERM_AMOUNT");
			    buffer.append(" FROM POS_PAYMENT_TERMS f, POS_COLLECTIONS c, POS_COLLECTION_ITEMS i");
			    buffer.append(" WHERE f.IDENTIFIER = i.PMT_TERM_ID");
			    buffer.append(" AND i.CASH_REGISTER_ID = c.CASH_REGISTER_ID");
			    buffer.append(" AND i.CASH_CONTROL_ID = c.CASH_CONTROL_ID");
			    buffer.append(" AND i.COLLECTION_ID = c.IDENTIFIER");
			    buffer.append(" AND c.CASH_REGISTER_ID = ?");
			    buffer.append(" AND c.CASH_CONTROL_ID = ?");
			    buffer.append(" AND c.INVOICE_ID = ?");
		    }
			
			ps = conn.prepareStatement(buffer.toString());			
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, invoiceId);
			rs = ps.executeQuery();
			
			Vector<GenericElement> list = new Vector<GenericElement>();
			while (rs.next()) { 
				GenericElement e = new GenericElement();
				e.setDESCRIPTION(rs.getString("TERM_NAME"));
				e.setAMOUNT(rs.getDouble("TERM_AMOUNT"));
				list.add(e);
		    }
			
			if (includeChange == true) {
			    // buscar el componente vuelto
			    buffer = new StringBuffer();
			    buffer.append("select 'VUELTO' TERM_NAME, i.CHANGE_AMOUNT, i.ROUNDED_CHGE_AMOUNT ROUNDED_CHANGE");
			    buffer.append(" FROM POS_COLLECTIONS i");
			    buffer.append(" WHERE i.CASH_REGISTER_ID = ?");
			    buffer.append(" AND i.CASH_CONTROL_ID = ?");
			    buffer.append(" AND i.INVOICE_ID = ?");
			
			    ps = conn.prepareStatement(buffer.toString());			
			    ps.setLong(1, cashId);
			    ps.setLong(2, controlId);
			    ps.setLong(3, invoiceId);
			    rs = ps.executeQuery();
			    if (rs.next()) { 
			        GenericElement e = new GenericElement();
			        e.setDESCRIPTION(rs.getString("TERM_NAME"));
			        a1 = rs.getDouble("CHANGE_AMOUNT");
			        a2 = rs.getDouble("ROUNDED_CHANGE");
			        e.setAMOUNT(a1);
			        if (a2 > 0 ) {
			            e.setAMOUNT(a2);
			        }
			        list.add(e);
			   }
			}
			return list;	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}	

	public static Employee getEmployee ( long cashId, long controlId, long invoiceId, Connection conn ) {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select E.IDENTIFIER, E.LAST_NAME, E.FIRST_NAME, E.IDENTITY_NO");
			buffer.append(" FROM POS_EMPLOYEES e, POS_PAYMENT_TERMS f, POS_COLLECTION_ITEMS i");
			buffer.append(" WHERE f.PAYMENT_TYPE = 'VALE-EMPLEADO'");
			buffer.append(" AND f.IDENTIFIER = i.PMT_TERM_ID");
			buffer.append(" AND e.IDENTIFIER = i.EMPLOYEE_ID");
			buffer.append(" AND i.CASH_REGISTER_ID = ?");
			buffer.append(" AND i.CASH_CONTROL_ID = ?");
			buffer.append(" AND i.INVOICE_ID = ?");			    	

			ps = conn.prepareStatement(buffer.toString());			
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, invoiceId);
			rs = ps.executeQuery();

			Employee e = new Employee();
			if (rs.next()) { 
				dataFound = true;
				e.setIDENTIFIER(rs.getLong("IDENTIFIER"));
				e.setLAST_NAME(rs.getString("LAST_NAME"));
				e.setFIRST_NAME(rs.getString("FIRST_NAME"));
				e.setIDENTITY_NO(rs.getString("IDENTITY_NO"));
			}
			if (dataFound == true) {
				return e;
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
