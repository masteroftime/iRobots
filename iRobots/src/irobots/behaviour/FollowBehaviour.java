package irobots.behaviour;

import java.awt.Rectangle;

import javax.microedition.lcdui.Graphics;

import irobots.Global;
import irobots.comm.Robot;
import irobots.tests.ObjectDisplay;
import irobots.vision.Camera;
import lejos.nxt.Button;
import lejos.robotics.navigation.DifferentialPilot;
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
		nav.clearPath();
		double speed = nav.getMoveController().getTravelSpeed();
		
		while(!suppressed) {
			Rectangle r = cam.getNearestRobotRectangle();
			
			if(r == null)
				break;
			
			double angle = Camera.getObjectAngle(r);
			double distance = Camera.getObjectDistance(r, 3, 15);
			
			Graphics g = new Graphics();
			g.clear();
			
			g.drawRect(ObjectDisplay.convX(r.x), ObjectDisplay.convY(r.y),
					ObjectDisplay.convX(r.width), ObjectDisplay.convY(r.height));
			
			g.drawArc(25, 10, 50, 50, 0, (int)angle);
			g.drawLine(5, 5, (int)(distance*2), 5);
			g.drawString(""+(int)distance, 5, 6, Graphics.TOP | Graphics.LEFT);
			
			if(distance < 7)
				distance = 0;
			
			DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
			
			p.setTravelSpeed(distance/8);
			p.steer(-angle*3);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			/*Robot[] robs = cam.detectRobots();
			
			if(robs.length == 0) {
				continue;
			}
			
			Robot r = robs[0];
			
			try {
			if(robs.length > 1) {
				for(int i = 1; i < robs.length; i++) {
					if (Robot.me.distanceTo(robs[i].getLocation()) < Robot.me.distanceTo(r.getLocation())) {
						r = robs[i];
					}
				}
			}
			} catch (NullPointerException e) {
				for(Robot rb : robs) {System.out.println(rb); }
				while(!Button.ESCAPE.isDown());
				System.exit(1);
			}
			
			nav.clearPath();
			nav.goTo(new Waypoint(r.pointAt(5, r.getHeading()+180)));*/
		}
		
		nav.getMoveController().setTravelSpeed(speed);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
