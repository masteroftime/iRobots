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
	
	private int id;
	
	private boolean positionAbsolute;
	
	private boolean xAbsolute;
	private boolean yAbsolute;
	
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
	
	public static Robot meFromPose(Pose p) {
		Robot r = new Robot(p.getX(), p.getY(), p.getHeading());
		r.positionAbsolute = me.positionAbsolute;
		r.xAbsolute = me.xAbsolute;
		r.yAbsolute = me.yAbsolute;
		r.id = me.id;
		return r;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
