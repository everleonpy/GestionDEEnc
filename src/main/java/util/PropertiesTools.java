package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
* 
* @author eleon
*
*/
public class PropertiesTools 
{
	private MsgApp msnApp;
	
	public PropertiesTools() 
	{
		msnApp = new MsgApp();
	}
	
	/**
	* 
	* @param propFile
	* @return
	*/
	public Properties getData(String propFile) 
	{
		Properties prop = new Properties();
		FileInputStream fileProp = null;
		
			try 
			{
				
				fileProp = new FileInputStream(propFile);
				prop.load(fileProp);
				
			} catch (IOException e) {
				//e.printStackTrace();
				msnApp.ErrorMessage(e);
			}
		
		return prop;
	}

}
