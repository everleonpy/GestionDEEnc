package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import nider.TmpFactuDE_A;
import nider.TmpFactuDE_B;
import nider.TmpFactuDE_C;
import nider.TmpFactuDE_D;
import nider.TmpFactuDE_D1;
import nider.TmpFactuDE_D2;
import nider.TmpFactuDE_D21;
import nider.TmpFactuDE_D22;
import nider.TmpFactuDE_D3;
import nider.TmpFactuDE_E0;
import nider.TmpFactuDE_E5;
import nider.TmpFactuDE_E8;
import nider.TmpFactuDE_E81;
import nider.TmpFactuDE_E811;
import nider.TmpFactuDE_E82;
import nider.TmpFactuDE_H;
import util.DateTools;
import util.UtilPOS;

public class NotaCrElectronicaDAO {
	
	public static ArrayList<TmpFactuDE_A> getDEList ( java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;  
		int idMov = 0;
		int rowsCounter = 0;
		int index = 0;
		// el valor de este atributo ira incrementando a medida que se vayan cargando
		// transacciones al lote hasta llegar a "maxTrxQty"
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
			
			// datos de la cabecera de la transaccion 
			/* Original**  buffer.append("select x.idConfig, x.idMov, x.dVerFor, x.Id,");
			buffer.append(" x.dDVId, x.dSisFact, x.dFecFirma");
			buffer.append(" from tmpFactuDE_A x");
			*/
			buffer.append("select a.idConfig,a.idMov,a.dVerFor,a.Id,a.dDVId,a.dSisFact,a.dFecFirma ");
			buffer.append("from tmpFactuDE_A  a inner join tmpFactuDE_C c  on a.idMov=c.idMov ");
			buffer.append("where c.iTiDE=5 ");
			
		    //buffer.append(" where not exists ( select 1");
		    //buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    //buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    //buffer.append(" and b.TRANSACTION_ID = h.IDENTIFIER )");
			
			buffer.append(" and a.fechaFactura >= ?");
			buffer.append(" and a.fechaFactura <= ?");
			buffer.append(" order by a.idMov");
			//
			ps = conn.prepareStatement(buffer.toString());
			index++;
			//ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			ps.setString(index, DateTools.getString(trxDate, "yyyy-MM-dd"));
			index++;
			//ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			ps.setString(index, DateTools.getString(trxDate, "yyyy-MM-dd"));
			System.out.println("Consultando fechas desde: " + fromDate + " hasta: " + toDate);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_A> deList = new ArrayList<TmpFactuDE_A>();
			while (rs.next()) {
				dataFound = true;
				idMov = rs.getInt("idMov");
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
				b = NotaCrElectronicaDAO.getgOpeDE(idMov, conn);
				o.setgOpeDe(b);
				// obtener la instancia de gTimb
				TmpFactuDE_C c = new TmpFactuDE_C();
				c = NotaCrElectronicaDAO.getgTimb(idMov, conn);
				o.setgTimb(c);
				// obtener la instancia de gDatGralOpe
				TmpFactuDE_D g = new TmpFactuDE_D();
				g = NotaCrElectronicaDAO.getgDatGralOpe(idMov, conn); 
				o.setgDatGralOpe(g);
				// obtener la instancia de gDTipDE
				TmpFactuDE_E0 td = new TmpFactuDE_E0();
				TmpFactuDE_E5 ne = NotaCrElectronicaDAO.getgCamNCDE(idMov, conn);
				td.setgCamNCDE(ne);
				// obtener la lista de items de la operacion
				ArrayList<TmpFactuDE_E8> items = NotaCrElectronicaDAO.getgCamItem(idMov, conn);
				System.out.println("Nota Credito: " + idMov + " Items: " + items.size());
				td.setItemsList(items);
				//
				o.setgTipDE(td);
				// obtener la lista de documentos asociados a la operacion
				ArrayList<TmpFactuDE_H> asoc = NotaCrElectronicaDAO.getgCamDEAsoc(idMov, conn);
				o.setGcamDEAsoc(asoc);
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
	
	public static TmpFactuDE_B getgOpeDE ( int idMov, Connection conn ) {
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
			buffer.append(" where x.idMov = ? and x.tipo = 5 ");
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
				gOpeCom = NotaCrElectronicaDAO.getgOpeCom(idMov, conn);
				o.setgOpeCom(gOpeCom);
				//
				TmpFactuDE_D2 gEmis = new TmpFactuDE_D2();
				gEmis = NotaCrElectronicaDAO.getgEmis(idMov, conn);
				o.setgEmis(gEmis);
				//
				TmpFactuDE_D3 gDatRec = new TmpFactuDE_D3();
				gDatRec = NotaCrElectronicaDAO.getgDatRec(idMov, conn);
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
				actList = NotaCrElectronicaDAO.getgActEco(idMov, conn);
				o.setEconActivList(actList);
				TmpFactuDE_D22 rsp = new TmpFactuDE_D22();
				rsp = NotaCrElectronicaDAO.getgRespDE(idMov, conn);
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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
			buffer.append(" where x.idMov = ? and x.tipo = 5");
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

	public static TmpFactuDE_E5 getgCamNCDE ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iMotEmi, x.dDesMotEmi");
			buffer.append(" from tmpFactuDE_E5 x");
			buffer.append(" where x.idMov = ? and x.tipo = 5");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E5 o = new TmpFactuDE_E5();
			if (rs.next()) {
				dataFound = true;
				o.setdDesMotEmi(rs.getString("dDesMotEmi"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiMotEmi(rs.getShort("iMotEmi"));
				//
				//System.out.println("TmpFactuDE_E: " + rs.getString("dDesMotEmi"));
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
		
	public static ArrayList<TmpFactuDE_E8> getgCamItem ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iddet, x.cPaisOrig, x.cRelMerc, x.cUniMed,");
			buffer.append(" x.dCanQuiMer, x.dCantProSer, x.dCDCAnticipo, x.dCodInt,");
			buffer.append(" x.dDesPaisOrig, x.dDesProSer, x.dDesRelMerc, x.dDesUniMed,");
			buffer.append(" x.dDncpE, x.dDncpG, x.dGtin, x.dGtinPq,");
			buffer.append(" x.dInfItem, x.dNCM, x.dParAranc, x.dPorQuiMer");
			buffer.append(" from tmpFactuDE_E8 x");
			buffer.append(" where x.idMov = ? and x.tipo = 5 ");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_E8> lst = new ArrayList<TmpFactuDE_E8>();
			while (rs.next()) {
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
				TmpFactuDE_E81 valItem = NotaCrElectronicaDAO.getgValorItem(idMov, rs.getInt("iddet"), conn);
				o.setgValorItem(valItem);
				// obtener el elemento correspondiente al IVA del item
				TmpFactuDE_E82 camIVA = NotaCrElectronicaDAO.getgCamIVA(idMov, rs.getInt("iddet"), conn);
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
	
	public static TmpFactuDE_E81 getgValorItem ( int idMov, int idDet,Connection conn ) {
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
			buffer.append(" where x.idMov = ? and x.tipo = 5 and iddet = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			ps.setInt(2, idDet);
			
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E81 o = new TmpFactuDE_E81();
			if (rs.next()) {
				dataFound = true;
				o.setdPUniProSer(new BigDecimal(rs.getDouble("dPUniProSer")));
				o.setdTiCamIt(new BigDecimal(rs.getDouble("dTiCamIt")));
				o.setdTotBruOpeItem(new BigDecimal(rs.getDouble("dTotBruOpeItem")));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				// obtener el elemento gValorRestaItem
				TmpFactuDE_E811 r = NotaCrElectronicaDAO.getgValorRestaItem(idMov, conn);
				o.setgValorRestaItem(r);
				//System.out.println("TmpFactuDE_E81: " + rs.getDouble("dPUniProSer"));				
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
	
	public static TmpFactuDE_E82 getgCamIVA ( int idMov, int idDet, Connection conn ) {
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
			buffer.append(" where x.idMov = ? and x.tipo = 5 and iddet = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			ps.setInt(2, idDet);
			
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E82 o = new TmpFactuDE_E82();
			if (rs.next()) {
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

	public static TmpFactuDE_E811 getgValorRestaItem ( int idMov, Connection conn ) {
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
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E811 o = new TmpFactuDE_E811();
			if (rs.next()) {
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
	
	public static ArrayList<TmpFactuDE_H> getgCamDEAsoc ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iTipDocAso, x.dDesTipDocAso, x.dCdCDERef,");
			buffer.append(" x.dNTimDI, x.dEstDocAso, x.dPExpDocAso, x.dNumDocAso,");
			buffer.append(" x.iTipoDocAso, x.dDTipoDocAso, x.dFecEmiDI, x.dNumComRet,");
			buffer.append(" x.dNumResCF, x.iTipCons, x.dDesTipCons, x.dNumCons,");
			buffer.append(" x.dNumControl");
			buffer.append(" from tmpFactuDE_H x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_H> l = new ArrayList<TmpFactuDE_H>();
			if (rs.next()) {
				dataFound = true;
				TmpFactuDE_H o = new TmpFactuDE_H();				
				o.setdCdCDERef(rs.getString("dCdCDERef"));
				o.setdDesTipCons(rs.getString("dDesTipCons"));
				o.setdDesTipDocAso(rs.getString("dDesTipDocAso"));
				o.setdDTipoDocAso(rs.getString("dDTipoDocAso"));
				o.setdEstDocAso(rs.getString("dEstDocAso"));
				o.setdFecEmiDI(rs.getString("dFecEmiDI"));
				o.setdNTimDI(rs.getString("dNTimDI"));
				o.setdNumComRet(rs.getString("dNumComRet"));
				o.setdNumCons(rs.getShort("dNumCons"));
				o.setdNumControl(rs.getString("dNumControl"));
				o.setdNumDocAso(rs.getString("dNumDocAso"));
				o.setdNumResCF(rs.getString("dNumResCF"));
				o.setdPExpDocAso(rs.getString("dPExpDocAso"));
				o.setiTipCons(rs.getShort("iTipCons"));
				o.setiTipDocAso(rs.getShort("iTipDocAso"));
				o.setiTipoDocAso(rs.getShort("iTipoDocAso"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				l.add(o);
				//System.out.println("TmpFactuDE_B: " + rs.getString("dNumDocAso"));
			}
			if (dataFound == true) {
				return l;
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

}
