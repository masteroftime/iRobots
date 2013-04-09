package irobots.behaviour;

import irobots.Global;
import irobots.comm.Robot;
import irobots.tests.ObjectDisplay;
import irobots.vision.Camera;
import irobots.vision.DetectedObject;

import java.awt.Rectangle;

import javax.microedition.lcdui.Graphics;

import lejos.robotics.navigation.DifferentialPilot;
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
			
			if(suppressed)
				break;
			
			/*if(nearID == -1) {
				new Graphics().fillRect(0, 0, 100, 63);
				
				if(fRobot != -1) {
					Robot fr = Global.robots[fRobot];
					float angle = fr.angleTo(Robot.me.getLocation());
					
					//if other robot is driving towards us we make a 180° turn reversing the following order
					if(Math.abs(angle - fr.getHeading()) < 30) {
						p.rotate(180);
						fRobot = -1;
					} else {
						angle += 180;
						if(angle >= 360) angle -= 360;
						float distance = fr.distanceTo(Robot.me.getLocation());
						move(angle, distance);
					}
				}
				break;
			}*/
			if(nearID == -1)
				break;
			
			DetectedObject nearest = objs[nearID];
			Robot r = Global.robots[nearID];
			
			float distance = nearest.getDistance();
			float angle = nearest.getAngle();
			
			//
			/*if(r != null) {
				float rDistance = r.distanceTo(Robot.me.getLocation());
				float rAngle = r.angleTo(Robot.me.getLocation()) + 180;
				if(rAngle > 360) rAngle -= 360;
				if(rAngle < 0) rAngle += 360;
				float dAngle = angle < 0 ? angle + 360 : angle; 
				
				if(Math.abs(rDistance-distance*2) < 30 && Math.abs(dAngle-angle) < 15) {
					fRobot = nearID;
				}
			}*/
			
			if(r != null) {
				float dAngle = r.getHeading()-Robot.me.getHeading();
				if(dAngle > 360) dAngle -= 360;
				if(dAngle < 0  ) dAngle += 360;
				
				//check if the angle is left or right (-1 left/1 right)
				int dir = dAngle > 180 ? -1 : 1;	 
				
				
				if(dAngle < 45) { //almost same direction as other robot
					move(angle, distance);
				}
				else if(dAngle < 90) { //other robot goes to left or right in same direction
					move(angle + 5*dir,distance);
				}
				else if(dAngle < 135) {	//other robot goes to left or right in opposite direction
					move(angle + 5*dir,distance-5);
				}
				else {	//other robot goes in opposite direction
					move(-180 * dir,0); //do a 180° turn
				}
			} else {
				move(angle, distance);
			}
			
			//********* DRAWING *********
			Graphics g = new Graphics();
			
			g.clear();
			
			Rectangle rec = nearest.getRect();
			g.drawRect(ObjectDisplay.convX(rec.x), ObjectDisplay.convY(rec.y),
					ObjectDisplay.convX(rec.width), ObjectDisplay.convY(rec.height));
			g.drawString(""+(int)distance, 2, 2, Graphics.TOP | Graphics.LEFT);
			g.drawString(""+(int)angle, 2, 17, Graphics.TOP | Graphics.LEFT);
			g.drawString(""+nearID, 2, 32, Graphics.TOP | Graphics.LEFT);
			//********* DRAWING *********
		}
		
		nav.getMoveController().setTravelSpeed(speed);
	}
	
	public void move(float angle, float distance) {
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		
		if(Math.abs(angle) > 4) {
			p.rotate(-angle, true);
		} else {
			p.travelArc(Math.signum(angle)-100*(4-Math.abs(angle)), Math.min(distance-12, 10), true);
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
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
