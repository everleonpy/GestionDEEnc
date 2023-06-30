package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import pojo.CamposItemsOperacion;
import pojo.CamposIvaItem;
import pojo.CamposValoresItem;
import pojo.PosInvoiceItem;
import util.UtilPOS;

public class ItemsFacturasPosV1DAO {
	
	public static ArrayList<CamposItemsOperacion> listaItems ( long invoiceId, 
			                                                   long controlId, 
			                                                   long cashId, 
			                                                   double exchangeRate, 
			                                                   Connection conn ) {
	    boolean dataFound = false;
	    short uomCode = 0;
	    String uomName;
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
	        //System.out.println("buscando items de: " + cashId + " - " + controlId + " - " + invoiceId);
        	    ArrayList<CamposItemsOperacion> l = new ArrayList<CamposItemsOperacion>();
        	    ArrayList<PosInvoiceItem> itms = ItemsFacturasPosV1DAO.listaPlana(invoiceId, controlId, cashId, conn);
        	    if (itms != null) {
        	    	    Iterator<PosInvoiceItem> itr1 = itms.iterator();
	            while (itr1.hasNext()) {
	        	        dataFound = true;
	        	        PosInvoiceItem x = (PosInvoiceItem) itr1.next();
	        	        CamposItemsOperacion o = new CamposItemsOperacion();
	        	        o.setcPaisOrig(null);
	        	        o.setcRelMerc(Short.valueOf("0"));
	        	        o.setcUniMed(x.getUomCode());
		        	    o.setdDesUniMed(x.getUomName());	        	    	
	        	        o.setdCanQuiMer(null);
		           	quantity = UtilPOS.appRound(x.getQuantity(), 4);
		           	o.setdCantProSer(BigDecimal.valueOf(quantity));
	        	        o.setdCDCAnticipo(null);
	        	        o.setdCodInt(x.getInternalCode());
	        	        o.setdDesPaisOrig(null);
	        	        o.setdDesProSer(x.getItemDescription());
	        	        o.setdDesRelMerc(null);
	        	        o.setdDncpE(null);
	        	        o.setdDncpG(null);
	        	        o.setdGtin(0);
	        	        o.setdGtinPq(0);
	        	        o.setdInfItem(null);
	        	        o.setdNCM(0);
	        	        o.setdParAranc(Short.valueOf("0"));
	        	        o.setdPorQuiMer(null);
	           	    // completar los valores del item
	           	    unitPrice = UtilPOS.appRound(x.getUnitPrice(), 8);
	        	        unitDiscount = UtilPOS.appRound((x.getDiscountAmt() / x.getQuantity()), 8);
	        	        taxRate = x.getTaxRate();
	        	        itemDiscPct = x.getDiscountPct();
	        	        globalDiscAmt = 0.0;
	        	        itemAdvAmt = 0.0;
	        	        globalAdvAmt = 0.0;
	        	        itemExchgRate = exchangeRate;
	         	    ItemsUtils utl = new ItemsUtils();
	          	    CamposValoresItem v = utl.calcItemValues ( quantity, 
	        			                                           unitPrice, 
	        			                                           unitDiscount, 
	        			                                           itemDiscPct, 
	        			                                           globalDiscAmt, 
	        			                                           itemAdvAmt, 
	        			                                           globalAdvAmt, 
	        			                                           itemExchgRate );
	           	    o.setgValorItem(v);
	           	    //System.out.println(o.getcUniMed() + " - " + o.getdDesProSer() + " - " + v.getdPUniProSer());
	         	    // Completar los campos del IVA
	         	    itemAmount = v.getgValorRestaItem().getdTotOpeItem().doubleValue();
	         	    CamposIvaItem t = utl.calcTaxValues(taxRate, itemAmount);
	        	        o.setgCamIVA(t);
	         	    //
   	         	    l.add(o);
	        	        counter ++;
	            }
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
	    }
	}

