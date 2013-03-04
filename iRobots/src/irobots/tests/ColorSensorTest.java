package irobots.tests;

import irobots.vision.ColorSensor;
import lejos.nxt.Button;

public class ColorSensorTest 
{
	public static void main(String[] args) {
		ColorSensor s = new ColorSensor();
		
		while(!Button.ESCAPE.isDown()) {
			System.out.println(s.lineDetected());
			System.out.println(s.getChipColor());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
