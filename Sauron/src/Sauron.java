import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class Sauron extends JFrame implements ActionListener
{
	private Bluetooth gandalf;
	private Bluetooth frodo;
	private Bluetooth gimli;
	private Bluetooth aragon;
	private JPanel layout;
	private JPanel gandalfPanel;
	private JPanel frodoPanel;
	private JPanel gimliPanel;
	private JPanel aragonPanel;
	private Icon gandalfIcon;
	private Icon frodoIcon;
	private Icon gimliIcon;
	private Icon aragonIcon;
	private File fShy = new File("shy.png"); // shy
    private File fCur = new File("curious.jpg"); // curious
	private File fAgg= new File("hulk.gif"); // aggressive
	private File fNcon = new File("AnimatedRadar.gif"); // not connected
	private File fPaus = new File("pause.gif");// pause
	private JLabel gandalfLabel;
	private JLabel frodoLabel;
	private JLabel gimliLabel;
	private JLabel aragonLabel;
	private Notification not;
	private JComboBox robs;
	private JComboBox commands;
	private JButton send;
	private JButton sendAll;
	
	
	public Sauron()
	{
		this.setTitle("Sauron");
		/*enable for fullscreen
		 * Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		Dimension dimension = toolkit.getScreenSize();
		this.setSize(dimension);*/ 
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		//=================
		this.not = new Notification();
		gandalf = new Bluetooth("Gandalf", "0016530DCE8B", this,this.not);
		gandalf = new Bluetooth("Frodo", "0016530C6773", this,this.not);
		gandalf = new Bluetooth("Gimli", "0016530DD310", this,this.not);
		gandalf = new Bluetooth("Aragon", "0016530DCEE1", this,this.not);
		startSettingPanel();
		//=================
		Font title = new Font("Ubuntu", Font.BOLD, 30);
		this.setFont(title);
		TitledBorder titleBorder = new TitledBorder("");
		titleBorder.setTitleFont(title);
		TitledBorder titleBorder2 = new TitledBorder("");
		titleBorder2.setTitleFont(title);
		TitledBorder titleBorder3 = new TitledBorder("");
		titleBorder3.setTitleFont(title);
		TitledBorder titleBorder4 = new TitledBorder("");
		titleBorder4.setTitleFont(title);
		layout = new JPanel(new GridLayout(2, 2));
		//========================Gandalf
		gandalfPanel = new JPanel();
		titleBorder.setTitle("Gandalf");
		gandalfPanel.setBorder(titleBorder);
		gandalfIcon = new ImageIcon(fNcon.getAbsolutePath());
		gandalfLabel = new JLabel(gandalfIcon);
		gandalfPanel.add(gandalfLabel);
		layout.add(gandalfPanel);
		//========================Frodo
		frodoPanel = new JPanel();
		titleBorder2.setTitle("Frodo");
		frodoPanel.setBorder(titleBorder2);
		frodoIcon = new ImageIcon(fNcon.getAbsolutePath());
		frodoLabel = new JLabel(frodoIcon);
		frodoPanel.add(frodoLabel);
		layout.add(frodoPanel);
		//========================Gimli
		gimliPanel = new JPanel();
		titleBorder3.setTitle("Gimli");
		gimliPanel.setBorder(titleBorder3);
		gimliIcon = new ImageIcon(fNcon.getAbsolutePath());
		gimliLabel = new JLabel(gimliIcon);
		gimliPanel.add(gimliLabel);
		layout.add(gimliPanel);
		//========================Aragon
		aragonPanel = new JPanel();
		titleBorder4.setTitle("Aragon");
		aragonPanel.setBorder(titleBorder4);
		aragonIcon = new ImageIcon(fNcon.getAbsolutePath());
		aragonLabel = new JLabel(aragonIcon);
		aragonPanel.add(aragonLabel);
		layout.add(aragonPanel);
		//========================
		this.add(layout);
		this.setVisible(true);
	}
	
    public void startSettingPanel()
    {
    	JFrame frame = new JFrame("Action");
    	JPanel panel = new JPanel(new FlowLayout());
    	String[] array = new String[]{"Gandalf", "Frodo", "Gimli", "Aragon"};
    	String[] arrayC	 = new String[]{"Shy", "Curious", "Aggressive", "StopAuto", "StartAuto", "Pause/Play", "KillROB"};
    	robs = new JComboBox(array);
    	commands = new JComboBox(arrayC);
    	send = new JButton("Send");
    	send.addActionListener(this);
    	sendAll = new JButton("Send All");
    	sendAll.addActionListener(this);
    	panel.add(robs);
    	panel.add(commands);
    	panel.add(send);
    	panel.add(sendAll);
    	frame.getContentPane().add(panel);
    	frame.pack();
    	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	frame.setVisible(true);
    }
	
	public void updateIcons(String robName, int state)
	{
		if(robName.equals("Gandalf"))
		{
			switch(state)
			{
				case 1:
					gandalfIcon = new ImageIcon(fShy.getAbsolutePath());
					break;
				case 2:
					gandalfIcon = new ImageIcon(fCur.getAbsolutePath());
					break;
				case 3:
					gandalfIcon = new ImageIcon(fAgg.getAbsolutePath());
					break;
				case 4:
					gandalfIcon = new ImageIcon(fPaus.getAbsolutePath());
					break;
				default:
					gandalfIcon = new ImageIcon(fNcon.getAbsolutePath());
			}
			gandalfLabel.setIcon(gandalfIcon);
		}
		else if(robName.equals("Frodo"))
		{
			switch(state)
			{
				case 1:
					frodoIcon = new ImageIcon(fShy.getAbsolutePath());
					break;
				case 2:
					frodoIcon = new ImageIcon(fCur.getAbsolutePath());
					break;
				case 3:
					frodoIcon = new ImageIcon(fAgg.getAbsolutePath());
					break;
				case 4:
					frodoIcon = new ImageIcon(fPaus.getAbsolutePath());
					break;
				default:
					frodoIcon = new ImageIcon(fNcon.getAbsolutePath());
			}
			frodoLabel.setIcon(frodoIcon);
		}
		else if(robName.equals("Gimli"))
		{
			switch(state)
			{
				case 1:
					gimliIcon = new ImageIcon(fShy.getAbsolutePath());
					break;
				case 2:
					gimliIcon = new ImageIcon(fCur.getAbsolutePath());
					break;
				case 3:
					gimliIcon = new ImageIcon(fAgg.getAbsolutePath());
					break;
				case 4:
					gimliIcon = new ImageIcon(fPaus.getAbsolutePath());
					break;
				default:
					gimliIcon = new ImageIcon(fNcon.getAbsolutePath());
			}
			gimliLabel.setIcon(gimliIcon);
		}
		else if(robName.equals("Aragon"))
		{
			switch(state)
			{
				case 1:
					aragonIcon = new ImageIcon(fShy.getAbsolutePath());
					break;
				case 2:
					aragonIcon = new ImageIcon(fCur.getAbsolutePath());
					break;
				case 3:
					aragonIcon = new ImageIcon(fAgg.getAbsolutePath());
					break;
				case 4:
					aragonIcon = new ImageIcon(fPaus.getAbsolutePath());
					break;
				default:
					aragonIcon = new ImageIcon(fNcon.getAbsolutePath());
			}
			aragonLabel.setIcon(aragonIcon);
		}
		else
		{
			not.addMessage("Update Fail - Unknown Robotname: " + robName);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int i = commands.getSelectedIndex();
		i++;
		if(e.getSource() == send)
		{
			if(robs.getSelectedItem().toString().equals("Gandalf"))
			{
					gandalf.send(i);
					if(i == 7)
					{
						gandalf.setRun(false);
						not.addMessage("Gandalf got killed x.X");
						updateIcons("gandalf",5);
					}
			}
			else if(robs.getSelectedItem().toString().equals("Frodo"))
			{
					frodo.send(i);
			}
			else if(robs.getSelectedItem().toString().equals("Gimli"))
			{
					gimli.send(i);
				
			}
			else if(robs.getSelectedItem().toString().equals("Aragon"))
			{
					aragon.send(i);
			}
			else
			{
				not.addMessage("Couldn't find that Rob :( - Sending Failed!");
			}
		}
		else if(e.getSource() == sendAll)
		{
				gandalf.send(i);
				frodo.send( i);
				gimli.send(i);
				aragon.send(i);
				if(i == 7)
				{
					gandalf.setRun(false);
					frodo.setRun(false);
					gimli.setRun(false);
					aragon.setRun(false);
					not.addMessage("All ROBS got killed x.X");
				}
			}
	}
}
