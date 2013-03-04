package irobots.behaviour;

import irobots.Global;
import irobots.comm.Robot;
import irobots.vision.ColorSensor;
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
	
	public static final float FIELD_WIDTH = 0;
	public static final float FIELD_HEIGHT = 0;
	
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
		System.out.println("At the line");
		Robot.me =  Robot.meFromPose(nav.getPoseProvider().getPose());
		
		float deg = Global.compass.getDegrees();
		
		if(315 < deg || deg < 45) {
			Robot.me.setFixedY(FIELD_HEIGHT);
		}
		else if(225 < deg) {
			Robot.me.setFixedX(0);
		}
		else if(135 < deg) {
			Robot.me.setFixedY(0);
		}
		else {
			Robot.me.setFixedX(FIELD_WIDTH);
		}
		
		nav.getPoseProvider().setPose(Robot.me);
		
		Pose p = nav.getPoseProvider().getPose();
		p.rotateUpdate(180);
		nav.clearPath();
		nav.goTo(p.getX(), p.getY(), p.getHeading());
		nav.waitForStop();
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
