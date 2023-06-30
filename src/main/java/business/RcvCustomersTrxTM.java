package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvCustomersTrxDAO;
import pojo.RcvCustomerTrx;
import business.ApplicationMessage;

public class RcvCustomersTrxTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RcvCustomerTrx> dataList = new ArrayList<RcvCustomerTrx> ();
	private String columns [] = {"Numero", "Tipo", "Fecha", "Cliente", "Sucursal", "Estado", "Identificador"};
	
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
		RcvCustomerTrx o = (RcvCustomerTrx) dataList.get(row);
		switch (col) {
		// numero de transaccion
		case 0:
			return o.getTxNumber();
		// tipo de transaccion
		case 1:
			return o.getTxTypeName();
		// fecha transaccion
		case 2:
			return o.getTxDate();	
		// cliente
		case 3:
			return o.getCustomerName();
		// sucursal de la transaccion
		case 4:
			return o.getSiteName();
		// estado
		case 5:
			return o.getStatus();
		// identificador de recepcion
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
    		RcvCustomerTrx o = new RcvCustomerTrx ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( RcvCustomerTrx o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<RcvCustomerTrx> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public RcvCustomerTrx getItemAt ( int row ) {
		return (RcvCustomerTrx)dataList.get(row);
	}
	
	public RcvCustomerTrx removeItemAt ( int row) {
		fireTableDataChanged ();
		return (RcvCustomerTrx)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long siteId, 
			                                 long txTypeId, 
			                                 long customerId, 
			                                 java.util.Date fromDate, 
			                                 java.util.Date toDate, 
			                                 String transactionNo ) {
		ApplicationMessage aMsg;
		try {
			dataList = RcvCustomersTrxDAO.getList ( siteId, txTypeId, customerId, fromDate, toDate, transactionNo);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				RcvCustomerTrx o = new RcvCustomerTrx ();
				o.setIdentifier(0);
				o.setCustomerName(" ");
				o.setTxDate(null);
				o.setIdentityNumber(" ");
				o.setSiteName(" ");
				o.setTxTypeName(" ");
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
	
	public void getModelData ( ArrayList<RcvCustomerTrx> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		RcvCustomerTrx o = new RcvCustomerTrx ();
		o.setIdentifier(0);
		o.setCustomerName(" ");
		o.setTxDate(null);
		o.setIdentityNumber(" ");
		o.setSiteName(" ");
		o.setTxTypeName(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<RcvCustomerTrx> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<RcvCustomerTrx> dataList) {
		this.dataList = dataList;
	}
}
