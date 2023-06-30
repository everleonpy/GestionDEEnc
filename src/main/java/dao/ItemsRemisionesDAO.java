package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposItemsOperacion;

public class ItemsRemisionesDAO {
	
	public static ArrayList<CamposItemsOperacion> listaItems ( long shipmentId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    short counter = 0;
	    //
	    try {
        	ArrayList<CamposItemsOperacion> l = new ArrayList<CamposItemsOperacion>();
	        StringBuffer buffer = new StringBuffer();
	        
	        buffer.append("select 'P-' || p.INTERNAL_CODE DCODINT, p.DESCRIPTION DDESPROSER, u1.CODE CUNIMED, u1.DESCRIPTION DDESUNIMED,");        
	        buffer.append(" x.QUANTITY DCANTPROSER, x.UNIT_PRICE DPUNIPROSER, x.TAX_TYPE_ID, x.TAX_RATE");
	        buffer.append(" from FND_ELEC_INVOICING_UOMS u1,");
	        buffer.append(" INV_UNIT_OF_MEASURES u2,");
	        buffer.append(" INV_MATERIALS p,");
	        buffer.append(" INV_INTRNL_SHIPMNT_ITEMS x");
	        
	        buffer.append(" where u1.IDENTIFIER = u2.EB_UOM_ID");
	        buffer.append(" and u2.IDENTIFIER = x.ITEM_UOM_ID");
	        buffer.append(" and p.IDENTIFIER = x.ITEM_ID");
	        buffer.append(" and x.SHIPMENT_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, shipmentId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	dataFound = true;
	        	CamposItemsOperacion o = new CamposItemsOperacion();
	        	o.setcPaisOrig(null);
	        	o.setcRelMerc(Short.valueOf("0"));
	        	o.setcUniMed(Short.valueOf(rs.getString("CUNIMED")));
	        	o.setdCanQuiMer(null);
	        	o.setdCantProSer(rs.getBigDecimal("DCANTPROSER"));
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
	        	o.setgValorItem(null);
	        	// Completar los campos del IVA
	        	o.setgCamIVA(null);
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
