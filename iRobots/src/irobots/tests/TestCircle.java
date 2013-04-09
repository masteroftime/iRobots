package irobots.tests;

import irobots.Global;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

public class TestCircle 
{
	private static DifferentialPilot p;
	
	public static void main(String[] args) {
		Global.initGlobals();
		Global.navigator = new Navigator(new DifferentialPilot(4.3, 11.0, Motor.B, Motor.C));
		
		p = (DifferentialPilot) Global.navigator.getMoveController();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Button.ESCAPE.isDown())
					Thread.yield();
				System.exit(0);
			}
		}).start();
		
		p.setTravelSpeed(15);
		
		try {
			while(true) {
				rotate(180);
				Thread.sleep(500);
				p.travel(15);
				Thread.sleep(3000);
				p.travel(-15);
				Thread.sleep(500);
				rotate(135);
				Thread.sleep(1000);
				p.travel(30);
				Thread.sleep(500);
				arc(30, 360);
				arc(30, 360);
				arc(30, 360);
				arc(30, 90);
				
				p.forward();
				while(!Global.color.lineDetected());
				p.travel(-5);
				p.rotate(-90);
				
				p.forward();
				while(!Global.color.lineDetected());
				p.travel(-5);
				rotate(-135);
			}
		}
		catch (InterruptedException e) {
			
		}
	}
	
	public static void arc(int rad, int deg) {
		float heading = Global.compass.getDegrees()+deg;
		if(heading >= 360) heading -= 360;
		if(heading < 0) heading += 360;
		
		p.arcForward(rad);
		
		while(Math.abs(Global.compass.getDegrees()-heading) < 5);
		
		while(Math.abs(Global.compass.getDegrees()-heading) > 3);
		p.stop();
	}
	
	public static void rotate(int deg) {
		float heading = Global.compass.getDegrees()-deg;
		if(heading >= 360) heading -= 360;
		if(heading < 0) heading += 360;
		
		p.setRotateSpeed(90);
		
		if(deg < 0) {
			p.rotateRight();
		} else {
			p.rotateLeft();
		}
		
		while(Math.abs(Global.compass.getDegrees()-heading) < 5);
		
		while(Math.abs(Global.compass.getDegrees()-heading) > 3);
		p.stop();
		
		//float s = (float) p.getTravelSpeed();
		//p.setTravelSpeed(s/2);
		
		/*if(deg < 0) {
			p.rotateRight();
		} else {
			p.rotateLeft();
		}
		
		while(Math.abs(Global.compass.getDegrees()-heading) < 5);
		
		while(Math.abs(Global.compass.getDegrees()-heading) > 3);
		p.stop();*/
		
		/*p.rotate(deg);
		
		while (Math.abs(Global.compass.getDegrees()-heading) > 3) {
			p.rotate(Global.compass.getDegrees()-heading);
		}*/
		
		//p.setTravelSpeed(s);
	}
}
