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
import dao.PosTrxEbBatchesDAO;
import dao.RcvCustomersTrxDAO;
import pojo.DocumElectronico;
import pojo.GenericStringsList;
import pojo.PosOption;
import pojo.PrepareTxParams;
import pojo.SendGroup;
import sifen.QueryPosInvoices;
import sifen.SendPosInvoicesAsync;
import sifen.SendRcvInvoicesAsync;

public class SendPosInvoicesControllerBeta {
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
	private String fromTime;
	private String toTime;
	private long batchId;
	private int fromGroup;
	private int toGroup;
	private int invoicesQty;
	private int notValidInvoicesQty;
	private int preparedQty;
	private SendGroup selectedGroup;
	private long selectedGroupId;
	private ArrayList<SendGroup> groupsList = new ArrayList<SendGroup> ();
	private PosSendGroupsTM sendTableModel;

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

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
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

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<SendGroup> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(ArrayList<SendGroup> groupsList) {
		this.groupsList = groupsList;
	}

	public PosSendGroupsTM getSendTableModel() {
		return sendTableModel;
	}

	public void setSendTableModel(PosSendGroupsTM tableModel) {
		this.sendTableModel = tableModel;
	}

	public PosOption getPosOpts() {
		return posOpts;
	}

	public void setPosOpts(PosOption posOpts) {
		this.posOpts = posOpts;
	}

