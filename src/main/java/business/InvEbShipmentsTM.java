package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.InvEbShipmentsDAO;
import pojo.InvEbShipment;

public class InvEbShipmentsTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<InvEbShipment> dataList = new ArrayList<InvEbShipment> ();
	private String columns [] = {"No. Remision", "CDC", "Fecha", "Destino",
			                     "Origen", "Motivo", "Tipo Identidad", "No. Identidad",
                                  "Cliente", "Identificador", "Lote Aplicacion"};
	
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
		InvEbShipment o = (InvEbShipment) dataList.get(row);
		//"No. Remision", "CDC", "Fecha", "Destino",
        //"Origen", "Motivo", "Tipo Identidad", "No. Identidad",
        //"Cliente", "Identificador", "Lote Aplicacion";

		switch (col) {
		// numero de remision
		case 0:
			return o.getTxNumber();
		// CDC
		case 1:
			return o.getEbControlCode();
		// fecha transaccion
		case 2:
			return o.getDefeemide();
		// sucursal destino
		case 3:
			return o.getToSiteName();
		// sucursal origen
		case 4:
			return o.getFromSiteName();
	    // motivo
		case 5:
			return o.getShipmentReason();
		// tipo de identidad
		case 6:
			return o.getIdentitytype();
	    // tipo de identidad
		case 7:
			return o.getIdentitynumber();
		// nombre contribuyente/cliente
		case 8:
			return o.getCustomername();
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
    		InvEbShipment o = new InvEbShipment ();
    	    o = dataList.get(row);
    	    // actualizar el modelo de datos
    	    dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( InvEbShipment o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<InvEbShipment> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public InvEbShipment getItemAt ( int row ) {
		return (InvEbShipment)dataList.get(row);
	}
	
	public InvEbShipment removeItemAt ( int row) {
		fireTableDataChanged ();
		return (InvEbShipment)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( int trxType,
			                                 java.util.Date txDate, 
			                                 long siteId, 
			                                 String txNumber) {
		ApplicationMessage aMsg;
		try {
			dataList = InvEbShipmentsDAO.getList(txDate, siteId, txNumber);
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				InvEbShipment o = new InvEbShipment ();
				o.setIdentifier(0);
				o.setCustomername(" ");
				o.setDefeemide(null);
				o.setTxNumber(" ");
				o.setFromSiteName(" ");
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
	
	public void getModelData ( ArrayList<InvEbShipment> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		InvEbShipment o = new InvEbShipment ();
		o.setIdentifier(0);
		o.setCustomername(" ");
		o.setDefeemide(null);
		o.setTxNumber(" ");
		o.setFromSiteName(" ");
		o.setTaxnumber(" ");
		o.setIdentitynumber("");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<InvEbShipment> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<InvEbShipment> dataList) {
		this.dataList = dataList;
	}
}
