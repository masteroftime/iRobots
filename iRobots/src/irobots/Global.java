package irobots;

import irobots.comm.Robot;
import irobots.vision.Camera;
import irobots.vision.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class Global 
{
	public static Camera camera;
	public static Navigator navigator;
	public static ColorSensor color;
	public static CompassHTSensor compass;
	public static Robot[] robots;
	
	public static void initGlobals() {
		camera = new Camera();
		//Creates the Navigator 4.3 - Tire diameter; 10.15 - Distance between center of the tires
		navigator = new Navigator(new DifferentialPilot(4.3, 10.15, Motor.B, Motor.C));
		color = new ColorSensor();
		compass = new CompassHTSensor(SensorPort.S3);
	}
}
