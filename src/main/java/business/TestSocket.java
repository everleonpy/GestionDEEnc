package business;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class TestSocket {
	public static boolean isSocketAlive (String hostName, int port) {
		boolean isAlive = false;
 
		// Creates a socket address from a hostname and a port number
		SocketAddress socketAddress = new InetSocketAddress(hostName, port);
		Socket socket = new Socket();
 
		// Timeout required - it's in milliseconds
		int timeout = 500;
 
		//System.out.println("hostName: " + hostName + ", port: " + port);
		try {
			socket.connect(socketAddress, timeout);
			socket.close();
			isAlive = true;
 
		} catch (SocketTimeoutException exception) {
			System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println("IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
		}
		return isAlive;
	}
 
}
