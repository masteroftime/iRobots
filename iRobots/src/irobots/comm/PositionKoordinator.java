package irobots.comm;

import java.awt.Point;

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
		Robot.me.setPosition(new Point(0, 0));
		Robot.me.setHeading(angle);
	}
	
	@Override
	public void run() {
		Pose p = pose.getPose();
		
		Robot.me.setPosition(p.getLocation());
		Robot.me.setHeading(p.getHeading());
	}
}
