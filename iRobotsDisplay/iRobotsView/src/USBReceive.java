import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class USBReceive 
{	
	private NXTConnector conn;
	private DataInputStream inDat;
	private DataOutputStream outDat;
	private Field field;
	private int count;
	
	public USBReceive(Field field)
	{
		conn = new NXTConnector();
		this.field = field;
		count = 0;
	}
	
	public void receive() throws IOException
	{	String s = "";
		boolean first = true;
		while(true)
		{
			if(first == true)
			{
				if(conn.connectTo(("usb://")))
				{
					first = false;
					inDat = new DataInputStream(conn.getInputStream());    
					outDat = new DataOutputStream(conn.getOutputStream());
				}
			}
			else
			{
				s = inDat.readUTF();
				String[] split = s.split(":");
				String type = "";
				String id = "";
				String[] split1 = split[1].split(";");
				int x = 0;
				int y = 0;
				double angle = 0;
				
				switch(split[0])
				{
					case "hello": type = "hello"; id = split1[0];
						break;
					case "bye": type = "bye"; id = split1[0];
						break;
					case "pos": type = "pos"; id = split1[0];
						String[] split2 = split1[1].split("/");
						x = Integer.parseInt(split2[0]);
						y = Integer.parseInt(split2[1]);
						angle = Double.parseDouble(split2[2]);
						break;
					default: type = "undef"; id = split1[0];
				}
				field.addMessage(new Message(id, type, x, y, angle));
				count++;
		        System.out.println(" Received " + s + " Count: " + count );
			}
		}
	}
}