package irobots.behaviour;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ExitProgramBehavior implements Behavior
{

	@Override
	public boolean takeControl() {
		return Button.ESCAPE.isDown();
	}

	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {
		
	}
	
}
