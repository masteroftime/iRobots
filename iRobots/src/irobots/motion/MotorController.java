
package irobots.motion;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class MotorController 
{
	public final static int SPEED = 500;
	public final static float DEG_PER_CM = 20;
	public final static float DEG_PER_DEG = 4;
	
	private NXTRegulatedMotor left;
	private NXTRegulatedMotor right;
	
	private int direction;
	
	public MotorController()
	{
		left = Motor.B;
		right = Motor.A;
	}
	
	public void backward()
	{
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		
		direction = -1;
		
		left.backward();
		right.backward();
	}
	
	public void forward()
	{
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		
		direction = 1;
		
		left.forward();
		right.forward();
	}
	
	public void stop()
	{
		left.stop(true);
		right.stop();
		
		direction = 0;
	}
	
	public void rotate(int length) //    [lenght] = cm
	{
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		
		if(length > 0)
			direction = 1;
		else if(length < 0)
			direction = -1;
		else 
			direction = 0;
		
		left.rotate((int)(length*DEG_PER_CM), true);
		right.rotate((int)(length*DEG_PER_CM), false);
	}
	
	public void turn(int degrees)
	{
		left.setSpeed(SPEED);
		right.setSpeed(SPEED);
		
		direction = 0;
		
		left.rotate((int)(degrees*DEG_PER_DEG), true);
		right.rotate((int)(-degrees*DEG_PER_DEG), false);
	}
	
	public void setSpeed(float left, float right)
	{
		if(left == 0)
		{
			this.left.flt(true);
		}
		else if(left < 0)
		{
			this.left.setSpeed(-left*SPEED);
			this.left.backward();
		}
		else
		{
			this.left.setSpeed(left*SPEED);
			this.left.forward();
		}
		
		if(right == 0)
		{
			this.right.flt(true);
		}
		else if(right < 0)
		{
			this.right.setSpeed(-right*SPEED);
			this.right.backward();
		}
		else
		{
			this.right.setSpeed(right*SPEED);
			this.right.forward();
		}
		
		if(left + right > 0)
			direction = 1;
		else if(left + right < 0)
			direction = -1;
		else
			direction = 0;
	}
	
	public boolean goesBackward()
	{
		return direction < 0;
	}
}
