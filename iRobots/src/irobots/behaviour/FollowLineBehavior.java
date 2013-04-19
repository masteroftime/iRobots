package irobots.behaviour;

import irobots.Global;
import irobots.vision.ColorSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This behavior can be used to make the robot follow a
 * black line on the ground.
 */
public class FollowLineBehavior implements Behavior {

	private volatile boolean suppressed;
	private boolean always;
	
	/**
	 * Create a new instance of this behavior.
	 * 
	 * <p>There are two possible modes to activate
	 * this behavior. In the first mode the robot
	 * always searches for the line, so this behavior
	 * always becomes active if it is bot supressed.
	 * When the line is not in reach of the robot it
	 * will continue to circle around as long as a
	 * higher priority behavior takes over.</p>
	 * 
	 * <p>In the second mode the behavior only becomes
	 * active if the line is detected. This can be used
	 * to have other lower priority behaviors which are
	 * active as long as the line is not detected, e.g.
	 * for locating the line. Then this behavior takes
	 * over once the robot reaches the line.</p>
	 * @param alwaysFollow Whether this behavior should
	 * always be active or if lower priorty behaviors
	 * should be possible and the line following should
	 * only start when the line is detected.
	 */
	public FollowLineBehavior(boolean alwaysFollow) {
		this.always = alwaysFollow;
	}
	
	/**
	 * Depending in what activation mode this behavior
	 * is it always takes control or only when the line
	 * is detected by the color sensor.
	 */
	@Override
	public boolean takeControl() {
		suppressed = false;
		
		return always || Global.color.lineDetected();
	}

	/**
	 * When the behavior is activated the robot will
	 * follow the line by turning to the right when
	 * the line is to the right and turning to the left
	 * when the line is to the left. This makes to
	 * robot move along the line.
	 */
	@Override
	public void action() {
		DifferentialPilot p = (DifferentialPilot)Global.navigator.getMoveController();
		ColorSensor color = Global.color;
		
		double travelSpeed = p.getTravelSpeed();
		p.setTravelSpeed(15);
		
		try {
			while(!suppressed) {
				p.stop();
				p.steer(100);
				while(!color.lineDetected() && !suppressed);
				while(color.lineDetected() && !suppressed);
				if(suppressed) break;
				
				Thread.sleep(200);
				p.stop();
				p.steer(-100);
				
				while(!color.lineDetected() && !suppressed);
				while(color.lineDetected() && !suppressed);
				if(suppressed) break;
				
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		p.setTravelSpeed(travelSpeed);
	}

	/**
	 * Suppresses this behavior.
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
