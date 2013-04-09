package irobots.tests;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import irobots.Global;
import irobots.vision.ColorSensor;
import irobots.vision.CompassSensor;

public class LineFollower 
{
	private static ColorSensor color;
	private static DifferentialPilot p;
	private static CompassSensor compass;
	private static float heading;
	
	public static void main(String[] args) {
		Global.initGlobals();
		//p = new DifferentialPilot(4.3, 10.15, Motor.B, Motor.C);
		p = (DifferentialPilot)Global.navigator.getMoveController();
		color = Global.color;//new ColorSensor();
		compass = Global.compass;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Button.ESCAPE.isDown())
					Thread.yield();
				System.exit(0);
			}
		}).start();
		
		heading = compass.getDegrees();
		
		p.setTravelSpeed(15);
		
		NXTRegulatedMotor b = Motor.B;
		NXTRegulatedMotor c = Motor.C;
		
		try {
			while(true) {
				b.forward();
				c.stop();
				while(!color.lineDetected());
				while(color.lineDetected());
				Thread.sleep(200);
				
				c.forward();
				b.stop();
				while(!color.lineDetected());
				while(color.lineDetected());
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkPos() {
		
	}
}
