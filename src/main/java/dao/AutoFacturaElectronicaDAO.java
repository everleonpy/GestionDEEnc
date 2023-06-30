package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposAutofacturaElectronica;
import pojo.CamposCondicionOperacion;
import pojo.CamposCuotas;
import pojo.CamposEmisorDE;
import pojo.CamposEspecificosDE;
import pojo.CamposFirmados;
import pojo.CamposGeneralesDE;
import pojo.CamposItemsOperacion;
import pojo.CamposOperacionComercial;
import pojo.CamposOperacionContado;
import pojo.CamposOperacionCredito;
import pojo.CamposOperacionDE;
import pojo.CamposReceptorDE;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;
import pojo.OrganizationData;

public class AutoFacturaElectronicaDAO {
	
	public static DocumElectronico getPayAutoInvoice ( long autoInvoiceId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat tf = new java.text.SimpleDateFormat("HH:mm:ss");
		double exchangeRate;
		short operType = 0;
		//
		DocumElectronico rDE;
		CamposFirmados DE;
		CamposOperacionDE gOpeDE;
		CamposTimbrado gTimb;
		CamposGeneralesDE gDatGralOpe;
		CamposReceptorDE gDatRec;
		CamposEmisorDE gEmis;
		CamposResponsableDE gRespDE;
		CamposOperacionComercial gOpeComer;
		CamposEspecificosDE gDtipDE;
		CamposAutofacturaElectronica gCamAE;
		CamposSubtotalesTotales gTotSub;
		OrganizationData org;
		//
		final short B2B = 1;
		final short B2C = 2;
		final short B2G = 3;
		final short B2F = 4;
        //
		final short FACTURA_ELECTRONICA = 1;
		final short FACTURA_ELEC_EXPORT = 2;
		final short FACTURA_ELEC_IMPORT = 3;
		final short AUTO_FACTURA_ELECTRONICA = 4;
		final short NOTA_CR_ELECTRONICA = 5;
		final short NOTA_DB_ELECTRONICA = 6;
		final short REMISION_ELECTRONICA = 7;
		//
		final short NORMAL = 1;
		final short CONTINGENCIA = 2;
		//
		final short NO_CONTRIBUYENTE = 1;
		final short EXTRANJERO = 2;
		//
		final short CEDULA_PARAGUAYA = 1;
		final short PASAPORTE = 2;
		final short CEDULA_EXTRANJERA = 3;
		final short CARNET_RESIDENCIA = 4;
		//
		final short PERSONA_FISICA = 1;
		final short PERSONA_JURIDICA = 2;
		final short GLOBAL = 1;
		final short POR_ITEM = 2;
		final short ANTICIPO_GLOBAL = 1;
		final short ANTICIPO_POR_ITEM = 2;
		final short SIN_ANTICIPO = 0;
		//
		final short CONTADO = 1;
		final short CREDITO = 2;
		//
		final short PLAZO = 1;
		final short CUOTA = 2;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				return null;
			}

			/*
             B2B
             Business to Business, acrónimo comúnmente utilizado para describir las operaciones entre 
             empresas.
             B2C
             Business to Consumer, acrónimo comúnmente utilizado para describir las operaciones entre 
             una empresa a un consumidor final.
             B2G
             Business to Government, acrónimo comúnmente utilizado para B2G describir las operaciones 
             entre una empresa y una entidad de gobierno.
             B2F
             Business to Foreign, acrónimo del tipo de operación para describir los servicios prestados 
             por una empresa nacional a una empresa o persona física del exterior.
			 */

			// preparar las estructuras de datos
			rDE = new DocumElectronico();

			// obrener los datos generales del emisor
			org = EmisorDocumentosDAO.getOrgData(1);

			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			buffer.append("select v.TAX_NUMBER, v.IDENTITY_TYPE, v.IDENTITY_NUMBER, v.VENDOR_ORIGIN,");
			buffer.append(" v.NAME DNOMVEN, v.ADDRESS1 DDIRVEN, v.BUILDING_NUMBER DNUMCASVEN, sta1.CODE CDEPVEN,");
			buffer.append(" cou1.CODE CDISVEN, cty1.CODE CCIUVEN, cnt1.CODE CPAISVND, v.PHONE1 DTELREC,");
			buffer.append(" v.PHONE2 DCELREC, v.E_MAIL, v.ALTERNATIVE_NAME, v.ORG_TYPE,");

