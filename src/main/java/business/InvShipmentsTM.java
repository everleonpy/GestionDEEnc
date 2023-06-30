package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.InvInternalShipmentsDAO;
import pojo.InvShipment;

public class InvShipmentsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<InvShipment> dataList = new ArrayList<InvShipment> ();
	private String columns [] = {"No. Remision", "Fecha", "Tipo", "Destino", 
			                     "Origen", "No. Identidad", "Tipo Identidad", "Cliente", 
			                     "Estado", "CDC", "Identificador", "Lote Informado"};
	
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
		InvShipment o = (InvShipment) dataList.get(row);
		//"No. Remision", "Fecha", "Tipo", "Destino", 
        //"Origen", "No. Identidad", "Tipo Identidad", "Cliente", 
        //"Estado", "CDC", "Identificador", "Lote Informado"};
		switch (col) {
		// no. de remision
		case 0:
			return o.getWaybillNo();
		// fecha de remision
		case 1:
			return o.getShipmentDate();	
		// tipo de envio
		case 2:
			return o.getShipmentType();
		// sucursal destino
		case 3:
			return o.getToSiteName();
		// sucursal origen
		case 4:
			return o.getSiteName();
		// numero de identificacion del cliente
		case 5:
			return o.getCustDocNumber();
		// tipo de identificacion del cliente
		case 6:
			return o.getCustDocType();
		// denominacion del cliente
		case 7:
			return o.getCustomerName();
		// estado de la transaccion
		case 8:
			return o.getShipmentStatus();
		// CDC
		case 9:
			return o.getControlCode();
		// Identificador de factura
		case 10:
			return o.getShipmentId();
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
    		InvShipment o = new InvShipment ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( InvShipment o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<InvShipment> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public InvShipment getItemAt ( int row ) {
		return (InvShipment)dataList.get(row);
	}
	
	public InvShipment removeItemAt ( int row) {
		fireTableDataChanged ();
		return (InvShipment)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( long siteId, 
			                                 java.util.Date trxDate ) {
		ApplicationMessage aMsg;
		try {
			dataList = InvInternalShipmentsDAO.getShipmentsNotSentList(siteId, trxDate);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				InvShipment o = new InvShipment ();
				o.setShipmentId(0);
				o.setToSiteName(" ");
				o.setCustomerName(" ");
				o.setShipmentDate(null);
				o.setCustomerNumber(" ");
				o.setSiteName(" ");
				o.setShipmentType(" ");
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
	
	public void getModelData ( ArrayList<InvShipment> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		InvShipment o = new InvShipment ();
		o.setShipmentId(0);
		o.setToSiteName(" ");
		o.setCustomerName(" ");
		o.setShipmentDate(null);
		o.setCustomerNumber(" ");
		o.setSiteName(" ");
		o.setShipmentType(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<InvShipment> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<InvShipment> dataList) {
		this.dataList = dataList;
	}
}
