package business;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.exceptions.SifenException;

import dao.PosOptionsDAO;
import dao.RcvTrxEbBatchesDAO;
import pojo.EdIdBuildingParams;
import pojo.PosOption;
import pojo.RcvTrxEbBatch;
import pojo.QrBuildingParams;
import sifen.SendRcvInvoicesAsync;
import tools.DEUtils;
import tools.GenerateInvoicePdf;

public class ViewRcvTrxBatchesCtrl {
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
	private String event;
	private String batchNumber;
	private String trxType;
	private java.util.Date fromDate;
	private java.util.Date toDate;
	private RcvTrxEbBatch selectedBatch;
	private String selectedBatchNo;
	private ArrayList<RcvTrxEbBatch> batchesList = new ArrayList<RcvTrxEbBatch> ();
	private RcvTrxEbBatchesTM queryTableModel;

	// internal workflow variables
	private String appAction;

	// getters and setters	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
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

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public RcvTrxEbBatch getSelectedBatch() {
		return selectedBatch;
	}

	public void setSelectedBatch(RcvTrxEbBatch selectedBatch) {
		this.selectedBatch = selectedBatch;
	}

	public String getSelectedBatchNo() {
		return selectedBatchNo;
	}

	public void setSelectedBatchNo(String selectedBatchNo) {
		this.selectedBatchNo = selectedBatchNo;
	}

