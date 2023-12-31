/*
 * Created on May 23, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import util.StringTools;

/**
* @author Owner
*
*/
public class Util 
{
	
	private static String ipAddress = null;
	private static String port = null;
	private static String dbName = null;
	private static Connection c = null;
	private static String user = null;
	private static String pass = null;
	
	public static Connection getConnection()  
	{
		
		try {
			
			InitParameters();
			
			if( ipAddress != null ) 
				{
					String connString = "jdbc:sqlserver://" + ipAddress + ":" + port + ";DatabaseName="+dbName;
					
					/*String connString = "jdbc:sqlserver://" + ipAddress + ":" + port + ";DatabaseName=JessyLens;"
									   +"integratedSecurity=true;encrypt=false"; */
					
					//System.out.println("DB Connection : "+connString);
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					c = DriverManager.getConnection(connString, user, pass);		
					
					return c;
				}
			
			return null;
		} catch (Exception e) 
		{
		
			/*StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
			JOptionPane.showMessageDialog(null, sw.toString(), " [ ERROR ] ", 
												JOptionPane.ERROR_MESSAGE);*/
			
			StringBuilder sbEx = new StringBuilder();
			sbEx.append(e.getClass().getName()+"\n");
			sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
			
			JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	
	/**
	* Metodo Encargado de cargar los parametros de conexion a la DB
	*/
	private static void InitParameters() 
	{
		Properties propiedades = new Properties();
        FileInputStream archivoPropiedades = null;
        
        try {
			archivoPropiedades = new FileInputStream("app.properties");
			propiedades.load(archivoPropiedades);
			
			ipAddress = propiedades.getProperty("db.ip");
			port = propiedades.getProperty("db.port");
			dbName = propiedades.getProperty("db.name");
			user = propiedades.getProperty("db.user");
			pass = propiedades.getProperty("db.pass");
			
		} catch (IOException e) {
			
			StringBuilder sbEx = new StringBuilder();
			sbEx.append(e.getClass().getName()+"\n");
			sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
			
			JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		
	}


	/**
	* 
	* @param conn
	*/
	public static void closeJDBCConnection(final Connection conn)
	{
		if (conn != null)
		{
			 try
			 {
				conn.close();
			 }
			 catch (SQLException e)
			 {
				    StringBuilder sbEx = new StringBuilder();
					sbEx.append(e.getClass().getName()+"\n");
					sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
					
					JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
							JOptionPane.ERROR_MESSAGE);
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
			 catch (SQLException e)
			 {
				StringBuilder sbEx = new StringBuilder();
				sbEx.append(e.getClass().getName()+"\n");
				sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
					
				JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
							JOptionPane.ERROR_MESSAGE);
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
			 catch (SQLException e)
			 {
				StringBuilder sbEx = new StringBuilder();
				sbEx.append(e.getClass().getName()+"\n");
				sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
						
					JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
								JOptionPane.ERROR_MESSAGE);
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
			 catch (SQLException e)
			 {
				StringBuilder sbEx = new StringBuilder();
				sbEx.append(e.getClass().getName()+"\n");
				sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
					
				JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
								JOptionPane.ERROR_MESSAGE);
			 }
		}
	}


}
