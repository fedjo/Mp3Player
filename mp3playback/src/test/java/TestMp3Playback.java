

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import gr.ntua.medialab.mp3playback.impl.PlayingThread;

public class TestMp3Playback {

	private static Scanner in;

	public static void main(String[] args) {

        String mp3File = "/home/fedjo/Music/Mikro_feat_pes_mou _le3h.mp3";

		
		System.out.println("Hello World!");

		PlayingThread player = PlayingThread.getInstance();
		player.setMp3File(mp3File);
		Thread playerThread = new Thread(player);
		playerThread.start();

		boolean temp = true;
		
		while (temp) {
			in = new Scanner(System.in);
			int threadCommand = in.nextInt();

			try {
				switch (threadCommand) {

				case 1:
					playerThread.sleep(1000);
					break;
				case 2:
					playerThread.notify();
					break;
				case 3:
					playerThread.interrupt();
					temp = false;
					break;
				default:
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
