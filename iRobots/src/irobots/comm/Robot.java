package irobots.comm;

import java.awt.Point;
import java.io.Serializable;

import lejos.robotics.navigation.Pose;

/**
 * This class contains information about a robot, like position,
 * heading, and where it is moving.
 * @author martin
 *
 */
@SuppressWarnings("serial")
public class Robot extends Pose
{
	public static Robot me = null;
	
	private boolean positionAbsolute;
	
	private transient boolean xAbsolute;
	private transient boolean yAbsolute;
	
	public void setFixedX(float x) {
		_location.x = x;
		xAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	public void setFixedY(float y) {
		_location.y = y;
		yAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	public boolean isPositionAbsolute() {
		return positionAbsolute;
	}
	
	public void setPositionAbsolute(boolean positionAbsolute) {
		this.positionAbsolute = positionAbsolute;
	}
}
