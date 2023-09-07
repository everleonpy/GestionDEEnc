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

public class EventoCancelacion extends JFrame {
	
	private static final long serialVersionUID = -1921381279271722263L;
	private JScrollPane scroll;
	private JFrame instancia;
	private ImageTools imgTools;
	private JTextPane etRespose;
	private static CheckStatusDETools statusTools;
	private JPanel contentPane;
	
	private JTextField etCDC;
	private JTextField etMotivoCancelacion;
	private JTextField etIDCancelacion;

	/**
	 * Create the frame.
	 * @throws SifenException 
	 * @throws IOException 
	 */
	public EventoCancelacion(String pFile, MainMenu mainMenu) throws SifenException, IOException 
	{
		statusTools = new CheckStatusDETools(pFile);
		mainMenu.dispose();
		instancia = this;
		imgTools = new ImageTools();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);setTitle("Evento CANCELACION");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contentPane = new FondoSP();//new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		etCDC = new JTextField();
		etCDC.setBounds(22, 43, 354, 40);
		contentPane.add(etCDC);
		etCDC.setColumns(10);
		
		JLabel lblNroDeLote = new JLabel(" CDC a Cancelar :");
		lblNroDeLote.setBounds(22, 12, 485, 28);
		contentPane.add(lblNroDeLote);
		
		etMotivoCancelacion = new JTextField();
		etMotivoCancelacion.setColumns(10);
		etMotivoCancelacion.setBounds(22, 120, 766, 40);
		contentPane.add(etMotivoCancelacion);
		
		JLabel lblMotivoDeLa = new JLabel("Motivo de la Cancelacion");
		lblMotivoDeLa.setBounds(22, 92, 485, 28);
		contentPane.add(lblMotivoDeLa);
		
		etIDCancelacion = new JTextField();
		etIDCancelacion.setColumns(10);
		etIDCancelacion.setBounds(412, 43, 90, 40);
		contentPane.add(etIDCancelacion);
		
		JLabel lblId = new JLabel("ID : ");
		lblId.setBounds(385, 48, 53, 28);
		contentPane.add(lblId);
		
		JButton btnConsultar = new JButton("Cancelar CDC");
		btnConsultar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			
				try {
					etRespose.setText(statusTools.eventoCancelacion(etCDC.getText().trim(), 
																	etMotivoCancelacion.getText().trim(), 
																	etIDCancelacion.getText().trim()));
					
				} catch (SifenException | IOException | SAXException | ParserConfigurationException ex) {
					StringWriter sw = new StringWriter();
			        ex.printStackTrace(new PrintWriter(sw));
					etRespose.setText( sw.toString() );
				}
			}
		});
		btnConsultar.setIcon(new ImageIcon(imgTools.getImageFromFile("cancel.png")));
		btnConsultar.setBounds(510, 42, 171, 40);
		contentPane.add(btnConsultar);
		
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
		
		etRespose = new JTextPane();
		etRespose.setEditable(false);
		scroll = new JScrollPane(etRespose);
		scroll.setBounds(22, 172, 766, 373);
		contentPane.add(scroll);
		
		this.setLocationRelativeTo(null);
		
	}
}
