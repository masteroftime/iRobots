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
 * the robot will do a 180° turn and the pass control to subsequent
 * behaviors.
 * 
 * @author Martin Feiler
 *
 */
public class LineBehaviour implements Behavior {
	
	public static final float FIELD_WIDTH = 25;
	public static final float FIELD_HEIGHT = 25;
	
	private Navigator nav;
	private ColorSensor color;
	
	public LineBehaviour() {
		this.nav = Global.navigator;
		this.color = Global.color;
	}

	@Override
	public boolean takeControl() {
		return color.lineDetected();// || Robot.me.getX() > 80 || Robot.me.getY() > 80 || Robot.me.getX() < -5 || Robot.me.getY() < -5; 
	}

	/**
	 * Turns the robot 180° and then passes control to other behaviours
	 */
	@Override
	public void action() {
		Graphics g = new Graphics();
		g.fillRect(0, 0, 100, 63);
		g.setColor(1);
		
		float deg = Global.compass.getDegrees();
		

		
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
		
		float heading = deg+170;
		if(heading > 360) heading -= 360;
		
		DifferentialPilot p = (DifferentialPilot)nav.getMoveController();
		p.stop();
		p.travel(-5);
		
		if(!Global.camera.robotDetected()) {
			p.rotate(170);
			
			if(Global.color.lineDetected()) {
				float diff = Global.compass.getDegrees()-heading;
				p.rotate(diff);
			}
		} else {
			p.travel(-5);
		}
		
		Robot.me =  Robot.meFromPose(nav.getPoseProvider().getPose());
		
		if(Robot.me.getX() < 5) {
			Robot.me.setFixedX(0);
		}
		if(Robot.me.getY() < 5) {
			Robot.me.setFixedY(0);
		}
		if(Robot.me.getX() > 30) {
			Robot.me.setFixedX(25);
		}
		if(Robot.me.getY() > 30) {
			Robot.me.setFixedY(25);
		}
		
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
