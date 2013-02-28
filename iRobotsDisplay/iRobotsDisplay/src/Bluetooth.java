import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.HashMap;

import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;

public class Bluetooth extends Thread
{
	private NXTInfo info;
	private NXTCommBluecove con;
	private DataInputStream input;
	private DataOutputStream output;
	private String mac;
	private String robName;
	private boolean run;
	
	public Bluetooth(String robName, String mac)
	{
		setMac(mac);
		setRobName(robName);
		this.run = true;
		start();
	}
	@Override
	public void run()
	{
		while(run)
		{
			info = new NXTInfo();
			info.deviceAddress = getMac();
			info.name = getRobName();
			con = new NXTCommBluecove();
			try 
			{
				read();
			} catch (NXTCommException e) 
			{
				//not.addMessage(info.name + " could not be found :(");
			}
			try {
				this.sleep(100);
			} catch (InterruptedException e) {
				//not.addMessage(info.name + " is not reachable");
				e.printStackTrace();
				
			}
		}
		//not.addMessage("Suchlauf beendet");
	}

	public synchronized void send(int command) 
	{
		output = new DataOutputStream(con.getOutputStream()); //initializing DataOutputStream
		try {
			output.writeInt(command);
			output.flush();
			output.close();
		} catch (Exception e) {
			//not.addMessage("Could not find "+info.name + " - Sending failed!");
			//sauron.updateIcons(info.name, 0);			
		}
	}

	public void setRun(boolean run) 
	{
		this.run = run;
	}
	
	/*public void connect() throws NXTCommException
	{
		try 
		{
			if(con.open(info))
			{
				new Notification("Successfull Connection with "+info.name+" :)");
				con.close();
			}
			else
			{
				new Notification("No Connection established :(");
			}			
		} 
		catch (NXTCommException | IOException e) 
		{
			new Notification(info.name + " is not reachable");
			e.printStackTrace();
		}
		
	}*/
	public void read() throws NXTCommException
	{
		//info.deviceAddress = macs.get(name); //To get the current MAC
		con.open(info); //establishing the connection
		//not.addMessage("Verbindung mit " + info.name + " hergestellt");
		input = new DataInputStream(con.getInputStream()); //initializing DataInputStream
		try
		{
			while(run)
			{
				int state = input.readInt();
				//sauron.updateIcons(info.name, state);
			}
		}
		catch(EOFException e)
		{
			//not.addMessage(info.name + " - Connection loss");
			//sauron.updateIcons(info.name, 0);
			//e.printStackTrace();
			//master.setStats("Slave " + names.get(info.deviceAddress) + " is disconnected", names.get(info.deviceAddress));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getRobName() {
		return robName;
	}

	public void setRobName(String robName) {
		this.robName = robName;
	}

	public boolean getRun() {
		return run;
	}
}
