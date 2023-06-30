package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	/**
	* @version 1
	* @author eleon
	* Clase encargada de instanciar una conexion singleton a la base de datos
	*/
	
	private static DbConnection instancia = null;
	  
	private Connection conn;
	private String url = "jdbc:sqlserver://192.168.30.100:1433;DatabaseName=JessyLens";
	private String username = "sa";
	private String password = "Sqljessylens12345";
	
	private DbConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.conn = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Connection getConnection() {
		return conn;
	}


	public static DbConnection getInstancia() throws SQLException {
		if( instancia == null ) {
			instancia = new DbConnection();
		} else if( instancia.getConnection().isClosed() ) {
			instancia = new DbConnection();
		}
		return instancia;
	}

}
