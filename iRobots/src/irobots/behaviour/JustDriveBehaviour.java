package irobots.behaviour;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class JustDriveBehaviour implements Behavior {
	
	private Navigator nav;
	private volatile boolean suppressed;
	
	public JustDriveBehaviour(Navigator nav) {
		this.nav = nav;
	}

	/**
	 * always returns true because this is the behaviour which should be
	 * executed if nothing else has to be done
	 */
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		nav.getMoveController().forward();
		while(!suppressed);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	

}
