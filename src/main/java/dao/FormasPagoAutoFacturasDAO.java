package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposOperacionContado;
import pojo.CamposPagoCheque;

public class FormasPagoAutoFacturasDAO {

	public static ArrayList<CamposOperacionContado> listaFormasPago ( long invoiceId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    //
	    	final short EFECTIVO = 1;
	    final short CHEQUE = 2;
	    //
	    try {
        	    ArrayList<CamposOperacionContado> gPaConEIni = new ArrayList<CamposOperacionContado>();
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select y.CODE CMONETIPAG, y.DESCRIPTION DDMONETIPAG, o.PAYMENT_METHOD PMT_TERM, x.AMOUNT DMONTIPAG,");
	        buffer.append(" o.EXCHANGE_RATE DTICAMTIPAG, 'X' DNUMCHEQ, 'XX' DBCOEMI");
	        buffer.append(" from FND_CURRENCIES y,");
	        buffer.append(" PAY_PAYMENT_ORDERS o,");
	        buffer.append(" PAY_PMT_ORDER_ITEMS x");
	        
	        buffer.append(" where y.IDENTIFIER = x.CURRENCY_ID");
	        buffer.append(" and o.PAYMENT_METHOD in ('EFECTIVO')");
	        buffer.append(" and o.PO_STATUS in ('CONFIRMADO', 'DESEMBOLSADO')");
	        buffer.append(" and o.IDENTIFIER = x.PAYMENT_ORDER_ID");
	        buffer.append(" and x.INVOICE_ID = ?");

	        buffer.append(" union all");
	        
	        buffer.append("select y.CODE, y.DESCRIPTION, o.PAYMENT_METHOD, x.AMOUNT,");
	        buffer.append(" o.EXCHANGE_RATE, h.CHECK_NO, b.BANK_NAME");
	        buffer.append(" from FND_CURRENCIES y,");
	        buffer.append(" PAY_BANK_BRANCHES b,");
	        buffer.append(" PAY_BANK_ACCOUNTS a,");
	        buffer.append(" TRM_PAYMENT_CHECKS h,");
	        buffer.append(" PAY_PAYMENT_ORDERS o,");
	        buffer.append(" PAY_PMT_ORDER_ITEMS x");
	        
	        buffer.append(" where y.IDENTIFIER = x.CURRENCY_ID");
	        buffer.append(" and b.IDENTIFIER = a.BANK_BRANCH_ID");
	        buffer.append(" and a.IDENTIFIER = h.BANK_ACCOUNT_ID");
	        buffer.append(" and h.PAYMENT_ORDER_ID = o.IDENTIFIER");
	        buffer.append(" and o.PAYMENT_METHOD in ('CHEQUE')");
	        buffer.append(" and o.PO_STATUS in ('CONFIRMADO', 'DESEMBOLSADO')");
	        buffer.append(" and o.IDENTIFIER = x.PAYMENT_ORDER_ID");
	        buffer.append(" and x.INVOICE_ID = ?");
	        
	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, invoiceId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	    dataFound = true;
	        	    CamposOperacionContado o = new CamposOperacionContado();
	        	    o.setcMoneTiPag(rs.getString("CMONETIPAG"));
	        	    o.setdDMoneTiPag(rs.getString("DDMONETIPAG"));
	        	    o.setiTiPago(EFECTIVO);
        		    o.setdDesTiPag("Efectivo");
	        	    if (rs.getString("PAYMENT_METHOD").equalsIgnoreCase("CHEQUE")) {
	        		    o.setiTiPago(CHEQUE);
	        		    o.setdDesTiPag("Cheque");
	        	    }
                o.setdMonTiPag(rs.getDouble("DMONTIPAG"));
                if (rs.getString("CMONETIPAG").equalsIgnoreCase("PYG") == false) {
                	    o.setdTiCamTiPag(rs.getDouble("DTICAMTIPAG"));
                }
	            //
                if (rs.getString("PAYMENT_METHOD").equalsIgnoreCase("CHEQUE") == true) {
                    CamposPagoCheque gPagCheq = new CamposPagoCheque();
                    gPagCheq.setdBcoEmi(rs.getString("DBCOEMI"));
                    gPagCheq.setdNumCheq(rs.getString("DNUMCHEQ"));
                    o.setgPagCheq(gPagCheq);
                }
                //
                gPaConEIni.add(o);
	        }
	        if (dataFound == true) {
	            return gPaConEIni;
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
