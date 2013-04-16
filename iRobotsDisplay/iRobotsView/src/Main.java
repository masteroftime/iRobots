import java.io.IOException;
import java.util.Timer;

import javax.swing.UnsupportedLookAndFeelException;


public class Main 
{

	public static void main(String[] args) throws IOException 
	{
		
		new USBReceive(new Display().getField()).receive();
		
		/*Field f = new Display().getField();
		
		f.addMessage(new Message("0", "pos", 11, 20, 0));
		f.addMessage(new Message("0", "pos", 17, 14, 0));
		f.addMessage(new Message("0", "pos", 27, 10, 0));
		f.addMessage(new Message("0", "pos", 40, 9, 0));
		f.addMessage(new Message("0", "pos", 50, 9, 0));
		f.addMessage(new Message("0", "pos", 57, 10, 0));
		f.addMessage(new Message("0", "pos", 64, 10, 84));
		
		f.addMessage(new Message("1", "pos", 29, 45, 0));
		f.addMessage(new Message("1", "pos", 27, 40, 0));
		f.addMessage(new Message("1", "pos", 26, 36, 0));
		f.addMessage(new Message("1", "pos", 26, 33, 0));
		f.addMessage(new Message("1", "pos", 29, 29, 0));
		f.addMessage(new Message("1", "pos", 31, 25, 0));
		f.addMessage(new Message("1", "pos", 33, 23, 0));
		f.addMessage(new Message("1", "pos", 35, 20, 0));
		f.addMessage(new Message("1", "pos", 38, 17, 0));
		f.addMessage(new Message("1", "pos", 42, 13, 0));
		f.addMessage(new Message("1", "pos", 49, 10, 103));*/
	}

}
