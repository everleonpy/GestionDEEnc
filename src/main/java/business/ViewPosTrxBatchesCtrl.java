package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.PosOptionsDAO;
import dao.PosTrxEbBatchesDAO;
import pojo.PosOption;
import pojo.PosTrxEbBatch;
import sifen.QueryPosInvoices;
import sifen.SendPosInvoicesAsync;

public class ViewPosTrxBatchesCtrl {
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
	private String batchNumber;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private PosTrxEbBatch selectedBatch;
	private String selectedBatchNo;
	private ArrayList<PosTrxEbBatch> batchesList = new ArrayList<PosTrxEbBatch> ();
	private PosTrxEbBatchesTM queryTableModel;
	private PosTrxEbBatchItemsTM itemsTableModel;
	
	private final String trxType = "FACTURA";
	
	// internal workflow variables
	private String appAction;

	// getters and setters	
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

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public PosTrxEbBatch getSelectedBatch() {
		return selectedBatch;
	}

	public void setSelectedBatch(PosTrxEbBatch selectedBatch) {
		this.selectedBatch = selectedBatch;
	}

	public String getSelectedBatchNo() {
		return selectedBatchNo;
	}

	public void setSelectedBatchNo(String selectedBatchNo) {
		this.selectedBatchNo = selectedBatchNo;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<PosTrxEbBatch> getBatchesList() {
		return batchesList;
	}

	public void setBatchesList(ArrayList<PosTrxEbBatch> batchesList) {
		this.batchesList = batchesList;
	}

	public PosTrxEbBatchesTM getQueryTableModel() {
		return queryTableModel;
	}

	public void setQueryTableModel(PosTrxEbBatchesTM tableModel) {
		this.queryTableModel = tableModel;
	}

	public PosTrxEbBatchItemsTM getItemsTableModel() {
		return itemsTableModel;
	}

	public void setItemsTableModel(PosTrxEbBatchItemsTM itemsTableModel) {
		this.itemsTableModel = itemsTableModel;
	}

	public ApplicationMessage initForm() {
		posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.batchNumber = null;
		this.fromDate = null;
		this.toDate = null;
		this.selectedBatch = null;
		this.selectedBatchNo = null;
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

	public void getBatchData ( long batchId ) {
		selectedBatch = null;
		Iterator itr1 = batchesList.iterator();
		while (itr1.hasNext()) {
			PosTrxEbBatch i = (PosTrxEbBatch) itr1.next();
			if (i.getIdentifier() == batchId) {
				selectedBatch = i;
			}
		}
	}
	
	public ApplicationMessage enterBatchNumber ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			this.setBatchNumber(s);
			return null;
		} else {
			return null;
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

	public ApplicationMessage loadQueryDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxType + " - " + this.fromDate + " - " +
		              this.toDate + " - " + this.batchNumber );
			this.batchesList = PosTrxEbBatchesDAO.getList(this.trxType, this.fromDate, this.toDate, this.batchNumber);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createQueryItemsTable ( ) {
		if (queryTableModel == null) {
			queryTableModel = new PosTrxEbBatchesTM();
		}
		queryTableModel.getModelData ( batchesList );
		queryTableModel.fireTableDataChanged();
	}	

	public PosTrxEbBatchesTM createQueryEmptyTable ( ) {
		PosTrxEbBatchesTM tm = new PosTrxEbBatchesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosTrxEbBatchItemsTM createItemsEmptyTable ( ) {
		PosTrxEbBatchItemsTM tm = new PosTrxEbBatchItemsTM();
		tm.getEmptyModel();
		return tm;
	}	
	
	public PosTrxEbBatchesTM createQueryTable () {
		PosTrxEbBatchesTM tm = new PosTrxEbBatchesTM();
		tm.getModelData(this.trxType, this.fromDate, this.toDate, this.batchNumber);
		return tm;
	}	

	public ApplicationMessage querySingleBatch ( String batchNumber, long batchId, int itemsQty ) {
		ApplicationMessage m;
		try {
	        QueryPosInvoices t = new QueryPosInvoices();
	        m = t.querySingleBatch( batchNumber, batchId, itemsQty );
	        return m;
		} catch ( SifenException e1 ) {
			m = new ApplicationMessage();
			m.setMessage("QUERY-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m = new ApplicationMessage();
			m.setMessage("QUERY-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public ApplicationMessage queryBatchesGroup ( ) {
		ApplicationMessage m;
		try {
			ArrayList<PosTrxEbBatch> l = PosTrxEbBatchesDAO.getList(this.trxType, this.fromDate, this.toDate, null);
			if (l != null) {
				if (l.size() > 0) {
					Iterator itr1 = l.iterator();
					while (itr1.hasNext()) {
                        PosTrxEbBatch x = (PosTrxEbBatch) itr1.next();
                        if (x.getQueriedFlag().equalsIgnoreCase("N")) {
                        	    System.out.println("*****Importando lote no. " + x.getBatchNumber());
                        	    QueryPosInvoices t = new QueryPosInvoices();
			                m = t.querySingleBatch ( x.getBatchNumber(), x.getIdentifier(), x.getItemsQty() );  
                        }
				    }
				}
			}
			m = new ApplicationMessage();
			m.setMessage("QUERY-TRX", "Actividad finalizada con exito", ApplicationMessage.MESSAGE);
			return m;
		} catch ( SifenException e1 ) {
			m = new ApplicationMessage();
			m.setMessage("QUERY-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m = new ApplicationMessage();
			m.setMessage("QUERY-TRX", e2.getMessage(), ApplicationMessage.ERROR);
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
