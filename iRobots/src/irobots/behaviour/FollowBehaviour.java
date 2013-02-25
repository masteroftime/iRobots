package irobots.behaviour;

import java.awt.Rectangle;

import irobots.vision.Camera;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

/**
 * This behavior makes the robot follow any other robot which
 * has been detected by the camera.
 * 
 * @author Martin Feiler
 *
 */
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
		Rectangle[] objs = cam.getObjects(ROBOT_COLORMAP);
		
		if(objs.length == 0) {
			return;
		}
		
		
	}

	@Override
	public void suppress() {
		
	}
	
}
