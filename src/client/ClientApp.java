package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientApp extends JFrame {

	private JPanel contentPane;
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private static ClientApp frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new NativeDiscovery().discover();
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

		//----------Setting central part of border layout control pane to be this player -----------------
		 mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
		//------------------------------------------------------------------------------------------------




		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});


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

		//---------------------Event handlers for vlcj (basic)------------------------------
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			
			//this is what happens when video is playing
			@Override
			public void playing(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setTitle(String.format(
								"My First Media Player - %s",
								mediaPlayerComponent.getMediaPlayer().getMediaMeta().getTitle()
								));
					}
				});
			}

			//this is what happens when video is finished
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						closeWindow();
					}
				});
			}
			
			//this is what happen when an error occurs
			@Override
			public void error(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(
								frame,
								"Failed to play media",
								"Error",
								JOptionPane.ERROR_MESSAGE
								);
						closeWindow();
					}
				});
			}
		});
		//-----------------------------------------------------------------------------------

		//----------------Defining mouse and keyboard use on video surface--------------------
		Canvas videoSurface = mediaPlayerComponent.getVideoSurface();

		//Adding for mouse events (clicks)
		videoSurface.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		//Adding for mouse wheel
		videoSurface.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});
		
		//Adding for keyboard buttons pressed
		videoSurface.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Adding foucus to key events
		mediaPlayerComponent.getVideoSurface().requestFocusInWindow();
		//------------------------------------------------------------------------------
	}

	/**Closing the window from event handler*/
	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

}
