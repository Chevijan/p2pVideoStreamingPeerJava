package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientApp extends JFrame {

	private JPanel contentPane;
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new NativeDiscovery().discover();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApp frame = new ClientApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		//----------Connecting peer to server -----------------
		Peer peer = new Peer();
		peer.ConnectToServer();
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
		
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
		
		//----------Setting central part of border layout control pane to be this player -----------------
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
		//------------------------------------------------------------------------------------------------
		
		//----------Creating commands buttons --------------------------
		JPanel controlsPane = new JPanel();
		contentPane.add(controlsPane, BorderLayout.SOUTH);
		
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayerComponent.getMediaPlayer().pause();
			}
		});
		controlsPane.add(pauseButton);
		
		JButton rewindButton = new JButton("<");
		rewindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//this button rewinds 10 seconds.. 
				 mediaPlayerComponent.getMediaPlayer().skip(-10000);
			}
		});
		controlsPane.add(rewindButton);
		
		JButton skipButton = new JButton(">");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//this button forwards 10 seconds..
				 mediaPlayerComponent.getMediaPlayer().skip(10000);
			}
		});
		controlsPane.add(skipButton);
		//----------------------------------------------------------------
		
	}

}
