package client;

import java.net.Socket;

/**Class for communication between peers. Defines process for streaming videos
 * or data. It implements runnable, so it can be started as a thread.*/
public class PToPCommunication implements Runnable {

	private Socket peerCommunication;
	private int numberOfThread;

	//***** Constructor with 2 params
	// 			-socket for communication between peers;
	//			-number of thread for ID; *****
	public PToPCommunication(Socket socket, int numberOfThread) {
		this.peerCommunication = socket;
		this.numberOfThread = numberOfThread;
	}


	@Override
	public void run() {
		
		System.out.println("PToPComm: Peer " + peerCommunication.getInetAddress().getHostAddress()
				+ " is now connected to this peer");
		

		/*
		 * Ovde sada ide deo gde treba napraviti strimovanje videa sa
		 * peer-servera na peer-clienta
		 */
	}
}
