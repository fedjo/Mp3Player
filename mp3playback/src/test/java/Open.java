import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFileChooser;

public class Open implements ActionListener {

	private mainWindow w;
	
	public Open(mainWindow open) {
		this.w = open;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(w.getFrmMpPlayer());
		
		w.setFilesOpen(chooser.getSelectedFiles()); 		
	}
}
