package gr.ntua.medialab.mp3playback.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;

public class SongMetada {

	public static final SongMetada INSTANCE = new SongMetada();

	private static String lastfmKey = "26dc868a306b42450c2df7fe5a13df2f";
	// private static String lastfmSecret = "0dbf0cfead3a4cd9b18510f880c6275d";

	private String artist;
	private String title;
	private long duration;
	private String albumTitle;
	private String albumImageURL;
	private Object artistBIO;

	private String songFile;

	private SongMetada() {
	}

	public void setSongFile(String songFile) {
		this.songFile = songFile;
		if (songFile != null)
			try {
				this.getSongMetadata();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static SongMetada getInstance() {
		return INSTANCE;
	}

	private void getSongMetadata() 
			throws UnsupportedAudioFileException, IOException {

		File song = new File(songFile);
		Map<String, Object> properties;

		AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(song);
		properties = baseFileFormat.properties();

		this.artist = (String) properties.get("author");
		this.title = (String) properties.get("title");
		this.duration = (Long)properties.get("duration");

		// Retrieve album title
		Track trackInfo = Track.getInfo(artist, title, lastfmKey);
		this.albumTitle = trackInfo.getAlbum();
		// Retrieve image URL
		this.albumImageURL = trackInfo.getImageURL(ImageSize.MEDIUM);
		// Retrieve artist BIO
		Artist artistInfo = Artist.getInfo(artist, lastfmKey);
		this.artistBIO = artistInfo.getWikiText();
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}

	public long getDuration() {
		return duration;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public String getAlbumImageURL() {
		return albumImageURL;
	}

	public Object getArtistBIO() {
		return artistBIO;
	}

}
