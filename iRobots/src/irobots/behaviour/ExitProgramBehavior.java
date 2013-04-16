package irobots.behaviour;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

/**
 * The ExitProgramBehavior exits the program when the escape button is pressed.
 * This Behavior can be used as the highest priority behavior in order to allow
 * exiting the program at any time.
 */
public class ExitProgramBehavior implements Behavior
{
	/**
	 * Takes control whenever the Escape button is pressed.
	 */
	@Override
	public boolean takeControl() {
		return Button.ESCAPE.isDown();
	}
	
	/**
	 * On activation the behavior causes the running program to exit.
	 */
	@Override
	public void action() {
		System.exit(0);
	}
	
	/**
	 * Suppression is not possible
	 */
	@Override
	public void suppress() {
		
	}
	
}