			buffer.append(" r.STAMP_NO DNUMTIM, s.INVOICING_CODE DEST, p.CODE DPUNEXP, lpad(to_char(x.TX_BASE_NUMBER), 7, '0') DNUMDOC,");
			buffer.append(" 'AA' DSERIENUM, r.FROM_DATE DFEINIT,");
			buffer.append(" to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') DEFEMIDE, x.TRX_CONDITION,");
			
			buffer.append(" sta2.CODE CDEPEMI, cou2.CODE CDISEMI, cty2.CODE CCIUEMI, s.ADDRESS1 DDIREMI,");
			buffer.append(" s.BUILDING_NUMBER DNUMCAS, s.ADDRESS2 DCOMPDIR1, s.ADDRESS3 DCOMPDIR2, s.PHONE1 DTELEMI,");
			buffer.append(" s.E_MAIL DEMAILE, s.NAME DDENSUC, cnt2.CODE CPAISEMI,");

			buffer.append(" rsp.IDENTITY_TYPE USERID_TYPE, rsp.IDENTITY_NUMBER DNUMIDRESPDE, rsp.NAME DNOMRESPDE,");
			
			buffer.append(" txtyp.CODE ITIPTRA, taxtyp.CODE ITIMP, y.EB_CODE CMONEOPE, x.EXCHANGE_RATE");
			
			buffer.append(" from FND_ORGANIZATIONS o,");
			buffer.append(" FND_CURRENCIES y,");
			buffer.append(" FND_COUNTRIES cnt2,");
			buffer.append(" FND_STATES sta2,");
			buffer.append(" FND_COUNTIES cou2,");
			buffer.append(" FND_CITIES cty2,");
			buffer.append(" FND_COUNTRIES cnt1,");
			buffer.append(" FND_STATES sta1,");
			buffer.append(" FND_COUNTIES cou1,");
			buffer.append(" FND_CITIES cty1,");
			buffer.append(" FND_SITES s,");
			buffer.append(" FND_USERS rsp,");
			buffer.append(" RCV_STAMP_RECORDS r,");
			buffer.append(" PAY_VENDORS v,");
			buffer.append(" PAY_TRX_TYPES t,");
			buffer.append(" PAY_VENDOR_TRX x");

			buffer.append(" where o.IDENTIFIER = x.ORG_ID");

			buffer.append(" and cnt2.IDENTIFIER = s.COUNTRY_ID");
			buffer.append(" and sta2.IDENTIFIER = s.STATE_ID");
			buffer.append(" and cou2.IDENTIFIER = s.COUNTY_ID");
			buffer.append(" and cty2.IDENTIFIER = s.CITY_ID");

			buffer.append(" and cnt1.IDENTIFIER(+) = v.COUNTRY_ID");
			buffer.append(" and sta1.IDENTIFIER(+) = v.STATE_ID");
			buffer.append(" and cou1.IDENTIFIER(+) = v.COUNTY_ID");
			buffer.append(" and cty1.IDENTIFIER(+) = v.CITY_ID");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and rsp.NAME = x.CREATED_BY");
			buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and v.IDENTIFIER = x.VENDOR_ID");
			
