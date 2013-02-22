package irobots;

import irobots.behaviour.JustDriveBehaviour;
import irobots.behaviour.LineBehaviour;
import irobots.vision.ColorSensor;
import lejos.nxt.Motor;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class GooseWalk implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GooseWalk().run();
	}
	
	private Navigator nav;
	private PoseProvider poseProvider;
	private ColorSensor color;
	
	public GooseWalk() {
		//Creates the Navigator 4.3 - Tire diameter; 10.15 - Distance between center of the tires
		nav = new Navigator(new DifferentialPilot(4.3, 10.15, Motor.B, Motor.C));
		poseProvider = nav.getPoseProvider();
		color = new ColorSensor();
	}

	@Override
	public void run() {
		Arbitrator a = new Arbitrator(new Behavior[] {new JustDriveBehaviour(nav), new LineBehaviour(nav, color)});
		a.start();
	}

}
