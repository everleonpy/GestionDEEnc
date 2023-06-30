package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposEmisorDE;
import pojo.CamposEspecificosDE;
import pojo.CamposFirmados;
import pojo.CamposGeneralesDE;
import pojo.CamposItemsOperacion;
import pojo.CamposOperacionDE;
import pojo.CamposReceptorDE;
import pojo.CamposRemisionElectronica;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;
import pojo.CamposTimbrado;
import pojo.Country;
import pojo.DocumElectronico;
import pojo.GeographicLocation;
import pojo.OrganizationData;
import pojo.RcvEbInvoice;

public class XRemisionElectronicaDAO {
	
	final static short ZERO_VALUE = 0;
	//
	final static short NORMAL = 1;
	final static short CONTINGENCIA = 2;
	//
	final static short FACTURA_ELECTRONICA = 1;
	final static short FACTURA_ELEC_EXPORT = 2;
	final static short FACTURA_ELEC_IMPORT = 3;
	final static short AUTO_FACTURA_ELECTRONICA = 4;
	final static short NOTA_CR_ELECTRONICA = 5;
	final static short NOTA_DB_ELECTRONICA = 6;
	final static short REMISION_ELECTRONICA = 7;	
	
	public static DocumElectronico getInvShipment ( long shipmentId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		long collectionId = 0;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat tf = new java.text.SimpleDateFormat("HH:mm:ss");
		short docType;
		double exchangeRate;
		String baseNumber;
		long securityCode = 0;
		String controlCode = null;
		//
		DocumElectronico rDE;
		CamposFirmados DE;
		CamposOperacionDE gOpeDE;
		CamposTimbrado gTimb;
		CamposGeneralesDE gDatGralOpe;
		CamposReceptorDE gDatRec;
		CamposEmisorDE gEmis;
		CamposResponsableDE gRespDE;
		CamposEspecificosDE gDtipDE;
		CamposRemisionElectronica gCamNRE;
		CamposSubtotalesTotales gTotSub;
		OrganizationData org;
		// 
		final short NORMAL = 1;
		final short CONTINGENCIA = 2;
		//
		final short FACTURA_ELECTRONICA = 1;
		final short FACTURA_ELEC_EXPORT = 2;
		final short FACTURA_ELEC_IMPORT = 3;
		final short AUTO_FACTURA_ELECTRONICA = 4;
		final short NOTA_CR_ELECTRONICA = 5;
		final short NOTA_DB_ELECTRONICA = 6;
		final short REMISION_ELECTRONICA = 7;
		//
		final short PERSONA_FISICA = 1;
		final short PERSONA_JURIDICA = 2;
		final short CONTRIBUYENTE = 1;
		final short NO_CONTRIBUYENTE = 2;
		final short GLOBAL = 1;
		final short POR_ITEM = 2;
		final short ANTICIPO_GLOBAL = 1;
		final short ANTICIPO_POR_ITEM = 2;
		final short SIN_ANTICIPO = 0;
		//
		final short EMISOR_FACTURA = 1;
		final short POSEEDOR_FACTURA = 2;
		final short EMPRESA_TRANSPORTITA = 3;
		final short DESPACHANTE_ADUANAS = 4;
		final short AGENTE_TRANSPORTE = 5;
		/*
		1= Emisor de la factura
		2= Poseedor de la factura y bienes
		3= Empresa transportista
		4=Despachante de Aduanas
		5= Agente de transporte o intermediario
	    */
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

			// remisiones de mercaderias a locales de la organizacion
			buffer.append("select r.STAMP_NO DNUMTIM, dp.INVOICING_CODE DEST, p.CODE DPUNEXP, lpad(to_char(x.TX_BASE_NUMBER), 7, '0') DNUMDOC,");
			buffer.append(" 'AA' DSERIENUM, r.FROM_DATE DFEINIT, r.TO_DATE DFEFINT,");
			buffer.append(" to_char(x.SHIPMENT_DATE, 'dd/mm/yyyy hh24:mi:ss') DEFEMIDE,");
			// datos emisor
			buffer.append(" dp.ADDRESS1 DDIREMI, dp.BUILDING_NUMBER DNUMCAS, dp.ADDRESS2 DCOMPDIR1, dp.ADDRESS3 DCOMPDIR2,");
			buffer.append(" sta1.CODE CDEPEMI, sta1.NAME DDESDEPEMI, cou1.CODE CDISEMI, cou1.NAME DDESDISEMI,");
			buffer.append(" cty1.CODE CCIUEMI, cty1.NAME DDESCIUEMI, dp.PHONE1 DTELEMI, dp.E_MAIL DEMAILE,");
			buffer.append(" dp.NAME DDENSUC, dp.INVOICING_CODE DEST,");
			// datos receptor
			buffer.append(" ap.COUNTRY_ID, ap.STATE_ID, ap.COUNTY_ID, ap.CITY_ID");
			// datos generales
			buffer.append(" x.SHIPMENT_TYPE, x.INVOICE_ID, x.COLD_CHAIN_FLAG, x.DANGER_LOAD_FLAG,");			
			buffer.append(" x.COMMENTS, wr.CODE IMOTEMINR, wr.DESCRIPTION DDESMOTEMINR, x.DISTANCE_MK DKMR,");			
			buffer.append(" to_char(x.INVOICE_DATE, 'RRRR-MM-DD') DFECEM, cnt1.CODE CPAISREC, cnt1.NAME DDESPAISRE");			

			buffer.append(" from FND_COUNTRIES cnt1,");
			buffer.append(" FND_STATES sta1,");
			buffer.append(" FND_COUNTIES cou1,");
			buffer.append(" FND_CITIES cty1,");
			buffer.append(" FND_ELEC_INVOICING_REFS wr,");
			buffer.append(" FND_SITES ap,");
			buffer.append(" FND_SITES dp,");
			buffer.append(" RCV_ISSUE_POINTS p,");
			buffer.append(" RCV_STAMP_RECORDS r,");
			buffer.append(" INV_INTERNAL_SHIPMENTS x");
			buffer.append(" where cnt1.IDENTIFIER = ap.COUNTRY_ID");
			buffer.append(" and sta1.IDENTIFIER = dp.STATE_ID");
			buffer.append(" and cou1.IDENTIFIER = dp.COUNTY_ID");
			buffer.append(" and cty1.IDENTIFIER = dp.CITY_ID");
			buffer.append(" and wr.IDENTIFIER = x.EB_REASON_ID");
			buffer.append(" and ap.IDENTIFIER = x.TO_SITE_ID");
			buffer.append(" and dp.IDENTIFIER = x.FROM_SITE_ID");
			buffer.append(" and p.IDENTIFIER = x.ISSUE_POINT_ID");
			buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and x.SHIPMENT_TYPE = 'ENVIO-LOCALES'");
			buffer.append(" and x.IDENTIFIER = ?");

			buffer.append(" union all");
			
			buffer.append(" select r.STAMP_NO DNUMTIM, dp.INVOICING_CODE DEST, p.CODE DPUNEXP, lpad(to_char(x.TX_BASE_NUMBER), 7, '0') DNUMDOC,");
			buffer.append(" 'AA' DSERIENUM, r.FROM_DATE DFEINIT, r.TO_DATE DFEFINT,");
			buffer.append(" to_char(x.SHIPMENT_DATE, 'dd/mm/yyyy hh24:mi:ss') DEFEMIDE,");
			// datos emisor
			buffer.append(" dp.ADDRESS1 DDIREMI, dp.BUILDING_NUMBER DNUMCAS, dp.ADDRESS2 DCOMPDIR1, dp.ADDRESS3 DCOMPDIR2,");
			buffer.append(" sta1.CODE CDEPEMI, sta1.NAME DDESDEPEMI, cou1.CODE CDISEMI, cou1.NAME DDESDISEMI,");
			buffer.append(" cty1.CODE CCIUEMI, cty1.NAME DDESCIUEMI, dp.PHONE1 DTELEMI, dp.E_MAIL DEMAILE,");
			buffer.append(" dp.NAME DDENSUC, dp.INVOICING_CODE DEST,");
			// datos receptor
			buffer.append(" i.COUNTRY_ID, i.STATE_ID, i.COUNTY_ID, i.CITY_ID");
			// datos generales
			buffer.append(" x.SHIPMENT_TYPE, x.INVOICE_ID, x.COLD_CHAIN_FLAG, x.DANGER_LOAD_FLAG,");			
			buffer.append(" x.COMMENTS, wr.CODE IMOTEMINR, wr.DESCRIPTION DDESMOTEMINR, x.DISTANCE_MK DKMR,");			
			buffer.append(" to_char(x.INVOICE_DATE, 'RRRR-MM-DD') DFECEM, 'X' CPAISREC, 'X' DDESPAISRE");			

			buffer.append(" from FND_STATES sta1,");
			buffer.append(" FND_COUNTIES cou1,");
			buffer.append(" FND_CITIES cty1,");
			buffer.append(" FND_ELEC_INVOICING_REFS wr,");
			buffer.append(" RCV_CUSTOMERS_TRX i,");
			buffer.append(" FND_SITES dp,");
			buffer.append(" RCV_ISSUE_POINTS p,");
			buffer.append(" RCV_STAMP_RECORDS r,");
			buffer.append(" INV_INTERNAL_SHIPMENTS x");
			buffer.append(" where cnt1.IDENTIFIER = ap.COUNTRY_ID");
			buffer.append(" and sta1.IDENTIFIER = dp.STATE_ID");
			buffer.append(" and cou1.IDENTIFIER = dp.COUNTY_ID");
			buffer.append(" and cty1.IDENTIFIER = dp.CITY_ID");
			buffer.append(" and wr.IDENTIFIER = x.EB_REASON_ID");
			buffer.append(" and i.IDENTIFIER = x.INVOICE_ID");
			buffer.append(" and dp.IDENTIFIER = x.FROM_SITE_ID");
			buffer.append(" and p.IDENTIFIER = x.ISSUE_POINT_ID");
			buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and x.SHIPMENT_TYPE = 'ENVIO-A-CLIENTE'");
			buffer.append(" and x.IDENTIFIER = ?");
			
			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, shipmentId);
			ps.setLong(2, shipmentId);
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
			    gOpeDE.setdInfoEmi("Traslado de mercaderias a locales de la empresa");
				if (rs.getString("SHIPMENT_TYPE").equalsIgnoreCase("ENVIO-A-CLIENTE")) {
				    gOpeDE.setdInfoEmi("Envio de mercaderias a cliente");
				}
				String infoFisc = null;
				if (rs.getString("COMMENTS") != null) {
				    infoFisc = rs.getString("COMMENTS");
				}
				if (rs.getString("COLD_CHAIN_FLAG") != null) {
					if (rs.getString("COLD_CHAIN_FLAG").equalsIgnoreCase("S")) {
						if (infoFisc == null) {
					        infoFisc = " ** Carga con cadena de frio **";
						} else {
						    infoFisc = infoFisc + " ** Carga con cadena de frio **";							
						}
					}
				}
				if (rs.getString("DANGER_LOAD_FLAG") != null) {
					if (rs.getString("DANGER_LOAD_FLAG").equalsIgnoreCase("S")) {
						if (infoFisc == null) {
					        infoFisc = " ** Carga peligrosa **";
						} else {
						    infoFisc = infoFisc + " ** Carga peligrosa **";
						}
					}
				}
				gOpeDE.setdInfoFisc(infoFisc);
				DE.setgOpeDE(gOpeDE);
				/**
	         	 +--------------------------------------------------------------+
	         	 |  completar los datos del timbrado                            |
	         	 +--------------------------------------------------------------+
				 */
				RcvEbInvoice w = new RcvEbInvoice();
				w.setDfeinit(rs.getDate("DFEINIT"));
				w.setDnumtim(rs.getString("DNUMTIM"));
				w.setDest(rs.getString("DEST"));
				w.setDnumdoc(rs.getString("DNUMDOC"));
				w.setDpunexp(rs.getString("DPUNEXP"));
				w.setDserienum(rs.getString("DSERIENUM"));
			    gTimb = setgTimbValues ( w );
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
			    gEmis = setgEmisValues ( w, org );
			    gDatGralOpe.setgEmis(gEmis);
				
