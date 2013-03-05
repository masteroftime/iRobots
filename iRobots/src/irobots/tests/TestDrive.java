package irobots.tests;

import irobots.vision.ColorSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;

public class TestDrive {
	public static void main(String[] args) {
		Navigator nav = new Navigator(new DifferentialPilot(4.3, 14.5, Motor.B, Motor.C));
		ColorSensor s = new ColorSensor();
		/*nav.rotateTo(180);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nav.rotateTo(0);*/
		
		/*nav.getMoveController().forward();
		
		while(!s.lineDetected());
		
		nav.stop();
		
		Pose p = nav.getPoseProvider().getPose();
		System.out.println("Pos:"+p.getX()+"/"+p.getY());
		
		while(!Button.ESCAPE.isDown());*/
		
		nav.addWaypoint(100, 0);
		nav.addWaypoint(100, 100);
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
