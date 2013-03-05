package irobots.behaviour;

import java.awt.Rectangle;

import javax.microedition.lcdui.Graphics;

import irobots.Global;
import irobots.comm.Robot;
import irobots.tests.ObjectDisplay;
import irobots.vision.Camera;
import irobots.vision.DetectedObject;
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
	private int fRobot;
	
	public FollowBehaviour() {
		this.nav = Global.navigator;
		this.cam = Global.camera;
		fRobot = -1;
	}

	@Override
	public boolean takeControl() {
		suppressed = false;
		return cam.robotDetected();
	}

	@Override
	public void action() {
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		nav.clearPath();
		double speed = p.getTravelSpeed();
		
		while(!suppressed) {
			DetectedObject[] objs = cam.detectRobots();
			int nearID = cam.getNearestId(objs);
			
			if(nearID == -1) {
				new Graphics().fillRect(0, 0, 100, 63);
				
				if(fRobot != -1) {
					Robot fr = Global.robots[fRobot];
					float angle = fr.angleTo(Robot.me.getLocation())+180;
					
					//if other robot is driving towards us we make a 180° turn reversing the following order
					if(Math.abs(angle - fr.getHeading()) < 30) {
						p.rotate(180);
						fRobot = -1;
					} else {
						
					}
				}
				break;
			}
			
			DetectedObject nearest = objs[nearID];
			Robot r = Global.robots[nearID];
			
			float distance = nearest.getDistance();
			float angle = nearest.getAngle();
			
			//if(r == null) {
				Graphics g = new Graphics();
				
				g.clear();
				
				Rectangle rec = nearest.getRect();
				g.drawRect(ObjectDisplay.convX(rec.x), ObjectDisplay.convY(rec.y),
						ObjectDisplay.convX(rec.width), ObjectDisplay.convY(rec.height));
				g.drawString(""+(int)distance, 2, 2, Graphics.TOP | Graphics.LEFT);
				g.drawString(""+(int)angle, 2, 17, Graphics.TOP | Graphics.LEFT);
				g.drawString(""+nearID, 2, 32, Graphics.TOP | Graphics.LEFT);
				
				if(distance < 8)
					distance = 0;
				
				if(Math.abs(angle) > 4) {
					p.rotate(-angle, true);
				} else {
					p.travelArc(Math.signum(angle)-100*(4-Math.abs(angle)), Math.min(distance-8, 10), true);
				}
				
				/*if(Math.abs(angle) < 5) angle = 0;
				
				float steer = -angle*10;
				float spd = Math.min(distance/6, 4);
				
				if(Math.abs(steer) > 120) spd = 2;
				
				p.setTravelSpeed(spd);
				p.steer(steer);*/
			//} else {
				//check sent and detected pos of robot
			//}
			
			
			
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
			
			/*Rectangle r = cam.getNearestRobotRectangle();
			
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
			}*/
			
		}
		
		nav.getMoveController().setTravelSpeed(speed);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