	public String getTRX_TYPE() {
		return TRX_TYPE;
	}
	
	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.event = null;
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
			// obtener los numeros del primer y del ultimo lote correspondiente a la fecha
			GenericStringsList x = this.getBatchesRange();
			if ( x != null ) {
				this.setFromGroup(Integer.parseInt(x.getElement1()));
				this.setToGroup(Integer.parseInt(x.getElement2()));
			}
			return null;
		} else {
			return null;
		}
	}

	public ApplicationMessage enterTimeFrom ( String s ) {
		ApplicationMessage aMsg;
		String hh = null;
		String mi = null;
		//
		if (s != null) {
			if (s.length() < 4) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("TIME-VAL", "La longitud del valor de la hora no puede ser menor a 4", ApplicationMessage.ERROR);
				return aMsg;    							
			}
			if (s.length() > 5) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("TIME-VAL", "La longitud del valor de la hora no puede ser mayor a 5", ApplicationMessage.ERROR);
				return aMsg;    							
			}
			//19:23
			//01234
			//1923
			//0123
			if (s.substring(2, 3).equalsIgnoreCase(":")) {
				hh = s.substring(0, 2);
				mi = s.substring(3, 5);
			} else {
				hh = s.substring(0, 2);
				mi = s.substring(2, 4);			
			}
			//
			try {
				int x = Integer.parseInt(hh);
				if (x < 0 | x > 23) {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("TIME-VAL", "El valor de la hora debe estar entre 0 y 23", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("HOUR-FORMAT", "Numero correspondiente a la hora no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			try {
				int x = Integer.parseInt(hh);
				if (x < 0 | x > 59) {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("TIME-VAL", "El valor de los minutos debe estar entre 0 y 59", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("MIN-FORMAT", "Numero correspondiente a la hora no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			//
			this.setFromTime(hh + ":" + mi);
			return null;
		} else {
			this.setFromTime("00:00");
			return null;
		}
	}

	public ApplicationMessage enterTimeTo ( String s ) {
		ApplicationMessage aMsg;
		String hh = null;
		String mi = null;
		//
		if (s != null) {
			if (s.length() < 4) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("TIME-VAL", "La longitud del valor de la hora no puede ser menor a 4", ApplicationMessage.ERROR);
				return aMsg;    							
			}
			if (s.length() > 5) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("TIME-VAL", "La longitud del valor de la hora no puede ser mayor a 5", ApplicationMessage.ERROR);
				return aMsg;    							
			}
			//19:23
			//01234
			//1923
			//0123
			if (s.substring(2, 3).equalsIgnoreCase(":")) {
				hh = s.substring(0, 2);
				mi = s.substring(3, 5);
			} else {
				hh = s.substring(0, 2);
				mi = s.substring(2, 4);			
			}
			//
			try {
				int x = Integer.parseInt(hh);
				if (x < 0 | x > 23) {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("TIME-VAL", "El valor de la hora debe estar entre 0 y 23", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("HOUR-FORMAT", "Numero correspondiente a la hora no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			try {
				int x = Integer.parseInt(hh);
				if (x < 0 | x > 59) {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("TIME-VAL", "El valor de los minutos debe estar entre 0 y 59", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (NumberFormatException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("MIN-FORMAT", "Numero correspondiente a la hora no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			//
			this.setToTime(hh + ":" + mi);
			return null;
		} else {
			this.setToTime("23:59");
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

	public GenericStringsList getBatchesRange () {
		try {
			GenericStringsList x = PosTrxEbBatchesDAO.getNotSendList(FACTURA, this.trxDate);
			return x;
		} catch (Exception e) {
			e.printStackTrace();
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
			System.out.println("enviando parametros: " + this.trxDate + " - " +
		              this.fromGroup + " - " + this.toGroup + " - " + rowsPerGroup );
			this.groupsList = PosTrxEbBatchesDAO.getTrxGroupsList(trxDate, fromGroup, toGroup, 50);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createSendItemsTable ( ) {
		if (sendTableModel == null) {
			sendTableModel = new PosSendGroupsTM();
		}
		sendTableModel.getModelData ( groupsList );
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

	public PosSendGroupsTM createSendTable () {
		int rowsPerGroup = 50;
		if (posOpts != null) {
		    rowsPerGroup = (int) posOpts.getEbBatchTxQty();
		    if (rowsPerGroup == 0) {
		    	    rowsPerGroup = 50;
		    }
		}
		//
		PosSendGroupsTM tm = new PosSendGroupsTM();
		tm.getModelData(this.trxDate, this.fromGroup, this.toGroup, 50);
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
	
	public ApplicationMessage prepareTaxNumbers () {
		ApplicationMessage aMsg = null;
		/**
		 +---------------------------------------------------------------------------+
		 | Primera actividad de preparacion                                          |
		 | Consultar los numeros de contribuyente que no existen en la base de datos |
		 | de la aplicacion                                                          |
		 +---------------------------------------------------------------------------+
		 */
		ArrayList<String> tList = RcvCustomersTrxDAO.getTaxPayersList(-1, this.trxDate);
		if (tList != null) {
			if (tList.size() > 0) {
				QueryPosInvoices p = new QueryPosInvoices();
				aMsg = p.queryTaxNumbers(tList);
			}
		}
		return aMsg;
	}
	
	public ApplicationMessage prepareTrx () {
		ApplicationMessage aMsg = null;
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//String d = sdf.format(this.trxDate);
		/**
		 +---------------------------------------------------------------------------+
		 | Segunda actividad de preparacion                                          |
		 | Generar los datos de las transacciones en las tablas de facturacion elec- |
		 | tronica                                                                   |
		 +---------------------------------------------------------------------------+
		 */
		if (this.fromTime == null) {
			this.setFromTime("00:00");
		}
		if (this.toTime == null) {
			this.setToTime("23:59");
		}
		PrepareTxParams x = PosEbInvoicesDAO.prepareInvoices(this.trxDate, this.fromTime, this.toTime);
		if (x != null) {
			this.setBatchId(x.getBatchId());
			this.setFromGroup(x.getFirstGroup());
			this.setToGroup(x.getLastGroup());
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", x.getRowsQty() + " transacciones preparadas para envio", ApplicationMessage.MESSAGE);
			return aMsg;
		} else {
			this.setBatchId(0);
			this.setFromGroup(0);
			this.setToGroup(0);
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", "No se ha podido preparar ninguna transaccion para envio", ApplicationMessage.ERROR);
			return aMsg;			
		}
	}

	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTxBatch ( int groupNumber ) {
		ApplicationMessage sMsg = new ApplicationMessage();
		ApplicationMessage qMsg;
		int fromGrp = this.fromGroup;
		int toGrp = this.toGroup;
		try {
			if (groupNumber > 0) {
				fromGrp = groupNumber;
				toGrp = groupNumber;
			}
			// esta clase se ha convertido en un thread, ya no puede ser invocada
			// en forma directa
			/*
		    SendPosInvoicesAsync t = new SendPosInvoicesAsync();
		    sMsg = t.sendDeBatch ( TRX_TYPE, 
		    		                   this.trxDate, 
		    		                   fromGrp, 
		    		                   toGrp,
		    		                   posOpts.getEbBatchTxQty(),
		    		                   UserAttributes.userOrg.getIDENTIFIER(), 
		    		                   UserAttributes.userUnit.getIDENTIFIER(), 
		    		                   UserAttributes.userName );
		    // consultar todos lo lotes POS no verificados de la fecha
		    // Mayo 15, 2023
		    // Los lotes se consultan automaticamente dentro del proceso de envio
		    // una vez completada una cantidad determinada de lotes enviados
		    //qMsg = t.queryBatchesList(this.trxDate);
		    //
		    */
		    return sMsg;
		} catch ( Exception e2 ) {
			sMsg.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return sMsg;
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
