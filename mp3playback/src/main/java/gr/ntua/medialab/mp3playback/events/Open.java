package gr.ntua.medialab.mp3playback.events;

import gr.ntua.medialab.mp3playback.ui.mainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Open implements ActionListener {

	private mainWindow w;
	
	public Open(mainWindow open) {
		this.w = open;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return ".mp3";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".mp3") || f.isDirectory() )
					return true;
				else
					return false;
			}
		});
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showOpenDialog(w.getFrmMpPlayer());
		
		w.setFilesOpen(chooser.getSelectedFiles()); 
		w.updateSongList(chooser.getSelectedFiles());
	}
}
