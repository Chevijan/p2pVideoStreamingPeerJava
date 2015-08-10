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
import java.awt.FlowLayout;

public class VideoPlayer extends JFrame {

	private JPanel contentPane;
	private static VideoPlayer frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private final JButton pauseButton;
	private final JButton rewindButton;
	private final JButton skipButton;

	/**
	 * Launch the application.
	 */
	public static void NewWindow() {
		new NativeDiscovery().discover();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new VideoPlayer();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception in creating frame with video player");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VideoPlayer() {
		setTitle("Video Player");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(new BorderLayout(0, 0));



		//-----------------Adding window listener for releasing resources ---------------
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				frame.dispose();
			}
		});
		//-------------------------------------------------------------------------------

		//---------------Creating media player component---------------------
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		//-------------------------------------------------------------------

		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

		setContentPane(contentPane);
		setVisible(true);

		JPanel controlsPane = new JPanel();
		controlsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		//-----------------------------------------------------------

		contentPane.add(controlsPane, BorderLayout.SOUTH);


		//---------------pause button action listener----------------
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().pause();
				if (pauseButton.getText().equals("Pause")) {
					pauseButton.setText("Play");
				} else {
					pauseButton.setText("Pause");
				}
			}
		});
		controlsPane.add(pauseButton);
		//-----------------------------------------------------------

		//---------------rewind button action listener---------------
		rewindButton = new JButton("<");
		rewindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(-10000);
			}
		});
		controlsPane.add(rewindButton);
		//-----------------------------------------------------------

		//--------------skip button action listener------------------
		skipButton = new JButton(">");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(10000);
			}
		});
		controlsPane.add(skipButton);

		//----------------Streaming video with this location-------------------
		mediaPlayerComponent.getMediaPlayer().playMedia("D:\\Videos\\ZlatanIbrahimovicTop50.mp4");
		//--------------------------------------------------------------

	}


}





