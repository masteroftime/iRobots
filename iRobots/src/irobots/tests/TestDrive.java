package irobots.tests;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class TestDrive {
	public static void main(String[] args) {
		Navigator nav = new Navigator(new DifferentialPilot(4.3, 14.5, Motor.B, Motor.C));
		/*nav.rotateTo(180);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nav.rotateTo(0);*/
		
		nav.addWaypoint(100, 0);
		nav.followPath();
		nav.waitForStop();
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nav.addWaypoint(0, 0);
		nav.followPath();
		nav.waitForStop();*/
	}
}
