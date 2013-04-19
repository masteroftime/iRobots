package irobots.behaviour;

import irobots.Global;
import irobots.comm.Robot;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * <p>This behavior uses position data received via wifi in
 * order to help detect other robots via the camera or to
 * avoid crashes with other robots</p>
 * 
 * <p>When another robot is expected to be in front of this
 * robot and the other robot is driving in the same direction
 * as this robot, then we turn to the other robot so that the
 * camera can detect the other robot and we can start following</p>
 * 
 * <p>When the other robot is driving in the opposite direction
 * than this robot, then we turn away from the other robot in
 * order to avoid crashing into the other robot.</p>
 */
public class FindAndEvadeBehaviour implements Behavior {
	private volatile boolean suppressed;
	
	/**
	 * Returns the distance from this robot to the given robot
	 */
	private float getRobotDistance(Robot r) {
		return r.distanceTo(Robot.me.getLocation());
	}
	
	/**
	 * Returns the heading of the given robot relative to the
	 * heading of this robot.
	 */
	private float getRelativeHeading(Robot r) {
		float head = r.getHeading()-Robot.me.getHeading();
		if(head > 360) head -= 360;
		if(head < 0) head += 360;
		
		return head;
	}
	
	/**
	 * Returns the robot which is the nearest to this robot.
	 */
	private Robot getNearestRobot() {
		Robot nearest = null;
		float dist = 0;
		
		for(Robot r : Global.robots) { 
			if(r == null)
				break;
			
			if(nearest == null) {
				nearest = r;
				dist = getRobotDistance(r);
				break;
			}
			
			float nDist = getRobotDistance(r);
			if(nDist < dist) {
				nearest = r;
				dist = nDist;
			}
		}
		
		return nearest;
	}
	
	/**
	 * This behavior takes control when another robot is nearer
	 * than a certain amount to this robot.
	 */
	@Override
	public boolean takeControl() {
		suppressed = false;
		
		Robot r = getNearestRobot();
		
		if(r != null) {
			return getRobotDistance(r) < 35;
		} else return false;
	}

	/**
	 * When the behavior is activated, a robot is in front
	 * of us and the other robot is driving in the same direction
	 * we are, then we turn towards the other robot so that
	 * the other robot can be detected by the camera. When the 
	 * other robot is driving in the opposite direction the robot
	 * turns away in order to avoid a crash.
	 */
	@Override
	public void action() {
		Robot r = getNearestRobot();
		DifferentialPilot p = (DifferentialPilot) Global.navigator.getMoveController();
		
		while(!suppressed && getRobotDistance(r) < 35) {
			float angle = Robot.me.angleTo(r.getLocation());
			
			if(Math.abs(angle) < 60) {
				float heading = getRelativeHeading(r)-180;
				p.steer(heading*(10/9));
			}
		}
	}

	/**
	 * Suppresses this behavior.
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
