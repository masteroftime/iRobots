package irobots;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import irobots.comm.PositionKoordinator;
import irobots.comm.Robot;
import irobots.vision.Camera;
import irobots.vision.ColorSensor;
import lejos.nxt.Button;
import lejos.nxt.LCD;
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
	public static PositionKoordinator posKoord;
	
	public static int id;
	
	public static void initGlobals() {
		camera = new Camera();
		//Creates the Navigator 4.3 - Tire diameter; 10.15 - Distance between center of the tires
		navigator = new Navigator(new DifferentialPilot(4.3, 10.15, Motor.B, Motor.C));
		color = new ColorSensor();
		compass = new CompassHTSensor(SensorPort.S3);
		posKoord = new PositionKoordinator();
		
		File f = new File("robId.bin");
		if(f.exists()) {
			try {
				DataInputStream din = new DataInputStream(new FileInputStream(f));
				id = din.readInt();
				din.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			while(true) {
				LCD.clear();
				System.out.println("Select robot id: ");
				System.out.println(id);
				
				if(Button.LEFT.isDown()) {
					id--;
					if(id < 0) id = 0;
					while(Button.LEFT.isDown());
				}
				if(Button.RIGHT.isDown()) {
					id++;
					if(id > 0) id = 7;
					while(Button.RIGHT.isDown());
				}
				if(Button.ENTER.isDown()) {
					break;
				}
				if(Button.ESCAPE.isDown()) {
					System.exit(0);
				}
			}
			LCD.clear();
			
			DataOutputStream dout;
			try {
				dout = new DataOutputStream(new FileOutputStream(f));
				dout.writeInt(id);
				dout.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
