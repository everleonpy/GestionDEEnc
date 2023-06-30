package business;

import java.util.Calendar;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.TimeZone;

public class AppConfig {
	public static String operativeMode;
	public static String logFileName; 
	public static String backupFileName;
	public static java.util.Calendar calendarDate;
	public static java.util.Calendar docLowestDate;
	public static java.util.Calendar docHighestDate;
	// parametros generales de la aplicacion 
	public static String serverIPAddress;
	public static int serverPort;
	public static String dbEngine; 
	public static String operatingSystem;
	public static String lookAndFeel;
	public static String vendorLogo;
	public static String productLogo;
	public static String productImagesFolder;
	public static String backupFolder;
	public static String logFolder;
	public static String iconsFolder;
	public static String graphRepsFolder;
	public static String xmlFolder; 
	public static String printerPort;
	public static String dateFormat;
	public static String dateTimeFormat;
	public static String timeZoneText;
	public static TimeZone timeZone;
	public static String language;
	public static String country;
	public static String pingCommand;
	public static String pingCommandParams;
	   
	public static final PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle("Application");
	public static final PropertyResourceBundle linuxOS = (PropertyResourceBundle)PropertyResourceBundle.getBundle("LinuxOS");
	public static final PropertyResourceBundle winOS = (PropertyResourceBundle)PropertyResourceBundle.getBundle("WindowsOS");
	public static final PropertyResourceBundle macOS = (PropertyResourceBundle)PropertyResourceBundle.getBundle("MacOs");
	// parametros para manejo de tamanhos de ventanas y componentes
	public static int sizePercent;

	public static void defineTImeZone ()
	{
        //System.getProperty("user.timezone")
   		System.setProperty("user.timezone", "America/Asuncion");
        System.out.println("TimeZone: " + System.getProperty("user.timezone"));   
	}	
	
	public static void loadConfig () {
        try {
			try {
				System.out.println("buscando config. 1...  ");
				operatingSystem = prb.getString("OPERATING_SYSTEM");
				System.out.println("buscando config. 2...  "+operatingSystem);
				dbEngine = prb.getString("DB_ENGINE");
				System.out.println("buscando config. 3..."+dbEngine);
				serverIPAddress = prb.getString("SERVER_IP_ADDRESS");
				System.out.println("buscando config. 4...");
				serverPort = Integer.valueOf(prb.getString("SERVER_PORT"));
				
				
				//
				if (operatingSystem.equalsIgnoreCase("Linux")) {
				    lookAndFeel = linuxOS.getString("LOOK_AND_FEEL");
				    logFileName = linuxOS.getString("LOG_FILE_NAME");
				    backupFileName = linuxOS.getString("BACKUP_FILE_NAME");
				    vendorLogo = linuxOS.getString("DEVELOPER_LOGO");
				    productLogo = linuxOS.getString("PRODUCT_LOGO");
				    productImagesFolder = linuxOS.getString("ITEMS_IMAGES_FOLDER");
				    backupFolder = linuxOS.getString("BACKUP_FOLDER");
				    logFolder = linuxOS.getString("LOG_FOLDER");
				    iconsFolder = linuxOS.getString("ICONS_FOLDER");
				    graphRepsFolder = linuxOS.getString("GR_FOLDER");
				    xmlFolder = linuxOS.getString("XML_FOLDER");
				    dateFormat = linuxOS.getString("DATE_FORMAT");
				    dateTimeFormat = linuxOS.getString("DATETIME_FORMAT");
				    timeZoneText = linuxOS.getString("TIMEZONE");
				    language = linuxOS.getString("LANGUAGE");
				    country = linuxOS.getString("COUNTRY");
				    pingCommand = linuxOS.getString("PING_COMMAND");
				    pingCommandParams = linuxOS.getString("PING_ARGS");
				}
				if (operatingSystem.equalsIgnoreCase("WinOS")) {
				    lookAndFeel = winOS.getString("LOOK_AND_FEEL");
				    logFileName = winOS.getString("LOG_FILE_NAME");
				    backupFileName = winOS.getString("BACKUP_FILE_NAME");
				    vendorLogo = winOS.getString("DEVELOPER_LOGO");
				    productLogo = winOS.getString("PRODUCT_LOGO");
				    productImagesFolder = winOS.getString("ITEMS_IMAGES_FOLDER");
				    backupFolder = winOS.getString("BACKUP_FOLDER");
				    logFolder = winOS.getString("LOG_FOLDER");
				    iconsFolder = winOS.getString("ICONS_FOLDER"); 
				    graphRepsFolder = linuxOS.getString("GR_FOLDER");
				    xmlFolder = linuxOS.getString("XML_FOLDER");
				    dateFormat = winOS.getString("DATE_FORMAT");
				    dateTimeFormat = winOS.getString("DATETIME_FORMAT");
				    timeZoneText = winOS.getString("TIMEZONE");
				    language = winOS.getString("LANGUAGE");
				    country = winOS.getString("COUNTRY");
				    pingCommand = winOS.getString("PING_COMMAND");
				    pingCommandParams = winOS.getString("PING_ARGS");
				}
				if (operatingSystem.equalsIgnoreCase("MacOS")) {
				    lookAndFeel = macOS.getString("LOOK_AND_FEEL");
				    logFileName = macOS.getString("LOG_FILE_NAME");
				    backupFileName = macOS.getString("BACKUP_FILE_NAME");
				    vendorLogo = macOS.getString("DEVELOPER_LOGO");
				    productLogo = macOS.getString("PRODUCT_LOGO");
				    productImagesFolder = macOS.getString("ITEMS_IMAGES_FOLDER");
				    backupFolder = macOS.getString("BACKUP_FOLDER");
				    logFolder = macOS.getString("LOG_FOLDER"); 
				    iconsFolder = macOS.getString("ICONS_FOLDER");	
				    graphRepsFolder = linuxOS.getString("GR_FOLDER");
				    xmlFolder = linuxOS.getString("XML_FOLDER");
				    dateFormat = macOS.getString("DATE_FORMAT");
				    dateTimeFormat = macOS.getString("DATETIME_FORMAT");
				    timeZoneText = macOS.getString("TIMEZONE");
				    language = macOS.getString("LANGUAGE");
				    country = macOS.getString("COUNTRY");
				    pingCommand = macOS.getString("PING_COMMAND");
				    pingCommandParams = macOS.getString("PING_ARGS");
				}
				System.out.println("look and feel: " + lookAndFeel);
			} 
			catch (RuntimeException e) { }
		}
		catch (RuntimeException e) { }
	}
	
	public static ApplicationMessage initAppContext()  {
		try {
		    // este metodo contiene rutinas de ejecucion de unica vez
            // el modo de uso standard es "STANDARD"
		    operativeMode = "STANDARD";
		    //
		    System.out.println("COUNTRY : "+country);
		    
            Locale l = new Locale(language, country);
		    timeZone = TimeZone.getTimeZone(timeZoneText);
            calendarDate = Calendar.getInstance(timeZone, l);
	  	    docLowestDate = Calendar.getInstance(timeZone, l);
	  	    docHighestDate = Calendar.getInstance(timeZone, l);
	  	    // tamanho de referencia para la ventana del programa POS
	  	    sizePercent = 100;
	  	    return null;
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationMessage m = new ApplicationMessage("Contexto App", "Error: " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
}
