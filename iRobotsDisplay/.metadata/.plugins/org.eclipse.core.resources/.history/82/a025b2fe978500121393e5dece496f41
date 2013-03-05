import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Field extends JPanel
{
	private Polygon poly;
	private ArrayList<Message> msg;
	
	public Field()
	{
		initComponents();
		msg = new ArrayList<>();
	}
	
	public void addMessage(Message m)
	{
		msg.add(m);
		updated();
	}
	
	public void initComponents()
	{
		this.setBackground(Color.WHITE);
		poly = new Polygon();
		poly.addPoint(100, 100);
		poly.addPoint(150, 150);
		poly.addPoint(50,  150);
	}
	
	@Override
	public void paintComponent( Graphics g ) 
	{
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        if(msg.size()> 0) 
        {
        	Message m  = msg.get(msg.size()-1); //get me the last element
        	int x = (m.getX() * this.getHeight())/100;
        	int y = (m.getY() * this.getHeight())/100;
        	if(m.getType().equals("pos"))
			{
				g.drawRect(x*5, y*5, 100, 100);
			}
        }
        System.out.println(this.getHeight());
	}
	
	//called after a new entry in the list
	public void updated()
	{
		Message m  = msg.get(msg.size()-1); //get me the last element
		if(m.getType().equals("pos"))
		{
			invalidate();
			repaint();
		}
	}
}
