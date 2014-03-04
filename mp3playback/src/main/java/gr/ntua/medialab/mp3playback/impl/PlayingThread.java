package gr.ntua.medialab.mp3playback.impl;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayingThread implements Runnable {
	public static final PlayingThread INSTANCE = new PlayingThread();

	private String mp3File;
	private int ignored;

	public boolean play;
	public boolean pause;
	public boolean forward;

	private byte[] crapy;

	private static final Object countLock = new Object();

	private PlayingThread() {
		this.ignored = 0;
		this.play = false;
		this.pause = false;
		this.forward = false;
		this.crapy = new byte[4096];
		createCrapy();
	}

	public void play() {
		try {
			do {
				File file = new File(mp3File);
				AudioInputStream in = AudioSystem.getAudioInputStream(file);
				AudioInputStream din = null;
				AudioFormat baseFormat = in.getFormat();
				AudioFormat decodedFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(), 16,
						baseFormat.getChannels(), baseFormat.getChannels() * 2,
						baseFormat.getSampleRate(), false);
				din = AudioSystem.getAudioInputStream(decodedFormat, in);
				// Play now.
				rawplay(decodedFormat, din);
				in.close();
			} while (play);
			play = false;
		} catch (Exception e) {
			// Handle exception.
			e.printStackTrace();
		}
	}

	private void rawplay(AudioFormat targetFormat, AudioInputStream din)
			throws IOException, LineUnavailableException {
		try {
			byte[] data = new byte[4096];
			SourceDataLine line = getLine(targetFormat);
			if (line != null) {
				// Start
				line.start();
				int nBytesRead = 0, nBytesWritten = 0;
				while (nBytesRead != -1 && play) {

					nBytesRead = din.read(data, 0, data.length);
					
					if (nBytesRead != -1)
						if (!forward) {
							nBytesWritten = line.write(data, 0, nBytesRead);
						} else {
							for (int i = 0; i < 50; i++) {
								nBytesRead = din.read(data, 0, data.length);
								nBytesWritten = line.write(crapy, 0, nBytesRead);
							}
							forward = false;
						}
					synchronized (countLock) {
						ignored += 4096;
					}
					while (play && pause) {
						synchronized (countLock) {
							wait(15);
						}
					}
				}
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}

	/**
	 * Waits a specified number of milliseconds.
	 * 
	 * @param time
	 *            Time to wait (in milliseconds).
	 */
	private void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.err.println("Could not wait!");
			e.printStackTrace();
		}
	}

	private void createCrapy(){
        for(int i = 0; i < crapy.length; i++){
            crapy[i] = 0;
        }
    }
	
	public void setMp3File(String mp3File) {
		this.mp3File = mp3File;
	}

	public static PlayingThread getInstance() {
		return INSTANCE;
	}

	public void run() {
		this.play();
	}

}
