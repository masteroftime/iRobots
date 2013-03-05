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
        				case "0" : p0.addPoint((ms.getX()*this.getHeight()/rsize), (ms.getY()*this.getHeight()/rsize));
        				
        				break;
        				case "1" : p1.addPoint((ms.getX() *this.getHeight()/rsize), (ms.getY()*this.getHeight()/rsize));
        				break;
        				case "2" : p2.addPoint((ms.getX() *this.getHeight()/rsize), (ms.getY()*this.getHeight()/rsize));
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
