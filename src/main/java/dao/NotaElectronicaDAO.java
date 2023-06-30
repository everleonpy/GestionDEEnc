package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.CamposDEAsociado;
import pojo.CamposEmisorDE;
import pojo.CamposEspecificosDE;
import pojo.CamposFirmados;
import pojo.CamposGeneralesDE;
import pojo.CamposItemsOperacion;
import pojo.CamposNotaElectronica;
import pojo.CamposOperacionComercial;
import pojo.CamposOperacionDE;
import pojo.CamposReceptorDE;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;
import pojo.OrganizationData;
import pojo.ReceiverData;
import util.UtilPOS;

public class NotaElectronicaDAO {
	
	public static DocumElectronico getRcvMemo ( long memoId ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat tf = new java.text.SimpleDateFormat("HH:mm:ss");
		double exchangeRate;
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
		CamposNotaElectronica gCamNCDE;
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

			// Datos del timbrado 
			// ( Numero, Establecimiento, Punto de expedicion, Numero timbrado,
			//   Numero transaccion, No. serie timbrado, Fecha inicio timbrado, Fecha fin timbrado )
			buffer.append("select r.STAMP_NO DNUMTIM, s.INVOICING_CODE ESTAB_CODE, p.CODE DISPATCH_POINT, lpad(to_char(x.TX_BASE_NUMBER), 7, '0') DNUMDOC,");
			buffer.append(" 'AA' DSERIENUM, r.FROM_DATE DFEINIT, r.TO_DATE DFEFINT,");
			// Campos generales del documento electronico
			// ( Fecha de emision )
			buffer.append(" to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') DEFEMIDE,");
			// Datos de la operacion comercial
			// ( Tipo transaccion, Descripcion Tipo Transaccion, Tipo impuesto afectado, Descripcion tipo impuesto afectado,
			//   Moneda operacion, Descripcion moneda operacion, Condicion tipo cambio, Tipo cambio, 
			//   Condicion anticipo, Descripcion condicion anticipo )
			buffer.append(" txtyp.CODE ITIPTRA, txtyp.DESCRIPTION DDESTIPTRA, taxtyp.CODE ITIMP, taxtyp.DESCRIPTION DDESTIMP,");
			buffer.append(" y.EB_CODE CMONEOPE, y.NAME DDESMONEOPE, x.EXCHANGE_RATE,");
			// Datos del emisor del documento electronico
			// ( RUC emisor, DV RUC emisor, Tipo contribuyente, Tipo regimen,
			//   Nombre emisor, Nombre fantasia, Direccion, No. de casa, 
			//   Complemento direccion 1, Complemento direccion 2, Codigo dpto. emision, Descripcion dpto. emision,
			//   Codigo distrito emision, Descripcion distrito emision, Codigo ciudad emision, Descripcion ciudad emision,
			//   No. telefono emision, Correo electronico emision, Nombre sucursal emision
			buffer.append(" s.ADDRESS1 ST_ADDRESS, s.BUILDING_NUMBER ST_BUILDING_NO, s.ADDRESS2 ST_ADDRESS2, s.ADDRESS3 ST_ADDRESS3,");
			buffer.append(" s.PHONE1 ST_PHONE, s.E_MAIL ST_EMAIL, s.NAME ST_NAME,");
			// Datos del responsable de la generacion del documento electronico
			// ( Tipo documento, Descripcion tipo documento, Numero documento, Nombre responsable,
			// Cargo del responsable )
			buffer.append(" rsp.IDENTITY_TYPE USERID_TYPE, rsp.IDENTITY_NUMBER DNUMIDRESPDE, rsp.NAME DNOMRESPDE,");
			// Datos del receptor del documento electronico	 
			// ( Naturaleza receptor, Tipo operacion, Codigo pais del receptor, Descripcion pais del receptor,
			//   Tipo de contribuyente, RUC receptor, DV RUC del receptor, Codigo tipo documento recepctor,
			//   Descripcion tipo documento receptor, No. documento receptor, Nombre receptor, Nombre fantadia receptor,
			//   Direccion receptor, No. casa, Codigo dpto. receptior, Descripcion dpto. receptor,
			//   Codigo distrito receptor, Descripcion distrito receptor, Codigo ciudad receptor, Descripcion ciudad receptor,
			//   No. telefono receptor, No. telef. celular receptor, Correo electronico receptor, Cod. cliente receptor
			buffer.append(" x.TAX_PAYER_NUMBER TAX_NUMBER, x.IDENTITY_TYPE, x.IDENTITY_NUMBER, x.CUSTOMER_NAME,");
			buffer.append(" x.ADDRESS1 ADDRESS, x.BUILDING_NUMBER, x.PHONE1 PHONE, x.PERSON_TYPE ORG_TYPE,");
			buffer.append(" x.E_MAIL, x.TRX_CONDITION, x.COLLECTION_ID, x.INSTALLMENTS_QTY,");
			buffer.append(" x.INST_DAYS_INTERVAL, x.INITIAL_PAYMENT, c.CODE CUSTOMER_CODE, x.PHONE2 CELL_PHONE,");
			// Campos que componen a las facturas electronicas
			buffer.append(" smod.CODE IINDPRES, smod.DESCRIPTION DDESINDPRES, to_char(x.REMISSION_DATE, 'dd/mm/yyyy hh24:mi:ss') DFECEMNR,");
			
			buffer.append(" s.COUNTRY_ID ST_COUNTRY_ID, s.STATE_ID ST_STATE_ID, s.COUNTY_ID ST_COUNTY_ID, s.CITY_ID ST_CITY_ID,");
			buffer.append(" cnt.NAME ST_COUNTRY_NAME, sta.NAME ST_STATE_NAME, cou.NAME ST_COUNTY_NAME, cty.NAME ST_CITY_NAME,");
			buffer.append(" x.COUNTRY_ID TX_COUNTRY_ID, x.STATE_ID TX_STATE_ID, x.COUNTY_ID TX_COUNTY_ID, x.CITY_ID TX_CITY_ID,");
			buffer.append(" cnt.CODE ST_COUNTRY_CODE, sta.CODE ST_STATE_CODE,  cou.CODE ST_COUNTY_CODE, cty.CODE ST_CITY_CODE,");			
			buffer.append(" issrs.CODE IMOTEMI, issrs.DESCRIPTION DDESMOTEMI,");
			// Enlaces al documento asociado
			buffer.append(" x.INVOICE_ID, x.INVOICE_STAMP_ID, x.CASH_REGISTER_ID, x.CASH_CONTROL_ID,");
			buffer.append(" x.POS_TRANSACTION_ID, t.TRX_TYPE");

			buffer.append(" from FND_ORGANIZATIONS o,");
			buffer.append(" FND_CURRENCIES y,");
			buffer.append(" FND_COUNTRIES cnt,");
			buffer.append(" FND_STATES sta,");
			buffer.append(" FND_COUNTIES cou,");
			buffer.append(" FND_CITIES cty,");
			buffer.append(" FND_SITES s,");
			buffer.append(" FND_USERS rsp,");
			buffer.append(" FND_ELEC_INVOICING_REFS smod,");
			buffer.append(" FND_ELEC_INVOICING_REFS optyp,");
			buffer.append(" FND_ELEC_INVOICING_REFS taxtyp,");
			buffer.append(" FND_ELEC_INVOICING_REFS txtyp,");
			buffer.append(" FND_ELEC_INVOICING_REFS issrs,");
			buffer.append(" RCV_ISSUE_POINTS p,");
			buffer.append(" RCV_STAMP_RECORDS r,");
			buffer.append(" RCV_CUSTOMERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

			buffer.append(" where o.IDENTIFIER = x.ORG_ID");
			buffer.append(" and y.IDENTIFIER = x.CURRENCY_ID");

			buffer.append(" and cnt.IDENTIFIER = s.COUNTRY_ID");
			buffer.append(" and sta.IDENTIFIER = s.STATE_ID");
			buffer.append(" and cou.IDENTIFIER = s.COUNTY_ID");
			buffer.append(" and cty.IDENTIFIER = s.CITY_ID");

			buffer.append(" and c.IDENTIFIER(+) = x.CUSTOMER_ID");

			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and rsp.NAME = x.CREATED_BY");
			buffer.append(" and issrs.IDENTIFIER = x.EB_CDM_REASON_ID");
			buffer.append(" and smod.IDENTIFIER = x.EB_SALE_MODE_ID");
			buffer.append(" and optyp.IDENTIFIER = x.EB_OPER_TYPE_ID");
			buffer.append(" and taxtyp.IDENTIFIER = x.EB_TAX_TYPE_ID");
			buffer.append(" and txtyp.IDENTIFIER = t.EB_TX_TYPE_ID");
			buffer.append(" and p.IDENTIFIER = x.ISSUE_POINT_ID");
			buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and t.TRX_TYPE in ('NOTA-CREDITO', 'NOTA-DEBITO')");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, memoId);
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
			    gOpeDE.setdInfoEmi("Emision de notas de credito");
				if (rs.getString("TRX_TYPE").equalsIgnoreCase("NOTA-DEBITO")) {
				    gOpeDE.setdInfoEmi("Emision de notas de debito");
				}
				gOpeDE.setdInfoFisc(null);
				DE.setgOpeDE(gOpeDE);
				/**
	         	 +--------------------------------------------------------------+
	         	 |  completar los datos del timbrado                            |
	         	 +--------------------------------------------------------------+
				 */
				gTimb = new CamposTimbrado();
				gTimb.setdFeFinT(rs.getDate("DFEFINT"));
				gTimb.setdFeIniT(rs.getDate("DFEINIT"));
				gTimb.setdNumTim(rs.getInt("DNUMTIM"));
				gTimb.setdEst(rs.getString("ESTAB_CODE"));
				gTimb.setdNumDoc(rs.getString("DNUMDOC"));
				gTimb.setdPunExp(rs.getString("DISPATCH_POINT"));
				//gTimb.setdSerieNum(rs.getString("DSERIENUM"));
				gTimb.setdSerieNum(null);
			    gTimb.setiTiDE(NOTA_CR_ELECTRONICA);
				if (rs.getString("TRX_TYPE").equalsIgnoreCase("NOTA-DEBITO")) {
				    gTimb.setiTiDE(NOTA_DB_ELECTRONICA);
				}
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
				gEmis.setdDirEmi(rs.getString("ST_ADDRESS"));
				// dNumCas
				if (rs.getString("ST_BUILDING_NO") != null) {
				    gEmis.setdNumCas(rs.getString("ST_BUILDING_NO"));
				} else {
				    gEmis.setdNumCas(rs.getString("0"));					
				}
				// dCompDir1
				gEmis.setdCompDir1(rs.getString("ST_ADDRESS2"));
				// dCompDir2
				gEmis.setdCompDir2(rs.getString("ST_ADDRESS3"));
				// cDepEmi, dDesDepEmi
				gEmis.setcDepEmi(rs.getShort("ST_STATE_CODE"));
				gEmis.setdDesDepEmi(rs.getString("ST_STATE_NAME"));
				// cDisEmi, dDesDisEmi
				gEmis.setcDisEmi(rs.getShort("ST_COUNTY_CODE"));
				gEmis.setdDesDisEmi(rs.getString("ST_COUNTY_NAME"));
				// cCiuEmi, dDesCiuEmi
				gEmis.setcCiuEmi(rs.getShort("ST_CITY_CODE"));
				gEmis.setdDesCiuEmi(rs.getString("ST_CITY_NAME"));
				// dTelEmi
				gEmis.setdTelEmi(rs.getString("ST_PHONE"));
				// dEmailE				
				if (rs.getString("ST_EMAIL") != null) {
					gEmis.setdEmailE(rs.getString("ST_EMAIL"));
				} else {
					gEmis.setdEmailE(org.geteMail());
				}
				// dDenSuc
				gEmis.setdDenSuc(rs.getString("ST_NAME"));
				// gActEco
				gEmis.setgActEco(org.getActivList());
				/**
	           	 +--------------------------------------------------------------+
	        	     | completar los datos del responsable de emision               |
	        	     +--------------------------------------------------------------+
				 */
				FacturasUtils f = new FacturasUtils();
				gRespDE = f.datosResponsable(rs.getString("DNOMRESPDE"), rs.getString("DNUMIDRESPDE") , rs.getString("USERID_TYPE"));
				gEmis.setgRespDE(gRespDE);
				gDatGralOpe.setgEmis(gEmis);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos del receptor                             |
	         	 +--------------------------------------------------------------+
				 */
				ReceiverData rcv = new ReceiverData();
				rcv.setAddress(rs.getString("ADDRESS"));
				rcv.setAltenativeName(null);
				rcv.setBuildingNo(rs.getString("BUILDING_NUMBER"));
				rcv.setCellPhone(rs.getString("CELL_PHONE"));
				rcv.setCustomerCode(rs.getString("CUSTOMER_CODE"));
				rcv.seteMail(rs.getString("E_MAIL"));
				rcv.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				rcv.setIdentityType(rs.getString("IDENTITY_TYPE"));
				rcv.setName(rs.getString("CUSTOMER_NAME"));
				if (rs.getString("ORG_TYPE") != null) {
				    rcv.setOrgType(rs.getString("ORG_TYPE"));
				} else {
				    rcv.setOrgType("INDIVIDUO");
				}
				rcv.setPhone(rs.getString("PHONE"));
				/*
				rcv.setCityId(rs.getShort("ST_CITY_ID"));
				if (rs.getInt("TX_CITY_ID") != 0) {
					rcv.setCityId(rs.getShort("TX_CITY_ID"));
				}
				rcv.setCountyId(rs.getShort("ST_COUNTY_ID"));
				if (rs.getShort("TX_COUNTY_ID") != 0) {
					rcv.setCountyId(rs.getShort("TX_COUNTY_ID"));					
				}
				rcv.setStateId(rs.getShort("ST_STATE_ID"));
				if (rs.getShort("TX_STATE_ID") != 0) {
					rcv.setStateId(rs.getShort("TX_STATE_ID"));					
				}
				rcv.setCountryId(rs.getShort("ST_COUNTRY_ID"));
				if (rs.getShort("TX_COUNTRY_ID") != 0) {
					rcv.setCountryId(rs.getShort("TX_COUNTRY_ID"));					
				}
				*/
				if (rs.getString("TAX_NUMBER") != null) {
					if (rs.getString("TAX_NUMBER").length() >= 5) {
						rcv.setTaxNumber(rs.getString("TAX_NUMBER"));						
					} else {
						rcv.setTaxNumber(null);						
					}
				} else {
					rcv.setTaxNumber(null);
				}
				gDatRec = f.datosReceptor ( rcv );
				gDatGralOpe.setgDatRec(gDatRec);
				/**
	         	 +--------------------------------------------------------------+
	         	 | completar los datos generales de la operacion                |
	         	 +--------------------------------------------------------------+
				 */
				gOpeComer = new CamposOperacionComercial ();
				gOpeComer.setcMoneOpe(rs.getString("CMONEOPE"));
				gOpeComer.setdDesMoneOpe(rs.getString("DDESMONEOPE"));
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
				gOpeComer.setdDesTImp(rs.getString("DDESTIMP"));
				//
				gOpeComer.setiTipTra(Short.valueOf(rs.getString("ITIPTRA")));
				gOpeComer.setdDesTipTra(rs.getString("DDESTIPTRA"));
				gDatGralOpe.setgOpeComer(gOpeComer);
				//
				gDtipDE = new CamposEspecificosDE();
				gCamNCDE = new CamposNotaElectronica();
				gCamNCDE.setiMotEmi(rs.getShort("IMOTEMI"));
				gCamNCDE.setdDesMotEmi(rs.getString("DDESMOTEMI"));
				gDtipDE.setgCamNCDE(gCamNCDE);
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
				ArrayList<CamposItemsOperacion> gCamItem = ItemsFacturasDAO.listaItems(memoId, exchangeRate, conn);
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
				// campos documento asociado
				CamposDEAsociado gCamDEAsoc = new CamposDEAsociado();
				if (rs.getLong("INVOICE_ID") != 0) {
					long invoiceId = rs.getLong("INVOICE_ID");
					gCamDEAsoc = NotaElectronicaDAO.getRcvInvoice(invoiceId, conn);
					ArrayList<CamposDEAsociado> l = new ArrayList<CamposDEAsociado>();
					l.add(gCamDEAsoc);
					DE.setgCamDEAsoc(l);
				}
				if (rs.getLong("POS_TRANSACTION_ID") != 0) {
					long cashId = rs.getLong("CASH_REGISTER_ID");
					long controlId = rs.getLong("CASH_CONTROL_ID");					
					long invoiceId = rs.getLong("POS_TRANSACTION_ID");
					gCamDEAsoc = NotaElectronicaDAO.getPosInvoice(invoiceId, controlId, cashId, conn);
					ArrayList<CamposDEAsociado> l = new ArrayList<CamposDEAsociado>();
					l.add(gCamDEAsoc);
					DE.setgCamDEAsoc(l);
				}				
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
	
	public static CamposDEAsociado getRcvInvoice ( long invoiceId, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
	    final short ELECTRONICO = 1;
		final short IMPRESO = 2;
		final short CONST_ELECTRONICA = 3;
		try {
		    // ejecutar la consulta de base de datos
		    StringBuffer buffer = new StringBuffer();

		    /*
		    buffer.append("select decode(r.STAMP_TYPE, 'ELECTRONICO', 1, 2) ITIPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', 'Electronico', 'Impreso') DDESTIPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', x.EB_CONTROL_CODE, null) DCDCDEREF,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, r.STAMP_NUMBER) DNTIMDI,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, s.INVOICING_CODE) DESTDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, p.CODE) DPEXPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, lpad(to_char(x.TX_BASE_NUMBER), 7, '0')) DNUMDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, 1) ITIPODOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, 'Factura') DDTIPODOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, x.TX_DATE) DFECEMIDI");
		    */

		    buffer.append("select r.STAMP_TYPE,");
		    buffer.append(" x.EB_CONTROL_CODE DCDCDEREF,");
		    buffer.append(" r.STAMP_NUMBER DNTIMDI,");
		    buffer.append(" s.INVOICING_CODE DESTDOCASO,");
		    buffer.append(" p.CODE DPEXPDOCASO,");
		    buffer.append(" x.TX_BASE_NUMBER,");
		    buffer.append(" x.TX_DATE DFECEMIDI");
		    
		    buffer.append(" from FND_SITES s,");
		    buffer.append(" RCV_ISSUE_POINTS p,");
		    buffer.append(" RCV_STAMP_RECORDS r,");
		    buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where p.IDENTIFIER = x.ISSUE_POINT_ID");
		    buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
		    buffer.append(" and s.IDENTIFIER = x.SITE_ID");
		    buffer.append(" and x.IDENTIFIER = ?");

		    ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, invoiceId);
		    rs = ps.executeQuery();
		
		    CamposDEAsociado o = new CamposDEAsociado();
		    if (rs.next()) {
			    dataFound = true;
			    // iTipDocAso
			    o.setiTipDocAso(IMPRESO);	
			    // dDesTipDocAso
			    o.setdDesTipDocAso("Impreso");
			    if (rs.getString("DCDCDEREF") != null) {
				    o.setiTipDocAso(ELECTRONICO);			    	
				    o.setdDesTipDocAso("Electronico");
			    }
			    // dCdCDERef
			    o.setdCdCDERef(null);
			    if (rs.getString("DCDCDEREF") != null) {
			        o.setdCdCDERef(rs.getString("DCDCDEREF"));
			    }
			    // dNTimDI
			    o.setdNTimDI(null);
			    // dEstDocAso
			    o.setdEstDocAso(null);
			    // dPExpDocAso
			    o.setdPExpDocAso(null);
			    // dNumDocAso
			    o.setdNumDocAso(null);
			    // iTipoDocAso
			    o.setiTipDocAso(Short.valueOf("0"));
			    // dDTipoDocAso
			    o.setdDesTipDocAso(null);
			    if (o.getiTipDocAso() == IMPRESO) {
			        o.setdNTimDI(rs.getString("DNTIMDI"));
			        o.setdEstDocAso(rs.getString("DESTDOCASO"));
			        o.setdPExpDocAso(rs.getString("DPEXPDOCASO"));
			        String txNo = String.valueOf(rs.getLong("TX_NUMBER"));
			        txNo = UtilPOS.paddingString(txNo, 7, '0', true);
			        o.setdNumDocAso(txNo);
			        o.setiTipDocAso(rs.getShort("ITIPODOCASO"));
			        o.setdDesTipDocAso(rs.getString("DDTIPODOCASO"));
			    }
			    // dFecEmiDI
			    o.setdFecEmiDI(null);
			    if (o.getdNTimDI() != null) {
			        o.setdFecEmiDI(rs.getDate("DFECEMIDI"));
			    }
			    // dNumComRet
			    o.setdNumComRet(null);
			    // dNumResCF
			    o.setdNumResCF(null);
			    // iTipCons
			    o.setiTipCons(Short.valueOf("0"));
			    // dDesTipCons
			    o.setdDesTipCons(null);
			    // dNumCons
			    o.setdNumCons(0);
			    // dNumControl
			    o.setdNumControl(null);
		    }
		    if ( dataFound == true ) {
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
	
	public static CamposDEAsociado getPosInvoice ( long invoiceId, long controlId, long cashId, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
	    final short ELECTRONICO = 1;
		final short IMPRESO = 2;
		final short CONST_ELECTRONICA = 3;
		try {
		    // ejecutar la consulta de base de datos
		    StringBuffer buffer = new StringBuffer();

		    /*
		    buffer.append("select decode(r.STAMP_TYPE, 'ELECTRONICO', 1, 2) ITIPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', 'Electronico', 'Impreso') DDESTIPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', x.EB_CONTROL_CODE, null) DCDCDEREF,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, r.STAMP_NUMBER) DNTIMDI,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, s.INVOICING_CODE) DESTDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, p.CODE) DPEXPDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, lpad(to_char(x.TX_BASE_NUMBER), 7, '0')) DNUMDOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, 1) ITIPODOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, 'Factura') DDTIPODOCASO,");
		    buffer.append(" decode(r.STAMP_TYPE, 'ELECTRONICO', null, x.TX_DATE) DFECEMIDI");
		    */
		    buffer.append("select r.STAMP_TYPE,");
		    buffer.append(" x.EB_CONTROL_CODE DCDCDEREF,");
		    buffer.append(" r.STAMP_NUMBER DNTIMDI,");
		    buffer.append(" s.INVOICING_CODE DESTDOCASO,");
		    buffer.append(" p.CODE DPEXPDOCASO,");
		    buffer.append(" x.TX_BASE_NUMBER,");
		    buffer.append(" x.TX_DATE DFECEMIDI");

		    buffer.append(" from FND_SITES s,");
		    buffer.append(" POS_ISSUE_POINTS p,");
		    buffer.append(" POS_STAMP_RECORDS r,");
		    buffer.append(" POS_TRANSACTIONS x");

		    buffer.append(" where p.IDENTIFIER = x.ISSUE_POINT_ID");
		    buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
		    buffer.append(" and s.IDENTIFIER = x.SITE_ID");
		    buffer.append(" and x.CASH_REGISTER_ID = ?");
		    buffer.append(" and x.CASH_CONTROL_ID = ?");
		    buffer.append(" and x.IDENTIFIER = ?");

		    ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, cashId);
		    ps.setLong(2, controlId);
		    ps.setLong(3, invoiceId);
		    rs = ps.executeQuery();
		
		    CamposDEAsociado o = new CamposDEAsociado();
		    if (rs.next()) {
			    dataFound = true;
			    // iTipDocAso
			    o.setiTipDocAso(IMPRESO);	
			    // dDesTipDocAso
			    o.setdDesTipDocAso("Impreso");
			    if (rs.getString("DCDCDEREF") != null) {
				    o.setiTipDocAso(ELECTRONICO);			    	
				    o.setdDesTipDocAso("Electronico");
			    }
			    // dCdCDERef
			    o.setdCdCDERef(null);
			    if (rs.getString("DCDCDEREF") != null) {
			        o.setdCdCDERef(rs.getString("DCDCDEREF"));
			    }
			    // dNTimDI
			    o.setdNTimDI(null);
			    // dEstDocAso
			    o.setdEstDocAso(null);
			    // dPExpDocAso
			    o.setdPExpDocAso(null);
			    // dNumDocAso
			    o.setdNumDocAso(null);
			    // iTipoDocAso
			    o.setiTipDocAso(Short.valueOf("0"));
			    // dDTipoDocAso
			    o.setdDesTipDocAso(null);
			    if (o.getiTipDocAso() == IMPRESO) {
			        o.setdNTimDI(rs.getString("DNTIMDI"));
			        o.setdEstDocAso(rs.getString("DESTDOCASO"));
			        o.setdPExpDocAso(rs.getString("DPEXPDOCASO"));
			        String txNo = String.valueOf(rs.getLong("TX_NUMBER"));
			        txNo = UtilPOS.paddingString(txNo, 7, '0', true);
			        o.setdNumDocAso(txNo);
			        o.setiTipDocAso(rs.getShort("ITIPODOCASO"));
			        o.setdDesTipDocAso(rs.getString("DDTIPODOCASO"));
			    }
			    // dFecEmiDI
			    o.setdFecEmiDI(null);
			    if (o.getdNTimDI() != null) {
			        o.setdFecEmiDI(rs.getDate("DFECEMIDI"));
			    }
			    // dNumComRet
			    o.setdNumComRet(null);
			    // dNumResCF
			    o.setdNumResCF(null);
			    // iTipCons
			    o.setiTipCons(Short.valueOf("0"));
			    // dDesTipCons
			    o.setdDesTipCons(null);
			    // dNumCons
			    o.setdNumCons(0);
			    // dNumControl
			    o.setdNumControl(null);
		    }
		    if ( dataFound == true ) {
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
