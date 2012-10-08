package info;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LightSensors 
{
	private LightSensor leftSensor;
	private LightSensor rightSensor;
	
	private int blackValueLeft;
	private int blackValueRight;
	
	public LightSensors()
	{
		leftSensor = new LightSensor(SensorPort.S3);
		rightSensor = new LightSensor(SensorPort.S2);
		
		leftSensor.setFloodlight(true);
		rightSensor.setFloodlight(true);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		blackValueLeft = leftSensor.readValue();
		blackValueRight = rightSensor.readValue();
	}
	
	public void calibrate()
	{
		blackValueLeft = leftSensor.readValue();
		blackValueRight = rightSensor.readValue();
	}
	
	/**
	 * true = is on the line
	 */
	public boolean getLeft()
	{
		return leftSensor.readValue() < blackValueLeft -10;
	}
	
	public boolean getRight()
	{
		return rightSensor.readValue() < blackValueRight -10;
	}
}
