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

public class FormasPagoFacturasDAO {

	public static ArrayList<CamposOperacionContado> listaFormasPago ( long collectionId, Connection conn ) {
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

	        buffer.append("select y.CODE CMONETIPAG, y.DESCRIPTION DDMONETIPAG, t.PMT_CLASS PMT_CLASS, x.AMOUNT DMONTIPAG,");
	        buffer.append(" r.CODE ITIPAGO, r.DESCRIPTION DDESTIPAG, c.EXCHANGE_RATE DTICAMTIPAG, x.DOCUM_NUMBER DNUMCHEQ,");
	        buffer.append(" x.BANK_NAME DBCOEMI, x.CARD_AUTHOR_CODE DCODAUOPE, x.PAYMENT_NAME DNOMTIT, x.CARD_NUMBER DNUMTARJ,");
	        buffer.append(" x.CARD_MARK_ID, x.CARD_PROCESSOR_ID");
	        buffer.append(" from FND_CURRENCIES y,");
	        buffer.append(" RCV_PAYMENT_TERMS t,");
	        buffer.append(" FND_ELEC_INVOICING_REFS r,");
	        buffer.append(" RCV_COLLECTIONS c,");
	        buffer.append(" RCV_RECEIVED_VALUES x");
	        
	        buffer.append(" where y.IDENTIFIER = x.CURRENCY_ID");

	        buffer.append(" and t.IDENTIFIER = x.PMT_TERM_ID");
	        buffer.append(" and r.IDENTIFIER = t.EB_PMT_TERM_ID");
	        buffer.append(" and c.IDENTIFIER = x.COLLECTION_ID");

	        buffer.append(" and x.COLLECTION_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, collectionId);
	        rs = ps.executeQuery();

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
                    gPagTarCD.setdCodAuOpe(rs.getInt("DCODAUOPE"));
                    gPagTarCD.setdNomTit(rs.getString("DNOMTIT"));
                    // tipo de tarjeta
                    RefValue rv = FormasPagoFacturasDAO.datosTipoTarjeta(rs.getLong("CARD_MARK_ID"), conn);
                    gPagTarCD.setiDenTarj(Short.valueOf(rv.getCode()));
                    gPagTarCD.setdDesDenTarj(rv.getDescription());
                    if (rs.getString("DNUMTARJ") != null) {
                    	if (rs.getString("DNUMTARJ").length() >= 4) {
                    		s = rs.getString("DNUMTARJ");
                    		s = s.substring(s.length() - 4);
                            gPagTarCD.setdNumTarj(Short.valueOf(s));
                    	} else {
                            gPagTarCD.setdNumTarj(Short.valueOf(rs.getString("DNUMTARJ")));                    		
                    	}
                    }
                    // procesadora de tarjetas
                    if (rs.getLong("CARD_MARK_ID") != 0) {
                        rv = FormasPagoFacturasDAO.datosProcesadora(rs.getLong("CARD_MARK_ID"), conn);
                        gPagTarCD.setdRSProTar(rv.getDescription());
                    	s = rs.getString(rv.getCode());
                        gPagTarCD.setdRUCProTar(s.substring(0, (s.length() - 2)));
                        gPagTarCD.setdDVProTar(Short.valueOf(s.substring(s.length())));
                    }
                    o.setgPagTarCD(gPagTarCD);
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

	        while (rs.next()) {
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
