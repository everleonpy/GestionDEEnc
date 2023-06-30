package business;

import java.net.*; 

public class LocalHost { 
	
	public String getName() { 
		String macname; 
		try { 
			InetAddress inet= InetAddress.getLocalHost();
			macname=inet.getHostName();
		    return macname; 
		    } 
		catch( java.net.UnknownHostException e) { e.printStackTrace();}
		return null; }
	} 
