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
				g.drawRect(conv(o.x), conv(o.y), conv(o.width), conv(o.height));
			}
			
			g.drawString(""+colormap, 49, 63, Graphics.BOTTOM | Graphics.HCENTER);
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int conv(int x) {
		return (int) (x/144.0)*100;
	}
}
