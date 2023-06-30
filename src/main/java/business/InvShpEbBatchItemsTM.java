package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.InvShpEbBatchItemsDAO;
import pojo.InvShpEbBatchItem;

public class InvShpEbBatchItemsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<InvShpEbBatchItem> dataList = new ArrayList<InvShpEbBatchItem> ();
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
		InvShpEbBatchItem o = (InvShpEbBatchItem) dataList.get(row);
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
    	    	InvShpEbBatchItem o = new InvShpEbBatchItem ();
    	        o = dataList.get(row);
    	        // actualizar el modelo de datos
    	        dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( InvShpEbBatchItem o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<InvShpEbBatchItem> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public InvShpEbBatchItem getItemAt ( int row ) {
		return (InvShpEbBatchItem)dataList.get(row);
	}
	
	public InvShpEbBatchItem removeItemAt ( int row) {
		fireTableDataChanged ();
		return (InvShpEbBatchItem)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long batchId ) {
		ApplicationMessage aMsg;
		try {
			    dataList = InvShpEbBatchItemsDAO.getList ( batchId );	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				InvShpEbBatchItem o = new InvShpEbBatchItem ();
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
	
	public void getModelData ( ArrayList<InvShpEbBatchItem> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		InvShpEbBatchItem o = new InvShpEbBatchItem ();
		o.setIdentifier(0);
		o.setResultStatus(" ");
		o.setResultCode(0);
		o.setResultMessage(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<InvShpEbBatchItem> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<InvShpEbBatchItem> dataList) {
		this.dataList = dataList;
	}
}
