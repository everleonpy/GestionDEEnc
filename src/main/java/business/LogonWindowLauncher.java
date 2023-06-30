package business;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.LogonWindow;

/*
 * Esta es la clase principal que se encarga de instanciar 
 * la clase de control y la clase de interfaz de usuario
 */
public class LogonWindowLauncher {
	
	public LogonWindowLauncher () {
		new LogonWindow ( );
	}

	public static void main (String args []) {
		ApplicationMessage aMsg = new ApplicationMessage();
		try {
			ApplicationInfo.setApplicationInfo();
			// buscar los parametros generales de la aplicacion
			AppConfig.loadConfig(); 
			AppConfig.initAppContext(); 
			UIManager.setLookAndFeel(AppConfig.lookAndFeel);
			new LogonWindowLauncher();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			JOptionPane.showMessageDialog(null, aMsg.getText(), "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			JOptionPane.showMessageDialog(null, aMsg.getText(), "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
		} catch (InstantiationException e) {
			e.printStackTrace();
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			JOptionPane.showMessageDialog(null, aMsg.getText(), "Error 1 - Avanza Tools", JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			aMsg.setMessage("Error", e.getMessage(), ApplicationMessage.ERROR);
			JOptionPane.showMessageDialog(null, aMsg.getText(), "Error 1 - Punto de Ventas", JOptionPane.ERROR_MESSAGE);
        }
	}

}
