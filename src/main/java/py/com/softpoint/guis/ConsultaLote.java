package py.com.softpoint.guis;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.roshka.sifen.core.exceptions.SifenException;
import util.CheckStatusDETools;
import util.FondoSP;
import util.ImageTools;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.awt.event.ActionEvent;
import java.awt.FontFormatException;


public class ConsultaLote extends JFrame {

	private static final long serialVersionUID = 1574211046239093962L;
	private JPanel contentPane;
	private JTextField etNroLOTE;
	private JScrollPane scroll;
	private static CheckStatusDETools statusTools;
	private JTextPane etRespose;
	private JFrame instancia;
	private ImageTools imgTools;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ConsultaLote frame = new ConsultaLote("", );
					//.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	* Create the frame.
	* @throws SifenException 
	 * @throws IOException 
	 * @throws FontFormatException 
	*/
	public ConsultaLote(String pFile, MainMenu mainMenu) throws SifenException, IOException, FontFormatException 
	{
		//if( pFile.length() == 0 && pFile != null ) 
		//{ 
			statusTools = new CheckStatusDETools(pFile);
			instancia = this;
			imgTools = new ImageTools();
		
			mainMenu.dispose();
			setResizable(false);
			setTitle("Consulta LOTE");
			setAlwaysOnTop(true);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 800, 600);
			contentPane = new FondoSP();//new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			etNroLOTE = new JTextField();
			etNroLOTE.setBounds(22, 43, 485, 40);
			contentPane.add(etNroLOTE);
			etNroLOTE.setColumns(10);
			
			JLabel lblNroDeLote = new JLabel("NRO DE LOTE");
			lblNroDeLote.setBounds(22, 16, 542, 15);
			contentPane.add(lblNroDeLote);
			
			JButton btnConsultar = new JButton("Cons. LOTE");
			btnConsultar.setIcon(new ImageIcon(imgTools.getImageFromFile("execute.png")));
			btnConsultar.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					try 
					{
						etRespose.setText(statusTools.checkLote(etNroLOTE.getText().trim()));
					} catch (SifenException | IOException | SAXException | ParserConfigurationException ex) {
						
						  StringWriter sw = new StringWriter();
				          ex.printStackTrace(new PrintWriter(sw));
						  etRespose.setText( sw.toString() );
					
					}
				}
			});
			btnConsultar.setBounds(510, 42, 171, 40);
			contentPane.add(btnConsultar);
			
			etRespose = new JTextPane();
			etRespose.setEditable(false);

			//etRespose.setBounds(331, 105, 457, 436);
			//contentPane.add(etRespose);
		
			scroll = new JScrollPane(etRespose);
			scroll.setBounds(22, 95, 766, 433);
			contentPane.add(scroll);
			
			JButton btnExit = new JButton("Salir");
			btnExit.setIcon(new ImageIcon(imgTools.getImageFromFile("exit.png")));
			btnExit.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					instancia.dispose();
					mainMenu.setVisible(true);
				}
			});
			btnExit.setBounds(686, 42, 102, 40);
			contentPane.add(btnExit);
			
			this.setLocationRelativeTo(null);
		//}
	}
}
