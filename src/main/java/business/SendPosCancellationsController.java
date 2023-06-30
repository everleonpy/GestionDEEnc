package business;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.CashRegisterDAO;
import dao.PosEbEventsLogDAO;
import dao.PosOptionsDAO;
import dao.PosTransactionsDAO;
import dao.Util;
import dao.UtilitiesDAO;
import pojo.CancelationEvent;
import pojo.CashRegister;
import pojo.GenericStringsList;
import pojo.PosEbEventItemLog;
import pojo.PosEbEventLog;
import pojo.PosInvoice;
import pojo.PosOption;
import sifen.PosEbEventItemsLogDAO;
import sifen.SendEvents;

public class SendPosCancellationsController {
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
	private long cashId;
	private String cashName;
	private java.util.Date trxDate;
	private PosInvoice selectedTransaction;
	private CashRegister cash;
	private long selectedTransactionId;
	private ArrayList<PosInvoice> canceledTxList = new ArrayList<PosInvoice> ();
	private PosInvoicesTM sendTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public java.util.Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
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

	public ArrayList<PosInvoice> getCanceledTxList() {
		return canceledTxList;
	}

	public void setTransactionsList(ArrayList<PosInvoice> canceledTxList) {
		this.canceledTxList = canceledTxList;
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
		this.cashName = null;
		this.cashId = 0;
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
			this.setTrxDate(d);
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage loadSendDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxDate  );
			this.canceledTxList = PosTransactionsDAO.getCanceledList(this.trxDate, "FACTURA"); 
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
		sendTableModel.getModelData ( canceledTxList );
		sendTableModel.fireTableDataChanged();
	}	

	public PosInvoicesTM createSendEmptyTable ( ) {
		PosInvoicesTM tm = new PosInvoicesTM();
		tm.getEmptyModel();
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
				    PosInvoice x = (PosInvoice) itr1.next();
				    CancelationEvent o = new CancelationEvent();
				    if (x.getCancelComments() != null) {
				        o.setCancelReason(x.getCancelComments());
				    } else {
				        o.setCancelReason("Operacion cancelada a peticion del cliente");					
				    }
				    o.setCashControlId(x.getControlId());
				    o.setCashId(x.getCashId());
				    o.setControlCode(x.getControlCode());
				    o.setTransactionId(x.getInvoiceId());
				    System.out.println("Transaccion: " + o.getTransactionId() + " - " + o.getCashControlId() + " - " +o.getCashId());
				    dataFound = true;
				    lst.add(o);
			    }			
				if (dataFound == true) {
			        // ejecutar la rutina de envio del evento a sifen
		            SendEvents t = new SendEvents();
		            m = t.sendPosCancelEvent(lst);
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
