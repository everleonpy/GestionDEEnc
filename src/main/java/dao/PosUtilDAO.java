package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.TicketCaption;

public class PosUtilDAO {
	/**
	 * Este metodo tiene como objetivo obtener la lista de lineas de mensaje 
	 * a imprimir en los tickets correspondientes a una ubicacion dentro de
	 * los mismos 
	 * 
	 * @author jLcc
	 *
	 */
	public static ArrayList <TicketCaption> getCaptionsList ( String ticketPlace, long cashId ) { 
		
		Connection conn = null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		TicketCaption dm;
		int index;
		
		try {
			
			conn = Util.getConnection();
			if ( conn == null ) {
				return null;
			}

			ArrayList <TicketCaption> s = new ArrayList <TicketCaption>();
			StringBuffer buffer = new StringBuffer();		
			buffer.append("SELECT M.IDENTIFIER, M.ORG_ID, M.UNIT_ID, M.LOCATION,");
			buffer.append(" M.LINE_NUMBER, M.EMPTY_FLAG, M.FROM_DATE, M.TEXT,");
			buffer.append(" M.ALIGNMENT, M.TO_DATE" );
			buffer.append(" FROM POS_TICKET_CAPTIONS M" );
			// esto esta temporalmente deshabilitado hasta que se implemente todo el circuito
			//buffer.append(" WHERE M.CASH_REGISTER_ID = ?" );
			buffer.append(" WHERE M.LOCATION = ?" );
			buffer.append(" ORDER BY M.LINE_NUMBER" );
			
			stmtConsulta = conn.prepareStatement(buffer.toString());
			//stmtConsulta.setLong(1, cashId);
			stmtConsulta.setString(1, ticketPlace);
			rsConsulta = stmtConsulta.executeQuery();
			
			boolean vfound = false;
			index = 0;
			while ( rsConsulta.next() ) {
				dm = new TicketCaption();
				dm.setIDENTIFIER(rsConsulta.getLong(1));
				dm.setORG_ID(rsConsulta.getLong(2));
				dm.setUNIT_ID(rsConsulta.getLong(3));
				dm.setLOCATION(rsConsulta.getString(4));
				dm.setLINE_NUMBER(rsConsulta.getInt(5));
				dm.setEMPTY_FLAG(rsConsulta.getString(6));
				dm.setFROM_DATE(rsConsulta.getDate(7));
				dm.setTEXT(rsConsulta.getString(8));
				dm.setALIGNMENT(rsConsulta.getString(9));
				dm.setTO_DATE(rsConsulta.getDate(10));
				//System.out.println("texto: " + dm.getTEXTO());
				s.add(index, dm);
				index++;
				vfound = true;
			}
			
			if ( !vfound ) {
				return null;
			} else {
			    return s;
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}
	}

}
