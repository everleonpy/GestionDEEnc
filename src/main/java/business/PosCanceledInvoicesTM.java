package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PosTransactionsDAO;
import pojo.PosInvoice;

public class PosCanceledInvoicesTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PosInvoice> dataList = new ArrayList<PosInvoice> ();
	private String columns [] = {"No. Factura", "CDC", "Fecha", "Fecha Anulacion", 
			                     "Importe", "No. Identidad", "Tipo Identidad", "Cliente", 
			                     "Tipo", "Identificador" };
	
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
		//"No. Factura", "CDC", "Fecha", "Fecha Anulacion", 
        //"Importe", "No. Identidad", "Tipo Identidad", "Cliente", 
        //"Tipo", "Identificador" 
		switch (col) {
		// no. de factura
		case 0:
			return o.getTxNumber();
		// CDC
		case 1:
			return o.getControlCode();
		// fecha transaccion
		case 2:
			return o.getTxDate();	
		// fecha anulacion
		case 3:
			return o.getCancelledOn();
		// importe
		case 4:
			return o.getAmount();
		// numero de identificacion del cliente
		case 5:
			return o.getCustDocNumber();
		// tipo de identificacion del cliente
		case 6:
			return o.getCustDocType();
		// denominacion del cliente
		case 7:
			return o.getCustomerName();
		// tipo de factura
		case 8:
			return o.getTxTypeName();
		// Identificador de factura
		case 9:
			return o.getInvoiceId();
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
	
	public ApplicationMessage getModelData ( String trxType,
			                                 java.util.Date trxDate ) {
		ApplicationMessage aMsg;
		try {
			dataList = PosTransactionsDAO.getCanceledList(trxDate, trxType);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				PosInvoice o = new PosInvoice ();
				o.setInvoiceId(0);
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
