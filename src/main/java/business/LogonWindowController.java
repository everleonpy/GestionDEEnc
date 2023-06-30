package business;

import java.io.PrintWriter;
import java.sql.Connection;

import dao.Util;

public class LogonWindowController {
	private Connection conn;
	private boolean testMode;
	private boolean supressDbCommands;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public boolean isTestMode() {
		return testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	public boolean isSupressDbCommands() {
		return supressDbCommands;
	}

	public void setSupressDbCommands(boolean supressDbCommands) {
		this.supressDbCommands = supressDbCommands;
	}

	public ApplicationMessage initForm () {
		ApplicationMessage m;
		try {
	        conn = Util.getConnection();
	        //
			// habilitar el archivo de log
			SessionContext.openLogFile();
			SessionContext.logWriter = new PrintWriter(SessionContext.logFile);		
			SessionContext.logWriter.println("Iniciando Avanza-EDocs");	
			// variables de test
			testMode = false;
			supressDbCommands = false;
			//testMode = true;
			//supressDbCommands = true;
	        return null;
		} catch (Exception e1) {
			m = new ApplicationMessage();
			m.setMessage("GET-CONN", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public void closeResources () {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
            System.out.println("cerrando archivo log1...");
	        SessionContext.closeLogFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
