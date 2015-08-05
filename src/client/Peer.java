package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Peer {

	private static volatile String meta = "";
	private Socket clientSocket = null;
	private int port = 7812;
	private static PrintWriter pw;


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

	/**Setting up streams in Peer*/
	public void SetupStreams() {
		try {
			pw = new PrintWriter(clientSocket.getOutputStream());
			System.out.println("Streams up");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldnt setup streams");
		}
	}

	/**Sending metadata of videos through socket*/
	public void SendMeta() {
		pw.println(meta);
		pw.flush();
		System.out.println("Metadata sent");
	}

	/**Getting meta date from files in specified folder*/
	public void GetMetadata() {
		File folder = new File("D:\\Videos\\");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				meta += file.getName() + "%";

			}
		}
		System.out.println("Metadata taken from folder on peer");
	}

	/**This is used to add action listener for closing the app*/
	public static void DisconnectPeer() {
		pw.println("-1");
		pw.flush();
		System.out.println("Disconnected");
	}

}
