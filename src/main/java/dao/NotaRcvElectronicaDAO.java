package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.roshka.sifen.core.types.TiMotEmi;

import pojo.CamposDEAsociado;
import pojo.CamposEmisorDE;
import pojo.CamposEspecificosDE;
import pojo.CamposFirmados;
import pojo.CamposGeneralesDE;
import pojo.CamposItemsOperacion;
import pojo.CamposIvaItem;
import pojo.CamposNotaElectronica;
import pojo.CamposOperacionComercial;
import pojo.CamposOperacionDE;
import pojo.CamposReceptorDE;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;
import pojo.CamposTimbrado;
import pojo.CamposValoresItem;
import pojo.DocumElectronico;
import pojo.OrganizationData;
import pojo.PosEbInvoiceItem;
import pojo.RcvEbInvoice;
import pojo.ReceiverData;
import util.UtilPOS;

public class NotaRcvElectronicaDAO {
		
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
	//
	final static short B2B = 1;
	final static short B2C = 2;
	final static short B2G = 3;
	final static short B2F = 4;
	//
	final static short OPERACION_PRESENCIAL = 1;
	final static short IVA_RENTA = 5;
	final static short VENTA_MERCADERIAS = 1;
	//
	final static short PERSONA_FISICA = 1;
	final static short PERSONA_JURIDICA = 2;
	final static short CONTRIBUYENTE = 1;
	final static short NO_CONTRIBUYENTE = 2;
	final static short GLOBAL = 1;
	final static short POR_ITEM = 2;
	final static short ANTICIPO_GLOBAL = 1;
	final static short ANTICIPO_POR_ITEM = 2;
	final static short SIN_ANTICIPO = 0;
	//
	final static short CONTADO = 1;
	final static short CREDITO = 2;
	//
	final static short PLAZO = 1;
	final static short CUOTA = 2;
	//
	
	public static ArrayList<DocumElectronico> getRcvMemos ( java.util.Date trxDate, int refNumber, int maxTrxQty) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		// el valor de este atributo ira incrementando a medida que se vayan cargando
		// transacciones al lote hasta llegar a "maxTrxQty"
		int trxCounter = 0;
		//
		RcvEbInvoice readTx = null;
		RcvEbInvoice previousTx = null;
		PosEbInvoiceItem readItem = null;
		PosEbInvoiceItem previousItem = null;
		int rowsCounter = 0;
		//
		DocumElectronico rDE;
		OrganizationData org;
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

			// obrener los datos generales del emisor
			org = EmisorDocumentosDAO.getOrgData(1);

			// ejecutar la consulta de base de datos
			StringBuffer buffer = new StringBuffer();

