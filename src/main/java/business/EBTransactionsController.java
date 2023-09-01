package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import dao.InvOptionsDAO;
import dao.RcvCustomerSitesDAO;
import dao.RcvCustomersDAO;
import dao.RcvCustomersTrxDAO;
import pojo.FndSite;
import pojo.RcvCustomer;
import pojo.RcvCustomerSite;
import pojo.RcvCustomerTrx;
import pojo.InvOptions;
import business.ApplicationMessage;
import business.TestSocket;

public class EBTransactionsController {
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
	private InvOptions invOptions;
	// business logic variables
	private String customerCode;
	private String customerSiteName;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private String transactionNo;
	private RcvCustomerTrx selectedTransaction;
	private FndSite site;
	private RcvCustomer customer;
	private RcvCustomerSite customerSite;
	private long selectedTransactionId;
	private ArrayList<RcvCustomerTrx> transactionsList = new ArrayList<RcvCustomerTrx> ();
	private RcvCustomersTrxTM tableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public InvOptions getInvOptions() {
		return invOptions;
	}

	public void setPoOptions(InvOptions invOptions) {
		this.invOptions = invOptions;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerSiteName() {
		return customerSiteName;
	}

	public void setCustomerSiteName(String customerSiteName) {
		this.customerSiteName = customerSiteName;
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}

	public java.util.Date getToDate() {
		return toDate;
	}

	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setReceivingNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public RcvCustomerTrx getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(RcvCustomerTrx selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public FndSite getSite() {
		return site;
	}

	public void setSite(FndSite site) {
		this.site = site;
	}

	public RcvCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(RcvCustomer customer) {
		this.customer = customer;
	}

	public RcvCustomerSite getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(RcvCustomerSite customerSite) {
		this.customerSite = customerSite;
	}

	public long getSelectedTransactionId() {
		return selectedTransactionId;
	}

	public void setSelectedTransactionId(long selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<RcvCustomerTrx> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(ArrayList<RcvCustomerTrx> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public RcvCustomersTrxTM getTableModel() {
		return tableModel;
	}

	public void setTableModel(RcvCustomersTrxTM tableModel) {
		this.tableModel = tableModel;
	}

	public ApplicationMessage initForm() {
		//ConnectionData.userName = "APPMGR";
		//ConnectionData.password = "APPMGR";
    	//UserAttributes.userName = "APPMGR";
    	try {
    	    UserAttributes.getUserAttributes();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	// obtener informacion de contexto 
    	try {
    	    this.invOptions = InvOptionsDAO.getRow( new java.util.Date(), UserAttributes.userUnit.getIDENTIFIER());
    	    if (this.invOptions == null) {
    	    	ApplicationMessage aMsg = new ApplicationMessage();
    	    	aMsg.setMessage("PARAM-NOT-FOUND", "No se ha podido detectar el juego de parametros de la aplicacion", ApplicationMessage.ERROR);
    	    	return aMsg;
    	    }
    	} catch (Exception e) {
    		e.printStackTrace();
	    	ApplicationMessage aMsg = new ApplicationMessage();
	    	aMsg.setMessage("PARAM-NOT-FOUND", "No se ha podido detectar el juego de parametros de la aplicacion", ApplicationMessage.ERROR);
	    	return aMsg;
    	}
    	//
    	SessionContext.scalePort = "NO-DEFINIDO";
    	//
    	clearFilters ();
    	//
    	return null;
    }   

	private void clearFilters () {
		this.customerCode = null;
		this.customerSiteName = null;
		this.fromDate = null;
		this.toDate = null;
		this.transactionNo = null;
		this.customer = null;
		this.customerSite = null;
		this.selectedTransaction = null;
		this.selectedTransactionId = 0;
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
    
    public void getSheetData ( long transactionId ) {
    	selectedTransaction = null;
        @SuppressWarnings("rawtypes")
		Iterator itr1 = transactionsList.iterator();
        while (itr1.hasNext()) {
        	RcvCustomerTrx i = (RcvCustomerTrx) itr1.next();
    	    if (i.getIdentifier() == transactionId) {
    	    	selectedTransaction = i;
    	    }
        }
    }
    
    public ApplicationMessage enterCustomerId ( long customerId ) {
    	ApplicationMessage aMsg;
    	try {
    		RcvCustomer o = RcvCustomersDAO.getRow(customerId);
    		if (o != null) {
    		    this.customer = o;
    			return null;
    		} else {
    		    aMsg = new ApplicationMessage();
    			aMsg.setMessage("NO-DATA-FOUND", "Cliente no encontrado", ApplicationMessage.ERROR);
    			return aMsg;
    		}
    	} catch ( Exception e ) {
    		e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
    	}
    }
    
    public ApplicationMessage enterCustomerCode ( String customerCode ) {
    	ApplicationMessage aMsg;
    	try {
    		if (customerCode == null) {
    			aMsg = new ApplicationMessage();
    			aMsg.setMessage("REQUIRED-ITEM", "Debe ingresar este dato", ApplicationMessage.ERROR);
    			return aMsg;    			
    		} else {
    		    RcvCustomer o = RcvCustomersDAO.getRowByCode(customerCode, UserAttributes.userUnit.getIDENTIFIER());
    		    if (o != null) {
    			    this.customer = o;
    			    return null;
    		    } else {
    			    aMsg = new ApplicationMessage();
    			    aMsg.setMessage("NO-DATA-FOUND", "Cliente no encontrado", ApplicationMessage.ERROR);
    			    return aMsg;
    		    }
    		}
    	} catch ( Exception e ) {
    		e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
    	}
    }

    public ApplicationMessage enterCustomerSiteId ( long customerSiteId ) {
    	ApplicationMessage aMsg;
    	try {
    		RcvCustomerSite o = RcvCustomerSitesDAO.getRow(customerSiteId);
    		if (o != null) {
    		    this.customerSite = o;
    			return null;
    		} else {
    		    aMsg = new ApplicationMessage();
    			aMsg.setMessage("NO-DATA-FOUND", "Sitio de cliente no encontrado", ApplicationMessage.ERROR);
    			return aMsg;
    		}
    	} catch ( Exception e ) {
    		e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
    	}
    }
    
    public ApplicationMessage enterCustomerSiteCode ( String customerSiteCode ) {
    	ApplicationMessage aMsg;
    	try {
    		if (customerSiteCode != null) {
    		    RcvCustomerSite o = RcvCustomerSitesDAO.getRow(customerSiteCode, this.customer.getIdentifier());
    		    if (o != null) {
    			    this.customerSite = o;
    			    return null;
    		    } else {
    			    aMsg = new ApplicationMessage();
    			    aMsg.setMessage("NO-DATA-FOUND", "Sitio de cliente no encontrado", ApplicationMessage.ERROR);
    			    return aMsg;
    		    }
    		} else {
			    this.customerSite = null;
			    return null;    			
    		}
    	} catch ( Exception e ) {
    		e.printStackTrace();
			aMsg = new ApplicationMessage();
			aMsg.setMessage("DATA-SEARCH", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
    	}
    }

    public ApplicationMessage enterFromDate ( String s ) {
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
    	    this.setFromDate(d);
    	    return null;
    	} else {
    		return null;
    	}
    }
    
    public ApplicationMessage enterToDate ( String s ) {
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
    	    this.setToDate(d);
    	    return null;
    	} else {
    		return null;
    	}
    }

    public ApplicationMessage enterReceivingNo ( String s ) {
    	if (s != null) {
    	    this.setReceivingNo(s);
    	}
	    return null;
    }
    
    public ApplicationMessage loadDataModel () {
    	long siteId = 0;
    	long txTypeId = 0;
    	long customerId = 0;
		try {
			if (customer != null) {
				customerId = customer.getIdentifier();
			}
			this.transactionsList = RcvCustomersTrxDAO.getList ( siteId, 
					                                             txTypeId,
					                                             customerId, 
					                                             fromDate, 
					                                             toDate, 
					                                             transactionNo );
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void createItemsTable ( ) {
    	if (tableModel == null) {
    		tableModel = new RcvCustomersTrxTM();
    	}
		tableModel.getModelData ( transactionsList );
		tableModel.fireTableDataChanged();
	}	
    
    public RcvCustomersTrxTM createTransactionsTable () {
    	long txTypeId = 0;
    	long siteId = 0;
    	RcvCustomersTrxTM tm = new RcvCustomersTrxTM();
		tm.getModelData ( txTypeId, 
				          siteId,
				          this.customer.getIdentifier(), 
				          this.fromDate, 
				          this.toDate, 
				          this.transactionNo );
		return tm;
	}	
    
	public RcvCustomersTrxTM createHeaderEmptyTable ( ) {
		RcvCustomersTrxTM tm = new RcvCustomersTrxTM();
		tm.getEmptyModel();
		return tm;
	}	

	public boolean isConnectionAvailable () {
	    try {
       	    return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
	    } catch (Exception e) {
	 		return false;   			
	    }
	}

}
