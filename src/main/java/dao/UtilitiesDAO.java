package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Util;
import dao.ProblemaDatosException;

public class UtilitiesDAO {
	/**
	 * Este metodo tiene como objetivo obtener el siguiente valor de una secuencia.
	 * @param seqName - String - Nombre de la secuencia cuyo valor se desea obtener
	 * @return
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */
	public static long getNextval ( String seqName ) {
		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		long seqValue = -1;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select " + seqName + ".nextval from dual");
			stmtConsulta = conn.prepareStatement(buffer.toString());			
			rsConsulta = stmtConsulta.executeQuery();
			if (rsConsulta.next()) { 
 			    seqValue = (rsConsulta.getLong(1));
			}
			return seqValue;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}

	public static long getNextval ( String seqName, Connection conn ) {
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		long seqValue = -1;
		
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select " + seqName + ".nextval from dual");
			stmtConsulta = conn.prepareStatement(buffer.toString());			
			rsConsulta = stmtConsulta.executeQuery();
			if (rsConsulta.next()) { 
 			    seqValue = (rsConsulta.getLong(1));
			}
			return seqValue;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
		}	
	}

	/**
	 * Este metodo tiene como objetivo obtener la fecha de la base de datos del punto de venta.
	 * @return java.Util.Date - Fecha actual de la base de datos del punto de venta
	 * @throws ProblemaDatosException
	 * @throws Exception
	 */
	public static java.util.Date getLocalDbDate ( ) throws ProblemaDatosException, Exception{
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		
		java.util.Date fecha = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select to_char(sysdate, 'dd/mm/yyyy hh24:mi:ss') from dual");         
			stmtConsulta = conn.prepareStatement(buffer.toString());			
			rsConsulta = stmtConsulta.executeQuery();
			
			if (rsConsulta.next()) { 
				fecha = sdf.parse(rsConsulta.getString(1)); 			    
			}
            return fecha;
			
		} catch (Exception e) {
			throw e;			
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}
	
}
