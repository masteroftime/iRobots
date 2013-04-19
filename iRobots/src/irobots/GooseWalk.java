package irobots;

import irobots.behaviour.ExitProgramBehavior;
import irobots.behaviour.FindAndEvadeBehaviour;
import irobots.behaviour.FollowBehaviour;
import irobots.behaviour.JustDriveBehaviour;
import irobots.behaviour.LineBehaviour;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * This Program makes the robots follow each other
 * within a field, marked by a black line on the ground.
 * 
 * <p>First priority of the robot is to stay within
 * the marked field. Within this field the robot
 * then tries to follow other robots it detects
 * by maintaining a certain distance.</p>
 * 
 * <p>Because every robot tries to follow another
 * robot at a certain distance, when many robots
 * run this program it should result in the robots
 * lining up into a single file.</p>
 * 
 */
public class GooseWalk implements Runnable {

	public static void main(String[] args) {
		Global.initGlobals();
		
		Global.navigator.getMoveController().setTravelSpeed(Global.navigator.getMoveController().getTravelSpeed()/2);
		
		new GooseWalk().run();
	}
	
	public GooseWalk() {
		

	}

	@Override
	public void run() {
		Arbitrator a = new Arbitrator(new Behavior[] {new JustDriveBehaviour(), new FindAndEvadeBehaviour(), new FollowBehaviour(), new LineBehaviour(), new ExitProgramBehavior()});
		//Arbitrator a = new Arbitrator(new Behavior[] {new FollowBehaviour(), new ExitProgramBehavior()});
		a.start();
	}

}
