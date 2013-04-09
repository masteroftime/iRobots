package irobots.comm;

import irobots.Global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.addon.NXTBee;

public class CommunicationManager extends Thread
{
	private NXTBee bee;
	private DataOutputStream out;
	private DataInputStream in;

	ArrayList<Integer> connected;
	ArrayList<Integer> ack;

	public CommunicationManager() {
		bee = new NXTBee();
		Thread t = new Thread(bee);
		t.setDaemon(true);
		t.start();

		out = new DataOutputStream(bee.getOutputStream());
		in = new DataInputStream(bee.getInputStream());
		connected = new ArrayList<Integer>(4);
		
		this.setDaemon(true);
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			receiveMessage();
		}
	}

	public synchronized void sendMessage(String message) {
		try {
			byte[] msg = (message+"@"+message.hashCode() + "$").getBytes();
			out.write(msg);
			out.flush();

			//System.out.println(message);

			/*if(ack) {
				waitForAck();
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage() {
		try {
			String msg = "";
    		int b;
    		//System.out.println("Was here!");
    		//String msg = in.readUTF();
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

	/*public void waitForAck() {
		int count = connected.size();
		ack = new ArrayList<Integer>(count);

		while(ack.size() < count) {
			Thread.yield();
		}
	}*/

	/*public void sendHello() {
		sendMessage("hello:"+Global.id);
	}
	
	public void sendBye() {
		sendMessage("bye:"+Global.id);
	}*/
	
	/*public void sendAck(int remote) {
		sendMessage("ack:"+remote+";"+Global.id, false);
	}*/
	/*public void sendNotAck(int remote) {
		sendMessage("nack:"+remote+";"+Global.id);
	}*/
	
	public void sendPos() {
		String s = "pos:"+Global.id+";"+(int)Robot.me.getX()+"/"+(int)Robot.me.getY()+"/"+(int)Robot.me.getHeading()+"/";
		if(Robot.me.isPositionAbsolute()){
			s+= "1";
		} else {
			s+="0";
		}
		sendMessage(s);
	}
	
	public void sendSee(int remote,int dx,int dy,int dangle) {
		String s = "see:"+remote+";"+Global.id+";"+dx+"/"+dy+"/"+dangle;
		if(Robot.me.isPositionAbsolute()){
			s+= "1";
		}else{
			s+="0";
		}
		sendMessage(s);
	}
	
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