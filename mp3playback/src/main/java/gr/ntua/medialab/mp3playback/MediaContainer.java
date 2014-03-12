package gr.ntua.medialab.mp3playback;

import java.lang.Thread.State;

import gr.ntua.medialab.mp3playback.impl.PlayingThread;
import gr.ntua.medialab.mp3playback.impl.SongMetada;

public class MediaContainer {

	private PlayingThread thread;
	private Thread mainThread;
	private SongMetada metadata;

	public MediaContainer() {
		thread = PlayingThread.getInstance();
		metadata = SongMetada.getInstance();

		mainThread = new Thread(thread);

	}

	public void setMp3File(String file) {
		thread.setMp3File(file);
		metadata.setSongFile(file);
	}

	public void play() {
		if (!thread.play) {
			try {
				if (!mainThread.getState().equals(State.TERMINATED)) {
					thread.play = true;
					mainThread.start();
				} else {
					thread.play = true;
					mainThread = new Thread(thread);
					mainThread.start();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void pause() {
		synchronized (mainThread) {
			if (thread.pause)
				thread.pause = false;
			else
				thread.pause = true;
		}
	}

	public void stop() {
		thread.play = false;
		mainThread.interrupt();
	}

	public void forward() {
		thread.forward = true;
	}
	
	public boolean isPlaying() {
		return thread.play;
	}

	public int time() {
		thread.setCurrentTime();
		long st = thread.getStartedTime();
		long cur = thread.getCurrentTime();
		
		long totalTime = (metadata.getDuration()/1000);
		double temp = (cur - st)/((double)totalTime);
		double time = temp*100; 
		return (int)time;
	}
	
	public boolean isPaused() {
		return thread.pause;
	}

	// Later I have to change this with wrapper for internal functions
	public SongMetada getMetadata() {
		return metadata;
	}

	
	
}
