package business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import dao.Util;
import nider.TmpFactuDE_A;
import nider.TmpFactuDE_B;
import nider.TmpFactuDE_C;
import nider.TmpFactuDE_D;
import nider.TmpFactuDE_D2;
import nider.TmpFactuDE_D21;
import nider.TmpFactuDE_D22;
import nider.TmpFactuDE_D3;
import nider.TmpFactuDE_E;
import nider.TmpFactuDE_E0;
import nider.TmpFactuDE_E1;
import nider.TmpFactuDE_E10;
import nider.TmpFactuDE_E101;
import nider.TmpFactuDE_E102;
import nider.TmpFactuDE_E103;
import nider.TmpFactuDE_E104;
import nider.TmpFactuDE_E6;
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
import nider.TmpFactuDE_G1;
import util.UtilPOS;

public class RemisionElectronicaDAO {
	
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
			conn =  Util.getConnection(); //Util.getConnection(); 
			if (conn == null) {
				return null;
			}

			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// configuracion de la fecha
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.idMov, x.dVerFor, x.Id,");
			buffer.append(" x.dDVId, x.dSisFact, x.dFecFirma");
			buffer.append(" from tmpFactuDE_A x");

		    //buffer.append(" where not exists ( select 1");
		    //buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    //buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    //buffer.append(" and b.TRANSACTION_ID = h.IDENTIFIER )");
			
