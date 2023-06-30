package business;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import dao.ApplicationUtilsDAO;
import dao.InvOptionsDAO;
import pojo.InvOptions;
import business.ApplicationMessage;

public class SessionContext {
	public static java.util.Date currentDate;
	public static long currentSessionId;
	public static long orgId;
	public static long unitId;
	public static long SiteId;
	public static String orgName;
	public static String siteName;
	public static String scalePort;

	//
	public static InvOptions invOptions;

	// log de aplicacion
	public static FileWriter logFile = null;
	public static PrintWriter logWriter = null;
	// impresion
	// aspectos generales
	public static String appImagesPath;
	// log de eventos de transaccion
	public static long eventSeqNumber;
	// testing
	//private static boolean testMode = true;
	private static boolean testMode = false;

	public static ApplicationMessage getContextValues () {
		ApplicationMessage aMsg;
		try {
			// Obtener los atributos de usuario
	        UserAttributes.getUserAttributes();
	        /*
    	        invOptions = InvOptionsDAO.getRow( new java.util.Date(), UserAttributes.userUnit.getIDENTIFIER());
    	        if (invOptions == null) {
        			aMsg = new ApplicationMessage();
    	    	        aMsg.setMessage("PARAM-NOT-FOUND", "No se ha podido detectar el juego de parametros de la aplicacion", ApplicationMessage.ERROR);
    	    	        return aMsg;
    	        }
    	        */
    			currentDate = new java.util.Date ();
    			appImagesPath = AppConfig.iconsFolder;
    			//
    			return null;
    	    } catch (Exception e) {
    		    e.printStackTrace();
    			aMsg = new ApplicationMessage();
	    	    aMsg.setMessage("PARAM-NOT-FOUND", "No se ha podido detectar el juego de parametros de la aplicacion", ApplicationMessage.ERROR);
	    	    return aMsg;
		}
	}

	public static void openLogFile () {
		java.util.Date currentDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		try {
			String fileName = AppConfig.logFolder + sdf.format(currentDate) + ".log"; 				
			logFile = new FileWriter(fileName, true);
			System.out.println("abriendo archivo log " + fileName);
		} catch (Exception e2) {
			e2.printStackTrace();
		}  
	}  

	public static void closeLogFile () {
		try {
			if (logFile != null) {
				logFile.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}  
	}  
    
}
