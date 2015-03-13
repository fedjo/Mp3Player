package gr.ntua.medialab.mp3playback.events;
import gr.ntua.medialab.mp3playback.ui.mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pause implements ActionListener {

	private mainWindow w;
	
	public Pause(mainWindow w) {
		this.w = w;
	}

	public void actionPerformed(ActionEvent e) {
		w.pauseSong();
	}

}
