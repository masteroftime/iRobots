package irobots.behaviour;

import javax.microedition.lcdui.Graphics;

import irobots.Global;
import irobots.comm.Robot;
import irobots.vision.ColorSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior causes the robot not to cross the line which marks
 * the field boundaries if it is detected. When the line is detected
 * the robot will do a 180° turn and the pass control to subsequent
 * behaviors.
 * 
 * @author Martin Feiler
 *
 */
public class LineBehaviour implements Behavior {
	
	public static final float FIELD_WIDTH = 75;
	public static final float FIELD_HEIGHT = 75;
	
	private Navigator nav;
	private ColorSensor color;
	
	public LineBehaviour() {
		this.nav = Global.navigator;
		this.color = Global.color;
	}

	@Override
	public boolean takeControl() {
		return color.lineDetected();
	}

	/**
	 * Turns the robot 180° and then passes control to other behaviours
	 */
	@Override
	public void action() {
		new Graphics().fillRect(0, 0, 100, 63);
		Robot.me =  Robot.meFromPose(nav.getPoseProvider().getPose());
		
		float deg = Global.compass.getDegrees();
		
		if(315 < deg || deg < 45) {
			Robot.me.setFixedY(FIELD_HEIGHT);
		}
		else if(225 < deg) {
			Robot.me.setFixedX(FIELD_WIDTH);
		}
		else if(135 < deg) {
			Robot.me.setFixedY(0);
		}
		else {
			Robot.me.setFixedX(0);
		}
		
		nav.getPoseProvider().setPose(Robot.me);
		
		float heading = deg+180;
		if(heading > 360) heading -= 360;
		
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		p.stop();
		p.travel(-5);
		p.rotate(180);
		
		if(Global.color.lineDetected()) {
			float diff = Global.compass.getDegrees()-heading;
			p.rotate(diff);
		}
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
