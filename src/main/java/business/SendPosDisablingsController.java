package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.PosTransactionsDAO;
import pojo.CashRegister;
import pojo.DisablingEvent;
import pojo.PosInvoice;
import sifen.SendEvents;
import util.UtilPOS;

public class SendPosDisablingsController {
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
	private String trxType;
	private java.util.Date trxDate;
	private PosInvoice selectedTransaction;
	private CashRegister cash;
	private long selectedTransactionId;
	private ArrayList<PosInvoice> disabledTxList = new ArrayList<PosInvoice> ();	
	private PosDisabledInvoicesTM sendTableModel;
	//
	private final short FACTURA_ELECTRONICA = 1;
	private final short FACTURA_ELECT_EXPORT = 2;
	private final short FACTURA_ELECT_IMPORT = 3;
	private final short AUTOFACTURA = 4;
	private final short NOTA_CREDITO = 5;
	private final short NOTA_DEBITO = 6;
	private final short NOTA_REMISION = 7;
	private final short RETENCION_ELECTRONICA = 8;
	
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

	public ArrayList<PosInvoice> getDisabledTxList() {
		return disabledTxList;
	}

	public void setDisabledTxList(ArrayList<PosInvoice> disabledTxList) {
		this.disabledTxList = disabledTxList;
	}

	public PosDisabledInvoicesTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(PosDisabledInvoicesTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public ApplicationMessage initForm() {
		this.trxType = "FACTURA";
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
		Iterator itr1 = disabledTxList.iterator();
		while (itr1.hasNext()) {
			PosInvoice i = (PosInvoice) itr1.next();
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
			System.out.println("enviando parametros: " + this.trxDate + " - " + this.trxType );
			this.disabledTxList = PosTransactionsDAO.getDisabledList( this.trxDate, this.trxType );
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createSendItemsTable ( ) {
		if (sendTableModel == null) {
			sendTableModel = new PosDisabledInvoicesTM();
		}
		sendTableModel.getModelData ( this.disabledTxList );
		sendTableModel.fireTableDataChanged();
	}	

	public PosDisabledInvoicesTM createSendEmptyTable ( ) {
		PosDisabledInvoicesTM tm = new PosDisabledInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosCanceledInvoicesTM createSendTable () {
		PosCanceledInvoicesTM tm = new PosCanceledInvoicesTM();
		tm.getModelData(this.trxType, this.trxDate);
		return tm;
	}	

	// este es el procedimiento para conectar con las rutinas de envio de los eventos
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendEvents ( ) {
		ApplicationMessage m = new ApplicationMessage();
		String txNo = null;
		boolean dataFound = false;
		try {
			if (this.disabledTxList != null) {
				// generar la lista base para la creacion de los eventos por transaccion
			    ArrayList<DisablingEvent> lst = new ArrayList<DisablingEvent>();
			    Iterator itr1 = this.disabledTxList.iterator();
			    while (itr1.hasNext()) {
			    	    PosInvoice x = (PosInvoice) itr1.next();
				    DisablingEvent o = new DisablingEvent();
				    if (x.getCancelComments() != null) {
				        o.setDisablingReason(x.getCancelComments());
				    } else {
				        o.setDisablingReason("Operacion cancelada a peticion del cliente");					
				    }
				    o.setTransactionId(x.getInvoiceId());
				    o.setCashControlId(x.getControlId());
				    o.setCashId(x.getCashId());
				    o.setEstabCode(x.getEstabCode());
				    o.setIssuePointCode(x.getIssuePointCode());
				    txNo = UtilPOS.paddingString(String.valueOf(x.getBaseNumber()), 7, '0', true);
				    o.setFirstNumber(txNo);
				    o.setLastNumber(txNo);
				    o.setStampNo(Integer.parseInt(x.getStampNo()));
				    o.setTxTypeId(FACTURA_ELECTRONICA);
				    System.out.println("Transaccion: " + o.getTransactionId() + " - " + o.getEstabCode() + " - " +
				        o.getIssuePointCode() + " - " + o.getFirstNumber() + " - " + o.getLastNumber() + " - " +
				    		o.getStampNo() + " - " + o.getTxTypeId());
				    dataFound = true;
				    lst.add(o);
			    }	
				if (dataFound == true) {
			        // ejecutar la rutina de envio del evento a sifen
		            SendEvents t = new SendEvents();
		            m = t.sendPosDisablingEvent(lst);
		            return m;
				} else {
					m.setMessage("SEND-EVT", "No se ha encontrado ninguna inutilizacion en esta fecha", ApplicationMessage.MESSAGE);
				    return m;					
				}
			} else {
				m.setMessage("SEND-EVT", "No se ha encontrado ninguna inutilizacion en esta fecha", ApplicationMessage.MESSAGE);
			    return m;					
			}
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-EVT", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-EVT", "Cargando lista - " + e2.getMessage(), ApplicationMessage.ERROR);
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
