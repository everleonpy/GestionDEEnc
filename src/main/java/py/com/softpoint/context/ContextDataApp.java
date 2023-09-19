package py.com.softpoint.context;

public class ContextDataApp 
{
	private static UserApp userapp;
	
	
	
	/*public ContextDataApp(UserApp userapp) 
	{
		ContextDataApp.userapp = userapp;
	}
	*/
	
	public static UserApp getDataContext() 
	{
		return userapp;
	}



	public static void setUserapp(UserApp userapp) {
		ContextDataApp.userapp = userapp;
	}
}
