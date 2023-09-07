package py.com.softpoint.guis;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.roshka.sifen.core.exceptions.SifenException;
import util.FondoSP;
import util.ImageTools;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static String pFile;
	private static MainMenu instancia;
	private ImageTools imgTools;
	
	private FondoSP fondo = new FondoSP();

	/**
	* Launch the application.
	* @throws SifenException 
	*/
	public static void main(String[] args) throws SifenException 
	{
		if( args.length > 0 ) 
		{
			System.out.println("Parameters : "+args[0]);
			pFile = args[0];
			
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MainMenu frame = new MainMenu();
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
	 * @throws IOException 
	*/
	public MainMenu() throws IOException 
	{
		this.setContentPane(fondo);
		
		instancia = this;
		imgTools = new ImageTools();
		setTitle("Menu Principal");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new FondoSP();
		//contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel jpHeader = new JPanel();
		contentPane.add(jpHeader, BorderLayout.NORTH);
		jpHeader.setLayout(new FlowLayout(FlowLayout.LEFT,4, 4));
		
		JMenuBar menuBarDE = new JMenuBar();
		jpHeader.add(menuBarDE);
		
		JMenu mnDocElectronicos = new JMenu("Doc. Electronicos");
		mnDocElectronicos.setIcon(new ImageIcon(imgTools.getImageFromFile("document.png")));
		menuBarDE.add(mnDocElectronicos);
		
		
		JMenuItem mntmConsultarRuc = new JMenuItem("Consultar RUC");
		mntmConsultarRuc.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/identity.png")));
		mntmConsultarRuc.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							ConsultaRUC consultaRUC = new ConsultaRUC(pFile, instancia);
							consultaRUC.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDocElectronicos.add(mntmConsultarRuc);
		
		JMenuItem mntmConsultaLote = new JMenuItem("Consultar LOTE");
		mntmConsultaLote.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/lote.png")));
		mntmConsultaLote.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ConsultaLote frmConsLote = new ConsultaLote(pFile, instancia);
							frmConsLote.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDocElectronicos.add(mntmConsultaLote);
		
		JMenuItem mntmConsultaCdc = new JMenuItem("Consultar CDC");
		mntmConsultaCdc.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/check.png")));
		mntmConsultaCdc.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							ConsultaCDC consultaCDC = new ConsultaCDC(pFile, instancia);
							consultaCDC.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDocElectronicos.add(mntmConsultaCdc);
		
		/*
		* Item de Menu para Enviar Facturas Electronicas 
		*/
		JMenuItem mntmEnvioFE = new JMenuItem("Enviar Lote de Fact. Elect.");
		mntmEnvioFE.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/send _m.png")));
		mntmEnvioFE.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							//ConsultaCDC consultaCDC = new ConsultaCDC(pFile, instancia);
							//consultaCDC.setVisible(true);
							EnvioFE envioFE = new EnvioFE(pFile, instancia);
							envioFE.setVisible(true);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDocElectronicos.add(mntmEnvioFE);
		
		/*
		* Item de Menu para Enviar Remisiones Electronicas 
		*/
		JMenuItem mntmEnvioREMI = new JMenuItem("Enviar Remisines Elect.");
		mntmEnvioREMI.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/shipped.png")));
		mntmEnvioREMI.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							//ConsultaCDC consultaCDC = new ConsultaCDC(pFile, instancia);
							//consultaCDC.setVisible(true);
							EnvioFE envioFE = new EnvioFE(pFile, instancia);
							envioFE.setVisible(true);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDocElectronicos.add(mntmEnvioREMI);
		
		
		
		JMenu mnEventos = new JMenu("Eventos");
		mnEventos.setIcon(new ImageIcon(imgTools.getImageFromFile("event.png")));
		menuBarDE.add(mnEventos);
		
		JMenuItem mntmCancelacionDe = new JMenuItem("Cancelacion DE");
		mntmCancelacionDe.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/cancel-doc.png")));
		mntmCancelacionDe.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
						
							EventoCancelacion eventoCancelacion = new EventoCancelacion(pFile, instancia);
							eventoCancelacion.setVisible(true);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnEventos.add(mntmCancelacionDe);
		
		JMenu mnHerramientas = new JMenu("Herramientas");
		mnHerramientas.setIcon(new ImageIcon(imgTools.getImageFromFile("tools.png")));
		menuBarDE.add(mnHerramientas);
		
		JMenuItem mntmCheckStatusF = new JMenuItem("Check Status F. Electronica");
		mntmCheckStatusF.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/check-status.png")));
		mntmCheckStatusF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							CheckStatusDE checkStatusDE = new CheckStatusDE(pFile, instancia);
							checkStatusDE.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		mnHerramientas.add(mntmCheckStatusF);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setIcon(new ImageIcon(imgTools.getImageFromFile("exit.png")));
		mntmSalir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				instancia.dispose();
				System.exit(0);
			}
		});
		jpHeader.add(mntmSalir);
		
		this.setLocationRelativeTo(null);
	}
		
}
