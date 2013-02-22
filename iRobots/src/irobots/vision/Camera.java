package irobots.vision;

import java.awt.Point;
import java.awt.Rectangle;
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
	
	public double getObjectAngle(Rectangle obj) {
		double x = obj.getCenterX() - SIZE_X/2;
		return (x / (SIZE_X / 2)) * (VIEW_ANGLE / 2); 
	}
}
