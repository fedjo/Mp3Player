import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Play implements ActionListener {

	private mainWindow w;
	
	public Play(mainWindow c) {
		this.w = c;
	}
	
	public void actionPerformed(ActionEvent e) {
		/*File[] songs = c.getFilesOpen();
		for (File f : songs) {
			c.updatePanels(f);
			
		}*/
		w.playSong();
		
	}

}
