/*
 * Created on May 23, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.apache.log4j.Logger;

/**
 * @author Owner
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Util {
	//private static Logger logger = Logger.getLogger(Util.class);

	public static Connection getConnection()  
	{
		//String ipAddress = "192.168.30.100";  // IP Jessy Lens
		//String ipAddress = "192.168.1.190";    // IP Agro 
		String ipAddress = "190.128.151.98";    // IP Agro Publica
		String port = "1433";
		//String dbName = "JessyLens"; 
		String dbName = "agro"; 
		
		Connection c = null;
		
		try {
			
			String connString = "jdbc:sqlserver://" + ipAddress + ":" + port + ";DatabaseName="+dbName;
			
			/*String connString = "jdbc:sqlserver://" + ipAddress + ":" + port + ";DatabaseName=JessyLens;"
							   +"integratedSecurity=true;encrypt=false"; */
			
			System.out.println("Connection string: " + connString);
			
			String user = "sa";
			//String pass =  "Sqljessylens12345";   //JessyLens
			String pass =  "Sqlseagro12345"; //Seagro
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			c = DriverManager.getConnection(connString, user, pass);		
			
			return c;
			
		} catch (Exception e) {
			System.out.println( e.getClass().getName()+": "+ e.getMessage() );
			//System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return null;
		}
	}
	
	public static void closeJDBCConnection(final Connection conn)
	{
		if (conn != null)
		{
			 try
			 {
				conn.close();
			 }
			 catch (SQLException ex)
			 {
				//logger.error(conn, ex);
				 System.out.println("Cierre conexion: " + ex.getMessage());
			 }
		}
	}

	public static void closeStatement(final Statement stmt)
	{
		if (stmt != null)
		{
			 try
			 {
				stmt.close();
			 }
			 catch (SQLException ex)
			 {
				//logger.error(stmt, ex);
				 System.out.println("Cierre sentencia: " + ex.getMessage());
			 }
		}
	}

	public static void closeCallableStatement(final CallableStatement stmt)
	{
		if (stmt != null)
		{
			 try
			 {
				stmt.close();
			 }
			 catch (SQLException ex)
			 {
				//logger.error(stmt, ex);
				 System.out.println("Cierre sentencia: " + ex.getMessage());
			 }
		}
	}

	public static void closeResultSet(final ResultSet rs)
	{
		if (rs != null)
		{
			 try
			 {
				rs.close();
			 }
			 catch (SQLException ex)
			 {
				//logger.error(rs, ex);
				 System.out.println("Cierre resulset: " + ex.getMessage());
			 }
		}
	}


}
