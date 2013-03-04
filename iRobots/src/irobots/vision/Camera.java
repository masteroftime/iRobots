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
		cam.enableTracking(true);
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
		
		Rectangle[] ret = new Rectangle[objs.size()];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = objs.get(i);
		}
		return ret;
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
	
	public boolean robotDetected() {
		int n = cam.getNumberOfObjects();
		for(int i = 0; i < n; i++) {
			switch(cam.getObjectColor(i)) {
			case 0:
			case 1:
			case 2:
				return true;
			}
		}
		return false;
	}
	
	public Robot detectRobot(int colormap) {
		if(!objectDetected(colormap))
			return null;

		Rectangle[] objs = getObjects(colormap);
		Rectangle obj = objs[0];
		Robot rob = null;
		
		if(objs.length > 1) {
			obj = mergeObjects(objs);
		}

		rob = new Robot();
		rob.setLocation(rob.pointAt((float)getObjectDistance(obj, 10.0, 4.7), (float)getObjectAngle(objs[0])));
		rob.setHeading((float)(Robot.me.getHeading()+getObjectAngle(obj)));
		return rob;
	}
	
	public NXTCam getCamera() {
		return cam;
	}
	
	public Robot[] detectRobots() {
		Robot[] r = new Robot[3];
		r[0] = detectRobot(0);
		r[1] = detectRobot(1);
		r[2] = detectRobot(2);
		
		int c = 0;
		
		for(Robot rob : r) {
			if(rob != null) c++;
		}
		
		Robot[] robs = new Robot[c];
		c = 0;
		
		for(Robot rob : r) {
			if(rob != null) robs[c] = rob;
		}
		
		return robs;
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
	
	public static Rectangle mergeObjects(Rectangle[] objs) {
		Rectangle r = objs[0];
		float size = r.height * r.width;
		
		for(int i = 1; i < objs.length; i++) {
			if(objs[i].height * objs[i].width > size) {
				r = objs[i];
				size = r.height * r.width;
			}
		}
		
		return r;
	}
}