			buffer.append(" where x.fechaFactura < ?");
			buffer.append(" and x.fechaFactura >= ?");
			buffer.append(" order by x.idMov");
			//
			ps = conn.prepareStatement(buffer.toString());
			index++;
			ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			index++;
			ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
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
				b = RemisionElectronicaDAO.getgOpeDE(idMov, conn);
				o.setgOpeDe(b);
				// obtener la instancia de gTimb
				TmpFactuDE_C c = new TmpFactuDE_C();
				c = RemisionElectronicaDAO.getgTimb(idMov, conn);
				o.setgTimb(c);
				// obtener la instancia de gDatGralOpe
				TmpFactuDE_D g = new TmpFactuDE_D();
				g = RemisionElectronicaDAO.getgDatGralOpe(idMov, conn);
				o.setgDatGralOpe(g);
				// obtener la instancia de gDTipDE
				TmpFactuDE_E0 td = new TmpFactuDE_E0();
				TmpFactuDE_E6 re = RemisionElectronicaDAO.getgCamNRE(idMov, conn);
				td.setgCamNRE(re);
				// obtener la lista de items de la operacion
				ArrayList<TmpFactuDE_E8> items = RemisionElectronicaDAO.getgCamItem(idMov, conn);
				System.out.println("Factura: " + idMov + " Items: " + items.size());
				td.setItemsList(items);
				// obtener los datos especificos del transporte de mercaderias
				TmpFactuDE_E10 gTransp = RemisionElectronicaDAO.getgTransp(idMov, conn);
				td.setgTransp(gTransp);
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
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_B o = new TmpFactuDE_B();
			if (rs.next()) {
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
			buffer.append(" where x.idMov = ?");
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
			buffer.append(" where x.idMov = ?");
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
				TmpFactuDE_D2 gEmis = new TmpFactuDE_D2();
				gEmis = RemisionElectronicaDAO.getgEmis(idMov, conn);
				o.setgEmis(gEmis);
				//
				TmpFactuDE_D3 gDatRec = new TmpFactuDE_D3();
				gDatRec = RemisionElectronicaDAO.getgDatRec(idMov, conn);
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
			buffer.append(" where x.idMov = ?");
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
				actList = RemisionElectronicaDAO.getgActEco(idMov, conn);
				o.setEconActivList(actList);
				TmpFactuDE_D22 rsp = new TmpFactuDE_D22();
				rsp = RemisionElectronicaDAO.getgRespDE(idMov, conn);
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
			buffer.append(" where x.idMov = ?");
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
			buffer.append(" where x.idMov = ?");
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
			buffer.append(" where x.idMov = ?");
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

	public static TmpFactuDE_E6 getgCamNRE ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();
			
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iMotEmiNR, x.dDesMotEmiNR, x.iRespEmiNR,");
			buffer.append(" x.dDesRespEmiNR, x.dKmR, x.dFecEm");
			buffer.append(" from tmpFactuDE_E6 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E6 o = new TmpFactuDE_E6();
			if (rs.next()) {
				dataFound = true;
				o.setdDesMotEmiNR(rs.getString("dDesMotEmiNR"));
				o.setdDesRespEmiNR(rs.getString("dDesRespEmiNR"));
				o.setdFecEm(rs.getString("dFecEm"));
				o.setdKmR(rs.getInt("dKmR"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiMotEmiNR(rs.getShort("iMotEmiNR"));
				o.setiRespEmiNR(rs.getShort("iRespEmiNR"));				
				//
				//System.out.println("TmpFactuDE_E6: " + rs.getString("dDesMotEmiNR"));
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
			buffer.append("select x.idConfig, x.cPaisOrig, x.cRelMerc, x.cUniMed,");
			buffer.append(" x.dCanQuiMer, x.dCantProSer, x.dCDCAnticipo, x.dCodInt,");
			buffer.append(" x.dDesPaisOrig, x.dDesProSer, x.dDesRelMerc, x.dDesUniMed,");
			buffer.append(" x.dDncpE, x.dDncpG, x.dGtin, x.dGtinPq,");
			buffer.append(" x.dInfItem, x.dNCM, x.dParAranc, x.dPorQuiMer");
			buffer.append(" from tmpFactuDE_E8 x");
			buffer.append(" where x.idMov = ?");
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
	
	public static TmpFactuDE_E81 getgValorItem ( int idMov, Connection conn ) {
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
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
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
	
	public static TmpFactuDE_E10 getgTransp ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();			
			
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iTipTrans, x.dDesTipTrans, x.iModTrans,");
			buffer.append(" x.dDesModTrans, x.iRespFlete, x.cCondNeg, x.dNuManif,");
			buffer.append(" x.dNuDespImp, x.dIniTras, x.dFinTras, x.cPaisDest,");
			buffer.append(" x.dDesPaisDest");
			buffer.append(" from TmpFactuDE_E10 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E10 o = new TmpFactuDE_E10();
			if (rs.next()) {
				dataFound = true;
				o.setcCondNeg(rs.getString("cCondNeg"));
				o.setcPaisDest(rs.getString("cPaisDest"));
				o.setdDesModTrans(rs.getString("dDesModTrans"));
				o.setdDesPaisDest(rs.getString("dDesPaisDest"));
				o.setdDesTipTrans(rs.getString("dDesTipTrans"));
				o.setdFinTras(rs.getString("dFinTras"));
				o.setdIniTras(rs.getString("dIniTras"));
				o.setdNuDespImp(rs.getString("dNuDespImp"));
				o.setdNuManif(rs.getString("dNuManif"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				o.setiModTrans(rs.getShort("iModTrans"));
				o.setiRespFlete(rs.getShort("iRespFlete"));
				o.setiTipTrans(rs.getShort("iTipTrans"));				
				//
				//System.out.println("TmpFactuDE_E10: " + rs.getString("dDesModTrans") + " - " + rs.getString("dDesTipTrans"));	
				o.setgCamSal(RemisionElectronicaDAO.getgCamSal(idMov, conn));
				o.setgCamEnt(RemisionElectronicaDAO.getgCamEnt(idMov, conn));
				o.setgVehTras(RemisionElectronicaDAO.getgVehTras(idMov, conn));
				o.setgCamTrans(RemisionElectronicaDAO.getgCamTrans(idMov, conn));
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
	
	public static TmpFactuDE_E101 getgCamSal ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();			
			
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dDirLocSal, x.dComp1Sal, x.dComp2Sal,");
			buffer.append(" x.cDepSal, x.dDesDepSal, x.cDisSal, x.dDesDisSal,");
			buffer.append(" x.cCiuSal, x.dDesCiuSal, x.dTelSal");
			buffer.append(" from TmpFactuDE_E101 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E101 o = new TmpFactuDE_E101();
			if (rs.next()) {
				dataFound = true;
				o.setcCiuSal(rs.getInt("cCiuSal"));
				o.setcDepSal(rs.getShort("cDepSal"));
				o.setcDisSal(rs.getShort("cDisSal"));
				o.setdComp1Sal(rs.getString("dComp1Sal"));
				o.setdComp2Sal(rs.getString("dComp2Sal"));
				o.setdDesCiuSal(rs.getString("dDesCiuSal"));
				o.setdDesDepSal(rs.getString("dDesDepSal"));
				o.setdDesDisSal(rs.getString("dDesDisSal"));
				o.setdDirLocSal(rs.getString("dDirLocSal"));
				o.setdNumCasSal(rs.getShort("dNumCasSal"));
				o.setdTelSal(rs.getString("dTelSal"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				//System.out.println("TmpFactuDE_E101: " + rs.getString("dDirLocSal"));				
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

	public static ArrayList<TmpFactuDE_E102> getgCamEnt ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();			
			
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dDirLocEnt, x.dNumCasEnt, x.dComp1Ent,");
			buffer.append(" x.dComp2Ent, x.cDepEnt, x.dDesDepEnt, x.cDisEnt,");
			buffer.append(" x.dDesDisEnt, x.cCiuEnt, x.dDesCiuEnt, x.dTelEnt");
			buffer.append(" from TmpFactuDE_E102 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_E102> l = new ArrayList<TmpFactuDE_E102>();
			while (rs.next()) {
				dataFound = true;
				TmpFactuDE_E102 o = new TmpFactuDE_E102();
				o.setcCiuEnt(rs.getInt("cCiuEnt"));
				o.setcDepEnt(rs.getShort("cDepEnt"));
				o.setcDisEnt(rs.getShort("cDisEnt"));
				o.setdComp1Ent(rs.getString("dComp1Ent"));
				o.setdComp2Ent(rs.getString("dComp2Ent"));
				o.setdDesCiuEnt(rs.getString("dDesCiuEnt"));
				o.setdDesDepEnt(rs.getString("dDesDepEnt"));
				o.setdDesDisEnt(rs.getString("dDesDisEnt"));
				o.setdDirLocEnt(rs.getString("dDirLocEnt"));
				o.setdNumCasEnt(rs.getString("dNumCasEnt"));
				o.setdTelEnt(rs.getString("dTelEnt"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				l.add(o);
				//
				//System.out.println("TmpFactuDE_E102: " + rs.getString("dDirLocEnt"));				
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

	public static ArrayList<TmpFactuDE_E103> getgVehTras ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();			
	
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.dTiVehTras, x.dMarVeh, x.dTipIdenVeh,");
			buffer.append(" x.dNroIDVeh, x.dAdicVeh, x.dNroMatVeh, x.dNroVuelo");
			buffer.append(" from TmpFactuDE_E102 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<TmpFactuDE_E103> l = new ArrayList<TmpFactuDE_E103>();
			while (rs.next()) {
				dataFound = true;
				TmpFactuDE_E103 o = new TmpFactuDE_E103();
				o.setdAdicVeh(rs.getString("dAdicVeh"));
				o.setdMarVeh(rs.getString("dMarVeh"));
				o.setdNroIDVeh(rs.getString("dNroIDVeh"));
				o.setdNroVuelo(rs.getString("dNroVuelo"));
				o.setdTipIdenVeh(rs.getShort("dTipIdenVeh"));
				o.setdTiVehTras(rs.getString("dTiVehTras"));
				o.setdNroMatVeh(rs.getString("dNroMatVeh"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				l.add(o);
				//
				//System.out.println("TmpFactuDE_E103: " + rs.getString("dTiVehTras"));				
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

	public static TmpFactuDE_E104 getgCamTrans ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();			
	
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.iNatTrans, x.dNomTrans, x.dRucTrans,");
			buffer.append(" x.dDVTrans, x.iTipIDTrans, x.dDTipIDTrans, x.dNumIDTrans,");
			buffer.append(" x.cNacTrans, x.dDesNacTrans, x.dNumIDChof, x.dNomChof,");
			buffer.append(" x.dDomFisc, x.dDirChof, x.dNombAg, x.dRucAg,");
			buffer.append(" x.dDVAg, x.dDirAge");
			buffer.append(" from TmpFactuDE_E104 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_E104 o = new TmpFactuDE_E104();
			if (rs.next()) {
				dataFound = true;
				o.setcNacTrans(rs.getString("cNacTrans"));
				o.setdDesNacTrans(rs.getString("dDesNacTrans"));
				o.setdDirAge(rs.getString("dDirAge"));
				o.setdDirChof(rs.getString("dDirChof"));
				o.setdDomFisc(rs.getString("dDomFisc"));
				o.setdDTipIDTrans(rs.getString("dDTipIDTrans"));
				o.setdDVAg(rs.getShort("dDVAg"));
				o.setdDVTrans(rs.getShort("dDVTrans"));
				o.setdNombAg(rs.getString("dNombAg"));
				o.setdNomChof(rs.getString("dNomChof"));
				o.setdNomTrans(rs.getString("dNomTrans"));
				o.setdNumIDChof(rs.getString("dNumIDChof"));
				o.setdNumIDTrans(rs.getString("dNumIDTrans"));
				o.setdRucAg(rs.getString("dRucAg"));
				o.setdRucTrans(rs.getString("dRucTrans"));
				o.setiNatTrans(rs.getShort("iNatTrans"));
				o.setiTipIDTrans(rs.getShort("iTipIDTrans"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);
				//
				//System.out.println("TmpFactuDE_E103: " + rs.getString("dTiVehTras"));				
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

	public static TmpFactuDE_G1 getgCamCarg ( int idMov, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
		try {
			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();
		
			// datos de la cabecera de la transaccion 
			buffer.append("select x.idConfig, x.cUniMedTotVol, x.dDesUniMedTotVol, x.dTotVolMerc,");
			buffer.append(" x.cUniMedTotPes, x.dDesUniMedTotPes, x.dTotPesMerc, x.iCarCarga,");
			buffer.append(" x.dDesCarCarga");
			buffer.append(" from TmpFactuDE_G1 x");
			buffer.append(" where x.idMov = ?");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setInt(1, idMov);
			rs = ps.executeQuery();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			TmpFactuDE_G1 o = new TmpFactuDE_G1();
			if (rs.next()) {
				dataFound = true;
				o.setcUniMedTotPes(rs.getInt("cUniMedTotPes"));
				o.setcUniMedTotVol(rs.getInt("cUniMedTotVol"));
				o.setdDesCarCarga(rs.getString("dDesCarCarga"));
				o.setdDesUniMedTotPes(rs.getString("dDesUniMedTotPes"));
				o.setdDesUniMedTotVol(rs.getString("dDesUniMedTotVol"));
				o.setdTotPesMerc(rs.getDouble("dTotPesMerc"));
				o.setdTotVolMerc(rs.getDouble("dTotVolMerc"));
				o.setiCarCarga(rs.getShort("iCarCarga"));
				o.setIdConfig(rs.getInt("idConfig"));
				o.setIdMov(idMov);				
				//
				//System.out.println("TmpFactuDE_G1: " + rs.getString("dDesCarCarga"));
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
	
}
