package gr.ntua.medialab.mp3playback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;

public class SongMetada<V> {

	private static String lastfmKey = "26dc868a306b42450c2df7fe5a13df2f";
    //private static String lastfmSecret = "0dbf0cfead3a4cd9b18510f880c6275d";
	
	private String artist;
	private String title;
	private String album;
	private String albumTitle;
	private String albumImageURL;
	private Object artistBIO;



	
	private String songFile;
	

	public SongMetada(String songFile) {
		this.songFile = songFile;
		getSongMetadata();
	}

	private void getSongMetadata() {

		File song = new File(songFile);
		Map<String, Object> properties;
		
		try {
			AudioFileFormat baseFileFormat = AudioSystem
					.getAudioFileFormat(song);
			properties = baseFileFormat.properties();

			this.artist = (String) properties.get("author");
			this.title  = (String) properties.get("title");
			this.album  = String.valueOf((Long) properties.get("duration"));

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Retrieve album title
		Track trackInfo = Track.getInfo(artist, title, lastfmKey);
        this.albumTitle = trackInfo.getAlbum();
        // Retrieve image URL
        this.albumImageURL = trackInfo.getImageURL(ImageSize.LARGE);
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

	public String getAlbum() {
		return album;
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