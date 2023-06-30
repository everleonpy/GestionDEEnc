package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposOperacionContado;
import pojo.CamposPagoCheque;
import pojo.CamposPagoTarjeta;
import pojo.RefValue;
import util.UtilPOS;

public class FormasPagoFacturasPosV1DAO {

	public static ArrayList<CamposOperacionContado> listaFormasPago ( long invoiceId, long controlId, long cashId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    String s;
	    //
	    final short POS = 1;
	    final short PAGO_ELECTRONICO = 2;
	    final short OTRO = 9;
	    //
	    try {
            ArrayList<CamposOperacionContado> gPaConEIni = new ArrayList<CamposOperacionContado>();
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select y.EB_CODE CMONETIPAG, y.DESCRIPTION DDMONETIPAG, t.PAYMENT_TYPE PMT_CLASS, x.AMOUNT DMONTIPAG,");
	        buffer.append(" r.CODE ITIPAGO, r.DESCRIPTION DDESTIPAG, c.EXCHANGE_RATE DTICAMTIPAG, x.DOCUM_NUMBER DNUMCHEQ,");
	        buffer.append(" x.PAYMENT_NAME DBCOEMI, x.CARD_AUTHOR_CODE DCODAUOPE, x.PAYMENT_NAME DNOMTIT, x.CARD_NUMBER DNUMTARJ,");
	        buffer.append(" x.CARD_MARK_ID, x.CARD_PROCESSOR_ID");
	        buffer.append(" from FND_CURRENCIES y,");
	        buffer.append(" POS_PAYMENT_TERMS t,");
	        buffer.append(" FND_ELEC_INVOICING_REFS r,");
	        //buffer.append(" POS_COLLECTIONS c,");
	        //buffer.append(" POS_COLLECTION_ITEMS x");
	        buffer.append(" POS_COLLECTIONS_EB c,");
	        buffer.append(" POS_COLLECTION_ITEMS_EB x");
	        
	        buffer.append(" where y.IDENTIFIER = x.CURRENCY_ID");

	        buffer.append(" and t.IDENTIFIER = x.PMT_TERM_ID");
	        buffer.append(" and r.IDENTIFIER = t.EB_PMT_TERM_ID");
	        buffer.append(" and c.CASH_REGISTER_ID = x.CASH_REGISTER_ID");
	        buffer.append(" and c.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
	        buffer.append(" and c.IDENTIFIER = x.COLLECTION_ID");

	        buffer.append(" and c.CASH_REGISTER_ID = ?");
	        buffer.append(" and c.CASH_CONTROL_ID = ?");
	        buffer.append(" and c.INVOICE_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, cashId);
	        ps.setLong(2, controlId);
	        ps.setLong(3, invoiceId);
	        rs = ps.executeQuery();

	        //System.out.println("buscando pagos de: " + cashId + " - " + controlId + " - " + invoiceId);
	        while (rs.next()) {
	        	    dataFound = true;
	        	    CamposOperacionContado o = new CamposOperacionContado();
	        	    o.setcMoneTiPag(rs.getString("CMONETIPAG"));
	        	    o.setdDMoneTiPag(rs.getString("DDMONETIPAG"));
        		    o.setiTiPago(Short.valueOf(rs.getString("ITIPAGO")));
        		    o.setdDesTiPag(rs.getString("DDESTIPAG"));
                o.setdMonTiPag(UtilPOS.appRound(rs.getDouble("DMONTIPAG"), 4));
                if (rs.getString("CMONETIPAG").equalsIgnoreCase("PYG") == false) {
                	    o.setdTiCamTiPag(rs.getDouble("DTICAMTIPAG"));
                }
        	        System.out.println(o.getcMoneTiPag() + ": " + o.getdDesTiPag() + " - " + o.getdMonTiPag());
	         	/**
	        	     +--------------------------------------------------------------+
	           	 | preparar la clase raiz                                       |
	         	 +--------------------------------------------------------------+
	        	    */
                if (rs.getString("PMT_CLASS").equalsIgnoreCase("CHEQUE") == true) {
                    CamposPagoCheque gPagCheq = new CamposPagoCheque();
                    gPagCheq.setdBcoEmi(rs.getString("DBCOEMI"));
                    gPagCheq.setdNumCheq(rs.getString("DNUMCHEQ"));
                    o.setgPagCheq(gPagCheq);
                }
                //
                if (rs.getString("PMT_CLASS").equalsIgnoreCase("TARJETA-CREDITO") == true |
                    rs.getString("PMT_CLASS").equalsIgnoreCase("TARJETA-DEBITO") == true) {
                    CamposPagoTarjeta gPagTarCD = new CamposPagoTarjeta();
                    // datos generales del pago con tarjeta
                    gPagTarCD.setiForProPa(POS);
                    //gPagTarCD.setdCodAuOpe(rs.getInt("DCODAUOPE"));
                    gPagTarCD.setdNomTit(rs.getString("DNOMTIT"));
                    // tipo de tarjeta
                    RefValue rv = FormasPagoFacturasPosV1DAO.datosTipoTarjeta(rs.getLong("CARD_MARK_ID"), conn);
                    if (rv != null) {
                        gPagTarCD.setiDenTarj(Short.valueOf(rv.getCode()));
                        gPagTarCD.setdDesDenTarj(rv.getDescription());
                    } else {
                        gPagTarCD.setiDenTarj(Short.valueOf("99"));
                        gPagTarCD.setdDesDenTarj("No definido");                    	
                    }
                    if (rs.getString("DNUMTARJ") != null) {
                    	    try {
                    	        if (rs.getString("DNUMTARJ").length() >= 4) {
                    		        s = rs.getString("DNUMTARJ");
                    		        s = s.substring(s.length() - 4);
                                gPagTarCD.setdNumTarj(Short.valueOf(s));
                    	        } else {
                                gPagTarCD.setdNumTarj(Short.valueOf(rs.getString("DNUMTARJ")));                    		
                    	        }
                    	    } catch (Exception e) {
                    	    	    e.printStackTrace();
                    	    	    System.out.println("Continua el procesamiento...");
                    	    }
                    }
                    // procesadora de tarjetas
                    if (rs.getLong("CARD_PROCESSOR_ID") != 0) {
                        rv = FormasPagoFacturasPosV1DAO.datosProcesadora(rs.getLong("CARD_PROCESSOR_ID"), conn);
                        if (rv != null) {
                            gPagTarCD.setdRSProTar(rv.getDescription());
                        	    if (rv.getCode() != null) {
                    	            s = rv.getCode();
                    	            System.out.println("mieeeerdaaaa: " + s);
                                gPagTarCD.setdRUCProTar(s.substring(0, (s.length() - 2)));
                                gPagTarCD.setdDVProTar(Short.valueOf(s.substring(s.length() - 1)));
                        	    }
                        }
                    }
                    o.setgPagTarCD(gPagTarCD);
                }
                //
                gPaConEIni.add(o);
	        }
	        if (dataFound == true) {
	        	    //System.out.println("arreglo de formas de pago contado: " + gPaConEIni.size());
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

	public static RefValue datosTipoTarjeta ( long cardTypeId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
    	RefValue o = new RefValue();
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select r.CODE, r.DESCRIPTION");
	        buffer.append(" from FND_ELEC_INVOICING_REFS r,");
	        buffer.append(" RCV_CREDIT_CARD_MARKS m");  
	        buffer.append(" where r.IDENTIFIER = m.EB_CARD_TYPE_ID");
	        buffer.append(" and m.IDENTIFIER = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, cardTypeId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	dataFound = true;
	        	o.setCode(rs.getString("CODE"));
	        	o.setDescription(rs.getString("DESCRIPTION"));
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
	    }
		
	}

	public static RefValue datosProcesadora ( long processorId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
      	RefValue o = new RefValue();
	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select x.TAX_NUMBER, x.DESCRIPTION");
	        buffer.append(" from RCV_CARD_PROCESSORS x");  
	        buffer.append(" where x.IDENTIFIER = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, processorId);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	        	dataFound = true;
	        	o.setCode(rs.getString("TAX_NUMBER"));
	        	o.setDescription(rs.getString("DESCRIPTION"));
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
	    }
		
	}

}
