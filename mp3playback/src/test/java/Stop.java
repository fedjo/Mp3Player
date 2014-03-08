import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Stop implements ActionListener {

	private mainWindow w;
	
	public Stop(mainWindow w) {
		super();
		this.w = w;
	}

	public void actionPerformed(ActionEvent e) {
		w.stopSong();
	}

}