				gEmis = new CamposEmisorDE();
				// datos generales de la organizacion
				gEmis.setcTipReg(org.getRegimeType());
				gEmis.setdDVEmi(String.valueOf(org.getCheckDigit()));
				if (rs.getString("DEMAILE") != null) {
					gEmis.setdEmailE(rs.getString("DEMAILE"));
				} else {
					gEmis.setdEmailE(org.geteMail());
				}
				gEmis.setdNomEmi(org.getName());
				gEmis.setdNomFanEmi(org.getAlternativeName());
				gEmis.setdRucEm(org.getTaxNumber());
				gEmis.setiTipCont(org.getTaxPayerType());
				gEmis.setgActEco(org.getActivList());
				// datos especificos de la sucursal de emision
				gEmis.setcCiuEmi(rs.getShort("CCIUEMI"));
				gEmis.setcDepEmi(rs.getShort("CDEPEMI"));
				gEmis.setcDisEmi(rs.getShort("CDISEMI"));
				gEmis.setdCompDir1(rs.getString("DCOMPDIR1"));
				gEmis.setdCompDir2(rs.getString("DCOMPDIR2"));
				gEmis.setdDenSuc(rs.getString("DDENSUC"));
				gEmis.setdDesCiuEmi(rs.getString("DDESCIUEMI"));
				gEmis.setdDesDepEmi(rs.getString("DDESDEPEMI"));
				gEmis.setdDesDisEmi(rs.getString("DDESDISEMI"));
				gEmis.setdDirEmi(rs.getString("DDIREMI"));
				gEmis.setdNumCas(rs.getString("DNUMCAS"));
				gEmis.setdTelEmi(rs.getString("DTELEMI"));
				/**
	           	 +--------------------------------------------------------------+
	        	     | completar los datos del responsable de emision               |
	        	     +--------------------------------------------------------------+
				 */
				RemisionesUtils f = new RemisionesUtils();
				gRespDE = f.datosResponsable(rs.getString("DNOMRESPDE"), rs.getString("DNUMIDRESPDE") , rs.getString("USERID_TYPE"));
				gEmis.setgRespDE(gRespDE);
				gDatGralOpe.setgEmis(gEmis);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos del receptor                             |
	         	 +--------------------------------------------------------------+
				 */
				gDatRec = f.datosReceptor ( rs.getString("SHIPMENT_TYPE"),
						rs.getString("DCELREC"), 
						rs.getString("DCODCLIENTE"), 
						rs.getString("DNOMREC"), 
						rs.getString("DDIRREC"), 
						rs.getString("ITIPIDREC"), 
						rs.getString("DRUCREC"), 
						Short.valueOf(rs.getString("DDVREC")), 
						rs.getString("DEMAILREC"), 
						rs.getString("DNOMFANREC"), 
						rs.getString("DNUMCASREC"), 
						rs.getString("DNUMIDREC"), 
						rs.getString("DTELREC"), 
						rs.getString("ITICONTREC"), 
						rs.getString("ITIPOPE"));
				if (rs.getString("SHIPMENT_TYPE").equalsIgnoreCase("ENVIO-A-CLIENTE")) {
				    if (rs.getShort("COUNTRY_ID") != 0) {
					    Country c = EmisorDocumentosDAO.getCountry(rs.getShort("CPAISREC"), conn);
					    gDatRec.setcPaisRec(c.getCountryCode());
					    gDatRec.setdDesPaisRe(c.getCountryName());
					    System.out.println("pais de transaccion: " + gDatRec.getcPaisRec() + " - " + gDatRec.getdDesPaisRe());
				    } else {
					    // por defecto tomar el pais de la sucursal  porque se trata de un dato obligatorio
					    gDatRec.setcPaisRec(rs.getString("CPAISEMI"));
					    gDatRec.setdDesPaisRe(rs.getString("DPAISEMI"));
					    System.out.println("pais de emisor: " + gDatRec.getcPaisRec() + " - " + gDatRec.getdDesPaisRe());
				    }
				    if (rs.getShort("STATE_ID") != 0) {
					    GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(rs.getShort("CDEPEMI"), "DEPARTAMENTO", conn);
					    gDatRec.setcDepRec(Short.valueOf(geoLoc.getLocationCode()));
					    gDatRec.setdDesDepRec(geoLoc.getLocationName());
				    } else {
					    // hay una obligatoriedad condicional para este dato, asi que si no esta definido en la
					    // transaccion, se toma por defecto el dato del emisor
					    gDatRec.setcDepRec(gEmis.getcDepEmi());
					    gDatRec.setdDesDepRec(gEmis.getdDesDepEmi());
				    }
				    if (rs.getShort("COUNTY_ID") != 0) {
					    GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(rs.getShort("CDISEMI"), "DISTRITO", conn);
					    gDatRec.setcDisRec(Short.valueOf(geoLoc.getLocationCode()));
					    gDatRec.setdDesDisRec(geoLoc.getLocationName());
				    }  
					// Campo obligatorio si se informa el campo D213 y D202 != 4, no se debe informar cuando D202 = 4
					if (gDatRec.getiTiOpe() != 4) {
					    if (rs.getShort("CITY_ID") != 0) {
						    GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(rs.getShort("CCIUEMI"), "CIUDAD", conn);
						    gDatRec.setcCiuRec(Short.valueOf(geoLoc.getLocationCode()));
						    gDatRec.setdDesCiuRec(geoLoc.getLocationName());
					    } else {
					    	    // debido a que es obligatorio informar la ciudad si se informa la direccion, se opta
					    	    // por dejar en blanco la direccion si no se espcifico la ciudad
					    	    gDatRec.setdDirRec(null);
					    }
					}
				}
				gDatGralOpe.setgDatRec(gDatRec);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos generales de la operacion                |
	        	     +--------------------------------------------------------------+
				 */
				gDtipDE = new CamposEspecificosDE();
				gCamNRE = new CamposRemisionElectronica();
				gCamNRE.setiMotEmiNR(rs.getShort("IMOTEMINR"));
				gCamNRE.setdDesMotEmiNR(rs.getString("DDESMOTEMINR"));
				gCamNRE.setiRespEmiNR(POSEEDOR_FACTURA);
				gCamNRE.setdDesRespEmiNR("Poseedor de la factura y bienes");
				gCamNRE.setdKmR(rs.getShort("DKMR"));
				if (rs.getLong("INVOICE_ID") != 0) {
					d = df.parse(rs.getString("DFECEMNR"));
					gCamNRE.setdFecEm(d);
				}
				gDtipDE.setgCamNRE(gCamNRE);
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
				/**
	         	 +--------------------------------------------------------------+
	        	     | obtener los items de la operacion                       |
	        	     +--------------------------------------------------------------+
				 */
				exchangeRate = rs.getDouble("EXCHANGE_RATE");
				ArrayList<CamposItemsOperacion> gCamItem = ItemsRemisionesDAO.listaItems(shipmentId, conn);
				gDtipDE.setgCamItem(gCamItem);
				DE.setgDtipDE(gDtipDE);
				//
				DE.setgDatGralOpe(gDatGralOpe);
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
	
