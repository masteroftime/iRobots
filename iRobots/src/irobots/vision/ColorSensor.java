package irobots.vision;

import lejos.nxt.SensorPort;

/**
 * Abstracts the access to the NXT color sensor and provides
 * utility functions to read information about detected chips
 * or border lines.
 */
public class ColorSensor {
	
	private lejos.nxt.ColorSensor sensor;
	
	public static final int BLACK = 7;
	public static final int WHITE = 6;
	
	public ColorSensor() {
		sensor = new lejos.nxt.ColorSensor(SensorPort.S1);
	}
	
	/**
	 * This method returns true if the color sensor has detected
	 * the black boundary line.
	 */
	public boolean lineDetected() {
		return sensor.getColorID() == BLACK;
	}
	
	/**
	 * This method returns true if the color sensor has detected 
	 * a colored chip.
	 */
	public boolean chipDetected() {
		return sensor.getColorID() != BLACK 
				&& sensor.getColorID() != WHITE;
	}
	
	/**
	 * This method returns the color of the detected chip.
	 */
	public int getChipColor() {
		return sensor.getColorID();
	}
}
