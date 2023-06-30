package business;

import java.util.ArrayList;

import dao.InvShpEbBatchItemsDAO;
import pojo.InvShpEbBatchItem;

public class ViewInvShpBatchItemsCtrl {
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
	private String batchNumber;
	private long batchId;
	private InvShpEbBatchItem selectedItem;
	private String selectedItemNo;
	private ArrayList<InvShpEbBatchItem> itemsList = new ArrayList<InvShpEbBatchItem> ();
	private InvShpEbBatchItemsTM queryTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
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

	public InvShpEbBatchItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(InvShpEbBatchItem selectedItem) {
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

	public ArrayList<InvShpEbBatchItem> getItemsList() {
		return itemsList;
	}

	public void setBatchesList(ArrayList<InvShpEbBatchItem> itemsList) {
		this.itemsList = itemsList;
	}

	public InvShpEbBatchItemsTM getQueryTableModel() {
		return queryTableModel;
	}

	public void setQueryTableModel(InvShpEbBatchItemsTM tableModel) {
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
			this.itemsList = InvShpEbBatchItemsDAO.getList(this.batchId);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createQueryItemsTable ( ) {
		if (queryTableModel == null) {
			queryTableModel = new InvShpEbBatchItemsTM();
		}
		queryTableModel.getModelData ( itemsList );
		queryTableModel.fireTableDataChanged();
	}	

	public InvShpEbBatchItemsTM createQueryEmptyTable ( ) {
		InvShpEbBatchItemsTM tm = new InvShpEbBatchItemsTM();
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
