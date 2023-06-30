package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import business.ApplicationMessage;
import business.RcvTrxEbBatchItemsTM;
import business.SessionContext;
import business.ViewElectronicDocumentCtrl;

public class ViewElectronicDocument extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpCenter = null;
	private JPanel jpOverview = null;
	private JScrollPane jsOverview = null;
	private JPanel jpIssuingCo = null;
	private JScrollPane jsIssuingCo = null;
	// Encabezado de formulario
	private JLabel jlVendorLogo = null;
	private JLabel jlOrgName;
	private JLabel jlUserName;
	// Barra de herramientas
	private JLabel jlAction1 = null;
	private JLabel jlAction2 = null;
	private JLabel jlAction3 = null;
	private JLabel jlAction4 = null;
	private JLabel jlAction5 = null;
	private JLabel jlAction6 = null;
	private JLabel jlAction7 = null;
	private JLabel jlAction8 = null;
	
	// Main data grid
	private JTable jaDocBatches;
	
	// Overview data
	private JLabel jlIssuingName;
	private JLabel jlIssuingTaxNo;
	private JLabel jlReceiverName;
	private JLabel jlReceiverId;
	private JLabel jlTransactionType;
	private JLabel jlSaleCondition;
	private JLabel jlOperationType;
	private JLabel jlTaxClass;
	private JLabel jlQrHash;
	private JLabel jlAmount;
	private JLabel jlExempt;
	private JLabel jlTaxable5;
	private JLabel jlTax5;
	private JLabel jlTaxable10;
	private JLabel jlTax10;
	//
	private JTextField jtIssuingName;
	private JTextField jtIssuingTaxNo;
	private JTextField jtReceiverName;
	private JTextField jtReceiverId;
	private JTextField jtTransactionType;
	private JTextField jtSaleCondition;
	private JTextField jtOperationType;
	private JTextField jtTaxClass;
	private JTextField jtQrHash;
	private JTextField jtExempt;
	private JTextField jtAmount;
	private JTextField jtTaxable5;
	private JTextField jtTax5;
	private JTextField jtTaxable10;
	private JTextField jtTax10;
	
	// look and feel
	private AppLookAndFeel appLook;
	private OvalBorder lineBorder = new OvalBorder();

	private int windowWidth;
	private int windowHeight;
	// toolbar panel
	private int toolbarWidth;
	private int toolbarHeight;
	private int toolbarX;
	private int toolbarY;
	private int tbtnWidth, tbtnHeight;
	// filters panel
	private int filtersWidth;
	private int filtersHeight;
	private int filtersX;
	private int filtersY;
	// center panel
	private int centerWidth;
	private int centerHeight;
	private int centerX;
	private int centerY;
	private int abtnWidth, abtnHeight;
	
	private int xPos;
	private int yPos;
	private int labelWidth, labelHeight;
	private int tfWidth, tfHeight;
	private int horizGap;
	private int vertGap;
	
	private int lbW, lbH;
	private int lbX, lbY;
	private int tfW, tfH;
	private int tfX, tfY;
	
	private ViewElectronicDocumentCtrl ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
		
	/**
	 * This is the default constructor
	 */
	public ViewElectronicDocument ( Frame owner, ViewElectronicDocumentCtrl ctrl ) {
		super(owner, "Items de Lotes de Documentos", true);
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = ctrl;
		// initialize controller
		ApplicationMessage aMsg = ctrl.initForm();
		// build GUI
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println("Resolucion: " + screenSize.width + " x " + screenSize.height);
		//windowWidth = 1000;
		//windowHeight = 750;
		windowWidth = (int) (screenSize.width * 0.8);
		windowHeight = (int) (screenSize.height * 0.8);
		this.setSize((int)windowWidth, (int)windowHeight);
		this.setLocation(new Point(80, 100));
		this.setName("ViewRcvTrxBatchItems");
		this.setModal(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Items de Lotes de Documentos Mayoristas");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setVisible(true);
		//this.addWindowListener(new java.awt.event.WindowAdapter() {
		//	public void windowClosing(java.awt.event.WindowEvent e) {
		//		closeWindow ();
		//	}
		//});
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			//
			labelWidth = getRelativeWidth (windowWidth, 12.0);
			labelHeight = getRelativeHeight (windowHeight, 5.0);	
			tfWidth = getRelativeWidth (windowWidth, 12.0);
			tfHeight = getRelativeHeight (windowHeight, 5.0);	
			//
			toolbarX = 0;
			toolbarY = 0;
			toolbarWidth = windowWidth;
			toolbarHeight = 55;
			this.add(getJpToolbar());
			//
			centerWidth = windowWidth;
			centerHeight = windowHeight - (toolbarHeight + 50);
			centerX = 0;
			centerY = toolbarHeight + filtersHeight;
			this.add(getJpCenter(), null);
		}
		return jContentPane;
	}	
	
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Region Toolbar                               | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */
	private JPanel getJpToolbar() {
		if (jpToolbar == null) {
			//
			jpToolbar = new JPanel();
			jpToolbar.setLayout(null);
			//System.out.println("jpToolbar x: " + toolbarX + " y:" + toolbarY + " w: " + toolbarWidth + " h: " + toolbarHeight);
			jpToolbar.setSize(toolbarWidth, toolbarHeight);
			jpToolbar.setLocation(new Point(toolbarX, toolbarY));
			jpToolbar.setBackground(appLook.getHeaderBg());
			//jpToolbar.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			//
			tbtnWidth = 180;
			tbtnHeight = 55;
			xPos = 0;
			//
			jlAction1 = new JLabel();
			jlAction1.setSize(new Dimension(tbtnWidth, tbtnHeight));
			jlAction1.setLocation(new Point(xPos, 1));
			jlAction1.setText(" Resumen");
			jlAction1.setBackground(appLook.getMenuBg());
			jlAction1.setForeground(appLook.getMenuFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    closeWindow();
			    } 
				public void mouseEntered (MouseEvent evt) {
					jlAction1.setBackground(appLook.getSelMenuBg());
					jlAction1.setForeground(appLook.getSelMenuFg());
					jlAction1.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction1.setBackground(appLook.getMenuBg());
					jlAction1.setForeground(appLook.getMenuFg());
					jlAction1.repaint();
				}
			});
			//
			jlAction2 = new JLabel();
			jlAction2.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction2.setLocation(new Point(xPos, 1));
			jlAction2.setText(" Doc. Asociados");
			jlAction2.setBackground(appLook.getMenuBg());
			jlAction2.setForeground(appLook.getMenuFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    closeWindow();
			    } 
				public void mouseEntered (MouseEvent evt) {
					jlAction2.setBackground(appLook.getSelMenuBg());
					jlAction2.setForeground(appLook.getSelMenuFg());
					jlAction2.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction2.setBackground(appLook.getMenuBg());
					jlAction2.setForeground(appLook.getMenuFg());
					jlAction2.repaint();
				}
			});
			//
			jlAction3 = new JLabel();
			jlAction3.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction3.setLocation(new Point(xPos, 1));
			jlAction3.setText(" Eventos");
			jlAction3.setBackground(appLook.getMenuBg());
			jlAction3.setForeground(appLook.getMenuFg());
			jlAction3.setFont(appLook.getSmallFont());
			jlAction3.setOpaque(true);
			jlAction3.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction3.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    closeWindow();
			    } 
				public void mouseEntered (MouseEvent evt) {
					jlAction3.setBackground(appLook.getSelMenuBg());
					jlAction3.setForeground(appLook.getSelMenuFg());
					jlAction3.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction3.setBackground(appLook.getMenuBg());
					jlAction3.setForeground(appLook.getMenuFg());
					jlAction3.repaint();
				}
			});
			//
			jlAction4 = new JLabel();
			jlAction4.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction4.setLocation(new Point(xPos, 1));
			jlAction4.setText(" Salir");
			jlAction4.setBackground(appLook.getMenuBg());
			jlAction4.setForeground(appLook.getMenuFg());
			jlAction4.setFont(appLook.getSmallFont());
			jlAction4.setOpaque(true);
			jlAction4.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction4.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    closeWindow();
			    } 
				public void mouseEntered (MouseEvent evt) {
					jlAction4.setBackground(appLook.getSelMenuBg());
					jlAction4.setForeground(appLook.getSelMenuFg());
					jlAction4.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction4.setBackground(appLook.getMenuBg());
					jlAction4.setForeground(appLook.getMenuFg());
					jlAction4.repaint();
				}
			});
			//
			jpToolbar.add(jlAction1, null);
			jpToolbar.add(jlAction2, null);
			jpToolbar.add(jlAction3, null);
			jpToolbar.add(jlAction4, null);
		}
		return jpToolbar;
	}
	
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Region Center                                | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */
	private JPanel getJpCenter() {
		if (jpCenter == null) {
			jpCenter = new JPanel();
			jpCenter.setLayout(null);
			//System.out.println("jpCenter x: " + centerX + " y:" + centerY + " w: " + centerWidth + " h: " + centerHeight);
			jpCenter.setSize(new Dimension(centerWidth, centerHeight));
			jpCenter.setPreferredSize(new Dimension(centerWidth, centerHeight));
			jpCenter.setLocation(new Point(centerX, centerY));
			jpCenter.setBackground(appLook.getBodyBg());
			//jpCenter.setBorder(BorderFactory.createLineBorder(Color.red, 1));
			jpCenter.add(getjsOverview(), null);
		}
		return jpCenter;
	}	

	private JScrollPane getjsOverview() {
		if (jsOverview == null) {
			jsOverview = new JScrollPane();
			jsOverview.setSize(new Dimension(centerWidth, centerHeight));
			jsOverview.setLocation(new Point(10, 10));
			jsOverview.setViewportView(getJpOverview());
		}
		return jsOverview;
	}	
	
	private JPanel getJpOverview() {
		if (jpOverview == null) {
			jpOverview = new JPanel();
			jpOverview.setLayout(new GridLayout(16, 2, 10, 10));
			jpOverview.setSize(new Dimension(centerWidth, centerHeight));
			//System.out.println("jpFilters x: " + filtersX + " y:" + filtersY + " w: " + filtersWidth + " h: " + filtersHeight);
			jpOverview.setPreferredSize(new Dimension(centerWidth, centerHeight));
			jpOverview.setLocation(new Point(0, 0));
			jpOverview.setBackground(appLook.getBodyBg());
			//jpOverview.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			//
			/**
			 +----------------------------------------------------+
			 | region labels placement                            |
			 +----------------------------------------------------+
			*/
			// primera linea
			jlIssuingName = getDataLabel("Razon Social del Emisor");
			jpOverview.add(jlIssuingName, null);
			jlIssuingTaxNo = getDataLabel("RUC");
			jpOverview.add(jlIssuingTaxNo, null);
			jpOverview.add(getjtIssuingName(), null);
			jpOverview.add(getjtIssuingTaxNo(), null);
			// segunda linea
			jlReceiverName = getDataLabel("Nombre o Razon Social del Receptor");
			jpOverview.add(jlReceiverName, null);
			jlReceiverId = getDataLabel("RUC/No. Identificacion");
			jpOverview.add(jlReceiverId, null);
			jpOverview.add(getjtReceiverName(), null);
		    jpOverview.add(getjtReceiverId(), null);
			// tercera linea
			jlTransactionType = getDataLabel("Tipo de Transaccion");
			jpOverview.add(jlTransactionType, null);
			jlSaleCondition = getDataLabel("Condicion de Venta");
			jpOverview.add(jlSaleCondition, null);
			jpOverview.add(getjtTransactionType(), null);
		    jpOverview.add(getjtSaleCondition(), null);
			// cuarta linea
			jlOperationType = getDataLabel("Tipo de Operacion");
			jpOverview.add(jlOperationType, null);
			jlTaxClass = getDataLabel("Tipo de Impuesto Afectado");
			jpOverview.add(jlTaxClass, null);
			jpOverview.add(getjtOperationType(), null);
		    jpOverview.add(getjtTaxClass(), null);
			// quinta linea
			jlQrHash = getDataLabel("Hash QR");
			jpOverview.add(jlQrHash, null);
			jlAmount = getDataLabel("Total");
			jpOverview.add(jlAmount, null);
			jpOverview.add(getjtQrHash(), null);
			jpOverview.add(getjtAmount(), null);
			// sexta linea
			jlTaxable5 = getDataLabel("Gravado 5%");
			jpOverview.add(jlTaxable5, null);
			jlTax5 = getDataLabel("IVA 5%");
			jpOverview.add(jlTax5, null);
			jpOverview.add(getjtTaxable5(), null);
		    jpOverview.add(getjtTax5(), null);
			// septima linea
			jlTaxable10 = getDataLabel("Gravado 10%");
			jpOverview.add(jlTaxable10, null);
			jlTax10 = getDataLabel("IVA 10%");
			jpOverview.add(jlTax10, null);
			jpOverview.add(getjtTaxable10(), null);
		    jpOverview.add(getjtTax10(), null);
			// octava linea
			jlExempt = getDataLabel("Exento");
			jpOverview.add(jlExempt, null);
			JLabel l = getDataLabel(".");
			jpOverview.add(l, null);
		    jpOverview.add(getjtExempt(), null);
		    //jpOverview.add(l, null);

		    
		}
		return jpOverview;
	}	
		
	private JLabel getDataLabel ( String text ) {
		JLabel l = new JLabel();
		l.setHorizontalTextPosition(SwingConstants.LEFT);
		l.setHorizontalAlignment(SwingConstants.LEFT);
		//l.setSize(new Dimension(lbW, lbH));
		//l.setLocation(new Point(lbX, lbY));
		l.setForeground(appLook.getBodyFg());
		l.setFont(appLook.getSmallFont());
		l.setText(text);
		return l;
	}
	
	private JTextField getDataTextfield () {
		JTextField t = new JTextField();
		t.setSize(new Dimension(tfW, tfH));
		t.setLocation(new Point(tfX, tfY));
		t.setBackground(appLook.getBodyBg());
		t.setForeground(appLook.getBodyFg());
		t.setFont(appLook.getRegularFont());
		//jtCustomerSite.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		t.setBorder(lineBorder);
		return t;
	}	
	
	/*
	private JTextField jtIssuingTaxNo;
	private JTextField jtReceiverName;
	private JTextField jtReceiverTaxNo;
	private JTextField jtReceiverIdNo;
	private JTextField jtTransactionType;
	private JTextField jtSaleCondition;
	private JTextField jtOperationType;
	private JTextField jtTaxClass;
	private JTextField jtQrHash;
	 */
	private JTextField getjtIssuingName() {
		if (jtIssuingName == null) {
			jtIssuingName = getDataTextfield();
			jtIssuingName.setText(ctrl.getDE().getgDatGralOpe().getgEmis().getdNomEmi());
		}
		return jtIssuingName;
	}	

	private JTextField getjtIssuingTaxNo() {
		if (jtIssuingTaxNo == null) {
			jtIssuingTaxNo = getDataTextfield();
			jtIssuingTaxNo.setText(ctrl.getDE().getgDatGralOpe().getgEmis().getdRucEm());
		}
		return jtIssuingTaxNo;
	}	
	
	private JTextField getjtReceiverName() {
		if (jtReceiverName == null) {
			jtReceiverName = getDataTextfield();
			jtReceiverName.setText(ctrl.getDE().getgDatGralOpe().getgDatRec().getdNomRec());
		}
		return jtReceiverName;
	}	
	
	private JTextField getjtReceiverId() {
		if (jtReceiverId == null) {
			jtReceiverId = getDataTextfield();
		    jtReceiverId.setText("-");
			if (ctrl.getDE().getgDatGralOpe().getgDatRec().getdNumIDRec() != null) {
			    jtReceiverId.setText(ctrl.getDE().getgDatGralOpe().getgDatRec().getdNumIDRec());
			}
			if (ctrl.getDE().getgDatGralOpe().getgDatRec().getdRucRec() != null) {
			    jtReceiverId.setText(ctrl.getDE().getgDatGralOpe().getgDatRec().getdRucRec());
			}
		}
		return jtReceiverId;
	}	

	private JTextField getjtTransactionType() {
		if (jtTransactionType == null) {
			jtTransactionType = getDataTextfield();
			jtTransactionType.setText(ctrl.getDE().getgDatGralOpe().getgOpeCom().getiTipTra().getDescripcion());
		}
		return jtTransactionType;
	}	

	private JTextField getjtSaleCondition() {
		if (jtSaleCondition == null) {
			jtSaleCondition = getDataTextfield();
			jtSaleCondition.setText(ctrl.getDE().getgDtipDE().getgCamCond().getiCondOpe().getDescripcion());
		}
		return jtSaleCondition;
	}	
	
	private JTextField getjtOperationType() {
		if (jtOperationType == null) {
			jtOperationType = getDataTextfield();
			jtOperationType.setText(ctrl.getDE().getgDatGralOpe().getgDatRec().getiTiOpe().name());
		}
		return jtOperationType;
	}	

	private JTextField getjtTaxClass() {
		if (jtTaxClass == null) {
			jtTaxClass = getDataTextfield();
			jtTaxClass.setText(ctrl.getDE().getgDatGralOpe().getgOpeCom().getiTImp().getDescripcion());
		}
		return jtTaxClass;
	}	

	private JTextField getjtQrHash() {
		if (jtQrHash == null) {
			jtQrHash = getDataTextfield();
			jtQrHash.setText(ctrl.getDE().getEnlaceQR());
		}
		return jtQrHash;
	}	

	private JTextField getjtAmount() {
		if (jtAmount == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdTotalGs());
	        jtAmount = getDataTextfield();
	        jtAmount.setText(val);
		}
		return jtAmount;
	}	

	private JTextField getjtExempt() {
		if (jtExempt == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdSubExe());
			jtExempt = getDataTextfield();
			jtExempt.setText(val);
		}
		return jtExempt;
	}	

	private JTextField getjtTaxable5() {
		if (jtTaxable5 == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdBaseGrav5());
	        jtTaxable5 = getDataTextfield();
	        jtTaxable5.setText(val);
		}
		return jtTaxable5;
	}	

	private JTextField getjtTax5() {
		if (jtTax5 == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdIVA5());
	        jtTax5 = getDataTextfield();
	        jtTax5.setText(val);
		}
		return jtTax5;
	}	
	
	private JTextField getjtTaxable10() {
		if (jtTaxable10 == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdBaseGrav10());
	        jtTaxable10 = getDataTextfield();
	        jtTaxable10.setText(val);
		}
		return jtTaxable10;
	}	

	private JTextField getjtTax10() {
		if (jtTax10 == null) {
	        DecimalFormat df = new DecimalFormat("###,###,###.##");
	        String val = df.format(ctrl.getDE().getgTotSub().getdIVA10());
	        jtTax10 = getDataTextfield();
	        jtTax10.setText(val);
		}
		return jtTax10;
	}	

	private JTable getEmptyTable ( ) {
		RcvTrxEbBatchItemsTM tm = new RcvTrxEbBatchItemsTM ();
		//
		tm = ctrl.createQueryEmptyTable();
		JTable table = new JTable(tm);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);
		for ( int i=0; i<table.getColumnCount(); i++) {
			TableColumn tc = table.getColumn(table.getColumnName(i));
			if ( i == 0 ) {
				tc.setPreferredWidth(50);
				//tc.setCellRenderer(new ConfigTablaItemsVenta());
			}
			if ( i == 1 ) {
				tc.setPreferredWidth(50);
				//tc.setCellRenderer(new ConfigTablaItemsVenta());
			}
			if ( i == 2 ) {
				tc.setPreferredWidth(50);
			}
		}
        return table;
	}	
	
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Login and GUI utilities                      | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */

	private int getRelativeWidth (int h, double p) {
		double baseValue = (double) h;
		double pctValue = p / 100.0;
		double d = baseValue * pctValue;
		//System.out.println("wbaseValue: " + baseValue + " wpctValue: " + pctValue + " d: " + d);
		return (int) d;
	}

	private int getRelativeHeight (int h, double p) {
		double baseValue = (double) h;
		double pctValue = p / 100.0;
		double d = baseValue * pctValue;
		//System.out.println("hbaseValue: " + baseValue + " hpctValue: " + pctValue + " d: " + d);
		return (int) d;
	}
	
	public ImageIcon getScaledIcon (String iconName, int width, int height) {
		//ImageIcon icon = new ImageIcon(iconName);
		ImageIcon icon = new ImageIcon(getClass().getResource(iconName));
		Image img = icon.getImage();  
		Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);  
		ImageIcon newIcon = new ImageIcon(newimg);
		return newIcon;
	}	   
    
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Inteligencia de negocio                      | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */
    private void executeQuery () {
		jpCenter.repaint();
    }
    
    private void closeWindow () {
		dispose();
	}
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMessage aMsg = SessionContext.getContextValues();
		//
		ViewElectronicDocument w = new ViewElectronicDocument(null, new ViewElectronicDocumentCtrl());
		w.setVisible(true);
		w.repaint();
	}
    
}
