import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Window.Type;
import java.util.ArrayList;
import java.awt.geom.*;

import javax.swing.JPanel;

/**
 * This class is responsible for drawing the field and positions
 * of the robots.
 */
public class Field extends JPanel
{
	private ArrayList<Message> msg;
	private final int rsize; //field size in robot steps
	private int rotation;

	public Field()
	{
		initComponents();
		msg = new ArrayList<>();
		rsize = 90;
	}
	
	/**
	 * Adds an update message to the end of the received message
	 * list and updates the drawing of the robot positions accordingly.
	 * @param m The message to process
	 */
	public void addMessage(Message m)
	{
		msg.add(m);
		updated();
	}
	
	private void initComponents()
	{
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public void paintComponent( Graphics gf ) 
	{
        super.paintComponent(gf);
        Graphics2D g = (Graphics2D)gf;
        
        int field_size = (Math.min(getHeight(), getWidth())*2)/3;
        g.translate((getWidth()-field_size)/2, (getHeight()-field_size)/2);
        g.rotate(Math.toRadians(rotation), field_size/2, field_size/2);
        
        g.drawRect(0, 0, field_size, field_size);
        
        if(msg.size()> 0) 
        {
        	Polygon p0 = new Polygon();
        	Polygon p1 = new Polygon();
        	Polygon p2 = new Polygon();
        	double a0 = Double.NaN;
        	double a1 = Double.NaN;
        	double a2 = Double.NaN;
        	for(Message ms : msg)
        	{
        		
        		if(ms.getType().equals("pos")) {
    				switch(ms.getId()) {
    				case "0" : p0.addPoint((ms.getX()*field_size)/rsize, ((rsize-ms.getY())*field_size)/rsize);
    				a0 = ms.getAngle();
    				break;
    				case "1" : p1.addPoint((ms.getX()*field_size)/rsize, ((rsize-ms.getY())*field_size)/rsize);
    				a1 = ms.getAngle();
    				break;
    				case "2" : p2.addPoint((ms.getX()*field_size)/rsize, ((rsize-ms.getY())*field_size)/rsize);
    				a2 = ms.getAngle();
    				break;
    				default : System.err.println("Unknown ID");
        			}
        		}
        	}

            g.setColor(Color.RED);
        	g.drawPolyline(p0.xpoints, p0.ypoints, p0.npoints);
        	drawRobot(a0, p0, g);
            g.setColor(Color.BLUE);
        	g.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);
        	drawRobot(a1, p1, g);
            g.setColor(Color.GREEN);
        	g.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
        	removeMsg();
        	drawRobot(a2, p2, g);
        }
	}
	
	/**
	 * Utility method to draw a triangle for the robot at the end
	 * of the robot path.
	 * @param angle The heading of the robot
	 * @param p The path traveled by the robot
	 * @param g Graphics to draw to
	 */
	private void drawRobot(double angle, Polygon p, Graphics2D g) {
		if(angle != Double.NaN && p.npoints > 0) {
			int x = p.xpoints[p.npoints-1];
			int y = p.ypoints[p.npoints-1];
			
			g.translate(x, y);
			g.rotate(Math.toRadians(angle));
			
			g.fillPolygon(new int[] {0, -8, 8}, new int[] {-8, 8, 8}, 3);
			
			g.rotate(-Math.toRadians(angle));
			g.translate(-x, -y);
		}
	}
	
	/**
	 * Removes the messages at the end of the list so in order
	 * to limit the length of the displayed path.
	 */
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
	
	/**
	 * This method should be called every time a new message is
	 * inserted into the message list. If the received message
	 * contains a position update the robot display is updated
	 * accordingly.
	 */
	public void updated()
	{
		Message m  = msg.get(msg.size()-1); //get me the last element
		if(m.getType().equals("pos"))
		{
			invalidate();
			repaint();
		}
	}
	
	/**
	 * Rotates the whole field around the given angle.
	 * @param rotation The angle to rotate in degrees
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
		this.invalidate();
		this.repaint();
	}
	
	public Point screenToRobot(int x, int y) {
		int field_size = (Math.min(getHeight(), getWidth())*2)/3;
		return new Point((x*rsize)/field_size, (-(y*rsize)/field_size+rsize));
	}
}
