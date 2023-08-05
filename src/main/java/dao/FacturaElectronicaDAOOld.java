package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.math3.util.Precision;

import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.fields.request.de.TgActEco;
import com.roshka.sifen.core.fields.request.de.TgCamCond;
import com.roshka.sifen.core.fields.request.de.TgCamFE;
import com.roshka.sifen.core.fields.request.de.TgCamIVA;
import com.roshka.sifen.core.fields.request.de.TgCamItem;
import com.roshka.sifen.core.fields.request.de.TgCuotas;
import com.roshka.sifen.core.fields.request.de.TgDatRec;
import com.roshka.sifen.core.fields.request.de.TgEmis;
import com.roshka.sifen.core.fields.request.de.TgOpeCom;
import com.roshka.sifen.core.fields.request.de.TgPaConEIni;
import com.roshka.sifen.core.fields.request.de.TgTimb;
import com.roshka.sifen.core.fields.request.de.TgTotSub;
import com.roshka.sifen.core.fields.request.de.TgValorRestaItem;
import com.roshka.sifen.core.types.TTipEmi;

import business.ApplicationMessage;
import pojo.CamposCondicionOperacion;
import pojo.CamposCuotas;
import pojo.CamposEmisorDE;
import pojo.CamposEspecificosDE;
import pojo.CamposFacturaElectronica;
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
import pojo.EbDatgralope;
import pojo.EbDe;
import pojo.EbGacteco;
import pojo.EbGcamcond;
import pojo.EbGcamfe;
import pojo.EbGcamitem;
import pojo.EbGcamiva;
import pojo.EbGcuotas;
import pojo.EbGdatrec;
import pojo.EbGdtipde;
import pojo.EbGemis;
import pojo.EbGopecom;
import pojo.EbGopede;
import pojo.EbGpaconeini;
import pojo.EbGpagcheq;
import pojo.EbGpagcred;
import pojo.EbGpagtarcd;
import pojo.EbGtimb;
import pojo.EbGtotsub;
import pojo.EbGvaloritem;
import pojo.EbGvalorrestaitem;
import pojo.EbRde;
import pojo.OrganizationData;
import pojo.ReceiverData;
import util.UtilPOS;

import static com.roshka.sifen.internal.Constants.SIFEN_CURRENT_VERSION;

public class FacturaElectronicaDAOOld {
	
