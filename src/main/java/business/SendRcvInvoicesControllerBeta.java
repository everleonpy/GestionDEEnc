package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.exceptions.SifenException;

import dao.FndSitesDAO;
import dao.PosOptionsDAO;
import dao.PosTrxEbBatchesDAO;
import dao.RcvCustomersTrxDAO;
import dao.RcvEbInvoicesDAO;
import dao.RcvTrxEbBatchesDAO;
import pojo.FndSite;
import pojo.GenericStringsList;
import pojo.PosOption;
import pojo.PrepareTxParams;
import pojo.RcvEbInvoice;
import pojo.RcvInvoice;
import pojo.SendGroup;
import sifen.SendRcvInvoicesAsync;

public class SendRcvInvoicesControllerBeta {
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
	private long invoiceId;
	private long siteId;
	private String siteName;
	private String txNumber;
	private int invoicesQty;
	private int preparedQty;
	private long batchId;
	private int firstGroup;
	private int lastGroup;
	private SendGroup selectedGroup;
	private long selectedGroupId;
	private long transactionId;
	private long selectedTransactionId;
	private String selectedCtrlCode;

	private ArrayList<RcvInvoice> invoicesList = new ArrayList<RcvInvoice> ();
	private RcvInvoicesTM invoicesTableModel;
	private ArrayList<RcvEbInvoice> preparedList = new ArrayList<RcvEbInvoice> ();
	private RcvEbInvoicesTM preparedTableModel;

	private final int FACTURA = 1;
    private final String TRX_TYPE = "FACTURA";

	public String getXmlFolder() {
		return xmlFolder;
	}

	public void setXmlFolder(String xmlFolder) {
		this.xmlFolder = xmlFolder;
	}

	public ArrayList<RcvInvoice> getTransactionsList() {
		return invoicesList;
	}

	public void setTransactionsList(ArrayList<RcvInvoice> invoicesList) {
		this.invoicesList = invoicesList;
	}

	public RcvInvoicesTM getInvoicesTableModel() {
		return invoicesTableModel;
	}

	public void setInvoicesTableModel(RcvInvoicesTM invoicesTableModel) {
		this.invoicesTableModel = invoicesTableModel;
	}

	public ArrayList<RcvEbInvoice> getPreparedList() {
		return preparedList;
	}

	public void setPreparedList(ArrayList<RcvEbInvoice> preparedList) {
		this.preparedList = preparedList;
	}

	public RcvEbInvoicesTM getPreparedTableModel() {
		return preparedTableModel;
	}

