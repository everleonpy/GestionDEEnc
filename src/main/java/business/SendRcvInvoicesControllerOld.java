package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.FndSitesDAO;
import dao.PosTransactionsDAO;
import dao.RcvCustomersTrxDAO;
import dao.UtilitiesDAO;
import pojo.FndSite;
import pojo.PosEbTransmissionLog;
import pojo.RcvInvoice;
import sifen.SendRcvInvoice;
import tools.GenerateInvoicePdf;
import util.SendEmail;
import util.TransmissionLog;

public class SendRcvInvoicesControllerOld {
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
	private String event;
	private String siteName;
	private long siteId;
	private String invCondition;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private RcvInvoice selectedTransaction;
	private FndSite site;
	private long selectedTransactionId;
	private ArrayList<RcvInvoice> transactionsList = new ArrayList<RcvInvoice> ();	
	private RcvInvoicesTM sendTableModel;
	private RcvDeliverDocumsTM deliverTableModel;
	

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

	public RcvInvoice getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(RcvInvoice selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public FndSite getSite() {
		return site;
	}

	public void setSite(FndSite site) {
		this.site = site;
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

	public ArrayList<RcvInvoice> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(ArrayList<RcvInvoice> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public RcvInvoicesTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(RcvInvoicesTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public RcvDeliverDocumsTM getDeliverTableModel() {
		return deliverTableModel;
	}

	public void setDeliverTableModel(RcvDeliverDocumsTM deliverTableModel) {
		this.deliverTableModel = deliverTableModel;
	}

	public ApplicationMessage initForm() {
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.event = null;
		this.siteName = null;
		this.siteId = 0;
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
			RcvInvoice i = (RcvInvoice) itr1.next();
			if (i.getInvoiceId() == transactionId) {
				selectedTransaction = i;
			}
		}
	}

	public FndSite enterSiteName ( String siteName ) {
		FndSite s;
		ArrayList<FndSite> l = FndSitesDAO.getListByName(siteName, UserAttributes.userUnit.getIDENTIFIER());
		if (l != null) {
			if (l.size() == 1) {
			    s = l.get(0);
			    return s;
			}
		} 
		return null;
	}

	public ApplicationMessage enterSiteId ( long siteId ) {
		ApplicationMessage aMsg;
		try {
			FndSite o = FndSitesDAO.getRow(siteId);
			if (o != null) {
				this.siteId = o.getIDENTIFIER();
				this.site = o;
				return null;
			} else {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NO-DATA-FOUND", "Sucursal no encontrada", ApplicationMessage.ERROR);
				return aMsg;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}

	public ApplicationMessage enterTxCondition ( String invCondition ) {
		ApplicationMessage aMsg;
		try {
			if (invCondition == null) {
				this.invCondition = "CONTADO";
			} else {
				this.invCondition = "CONTADO";
				if (invCondition.equalsIgnoreCase("Credito")) {
					this.invCondition = "CREDITO";					
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

	public ApplicationMessage loadSendDataModel () {
		try {
			//this.transactionsList = RcvCustomersTrxDAO.getInvoicesNotSentList(siteId, invCondition, fromDate, toDate);
			this.transactionsList = RcvCustomersTrxDAO.getInvoicesNotSentList(siteId, toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ApplicationMessage loadRenderDataModel () {
		try {
			this.transactionsList = RcvCustomersTrxDAO.getNotRenderedList(this.siteId, this.fromDate, this.toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ApplicationMessage loadDeliverDataModel () {
		try {
			this.transactionsList = RcvCustomersTrxDAO.getRenderedList(this.siteId, this.fromDate, this.toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createSendItemsTable ( ) {
		if (sendTableModel == null) {
			sendTableModel = new RcvInvoicesTM();
		}
		sendTableModel.getModelData ( transactionsList );
		sendTableModel.fireTableDataChanged();
	}	

	public void createDeliverItemsTable ( ) {
		if (deliverTableModel == null) {
			deliverTableModel = new RcvDeliverDocumsTM();
		}
		deliverTableModel.getModelData ( transactionsList );
		deliverTableModel.fireTableDataChanged();
	}	

	public RcvInvoicesTM createSendEmptyTable ( ) {
		RcvInvoicesTM tm = new RcvInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public RcvDeliverDocumsTM createDeliverEmptyTable ( ) {
		RcvDeliverDocumsTM tm = new RcvDeliverDocumsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public RcvInvoicesTM createSendTable () {
		RcvInvoicesTM tm = new RcvInvoicesTM();
		//tm.getModelData(this.event, this.siteId, this.invCondition, this.fromDate, this.toDate);
		return tm;
	}	

	public RcvDeliverDocumsTM createDeliverTable () {
		RcvDeliverDocumsTM tm = new RcvDeliverDocumsTM();
		tm.getModelData(this.siteId, this.fromDate, this.toDate);
		return tm;
	}	

	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendInvoice ( long invoiceId, 
			                                long orgId, 
			                                long unitId, 
			                                String usrName ) {
		ApplicationMessage m = new ApplicationMessage();
		try {
		    SendRcvInvoice t = new SendRcvInvoice();
		    m = t.sendDE( invoiceId, orgId, unitId, usrName );
		    return m;
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
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
        //
	    PosEbTransmissionLog tLog = new PosEbTransmissionLog();
	    long logId = UtilitiesDAO.getNextval("SQ_POS_EB_TRANSMISSION_LOG");
        tLog.setIdentifier(logId);
	    tLog.setErrorCode("0");
	    tLog.setErrorMsg(m.getText());
	    tLog.setEventId(TransmissionLog.GENERACION_KUDE.getVal());
	    tLog.setIdentifier(logId);
	    tLog.setOrgId(UserAttributes.userOrg.getIDENTIFIER());
	    tLog.setTransactionId(invoiceId);
	    tLog.setCashControlId(controlId);
	    tLog.setCashId(cashId);
	    tLog.setUnitId(UserAttributes.userUnit.getIDENTIFIER());
	    int updated = PosTransactionsDAO.createTransmissionLog(tLog);
        //
        return m;
	}

	// este es el procedimiento para invocar las rutinas de envio de la representacion
	// grafica de las facturas
	public ApplicationMessage deliverGraphRepresent ( long invoiceId, 
			                                          long controlId, 
			                                          long cashId, 
			                                          String txNumber, 
			                                          String eMail,
			                                          String fileName) {
		ApplicationMessage m;
		String subject = "Comercial El Cacique S.R.L. Factura No. " + txNumber + " - Enviado por Avanza ERP.";
        SendEmail mail = new SendEmail();
        //-- destino         , Suject                     , texto del correo
        String resp = mail.enviarEmail(eMail, subject, "KuDE de factura No. " + txNumber, null);
        System.out.println("Resp.: " + resp);
        // generar el log del evento
	    // generar el log del evento
	    PosEbTransmissionLog tLog = new PosEbTransmissionLog();
	    long logId = UtilitiesDAO.getNextval("SQ_POS_EB_TRANSMISSION_LOG");
        tLog.setIdentifier(logId);
	    tLog.setErrorCode("0");
	    tLog.setErrorMsg(resp);
	    tLog.setEventId(TransmissionLog.ENVIO_KUDE.getVal());
	    tLog.setIdentifier(logId);
	    tLog.setOrgId(UserAttributes.userOrg.getIDENTIFIER());
	    tLog.setTransactionId(invoiceId);
	    tLog.setCashControlId(controlId);
	    tLog.setCashId(cashId);
	    tLog.setUnitId(UserAttributes.userUnit.getIDENTIFIER());
	    int updated = PosTransactionsDAO.createTransmissionLog(tLog);
	    //
        m = new ApplicationMessage("SEND-MAIL", resp, ApplicationMessage.MESSAGE);
        return m;
	}
	
	public boolean isConnectionAvailable () {
		try {
			return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
		} catch (Exception e) {
			return false;   			
		}
	}

}
