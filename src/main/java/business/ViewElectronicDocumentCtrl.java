package business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.beans.DocumentoElectronico;

import dao.RcvTrxEbBatchItemsDAO;
import pojo.RcvTrxEbBatchItem;

public class ViewElectronicDocumentCtrl {
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
	private DocumentoElectronico DE;
	private String batchNumber;
	private long batchId;
	private RcvTrxEbBatchItem selectedItem;
	private String selectedItemNo;
	private ArrayList<RcvTrxEbBatchItem> itemsList = new ArrayList<RcvTrxEbBatchItem> ();
	private RcvTrxEbBatchItemsTM queryTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public DocumentoElectronico getDE() {
		return DE;
	}

	public void setDE(DocumentoElectronico dE) {
		DE = dE;
	}
	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public RcvTrxEbBatchItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(RcvTrxEbBatchItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getSelectedItemNo() {
		return selectedItemNo;
	}

	public void setSelectedItemNo(String selectedItemNo) {
		this.selectedItemNo = selectedItemNo;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<RcvTrxEbBatchItem> getItemsList() {
		return itemsList;
	}

	public void setBatchesList(ArrayList<RcvTrxEbBatchItem> itemsList) {
		this.itemsList = itemsList;
	}

	public RcvTrxEbBatchItemsTM getQueryTableModel() {
		return queryTableModel;
	}

	public void setQueryTableModel(RcvTrxEbBatchItemsTM tableModel) {
		this.queryTableModel = tableModel;
	}

	public ApplicationMessage initForm() {
		clearFilters ();
	    loadQueryDataModel();
	    createQueryItemsTable();
		return null;
	}   

	private void clearFilters () {
		this.selectedItem = null;
		this.selectedItemNo = null;
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

	public ApplicationMessage loadQueryDataModel () {
		try {
			System.out.println("enviando parametros: " + this.batchId );
			this.itemsList = RcvTrxEbBatchItemsDAO.getList(this.batchId);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createQueryItemsTable ( ) {
		if (queryTableModel == null) {
			queryTableModel = new RcvTrxEbBatchItemsTM();
		}
		queryTableModel.getModelData ( itemsList );
		queryTableModel.fireTableDataChanged();
	}	

	public RcvTrxEbBatchItemsTM createQueryEmptyTable ( ) {
		RcvTrxEbBatchItemsTM tm = new RcvTrxEbBatchItemsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public boolean isConnectionAvailable () {
		try {
			return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
		} catch (Exception e) {
			return false;   			
		}
	}

}
