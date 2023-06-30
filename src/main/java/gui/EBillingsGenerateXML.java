package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import business.AppConfig;
import business.EBTransactionsController;
import business.RcvCustomersTrxTM;
import business.TxXmlViewerController;
import business.UserAttributes;
import dao.ProblemaDatosException;
import pojo.ListOfValuesParameters;
import pojo.ListOfValuesSelection;
import business.ApplicationMessage;

public class EBillingsGenerateXML extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpHeader = null;
	private JPanel jpFilters = null;
	private JPanel jpCenter = null;
	private JScrollPane jsTransactions = null;
	// Encabezado de formulario
	private JLabel jlVendorLogo = null;
	private JLabel jlOrgName;
	private JLabel jlUserName;
	// Barra de herramientas
	private JLabel jlAction1 = null;
	private JLabel jlAction2 = null;
	private JLabel jlAction3 = null;
	
	// bloque de filtro
	private JLabel jlCustomerCode;
	private JLabel jlCustomerName;
	private JLabel jlCustomerSite;
	private JLabel jlFromDate;
	private JLabel jlToDate;
	private JLabel jlTransactionNo;
	private JTextField jtCustomerCode;
	private JButton jbLov1 = null;
	private JButton jbLov2 = null;
	private JTextField jtCustomerSite;
	private JTextField jtFromDate;
	private JTextField jtToDate;
	private JTextField jtTransactionNo;
	// Main data grid
	private JTable jaTransactions;
	
	// look and feel
	private AppLookAndFeel appLook;
	private OvalBorder lineBorder = new OvalBorder();

	private int windowWidth;
	private int windowHeight;
	// header panel
	private int headerWidth;
	private int headerHeight;
	private int headerX;
	private int headerY;
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
	
	private EBTransactionsController ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
	private ListOfValuesSelection lovSelection;
		
	/**
	 * This is the default constructor
	 */
	public EBillingsGenerateXML () {
		super();
		// esto se hace aqui solo en etapa de desarrollo y pruebas
		AppConfig.loadConfig();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new EBTransactionsController();
		// build GUI
		initialize();
		try {		
			// initialize controller
			ApplicationMessage aMsg = ctrl.initForm();
			if (aMsg == null) {
				showContextInfo();
		        jtCustomerCode.requestFocusInWindow();
			} else {
				endProgram ();
			}
		} catch (Exception e) {		
			e.printStackTrace();
		}
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
		windowWidth = screenSize.width;
		windowHeight = screenSize.height;
		this.setSize((int)windowWidth, (int)windowHeight);
		this.setLocation(new Point(0, 0));
		this.setName("ElectronicTransactionsHome");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Generacion de Archivo XML de Facturas Electronicas");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				requestLeaving ();
			}
		});
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			//
			labelWidth = getRelativeWidth (windowWidth, 12.0);
			labelHeight = getRelativeHeight (windowHeight, 4.0);	
			tfWidth = getRelativeWidth (windowWidth, 12.0);
			tfHeight = getRelativeHeight (windowHeight, 4.0);	
			//
			headerWidth = windowWidth;
			headerHeight = getRelativeHeight (windowHeight, 6.0);
			headerX = 0;
			headerY = 0;
			this.add(getJpHeader(), null);
			//
			toolbarX = 0;
			toolbarY = headerY + (int)headerHeight;
			toolbarWidth = windowWidth;
			toolbarHeight = 55;
			this.add(getJpToolbar());
			//
			filtersWidth = windowWidth;
			filtersHeight = getRelativeHeight (windowHeight, 19.0);
			filtersX = 0;
			filtersY = toolbarY + (int)toolbarHeight;
			this.add(getJpFilters(), null);
			//
			centerWidth = windowWidth;
			centerHeight = windowHeight - (headerHeight + toolbarHeight + filtersHeight + 50);
			centerX = 0;
			centerY = headerHeight + toolbarHeight + filtersHeight;
			this.add(getJpCenter(), null);
		}
		return jContentPane;
	}	
	
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Region Header                                | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */
	private JPanel getJpHeader() {
		if (jpHeader == null) {
			jlVendorLogo = new JLabel();
			int h = headerHeight - 5;
			int w = h * 2;
			jlVendorLogo.setSize(new Dimension(w, h));
			jlVendorLogo.setLocation(new Point(10, 0));
			String archivoImg = "softpoint-avanza.jpg";
			h = h - 20;
			ImageIcon i = getScaledIcon(archivoImg, w, h);
			jlVendorLogo.setIcon(i);
			//
			jlOrgName = new JLabel();
			h = headerWidth / 4;
			int x = windowWidth - h;
			int y = 0;
			jlOrgName.setSize(new Dimension(h, labelHeight));
			jlOrgName.setLocation(new Point(x, y));
			jlOrgName.setText("Organisation");
			jlOrgName.setForeground(appLook.getBodyFg());
			jlOrgName.setFont(appLook.getSmallFont());
			jlOrgName.setHorizontalAlignment(SwingConstants.RIGHT);
			//			
			jlUserName = new JLabel();
			y = y + labelHeight;
			jlUserName.setSize(new Dimension(h, labelHeight));
			jlUserName.setLocation(new Point(x, (headerHeight - labelHeight)));
			jlUserName.setText("User");
			jlUserName.setForeground(appLook.getBodyFg());
			jlUserName.setFont(appLook.getSmallFont());
			jlUserName.setHorizontalAlignment(SwingConstants.RIGHT);
			//			
			jpHeader = new JPanel();
			jpHeader.setLayout(null);
			//System.out.println("jpHeader x: " + headerX + " y:" + headerY + " w: " + headerWidth + " h: " + headerHeight);
			jpHeader.setSize(new Dimension(headerWidth, headerHeight));
			jpHeader.setLocation(new Point(headerX, headerY));
			//jpHeader.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jpHeader.setBackground(appLook.getBodyBg());
			jpHeader.add(jlVendorLogo, null);
			jpHeader.add(jlOrgName, null);		
			jpHeader.add(jlUserName, null);		
		}
		return jpHeader;
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
			tbtnWidth = 100;
			tbtnHeight = 55;
			xPos = 0;
			//
			jlAction1 = new JLabel();
			jlAction1.setSize(new Dimension(tbtnWidth, tbtnHeight));
			jlAction1.setLocation(new Point(xPos, 1));
			jlAction1.setText(" Ver Items");
			jlAction1.setBackground(appLook.getHeaderBg());
			jlAction1.setForeground(appLook.getHeaderFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	//openItemsWindow();
			        ErrorMessageWindow x = new ErrorMessageWindow(null, "Aqui se debe abrir la ventana de los items");
			        x.setVisible(true);
			    }  
			    public void mouseEntered (MouseEvent evt) {
					jlAction1.setBackground(Color.black);
				    jlAction1.setForeground(Color.white);
				    jlAction1.repaint();
			    }
			    public void mouseExited (MouseEvent evt) {
					jlAction1.setBackground(appLook.getHeaderBg());
					jlAction1.setForeground(appLook.getHeaderFg());
				    jlAction1.repaint();
			    }
			});
			//
			jlAction2 = new JLabel();
			jlAction2.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction2.setLocation(new Point(xPos, 1));
			jlAction2.setText(" Ver XML");
			jlAction2.setBackground(appLook.getHeaderBg());
			jlAction2.setForeground(appLook.getHeaderFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	viewXmlWindow();
			    }  
			    public void mouseEntered (MouseEvent evt) {
					jlAction2.setBackground(Color.black);
				    jlAction2.setForeground(Color.white);
				    jlAction2.repaint();
			    }
			    public void mouseExited (MouseEvent evt) {
					jlAction2.setBackground(appLook.getHeaderBg());
					jlAction2.setForeground(appLook.getHeaderFg());
				    jlAction2.repaint();
			    }
			});
			//
			jlAction3 = new JLabel();
			jlAction3.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction3.setLocation(new Point(xPos, 1));
			jlAction3.setText(" Salir");
			jlAction3.setBackground(appLook.getHeaderBg());
			jlAction3.setForeground(appLook.getHeaderFg());
			jlAction3.setFont(appLook.getSmallFont());
			jlAction3.setOpaque(true);
			jlAction3.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction3.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	requestLeaving();
			    } 
			    public void mouseEntered (MouseEvent evt) {
					jlAction3.setBackground(Color.black);
				    jlAction3.setForeground(Color.white);
				    jlAction3.repaint();
			    }
			    public void mouseExited (MouseEvent evt) {
					jlAction3.setBackground(appLook.getHeaderBg());
					jlAction3.setForeground(appLook.getHeaderFg());
				    jlAction3.repaint();
			    }
			});
			//
			jpToolbar.add(jlAction1, null);
			jpToolbar.add(jlAction2, null);
			jpToolbar.add(jlAction3, null);
		}
		return jpToolbar;
	}
	
	/**
	 +----------------------------------------------+	
	 |                                              |
	 |                                              |
	 | Region Filters                               | 	
	 |                                              |
	 |                                              |
	 +----------------------------------------------+
	 */
	private JPanel getJpFilters() {
		if (jpFilters == null) {
			int hGap = 5;
			int startX = 10;
			int startY = 10;
			int lineNum = 0;
			jpFilters = new JPanel();
			jpFilters.setLayout(null);
			jpFilters.setSize(new Dimension(filtersWidth, filtersHeight));
			//System.out.println("jpFilters x: " + filtersX + " y:" + filtersY + " w: " + filtersWidth + " h: " + filtersHeight);
			jpFilters.setPreferredSize(new Dimension(filtersWidth, filtersHeight));
			jpFilters.setLocation(new Point(filtersX, filtersY));
			jpFilters.setBackground(appLook.getBodyBg());
			jpFilters.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			//
			lbW = labelWidth;
			lbH = labelHeight;
			tfW = tfWidth;
			tfH = tfHeight;
			/**
			 +----------------------------------------------------+
			 | region labels placement                            |
			 +----------------------------------------------------+
			 */
			lbX = startX;
			lbY = startY;
			// primera linea
			jlCustomerCode = getFiltersLabel("Cod. Cliente");
			jpFilters.add(jlCustomerCode, null);
			// segunda linea
			lineNum++;
			lbY = lbY + lbH;
			jlCustomerSite = getFiltersLabel("Sucursal Cliente");
			jpFilters.add(jlCustomerSite, null);
			// tercera linea
			lineNum++;
			lbY = lbY + lbH;
			jlFromDate = getFiltersLabel("Fecha Desde");
			jpFilters.add(jlFromDate, null);
			lbX = startX + lbW + tfW;
			jlToDate = getFiltersLabel("Fecha Hasta");
			jpFilters.add(jlToDate, null);
			lbX = startX;
			// cuarta linea
			lineNum++;
			lbY = lbY + lbH;
			jlTransactionNo = getFiltersLabel("No. Transaccion");
			jpFilters.add(jlTransactionNo, null);
			/**
			 +----------------------------------------------------+
			 | region text fields placement                       |
			 +----------------------------------------------------+
			 */
			tfX = startX + lbW + hGap;
			tfY = startY;
			lineNum = 0;
			// primera linea
			jpFilters.add(getjtCustomerCode(), null);
			tfX = tfX + tfW;
			jpFilters.add(getJbLov1());
			//
			tfW = tfWidth * 3;
			tfX = tfX + tfH;
			jlCustomerName = getFiltersDisplay("");
			jpFilters.add(jlCustomerName, null);			
			// segunda linea
			tfW = tfWidth * 3;
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			jpFilters.add(getjtCustomerSite(), null);
			tfX = tfX + tfW;
			jpFilters.add(getJbLov2());
			// tercera linea
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			tfW = tfWidth;
			jpFilters.add(getjtFromDate(), null);
			//
			tfW = tfWidth;
			// tener en cuenta aqui que el valor de lbW puede no ser conocido
			tfX = tfX + tfW + lbW;
			jpFilters.add(getjtToDate(), null);
			// cuarta linea
			tfW = tfWidth * 3;
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			jpFilters.add(getjtTransactionNo(), null);
		}
		return jpFilters;
	}
	
	private JLabel getFiltersLabel ( String text ) {
		JLabel l = new JLabel();
		l.setHorizontalTextPosition(SwingConstants.RIGHT);
		l.setHorizontalAlignment(SwingConstants.RIGHT);
		l.setSize(new Dimension(lbW, lbH));
		l.setLocation(new Point(lbX, lbY));
		l.setForeground(appLook.getBodyFg());
		l.setFont(appLook.getSmallFont());
		l.setText(text);
		return l;
	}

	private JTextField getjtCustomerCode() {
		if (jtCustomerCode == null) {
			jtCustomerCode = getFiltersTextfield();
			jtCustomerCode.setName("CUSTOMER_CODE");
			jtCustomerCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterCustomerCode(jtCustomerCode.getText());
					if (aMsg == null) {
						jlCustomerName.setText(ctrl.getCustomer().getName());
						jlCustomerName.repaint();						
						jtCustomerSite.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtCustomerCode.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtCustomerCode;
	}	
	
	private JButton getJbLov1() {
		if (jbLov1 == null) {
			jbLov1 = getLovButton ("jbLov1");
			jbLov1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showCustomerCodeLov(null);
					if (lovSelection != null) {
					    ApplicationMessage aMsg = enterCustomerCode(lovSelection.getItemCode());
						if (aMsg == null) {
							jlCustomerName.setText(ctrl.getCustomer().getName());
							jlCustomerName.repaint();						
							jtCustomerSite.requestFocusInWindow();
						} else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
					        x.setVisible(true);
						}		
					}
				}
			});
		}
		return jbLov1;
	}	
	
	private JTextField getjtCustomerSite() {
		if (jtCustomerSite == null) {
			jtCustomerSite = getFiltersTextfield();
			jtCustomerSite.setName("CUSTOMER_SITE");
			jtCustomerSite.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterCustomerSiteCode(jtCustomerSite.getText());
					if (aMsg == null) {
						jtFromDate.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtCustomerSite.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtCustomerSite;
	}	
	
	private JButton getJbLov2() {
		if (jbLov2 == null) {
			jbLov2 = getLovButton ("jbLov2");
			jbLov2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showCustomerSiteLov(null);
					if (lovSelection != null) {
					    ApplicationMessage aMsg = enterCustomerSiteId(lovSelection.getItemId());
						if (aMsg == null) {
							jtCustomerSite.setText(ctrl.getCustomerSite().getName());
							jtCustomerSite.repaint();
							jtFromDate.requestFocusInWindow();
						} else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
					        x.setVisible(true);
						}		
					}
				}
			});
		}
		return jbLov2;
	}	
	
	private JTextField getjtFromDate() {
		if (jtFromDate == null) {
			jtFromDate = getFiltersTextfield();
			jtFromDate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterFromDate();
					if (aMsg == null) {
						if (ctrl.getFromDate() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							String d = sdf.format(ctrl.getFromDate());
					        jtFromDate.setText(d);
						} else {
						    jtFromDate.setText("");							
						}
						jtFromDate.repaint();
						jtToDate.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtFromDate.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtFromDate;
	}	

	private JTextField getjtToDate() {
		if (jtToDate == null) {
			jtToDate = getFiltersTextfield();
			jtToDate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterToDate();
					if (aMsg == null) {
						if (ctrl.getToDate() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							String d = sdf.format(ctrl.getToDate());
					        jtToDate.setText(d);
						} else {
						    jtToDate.setText("");							
						}
						jtToDate.repaint();
						jtTransactionNo.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtToDate.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtToDate;
	}	

	private JTextField getjtTransactionNo() {
		if (jtTransactionNo == null) {
			jtTransactionNo = getFiltersTextfield();
			jtTransactionNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterTransactionNo();
					if (aMsg == null) {
					    executeQuery();
				    } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
			            x.setVisible(true);
				    }
				}
			});
		}
		return jtTransactionNo;
	}	
	
	private JTextField getFiltersTextfield () {
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
	
	private JLabel getFiltersDisplay ( String text ) {
		JLabel l = new JLabel();
		l.setHorizontalTextPosition(SwingConstants.LEFT);
		l.setHorizontalAlignment(SwingConstants.LEFT);
		l.setSize(new Dimension(tfW, tfH));
		l.setLocation(new Point(tfX, tfY));
		l.setForeground(appLook.getBodyFg());
		l.setFont(appLook.getRegularFont());
		l.setText(text);
		l.setBorder(lineBorder);
		return l;
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
			jpCenter.add(getEmptyTable(), null);
		}
		return jpCenter;
	}	

	private JScrollPane getjsTransactions() {
		if (jsTransactions == null) {
			jsTransactions = new JScrollPane();
			jsTransactions.setSize(new Dimension((centerWidth - 20), (centerHeight - (abtnHeight + 30))));
			jsTransactions.setLocation(new Point(10, 10));
			getjaTransactions ( );
			jsTransactions.setViewportView(jaTransactions);
		}
		return jsTransactions;
	}	
	
	private void getjaTransactions ( ) {
		jaTransactions = new JTable(ctrl.getTableModel());
		jaTransactions.setCellSelectionEnabled(true);
		jaTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jaTransactions.setRowHeight(30);
		jaTransactions.setFont(appLook.getSmallFont());
		for ( int i=0; i<jaTransactions.getColumnCount(); i++) {
			TableColumn tc = jaTransactions.getColumn(jaTransactions.getColumnName(i));
			if ( i == 0 | i == 2) {
				tc.setPreferredWidth(200);
				//tc.setCellRenderer(new ConfigTablaItemsVenta());
			} else {
				tc.setPreferredWidth(50);
				//tc.setCellRenderer(new ConfigTablaItemsVenta());
			}
		}
	}	
	
	private JTable getEmptyTable ( ) {
		RcvCustomersTrxTM tm = new RcvCustomersTrxTM ();
		//
		tm = ctrl.createHeaderEmptyTable();
		JTable table = new JTable(tm);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);
		for ( int i=0; i<table.getColumnCount(); i++) {
			TableColumn tc = table.getColumn(table.getColumnName(i));
			if ( i == 0 ) {
				tc.setPreferredWidth(150);
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
	private JButton getLovButton (String name) {
		ImageIcon i = getScaledIcon("horiz-menub.png", 36, 36);
		JButton b = new JButton(i);
		//JButton b = new JButton();
		//b.setText("...");
		b.setName(name);
		b.setActionCommand("LOV");
		b.setSize(new Dimension(tfH, tfH));
		b.setLocation(new Point(tfX, tfY));
		b.setBackground(appLook.getBodyBg());
		//b.setForeground(appLook.getToolbarButtonFg());
		//b.setOpaque(true);
		//b.setBorder(lineBorder);
		b.setToolTipText("Mostrar lista de valores");
		return b;
	}	
	
	private ApplicationMessage enterCustomerCode (String code) {
		ApplicationMessage aMsg = null;
		if (code != null) {
		    if (code.length() > 0) {
			    aMsg = ctrl.enterCustomerCode(code);	
		    } else {
			    aMsg = ctrl.enterCustomerCode(null);	
		    }
		} else {
		    aMsg = ctrl.enterCustomerCode(null);	
	    }
		return aMsg;
	}
	
	private ApplicationMessage enterCustomerId (long id) {
		ApplicationMessage aMsg = ctrl.enterCustomerId(id);	
		return aMsg;
	}

	private ApplicationMessage enterCustomerSiteCode (String code) {
		ApplicationMessage aMsg = null;
		if (code != null) {
		    if (code.length() > 0) {
			    aMsg = ctrl.enterCustomerSiteCode(code);	
		    } else {
			    aMsg = ctrl.enterCustomerSiteCode(null);	
		    }
		} else {
		    aMsg = ctrl.enterCustomerSiteCode(null);	
	    }
		return aMsg;
	}
	
	private ApplicationMessage enterCustomerSiteId (long id) {
		ApplicationMessage aMsg = ctrl.enterCustomerSiteId(id);	
		return aMsg;
	}

	private ApplicationMessage enterFromDate () {
		ApplicationMessage aMsg = null;
		String s = jtFromDate.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterFromDate(s);	
		} else {
			aMsg = ctrl.enterFromDate(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}
	
	private ApplicationMessage enterToDate () {
		ApplicationMessage aMsg = null;
		String s = jtToDate.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterToDate(s);	
		} else {
			aMsg = ctrl.enterToDate(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
	        return aMsg;
		}																	
	}
	
	private ApplicationMessage enterTransactionNo () {
		ApplicationMessage aMsg = null;
		String s = jtTransactionNo.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterReceivingNo(s);	
		} else {
			aMsg = ctrl.enterReceivingNo(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
	        return aMsg;
		}												
	}
		
	private void dataEntryKeyPressed (java.awt.event.KeyEvent e) {
		//System.out.println("********" + e.getKeyCode() + " - " + e.isAltDown());
		//System.out.println("text2: " + jtCustomerCode.getText() + " - " + jtCustomerCode.getText().isEmpty());
        if (e.isAltDown() == true) {
            if (e.getKeyCode() == KeyEvent.VK_X) {
     		   endProgram();
            }
        } else {
			if (e.getKeyCode() == KeyEvent.VK_F9) {
				Component focusOwner = FocusManager.getCurrentManager().getFocusOwner();
				if (focusOwner instanceof JTextField) {
					//System.out.println("...textField: " + focusOwner.getName() + " tipo: " + focusOwner.getClass());
					if (focusOwner.getName().equalsIgnoreCase("CUSTOMER_CODE")) {
						showCustomerCodeLov(null);
					}
					if (focusOwner.getName().equalsIgnoreCase("CUSTOMER_SITE")) {
						showCustomerSiteLov(null);
					}
				}
			}
		}
	}

	private void showCustomerCodeLov (String searchString) {
		try {
			ListOfValuesParameters p = new ListOfValuesParameters();
			p.setEntity("CLIENTES");
			p.setTitle("Clientes");
			p.setAutoDisplay(true);
			p.setUnitId(UserAttributes.userUnit.getIDENTIFIER());
			p.setReferenceId(0);
			p.setSearchString(searchString);
			//
			lovSelection = buildLov(p);
			System.out.println(lovSelection.getItemCode() + " - " + lovSelection.getItemName() + " - " + lovSelection.getItemId());
			jtCustomerCode.setText(lovSelection.getItemCode());
			jtCustomerCode.requestFocusInWindow();
		} catch (ProblemaDatosException e1) {
			e1.printStackTrace();
			ErrorMessageWindow x = new ErrorMessageWindow(null, e1.getMessage());
			x.setVisible(true);
			jtCustomerCode.requestFocusInWindow();
			jtCustomerCode.selectAll();
		} catch (Exception e1) {
			e1.printStackTrace();
			ErrorMessageWindow x = new ErrorMessageWindow(null, e1.getMessage());
			x.setVisible(true);
			jtCustomerCode.requestFocusInWindow();
			jtCustomerCode.selectAll();
		}								
	}
	
	private void showCustomerSiteLov (String searchString) {
		try {
			ListOfValuesParameters p = new ListOfValuesParameters();
			p.setEntity("SITIOS-CLIENTE");
			p.setTitle("Sitios Cliente");
			p.setAutoDisplay(true);
			p.setUnitId(UserAttributes.userUnit.getIDENTIFIER());
			p.setReferenceId(ctrl.getCustomer().getIdentifier());
			p.setSearchString(searchString);
			//System.out.println(p.getUnitId() + " - " + p.getReferenceId() + " - " + searchString);
			//
			lovSelection = buildLov(p);
			jtCustomerSite.setText(lovSelection.getItemName());
			jtCustomerSite.requestFocusInWindow();
		} catch (ProblemaDatosException e1) {
			e1.printStackTrace();
			ErrorMessageWindow x = new ErrorMessageWindow(null, e1.getMessage());
			x.setVisible(true);
			jtCustomerSite.requestFocusInWindow();
			jtCustomerSite.selectAll();
		} catch (Exception e1) {
			e1.printStackTrace();
			ErrorMessageWindow x = new ErrorMessageWindow(null, e1.getMessage());
			x.setVisible(true);
			jtCustomerSite.requestFocusInWindow();
			jtCustomerSite.selectAll();
		}								
	}
	
	private void clearAll () {
    }
    
    private void reInitGUI () {
    	clearAll ();
    	repaint();
    	
    }
    
	public ListOfValuesSelection buildLov ( ListOfValuesParameters p ) throws ProblemaDatosException, Exception {
		ListOfValuesWindow ldv = new ListOfValuesWindow (p);
		ListOfValuesSelection s = new ListOfValuesSelection();
		s.setItemId(ldv.getIdentifierValue());
		s.setItemCode(ldv.getCodeValue());
		s.setItemName(ldv.getDescriptionValue());
		return s;
	}
	
    private void requestLeaving() {
	   int i = JOptionPane.showConfirmDialog(null, "Desea finalizar el programa? ","Finalizar - AvanzaTx",JOptionPane.YES_NO_OPTION);	   
	   if ( i == JOptionPane.YES_OPTION ) {
		   endProgram();
	   } else {
		   jtCustomerCode.requestFocusInWindow();
	   }
    }
	
    private void requestDeleting() {
	   int i = JOptionPane.showConfirmDialog(null, "Desea eliminar este registro? ","Eliminar - AvanzaTx",JOptionPane.YES_NO_OPTION);	   
	   if ( i == JOptionPane.YES_OPTION ) {
		   deleteRecord();
	   } else {
		   jtCustomerCode.requestFocusInWindow();
	   }
    }

    /*
	private void openAddWindow () {
	    PurchasingsSheetsHeaderController c = new PurchasingsSheetsHeaderController();
	    // set action for editing form
	    c.setAppAction("ADD");
	    // default values from home form parameters
	    c.setSheet(null);
	    c.setCustomer(ctrl.getCustomer());
	    c.setCustomerSite(ctrl.getCustomerSite());
    	PurchasingsSheetsHeaderWindow w = new PurchasingsSheetsHeaderWindow(this, c);
		w.setVisible(true);
	}
    */
    
    /*
	private void openItemsWindow () {
		boolean edit = true;
		long transactionId = 0;
	    try {
	        int rowIndex = jaTransactions.getSelectedRow();
	        //int colIndex = jaReceivings.getSelectedColumn();
	        if (rowIndex >= 0) {
	            // verificacion de condiciones necesarias para la edicion
	            String objectStatus = (String) jaTransactions.getValueAt(rowIndex, 5);
	            if (objectStatus.equalsIgnoreCase("INGRESADO") == false) {
	        	    edit = false;
				    ErrorMessageWindow x = new ErrorMessageWindow(null, "Transaccion se encuentra en estado " + objectStatus);
				    x.setVisible(true);			    	
	            }
	            if (edit == true) {
		            // el identificador del objeto a editar se encuentra en la columna 6
	            	transactionId = (Long) jaTransactions.getValueAt(rowIndex, 6);
	    	        if (transactionId != 0) {
	    	        	ctrl.getSheetData(transactionId);
			            if (ctrl.getSelectedTransaction() != null) {
			    	        //System.out.println("seleccionando recepcion: " + ctrl.getSelectedReceiving().getIDENTIFIER());
			    	        InvReceivingsItemsController c = new InvReceivingsItemsController();
			    	        c.setTransaction(ctrl.getSelectedTransaction());
			    	        // aqui hay que cargar la recepcion seleccionada en el controlador
				            InvReceivingsItemsWindow w = new InvReceivingsItemsWindow(this, c);
					        w.setVisible(true);
			            } else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, "No ha seleccionado ninguna recepcion");
					        x.setVisible(true);			    	
			            }
	    	        } else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, "No ha seleccionado ninguna recepcion");
				        x.setVisible(true);
	    	        }
	    	    }
	        }
	    } catch (ArrayIndexOutOfBoundsException e) {
			ErrorMessageWindow x = new ErrorMessageWindow(null, "No hay recepciones que cumplan las condiciones de consulta especificadas");
			x.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
    
	private void finalizeReceiving () {
		ApplicationMessage aMsg = null;
        int rowIndex = jaTransactions.getSelectedRow();
        //int colIndex = jaReceivings.getSelectedColumn();
        if (rowIndex >= 0) {
	        // el identificador del objeto a editar se encuentra en la columna 6
            long receivingId = (Long) jaTransactions.getValueAt(rowIndex, 6);
    	    ctrl.setSelectedTransactionId(receivingId);
		    //					
	        int i = JOptionPane.showConfirmDialog(null, "Desea dar por finalizado el pesaje? ","Finalizar - AvanzaTx",JOptionPane.YES_NO_OPTION);	   
            if ( i == JOptionPane.YES_OPTION ) {
		        //aMsg = ctrl.finalizeReceiving(ctrl.getSelectedTransactionId());
		        if (aMsg.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
	                ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
	                x.setVisible(true);										
		        } else {
	                MessageWindow x = new MessageWindow(null, "Finalizar Registro", aMsg.getText());
	                x.setVisible(true);	
	        	    ctrl.loadDataModel();
	        	    ctrl.createItemsTable();
	        	    jsTransactions.repaint();
		        }
		    } else {
		    	jaTransactions.requestFocusInWindow();
		    }
        }
	}

	private void viewXmlWindow () {
        TxXmlViewerController c = new TxXmlViewerController();
        int rowIndex = jaTransactions.getSelectedRow();
        //int colIndex = jaReceivings.getSelectedColumn();
        if (rowIndex >= 0) {
	        // el identificador del objeto a editar se encuentra en la columna 6
            long transactionId = (Long) jaTransactions.getValueAt(rowIndex, 6);
    	    c.setTransactionId(transactionId);
            TxXmlViewerWindow w = new TxXmlViewerWindow(this, c);
            w.setVisible(true);
        }
	}
	
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
	
	private void showContextInfo () {
		jlOrgName.setText(UserAttributes.userOrg.getNAME());
		jlUserName.setText(UserAttributes.user.getFULL_NAME());
		//jlSiteName.setText(UserAttributes.userSite.getNAME());
		jpHeader.repaint();
	}
	
	public ImageIcon getScaledIcon (String iconName, int width, int height) {
		//ImageIcon icon = new ImageIcon(iconName);
		ImageIcon icon = new ImageIcon(getClass().getResource(iconName));
		Image img = icon.getImage();  
		Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);  
		ImageIcon newIcon = new ImageIcon(newimg);
		return newIcon;
	}	   
    
    private void endProgram () {
		this.dispose();
		new PosMenu ();
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
	    //ErrorMessageWindow x = new ErrorMessageWindow(null, "Ejecutando consulta");
	    //x.setVisible(true);  
    	    ctrl.loadDataModel();
    	    ctrl.createItemsTable();
		if (jsTransactions == null) {
			System.out.println("jsReceivings es nulo...");
		    jpCenter.add(getjsTransactions(), null);
		} else {
			System.out.println("jsReceivings no es nulo...");
		    getjaTransactions ( );
		    jsTransactions.setViewportView(jaTransactions);
		    jpCenter.add(jsTransactions, null);
		}
		jpCenter.repaint();
		jaTransactions.requestFocusInWindow();
    }
    
    private void deleteRecord () {
    	    System.out.println("eliminando registro...");    	
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String port = args[0];
		EBillingsGenerateXML w = new EBillingsGenerateXML();
		w.setVisible(true);
		w.repaint();
	}
    
}
