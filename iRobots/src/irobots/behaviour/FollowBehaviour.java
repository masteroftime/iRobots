package irobots.behaviour;

import java.awt.Rectangle;

import irobots.Global;
import irobots.comm.Robot;
import irobots.vision.Camera;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

/**
 * This behavior makes the robot follow any other robot which
 * has been detected by the camera.
 * 
 * @author Martin Feiler
 *
 */
public class FollowBehaviour implements Behavior {
	private Navigator nav;
	private Camera cam;
	private volatile boolean suppressed = false;
	
	public FollowBehaviour() {
		this.nav = Global.navigator;
		this.cam = Global.camera;
	}

	@Override
	public boolean takeControl() {
		return cam.robotDetected();
	}

	@Override
	public void action() {
		suppressed = false;
		
		while(!suppressed && (nav.isMoving() || cam.robotDetected())) {
			Robot[] robs = cam.detectRobots();
			
			if(robs.length == 0) {
				continue;
			}
			
			Robot r = robs[0];
			
			if(robs.length > 1) {
				for(int i = 1; i < robs.length; i++) {
					if (Robot.me.distanceTo(robs[i].getLocation()) < Robot.me.distanceTo(r.getLocation())) {
						r = robs[i];
					}
				}
			}
			
			nav.clearPath();
			nav.goTo(new Waypoint(r.pointAt(5, r.getHeading()+180)));
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
