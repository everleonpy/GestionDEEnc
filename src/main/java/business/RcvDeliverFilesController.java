package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import dao.PosOptionsDAO;
import dao.RcvCustomersTrxDAO;
import dao.RcvTaxMailingListDAO;
import dao.RcvTaxPayersDAO;
import pojo.PosOption;
import pojo.RcvInvoice;
import pojo.RcvTaxMailingList;
import pojo.RcvTaxPayer;
import pojo.SendGroup;
import util.SendEmail;

public class RcvDeliverFilesController {
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
	private String taxNumber;
	private String taxName;
	private String identityNumber;
	private String customerName;
	private java.util.Date trxDate;
	private int fromGroup;
	private int toGroup;
	private SendGroup selectedGroup;
	private long selectedGroupId;
	private long transactionId;
	private long selectedTransactionId;
	private String eMailAddress;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private long fromNumber;
	private long toNumber;
	private ArrayList<RcvInvoice> transactionsList = new ArrayList<RcvInvoice> ();
	private RcvDeliverDocumsTM deliverTableModel;
	private ArrayList<SendGroup> groupsList = new ArrayList<SendGroup> ();
	private RcvSendGroupsTM sendTableModel;


	public String getXmlFolder() {
		return xmlFolder;
	}

	public void setXmlFolder(String xmlFolder) {
		this.xmlFolder = xmlFolder;
	}

	public String geteMailAddress() {
		return eMailAddress;
	}

	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public ArrayList<RcvInvoice> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(ArrayList<RcvInvoice> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public java.util.Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}

	public int getFromGroup() {
		return fromGroup;
	}

	public void setFromGroup(int fromNumber) {
		this.fromGroup = fromNumber;
	}

	public int getToGroup() {
		return this.toGroup;
	}

	public void setToGroup(int toGroup) {
		this.toGroup = toGroup;
	}

	public SendGroup getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(SendGroup selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public long getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	public ArrayList<SendGroup> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(ArrayList<SendGroup> groupsList) {
		this.groupsList = groupsList;
	}

	public RcvSendGroupsTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(RcvSendGroupsTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
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

	public PosOption getPosOpts() {
		return posOpts;
	}

	public void setPosOpts(PosOption posOpts) {
		this.posOpts = posOpts;
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

	public RcvDeliverDocumsTM getDeliverTableModel() {
		return deliverTableModel;
	}

	public void setDeliverTableModel(RcvDeliverDocumsTM deliverTableModel) {
		this.deliverTableModel = deliverTableModel;
	}



	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		this.xmlFolder = "/Users/jota_ce/Documents/cacique-sifen/xml/";
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.trxDate = null;
		this.fromGroup = 0;
		this.toGroup = 0;
		this.selectedGroup = null;
		this.selectedGroupId = 0;
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

	public void getGroupData ( long groupNumber ) {
		selectedGroup = null;
		Iterator itr1 = groupsList.iterator();
		while (itr1.hasNext()) {
			SendGroup i = (SendGroup) itr1.next();
			if (i.getGroupNumber() == groupNumber) {
				selectedGroup = i;
			}
		}
	}

	public ApplicationMessage enterDate ( String s ) {
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
			this.setTrxDate(d);
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage enterFromGroup ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			try {
				int x = Integer.parseInt(s);
				this.setFromGroup(x);
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NUM-FORMAT", "Numero no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			return null;
		} else {
			this.setFromGroup(0);
			return null;
		}
	}
	
	public ApplicationMessage enterToGroup ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			try {
				int x = Integer.parseInt(s);
				this.setToGroup(x);
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("NUM-FORMAT", "Numero no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			return null;
		} else {
			this.setToGroup(0);
			return null;
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
	
	public ApplicationMessage loadDeliverDataModel () {
		try {
			this.transactionsList = RcvCustomersTrxDAO.getRenderedList(1, this.fromDate, this.toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void createDeliverItemsTable ( ) {
		if (deliverTableModel == null) {
			deliverTableModel = new RcvDeliverDocumsTM();
		}
		deliverTableModel.getModelData( transactionsList );
		deliverTableModel.fireTableDataChanged();
	}	
	
	public RcvSendGroupsTM groupsDeliverEmptyTable ( ) {
		RcvSendGroupsTM tm = new RcvSendGroupsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosDeliverDocumsTM trxDeliverEmptyTable ( ) {
		PosDeliverDocumsTM tm = new PosDeliverDocumsTM();
		tm.getEmptyModel();
		return tm;
	}	
	
	public RcvSendGroupsTM createSendTable () {
		int FACTURA = 1;
		int rowsPerGroup = 50;
		if (posOpts != null) {
		    rowsPerGroup = (int) posOpts.getEbBatchTxQty();
		    if (rowsPerGroup == 0) {
		    	    rowsPerGroup = 50;
		    }
		}
		//
		RcvSendGroupsTM tm = new RcvSendGroupsTM();
		tm.getModelData(this.trxDate, FACTURA, this.fromGroup, this.toGroup, 50);
		return tm;
	}	

	// este es el procedimiento para invocar las rutinas de envio de la representacion
	// grafica de las facturas
	public ApplicationMessage deliverXmlFile ( long invoiceId, long controlId, long cashId, String txNumber ) {
		ApplicationMessage m = new ApplicationMessage();
		String fileName = String.valueOf(invoiceId) + ".xml";
		ArrayList<String> l = new ArrayList<String>();
		l.add(this.xmlFolder + fileName);
		if (l.size() > 6) {
	        m.setMessage("SEND-MAIL", "La cantidad maxima de archivos adjuntos es de 6 (seis)", ApplicationMessage.ERROR);
	        return m;			
		}
		SendEmail mail = new SendEmail();
	    //public String enviarEmail ( String mailTo, String emailSubject, String emailBody, List<String> atachment) {
        String resp = mail.enviarEmail ( this.eMailAddress, 
        		                             "Documentos electronicos emitidos por Comercial El Cacique S.R.L.", 
        		                             "Documentos electronicos", 
        		                             l);
        m.setMessage("SEND-MAIL", resp, ApplicationMessage.MESSAGE);
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
