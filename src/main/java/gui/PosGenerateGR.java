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
import java.util.Iterator;

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
import business.ApplicationMessage;
import business.PosInvoicesTM;
import business.SendPosInvoicesController;
import business.SessionContext;
import business.TxXmlViewerController;
import business.UserAttributes;
import dao.ProblemaDatosException;
import pojo.CashRegister;
import pojo.ListOfValuesParameters;
import pojo.ListOfValuesSelection;
import pojo.PosInvoice;

public class PosGenerateGR extends JFrame {
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
	private JLabel jlCash;
	private JLabel jlFromDate;
	private JLabel jlToDate;
	private JTextField jtCash;
	private JButton jbLov1 = null;
	private JTextField jtFromDate;
	private JTextField jtToDate;
	
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
	
	private SendPosInvoicesController ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
	private ListOfValuesSelection lovSelection;
		
	/**
	 * This is the default constructor
	 */
	public PosGenerateGR () {
		super();
		// esto se hace aqui solo en etapa de desarrollo y pruebas
		AppConfig.loadConfig();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new SendPosInvoicesController();
	    this.ctrl.setEvent("RENDERING");
		// build GUI
		initialize();
		try {		
			// initialize controller
			ApplicationMessage aMsg = ctrl.initForm();
			if (aMsg == null) {
				showContextInfo();
		        jtCash.requestFocusInWindow();
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
		this.setName("PosGenerateGR");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Representaciones Graficas de Facturas POS");
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
			jlAction1.setText(" Generar KuDE");
			jlAction1.setBackground(appLook.getHeaderBg());
			jlAction1.setForeground(appLook.getHeaderFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    long invoiceId = 0;
			    	    long controlId = 0;
			    	    long cashId = 0;
			    	    String txNumber;
			    	    java.util.Date txDate;
			        int rowIndex = jaTransactions.getSelectedRow();
			        if (rowIndex >= 0) {
				        // el identificador del objeto a editar se encuentra en la columna 7
			            invoiceId = (Long) jaTransactions.getValueAt(rowIndex, 7);
			            cashId = (Long) jaTransactions.getValueAt(rowIndex, 8);
			            controlId = (Long) jaTransactions.getValueAt(rowIndex, 9);
			            txNumber = (String) jaTransactions.getValueAt(rowIndex, 1);
			            txDate = (java.util.Date) jaTransactions.getValueAt(rowIndex, 2);
			            ctrl.setSelectedTransactionId(invoiceId);
			    	        ApplicationMessage m = ctrl.genGraphicRepresent(invoiceId, controlId, cashId, txNumber, txDate);
			    	        if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
			    	        	    MessageWindow x = new MessageWindow(null, "KuDE de Facturas POS", m.getText());
							x.setVisible(true);			    	        	
			    	        } else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
					        x.setVisible(true);			    	        	
			    	        }
			        } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe elegir una factura para realizar el envio");
			            x.setVisible(true);
			        }
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
			jlAction2.setText(" Generar Todo");
			jlAction2.setBackground(appLook.getHeaderBg());
			jlAction2.setForeground(appLook.getHeaderFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    int counter = 0;
			    	    int errCounter = 0;
		    	        Iterator itr = ctrl.getTransactionsList().iterator();
		            while (itr.hasNext()) {
		        	        PosInvoice x = (PosInvoice) itr.next();
			    	        ApplicationMessage m = ctrl.genGraphicRepresent ( x.getInvoiceId(), 
			    	        		                                              x.getControlId(), 
			    	        		                                              x.getCashId(), 
			    	        		                                              x.getTxNumber(), 
			    	        		                                              x.getTxDate() );
			    	        if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
			    	        	    errCounter++;			    	        	
			    	        } 
		    	            counter++;
		            }
		            if (errCounter == 0) {
	        	            MessageWindow x = new MessageWindow(null, "Generacion de KuDE", "Todos los KuDE han sido generados");
			            x.setVisible(true);
		            } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, errCounter + " KuDE no han podido ser generados");
			            x.setVisible(true);		            	
		            }
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
			jlCash = getFiltersLabel("Caja");
			jpFilters.add(jlCash, null);
			// segunda linea
			lineNum++;
			lbY = lbY + lbH;
			jlFromDate = getFiltersLabel("Fecha Desde");
			jpFilters.add(jlFromDate, null);
			lbX = startX + lbW + tfW;
			jlToDate = getFiltersLabel("Fecha Hasta");
			jpFilters.add(jlToDate, null);
			/**
			 +----------------------------------------------------+
			 | region text fields placement                       |
			 +----------------------------------------------------+
			 */
			tfW = tfWidth * 3;
			tfX = startX + lbW + hGap;
			tfY = startY;
			lineNum = 0;
			// primera linea
			jpFilters.add(getjtCash(), null);
			tfX = tfX + tfW;
			jpFilters.add(getJbLov1());
			// segunda linea
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			tfW = tfWidth;
			jpFilters.add(getjtFromDate(), null);
			//
			tfW = tfWidth;
			// tener en cuenta aqui que el valor de lbW puede no ser conocido
			tfX = tfX + tfW + lbW;
			jpFilters.add(getjtToDate(), null);
			
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

	private JTextField getjtCash() {
		if (jtCash == null) {
			jtCash = getFiltersTextfield();
			jtCash.setName("CASH");
			jtCash.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// si el campo contiene un texto, realizar la busqueda por descripcion
					if (jtCash.getText().isEmpty() == false) {
						CashRegister s = ctrl.enterCashName(jtCash.getText());
						if ( s != null ) {
							// si el procedimiento de busqueda retorno una fila, pasar el control al sigiuente item
							jtFromDate.requestFocusInWindow();							
						} else {
							// si el procedimiento no encontro ninguna fila, ejecuta la lista de valores
							showListOfValues ( "CAJAS", "Cajas POS", 0, null);
							if (lovSelection != null) {
								ctrl.enterCashId(lovSelection.getItemId());
								jtCash.setText(lovSelection.getItemName());
								jtCash.repaint();
								jtFromDate.requestFocusInWindow();
							}
						}
					}
				}
			});
			jtCash.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtCash;
	}	
	
	private JButton getJbLov1() {
		if (jbLov1 == null) {
			jbLov1 = getLovButton ("jbLov1");
			jbLov1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showListOfValues ( "CAJAS", "Cajas POS", 0, null);
					if (lovSelection != null) {
						ctrl.enterCashId(lovSelection.getItemId());
						jtCash.setText(lovSelection.getItemName());
						jtCash.repaint();
						jtFromDate.requestFocusInWindow();
					}
				}
			});
		}
		return jbLov1;
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
						executeQuery();
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
		jaTransactions = new JTable(ctrl.getSendTableModel());
		jaTransactions.setCellSelectionEnabled(true);
		jaTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jaTransactions.setRowHeight(30);
		jaTransactions.setFont(appLook.getSmallFont());
		for ( int i=0; i<jaTransactions.getColumnCount(); i++) {
			TableColumn tc = jaTransactions.getColumn(jaTransactions.getColumnName(i));
			if ( i == 0 ) {
				// numero de item
				tc.setPreferredWidth(40);
			}
			if ( i == 1 ) {
				// numero de transaccion
				tc.setPreferredWidth(80);
			}
			if ( i == 2 ) {
				// fecha de transaccion
				tc.setPreferredWidth(80);
			}
			if ( i == 3 ) {
				// condicion de venta
				tc.setPreferredWidth(50);
			}
			if ( i == 4 ) {
				// tipo de transaccion
				tc.setPreferredWidth(100);
			}
			if ( i == 5 ) {
				// importe transaccion
				tc.setPreferredWidth(80);
			}
			if ( i == 6 ) {
				// numero de identificacion del cliente
				tc.setPreferredWidth(50);
			}
			if ( i == 7 ) {
				// numero de contribuyente
				tc.setPreferredWidth(50);
			}
			if ( i == 8 ) {
				// denominacion del cliente
				tc.setPreferredWidth(150);
			}
			if ( i == 9 ) {
				// identificador de transaccion
				tc.setPreferredWidth(50);
			}
		}
	}	
	
	private JTable getEmptyTable ( ) {
		PosInvoicesTM tm = new PosInvoicesTM ();
		//
		tm = ctrl.createSendEmptyTable();
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
					if (focusOwner.getName().equalsIgnoreCase("CAJA")) {
						showListOfValues ( "CAJAS", "Cajas POS", 0, null);
						if (lovSelection != null) {
							ctrl.enterCashId(lovSelection.getItemId());
							jtCash.setText(lovSelection.getItemName());
							jtCash.repaint();
							jtFromDate.requestFocusInWindow();
						}
					}
				}
			}
		}
	}

	// procedimiento generico para ejecucion de listas de valores
	private void showListOfValues ( String entity, String title, long refId, String searchString) {
		ListOfValuesParameters p = new ListOfValuesParameters();
		p.setEntity(entity);
		p.setTitle(title);
		p.setAutoDisplay(true);
		p.setUnitId(UserAttributes.userUnit.getIDENTIFIER());
		p.setReferenceId(refId);
		p.setSearchString(searchString);
		//
		try {
			lovSelection = buildLov(p);
			System.out.println(entity + ": " + lovSelection.getItemCode() + " - " + lovSelection.getItemName() + " - " + lovSelection.getItemId());
		} catch (Exception e) {
			e.printStackTrace();
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
		   jtCash.requestFocusInWindow();
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
    	    ctrl.loadRenderDataModel();
    	    ctrl.createSendItemsTable();
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
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMessage aMsg = SessionContext.getContextValues();
		//
		PosGenerateGR w = new PosGenerateGR();
		w.setVisible(true);
		w.repaint();
	}
    
	
}