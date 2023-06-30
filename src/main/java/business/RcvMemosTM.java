package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.RcvCustomersTrxDAO;
import pojo.RcvMemo;

public class RcvMemosTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RcvMemo> dataList = new ArrayList<RcvMemo> ();
	private String columns [] = {"No. Nota", "Fecha", "Tipo", "Importe",
			                     "No. Identidad", "Tipo Identidad", "Cliente", "Estado",
			                     "CDC", "Identificador", "No. Factura", "Lote Informado"};
	
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
		RcvMemo o = (RcvMemo) dataList.get(row);
		//"No. Nota", "Fecha", "Tipo", "Importe",
        //"No. Identidad", "Tipo Identidad", "Cliente", "Estado",
        //"CDC", "Identificador", "No. Factura", "Lote Envio";
		switch (col) {
		// no. de nota
		case 0:
			return o.getTxNumber();
		// fecha transaccion
		case 1:
			return o.getTxDate();	
		// tipo de nota
		case 2:
			return o.getTxTypeName();
		// importe
		case 3:
			return o.getAmount();
		// numero de identificacion del cliente
		case 4:
			return o.getCustDocNumber();
		// tipo de identificacion del cliente
		case 5:
			return o.getCustDocType();
		// denominacion del cliente
		case 6:
			return o.getCustomerName();
		// estado de la transaccion
		case 7:
			return o.getStatus();
		// CDC
		case 8:
			return o.getControlCode();
		// Identificador de nota
		case 9:
			return o.getMemoId();
		// No. de factura vinculada
		case 10:
			return o.getInvoiceNumber();
		// Identificador de lote de envio
		case 11:
			return o.getEbBatchId();
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
    		RcvMemo o = new RcvMemo ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( RcvMemo o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<RcvMemo> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public RcvMemo getItemAt ( int row ) {
		return (RcvMemo)dataList.get(row);
	}
	
	public RcvMemo removeItemAt ( int row) {
		fireTableDataChanged ();
		return (RcvMemo)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long siteId, 
			                                 java.util.Date trxDate ) {
		ApplicationMessage aMsg;
		try {
			dataList = RcvCustomersTrxDAO.getMemosNotSentList(siteId, trxDate);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				RcvMemo o = new RcvMemo ();
				o.setMemoId(0);
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
	
	public void getModelData ( ArrayList<RcvMemo> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		RcvMemo o = new RcvMemo ();
		o.setMemoId(0);
		o.setCustomerName(" ");
		o.setTxDate(null);
		o.setCustomerNumber(" ");
		o.setSiteName(" ");
		o.setTxTypeName(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<RcvMemo> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<RcvMemo> dataList) {
		this.dataList = dataList;
	}

}
