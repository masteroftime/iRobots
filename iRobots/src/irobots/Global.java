package irobots;

import irobots.comm.CommunicationManager;
import irobots.comm.PositionCoordinator;
import irobots.comm.Robot;
import irobots.vision.Camera;
import irobots.vision.ColorSensor;
import irobots.vision.CompassSensor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class Global 
{
	/**
	 * The camera sensor at the front of the robot.
	 */
	public static Camera camera;
	
	/**
	 * The navigator controls the robots movement. It can be used
	 * to navigate to a certain position. The navigator also holds
	 * a robot to the differential pilot which can be used to start
	 * certain movements. Controlling the motors should only be done
	 * through the navigator or the pilot, otherwise the position
	 * calculation will not work.
	 */
	public static Navigator navigator;
	
	/**
	 * The robots color sensor.
	 */
	public static ColorSensor color;
	
	/**
	 * The robots compass sensor.
	 */
	public static CompassSensor compass;
	
	/**
	 * This array holds the data about the other robots which
	 * has been received. The index in the array corresponds to
	 * the id of the other robot. If no information about the robot
	 * has been received the entry in the array equals to null.
	 */
	public static Robot[] robots;
	
	/**
	 * The position coordinator is responsible for sending the
	 * robots position and to update the Robots data in Robot.me
	 */
	public static PositionCoordinator posKoord;
	
	/**
	 * The communication manager handles the communication between
	 * the robots. It is also responsible for receiving the other
	 * robots positions.
	 */
	public static CommunicationManager comm;
	
	/**
	 * The robots unique id.
	 */
	public static int id;
	
	/**
	 * Initialize the Global constants. This method should be called once
	 * during the program initialization and initializes all the sensors and
	 * communication. If the robot has no robot id defined the user is prompted
	 * to set the id of the robot when this method is called.
	 */
	public static void initGlobals() {
		robots = new Robot[3];
		camera = new Camera();
		//Creates the Navigator 4.3 - Tire diameter; 10.15 - Distance between center of the tires
		navigator = new Navigator(new DifferentialPilot(4.3, 10.15, Motor.B, Motor.C));
		color = new ColorSensor();
		compass = new CompassSensor();
		posKoord = new PositionCoordinator();
		comm = new CommunicationManager();
		
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
				Graphics g = new Graphics();
				g.clear();
				g.drawString("Select robot id: ", 5,  5, Graphics.TOP | Graphics.LEFT);
				g.drawString(""+id, 5, 25, Graphics.TOP | Graphics.LEFT);
				
				Button.waitForAnyPress();
				
				if(Button.LEFT.isDown()) {
					id--;
					if(id < 0) id = 0;
					while(Button.LEFT.isDown());
				}
				if(Button.RIGHT.isDown()) {
					id++;
					if(id > 2) id = 7;
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
