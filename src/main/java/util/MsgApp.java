package util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

/**
* Clase encargada de desplegar los distitos tipos de mensaje de la applicacione 
* @author eleon
*
*/
public class MsgApp 
{
	
	public void ErrorMessage(Exception e) 
	{
		//StringBuilder sbEx = new StringBuilder();
		//sbEx.append(e.getClass().getName()+"\n");
		//sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
		
		StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
		JOptionPane.showMessageDialog(null, StringTools.cortarString(sw.toString(), 200),
											" [ ERROR ] ", 
											JOptionPane.ERROR_MESSAGE);
	}
	
	
	public void ErrorMessage(String msg) 
	{
		JOptionPane.showMessageDialog(null,msg,
				" [ ERROR ] ", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	
	public void InfoMessage(String msg) 
	{
		JOptionPane.showMessageDialog(null,msg,
				" [ ATENCION ] ", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	

}
