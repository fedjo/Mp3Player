package gr.ntua.medialab.mp3playback.events;

import gr.ntua.medialab.mp3playback.ui.mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FastForward implements ActionListener {

	private mainWindow w;

	public FastForward(mainWindow w) {
		this.w = w;
	}

	public void actionPerformed(ActionEvent e) {
		w.fastForward();
	}
}
