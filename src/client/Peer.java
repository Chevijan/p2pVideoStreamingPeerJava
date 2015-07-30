package client;

import java.io.IOException;
import java.net.Socket;

public class Peer {

	private Socket clientSocket = null;
	private int port = 7812;
	
	
	//------------METHODS-----------
	
	/**Connecting to a localhost server*/
	public void ConnectToServer() {

	    try {
	    	clientSocket = new Socket("localhost", port);
	    	System.out.println("Connected to: "+ clientSocket.getInetAddress().getHostName());  	
	    	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception in connectToServer");
		}
	}
	
}
