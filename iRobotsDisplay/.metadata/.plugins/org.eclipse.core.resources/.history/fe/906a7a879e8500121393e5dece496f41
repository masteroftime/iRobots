import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BluetoothConn 
{
	private BTConnection con;
	private DataOutputStream out;
	
	private volatile boolean connected;
	
	public BluetoothConn()
	{
		

	}
	
	public boolean connect()
	{
		con = Bluetooth.waitForConnection();
		if(con != null)
		{
			connected = true;
			System.out.println("Connected");
			return true;
		}
		return false;
	}
	
	public void sendData(String data) throws IOException
	{
		if(!connected)
		{
			System.out.println("Not connected");
				return;
		}
		out = con.openDataOutputStream();
		out.writeUTF(data);
		out.flush();
	}
	
	public void closeConnection() throws IOException
	{
		out.close();
	}
}
