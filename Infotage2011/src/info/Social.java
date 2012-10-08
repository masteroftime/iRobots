package info;

import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;

public class Social 
{
	private State state;
	private BluetoothConnection bluetooth;
	
	private int counter;
	private int prevDistance;
	private boolean changeState;
	
	public Social()
	{
		bluetooth = new BluetoothConnection(this);
		double r = Math.random();
		changeState = true;
		
		if(r <= 0.3)
		{
			setState(State.shy);
		}
		else if(r <= 0.7)
		{
			setState(State.curious);
		}
		else
		{
			setState(State.aggressive);
		}
	}
	
	public State getState()
	{
		return state;
	}
	
	public void setState(State state)
	{
		if(!changeState) {
			return;
		}
		
		this.state = state;
		
		try {
			bluetooth.sendStatusUpdate(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(state.toString());
	}
	
	public void alwaysSetState(State state)
	{
		this.state = state;
		
		try {
			bluetooth.sendStatusUpdate(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(state.toString());
	}
	
	public void setChangeState(boolean change)
	{
		changeState = change;
	}
	
	public Action meetRoboter(int distance)
	{
		switch(state)
		{
		case shy:
		{
			if(prevDistance > 50 && ++counter >= 10) //konstanten einfügen
			{
				setState(State.curious);
			}
			prevDistance = distance;
			return Action.evade;
		}
		case curious:
		{
			if(prevDistance > 50)
				counter = 0;
			
			int delta = prevDistance - distance;
			prevDistance = distance;
			
			if(distance < 10)
			{
				setState(State.shy);
				return Action.evade;
			}
			if(delta <= 0 && ++counter >= 35)
			{
				setState(State.aggressive);
				counter = 0;
			}
			
			if(distance < 20)
			{
				return Action.evade;
			}
			if(distance < 30)
			{
				return Action.stop;
			}
			return Action.follow;
		}
		case aggressive:
		{
			prevDistance = distance;
			if(distance < 15)
			{
				if (Math.random() > 0.6)
				{
					setState(State.shy);
				}
				counter = 0;
				return Action.evade;				
			}
			if(distance < 25)
			{
				if(counter < 200)
				{
					counter++;
					return Action.stop;
				}
				else
				{
					return Action.evade;
				}
			}
			else
			{
				counter = 0;
				return Action.chase;
			}
			
		}
		}
		prevDistance = distance;
		return Action.evade;
	}
	
	public enum State {
		shy,
		curious,
		aggressive
	}
	
	public enum Action {
		evade,
		stop,
		follow,
		chase
	}
}
