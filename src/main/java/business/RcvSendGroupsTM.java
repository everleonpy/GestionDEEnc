package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvTrxEbBatchesDAO;
import pojo.SendGroup;

public class RcvSendGroupsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<SendGroup> dataList = new ArrayList<SendGroup> ();
	private String columns [] = {"No. Grupo", "Cantidad Transacciones", "Cantidad No Enviada"};
	
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
		SendGroup o = (SendGroup) dataList.get(row);
		//	private String columns [] = {"No. Grupo", "Cantidad Transacciones", "Cantidad No Enviada"};
		switch (col) {
		// numero de grupo
		case 0:
			return o.getGroupNumber();
		// cantidad de transacciones
		case 1:
			return o.getTxQuantity();
		// cantidad de transacciones no enviadas
		case 2:
			return o.getNotSentQuantity();	
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
    	    	    SendGroup o = new SendGroup ();
    	        o = dataList.get(row);
    	        // actualizar el modelo de datos
    	        dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( SendGroup o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<SendGroup> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public SendGroup getItemAt ( int row ) {
		return (SendGroup)dataList.get(row);
	}
	
	public SendGroup removeItemAt ( int row) {
		fireTableDataChanged ();
		return (SendGroup)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( java.util.Date trxDate, 
			                                 int txType,
			                                 int fromNumber,
			                                 int toNumber,
			                                 int rowsPerGroup ) {
		ApplicationMessage aMsg;
		try {
			    dataList = RcvTrxEbBatchesDAO.getTrxGroupsList(trxDate, txType, fromNumber, toNumber, rowsPerGroup);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				SendGroup o = new SendGroup ();
				o.setGroupNumber(0);
				o.setNotSentQuantity(0);
				o.setSentQuantity(0);
				o.setTxQuantity(0);
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
	
	public void getModelData ( ArrayList<SendGroup> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		SendGroup o = new SendGroup ();
		o.setGroupNumber(0);
		o.setNotSentQuantity(0);
		o.setSentQuantity(0);
		o.setTxQuantity(0);
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<SendGroup> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<SendGroup> dataList) {
		this.dataList = dataList;
	}
}
