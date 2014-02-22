
import gr.ntua.medialab.mp3playback.PlayingThread;
import gr.ntua.medialab.mp3playback.SongMetada;

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
		String mp3File = "/home/fedjo/Music/The Last Drive/The Last Drive - Heatwave/04. Devil May Care.mp3";

		// Main Thread for the song playing
		PlayingThread mainStream = new PlayingThread(mp3File);
		Thread mainThread = new Thread(mainStream);
		mainThread.start();

		SongMetada meta = new SongMetada(mp3File);

		Session session = Authenticator.getMobileSession("fedjno",
				"dr1rbl9Z83", lastfmKey, lastfmSecret);

		Artist artistInfo = Artist.getInfo(meta.getArtist(), lastfmKey);
		Track trackInfo = Track.getInfo(meta.getArtist(), meta.getTitle(),
				lastfmKey);

		Object artistBIO = artistInfo.getWikiText();
		String albumTitle = trackInfo.getAlbum();
		String albumImageURL = trackInfo.getImageURL(ImageSize.LARGE);

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
		 * !res.isIgnored()));
		 */

		Collection<Album> topAlbums = Artist.getTopAlbums("Wax Tailor",
				lastfmKey);
		for (Album a : topAlbums) {
			System.out.println("Album: " + a.getName());
		}

	}

}
