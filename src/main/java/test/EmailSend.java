package test;

import java.util.ArrayList;
import java.util.List;
import util.SendEmail;

/**
* 
* @author eleon
*
*/
public class EmailSend 
{
	public static void main(String[] args) 
	{
		
		SendEmail email = new SendEmail();
		
		List<String> atachment = new ArrayList<String>();
		atachment.add("/home/eleon/tmps/228801.pdf");
		
		email.enviarEmail("everleonpy@gmail.com", 
						  "Prueba de Envio de correo electronicio", 
						  "Cuerpo del correo electronico", atachment);
	}

}
