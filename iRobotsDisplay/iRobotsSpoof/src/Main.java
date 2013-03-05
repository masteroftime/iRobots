import java.io.DataInputStream;

import java.util.Date;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;

public class Main 
{
	private USBSend usb;
    static volatile boolean stop = false;
    private DataInputStream in;
    private NXTBee xbee;
    private Thread rt;

    public Main()
    {
    	xbee = new NXTBee();
    	Thread t = new Thread(xbee);
    	t.setDaemon(true);
    	t.start();
        System.out.println("I joined XBee");
		System.out.println("Connection USB...");
		usb = new USBSend();
		System.out.println("...connected USB :) ");
		try {
			relay();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Relay error");
		}
    }
    
    public void relay() throws IOException
    {
    	in = new DataInputStream(xbee.getInputStream());
    	while(!Button.ESCAPE.isDown())
    	{
    		String msg = "";
    		int b;
    		//System.out.println("Was here!");
    		//String msg = in.readUTF();
    		while((b = in.read()) != -1)
    		{
    			msg += (char)b;
    			if(msg.charAt(msg.length()-1) == '$') break;
    		}
    		//System.out.println("got message");
			if(checkChecksum(msg) == true)
			{
				//System.out.println("sending message");
				usb.sendData(msg);
				//System.out.println("sent message");
			}
			else
			{
				System.out.println("CS Error - No Problem");
				while(in.available() > 0)
					in.read();
			}
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
    
    public static void main(String[] args) 
    {
        new Main();        
    }

}
