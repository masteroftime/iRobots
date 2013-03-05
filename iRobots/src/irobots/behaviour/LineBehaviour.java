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
		Graphics g = new Graphics();
		g.fillRect(0, 0, 100, 63);
		g.setColor(1);
		Robot.me =  Robot.meFromPose(nav.getPoseProvider().getPose());
		
		float deg = Global.compass.getDegrees();
		
		if(Robot.me.getX() < 0) {
			Robot.me.setFixedX(0);
		}
		if(Robot.me.getY() < 0) {
			Robot.me.setFixedY(0);
		}
		if(Robot.me.getX() > 75) {
			Robot.me.setFixedX(75);
		}
		if(Robot.me.getY() < 75) {
			Robot.me.setFixedY(75);
		}
		
		/*if(315 < deg || deg < 45) {
			g.drawString("North", 50, 30, Graphics.HCENTER | Graphics.VCENTER);
			Robot.me.setFixedX(FIELD_HEIGHT);
		}
		else if(225 < deg) {
			g.drawString("West", 50, 30, Graphics.HCENTER | Graphics.VCENTER);
			Robot.me.setFixedY(FIELD_WIDTH);
		}
		else if(135 < deg) {
			g.drawString("South", 50, 30, Graphics.HCENTER | Graphics.VCENTER);
			Robot.me.setFixedX(0);
		}
		else {
			g.drawString("East", 50, 30, Graphics.HCENTER | Graphics.VCENTER);
			Robot.me.setFixedY(0);
		}*/
		
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