	public static DocumElectronico getRcvInvoice ( long invoiceId ) {
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
		CamposOperacionComercial gOpeComer;
		CamposEspecificosDE gDtipDE;
		CamposFacturaElectronica gCamFE;
		CamposSubtotalesTotales gTotSub;
		OrganizationData org;
		// 
		final short ZERO_VALUE = 0;
		//
		final short NORMAL = 1;
		final short CONTINGENCIA = 2;
		//
		final short B2B = 1;
		final short B2C = 2;
		final short B2G = 3;
		final short B2F = 4;
		//
		final short OPERACION_PRESENCIAL = 1;
		final short IVA_RENTA = 5;
		final short VENTA_MERCADERIAS = 1;
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
		final short CONTADO = 1;
		final short CREDITO = 2;
		//
		final short PLAZO = 1;
		final short CUOTA = 2;
		//
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
			buffer.append(" smod.CODE IINDPRES, smod.DESCRIPTION DDESINDPRES, to_char(x.REMISSION_DATE, 'dd/mm/yyyy hh24:mi:ss') DFECEMNR, optyp.CODE ITIOPE,");
			
			buffer.append(" s.COUNTRY_ID ST_COUNTRY_ID, s.STATE_ID ST_STATE_ID, s.COUNTY_ID ST_COUNTY_ID, s.CITY_ID ST_CITY_ID,");
			buffer.append(" cnt.NAME ST_COUNTRY_NAME, sta.NAME ST_STATE_NAME, cou.NAME ST_COUNTY_NAME, cty.NAME ST_CITY_NAME,");
			buffer.append(" x.COUNTRY_ID TX_COUNTRY_ID, x.STATE_ID TX_STATE_ID, x.COUNTY_ID TX_COUNTY_ID, x.CITY_ID TX_CITY_ID,");
			buffer.append(" cnt.CODE ST_COUNTRY_CODE, sta.CODE ST_STATE_CODE,  cou.CODE ST_COUNTY_CODE, cty.CODE ST_CITY_CODE");			

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
			buffer.append(" and smod.IDENTIFIER(+) = x.EB_SALE_MODE_ID");
			buffer.append(" and optyp.IDENTIFIER(+) = x.EB_OPER_TYPE_ID");
			buffer.append(" and taxtyp.IDENTIFIER(+) = x.EB_TAX_TYPE_ID");
			buffer.append(" and txtyp.IDENTIFIER(+) = t.EB_TX_TYPE_ID");
			buffer.append(" and p.IDENTIFIER = x.ISSUE_POINT_ID");
			buffer.append(" and r.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and t.TRX_TYPE in ('FACTURA')");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, invoiceId);
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
				gOpeDE.setdInfoEmi("Ventas en linea de cajas");
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
				gTimb.setdSerieNum(rs.getString("DSERIENUM"));
				gTimb.setiTiDE(FACTURA_ELECTRONICA);
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
				rcv.setOperationType(rs.getShort("ITIOPE"));
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
				if (rs.getShort("TX_COUNTY_ID") != ZERO_VALUE) {
					rcv.setCountyId(rs.getShort("TX_COUNTY_ID"));					
				}
				rcv.setStateId(rs.getShort("ST_STATE_ID"));
				if (rs.getShort("TX_STATE_ID") != ZERO_VALUE) {
					rcv.setStateId(rs.getShort("TX_STATE_ID"));					
				}
				rcv.setCountryId(rs.getShort("ST_COUNTRY_ID"));
				if (rs.getShort("TX_COUNTRY_ID") != ZERO_VALUE) {
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
					gOpeComer.setdTiCam(BigDecimal.ZERO);
					gOpeComer.setdCondTiCam(Short.valueOf("0"));	            	
				}
				// TODO: Consultar de que se trata este dato
				gOpeComer.setiCondAnt(ANTICIPO_GLOBAL);
				gOpeComer.setdDesCondAnt("Anticipo Global");
				// este dato se debe derivar de la organizacion
				if (rs.getString("ITIMP") != null) {
				    gOpeComer.setiTImp(Short.valueOf(rs.getString("ITIMP")));
				    gOpeComer.setdDesTImp(rs.getString("DDESTIMP"));
				} else {
				    gOpeComer.setiTImp(IVA_RENTA);
				    gOpeComer.setdDesTImp("IVA – Renta");					
				}
				//
				if (rs.getString("ITIPTRA") != null) {
				    gOpeComer.setiTipTra(Short.valueOf(rs.getString("ITIPTRA")));
				    gOpeComer.setdDesTipTra(rs.getString("DDESTIPTRA"));
				} else {
				    gOpeComer.setiTipTra(VENTA_MERCADERIAS);
				    gOpeComer.setdDesTipTra("Venta de mercadería");					
				}
				gDatGralOpe.setgOpeComer(gOpeComer);
				//
				gDtipDE = new CamposEspecificosDE();
				gCamFE = new CamposFacturaElectronica();
				if (rs.getString("IINDPRES") != null) {
				    gCamFE.setiIndPres(Short.valueOf(rs.getString("IINDPRES")));
				    gCamFE.setdDesIndPres(rs.getString("DDESINDPRES"));					
				} else {
				    gCamFE.setiIndPres(OPERACION_PRESENCIAL);
				    gCamFE.setdDesIndPres("Operacion Presencial");
				}
				if (rs.getString("DFECEMNR") != null) {
					d = sdf.parse(rs.getString("DFECEMNR"));
					gCamFE.setdFecEmNR(d);
				}
				gDtipDE.setgCamFE(gCamFE);
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
				    CamposCondicionOperacion gCamCond = new CamposCondicionOperacion();
				    if (rs.getString("TRX_CONDITION") == null) {
					    gCamCond.setiCondOpe(CONTADO);	
				    } else {
				        gCamCond.setiCondOpe(CONTADO);				    	    
				        if (rs.getString("TRX_CONDITION").equalsIgnoreCase("CREDITO")) {
					        gCamCond.setiCondOpe(CREDITO);				    	    
				        }
					}
				    if (gCamCond.getiCondOpe() == CONTADO) {
					    gCamCond.setdDCondOpe("Contado");
					    // detalles de pagos recibidos por la operacion
					    collectionId = rs.getLong("COLLECTION_ID");
					    ArrayList<CamposOperacionContado> gPaConEIni = FormasPagoFacturasDAO.listaFormasPago(collectionId, conn);
					    gCamCond.setgPaConEIni(gPaConEIni);
					    gDtipDE.setgCamCond(gCamCond);
				    } else {
					    gCamCond.setdDCondOpe("Credito");	
					    CamposOperacionCredito gPagCred = new CamposOperacionCredito();
					    if (rs.getInt("INSTALLMENTS_QTY") > 1) {
						    gPagCred.setiCondCred(CUOTA);
						    gPagCred.setdDCondCred("Cuota");	        	
						    gPagCred.setdCuotas(rs.getShort("INSTALLMENTS_QTY"));
						    ArrayList<CamposCuotas> gCuotas = CuotasFacturasDAO.listaCuotas(invoiceId, conn);
						    gPagCred.setgCuotas(gCuotas);
					    } else {
						    gPagCred.setiCondCred(PLAZO);
						    gPagCred.setdDCondCred("Plazo");
						    gPagCred.setdPlazoCre(String.valueOf(rs.getInt("INST_DAYS_INTERVAL") + " dias"));
			    		    }
					    if (rs.getDouble("INITIAL_PAYMENT") > 0) {
						    gPagCred.setdMonEnt(rs.getBigDecimal("INITIAL_PAYMENT"));
					    } else {
						    gPagCred.setdMonEnt(BigDecimal.ZERO);						
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
				ArrayList<CamposItemsOperacion> gCamItem = ItemsFacturasDAO.listaItems(invoiceId, exchangeRate, conn);
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
				//baseNumber = rs.getString("TX_NUMBER").substring(8);
				// 001-001-0023145
				// 012345678901234
				// asegurar que el codigo de seguridad no exista ya en la tabla de transacciones
				// de ventas
				/*
				boolean existsCode = true;
				while (existsCode == true) {
					securityCode = UtilPOS.genRandomNumber(1, 999999999);
					existsCode = RcvCustomersTrxDAO.existsSecurityCode(securityCode);
				}
				// generar el codigo de control
				controlCode = FacturaElectronicaDAO.buildControlCode ( gTimb.getiTiDE(), 
						gEmis.getdRucEm(), 
						gEmis.getdDVEmi(), 
						gTimb.getdEst(), 
						gTimb.getdPunExp(), 
						baseNumber, 
						gEmis.getiTipCont(), 
						rs.getDate("TX_DATE"), 
						gOpeDE.getiTipEmi(), 
						securityCode );
				DE.setId(controlCode);
				System.out.println(controlCode + " - " + "long.: " + controlCode.length());
				DE.setdDVId(Short.valueOf(controlCode.substring(controlCode.length() - 1)));
				*/
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
	
	public static String buildControlCode ( short txType, 
			                                String taxNumber, 
			                                String taxNoCd, 
			                                String estabNo, 
			                                String issueNo, 
			                                String txNumber, 
			                                short payerType, 
			                                java.util.Date issueDate, 
			                                short issueType, 
			                                long securityCode ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String code;
		String s;
		// tipo de documento
		if (txType < 10) {
			code = "0" + String.valueOf(txType);
		} else {
			code = String.valueOf(txType);
		}
		// RUC del emisor
		if (taxNumber.length() < 8) {
			s = UtilPOS.paddingString(taxNumber, 8, '0', true);
			code = code + s;
		} else {
			code = code + taxNumber;
		}
		// DV del RUC del emisor
		code = code + String.valueOf(taxNoCd);
		// establecimiento
		code = code + estabNo;
		// punto de expedicion
		code = code + issueNo;
		// numero de documento
		code = code + txNumber;
		// tipo de contribuyente (D103 - 1: Persona fisica | 2: Persona juridica)
		code = code + String.valueOf(payerType);
		// fecha de emision
		code = code + sdf.format(issueDate);
		// tipo de emision ( B002 - 1: Normal | 2: Contingencia)
		code = code + String.valueOf(issueType);		
		// codigo de seguridad
		s = String.valueOf(securityCode);
		if (s.length() < 9) {
			code = UtilPOS.paddingString(s, 9, '0', true);
		} else {
			code = code + s;
		}
		// digito verificador
		int dv = UtilPOS.calcCheckDigit(code, 43);
		code = code + String.valueOf(dv);
		//
		return code;
	}

	public static String buildQrCode ( String srvVersion,
			                           String controlCode,
			                           String issueDate,
			                           String rvrTaxNumber,
			                           String txAmount, 
			                           String taxAmount,
			                           String itemsQty ) {
		String qrCode = null;
		//
		/*
		 nVersion=150&Id=0144444401700100100145282201701251587326098
		 8&dFeEmiDE=323031372d30312d32355430393a33353a3137&dRucRec
		 =88899990&dTotGralOpe=300000&dTotIVA=27272&cItems=2&DigestV
		 alue=797a4759685578312f5859597a6b7357422b6650523351633530633
		 d&IdCSC=0001
		 */
		qrCode = "nVersion=" + srvVersion;
		qrCode = qrCode + "&Id=" + controlCode;
		qrCode = qrCode + "&dFeEmiDE=" + issueDate;
		qrCode = qrCode + "&dRucRec=" + rvrTaxNumber;
		qrCode = qrCode + "&dTotGralOpe=" + txAmount;
		qrCode = qrCode + "&dTotIVA=" + taxAmount;
		qrCode = qrCode + "&cItems=" + itemsQty;
		return qrCode;
	}
	
	public static ApplicationMessage registrarDE ( DocumentoElectronico DE, 
			                                       String usrName, 
			                                       long transactionId, 
			                                       long orgId, 
			                                       long unitId ) {
		Connection conn = null;
		ApplicationMessage m = null;
		String event = "Inicio";
		short zeroVal = 0;
		try {
			event = "Obtener conexion";
			conn = Util.getConnection();
			if ( conn == null ) {
				ApplicationMessage aMsg = new ApplicationMessage("NO-CONN", "No se ha podido establecer la conexion con la base de datos", ApplicationMessage.ERROR);
				return aMsg;
			}
			// desarmar el documento electronico que fue enviado a sifen y almacenarlo en las
			// tablas destinadas para el efecto en la aplicacion
			/**
		     +------------------------------------------------------------------------------------------------+
		     | AA001 - rDE                                                                                    |
		     | Campos que identifican el formato electrónico XML                                              |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Formto electronico";
			long ebRdeId = UtilitiesDAO.getNextval("SQ_EB_RDE", conn);
			EbRde ebRde = new EbRde();
			ebRde.setDverfor(SIFEN_CURRENT_VERSION);
			//
			ebRde.setCashControlId(0);
			ebRde.setCashRegisterId(0);
			ebRde.setCreatedBy(usrName);
			ebRde.setCreatedOn(new java.util.Date());
			ebRde.setIdentifier(ebRdeId);
			ebRde.setModifiedBy(null);
			ebRde.setModifiedOn(null);
			ebRde.setOrgId(orgId);
			ebRde.setTransactionId(transactionId);
			ebRde.setTrxType("FACTURA-MAYORISTAS");
			ebRde.setUnitId(unitId);
			m = EbRdeDAO.addRow(ebRde, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | A001 - DE                                                                                      |
		     | Campos firmados del Documento Electrónico                                                      |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Documento electronico";
			long ebDeId = UtilitiesDAO.getNextval("SQ_EB_DE", conn);
			EbDe ebDe = new EbDe();
			ebDe.setId(DE.getId());
			ebDe.setDdvid(DE.getdDVId());
			ebDe.setDfecfirma(java.sql.Timestamp.valueOf(DE.getdFecFirma()));
			ebDe.setDsisfact(DE.getdSisFact());
			//
			ebDe.setCreatedBy(usrName);
			ebDe.setCreatedOn(new java.util.Date());
			ebDe.setIdentifier(ebDeId);
			ebDe.setModifiedBy(null);
			ebDe.setOrgId(orgId);
			ebDe.setRdeId(ebRdeId);
			ebDe.setUnitId(unitId);
			m = EbDeDAO.addRow(ebDe, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | B001 - gOpeDE                                                                                  |
		     | Campos inherentes a la operación de Documentos Electrónicos                                    |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Operacion DE";
			long opeDeId = UtilitiesDAO.getNextval("SQ_EB_GOPEDE", conn);
			EbGopede ebGopede = new EbGopede();
			TTipEmi t1 = DE.getgOpeDE().getiTipEmi();
			ebGopede.setItipemi(t1.getVal());
			ebGopede.setDdestipemi(t1.getDescripcion());
			ebGopede.setDcodseg(DE.getgOpeDE().getdCodSeg());
			ebGopede.setDinfoemi(DE.getgOpeDE().getdInfoEmi());
			ebGopede.setDinfofisc(DE.getgOpeDE().getdInfoFisc());
			//
			ebGopede.setCreatedBy(usrName);
			ebGopede.setCreatedOn(new java.util.Date());
			ebGopede.setDeId(ebDeId);
			ebGopede.setIdentifier(opeDeId);
			ebGopede.setModifiedBy(null);
			ebGopede.setModifiedOn(null);
			ebGopede.setOrgId(orgId);
			ebGopede.setUnitId(unitId);
			m = EbGopedeDAO.addRow(ebGopede, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | C001 - gTimb                                                                                   |
		     | Campos de datos del Timbrado                                                                   |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Timbrado";
			EbGtimb ebGtimb = new EbGtimb();
			TgTimb gTim = DE.getgTimb();
			ebGtimb.setItide(gTim.getiTiDE().getVal());
			ebGtimb.setDdestide(gTim.getiTiDE().getDescripcion());
			ebGtimb.setDnumtim(gTim.getdNumTim());
			ebGtimb.setDest(gTim.getdEst());
			ebGtimb.setDpunexp(gTim.getdPunExp());
			ebGtimb.setDnumdoc(gTim.getdNumDoc());
			ebGtimb.setDserienum(gTim.getdSerieNum());
			ebGtimb.setDfeinit(java.sql.Date.valueOf(gTim.getdFeIniT()));
			//
			ebGtimb.setCreatedBy(usrName);
			ebGtimb.setCreatedOn(new java.util.Date());
			ebGtimb.setDeId(ebDeId);
			ebGtimb.setModifiedBy(null);
			ebGtimb.setModifiedOn(null);
			ebGtimb.setOrgId(orgId);
			ebGtimb.setUnitId(unitId);
			m = EbGtimbDAO.addRow(ebGtimb, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | D001 - gDatGralOpe                                                                             |
		     | Campos Generales del Documento Electrónico DE                                                  |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Datos generales DE";
			long datGralOpeId = UtilitiesDAO.getNextval("SQ_EB_DATGRALOPE", conn);
			EbDatgralope datOpe = new EbDatgralope();
			datOpe.setDfeemide(java.sql.Timestamp.valueOf(DE.getgDatGralOpe().getdFeEmiDE()));
			//
			datOpe.setCreatedBy(usrName);
			datOpe.setCreatedOn(new java.util.Date());
			datOpe.setDeId(ebDeId);
			datOpe.setIdentifier(datGralOpeId);
			datOpe.setModifiedBy(null);
			datOpe.setModifiedOn(null);
			datOpe.setOrgId(orgId);
			datOpe.setUnitId(unitId);
			m = EbDatgralopeDAO.addRow(datOpe, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | D010 - gOpeCom                                                                                 |
		     | Campos inherentes a la operación comercial                                                     |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Operacion comercial";
			long opeComId = UtilitiesDAO.getNextval("SQ_EB_GOPECOM", conn);
			EbGopecom opeCom = new EbGopecom();
			TgOpeCom oc = DE.getgDatGralOpe().getgOpeCom();
			if (oc.getiTipTra() != null) {
			    opeCom.setItiptra(oc.getiTipTra().getVal());
			    opeCom.setDdestiptra(oc.getiTipTra().getDescripcion());
			}
			opeCom.setItimp(oc.getiTImp().getVal());
			opeCom.setDdestimp(oc.getiTImp().getDescripcion());
			opeCom.setCmoneope(oc.getcMoneOpe().name());
			opeCom.setDdesmoneope(oc.getcMoneOpe().getDescripcion());
			if (oc.getdCondTiCam() != null) {
			    opeCom.setDcondticam(oc.getdCondTiCam().getVal());
				opeCom.setDticam(oc.getdTiCam().doubleValue());
			} else {
				opeCom.setDcondticam(zeroVal);
				opeCom.setDticam(0.0);
			}
			if (oc.getiCondAnt() != null) {
			    opeCom.setIcondant(oc.getiCondAnt().getVal());
			    opeCom.setDdescondant(oc.getiCondAnt().getDescripcion());
			}
			//
			opeCom.setCreatedBy(usrName);
			opeCom.setCreatedOn(new java.util.Date());
			opeCom.setDatgralopeId(datGralOpeId);
			opeCom.setIdentifier(opeComId);
			opeCom.setModifiedBy(null);
			opeCom.setModifiedOn(null);
			opeCom.setOrgId(orgId);
			opeCom.setUnitId(unitId);
			m = EbGopecomDAO.addRow(opeCom, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | D100 - gEmis                                                                                   |
		     | Campos que identifican al emisor del Documento Electrónico DE                                  |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Emisor DE";
			long emisId = UtilitiesDAO.getNextval("SQ_EB_GEMIS", conn);
			EbGemis gEmis = new EbGemis();
			TgEmis em = DE.getgDatGralOpe().getgEmis();
			gEmis.setDrucem(em.getdRucEm());
			gEmis.setDdvemi(em.getdDVEmi());
			gEmis.setItipcont(em.getiTipCont().getVal());
			if (em.getcTipReg() != null) {
			    gEmis.setCtipreg(em.getcTipReg().getVal());
			} else {
				gEmis.setCtipreg(zeroVal);
			}
			gEmis.setDnomemi(em.getdNomEmi());
			gEmis.setDnomfanemi(em.getdNomFanEmi());
			gEmis.setDdiremi(em.getdDirEmi());
			gEmis.setDnumcas(em.getdNumCas());
			gEmis.setDcompdir1(em.getdCompDir1());
			gEmis.setDcompdir2(em.getdCompDir2());
			gEmis.setCdepemi(em.getcDepEmi().getVal());
			gEmis.setDdesdepemi(em.getcDepEmi().getDescripcion());
			gEmis.setCdisemi(em.getcDisEmi());
			gEmis.setDdesdisemi(em.getdDesDisEmi());
			gEmis.setCciuemi(em.getcCiuEmi());
			gEmis.setDdesciuemi(em.getdDesCiuEmi());
			gEmis.setDtelemi(em.getdTelEmi());
			gEmis.setDemaile(em.getdEmailE());
			gEmis.setDdensuc(em.getdDenSuc());
			//
			gEmis.setCreatedBy(usrName);
			gEmis.setCreatedOn(new java.util.Date());
			gEmis.setDatgralopeId(datGralOpeId);
			gEmis.setIdentifier(emisId);
			gEmis.setModifiedBy(null);
			gEmis.setModifiedOn(null);
			gEmis.setOrgId(orgId);
			gEmis.setUnitId(unitId);
			m = EbGemisDAO.addRow(gEmis, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | D130 - gActEco                                                                                 |
		     | Campos que describen la actividad económica del emisor                                         |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Activ. economicas";
			Iterator itr1 = em.getgActEcoList().iterator();
			while (itr1.hasNext()) {
				TgActEco o = (TgActEco) itr1.next();
				EbGacteco x = new EbGacteco();
				x.setCacteco(o.getcActEco());
				x.setDdesacteco(o.getdDesActEco());
				//
				x.setCreatedBy(usrName);
				x.setCreatedOn(new java.util.Date());
				x.setEmisId(emisId);
				x.setModifiedBy(null);
				x.setModifiedOn(null);
				x.setOrgId(orgId);
				x.setUnitId(unitId);
				m = EbGactecoDAO.addRow(x, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | D200 - gDatRec                                                                                 |
		     | Campos que identifican al receptor del Documento Electrónico DE                                |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Receptor DE";
			long datRecId = UtilitiesDAO.getNextval("SQ_EB_GDATREC", conn);
			EbGdatrec rcpt = new EbGdatrec();
			TgDatRec re = DE.getgDatGralOpe().getgDatRec();
			rcpt.setInatrec(re.getiNatRec().getVal());
			rcpt.setItiope(re.getiTiOpe().getVal());
			rcpt.setCpaisrec(re.getcPaisRec().name());
			rcpt.setDdespaisre(re.getcPaisRec().getNombre());
			if (re.getiTiContRec() != null) {
			    rcpt.setIticontrec(re.getiTiContRec().getVal());
			} else {
				rcpt.setIticontrec(zeroVal);
			}
			rcpt.setDrucrec(re.getdRucRec());
			rcpt.setDdvrec(re.getdDVRec());
			if (re.getiTipIDRec() != null) {
			    rcpt.setItipidrec(re.getiTipIDRec().getVal());
				rcpt.setDdtipidrec(re.getdDTipIDRec());
			} else {
				rcpt.setItipidrec(zeroVal);
			}
			rcpt.setDnumidrec(re.getdNumIDRec());
			rcpt.setDnomrec(re.getdNomRec());
			rcpt.setDnomfanrec(re.getdNomFanRec());
			rcpt.setDdirrec(re.getdDirRec());
			rcpt.setDnumcasrec(re.getdNumCasRec());
			if (re.getcDepRec() != null) {
			    rcpt.setCdeprec(re.getcDepRec().getVal());
			    rcpt.setDdesdeprec(re.getcDepRec().getDescripcion());
			} else {
				rcpt.setCdeprec(zeroVal);
			}
			rcpt.setCdisrec(re.getcDisRec());
			rcpt.setDdesdisrec(re.getdDesDisRec());
			rcpt.setCciurec(re.getcCiuRec());
			rcpt.setDdesciurec(re.getdDesCiuRec());
			rcpt.setDtelrec(re.getdTelRec());
			rcpt.setDcelrec(re.getdCelRec());
			rcpt.setDemailrec(re.getdEmailRec());
			rcpt.setDcodcliente(re.getdCodCliente());
			//
			rcpt.setCreatedBy(usrName);
			rcpt.setCreatedOn(new java.util.Date());
			rcpt.setDatgralopeId(datGralOpeId);
			rcpt.setIdentifier(datRecId);
			rcpt.setModifiedBy(null);
			rcpt.setModifiedOn(null);
			rcpt.setOrgId(orgId);
			rcpt.setUnitId(unitId);
			m = EbGdatrecDAO.addRow(rcpt, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E001 - gDtipDE                                                                                 |
		     | Campos específicos por tipo de Documento Electrónico                                           |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Datos especificos";
			long tdeId = UtilitiesDAO.getNextval("SQ_EB_GDTIPDE", conn);
			EbGdtipde tde = new EbGdtipde();
			tde.setCreatedBy(usrName);
			tde.setCreatedOn(new java.util.Date());
			tde.setDeId(ebDeId);
			tde.setIdentifier(tdeId);
			tde.setModifiedBy(null);
			tde.setModifiedOn(null);
			tde.setOrgId(orgId);
			tde.setUnitId(unitId);
			m = EbGdtipdeDAO.addRow(tde, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E010 - gCamFE                                                                                  |
		     | Campos que componen la Factura Electrónica FE                                                  |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Factura electronica";
			long feId = UtilitiesDAO.getNextval("SQ_EB_GCAMFE", conn);
			TgCamFE cFe = DE.getgDtipDE().getgCamFE();
			EbGcamfe fe = new EbGcamfe();
			fe.setIindpres(cFe.getiIndPres().getVal());
			fe.setDdesindpres(cFe.getiIndPres().getDescripcion());
			if (cFe.getdFecEmNR() != null) {
			    fe.setDfecemnr(java.sql.Date.valueOf(cFe.getdFecEmNR()));
			}
			//
			fe.setCreatedBy(usrName);
			fe.setCreatedOn(new java.util.Date());
			fe.setIdentifier(feId);
			fe.setModifiedBy(null);
			fe.setModifiedOn(null);
			fe.setOrgId(orgId);
			fe.setTipdeId(tdeId);
			fe.setUnitId(unitId);
			m = EbGcamfeDAO.addRow(fe, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E600 - gCamCond                                                                                |
		     | Campos que describen la condición de la operación                                              |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Condicion operacion";
			TgCamCond cco = DE.getgDtipDE().getgCamCond();
			long condId = UtilitiesDAO.getNextval("SQ_EB_GCAMCOND", conn);
			EbGcamcond cond = new EbGcamcond();
			cond.setIcondope(cco.getiCondOpe().getVal());
			cond.setDdcondope(cco.getiCondOpe().getDescripcion());
			//
			cond.setCreatedBy(usrName);
			cond.setCreatedOn(new java.util.Date());
			cond.setIdentifier(condId);
			cond.setModifiedBy(null);
			cond.setModifiedOn(null);
			cond.setOrgId(orgId);
			cond.setTipdeId(tdeId);
			cond.setUnitId(unitId);
			m = EbGcamcondDAO.addRow(cond, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E605 - gPaConEIni                                                                              |
		     | Campos que describen la forma de pago de la operación contado o del monto de entrega inicial   |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			if (cco.getgPaConEIniList() != null) {
			    event = "Pago al contado";
			    EbGpaconeini coei = new EbGpaconeini();
			    Iterator itr2 = cco.getgPaConEIniList().iterator();
			    while (itr2.hasNext()) {
				    TgPaConEIni x = (TgPaConEIni) itr2.next();
				    long coeiId = UtilitiesDAO.getNextval("SQ_EB_GPACONEINI", conn);
				    coei.setItipago(x.getiTiPago().getVal());
				    coei.setDdestipag(x.getdDesTiPag());
				    coei.setDmontipag(x.getdMonTiPag().doubleValue());
				    coei.setCmonetipag(x.getcMoneTiPag().name());
				    coei.setDdmonetipag(x.getcMoneTiPag().getDescripcion());
				    coei.setDticamtipag(x.getdTiCamTiPag().doubleValue());
				    //
				    coei.setCamcondId(condId);
				    coei.setCreatedBy(usrName);
				    coei.setCreatedOn(new java.util.Date());
				    coei.setIdentifier(coeiId);
				    coei.setModifiedBy(null);
				    coei.setModifiedOn(null);
				    coei.setOrgId(orgId);
				    coei.setUnitId(unitId);
				    m = EbGpaconeiniDAO.addRow(coei, conn);
				    if ( m != null ) {
					    if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						    return m;
					    }
				    }
				    /**
			         +------------------------------------------------------------------------------------------------+
			         | E620 - gPagTarCD                                                                               |
			         | Campos que describen el pago o entrega inicial de la operación con tarjeta de crédito/débito   |
			         +------------------------------------------------------------------------------------------------+		 
				     */
				    if (x.getgPagTarCD() != null) {
					    event = "Pago con tarjeta";
					    EbGpagtarcd tcd = new EbGpagtarcd();
					    tcd.setIdentarj(x.getgPagTarCD().getiDenTarj().getVal());
					    tcd.setDdesdentarj(x.getgPagTarCD().getdDesDenTarj());
					    tcd.setDrsprotar(x.getgPagTarCD().getdRSProTar());
					    tcd.setDrucprotar(x.getgPagTarCD().getdRUCProTar());
					    tcd.setDdvprotar(x.getgPagTarCD().getdDVProTar());
					    tcd.setIforpropa(x.getgPagTarCD().getiForProPa().getVal());
					    tcd.setDcodauope(x.getgPagTarCD().getdCodAuOpe());
					    tcd.setDnomtit(x.getgPagTarCD().getdNomTit());
					    tcd.setDnumtarj(x.getgPagTarCD().getdNumTarj());
					    //
					    tcd.setCreatedBy(usrName);
					    tcd.setCreatedOn(new java.util.Date());
					    tcd.setModifiedBy(null);
					    tcd.setModifiedOn(null);
					    tcd.setOrgId(orgId);
					    tcd.setPaconeiniId(coeiId);
					    tcd.setUnitId(unitId);
					    m = EbGpagtarcdDAO.addRow(tcd, conn);
					    if ( m != null ) {
						    if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
							    return m;
						    }
					    } 
				    }
				    /**
			         +------------------------------------------------------------------------------------------------+
			         | E630 - gPagCheq                                                                                |
			         | Campos que describen el pago o entrega inicial de la operación con cheque                      |
			         +------------------------------------------------------------------------------------------------+		 
				     */
				    if (x.getgPagCheq() != null) {
					    event = "Pago con cheque";
					    EbGpagcheq tch = new EbGpagcheq();
					    tch.setDnumcheq(x.getgPagCheq().getdNumCheq());
					    tch.setDbcoemi(x.getgPagCheq().getdBcoEmi());
					    //
					    tch.setCreatedBy(usrName);
					    tch.setCreatedOn(new java.util.Date());
					    tch.setModifiedBy(null);
					    tch.setModifiedOn(null);
					    tch.setOrgId(orgId);
					    tch.setPaconeiniId(coeiId);
					    tch.setUnitId(unitId);
					    m = EbGpagcheqDAO.addRow(tch, conn);
					    if ( m != null ) {
						    if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
							    return m;
						    }
					    }
				    }
			    }
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E640 - gPagCred                                                                                |
		     | Campos que describen la operación a crédito                                                    |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			if (cco.getgPagCred() != null) {
				event = "Pago a credito";
				EbGpagcred pcr = new EbGpagcred();
				pcr.setIcondcred(cco.getgPagCred().getiCondCred().getVal());
				pcr.setDdcondcred(cco.getgPagCred().getiCondCred().getDescripcion());
				pcr.setDplazocre(cco.getgPagCred().getdPlazoCre());
				pcr.setDcuotas(cco.getgPagCred().getdCuotas());
				pcr.setDmonent(0.0);
				if (cco.getgPagCred().getdMonEnt() != null) {
				    pcr.setDmonent(cco.getgPagCred().getdMonEnt().doubleValue());
				}
				//
				long pagCredId = UtilitiesDAO.getNextval("SQ_EB_GPAGCRED", conn);
				pcr.setCamcondId(condId);
				pcr.setCreatedBy(usrName);
				pcr.setCreatedOn(new java.util.Date());
				pcr.setIdentifier(pagCredId);
				pcr.setModifiedBy(null);
				pcr.setModifiedOn(null);
				pcr.setOrgId(orgId);
				pcr.setUnitId(unitId);
				m = EbGpagcredDAO.addRow(pcr, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
				/**
			     +------------------------------------------------------------------------------------------------+
			     | E650 - gCuotas                                                                                 |
			     | Campos que describen las cuotas                                                    |
			     +------------------------------------------------------------------------------------------------+		 
				 */
				if (cco.getgPagCred().getgCuotasList() != null) {
				    event = "Cuotas";
				    Iterator itr3 = cco.getgPagCred().getgCuotasList().iterator();
				    while (itr3.hasNext()) {
					    TgCuotas x = (TgCuotas) itr3.next();
					    EbGcuotas cuo = new EbGcuotas();
					    cuo.setCmonecuo(x.getcMoneCuo().name());
					    cuo.setDdmonecuo(x.getcMoneCuo().getDescripcion());
					    cuo.setDmoncuota( Precision.round(x.getdMonCuota().doubleValue(),4) );
					    
					    System.out.println("****************************************");
					    System.out.println("** MONTO CUOTA BRUTO : "+cuo.getDmoncuota() );
					    System.out.println("*****************************************");
					    
					    if (x.getdVencCuo() != null) {
					        cuo.setDvenccuo(java.sql.Date.valueOf(x.getdVencCuo()));
					    }
					    //
					    cuo.setCreatedBy(usrName);
					    cuo.setCreatedOn(new java.util.Date());
					    cuo.setModifiedBy(null);
					    cuo.setModifiedOn(null);
					    cuo.setOrgId(orgId);
					    cuo.setPagcredId(pagCredId);
					    cuo.setUnitId(unitId);
					    m = EbGcuotasDAO.addRow(cuo, conn);
					    if ( m != null ) {
						    if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
							    return m;
						    }
					    }
				    }
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | E700 - gCamItem                                                                                |
		     | Campos que describen los ítems de la operación                                                 |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Items de la operacion";
			Iterator itr4 = DE.getgDtipDE().getgCamItemList().iterator();
			while (itr4.hasNext()) {
				TgCamItem x = (TgCamItem) itr4.next();
				EbGcamitem itm = new EbGcamitem();
				long itmId = UtilitiesDAO.getNextval("SQ_EB_GCAMITEM", conn);
				itm.setDcodint(x.getdCodInt());
				itm.setDpararanc(x.getdParAranc());
				itm.setDncm(x.getdNCM());
				itm.setDdncpg(x.getdDncpG());
				itm.setDdncpe(x.getdDncpE());
				itm.setDgtin(x.getdGtin());
				itm.setDgtinpq(x.getdGtinPq());
				itm.setDdesproser(x.getdDesProSer());
				itm.setCunimed(x.getcUniMed().getVal());
				itm.setDdesunimed(x.getcUniMed().getDescripcion());
				itm.setDcantproser(x.getdCantProSer().doubleValue());
				if (x.getcPaisOrig() != null) {
				    itm.setCpaisorig(x.getcPaisOrig().name());
				    itm.setDdespaisorig(x.getcPaisOrig().getNombre());
				}
				itm.setDinfitem(x.getdInfItem());
				if (x.getcRelMerc() != null) {
					itm.setCrelmerc(x.getcRelMerc().getVal());
					itm.setDdesrelmerc(x.getcRelMerc().getDescripcion());
				}
				if (x.getdCanQuiMer() != null) {
					itm.setDcanquimer(x.getdCanQuiMer().doubleValue());
				} else {
					itm.setDcanquimer(0.0);
				}
				if (x.getdPorQuiMer() != null) {
					itm.setDporquimer(x.getdPorQuiMer().doubleValue());
				} else {
					itm.setDporquimer(0.0);
				}
				itm.setDcdcanticipo(x.getdCDCAnticipo());
				//
				itm.setCreatedBy(usrName);
				itm.setCreatedOn(new java.util.Date());
				itm.setIdentifier(itmId);
				itm.setModifiedBy(null);
				itm.setModifiedOn(null);
				itm.setOrgId(orgId);
				itm.setTipdeId(tdeId);
				m = EbGcamitemDAO.addRow(itm, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
				/**
			     +------------------------------------------------------------------------------------------------+
			     | E720 - gValorItem                                                                              |
			     | Campos que describen el precio, tipo de cambio y valor total de la operación por ítem          |
			     +------------------------------------------------------------------------------------------------+		 
				 */
				event = "Valores item " + itm.getDdesproser();
				EbGvaloritem vit = new EbGvaloritem();
				long valId = UtilitiesDAO.getNextval("SQ_EB_GVALORITEM", conn);
				vit.setDpuniproser(x.getgValorItem().getdPUniProSer().doubleValue());
				if (x.getgValorItem().getdTiCamIt() != null) {
				    vit.setDticamit(x.getgValorItem().getdTiCamIt().doubleValue());
				} else {
					vit.setDticamit(0.0);
				}
				vit.setDtotbruopeitem(x.getgValorItem().getdTotBruOpeItem().doubleValue());
				//
				vit.setIdentifier(valId);
				vit.setCamitemId(itmId);
				vit.setCreatedBy(usrName);
				vit.setCreatedOn(new java.util.Date());
				vit.setModifiedBy(null);
				vit.setModifiedOn(null);
				vit.setOrgId(orgId);
				vit.setUnitId(unitId);
				m = EbGvaloritemDAO.addRow(vit, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
				/**
			     +------------------------------------------------------------------------------------------------+
			     | EA001 - gValorRestaItem                                                                        |
			     | Campos que describen los descuentos, anticipos y valor total por ítem                          |
			     +------------------------------------------------------------------------------------------------+		 
				 */
				event = "Descuentos item " + itm.getDdesproser();
				EbGvalorrestaitem vri = new EbGvalorrestaitem();
				TgValorRestaItem r = x.getgValorItem().getgValorRestaItem();
				if (r.getdDescItem() != null) {
				    vri.setDdescitem(r.getdDescItem().doubleValue());
				} else {
				    vri.setDdescitem(0.0);
				}
				if (r.getdPorcDesIt() != null) {
				    vri.setDporcdesit(r.getdPorcDesIt().doubleValue());
				} else {
				    vri.setDporcdesit(0.0);					
				}
				if (r.getdDescGloItem() != null) {
				    vri.setDdescgloitem(r.getdDescGloItem().doubleValue());
				} else {
				    vri.setDdescgloitem(0.0);					
				}
				if (r.getdAntPreUniIt() != null) {
				    vri.setDantpreuniit(r.getdAntPreUniIt().doubleValue());
				} else {
				    vri.setDantpreuniit(0.0);					
				}
				if (r.getdAntGloPreUniIt() != null) {
				    vri.setDantglopreuniit(r.getdAntGloPreUniIt().doubleValue());
				} else {
				    vri.setDantglopreuniit(0.0);	
				}
				vri.setDtotopeitem(r.getdTotOpeItem().doubleValue());
				if (r.getdTotOpeGs() != null) {
				    vri.setDtotopegs(r.getdTotOpeGs().doubleValue());
				} else {
				    vri.setDtotopegs(0.0);					
				}
				//
				vri.setCreatedBy(usrName);
				vri.setCreatedOn(new java.util.Date());
				vri.setModifiedBy(null);
				vri.setModifiedOn(null);
				vri.setOrgId(orgId);
				vri.setUnitId(unitId);
				vri.setValoritemId(valId);
				m = EbGvalorrestaitemDAO.addRow(vri, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
				/**
			     +------------------------------------------------------------------------------------------------+
			     | E730 - gCamIVA                                                                                 |
			     | Campos que describen el IVA de la operación por ítem                                           |
			     +------------------------------------------------------------------------------------------------+		 
				 */
				event = "IVA item " + itm.getDdesproser();
				EbGcamiva iva = new EbGcamiva();
				TgCamIVA i = x.getgCamIVA();
				iva.setIafeciva(i.getiAfecIVA().getVal());
				iva.setDdesafeciva(i.getiAfecIVA().getDescripcion());
				iva.setDpropiva(i.getdPropIVA().longValue());
				iva.setDtasaiva(i.getdTasaIVA().shortValue());
				iva.setDbasgraviva(i.getdBasGravIVA().doubleValue());
				iva.setDliqivaitem(i.getdLiqIVAItem().doubleValue());
				//
				iva.setCamitemId(itmId);
				iva.setCreatedBy(usrName);
				iva.setCreatedOn(new java.util.Date());
				iva.setModifiedBy(null);
				iva.setModifiedOn(null);
				iva.setOrgId(orgId);
				iva.setUnitId(unitId);
				m = EbGcamivaDAO.addRow(iva, conn);
				if ( m != null ) {
					if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
						return m;
					}
				}
			}
			/**
		     +------------------------------------------------------------------------------------------------+
		     | F001 - gTotSub                                                                                 |
		     | Campos que describen los subtotales y totales de la transacción documentada                    |
		     +------------------------------------------------------------------------------------------------+		 
			 */
			event = "Totales y sub-totales";
			EbGtotsub ts = new EbGtotsub();
			TgTotSub o = DE.getgTotSub();
			ts.setDsubexe(0.0);
			if (o.getdSubExe() != null) {
			    ts.setDsubexe(o.getdSubExe().doubleValue());
			}
			ts.setDsubexo(0.0);
			if (o.getdSubExo() != null) {
			    ts.setDsubexo(o.getdSubExo().doubleValue());
			}
			ts.setDsub5(0.0);
			if (o.getdSub5() != null) {
			    ts.setDsub5(o.getdSub5().doubleValue());
			}
			ts.setDsub10(0.0);
			if (o.getdSub10() != null) {
			    ts.setDsub10(o.getdSub10().doubleValue());
			}
			ts.setDtotope(o.getdTotOpe().doubleValue());
			ts.setDtotdesc(o.getdTotDesc().doubleValue());
			ts.setDtotdescglotem(o.getdTotDescGlotem().doubleValue());
			ts.setDtotantitem(o.getdTotAntItem().doubleValue());
			ts.setDtotant(o.getdTotAnt().doubleValue());
			ts.setDporcdesctotal(o.getdPorcDescTotal().doubleValue());
			ts.setDdesctotal(o.getdDescTotal().doubleValue());
			ts.setDanticipo(o.getdAnticipo().doubleValue());
			ts.setDredon(o.getdRedon().doubleValue());
			ts.setDcomi(0.0);
			if (o.getdComi() != null) {
			    ts.setDcomi(o.getdComi().doubleValue());
			}
			ts.setDtotgralope(o.getdTotGralOpe().doubleValue());
			ts.setDiva5(0.0);
			if (o.getdIVA5() != null) {
			    ts.setDiva5(o.getdIVA5().doubleValue());
			}
			ts.setDiva10(0.0);
			if (o.getdIVA10() != null) {
			    ts.setDiva10(o.getdIVA10().doubleValue());
			}
			ts.setDliqtotiva5(0.0);
			if (o.getdLiqTotIVA5() != null) {
			    ts.setDliqtotiva5(o.getdLiqTotIVA5().doubleValue());
			}
			ts.setDliqtotiva10(0.0);
			if (o.getdLiqTotIVA10() != null) {
			    ts.setDliqtotiva10(o.getdLiqTotIVA10().doubleValue());
			}
			ts.setDivacomi(0.0);
			if (o.getdIVAComi() != null) {
			    ts.setDivacomi(o.getdIVAComi().doubleValue());
			}
			ts.setDtotiva(0.0);
			if (o.getdTotIVA() != null) {
			    ts.setDtotiva(o.getdTotIVA().doubleValue());
			}
			ts.setDbasegrav5(0.0);
			if (o.getdBaseGrav5() != null) {
			    ts.setDbasegrav5(o.getdBaseGrav5().doubleValue());
			}
			ts.setDbasegrav10(0.0);
			if (o.getdBaseGrav10() != null) {
			    ts.setDbasegrav10(o.getdBaseGrav10().doubleValue());
			}
			ts.setDtbasgraiva(0.0);
			if (o.getdTBasGraIVA() != null) {
			    ts.setDtbasgraiva(o.getdTBasGraIVA().doubleValue());
			}
			ts.setDtotalgs(0.0);
			if (o.getdTotalGs() != null) {
			    ts.setDtotalgs(o.getdTotalGs().doubleValue());
			}
			//
			ts.setCreatedBy(usrName);
			ts.setCreatedOn(new java.util.Date());
			ts.setDeId(ebDeId);
			ts.setModifiedBy(null);
			ts.setModifiedOn(null);
			ts.setOrgId(orgId);
			ts.setUnitId(unitId);
			m = EbGtotsubDAO.addRow(ts, conn);
			if ( m != null ) {
				if ( m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			//
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			m = new ApplicationMessage("CREATE-XML-IMG", event + ": " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		} finally {
			Util.closeJDBCConnection(conn);
		}	
	}
}
