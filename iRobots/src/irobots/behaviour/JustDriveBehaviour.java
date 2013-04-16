package irobots.behaviour;

import javax.microedition.lcdui.Graphics;

import irobots.Global;
import irobots.comm.Robot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

/**
 * This behavior will make the robot go forward if it is
 * idle and no other behaviors take control over the robot.
 * This behavior can be used to let the robot explore the
 * field if no other action has to be done.
 */
public class JustDriveBehaviour implements Behavior {
	
	private Navigator nav;
	private volatile boolean suppressed;
	
	public JustDriveBehaviour() {
		this.nav = Global.navigator;
	}

	/**
	 * Always returns true because this behavior does not have a certain
	 * trigger but is a behavior which can be executed when the robot is
	 * idle.
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
			
			//************* DRAWING ****************
			Graphics g = new Graphics();
			g.clear();
			g.drawString((int)Robot.me.getX() + "/" + (int)Robot.me.getY(), 50, 33, Graphics.HCENTER | Graphics.VCENTER);
			//************* DRAWING ****************
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
