package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**Class for starting server on a peer. It implements runnable, so it can be
 * started as a thread.*/
public class ClientAsAServer implements Runnable {

	// *****Creating sockets for communication between peers*****
	public static ServerSocket clientServerSocket;
	private Socket peerCommunicationSocket;

	@Override
	public void run() {
		try {
			clientServerSocket = new ServerSocket(8888);
			System.out.println("ClientAsAServer: socket on port 8888 is open;");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.println("ClientAsAServer: receiving connections from new peers");
				// *****Waiting for connection from other peers*****
				peerCommunicationSocket = clientServerSocket.accept();
				System.out.println("ClientAsAServer: New peer connected: "
						+ peerCommunicationSocket.getInetAddress().getHostAddress());

			} catch (IOException e) {
				System.out.println("ClientAsAServer: Couldn't receive connection from new peer");
				e.printStackTrace();
			}
		}
	}
}
