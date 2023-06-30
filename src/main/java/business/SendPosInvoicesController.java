package business;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.dsig.SignedInfo;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.internal.helpers.SignatureHelper;

import dao.CashRegisterDAO;
import dao.PosOptionsDAO;
import dao.PosTransactionsDAO;
import dao.RcvTaxMailingListDAO;
import dao.RcvTaxPayersDAO;
import pojo.CashRegister;
import pojo.EdIdBuildingParams;
import pojo.PosInvoice;
import pojo.PosOption;
import pojo.QrBuildingParams;
import pojo.RcvTaxMailingList;
import pojo.RcvTaxPayer;
import sifen.SendPosInvoicesAsync;
import tools.DEUtils;
import tools.GenerateInvoicePdf;
import util.SendEmail;

public class SendPosInvoicesController {
	/* 
	 * +-------------------------------------------------------------+
	 * |                                                             | 
	 * |                                                             |
	 * | Atributos de la clase de control                            |
	 * |                                                             |
	 * |                                                             | 
	 * +-------------------------------------------------------------+	
	 */
	// global variables
	// business logic variables
	private PosOption posOpts;
	private String xmlFolder;
	private String event;
	private long cashId;
	private String cashName;
	private String invCondition;
	private String taxNumber;
	private String taxName;
	private String identityNumber;
	private String customerName;
	private String eMailAddress;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private long fromNumber;
	private long toNumber;
	private PosInvoice selectedTransaction;
	private CashRegister cash;
	private long selectedTransactionId;
	private ArrayList<PosInvoice> transactionsList = new ArrayList<PosInvoice> ();
	private PosInvoicesTM sendTableModel;
	private PosDeliverDocumsTM deliverTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}

	public java.util.Date getToDate() {
		return toDate;
	}

	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}

	public long getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(long fromNumber) {
		this.fromNumber = fromNumber;
	}

	public long getToNumber() {
		return toNumber;
	}

	public void setToNumber(long toNumber) {
		this.toNumber = toNumber;
	}

	public PosInvoice getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(PosInvoice selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public CashRegister getCash() {
		return cash;
	}

	public void setCash(CashRegister cash) {
		this.cash = cash;
	}

	public long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<PosInvoice> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(ArrayList<PosInvoice> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public PosInvoicesTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(PosInvoicesTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public PosDeliverDocumsTM getDeliverTableModel() {
		return deliverTableModel;
	}

	public void setDeliverTableModel(PosDeliverDocumsTM deliverTableModel) {
		this.deliverTableModel = deliverTableModel;
	}
	
	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		this.xmlFolder = "/Users/jota_ce/Documents/cacique-sifen/xml/";
		clearFilters ();
		return null;
	}   

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	private void clearFilters () {
		this.event = null;
		this.cashName = null;
		this.cashId = 0;
		this.taxNumber = null;
		this.taxName = null;
		this.identityNumber = null;
		this.eMailAddress = null;
		this.fromDate = null;
		this.toDate = null;
		this.invCondition = null;
		this.selectedTransaction = null;
		this.selectedTransactionId = 0;
	}

	public String formatDate ( String dateValue ) {
		String dd;
		String mm;
		String yy;
		if (dateValue.length() < 6) {
			return null;
		}
		if (dateValue.length() > 10) {
			return null;
		}
		if (dateValue.length() == 6) {
			dd = dateValue.substring(0, 2);
			mm = dateValue.substring(2, 4);
			yy = dateValue.substring(4, 6);
			if (Integer.valueOf(yy) > 50) {
				return dd + "/" + mm + "/" + "19" + yy;
			} else {
				return dd + "/" + mm + "/" + "20" + yy;    			
			}
		}
		if (dateValue.length() == 8) {
			dd = dateValue.substring(0, 2);
			mm = dateValue.substring(2, 4);
			yy = dateValue.substring(4, 8);
			return dd + "/" + mm + "/" + yy;
		}
		if (dateValue.length() == 10) {
			return dateValue;
		}    	
		return null;
	}

	public java.util.Date getDateValue (String dateText) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date dateValue = sdf.parse(dateText);
			return dateValue;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getInvoiceData ( long transactionId ) {
		selectedTransaction = null;
		Iterator itr1 = transactionsList.iterator();
		while (itr1.hasNext()) {
			PosInvoice i = (PosInvoice) itr1.next();
			if (i.getInvoiceId() == transactionId) {
				selectedTransaction = i;
			}
		}
	}

	public CashRegister enterCashName ( String cashName ) {
		CashRegister c;
		ArrayList<CashRegister> l = CashRegisterDAO.getListByName(cashName, UserAttributes.userUnit.getIDENTIFIER());
		if (l != null) {
			if (l.size() == 1) {
			    c = l.get(0);
			    return c;
			}
		} 
		return null;
	}

	public ApplicationMessage enterCashId ( long cashId ) {
		ApplicationMessage aMsg;
		try {
			CashRegister o = CashRegisterDAO.getRow(cashId);
			if (o != null) {
				this.cash = o;
				this.cashId = o.getIDENTIFIER();
				return null;
			} else {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NO-DATA-FOUND", "Caja no encontrada", ApplicationMessage.ERROR);
				return aMsg;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}
	
	public ApplicationMessage enterTaxNumber ( String taxNumber ) {
		ApplicationMessage aMsg;
		try {
			RcvTaxPayer o = RcvTaxPayersDAO.getRow(taxNumber);
			if (o != null) {
				this.taxNumber = taxNumber;
				this.taxName = o.getFullName();
				RcvTaxMailingList m = RcvTaxMailingListDAO.getRowByTaxNumber(taxNumber);
				if ( m != null ) {
					this.eMailAddress = m.geteMail();
				}
				return null;
			} else {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NO-TAX-PAYER", "Contribuyente no encontrado", ApplicationMessage.ERROR);
				return aMsg;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}
	
	public ApplicationMessage enterIdentityNumber ( String idNumber ) {
		ApplicationMessage aMsg;
		try {
			this.identityNumber = idNumber;
			if ( idNumber != null) {
			    RcvTaxMailingList m = RcvTaxMailingListDAO.getRowByIdNumber(idNumber);
			    if ( m != null ) {
				    this.eMailAddress = m.geteMail();
			    }
			}
			return null;
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}
	
	public ApplicationMessage enterEmailAddress ( String emAddress ) {
		ApplicationMessage aMsg;
		try {
			this.eMailAddress = emAddress;
			return null;
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}

	public ApplicationMessage enterFromDate ( String s ) {
		java.util.Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ApplicationMessage aMsg;
		if (s != null) {
			// aqui se somete la entrada a un formateo previo que permite traducir las entradas
			// con formatos abreviados de fecha
			String v = this.formatDate(s);
			try {
				d = sdf.parse(v);
			} catch (ParseException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("DATE-FORMAT", "Formato de fecha no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			this.setFromDate(d);
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage enterToDate ( String s ) {
		java.util.Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ApplicationMessage aMsg;
		if (s != null) {
			// aqui se somete la entrada a un formateo previo que permite traducir las entradas
			// con formatos abreviados de fecha
			String v = this.formatDate(s);
			try {
				d = sdf.parse(v);
			} catch (ParseException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("DATE-FORMAT", "Formato de fecha no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			this.setToDate(d);
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage enterFromNumber ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			try {
				long x = Long.parseLong(s);
				this.setFromNumber(x);
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NUM-FORMAT", "Numero no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			return null;
		} else {
			return null;
		}
	}
	
	public ApplicationMessage enterToNumber ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			try {
				long x = Long.parseLong(s);
				this.setToNumber(x);
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NUM-FORMAT", "Numero no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage loadSendDataModel () {
		int rowsPerGroup = 50;
		if (posOpts != null) {
		    rowsPerGroup = (int) posOpts.getEbBatchTxQty();
		    if (rowsPerGroup == 0) {
		    	    rowsPerGroup = 50;
		    }
		}
		try {
			System.out.println("enviando parametros: " + this.cashId + " - " + this.fromDate + " - " +
		              this.toDate + " - " + this.fromNumber + " - " + this.toNumber + " - " +
					  rowsPerGroup );
			this.transactionsList = PosTransactionsDAO.getNotSentList ( 0, this.fromDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ApplicationMessage loadRenderDataModel () {
		try {
			this.transactionsList = PosTransactionsDAO.getNotRenderedList(this.cashId, this.fromDate, this.toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ApplicationMessage loadDeliverDataModel () {
		try {
			this.transactionsList = PosTransactionsDAO.getRenderedList ( this.cashId, 
					                                                     this.taxNumber, 
					                                                     this.identityNumber, 
					                                                     this.fromDate, 
					                                                     this.toDate );
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createSendItemsTable ( ) {
		if (sendTableModel == null) {
			sendTableModel = new PosInvoicesTM();
		}
		sendTableModel.getModelData ( transactionsList );
		sendTableModel.fireTableDataChanged();
	}	

	public void createDeliverItemsTable ( ) {
		if (deliverTableModel == null) {
			deliverTableModel = new PosDeliverDocumsTM();
		}
		deliverTableModel.getModelData ( transactionsList );
		deliverTableModel.fireTableDataChanged();
	}	

	public PosInvoicesTM createSendEmptyTable ( ) {
		PosInvoicesTM tm = new PosInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosDeliverDocumsTM createDeliverEmptyTable ( ) {
		PosDeliverDocumsTM tm = new PosDeliverDocumsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosInvoicesTM createSendTable () {
		int rowsPerGroup = 50;
		if (posOpts != null) {
		    rowsPerGroup = (int) posOpts.getEbBatchTxQty();
		    if (rowsPerGroup == 0) {
		    	    rowsPerGroup = 50;
		    }
		}
		//
		PosInvoicesTM tm = new PosInvoicesTM();
		tm.getModelData(this.event, this.cashId, this.fromDate);
		return tm;
	}	

	public PosDeliverDocumsTM createDeliverTable () {
		PosDeliverDocumsTM tm = new PosDeliverDocumsTM();
		tm.getModelData(this.cashId, this.fromDate, this.toDate);
		return tm;
	}	

	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTransaction ( long invoiceId, 
			                                    long controlId, 
			                                    long cashId, 
			                                    long orgId, 
			                                    long unitId, 
			                                    String usrName ) {
		ApplicationMessage m = new ApplicationMessage();
		/*
		try {
		    SendPosInvoice2 t = new SendPosInvoice2();
		    m = t.sendDE( invoiceId, controlId, cashId, orgId, unitId, usrName );
		    return m;
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
		*/
		return null;
	}
	
	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTxBatch ( ) {
		ApplicationMessage m = new ApplicationMessage();
		try {
			/*
		    SendPosInvoice2 t = new SendPosInvoice2();
		    ArrayList<PosInvoice> l = new ArrayList<PosInvoice>();
		    PosInvoice f1 = new PosInvoice();
		    f1.setInvoiceId(279583);
		    f1.setControlId(3416);
		    f1.setCashId(11);
		    f1.setControlCode("01800524683001011000000122022122816923339010");
		    f1.setUserName("PRUEBA1");
		    l.add(f1);
		    PosInvoice f2 = new PosInvoice();
		    f2.setInvoiceId(279584);
		    f2.setControlId(3416);
		    f2.setCashId(11);
		    f2.setControlCode("01800524683001011000000222022122813833640185");
		    f2.setUserName("PRUEBA1");
		    l.add(f2);
		    m = t.sendDeBatch(l);
		    */
			/*
		    SendPosInvoicesAsync t = new SendPosInvoicesAsync();
		    m = t.sendDeBatch ( "FACTURA",
		    		                new java.util.Date(),
		    		                26, 
		    		                36,
		    		                50,
		    		                UserAttributes.userOrg.getIDENTIFIER(), 
		    		                UserAttributes.userUnit.getIDENTIFIER(), 
		    		                UserAttributes.userName );
		    */
		    return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	// este es el procedimiento para invocar las rutinas de generacion de la representacion
	// grafica de las facturas
	public ApplicationMessage genGraphicRepresent ( long invoiceId, long controlId, long cashId, String txNumber, java.util.Date txDate ) {
		ApplicationMessage m;
        GenerateInvoicePdf g = new GenerateInvoicePdf();
        m = g.createDocument(invoiceId, controlId, cashId, txNumber);
        return m;
	}

	// este es el procedimiento para invocar las rutinas de envio de la representacion
	// grafica de las facturas
	public ApplicationMessage deliverGraphRepresent ( long invoiceId, long controlId, long cashId, String txNumber, java.util.Date txDate ) {
		ApplicationMessage m;
        GenerateInvoicePdf g = new GenerateInvoicePdf();
        m = g.createDocument(invoiceId, controlId, cashId, txNumber);
        return m;
	}

	// este es el procedimiento para invocar las rutinas de envio de la representacion
	// grafica de las facturas
	public ApplicationMessage deliverXmlFile ( long invoiceId, 
			                                   long controlId, 
			                                   long cashId, 
			                                   String eMailAddress,
			                                   String txNumber,
			                                   java.util.Date txDate) {
		ApplicationMessage m = new ApplicationMessage();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String subFolder = sdf.format(txDate);
		String fileName = String.valueOf(cashId) + "_" + String.valueOf(controlId) + "_" + String.valueOf(invoiceId) + ".xml";
		ArrayList<String> l = new ArrayList<String>();
		l.add(this.xmlFolder + subFolder + "/" + fileName);
		if (l.size() > 6) {
	        m.setMessage("SEND-MAIL", "La cantidad maxima de archivos adjuntos es de 6 (seis)", ApplicationMessage.ERROR);
	        return m;			
		}
		SendEmail mail = new SendEmail();
	    //public String enviarEmail ( String mailTo, String emailSubject, String emailBody, List<String> atachment) {
        String resp = mail.enviarEmail ( eMailAddress, 
        		                             "Documentos electronicos emitidos por Comercial El Cacique S.R.L. - Avanza ERP", 
        		                             "Estimado Cliente.\n" +
        		                             "Le remitimos los documentos electronicos correspondientes a sus operaciones.\n" +
        		                             "Saludos cordiales.\n\n" +
        		                             "Dpto. de Administracion.\n" +
        		                             "Comercial El Cacique S.R.L.", 
        		                             l);
        m.setMessage("SEND-MAIL", resp, ApplicationMessage.MESSAGE);
        return m;
	}

	public void requestCDC () {
		short FACTURA_ELECTRONICA = 1;
		short PERSONA_FISICA = 1;
		short NORMAL = 1;
		//
		DEUtils x = new DEUtils();
		EdIdBuildingParams cdcParams = new EdIdBuildingParams();
		cdcParams.setdCodSeg("123456789");
		cdcParams.setdRucEm("80052468");
		cdcParams.setdDVEmi("3");
		cdcParams.setdEst("001");
		cdcParams.setdFeEmiDE(LocalDateTime.now());
		cdcParams.setdNumDoc("001-001-0001234");
		cdcParams.setdPunExp("001");
		cdcParams.setiTiDE(FACTURA_ELECTRONICA);
		cdcParams.setiTipCont(PERSONA_FISICA);
		cdcParams.setiTipEmi(NORMAL);
		// invocar el metodo para construir el CDC con los valores enviados
		try {
		    String CDC = x.obtenerCDC(cdcParams);
		} catch ( SifenException e ) {
			e.printStackTrace();
		}	
	}
	
	public void requestQR () {
		short CONTRIBUYENTE = 1;
		short FACTURA_ELECTRONICA = 1;
		short IVA_RENTA = 5;
		short B2C = 2;
		//
		DEUtils x = new DEUtils();
		QrBuildingParams qrParams = new QrBuildingParams();
		qrParams.setCantidadItems(Short.valueOf("8"));
		qrParams.setdFeEmiDE(LocalDateTime.now());
		qrParams.setdNumIDRec("1494393");
		qrParams.setdRucRec("1494393");
		qrParams.setdTotGralOpe(new BigDecimal(184000));
		qrParams.setdTotIVA(new BigDecimal(16727));
		qrParams.setId("01800805534001002000000722021040613265708133");
		qrParams.setiNatRec(CONTRIBUYENTE);
		qrParams.setiTiDE(FACTURA_ELECTRONICA);
		qrParams.setiTImp(IVA_RENTA	);
		qrParams.setiTiOpe(B2C);
		// invocar el metodo para construir el codigo QR con los valores enviados
		try {
	        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
	        Sifen.setSifenConfig(sifenConfig);
	        // Firma Digital del XML
	        //SignedInfo signedInfo = SignatureHelper.signDocument(sifenConfig, rDE, this.getId());
	        //
		    //String QR = x.generateQRLink(signedInfo, sifenConfig, qrParams);
		} catch ( SifenException e ) {
			e.printStackTrace();
		}	
	}
	
	public boolean isConnectionAvailable () {
		try {
			return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
		} catch (Exception e) {
			return false;   			
		}
	}

}
