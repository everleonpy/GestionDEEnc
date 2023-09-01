package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.Util;
import business.AppConfig;
import dao.ProblemaDatosException;

public class ApplicationUtilsDAO 
{
	 
	/**
	* Este metodo tiene como objetivo obtener el valor de una secuencia 
	* de base de datos cuyo nombre se recibe como parametro
	* 
	* @author jLcc 
	*
	*/
	public static long getSeqNextval ( String seqName ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long seqValue = 0;
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			if (AppConfig.dbEngine.equalsIgnoreCase("Oracle")) {
				buffer.append("select " + seqName + ".nextval from dual");         
			}
			if (AppConfig.dbEngine.equalsIgnoreCase("Postgresql")) {
				buffer.append("select nextval(" + seqName + ")");         
			}
			ps = conn.prepareStatement(buffer.toString());			
			rs = ps.executeQuery();
			boolean dataFound = false;
			
			if (rs.next()) { 
 			    seqValue = (rs.getLong(1));
				dataFound = true;
			}
		
			if (dataFound == false ) {
				throw new ProblemaDatosException(1, "No se pudo obtener el valor de la secuencia: " + seqName);
			}

			return seqValue;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			//throw new ProblemaDatosException(1, "No existe la secuencia: " + seqName);
		} finally { 
			
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
			
		}	
	}

	/**
	 * Este metodo tiene como objetivo determinar si un usuario tiene 
	 * una autorizacion especifica 
	 * 
	 * @author jLcc
	 *
	 */
	public static String[] getRefValues ( String tableName, String columnName ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String [] vr;
		int index = 0;
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}

			// contar de antemano la cantidad de elementos a recuperar
			StringBuffer buffer = new StringBuffer();
			buffer.append("select count(*)");         
			buffer.append(" from fnd_references r");
			buffer.append(" where r.column_name like ?");
			buffer.append(" and r.table_name like ?");			
			ps = conn.prepareStatement(buffer.toString());			
			ps.setString(1, columnName);
			ps.setString(2, tableName);
			rs = ps.executeQuery();
			
			if (rs.next()) { 
				index = rs.getInt(1);
			}

            if (index > 0) {			
			    // cargar el array de valores de referencia
			    buffer = new StringBuffer();			
			    buffer.append("select r.value");         
			    buffer.append(" from fnd_references r");
			    buffer.append(" where r.column_name like ?");
			    buffer.append(" and r.table_name like ?");			
			    ps = conn.prepareStatement(buffer.toString());			
			    ps.setString(1, columnName);
			    ps.setString(2, tableName);
			    rs = ps.executeQuery();
			    vr = new String[index];
			    index = 0;
			    while (rs.next()) { 
				    vr[index] = rs.getString(1);
				    index++;
			    }	
		       return vr;
            } else {
            	return null;
            }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}	
		
	/**
	 * Este metodo tiene como objetivo devolver la fecha de la base 
	 * de datos del punto de venta 
	 * 
	 * @author jLcc
	 *
	 */
	public static java.util.Date getDbDate ( ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		java.util.Date fecha = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			if (AppConfig.dbEngine.equalsIgnoreCase("Oracle")) {
				buffer.append("select to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss') from dual");         
			}
			if (AppConfig.dbEngine.equalsIgnoreCase("Postgresql")) {
				buffer.append("select to_char(current_timestamp, 'dd/mm/yyyy hh24:mi:ss') from dual");         
			}
			ps = conn.prepareStatement(buffer.toString());			
			rs = ps.executeQuery();
			if (rs.next()) { 
				//System.out.println("**********fecha trx: " + rsConsulta.getString(1));
				fecha = sdf.parse(rs.getString(1)); 	
			}		
            return fecha;
		} catch (Exception e) {
			e.printStackTrace(); 			
			throw e;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}

	/**
	 * Este metodo tiene como objetivo devolver la hora actual de la base 
	 * de datos del punto de venta 
	 * 
	 * @author jLcc
	 *
	 */
	public static String getLocalDbTime ( ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		
		String hora = null;
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			if (AppConfig.dbEngine.equalsIgnoreCase("Oracle")) {
			    buffer.append("select to_char(sysdate, 'hh24mi') from dual");    
			}
			if (AppConfig.dbEngine.equalsIgnoreCase("Postgresql")) {
			    buffer.append("select to_char(current_timestamp, 'hh24mi')");    
			}
			stmtConsulta = conn.prepareStatement(buffer.toString());			
			rsConsulta = stmtConsulta.executeQuery();
			
			if (rsConsulta.next()) { 
				hora = (rsConsulta.getString(1)); 			    
			}
			
            return hora;
			
		} catch (Exception e) {
			//Como no pude conectarme puedo o lanzar un error, si no hay error 
			
			throw e;
			
		} finally { 
			
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
			
		}	
	}	
	
	/**
	 * Este metodo tiene como objetivo obtener el nombre de un dia de 
	 * la semana 
	 * 
	 * @author jLcc
	 *
	 */
	public static String getDayName ( int diaSemana ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		String nombreDia = "";
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select NAME");         
			buffer.append(" from FND_DAYS_OF_WEEK");
			buffer.append(" where IDENTIFIER = ?");
			stmtConsulta = conn.prepareStatement(buffer.toString());			
			stmtConsulta.setLong(1, diaSemana);
			rsConsulta = stmtConsulta.executeQuery();
			
			if (rsConsulta.next()) { 
 			    nombreDia = (rsConsulta.getString(1));
			}
			
		    return nombreDia;
			
		} catch (Exception e) {
			//Como no pude conectarme puedo o lanzar un error, si no hay error 
			
			throw e;
			
		} finally { 
			
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
			
		}	
	}

}
