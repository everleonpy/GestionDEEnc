package tools;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.zxing.WriterException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import business.AppConfig;
import business.ApplicationMessage;
import dao.PosCollectionItemsDAO;
import dao.PosCollectionsDAO;
import dao.PosTransItemsDAO;
import dao.PosTransactionsDAO;
import dao.PosUtilDAO;
import dao.Util;
import pojo.Employee;
import pojo.GenericElement;
import pojo.PosCollection;
import pojo.PosInvoiceData;
import pojo.PosInvoiceItemData;
import pojo.TicketCaption;
import pojo.TicketDetail;
import pojo.TicketFooter;
import pojo.TicketHeader;
import util.UtilPOS;


public class GenerateInvoicePdf 
{

	long cashId;
	long cashControlId;
	long transactionId;
	
	Document document = new Document();
	Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

	Connection conn = null;
	PosInvoiceData invHeader;
	PosCollection collData;
	TicketHeader ticketHeader = new TicketHeader();
	ArrayList<TicketDetail> itemsList;
	TicketFooter ticketFooter = new TicketFooter();

	private final int CENTRO = 0;
	private final int DERECHA = 1;
	private final int IZQUIERDA = 2;
	private final int LINE_LENGTH = 40;

	public long getCashId() {
		return cashId;
	}

	public void setCashId(long cashId) {
		this.cashId = cashId;
	}

	public long getCashControlId() {
		return cashControlId;
	}

