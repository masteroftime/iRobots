package irobots;

import irobots.behaviour.ExitProgramBehavior;
import irobots.behaviour.FollowBehaviour;
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
		Global.initGlobals();
		new GooseWalk().run();
	}
	
	public GooseWalk() {
		

	}

	@Override
	public void run() {
		//Arbitrator a = new Arbitrator(new Behavior[] {new JustDriveBehaviour(), new FollowBehaviour(), new LineBehaviour(), new ExitProgramBehavior()});
		Arbitrator a = new Arbitrator(new Behavior[] {new FollowBehaviour(), new ExitProgramBehavior()});
		a.start();
	}

}
