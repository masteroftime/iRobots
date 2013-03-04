package irobots.behaviour;

import irobots.Global;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

/**
 * This behavior will make the robot go forward if it is
 * idle and no other behaviors take control over the robot.
 * This behavior can be used to let the robot explore the
 * field if no other action has to be done.
 * 
 * @author Martin Feiler
 *
 */
public class JustDriveBehaviour implements Behavior {
	
	private Navigator nav;
	private volatile boolean suppressed;
	
	public JustDriveBehaviour() {
		this.nav = Global.navigator;
	}

	/**
	 * always returns true because this is the behaviour which should be
	 * executed if nothing else has to be done
	 */
	@Override
	public boolean takeControl() {
		return true;
	}

	/**
	 * Causes the robot to go forward until another behavior takes over.
	 */
	@Override
	public void action() {
		System.out.println("Just Driving");
		nav.getMoveController().forward();
		//while(!suppressed); //not sure if needed
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	

}
