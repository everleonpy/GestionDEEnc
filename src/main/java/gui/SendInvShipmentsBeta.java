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

import business.ApplicationMessage;
import business.InvShipmentsTM;
import business.SendInvShipmentsControllerBeta;
import business.SessionContext;
import business.UserAttributes;
import dao.ProblemaDatosException;
import pojo.FndSite;
import pojo.ListOfValuesParameters;
import pojo.ListOfValuesSelection;

public class SendInvShipmentsBeta extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpHeader = null;
	private JPanel jpFilters = null;
	private JPanel jpCenter = null;
	private JScrollPane jsTrxGroups = null;
	// Encabezado de formulario
	private JLabel jlVendorLogo = null;
	private JLabel jlOrgName;
	private JLabel jlUserName;
	// Barra de herramientas
	private JLabel jlAction1 = null;
	private JLabel jlAction2 = null;
	private JLabel jlAction3 = null;
	//private JLabel jlAction4 = null;
	//private JLabel jlAction5 = null;
	//private JLabel jlAction6 = null;
	
	// bloque de filtro
	private JLabel jlDate;
	private JLabel jlSite;
	private JLabel jlTxNumber;
	private JTextField jtDate;
	private JTextField jtSite;
	private JButton jbLov1 = null;
	private JTextField jtTxNumber;
	
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
	
	private SendInvShipmentsControllerBeta ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
	private ListOfValuesSelection lovSelection;
		
	/**
	 * This is the default constructor
	 */
	public SendInvShipmentsBeta () {
		super();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new SendInvShipmentsControllerBeta();
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
		this.setName("SendInvShipments");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Envio de Remisiones");
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
			jlAction1.setText(" Enviar");
			jlAction1.setBackground(appLook.getMenuBg());
			jlAction1.setForeground(appLook.getMenuFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
				public void mouseClicked(MouseEvent e) {  
					boolean sendData = true;
					/*
					ctrl.findPreparedCount();
					if (ctrl.getPreparedQty() == 0) {
						sendData = false;
						ErrorMessageWindow em = new ErrorMessageWindow(null, "No hay ninguna transaccion preparada en este rango de fechas");
						em.setVisible(true);			    	        				    	    							
					}
					// 
					ctrl.findUnavailableCount();
					if (ctrl.getUnavailableQty() > 0) {
						String txt = "En este rango de fechas existen " + ctrl.getUnavailableQty() + " transacciones" +
					                 " que no cumplen las condiciones para ser enviadas";
						MessageWindow wm = new MessageWindow(null, "Envio de Lote de Remisiones", txt);
						wm.setVisible(true);	
					}
					*/
					if (sendData == true) {
						ApplicationMessage m = ctrl.sendTxBatch ();
						if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
							MessageWindow wm = new MessageWindow(null, "Envio de Lote de Remisiones", m.getText());
							wm.setVisible(true);	
							// ejecutar inmediatamente la consulta de los lotes enviados
							m = ctrl.queryBatches();
							if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
								wm = new MessageWindow(null, "Consulta de Lote de Remisiones", m.getText());
								wm.setVisible(true);	
							} else {
								ErrorMessageWindow em = new ErrorMessageWindow(null, m.getText());
								em.setVisible(true);			    	        		    	                    	
							}
						} else {
							ErrorMessageWindow em = new ErrorMessageWindow(null, m.getText());
							em.setVisible(true);			    	        	
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
			jlAction2.setText(" Consultar Documento");
			jlAction2.setBackground(appLook.getMenuBg());
			jlAction2.setForeground(appLook.getMenuFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
		    	        String controlCode = "";
		    	        long batchId = -1;
			        int rowIndex = -1;
			        if (jaTrxGroups != null) {
				        rowIndex = jaTrxGroups.getSelectedRow();
				        System.out.println("rowIndex: " + rowIndex);
			        }
		            if (rowIndex > -1) {
			            // el CDC de la transaccion se encuentra en la columna 9
		            	    controlCode = (String) jaTrxGroups.getValueAt(rowIndex, 9);
		            	    System.out.println("CDC: " + controlCode);
		            	    if (controlCode != null) {
		                    ctrl.setSelectedCtrlCode(controlCode);
		    	                ApplicationMessage m = ctrl.queryCDC ( controlCode );
		                    //
		    	                if (m.getLevel().equalsIgnoreCase(ApplicationMessage.MESSAGE)) {
		    	        	            MessageWindow x = new MessageWindow(null, "Consulta de CDC", m.getText());
						        x.setVisible(true);			    	        	
		    	                } else {
				                ErrorMessageWindow x = new ErrorMessageWindow(null, m.getText());
				                x.setVisible(true);			    	        	
		    	                }
		            	    } else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, "No se ha podido obtener el valor del CDC");
					        x.setVisible(true);			    	        			            	    	
		            	    }
		            } else {
			            ErrorMessageWindow x = new ErrorMessageWindow(null, "No se ha podido obtener el valor del CDC");
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
			//jpToolbar.add(jlAction4, null);
			//jpToolbar.add(jlAction5, null);
			//jpToolbar.add(jlAction6, null);
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
			int lineNum = 0;
			lbX = startX;
			lbY = startY;
			// primera linea
			jlDate = getFiltersLabel("Fecha");
			jpFilters.add(jlDate, null);
			// segunda linea
			lineNum++;
			lbY = lbY + lbH;
			jlSite = getFiltersLabel("Sucursal");
			jpFilters.add(jlSite, null);
			// tercera linea
			lineNum++;
			lbY = lbY + lbH;
			jlTxNumber = getFiltersLabel("No. Factura");
			jpFilters.add(jlTxNumber, null);
			/**
			 +----------------------------------------------------+
			 | region text fields placement                       |
			 +----------------------------------------------------+
			 */
			lineNum = 0;
			tfW = tfWidth * 3;
			tfX = startX + lbW + hGap;
			tfY = startY;
			// primera linea
			jpFilters.add(getjtDate(), null);
			// segunda linea
			lineNum++;
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			jpFilters.add(getjtSite(), null);
			// tercera linea
			lineNum++;
			tfX = startX + lbW + hGap;
			tfY = tfY + tfH;
			tfW = tfWidth;
			jpFilters.add(getjtTxNumber(), null);
			
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
						} else {
							jtDate.setText("");							
						}
						jtDate.repaint();
						jtSite.requestFocusInWindow();
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

	private JTextField getjtSite() {
		if (jtSite == null) {
			jtSite = getFiltersTextfield();
			jtSite.setName("SITE");
			jtSite.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// si el campo contiene un texto, realizar la busqueda por descripcion
					if (jtSite.getText().isEmpty() == false) {
						FndSite s = ctrl.findSite(0, jtSite.getText(), UserAttributes.userUnit.getIDENTIFIER());
						if ( s != null ) {
							// si el procedimiento de busqueda retorno una fila, pasar el control al sigiuente item
							jtTxNumber.requestFocusInWindow();							
						} else {
							// si el procedimiento no encontro ninguna fila, ejecuta la lista de valores
							showListOfValues ( "SUCURSALES", "Sucursales", 0, null);
							if (lovSelection != null) {
								ctrl.enterSiteId(lovSelection.getItemId());
								jtSite.setText(lovSelection.getItemName());
								jtSite.repaint();
								jtTxNumber.requestFocusInWindow();
							}
						}
					} else {
						jtTxNumber.requestFocusInWindow();													
					}
				}
			});
			jtSite.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtSite;
	}	
	
	private JButton getJbLov1() {
		if (jbLov1 == null) {
			jbLov1 = getLovButton ("jbLov1");
			jbLov1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showListOfValues ( "SUCURSALES", "Sucursales de la Organizacion", 0, null);
					if (lovSelection != null) {
						ctrl.enterSiteId(lovSelection.getItemId());
						jtSite.setText(lovSelection.getItemName());
						jtSite.repaint();
						jtTxNumber.requestFocusInWindow();
					}
				}
			});
		}
		return jbLov1;
	}	
	
	private JTextField getjtTxNumber() {
		if (jtTxNumber == null) {
			jtTxNumber = getFiltersTextfield();
			jtTxNumber.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg = enterTxNumber();
					if (aMsg == null) {
						if (ctrl.getTxNumber() != null) {
							jtTxNumber.setText(ctrl.getTxNumber());
						} else {
							jtTxNumber.setText("");							
						}
						jtTxNumber.repaint();
						jtSite.requestFocusInWindow();
						/*
						if (ctrl.getPreparedQty() == 0) {
	    	        	            ctrl.findPreparedCount();
						}
						if (ctrl.getPreparedQty() > 0) {
						    executeQuery();
						} else {
							jtSite.requestFocusInWindow();
						}
						*/
					} else {
				        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				        x.setVisible(true);
					}		
				}
			});
			jtTxNumber.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					dataEntryKeyPressed (e);
				}
			});
		}
		return jtTxNumber;
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

	private JScrollPane getjsTrxGroups( String viewType ) {
		if (jsTrxGroups == null) {
			jsTrxGroups = new JScrollPane();
			jsTrxGroups.setSize(new Dimension((centerWidth - 20), (centerHeight - (abtnHeight + 30))));
			jsTrxGroups.setLocation(new Point(10, 10));
			getjaTrxGroups ( viewType );
			jsTrxGroups.setViewportView(jaTrxGroups);
		}
		return jsTrxGroups;
	}	
	
	private void getjaTrxGroups ( String viewType ) {
		if (viewType.equalsIgnoreCase("SHIPMENTS")) {
			jaTrxGroups = new JTable(ctrl.getShipmentsTableModel());
			jaTrxGroups.setCellSelectionEnabled(true);
			jaTrxGroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jaTrxGroups.setRowHeight(30);
			jaTrxGroups.setFont(appLook.getSmallFont());
			//"No. Remision", "Fecha", "Tipo", "Destino", 
            //"No. Identidad", "Tipo Identidad", "Cliente", "Estado", 
            //"CDC", "Identificador", "Lote Informado"};
			for ( int i=0; i<jaTrxGroups.getColumnCount(); i++) {
				TableColumn tc = jaTrxGroups.getColumn(jaTrxGroups.getColumnName(i));
				if ( i == 0 ) {
					// numero de factura
					tc.setPreferredWidth(70);
				}
				if ( i == 1 ) {
					// fecha de factura
					tc.setPreferredWidth(50);
				}
				if ( i == 2 ) {
					// tipo de factura
					tc.setPreferredWidth(70);
				}
				if ( i == 3 ) {
					// importe de factura
					tc.setPreferredWidth(70);
				}
				if ( i == 4 ) {
					// numero de identidad del cliente
					tc.setPreferredWidth(70);
				}
				if ( i == 5 ) {
					// tipo de identificacion del cliente
					tc.setPreferredWidth(30);
				}
				if ( i == 6 ) {
					// denominacion del cliente
					tc.setPreferredWidth(70);
				}
				if ( i == 7 ) {
					// estado de la factura
					tc.setPreferredWidth(30);
				}
				if ( i == 8 ) {
					// importe cobrado
					tc.setPreferredWidth(70);
				}
				if ( i == 9 ) {
					// CDC
					tc.setPreferredWidth(150);
				}
				if ( i == 10 ) {
					// identificador de factura
					tc.setPreferredWidth(40);
				}
				if ( i == 11 ) {
					// lote de envio
					tc.setPreferredWidth(40);
				}
			}
		}
		//
		if (viewType.equalsIgnoreCase("PREPARED")) {
			jaTrxGroups = new JTable(ctrl.getPreparedTableModel());
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
	}	
	
	private JTable getEmptyTable ( ) {
		InvShipmentsTM tm = new InvShipmentsTM ();
		//
		tm = ctrl.createShipmentsEmptyTable();
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
	
	private ApplicationMessage enterTxNumber () {
		ApplicationMessage aMsg = null;
		String s = jtTxNumber.getText();
		if (s.length() > 0) {
			aMsg = ctrl.enterTxNumber(s);	
		} else {
			aMsg = ctrl.enterTxNumber(null);	
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
		new InventoryMenu ();
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
    private void queryShipments () {
	    //ErrorMessageWindow x = new ErrorMessageWindow(null, "Ejecutando consulta");
	    //x.setVisible(true); 
    	    // obtener la cantidad de transacciones que cumplen las condiciones para el envio
    	    ctrl.findShipmentsCount();
    	    // obtener la cantidad de transacciones que no cumplen las condiciones para el envio
    	    ctrl.findUnavailableCount(); 
    	    // mostrar las transacciones validas para envio en una tabla
    	    ctrl.loadShipmentsDataModel();
    	    ctrl.createShipmentsItemsTable();
		if (jsTrxGroups == null) {
			System.out.println("jsTransactions es nulo...");
		    jpCenter.add(getjsTrxGroups("SHIPMENTS"), null);
		} else {
			System.out.println("jsTransactions no es nulo...");
		    getjaTrxGroups ("SHIPMENTS");
		    jsTrxGroups.setViewportView(jaTrxGroups);
		    jpCenter.add(jsTrxGroups, null);
		}
		jpCenter.repaint();
		String txt = "Remisiones validas para envio: " + ctrl.getShipmentsQty() + 
				     " | Remisiones no validas para envio: " + ctrl.getUnavailableQty();
        MessageWindow x = new MessageWindow(null, "Consulta de Remisiones", txt);
        x.setVisible(true);			    	        	
		jaTrxGroups.requestFocusInWindow();
    }
    
    private void queryPrepared () {
	    //ErrorMessageWindow x = new ErrorMessageWindow(null, "Ejecutando consulta");
	    //x.setVisible(true);  
    	    ctrl.loadPreparedDataModel();
    	    ctrl.createPreparedItemsTable();
		if (jsTrxGroups == null) {
			System.out.println("jsTransactions es nulo...");
		    jpCenter.add(getjsTrxGroups("PREPARED"), null);
		} else {
			System.out.println("jsTransactions no es nulo...");
		    getjaTrxGroups ("PREPARED");
		    jsTrxGroups.setViewportView(jaTrxGroups);
		    jpCenter.add(jsTrxGroups, null);
		}
		jpCenter.repaint();
		jaTrxGroups.requestFocusInWindow();
    }

    /**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMessage aMsg = SessionContext.getContextValues();
		//
		SendInvShipmentsBeta w = new SendInvShipmentsBeta();
		w.setVisible(true);
		w.repaint();
	}
    
}
