import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Window.Type;
import java.util.ArrayList;
import java.awt.geom.*;

import javax.swing.JPanel;


public class Field extends JPanel
{
	private ArrayList<Message> msg;
	private final int rsize; //field size in robot steps
	
	public Field()
	{
		initComponents();
		msg = new ArrayList<>();
		rsize = 75;
	}
	
	public void addMessage(Message m)
	{
		msg.add(m);
		updated();
	}
	
	public void initComponents()
	{
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public void paintComponent( Graphics g ) 
	{
        super.paintComponent(g);
        if(msg.size()> 0) 
        {
        	Polygon p0 = new Polygon();
        	Path2D.Double d0 = new Path2D.Double();
        	Polygon p1 = new Polygon();
        	Polygon p2 = new Polygon();
        	for(Message ms : msg)
        	{
        		if(ms.getType().equals("pos")) {
    				switch(ms.getId()) {
    				case "0" : p0.addPoint(10+(ms.getX()*this.getHeight()/rsize), this.getHeight()-(ms.getY()*this.getHeight()/rsize)+10);
    				
    				break;
    				case "1" : p1.addPoint(10+(ms.getX() *this.getHeight()/rsize), this.getHeight()-(ms.getY()*this.getHeight()/rsize)+10);
    				break;
    				case "2" : p2.addPoint(10+(ms.getX() *this.getHeight()/rsize), this.getHeight()-(ms.getY()*this.getHeight()/rsize)+10);
    				break;
    				default : System.err.println("Unknown ID");
        			}
        		}
        	}

            g.setColor(Color.RED);
        	g.drawPolyline(p0.xpoints, p0.ypoints, p0.npoints);
            g.setColor(Color.BLUE);
        	g.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);
            g.setColor(Color.GREEN);
        	g.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
        	removeMsg();
        }
	}
	
	public void removeMsg()
	{
		ArrayList<Message> r0 = new ArrayList<>();
		ArrayList<Message> r1 = new ArrayList<>();
		ArrayList<Message> r2 = new ArrayList<>();
		for(Message m : msg)
		{
			switch (m.getId())
			{
				case "0": r0.add(m);
					break;
				case "1": r1.add(m);
					break;
				case "2": r2.add(m);
					break;
				default:;
			}
		}
		if(r0.size() > 20)
		{
			for(Message m : r0)
			{
				msg.remove(m);
				return;
			}
		}
		if(r1.size() > 20)
		{
			for(Message m : r1)
			{
				msg.remove(m);
				return;
			}
		}
		if(r1.size() > 20)
		{
			for(Message m : r1)
			{
				msg.remove(m);
				return;
			}
		}
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
