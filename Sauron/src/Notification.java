import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;


public class Notification extends JFrame
{
	private JTextArea notArea;
	
	public Notification()
	{
		this.setTitle("Notification Center");
		this.setSize(new Dimension(400,400));
		JPanel layout = new JPanel(new BorderLayout());
		notArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(notArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		layout.add(scrollPane);
		this.add(layout);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setVisible(true);		
	}
	
	public void addMessage(String text)
	{
		notArea.append(text+"\n");
	}
}
