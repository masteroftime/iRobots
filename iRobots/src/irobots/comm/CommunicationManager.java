package irobots.comm;

import irobots.Global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.addon.NXTBee;

/**
 * The CommunicationManager provides functions which can be used
 * to send messages to other robots. It also runs a background
 * thread that receives messages from the other robots and
 * processes the received messages.
 */
public class CommunicationManager extends Thread
{
	private NXTBee bee;
	private DataOutputStream out;
	private DataInputStream in;

	/**
	 * Creates a new CommunicationManager instance and starts
	 * the message receiving thread.
	 */
	public CommunicationManager() {
		bee = new NXTBee();
		Thread t = new Thread(bee);
		t.setDaemon(true);
		t.start();

		out = new DataOutputStream(bee.getOutputStream());
		in = new DataInputStream(bee.getInputStream());
		
		this.setDaemon(true);
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			receiveMessage();
		}
	}

	/**
	 * Sends a message.
	 * @param message The message to send without checksum.
	 */
	public synchronized void sendMessage(String message) {
		try {
			byte[] msg = (message+"@"+message.hashCode() + "$").getBytes();
			out.write(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a message from another robot and processes the
	 * received message. This method blocks until a message is
	 * received. This method is used by the message receiving
	 * thread to receive and process incoming messages and
	 * should not be called directly.
	 */
	public void receiveMessage() {
		try {
			String msg = "";
    		int b;
    		
    		while((b = in.read()) != -1)
    		{
    			msg += (char)b;
    			if(msg.charAt(msg.length()-1) == '$') break;
    		}
			
			if(!checkChecksum(msg)) {
				while(in.available() > 0) {
					in.read();
				}
				return;
			}
			
			String[] sp = split(msg, ':', 2);
			
			if(sp == null)
				return;
			
			switch (sp[0]) {
				case "pos": receivePos(sp[1]);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the checksum of the given message is valid.
	 * @param data The message to check.  
	 */
    public boolean checkChecksum(String data)
    {
    	if(data != null)
    	{
	    	int m = data.indexOf("@");
	    	if(m != -1)
	    	{
		        String checkSum = data.substring(m+1,data.length()-1);
		        int checkBody = data.substring(0,m).hashCode();
		        String checkString = ""+checkBody;
		        if(checkSum.equals(checkString))
		        {  	
		        	return true;
		        } 
	    	}
    	}
    	return false;
    }
	
    /**
     * Processes a pos message.
     */
	public void receivePos(String p) {
		String[] s = split(p, ';', 2);
		
		if(s == null)
			return;
		
		String[] pos = split(s[1], '/', 4);
		
		if(pos == null)
			return;
		
		try {
			Robot r = new Robot(Integer.decode(pos[0]), Integer.decode(pos[1]), Integer.decode(pos[2]));
			r.setPositionAbsolute(pos[3].equals("1"));
			int id = Integer.decode(s[0]);
			r.setId(id);
			
			Global.robots[id] = r;
		} catch (NumberFormatException e) {
			return;
		}
	}
	
	/**
	 * Sends the current position of the robot.
	 */
	public void sendPos() {
		String s = "pos:"+Global.id+";"+(int)Robot.me.getX()+"/"+(int)Robot.me.getY()+"/"+(int)Robot.me.getHeading()+"/";
		if(Robot.me.isPositionAbsolute()){
			s+= "1";
		} else {
			s+="0";
		}
		sendMessage(s);
	}
	
	/**
	 * Sends a see message.
	 * @param remote The remot robots id
	 * @param rx The detected x-coordinate of the remote robot
	 * @param ry The detected y-cooddinate of the remote robot
	 */
	public void sendSee(int remote,int rx,int ry) {
		String s = "see:"+remote+";"+Global.id+";"+rx+"/"+ry+"/";
		if(Robot.me.isPositionAbsolute()){
			s+= "1";
		}else{
			s+="0";
		}
		sendMessage(s);
	}
	
	/**
	 * Splits a string at a given char.
	 * @param s The string to split
	 * @param c The char at which should be split.
	 * @param count The number of parts after the split.
	 * @return A String array with the length of count. If the
	 * split char does not occur often enough the remaining Strings
	 * in the array equal to null. 
	 */
	public static String[] split(String s, char c, int count) {
		String[] ret = new String[count];
		
		for(int i = 0; i < count-1; i++) {
			int x = s.indexOf(c);
			
			if (x == -1)
				return null;
			
			ret[i] = s.substring(0, i);
			s = s.substring(i+1);
		}
		
		ret[count-1] = s;
		
		return ret;
	}
}