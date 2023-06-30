package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposItemsOperacion;
import pojo.CamposIvaItem;
import pojo.CamposValoresItem;
import util.UtilPOS;

public class ItemsFacturasDAO {
	
	public static ArrayList<CamposItemsOperacion> listaItems ( long invoiceId, double exchangeRate, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    short counter = 0;
	    double quantity;
	    double unitPrice;
	    double unitDiscount;
	    double itemDiscPct;
	    double globalDiscAmt; 
	    double itemAdvAmt;
	    double globalAdvAmt;
	    double itemExchgRate;
	    double taxRate;
	    double itemAmount;
	    //
	    try {
            ArrayList<CamposItemsOperacion> l = new ArrayList<CamposItemsOperacion>();
	        StringBuffer buffer = new StringBuffer();
	        
	        buffer.append("select 'P-' || p.INTERNAL_CODE DCODINT, p.DESCRIPTION DDESPROSER, u1.CODE CUNIMED, u1.DESCRIPTION DDESUNIMED,");        
	        buffer.append(" x.QUANTITY, x.UNIT_PRICE, x.DISCOUNT_AMT, x.DISCOUNT_PCT,");
	        buffer.append(" x.UNIT_DISCOUNT, x.TAX_TYPE_ID, x.TAX_RATE");
	        buffer.append(" from FND_ELEC_INVOICING_UOMS u1,");
	        buffer.append(" INV_UNIT_OF_MEASURES u2,");
	        buffer.append(" INV_MATERIALS p,");
	        buffer.append(" RCV_CUSTOMER_TRX_LINES x");
	        
	        buffer.append(" where u1.IDENTIFIER = u2.EB_UOM_ID");
	        buffer.append(" and u2.IDENTIFIER = x.ITEM_UOM_ID");
	        buffer.append(" and p.IDENTIFIER = x.ITEM_ID");
	        buffer.append(" and x.LINE_TYPE = 'PRODUCTO'");
	        buffer.append(" and x.CUSTOMER_TRX_ID = ?");

	        buffer.append(" union all ");

	        buffer.append("select 'S-' || p.INTERNAL_CODE DCODINT, p.DESCRIPTION DDESPROSER, '77' CUNIMED, 'UNIDAD' DDESUNIMED,");        
	        buffer.append(" x.QUANTITY, x.UNIT_PRICE, x.DISCOUNT_AMT, x.DISCOUNT_PCT,");
	        buffer.append(" x.UNIT_DISCOUNT, x.TAX_TYPE_ID, x.TAX_RATE");
	        buffer.append(" from INV_SERVICES p,");
	        buffer.append(" RCV_CUSTOMER_TRX_LINES x");
	        
	        buffer.append(" where p.IDENTIFIER = x.SERVICE_ID");
	        buffer.append(" and x.LINE_TYPE = 'SERVICIO'");
	        buffer.append(" and x.CUSTOMER_TRX_ID = ?");
	        
	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, invoiceId);
	        ps.setLong(2, invoiceId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	dataFound = true;
	        	CamposItemsOperacion o = new CamposItemsOperacion();
	        	o.setcPaisOrig(null);
	        	o.setcRelMerc(Short.valueOf("0"));
	        	o.setcUniMed(Short.valueOf(rs.getString("CUNIMED")));
	        	o.setdCanQuiMer(null);
	        	quantity = UtilPOS.appRound(rs.getDouble("QUANTITY"), 4);
	        	o.setdCantProSer(BigDecimal.valueOf(quantity));
	        	o.setdCDCAnticipo(null);
	        	o.setdCodInt(rs.getString("DCODINT"));
	        	o.setdDesPaisOrig(null);
	        	o.setdDesProSer(rs.getString("DDESPROSER"));
	        	o.setdDesRelMerc(null);
	        	o.setdDesUniMed(rs.getString("DDESUNIMED"));
	        	o.setdDncpE(null);
	        	o.setdDncpG(null);
	        	o.setdGtin(0);
	        	o.setdGtinPq(0);
	        	o.setdInfItem(null);
	        	o.setdNCM(0);
	        	o.setdParAranc(Short.valueOf("0"));
	        	o.setdPorQuiMer(null);
	        	// completar los valores del item
	        	unitPrice = UtilPOS.appRound(rs.getDouble("UNIT_PRICE"), 8);
	        	unitDiscount = UtilPOS.appRound(rs.getDouble("DISCOUNT_AMT") / rs.getDouble("QUANTITY"), 8);
	        	taxRate = rs.getDouble("TAX_RATE");
	        	itemDiscPct = rs.getDouble("DISCOUNT_PCT");
	        	globalDiscAmt = 0.0;
	        	itemAdvAmt = 0.0;
	        	globalAdvAmt = 0.0;
	        	itemExchgRate = exchangeRate;
	        	ItemsUtils utl = new ItemsUtils();
	        	//System.out.println("(1)item: " + rs.getString("DCODINT") + " desc.: " + unitDiscount);
	        	CamposValoresItem v = utl.calcItemValues ( quantity, 
	        			                                   unitPrice, 
	        			                                   unitDiscount, 
	        			                                   itemDiscPct, 
	        			                                   globalDiscAmt, 
	        			                                   itemAdvAmt, 
	        			                                   globalAdvAmt, 
	        			                                   itemExchgRate );
	        	//System.out.println("(2)item: " + rs.getString("DCODINT") + " desc.: " + v.getgValorRestaItem().getdDescItem());
	        	o.setgValorItem(v);
	        	// Completar los campos del IVA
	        	itemAmount = v.getgValorRestaItem().getdTotOpeItem().doubleValue();
	        	CamposIvaItem t = utl.calcTaxValues(taxRate, itemAmount);
	        	o.setgCamIVA(t);
	        	//
   	        	l.add(o);
	        	counter ++;
	        }
	        //
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

}
