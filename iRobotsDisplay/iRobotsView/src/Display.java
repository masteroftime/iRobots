import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.GapContent;

import lejos.robotics.mapping.SliderPanel;


public class Display extends JFrame
{
	private Field field;
	private JSlider rotSlider;
	private Compass compass;
	private JPanel sidePanel;

	public Display()
	{
		super("iRobots - Display");
		
		this.setLayout(new BorderLayout());
		
		//Side Panel
		sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		sidePanel.add(new JLabel("Press H to hide"));
		sidePanel.add(Box.createVerticalStrut(25));
		sidePanel.add(new JLabel("Rotation:"));
		
		//Slider for Rotation
		rotSlider = new JSlider(0, 360, 0);
		rotSlider.setMajorTickSpacing(90);
		rotSlider.setPaintTicks(true);
		rotSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				if(Math.abs(rotSlider.getValue() - 90) < 5)
					rotSlider.setValue(90);
				if(Math.abs(rotSlider.getValue() - 180) < 5)
					rotSlider.setValue(180);
				if(Math.abs(rotSlider.getValue() - 270) < 5)
					rotSlider.setValue(270);
				
				field.setRotation(rotSlider.getValue());
				compass.setRotation(rotSlider.getValue());
			}
		});
		sidePanel.add(rotSlider);
		
		//Compass Rose
		sidePanel.add(Box.createVerticalStrut(50));
		compass = new Compass();
		sidePanel.add(compass);
		
		this.add(sidePanel, BorderLayout.EAST);
		
		//Field Panel
		field = new Field();
		this.add(field, BorderLayout.CENTER);
		
		//Hide/Show Side Panel when pressing H
		field.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('h'), "sPanel");
		field.getActionMap().put("sPanel", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sidePanel.setVisible(!sidePanel.isVisible());
			}
		});
		
		
		this.setExtendedState(this.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//UIManager.setLookAndFeel(UIManager.getLookAndFeel());
		this.setVisible(true);
	}

	public Field getField()
	{
		return this.field;
	}
}
