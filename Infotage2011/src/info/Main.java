package info;

import info.Social.Action;
import info.Social.State;

import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class Main 
{
	private UltrasonicSensors us;
	private LightSensors light;
	private MotorController motors;
	private Social social;
	
	public static boolean run;
	public static boolean pause;
	
	public void init()
	{
		light = new LightSensors();
		us = new UltrasonicSensors();
		motors = new MotorController();
		social = new Social();
	}
	
	public void run()
	{
		init();
		
		while(!run)
		{
			if(Button.ENTER.isPressed())
			{
				run = true;
			}
			else if(Button.ESCAPE.isPressed())
			{
				System.exit(0);
			}
			else
			{
				Thread.yield();
			}
		}
		
		light.calibrate();
		
		motors.forward();
		
		while(run)
		{
			if(Button.ESCAPE.isPressed())
			{
				return;
			}
			
			if(pause)
			{
				motors.stop();
				Thread.yield();
				continue;
			}
			
			if(light.getLeft() || light.getRight())
			{
				if(motors.goesBackward())
				{
					motors.rotate(10);
					continue;
				}
				if(light.getLeft() && light.getRight())
				{
					if(Math.random() < 0.5)
					{
						turnRandomRange(115, 180);
					}
					else
					{
						turnRandomRange(-115, -180);
					}
				}
				else if(light.getLeft())
				{
					turnRandomRange(80, 110);
					//motors.turn(180);
				}
				else if(light.getRight())
				{
					turnRandomRange(-80, -110);
					//motors.turn(-180);
				}
			}
			else if(us.getLeftDistance() < 50 || us.getRightDistance() < 50)
			{
				Action a = social.meetRoboter(Math.min(us.getLeftDistance(), us.getRightDistance()));
				
				switch(a)
				{
					case evade:
						evade();
						break;
					case stop:
						motors.stop();
						break;
					case follow:
						follow();
						break;
					case chase:
						chase();
						break;
				}
			}
			else
			{
				motors.forward();
			}
			
			try {
				for(int i = 0; i < 10; i++)
				{
					Thread.sleep(1);
					if(light.getLeft() || light.getRight())
						break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void turn(int degrees)
	{		
		motors.stop();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(social.getState() == State.aggressive)
		{
			motors.rotate(-10);
		}
		else
		{
			motors.rotate(-5);
		}
		
		motors.turn(degrees);
		
		motors.forward();
	}
	
	public void turnRandomRange(int from, int to)
	{
		Random r = new Random();
		turn(r.nextInt(to-from)+from);
	}
	
	public void evade()
	{
		int left = us.getLeftDistance(); 
		int right = us.getRightDistance();
		
		if(left < 50 && right < 50)
		{
			if(left < 10)
			{
				motors.setSpeed(-1, -2.5f);
			}
			else if(right < 10)
			{
				motors.setSpeed(-2.5f, -1);
			}
			else if(left < 25)
			{
				motors.setSpeed(1, 0);
			}
			else
			{
				motors.setSpeed(0, 1);
			}
		}
		else if(left < 50)
		{
			if(left > 30)
			{
				motors.setSpeed(1, 0);
			}
			else if(left > 10)
			{
				motors.setSpeed(1, -1);
			}
			else
			{
				motors.setSpeed(0, -2);
			}
		}
		else if(right < 50)
		{
			if(right > 30)
			{
				motors.setSpeed(0, 1);
			}
			else if(right > 10)
			{
				motors.setSpeed(-1, 1);
			}
			else
			{
				motors.setSpeed(-2, 0);
			}
		}
	}
	
	public void follow()
	{
		int left = us.getLeftDistance(); 
		int right = us.getRightDistance();
		
		if(left < 50 && right < 50)
		{
			motors.setSpeed(1, 1);
		}
		else if(left < 50)
		{
			motors.setSpeed(0, 1);
		}
		else if(right < 50)
		{
			motors.setSpeed(1, 0);
		}
	}
	
	public void chase()
	{
		int left = us.getLeftDistance(); 
		int right = us.getRightDistance();
		
		if(left < 50 && right < 50)
		{
			motors.setSpeed(2, 2);
		}
		else if(left < 50)
		{
			motors.setSpeed(2, 0);
		}
		else if(right < 50)
		{
			motors.setSpeed(0, 2);
		}
	}
	
	public void onObjectDetected()
	{
		int left = us.getLeftDistance(); 
		int right = us.getRightDistance();
		
		if(left < 50 && right < 50)
		{
			if(left < 10)
			{
				motors.setSpeed(0, -1);
			}
			else if(right < 10)
			{
				motors.setSpeed(-1, 0);
			}
			else if(left < 25 && right < 25)
			{
				motors.setSpeed(0, 0);
			}
			else if(left < 25)
			{
				motors.setSpeed(0, 1);
			}
			else
			{
				motors.setSpeed(1, 0);
			}
		}
		else if(left < 50)
		{
			if(left > 30)
			{
				motors.setSpeed(0, 1);
			}
			else if(left > 10)
			{
				motors.setSpeed(1, -1);
			}
			else
			{
				motors.setSpeed(0, -1);
			}
		}
		else if(right < 50)
		{
			if(right > 30)
			{
				motors.setSpeed(1, 0);
			}
			else if(right > 10)
			{
				motors.setSpeed(-1, 1);
			}
			else
			{
				motors.setSpeed(-1, 0);
			}
		}
	}
	
	public static void main(String[] args) {
		new Main().run();
		System.exit(0);
	}
}
