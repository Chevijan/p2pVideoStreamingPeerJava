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

public class ClientApp extends JFrame {

	private JPanel contentPane;
	private static ClientApp frame;

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

		//----------Connecting peer to server -----------------
		Peer peer = new Peer();
		peer.ConnectToServer();
		//-----------------------------------------------------

		//-----------Setting up streams------------------------
		peer.SetupStreams();
		//-----------------------------------------------------

		//------------Various methods-------------------------
		peer.GetMetadata();
		peer.SendMeta();
		//-----------------------------------------------------

		//------------Starts reading for ip-adresses-----------
		peer.receiveIpsWithVideo();
		//-----------------------------------------------------

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

		//----------------Creating the frame with video player using button--------------------
		//----------------This should happen after choosing the video--------------------------
		JButton btnMovie1 = new JButton("Movie1");
		btnMovie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VideoPlayer.NewWindow();
				Peer.setRequestForVideo("ZlatanIbrahimovicTop50.mp4");
				Peer.SendRequestForVideo();
			}
		});
		panel.add(btnMovie1);

		JButton btnMovie2 = new JButton("Movie2");
		btnMovie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VideoPlayer.NewWindow();
				Peer.setRequestForVideo("Alice_In_Chains_-_Would.mp4");
				Peer.SendRequestForVideo();
			}
		});
		panel.add(btnMovie2);

		JButton btnMovie3 = new JButton("Movie3");
		btnMovie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoPlayer.NewWindow();
				Peer.setRequestForVideo("Lilly_Wood__The_Prick_and_Robin_Schulz_-_Prayer_In_C_(Robin_Schulz_Remix)_(Official).mp4");
				Peer.SendRequestForVideo();
			}
		});
		panel.add(btnMovie3);
		//-------------------------------------------------------------------------------------

		//-------------Adding what happens when we close the widnow---------------
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Peer.DisconnectPeer();
			}
		});
		//----------------------------------------------------------------------
	}


}
