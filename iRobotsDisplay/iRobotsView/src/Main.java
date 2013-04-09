import java.io.IOException;
import java.util.Timer;

import javax.swing.UnsupportedLookAndFeelException;


public class Main 
{

	public static void main(String[] args) throws IOException 
	{
		
		//new USBReceive(new Display().getField()).receive();
		
		Field f = new Display().getField();
		
		f.addMessage(new Message("0", "pos", 2, 2, 0));
		f.addMessage(new Message("0", "pos", 35, 35, 0));
		f.addMessage(new Message("0", "pos", 73, 2, 0));
		f.addMessage(new Message("0", "pos", 40, 35, 0));
		f.addMessage(new Message("0", "pos", 73, 73, 0));
		f.addMessage(new Message("0", "pos", 40, 40, 0));
		f.addMessage(new Message("0", "pos", 2, 73, 0));
		f.addMessage(new Message("0", "pos", 35, 40, 135));
		
		f.addMessage(new Message("1", "pos", 15, 30, 0));
		f.addMessage(new Message("1", "pos", 30, 40, 0));
		f.addMessage(new Message("1", "pos", 20, 50, 315));
	}

}
