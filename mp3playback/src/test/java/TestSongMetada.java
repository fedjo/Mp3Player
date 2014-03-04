import gr.ntua.medialab.mp3playback.impl.SongMetada;


public class TestSongMetada {

	/**
	 * @param args
	 */
    private static String songFile = "/home/fedjo/Music/The Last Drive/The Last Drive - Heatwave/04. Devil May Care.mp3";

	
	public static void main(String[] args) {
		
		SongMetada meta = SongMetada.getInstance();
		meta.setSongFile(songFile);
		System.out.println(meta.getArtist() + "-" + meta.getTitle() + "-" + meta.getAlbum());
		System.out.println(meta.getAlbumTitle() + "-" + meta.getAlbumImageURL());
		System.out.println(meta.getArtistBIO());

	}

}
