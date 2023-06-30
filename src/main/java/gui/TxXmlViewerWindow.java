package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import business.AppConfig;
import business.TxXmlViewerController;
import dao.FacturaElectronicaDAOOld;
import dao.PrintDE;
import pojo.DocumElectronico;
import business.ApplicationMessage;
import business.TestSocket;

public class TxXmlViewerWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpItems = null;
	private JPanel jpButtons = null;
	private JLabel jlTransactionNo = null;
	private JTextField jtTransactionNo = null;

	private JTextArea jaXmlContent = null;
	private JButton jbClose = null;
	
	private TxXmlViewerController ctrl;
	
	private int wWidth, wHeight;
	private int p1Width, p1Height;
	private int p2Width, p2Height;
	private int labelsX, labelsY;
	private int lWidth, lHeight;
	private int wOffset;
	
	private int textFieldsX, textFieldsY;
	private int tfWidth, tfHeight;
	
	// look and feel
	private AppLookAndFeel appLook;
	private OvalBorder lineBorder = new OvalBorder();

	/**
	 * @param owner
	 */
	public TxXmlViewerWindow ( Frame owner, TxXmlViewerController ctrl ) {
		super();
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		//appLook = new AppLookAndFeel("GREEN-FOREST");
		wWidth = 1000;
		wHeight = 730;
		p1Width = wWidth;
		double val = wHeight * 0.88;
		p1Height = (int) val;
		p2Width = wWidth;
		val = wHeight * 0.12;
		p2Height = (int) val;
		//
		this.ctrl = ctrl;
		initialize();
		initializeItems();
		generateXmlContent();
		jaXmlContent.repaint();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(wWidth, wHeight);
		this.setLocation(new Point(100, 100));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle("Visor de Archivo XML");
		this.setModal(true);
		this.setLayout(new BorderLayout());
		this.add(getJContentPane());
		//
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				//dispose ();
				endProgram ();
			}
		});
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			this.add(getJpItems(), null);
			this.add(getJpButtons(), null);
		}
		return jContentPane;
	}	

	private JPanel getJpItems () {
		if (jpItems == null) {
			labelsX = 85;
			labelsY = 65;
			wOffset = 37;
			lWidth = 120;
			lHeight = 35;
						
			jlTransactionNo = new JLabel();
			jlTransactionNo.setLocation(new Point(labelsX, labelsY));
			jlTransactionNo.setSize(new Dimension(lWidth, lHeight));
			jlTransactionNo.setForeground(appLook.getBodyFg());
			jlTransactionNo.setFont(appLook.getRegularFont());
			jlTransactionNo.setText("No. Transaccion");
			jlTransactionNo.setHorizontalAlignment(SwingConstants.RIGHT);

			jpItems = new JPanel();
			jpItems.setLayout(null);
			jpItems.setSize(new Dimension(p1Width, p1Height));
			jpItems.setLocation(new Point(0, 0));
			jpItems.setPreferredSize(new Dimension(p1Width, p1Height));			
			jpItems.setOpaque(false);
			jpItems.add(jlTransactionNo, null);
			//
			textFieldsX = 222;
			textFieldsY = 64;
			wOffset = 37;
			tfWidth = 430;
			tfHeight = 35;
			jpItems.add(getjtTransactionNo(), null);
			textFieldsY = textFieldsY + wOffset;
			JScrollPane s = new JScrollPane (getJaXmlContent(), 
					   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			s.setSize(new Dimension((p1Width - 20), (tfHeight * 15)));
			s.setLocation(new Point(10, textFieldsY));
			s.setVisible(true);
			jpItems.add(s, null);

		}
		return jpItems;
	}

	private JTextField getjtTransactionNo() {
		if (jtTransactionNo == null) {
			jtTransactionNo = new JTextField();
			jtTransactionNo.setSize(new Dimension(tfWidth, tfHeight));
			jtTransactionNo.setLocation(new Point(textFieldsX, textFieldsY));
			jtTransactionNo.setBackground(appLook.getBodyBg());
			jtTransactionNo.setForeground(appLook.getBodyFg());
			jtTransactionNo.setFont(appLook.getRegularFont());
			jtTransactionNo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray));
			jtTransactionNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jaXmlContent.requestFocusInWindow();
				}
			});
		}
		return jtTransactionNo;
	}

	private JTextArea getJaXmlContent() {
		if (jaXmlContent == null) {
			jaXmlContent = new JTextArea();
			jaXmlContent.setName("jtSequence");
			jaXmlContent.setSize(new Dimension((p1Width - 40), (wHeight * 15)));
			jaXmlContent.setLocation(new Point(10, textFieldsY));
			jaXmlContent.setBackground(appLook.getBodyBg());
			jaXmlContent.setForeground(appLook.getBodyFg());
			jaXmlContent.setFont(new Font("Courier", Font.PLAIN, 14));
			jaXmlContent.setBorder(lineBorder);
			//jaXmlContent.setLineWrap(true);
			jaXmlContent.setVisible(true);
		}
		return jaXmlContent;
	}
	
	/**
	 * This method initializes jpButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpButtons() {
		if (jpButtons == null) {
			jpButtons = new JPanel();
			jpButtons.setLayout(null);
			jpButtons.setSize(new Dimension(p2Width, p2Height));
			jpButtons.setLocation(new Point(0, (p1Height - 10)));
			//jpButtons.setBackground(appLook.getFooterBg());
			jpButtons.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.lightGray));
			jpButtons.add(getjbClose(), null);
		}
		return jpButtons;
	}

	private JButton getjbClose() {
		if (jbClose == null) {
			jbClose = new JButton();
			jbClose.setSize(new Dimension(150, 50));
			jbClose.setLocation(new Point(20, 15));
			jbClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			jbClose.setText("Cerrar");
			jbClose.setToolTipText("Cerrar ventana");
			jbClose.setBackground(appLook.getPrimaryBg());
			jbClose.setForeground(appLook.getPrimaryBg());
			jbClose.setFont(appLook.getRegularFont());
			jbClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					endProgram ();
				}
			});
		}
		return jbClose;
	}

	private void initializeItems () {
		jtTransactionNo.setText(ctrl.getTransactionNo());
		jtTransactionNo.repaint();
	}

	private ApplicationMessage verifyData (  ) {
	    ApplicationMessage aMsg;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		
	    /*
	    if ( dataType == null ) {
	    	aMsg = new ApplicationMessage ();
			aMsg.setMessage("VERF-DATA", "Debe indicar el tipo de informacion a enviar", ApplicationMessage.ERROR);
			return aMsg;
	    }
	    */
		return null;
	}
	
	private void generateXmlContent ( ) {
		DocumElectronico d = FacturaElectronicaDAOOld.getRcvInvoice(ctrl.getTransactionId());
		if ( d != null) {
			String x = PrintDE.printObject(d);
			jaXmlContent.setText(x);
		}
	}
		
	private boolean isValidDateLength ( String data ) {
		if (data.length() == 6 | data.length() == 8 | data.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
	
	private String formatDateValue (String v) {
		String dateValue = null;
		String dd = null;
		String mm = null;
		String yy = null;
		int year = 0;
		//
		if (v.length() == 6 | v.length() == 8) {
			dd = v.substring(0, 2);
			mm = v.substring(2, 4);
			yy = v.substring(4);
			//
			if (Integer.valueOf(yy) <= 0) {
				return null;
			}
			//
			if (v.length() == 6) {
				if (Integer.valueOf(yy) >= 50) {
					year = Integer.valueOf("19" + yy);
				} else {
					year = Integer.valueOf("20" + yy);					
				}
				yy = String.valueOf(year);
			}			
			if (v.length() == 8) {
				year = Integer.valueOf(yy);
			}
			//
			dd = v.substring(0, 2);
			if (Integer.valueOf(dd) < 1 | Integer.valueOf(dd) > 31) {
				return null;
			}
			mm = v.substring(2, 4);
			if (Integer.valueOf(mm) < 1 | Integer.valueOf(mm) > 12) {
				return null;
			} else {
				if (Integer.valueOf(mm) == 2) {
					if ((year % 4) == 0 & (year % 100) != 0) {
						if (Integer.valueOf(dd) > 28) {
							return null;
						}						
					} else {
						if (Integer.valueOf(dd) > 29) {
							return null;
						}												
					}
				}
			}
			//
			dateValue = dd + "/" + mm + "/" + String.valueOf(year);
			return dateValue;
		} else {
			return null;
		}
	}
	
	public boolean isConnectionAvailable () {
	      try {
	    	  return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
	      } catch (Exception e) {
	 		  return false;   			
	      }
	   }  	
	
	private void endProgram () {
		dispose ();
	}

}
