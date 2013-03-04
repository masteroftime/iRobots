package irobots.comm;

import lejos.robotics.navigation.Pose;

/**
 * This class contains information about a robot, like position,
 * heading, and where it is moving.
 * @author martin
 *
 */
public class Robot extends Pose
{
	public static Robot me = null;
	
	private boolean positionAbsolute;
	
	private transient boolean xAbsolute;
	private transient boolean yAbsolute;
	
	public Robot() {
		super();
	}
	
	public Robot(float x, float y, float heading) {
		super(x, y, heading);
	}
	
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
	
	public static Robot fromPose(Pose p) {
		return new Robot(p.getX(), p.getY(), p.getHeading());
	}
}
