package irobots;

import java.awt.Rectangle;

import javax.microedition.lcdui.Graphics;

import lejos.nxt.Button;
import irobots.vision.Camera;

public class ObjectDisplay 
{
	public static void main(String[] args) {
		
		Camera cam = new Camera();
		Graphics g = new Graphics();
		int colormap = 0;
		
		while(!Button.ESCAPE.isDown()) {
			if(Button.LEFT.isDown()) {
				colormap--;
				if(colormap < 0)
					colormap = 0;
				while(Button.LEFT.isDown());
			}
			if(Button.RIGHT.isDown()) {
				colormap++;
				if(colormap > 7)
					colormap = 7;
				while(Button.RIGHT.isDown());
			}
			
			Rectangle[] objs = cam.getObjects(colormap);
			
			g.clear();
			
			for(Rectangle o : objs) {
				g.drawRect(convX(o.x), convY(o.y), convX(o.width), convY(o.height));
				//g.drawRect(o.x, o.y, o.width, o.height);
			}
			
			g.drawString(""+colormap, 49, 63, Graphics.BOTTOM | Graphics.HCENTER);
			g.drawString(objs.length + "/" + cam.getCamera().getNumberOfObjects(), 90, 63, Graphics.BOTTOM | Graphics.RIGHT);
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int convX(double x) {
		return (int) ((x/144.0)*100);
	}
	
	public static int convY(double y) {
		return (int) ((y/88.0)*63);
	}
}
