package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposCuotas;

public class CuotasFacturasDAO {
	
	public static ArrayList<CamposCuotas> listaCuotas ( long invoiceId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	    short counter = 0;
	    //
	    try {
        	ArrayList<CamposCuotas> gCuotas = new ArrayList<CamposCuotas>();
	        StringBuffer buffer = new StringBuffer();
	        
	        buffer.append("select y.EB_CODE CMONECUO, y.DESCRIPTION DDMONECUO, x.AMOUNT DMONCUOTA, to_char(x.DUE_DATE, 'dd/mm/yyyy') DVENCUO");
	        buffer.append(" from FND_CURRENCIES y,");
	        buffer.append(" RCV_CUSTOMERS_TRX i,");
	        buffer.append(" RCV_PAYMENT_SCHEDULE x");
	        
	        buffer.append(" where y.IDENTIFIER = i.CURRENCY_ID");
	        buffer.append(" and i.IDENTIFIER = x.CUSTOMER_TRX_ID");
	        buffer.append(" and x.CUSTOMER_TRX_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, invoiceId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	    dataFound = true;
	        	    CamposCuotas o = new CamposCuotas();
	        	    o.setcMoneCuo(rs.getString("CMONECUO"));
	        	    o.setdDMoneCuo(rs.getString("DDMONECUO"));
	        	    o.setdMonCuota(rs.getDouble("DMONCUOTA"));	
	            d = sdf.parse(rs.getString("DVENCUO"));
	            o.setdVencCuo(d);
                //
	        	    gCuotas.add(o);
	        	    counter ++;
	        }
	        //
	        if (dataFound == true) {
	            return gCuotas;
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
