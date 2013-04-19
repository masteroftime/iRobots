package irobots.tests;

import irobots.vision.ColorSensor;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class MeasureField {
	public static void main(String[] args) {
		Navigator nav = new Navigator(new DifferentialPilot(4.3, 14.5, Motor.B, Motor.C));
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		
		ColorSensor c = new ColorSensor();
		
		p.forward();
		
		while(!c.lineDetected() && !Button.ESCAPE.isDown());
		
		System.out.println((int)nav.getPoseProvider().getPose().getX() + "/" + (int)nav.getPoseProvider().getPose().getY());
		
		while(!Button.ESCAPE.isDown());
	}
}
