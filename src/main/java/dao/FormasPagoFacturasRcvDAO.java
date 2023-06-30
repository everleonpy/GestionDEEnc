package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import pojo.CamposOperacionContado;
import pojo.CamposPagoCheque;
import pojo.CamposPagoTarjeta;
import pojo.PaymentTermItem;
import pojo.RefValue;
import util.UtilPOS;

public class FormasPagoFacturasRcvDAO {

	public static ArrayList<CamposOperacionContado> listaFormasPago ( long invoiceId, Connection conn ) {
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

	        buffer.append("select CMONETIPAG, DDMONETIPAG, PMTCLASS, DMONTIPAG,");
	        buffer.append(" ITIPAGO, DDESTIPAG, DTICAMTIPAG, DNUMCHEQ,");
	        buffer.append(" DBCOEMI, DCODAUOPE, DNOMTIT, DNUMTARJ,");
	        buffer.append(" CARDMARKID, CARDMARKNAME, CARDPROCESSORID");
	        buffer.append(" from RCV_EB_COLLECT_ITEMS");
	        buffer.append(" where TRANSACTION_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, invoiceId);
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
                if (rs.getString("PMTCLASS").equalsIgnoreCase("CHEQUE") == true) {
                    CamposPagoCheque gPagCheq = new CamposPagoCheque();
                    gPagCheq.setdBcoEmi(rs.getString("DBCOEMI"));
                    gPagCheq.setdNumCheq(rs.getString("DNUMCHEQ"));
                    o.setgPagCheq(gPagCheq);
                }
                //
                if (rs.getString("PMTCLASS").equalsIgnoreCase("TARJETA-CREDITO") == true |
                    rs.getString("PMTCLASS").equalsIgnoreCase("TARJETA-DEBITO") == true) {
                    CamposPagoTarjeta gPagTarCD = new CamposPagoTarjeta();
                    // datos generales del pago con tarjeta
                    gPagTarCD.setiForProPa(POS);
                    //gPagTarCD.setdCodAuOpe(rs.getInt("DCODAUOPE"));
                    gPagTarCD.setdNomTit(rs.getString("DNOMTIT"));
                    // tipo de tarjeta
                    /*
                    RefValue rv = FormasPagoFacturasPosDAO.datosTipoTarjeta(rs.getLong("CARDMARKID"), conn);
                    if (rv != null) {
                        gPagTarCD.setiDenTarj(Short.valueOf(rv.getCode()));
                        gPagTarCD.setdDesDenTarj(rv.getDescription());
                    } else {
                        gPagTarCD.setiDenTarj(Short.valueOf("99"));
                        gPagTarCD.setdDesDenTarj("No definido");                    	
                    }
                    */
                    gPagTarCD.setiDenTarj((short)rs.getLong("CARDMARKID"));
                    gPagTarCD.setdDesDenTarj(rs.getString("CARDMARKNAME"));
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
                    /*
                    if (rs.getLong("CARDPROCESSORID") != 0) {
                        rv = FormasPagoFacturasPosDAO.datosProcesadora(rs.getLong("CARDPROCESSORID"), conn);
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
                    */
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

	public static ArrayList<CamposOperacionContado> listaFormasPago ( ArrayList<PaymentTermItem> items) {
		boolean dataFound = false;
		//
		final short POS = 1;
		final short PAGO_ELECTRONICO = 2;
		final short OTRO = 9;
		//
		try {
			ArrayList<CamposOperacionContado> gPaConEIni = new ArrayList<CamposOperacionContado>();
			Iterator itr1 = items.iterator();
			while (itr1.hasNext()) {
				PaymentTermItem x = (PaymentTermItem) itr1.next();
				dataFound = true;
				CamposOperacionContado o = new CamposOperacionContado();
				o.setcMoneTiPag("PYG");
				o.setdDMoneTiPag("GUARANIES");
				o.setiTiPago(x.getTermId());
				o.setdMonTiPag(UtilPOS.appRound(x.getAmount(), 4));
				if (o.getcMoneTiPag().equalsIgnoreCase("PYG") == false) {
					o.setdTiCamTiPag(0.0);
				}
				if (x.getCheckNumber() != null) {
					CamposPagoCheque gPagCheq = new CamposPagoCheque();
					gPagCheq.setdBcoEmi(x.getCheckBank());
					gPagCheq.setdNumCheq(x.getCheckNumber());
					o.setgPagCheq(gPagCheq);
				}
				//
				if (x.getCardMark() != 0) {
					CamposPagoTarjeta gPagTarCD = new CamposPagoTarjeta();
					gPagTarCD.setiForProPa(POS);
					gPagTarCD.setiDenTarj(x.getCardMark());
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
		}
	}

}
