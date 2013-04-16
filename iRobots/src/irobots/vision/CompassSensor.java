package irobots.vision;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

/**
 * This class provides access to the compass sensor
 * which is calibrated in a way that 0° are on the
 * positive x-axis.
 */
public class CompassSensor 
{
	private CompassHTSensor compass;
	private float correction;
	
	public CompassSensor() {
		compass = new CompassHTSensor(SensorPort.S3);
	}
	
	/**
	 * </p>Calibrate the compass sensor in order that it is
	 * aligned with the boundaries of the field so that
	 * two boundary lines go from north to south and two
	 * from west to east.<p>
	 * 
	 * <code>
	 *               North
	 *       ----------------------
	 *       |                    |
	 *       |                    |
	 *  West |                    |  East
	 *       |                    |
	 *       |                    |
	 *       ----------------------
	 *               South
	 * </code>
	 * 
	 * <p>During the process of calibration the robot
	 * has to stand parallel to any boundary line of
	 * the field.</p>
	 */
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
	
	/**
	 * Returns the angle the robot is facing in degrees.
	 */
	public float getDegrees() {
		float deg = compass.getDegrees() + correction;
		if (deg >= 360) deg -= 360;
		return deg;
	}
}
