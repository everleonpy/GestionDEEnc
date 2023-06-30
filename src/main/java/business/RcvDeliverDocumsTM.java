package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvCustomersTrxDAO;
import pojo.RcvInvoice;
import business.ApplicationMessage;

public class RcvDeliverDocumsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RcvInvoice> dataList = new ArrayList<RcvInvoice> ();
	private String columns [] = {"No. Factura", "RUC", "No. Identidad", "Cliente", 
			                     "E-mail", "Archivo", "Identificador"};
	
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
		RcvInvoice o = (RcvInvoice) dataList.get(row);
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
		// nombre del archivo a enviar
		case 5:
			return o.getgRFileName();
		// identificador de la transaccion
		case 6:
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
    		RcvInvoice o = new RcvInvoice ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( RcvInvoice o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<RcvInvoice> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public RcvInvoice getItemAt ( int row ) {
		return (RcvInvoice)dataList.get(row);
	}
	
	public RcvInvoice removeItemAt ( int row) {
		fireTableDataChanged ();
		return (RcvInvoice)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long siteId, 
			                                 java.util.Date fromDate, 
			                                 java.util.Date toDate ) {
		ApplicationMessage aMsg;
		try {
			dataList = RcvCustomersTrxDAO.getRenderedList(siteId, fromDate, toDate);	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				RcvInvoice o = new RcvInvoice ();
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
	
	public void getModelData ( ArrayList<RcvInvoice> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		RcvInvoice o = new RcvInvoice ();
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
	
	public ArrayList<RcvInvoice> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<RcvInvoice> dataList) {
		this.dataList = dataList;
	}
}