			// configuracion de la fecha
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			// datos de la cabecera de la transaccion 
			buffer.append("select h.DNUMTIM, h.DEST, h.DPUNEXP, h.DNUMDOC,");
			buffer.append(" h.DSERIENUM, h.DFEINIT, to_date('31125000', 'ddmmyyyy') DFEFINT,");
			buffer.append(" to_char(h.DEFEEMIDE, 'dd/mm/yyyy hh24:mi:ss') DEFEEMIDE,");
			//buffer.append(" h.DEFEEMIDE,");
			buffer.append(" h.ITIPTRA, h.DDESTIPTRA, h.ITIMP, h.DDESTIMP,");
			buffer.append(" h.CMONEOPE, h.DDESMONEOPE, h.DTICAM,");
			buffer.append(" h.STADDRESS, h.STBUILDINGNO, h.STADDRESS2, h.STADDRESS3,");
			buffer.append(" h.STPHONE, h.STEMAIL, STNAME,");
			buffer.append(" h.TAXNUMBER, h.TAXNUMBERCD, h.IDENTITYTYPE, h.IDENTITYNUMBER, h.CUSTOMERNAME,");
			buffer.append(" h.ADDRESS, h.BUILDINGNUMBER, h.PHONE, h.ORGTYPE,");
			buffer.append(" h.EMAIL, h.TRXCONDITION, h.COLLECTIONID, h.INSTALLMENTSQTY,");
			buffer.append(" h.INSTDAYSINTERVAL, h.INITIALPAYMENT,");
			buffer.append(" h.IINDPRES, h.DDESINDPRES, to_char(h.REMISSIONDATE, 'dd/mm/yyyy hh24:mi:ss') REMISSIONDATE, h.ITIOPE,");			
			buffer.append(" h.STCOUNTRYCODE, h.STSTATECODE, h.STCOUNTYCODE, h.STCITYCODE,");
			buffer.append(" h.STCOUNTRYNAME, h.STSTATENAME, h.STCOUNTYNAME, h.STCITYNAME,");
			buffer.append(" h.TXCOUNTRYCODE, h.TXSTATECODE, h.TXCOUNTYCODE, h.TXCITYCODE,");
			buffer.append(" h.TXCOUNTRYNAME, h.TXSTATENAME, h.TXCOUNTYNAME, h.TXCITYNAME,");
			buffer.append(" h.CONTROLCODE, h.IDENTIFIER, h.CANCELED_ON, h.RECEIVABLESTYPE,");
			buffer.append(" h.CMINVOICEID, h.CMCASHID, h.CMCONTROLID,");
			// campos de los items de la transaccion
			buffer.append(" x.DCODINT, x.DDESPROSER, x.QTYHANDLING,");
			buffer.append(" x.QUANTITY, x.UNITPRICE, x.DISCOUNTAMOUNT, x.DISCOUNTPCT,");
			buffer.append(" x.UNITDISCAMOUNT, x.TAXTYPEID, x.TAXRATE,");
			buffer.append(" x.PRODUCTID");
			// condiciones de la consulta
			buffer.append(" from RCV_EB_INVOICES h,");
			buffer.append(" RCV_EB_INVOICE_ITEMS x");
			
		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = h.IDENTIFIER )");
			
			buffer.append(" and x.TRANSACTIONID = h.IDENTIFIER");
			buffer.append(" and h.ITIPTRA in (5, 6)");
			buffer.append(" and h.DEFEEMIDE < ?");
			buffer.append(" and h.DEFEEMIDE >= ?");
			buffer.append(" and h.REF_NUMBER = ?");

			//buffer.append(" and h.IDENTIFIER = 222716");
			
			//
			//buffer.append(" and x.TRN_DATE <= to_date('29/12/2022 08:20:00', 'dd/mm/yyyy hh24:mi:ss')");
			//buffer.append(" and x.TRN_DATE >= to_date('29/12/2022 07:00:00', 'dd/mm/yyyy hh24:mi:ss')");
			buffer.append(" order by h.IDENTIFIER");
			//
			ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));
			ps.setString(3, String.valueOf(refNumber));
			rs = ps.executeQuery();
			// inicializar las variables de corte de control
			previousTx = null;
			previousItem = null;
			ArrayList<CamposItemsOperacion> gCamItem = new ArrayList<CamposItemsOperacion>();
			// arreglo para almacenar la lista de documentos electronicos auxiliares
			ArrayList<DocumElectronico> deList = new ArrayList<DocumElectronico>();
			while (rs.next()) {
				dataFound = true;
				rowsCounter++;
				//
				//System.out.println("Leyendo: " + rs.getLong("IDENTIFIER") + " - " + rs.getString("DDESPROSER"));
				readTx = new RcvEbInvoice();
				readTx.setAddress(rs.getString("ADDRESS"));
				readTx.setBuildingnumber(rs.getString("BUILDINGNUMBER"));
				readTx.setIdentifier(rs.getLong("IDENTIFIER"));
				readTx.setCanceledOn(rs.getDate("CANCELED_ON"));
				readTx.setCmoneope(rs.getString("CMONEOPE"));
				readTx.setCollectionid(rs.getLong("COLLECTIONID"));
				readTx.setControlcode(rs.getString("CONTROLCODE"));
				readTx.setCustomername(rs.getString("CUSTOMERNAME"));
				readTx.setIindpres(rs.getInt("IINDPRES"));
				readTx.setDdesindpres(rs.getString("DDESINDPRES"));
				readTx.setDdesmoneope(rs.getString("DDESMONEOPE"));
				readTx.setDdestimp(rs.getString("DDESTIMP"));
				readTx.setDdestiptra(rs.getString("DDESTIPTRA"));
				readTx.setDefeemide(rs.getString("DEFEEMIDE"));
				readTx.setDest(rs.getString("DEST"));
				readTx.setDfeinit(rs.getDate("DFEINIT"));
				readTx.setDnumdoc(rs.getString("DNUMDOC"));
				readTx.setDnumtim(rs.getString("DNUMTIM"));
				readTx.setDpunexp(rs.getString("DPUNEXP"));	
				readTx.setDserienum(rs.getString("DSERIENUM"));
				readTx.setDticam(rs.getDouble("DTICAM"));
				readTx.setEmail(rs.getString("EMAIL"));
				readTx.setIdentitynumber(rs.getString("IDENTITYNUMBER"));
				readTx.setIdentitytype(rs.getString("IDENTITYTYPE"));
				readTx.setInitialpayment(rs.getDouble("INITIALPAYMENT"));
				readTx.setInstallmentsqty(rs.getInt("INSTALLMENTSQTY"));
				readTx.setInstdaysinterval(rs.getInt("INSTDAYSINTERVAL"));
				readTx.setItimp(rs.getShort("ITIMP"));
				readTx.setItiope(rs.getShort("ITIOPE"));
				readTx.setItiptra(rs.getShort("ITIPTRA"));
				readTx.setOrgtype(rs.getString("ORGTYPE"));
				readTx.setPhone(rs.getString("PHONE"));
				readTx.setRefNumber(String.valueOf(refNumber));
				readTx.setRemissiondate(rs.getString("REMISSIONDATE"));
				readTx.setReceivablesType(rs.getString("RECEIVABLESTYPE"));
				readTx.setCmInvoiceId(rs.getLong("CMINVOICEID"));
				readTx.setCmControlId(rs.getLong("CMCONTROLID"));
				readTx.setCmCashId(rs.getLong("CMCASHID"));
				
				readTx.setStaddress(rs.getString("STADDRESS"));
				readTx.setStaddress2(rs.getString("STADDRESS2"));
				readTx.setStaddress3(rs.getString("STADDRESS3"));
				readTx.setStbuildingno(rs.getString("STBUILDINGNO"));
				
				readTx.setStCityCode(rs.getShort("STCITYCODE"));
				readTx.setStCityName(rs.getString("STCITYNAME"));
				readTx.setStCountryCode(rs.getString("STCOUNTRYCODE"));
				readTx.setStCountryName(rs.getString("STCOUNTRYNAME"));
				readTx.setStCountyCode(rs.getShort("STCOUNTYCODE"));
				readTx.setStCountyName(rs.getString("STCOUNTYNAME"));
				readTx.setStStateCode(rs.getShort("STSTATECODE"));
				readTx.setStStateName(rs.getString("STSTATENAME"));
				
				readTx.setTxCityCode(rs.getShort("TXCITYCODE"));
				readTx.setTxCityName(rs.getString("TXCITYNAME"));
				readTx.setTxCountryCode(rs.getString("TXCOUNTRYCODE"));
				readTx.setTxCountryName(rs.getString("TXCOUNTRYNAME"));
				readTx.setTxCountyCode(rs.getShort("TXCOUNTYCODE"));
				readTx.setTxCountyName(rs.getString("TXCOUNTYNAME"));
				readTx.setTxStateCode(rs.getShort("TXSTATECODE"));
				readTx.setTxStateName(rs.getString("TXSTATENAME"));

				readTx.setStemail(rs.getString("STEMAIL"));
				readTx.setStname(rs.getString("STNAME"));
				readTx.setStphone(rs.getString("STPHONE"));
				readTx.setTaxnumber(rs.getString("TAXNUMBER"));
				readTx.setTaxnumbercd(rs.getString("TAXNUMBERCD"));
				readTx.setTrxcondition(rs.getString("TRXCONDITION"));

				//
				readItem = new PosEbInvoiceItem();
				readItem.setTransactionid(readTx.getIdentifier());
				readItem.setIdentifier(rs.getLong("IDENTIFIER"));
				readItem.setDcodint(rs.getString("DCODINT"));
				readItem.setDdesproser(rs.getString("DDESPROSER"));
				readItem.setDiscountamount(rs.getDouble("DISCOUNTAMOUNT"));
				readItem.setDiscountpct(rs.getDouble("DISCOUNTPCT"));
				readItem.setProductid(rs.getLong("PRODUCTID"));
				readItem.setQtyhandling(rs.getString("QTYHANDLING"));
				readItem.setQuantity(rs.getDouble("QUANTITY"));
				readItem.setTaxrate(rs.getDouble("TAXRATE"));
				readItem.setTaxtypeid(rs.getLong("TAXTYPEID"));
				readItem.setUnitdiscamount(rs.getDouble("UNITDISCAMOUNT"));
				readItem.setUnitprice(rs.getDouble("UNITPRICE"));
				//
				if (rowsCounter == 1) {
					previousTx = readTx;
					previousItem = readItem;
				}
				// comparar el registro leido con el anterior
				if (readTx.getIdentifier() != previousTx.getIdentifier()) {
				    //System.out.println("Construyendo lista de pagos: " + previousTx.getIdentifier() );
					// cuando hay cambio de cabecera de documento se debe generar el DE 
					// y almacenarlo en el arreglo
				    trxCounter++;
				    //System.out.println("Cantidad de elementos: " + trxCounter);
					if (trxCounter <= maxTrxQty) {
				        //System.out.println("Cargando documento auxiliar: " + previousTx.getIdentifier() );
				        rDE = loadDocument( previousTx, org, gCamItem, conn);
				        System.out.println("Documento auxiliar cargado: " + previousTx.getIdentifier() + " CDC: " + previousTx.getControlcode() );
				        deList.add(rDE);
					}
				    //System.out.println("****** Contador de transacciones: " + counter + "****** " + cashId + "-" + controlId + "-" + invoiceId);
				    if (maxTrxQty > 0) {
					    if (trxCounter >= maxTrxQty) {
						    break;
					    }
				    }
				    // mover al registro anterior los valores del registro actual
				    previousTx = readTx;
				    previousItem = readItem;
					gCamItem = new ArrayList<CamposItemsOperacion>();
				} 
				// si no hubo cambio en los valores de cabecera del nuevo registro leido con respecto
				// al anterior, se debe cargar los valores de la linea en el arreglo de items
				CamposItemsOperacion o = setItemValues ( readItem, readTx.getDticam());
				gCamItem.add(o);
			}
			if (dataFound == true) {
			    trxCounter++;
			    //System.out.println("Cantidad de elementos: " + trxCounter);
				if (trxCounter <= maxTrxQty) {
			        // cargar la ultima transaccion leida
		            //System.out.println("Cargando al arreglo ultimo Id: " + previousTx.getIdentifier() );
		            rDE = loadDocument( previousTx, org, gCamItem, conn);
		            System.out.println("Cargado ultimo: " + previousTx.getIdentifier() + " CDC: " + previousTx.getControlcode() );
		            deList.add(rDE);
				}
		        //
		        //System.out.println("retornando lista con tamanho: " + deList.size());
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
	
	private static DocumElectronico loadDocument ( RcvEbInvoice p, 
			                                       OrganizationData org, 
			                                       ArrayList<CamposItemsOperacion> itms,
			                                       Connection conn ) {
		short NOTA_CREDITO = 5;
		short NOTA_DEBITO = 6;
		DocumElectronico rDE;
		CamposFirmados DE;
		CamposOperacionDE gOpeDE;
		CamposGeneralesDE gDatGralOpe;
		CamposReceptorDE gDatRec;
		CamposEmisorDE gEmis;
		CamposResponsableDE gRespDE;
		CamposOperacionComercial gOpeComer;
		CamposEspecificosDE gDtipDE;
		CamposNotaElectronica gCamNCDE;
		CamposSubtotalesTotales gTotSub;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat tf = new java.text.SimpleDateFormat("HH:mm:ss");
		//
		//System.out.println("1a");
	    rDE = new DocumElectronico();
	    DE = new CamposFirmados();
	    if (p.getControlcode() != null) {
			//System.out.println("2a");
	        DE.setId(p.getControlcode());
	        DE.setdDVId(p.getControlcode().substring(p.getControlcode().length()));
	    }
		//System.out.println("3a");
	    java.util.Date signatureDate = new java.util.Date();
	    String signDate = df.format(signatureDate);
	    String signTime = tf.format(signatureDate);
	    DE.setdFecFirma(signDate + "T" + signTime);
	    // 1=Sistema de facturación del contribuyente
	    // 2=SIFEN solución gratuita
	    DE.setdSisFact(new Short("1"));
		//System.out.println("4a");
     	//
		//System.out.println("5a");
	    gOpeDE = new CamposOperacionDE();
	    gOpeDE.setiTipEmi(NORMAL);
	    gOpeDE.setdDesTipEmi("Normal");
        gOpeDE.setdInfoEmi("Notas Credito Mayoristas");
	    if (p.getItiptra() == NOTA_DEBITO) {
	        gOpeDE.setdInfoEmi("Notas Debito Mayoristas");
	    }
	    gOpeDE.setdInfoFisc(null);
	    // si se trata de un CDC pre-generado, asignar el valor correspondiente
	    if (p.getControlcode() != null) {
	    	    gOpeDE.setdCodSeg(p.getControlcode().substring(34, 43));
	    }
	    DE.setgOpeDE(gOpeDE);
	    // datos del timbrado
		//System.out.println("6a");
	    CamposTimbrado gTimb = setgTimbValues ( p );
	    DE.setgTimb(gTimb);
	    // campos generales DE
		//System.out.println("7a");
	    gDatGralOpe = new CamposGeneralesDE();
	    try {
	        d = sdf.parse(p.getDefeemide());
	        //d = p.getDefeemide();
	    } catch ( Exception e) {
	    	    e.printStackTrace();
	    }
		//System.out.println("8a");
	    gDatGralOpe.setdFeEmiDE(d);
	    // datos del emisor
		//System.out.println("9a");
	    gEmis = setgEmisValues ( p, org );
	    gDatGralOpe.setgEmis(gEmis);
	    // datos del receptor
		//System.out.println("10a");
		ReceiverData rcv = setReceiverData(p);
	    FacturasUtils f = new FacturasUtils();
	    gDatRec = f.datosReceptor ( rcv );
	    gDatGralOpe.setgDatRec(gDatRec);
	    // datos de la operacion comercial
		//System.out.println("11a");
	    gOpeComer = setgOpeComerValues ( p );
	    gDatGralOpe.setgOpeComer(gOpeComer);
	    // campos especificos de la nota electronica
		//System.out.println("12a");
     	gDtipDE = new CamposEspecificosDE();	    
	    gCamNCDE = getMemoValues(p.getReceivablesType());
	    gDtipDE.setgCamNCDE(gCamNCDE);
	    /**
 	     +--------------------------------------------------------------+
	     | los items de la operacion han sido cargados dentro del ciclo |
	     | de cada cabecera                                             |
	     +--------------------------------------------------------------+
	    */
	    // campos correspondientes a los items de la operacion
	    // el arreglo fue cargando en cada lectura en el objeto anterior
		//System.out.println("18a");
	    gDtipDE.setgCamItem(itms);
	    DE.setgDtipDE(gDtipDE);
	    //
		//System.out.println("19a");
	    DE.setgDatGralOpe(gDatGralOpe);
	    // asignar el codigo de control, y el codigo qr si fueron generados previamente
	    if (p.getControlcode() != null) {
		    DE.setId(p.getControlcode());
		    int pos = p.getControlcode().length() - 1;
		    DE.setdDVId(p.getControlcode().substring(pos));
	    }
	    /**
 	     +--------------------------------------------------------------+
	     | Asignar los documentos asociados                             |
 	     +--------------------------------------------------------------+	     
	     */
		CamposDEAsociado gCamDEAsoc = new CamposDEAsociado();
		if (p.getCmCashId() == 0) {
			System.out.println("cargando asociado rcv: " + p.getCmInvoiceId());
			gCamDEAsoc = NotaRcvElectronicaDAO.getRcvInvoice(p.getCmInvoiceId(), conn);
			ArrayList<CamposDEAsociado> l = new ArrayList<CamposDEAsociado>();
			l.add(gCamDEAsoc);
			DE.setgCamDEAsoc(l);
		} else {
			System.out.println("cargando asociado pos: " + p.getCmInvoiceId());
			gCamDEAsoc = NotaRcvElectronicaDAO.getPosInvoice(p.getCmInvoiceId(), p.getCmControlId(), p.getCmCashId(), conn);
			ArrayList<CamposDEAsociado> l = new ArrayList<CamposDEAsociado>();
			l.add(gCamDEAsoc);
			DE.setgCamDEAsoc(l);
		}				
	    
		//System.out.println("20a");
	    rDE.setDE(DE);
	    rDE.setTransactionId(p.getIdentifier());
		//System.out.println("21a");
	    return rDE;
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
	    gTimb.setiTiDE(NOTA_CR_ELECTRONICA);
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
	
	private static ReceiverData setReceiverData ( RcvEbInvoice x ) {
		ReceiverData rcv = new ReceiverData();
		rcv.setOperationType((short)x.getItiope());
		rcv.setAddress(null);
		if (x.getAddress() != null) {
			if (x.getTxStateCode() != 0) {
		        rcv.setAddress(x.getAddress());
			}
		}
		rcv.setAltenativeName(null);
		rcv.setBuildingNo(x.getBuildingnumber());
		rcv.setCellPhone(null);
		rcv.setCustomerCode(null);
		rcv.seteMail(x.getEmail());
		rcv.setIdentityNumber(x.getIdentitynumber());
		rcv.setIdentityType(x.getIdentitytype());
		rcv.setName(x.getCustomername());
		if (x.getOrgtype() != null) {
		    rcv.setOrgType(x.getOrgtype());
		} else {
		    rcv.setOrgType("INDIVIDUO");
		}
		rcv.setPhone(x.getPhone());

		rcv.setCountryCode(x.getStCountryCode());
		rcv.setCountryName(x.getStCountryName());
		if (x.getTxCountryCode() != null) {
		    rcv.setCountryCode(x.getTxCountryCode());
		    rcv.setCountryName(x.getTxCountryName());
		}
		if (x.getTxCityCode() != 0) {
		    rcv.setCityCode(x.getTxCityCode());
		    rcv.setCityName(x.getTxCityName());
		}
		if (x.getTxCountyCode() != 0) {
		    rcv.setCountyCode(x.getTxCountyCode());
		    rcv.setCountyName(x.getTxCountyName());
		}
		if (x.getTxStateCode() != 0) {
		    rcv.setStateCode(x.getTxStateCode());
		    rcv.setStateName(x.getTxStateName());
		}
		
		if (x.getTaxnumber() != null) {
			if (x.getTaxnumber().length() >= 5) {
				rcv.setTaxNumber(x.getTaxnumber());						
			} else {
				rcv.setTaxNumber(null);						
			}
		} else {
			rcv.setTaxNumber(null);
		}
		//
		return rcv;
	}
	
	private static CamposOperacionComercial setgOpeComerValues ( RcvEbInvoice x ) {
	    // datos de la operacion comercial
		CamposOperacionComercial o = new CamposOperacionComercial ();
	    o.setcMoneOpe(x.getCmoneope());
	    o.setdDesMoneOpe(x.getDdesmoneope());
	    if (x.getCmoneope().equalsIgnoreCase("PYG") == false) {
		    // el paquete Roshka Sifen solo agrega los datos del tipo de cambio 
		    // si el codigo de la moneda es igual a PYG
		    // mascara: 1-5p(0-4)
		    o.setdTiCam(new BigDecimal(x.getDticam()));
		    o.setdCondTiCam(GLOBAL);
	    } else {
		    o.setdTiCam(BigDecimal.ZERO);
		    o.setdCondTiCam(Short.valueOf("0"));	            	
	    }
	    // TODO: Consultar de que se trata este dato
	    o.setiCondAnt(ANTICIPO_GLOBAL);
	    o.setdDesCondAnt("Anticipo Global");
	    // este dato se debe derivar de la organizacion
	    if (x.getItimp() != 0) {
	        o.setiTImp((short)x.getItimp());
	        o.setdDesTImp(x.getDdestimp());
     	} else {
	        o.setiTImp(IVA_RENTA);
	        o.setdDesTImp("IVA – Renta");					
	    }
	    //
	    if (x.getItiptra() != 0) {
	        o.setiTipTra((short)x.getItiptra());
	        o.setdDesTipTra(x.getDdestiptra());
	    } else {
	        o.setiTipTra(VENTA_MERCADERIAS);
	        o.setdDesTipTra("Venta de mercadería");					
	    }
		//
	    return o;
	}
	
	private static CamposItemsOperacion setItemValues ( PosEbInvoiceItem x, double exchgRate ) {
		CamposItemsOperacion o = new CamposItemsOperacion();
        o.setcPaisOrig(null);
        o.setcRelMerc(Short.valueOf("0"));
        o.setcUniMed(Short.valueOf("77"));
        o.setdDesUniMed("UNIDAD");	        	    	
        o.setdCanQuiMer(null);
	    double quantity = UtilPOS.appRound(x.getQuantity(), 4);
	    o.setdCantProSer(BigDecimal.valueOf(quantity));
        o.setdCDCAnticipo(null);
        o.setdCodInt(x.getDcodint());
        o.setdDesPaisOrig(null);
        o.setdDesProSer(x.getDdesproser());
        o.setdDesRelMerc(null);
        o.setdDncpE(null);
        o.setdDncpG(null);
        o.setdGtin(0);
        o.setdGtinPq(0);
        o.setdInfItem(null);
        o.setdNCM(0);
        o.setdParAranc(Short.valueOf("0"));
        o.setdPorQuiMer(null);
        // completar los valores del item
        double unitPrice = UtilPOS.appRound(x.getUnitprice(), 8);
        double unitDiscount = UtilPOS.appRound((x.getDiscountamount() / x.getQuantity()), 8);
        double taxRate = x.getTaxrate();
        double itemDiscPct = x.getDiscountpct();
        double globalDiscAmt = 0.0;
        double itemAdvAmt = 0.0;
        double globalAdvAmt = 0.0;
        double itemExchgRate = exchgRate;
        ItemsUtils utl = new ItemsUtils();
        CamposValoresItem v = utl.calcItemValues ( quantity, 
	                                               unitPrice, 
	                                               unitDiscount, 
	                                               itemDiscPct, 
	                                               globalDiscAmt, 
	                                               itemAdvAmt, 
	                                               globalAdvAmt, 
	                                               itemExchgRate );
        o.setgValorItem(v);
        //System.out.println(o.getcUniMed() + " - " + o.getdDesProSer() + " - " + v.getdPUniProSer());
        // Completar los campos del IVA
        double itemAmount = v.getgValorRestaItem().getdTotOpeItem().doubleValue();
        CamposIvaItem t = utl.calcTaxValues(taxRate, itemAmount);
        o.setgCamIVA(t);
	    //
	    return o;
	}
	
	private static CamposNotaElectronica getMemoValues ( String receivableType ) {
		CamposNotaElectronica o = new CamposNotaElectronica();
		short iMotEmi = 0;
		String dDesMotEmi = null;
		//
		if (receivableType.equalsIgnoreCase("DEVOLUCION")) {
			iMotEmi = TiMotEmi.DEVOLUCION.getVal();
			dDesMotEmi = TiMotEmi.DEVOLUCION.getDescripcion();
		}
		if (receivableType.equalsIgnoreCase("DESCUENTOS")) {
			iMotEmi = TiMotEmi.DESCUENTO.getVal();
			dDesMotEmi = TiMotEmi.DESCUENTO.getDescripcion();
		}
		if (receivableType.equalsIgnoreCase("VENTA-LOCAL")) {
			// este mapeo es arbitario
			iMotEmi = TiMotEmi.DEVOLUCION.getVal();
			dDesMotEmi = TiMotEmi.DEVOLUCION.getDescripcion();
		}
		if (receivableType.equalsIgnoreCase("ACUERDO-COMERCIAL")) {
			iMotEmi = TiMotEmi.BONIFICACION.getVal();
			dDesMotEmi = TiMotEmi.BONIFICACION.getDescripcion();
		}
		// si el motivo de la nota no es ninguno de los anteriores, tomar por defecto
		// el valor correspondiente a "DEVOLUCION"
		if (dDesMotEmi == null) {
			iMotEmi = TiMotEmi.DEVOLUCION.getVal();
			dDesMotEmi = TiMotEmi.DEVOLUCION.getDescripcion();			
		}
		//
		o.setiMotEmi(iMotEmi);
		o.setdDesMotEmi(dDesMotEmi);
		return o;
	}

	public static CamposDEAsociado getRcvInvoice ( long invoiceId, Connection conn ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		//
	    final short ELECTRONICO = 1;
		final short IMPRESO = 2;
		final short CONST_ELECTRONICA = 3;
		//
		final short FACTURA = 1;
		//
		try {
		    // ejecutar la consulta de base de datos
		    StringBuffer buffer = new StringBuffer();

		    /*
 identifier                     number(12) not null,
 transactionId                  number(10) not null, 
 created_by                     varchar2(15) not null, 
 created_on                     date not null, 
 docTypeCode                    number(1) not null,
 refControlCode                 varchar2(44),
 refStampNo                     varchar2(8), 
 refEstablishment               varchar2(3), 
 refIssuePoint                  varchar2(3), 
 refDocumentNo                  varchar2(7), 
 printedDocType                 number(1), 
 refDocDate                     date, 
 withholdingNo                  varchar2(15),
 cashId                         number(10), 
 cashControlId                  number(10)
		     */
		    buffer.append("select x.refControlCode DCDCDEREF,");
		    buffer.append(" x.refStampNo DNTIMDI,");
		    buffer.append(" x.refEstablishment DESTDOCASO,");
		    buffer.append(" x.refIssuePoint DPEXPDOCASO,");
		    buffer.append(" x.refDocumentNo DNUMDOCASO,");
		    buffer.append(" x.refDocDate DFECEMIDI");
		    
		    buffer.append(" from RCV_EB_ASSOCIATED_TRX x");

		    buffer.append(" where x.transactionId = ?");

		    ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, invoiceId);
		    rs = ps.executeQuery();
		
		    CamposDEAsociado o = new CamposDEAsociado();
		    if (rs.next()) {
			    dataFound = true;
			    if (rs.getString("DCDCDEREF") == null) {
			        // iTipDocAso
			        o.setiTipDocAso(IMPRESO);	
			        // dDesTipDocAso
			        o.setdDesTipDocAso("Impreso");
			        // dCdCDERef
			        o.setdCdCDERef(null);
			        if (rs.getString("DCDCDEREF") != null) {
			            o.setdCdCDERef(rs.getString("DCDCDEREF"));
			        }
			        // dNTimDI
			        o.setdNTimDI(rs.getString("DNTIMDI"));
			        // dEstDocAso
			        o.setdEstDocAso(rs.getString("DESTDOCASO"));
			        // dPExpDocAso
			        o.setdPExpDocAso(rs.getString("DPEXPDOCASO"));
			        // dNumDocAso
			        o.setdNumDocAso(rs.getString("DNUMDOCASO"));
			        // iTipoDocAso
			        o.setiTipoDocAso(FACTURA);
			        // dDTipoDocAso
			        o.setdDesTipDocAso("Factura");
				    // dFecEmiDI
				    o.setdFecEmiDI(rs.getDate("DFECEMIDI"));
			    } else {
				    // iTipDocAso
				    o.setiTipDocAso(ELECTRONICO);			    	
				    // dDesTipDocAso
				    o.setdDesTipDocAso("Electronico");
				    // dCdCDERef
				    o.setdCdCDERef(rs.getString("DCDCDEREF"));
				    // dNTimDI
				    o.setdNTimDI(null);
				    // dEstDocAso
				    o.setdEstDocAso(null);
				    // dPExpDocAso
				    o.setdPExpDocAso(null);
				    // dNumDocAso
				    o.setdNumDocAso(null);
				    // iTipoDocAso
				    o.setiTipoDocAso(Short.valueOf("0"));
				    // dDTipoDocAso
				    o.setdDesTipDocAso(null);
				    // dFecEmiDI
				    o.setdFecEmiDI(null);
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
		    	System.out.println("1.cargando docAso: " + o.getiTipDocAso());
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
		//
		final short FACTURA = 1;
		//
		try {
		    // ejecutar la consulta de base de datos
		    StringBuffer buffer = new StringBuffer();

		    /*
 identifier                     number(12) not null,
 transactionId                  number(10) not null, 
 created_by                     varchar2(15) not null, 
 created_on                     date not null, 
 docTypeCode                    number(1) not null,
 refControlCode                 varchar2(44),
 refStampNo                     varchar2(8), 
 refEstablishment               varchar2(3), 
 refIssuePoint                  varchar2(3), 
 refDocumentNo                  varchar2(7), 
 printedDocType                 number(1), 
 refDocDate                     date, 
 withholdingNo                  varchar2(15),
 cashId                         number(10), 
 cashControlId                  number(10)
		    */
		    buffer.append("select x.refControlCode DCDCDEREF,");
		    buffer.append(" x.refStampNo DNTIMDI,");
		    buffer.append(" x.refEstablishment DESTDOCASO,");
		    buffer.append(" x.refIssuePoint DPEXPDOCASO,");
		    buffer.append(" x.refDocumentNo DNUMDOCASO,");
		    buffer.append(" x.refDocDate DFECEMIDI");
		    
		    buffer.append(" from RCV_EB_ASSOCIATED_TRX x");

		    buffer.append(" where x.cashId = ?");
		    buffer.append(" and x.cashControlId = ?");
		    buffer.append(" and x.transactionId = ?");

		    ps = conn.prepareStatement(buffer.toString());
		    ps.setLong(1, cashId);
		    ps.setLong(2, controlId);
		    ps.setLong(3, invoiceId);
		    rs = ps.executeQuery();
		
		    CamposDEAsociado o = new CamposDEAsociado();
		    if (rs.next()) {
			    dataFound = true;
			    if (rs.getString("DCDCDEREF") == null) {
			        // iTipDocAso
			        o.setiTipDocAso(IMPRESO);	
			        // dDesTipDocAso
			        o.setdDesTipDocAso("Impreso");
			        // dCdCDERef
			        o.setdCdCDERef(null);
			        if (rs.getString("DCDCDEREF") != null) {
			            o.setdCdCDERef(rs.getString("DCDCDEREF"));
			        }
			        // dNTimDI
			        o.setdNTimDI(rs.getString("DNTIMDI"));
			        // dEstDocAso
			        o.setdEstDocAso(rs.getString("DESTDOCASO"));
			        // dPExpDocAso
			        o.setdPExpDocAso(rs.getString("DPEXPDOCASO"));
			        // dNumDocAso
			        o.setdNumDocAso(rs.getString("DNUMDOCASO"));
			        // iTipoDocAso
			        o.setiTipoDocAso(FACTURA);
			        // dDTipoDocAso
			        o.setdDesTipDocAso("Factura");
				    // dFecEmiDI
				    o.setdFecEmiDI(rs.getDate("DFECEMIDI"));
			    } else {
				    // iTipDocAso
				    o.setiTipDocAso(ELECTRONICO);			    	
				    // dDesTipDocAso
				    o.setdDesTipDocAso("Electronico");
				    // dCdCDERef
				    o.setdCdCDERef(rs.getString("DCDCDEREF"));
				    // dNTimDI
				    o.setdNTimDI(null);
				    // dEstDocAso
				    o.setdEstDocAso(null);
				    // dPExpDocAso
				    o.setdPExpDocAso(null);
				    // dNumDocAso
				    o.setdNumDocAso(null);
				    // iTipoDocAso
				    o.setiTipoDocAso(Short.valueOf("0"));
				    // dDTipoDocAso
				    o.setdDesTipDocAso(null);
				    // dFecEmiDI
				    o.setdFecEmiDI(null);
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
		    	System.out.println("2.cargando docAso: " + o.getiTipDocAso());
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
