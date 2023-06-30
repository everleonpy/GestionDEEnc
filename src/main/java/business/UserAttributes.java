package business;

import dao.UserCredentialsDAO;
import dao.Util;
import pojo.BusinessUnit;
import pojo.Organization;
import pojo.Site;
import pojo.FndUser;

import java.sql.Connection;

import business.LocalHost;

/*
 * Esta clase tiene como objetivo agrupar en un contenedor los juegos de
 * entidades que conforman el contexto de un usuario
 */

public class UserAttributes {

	public static String userName;
	public static String workStation;
	public static FndUser user;
	public static Organization userOrg;
	public static BusinessUnit userUnit;
	public static Site userSite;
	
	/*
	public static void getUserAttributes () throws Exception {
		LocalHost lh = new LocalHost();
		Connection conn = null;
		workStation = lh.getName().toUpperCase();
		//######################################
		// Esta linea se agrega a efectos de testear con la base de datos de una caja
		// El otro lugar a modificar es SessionContext.getTicketPrinter
		//workStation = "CAJA44";
		//#######################################
		try {
			conn = Util.getConnection();
		    user = UserCredentialsDAO.getUser(userName, conn);
		    //System.out.println("Usr/unidad/sitio: " + user.getFULL_NAME() + " - " + user.getUNIT_ID() + " - " + user.getSITE_ID());
		    userOrg = UserCredentialsDAO.getOrganization(user.getORG_ID(), conn);
		    userUnit = UserCredentialsDAO.getBusinessUnit(user.getUNIT_ID(), conn);
		    userSite = UserCredentialsDAO.getSite(user.getSITE_ID(), conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.closeJDBCConnection(conn);
		}
	}
    */
	
	public static void getUserAttributes () throws Exception {
		LocalHost lh = new LocalHost();
		Connection conn = null;
		workStation = lh.getName().toUpperCase();
		//######################################
		// Esta linea se agrega a efectos de testear con la base de datos de una caja
		// El otro lugar a modificar es SessionContext.getTicketPrinter
		//workStation = "CAJA44";
		//#######################################
		try {
			conn = Util.getConnection();
		    user = new FndUser();
		    user.setACTIVE_FLAG("S");
		    user.setNAME("APPMGR");
		    user.setORG_ID(1);
		    user.setPASSWORD("APPMGR");
		    user.setSITE_ID(1);
		    user.setUNIT_ID(1);
		    user.setSYSTEM_ITEM("S");
		    //System.out.println("Usr/unidad/sitio: " + user.getFULL_NAME() + " - " + user.getUNIT_ID() + " - " + user.getSITE_ID());
		    userOrg = new Organization();
		    userOrg.setIDENTIFIER(1);
		    userOrg.setNAME("Jessy Lens");
		    userUnit = new BusinessUnit();
		    userUnit.setIDENTIFIER(1);
		    userUnit.setNAME("CENTRAL");
		    userSite = new Site();
		    userSite.setIDENTIFIER(1);
		    userSite.setNAME("CENTRAL");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.closeJDBCConnection(conn);
		}
	}

}
