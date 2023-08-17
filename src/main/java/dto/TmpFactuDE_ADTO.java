package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.Util;

/**
* 
* @author eleon
*
*/
public class TmpFactuDE_ADTO 
{
	
	
	/**
	* 
	* @param feche
	* @return
	*/
	public static List<String> obtenrCDCs(Date fecha)
	{
		Connection conn = null;
		@SuppressWarnings("unused")
		PreparedStatement stmtConsulta = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select tmp.idConfig, tmp.idMov, tmp.dVerFor, tmp.Id, tmp.dDVId, tmp.dSisFact, tmp.dFecFirma");
		sb.append(" from tmpFactuDE_A tmp inner join tmpFactuDE_C c  on tmp.idMov=c.idMov");
		sb.append(" where tmp.fechaFactura >= '"+DatetoString(fecha)+"'");
		sb.append(" and tmp.fechaFactura <= '"+DatetoString(fecha)+"'");
		sb.append(" and c.iTiDE=1");
		sb.append(" and tmp.idMov not in(select transaction_id from RCV_TRX_EB_BATCH_ITEMS where result_status='Aprobado')");
		sb.append(" order by tmp.idMov");
		
		
				
				try 
				{
					
					conn = Util.getConnection();
					ps = conn.prepareStatement(sb.toString().trim());
					rs = ps.executeQuery();
					
					if( rs != null ) 
					{
						List<String> resp = new ArrayList<String>();
						while(rs.next()) 
						{
							resp.add(rs.getString("id"));
						}
						
						return resp;
					}
					
					
				} catch (SQLException e) {
					System.err.println("*** [ ERROR ] *************************************************");
					System.err.println("* "+e.getMessage());
					System.err.println("* "+e.getErrorCode());
					System.err.println("* "+e.getLocalizedMessage());
					System.err.println("***************************************************************");
					e.printStackTrace();
					
				} finally { 
					
					Util.closeResultSet(rs);
					Util.closeStatement(ps);
					Util.closeJDBCConnection(conn);
					
				}	
		
		
		return null;
	}
	
	
	/**
	* update RCV_TRX_EB_BATCH_ITEMS set result_status='Aprobado' where control_code='01800546750001002000003022023070114378982024'
	* @param cdc
	* @return
	*/
	public static boolean updateStatus(String cdc) 
	{
		Connection conn = null;
		@SuppressWarnings("unused")
		PreparedStatement stmtConsulta = null;
		PreparedStatement ps = null;
				
		StringBuilder sb = new StringBuilder();
		sb.append("update RCV_TRX_EB_BATCH_ITEMS set result_status='Aprobado' where control_code=?");
			
			try 
			{
				conn = Util.getConnection();
				ps = conn.prepareStatement(sb.toString().trim());
				ps.setString(1, cdc.trim());
				
				int record = ps.executeUpdate();
				if( record > 0 ) 
				{
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally 
			{ 
				
				Util.closeStatement(ps);
				Util.closeJDBCConnection(conn);
			
			}	
				
		return false;
	}
	
	
	
	/**
	* 
	* @param toDate
	* @return
	*/
	private static String DatetoString(java.util.Date toDate) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		return dateFormat.format(toDate);
	}

}
