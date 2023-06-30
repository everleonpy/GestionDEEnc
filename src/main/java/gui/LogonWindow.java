package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import business.AppConfig;
import business.ApplicationInfo;
import business.ApplicationMessage;
import business.LogonWindowController;
import business.SessionContext;
import business.TestSocket;
import business.UserAttributes;
//import pos.business.UserModeLauncher;
import dao.UserCredentialsDAO;
import pojo.FndUser;

public class LogonWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	private JPanel jpCenter = null;
	private JPanel jpBottom = null;
	
	private JLabel jlProductLogo = null;
	private JLabel jlUserName = null;
	private JLabel jlPassword = null;
	private JTextField jtUserName = null;
	private JPasswordField jpPassword = null;
	private JButton jbExit = null;
	private JButton jbEnter = null;
	
	private boolean upperInputs = true;

	// look and feel
	private AppLookAndFeel appLook;
	private OvalBorder lineBorder = new OvalBorder();
	
	private LogonWindowController ctrl;
	
	//private String userName;
	//private String userPass;
    //private String ipAddress;
    
	private static final String systemInfo = ApplicationInfo.nombreProveedor + "  |  " +
	                                         ApplicationInfo.nombreAplicacion + "  |  " +
	                                         ApplicationInfo.nroVersion + "  |  " +
	                                         ApplicationInfo.nombreCliente + "  |  " +
	                                         "JDK " + System.getProperty("java.version");
	// El numero de version se interpreta de la siguiente manera:
	// 1er segmento: Version Mayor
	// 2do segmento: Version Menor
	// 3er segmento: Numero de compilacion
	
	private int labelsX, labelsY;
	private int lWidth, lHeight;
	private int wOffset;
	
	private int textFieldsX, textFieldsY;
	private int tfWidth, tfHeight;
	private int lbWidth, lbHeight;
	private int btWidth, btHeight;
	
	// variables de control de pruebas
	private boolean testMode;
	private boolean supressDbCommands;
	
	/**
	 * This is the default constructor
	 */
	public LogonWindow() {
		super();
		this.ctrl = new LogonWindowController();
		this.ctrl.initForm();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("GREEN-FOREST");
		initialize();
		setActions ();
		repaint();
		jtUserName.requestFocusInWindow();
		jtUserName.selectAll();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 680);
		this.setLocation(new Point(150, 70));
		this.setResizable(false);
		this.setName("jfLogin");
		/*
		if (appLook.isBckgndImage() == true) {
		    this.setLayout(new BorderLayout());
		    this.setContentPane(new JLabel(new ImageIcon(AppConfig.iconsFolder + "main-bg.jpg")));
		    this.add(getJpCenter(), null);
		    this.add(getJpBottom(), null);
		} else {
		    this.add(getJContentPane());	
		}
		*/
	    this.add(getJContentPane());			
		this.setTitle("Acceso a Avanza ERP");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				endProgram ();
			}
		});
	}

	private void setActions () {
		ActionListener lst = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//System.out.println("jbEnter - actionPerformed()");
				ApplicationMessage aMsg;
				aMsg = validateAll ();
				if ( aMsg == null ) {
					//System.out.println("chequeando configuracion...");
				    execCommand ("CONECTAR");
				} else {
				    ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
				    x.setVisible(true);
				}
			}
		};

		jbEnter.addActionListener(lst);
		//getRootPane().setDefaultButton(jbEnter);
		getRootPane().registerKeyboardAction(lst, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),	JComponent.WHEN_IN_FOCUSED_WINDOW);

		lst = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				execCommand ("CANCELAR");
			}
		};
		jbExit.addActionListener(lst);
		getRootPane().registerKeyboardAction(lst, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(appLook.getBodyBg());
			jContentPane.setForeground(appLook.getBodyFg());
			jContentPane.add(getJpCenter(), null);
			jContentPane.add(getJpBottom(), null);
		}
		return jContentPane;
	}	
	
	private JPanel getJpCenter() {
		if (jpCenter == null) {
			tfWidth = 400;
			tfHeight = 90;
			lbWidth = 400;
			lbHeight = 50;
			btWidth = 400;
			btHeight = 50;
			//
			//		
			jpCenter = new JPanel();
			jpCenter.setLayout(null);
			jpCenter.setOpaque(false);
			jpCenter.setBackground(appLook.getBodyBg());
			jpCenter.setSize(new Dimension(500, 500));
			jpCenter.setLocation(new Point(0, 0));
			//jpCenter.setBorder(lineBorder);
			textFieldsX = 50;
			textFieldsY = 0;
			jpCenter.add(getUserNamePrompt(), null);
			textFieldsY = textFieldsY + lbHeight;
			jpCenter.add(getJtUserName(), null);
			textFieldsY = textFieldsY + tfHeight;
			jpCenter.add(getPasswordPrompt(), null);
			textFieldsY = textFieldsY + lbHeight;
			jpCenter.add(getjpPassword(), null);
			//
			textFieldsY = textFieldsY + tfHeight + tfHeight;
			jpCenter.add(getjbEnter(), null);
			textFieldsY = textFieldsY + btHeight;
			jpCenter.add(getjbExit(), null);
		}
		return jpCenter;
	}	
	
	private JLabel getUserNamePrompt() {
		if (jlUserName == null) {
		    jlUserName = new JLabel();
		    jlUserName.setSize(new Dimension(lbWidth, lbHeight));
		    jlUserName.setLocation(new Point(textFieldsX, textFieldsY));
		    jlUserName.setName("jlUserName");
		    jlUserName.setHorizontalAlignment(SwingConstants.LEFT);
		    jlUserName.setText("Usuario");
		    jlUserName.setFont(appLook.getHeaderFont3());
		    jlUserName.setForeground(appLook.getBodyFg());
		}
		return jlUserName;
	}
	
	private JTextField getJtUserName() {
		if (jtUserName == null) {
			jtUserName = new JTextField();
			jtUserName.setName("jtUserName");
			jtUserName.setSize(new Dimension(tfWidth, tfHeight));
			jtUserName.setLocation(new Point(textFieldsX, textFieldsY));
			jtUserName.setToolTipText("Ingrese su Nombre de Usuario");
			jtUserName.setFont(appLook.getHeaderFont2());
			jtUserName.setBackground(appLook.getTxtFieldBg());
			jtUserName.setForeground(appLook.getTxtFieldFg());
			if (appLook.isLightBackground() == true) {
			    jtUserName.setOpaque(false);
			    jtUserName.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
			} else {
				jtUserName.setBorder(lineBorder);
			}
			jtUserName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg;
					boolean validInput = true;
					if (jtUserName.getText() == null) {
						validInput = false;
					    ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe ingresar el nombre de usuario");
					    x.setVisible(true);						
					} else {
						if (jtUserName.getText().isEmpty() == true) {
							validInput = false;
						    ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe ingresar el nombre de usuario");
						    x.setVisible(true);						
						}						
					}
					if (validInput == true) {
						if (upperInputs == true) {
							jtUserName.setText(jtUserName.getText().toUpperCase());
						}
					    aMsg = validateUserName (jtUserName.getText());
					    if (aMsg != null) {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
					        x.setVisible(true);
					    } else {
						    jpPassword.requestFocusInWindow();
						    jpPassword.selectAll();
					    }
					}
				}
			});
			jtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jtUserName.selectAll();
				}
			});
		}
		return jtUserName;
	}

	private JLabel getPasswordPrompt() {
		if (jlPassword == null) {
		    jlPassword = new JLabel();
		    jlPassword.setSize(new Dimension(lbWidth, lbHeight));
		    jlPassword.setLocation(new Point(textFieldsX, textFieldsY));
		    jlPassword.setName("jlPassword");
		    jlPassword.setHorizontalAlignment(SwingConstants.LEFT);
		    jlPassword.setText("Clave de Acceso");
		    jlPassword.setFont(appLook.getHeaderFont3());
		    jlPassword.setForeground(appLook.getBodyFg());
		}
		return jlPassword;
	}
	
	private JPasswordField getjpPassword() {
		if (jpPassword == null) {
			jpPassword = new JPasswordField();
			jpPassword.setName("jpPassword");
			jpPassword.setSize(new Dimension(tfWidth, tfHeight));
			jpPassword.setLocation(new Point(textFieldsX, textFieldsY));
			jpPassword.setToolTipText("Ingrese su Clave de Acceso");
			jpPassword.setFont(appLook.getHeaderFont2());
			jpPassword.setBackground(appLook.getTxtFieldBg());
			jpPassword.setForeground(appLook.getTxtFieldFg());
			if (appLook.isLightBackground() == true) {
			    jpPassword.setOpaque(false);
			    jpPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
			} else {
				jpPassword.setBorder(lineBorder);
			}
			jpPassword.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationMessage aMsg;
					boolean validInput = true;
					if (new String(jpPassword.getPassword()).isEmpty() == true) {
						validInput = false;
					    ErrorMessageWindow x = new ErrorMessageWindow(null, "Debe ingresar la palabra clave de usuario");
					    x.setVisible(true);						
					}
					if (validInput == true) {
						if (upperInputs == true) {
							jpPassword.setText(new String(jpPassword.getPassword()).toUpperCase());
						}
					    aMsg = validateAll ();
					    if ( aMsg == null ) {
					        execCommand ("CONECTAR");
					    } else {
					        ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
					        x.setVisible(true);
					    }
					}
				}
			});
			jpPassword.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					jpPassword.selectAll();
				}
			});
		}
		return jpPassword;
	}

	private JButton getjbExit() {
		if (jbExit == null) {
			jbExit = new JButton();
			jbExit.setName("jbExit");
			jbExit.setText("Salir");
			jbExit.setSize(new Dimension(btWidth, btHeight));
			jbExit.setLocation(new Point(textFieldsX, textFieldsY));
			jbExit.setBackground(appLook.getSecondaryBg());
			jbExit.setForeground(appLook.getSecondaryFg());
			jbExit.setFont(appLook.getRegularFont());
			jbExit.setToolTipText("Descartar Ingreso a la Aplicacion");
			//jbExit.setBorder(lineBorder);
		}
		return jbExit;
	}

	private JButton getjbEnter() {
		if (jbEnter == null) {
			jbEnter = new JButton();
			jbEnter.setName("jbEnter");
			jbEnter.setText("Ingresar");
			jbEnter.setSize(new Dimension(btWidth, btHeight));
			jbEnter.setLocation(new Point(textFieldsX, textFieldsY));
			jbEnter.setBackground(appLook.getPrimaryBg());
			jbEnter.setForeground(appLook.getPrimaryFg());
			jbEnter.setFont(appLook.getRegularFont());
			jbEnter.setToolTipText("Ingresar a la Aplicacion");
			//jbEnter.setBorder(lineBorder);
			jbEnter.setFocusable(false);
		}
		return jbEnter;
	}
	
	private JPanel getJpBottom() {
		
		if (jpBottom == null) {
			jlProductLogo = new JLabel();
			jlProductLogo.setSize(new Dimension(280, 100));
			jlProductLogo.setLocation(new Point(210, 2));
			jlProductLogo.setName("jlProductLogo");
			jlProductLogo.setText(" ");
			jlProductLogo.setHorizontalTextPosition(JLabel.CENTER);
			String archivoImg = "avanza.jpg";
			ImageIcon i = getScaledIcon(archivoImg, 270, 90);
	        jlProductLogo.setIcon(i);
	        
			//
			jpBottom = new JPanel();
			jpBottom.setLayout(null);
			jpBottom.setSize(new Dimension(500, 100));
			jpBottom.setLocation(new Point(0, 500));
			jpBottom.setBackground(appLook.getBodyBg());
			jpBottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.lightGray));
			//jpBottom.setBorder(lineBorder);
			jpBottom.add(jlProductLogo, null);
		}
		return jpBottom;
	}	
			
	/*
	 +---------------------------------------------------------+
	 | seccion de codigo para validaciones y controles         |
	 +---------------------------------------------------------+
	 */
	private ApplicationMessage validateUserName ( String nombre ) {
		ApplicationMessage aMsg = new ApplicationMessage ();
		
		if (nombre == null || nombre.length() == 0) {
			aMsg.setMessage("Error", "Debe ingresar su nombre de usuario", ApplicationMessage.ERROR);
 		    return aMsg;
		}
		return null;
	}
	
	private ApplicationMessage validatePassword ( String clave ) {
		ApplicationMessage aMsg = new ApplicationMessage ();
		
		if (clave == null || clave.length() == 0) {
			aMsg.setMessage("Error", "Debe ingresar su clave de acceso", ApplicationMessage.ERROR);
 		    return aMsg;
		}
		return null;
	}

	private ApplicationMessage validateAll () {
	    ApplicationMessage aMsg;
	    
	    aMsg = validateUserName (new String(jtUserName.getText()));
	    if (aMsg != null) {
		    return aMsg;
	    } 
	    aMsg = validatePassword (new String(jpPassword.getPassword()));
	    if (aMsg != null) {
		    return aMsg;
	    }
	    /*
	    aMsg = validateUserSite (new String(jtUserName.getText()));
	    if (aMsg != null) {
		    return aMsg;
	    }
	    */	    
		return null;
	}
	
	private void execCommand ( String action ) {
		ApplicationMessage aMsg = new ApplicationMessage();
		
		if (action.equals("CONECTAR")) {
			//System.out.println("conexion: " + jtUserName.getText() + " - " + new String (jpPassword.getPassword()));
			aMsg = tryConnection ( jtUserName.getText(), new String (jpPassword.getPassword()));
			if (aMsg != null) {
				//JOptionPane.showMessageDialog(null, aMsg.getText(), "Error Intento Conexion", JOptionPane.ERROR_MESSAGE);
			    ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
			    x.setVisible(true);
				endProgram ();
			} else {
				aMsg = startSession (jtUserName.getText());
				if (aMsg == null) {
					//new InvReceivingsHomeWindow();
					new EDocsMenu();
					this.dispose();
					//ErrorMessageWindow x = new ErrorMessageWindow(null, "logon exitoso");
					//x.setVisible(true);
					//System.exit(0);
			    } else {
					ErrorMessageWindow x = new ErrorMessageWindow(null, aMsg.getText());
					x.setVisible(true);
			    }
			}
		}
		if (action.equals("CANCELAR")) {
			endProgram ();
		}
	}
	
	private ApplicationMessage tryConnection ( String userName, String userKey ) {
		ApplicationMessage aMsg = new ApplicationMessage ();
		FndUser usr = null;
		String decodedPass;
		try {
			UserAttributes.userName = userName.toUpperCase();
			// conectar con las credenciales del usuario logueado
			System.out.println("Obteniendo datos del usuario...");
			//usr = UserCredentialsDAO.getUser(userName, ctrl.getConn());
			usr = new FndUser();
			usr.setIDENTIFIER(1);
			usr.setNAME("APPMGR");
			usr.setPASSWORD("APPMGR");
			if (usr == null) {
				aMsg.setMessage("Error", "Usuario no definido", ApplicationMessage.ERROR);
				return aMsg;	
			} else {
				if (usr.getPASSWORD() == null) {
				    aMsg.setMessage("Error", "Clave de acceso no valida", ApplicationMessage.ERROR);
				    return aMsg;											
				} else {
					//decodedPass = Base64Utils.decoder(csh.getPASSWORD());
					decodedPass = usr.getPASSWORD();
					System.out.println(usr.getPASSWORD() + " - " + decodedPass + " - " + userKey);
					if (decodedPass.equals(userKey) == false) {
						aMsg.setMessage("Error", "Nombre de usuario o clave no valida", ApplicationMessage.ERROR);
						return aMsg;											
				    }
				}
			}
		    return null;
		} catch (Exception e) {
			//System.out.println("Error Conexion: " + e.getMessage());
			e.printStackTrace();
			StackTraceElement[] t = e.getStackTrace();
			String m = t[0].toString();
			System.out.println("StackTraceElement: " + m);
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}

	private ApplicationMessage startSession ( String userName ) {
		ApplicationMessage aMsg = new ApplicationMessage ();
		long sessionId = 0;
		String seccionCodigo = " ";
		try {
			// completar los datos necesarios para el registro de sesion
			/*
			UserSession s = new UserSession ();
			seccionCodigo = "OBTENER-CONTEXTO-USUARIO";
			UserAttributes.userName = userName;
			UserAttributes.getUserAttributes();
			AppConfig.initAppContext();
			seccionCodigo = "OBTENER-ID-SESION";
			sessionId = UtilitiesDAO.getNextval("SQ_POS_USERS_SESSIONS");
			s.setIDENTIFIER(sessionId);
			s.setORG_ID(UserAttributes.userOrg.getIDENTIFIER());
			s.setUNIT_ID(UserAttributes.userUnit.getIDENTIFIER());
			s.setUSER_NAME(userName.toUpperCase());
	    	    s.setCASH_REGISTER_ID(-1);
			s.setWORK_STATION(UserAttributes.workStation);
			s.setSTART_DATE(new java.util.Date());
			// crear un registro de sesion de usuario
			seccionCodigo = "CREAR-SESION";
			UserSessionDAO.insertRow(s);
			*/
			System.out.println("Obteniendo datos del contexto de sesion...");
			aMsg = SessionContext.getContextValues();
			if (aMsg != null) {
				return aMsg;
			}
			SessionContext.currentSessionId = sessionId;
			//System.out.println("sesion: " + AppConfig.sessionId);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			aMsg.setMessage("Error", seccionCodigo + "] " + e.getMessage(), ApplicationMessage.ERROR);
			return aMsg;
		}
	}
	
	public ImageIcon getScaledIcon (String iconName, int width, int height) {
		//ImageIcon icon = new ImageIcon(iconName);
		ImageIcon icon = new ImageIcon(getClass().getResource("/"+iconName));
		Image img = icon.getImage();  
		Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);  
		ImageIcon newIcon = new ImageIcon(newimg);
		return newIcon;
	}		
	
    public boolean isServerOnLine () {
        try {
        	return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
        } catch (Exception e) {
 		    return false;   			
        }
    }  
    
	public void endProgram () {
        ctrl.closeResources();
        //JOptionPane.showMessageDialog(null, "Fin de sesion. Hasta luego.", "Informacion - Punto de Ventas", JOptionPane.INFORMATION_MESSAGE);
	    ErrorMessageWindow x = new ErrorMessageWindow(null, "Fin de sesion. Hasta luego.");
	    x.setVisible(true);
	    System.exit(0);		
	}
}
