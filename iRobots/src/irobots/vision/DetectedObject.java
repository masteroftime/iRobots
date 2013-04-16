package irobots.vision;

import java.awt.Rectangle;

/**
 * This class contains information about an object
 * which has been detected by the robots camera sensor
 * such as distance and angle to the other object as
 * well as the colormap id of the detected object.
 */
public class DetectedObject 
{
	private Rectangle rect;
	private float angle;
	private int id;
	private float distance;
	
	public DetectedObject(Rectangle rect, int id) {
		super();
		this.rect = rect;
		this.id = id;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
}