	private static CamposTimbrado setgTimbValues ( RcvEbInvoice x) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//
		CamposTimbrado gTimb = new CamposTimbrado();
	    gTimb.setdFeIniT(x.getDfeinit());
	    gTimb.setdNumTim(Integer.valueOf(x.getDnumtim()));
	    gTimb.setdEst(x.getDest());
	    gTimb.setdNumDoc(x.getDnumdoc());
	    gTimb.setdPunExp(x.getDpunexp());
	    gTimb.setdSerieNum(x.getDserienum());
	    gTimb.setiTiDE(REMISION_ELECTRONICA);
	    try {
	        java.util.Date stampDate = df.parse("2022-12-20");
		    gTimb.setdFeIniT(stampDate);
	    } catch (Exception e) {
	    	    e.printStackTrace();
	    }
	    // esto solo debe ser utilizado durante la etapa de pruebas
	    // ********************************************************
	    // N° de Timbrado: 12560102
	    // Establecimiento: 1
	    // Punto de expedición: 1-2-3
	    // inicio de vigencia: 11/11/2022
	    /*
	    gTimb.setdNumTim(12560102);
	    java.util.Date stampDate = df.parse("2022-11-11");
	    gTimb.setdFeIniT(stampDate);
	    gTimb.setdEst("001");
	    gTimb.setdPunExp("001");
     	*/
		return gTimb;
	}
	
	private static CamposEmisorDE setgEmisValues ( RcvEbInvoice x, OrganizationData org) {
		CamposEmisorDE gEmis = new CamposEmisorDE();
		try {
		// datos generales de la organizacion
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
		gEmis.setdDirEmi(x.getStaddress());
		// dNumCas
		if (x.getBuildingnumber() != null) {
			gEmis.setdNumCas(x.getBuildingnumber());
		} else {
			gEmis.setdNumCas("0");					
		}
		// dCompDir1
		gEmis.setdCompDir1(x.getStaddress2());
		// dCompDir2
		gEmis.setdCompDir2(x.getStaddress3());
		// cDepEmi, dDesDepEmi
		gEmis.setcDepEmi(x.getStStateCode());
		gEmis.setdDesDepEmi(x.getStStateName());
		// cDisEmi, dDesDisEmi
		gEmis.setcDisEmi(x.getStCountyCode());
		gEmis.setdDesDisEmi(x.getStCountyName());
		// cCiuEmi, dDesCiuEmi
		gEmis.setcCiuEmi(x.getStCityCode());
		gEmis.setdDesCiuEmi(x.getStCityName());
		// dTelEmi
		gEmis.setdTelEmi(x.getStphone());
		// dEmailE				
		if (x.getStemail() != null) {
			gEmis.setdEmailE(x.getStemail());
		} else {
			gEmis.setdEmailE(org.geteMail());
		}
		// dDenSuc
		gEmis.setdDenSuc(x.getStname());
		// gActEco
		gEmis.setgActEco(org.getActivList());
		//
		return gEmis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
