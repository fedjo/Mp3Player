package gr.ntua.medialab.mp3playback.ui;

import gr.ntua.medialab.mp3playback.MediaContainer;
import gr.ntua.medialab.mp3playback.events.*;
import gr.ntua.medialab.mp3playback.impl.SongMetada;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class mainWindow extends Observable {

	private JFrame frmMpPlayer;
	private File[] filesOpen;
	private Map<String, Component> comps = new HashMap<String, Component>();
	private MediaContainer mc = new MediaContainer();
	private Task task;
	private String resetFile = "http://31.media.tumblr.com/avatar_2a8fe72ff5f9_128.png";

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

		ImagePanel AlbumPhoto = new ImagePanel();
		AlbumPhoto.setBounds(12, 66, 189, 140);
		AlbumPhoto.reset(resetFile);
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
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSongList();
				filesOpen = null;
				stopSong();
				mc.setMp3File(null);
				resetPanels();
				resetBio();
				((ImagePanel) comps.get("photo")).reset(resetFile);
				frmMpPlayer.repaint();
			}
		});
		mnFile.add(menuItem_6);

		JMenuItem menuItem_7 = new JMenuItem("Exit");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnFile.add(menuItem_7);

		JMenu mnHelp = new JMenu("Help");
		MainMenu.add(mnHelp);

		JTextArea txtrAboutYiorgosMarinellis = new JTextArea();
		txtrAboutYiorgosMarinellis.setText("About: Yiorgos Marinellis");
		mnHelp.add(txtrAboutYiorgosMarinellis);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		JLabel AppTitle = new JLabel("  Mp3 Player");
		AppTitle.setBounds(12, 23, 1254, 15);
		frmMpPlayer.getContentPane().add(AppTitle);

		JPanel ProgressBar = new JPanel();
		ProgressBar.setToolTipText("Bar");
		ProgressBar.setBounds(201, 104, 1065, 27);
		frmMpPlayer.getContentPane().add(ProgressBar);
		ProgressBar.setLayout(null);

		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(0, 0, 1065, 14);
		ProgressBar.add(progressBar);
		comps.put("pbar", progressBar);

		JPanel NowPlaying = new JPanel();
		NowPlaying.setBounds(201, 66, 1065, 36);
		frmMpPlayer.getContentPane().add(NowPlaying);
		NowPlaying.setLayout(null);

		JLabel Title = new JLabel("title");
		Title.setBounds(211, 5, 200, 15);
		NowPlaying.add(Title);
		comps.put("title", Title);

		JLabel Album = new JLabel("album");
		Album.setBounds(442, 5, 200, 15);
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

		JToggleButton Pause = new JToggleButton("||");
		Pause.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
			        pauseSong();
			      } else if(e.getStateChange()==ItemEvent.DESELECTED){
			        pauseSong();
			      }
				
			}
		});
		Pause.setBounds(60, 0, 53, 45);
		PlayButtons.add(Pause);
		
		/*JButton Pause = new JButton("||");
		Pause.addActionListener(new Pause(this));
		Pause.setBounds(60, 0, 53, 45);
		PlayButtons.add(Pause);*/

		JButton Stop = new JButton("O");
		Stop.addActionListener(new Stop(this));
		Stop.setBounds(125, 0, 58, 45);
		PlayButtons.add(Stop);

		JButton FastForward = new JButton(">>");
		FastForward.addActionListener(new FastForward(this));
		FastForward.setHorizontalAlignment(SwingConstants.LEFT);
		FastForward.setBounds(203, 0, 48, 45);
		PlayButtons.add(FastForward);

		JPanel panel = new JPanel();
		panel.setBounds(201, 187, 1065, 295);
		frmMpPlayer.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Music Connects People");
		lblNewLabel.setBounds(0, 0, 620, 138);
		panel.add(lblNewLabel);
		comps.put("bio", lblNewLabel);
	}

	public JFrame getFrmMpPlayer() {
		return frmMpPlayer;
	}

	public File[] getFilesOpen() {
		return filesOpen;
	}

	public void setFilesOpen(File[] filesOpen) {
		if (this.filesOpen == null) {
			this.filesOpen = filesOpen;
		} else {
			int len = this.filesOpen.length + filesOpen.length;
			File[] mergedArray = new File[len];
			
			for (int i = 0; i < this.filesOpen.length; i++) {
				mergedArray[i] = this.filesOpen[i];
			}
			for (int i = 0; i < filesOpen.length; i++) {
				mergedArray[this.filesOpen.length] = filesOpen[i];
			}
			this.filesOpen = mergedArray;
		}
	}

	public Map<String, Component> getComps() {
		return comps;
	}

	public void updatePanels(File f) {
		SongMetada data = mc.getMetadata();
		((JLabel) comps.get("artist")).setText(data.getArtist());
		((JLabel) comps.get("album")).setText(data.getAlbumTitle());
		((JLabel) comps.get("title")).setText(data.getTitle());
		frmMpPlayer.repaint();
	}

	public void resetPanels() {
		((JLabel) comps.get("artist")).setText("");
		((JLabel) comps.get("album")).setText("");
		((JLabel) comps.get("title")).setText("");
		frmMpPlayer.repaint();
	}

	public void playSong() {
		if (filesOpen != null) {
			for (File f : filesOpen) {
				mc.setMp3File(f.getPath());
				updatePanels(f);
				updatePhoto(mc.getMetadata().getAlbumImageURL());
				updateBio(mc.getMetadata().getArtistBIO());
				frmMpPlayer.repaint();
				task = new Task();
				task.start();
				mc.play();
			}
		}
	}

	private void updateBio(Object bio) {
		String textBIO = "<html>" + (String) bio + "</html>";
		((JLabel) comps.get("bio")).setText(textBIO);
	}

	private void resetBio() {
		String textBIO = "<html><h1>Piracy Helps Music</h1></html>";
		((JLabel) comps.get("bio")).setText(textBIO);
	}

	private void updatePhoto(String filePath) {
		((ImagePanel) comps.get("photo")).configure(filePath);
	}

	public void pauseSong() {
		mc.pause();
		frmMpPlayer.repaint();
	}

	public void stopSong() {
		mc.stop();
		frmMpPlayer.repaint();
	}

	public void updateSongList(File[] files) {

		if (comps.get("list") != null) {
			frmMpPlayer.remove(comps.get("list"));
		}

		List<String> songs = new ArrayList<String>();
		for (File f : files) {
			songs.add(f.getName());
		}

		String[] songs_ = songs.toArray(new String[songs.size()]);

		JList<String> songsList = new JList<String>(songs_);
		JScrollPane scrollPane = new JScrollPane(songsList);
		scrollPane.setBounds(12, 202, 188, 237);
		frmMpPlayer.getContentPane().add(scrollPane);
		comps.put("list", scrollPane);
		frmMpPlayer.repaint();
	}

	public void resetSongList() {

		if (comps.get("list") != null) {
			frmMpPlayer.remove(comps.get("list"));
		}
	}

	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private BufferedImage image;

		public void configure(String filePath) {
			try {
				image = ImageIO.read(new URL(filePath));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		public void reset(String filepath) {
			try {
				image = ImageIO.read(new URL(filepath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}
	}
	
	private class Task extends Thread {    
	      public Task(){
	      }

	      public void run(){
	         /*for(int i = 0; i<= 100; i+=10){
	            final int progress = i;
	            SwingUtilities.invokeLater(new Runnable() {
	               public void run() {
	            	   ((JProgressBar)comps.get("pbar"))
	            	   			.setValue(progress);
	               }
	            });
	            try {
	               Thread.sleep(100);
	            } catch (InterruptedException e) {}
	         }*/
	    	 while (mc.isPlaying()) {    		 
	    		 ((JProgressBar)comps.get("pbar"))
 	   				.setValue((int)mc.time());
	    		 frmMpPlayer.repaint();
	    	 }
	      }
	   }

	public void fastForward() {
		mc.forward();
	}   

}