	public static ArrayList<PosInvoiceItem> listaPlana ( long invoiceId, long controlId, long cashId, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		short uomCode = 0;
		String uomName;
		double netQuantity = 0.0;
		double netDiscAmount = 0.0;
		//
		try {
			ArrayList<PosInvoiceItem> l1 = new ArrayList<PosInvoiceItem>();
			StringBuffer buffer = new StringBuffer();

			/*
			buffer.append("select 'P-' || p.INTERNAL_CODE DCODINT, p.DESCRIPTION DDESPROSER, p.QTY_HANDLING,");
			buffer.append(" x.QUANTITY, x.UNIT_PRICE, x.DISCOUNT_AMOUNT, x.DISCOUNT_PCT,");
			buffer.append(" x.UNIT_DISC_AMOUNT DDESCITEM, x.TAX_TYPE_ID, x.TAX_RATE, x.IDENTIFIER,");
			buffer.append(" x.PRODUCT_ID");
			buffer.append(" from INV_MATERIALS p,");
			//buffer.append(" POS_TRANS_ITEMS x");
			buffer.append(" POS_TRANS_ITEMS_EB x");

			buffer.append(" where p.IDENTIFIER = x.PRODUCT_ID");
			buffer.append(" and x.ITEM_TYPE = 'PRODUCTO'");
			buffer.append(" and x.CASH_REGISTER_ID = ?");
			buffer.append(" and x.CASH_CONTROL_ID = ?");
			buffer.append(" and x.TRANSACTION_ID = ?");
            */
			
			buffer.append("select 'P-' || p.INTERNAL_CODE DCODINT, p.DESCRIPTION DDESPROSER, p.QTY_HANDLING,");
			buffer.append(" sum(x.QUANTITY) QUANTITY, max(x.UNIT_PRICE) UNIT_PRICE, sum(x.DISCOUNT_AMOUNT) DISCOUNT_AMOUNT, x.DISCOUNT_PCT,");
			buffer.append(" sum(x.UNIT_DISC_AMOUNT) DDESCITEM, x.TAX_TYPE_ID, x.TAX_RATE,");
			buffer.append(" x.PRODUCT_ID");
			buffer.append(" from INV_MATERIALS p,");
			//buffer.append(" POS_TRANS_ITEMS x");
			buffer.append(" POS_TRANS_ITEMS_EB x");

			buffer.append(" where p.IDENTIFIER = x.PRODUCT_ID");
			buffer.append(" and x.ITEM_TYPE = 'PRODUCTO'");
			buffer.append(" and x.CASH_REGISTER_ID = ?");
			buffer.append(" and x.CASH_CONTROL_ID = ?");
			buffer.append(" and x.TRANSACTION_ID = ?");

			buffer.append(" group by 'P-' || p.INTERNAL_CODE, p.DESCRIPTION, p.QTY_HANDLING,");
			buffer.append(" x.DISCOUNT_PCT, x.TAX_TYPE_ID, x.TAX_RATE, x.PRODUCT_ID");
			buffer.append(" having sum(x.QUANTITY) > 0");
			
			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, cashId);
			ps.setLong(2, controlId);
			ps.setLong(3, invoiceId);
			rs = ps.executeQuery();

			// System.out.println("buscando items de: " + cashId + " - " + controlId + " - "
			// + invoiceId);
			while (rs.next()) {
				dataFound = true;
				PosInvoiceItem o = new PosInvoiceItem();
				o.setDiscountAmt(rs.getDouble("DISCOUNT_AMOUNT"));
				o.setDiscountPct(rs.getDouble("DISCOUNT_PCT"));
				o.setInternalCode(rs.getString("DCODINT"));
				o.setItemDescription(rs.getString("DDESPROSER"));
				//o.setItemId(rs.getLong("IDENTIFIER"));
				o.setProductId(rs.getLong("PRODUCT_ID"));
				o.setQtyHandling(rs.getString("QTY_HANDLING"));
				o.setQuantity(rs.getDouble("QUANTITY"));
				o.setTaxRate(rs.getDouble("TAX_RATE"));
				o.setTaxTypeId(rs.getLong("TAX_TYPE_ID"));
				o.setUnitDiscount(0.0);
				if (rs.getDouble("DISCOUNT_AMOUNT") > 0.0) {
				    o.setUnitDiscount(rs.getDouble("DISCOUNT_AMOUNT") / rs.getDouble("QUANTITY"));
				}
				o.setUnitPrice(rs.getDouble("UNIT_PRICE"));
				uomCode = 77;
				uomName = "Unidad";
				if (rs.getString("QTY_HANDLING").equalsIgnoreCase("PESABLES")) {
					uomCode = 83;
					uomName = "Kilogramos";
				}
				o.setUomCode(uomCode);
				o.setUomName(uomName);	
				//
				l1.add(o);
			}
			if (dataFound == true) {
				return l1;
			} else {
				return null;
			}
			/*
			if (dataFound == true) {
				// procesar el arreglo para obtener la lista compacta sin las cantidades negativas
				ArrayList<PosInvoiceItem> l2 = new ArrayList<PosInvoiceItem>();
				Iterator<PosInvoiceItem> itr1 = l1.iterator();
				while (itr1.hasNext()) {
					PosInvoiceItem x1 = (PosInvoiceItem) itr1.next();
					if (x1.getQuantity() > 0.0) {
						netQuantity = x1.getQuantity();
						netDiscAmount = x1.getDiscountAmt();
						Iterator<PosInvoiceItem> itr2 = l1.iterator();
						while (itr2.hasNext()) {
							PosInvoiceItem x2 = (PosInvoiceItem) itr2.next();
						    if (x2.getProductId() == x1.getProductId()) {
							    if (x2.getQuantity() < 0.0) {
							    	    netQuantity = netQuantity - Math.abs(x2.getQuantity());
							    	    netDiscAmount = netDiscAmount - Math.abs(x2.getDiscountAmt());
							    }
							}
						}
						if (netQuantity > 0.0) {
							x1.setQuantity(netQuantity);
							x1.setDiscountAmt(netDiscAmount);
							l2.add(x1);
							//System.out.println("item agregado: " + x1.getInternalCode() + " - " + x1.getQuantity() + " - " + 
							//   x1.getTaxRate() + " - " + x1.getDiscountAmt());
						}
					}
				}
				//
				return l2;
			} else {
				return null;
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}
	
}
