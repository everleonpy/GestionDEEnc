package py.com.softpoint.guis;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.roshka.sifen.core.exceptions.SifenException;

import util.CheckStatusDETools;
import util.DateTools;
import util.FondoSP;
import util.ImageTools;

public class EnvioFE extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scroll;
	private JFrame instancia;
	private ImageTools imgTools;
	private JTextPane etRespose;
	private static CheckStatusDETools statusTools;
	private JTextField etFecha;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//EnvioFE frame = new EnvioFE();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	* Clase GUI encargada del gestionar el envio de las FE
	* @throws SifenException 
	* @throws IOException 
	*/
	public EnvioFE(String pFile, MainMenu mainMenu) throws SifenException, IOException 
	{
		statusTools = new CheckStatusDETools(pFile);
		mainMenu.dispose();
		instancia = this;
		imgTools = new ImageTools();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);setTitle(" Envio de Facturas Electronicas ");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contentPane = new FondoSP();//new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		/*
		* Fecha Actual
		*/
		String fechaDia = DateTools.getString(new Date(), null);
		System.out.println("FECHA : "+fechaDia);
		etFecha = new JTextField(fechaDia);
		etFecha.setBounds(22, 43, 485, 40);
		contentPane.add(etFecha);
		etFecha.setColumns(10);
		
		JLabel lblNroDeLote = new JLabel("Fecha a Verificar (dd/mm/yyyy) :");
		lblNroDeLote.setBounds(22, 12, 485, 28);
		contentPane.add(lblNroDeLote);
		
		JButton btnConsultar = new JButton("Enviar FE");
		btnConsultar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(etFecha.getText().trim().length() > 0) 
				{
					try 
					{
						//Acan disparamos el proceso de envio
						etRespose.setText(statusTools.checkCDCtoDate(etFecha.getText().trim()));
						
					} catch (SifenException  ex) {
						StringWriter sw = new StringWriter();
				        ex.printStackTrace(new PrintWriter(sw));
						etRespose.setText( sw.toString() );
					}
				}else {etRespose.setText("ERROR : Ingrese una Fecha Valida");}
			}
		});
		btnConsultar.setIcon(new ImageIcon(imgTools.getImageFromFile("send.png")));
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
