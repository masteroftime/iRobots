package irobots.comm;

import irobots.Global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
	}
	
	public synchronized void sendMessage(String message, boolean ack) {
		try {
			out.writeUTF(message);
			out.flush();
			
			System.out.println(message);
			
			if(ack) {
				waitForAck();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForAck() {
		int count = connected.size();
		ack = new ArrayList<Integer>(count);
		
		while(ack.size() < count) {
			Thread.yield();
		}
	}
	
	public void sendHello() {
		sendMessage("hello:"+Global.id, false);
	}
}
