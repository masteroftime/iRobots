package info;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class UltrasonicSensors 
{ 
	private UltrasonicSensor leftSensor;
	private UltrasonicSensor rightSensor;
	
	public UltrasonicSensors()
	{
		leftSensor = new UltrasonicSensor(SensorPort.S4);
		rightSensor = new UltrasonicSensor(SensorPort.S1);
		
		leftSensor.continuous();
		rightSensor.continuous();
	}
	
	public int getLeftDistance()
	{
		return leftSensor.getDistance();
	}
	
	public int getRightDistance()
	{
		return rightSensor.getDistance();
	}
}
