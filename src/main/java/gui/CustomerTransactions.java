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

import com.roshka.sifen.core.beans.DocumentoElectronico;

import business.AppConfig;
import business.ApplicationMessage;
import business.PosInvoicesTM;
import business.SendPosInvoicesControllerBeta;
import business.SessionContext;
import business.UserAttributes;
import business.ViewElectronicDocumentCtrl;
import dao.ProblemaDatosException;
import pojo.GenericStringsList;
import pojo.ListOfValuesParameters;
import pojo.ListOfValuesSelection;

public class CustomerTransactions extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpFilters = null;
	private JPanel jpGrid = null;
	private JPanel jpFooter = null;

	// Header components
	private JLabel jlVendorLogo = null;
	private JLabel jlOrgName;
	private JLabel jlUserName;
	// Toolbar components
	private JLabel jlAction1 = null;
	private JLabel jlAction2 = null;
	private JLabel jlAction3 = null;
	private JLabel jlAction4 = null;
	// Filter components
	private JLabel jlCustomer;
    private JTextField jtCustomer;
    private JButton jbCustLov;
	private JLabel jlTaxNumber;
    private JTextField jtTaxNumber;
	private JLabel jlFromDate;
    private JTextField jtFromDate;
	private JLabel jlToDate;
    private JTextField jtToDate;
	private JLabel jlTxNumber;
    private JTextField jtTxNumber;
    // Data grid components
	private JScrollPane jsDataGrid = null;
	private JTable jaDataGrid;


	private JPanel jpHeader = null;
	private JPanel jpCenter = null;
	private JScrollPane jsTrxGroups = null;
	
	// bloque de filtro
	private JLabel jlDate;
	private JLabel jlTimeFrom;
	private JLabel jlTimeTo;
	private JLabel jlFromGroup;
	private JLabel jlToGroup;
	private JTextField jtDate;
	private JTextField jtTimeFrom;
	private JTextField jtTimeTo;
	private JTextField jtFromGroup;
	private JTextField jtToGroup;
	
	// Main data grid
	private JTable jaTrxGroups;
	
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
	
	private SendPosInvoicesControllerBeta ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
	private ListOfValuesSelection lovSelection;
		
	/**
	 * This is the default constructor
	 */
	public CustomerTransactions () {
		super();
		// esto se hace aqui solo en etapa de desarrollo y pruebas
		AppConfig.loadConfig();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new SendPosInvoicesControllerBeta();
	    this.ctrl.setEvent("SENDING");
		// build GUI
		initialize();
		try {		
			// initialize controller
			ApplicationMessage aMsg = ctrl.initForm();
			if (aMsg == null) {
				showContextInfo();
		        jtDate.requestFocusInWindow();
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
		this.setName("CustomerTransactions");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Facturas y Notas de Clientes");
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
			jlAction1.setText(" Preparar Envio");
			jlAction1.setBackground(appLook.getMenuBg());
			jlAction1.setForeground(appLook.getMenuFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
				public void mouseClicked(MouseEvent e) {  
					if (ctrl.getTrxDate() == null) {
						ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe indicar la fecha para preparar los datos");
						x.setVisible(true);			    	        				    	    				    	    	
					} else {
						boolean prepareTrx = true;
						// determinar si existen transacciones de tipo factura en el rango de fechas
						ctrl.findInvoicesCount();
						if (ctrl.getInvoicesQty() == 0) {
							prepareTrx = false;
							ErrorMessageWindow x = new ErrorMessageWindow(null, "No existen transacciones para estos filtros");
							x.setVisible(true);			    	        				    	    	
						} else {
							ctrl.findNotValidInvoicesCount();
							if (ctrl.getNotValidInvoicesQty() > 0) {
								prepareTrx = false;
								ErrorMessageWindow x = new ErrorMessageWindow(null, "Existen transacciones no validas en este rango de fechas");
								x.setVisible(true);			    	        				    	    									
							} else {
							    ctrl.findPreparedCount();
							    if (ctrl.getPreparedQty() == ctrl.getInvoicesQty()) {
								    prepareTrx = false;
								    ErrorMessageWindow x = new ErrorMessageWindow(null, "Ya estan preparadas las transacciones en este rango de fechas");
								    x.setVisible(true);			    	        				    	    	
							    }
							}
						}
						//
						if (prepareTrx == true) {
							ApplicationMessage m = ctrl.prepareTrx();
							if (m.getLevel().equalsIgnoreCase(ApplicationMessage.ERROR)) {
								ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
								x.setVisible(true);		
							} else {
								MessageWindow x = new MessageWindow(null, "Preparacion de Transacciones", m.getText());
								x.setVisible(true);	
							}
						}
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
			jlAction2.setText(" Enviar Todo");
			jlAction2.setBackground(appLook.getMenuBg());
			jlAction2.setForeground(appLook.getMenuFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
	    	            ApplicationMessage m = ctrl.sendTxBatch ( 0 );
	    	            if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
	    	        	        MessageWindow x = new MessageWindow(null, "Envio de Lotes de Facturas", m.getText());
					    x.setVisible(true);			    	        	
	    	            } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
			                x.setVisible(true);			    	        	
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
			jlAction3.setText(" Enviar Lote");
			jlAction3.setBackground(appLook.getMenuBg());
			jlAction3.setForeground(appLook.getMenuFg());
			jlAction3.setFont(appLook.getSmallFont());
			jlAction3.setOpaque(true);
			jlAction3.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction3.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
		    	        int groupNumber = 0;
			        int rowIndex = -1;
			        if (jaTrxGroups != null) {
				        rowIndex = jaTrxGroups.getSelectedRow();
			        }
		            if (rowIndex > -1) {
			            // el identificador del objeto a editar se encuentra en la columna 0
		            	    groupNumber = (int) jaTrxGroups.getValueAt(rowIndex, 0);
		                ctrl.setSelectedGroupId(groupNumber);
		    	            ApplicationMessage m = ctrl.sendTxBatch ( groupNumber );
		                //
		    	            if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
		    	        	        MessageWindow x = new MessageWindow(null, "Envio de Lote de Facturas", m.getText());
						    x.setVisible(true);			    	        	
		    	            } else {
				            ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
				            x.setVisible(true);			    	        	
		    	            }
		            } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe seleccionar el lote a enviar");
			            x.setVisible(true);			    	        	
		            }
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
			    	    requestLeaving();
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
			jlDate = getFiltersLabel("Fecha");
			jpFilters.add(jlDate, null);
			// segunda linea
			lineNum++;
			lbY = lbY + lbH;
			jlTimeFrom = getFiltersLabel("Hora Desde");
			jpFilters.add(jlTimeFrom, null);
			lbX = startX + lbW + tfW;
			jlTimeTo = getFiltersLabel("Hora Hasta");
			jpFilters.add(jlTimeTo, null);
			// tercera linea
			lineNum++;
			lbX = startX;
			lbY = lbY + lbH;
			jlFromGroup = getFiltersLabel("No. Grupo Inicial");
			jpFilters.add(jlFromGroup, null);
			lbX = startX + lbW + tfW;
			jlToGroup = getFiltersLabel("No. Grupo Final");
			jpFilters.add(jlToGroup, null);
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
			jpFilters.add(getjtDate(), null);
			// segunda linea
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			tfW = tfWidth;
			jpFilters.add(getjtTimeFrom(), null);
			//
			tfW = tfWidth;
			// tener en cuenta aqui que el valor de lbW puede no ser conocido
			tfX = tfX + tfW + lbW;
			jpFilters.add(getjtTimeTo(), null);
			// tercera linea
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			tfW = tfWidth;
			jpFilters.add(getjtFromGroup(), null);
			//
			tfW = tfWidth;
			// tener en cuenta aqui que el valor de lbW puede no ser conocido
			tfX = tfX + tfW + lbW;
			jpFilters.add(getjtToGroup(), null);
			
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

	private JTextField getjtDate() {
		if (jtDate == null) {
			jtDate = getFiltersTextfield();
			jtDate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterDate();
					if (aMsg == null) {
						if (ctrl.getTrxDate() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							String d = sdf.format(ctrl.getTrxDate());
							jtDate.setText(d);
							// mostrar el rango de lotes con transacciones no enviadas
							if (ctrl.getFromGroup() > 0 & ctrl.getToGroup() > 0) {
								jtFromGroup.setText(String.valueOf(ctrl.getFromGroup()));
								jtToGroup.setText(String.valueOf(ctrl.getToGroup()));
								jtFromGroup.repaint();
								jtToGroup.repaint();
							}
						} else {
							jtDate.setText("");							
						}
						jtDate.repaint();
						jtTimeFrom.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtDate.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtDate;
	}	

	private JTextField getjtTimeFrom() {
		if (jtTimeFrom == null) {
			jtTimeFrom = getFiltersTextfield();
			jtTimeFrom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterTimeFrom();
					if (aMsg == null) {
					    jtTimeFrom.setText(ctrl.getFromTime());
					    jtTimeFrom.repaint();
						jtTimeTo.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtTimeFrom.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtTimeFrom;
	}	
	
	private JTextField getjtTimeTo() {
		if (jtTimeTo == null) {
			jtTimeTo = getFiltersTextfield();
			jtTimeTo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterTimeTo();
					if (aMsg == null) {
						jtTimeTo.setText(ctrl.getToTime());
						jtTimeTo.repaint();
						jtFromGroup.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtTimeTo.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtTimeTo;
	}	

	private JTextField getjtFromGroup() {
		if (jtFromGroup == null) {
			jtFromGroup = getFiltersTextfield();
			jtFromGroup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterFromGroup();
					if (aMsg == null) {
						if (ctrl.getFromGroup() != 0) {
							jtFromGroup.setText(String.valueOf(ctrl.getFromGroup()));
						} else {
							jtFromGroup.setText("");							
						}
						jtFromGroup.repaint();
						jtToGroup.requestFocusInWindow();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtFromGroup.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtFromGroup;
	}	

	private JTextField getjtToGroup() {
		if (jtToGroup == null) {
			jtToGroup = getFiltersTextfield();
			jtToGroup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterToGroup();
					if (aMsg == null) {
						if (ctrl.getToGroup() != 0) {
							jtToGroup.setText(String.valueOf(ctrl.getToGroup()));
						} else {
							jtToGroup.setText("");							
						}
						jtToGroup.repaint();
						executeQuery();
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtToGroup.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtToGroup;
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

	private JScrollPane getjsTrxGroups() {
		if (jsTrxGroups == null) {
			jsTrxGroups = new JScrollPane();
			jsTrxGroups.setSize(new Dimension((centerWidth - 20), (centerHeight - (abtnHeight + 30))));
			jsTrxGroups.setLocation(new Point(10, 10));
			getjaTrxGroups ( );
			jsTrxGroups.setViewportView(jaTrxGroups);
		}
		return jsTrxGroups;
	}	
	
	private void getjaTrxGroups ( ) {
		jaTrxGroups = new JTable(ctrl.getSendTableModel());
		jaTrxGroups.setCellSelectionEnabled(true);
		jaTrxGroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jaTrxGroups.setRowHeight(30);
		jaTrxGroups.setFont(appLook.getSmallFont());
		//{"No. Item", "No. Factura", "Fecha", "Importe", "RUC/No. Identidad", 
        //        "RUC", "Cliente", "CDC", "Identificador", "Caja", "Control"};
		for ( int i=0; i<jaTrxGroups.getColumnCount(); i++) {
			TableColumn tc = jaTrxGroups.getColumn(jaTrxGroups.getColumnName(i));
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
	private ApplicationMessage enterDate () {
		ApplicationMessage aMsg = null;
		String s = jtDate.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterDate(s);
		} else {
			aMsg = ctrl.enterDate(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}
	
	private ApplicationMessage enterTimeFrom () {
		ApplicationMessage aMsg = null;
		String s = jtTimeFrom.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterTimeFrom(s);	
		} else {
			aMsg = ctrl.enterTimeFrom(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}

	private ApplicationMessage enterTimeTo () {
		ApplicationMessage aMsg = null;
		String s = jtTimeTo.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterTimeTo(s);	
		} else {
			aMsg = ctrl.enterTimeTo(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}

	private ApplicationMessage enterFromGroup () {
		ApplicationMessage aMsg = null;
		String s = jtFromGroup.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterFromGroup(s);	
		} else {
			aMsg = ctrl.enterFromGroup(null);	
		}
		if (aMsg == null) {
			return null;
		} else {
			return aMsg;
		}
	}
	
	private ApplicationMessage enterToGroup () {
		ApplicationMessage aMsg = null;
		String s = jtToGroup.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterToGroup(s);	
		} else {
			aMsg = ctrl.enterToGroup(null);	
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
						if (lovSelection != null) {
							//ctrl.enterCashId(lovSelection.getItemId());
							//jtCash.setText(lovSelection.getItemName());
							//jtCash.repaint();
							//jtFromDate.requestFocusInWindow();
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
		   jtDate.requestFocusInWindow();
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
    	    GenericStringsList x = ctrl.getBatchesRange();
    	    if ( x == null ) {
		    ErrorMessageWindow em = new ErrorMessageWindow(null, "No se ha encontrado ninguna transaccion pendiente de envio en esta fecha");
		    em.setVisible(true);
    	    }
    	    ctrl.loadSendDataModel();
    	    ctrl.createSendItemsTable();
		if (jsTrxGroups == null) {
			System.out.println("jsTransactions es nulo...");
		    jpCenter.add(getjsTrxGroups(), null);
		} else {
			System.out.println("jsTransactions no es nulo...");
		    getjaTrxGroups ( );
		    jsTrxGroups.setViewportView(jaTrxGroups);
		    jpCenter.add(jsTrxGroups, null);
		}
		jpCenter.repaint();
		jaTrxGroups.requestFocusInWindow();
    }
    
    private void deleteRecord () {
    	    System.out.println("eliminando registro...");    	
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMessage aMsg = SessionContext.getContextValues();
		//
		CustomerTransactions w = new CustomerTransactions();
		w.setVisible(true);
		w.repaint();
	}
    
}
