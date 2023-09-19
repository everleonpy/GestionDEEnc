package py.com.softpoint.guis;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import py.com.softpoint.context.ContextDataApp;
import py.com.softpoint.context.UserApiClient;
import py.com.softpoint.context.UserApp;
import util.ImageTools;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
* URI : http://hostserver/customer_services/api.php/?username=appmgr
* @author eleon
*
*/
public class LoginApp extends JFrame 
{
	private static final long serialVersionUID = 337424497663284616L;
	private JPanel contentPane;
	private JTextField etUser;
	private JPasswordField etPassword;
	private LoginApp instance;
	
	private ImageTools imgTools;
	@SuppressWarnings("unused")
	private static String pFile;
	private UserApiClient apiclient;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		if( args.length > 0 ) 
		{
			
			pFile = args[0];
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try 
						{
							LoginApp frame = new LoginApp();
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
	public LoginApp() throws IOException 
	{
		imgTools = new ImageTools();
		apiclient = new UserApiClient();
		instance = this;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(12, 270, 428, 306);
		contentPane.add(panel);
		panel.setLayout(null);
		
		etUser = new JTextField();
		etUser.setBounds(75, 66, 341, 46);
		panel.add(etUser);
		etUser.setColumns(10);
		
		JLabel userIcon = new JLabel("");
		userIcon.setHorizontalAlignment(SwingConstants.CENTER);
		userIcon.setBounds(12, 66, 61, 46);
		userIcon.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/user.png")));
		panel.add(userIcon);
		
		etPassword = new JPasswordField();
		etPassword.setBounds(75, 133, 341, 46);
		panel.add(etPassword);
		
		JLabel passwordIcon = new JLabel("");
		passwordIcon.setHorizontalAlignment(SwingConstants.CENTER);
		passwordIcon.setBounds(12, 133, 61, 46);
		passwordIcon.setIcon(new ImageIcon(imgTools.getImageFromFile("icons/password.png")));
		panel.add(passwordIcon);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				@SuppressWarnings("deprecation")
				UserApp user = apiclient.getUser(etUser.getText().trim(), 
								  etPassword.getText().trim());
				
					if( user != null ) 
					{
						
						ContextDataApp.setUserapp(user);
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try 
								{
									instance.dispose();
									MainMenu frame = new MainMenu(pFile);
									frame.setVisible(true);
	
								} catch (Exception e) {
									e.printStackTrace();
									
								}
							}
						});
						
					}
				
			}
		});
		btnNewButton.setBounds(12, 225, 404, 60);
		panel.add(btnNewButton);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(97, 12, 258, 238);
		lblLogo.setIcon(getLogo(lblLogo));
		contentPane.add(lblLogo);
		
		this.setLocationRelativeTo(null);
		
	}
	
	
	private ImageIcon getLogo(JLabel lbl) throws IOException 
	{
		ImageIcon icon = new ImageIcon(imgTools.getImageFromFile("icons/LogoSP.png"));
		Dimension size = lbl.getSize();
		Image imgNew = icon.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
		ImageIcon resp = new ImageIcon(imgNew);
	
		return resp;
	}
	
	
}
