package gr.ntua.medialab.mp3playback;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleResult;

/**
 * Hello world!
 *
 */
public class TestMp3Playback 
{

	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String mp3File = "/home/fedjo/Music/Mikro_feat_pes_mou _le3h.mp3";
        
        TestMp3Playback player = new TestMp3Playback();
        //player.testPlay(mp3File);
        
        String lastfmKey = "26dc868a306b42450c2df7fe5a13df2f";
        String lastfmSecret = "0dbf0cfead3a4cd9b18510f880c6275d";
        
        String[] metadata = player.getSongMetadata(mp3File);
        for (String s : metadata) {
        	System.out.println(s);
        }
        
        Session session = Authenticator.getMobileSession("fedjno", "dr1rbl9Z83", lastfmKey, lastfmSecret);
        
        ScrobbleResult result = Track.updateNowPlaying(metadata[0], metadata[1], session);
        //System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        
        int now = (int) (System.currentTimeMillis() / 1000);
        ScrobbleResult res = Track.scrobble(metadata[0], metadata[1], now, session);
        //System.out.println("ok: " + (res.isSuccessful() && !res.isIgnored()));
        
        Collection<Album> topAlbums = Artist.getTopAlbums("Wax Tailor", lastfmKey);
        for (Album a  : topAlbums) {
        	System.out.println("Album: " + a.getName());
        }
        
        
    }

	private boolean play;
	
    public TestMp3Playback() {
		this.play = true;
	}

    public void testPlay(String filename)
    {
      try {
        File file = new File(filename);
        AudioInputStream in= AudioSystem.getAudioInputStream(file);
        AudioInputStream din = null;
        AudioFormat baseFormat = in.getFormat();
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                                                      baseFormat.getSampleRate(),
                                                                                      16,
                                                                                      baseFormat.getChannels(),
                                                                                      baseFormat.getChannels() * 2,
                                                                                      baseFormat.getSampleRate(),
                                                                                      false);
        din = AudioSystem.getAudioInputStream(decodedFormat, in);
        // Play now.
        rawplay(decodedFormat, din);
        in.close();
      } catch (Exception e)
        {
            //Handle exception.
        }
    }

    private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
    {
      byte[] data = new byte[4096];
      SourceDataLine line = getLine(targetFormat);
      if (line != null)
      {
        // Start
        line.start();
        int nBytesRead = 0, nBytesWritten = 0;
        while (nBytesRead != -1)
        {	
        	while(!play ){} // Pause funtionality - loop untill someone presses again pause button.
            nBytesRead = din.read(data, 0, data.length);
            if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
        }
        // Stop
        line.drain();
        line.stop();
        line.close();
        din.close();
      }
    }

    public void pause() {
    	
    }
    
    public String[] getSongMetadata(String filename) {
    	
    	File song = new File(filename);
    	Map properties;
    	String[] res = new String[3];
    	try {
			AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(song);
			properties = baseFileFormat.properties();
			
			res[0] = (String)properties.get("author");
			res[1] = (String)properties.get("title");
			res[2] = String.valueOf((Long)properties.get("duration"));
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return res;
    }
    
    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
      SourceDataLine res = null;
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      res = (SourceDataLine) AudioSystem.getLine(info);
      res.open(audioFormat);
      return res;
    }

}
