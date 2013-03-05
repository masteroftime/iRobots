package irobots.vision;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class CompassSensor 
{
	private CompassHTSensor compass;
	private float correction;
	
	public CompassSensor() {
		compass = new CompassHTSensor(SensorPort.S3);
		

	}
	
	public void calibrate() {
		float deg = compass.getDegrees();
		
		if(315 < deg || deg < 45) {
			correction = deg;
		}
		else if(225 < deg) {
			correction = deg - 270;
		}
		else if(135 < deg) {
			correction = deg - 180;
		}
		else {
			correction = deg - 90;
		}
		
		if(correction < 0) {
			correction += 360;
		}
	}
	
	public float getDegrees() {
		float deg = compass.getDegrees() + correction;
		if (deg >= 360) deg -= 360;
		return deg;
	}
}
