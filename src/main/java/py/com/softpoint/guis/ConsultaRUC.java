package py.com.softpoint.guis;


import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.roshka.sifen.core.exceptions.SifenException;

import py.com.softpoint.context.ContextDataApp;
import util.CheckStatusDETools;
import util.FondoSP;
import util.ImageTools;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
* 
* @author eleon
*
*/
public class ConsultaRUC extends JFrame {

	private static final long serialVersionUID = -9193761440589419547L;
	private JPanel contentPane;
	private JTextField etRUC;
	private ImageTools imgTools;
	private JTextPane etRespose;
	private JScrollPane scroll;
	private JFrame instancia;
	private static CheckStatusDETools statusTools;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ConsultaRUC frame = new ConsultaRUC();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	* Create the frame.
	* @throws IOException 
	* @throws SifenException 
	*/
	public ConsultaRUC(String pFile, MainMenu mainMenu) throws IOException, SifenException 
	{
		statusTools = new CheckStatusDETools(pFile);
		mainMenu.dispose();
		instancia = this;
		imgTools = new ImageTools();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);setTitle("Consultar RUC ");
		setAlwaysOnTop(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contentPane = new FondoSP();//new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		etRUC = new JTextField();
		etRUC.setBounds(22, 43, 485, 40);
		contentPane.add(etRUC);
		etRUC.setColumns(10);
		
		JLabel lblNroDeLote = new JLabel(" RUC :");
		lblNroDeLote.setBounds(22, 12, 485, 28);
		contentPane.add(lblNroDeLote);
		
		JButton btnConsultar = new JButton("Cons. RUC");
		btnConsultar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					etRespose.setText(statusTools.checkRUC(etRUC.getText().trim()));
				} catch (SifenException | IOException | SAXException | ParserConfigurationException ex) {
					StringWriter sw = new StringWriter();
			        ex.printStackTrace(new PrintWriter(sw));
					etRespose.setText( sw.toString() );
				}
			}
		});
		btnConsultar.setIcon(new ImageIcon(imgTools.getImageFromFile("execute.png")));
		btnConsultar.setBounds(510, 42, 171, 40);
		contentPane.add(btnConsultar);
		
		
		etRespose = new JTextPane();
		etRespose.setEditable(false);
		scroll = new JScrollPane(etRespose);
		scroll.setBounds(22, 95, 766, 433);
		contentPane.add(scroll);
		
		JButton btnExit = new JButton("Salir");
		btnExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				instancia.dispose();
				mainMenu.setVisible(true);
			}
		});
		btnExit.setIcon(new ImageIcon(imgTools.getImageFromFile("exit.png")));
		btnExit.setBounds(686, 42, 102, 40);
		contentPane.add(btnExit);
		
		JLabel lblUserName = new JLabel();
		lblUserName.setBounds(22, 540, 388, 24);
		lblUserName.setText(ContextDataApp.getDataContext().getNombre_Completo());
		contentPane.add(lblUserName);
		
		this.setLocationRelativeTo(null);
		
	}
}
