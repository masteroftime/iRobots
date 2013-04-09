package irobots.vision;

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
	
	public static final int BLACK = 7;
	public static final int WHITE = 6;
	
	public ColorSensor() {
		sensor = new lejos.nxt.ColorSensor(SensorPort.S1);
	}
	
	public boolean lineDetected() {
		//return sensor.getColorID() != WHITE && sensor.getColorID() != lejos.nxt.ColorSensor.YELLOW;
		return sensor.getColorID() == BLACK;
	}
	
	public boolean chipDetected() {
		return sensor.getColorID() != BLACK 
				&& sensor.getColorID() != WHITE;
	}
	
	public int getChipColor() {
		return sensor.getColorID();
	}
}
