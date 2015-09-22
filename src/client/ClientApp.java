package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;

public class ClientApp extends JFrame {

	// *****variables for creating window (GUI)*****
	private JPanel contentPane;
	private static ClientApp frame;
	

	// *****String requestForVideo which cooperates with buttons*****
	private static String requestForVideo = "";

	// *****Thread which starts Server on peer*****
	private static Thread threadForServerStarting;
	
	//*****Socket for messages of list updating server - peer*****
	private static Thread liveUpdaterThread;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ClientApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// *****Initializing instance of PToSCommunication*****
		PToSCommunication pToSCommunication = new PToSCommunication();

		// *****Connecting peer to server with given ip adress and port*****
		pToSCommunication.ConnectToServer("localhost", 7812);

		// *****Starting a thread where client becomes server for other
		// peers*****
		ClientAsAServer cs = new ClientAsAServer();
		threadForServerStarting = new Thread(cs);
		threadForServerStarting.start();

		// *****Setting up streams for communication*****
		pToSCommunication.SetupStreams();
		
		// *****Sending meta data from peer to server. Server uses that
		// data for completing the list of ip-scurrently connected*****
		pToSCommunication.SendMeta(pToSCommunication.GetMetadata());
		
		//*****Starting a thread for live updating list of ips
		//	 for connection on other peers*****
		LiveUpdater LU = new LiveUpdater();
		liveUpdaterThread = new Thread(LU);
		liveUpdaterThread.start();
		
	}

	/**
	 * Create the frame.
	 */
	public ClientApp() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		// *****Buttons for choosing videos by sending request to server
		// and receiving list of ip adresses of peers which have that video*****
		// 1
		JButton btnMovie1 = new JButton("Movie1");
		btnMovie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VideoPlayer.NewWindow();
				setRequestForVideo("ZlatanIbrahimovicTop50.mp4");
				PToSCommunication.SendRequestForVideo(requestForVideo);
				
				//*****Connecting to peers when button is presse
				// if list with ip adresses which server sends to peer
				// is not empty*****
				PToSCommunication.ConnectToPeers();
			}
		});
		panel.add(btnMovie1);

		// 2
		JButton btnMovie2 = new JButton("Movie2");
		btnMovie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VideoPlayer.NewWindow();
				setRequestForVideo("Alice_In_Chains_-_Would.mp4");
				PToSCommunication.SendRequestForVideo(requestForVideo);
				
				//*****Connecting to peers when button is presse
				// if list with ip adresses which server sends to peer
				// is not empty*****
				PToSCommunication.ConnectToPeers();
			}
		});
		panel.add(btnMovie2);

		// 3
		JButton btnMovie3 = new JButton("Movie3");
		btnMovie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoPlayer.NewWindow();
				setRequestForVideo(
						requestForVideo = "Lilly_Wood__The_Prick_and_Robin_Schulz_-_Prayer_In_C_(Robin_Schulz_Remix)_(Official).mp4");
				PToSCommunication.SendRequestForVideo(requestForVideo);
				
				//*****Connecting to peers when button is presse
				// if list with ip adresses which server sends to peer
				// is not empty*****
				PToSCommunication.ConnectToPeers();
			}
		});
		panel.add(btnMovie3);

		// *****Action listener for closing window.
		// Peer disconnects on window close*****
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PToSCommunication.DisconnectPeer();
				System.exit(-1);
			}
		});
	}

	// *****Getters and setters*****
	
	public static String getRequestForVideo() {
		return requestForVideo;
	}

	public static void setRequestForVideo(String requestForVideo) {
		ClientApp.requestForVideo = requestForVideo;
	}

	public static Thread getThreadForServerStarting() {
		return threadForServerStarting;
	}

	public static void setThreadForServerStarting(Thread threadForServerStarting) {
		ClientApp.threadForServerStarting = threadForServerStarting;
	}
}

