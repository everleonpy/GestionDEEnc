package py.com.softpoint.context;

import java.util.Properties;

import util.MsgApp;
import util.PropertiesTools;


/**
* private String URL_BASE = "http://localhost/customer_services/api.php/";
* @author eleon
*
*/
public class UserApiClient 
{

	private MsgApp msgApp;
	private PropertiesTools propTools;
	private Properties prop;

	public UserApiClient() 
	{
		msgApp = new MsgApp();
		propTools = new PropertiesTools();
		prop = propTools.getData("app.properties");
	}
	
	/**
	* Recpera los datos de un usuario via un api 
	* @param username
	* @param password
	* @return
	*/
	public UserApp getUser(String username, String password) 
	{
		UserApp user = null;
		ApiClient<UserApp> client = new ApiClient<UserApp>();
		if( prop != null ) 
		{	
			user = client.get(prop.getProperty("URI_BASE")+"?username="+username, UserApp.class);
			if( user != null ) 
			{
				if( user.getPassword().equalsIgnoreCase(password) ) 
				{
					return user;
				}else 
				{
					msgApp.InfoMessage("La contraseña es INCORRECTA...");
					return null;
				}
			}else
			{
				msgApp.ErrorMessage("Usuario no EXISTE");
				
			}
			client.close();
		}
		return user;
	}

}
