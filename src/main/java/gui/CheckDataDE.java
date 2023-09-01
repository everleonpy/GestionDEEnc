package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.roshka.sifen.core.exceptions.SifenException;
import util.CheckStatusDETools;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class CheckDataDE extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
	private static CheckStatusDETools statusTools;
	private static String propFile;
	private static JTextPane txtResponse;
	private JTextField txtIdCancelacion;
	private JTextField txtFechaVerificar;
	private JScrollPane scroll;
	private JLabel lblStatus;
	
	/**
	* Launch the application.
	* @throws SifenException 
	*/
	public static void main(String[] args) throws SifenException 
	{	
		
		if( args.length > 0 ) 
		{
			System.out.println("Parameters : "+args[0]);
			propFile = args[0];
			if( propFile != null ) 
			{
				statusTools = new CheckStatusDETools(propFile);
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CheckDataDE frame = new CheckDataDE();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		}else {
			System.err.println("ERROR: Ingrese un parametro ");
		}
	}

	/**
	* Create the frame.
	*/
	public CheckDataDE() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtInput = new JTextField();
		txtInput.setBounds(28, 37, 595, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nro de Lote /  CDC / RUC ");
		lblNewLabel.setBounds(28, 10, 415, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnLote = new JButton("Consultar LOTE\n");
		btnLote.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					
					txtResponse.setText(statusTools.checkLote(txtInput.getText().trim()));
					
				} catch (SifenException | IOException | SAXException | ParserConfigurationException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnLote.setBounds(635, 37, 273, 40);
		contentPane.add(btnLote);
		
		txtResponse = new JTextPane();
		//txtResponse.setBounds(28, 112, 595, 357);
		
		scroll = new JScrollPane(txtResponse);
		scroll.setBounds(28, 112, 595, 357);
		
		//contentPane.add(txtResponse);
		contentPane.add(scroll);
		
		JLabel lblRespuesta = new JLabel("Respuesta : ");
		lblRespuesta.setBounds(28, 89, 415, 15);
		contentPane.add(lblRespuesta);
		
		JButton btnCDC = new JButton("Consultar CDC\n");
		btnCDC.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					txtResponse.setText(statusTools.checkCDC(txtInput.getText().trim()));
				} catch (SifenException | IOException | SAXException | ParserConfigurationException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnCDC.setBounds(635, 102, 273, 40);
		contentPane.add(btnCDC);
		
		JButton btnRUC = new JButton("Consultar RUC\n");
		btnRUC.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					txtResponse.setText(statusTools.checkRUC(txtInput.getText().trim()));
				} catch (SifenException | IOException | SAXException | ParserConfigurationException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
		
		btnRUC.setBounds(635, 161, 273, 40);
		contentPane.add(btnRUC);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(28, 532, 880, 28);
		contentPane.add(lblStatus);
		
		JButton btnExit = new JButton("Salir");
		btnExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnExit.setBounds(635, 429, 273, 40);
		contentPane.add(btnExit);
		
		JButton btnCancelDE = new JButton("Evento Cancelacion DE");
		btnCancelDE.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					txtResponse.setText(statusTools.eventoCancelacion(txtInput.getText().trim(), txtResponse.getText().trim(), txtIdCancelacion.getText().trim()));
				} catch (IOException | SAXException | ParserConfigurationException | SifenException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnCancelDE.setBounds(635, 219, 273, 40);
		contentPane.add(btnCancelDE);
		
		txtIdCancelacion = new JTextField();
		txtIdCancelacion.setBounds(130, 481, 206, 39);
		contentPane.add(txtIdCancelacion);
		txtIdCancelacion.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("ID Cancelacion : ");
		lblNewLabel_1.setBounds(12, 481, 116, 28);
		contentPane.add(lblNewLabel_1);
		
		JButton btnStatusCheckFE = new JButton("Check Status F. Electronica");
		btnStatusCheckFE.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				try {
					//TODO mandar la fecha a procesar como parametro
					txtResponse.setText(statusTools.checkCDCtoDate(txtFechaVerificar.getText().trim()));
				} catch (SifenException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnStatusCheckFE.setBounds(635, 377, 273, 40);
		contentPane.add(btnStatusCheckFE);
		
		txtFechaVerificar = new JTextField();
		txtFechaVerificar.setBounds(635, 481, 273, 39);
		contentPane.add(txtFechaVerificar);
		txtFechaVerificar.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Fecha a Verificar (dd/mm/yyyy) : ");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setBounds(364, 481, 259, 40);
		contentPane.add(lblNewLabel_1_1);
			
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(JLabel lblStatus) {
		this.lblStatus = lblStatus;
	}
}
