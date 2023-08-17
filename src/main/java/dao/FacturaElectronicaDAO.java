package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.math3.util.Precision;
import nider.TmpFactuDE_A;
import nider.TmpFactuDE_B;
import nider.TmpFactuDE_C;
import nider.TmpFactuDE_D;
import nider.TmpFactuDE_D1;
import nider.TmpFactuDE_D2;
import nider.TmpFactuDE_D21;
import nider.TmpFactuDE_D22;
import nider.TmpFactuDE_D3;
import nider.TmpFactuDE_E;
import nider.TmpFactuDE_E0;
import nider.TmpFactuDE_E1;
import nider.TmpFactuDE_E7;
import nider.TmpFactuDE_E71;
import nider.TmpFactuDE_E711;
import nider.TmpFactuDE_E712;
import nider.TmpFactuDE_E72;
import nider.TmpFactuDE_E721;
import nider.TmpFactuDE_E8;
import nider.TmpFactuDE_E81;
import nider.TmpFactuDE_E811;
import nider.TmpFactuDE_E82;
import util.UtilPOS;

public class FacturaElectronicaDAO 
{
	
	public static ArrayList<TmpFactuDE_A> getDEList ( java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		@SuppressWarnings("unused")
		java.util.Date toDate = null;  
		int idMov = 0;
		int rowsCounter = 0;
		@SuppressWarnings("unused")
		int index = 0;
		// el valor de este atributo ira incrementando a medida que se vayan cargando
		// transacciones al lote hasta llegar a "maxTrxQty"
		@SuppressWarnings("unused")
		int trxCounter = 0;
		//
		try {
			conn = Util.getConnection();
			if (conn == null) {
				return null; 
			}

			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// configuracion de la fecha
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			//TODO  datos de la cabecera de la transaccion 
			/*buffer.append("select x.idConfig, x.idMov, x.dVerFor, x.Id,");
			buffer.append(" x.dDVId, x.dSisFact, x.dFecFirma"); 
			buffer.append(" from tmpFactuDE_A x");
			//
		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = h.IDENTIFIER )");
			//
			buffer.append(" and x.fechaFactura < ?");
			buffer.append(" and x.fechaFactura >= ?");
			buffer.append(" order by x.idMov");
			*/
			
			//TODO Nuevo Select Via Nider -----
			//System.out.println("Fecha Desde : "+DatetoString(fromDate)+"  Hasta : "+DatetoString(fromDate));
			buffer.append(" select tmp.idConfig, tmp.idMov, tmp.dVerFor, tmp.Id, tmp.dDVId, tmp.dSisFact, tmp.dFecFirma");
			buffer.append(" from tmpFactuDE_A tmp inner join tmpFactuDE_C c  on tmp.idMov=c.idMov");
			buffer.append(" where tmp.fechaFactura >= '"+DatetoString(fromDate)+"'");
			buffer.append(" and tmp.fechaFactura <= '"+DatetoString(fromDate)+"'");
			buffer.append(" and c.iTiDE=1");
			buffer.append(" and tmp.idMov not in(select transaction_id from RCV_TRX_EB_BATCH_ITEMS where result_status='Aprobado')");
			//buffer.append(" and convert(varchar,tmp.fechaFactura,103)>=convert(varchar,?,103)"); 
			//buffer.append(" and convert(varchar,tmp.fechaFactura,103)<=convert(varchar,?,103)");
			buffer.append(" order by tmp.idMov");
		
			System.out.println("*************************************************************************");
			System.out.println("SQL : "+buffer.toString());
			System.out.println("*************************************************************************");
			
			//
			ps = conn.prepareStatement(buffer.toString());
			index++;
			
			//ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			//ps.setDate(index, new Date(toDate.getTime()));
			index++;
			//ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			//ps.setDate(index, new Date(fromDate.getTime()));
			//System.out.println("Consultando fechas desde: " + fromDate + " hasta: " + toDate);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_A> deList = new ArrayList<TmpFactuDE_A>();
			
			while (rs.next()) 
			{
				dataFound = true;
				idMov = rs.getInt("idMov");
				//itDe =
				System.out.println("********* Transaccion: " + idMov);
				TmpFactuDE_A o = new TmpFactuDE_A();
				o.setdDVId(rs.getShort("dDVId"));
				o.setdFecFirma(rs.getString("dFecFirma"));
				o.setdSisFact(rs.getShort("dSisFact"));
				o.setdVerFor(rs.getShort("dVerFor"));
				o.setId(rs.getString("Id"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// obtener la instancia de gOpeDE
				TmpFactuDE_B b = new TmpFactuDE_B();
				b = FacturaElectronicaDAO.getgOpeDE(idMov, conn);
				o.setgOpeDe(b);
				// obtener la instancia de gTimb
				TmpFactuDE_C c = new TmpFactuDE_C();
				c = FacturaElectronicaDAO.getgTimb(idMov, conn);
				o.setgTimb(c);
				// obtener la instancia de gDatGralOpe
				TmpFactuDE_D g = new TmpFactuDE_D();
				g = FacturaElectronicaDAO.getgDatGralOpe(idMov, conn);
				o.setgDatGralOpe(g);
				// obtener la instancia de gDTipDE
				TmpFactuDE_E0 td = new TmpFactuDE_E0();
				TmpFactuDE_E fe = FacturaElectronicaDAO.getgCamFE(idMov, conn);
				td.setgCamFE(fe);
				// obtener la condicion de la operacion
				TmpFactuDE_E7 co = new TmpFactuDE_E7();
				co = FacturaElectronicaDAO.getgCamCond(idMov, conn);
				td.setgCamCond(co);
				// obtener la lista de items de la operacion
				ArrayList<TmpFactuDE_E8> items = FacturaElectronicaDAO.getgCamItem(idMov, conn);
				System.out.println("Factura: " + idMov + " Items: " + items.size());
				td.setItemsList(items);
				//
				o.setgTipDE(td);
				//
				deList.add(o);
				rowsCounter++;
				//
				System.out.println("tmpFactuDE_A: " + rs.getInt("idMov") + " - " + rs.getInt("idConfig") + " - " + rowsCounter);
				
				
				// este codigo es solo para propositos de test
				//if (rowsCounter == 3) {
				//	break;
				//}
			}
			if (dataFound == true) {
		        System.out.println("retornando lista con tamanho: " + deList.size());
				return deList;
			} else {
				System.out.println("retornando lista vacia");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}
	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	 */
	public static TmpFactuDE_B getgOpeDE ( int idMov, Connection conn ) 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iTipEmi, x.dDesTipEmi, x.dCodSeg,");
			buffer.append(" x.dInfoEmi, x.dInfoFisc");
			buffer.append(" from tmpFactuDE_B x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_B o = new TmpFactuDE_B();
			if (rs.next()) 
			{
				dataFound = true;
				o.setdCodSeg(rs.getString("dCodSeg"));
				o.setdDesTipEmi(rs.getString("dDesTipEmi"));
				o.setdInfoEmi(rs.getString("dInfoEmi"));
				o.setdInfoFisc(rs.getString("dInfoFisc"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTipEmi(rs.getShort("iTipEmi"));				
				//
				//System.out.println("TmpFactuDE_B: " + rs.getString("dCodSeg"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	*/
	public static TmpFactuDE_C getgTimb ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date xDate = null;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.iTiDE, x.dDesTiDE, x.dNumTim, x.dEst,");
			buffer.append(" x.dPunExp, x.dNumDoc, x.dSerieNum, x.dFeIniT,");
			buffer.append(" x.idConfig");
			buffer.append(" from tmpFactuDE_C x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
		
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_C o = new TmpFactuDE_C();
			if (rs.next()) {
				dataFound = true;
				o.setdDesTiDE(rs.getString("dDesTiDE"));
				o.setdEst(rs.getString("dEst"));
			    try {
			        xDate = sdf.parse(rs.getString("dFeIniT"));
			    } catch ( Exception e) {
			    	    System.out.println("Error al parsear fecha inicio: " + rs.getString("dFeIniT"));
			    	    e.printStackTrace();
			    }
				o.setdFeIniT(xDate);
				o.setdNumDoc(rs.getInt("dNumDoc"));
				o.setdNumTim(rs.getInt("dNumTim"));
				o.setdPunExp(rs.getString("dPunExp"));
				if (rs.getString("dSerieNum") != null) {
				    o.setdSerieNum(rs.getString("dSerieNum"));
				}
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTiDE(rs.getShort("iTiDE"));				
				//
				//System.out.println("TmpFactuDE_C: " + rs.getString("dEst") + "-" + rs.getString("dPunExp") + "-" + rs.getInt("dNumDoc"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	 */
	public static TmpFactuDE_D getgDatGralOpe ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = null;
		String t = null;
		java.util.Date xDate = null;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dFeEmiDE");
			buffer.append(" from tmpFactuDE_D x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_D o = new TmpFactuDE_D();
			if (rs.next()) {
				dataFound = true;
			    try {
			    	    d = rs.getString("dFeEmiDE").substring(0, 10);
			    	    t = rs.getString("dFeEmiDE").substring(11);
			        xDate = sdf1.parse(d + " " + t);
			    } catch ( Exception e) {
			    	    System.out.println("Error al parsear fecha: " + d + " " + t);
			    	    e.printStackTrace();
			    }
				o.setdFeEmiDE(xDate);
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// cargar los elementos contenidos
				TmpFactuDE_D1 gOpeCom = new TmpFactuDE_D1();
				gOpeCom = FacturaElectronicaDAO.getgOpeCom(idMov, conn);
				o.setgOpeCom(gOpeCom);
				//
				TmpFactuDE_D2 gEmis = new TmpFactuDE_D2();
				gEmis = FacturaElectronicaDAO.getgEmis(idMov, conn);
				o.setgEmis(gEmis);
				//
				TmpFactuDE_D3 gDatRec = new TmpFactuDE_D3();
				gDatRec = FacturaElectronicaDAO.getgDatRec(idMov, conn);
				o.setgDatRec(gDatRec);
				//
				//System.out.println("TmpFactuDE_D: " + rs.getString("dFeEmiDE"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	*/
	public static TmpFactuDE_D1 getgOpeCom ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iTipTra, x.dDesTipTra, x.iTImp,");
			buffer.append(" x.dDesTImp, x.cMoneOpe, x.dDesMoneOpe, x.dCondTiCam,");
			buffer.append(" x.dTiCam, x.iCondAnt, x.dDesCondAnt");
			buffer.append(" from tmpFactuDE_D1 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_D1 o = new TmpFactuDE_D1();
			if (rs.next()) {
				dataFound = true;
				
				o.setcMoneOpe(rs.getString("cMoneOpe"));
				o.setdCondTiCam(rs.getShort("dCondTiCam"));
				o.setdDesCondAnt(rs.getString("dDesCondAnt"));
				o.setdDesMoneOpe(rs.getString("dDesMoneOpe"));
				o.setdDesTImp(rs.getString("dDesTImp"));
				o.setdDesTipTra(rs.getString("dDesTipTra"));
				o.setdTiCam(new BigDecimal(rs.getDouble("dTiCam")));
				o.setdDesCondAnt(rs.getString("dDesCondAnt"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTImp(rs.getShort("iTImp"));
				o.setiTipTra(rs.getShort("iTipTra"));
				//
				//System.out.println("TmpFactuDE_D1: " + rs.getString("iTipTra"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	/**
	* 
	* @param idMov
	* @param conn
	* @return
	*/
	public static TmpFactuDE_D2 getgEmis ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dRucEm, x.dDVEmi, x.iTipCont,");
			buffer.append(" x.cTipReg, x.dNomEmi, x.dNomFanEmi, x.dDirEmi,");
			buffer.append(" x.dNumCas, x.dCompDir1, x.dCompDir2, x.cDepEmi,");
			buffer.append(" x.dDesDepEmi, x.cDisEmi, x.dDesDisEmi, x.cCiuEmi,");
			buffer.append(" x.dDesCiuEmi, x.dTelEmi, x.dEmailE, x.dDenSuc");
			buffer.append(" from tmpFactuDE_D2 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_D2 o = new TmpFactuDE_D2();
			if (rs.next()) {
				dataFound = true;
				o.setcCiuEmi(rs.getShort("cCiuEmi"));
				o.setcDepEmi(rs.getShort("cDepEmi"));
				o.setcDisEmi(rs.getShort("cDisEmi"));
				o.setcTipReg(rs.getShort("cTipReg"));
				o.setdCompDir1(rs.getString("dCompDir1"));
				o.setdCompDir2(rs.getString("dCompDir2"));
				o.setdDenSuc(rs.getString("dDenSuc"));
				o.setdDesCiuEmi(rs.getString("dDesCiuEmi"));
				o.setdDesDepEmi(rs.getString("dDesDepEmi"));
				o.setdDesDisEmi(rs.getString("dDesDisEmi"));
				o.setdDirEmi(rs.getString("dDirEmi"));
				o.setdDVEmi(rs.getShort("dDVEmi"));
				o.setdEmailE(rs.getString("dEmailE").trim());
				o.setdNomEmi(rs.getString("dNomEmi"));
				o.setdNomFanEmi(rs.getString("dNomFanEmi"));
				o.setdNumCas(rs.getString("dNumCas"));
				o.setdRucEm(rs.getString("dRucEm"));
				o.setdTelEmi(rs.getString("dTelEmi"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTipCont(rs.getShort("iTipCont"));				
				// cargar los elementos contenidos por este elemento
				ArrayList<TmpFactuDE_D21> actList = new ArrayList<TmpFactuDE_D21>();
				actList = FacturaElectronicaDAO.getgActEco(idMov, conn);
				o.setEconActivList(actList);
				TmpFactuDE_D22 rsp = new TmpFactuDE_D22();
				rsp = FacturaElectronicaDAO.getgRespDE(idMov, conn);
				o.setgRespDE(rsp);
				//
				//System.out.println("TmpFactuDE_D2: " + rs.getString("dNomEmi"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	public static ArrayList<TmpFactuDE_D21> getgActEco ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.cActEco, x.dDesActEco");
			buffer.append(" from tmpFactuDE_D21 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_D21> lst = new ArrayList<TmpFactuDE_D21>();
			while (rs.next()) {
				dataFound = true;
				TmpFactuDE_D21 o = new TmpFactuDE_D21();
				o.setcActEco(rs.getInt("cActEco"));
				o.setdDesActEco(rs.getString("dDesActEco"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				lst.add(o);
				//System.out.println("TmpFactuDE_D21: " + rs.getString("dDesActEco"));
			}
			if (dataFound == true) {
				return lst;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static TmpFactuDE_D22 getgRespDE ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iTipIDRespDE, x.dDTipIDRespDE, x.dNumIDRespDE,");
			buffer.append(" x.dNomRespDE, x.dCarRespDE");
			buffer.append(" from tmpFactuDE_D22 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_D22 o = new TmpFactuDE_D22();
			if (rs.next()) {
				dataFound = true;
				
				o.setdCarRespDE(rs.getString("dCarRespDE"));
				o.setdDTipIDRespDE(rs.getString("dDTipIDRespDE"));
				o.setdNomRespDE(rs.getString("dNomRespDE"));
				o.setdNumIDRespDE(rs.getString("dNumIDRespDE"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTipIDRespDE(rs.getShort("iTipIDRespDE"));				
				//
				//System.out.println("TmpFactuDE_D22: " + rs.getString("dNomRespDE"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	*/
	public static TmpFactuDE_D3 getgDatRec ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.iNatRec, x.iTiOpe, x.cPaisRec, x.dDesPaisRe,");
			buffer.append(" x.iTiContRec, x.dRucRec, x.dDVRec, x.iTipIDRec,");
			buffer.append(" x.dDTipIDRec, x.dNumIDRec, x.dNomRec, x.dNomFanRec,");
			buffer.append(" x.dDirRec, x.dNumCasRec, x.cDepRec, x.dDesDepRec,");
			buffer.append(" x.cDisRec, x.dDesDisRec, x.cCiuRec, x.dDesCiuRec,");
			buffer.append(" x.dTelRec, x.dCelRec, x.dEmailRec, x.dCodCliente,");
			buffer.append(" x.idConfig");

			buffer.append(" from tmpFactuDE_D3 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_D3 o = new TmpFactuDE_D3();
			if (rs.next()) {
				dataFound = true;
				
				o.setcCiuRec(rs.getInt("cCiuRec"));
				o.setcDepRec(rs.getShort("cDepRec"));
				o.setcDisRec(rs.getShort("cDisRec"));
				o.setcPaisRec(rs.getString("cPaisRec"));
				o.setdCelRec(rs.getString("dCelRec"));
				o.setdCodCliente(rs.getString("dCodCliente"));
				o.setdDesCiuRec(rs.getString("dDesCiuRec"));
				o.setdDesDepRec(rs.getString("dDesDepRec"));
				o.setdDesDisRec(rs.getString("dDesDisRec"));
				o.setdDesPaisRe(rs.getString("dDesPaisRe"));
				o.setdDirRec(rs.getString("dDirRec"));
				o.setdDTipIDRec(rs.getString("dDTipIDRec"));
				o.setdDVRec(rs.getShort("dDVRec"));
				o.setdEmailRec(rs.getString("dEmailRec"));
				o.setdNomFanRec(rs.getString("dNomFanRec"));
				o.setdNomRec(rs.getString("dNomRec"));
				o.setdNumCasRec(rs.getInt("dNumCasRec"));
				o.setdNumIDRec(rs.getString("dNumIDRec"));
				o.setdRucRec(rs.getString("dRucRec"));
				o.setdTelRec(rs.getString("dTelRec"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiNatRec(rs.getShort("iNatRec"));
				o.setiTiContRec(rs.getShort("iTiContRec"));
				o.setiTiOpe(rs.getShort("iTiOpe"));
				o.setiTipIDRec(rs.getShort("iTipIDRec"));				
				//
				//System.out.println("TmpFactuDE_D3: " + rs.getString("dNumIDRec") + " - " + rs.getString("dNomRec"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	
	/**
	* 
	* @param idMov
	* @param conn
	* @return
	*/
	public static TmpFactuDE_E getgCamFE ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dDesIndPres, x.dFecEmNR, x.iIndPres");
			buffer.append(" from tmpFactuDE_E x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E o = new TmpFactuDE_E();
			if (rs.next()) {
				dataFound = true;
				o.setdDesIndPres(rs.getString("dDesIndPres") );
				o.setdFecEmNR(rs.getString("dFecEmNR"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiIndPres(rs.getShort("iIndPres"));				
				//
				//System.out.println("TmpFactuDE_E: " + rs.getString("dDesIndPres"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
		
	public static TmpFactuDE_E1 getgCompPub ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dModCont, x.dEntCont, x.dAnoCont,");
			buffer.append(" x.dSecCont, x.dFeCodCont");
			buffer.append(" from tmpFactuDE_D x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E1 o = new TmpFactuDE_E1();
			if (rs.next()) {
				dataFound = true;
				o.setdAnoCont(rs.getShort("dAnoCont"));
				o.setdEntCont(rs.getInt("dEntCont"));
				o.setdFeCodCont(rs.getString("dFeCodCont"));
				o.setdModCont(rs.getString("dModCont"));
				o.setdSecCont(rs.getInt("dSecCont"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				//System.out.println("TmpFactuDE_E1: " + rs.getString("dFeCodCont"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static TmpFactuDE_E7 getgCamCond ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dDCondOpe, x.iCondOpe");
			buffer.append(" from tmpFactuDE_E7 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E7 o = new TmpFactuDE_E7();
			if (rs.next()) {
				dataFound = true;
				o.setdDCondOpe(rs.getString("dDCondOpe"));
				o.setiCondOpe(rs.getShort("iCondOpe"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				if (rs.getShort("iCondOpe") == 1) {
				    // obtener la lista de elementos gPaConEIni ( gPagTarCD y gPagCheq )
				    ArrayList<TmpFactuDE_E71> lst = new ArrayList<TmpFactuDE_E71>();
				    lst = FacturaElectronicaDAO.getgPaConEIni ( idMov, conn);
				    o.setTrmList(lst);
				}
				if (rs.getShort("iCondOpe") == 2) {
					TmpFactuDE_E72 cr = FacturaElectronicaDAO.getgPagCred(idMov, conn);
					o.setgPagCred(cr);
				}
				//
				//System.out.println("TmpFactuDE_E7: " + rs.getShort("iCondOpe") + " - " + rs.getString("dDCondOpe"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static ArrayList<TmpFactuDE_E71> getgPaConEIni ( int idMov, Connection conn ) 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.cMoneTiPag, x.dDesTiPag, x.dDMoneTiPag,");
			buffer.append(" x.dMonTiPag, x.iTiPago, x.dTiCamTiPag");
			buffer.append(" from tmpFactuDE_E71 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de medios de pago
			ArrayList<TmpFactuDE_E71> lst = new ArrayList<TmpFactuDE_E71>();
			while (rs.next()) {
				dataFound = true;
				TmpFactuDE_E71 o = new TmpFactuDE_E71();
				o.setcMoneTiPag(rs.getString("cMoneTiPag"));
				o.setdDesTiPag(rs.getString("dDesTiPag"));
				o.setdDMoneTiPag(rs.getString("dDMoneTiPag"));
				o.setdMonTiPag(rs.getDouble("dMonTiPag"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiTiPago(rs.getShort("iTiPago"));
				o.setdTiCamTiPag(rs.getDouble("dTiCamTiPag"));
				// obtener lo datos de la tarjetas o el cheque
				o.setgPagTarCD(null);
				o.setgPagCheq(null);
				if (rs.getShort("iTiPago") == 3 | rs.getShort("iTiPago") == 4) {
					TmpFactuDE_E711 tdc = FacturaElectronicaDAO.getgPagTarCD(idMov, conn);
					o.setgPagTarCD(tdc);
				}
				if (rs.getShort("iTiPago") == 2) {
					TmpFactuDE_E712 chq = FacturaElectronicaDAO.getgPagCheq(idMov, conn);
					o.setgPagCheq(chq);
				}
				lst.add(o);
				//
				//System.out.println("TmpFactuDE_E71: " + rs.getShort("iTiPago") + " - " + rs.getString("dDesTiPag"));				
			}
			if (dataFound == true) {
				return lst;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	public static TmpFactuDE_E711 getgPagTarCD ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dCodAuOpe, x.dDesDenTarj, x.dDVProTar,");
			buffer.append(" x.dNomTit, x.dNumTarj, x.dRSProTar, x.dRUCProTar,");
			buffer.append(" x.iDenTarj, iForProPa");
			buffer.append(" from tmpFactuDE_E711 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E711 o = new TmpFactuDE_E711();
			if (rs.next()) {
				dataFound = true;
				o.setdCodAuOpe(rs.getInt("dCodAuOpe"));
				o.setdDesDenTarj(rs.getString("dDesDenTarj"));
				o.setdDVProTar(rs.getShort("dDVProTar"));
				o.setdNomTit(rs.getString("dNomTit"));
				o.setdNumTarj(rs.getShort("dNumTarj"));
				o.setdRSProTar(rs.getString("dRSProTar"));
				o.setdRUCProTar(rs.getString("dRUCProTar"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setiDenTarj(rs.getShort("iDenTarj"));
				o.setIdMov(idMov);
				o.setiForProPa(rs.getShort("iForProPa"));
				//
				//System.out.println("TmpFactuDE_E711: " + rs.getShort("iForProPa") + " - " + rs.getString("dNumTarj"));
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static TmpFactuDE_E712 getgPagCheq ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dBcoEmi, x.dNumCheq");
			buffer.append(" from tmpFactuDE_E712 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E712 o = new TmpFactuDE_E712();
			if (rs.next()) {
				dataFound = true;
				o.setdBcoEmi(rs.getString("dBcoEmi"));
				o.setdNumCheq(rs.getString("dNumCheq"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				//System.out.println("TmpFactuDE_E712: " + rs.getString("dBcoEmi") + " - " + rs.getString("dNumCheq"));				
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static TmpFactuDE_E72 getgPagCred ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dCuotas, x.dDCondCred, x.dMonEnt,");
			buffer.append(" x.dPlazoCre, x.iCondCred");
			buffer.append(" from tmpFactuDE_E72 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E72 o = new TmpFactuDE_E72();
			if (rs.next()) {
				dataFound = true;
				o.setdCuotas(rs.getShort("dCuotas"));
				o.setdDCondCred(rs.getString("dDCondCred"));
				o.setdMonEnt(new BigDecimal(rs.getDouble("dMonEnt")));
				o.setdPlazoCre(rs.getString("dPlazoCre"));
				o.setiCondCred(rs.getShort("iCondCred"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// obtener la lista de cuotas
				ArrayList<TmpFactuDE_E721> instsLst = FacturaElectronicaDAO.getgCuotas(idMov, conn);
				o.setInstsList(instsLst);
				//System.out.println("TmpFactuDE_E72: " + rs.getShort("dCuotas"));				
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	public static ArrayList<TmpFactuDE_E721> getgCuotas ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.cMoneCuo, x.dDMoneCuo, x.dMonCuota,");
			buffer.append(" x.dVencCuo");
			buffer.append(" from tmpFactuDE_E721 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_E721> lst = new ArrayList<TmpFactuDE_E721>();
			while (rs.next()) {
				dataFound = true;
				TmpFactuDE_E721 o = new TmpFactuDE_E721();
				o.setcMoneCuo(rs.getString("cMoneCuo"));
				o.setdDMoneCuo(rs.getString("dDMoneCuo"));
				o.setdMonCuota( Precision.round(rs.getDouble("dMonCuota"),4) );
				o.setdVencCuo(rs.getString("dVencCuo"));				
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				lst.add(o);
				
				System.out.println(FacturaElectronicaDAO.class.getName()+"  o.setdMonCuota : "+o.getdMonCuota());
			}
			if (dataFound == true) {
				return lst;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static ArrayList<TmpFactuDE_E8> getgCamItem ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iddet ,x.cPaisOrig, x.cRelMerc, x.cUniMed,");
			buffer.append(" x.dCanQuiMer, x.dCantProSer, x.dCDCAnticipo, x.dCodInt,");
			buffer.append(" x.dDesPaisOrig, x.dDesProSer, x.dDesRelMerc, x.dDesUniMed,");
			buffer.append(" x.dDncpE, x.dDncpG, x.dGtin, x.dGtinPq,");
			buffer.append(" x.dInfItem, x.dNCM, x.dParAranc, x.dPorQuiMer");
			buffer.append(" from tmpFactuDE_E8 x");
			buffer.append(" where x.idMov = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_E8> lst = new ArrayList<TmpFactuDE_E8>();
			
			while (rs.next()) 
			{
				dataFound = true;
				TmpFactuDE_E8 o = new TmpFactuDE_E8();
				o.setcPaisOrig(rs.getString("cPaisOrig"));
				o.setcRelMerc(rs.getShort("cRelMerc"));
				o.setcUniMed(rs.getShort("cUniMed"));
				o.setdCanQuiMer(new BigDecimal(rs.getDouble("dCanQuiMer")));
				o.setdCantProSer(new BigDecimal(rs.getDouble("dCantProSer")));
				o.setdCDCAnticipo(rs.getString("dCDCAnticipo"));
				o.setdCodInt(rs.getString("dCodInt"));
				o.setdDesPaisOrig(rs.getString("dDesPaisOrig"));
				o.setdDesProSer(rs.getString("dDesProSer"));
				o.setdDesRelMerc(rs.getString("dDesRelMerc"));
				o.setdDesUniMed(rs.getString("dDesUniMed"));
				o.setdDncpE(rs.getString("dDncpE"));
				o.setdDncpG(rs.getString("dDncpG"));
				o.setdGtin(rs.getLong("dGtin"));
				o.setdGtinPq(rs.getLong("dGtinPq"));
				o.setdInfItem(rs.getString("dInfItem"));
				o.setdNCM(rs.getInt("dNCM"));
				o.setdParAranc(rs.getShort("dParAranc"));
				o.setdPorQuiMer(new BigDecimal(rs.getDouble("dPorQuiMer")));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// obtener el elemento correspondiente a los valores del item
				System.out.println("IDEDET :      "+rs.getInt("iddet"));
				TmpFactuDE_E81 valItem = FacturaElectronicaDAO.getgValorItem(idMov, rs.getInt("iddet"),conn);
				System.out.println(" DESCRIPCION ITEM : "+rs.getString("dDesProSer"));
				System.out.println(" VALOR ITEM  :    "+valItem.getdPUniProSer());
				o.setgValorItem(valItem);
				// obtener el elemento correspondiente al IVA del item
				TmpFactuDE_E82 camIVA = FacturaElectronicaDAO.getgCamIVA(idMov, rs.getInt("iddet"), conn);
				o.setgCamIVA(camIVA);
				//
				lst.add(o);
				//
				//System.out.println("TmpFactuDE_E8: " + rs.getString("dCodInt") + " - " + rs.getString("dDesProSer"));				
			}
			if (dataFound == true) {
				return lst;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	/**
	* 
	* @param idMov
	* @param idDet
	* @param conn
	* @return
	*/
	public static TmpFactuDE_E81 getgValorItem ( int idMov, int idDet ,Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dPUniProSer, x.dTiCamIt, x.dTotBruOpeItem");
			buffer.append(" from tmpFactuDE_E81 x");
			buffer.append(" where x.idMov = ? and iddet = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			ps.setInt(2, idDet);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E81 o = new TmpFactuDE_E81();
			
			while (rs.next()) 
			{
				dataFound = true;
				/*System.out.println("************************************************************************************");
				System.out.println("");
				System.out.println("PRECIO UNITARIO : "+ rs.getDouble("dPUniProSer"));
				System.out.println("");
				System.out.println("************************************************************************************");*/
				o.setdPUniProSer(new BigDecimal(rs.getDouble("dPUniProSer")));
				o.setdTiCamIt(new BigDecimal(rs.getDouble("dTiCamIt")));
				o.setdTotBruOpeItem(new BigDecimal(rs.getDouble("dTotBruOpeItem")));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// obtener el elemento gValorRestaItem
				TmpFactuDE_E811 r = FacturaElectronicaDAO.getgValorRestaItem(idMov, idDet ,conn);
				o.setgValorRestaItem(r);
							
			}
			
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	public static TmpFactuDE_E82 getgCamIVA ( int idMov, int idDet,Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dBasGravIVA, x.dDesAfecIVA, x.dLiqIVAItem,");
			buffer.append(" x.dPropIVA, x.dTasaIVA, x.iAfecIVA");
			buffer.append(" from tmpFactuDE_E82 x");
			buffer.append(" where x.idMov = ? and iddet = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			ps.setInt(2, idDet);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E82 o = new TmpFactuDE_E82();
			
			while (rs.next()) 
			{
				dataFound = true;
				System.out.println("new: " + new BigDecimal(rs.getDouble("dBasGravIVA")));
				System.out.println("valueOf: " + BigDecimal.valueOf(rs.getDouble("dBasGravIVA")));
				o.setdBasGravIVA(new BigDecimal(rs.getDouble("dBasGravIVA")));
				o.setdDesAfecIVA(rs.getString("dDesAfecIVA"));
				o.setdLiqIVAItem(new BigDecimal(rs.getDouble("dLiqIVAItem")));
				System.out.println("propiva new: " + new BigDecimal(rs.getDouble("dPropIVA")));
				System.out.println("propiva valueOf: " + BigDecimal.valueOf(rs.getDouble("dPropIVA")));
				o.setdPropIVA(new BigDecimal(rs.getDouble("dPropIVA")));
				o.setdTasaIVA(rs.getShort("dTasaIVA"));
				o.setiAfecIVA(rs.getShort("iAfecIVA"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				//System.out.println("TmpFactuDE_E82: " + rs.getShort("iAfecIVA"));				
			}
			
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}

	public static TmpFactuDE_E811 getgValorRestaItem ( int idMov, int idDet ,Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dAntGloPreUniIt, x.dAntPreUniIt, x.dDescGloItem,");
			buffer.append(" x.dDescItem, x.dPorcDesIt, x.dTotOpeGs, x.dTotOpeItem");
			buffer.append(" from tmpFactuDE_E811 x");
			buffer.append(" where x.idMov = ? and iddet = ? and tipo=1");
			//
			ps = conn.prepareStatement(buffer.toString()); 
			ps.setInt(1, idMov);
			ps.setInt(2, idDet);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E811 o = new TmpFactuDE_E811();
			while (rs.next()) 
			{
				dataFound = true;
				o.setdAntGloPreUniIt(new BigDecimal(rs.getDouble("dAntGloPreUniIt")));
				o.setdAntPreUniIt(new BigDecimal(rs.getDouble("dAntPreUniIt")));
				o.setdDescGloItem(new BigDecimal(rs.getDouble("dDescGloItem")));
				o.setdDescItem(new BigDecimal(rs.getDouble("dDescItem")));
				o.setdPorcDesIt(new BigDecimal(rs.getDouble("dPorcDesIt")));
				o.setdTotOpeGs(new BigDecimal(rs.getDouble("dTotOpeGs")));
				o.setdTotOpeItem(new BigDecimal(rs.getDouble("dTotOpeItem")));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				System.out.println("TmpFactuDE_E811: " + rs.getDouble("dDescItem"));				
			}
			if (dataFound == true) {
				return o;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	
	private static String DatetoString(java.util.Date toDate) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		return dateFormat.format(toDate);
	}

}
