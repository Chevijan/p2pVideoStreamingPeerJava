package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**Public class which implements runnable so it can be started as a thread;
 * It's purpose is to update private static volatile ipAdresses from PToSCommunication class;*/
public class LiveUpdater implements Runnable{

	private BufferedReader br;

	@Override
	public void run() {
		receiveIpsWithVideo();
	}

	/**
	 * The method waits for CHANGED list of ip'sfrom server, so when
	 * someone connects to server, every peer gets new, updated list
	 *  so it can connect to more peers!
	 */
	public void receiveIpsWithVideo() {

		SetupStreams();
		
		while (true) {
			try {
				String text = br.readLine();
				PToSCommunication.setIpAdresses(text);
				System.out.println("LiveUpdater: ips received on peer " + PToSCommunication.getIpAdresses());

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("LiveUpdater: Error: Couldn't read ip-adresses");
			}
		}
	}
	
	/** Setting up streams in LiveUpdater */
	public void SetupStreams() {
		try {
			br = new BufferedReader(new InputStreamReader(PToSCommunication.CSCommunicationSocket.getInputStream()));
			System.out.println("LiveUpdater: Streams up");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("LiveUpdater: Couldnt setup streams");
		}
	}
}
