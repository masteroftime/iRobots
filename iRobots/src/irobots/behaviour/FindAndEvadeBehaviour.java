package irobots.behaviour;

import irobots.Global;
import irobots.comm.Robot;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FindAndEvadeBehaviour implements Behavior {
	private volatile boolean suppressed;
	
	private float getRobotDistance(Robot r) {
		return r.distanceTo(Robot.me.getLocation());
	}
	
	private float getRelativeHeading(Robot r) {
		float head = r.getHeading()-Robot.me.getHeading();
		if(head > 360) head -= 360;
		if(head < 0) head += 360;
		
		return head;
	}
	
	private Robot getNearestRobot() {
		Robot nearest = null;
		float dist = 0;
		
		for(Robot r : Global.robots) { 
			if(r == null)
				break;
			
			if(nearest == null) {
				nearest = r;
				dist = getRobotDistance(r);
				break;
			}
			
			float nDist = getRobotDistance(r);
			if(nDist < dist) {
				nearest = r;
				dist = nDist;
			}
		}
		
		return nearest;
	}
	
	@Override
	public boolean takeControl() {
		suppressed = false;
		return getRobotDistance(getNearestRobot()) < 35;
	}

	@Override
	public void action() {
		Robot r = getNearestRobot();
		DifferentialPilot p = (DifferentialPilot) Global.navigator.getMoveController();
		
		while(!suppressed && getRobotDistance(r) < 35) {
			float angle = Robot.me.angleTo(r.getLocation());
			
			if(Math.abs(angle) < 60) {
				float heading = getRelativeHeading(r)-180;
				p.steer(heading*(10/9));
			}
				
				/*float absHead = Math.abs(heading);
				
				if(absHead < 45) {
					//turn+drive
					p.steer(turnRate)
				}
				else if(absHead < 90) {
					//one wheel turn forward
				}
				else if(absHead < 135) {
					//one wheel turn backward
				}
				else {
					//0-degree turn
				}else if(Math.abs(angle) > 120) {
				
			}*/
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
}
