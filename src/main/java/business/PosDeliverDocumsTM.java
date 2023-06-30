package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PosTransactionsDAO;
import pojo.PosInvoice;
import business.ApplicationMessage;

public class PosDeliverDocumsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PosInvoice> dataList = new ArrayList<PosInvoice> ();
	private String columns [] = {"No. Factura", "RUC", "No. Identidad", "Cliente", 
			                     "E-mail", "Fecha", "Identificador", "Caja", "Control"};
	
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
		PosInvoice o = (PosInvoice) dataList.get(row);
		//"No. Factura", "RUC", "No. Identidad", "Cliente", 
        //"E-mail", "Archivo", "Identificador", "Caja", "Control";
		switch (col) {
		// numero de factura
		case 0:
			return o.getTxNumber();
		// numero de contribuyente
		case 1:
			return o.getTaxNumber();
		// numero de identificacion del cliente
		case 2:
			return o.getCustomerNumber();
		// denominacion del cliente
		case 3:
			return o.getCustomerName();
		// e-mail
		case 4:
			return o.geteMail();
		// fecha de la transaccion
		case 5:
			return o.getTxDate();
		// identificador de la transaccion
		case 6:
			return o.getInvoiceId();
		// identificador de la caja POS
		case 7:
		    return o.getCashId();
		// identificador del control de caja
		case 8:
			return o.getControlId();
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
    		PosInvoice o = new PosInvoice ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( PosInvoice o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<PosInvoice> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public PosInvoice getItemAt ( int row ) {
		return (PosInvoice)dataList.get(row);
	}
	
	public PosInvoice removeItemAt ( int row) {
		fireTableDataChanged ();
		return (PosInvoice)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long cashId, 
			                                 java.util.Date fromDate, 
			                                 java.util.Date toDate ) {
		ApplicationMessage aMsg;
		try {
			dataList = PosTransactionsDAO.getRenderedList(cashId, null, null, fromDate, toDate);	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				PosInvoice o = new PosInvoice ();
				o.setInvoiceId(0);
				o.setControlId(0);
				o.setCashId(0);
				o.setCustomerName(" ");
				o.setTxDate(null);
				o.setCustomerNumber(" ");
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
	
	public void getModelData ( ArrayList<PosInvoice> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		PosInvoice o = new PosInvoice ();
		o.setInvoiceId(0);
		o.setControlId(0);
		o.setCashId(0);
		o.setCustomerName(" ");
		o.setTxDate(null);
		o.setCustomerNumber(" ");
		o.setSiteName(" ");
		o.setTxTypeName(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<PosInvoice> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<PosInvoice> dataList) {
		this.dataList = dataList;
	}
}
