package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvEbInvoicesDAO;
import pojo.RcvEbInvoice;

public class RcvEbInvoicesTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RcvEbInvoice> dataList = new ArrayList<RcvEbInvoice> ();
	private String columns [] = {"No. Factura", "CDC", "Fecha", "Importe",
                                 "Tipo Identidad", "No. Identidad", "Cliente", "Condicion", 
                                 "Sucursal", "Identificador", "Lote Aplicacion"};
	
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
		RcvEbInvoice o = (RcvEbInvoice) dataList.get(row);
		//"No. Factura", "CDC", "Fecha", "Importe",
        //"Tipo Identidad", "No. Identidad", "Cliente", "Condicion", 
        //"Sucursal", "Identificador", "Lote Envio"};

		switch (col) {
		// numero de factura
		case 0:
			return o.getTxNumber();
		// CDC
		case 1:
			return o.getEbControlCode();
		// fecha transaccion
		case 2:
			return o.getDefeemide();
		// importe de factura
		case 3:
			return o.getAmount();
		// Tipo Identidad
		case 4:
			return o.getCustDocType();
	    // Numero de Identidad
		case 5:
			return o.getCustDocNumber();
		// nombre contribuyente/cliente
		case 6:
			return o.getCustomername();
		// condicion de veta
		case 7:
			return o.getTrxcondition();
		// sucursal
		case 8:
			return o.getSiteName();
		// identificador
		case 9:
			return o.getIdentifier();
		// id. de lote de procesamiento para envio
		case 10:
			return o.getBatchId();
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
    		RcvEbInvoice o = new RcvEbInvoice ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( RcvEbInvoice o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<RcvEbInvoice> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public RcvEbInvoice getItemAt ( int row ) {
		return (RcvEbInvoice)dataList.get(row);
	}
	
	public RcvEbInvoice removeItemAt ( int row) {
		fireTableDataChanged ();
		return (RcvEbInvoice)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( int trxType,
			                                 java.util.Date txDate, 
			                                 long siteId, 
			                                 String txNumber) {
		ApplicationMessage aMsg;
		try {
			dataList = RcvEbInvoicesDAO.getPreparedList(trxType, txDate, siteId, txNumber);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				RcvEbInvoice o = new RcvEbInvoice ();
				o.setIdentifier(0);
				o.setCustomername(" ");
				o.setDefeemide(null);
				o.setTxNumber(" ");
				o.setSiteName(" ");
				o.setTaxnumber(" ");
				o.setIdentitynumber("");
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
	
	public void getModelData ( ArrayList<RcvEbInvoice> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		RcvEbInvoice o = new RcvEbInvoice ();
		o.setIdentifier(0);
		o.setCustomername(" ");
		o.setDefeemide(null);
		o.setTxNumber(" ");
		o.setSiteName(" ");
		o.setTaxnumber(" ");
		o.setIdentitynumber("");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<RcvEbInvoice> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<RcvEbInvoice> dataList) {
		this.dataList = dataList;
	}
}
