package irobots.comm;

import lejos.robotics.navigation.Pose;

/**
 * This class contains information about a robot, like position,
 * heading, and where it is moving.
 */
public class Robot extends Pose
{
	/**
	 * This variable contains the information about
	 *  the robot running the program.
	 */
	public static volatile Robot me = null;
	
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
	
	/**
	 * Sets the x-coordinate of the robot and marks it to be absolute.
	 * When both the x-coordinate and y-coordinate are marked as absolute
	 * the position of the robot is also marked as being absolute.
	 * 
	 * @param x The x-coordinate of the robot
	 */
	public void setFixedX(float x) {
		_location.x = x;
		xAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	/**
	 * Sets the y-coordinate of the robot and marks it to be absolute.
	 * When both the x-coordinate and y-coordinate are marked as absolute
	 * the position of the robot is also marked as being absolute.
	 * 
	 * @param y The y-coordinate of the robot
	 */
	public void setFixedY(float y) {
		_location.y = y;
		yAbsolute = true;
		
		if(xAbsolute && yAbsolute)
			positionAbsolute = true;
	}
	
	/**
	 * Returns whether the position of the robot is absolute.
	 * If the robots position is absolute (0,0) is in the south-east
	 * corner of the field. If the position is not absolute (0,0) is
	 * at the starting position of the robot.
	 */
	public boolean isPositionAbsolute() {
		return positionAbsolute;
	}
	
	/**
	 * Sets if the postion of the robot is absolute.
	 */
	public void setPositionAbsolute(boolean positionAbsolute) {
		this.positionAbsolute = positionAbsolute;
	}
	
	/**
	 * Creates a new robot from the position data provided by
	 * the given pose. Values not contained in the given pose
	 * are taken from Robot.me. This method is primarily intended
	 * to be used to update Robot.me with new information from
	 * the pose provider.
	 * @param p The pose to extract information from
	 * @return A robot instance corresponding to the position
	 * and heading in the given pose.
	 */
	public static Robot meFromPose(Pose p) {
		Robot r = new Robot(p.getX(), p.getY(), p.getHeading());
		r.positionAbsolute = me.positionAbsolute;
		r.xAbsolute = me.xAbsolute;
		r.yAbsolute = me.yAbsolute;
		r.id = me.id;
		return r;
	}

	/**
	 * Returns the unique id of the robot.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the robot.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
