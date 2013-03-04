package irobots.comm;

import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Pose;
import irobots.Global;

public class PositionKoordinator extends Thread
{
	private PoseProvider pose;
	
	public PositionKoordinator() {
		float angle = Global.compass.getDegrees();
		pose = Global.navigator.getPoseProvider();
		
		pose.setPose(new Pose(0, 0, angle));
		Robot.me = new Robot();
		Robot.me = Robot.meFromPose(pose.getPose());
		
		this.setDaemon(true);
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			float angle = Global.compass.getDegrees();
			
			Pose p = pose.getPose();
			p.setHeading(angle);
			
			Robot.me = Robot.meFromPose(p);
			
			Thread.yield();
		}
	}
}
