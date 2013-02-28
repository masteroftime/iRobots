package irobots.vision;

import irobots.comm.Robot;

import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;

/**
 * Abstracts the access to the NXTCam and provides utility functions to
 * read the objects detected by the camera
 * @author martin
 *
 */
public class Camera {

	private NXTCam cam;
	
	public static final double VIEW_ANGLE = 43;
	public static final int SIZE_X = 144;
	public static final int SIZE_Y = 88;
	
	public Camera() {
		cam = new NXTCam(SensorPort.S3);
	}
	
	/**
	 * Returns all objects which match the given colormap
	 * @param colormap The colormap id (starting at zero)
	 * @return An array of Rectangles corresponding to the detected objects
	 */
	public Rectangle[] getObjects(int colormap) {
		int n = cam.getNumberOfObjects();
		ArrayList<Rectangle> objs = new ArrayList<>(n);
		for(int i = 0; i < n; i++) {
			if(cam.getObjectColor(i) == colormap) {
				objs.add(cam.getRectangle(i));
			}
		}
		return (Rectangle[]) objs.toArray();
	}
	
	/**
	 * Checks if an object of the given color has been detected.
	 * @param colormap The colormap which should be checked.
	 * @return returns true if at least one object has bee detected. False otherwise
	 */
	public boolean objectDetected(int colormap) {
		int n = cam.getNumberOfObjects();
		for(int i = 0; i < n; i++) {
			if(cam.getObjectColor(i) == colormap) {
				return true;
			}
		}
		return false;
	}
	
	public Robot detectRobot(int colormap) {
		if(!objectDetected(colormap))
			return null;
		
		Rectangle[] objs = getObjects(colormap);
		Rectangle obj = null;
		
		if (objs.length == 1) {
			Point p = objs[0].getLocation();
			Dimenstion d = objs[0].getSize();
		}
		if (objs.length == 2) {
		}
		if (objs.length == 3) {
		}
		if (objs.length == 4) {
		}
			
		} else {
			obj = objs[0];
		}
		
		return new Robot();
	}
	
	public Robot[] detectRobots() {
		return null;
	}
	
	/**
	 * Utility function which calculates the angle to a certain detected object,
	 * relative to the direction the robot is facing.
	 * @param obj The object to which the angle should be calculated
	 * @return The angle to the given object in degrees.
	 */
	public static double getObjectAngle(Rectangle obj) {
		double x = obj.getCenterX() - SIZE_X/2;
		return (x / (SIZE_X / 2)) * (VIEW_ANGLE / 2); 
	}
	
	
	/**
	 * Returns the distance to the given object.
	 * @param obj The object to which the distance should be measured
	 * @param size The horizontal size of the object in cm
	 * @return The distance to the given object in cm
	 */
	public static double getObjectDistance(Rectangle obj, double width, double height) {
		/*normalize the detected object dimensions
		 * the bigger normalized value will be used for the distance
		 * because it is less likely to be reduced in size resulting
		 * from the object being rotated away from the camera
		 */
		double normW = obj.width / width;
		double normH = obj.height / height;
		
		double psize = 0;	//pixel size of the object
		double rsize = 0;	//realworld size of the object
		double res = 0;		//camera resolution in the used axis
		
		if (normW < normH) {
			psize = obj.height;
			rsize = height;
			res = SIZE_Y;
		} else {
			psize = obj.width;
			rsize = width;
			res = SIZE_X;
		}
		
		return (rsize * res) / (psize * 2 * Math.tan(VIEW_ANGLE / 2));
	}
}