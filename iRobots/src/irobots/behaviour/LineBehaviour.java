package irobots.behaviour;

import irobots.Global;
import irobots.comm.Robot;
import irobots.vision.ColorSensor;

import javax.microedition.lcdui.Graphics;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior causes the robot not to cross the line which marks
 * the field boundaries if it is detected. When the line is detected
 * the robot will do a 180° turn and then pass control to subsequent
 * behaviors.
 */
public class LineBehaviour implements Behavior {
	
	public static final float FIELD_WIDTH = 90;
	public static final float FIELD_HEIGHT = 90;
	
	private Navigator nav;
	private ColorSensor color;
	private DifferentialPilot pilot;
	
	public LineBehaviour() {
		this.nav = Global.navigator;
		this.color = Global.color;
		this.pilot = (DifferentialPilot)nav.getMoveController();
	}

	/**
	 * Takes control when the line is detected by the color sensor.
	 */
	@Override
	public boolean takeControl() {
		if(color.lineDetected()) {
			//pilot.stop();
			return true;
		}
		return false; 
	}

	/**
	 * Turns the robot 180° and then passes control to other behaviours
	 */
	@Override
	public void action() {
		//************* DRAWING ****************
		Graphics g = new Graphics();
		g.fillRect(0, 0, 100, 63);
		g.setColor(1);
		//************* DRAWING ****************
		
		//calculate new heading
		float deg = Global.compass.getDegrees();
		float heading = deg+170;
		if(heading > 360) heading -= 360;
		
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		p.stop();
		p.travel(-5);
		
		//BeSt prototype: when following the robot outside the field do not turn but just go back a bit
		if(!Global.camera.robotDetected()) {
			p.rotate(170);
			
			//when the line is still detected correct the turning withe the value from the compass
			if(Global.color.lineDetected()) {
				float diff = Global.compass.getDegrees()-heading;
				p.rotate(diff);
			}
		} else {
			p.travel(-5);
		}
		
		//update the position in Robot.me
		Robot.me =  Robot.meFromPose(nav.getPoseProvider().getPose());
		
		//reset the robot position if its out of the fields boundaries
		if(Robot.me.getX() < 0) {
			Robot.me.setFixedX(0);
		}
		if(Robot.me.getY() < 0) {
			Robot.me.setFixedY(0);
		}
		if(Robot.me.getX() > FIELD_WIDTH+5) {
			Robot.me.setFixedX(FIELD_WIDTH);
		}
		if(Robot.me.getY() > FIELD_HEIGHT+5) {
			Robot.me.setFixedY(FIELD_HEIGHT);
		}
		
		//update the position in the navigator
		nav.getPoseProvider().setPose(Robot.me);
	}

	/**
	 * Not going out of the line boundaries has the highest priority
	 * and cannot be supressed so supress does nothing.
	 */
	@Override
	public void suppress() {
		return;
	}
	
}
