package mp3playback;

import java.util.Scanner;

import gr.ntua.medialab.mp3playback.PlayingThread;

public class TestMp3Playback {

	private static Scanner in;

	public static void main(String[] args) {

		System.out.println("Hello World!");

		Runnable player = new PlayingThread();
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
