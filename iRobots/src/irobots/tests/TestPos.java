package irobots.tests;

import irobots.comm.CommunicationManager;
import irobots.comm.PositionCoordinator;

import java.util.Random;

import lejos.nxt.Button;

public class TestPos {
	public static void main(String[] args) {
		int x = 0, y = 0;
		double angle = 0;
		Random r = new Random();
		CommunicationManager comm = new CommunicationManager();
		
		while(!Button.ESCAPE.isDown()) {
			x = r.nextInt(100);
			y = r.nextInt(100);
			angle = (int)(r.nextDouble()*360);
			comm.sendMessage("pos:0;"+x+"/"+y+"/"+angle, false);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
