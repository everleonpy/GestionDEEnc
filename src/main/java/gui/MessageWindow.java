package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MessageWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jpTitle = null;
	private JPanel jpMessage = null;
	private JPanel jpLine1 = null;
	private JPanel jpLine2 = null;
	private JPanel jpButtons = null;
	
	private JButton jbClose = null;
	private JLabel jlTitle = null;
	private JLabel jlMessage = null;
	private String title;
	private String msg;
	
	// look and feel
	private AppLookAndFeel appLook;
    
	/**
	 * @param owner
	 */
	public MessageWindow(Frame owner, String title, String msg) {
		super(owner);
		//super();
		this.title = title;
		this.msg = msg;
		// set application theme
		appLook = new AppLookAndFeel("BLUE-SKY");
		initialize(title);
		setActions ();
		jbClose.requestFocusInWindow();
		setUndecorated(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize( String title ) {
		this.setSize(600, 180);
		this.setTitle(title);
		this.setModal(true);
		this.setLayout(null);
		this.setLocation(new Point(100, 100));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(false);
		//this.setVisible(true);
		this.setResizable(false);
		//
		this.add(getjpTitle(), null);
		this.add(getjpLine1(), null);
		this.add(getjpMessage(), null);
		this.add(getjpLine2(), null);
		this.add(getjpButtons(), null);

	}

	private void setActions () {
		ActionListener lst = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose ();
			}
		};
		//getRootPane().setDefaultButton(jbClose);
		getRootPane().registerKeyboardAction(lst, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),	JComponent.WHEN_IN_FOCUSED_WINDOW);		
	}

	/**
	 * This method initializes jpMessage	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpTitle() {
		if (jpTitle == null) {
			//
			jlTitle = new JLabel();
			jlTitle.setHorizontalAlignment(SwingConstants.LEADING);
			jlTitle.setSize(new Dimension(580, 40));
			jlTitle.setLocation(new Point(10, 5));
			jlTitle.setForeground(appLook.getMsgWindowFg());
			jlTitle.setFont(appLook.getHeaderFont2());
			jlTitle.setText(this.title);
			//jlMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
            //
			jpTitle = new JPanel();
			jpTitle.setLayout(null);
			jpTitle.setBackground(appLook.getMsgWindowBg());
			jpTitle.setSize(new Dimension(600, 50));
			jpTitle.setLocation(new Point(0, 0));
			//jpMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
			jpTitle.add(jlTitle, null);
		}
		return jpTitle;
	}

	/**
	 * This method initializes jpLine1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpLine1() {
		if (jpLine1 == null) {
			jpLine1 = new JPanel();
			jpLine1.setLayout(null);
			jpLine1.setBackground(appLook.getMsgWindowFg());
			jpLine1.setSize(new Dimension(600, 1));
			jpLine1.setLocation(new Point(0, 51));
			jpLine1.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		return jpLine1;
	}

	/**
	 * This method initializes jpMessage	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpMessage() {
		if (jpMessage == null) {
			//
			jlMessage = new JLabel();
			jlMessage.setHorizontalAlignment(SwingConstants.LEADING);
			jlMessage.setSize(new Dimension(580, 66));
			jlMessage.setLocation(new Point(10, 2));
			jlMessage.setForeground(appLook.getMsgWindowFg());
			jlMessage.setFont(appLook.getRegularFont());
			jlMessage.setText("<html>"+ this.msg +"</html>");
			//jlMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
            //
			jpMessage = new JPanel();
			jpMessage.setLayout(null);
			jpMessage.setBackground(appLook.getMsgWindowBg());
			jpMessage.setSize(new Dimension(600, 70));
			jpMessage.setLocation(new Point(0, 51));
			//jpMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
			jpMessage.add(jlMessage, null);
		}
		return jpMessage;
	}

	/**
	 * This method initializes jpLine2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpLine2() {
		if (jpLine2 == null) {
			jpLine2 = new JPanel();
			jpLine2.setLayout(null);
			jpLine2.setBackground(appLook.getMsgWindowFg());
			jpLine2.setSize(new Dimension(600, 1));
			jpLine2.setLocation(new Point(0, 122));
			jpLine2.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		return jpLine2;
	}

	/**
	 * This method initializes jpButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpButtons() {
		if (jpButtons == null) {
			jpButtons = new JPanel();
			jpButtons.setLayout(null);
			jpButtons.setBackground(appLook.getMsgWindowBg());
			jpButtons.setSize(new Dimension(600, 55));
			jpButtons.setLocation(new Point(0, 123));
			//jpButtons.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jpButtons.add(getjbClose(), null);
		}
		return jpButtons;
	}

	/**
	 * This method initializes jbClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbClose() {
		if (jbClose == null) {
			jbClose = new JButton();
			jbClose.setBackground(Color.white);
			jbClose.setForeground(appLook.getMsgWindowBg());
			jbClose.setFont(appLook.getSmallFont());
			jbClose.setSize(new Dimension(150, 46));
			jbClose.setLocation(new Point(440, 4));
			jbClose.setToolTipText("Cerrar Ventana");
			jbClose.setText("Aceptar");
			jbClose.setSelected(false);
			jbClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose ();
				}
			});
		}
		return jbClose;
	}

	public static void main (String args []) {
		String t = "Ventana de Mensajes";
		String d = "Este es un texto de mensaje de error generado para probar esta ventana.Ahora haremos que el texto a mostrar" +
	               " sea realmente largo para ver como se comporta el text wrapping del JLabel.";
		MessageWindow w = new MessageWindow(null, t, d);
		w.setVisible(true);
	}
}
