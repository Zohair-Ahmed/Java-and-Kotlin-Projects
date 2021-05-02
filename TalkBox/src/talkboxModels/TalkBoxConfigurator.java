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

	/*---------GLOBAL VARIABLES---------*/
	private static final long serialVersionUID = 1L;

	private JFrame frame; // main frame
	private int width = 600; // width of main frame
	private int height = 600; // height of main frame

	private ArrayList<JButton> iconTabButtons = new ArrayList<JButton>(); // icon buttons

	private ArrayList<JButton> audioTabButtons = new ArrayList<JButton>();; // audio buttons

	private int numOfAudioButtons; // number of audio buttons
	private int numOfAudioSets; // number of audio sets
	private int totalButtons; // buttons in total

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

	/*---------MUTATORS---------*/

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

		// icon panel
		JPanel iconPanel = new JPanel(new GridLayout(0, 6, 10, 10));
		iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabs.add("Icons", addIcons(this.iconTabButtons, iconPanel));

		// audio panel
		JPanel audioPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		audioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabs.add("Audio", addAudio(this.audioTabButtons, audioPanel));

		this.frame.getContentPane().add(BorderLayout.SOUTH, tabs);
		this.frame.pack();
		this.frame.setVisible(true);
	}

	/**
	 * Adds the icon buttons to the JTabbedPane
	 * 
	 * @param buttons   - the ArrayList for the buttons created
	 * @param thisPanel - the JPanel where the buttons are to be displayed
	 * 
	 * @return - a JScrollPane of the desired icons as buttons
	 */
	private JScrollPane addIcons(ArrayList<JButton> buttons, JPanel thisPanel) {

		// access icon file
		File iconFile = new File(".//icons/");

		// for every icon, create a button that takes shape of the respective icon
		JButton iconButton; // button
		BufferedImage buttonImg = null; // image of button
		String filename; // file of image of button
		for (File file : iconFile.listFiles()) {
			try {
				filename = file.getName();
				if (filename.endsWith(".png") && !filename.equals("Sound.png")) {
					buttonImg = ImageIO.read(new File(".//icons/" + file.getName()));
					iconButton = new JButton(new ImageIcon(buttonImg));
					iconButton.setBorder(BorderFactory.createEmptyBorder());
					iconButton.setPreferredSize(new Dimension(0, 90));
					buttons.add(iconButton);
					thisPanel.add(iconButton);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// create JScrollPane of the panel icons are being added to
		JScrollPane icons = new JScrollPane(thisPanel);
		icons.setPreferredSize(new Dimension(this.width, 150));

		return icons;
	}

	/**
	 * Adds the audio buttons to the JTabbedPane
	 * 
	 * @param buttons   - the ArrayList holding each audio button
	 * @param thisPanel - panel audio buttons are added to
	 * 
	 * @return - a JScrollPane of the desired audio as buttons
	 */
	private JScrollPane addAudio(ArrayList<JButton> buttons, JPanel thisPanel) {

		// access sound file
		File soundFile = new File(".//sounds");
		
		// for every sub-directory in the sounds file,
		// create a sub-panel for the sub-directory
		// inside each sub-panel, put respective buttons
		// for the audio files
		JButton audioButton;
		for (File subDir : soundFile.listFiles()) {
			if (subDir.isDirectory()) {
				JPanel subPanel = new JPanel(new GridLayout(0, 4));
				subPanel.setBorder(BorderFactory.createTitledBorder(subDir.getName()));
				subPanel.setPreferredSize(new Dimension(this.width - 60, 150));
				thisPanel.add(subPanel);
				
				for (File file : subDir.listFiles()) {
					if (file.getName().endsWith(".wav")) {
						audioButton = new JButton(file.getName());
						audioButton.setPreferredSize(new Dimension(0, 0));
						this.audioTabButtons.add(audioButton);
						subPanel.add(audioButton);
					}
				}
			} 
		}

		// create JScrollPane of the panel the audio buttons are being added to
		JScrollPane audioPanel = new JScrollPane(thisPanel);
		audioPanel.setPreferredSize(new Dimension(this.width, 150));

		return audioPanel;
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
