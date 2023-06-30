package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.exceptions.SifenException;

import dao.PosEbInvoicesDAO;
import dao.PosOptionsDAO;
import dao.PosTransactionsDAO;
import pojo.PosInvoice;
import pojo.PosOption;
import pojo.PrepareTxParams;
import sifen.QueryPosInvoices;
import sifen.SendPosInvoicesAsync;

public class ResendPosInvoicesControllerBeta {
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
	private String event;
	private java.util.Date trxDate;
	private long siteId;
	private long batchId;
	private int invoicesQty;
	private int notValidInvoicesQty;
	private int preparedQty;
	private int fromGroup;
	private int toGroup;
	private PosInvoice selectedTransaction;
	private long selectedTransactionId;
	private String selectedCtrlCode;
	private ArrayList<PosInvoice> transactionsList = new ArrayList<PosInvoice> ();
	private PosInvoicesTM sendTableModel;

	private final int FACTURA = 1;
    private final String TRX_TYPE = "FACTURA";
	
	// internal workflow variables
	private String appAction;

	// getters and setters	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public java.util.Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	
	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public int getInvoicesQty() {
		return invoicesQty;
	}

	public void setInvoicesQty(int invoicesQty) {
		this.invoicesQty = invoicesQty;
	}

	public int getNotValidInvoicesQty() {
		return notValidInvoicesQty;
	}

	public void setNotValidInvoicesQty(int notValidInvoicesQty) {
		this.notValidInvoicesQty = notValidInvoicesQty;
	}
	
	public int getPreparedQty() {
		return preparedQty;
	}

	public void setPreparedQty(int preparedQty) {
		this.preparedQty = preparedQty;
	}

	public int getFromGroup() {
		return fromGroup;
	}

	public void setFromGroup(int fromGroup) {
		this.fromGroup = fromGroup;
	}

	public int getToGroup() {
		return toGroup;
	}

	public void setToGroup(int toGroup) {
		this.toGroup = toGroup;
	}
	
	public PosInvoice getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(PosInvoice selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}

	public String getSelectedCtrlCode() {
		return selectedCtrlCode;
	}

	public void setSelectedCtrlCode(String selectedCtrlCode) {
		this.selectedCtrlCode = selectedCtrlCode;
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

	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.event = null;
		this.trxDate = null;
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

	public void getTransactionData ( long transactionId ) {
		selectedTransaction = null;
		Iterator itr1 = transactionsList.iterator();
		while (itr1.hasNext()) {
			PosInvoice i = (PosInvoice) itr1.next();
			if (i.getInvoiceId() == transactionId) {
				selectedTransaction = i;
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
			return null;
		}
	}
	
	public ApplicationMessage loadSendDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxDate + " - " +
		              this.siteId );
			this.transactionsList = PosTransactionsDAO.getNotSentList ( this.siteId, this.trxDate);
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

	public PosInvoicesTM createSendEmptyTable ( ) {
		PosInvoicesTM tm = new PosInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosSendGroupsTM createDeliverEmptyTable ( ) {
		PosSendGroupsTM tm = new PosSendGroupsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public void findNotValidInvoicesCount () {
		this.invoicesQty = PosTransactionsDAO.invoicesQty(this.trxDate);
	}

	public void findInvoicesCount () {
		this.invoicesQty = PosTransactionsDAO.invoicesQty(this.trxDate);
	}
	
	public void findPreparedCount () {
		this.preparedQty = PosEbInvoicesDAO.preparedInvoicesQty(FACTURA, this.trxDate);
	}
	
	public ApplicationMessage prepareTrx () {
		ApplicationMessage aMsg;
		String fromTime = "00:00";
		String toTime = "23:59";
		this.setFromGroup(0);
		this.setToGroup(0);
		
		PrepareTxParams x = PosEbInvoicesDAO.prepareInvoices(this.trxDate, fromTime, toTime);
		if (x != null) {
			this.setBatchId(x.getBatchId());
			this.setFromGroup(x.getFirstGroup());
			this.setToGroup(x.getLastGroup());
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", "Total transacciones: " + x.getRowsQty() +
					" Grupo Desde: " + x.getFirstGroup() + " Grupo Hasta: " + x.getLastGroup(), ApplicationMessage.MESSAGE);
			return aMsg;
		} else {
			this.setBatchId(0);
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", "No se ha podido preparar ninguna transaccion para envio", ApplicationMessage.ERROR);
			return aMsg;			
		}
	}
	
	public int deleteTrx () {
		int deletedQty = PosEbInvoicesDAO.deleteInvoices(this.trxDate);
		return deletedQty;			
	}

	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTxBatch ( int fromGrp, int toGrp ) {
		ApplicationMessage sMsg = new ApplicationMessage();
		ApplicationMessage qMsg;
		try {
			/*
		    SendPosInvoicesAsync t = new SendPosInvoicesAsync();
		    // ejecutar el metodo de envio del lote de transacciones
		    sMsg = t.sendDeBatch ( TRX_TYPE, 
		    		                   this.trxDate, 
		    		                   fromGrp, 
		    		                   toGrp,
		    		                   posOpts.getEbBatchTxQty(),
		    		                   UserAttributes.userOrg.getIDENTIFIER(), 
		    		                   UserAttributes.userUnit.getIDENTIFIER(), 
		    		                   UserAttributes.userName );
		    System.out.println("mensaje de envio es nulo");
		    // consultar todos lo lotes POS no verificados de la fecha
		    qMsg = t.queryBatchesList(this.trxDate);
		    */
		    return sMsg;
		//} catch ( SifenException e1 ) {
		//	sMsg.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
		//	return sMsg;
		} catch ( Exception e2 ) {
			sMsg.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return sMsg;
		}
	}

	public DocumentoElectronico queryCDC ( String controlCode, 
			long transactionId, 
			long controlId, 
			long cashId, 
			String txNumber ) {
		try {
			QueryPosInvoices t = new QueryPosInvoices();
			DocumentoElectronico d = t.queryCDC(controlCode, transactionId, controlId, cashId, txNumber);
			return d;
		} catch ( SifenException e1 ) {
			return null;
		} catch ( Exception e2 ) {
			return null;
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
