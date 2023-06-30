package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import business.AppConfig;
import business.ApplicationMessage;
import business.PosMenuController;
import business.UserAttributes;

public class PosMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpToolbar = null;
	private JPanel jpHeader = null;
	private JPanel jpFilters = null;
	private JPanel jpCenter = null;
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
	
	private PosMenuController ctrl;
	
	// Application specific data
	private final String appModule = "Avanza Electronic Billing";
		
	/**
	 * This is the default constructor
	 */
	public PosMenu () {
		super();
		// esto se hace aqui solo en etapa de desarrollo y pruebas
		AppConfig.loadConfig();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("BLUE-SKY");
		// instantiate MVC controller
	    this.ctrl = new PosMenuController();
		// build GUI
		initialize();
		try {		
			// initialize controller
			ApplicationMessage aMsg = ctrl.initForm();
			if (aMsg == null) {
				showContextInfo();
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
		this.setName("PosMenu");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		//this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		this.add(getJContentPane());
		//
		this.setTitle("Documentos Electronicos POS");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				endProgram();			}
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
			// {" Enviar Lotes", " Enviar Documentos", " Cancelacion", " Inutilizacion", " KuDE", " XML", " Consultar Lotes", " Salir"};
			int idx = 0;
			jlAction1 = new JLabel();
			jlAction1.setSize(new Dimension(tbtnWidth, tbtnHeight));
			jlAction1.setLocation(new Point(xPos, 1));
			jlAction1.setText(ctrl.getMenuItems()[idx]);
			jlAction1.setBackground(appLook.getMenuBg());
			jlAction1.setForeground(appLook.getMenuFg());
			jlAction1.setFont(appLook.getSmallFont());
			jlAction1.setOpaque(true);
			jlAction1.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction1.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			        openSendBatchWindow();
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
			idx++;
			jlAction2 = new JLabel();
			jlAction2.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction2.setLocation(new Point(xPos, 1));
			jlAction2.setText(ctrl.getMenuItems()[idx]);
			jlAction2.setBackground(appLook.getMenuBg());
			jlAction2.setForeground(appLook.getMenuFg());
			jlAction2.setFont(appLook.getSmallFont());
			jlAction2.setOpaque(true);
			jlAction2.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction2.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    openSendDocumWindow();
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
			idx++;
			jlAction3 = new JLabel();
			jlAction3.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction3.setLocation(new Point(xPos, 1));
			jlAction3.setText(ctrl.getMenuItems()[idx]);
			jlAction3.setBackground(appLook.getMenuBg());
			jlAction3.setForeground(appLook.getMenuFg());
			jlAction3.setFont(appLook.getSmallFont());
			jlAction3.setOpaque(true);
			jlAction3.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction3.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    openCancelWindow();
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
			idx++;
			jlAction4 = new JLabel();
			jlAction4.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction4.setLocation(new Point(xPos, 1));
			jlAction4.setText(ctrl.getMenuItems()[idx]);
			jlAction4.setBackground(appLook.getMenuBg());
			jlAction4.setForeground(appLook.getMenuFg());
			jlAction4.setFont(appLook.getSmallFont());
			jlAction4.setOpaque(true);
			jlAction4.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction4.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    openDisablingWindow();
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
			// {" Enviar Lotes", " Enviar Documentos", " Cancelacion", " Inutilizacion", " KuDE", " XML", " Consultar Lotes", " Salir"};
			idx++;
			jlAction5 = new JLabel();
			jlAction5.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction5.setLocation(new Point(xPos, 1));
			jlAction5.setText(ctrl.getMenuItems()[idx]);
			jlAction5.setBackground(appLook.getMenuBg());
			jlAction5.setForeground(appLook.getMenuFg());
			jlAction5.setFont(appLook.getSmallFont());
			jlAction5.setOpaque(true);
			jlAction5.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction5.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    openRenderWindow();
			    }  
				public void mouseEntered (MouseEvent evt) {
					jlAction5.setBackground(appLook.getSelMenuBg());
					jlAction5.setForeground(appLook.getSelMenuFg());
					jlAction5.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction5.setBackground(appLook.getMenuBg());
					jlAction5.setForeground(appLook.getMenuFg());
					jlAction5.repaint();
				}
			});
			// {" Enviar Lotes", " Enviar Documentos", " Cancelacion", " Inutilizacion", " KuDE", " XML", " Consultar Lotes", " Salir"};
			idx++;
			jlAction6 = new JLabel();
			jlAction6.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction6.setLocation(new Point(xPos, 1));
			jlAction6.setText(ctrl.getMenuItems()[idx]);
			jlAction6.setBackground(appLook.getMenuBg());
			jlAction6.setForeground(appLook.getMenuFg());
			jlAction6.setFont(appLook.getSmallFont());
			jlAction6.setOpaque(true);
			jlAction6.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction6.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
		    	        openDeliveryXmlWindow();
			    }  
				public void mouseEntered (MouseEvent evt) {
					jlAction6.setBackground(appLook.getSelMenuBg());
					jlAction6.setForeground(appLook.getSelMenuFg());
					jlAction6.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction6.setBackground(appLook.getMenuBg());
					jlAction6.setForeground(appLook.getMenuFg());
					jlAction6.repaint();
				}
			});
			// {" Enviar Lotes", " Enviar Documentos", " Cancelacion", " Inutilizacion", " KuDE", " XML", " Consultar Lotes", " Salir"};
			idx++;
			jlAction7 = new JLabel();
			jlAction7.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction7.setLocation(new Point(xPos, 1));
			jlAction7.setText(ctrl.getMenuItems()[idx]);
			jlAction7.setBackground(appLook.getMenuBg());
			jlAction7.setForeground(appLook.getMenuFg());
			jlAction7.setFont(appLook.getSmallFont());
			jlAction7.setOpaque(true);
			jlAction7.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction7.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    openQueryWindow();
			    }  
				public void mouseEntered (MouseEvent evt) {
					jlAction7.setBackground(appLook.getSelMenuBg());
					jlAction7.setForeground(appLook.getSelMenuFg());
					jlAction7.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction7.setBackground(appLook.getMenuBg());
					jlAction7.setForeground(appLook.getMenuFg());
					jlAction7.repaint();
				}
			});
			//
			idx++;
			jlAction8 = new JLabel();
			jlAction8.setSize(new Dimension(tbtnWidth, tbtnHeight));
			xPos = xPos + tbtnWidth;
			jlAction8.setLocation(new Point(xPos, 1));
			jlAction8.setText(ctrl.getMenuItems()[idx]);
			jlAction8.setBackground(appLook.getMenuBg());
			jlAction8.setForeground(appLook.getMenuFg());
			jlAction8.setFont(appLook.getSmallFont());
			jlAction8.setOpaque(true);
			jlAction8.setHorizontalAlignment(SwingConstants.LEFT);
			jlAction8.addMouseListener(new MouseAdapter() {  
			    public void mouseClicked(MouseEvent e) {  
			    	    endProgram();
			    	} 
				public void mouseEntered (MouseEvent evt) {
					jlAction8.setBackground(appLook.getSelMenuBg());
					jlAction8.setForeground(appLook.getSelMenuFg());
					jlAction8.repaint();
				}
				public void mouseExited (MouseEvent evt) {
					jlAction8.setBackground(appLook.getMenuBg());
					jlAction8.setForeground(appLook.getMenuFg());
					jlAction8.repaint();
				}
			});
			//
			jpToolbar.add(jlAction1, null);
			jpToolbar.add(jlAction2, null);
			jpToolbar.add(jlAction3, null);
			jpToolbar.add(jlAction4, null);
			jpToolbar.add(jlAction5, null);
			jpToolbar.add(jlAction6, null);
			jpToolbar.add(jlAction7, null);
			jpToolbar.add(jlAction8, null);
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
			jpFilters = new JPanel();
			jpFilters.setLayout(null);
			jpFilters.setSize(new Dimension(filtersWidth, filtersHeight));
			//System.out.println("jpFilters x: " + filtersX + " y:" + filtersY + " w: " + filtersWidth + " h: " + filtersHeight);
			jpFilters.setPreferredSize(new Dimension(filtersWidth, filtersHeight));
			jpFilters.setLocation(new Point(filtersX, filtersY));
			jpFilters.setBackground(appLook.getBodyBg());
			jpFilters.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		return jpFilters;
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
		}
		return jpCenter;
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
	
	private void clearAll () {
    }
    
    private void reInitGUI () {
        clearAll ();
    	    repaint();
    }
    
	private void openSendBatchWindow () {
		new SendPosInvoicesBeta();
		this.dispose();
	}
    
	private void openSendDocumWindow () {
		new ResendPosInvoicesBeta();
		this.dispose();
	}

	private void openRenderWindow () {
		new PosGenerateGR();
		this.dispose();
	}

	private void openDeliveryGrWindow () {
		new PosDeliverGraphReps();
		this.dispose();
	}
	
	private void openDeliveryXmlWindow () {
		new PosDeliverXml();
		this.dispose();
	}

	private void openCancelWindow () {
		new SendPosCancellationsBeta();
		this.dispose();
	}
	
	private void openDisablingWindow () {
		new SendPosDisablingsBeta();
		this.dispose();
	}

	private void openQueryWindow () {
		new ViewPosTrxBatches();
		this.dispose();
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
		new EDocsMenu ();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String port = args[0];
		PosMenu w = new PosMenu();
		w.setVisible(true);
		w.repaint();
	}
    
}
