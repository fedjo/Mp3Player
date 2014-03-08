import gr.ntua.medialab.mp3playback.MediaContainer;
import gr.ntua.medialab.mp3playback.impl.PlayingThread;
import gr.ntua.medialab.mp3playback.impl.SongMetada;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.Dimension;


public class mainWindow extends Observable {

	private JFrame frmMpPlayer;
	private File[] filesOpen;
	private Map<String, Component> comps = new HashMap<String, Component>();
	private MediaContainer mc = new MediaContainer(null);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frmMpPlayer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMpPlayer = new JFrame();
		frmMpPlayer.getContentPane().setSize(new Dimension(800, 800));
		frmMpPlayer.setPreferredSize(new Dimension(800, 800));
		frmMpPlayer.setSize(new Dimension(800, 800));
		frmMpPlayer.setTitle("Mp3 Player");
		frmMpPlayer.setBounds(50, 50, 1300, 700);
		frmMpPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMpPlayer.getContentPane().setLayout(null);
		
		JPanel AlbumPhoto = new JPanel();
		AlbumPhoto.setBounds(12, 66, 189, 140);
		frmMpPlayer.getContentPane().add(AlbumPhoto);
		comps.put("photo", AlbumPhoto);
		
		JMenuBar MainMenu = new JMenuBar();
		MainMenu.setBounds(12, 39, 1254, 27);
		frmMpPlayer.getContentPane().add(MainMenu);
		
		JMenu mnFile = new JMenu("File");
		MainMenu.add(mnFile);
		
		JMenuItem menuItem_5 = new JMenuItem("Open");
		menuItem_5.addActionListener(new Open(this));
		mnFile.add(menuItem_5);
		comps.put("open", menuItem_5);
		
		JMenuItem menuItem_6 = new JMenuItem("Close");
		mnFile.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("Exit");
		mnFile.add(menuItem_7);
		
		JMenu mnHelp = new JMenu("Help");
		MainMenu.add(mnHelp);
		
		JTextArea txtrAboutYiorgosMarinellis = new JTextArea();
		txtrAboutYiorgosMarinellis.setText("About: Yiorgos Marinellis");
		mnHelp.add(txtrAboutYiorgosMarinellis);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JTextPane txtpnGfgdfgdfgdf = new JTextPane();
		txtpnGfgdfgdfgdf.setText("About: '\\n' dfjdsfds");
		mnHelp.add(txtpnGfgdfgdfgdf);
		
		JLabel AppTitle = new JLabel("  Mp3 Player");
		AppTitle.setBounds(12, 23, 1254, 15);
		frmMpPlayer.getContentPane().add(AppTitle);
		
		JPanel ProgressBar = new JPanel();
		ProgressBar.setToolTipText("Bar");
		ProgressBar.setBounds(201, 104, 1065, 27);
		frmMpPlayer.getContentPane().add(ProgressBar);
		ProgressBar.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 0, 1065, 14);
		ProgressBar.add(progressBar);
		
		JPanel NowPlaying = new JPanel();
		NowPlaying.setBounds(201, 66, 1065, 36);
		frmMpPlayer.getContentPane().add(NowPlaying);
		NowPlaying.setLayout(null);
		
		JLabel Title = new JLabel("title");
		Title.setBounds(148, 5, 200, 15);
		NowPlaying.add(Title);
		comps.put("title", Title);
		
		final JLabel Album = new JLabel("album");
		Album.setBounds(380, 5, 200, 15);
		NowPlaying.add(Album);
		comps.put("album", Album);
		
		JLabel Time = new JLabel("time elapsed");
		Time.setBounds(947, 12, 118, 15);
		NowPlaying.add(Time);
		comps.put("time elapsed", Time);
		
		JLabel Artist = new JLabel("artist");
		Artist.setBounds(0, 5, 200, 15);
		NowPlaying.add(Artist);
		comps.put("artist", Artist);
		
		JPanel PlayButtons = new JPanel();
		PlayButtons.setBounds(201, 131, 1065, 45);
		frmMpPlayer.getContentPane().add(PlayButtons);
		PlayButtons.setLayout(null);
		
		
		JButton Play = new JButton("|>");
		Play.addActionListener(new Play(this));
		Play.setHorizontalAlignment(SwingConstants.LEFT);
		Play.setBounds(0, 0, 48, 45);
		PlayButtons.add(Play);
		
		JButton Pause = new JButton("||");
		Pause.addActionListener(new Pause(this));
		Pause.setBounds(60, 0, 53, 45);
		PlayButtons.add(Pause);
		
		JButton Stop = new JButton("O");
		Pause.addActionListener(new Stop(this));
		Stop.setBounds(125, 0, 58, 45);
		PlayButtons.add(Stop);
	}
	
	
	
	public JFrame getFrmMpPlayer() {
		return frmMpPlayer;
	}

	public File[] getFilesOpen() {
		return filesOpen;
	}

	public void setFilesOpen(File[] filesOpen) {
		this.filesOpen = filesOpen;
	}
	
	public Map<String, Component> getComps() {
		return comps;
	}
	
	public void updatePanels(File f) {
		SongMetada data = mc.getMetadata();
		((JLabel)comps.get("artist")).setText(data.getArtist());
		((JLabel)comps.get("album")).setText(data.getAlbumTitle());
		((JLabel)comps.get("title")).setText(data.getTitle());
	}

	public void playSong() {
		for (File f : filesOpen) {
			mc.setMp3File(f.getPath());
			updatePanels(f);
			mc.play();
		}
		
	}

	public void pauseSong() {
		mc.pause();
	}

	public void stopSong() {
		mc.stop();
	}

}