			buffer.append(" and t.TRX_TYPE in ('AUTO-FACTURA')");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, autoInvoiceId);
			rs = ps.executeQuery();

			if (rs.next()) {
				dataFound = true;
				DE = new CamposFirmados();
				java.util.Date signatureDate = new java.util.Date();
				String signDate = df.format(signatureDate);
				String signTime = tf.format(signatureDate);
				DE.setdFecFirma(signDate + "T" + signTime);
				// 1=Sistema de facturación del contribuyente
				// 2=SIFEN solución gratuita
				DE.setdSisFact(new Short("1"));
				//
				gOpeDE = new CamposOperacionDE();
				gOpeDE.setiTipEmi(NORMAL);
				gOpeDE.setdDesTipEmi("Normal");
				gOpeDE.setdInfoEmi("Compras con autofacturas");
				gOpeDE.setdInfoFisc(null);
				DE.setgOpeDE(gOpeDE);
				/**
	         	 +--------------------------------------------------------------+
	         	 |  completar los datos del timbrado                            |
	         	 +--------------------------------------------------------------+
				 */
				gTimb = new CamposTimbrado();
				gTimb.setdFeIniT(rs.getDate("DFEINIT"));
				gTimb.setdNumTim(rs.getInt("DNUMTIM"));
				gTimb.setdEst(rs.getString("DEST"));
				gTimb.setdNumDoc(rs.getString("DNUMDOC"));
				gTimb.setdPunExp(rs.getString("DPUNEXP"));
				gTimb.setdSerieNum(rs.getString("DSERIENUM"));
				gTimb.setiTiDE(AUTO_FACTURA_ELECTRONICA);
				// esto solo debe ser utilizado durante la etapa de pruebas
				// ********************************************************
				// N° de Timbrado: 12560102
				// Establecimiento: 1
				// Punto de expedición: 1-2-3
				// inicio de vigencia: 11/11/2022
				gTimb.setdNumTim(12560102);
				java.util.Date stampDate = df.parse("2022-11-11");
				gTimb.setdFeIniT(stampDate);
				gTimb.setdEst("001");
				gTimb.setdPunExp("001");
				//
				DE.setgTimb(gTimb);
				/**
	         	 +--------------------------------------------------------------+
	         	 | preparar la clase raiz                                       |
	         	 +--------------------------------------------------------------+
				 */
				gDatGralOpe = new CamposGeneralesDE();
				d = sdf.parse(rs.getString("DEFEMIDE"));
				gDatGralOpe.setdFeEmiDE(d);
				/**
	           	 +--------------------------------------------------------------+
	          	 | completar los datos del emisor                               |
	         	 +--------------------------------------------------------------+
				 */
				gEmis = new CamposEmisorDE();
				// dRucEm, dDVEmi
				gEmis.setdRucEm(org.getTaxNumber());
				gEmis.setdDVEmi(String.valueOf(org.getCheckDigit()));
				// iTipCont
				gEmis.setiTipCont(org.getTaxPayerType());
				// cTipReg
				gEmis.setcTipReg(org.getRegimeType());
				// dNomEmi
				gEmis.setdNomEmi(org.getName());
				// dNomFanEmi
				gEmis.setdNomFanEmi(org.getAlternativeName());
				// dDirEmi
				gEmis.setdDirEmi(rs.getString("DDIREMI"));
				// dNumCas
				gEmis.setdNumCas(rs.getString("DNUMCAS"));
				// dCompDir1
				gEmis.setdCompDir1(rs.getString("DCOMPDIR1"));
				// dCompDir2
				gEmis.setdCompDir2(rs.getString("DCOMPDIR2"));
				// cDepEmi, dDesDepEmi
				gEmis.setcDepEmi(rs.getShort("CDEPEMI"));
				// cDisEmi, dDesDisEmi
				gEmis.setcDisEmi(rs.getShort("CDISEMI"));
				// cCiuEmi, dDesCiuEmi
				gEmis.setcCiuEmi(rs.getShort("CCIUEMI"));
				// dTelEmi
				gEmis.setdTelEmi(rs.getString("DTELEMI"));
				// dEmailE
				if (rs.getString("DEMAILE") != null) {
					gEmis.setdEmailE(rs.getString("DEMAILE"));
				} else {
					gEmis.setdEmailE(org.geteMail());
				}
				// dDenSuc
				gEmis.setdDenSuc(rs.getString("DDENSUC"));
				// gActEco
				gEmis.setgActEco(org.getActivList());
				/**
	           	 +--------------------------------------------------------------+
	        	     | completar los datos del responsable de emision               |
	        	     +--------------------------------------------------------------+
				 */
				AutoFacturasUtils f = new AutoFacturasUtils();
				gRespDE = f.datosResponsable(rs.getString("DNOMRESPDE"), rs.getString("DNUMIDRESPDE") , rs.getString("USERID_TYPE"));
				gEmis.setgRespDE(gRespDE);
				gDatGralOpe.setgEmis(gEmis);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos del receptor                             |
	         	 +--------------------------------------------------------------+
				 */
				gDatRec = f.datosReceptor ( rs.getString("DCELREC"), 
						rs.getString("DNOMVEN"), 
						rs.getString("DDIRVEN"), 
						rs.getString("IDENTITY_TYPE"), 
						rs.getString("E_MAIL"), 
						rs.getString("ALTERNATIVE_NAME"), 
						rs.getString("DNUMCASVEN"), 
						rs.getString("IDENTITY_NUMBER"), 
						rs.getString("DTELREC") );
				// iNatRec
				gDatRec.setiNatRec(NO_CONTRIBUYENTE);
				// iTiOpe
				if (rs.getString("TAX_NUMBER") != null) {
					operType = B2B;
				} else {
					operType = B2C;					
				}
				gDatRec.setiTiOpe(operType);
				// cPaisRec, dDesPaisRe
				if (rs.getString("CPAISVND") != null) {
				    gDatRec.setcPaisRec(rs.getString("CPAISVND"));
				} else {
				    gDatRec.setcPaisRec(rs.getString("CPAISEMI"));
				}
				// iTiContRec
				gDatRec.setiTiContRec(PERSONA_JURIDICA);
				if (rs.getString("ORG_TYPE").equalsIgnoreCase("INDIVIDUO")) {
					gDatRec.setiTiContRec(PERSONA_FISICA);
				}
				// cDepRec, dDesDepRec
				if (rs.getShort("CDEPVEN") != 0) {
				    gDatRec.setcDepRec(rs.getShort("CDEPVEN"));
			    } else {
				    gDatRec.setcDepRec(gEmis.getcDepEmi());
			    }	
				// cDisRec, dDesDisRec
				if (rs.getShort("CDISVEN") != 0) {
					gDatRec.setcDisRec(rs.getShort("CDISVEN"));
			    } else {
				    gDatRec.setcDisRec(gEmis.getcDisEmi());
			    }
				// cCiuRec, dDesCiuRec
				// Campo obligatorio si se informa el campo D213 y D202 != 4, no se debe informar cuando D202 = 4
				if (gDatRec.getiTiOpe() != B2F) {
				    if (rs.getShort("CCIUVEN") != 0) {
						gDatRec.setcCiuRec(rs.getShort("CCIUVEN"));
				    } else {
				    	    // debido a que es obligatorio informar la ciudad si se informa la direccion, se opta
				    	    // por dejar en blanco la direccion si no se espcifico la ciudad
				    	    gDatRec.setdDirRec(null);
				    }
				}
				gDatGralOpe.setgDatRec(gDatRec);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos generales de la operacion                |
	        	     +--------------------------------------------------------------+
				 */
				gOpeComer = new CamposOperacionComercial ();
				gOpeComer.setcMoneOpe(rs.getString("CMONEOPE"));
				if (rs.getString("CMONEOPE").equalsIgnoreCase("PYG") == false) {
					// el paquete Roshka Sifen solo agrega los datos del tipo de cambio 
					// si el codigo de la moneda es igual a PYG
					// mascara: 1-5p(0-4)
					gOpeComer.setdTiCam(rs.getBigDecimal("EXCHANGE_RATE"));
					gOpeComer.setdCondTiCam(GLOBAL);
				} else {
					gOpeComer.setdTiCam(new BigDecimal(0));
					gOpeComer.setdCondTiCam(Short.valueOf("0"));	            	
				}
				// TODO: Consultar de que se trata este dato
				gOpeComer.setiCondAnt(ANTICIPO_GLOBAL);
				gOpeComer.setdDesCondAnt("Anticipo Global");
				// este dato se debe derivar de la organizacion
				gOpeComer.setiTImp(Short.valueOf(rs.getString("ITIMP")));
				//
				gOpeComer.setiTipTra(Short.valueOf(rs.getString("ITIPTRA")));
				gDatGralOpe.setgOpeComer(gOpeComer);
				//
				gDtipDE = new CamposEspecificosDE();
				gCamAE = new CamposAutofacturaElectronica();
			    gCamAE.setiNatVen(NO_CONTRIBUYENTE);
				if (rs.getString("VENDOR_ORIGIN").equalsIgnoreCase("EXTRANJERO")) {
				    gCamAE.setiNatVen(EXTRANJERO);
				}
				gCamAE.setiTipIDVen(CEDULA_PARAGUAYA);
				if (gCamAE.getiNatVen() == EXTRANJERO) {
					gCamAE.setiTipIDVen(CEDULA_EXTRANJERA);					
				}
				gCamAE.setdNumIDVen(rs.getString("IDENTITY_NUMBER"));
				gCamAE.setdNomVen(rs.getString("DNOMVEN"));
				gCamAE.setdDirVen(rs.getString("DDIRVEN"));
				gCamAE.setdNumCasVen(Short.valueOf(rs.getString("DNUMCASVEN")));
				gCamAE.setcDepVen(gDatRec.getcDepRec());
				gCamAE.setcDisVen(gDatRec.getcDisRec());
				gCamAE.setcCiuVen(gDatRec.getcCiuRec());
				gCamAE.setdDirProv(rs.getString("DDIRPROV"));
				gCamAE.setcDepProv(gEmis.getcDepEmi());
				gCamAE.setcDisProv(gEmis.getcDisEmi());
				gCamAE.setcCiuProv(gEmis.getcCiuEmi());
				gDtipDE.setgCamAE(gCamAE);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar la condicion de la operacion                       |
	         	 | Obligatorio si C002 = 1 o 4                                  |
                 | No informar si C002 != 1 o 4                                 |
                 | C002 = gTimb.iTiDE                                           |
                 | 1 = Factura electronica                                      |
                 | 4 = Autofactura electronica                                  |
	        	     +--------------------------------------------------------------+
				 */
				if (gDatRec.getiTiOpe() != B2F) {
					/*
					 las autofacturas solo pueden ser operaciones contado
					*/
				    CamposCondicionOperacion gCamCond = new CamposCondicionOperacion();
				    gCamCond.setiCondOpe(CONTADO);
				    if (rs.getString("TRX_CONDITION").equalsIgnoreCase("CREDITO")) {
					    gCamCond.setiCondOpe(CREDITO);					
				    }
				    if (gCamCond.getiCondOpe() == CONTADO) {
					    gCamCond.setdDCondOpe("Contado");
					    // detalles de pagos recibidos por la operacion
					    ArrayList<CamposOperacionContado> gPaConEIni = FormasPagoAutoFacturasDAO.listaFormasPago(autoInvoiceId, conn);
					    gCamCond.setgPaConEIni(gPaConEIni);
					    gDtipDE.setgCamCond(gCamCond);
				    } else {
					    gCamCond.setdDCondOpe("Credito");	
					    CamposOperacionCredito gPagCred = new CamposOperacionCredito();
					    if (rs.getInt("INSTALLMENTS_QTY") > 1) {
						    gPagCred.setiCondCred(CUOTA);
						    gPagCred.setdDCondCred("Cuota");	        	
						    gPagCred.setdCuotas(rs.getShort("INSTALLMENTS_QTY"));
						    ArrayList<CamposCuotas> gCuotas = CuotasAutoFacturasDAO.listaCuotas(autoInvoiceId, conn);
						    gPagCred.setgCuotas(gCuotas);
					    } else {
						    gPagCred.setiCondCred(PLAZO);
						    gPagCred.setdDCondCred("Plazo");
						    gPagCred.setdPlazoCre(String.valueOf(rs.getInt("INST_DAYS_INTERVAL") + " dias"));
					    }
					    if (rs.getDouble("INITIAL_PAYMENT") > 0) {
						    gPagCred.setdMonEnt(rs.getBigDecimal("INITIAL_PAYMENT"));
				     	} else {
						    gPagCred.setdMonEnt(new BigDecimal("0"));						
					    }
					    gCamCond.setgPagCred(gPagCred);
					    gDtipDE.setgCamCond(gCamCond);
				    }
				}
				/**
	         	 +--------------------------------------------------------------+
	        	     | obtener los items de la operacion                       |
	        	     +--------------------------------------------------------------+
				 */
				exchangeRate = rs.getDouble("EXCHANGE_RATE");
				ArrayList<CamposItemsOperacion> gCamItem = ItemsAutoFacturasDAO.listaItems(autoInvoiceId, exchangeRate, conn);
				gDtipDE.setgCamItem(gCamItem);
				DE.setgDtipDE(gDtipDE);
				//
				DE.setgDatGralOpe(gDatGralOpe);
				//
				gTotSub = f.totalesOperacion ( gCamItem, 
						gTimb.getiTiDE(), 
						gOpeComer.getiTImp(), 
						gOpeComer.getcMoneOpe(), 
						gOpeComer.getdCondTiCam(), 
						gOpeComer.getdTiCam().doubleValue() );
				DE.setgTotSub(gTotSub);
				//
				rDE.setDE(DE);
			}
			if (dataFound == true) {
				return rDE;
			} else {
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
	
}
