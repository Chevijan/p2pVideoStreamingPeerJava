package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Peer {

	private static volatile String meta = "";
	private Socket clientSocket = null;
	private int port = 7812;
	private BufferedReader br;
	private static PrintWriter pw;
	private static volatile String requestForVideo;
	private static String ipAdresses;

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
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

	/**Sends request that starts with choice*/
	public static void SendRequestForVideo() {
		pw.println("choice#" + requestForVideo);
		pw.flush();
		System.out.println("request sent");
	}

	/**listens for String with ip adresses of peers that have requested video
	 * method for requesting videos is SendRequestForVideo()*/
	public void receiveIpsWithVideo() {
		try {
			Peer.ipAdresses = br.readLine();
			System.out.println("Ips received: "+ ipAdresses);
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("Error: Couldn't read ip-adresses");
		}
	}

	//------Getters and setters for request for video----------------
	public static String getRequestForVideo() {
		return requestForVideo;
	}

	public static void setRequestForVideo(String requestForVideo) {
		Peer.requestForVideo = requestForVideo;
	}
	//---------------------------------------------------------------

}
