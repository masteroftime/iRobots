package irobots.vision;

import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;

/**
 * Abstracts the access to the NXT color sensor and provides
 * utility functions to read information about detected chips
 * or border lines.
 * @author martin
 *
 */
public class ColorSensor {
	
	private lejos.nxt.ColorSensor sensor;
	
	public ColorSensor() {
		sensor = new lejos.nxt.ColorSensor(SensorPort.S1);
	}
	
	public boolean lineDetected() {
		return sensor.getColorID() == SensorConstants.BLACK;
	}
	
	public boolean chipDetected() {
		return sensor.getColorID() != SensorConstants.BLACK 
				&& sensor.getColorID() != SensorConstants.WHITE;
	}
	
	public int getChipColor() {
		return sensor.getColorID();
	}
}
