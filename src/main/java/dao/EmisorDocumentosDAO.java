package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposActivEconomica;
import pojo.Country;
import pojo.EconomicActivity;
import pojo.GeographicLocation;
import pojo.OrganizationData;

public class EmisorDocumentosDAO {
	
	public static OrganizationData getOrgData ( long orgId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    //
	    OrganizationData o;
	    ArrayList<CamposActivEconomica> activList;
	    //
	    final short PERSONA_FISICA = 1;
	    final short PERSONA_JURIDICA = 2;
	    //
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            return null;
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select o.NAME,");
	        buffer.append(" substr(o.TAX_NUMBER, 1, (instr(o.TAX_NUMBER, '-') - 1)) TAX_NUMBER,");
	        buffer.append(" o.ALTERNATIVE_NAME,");
	        buffer.append(" substr(o.TAX_NUMBER, -1) CHECK_DIGIT,");
	        buffer.append(" o.E_MAIL,");
	        buffer.append(" o.EB_REGIME_ID CTIPREG");
	        buffer.append(" from FND_ORG_ATTRIBUTES a,");
	        buffer.append(" FND_ORGANIZATIONS o");
	        buffer.append(" where a.ORG_ID = o.IDENTIFIER");
	        buffer.append(" and o.IDENTIFIER = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, orgId);
	        rs = ps.executeQuery();

	        o = new OrganizationData();
	        if (rs.next()) {
	        	dataFound = true;
	        	o.setAlternativeName(rs.getString("ALTERNATIVE_NAME"));
	        	o.setCheckDigit(Short.valueOf(rs.getString("CHECK_DIGIT")));
	        	o.seteMail(rs.getString("E_MAIL"));
	        	o.setName(rs.getString("NAME"));
	        	if (rs.getString("CTIPREG") != null) {
	        	    o.setRegimeType(Short.valueOf(rs.getString("CTIPREG")));
	        	} else {
	        		o.setRegimeType(Short.valueOf("0"));
	        	}
	        	o.setTaxNumber(rs.getString("TAX_NUMBER"));
	        	o.setTaxPayerType(PERSONA_JURIDICA);
	        	activList = EmisorDocumentosDAO.getActivitiesList(orgId, conn);
	        	o.setActivList(activList);
	        }
	        if (dataFound == true) {
	            return o;
	        } else {
	        	return null;
	        }

	    } catch (Exception e) {
	    	e.printStackTrace();
	        return null	;
	    } finally {
	    	Util.closeResultSet(rs);
	    	Util.closeStatement(ps);
	    	Util.closeJDBCConnection(conn);
	    }
		
	}
	
	public static ArrayList<CamposActivEconomica> getActivitiesList ( long orgId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    //
	    ArrayList<CamposActivEconomica> l = new ArrayList<CamposActivEconomica>();
	    CamposActivEconomica o;
	    //
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            return null;
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select a.ATTR_TYPE, a.ATTR_CODE, upper(a.ATTR_VALUE) ATTR_VALUE");
	        buffer.append(" from FND_ORG_ATTRIBUTES a");
	        buffer.append(" where a.ORG_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, orgId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	           	dataFound = true;
	        	    o = new CamposActivEconomica();
	        	    o.setcActEco(rs.getString("ATTR_CODE"));
	        	    o.setdDesActEco(rs.getString("ATTR_VALUE"));
	        	    //
	        	    l.add(o);	        	
	        }
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
	
	public static ArrayList<EconomicActivity> XgetActivitiesList ( long orgId, Connection conn ) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    //
	    ArrayList<EconomicActivity> l = new ArrayList<EconomicActivity>();
	    EconomicActivity o;
	    //
	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            return null;
	        }

	        StringBuffer buffer = new StringBuffer();

	        buffer.append("select a.ATTR_TYPE, a.ATTR_CODE, a.ATTR_VALUE");
	        buffer.append(" from FND_ORG_ATTRIBUTES a");
	        buffer.append(" where a.ORG_ID = ?");

	        ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, orgId);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	dataFound = true;
	        	o = new EconomicActivity();
	        	o.setActivityCode( Short.valueOf(rs.getString("ATTR-CODE")));
	        	o.setActivityName(rs.getString("ATTR_VALUE"));
	        	o.setActivityType(rs.getString("ATTR_TYPE"));
	        	//
	        	l.add(o);
	        	
	        }
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

	public static Country getCountry ( short countryId, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		Country o = new Country();
		//
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.CODE CCODE, x.NAME CNAME");
			buffer.append(" from FND_COUNTRIES x");
			buffer.append(" where x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setShort(1, countryId);
			rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
				o.setCountryCode(rs.getString("CCODE"));
				o.setCountryId(countryId);
				o.setCountryName(rs.getString("CNAME"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null	;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}
		
	public static GeographicLocation getLocation ( short locationId, String locationType, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		GeographicLocation geoLoc = new GeographicLocation();
		//
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.CODE LOCCODE, x.NAME LOCNAME");
			if (locationType.equalsIgnoreCase("DEPARTAMENTO")) {
			    buffer.append(" from FND_STATES x");
			}
			if (locationType.equalsIgnoreCase("DISTRITO")) {
			    buffer.append(" from FND_COUNTIES x");
			}
			if (locationType.equalsIgnoreCase("CIUDAD")) {
			    buffer.append(" from FND_CITIES x");
			}
			buffer.append(" where x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setShort(1, locationId);
			rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
				geoLoc.setLocationCode(rs.getString("LOCCODE"));
				geoLoc.setLocationName(rs.getString("LOCNAME"));
			}
			if (dataFound == true) {
				return geoLoc;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null	;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}
	}

	public static GeographicLocation getLocation ( short locationId, String locationType ) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		GeographicLocation geoLoc = new GeographicLocation();
		//
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            return null;
	        }
			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.CODE LOCCODE, x.NAME LOCNAME");
			if (locationType.equalsIgnoreCase("PAIS")) {
			    buffer.append(" from FND_COUNTRIES x");
			}
			if (locationType.equalsIgnoreCase("DEPARTAMENTO")) {
			    buffer.append(" from FND_STATES x");
			}
			if (locationType.equalsIgnoreCase("DISTRITO")) {
			    buffer.append(" from FND_COUNTIES x");
			}
			if (locationType.equalsIgnoreCase("CIUDAD")) {
			    buffer.append(" from FND_CITIES x");
			}
			buffer.append(" where x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setShort(1, locationId);
			rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
				geoLoc.setLocationCode(rs.getString("LOCCODE"));
				geoLoc.setLocationName(rs.getString("LOCNAME"));
			}
			if (dataFound == true) {
				return geoLoc;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null	;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}

}
