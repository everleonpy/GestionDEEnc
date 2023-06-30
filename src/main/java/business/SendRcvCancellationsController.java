package business;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.PosOptionsDAO;
import dao.RcvCustomersTrxDAO;
import dao.RcvEbEventItemsLogDAO;
import dao.RcvEbEventsLogDAO;
import dao.RcvTaxMailingListDAO;
import dao.RcvTaxPayersDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.CancelationEvent;
import pojo.CashRegister;
import pojo.GenericStringsList;
import pojo.PosInvoice;
import pojo.PosOption;
import pojo.RcvEbEventItemLog;
import pojo.RcvEbEventLog;
import pojo.RcvInvoice;
import pojo.RcvTaxMailingList;
import pojo.RcvTaxPayer;
import sifen.SendEvents;

public class SendRcvCancellationsController {
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
	private String trxType;
	private java.util.Date trxDate;
	private RcvInvoice selectedTransaction;
	private CashRegister cash;
	private long selectedTransactionId;
	private ArrayList<RcvInvoice> canceledTxList = new ArrayList<RcvInvoice> ();	
	private RcvCanceledInvoicesTM sendTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public java.util.Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}

	public RcvInvoice getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(RcvInvoice selectedTransaction) {
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

	public ArrayList<RcvInvoice> getCanceledTxList() {
		return canceledTxList;
	}

	public void setCanceledTxList(ArrayList<RcvInvoice> canceledTxList) {
		this.canceledTxList = canceledTxList;
	}

	public RcvCanceledInvoicesTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(RcvCanceledInvoicesTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
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

	public void getInvoiceData ( long transactionId ) {
		selectedTransaction = null;
		Iterator itr1 = canceledTxList.iterator();
		while (itr1.hasNext()) {
			RcvInvoice i = (RcvInvoice) itr1.next();
			if (i.getInvoiceId() == transactionId) {
				selectedTransaction = i;
			}
		}
	}

	public ApplicationMessage enterTrxDate ( String s ) {
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

	public ApplicationMessage loadSendDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxDate  );
			this.canceledTxList = RcvCustomersTrxDAO.getCanceledList(this.trxType, this.trxDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createSendItemsTable ( ) {
		if (sendTableModel == null) {
			sendTableModel = new RcvCanceledInvoicesTM();
		}
		sendTableModel.getModelData ( canceledTxList );
		sendTableModel.fireTableDataChanged();
	}	

	public RcvCanceledInvoicesTM createSendEmptyTable ( ) {
		RcvCanceledInvoicesTM tm = new RcvCanceledInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public RcvCanceledInvoicesTM createSendTable () {
		int rowsPerGroup = 50;
		if (posOpts != null) {
		    rowsPerGroup = (int) posOpts.getEbBatchTxQty();
		    if (rowsPerGroup == 0) {
		    	    rowsPerGroup = 50;
		    }
		}
		//
		RcvCanceledInvoicesTM tm = new RcvCanceledInvoicesTM();
		tm.getModelData(this.trxType, this.trxDate);
		return tm;
	}	

	// este es el procedimiento para conectar con las rutinas de envio de los eventos
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendEvents ( ) {
		ApplicationMessage m = new ApplicationMessage();
		boolean dataFound = false;
		try {
			if (this.canceledTxList != null) {
				// generar la lista base para la creacion de los eventos por transaccion
			    ArrayList<CancelationEvent> lst = new ArrayList<CancelationEvent>();
			    Iterator itr1 = this.canceledTxList.iterator();
			    while (itr1.hasNext()) {
			    	    RcvInvoice x = (RcvInvoice) itr1.next();
				    CancelationEvent o = new CancelationEvent();
				    if (x.getCancelComments() != null) {
				        o.setCancelReason(x.getCancelComments());
				    } else {
				        o.setCancelReason("Operacion cancelada a peticion del cliente");					
				    }
				    o.setControlCode(x.getControlCode());
				    o.setTransactionId(x.getInvoiceId());
				    System.out.println("CDC: " + o.getControlCode());
				    System.out.println("Transaccion: " + o.getTransactionId());
				    dataFound = true;
				    lst.add(o);
			    }			
				if (dataFound == true) {
			        // ejecutar la rutina de envio del evento a sifen
		            SendEvents t = new SendEvents();
		            m = t.sendRcvCancelEvent(lst);
		            return m;
				} else {
					m.setMessage("SEND-EVT", "No se ha encontrado ninguna cancelacion en esta fecha", ApplicationMessage.MESSAGE);
				    return m;					
				}
			} else {
				m.setMessage("SEND-EVT", "No se ha encontrado ninguna cancelacion en esta fecha", ApplicationMessage.MESSAGE);
			    return m;					
			}
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-EVT", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-EVT", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
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