	public String getAppAction() {
		return appAction;
	}

	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}

	public ArrayList<RcvTrxEbBatch> getBatchesList() {
		return batchesList;
	}

	public void setBatchesList(ArrayList<RcvTrxEbBatch> batchesList) {
		this.batchesList = batchesList;
	}

	public RcvTrxEbBatchesTM getQueryTableModel() {
		return queryTableModel;
	}

	public void setQueryTableModel(RcvTrxEbBatchesTM tableModel) {
		this.queryTableModel = tableModel;
	}

	public ApplicationMessage initForm() {
		//posOpts = PosOptionsDAO.getRow(UserAttributes.userUnit.getIDENTIFIER());
		posOpts = new PosOption();
		posOpts.setEbBatchTxQty(50);
		posOpts.setIdentifier(1);
		posOpts.setOrgId(1);
		posOpts.setUnitId(1);
		clearFilters ();
		return null;
	}   

	private void clearFilters () {
		this.event = null;
		this.batchNumber = null;
		this.fromDate = null;
		this.toDate = null;
		this.selectedBatch = null;
		this.selectedBatchNo = null;
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

	public void getBatchData ( long batchId ) {
		selectedBatch = null;
		Iterator itr1 = batchesList.iterator();
		while (itr1.hasNext()) {
			RcvTrxEbBatch i = (RcvTrxEbBatch) itr1.next();
			if (i.getIdentifier() == batchId) {
				selectedBatch = i;
			}
		}
	}
	
	public ApplicationMessage enterBatchNumber ( String s ) {
		ApplicationMessage aMsg;
		if (s != null) {
			this.setBatchNumber(s);
			return null;
		} else {
			return null;
		}
	}	

	public ApplicationMessage enterTrxType (String enteredType) {
		ApplicationMessage aMsg = new ApplicationMessage();
		this.trxType = "FACTURA";
		if (enteredType.equalsIgnoreCase("Nota de Credito")) {
			this.trxType = "NOTA-CREDITO";
		}
		if (enteredType.equalsIgnoreCase("Nota de Debito")) {
			this.trxType = "NOTA-DEBITO";
		}
		return null;
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
			this.setFromDate(null);
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
			this.setToDate(null);
			return null;
		}
	}

	public ApplicationMessage loadQueryDataModel () {
		try {
			System.out.println("enviando parametros: " + this.trxType + " - " + 
		                       this.fromDate + " - " + this.toDate );
			this.batchesList = RcvTrxEbBatchesDAO.getList(this.trxType, this.batchNumber, this.fromDate, this.toDate);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createQueryItemsTable ( ) {
		if (queryTableModel == null) {
			queryTableModel = new RcvTrxEbBatchesTM();
		}
		queryTableModel.getModelData ( batchesList );
		queryTableModel.fireTableDataChanged();
	}	

	public RcvTrxEbBatchesTM createQueryEmptyTable ( ) {
		RcvTrxEbBatchesTM tm = new RcvTrxEbBatchesTM();
		tm.getEmptyModel();
		return tm;
	}	

	public PosTrxEbBatchesTM createQueryTable () {
		PosTrxEbBatchesTM tm = new PosTrxEbBatchesTM();
		tm.getModelData(this.trxType, this.fromDate, this.toDate, this.batchNumber);
		return tm;
	}	

	public ApplicationMessage querySingleBatch ( String batchNumber ) {
		try {
	        SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
	        ApplicationMessage m = t.querySingleBatch( batchNumber );
	        return m;
		} catch ( SifenException e1 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-SINGLE", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-SINGLE", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public ApplicationMessage queryBatchesList ( String trxType, 
			                                     java.util.Date fromDate, 
			                                     java.util.Date toDate ) {
		try {
			SendRcvInvoicesAsync t = new SendRcvInvoicesAsync();
			ApplicationMessage m = t.queryBatchesList( trxType, fromDate, toDate );
			return m;
		} catch ( SifenException e1 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-LIST", e1.getMessage(), ApplicationMessage.ERROR);
			return m;
		} catch ( Exception e2 ) {
			ApplicationMessage m = new ApplicationMessage();
			m.setMessage("QUERY-LIST", e2.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}

	// este es el procedimiento para invocar las rutinas de generacion de la representacion
	// grafica de las facturas
	public ApplicationMessage genGraphicRepresent ( long invoiceId, long controlId, long cashId, String txNumber, java.util.Date txDate ) {
		ApplicationMessage m;
        GenerateInvoicePdf g = new GenerateInvoicePdf();
        m = g.createDocument(invoiceId, controlId, cashId, txNumber);
        return m;
	}

	// este es el procedimiento para invocar las rutinas de envio de la representacion
	// grafica de las facturas
	public ApplicationMessage deliverGraphRepresent ( long invoiceId, long controlId, long cashId, String txNumber, java.util.Date txDate ) {
		ApplicationMessage m;
        GenerateInvoicePdf g = new GenerateInvoicePdf();
        m = g.createDocument(invoiceId, controlId, cashId, txNumber);
        return m;
	}

	public void requestCDC () {
		short FACTURA_ELECTRONICA = 1;
		short PERSONA_FISICA = 1;
		short NORMAL = 1;
		//
		DEUtils x = new DEUtils();
		EdIdBuildingParams cdcParams = new EdIdBuildingParams();
		cdcParams.setdCodSeg("123456789");
		cdcParams.setdRucEm("80052468");
		cdcParams.setdDVEmi("3");
		cdcParams.setdEst("001");
		cdcParams.setdFeEmiDE(LocalDateTime.now());
		cdcParams.setdNumDoc("001-001-0001234");
		cdcParams.setdPunExp("001");
		cdcParams.setiTiDE(FACTURA_ELECTRONICA);
		cdcParams.setiTipCont(PERSONA_FISICA);
		cdcParams.setiTipEmi(NORMAL);
		// invocar el metodo para construir el CDC con los valores enviados
		try {
		    String CDC = x.obtenerCDC(cdcParams);
		} catch ( SifenException e ) {
			e.printStackTrace();
		}	
	}
	
	public void requestQR () {
		short CONTRIBUYENTE = 1;
		short FACTURA_ELECTRONICA = 1;
		short IVA_RENTA = 5;
		short B2C = 2;
		//
		DEUtils x = new DEUtils();
		QrBuildingParams qrParams = new QrBuildingParams();
		qrParams.setCantidadItems(Short.valueOf("8"));
		qrParams.setdFeEmiDE(LocalDateTime.now());
		qrParams.setdNumIDRec("1494393");
		qrParams.setdRucRec("1494393");
		qrParams.setdTotGralOpe(new BigDecimal(184000));
		qrParams.setdTotIVA(new BigDecimal(16727));
		qrParams.setId("01800805534001002000000722021040613265708133");
		qrParams.setiNatRec(CONTRIBUYENTE);
		qrParams.setiTiDE(FACTURA_ELECTRONICA);
		qrParams.setiTImp(IVA_RENTA	);
		qrParams.setiTiOpe(B2C);
		// invocar el metodo para construir el codigo QR con los valores enviados
		try {
	        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
	        Sifen.setSifenConfig(sifenConfig);
	        // Firma Digital del XML
	        //SignedInfo signedInfo = SignatureHelper.signDocument(sifenConfig, rDE, this.getId());
	        //
		    //String QR = x.generateQRLink(signedInfo, sifenConfig, qrParams);
		} catch ( SifenException e ) {
			e.printStackTrace();
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
