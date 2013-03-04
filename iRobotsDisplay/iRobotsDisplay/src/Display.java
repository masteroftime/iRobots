import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Display extends JFrame
{
	private Field field;

	public Display()
	{
		super("iRobots - Display");
		field = new Field();
		this.add(field);
		this.setExtendedState(this.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		this.setVisible(true);
	}

}
