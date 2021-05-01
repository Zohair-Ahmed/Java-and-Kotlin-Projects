package talkboxModels;

import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Aids those with speech impediments to communicate using a sound generating
 * device called the TalkBox
 * 
 * This class configures the TalkBox, meaning allows the user to choose which
 * buttons and their associated images can create which sound
 * 
 * @author Zohair Ahmed
 */
public class TalkBoxConfigurator implements TalkBoxConfiguration {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	//private JPanel mainPanel;
	private ArrayList<JButton> iconTabButtons;
	private ArrayList<String> iconImages = new ArrayList<String>();
	private ArrayList<JButton> audioTabButtons;

	private int width = 600;
	private int height = 600;

	private int numOfAudioButtons;
	private int numOfAudioSets;
	private int totalButtons;

	/*---------MAIN METHOD---------*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxConfigurator window = new TalkBoxConfigurator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*---------CONSTRUCTORS---------*/

	/**
	 * Create the application.
	 * 
	 * The configurator that sets the buttons and associating audio for the sound
	 * generating device, called the TalkBox
	 */
	public TalkBoxConfigurator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// MAIN FRAME
		this.frame = new JFrame("TalkBox Configurator");
		this.frame.setPreferredSize(new Dimension(this.width, this.height));
		this.frame.setLocationByPlatform(true);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setMinimumSize(this.frame.getSize());

		// ICON AND AUDIO TABS
		JTabbedPane tabs = new JTabbedPane();
		tabs.setBackground(Color.BLUE);
		
		// grid for icon and audio panel
		GridLayout grid = new GridLayout(0, 6);
		grid.setHgap(10);
		grid.setVgap(10);
		
		// icon panel
		JPanel iconPanel = new JPanel();
		iconPanel.setLayout(grid);
		iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabs.add("Icons", addIcons(this.iconTabButtons, iconPanel));
		
		// audio panel
		JPanel audioPanel = new JPanel();
		audioPanel.setLayout(grid);
		audioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabs.add("Audio", addButtons(this.audioTabButtons, audioPanel));
		
		this.frame.getContentPane().add(BorderLayout.SOUTH, tabs);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private JScrollPane addButtons(ArrayList<JButton> buttons, JPanel thisPanel) {
		
		buttons = new ArrayList<JButton>();

		for (int i = 1; i <= 40; i++) {
			JButton icon = new JButton("" + i);
			buttons.add(icon);
			thisPanel.add(icon);
		}
		
		JScrollPane icons = new JScrollPane(thisPanel);
		icons.setPreferredSize(new Dimension(this.width, 150));
		
		return icons;
		
	}
	
	private JScrollPane addIcons(ArrayList<JButton> buttons, JPanel thisPanel) {
		
		buttons = new ArrayList<JButton>();
		
		File iconFile = new File(".//icons/");
		String filename;
		for (File file : iconFile.listFiles()) {
			filename = file.getName();
			if (filename.endsWith(".png") && !filename.equals("Sound.png"))
				this.iconImages.add(filename);
		}
		
		BufferedImage buttonIcon = null;
		JButton icon;
		for (String imageFile : this.iconImages) {
			try {
				buttonIcon = ImageIO.read(new File(".//icons/" + imageFile));
				icon = new JButton(new ImageIcon(buttonIcon));
				icon.setBorder(BorderFactory.createEmptyBorder());
				icon.setPreferredSize(new Dimension(10, 100));
				buttons.add(icon);
				thisPanel.add(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JScrollPane icons = new JScrollPane(thisPanel);
		icons.setPreferredSize(new Dimension(this.width, 150));
		
		return icons;
	}
	
	private JScrollPane addAudio(ArrayList<JButton> buttons, JPanel thisPanel) {
		
		buttons = new ArrayList<JButton>();
		
		
		File iconFile = new File(".//icons/Sound.png");
		
		return null;
	}
	
	/*--------- ACCESSORS ---------*/

	@Override
	public int getNumberOfAudioButtons() {
		return this.numOfAudioButtons;
	}

	@Override
	public int getNumberOfAudioSets() {
		return this.numOfAudioSets;
	}

	@Override
	public int getTotalNumberOfButtons() {
		return this.totalButtons;
	}

	@Override
	public Path getRelativePathToAudioFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] getAudioFileNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
