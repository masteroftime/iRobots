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
		suppressed = false;
		return true;
	}

	/**
	 * Causes the robot to go forward until another behavior takes over.
	 */
	@Override
	public void action() {
		nav.getMoveController().forward();
		while(!suppressed) {
			nav.getMoveController().forward();
			//Graphics g = new Graphics();
			//g.clear();
			//g.drawString((int)Robot.me.getX() + "/" + (int)Robot.me.getY(), 50, 33, Graphics.HCENTER | Graphics.VCENTER);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	

}
