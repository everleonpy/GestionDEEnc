package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvTrxEbBatchItemsDAO;
import pojo.RcvTrxEbBatchItem;

public class RcvTrxEbBatchItemsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RcvTrxEbBatchItem> dataList = new ArrayList<RcvTrxEbBatchItem> ();
	private String columns [] = {"No. Transaccion", "CDC", "Estado Resultado", "Cod. Resultado", 
			                     "Mensaje Resultado", "Identificador"};
	
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
		RcvTrxEbBatchItem o = (RcvTrxEbBatchItem) dataList.get(row);
		// "No. Transaccion", "CDC", "Estado Resultado", "Cod. Resultado", 
        // "Mensaje Resultado", "Identificador"};
		switch (col) {
		// numero de transaccion
		case 0:
			return o.getTxNumber();
		// CDC
		case 1:
			return o.getControlCode();
		// estado del resultado
		case 2:
			return o.getResultStatus();	
		// estado del resultado
		case 3:
			return o.getResultCode();	
		// mensaje de la respuesta
		case 4:
			return o.getResultMessage();
		// identificador
		case 5:
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
    	      	RcvTrxEbBatchItem o = new RcvTrxEbBatchItem ();
    	        o = dataList.get(row);
    	        // actualizar el modelo de datos
    	        dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( RcvTrxEbBatchItem o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<RcvTrxEbBatchItem> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public RcvTrxEbBatchItem getItemAt ( int row ) {
		return (RcvTrxEbBatchItem)dataList.get(row);
	}
	
	public RcvTrxEbBatchItem removeItemAt ( int row) {
		fireTableDataChanged ();
		return (RcvTrxEbBatchItem)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long batchId ) {
		ApplicationMessage aMsg;
		try {
			    dataList = RcvTrxEbBatchItemsDAO.getList ( batchId );	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				RcvTrxEbBatchItem o = new RcvTrxEbBatchItem ();
				o.setIdentifier(0);
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
	
	public void getModelData ( ArrayList<RcvTrxEbBatchItem> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		RcvTrxEbBatchItem o = new RcvTrxEbBatchItem ();
		o.setIdentifier(0);
		o.setResultStatus(" ");
		o.setResultCode(0);
		o.setResultMessage(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<RcvTrxEbBatchItem> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<RcvTrxEbBatchItem> dataList) {
		this.dataList = dataList;
	}
}
