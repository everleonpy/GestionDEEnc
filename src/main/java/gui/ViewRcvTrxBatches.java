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
import javax.swing.JComboBox;
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
import business.RcvTrxEbBatchesTM;
import business.SessionContext;
import business.UserAttributes;
import business.ViewRcvTrxBatchItemsCtrl;
import business.ViewRcvTrxBatchesCtrl;
import dao.ProblemaDatosException;
import pojo.ListOfValuesParameters;
import pojo.ListOfValuesSelection;

public class ViewRcvTrxBatches extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpHeader = null;
	private JPanel jpFilters = null;
	private JPanel jpCenter = null;
	private JScrollPane jsDocBatches = null;
	// Encabezado de formulario
	private JLabel jlVendorLogo = null;
	private JLabel jlOrgName;
	private JLabel jlUserName;
	// Barra de herramientas
	private JLabel jlAction1 = null;
	private JLabel jlAction2 = null;
	private JLabel jlAction3 = null;
	
	// bloque de filtro
	private JLabel jlBatchNumber;
	private JLabel jlTrxType;
	private JLabel jlFromDate;
	private JLabel jlToDate;
	private JTextField jtBatchNumber;
	private JComboBox<String> cbTrxType = null;
	private JTextField jtFromDate;
	private JTextField jtToDate;
	
	// Main data grid
	private JTable jaDocBatches;
	
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
	
	private ViewRcvTrxBatchesCtrl ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
		
	/**
	 * This is the default constructor
	 */
	public ViewRcvTrxBatches () {
		super();
		// esto se hace aqui solo en etapa de desarrollo y pruebas
		AppConfig.loadConfig();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new ViewRcvTrxBatchesCtrl();
	    this.ctrl.setEvent("SENDING");
		// build GUI
		initialize();
		try {		
			// initialize controller
			ApplicationMessage aMsg = ctrl.initForm();
			if (aMsg == null) {
				showContextInfo();
		        jtBatchNumber.requestFocusInWindow();
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
		this.setName("ViewRcvTrxBatches");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Consulta de Lotes de Documentos Mayoristas");
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
			tbtnWidth = 180;
			tbtnHeight = 55;
			xPos = 0;
			//
			jlAction1 = new JLabel();
			jlAction1.setSize(new Dimension(tbtnWidth, tbtnHeight));
			jlAction1.setLocation(new Point(xPos, 1));
			jlAction1.setText(" Consultar");
			jlAction1.setBackground(appLook.getMenuBg());
			jlAction1.setForeground(appLook.getMenuFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
		    	        String batchNumber = "";
		    	        long batchId = 0;
			        int rowIndex = -1;
			        if (jaDocBatches != null) {
				        rowIndex = jaDocBatches.getSelectedRow();
				        System.out.println("rowIndex: " + rowIndex);
			        }
		            if (rowIndex > -1) {
		            	    batchNumber = (String) jaDocBatches.getValueAt(rowIndex, 0);
		            	    batchId = (long) jaDocBatches.getValueAt(rowIndex, 8);
		            	    System.out.println("batchNumber: " + batchNumber);
    	        	            openItemsWindow( batchId, batchNumber );
		            } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe elegir el numero de lote cuyos detalles desea consultar");
			            x.setVisible(true);			    	        	
		            }
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
			jlAction2.setText(" Importar");
			jlAction2.setBackground(appLook.getMenuBg());
			jlAction2.setForeground(appLook.getMenuFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
		    	        String batchNumber = "";
		    	        long batchId = -1;
			        int rowIndex = -1;
			        if (jaDocBatches != null) {
				        rowIndex = jaDocBatches.getSelectedRow();
				        System.out.println("rowIndex: " + rowIndex);
			        }
		            if (rowIndex > -1) {
		        		    //"No. Lote", "Fecha Documentos", "Fecha Recepcion", "Cod. Respuesta", 
		                //"Respuesta", "Tiempo Proceso", "Cant. Items", "Verificado",
		                //"Identificador"
			            // el identificador del objeto a editar se encuentra en la columna 8
		            	    batchNumber = (String) jaDocBatches.getValueAt(rowIndex, 0);
		            	    batchId = (long) jaDocBatches.getValueAt(rowIndex, 8);
		            	    System.out.println("batchNumber: " + batchNumber);
		                ctrl.setSelectedBatchNo(batchNumber);
		    	            ApplicationMessage m = ctrl.querySingleBatch ( batchNumber );
		                //
		    	            if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
		    	        	        MessageWindow x = new MessageWindow(null, "Consulta de Lote de Documentos", m.getText());
		    	        	        openItemsWindow( batchId, batchNumber );
		    	            } else {
				            ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
				            x.setVisible(true);			    	        	
		    	            }
		            } else {
	    	                ApplicationMessage m = ctrl.queryBatchesList ( ctrl.getTrxType(), ctrl.getFromDate(), ctrl.getToDate() );
	                    //
	    	                if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
	    	        	            MessageWindow x = new MessageWindow(null, "Consulta de Lote de Documentos", m.getText());
					        x.setVisible(true);	
	    	                } else {
			                ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
			                x.setVisible(true);			    	        	
	    	                }
		            }
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
			jlAction3.setText(" Salir");
			jlAction3.setBackground(appLook.getMenuBg());
			jlAction3.setForeground(appLook.getMenuFg());
			jlAction3.setFont(appLook.getSmallFont());
			jlAction3.setOpaque(true);
			jlAction3.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction3.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    requestLeaving();
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
			jlBatchNumber = getFiltersLabel("Numero de Lote");
			jpFilters.add(jlBatchNumber, null);
			// segunda linea
			lineNum++;
			lbY = lbY + lbH;
			jlTrxType = getFiltersLabel("Tipo Transaccion");
			jpFilters.add(jlTrxType, null);
			// tercera linea
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
			jpFilters.add(getjtBatchNumber(), null);
			// segunda linea
			tfY = tfY + tfH;
			tfW = tfWidth * 2;
			jpFilters.add(getcbTrxType(), null);
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

	private JTextField getjtBatchNumber() {
		if (jtBatchNumber == null) {
			jtBatchNumber = getFiltersTextfield();
			jtBatchNumber.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterBatchNumber();
					if (aMsg == null) {
						cbTrxType.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtBatchNumber.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtBatchNumber;
	}	

	private JComboBox<String> getcbTrxType() {
		if (cbTrxType == null) {
			cbTrxType = getFiltersComboBox();
			cbTrxType.addItem("Factura");
			cbTrxType.addItem("Nota de Credito");
			cbTrxType.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//System.out.println("********" + e.isActionKey() + " - " + e.getKeyCode() + " - " + e.isAltDown() + " - " + e.isControlDown());
					//System.out.println("text2: " + jtProductCode.getText() + " - " + jtProductCode.getText().isEmpty());
					if (e.getKeyCode() == KeyEvent.VK_ENTER | e.getKeyCode() == KeyEvent.VK_TAB) {
						ApplicationMessage aMsg = enterTrxType();
						jtFromDate.requestFocusInWindow();
					}
				}
			});
		}
		return cbTrxType;
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
	
	private JComboBox getFiltersComboBox () {
		JComboBox t = new JComboBox();
		t.setSize(new Dimension(tfW, tfH));
		t.setLocation(new Point(tfX, tfY));
		t.setBackground(appLook.getBodyBg());
		t.setForeground(appLook.getBodyFg());
		t.setFont(appLook.getRegularFont());
		//jtCustomerSite.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
		//t.setBorder(lineBorder);
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

	private JScrollPane getjsDocBatches() {
		if (jsDocBatches == null) {
			jsDocBatches = new JScrollPane();
			jsDocBatches.setSize(new Dimension((centerWidth - 20), (centerHeight - (abtnHeight + 30))));
			jsDocBatches.setLocation(new Point(10, 10));
			getjaDocBatches ( );
			jsDocBatches.setViewportView(jaDocBatches);
		}
		return jsDocBatches;
	}	
	
	private void getjaDocBatches ( ) {
		jaDocBatches = new JTable(ctrl.getQueryTableModel());
		jaDocBatches.setCellSelectionEnabled(true);
		jaDocBatches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jaDocBatches.setRowHeight(30);
		jaDocBatches.setFont(appLook.getSmallFont());
		//{"No. Item", "No. Factura", "Fecha", "Importe", "RUC/No. Identidad", 
        //        "RUC", "Cliente", "CDC", "Identificador", "Caja", "Control"};
		for ( int i=0; i<jaDocBatches.getColumnCount(); i++) {
			TableColumn tc = jaDocBatches.getColumn(jaDocBatches.getColumnName(i));
			if ( i == 0 ) {
				// numero de grupo
				tc.setPreferredWidth(50);
			}
			if ( i == 1 ) {
				// cantidad de transacciones
				tc.setPreferredWidth(50);
			}
			if ( i == 2 ) {
				// cantidad de transacciones no enviadas
				tc.setPreferredWidth(50);
			}
		}
	}	
	
	private JTable getEmptyTable ( ) {
		RcvTrxEbBatchesTM tm = new RcvTrxEbBatchesTM ();
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
	private ApplicationMessage enterBatchNumber () {
		ApplicationMessage aMsg = null;
		String s = jtBatchNumber.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterBatchNumber(s);	
		} else {
			aMsg = ctrl.enterBatchNumber(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}

	public ApplicationMessage enterTrxType () {
		ApplicationMessage aMsg = null;
		String s = (String) cbTrxType.getSelectedItem();
		if (s.length() > 0) {
			aMsg = ctrl.enterTrxType(s);	
		} else {
			aMsg = ctrl.enterBatchNumber(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
		
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
						//showListOfValues ( "CAJAS", "Cajas POS", 0, null);
						//if (lovSelection != null) {
							//ctrl.enterCashId(lovSelection.getItemId());
							//jtCash.setText(lovSelection.getItemName());
							//jtCash.repaint();
							//jtFromDate.requestFocusInWindow();
						//}
					}
				}
			}
		}
	}
	
	private void openItemsWindow ( long batchId, String batchNumber ) {
		ViewRcvTrxBatchItemsCtrl ctrl = new ViewRcvTrxBatchItemsCtrl();
		ctrl.setBatchId(batchId);
		ctrl.setBatchNumber(batchNumber);
	    ViewRcvTrxBatchItems w = new ViewRcvTrxBatchItems( this, ctrl );
	    w.setVisible(true);			
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
		   jtBatchNumber.requestFocusInWindow();
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
		new ReceivablesMenu ();
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
    	    // debido a que no se realizan las validaciones cuando la navegacion es por mouse o por
    	    // la tecla Tab, se repiten las mismas en este punto antes de ejecutar la consulta
    	    ApplicationMessage aMsg = null;
    	    aMsg = enterBatchNumber();
		aMsg = enterTrxType();
		aMsg = enterFromDate();
	    if (aMsg != null) {
    	        if (aMsg.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
		        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
		        x.setVisible(true);    	    	    	
    	        }
        }
		aMsg = enterToDate();
	    if (aMsg != null) {
    	        if (aMsg.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
		        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
		        x.setVisible(true);    	    	    	
    	        }
        }
	    //ErrorMessageWindow x = new ErrorMessageWindow(null, "Ejecutando consulta");
	    //x.setVisible(true);  
    	    ctrl.loadQueryDataModel();
    	    ctrl.createQueryItemsTable();
		if (jsDocBatches == null) {
			System.out.println("jsTransactions es nulo...");
		    jpCenter.add(getjsDocBatches(), null);
		} else {
			System.out.println("jsTransactions no es nulo...");
		    getjaDocBatches ( );
		    jsDocBatches.setViewportView(jaDocBatches);
		    jpCenter.add(jsDocBatches, null);
		}
		jpCenter.repaint();
		jaDocBatches.requestFocusInWindow();
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMessage aMsg = SessionContext.getContextValues();
		//
		ViewRcvTrxBatches w = new ViewRcvTrxBatches();
		w.setVisible(true);
		w.repaint();
	}
    
}
