import gr.ntua.medialab.mp3playback.MediaContainer;
import gr.ntua.medialab.mp3playback.impl.PlayingThread;
import gr.ntua.medialab.mp3playback.impl.SongMetada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;

/**
 * Hello world!
 * 
 */
public class MainTest {
	private static String lastfmKey = "26dc868a306b42450c2df7fe5a13df2f";
	private static String lastfmSecret = "0dbf0cfead3a4cd9b18510f880c6275d";

	public static void main(String[] args) {
		// Linux File
		//String mp3File = "/home/fedjo/Music/The Last Drive/The Last Drive - Heatwave/04. Devil May Care.mp3";
		// Windows file
		String mp3File = "C:\\Users\\marinellis\\Music\\Collapse Under The Empire - Crawling.mp3";

		// Main Thread for the song playing
		MediaContainer cont = new MediaContainer();
		cont.setMp3File(mp3File);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input;

			while ((input = br.readLine()) != null) {
				if (input.equals("play")) {
					cont.play();
				} else if (input.equals("pause")) {
					cont.pause();
				} else if (input.equals("stop")) {
					cont.stop();
				} else if (input.equals("for")) {
					cont.forward();
				} else if (input.equals("isPlay")) {
					System.out.println(cont.isPlaying());
				} else if (input.equals("isPause")) {
					System.out.println(cont.isPaused());
				} else if (input.equals("isStop")) {
					System.out.println(cont.isPlaying());
				}
			}

		} catch (IOException io) {
			io.printStackTrace();
		}

		/*Session session = Authenticator.getMobileSession("fedjno",
				"dr1rbl9Z83", lastfmKey, lastfmSecret);*/

		SongMetada data = cont.getMetadata();
		
		/*Artist artistInfo = Artist.getInfo(data.getArtist(),
				lastfmKey);
		Track trackInfo = Track.getInfo(data.getArtist(), data.getTitle(), lastfmKey);*/

		Object artistBIO = data.getArtistBIO();
		String albumTitle = data.getAlbumTitle();
		String albumImageURL = data.getAlbumImageURL();

		System.out.println(artistBIO);
		System.out.println(albumTitle);
		System.out.println(albumImageURL);

		/*
		 * ScrobbleResult result = Track.updateNowPlaying(metadata[0],
		 * metadata[1], session); System.out.println("ok: " +
		 * (result.isSuccessful() && !result.isIgnored())); int now = (int)
		 * (System.currentTimeMillis() / 1000); ScrobbleResult res =
		 * Track.scrobble(metadata[0], metadata[1], now, session);
		 * System.out.println("ok: " + (res.isSuccessful() &&
		 */

		Collection<Album> topAlbums = Artist.getTopAlbums("Wax Tailor",
				lastfmKey);
		for (Album a : topAlbums) {
			System.out.println("Album: " + a.getName());
		}

	}

}
