package irobots;

import irobots.comm.Robot;
import irobots.vision.Camera;
import irobots.vision.ColorSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.Navigator;

public class Global 
{
	public static Camera camera;
	public static Navigator navigator;
	public static ColorSensor color;
	public static CompassHTSensor compass;
	public static Robot[] robots;
}
