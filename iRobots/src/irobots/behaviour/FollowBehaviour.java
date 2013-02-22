package irobots.behaviour;

import irobots.vision.Camera;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class FollowBehaviour implements Behavior {
	
	public final int ROBOT_COLORMAP = 0;
	
	private Navigator nav;
	private Camera cam;
	
	public FollowBehaviour(Navigator nav, Camera cam) {
		this.nav = nav;
		this.cam = cam;
	}

	@Override
	public boolean takeControl() {
		return cam.objectDetected(ROBOT_COLORMAP);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		
	}
	
}
