package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PosTrxEbBatchItemsDAO;
import pojo.PosTrxEbBatchItem;

public class PosTrxEbBatchItemsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PosTrxEbBatchItem> dataList = new ArrayList<PosTrxEbBatchItem> ();
	private String columns [] = {"No. Item", "No. Transaccion", "CDC", "Estado",  
			                     "Cod. Respuesta", "Respuesta", "Identificador"};
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		if (dataList != null) {
		    return dataList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		PosTrxEbBatchItem o = (PosTrxEbBatchItem) dataList.get(row);
		//"No. Item", "No. Transaccion", "CDC", "Estado",  
        //"Cod. Respuesta", "Respuesta", "Identificador"};
		switch (col) {
		// numero de item
		case 0:
			return (row + 1);
		// numero de transaccion en origen
		case 1:
			return o.getLocalDocNumber();
		// CDC
		case 2:
			return o.getControlCode();
		// estado documento
		case 3:
			return o.getResultStatus();	
		// codigo de la respuesta
		case 4:
			return o.getResultCode();
		// mensaje de la respuesta
		case 5:
			return o.getResultMessage();
		// identificador
		case 6:
			return o.getIdentifier();
		default:
		    return null;
		}
	}
	
	public String getColumnName ( int col ) {
		return columns[col];
	}
	
	public boolean isCellEditable(int row, int col) {
   		return false;
    }
    
	public Class getColumnClass(int col) {
		//System.out.println("col: " + col);
		if (getValueAt(0, col) != null) {
		    return getValueAt(0, col).getClass();
		} else {
			return String.class;
		}
    }

    public void setValueAt(Object value, int row, int col) {
    	    if ( col == 0 ) {
    	    	PosTrxEbBatchItem o = new PosTrxEbBatchItem ();
    	        o = dataList.get(row);
    	        // actualizar el modelo de datos
    	        dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( PosTrxEbBatchItem o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<PosTrxEbBatchItem> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public PosTrxEbBatchItem getItemAt ( int row ) {
		return (PosTrxEbBatchItem)dataList.get(row);
	}
	
	public PosTrxEbBatchItem removeItemAt ( int row) {
		fireTableDataChanged ();
		return (PosTrxEbBatchItem)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long batchId ) {
		ApplicationMessage aMsg;
		try {
			    dataList = PosTrxEbBatchItemsDAO.getList ( batchId );	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				PosTrxEbBatchItem o = new PosTrxEbBatchItem ();
				o.setIdentifier(0);
				o.setControlCode(" ");
				o.setResultStatus(" ");
				o.setResultCode(0);
				o.setResultMessage(" ");
				dataList.add(0, o);
			}
			fireTableDataChanged ();
			return null;		
		} catch (Exception e) {
			aMsg = new ApplicationMessage ();
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}		
	}
	
	public void getModelData ( ArrayList<PosTrxEbBatchItem> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		PosTrxEbBatchItem o = new PosTrxEbBatchItem ();
		o.setIdentifier(0);
		o.setControlCode(" ");
		o.setResultStatus(" ");
		o.setResultCode(0);
		o.setResultMessage(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<PosTrxEbBatchItem> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<PosTrxEbBatchItem> dataList) {
		this.dataList = dataList;
	}
}
