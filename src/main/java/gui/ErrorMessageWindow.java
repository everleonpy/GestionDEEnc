package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ErrorMessageWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jpMessage = null;
	private JPanel jpLine = null;
	private JPanel jpButtons = null;
	
	private JButton jbClose = null;
	private JLabel jlMessage = null;
	private String msg;
	
    // Colores de la interfaz grafica
    private static Color WINDOW_BG = new Color(236, 19, 19);
    private static Color BODY_FG = Color.white;
    // Fuentes de la interfaz grafica
    private static Font NORMAL_FONT = new Font("Helvetica", Font.BOLD, 18);
    private static Font SMALL_FONT = new Font("Helvetica", Font.BOLD, 14);

	/**
	 * @param owner
	 */
	public ErrorMessageWindow(Frame owner, String msg) {
		super(owner);
		//super();
		this.msg = msg;
		initialize("Error");
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
		this.add(getjpMessage(), null);
		this.add(getjpLine(), null);
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
	private JPanel getjpMessage() {
		if (jpMessage == null) {
			//
			jlMessage = new JLabel();
			jlMessage.setHorizontalAlignment(SwingConstants.LEADING);
			jlMessage.setSize(new Dimension(580, 120));
			jlMessage.setLocation(new Point(10, 10));
			jlMessage.setForeground(BODY_FG);
			jlMessage.setFont(NORMAL_FONT);
			jlMessage.setText("<html>"+ this.msg +"</html>");
			//jlMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
            //
			jpMessage = new JPanel();
			jpMessage.setLayout(null);
			jpMessage.setBackground(WINDOW_BG);
			jpMessage.setSize(new Dimension(600, 120));
			jpMessage.setLocation(new Point(0, 0));
			//jpMessage.setBorder(BorderFactory.createLineBorder(Color.white, 1));
			jpMessage.add(jlMessage, null);
		}
		return jpMessage;
	}

	/**
	 * This method initializes jpLine	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getjpLine() {
		if (jpLine == null) {
			jpLine = new JPanel();
			jpLine.setLayout(null);
			jpLine.setBackground(BODY_FG);
			jpLine.setSize(new Dimension(600, 1));
			jpLine.setLocation(new Point(0, 120));
			//jpButtons.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		return jpLine;
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
			jpButtons.setBackground(WINDOW_BG);
			jpButtons.setSize(new Dimension(600, 60));
			jpButtons.setLocation(new Point(0, 121));
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
			jbClose.setBackground(BODY_FG);
			jbClose.setForeground(WINDOW_BG);
			jbClose.setFont(SMALL_FONT);
			jbClose.setSize(new Dimension(150, 50));
			jbClose.setLocation(new Point(440, 6));
			jbClose.setToolTipText("Cerrar Ventana");
			jbClose.setText("Aceptar");
			//jbClose.setSelected(true);
			jbClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose ();
				}
			});
		}
		return jbClose;
	}

	public static void main (String args []) {
		String d = "Este es un texto de mensaje de error generado para probar esta ventana.Ahora haremos que el texto a mostrar" +
	               " sea realmente largo para ver como se comporta el text wrapping del JLabel.";
		ErrorMessageWindow w = new ErrorMessageWindow(null, d);
		w.setVisible(true);
	}
}