	public void setPreparedTableModel(RcvEbInvoicesTM tableModel) {
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
	
	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
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
	
	public int getInvoicesQty() {
		return invoicesQty;
	}

	public void setInvoicesQty(int invoicesQty) {
		this.invoicesQty = invoicesQty;
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
		// este es el codigo original
		//posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		// este es un mock para integracion con sistemas de terceros
		posOpts = new PosOption();
		posOpts.setEbBatchTxQty(50);
		posOpts.setIdentifier(1);
		posOpts.setOrgId(1);
		posOpts.setUnitId(1);
		//
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
			// obtener los numeros del primer y del ultimo lote correspondiente a la fecha
			GenericStringsList x = this.getBatchesRange();
			if ( x != null ) {
				this.setFirstGroup(Integer.parseInt(x.getElement1()));
				this.setLastGroup(Integer.parseInt(x.getElement2()));
			}
			//
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
					long txId = RcvCustomersTrxDAO.existsTx(TRX_TYPE, this.trxDate, txNumber, this.siteId);
					if (txId > 0) {
						this.txNumber = txNumber;
						this.invoiceId = txId;
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
	
	public void findInvoicesCount () {
		System.out.println("Contado facturas: " + this.trxDate);
		this.invoicesQty = RcvCustomersTrxDAO.invoicesQty(this.trxDate);
	}
	
	public void findPreparedCount () {
		this.preparedQty = RcvEbInvoicesDAO.preparedInvoicesQty(FACTURA, this.trxDate);
	}
	
	public ApplicationMessage prepareTrx () {
		ApplicationMessage aMsg;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String d = sdf.format(this.trxDate);
		PrepareTxParams x = RcvEbInvoicesDAO.prepareInvoices(d, this.invoiceId);
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
		
	public String checkTrxData ( long batchId ) {
		// aqui se debe invocar un procedimiento que retorne todos los identificadores
		// de lotes que contienen las transacciones a ser revisadas
		String msg = RcvEbInvoicesDAO.checkTrxData(batchId);
		return msg;
	}

	public GenericStringsList getBatchesRange () {
		try {
			//GenericStringsList x = RcvTrxEbBatchesDAO.getNotSendList(FACTURA, this.trxDate);
			GenericStringsList x = new GenericStringsList();
			x.setElement1("1");
			x.setElement2("1");
			return x;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ApplicationMessage loadInvoicesDataModel () {
		try {
			System.out.println("Obteniendo facturas: Sucursal: " + this.siteId + " - Fecha: " + this.trxDate);
			this.invoicesList = RcvCustomersTrxDAO.getInvoicesNotSentList(this.siteId, this.trxDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createInvoicesItemsTable ( ) {
		if (invoicesTableModel == null) {
			invoicesTableModel = new RcvInvoicesTM();
		}
		invoicesTableModel.getModelData ( invoicesList );
		invoicesTableModel.fireTableDataChanged();
	}	

	public RcvInvoicesTM createInvoicesEmptyTable ( ) {
		RcvInvoicesTM tm = new RcvInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public ApplicationMessage loadPreparedDataModel () {
		try {
			System.out.println("enviando parametros: " + FACTURA + " - " + this.trxDate + " - " +
		                        this.siteId + " - " + this.txNumber);
			this.preparedList = RcvEbInvoicesDAO.getPreparedList(FACTURA, this.trxDate, this.siteId, this.txNumber);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createPreparedItemsTable ( ) {
		if (preparedTableModel == null) {
			preparedTableModel = new RcvEbInvoicesTM();
		}
		preparedTableModel.getModelData ( preparedList );
		preparedTableModel.fireTableDataChanged();
	}	

	public RcvEbInvoicesTM createPreparedEmptyTable ( ) {
		RcvEbInvoicesTM tm = new RcvEbInvoicesTM();
		tm.getEmptyModel();
		return tm;
	}	
	
	public ApplicationMessage loadSentDataModel () {
		try {
			System.out.println("Obteniendo facturas enviadas: Sucursal: " + this.siteId + " - Fecha: " + this.trxDate);
			this.invoicesList = RcvCustomersTrxDAO.getSentInvoicesList(this.siteId, this.trxDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// este es el procedimiento para conectar con las rutinas de envio de la transaccion
	// a la entidad recaudadora de impuestos
	public ApplicationMessage sendTxBatch ( ) {
		ApplicationMessage m = new ApplicationMessage();
		try {
			// obtener el menor y el mayor numero de lote de envio ("RCV_EB_INVOICES.REF_NUMBER")
			// que aun no tenga registro aprobado en la tabla de items de lotes Receivables
			// en la fecha ingresada
			/*
			PrepareTxParams o = RcvEbInvoicesDAO.findPreparedInfo(FACTURA, this.trxDate);
			if (o != null) {
				this.firstGroup = o.getFirstGroup();
				this.lastGroup = o.getLastGroup();
			} else {
				this.firstGroup = 0;
				this.lastGroup = 0;					
			}
			*/
			//if (this.firstGroup > 0) {
			    System.out.println("Buscando transacciones de fecha: " + this.trxDate );
		        SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
		        m = t.sendDeBatch ( "FACTURA", 
		    		                    this.trxDate, 
		    		                    UserAttributes.userOrg.getIDENTIFIER(), 
		    		                    UserAttributes.userUnit.getIDENTIFIER(), 
		    		                    UserAttributes.userName );
			    return m;
			//} else {
			//	m.setMessage("SEND-TRX", "No se han preparado los datos para el envio", ApplicationMessage.ERROR);
			//	return m;				
			//}
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
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
            SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
		    m = t.queryBatches(TRX_TYPE, this.trxDate);
		    return m;
		} catch ( SifenException e1 ) {
			m.setMessage("SEND-TRX", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			m.setMessage("SEND-TRX", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public DocumentoElectronico queryCDC ( String controlCode, long transactionId, String txNumber ) {
		try {
	        SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
	        DocumentoElectronico d = t.queryCDC(controlCode, transactionId, txNumber);
	        return d;
		} catch ( SifenException e1 ) {
			return null;
		} catch ( Exception e2 ) {
			return null;
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
