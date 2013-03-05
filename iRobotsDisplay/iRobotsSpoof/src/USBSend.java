import lejos.nxt.*;
import java.io.*;

import lejos.nxt.comm.*;

/**
 * Test of Java streams over USB.
 * Run the PC example, USBSend, to send data.
 * 
 * @author der Hickl
 *
 */
public class USBSend 
{
	private USBConnection conn;
	private DataOutputStream dOut;
	private DataInputStream dIn;
	
	public USBSend()
	{
		conn = null;
		conn = USB.waitForConnection();
		dOut = conn.openDataOutputStream();
		dIn = conn.openDataInputStream();
	}
	
	public void sendData(String s) 
	{
		try {
			dOut.writeUTF(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("usberror");
		}
		try {
			dOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("usberror");
		}
	}
	
	public USBConnection getState()
	{
		return this.conn;
	}
}
