package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.RcvTrxEbBatchItemsDAO;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class CheckStatusDeGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private  RcvTrxEbBatchItemsDAO dao; // = new RcvTrxEbBatchItemsDAO();
	private JPanel contentPane;
	
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CheckStatusDeGui frame = new CheckStatusDeGui();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CheckStatusDeGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1270, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fecha a Verificar :  ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(39, 22, 184, 36);
		contentPane.add(lblNewLabel);
		
		JFormattedTextField txtDateCheck = new JFormattedTextField();
		txtDateCheck.setBounds(227, 23, 237, 36);
		txtDateCheck.setValue(new Date());
		contentPane.add(txtDateCheck);
		
		JButton btnCheckOn = new JButton("Procesar");
		btnCheckOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesarChequeo();
			}
		});
		btnCheckOn.setBounds(280, 100, 184, 46);
		contentPane.add(btnCheckOn);
	}
	
	/*
	* 
	*/
	private void procesarChequeo() 
	{
		
	}
	
}
