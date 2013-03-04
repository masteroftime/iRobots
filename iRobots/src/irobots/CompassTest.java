package irobots;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class CompassTest {
	public static void main(String[] args) {
		CompassHTSensor c = new CompassHTSensor(SensorPort.S3);
		
		while(!Button.ESCAPE.isDown()) {			
			System.out.println(c.getDegrees());
		}
	}
}
