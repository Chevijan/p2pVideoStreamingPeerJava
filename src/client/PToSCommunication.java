package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PToSCommunication {

	// ***** Variables for communication between peer and server*****
	private static volatile String ipAdresses = "";
	public static volatile Socket CSCommunicationSocket;
	private static PrintWriter pw;

	// ***** This one should get updated always with new connected
	// and disconnected peers. Also, for Sockets, a list*****
	private static String[] ipAdressArray;
	private static ArrayList<Socket> socketsForStreaming = new ArrayList<Socket>();
	private static ArrayList<Thread> pToPCommThreads = new ArrayList<Thread>();

	/** Connecting to a localhost server. Ip adress "localhost" can be changed!*/
	public void ConnectToServer(String IPAdressOfServer, int portOfServer) {

		try {
			CSCommunicationSocket = new Socket(IPAdressOfServer, portOfServer);
			System.out.println("PToSComm: Connected to: " + CSCommunicationSocket.getInetAddress().getHostName());

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("PToSComm: Exception in connectToServer");
		}
	}

	/** Setting up streams in Peer */
	public void SetupStreams() {
		try {
			pw = new PrintWriter(CSCommunicationSocket.getOutputStream());
			System.out.println("PToSComm: Streams up");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("PToSComm: Couldnt setup streams");
		}
	}

	/** Sending metadata of videos through socket */
	public void SendMeta(String metaDataOfVideosOnThisPeer) {
		pw.println(metaDataOfVideosOnThisPeer);
		pw.flush();
		System.out.println("PToSComm: Metadata sent");
	}

	/**
	 * Getting meta data from files in specified folder;
	 * 		-meta data is added to String, separated with "%"
	 */
	public String GetMetadata() {

		String metaDataOfVideosOnThisPeer = "";

		File folder = new File("D:\\Videos\\");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				metaDataOfVideosOnThisPeer += file.getName() + "%";

			}
		}
		System.out.println("PToSComm: Metadata taken from folder on peer");
		return metaDataOfVideosOnThisPeer;
	}

	/** This is used to add action listener for closing the app;
	 * 		-client prints -1, and it is handled at server side */
	public static void DisconnectPeer() {
		pw.println("-1");
		pw.flush();
		System.out.println("PToSComm: Disconnected");
	}

	/** Sending request that starts with choice;
	 * 		-"choice#" is being substracted at the server side, but it is imortant
	 * 		  because thats how server knows that it received request for video;*/
	public static void SendRequestForVideo(String requestForVideo) {
		pw.println("choice#" + requestForVideo);
		pw.flush();
		System.out.println("PToSComm: request sent");
	}

	/** Connecting to peers with ip adresses which are provided by server;
	 *  Adding sockets to list of sockets;
	 *  	-these sockets are used for sending and receiving data from other peers
	 *  Adding threads for communication between peers to pToPCommThreads list;
	 *  Starting new threads for communication between peers (for video streaming)
	 *  	-number of threads deppends on how many ip adresses are in list;*/
	public static void ConnectToPeers() {

		//***** Wait while this peer receives list of adresses from server*****
		while(ipAdresses.equals("")) {
			
		}

		//***** This condition is here just because server will print ipadress of this client
		// itself, so we'll try to avoid selfconnecting. Client will get adresses of 
		// other peers that have requested movie*****
//		if(ipAdresses.equals("127.0.0.1#")) {
//			System.out.println("PToSComm: there are currently no peers for this video...");
//		} else {
			ipAdressArray = ipAdresses.split("#");
			System.out.println("PToSComm: added adresses to ipAdressArray: " + ipAdressArray[0]);
			
			try {

				//***** We are itterating through list of adresses received from server, 
				// adding sockets in list of sockets (every socket will have different ip adress),
				// and adding and starting threads for communication
				// between peers (PToPCommunication)*****
				for (int i = 0; i < ipAdressArray.length; i++) {
					socketsForStreaming.add(new Socket(ipAdressArray[i], 8888));
					System.out.println("PToSComm: Socket with ip: " + ipAdressArray[i] + " is added to list");
					
					//***** i as a parameter is used for identifying thread so you can set seqNum of frames which are 
					// requested/sent from each peer. If you want to optimize streaming, you should use different
					// mechanisam for id of thread and for transferring data*****/
					pToPCommThreads.add(new Thread(new PToPCommunication(socketsForStreaming.get(i), i)));
					pToPCommThreads.get(i).start();
					System.out.println("PToSComm: thread pToP started");
				}

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}
	}

	// *****Getters and setters*****
	public static String getIpAdresses() {
		return ipAdresses;
	}

	public static void setIpAdresses(String ipAdresses) {
		PToSCommunication.ipAdresses = ipAdresses;
	}

}
