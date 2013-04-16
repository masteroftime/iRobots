import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Displays a compass which is used to display how the field
 * is rotated.
 */
public class Compass extends JComponent {
	
	private int rotation;
	private Image img;
	
	/**
	 * Creates a new compass component.
	 */
	public Compass() {
		try {
			this.img = ImageIO.read(new File("compass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(200,200);
	}

	/**
	 * Changes the displayed rotation value and updates
	 * the display.
	 * @param rotation The angle to display in degrees. 
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
		this.invalidate();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics gf) {
		super.paint(gf);
		Graphics2D g = (Graphics2D)gf;
		g.rotate(Math.toRadians(rotation), 100, 100);
		
		g.drawImage(img, 0, 0, 200, 200, null);
		
		g.rotate(-Math.toRadians(rotation), 100, 100);
		
		drawChar('N', -rotation+90, g);
		drawChar('E', -rotation, g);
		drawChar('S', -rotation+270, g);
		drawChar('W', -rotation+180, g);
	}
	
	private void drawChar(char c, int angle, Graphics2D g) {
		g.setFont(g.getFont().deriveFont(20.0f));
		
		int x = 100 + (int)(Math.cos(Math.toRadians(angle))*80);
		int y = 100 - (int)(Math.sin(Math.toRadians(angle))*80);
		
		x -= g.getFontMetrics().charWidth(c)/2;
		y += g.getFontMetrics().getHeight()/2-4;
		
		g.drawString(""+c, x, y);
	}
}
