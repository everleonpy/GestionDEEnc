package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.Util;
import pojo.Currency;

public class FndCurrenciesDAO {

	public static Currency getRow ( long currencyId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select IDENTIFIER, CODE, NAME, DECIMAL_PLACES,");
	        buffer.append(" COUNTRY, SYSTEM_ITEM, SYMBOL, FORMAT_MASK,");
	        buffer.append(" DISPLAY_LABEL");
	        buffer.append(" from FND_CURRENCIES");
	        buffer.append(" where IDENTIFIER = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, currencyId);
	        rs = ps.executeQuery();

	        Currency c = new Currency();

	        if (rs.next()) {
	            c.setIDENTIFIER(rs.getLong("IDENTIFIER"));
	            c.setCODE(rs.getString("CODE"));
	            c.setNAME(rs.getString("NAME"));
	            c.setDECIMAL_PLACES(rs.getInt("DECIMAL_PLACES"));
	            c.setCOUNTRY(rs.getString("COUNTRY"));
	            c.setSYSTEM_ITEM(rs.getString("SYSTEM_ITEM"));
	            c.setSYMBOL(rs.getString("SYMBOL"));
	            c.setFORMAT_MASK(rs.getString("FORMAT_MASK"));
	            if (rs.getString("DISPLAY_LABEL") != null) {
	                c.setDISPLAY_LABEL(rs.getString("DISPLAY_LABEL"));
	            } else {
	            	c.setDISPLAY_LABEL(rs.getString("CODE"));
	            }
	        }
	        return c;

	    } catch (Exception e) {
	    	    e.printStackTrace();
	        return null;
	    } finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
	    }
	}
	
}
