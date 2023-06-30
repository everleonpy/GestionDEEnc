package business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import dao.PosTrxEbBatchItemsDAO;
import pojo.PosTrxEbBatchItem;

public class ViewPosTrxBatchItemsCtrl {
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
	private PosTrxEbBatchItem selectedItem;
	private String selectedItemNo;
	private ArrayList<PosTrxEbBatchItem> itemsList = new ArrayList<PosTrxEbBatchItem> ();
	private PosTrxEbBatchItemsTM queryTableModel;

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

	public PosTrxEbBatchItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(PosTrxEbBatchItem selectedItem) {
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

	public ArrayList<PosTrxEbBatchItem> getItemsList() {
		return itemsList;
	}

	public void setBatchesList(ArrayList<PosTrxEbBatchItem> itemsList) {
		this.itemsList = itemsList;
	}

	public PosTrxEbBatchItemsTM getQueryTableModel() {
		return queryTableModel;
	}

	public void setQueryTableModel(PosTrxEbBatchItemsTM tableModel) {
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

	public void getBatchData ( long itemId ) {
		selectedItem = null;
		Iterator itr1 = itemsList.iterator();
		while (itr1.hasNext()) {
			PosTrxEbBatchItem i = (PosTrxEbBatchItem) itr1.next();
			if (i.getIdentifier() == itemId) {
				selectedItem = i;
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

	public ApplicationMessage loadQueryDataModel () {
		try {
			System.out.println("enviando parametros: " + this.batchId );
			this.itemsList = PosTrxEbBatchItemsDAO.getList(this.batchId);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createQueryItemsTable ( ) {
		if (queryTableModel == null) {
			queryTableModel = new PosTrxEbBatchItemsTM();
		}
		queryTableModel.getModelData ( itemsList );
		queryTableModel.fireTableDataChanged();
	}	

	public PosTrxEbBatchItemsTM createQueryEmptyTable ( ) {
		PosTrxEbBatchItemsTM tm = new PosTrxEbBatchItemsTM();
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
