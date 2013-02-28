package irobots.comm;

import java.awt.Point;
import java.io.Serializable;

/**
 * This class contains information about a robot, like position,
 * heading, and where it is moving.
 * @author martin
 *
 */
@SuppressWarnings("serial")
public class Robot implements Serializable
{
	public transient static Robot me = null;
	
	private Point position;
	private float heading;
	private boolean positionAbsolute;
	
	private transient boolean xAbsolute;
	private transient boolean yAbsolute;
	
	public void setFixedX(int x) {
		position.x = x;
		xAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	public void setFixedY(int y) {
		position.y = y;
		yAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public float getHeading() {
		return heading;
	}
	
	public void setHeading(float heading) {
		this.heading = heading;
	}
	
	public boolean isPositionAbsolute() {
		return positionAbsolute;
	}
	
	public void setPositionAbsolute(boolean positionAbsolute) {
		this.positionAbsolute = positionAbsolute;
	}
}
