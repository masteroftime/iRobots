package irobots.comm;

import java.util.Timer;
import java.util.TimerTask;

import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.Pose;
import irobots.Global;

public class PositionCoordinator extends Thread
{
	private PoseProvider pose;
	
	public PositionCoordinator() {
		float angle = Global.compass.getDegrees();
		pose = Global.navigator.getPoseProvider();
		
		pose.setPose(new Pose(0, 0, angle));
		Robot.me = new Robot();
		Robot.me = Robot.meFromPose(pose.getPose());
		
		this.setDaemon(true);
		this.start();
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Global.comm.sendPos();
			}
		}, 250, 250);
	}
	
	@Override
	public void run() {
		while(true) {
			float angle = Global.compass.getDegrees();
			
			Pose p = pose.getPose();
			p.setHeading(angle);
			
			//System.out.println((int)p.getX() + "/" + (int)p.getY());
			
			//Move m = Global.navigator.getMoveController().getMovement();
			
			
			
			Robot.me = Robot.meFromPose(p);
			pose.setPose(p);
			
			Thread.yield();
		}
	}
}
