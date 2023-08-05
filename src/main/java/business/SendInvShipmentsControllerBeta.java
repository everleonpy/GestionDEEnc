package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.exceptions.SifenException;

import dao.FndSitesDAO;
import dao.InvEbShipmentsDAO;
import dao.InvInternalShipmentsDAO;
import dao.PosOptionsDAO;
import pojo.FndSite;
import pojo.InvEbShipment;
import pojo.InvShipment;
import pojo.PosOption;
import pojo.PrepareTxParams;
import pojo.SendGroup;
import sifen.SendInvShipmentsAsync;

public class SendInvShipmentsControllerBeta {
	/* 
	 * +-------------------------------------------------------------+
	 * |                                                             | 
	 * |                                                             |
	 * | Atributos de la clase de control                            |
	 * |                                                             |
	 * |                                                             | 
	 * +-------------------------------------------------------------+	
	 */
	// global variables
	// business logic variables
	private PosOption posOpts;
	private String xmlFolder;
	private java.util.Date trxDate;
	private long shipmentId;
	private long siteId;
	private String siteName;
	private String txNumber;
	private int shipmentsQty;
	private int preparedQty;
	private int unavailableQty;
	private long batchId;
	private int firstGroup;
	private int lastGroup;
	private SendGroup selectedGroup;
	private long selectedGroupId;
	private long transactionId;
	private long selectedTransactionId;
	private String selectedCtrlCode;

	private ArrayList<InvShipment> shipmentsList = new ArrayList<InvShipment> ();
	private InvShipmentsTM shipmentsTableModel;
	private ArrayList<InvEbShipment> preparedList = new ArrayList<InvEbShipment> ();
	private InvEbShipmentsTM preparedTableModel;

	public String getXmlFolder() {
		return xmlFolder;
	}

	public void setXmlFolder(String xmlFolder) {
		this.xmlFolder = xmlFolder;
	}

	public ArrayList<InvShipment> getShipmentsList() {
		return shipmentsList;
	}

	public void setShipmentsList(ArrayList<InvShipment> shipmentsList) {
		this.shipmentsList = shipmentsList;
	}

	public InvShipmentsTM getShipmentsTableModel() {
		return shipmentsTableModel;
	}

	public void setShipmentsTableModel(InvShipmentsTM shipmentsTableModel) {
		this.shipmentsTableModel = shipmentsTableModel;
	}

	public ArrayList<InvEbShipment> getPreparedList() {
		return preparedList;
	}

	public void setPreparedList(ArrayList<InvEbShipment> preparedList) {
		this.preparedList = preparedList;
	}

	public InvEbShipmentsTM getPreparedTableModel() {
		return preparedTableModel;
	}

	public void setPreparedTableModel(InvEbShipmentsTM tableModel) {
		this.preparedTableModel = tableModel;
	}
	
	// internal workflow variables
	private String appAction;

