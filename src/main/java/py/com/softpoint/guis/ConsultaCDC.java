package py.com.softpoint.guis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import util.CheckStatusDETools;
import util.FondoSP;
import util.ImageTools;

public class ConsultaCDC extends JFrame {

	private static final long serialVersionUID = -6051341536646722094L;
	private JScrollPane scroll;
	private JFrame instancia;
	private ImageTools imgTools;
	private JTextPane etRespose;
	private JPanel contentPane;
	private static CheckStatusDETools statusTools;
	private JTextField etCDC;
	
	/**
	* Create the frame.
	 * @throws SifenException 
	 * @throws IOException 
	*/
	public ConsultaCDC(String pFile, MainMenu mainMenu) throws SifenException, IOException 
	{
		statusTools = new CheckStatusDETools(pFile);
		mainMenu.dispose();
		instancia = this;
		imgTools = new ImageTools();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);setTitle("Consultar CDC");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contentPane = new FondoSP();//new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		etCDC = new JTextField();
		etCDC.setBounds(22, 43, 485, 40);
		contentPane.add(etCDC);
		etCDC.setColumns(10);
		
		JLabel lblNroDeLote = new JLabel(" CDC :");
		lblNroDeLote.setBounds(22, 12, 485, 28);
		contentPane.add(lblNroDeLote);
		
		JButton btnConsultar = new JButton("Cons. CDC");
		btnConsultar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					etRespose.setText(statusTools.checkCDC(etCDC.getText().trim()));
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
		
		this.setLocationRelativeTo(null);
	}

}
