package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dao.Util;
import pojo.UserSession;

public class UserSessionDAO {

	public static UserSession getRow (String userName) throws ProblemaDatosException, Exception{

		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean exists = false;

		try {
			conn = Util.getConnection();
			if( conn == null ) {
				throw new SQLException("No hay conexion con Base de Datos ");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, USER_NAME,");
			buffer.append(" CASH_REGISTER_ID, WORK_STATION, START_DATE, END_DATE,");
			buffer.append(" nvl(NORMAL_EXIT, 'N') NORMAL_EXIT, CLOSING_HEADTLLR_ID, CLOSING_DATE, nvl(PRINT_HEADER_FLAG,'N') PRINT_HEADER_FLAG");
			buffer.append(" from POS_USERS_SESSIONS s");
			buffer.append(" where trunc(s.START_DATE) >= (trunc(sysdate) - 1)");			
			buffer.append(" and upper(s.USER_NAME) = upper(?)");
			buffer.append(" and s.END_DATE is null");
	        	buffer.append(" order by s.START_DATE desc"); 

			stmtConsulta = conn.prepareStatement(buffer.toString());
			stmtConsulta.setString(1, userName.toUpperCase());
			rsConsulta = stmtConsulta.executeQuery();

			//System.out.println(userName.toUpperCase());
			
			UserSession s = new UserSession();

			if ( rsConsulta.next() ) {
				s.setCASH_REGISTER_ID(rsConsulta.getLong("CASH_REGISTER_ID"));
				s.setCLOSING_DATE(rsConsulta.getDate("CLOSING_DATE"));
				s.setCLOSING_HEADTLLR_ID(rsConsulta.getLong("CLOSING_HEADTLLR_ID"));
				s.setEND_DATE(rsConsulta.getDate("END_DATE"));
				s.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
				//System.out.println(s.getIDENTIFIER());
				s.setNORMAL_EXIT(rsConsulta.getString("NORMAL_EXIT"));
				s.setORG_ID(rsConsulta.getLong("ORG_ID"));
				s.setPRINT_HEADER_FLAG(rsConsulta.getString("PRINT_HEADER_FLAG"));
				s.setSTART_DATE(rsConsulta.getDate("START_DATE"));
				s.setUNIT_ID(rsConsulta.getLong("UNIT_ID"));
				s.setUSER_NAME(rsConsulta.getString("USER_NAME"));
				s.setWORK_STATION(rsConsulta.getString("WORK_STATION"));	
				exists = true;
			}

			if ( exists == true) {
			    return s;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static ArrayList<UserSession> getOpenSessionsList () throws ProblemaDatosException, Exception{

		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		UserSession c;
		ArrayList<UserSession> l;
		ResultSet rsConsulta = null;
		boolean dataFound = false;

		try {
			conn = Util.getConnection();
			if( conn == null ) {
				throw new SQLException("No hay conexion con Base de Datos ");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select IDENTIFIER, ORG_ID, UNIT_ID, USER_NAME,");
			buffer.append(" CASH_REGISTER_ID, WORK_STATION, START_DATE, END_DATE,");
			buffer.append(" nvl(NORMAL_EXIT, 'N') NORMAL_EXIT, CLOSING_HEADTLLR_ID, CLOSING_DATE, nvl(PRINT_HEADER_FLAG,'N') PRINT_HEADER_FLAG");
			buffer.append(" from POS_USERS_SESSIONS s");
			buffer.append(" where s.END_DATE is null");
	      	buffer.append(" order by s.START_DATE desc"); 

			stmtConsulta = conn.prepareStatement(buffer.toString());
			rsConsulta = stmtConsulta.executeQuery();

			l = new ArrayList<UserSession>();

			while ( rsConsulta.next() ) {
				dataFound = true;
				c = new UserSession();
				c.setCASH_REGISTER_ID(rsConsulta.getLong("CASH_REGISTER_ID"));
				c.setCLOSING_DATE(rsConsulta.getDate("CLOSING_DATE"));
				c.setCLOSING_HEADTLLR_ID(rsConsulta.getLong("CLOSING_HEADTLLR_ID"));
				c.setEND_DATE(rsConsulta.getDate("END_DATE"));
				c.setIDENTIFIER(rsConsulta.getLong("IDENTIFIER"));
				c.setNORMAL_EXIT(rsConsulta.getString("NORMAL_EXIT"));
				c.setORG_ID(rsConsulta.getLong("ORG_ID"));
				c.setPRINT_HEADER_FLAG(rsConsulta.getString("PRINT_HEADER_FLAG"));
				c.setSTART_DATE(rsConsulta.getDate("START_DATE"));
				c.setUNIT_ID(rsConsulta.getLong("UNIT_ID"));
				c.setUSER_NAME(rsConsulta.getString("USER_NAME"));
				c.setWORK_STATION(rsConsulta.getString("WORK_STATION"));	
				l.add(c);
			}
			if ( dataFound == true) {
			    return l;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}
	
	/**
	 * Tiene como funcion verificar el estado del control de impresion de titulo
	 * de ticket
	 * @param ItemCobro
	 * @throws Exception
	 */
	public static boolean isTitlePrinted ( long sessionId, long unitId ) throws ProblemaDatosException, Exception{

		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean printed = false;

		try {
			conn = Util.getConnection();
			if( conn == null ) {
				throw new SQLException("No hay conexion con Base de Datos ");
			}
		
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select nvl(PRINT_HEADER_FLAG, 'N')");
			buffer.append(" from pos_users_sessions s");
			buffer.append(" where s.unit_id = ?");
			buffer.append(" and s.identifier = ?");

			stmtConsulta = conn.prepareStatement(buffer.toString());
			stmtConsulta.setLong(1, unitId);
			stmtConsulta.setLong(2, sessionId);
			rsConsulta = stmtConsulta.executeQuery();

			if ( rsConsulta.next() ) {
				printed = (rsConsulta.getString(1).equals("S"));
			}

			return printed;

		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}
	
	/**
	 * Tiene como funcion actualizar el control de impresion de encabezado
	 * de ticket
	 * @param 
	 * @throws Exception
	 */

	public static void setPrintedFlag (long sessionId, long unitId, boolean printed) throws Exception {

		Connection conn = null;
		PreparedStatement stmtInsert = null;
		String printedFlag;

		try {
			conn = Util.getConnection();
			if ( conn == null) {
				throw new SQLException("No hay conexion con Base de Datos");
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("update pos_users_sessions");
			buffer.append(" set PRINT_HEADER_FLAG = ?");
			buffer.append(" where unit_id = ?");
			buffer.append(" and identifier = ?");

			stmtInsert = conn.prepareStatement(buffer.toString());

			if (printed) {
				printedFlag = "S";
			} else {
				printedFlag = "N";
			}
			stmtInsert.setString(1, printedFlag);
			stmtInsert.setLong(2, unitId);
			stmtInsert.setLong(3, sessionId);

			stmtInsert.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtInsert);
			Util.closeJDBCConnection(conn);
		}

	}

	/**
	 * Tiene como funcion crear un registro de control de sesion
	 * @param idSesion
	 * @param idUnidad
	 * @throws Exception
	 */	
	public static void insertRow ( UserSession s ) throws Exception {
		
		Connection conn =  null;
		PreparedStatement stmtInsert = null;
			
		try {
			conn = Util.getConnection();
			
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			             
		    StringBuffer buffer = new StringBuffer();

			buffer.append("insert into POS_USERS_SESSIONS (");
			buffer.append(" IDENTIFIER, ORG_ID, UNIT_ID, USER_NAME,");
			buffer.append(" CASH_REGISTER_ID, WORK_STATION, START_DATE, END_DATE,");
			buffer.append(" NORMAL_EXIT, CLOSING_HEADTLLR_ID, CLOSING_DATE, PRINT_HEADER_FLAG )");
			buffer.append(" values (");
			buffer.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
			buffer.append(" ?, ? )");			
			
			stmtInsert = conn.prepareStatement(buffer.toString());
			
			stmtInsert.setLong(1, s.getIDENTIFIER());
			stmtInsert.setLong(2, s.getORG_ID());
			stmtInsert.setLong(3, s.getUNIT_ID());
			stmtInsert.setString(4, s.getUSER_NAME());
			stmtInsert.setLong(5, s.getCASH_REGISTER_ID());
			stmtInsert.setString(6, s.getWORK_STATION());
			stmtInsert.setTimestamp(7, new Timestamp( s.getSTART_DATE().getTime()));
			stmtInsert.setNull(8, java.sql.Types.DATE);
			stmtInsert.setNull(9, java.sql.Types.VARCHAR);
			stmtInsert.setNull(10, java.sql.Types.INTEGER);
			stmtInsert.setNull(11, java.sql.Types.DATE);
			stmtInsert.setNull(12, java.sql.Types.VARCHAR);

			stmtInsert.executeUpdate();
			
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		Util.closeStatement(stmtInsert);
		Util.closeJDBCConnection(conn);
	}
	}	

	/**
	 * Tiene como funcion marcar como cerrado un registro de control de sesion
	 * @param idSesion
	 * @param idUnidad
	 * @throws Exception
	 */

	public static void endSession (long sessionId, long unitId) throws Exception {

		Connection conn = null;
		PreparedStatement stmtInsert = null;

		try {
			conn = Util.getConnection();
			if ( conn == null) {
				throw new SQLException("No hay conexion con Base de Datos");
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("update POS_USERS_SESSIONS");
			buffer.append(" set END_DATE = sysdate");
			buffer.append(" NORMAL_EXIT = 'S'");
			buffer.append(" where UNIT_ID = ?");
			buffer.append(" and IDENTIFIER = ?");

			stmtInsert = conn.prepareStatement(buffer.toString());

			stmtInsert.setLong(1, unitId);
			stmtInsert.setLong(2, sessionId);

			stmtInsert.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtInsert);
			Util.closeJDBCConnection(conn);
		}
	}	

	/**
	 * Tiene como funcion marcar como cerrado un registro de control de sesion
	 * @param codUsuario - String - Nombre de usuario cuyas sesiones cerrar
	 * @param fecha - Date - Fecha de las sesiones a cerrar
	 * @param idUnidad - long - Identificador de unidad operativa
	 * @throws Exception
	 */

	public static void endSession (String userName, java.util.Date date, long unitId) throws Exception {

		Connection conn = null;
		PreparedStatement stmtUpdate = null;

		try {
			conn = Util.getConnection();
			if ( conn == null) {
				throw new SQLException("No hay conexion con Base de Datos");
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("update POS_USERS_SESSIONS");
			buffer.append(" set FECHA_FIN = sysdate,");
			buffer.append(" NORMAL_EXIT = 'S'");
			buffer.append(" where UNIT_ID = ?");
			buffer.append(" and upper(USUARIO) = upper(?)");
			buffer.append(" and trunc(FECHA_INICIO) = trunc(?)");

			stmtUpdate = conn.prepareStatement(buffer.toString());

			stmtUpdate.setLong(1, unitId);
			stmtUpdate.setString(2, userName);
			stmtUpdate.setTimestamp(3, new Timestamp(date.getTime()));

			stmtUpdate.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
		}
	}	

	public static void terminateSession (long sessionId, long headTellerId) throws Exception {

		Connection conn = null;
		PreparedStatement stmtInsert = null;

		try {
			conn = Util.getConnection();
			if ( conn == null) {
				throw new SQLException("No hay conexion con Base de Datos");
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("update POS_USERS_SESSIONS");
			buffer.append(" set END_DATE = sysdate,");
			buffer.append(" CLOSING_DATE = sysdate,");
			buffer.append(" NORMAL_EXIT = 'N',");
			buffer.append(" CLOSING_HEADTLLR_ID = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtInsert = conn.prepareStatement(buffer.toString());

			stmtInsert.setLong(1, headTellerId);
			stmtInsert.setLong(2, sessionId);

			stmtInsert.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Util.closeStatement(stmtInsert);
			Util.closeJDBCConnection(conn);
		}
	}	
	
}