	// getters and setters	
	public java.util.Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}

	public SendGroup getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(SendGroup selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public long getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}

	public String getSelectedCtrlCode() {
		return selectedCtrlCode;
	}

	public void setSelectedCtrlCode(String selectedCtrlCode) {
		this.selectedCtrlCode = selectedCtrlCode;
	}
	
	public long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(long shipmentId) {
		this.shipmentId = shipmentId;
	}
	
	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getTxNumber() {
		return txNumber;
	}

	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	public int getPreparedQty() {
		return preparedQty;
	}

	public void setPreparedQty(int preparedQty) {
		this.preparedQty = preparedQty;
	}
	
	public int getUnavailableQty() {
		return unavailableQty;
	}

	public void setUnavailableQty(int unavailableQty) {
		this.unavailableQty = unavailableQty;
	}

	public int getShipmentsQty() {
		return shipmentsQty;
	}

	public void setShipmentsQty(int shipmentsQty) {
		this.shipmentsQty = shipmentsQty;
	}
	
	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	
	public int getFirstGroup() {
		return firstGroup;
	}

	public void setFirstGroup(int firstGroup) {
		this.firstGroup = firstGroup;
	}

	public int getLastGroup() {
		return lastGroup;
	}

	public void setLastGroup(int lastGroup) {
		this.lastGroup = lastGroup;
	}
	
	public PosOption getPosOpts() {
		return posOpts;
	}

	public void setPosOpts(PosOption posOpts) {
		this.posOpts = posOpts;
	}


	public ApplicationMessage initForm() {
		posOpts = null; //PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		this.xmlFolder = "/Users/jota_ce/Documents/cacique-sifen/xml/";
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.siteId = 0;
		this.siteName = null;
		this.trxDate = null;
		this.txNumber = null;
		this.selectedGroup = null;
		this.selectedGroupId = 0;
	}

	public String formatDate ( String dateValue ) {
		String dd;
		String mm;
		String yy;
		if (dateValue.length() < 6) {
			return null;
		}
		if (dateValue.length() > 10) {
			return null;
		}
		if (dateValue.length() == 6) {
			dd = dateValue.substring(0, 2);
			mm = dateValue.substring(2, 4);
			yy = dateValue.substring(4, 6);
			if (Integer.valueOf(yy) > 50) {
				return dd + "/" + mm + "/" + "19" + yy;
			} else {
				return dd + "/" + mm + "/" + "20" + yy;    			
			}
		}
		if (dateValue.length() == 8) {
			dd = dateValue.substring(0, 2);
			mm = dateValue.substring(2, 4);
			yy = dateValue.substring(4, 8);
			return dd + "/" + mm + "/" + yy;
		}
		if (dateValue.length() == 10) {
			return dateValue;
		}    	
		return null;
	}

	public java.util.Date getDateValue (String dateText) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date dateValue = sdf.parse(dateText);
			return dateValue;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getGroupData ( long groupNumber ) {
		selectedGroup = null;
		Iterator itr1 = preparedList.iterator();
		while (itr1.hasNext()) {
			SendGroup i = (SendGroup) itr1.next();
			if (i.getGroupNumber() == groupNumber) {
				selectedGroup = i;
			}
		}
	}

	public ApplicationMessage enterDate ( String s ) {
		java.util.Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ApplicationMessage aMsg;
		if (s != null) {
			// aqui se somete la entrada a un formateo previo que permite traducir las entradas
			// con formatos abreviados de fecha
			String v = this.formatDate(s);
			try {
				d = sdf.parse(v);
			} catch (ParseException e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("DATE-FORMAT", "Formato de fecha no valido", ApplicationMessage.ERROR);
				return aMsg;    			
			}
			this.setTrxDate(d);
			return null;
		} else {
			return null;
		}
	}

	public FndSite findSite ( long siteId, String siteName, long unitId ) {
		FndSite s = null;
		if ( siteId > 0) {
			s = FndSitesDAO.getRow(siteId);
		}			
		if (siteName != null) {
		    s = FndSitesDAO.getRowByName(siteName, unitId);
		}
		return s;
	}	
	
	public ApplicationMessage enterSiteName ( String siteName, long unitId ) {
		ApplicationMessage aMsg;
		if (siteName != null) {
			try {
				FndSite s = FndSitesDAO.getRowByName(siteName, unitId);
				if (s != null) {
					this.siteName = s.getNAME();
					return null;
				} else {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("VALID-SITE", "Sucursal no definida", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (Exception e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("VALID-SITE", "Error: " + e.getMessage(), ApplicationMessage.ERROR);
				return aMsg;    			
			}
		} else {
			this.siteName = null;
			return null;
		}
	}

	public ApplicationMessage enterSiteId ( long siteId ) {
		ApplicationMessage aMsg;
		if (siteId != 0) {
			try {
				FndSite s = FndSitesDAO.getRow(siteId);
				if (s != null) {
					this.siteName = s.getNAME();
					return null;
				} else {
					aMsg = new ApplicationMessage();
					aMsg.setMessage("VALID-SITE", "Sucursal no definida", ApplicationMessage.ERROR);
					return aMsg;    								
				}
			} catch (Exception e) {
				aMsg = new ApplicationMessage();
				aMsg.setMessage("VALID-SITE", "Error: " + e.getMessage(), ApplicationMessage.ERROR);
				return aMsg;    			
			}
		} else {
			this.siteName = null;
			return null;
		}
	}

	public ApplicationMessage enterTxNumber ( String txNumber ) {
		ApplicationMessage aMsg;
		try {
			if (txNumber != null) {
				if (txNumber.isEmpty() == false) {
					long txId = InvInternalShipmentsDAO.existsTx(this.trxDate, txNumber, this.siteId);
					if (txId > 0) {
						this.txNumber = txNumber;
						this.shipmentId = txId;
						return null;
					} else {
						this.txNumber = null;
						aMsg = new ApplicationMessage();
						aMsg.setMessage("TX-NOTFOUND", "No existe ninguna transaccion con ese numero en la fecha indicada", ApplicationMessage.ERROR);
						return aMsg;
					}					
				} else {
					this.txNumber = null;
				}
			} else {
				this.txNumber = null;
			}
			return null;
		} catch ( Exception e ) {
			e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("TX-NOTFOUND", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}
	
	public void findShipmentsCount () {
		this.shipmentsQty = InvInternalShipmentsDAO.shipmentsQty(this.trxDate);
	}
	
	public void findUnavailableCount () {
		this.unavailableQty = InvInternalShipmentsDAO.unavailablesQty(this.trxDate);
	}

	public void findPreparedCount () {
		this.preparedQty = InvEbShipmentsDAO.preparedShipmentsQty(this.trxDate);
	}
	
	public ApplicationMessage prepareTrx () {
		ApplicationMessage aMsg;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String d = sdf.format(this.trxDate);
		PrepareTxParams x = InvEbShipmentsDAO.prepareShipments(d, this.shipmentId);
		if (x != null) {
			this.setBatchId(x.getBatchId());
			this.setFirstGroup(x.getFirstGroup());
			this.setLastGroup(x.getLastGroup());
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", x.getRowsQty() + " transacciones preparadas para envio", ApplicationMessage.MESSAGE);
			return aMsg;
		} else {
			this.setBatchId(0);
			this.setFirstGroup(0);
			this.setLastGroup(0);
			aMsg = new ApplicationMessage();
			aMsg.setMessage("PREPARE-TX", "No se ha podido preparar ninguna transaccion para envio", ApplicationMessage.ERROR);
			return aMsg;			
		}
	}
		
	public void defaultTrxValues () {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String d = sdf.format(this.trxDate);
		InvEbShipmentsDAO.defaultValues(d);
	}

	public ApplicationMessage loadShipmentsDataModel () {
		try {
			System.out.println("Obteniendo remisiones: Sucursal: " + this.siteId + " - Fecha: " + this.trxDate);
			this.shipmentsList = InvInternalShipmentsDAO.getShipmentsNotSentList(this.siteId, this.trxDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createShipmentsItemsTable ( ) {
		if (shipmentsTableModel == null) {
			shipmentsTableModel = new InvShipmentsTM();
		}
		shipmentsTableModel.getModelData ( shipmentsList );
		shipmentsTableModel.fireTableDataChanged();
	}	

	public InvShipmentsTM createShipmentsEmptyTable ( ) {
		InvShipmentsTM tm = new InvShipmentsTM();
		tm.getEmptyModel();
		return tm;
	}	

	public ApplicationMessage loadPreparedDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxDate + " - " +
		                        this.siteId + " - " + this.txNumber);
			this.preparedList = InvEbShipmentsDAO.getList(this.trxDate, this.siteId, this.txNumber);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createPreparedItemsTable ( ) {
		if (preparedTableModel == null) {
			preparedTableModel = new InvEbShipmentsTM();
		}
		preparedTableModel.getModelData ( preparedList );
		preparedTableModel.fireTableDataChanged();
	}	

	public InvEbShipmentsTM createPreparedEmptyTable ( ) {
		InvEbShipmentsTM tm = new InvEbShipmentsTM();
		tm.getEmptyModel();
		return tm;
	}	
	
	
	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTxBatch ( ) {
		ApplicationMessage m = new ApplicationMessage();
		try {
			/*
			if (this.firstGroup == 0) {
				PrepareTxParams o = InvEbShipmentsDAO.findPreparedInfo(1, this.trxDate);
				if (o != null) {
					this.firstGroup = o.getFirstGroup();
					this.lastGroup = o.getLastGroup();
				} else {
					this.firstGroup = 0;
					this.lastGroup = 0;					
				}
			}
			*/
			//if (this.firstGroup > 0) {
			    System.out.println("parametros envio: " + this.trxDate + " - " + this.firstGroup + " - " + this.lastGroup);
		        SendInvShipmentsAsync t = new SendInvShipmentsAsync();
		        m = t.sendDeBatch ( "ENVIO-LOCALES", 
		    		                    this.trxDate, 
		    		                    UserAttributes.userOrg.getIDENTIFIER(), 
		    		                    UserAttributes.userUnit.getIDENTIFIER(), 
		    		                    UserAttributes.userName );
			    return m;
			//} else {
			//	m.setMessage("SEND-TRX", "No se han preparado los datos para el envio", ApplicationMessage.ERROR);
			//	return m;				
			//}
			/*
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
			*/
		} catch ( Exception e2 ) {
			m.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	/**
	 * Este metodo tiene como objetivo ejecutar la consulta de un lote de transacciones Receivables
	 * para registrar los resultados correspondientes a cada elemento del lote
	 * @return
	 */
	public ApplicationMessage queryBatches () {
		ApplicationMessage m = new ApplicationMessage();
		try {
            SendInvShipmentsAsync t = new SendInvShipmentsAsync();
		    m = t.queryBatches(this.trxDate);
		    return m;
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public ApplicationMessage queryCDC ( String controlCode ) {
		try {
			/*
	        SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
	        ApplicationMessage m = t.queryCDC(controlCode);
	        return m;
	        */
			return null;
		/*
		 } catch ( SifenException e1 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-CDC", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		*/
		} catch ( Exception e2 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-CDC", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public boolean isConnectionAvailable () {
		try {
			return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
		} catch (Exception e) {
			return false;   			
		}
	}

}
