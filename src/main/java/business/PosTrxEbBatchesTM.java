package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PosTrxEbBatchesDAO;
import pojo.PosTrxEbBatch;

public class PosTrxEbBatchesTM extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<PosTrxEbBatch> dataList = new ArrayList<PosTrxEbBatch> ();
	private String columns [] = {"No. Lote", "Fecha Documentos", "Fecha Recepcion", "Cod. Respuesta", 
			                     "Respuesta", "Tiempo Proceso", "Cant. Items", "Verificado",
			                     "Identificador"};
	
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
		PosTrxEbBatch o = (PosTrxEbBatch) dataList.get(row);
		//"No. Lote", "Fecha Documentos", "Fecha Recepcion", "Cod. Respuesta", 
        //"Respuesta", "Tiempo Proceso", "Cant. Items", "Verificado",
        //"Identificador"
		switch (col) {
		// numero de lote
		case 0:
			return o.getBatchNumber();
		// fecha de recepcion
		case 1:
			return o.getTrxDate();
		// fecha de recepcion
		case 2:
			return o.getTransmissDate();
		// codigo de respuesta
		case 3:
			return o.getResultCode();	
		// mensaje de la respuesta
		case 4:
			return o.getResultMessage();
		// tiempo de procesamiento
		case 5:
			return o.getProcessTime();
		// denominacion del cliente
		case 6:
			return o.getItemsQty();
		// denominacion del cliente
		case 7:
			return o.getQueriedFlag();
		// identificador
		case 8:
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
    	    	PosTrxEbBatch o = new PosTrxEbBatch ();
    	        o = dataList.get(row);
    	        // actualizar el modelo de datos
    	        dataList.set(row, o);
            fireTableCellUpdated(row, col);
       	}
    }

	public void addItem ( PosTrxEbBatch o ) {
		dataList.add(o);
		fireTableDataChanged ();
	}
	
	public void addItemsList ( List<PosTrxEbBatch> l) {
		dataList.addAll(l);
		fireTableDataChanged ();
	}
	
	public PosTrxEbBatch getItemAt ( int row ) {
		return (PosTrxEbBatch)dataList.get(row);
	}
	
	public PosTrxEbBatch removeItemAt ( int row) {
		fireTableDataChanged ();
		return (PosTrxEbBatch)dataList.remove(row);
	}
	
	public ApplicationMessage getModelData ( String trxType,
			                                 java.util.Date fromDate, 
			                                 java.util.Date toDate, 
			                                 String batchNumber ) {
		ApplicationMessage aMsg;
		try {
			    dataList = PosTrxEbBatchesDAO.getList ( trxType, fromDate, toDate, batchNumber);	
			if ( dataList == null) {
				// si no hay items de venta, se devuelve una tabla vacia
				PosTrxEbBatch o = new PosTrxEbBatch ();
				o.setIdentifier(0);
				o.setBatchNumber(" ");
				o.setTransmissDate(null);
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
	
	public void getModelData ( ArrayList<PosTrxEbBatch> l ) {
		dataList = l;
	}
	
	public ApplicationMessage getEmptyModel ( ) {
		PosTrxEbBatch o = new PosTrxEbBatch ();
		o.setIdentifier(0);
		o.setBatchNumber(" ");
		o.setTransmissDate(null);
		o.setResultCode(0);
		o.setResultMessage(" ");
		dataList.add(0, o);
		fireTableDataChanged ();
		return null;		
	}
	
	public ArrayList<PosTrxEbBatch> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<PosTrxEbBatch> dataList) {
		this.dataList = dataList;
	}
}