	public void setCashControlId(long cashControlId) {
		this.cashControlId = cashControlId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	private ApplicationMessage prepareDocument ( String fileName ) {
		try {
		    document = new Document();
		    PdfWriter.getInstance(document, new FileOutputStream(AppConfig.graphRepsFolder + fileName)).setInitialLeading(16);
		    document.open();
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationMessage m = new ApplicationMessage("CREATE-FILE", "Error al crear doc.: " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	private ApplicationMessage getTransactionData ( ) {
		ApplicationMessage m;
		invHeader = PosTransactionsDAO.getFormatList(transactionId, cashControlId, cashId, conn);
		if (invHeader == null) {
			m = new ApplicationMessage("GET-TX-DATA", "Transaccion no encontrada: " + transactionId + " - " + cashControlId + " - " + cashId, ApplicationMessage.ERROR);
			return m;
		}
		collData = PosCollectionsDAO.getRow(transactionId, cashControlId, cashId, conn);
		if (collData == null) {
			m = new ApplicationMessage("GET-TX-DATA", "No se pudo encontrar la cobranza: " + transactionId + " - " + cashControlId + " - " + cashId, ApplicationMessage.ERROR);
			return m;
		}
		//
		return null;
	}
	
	private void formatHeader () {

		ticketHeader.setInvoiceNumber(invHeader.getTrnNumber());
		ticketHeader.setHeadquartersAddress(invHeader.getOrgAddress());
		ticketHeader.setEconomicActivity(null);
		if (invHeader.getActivList() != null) {
			if (invHeader.getActivList().length > 0) {
				for( int i = 0; i <= invHeader.getActivList().length - 1; i++)
				{
				    // get element number 0 and 1 and put it in a variable, 
				    // and the next time get element      1 and 2 and put this in another variable. 
					//ticketHeader.setSecEconActivities(l);
				}				
			}
		}
		ticketHeader.setPrintingDate(invHeader.getTxDate());
		ticketHeader.setStampStartDate(invHeader.getStampFromDate());
		ticketHeader.setStampEndDate(invHeader.getStampToDate());
		ticketHeader.setStampExpiryDate(invHeader.getStampExpDate());
		ticketHeader.setStampAuthorDate(invHeader.getStampAuthDate());
		ticketHeader.setTaxLabel("I.V.A. INCLUIDO");
		ticketHeader.setCashierName(invHeader.getCashierName());
		ticketHeader.setBusinessName(invHeader.getOrgName());
		ticketHeader.setTradingName(invHeader.getTradingName());
		//
		ticketHeader.setSiteName(invHeader.getSiteName());
		ticketHeader.setSiteAddress(invHeader.getSiteAddress());
		ticketHeader.setSitePhone(invHeader.getSitePhone());
		//
		ticketHeader.setCashRegisterNo(invHeader.getCashNumber());
		ticketHeader.setTaxNumber(invHeader.getOrgTaxNumber());
		ticketHeader.setStampNumber(invHeader.getStampNo());
		ticketHeader.setIssuePoint(invHeader.getIssuePointCode());
		ticketHeader.setIssueSite(invHeader.getIssuePointName());
		ticketHeader.setHeadquartersPhone(invHeader.getOrgPhone());
		ticketHeader.setInvoiceType(invHeader.getTxTypeName());
		ticketHeader.setPaymentDays(Short.valueOf("0"));
		if (invHeader.getInvCondition().equalsIgnoreCase("CREDITO")) {
			ticketHeader.setPaymentDays(invHeader.getPaymentDays());
		}
	}
    	
	public ApplicationMessage printHeader (  ) {
		ApplicationMessage m;
		String s, tmp;
		String fv1;
		String task = "Inicio";

		TicketCaption msgTicket;
		ArrayList <TicketCaption> msg = new ArrayList <TicketCaption>();
		// auxiliares para formateo de lineas a "LINE_LENGTH" columnas
		String baseString;
		String segment = null;
		int lines = 0;
		int strIndex = 0;
		int fromPos = 0;
		int toPos = 0;
		int charsCounter = 0;

		try {

			/**
			 +------------------------------------------------------+
			 | seccion de mensajes pre-definidos (Antes del Titulo) |
			 +------------------------------------------------------+
			 */
			msg = PosUtilDAO.getCaptionsList("ANTES-TITULO", cashId);
			if (msg != null) {
				task = "Antes Titulo";
				for( int i = 0; i < msg.size(); i ++ ) {
					msgTicket = new TicketCaption();
					msgTicket = msg.get(i);
					if (msgTicket.getEMPTY_FLAG().equalsIgnoreCase("S")) {
						s = " ";
						addToDocum (s);
					} else {
						s = msgTicket.getTEXT();
						addToDocum (s);
					}
				}				
			}			
			/**
			 +------------------------------------------------------+
			 | seccion cuerpo del titulo                            |
			 +------------------------------------------------------+
			 */
			// nombre de la empresa
			task = "Nombre Empresa";
			s = formatLine (ticketHeader.getBusinessName(), LINE_LENGTH, CENTRO);
			addToDocum (s);
			/**
			 +------------------------------------------------------+
			 | seccion de mensajes pre-definidos ( Despues del      |
			 | nombre de la empresa                                 |
			 +------------------------------------------------------+
			 */
			msg = PosUtilDAO.getCaptionsList("DESPUES-EMPRESA", cashId);
			if (msg != null) {
				task = "Despues Empresa";
				for( int i = 0; i < msg.size(); i ++ ) {
					msgTicket = new TicketCaption();
					msgTicket = msg.get(i);
					if (msgTicket.getEMPTY_FLAG().equalsIgnoreCase("S")) {
						s = " ";
						addToDocum (s);
					} else {
						s = msgTicket.getTEXT();
						addToDocum (s);
					}
				}				
			}
			// numero de RUC
			task = "RUC";
			s = formatLine ("RUC " + ticketHeader.getTaxNumber(), LINE_LENGTH, CENTRO);
			addToDocum (s);
			// etiqueta de impuestos
			if (ticketHeader.getTaxLabel() != null) {
				task = "Etiqueta Imp. Incluido";
				s = formatLine (ticketHeader.getTaxLabel(), LINE_LENGTH, CENTRO);
				addToDocum (s);
			}
			// actividad economica principal
			if (ticketHeader.getEconomicActivity() != null) {
				if ( ticketHeader.getEconomicActivity().length() > 0 ) {
					task = "Activ. Principales";
					// ciclo para imprimir todo el contenido de la actividad economica
					// dentro de los limites de "lineLength" columnas por linea
					baseString = ticketHeader.getEconomicActivity();
					segment = null;
					lines = baseString.length() / LINE_LENGTH;
					strIndex = 0;
					fromPos = 0;
					toPos = LINE_LENGTH - 2;
					charsCounter = 0;
					while (strIndex < lines) {
						segment = baseString.substring(fromPos, toPos);
						s = formatLine (segment, LINE_LENGTH, CENTRO);
						addToDocum (s);
						charsCounter = charsCounter + segment.length();
						fromPos = toPos - 1;
						toPos = toPos + (LINE_LENGTH - 2);
						strIndex++;
					}
					if (charsCounter < baseString.length()) {
						fromPos = charsCounter;
						toPos = baseString.length();
						segment = baseString.substring(fromPos, toPos);
						s = formatLine (segment, LINE_LENGTH, CENTRO);
						addToDocum (s);
					}
				}
			}
			// actividades economicas secundarias
			if (ticketHeader.getSecEconActivities() != null) {
				if (ticketHeader.getSecEconActivities().size() > 0) {
					task = "Activ. Secundarias";
					@SuppressWarnings("rawtypes")
					Iterator itr1 = ticketHeader.getSecEconActivities().iterator();
					while (itr1.hasNext()) {
						baseString = (String) itr1.next();
						segment = null;
						lines = baseString.length() / LINE_LENGTH;
						strIndex = 0;
						fromPos = 0;
						toPos = LINE_LENGTH - 2;
						charsCounter = 0;
						while (strIndex < lines) {
							segment = baseString.substring(fromPos, toPos);
							s = formatLine (segment, LINE_LENGTH, CENTRO);
							addToDocum (s);
							charsCounter = charsCounter + segment.length();
							fromPos = toPos - 1;
							toPos = toPos + (LINE_LENGTH - 2);
							strIndex++;
						}
						if (charsCounter < baseString.length()) {
							fromPos = charsCounter;
							toPos = baseString.length();
							segment = baseString.substring(fromPos, toPos);
							s = formatLine (segment, LINE_LENGTH, CENTRO);
							addToDocum (s);
						}
					}
				}
			}
			s = " ";
			addToDocum (s);
			// direccion casa matriz
			if ( ticketHeader.getHeadquartersAddress().length() > 0 ) {
				task = "Casa Matriz";
				baseString = "MATRIZ: " + ticketHeader.getHeadquartersAddress();
				segment = null;
				lines = baseString.length() / LINE_LENGTH;
				strIndex = 0;
				fromPos = 0;
				toPos = LINE_LENGTH - 2;
				charsCounter = 0;
				while (strIndex < lines) {
					segment = baseString.substring(fromPos, toPos);
					s = formatLine (segment, LINE_LENGTH, CENTRO);
					addToDocum (s);
					charsCounter = charsCounter + segment.length();
					fromPos = toPos - 1;
					toPos = toPos + (LINE_LENGTH - 2);
					strIndex++;
				}
				if (charsCounter < baseString.length()) {
					fromPos = charsCounter;
					toPos = baseString.length();
					segment = baseString.substring(fromPos, toPos);
					s = formatLine (segment, LINE_LENGTH, CENTRO);
					addToDocum (s);
				}
			}
			// telefono casa matriz
			if ( ticketHeader.getHeadquartersPhone().length() > 0 ) {
				task = "Telef. Matriz";
				s = formatLine ("Tels.: " + ticketHeader.getHeadquartersPhone(), LINE_LENGTH, CENTRO);
				addToDocum (s);
			}
			// datos de la sucursal
			if (ticketHeader.getSiteName() != null) {
				if ( ticketHeader.getSiteName().length() > 0 ) {
					task = "Nombre Sucursal";
					baseString = "Suc. " + ticketHeader.getSiteName();
					segment = null;
					lines = baseString.length() / LINE_LENGTH;
					strIndex = 0;
					fromPos = 0;
					toPos = LINE_LENGTH - 2;
					charsCounter = 0;
					while (strIndex < lines) {
						segment = baseString.substring(fromPos, toPos);
						s = formatLine (segment, LINE_LENGTH, CENTRO);
						addToDocum (s);
						charsCounter = charsCounter + segment.length();
						fromPos = toPos - 1;
						toPos = toPos + (LINE_LENGTH - 2);
						strIndex++;
					}
					if (charsCounter < baseString.length()) {
						fromPos = charsCounter;
						toPos = baseString.length();
						segment = baseString.substring(fromPos, toPos);
						s = formatLine (segment, LINE_LENGTH, CENTRO);
						addToDocum (s);
					}
				}		    
				if ( ticketHeader.getSiteAddress() != null) {
					if ( ticketHeader.getSiteAddress().length() > 0 ) {
						task = "Direccion Sucursal";
						baseString = ticketHeader.getSiteAddress();
						segment = null;
						lines = baseString.length() / LINE_LENGTH;
						strIndex = 0;
						fromPos = 0;
						toPos = LINE_LENGTH - 2;
						charsCounter = 0;
						while (strIndex < lines) {
							segment = baseString.substring(fromPos, toPos);
							s = formatLine (segment, LINE_LENGTH, CENTRO);
							addToDocum (s);
							charsCounter = charsCounter + segment.length();
							fromPos = toPos - 1;
							toPos = toPos + (LINE_LENGTH - 2);
							strIndex++;
						}
						if (charsCounter < baseString.length()) {
							fromPos = charsCounter;
							toPos = baseString.length();
							segment = baseString.substring(fromPos, toPos);
							s = formatLine (segment, LINE_LENGTH, CENTRO);
							addToDocum (s);
						}
					} 
				}
				if ( ticketHeader.getSitePhone() != null) {
					if ( ticketHeader.getSitePhone().length() > 0 ) {
						task = "Telef. Sucursal";
						if ( ticketHeader.getSitePhone().length() >= 30 ) {
							tmp = "Tels.: " + ticketHeader.getSitePhone().substring(0, 29);
						} else {
							tmp = "Tels.: " + ticketHeader.getSitePhone();
						}				
						s = formatLine (tmp, LINE_LENGTH, CENTRO);
						addToDocum (s);
					} 
				}
			}
			// datos del timbrado vigente
			s = " ";
			addToDocum (s);
			SimpleDateFormat frmFecha = new SimpleDateFormat("dd/MM/yyyy");			    
			if ( ticketHeader.getStampNumber().length() <= 9 ) {
				task = "No. Timbrado";
				fv1 = frmFecha.format(ticketHeader.getStampAuthorDate());
				if (ticketHeader.getStampNumber().length() > 8) {
					tmp = "TIMBRADO NO. " + ticketHeader.getStampNumber().substring(0, 8) + " AUT.: " + fv1;
				} else {
					tmp = "TIMBRADO NO. " + ticketHeader.getStampNumber() + " AUT.: " + fv1;
				}
				s = formatLine (tmp, LINE_LENGTH, DERECHA);
				addToDocum (s);
				//
				task = "Inicio Timbrado";
				fv1 = frmFecha.format(ticketHeader.getStampStartDate());
				tmp = "INICIO: " + fv1;
				task = "Fin Timbrado";
				fv1 = frmFecha.format(ticketHeader.getStampExpiryDate());
				tmp = tmp + "     VCTO.: " + fv1;
				s = formatLine (tmp, LINE_LENGTH, DERECHA);
				addToDocum (s);
			} else {
				task = "No. Timbrado";
				tmp = "TIMBRADO NO. " + ticketHeader.getStampNumber();
				s = formatLine (tmp, LINE_LENGTH, DERECHA);
				addToDocum (s);
				//
				task = "Autotiz. Timbrado";
				fv1 = frmFecha.format(ticketHeader.getStampAuthorDate());
				tmp = "FECHA AUTORIZ.: " + fv1;
				s = formatLine (tmp, LINE_LENGTH, DERECHA);
				addToDocum (s);
				//
				task = "Inicio Timbrado";
				fv1 = frmFecha.format(ticketHeader.getStampStartDate());
				tmp = "INICIO: " + fv1;
				fv1 = frmFecha.format(ticketHeader.getStampExpiryDate());
				tmp = tmp + "     VCTO.: " + fv1;
				s = formatLine (tmp, LINE_LENGTH, DERECHA);
				addToDocum (s);
			}
			/**
			 +------------------------------------------------------+
			 | seccion de mensajes pre-definidos ( Despues del      |
			 | titulo )                                             |
			 +------------------------------------------------------+
			 */
			msg = PosUtilDAO.getCaptionsList("DESPUES-TITULO", cashId);
			if (msg != null) {
				task = "Despues Titulo";
				for( int i = 0; i < msg.size(); i ++ ) {
					msgTicket = new TicketCaption();
					msgTicket = msg.get(i);
					if (msgTicket.getEMPTY_FLAG().equalsIgnoreCase("S")) {
						s = " ";
						addToDocum (s);
					} else {
						s = msgTicket.getTEXT();
						addToDocum (s);
					}
				}				
			}		    
			//
			task = "Fecha Emision";
			frmFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
		    String fv2 = frmFecha.format(ticketHeader.getPrintingDate());
			frmFecha = new SimpleDateFormat("EEEE", new Locale("es"));
		    tmp = frmFecha.format(ticketHeader.getPrintingDate());
		    		    
		    tmp = tmp.toUpperCase();
		    if (tmp.length() > 9) {
		        tmp = tmp.substring(0, 9);
		    }
		    task = "Caja";
			s = formatLine ("Caja..: " + ticketHeader.getCashRegisterNo() + "    " + fv2, LINE_LENGTH, DERECHA);
			addToDocum (s);
			task = "Cajero";
		    if ( ticketHeader.getCashierName().length() >= 20 ) {
		        s = formatLine ("Cajero: " + ticketHeader.getCashierName().substring(0, 20) + " - " + tmp, LINE_LENGTH, DERECHA);
		    } else {
		    	    s = formatLine ("Cajero: " + ticketHeader.getCashierName() + " - " + tmp, LINE_LENGTH, DERECHA);
		    }
			addToDocum (s);
			task = "Tipo/No. Transaccion";
			s = formatLine ( ticketHeader.getInvoiceType() + ": " + ticketHeader.getInvoiceNumber(), LINE_LENGTH, DERECHA);
			addToDocum (s);
			// plazo de pago
			if (ticketHeader.getPaymentDays() > 0) {
				task = "Plazo Pago";
				s = "Plazo de Pago: " + ticketHeader.getPaymentDays() + " dias";
				addToDocum (s);
			}
		    //
		    if (ticketHeader.getSourceTransNumber() != null) {
		    	task = "Transaccion Origen";
				s = formatLine ("Comprobante de Venta: " + ticketHeader.getSourceTransNumber(), LINE_LENGTH, DERECHA);
				addToDocum (s);
		    }
		    task = "Encabezado Columnas";
			s = "----------------------------------------";
			addToDocum (s);
			s = "Articulo Cantidad  Precio          Total";
			addToDocum (s);
			s = "----------------------------------------";
			addToDocum (s);
			//
			return null;
		} catch(Exception e){
			e.printStackTrace();
			m = new ApplicationMessage();
			m.setMessage("FORMAT-HEADER", task + ": " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public ApplicationMessage formatItems () {
		// formatear las lineas del ticket
		int counter = 0;
		ApplicationMessage m = new ApplicationMessage();
		itemsList = new ArrayList<TicketDetail>();
		ArrayList<PosInvoiceItemData> l = PosTransItemsDAO.listaPlana (transactionId, cashControlId, cashId, conn);
		if (l != null) {
			if (l.size() > 0) {
				@SuppressWarnings("rawtypes")
				Iterator itr = l.iterator();
				while (itr.hasNext()) {
					PosInvoiceItemData x = (PosInvoiceItemData) itr.next();
					TicketDetail d = new TicketDetail();
					d.setItemQuantity(x.getItemQuantity());
					d.setBarCode(x.getBarCode());
					d.setProductCode(x.getInternalCode());
					if (x.getShortName() != null) {
						if (x.getShortName().length() > 0) {
							d.setProdDescription(x.getShortName());
						} else {
							d.setProdDescription(x.getItemDescription());
						}
					} else {
						d.setProdDescription(x.getItemDescription());
					}
					d.setItemAmount(Math.round(x.getItemAmount()));
					d.setTaxPercent(x.getTaxRate());
					d.setUnitPrice(x.getUnitPrice());
					//
					itemsList.add(d);
					//
					if (x.getItemQuantity() > 0.0) {
						counter = counter + 1;
					} else {
						counter = counter - 1;
					}
				}
				ticketFooter.setPRODUCTS_QUANTITY(counter);
			}
		}
		return m;
	}
	
	public ApplicationMessage printItems ( ) {
		ApplicationMessage m;
		String s, codProducto;
		NumberFormat amountFmtr = NumberFormat.getNumberInstance(Locale.getDefault());
		NumberFormat quantityFmtr = NumberFormat.getNumberInstance(Locale.getDefault());
		@SuppressWarnings("unused")
		DecimalFormat fmtr;
		String task = "Inicio";

		try {
			quantityFmtr.setMinimumFractionDigits(4);
			//
			@SuppressWarnings("rawtypes")
			Iterator itr = itemsList.iterator();
			while (itr.hasNext()) {
				TicketDetail d = (TicketDetail) itr.next();
				//
				task = "Codigo Barras";
				if (d.getBarCode() != null) {
					codProducto = d.getBarCode();
				} else {
					codProducto = UtilPOS.paddingString(d.getProductCode(), 13, '0', true);
				}
				String desProducto = "";
				task = "Cantidad";
				String cantidad = quantityFmtr.format(d.getItemQuantity());
				task = "Tasa Impuesto";
				String tasaImpuesto = amountFmtr.format(d.getTaxPercent());
				task = "Precio Unitario";
				String precioUnitario = amountFmtr.format(d.getUnitPrice());
				task = "Importe";
				String importe = amountFmtr.format(d.getItemAmount());
				task = "Descripcion";
				int longCadena = d.getProdDescription().length();
				if (longCadena > 22)
					desProducto = d.getProdDescription().substring(0, 21);
				else
					desProducto = d.getProdDescription();
				task = "Cantidad";
				cantidad = UtilPOS.paddingString(cantidad, 10, ' ', true);
				task = "Precio Unitario(2)";
				precioUnitario = UtilPOS.paddingString(precioUnitario, 14, ' ', true);
				task = "Importe(2)";
				importe = UtilPOS.paddingString(importe, 14, ' ', true);
				task = "Item";
				s = codProducto + " " + desProducto + " " + tasaImpuesto + "%";
				addToDocum (s);
				s = cantidad + " " + precioUnitario + " " + importe;
				s = s.trim();
				addToDocum (s);
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
			m = new ApplicationMessage();
			m.setMessage("PRINT-LINE", task + ": " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	public ApplicationMessage formatFooter () {
		// preparar los datos para impresion de pie de ticket
		@SuppressWarnings("unused")
		long pointsQty  = 0;
		Employee emp = null;
		ApplicationMessage m = new ApplicationMessage();
		ticketFooter = new TicketFooter();
		String lugarCodigo = "Inicio";
		boolean summarizedFlag = true;
		boolean includeChange = true;
		String printPromDetails = "S";
		//
		try {
			// construir el objeto que representa el pie del comprobante
			lugarCodigo = "Asignar Valores Pie";
			ticketFooter.setAMOUNT_TOTAL(invHeader.getInvoiceAmount());
			ticketFooter.setRESIDUAL_AMOUNT(invHeader.getResidualAmount());
			ticketFooter.setEXCEMPT_TOTAL(invHeader.getExemptAmount());
			// se suman los impuestos a los montos gravados porque en la
			// impresion del ticket los montos gravados deben llevar el
			// IVA incluido
			ticketFooter.setTAXABLE_TOTAL(invHeader.getTaxableAmount() + invHeader.getTaxAmount());
			ticketFooter.setTAXABLE10_TOTAL(invHeader.getTaxable10Amount() + invHeader.getTax10Amount());
			ticketFooter.setTAXABLE5_TOTAL(invHeader.getTaxable5Amount() + invHeader.getTax5Amount());
			ticketFooter.setTAX_TOTAL(invHeader.getTaxAmount());
			ticketFooter.setTAX10_TOTAL(invHeader.getTax10Amount());
			ticketFooter.setTAX5_TOTAL(invHeader.getTax5Amount());
			ticketFooter.setPRODUCTS_QUANTITY(0);
			//
			ticketFooter.setTRX_CONDITION(invHeader.getInvCondition());
			// el tercer parametro va como true para que el metodo
			// devuelva un resumen por forma de pago
			ticketFooter.setCollectionDetail ( PosCollectionItemsDAO.getItemsList ( transactionId, 
					                                                                cashControlId, 
					                                                                cashId, 
					                                                                summarizedFlag, 
					                                                                includeChange, 
					                                                                conn) );
			
			// obtener los datos del empleado si la forma de pago utilizada fue VALE-EMPLEADO
			try {
				emp = PosCollectionItemsDAO.getEmployee ( cashId, cashControlId, transactionId, conn );
			} catch (Exception e) {
				System.out.println("**** Error al buscar datos de empleado para impresion ****");
				e.printStackTrace();	    	  
			}
			if (emp != null) {
				ticketFooter.setEMPLOYEE_NAME(emp.getLAST_NAME() + ", " + emp.getFIRST_NAME());
				ticketFooter.setEMPLOYEE_NO(emp.getIDENTITY_NO());
			}
			ticketFooter.setCUSTOMER_NAME(invHeader.getCustomerName());
			ticketFooter.setCUSTOMER_NUMBER(invHeader.getCustomerNumber());
			if (invHeader.getTaxNumber() != null) {
				ticketFooter.setTAX_PAYER_NUMBER(invHeader.getTaxNumber());
				ticketFooter.setTAX_PAYER_NAME(invHeader.getTaxName());
			} else {
				ticketFooter.setTAX_PAYER_NUMBER("XXX");
				ticketFooter.setTAX_PAYER_NAME("SIN NOMBRE");
			}
			ticketFooter.setDISCOUNT_TOTAL(invHeader.getDiscountAmount());
			ticketFooter.setGROSS_TOTAL(invHeader.getInvoiceAmount() + invHeader.getDiscountAmount());
			ticketFooter.setAMOUNT_TOTAL(invHeader.getInvoiceAmount());
			ticketFooter.setNET_TOTAL(invHeader.getInvoiceAmount());	
			if (invHeader.getSiteBenefName() != null) {
				ticketFooter.setDONATION_CONCEPT(invHeader.getSiteBenefName());
			} else {
				ticketFooter.setDONATION_CONCEPT("DONACION");
			}
			ticketFooter.setDONATION_AMOUNT(collData.getChangeDonationAmt());
			ticketFooter.setCurrencyCode(invHeader.getCurrencyCode());
			ticketFooter.setCcyDecimals(invHeader.getCcyDecimals());
			// mostrar los detalles de los descuentos aplicados
			//if (SessionContext.prnParams.getPRINT_PROM_DETAILS().equalsIgnoreCase("S")) {
			if (printPromDetails.equalsIgnoreCase("S")) {
				// mostrar el detalle de descuentos por producto
				lugarCodigo = "Obtener Detalle Descuento Productos";
				try {
					//ticketFooter.setDiscountDetail(PosPromDiscountApplDAO.getDiscountItemsList(TxControl.transactionId));
					ticketFooter.setDiscountDetail ( PosTransItemsDAO.getDiscountList(transactionId, cashControlId, cashId, conn));
				} catch (Exception e) {
					System.out.println("**** Error al buscar detalle descuentos para impresion ****");
					e.printStackTrace();		    	  
				}
			}
			return null;
		} catch (Exception e2) {
			e2.printStackTrace();
			m = new ApplicationMessage();
			m.setMessage("PRE-IMP-PIE", lugarCodigo + ": " + e2.getMessage(), ApplicationMessage.ERROR);
			return m;		           
		}
	}
	
	public ApplicationMessage printFooter (  ) {
		ApplicationMessage m;
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat fmtr;
		String cadena, s, tmp;
		String task = "Inicio";
		int longCadena = 0;
		GenericElement elem;
		//
		TicketCaption msgTicket;
		ArrayList <TicketCaption> msg = new ArrayList <TicketCaption>();
		//
		@SuppressWarnings("unused")
		String fv1;
		@SuppressWarnings("unused")
		int idx = -1;
		//
		String custLayout = "AMT-LAYOUT-1";

		try {		
			// seccion de mensajes pre-definidos (Antes del Pie)
			msg = PosUtilDAO.getCaptionsList("ANTES-PIE", cashId);
			if (msg != null) {
				task = "Antes Pie";
				for( int i = 0; i < msg.size(); i ++ ) {
					msgTicket = new TicketCaption();
					msgTicket = msg.get(i);
					idx++;
					if (msgTicket.getEMPTY_FLAG().equalsIgnoreCase("S")) {
						s = " ";
						addToDocum (s);
					} else {
						s = msgTicket.getTEXT();
						addToDocum (s);
					}
				}				
			}	
			/**
             +---------------------------------------------------------+
             | seccion pie de ticket propiamente dicho                 |
             +---------------------------------------------------------+
			 */
			if (custLayout.equalsIgnoreCase("AMT-LAYOUT-1")) {
				s = "---------------------------- Impuestos -";
				addToDocum (s);
				//
				if (ticketFooter.getEXCEMPT_TOTAL() != 0) {
					task = "Importe Exento";
					if (ticketFooter.getCcyDecimals() == 0) {
						s = formatDescriptionAmount ( "Total Exento",
								numberFormatter.format(Math.round(ticketFooter.getEXCEMPT_TOTAL())),
								15 );
					} else {
						s = formatDescriptionAmount ( "Total Exento",
								numberFormatter.format(ticketFooter.getEXCEMPT_TOTAL()),
								15 );
					}
					addToDocum (s);
				}
				//
				if (ticketFooter.getTAXABLE_TOTAL() != 0.0) {
					if (ticketFooter.getTAXABLE5_TOTAL() != 0.0) {
						task = "Gravado 5";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado 5%",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE5_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado 5%",
									numberFormatter.format(ticketFooter.getTAXABLE5_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAX5_TOTAL() != 0.0) {
						task = "IVA 5";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A. 5%",
									numberFormatter.format(Math.round(ticketFooter.getTAX5_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A. 5%",
									numberFormatter.format(ticketFooter.getTAX5_TOTAL()),
									15 );
						}
						addToDocum (s);
					}			        
					//
					if (ticketFooter.getTAXABLE10_TOTAL() != 0.0) {
						task = "Gravado 10";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado 10%",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE10_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado 10%",
									numberFormatter.format(ticketFooter.getTAXABLE10_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAX10_TOTAL() != 0.0) {
						task = "IVA 10";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A. 10%",
									numberFormatter.format(Math.round(ticketFooter.getTAX10_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A. 10%",
									numberFormatter.format(ticketFooter.getTAX10_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					s = "-------------------------- Sub totales -";
					addToDocum (s);
					//	            
					if (ticketFooter.getTAXABLE_TOTAL() != 0.0) {
						task = "Total Gravado";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado",
									numberFormatter.format(ticketFooter.getTAXABLE_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAX_TOTAL() != 0.0) {
						task = "Total IVA";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A.",
									numberFormatter.format(Math.round(ticketFooter.getTAX_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A.",
									numberFormatter.format(ticketFooter.getTAX_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
				}			
			} else {
				s = "----------------------------------------";
				addToDocum (s);
				//
				if (ticketFooter.getEXCEMPT_TOTAL() != 0) {
					task = "Importe Exento";
					if (ticketFooter.getCcyDecimals() == 0) {
						s = formatDescriptionAmount ( "Total Exento",
								numberFormatter.format(Math.round(ticketFooter.getEXCEMPT_TOTAL())),
								15 );
					} else {
						s = formatDescriptionAmount ( "Total Exento",
								numberFormatter.format(ticketFooter.getEXCEMPT_TOTAL()),
								15 );
					}
					addToDocum (s);
				}
				//
				if (ticketFooter.getTAXABLE_TOTAL() != 0.0) {
					if (ticketFooter.getTAXABLE_TOTAL() != 0.0) {
						task = "Total Gravado";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado",
									numberFormatter.format(ticketFooter.getTAXABLE_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAXABLE5_TOTAL() != 0.0) {
						task = "Total Gravado 5";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado 5%",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE5_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado 5%",
									numberFormatter.format(ticketFooter.getTAXABLE5_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAXABLE10_TOTAL() != 0.0) {
						task = "Total Gravado 10";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total Gravado 10%",
									numberFormatter.format(Math.round(ticketFooter.getTAXABLE10_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total Gravado 10%",
									numberFormatter.format(ticketFooter.getTAXABLE10_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					s = "----------------------------------------";
					addToDocum (s);
					//
					if (ticketFooter.getTAX5_TOTAL() != 0.0) {
						task = "Total IVA 5";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A. 5%",
									numberFormatter.format(Math.round(ticketFooter.getTAX5_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A. 5%",
									numberFormatter.format(ticketFooter.getTAX5_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAX10_TOTAL() != 0.0) {
						task = "Total IVA 10";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A. 10%",
									numberFormatter.format(Math.round(ticketFooter.getTAX10_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A. 10%",
									numberFormatter.format(ticketFooter.getTAX10_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
					//
					if (ticketFooter.getTAX_TOTAL() != 0.0) {
						task = "Total IVA";
						if (ticketFooter.getCcyDecimals() == 0) {
							s = formatDescriptionAmount ( "Total I.V.A.",
									numberFormatter.format(Math.round(ticketFooter.getTAX_TOTAL())),
									15 );
						} else {
							s = formatDescriptionAmount ( "Total I.V.A.",
									numberFormatter.format(ticketFooter.getTAX_TOTAL()),
									15 );
						}
						addToDocum (s);
					}
				}
			}
			/*
             +--------------------------------------------------------+
             | Informacion del contribuyente                          |
             +--------------------------------------------------------+			 
			 */
			if ( ticketFooter.getTAX_PAYER_NAME() != null ) {
				if (!ticketFooter.getTAX_PAYER_NAME().isEmpty()) {
					s = "----------------------------------------";
					addToDocum (s);
					if (ticketFooter.getTAX_PAYER_NUMBER() != null) {
						task = "RUC";
						s = "R.U.C.: " + ticketFooter.getTAX_PAYER_NUMBER();
						addToDocum (s);
					}
					if (ticketFooter.getTAX_PAYER_NAME() != null) {
						task = "Denom. RUC";
						longCadena = ticketFooter.getTAX_PAYER_NAME().length();
						if (longCadena >= 40) {
							cadena = ticketFooter.getTAX_PAYER_NAME().substring(0, 39);
						} else {
							cadena = ticketFooter.getTAX_PAYER_NAME();
						}
						s = cadena;
						addToDocum (s);
					}
				}
			}			
			/*
             +--------------------------------------------------------+
             | Informacion del cliente                                |
             +--------------------------------------------------------+			 
			 */
			if ( ticketFooter.getCUSTOMER_NAME() != null ) {
				if (!ticketFooter.getCUSTOMER_NAME().isEmpty()) {
					s = "----------------------------------------";
					addToDocum (s);
					if (ticketFooter.getCUSTOMER_NUMBER() != null) {
						task = "ID. Cliente";
						s = "C.I.: " + ticketFooter.getCUSTOMER_NUMBER();
						addToDocum (s);
					}
					if (ticketFooter.getCUSTOMER_NAME() != null) {
						task = "Denom. Cliente";
						longCadena = ticketFooter.getCUSTOMER_NAME().length();
						if (longCadena >= 30) {
							cadena = ticketFooter.getCUSTOMER_NAME().substring(0, 29);
						} else {
							cadena = ticketFooter.getCUSTOMER_NAME();
						}
						s = "Cliente: " + cadena;
						addToDocum (s);
					}
				}
			} 
			/*
             +--------------------------------------------------------+
             | Cliente innominado                                     |
             +--------------------------------------------------------+			 
			 */
			if (ticketFooter.getTAX_PAYER_NAME() == null & ticketFooter.getCUSTOMER_NAME() == null) {
				// codigo agregado en 05/mar/2022
				task = "Cliente Innominado";
				s = "----------------------------------------";
				addToDocum (s);
				s = "C.I. / R.U.C.: XXX";
				addToDocum (s);
				s = "Cliente: SIN NOMBRE";
				addToDocum (s);
			}
			// seccion de resumen de la operacion
			s = "----------------------------------------";
			addToDocum (s);
			// Articulos + Total neto
			task = "Cantidad Articulos";
			cadena = numberFormatter.format(ticketFooter.getPRODUCTS_QUANTITY());
			cadena = UtilPOS.paddingString(cadena, 6, ' ', true);
			s = "Articulos: " + cadena;
			task = "Total Neto";
			if (ticketFooter.getCcyDecimals() == 0) {
				cadena = numberFormatter.format(Math.round(ticketFooter.getNET_TOTAL()));
			} else {
				cadena = numberFormatter.format(ticketFooter.getNET_TOTAL());
			}
			cadena = UtilPOS.paddingString(cadena, 15, ' ', true);
			s = s + "  Neto: " + cadena;
			addToDocum (s);
			// Total descuento
			task = "Total Descuento";
			if (ticketFooter.getCcyDecimals() == 0) {
				cadena = numberFormatter.format(Math.round(ticketFooter.getDISCOUNT_TOTAL()));
			} else {
				cadena = numberFormatter.format(ticketFooter.getDISCOUNT_TOTAL());
			}
			cadena = UtilPOS.paddingString(cadena, 11, ' ', true);
			s = "Dto.: " + cadena;
			addToDocum (s);
			// Total factura
			s = "****************************************";
			addToDocum (s);
			if (ticketFooter.getCcyDecimals() == 0) {
				cadena = numberFormatter.format(Math.round(ticketFooter.getNET_TOTAL()));
			} else {
				cadena = numberFormatter.format(ticketFooter.getNET_TOTAL());
			}
			task = "Total Transaccion";
			cadena = UtilPOS.paddingString(cadena, 15, ' ', true);
			s = "       TOTAL " + ticketFooter.getCurrencyCode() + " " + cadena;
			addToDocum (s);
			s = "****************************************";
			addToDocum (s);
			/**
             +------------------------------------------------------+
             ! seccion de detalle de formas de pago	                !
             +------------------------------------------------------+
			 */
			if (ticketFooter.getCollectionDetail() != null) {
				for( int i = 0; i < ticketFooter.getCollectionDetail().size(); i ++ ) {
					elem = ticketFooter.getCollectionDetail().elementAt(i);
					task = "Item Cobro " + elem;
					s = formatDescriptionAmount ( elem.getDESCRIPTION(),
							numberFormatter.format(Math.round(elem.getAMOUNT())),
							15 );
					addToDocum (s);
				}
			}
			// seccion de venta a credito
			if (ticketFooter.getTRX_CONDITION().equalsIgnoreCase("CREDITO")) {
				task = "Firma Credito";
				s = "========================================";
				addToDocum (s);
				s = "Fecha: _________________________________";
				addToDocum (s);
				s = "Firma: _________________________________";
				addToDocum (s);
				s = "Aclaracion: ____________________________";
				addToDocum (s);
				s = "========================================";
				addToDocum (s);
			}
			// indicacion de destino de las copias
			task = "Destino Copias";
			s = " ";
			addToDocum (s);
			s = "ORIGINAL: Cliente - Comprador";
			addToDocum (s);
			s = "DUPLICADO: Archivo Tributario";
			addToDocum (s);
			// seccion datos de empleado por forma de pago VALE-EMPLEADO
			if (ticketFooter.getEMPLOYEE_NAME() != null) {
				task = "Datos Empleado";
				s = " ";
				addToDocum (s);
				addToDocum (s);
				s = " -------------------------------------- ";
				addToDocum (s);
				if (ticketFooter.getEMPLOYEE_NAME().length() > 40) {
					s = ticketFooter.getEMPLOYEE_NAME().substring(0, 40);
				} else {
					s = ticketFooter.getEMPLOYEE_NAME().substring(0);
				}
				addToDocum (s);
			}
			// seccion de redondeo de vuelto
			if ( ticketFooter.getDONATION_AMOUNT() > 0.0 ) {
				if (ticketFooter.getDONATION_CONCEPT() != null & ticketFooter.getDONATION_CONCEPT().isEmpty() == false) {
					task = "Datos Donacion";
					if (ticketFooter.getDONATION_CONCEPT().length() > (LINE_LENGTH - (15 + 2))) {
						s = formatDescriptionAmount ( ticketFooter.getDONATION_CONCEPT().substring(0, (LINE_LENGTH - (15 + 2))),
								numberFormatter.format(Math.round(ticketFooter.getDONATION_AMOUNT())),
								15 );
					} else {
						s = formatDescriptionAmount ( ticketFooter.getDONATION_CONCEPT(),
								numberFormatter.format(Math.round(ticketFooter.getDONATION_AMOUNT())),
								15 );						
					}
				} else {
					task = "Datos Redondeo";
					s = formatDescriptionAmount ( "REDONDEO",
							numberFormatter.format(Math.round(ticketFooter.getDONATION_AMOUNT())),
							15 );
				}
				addToDocum (s);
			}
			// seccion de promociones	
			if (ticketFooter.getPromoDetail() != null) {
				for( int i = 0; i < ticketFooter.getPromoDetail().size(); i ++ ) {
					s = ticketFooter.getPromoDetail().elementAt(i);
					task = "Promo " + s;
					addToDocum (s);
				}
			}
			// seccion de detalle de descuentos por producto
			// lista de descuentos standard
			if (ticketFooter.getDiscountDetail() != null && ticketFooter.getDiscountDetail().isEmpty() == false) {		
				task = "Lista Desctos.";
				// lista de descuentos para Comercial Cacique
				s = " ";
				addToDocum (s);
				s = " ***  D  E  S  C  U  E  N  T  O  S  ***";
				addToDocum (s);
				s = "Articulo             Precio Dcto. Neto";
				addToDocum (s);
				s = "----------------------------------------";
				addToDocum (s);
				for( int i = 0; i < ticketFooter.getDiscountDetail().size(); i ++ ) {
					elem = ticketFooter.getDiscountDetail().elementAt(i);
					// descripcion del producto
					if (elem.getDESCRIPTION().length() > 15) {
						tmp = elem.getDESCRIPTION().substring(0, 14);
					} else {
						tmp = UtilPOS.paddingString(elem.getDESCRIPTION(), 15, ' ', false);
					}
					cadena = tmp;
					// precio normal
					fmtr = new DecimalFormat("###,##0");
					tmp = fmtr.format(Math.round(elem.getUNIT_PRICE()));
					tmp = UtilPOS.paddingString(tmp, 7, ' ', true);
					cadena = cadena + " " + tmp;
					// descuento unitario
					fmtr = new DecimalFormat("##,##0");
					tmp = fmtr.format(Math.round(elem.getUNIT_DISCOUNT_AMOUNT()));
					tmp = UtilPOS.paddingString(tmp, 6, ' ', true);
					cadena = cadena + " " + tmp;
					// precio neto
					fmtr = new DecimalFormat("###,##0");
					tmp = fmtr.format(Math.round(elem.getNET_UNIT_PRICE()));
					tmp = UtilPOS.paddingString(tmp, 7, ' ', true);
					cadena = cadena + " " + tmp;	
					s = cadena;
					addToDocum (s);
					// linea de resumenes
					// Total 999,999 Dto. -99,999 Neto 999,999
					fmtr = new DecimalFormat("###,##0");
					tmp = fmtr.format(Math.round(elem.getGROSS_AMOUNT()));
					tmp = UtilPOS.paddingString(tmp, 7, ' ', true);
					cadena = "Total " + tmp;
					fmtr = new DecimalFormat("##,##0");
					tmp = fmtr.format(Math.round(elem.getDISCOUNT_AMOUNT()));
					tmp = UtilPOS.paddingString(tmp, 6, ' ', true);
					cadena = cadena + " Dto. " + tmp;
					fmtr = new DecimalFormat("###,##0");
					tmp = fmtr.format(Math.round(elem.getNET_AMOUNT()));
					tmp = UtilPOS.paddingString(tmp, 7, ' ', true);
					cadena = cadena + " Neto " + tmp;		            
					idx++;
					s = cadena;
					addToDocum (s);
				}
			}	
			// fin de pie de comprobante
			s = " ";
			addToDocum (s);
			// seccion de mensajes pre-definidos (Despues del Pie)
			msg = PosUtilDAO.getCaptionsList("DESPUES-PIE", cashId);
			if (msg != null) {
				task = "Despues Pie";
				for( int i = 0; i < msg.size(); i ++ ) {
					msgTicket = new TicketCaption();
					msgTicket = msg.get(i);
					if (msgTicket.getEMPTY_FLAG().equalsIgnoreCase("S")) {
						s = " ";
						addToDocum (s);
					} else {
						s = msgTicket.getTEXT();
						addToDocum (s);
					}
				}				
			}
			return null;
		} catch(Exception e) {
			e.printStackTrace();	
			System.out.println("Error Pie: " + e.getMessage());
			m = new ApplicationMessage();
			m.setMessage("PRINT-FOOTER", task + ": " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	  
	public ApplicationMessage buildQrImage ( ) {
		ApplicationMessage m;
		String qrString = invHeader.getQrCode();
	    CodeBarUtils qrTools;
        try {
            int size = 220;
            qrTools = new CodeBarUtils();
            java.awt.Image qrImg = qrTools.generateQR(qrString.trim(), size);
            if ( qrImg != null ) {
            	    addImage(qrImg);
            }   
            return null;
        } catch (WriterException ex) {
            Logger.getLogger(TestQR.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            m = new ApplicationMessage("BUILD-QR", "Error: " + ex.getMessage(), ApplicationMessage.ERROR);
            return m;
        }
	}
	
	public ApplicationMessage prinTaxCaptions (  ) {
		ApplicationMessage m;
		String s;
		try {
			//   1234567890123456789012345678901234567890
			s = "  Consulta la validez de esta Factura ";
			addToDocum (s);
			s = "   Electronica con el numero de CDC";
			addToDocum (s);
			s = "          impreso abajo en:";
			addToDocum (s);
			s = " https://ekuatia.set.gov.py/consultas/";
			addToDocum (s);
			// codigo de control
			s = invHeader.getEbControlCode();
			addToDocum (s);
			//   1234567890123456789012345678901234567890
			s = "  ESTE DOCUMENTO ES UNA REPRESENTACION ";
			addToDocum (s);
			s = "  GRAFICA DE UN DOCUMENTO ELECTRONICO";
			addToDocum (s);
			s = "                (XML)";
			addToDocum (s);
			
			return null;
		} catch(Exception e){
			e.printStackTrace();
			m = new ApplicationMessage();
			m.setMessage("PRN", e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
	}
	
	private String formatLine ( String text, int lineLength, int alignment ) {
	    String s = text;
	    if ( alignment == CENTRO ) {
	        s = UtilPOS.centerString(text, lineLength);
	        return s;
	    }
	    if ( alignment == DERECHA ) {
	        s = UtilPOS.paddingString(text, lineLength, ' ', false);
	        return s;
	    }
	    if ( alignment == IZQUIERDA ) {
	        s = UtilPOS.paddingString(text, lineLength, ' ', true);
	        return s;
	    }
	    return s;
	}
	
	private String formatDescriptionAmount (String description, String amount, int amountLength) {
        String s;
        String s1;
        String s2;
        char filler = ' ';
        // formatear el componente "Descripcion"
        int longItem = 40 - (amountLength + 1);
        if (description.length() > longItem)
        	s1 = description.substring(0, (longItem - 1));
        else
            s1 = description;
        s1 = UtilPOS.paddingString(s1, longItem, filler, false);
        // formatear el componente "Importe"
        if (amount.length() > amountLength)
        	s2 = amount.substring(0, amountLength);
        else
            s2 = amount;
        s2 = UtilPOS.paddingString(s2, amountLength, filler, true);
        //
        s = s1 + " " + s2;
        return s;
	}	
	
	private void addToDocum ( String s ) {
		try {
		    document.add(Chunk.NEWLINE);
			Chunk chunk = new Chunk(s, font);
		    document.add(chunk);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void addImage ( java.awt.Image src ) {
		try {
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(src, null); 
			document.add(img);			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public ApplicationMessage closeDocument () {
		ApplicationMessage m;
		try {
			document.close();
			return null;
		} catch ( Exception e) {
			e.printStackTrace();
            m = new ApplicationMessage("CLOSE-PDF", "Error: " + e.getMessage(), ApplicationMessage.ERROR);
            return m;
		}
	}
	
	public ApplicationMessage createDocument ( long transactionId, long controlId, long cashId, String txNumber ) {
		ApplicationMessage m;
		try {
			setCashId(cashId);
			setCashControlId(controlId);
			setTransactionId(transactionId);
			// preparar el archivo PDF
			String fileName = txNumber + "_" + String.valueOf(transactionId) + ".pdf";
			m = prepareDocument( fileName );
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			// obtener los datos de la transaccion
	        conn = Util.getConnection();
	        if (conn == null) {
				m = new ApplicationMessage("DB-CONN", "No se pudo obtener conexion con la base de datos", ApplicationMessage.ERROR);
				return m;
	        }
			m = getTransactionData();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			// generar los datos de las secciones de impresion
			formatHeader();
			formatItems();
			formatFooter();
			// descargar el contenido al documento PDF
			m = printHeader();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			m = printItems();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			m = printFooter();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			// agregar la imagen correspondiente al codigo QR
			m = buildQrImage();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			// imprimir los mensajes de la administracion tributaria
			prinTaxCaptions();
			// cerrar el documento
			m = closeDocument();
			if (m != null) {
				if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
					return m;
				}
			}
			// actualizar la transaccion con el nombre del archivo generado
			PosTransactionsDAO.updateGrFile (transactionId, controlId, cashId, fileName);
			//
			m = new ApplicationMessage("GEN-GRAPH-REP", "Actividad finalizada con exito", ApplicationMessage.MESSAGE);
			return m;
		} catch ( Exception e) {
			e.printStackTrace();
			m = new ApplicationMessage("GEN-GRAPH-REP", "Error: " + e.getMessage(), ApplicationMessage.ERROR);
			return m;
		}
		
	}
	
	public static void main (String args[]) {
		@SuppressWarnings("unused")
		ApplicationMessage m;
		try {
			
			GenerateInvoicePdf g = new GenerateInvoicePdf();
			g.setCashId(1);
			g.setCashControlId(1);
			g.setTransactionId(1);
			// preparar el archivo PDF
			m = g.prepareDocument("001-001-0000001_1.pdf");
			// obtener los datos de la transaccion
	        Connection conn = Util.getConnection();
	        if (conn == null) {
	            System.out.println("No hay conexion con base de datos");
	        }
			g.getTransactionData();
			// generar los datos de las secciones de impresion
			g.formatHeader();
			g.formatItems();
			g.formatFooter();
			// descargar el contenido al documento PDF
			g.printHeader();
			g.printItems();
			g.printFooter();
			// agregar la imagen correspondiente al codigo QR
			g.buildQrImage();
			// cerrar el documento
			g.closeDocument();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
}
