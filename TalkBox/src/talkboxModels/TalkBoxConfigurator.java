package talkboxModels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Aids those with speech impediments to communicate using a sound generating
 * device called the TalkBox
 * 
 * This class configures the TalkBox, meaning allows the user to choose which
 * buttons and their associated images can create which sound
 * 
 * @author Zohair Ahmed
 */
public class TalkBoxConfigurator {

	/*---------GLOBAL VARIABLES---------*/
	private JFrame frame; // main frame
	private int width = 800; // width of main frame
	private int height = 800; // height of main frame
	private JPanel innerPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // talkbox demo inner panel
	public static JPanel iconPanel = new JPanel(new GridLayout(0, 8, 10, 10));
	public static JLabel status = new JLabel("Welcome to the TalkBox Configurator!"); // status messages

	public static int demoInnerPanelCounter = 12; // a total of 12 TalkBoxDemoButtons can be created
	public static ArrayList<TalkboxDemoButton> demoButtons = new ArrayList<TalkboxDemoButton>(demoInnerPanelCounter);

	// global so that when click, can mutate other panels
	public static JButton addB = new JButton("Add new button"); // add new talkbox demo button
	private JButton recordB = new JButton("Record my own Audio"); // record personal audio button
	private JButton saveB = new JButton("Save"); // record personal audio button
	private JButton clearB = new JButton("Clear"); // clear all buttons in the TalkBox app button
	public static TalkboxDemoButton lastFocusedButton = new TalkboxDemoButton(); // used for adding audio

	public static boolean isSaved = false;

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

	/*---------USER INTERFACE---------*/

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
		tabs.add("Icons", addIcons()); // icon panel
		tabs.add("Audio", addAudio()); // audio panel
		this.frame.getContentPane().add(BorderLayout.SOUTH, tabs);

		// BUTTON PANEL
		this.frame.getContentPane().add(BorderLayout.WEST, buttonPanel());

		// TALKBOX SIMULATOR PANEL
		this.frame.getContentPane().add(BorderLayout.CENTER, tbDemoPanel());

		// STATUS PANEL
		this.frame.getContentPane().add(BorderLayout.NORTH, statusPanel());

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
	private JScrollPane addIcons() {

		// icon panel
		iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		iconPanel.setBackground(Color.WHITE);

		// access icon file
		File iconFile = new File(".//icons/");

		// for every icon, create a button that takes shape of the respective icon
		BufferedImage buttonImg = null; // image of button
		String filename; // file of image of button
		for (File file : iconFile.listFiles()) {
			try {
				filename = file.getName();
				if (filename.endsWith(".png") && !filename.equals("Sound.png")) {
					buttonImg = ImageIO.read(new File(".//icons/" + file.getName()));
					JLabel img = new JLabel(
							new ImageIcon(buttonImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
					img.setName(file.getName());
					img.addMouseListener(new SelectIcon());
					iconPanel.add(img);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// create JScrollPane of the panel icons are being added to
		JScrollPane icons = new JScrollPane(iconPanel);
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
	private JScrollPane addAudio() {

		// audio Panel
		JPanel audioPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		audioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
				subPanel.setName(subDir.getName());
				audioPanel.add(subPanel);

				for (File file : subDir.listFiles()) {
					if (file.getName().endsWith(".wav")) {
						audioButton = new JButton(file.getName());
						audioButton.setPreferredSize(new Dimension(0, 25));
						audioButton.addMouseListener(new PlayAudio());
						subPanel.add(audioButton);
					}
				}
			}
		}

		// create JScrollPane of the panel the audio buttons are being added to
		JScrollPane audios = new JScrollPane(audioPanel);
		audios.setPreferredSize(new Dimension(this.width, 150));

		return audios;
	}

	/**
	 * Returns a JPanel holding mutator buttons such as add, remove, record
	 * 
	 * @return - a JPanel holding mutator buttons such as add, remove, record
	 */
	private JPanel buttonPanel() {

		// main panel
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

		// add the add, clear and record buttons
		buttonPanel.add(addB);
		addB.addActionListener(new AddDemoButton());
		
		buttonPanel.add(this.recordB);

		buttonPanel.add(this.saveB);
		this.saveB.addActionListener(new SaveButton());

		buttonPanel.add(this.clearB);
		this.clearB.addActionListener(new ClearDemoButton());

		return buttonPanel;
	}

	/**
	 * The message panel
	 * 
	 * @return - a JPanel informing the users of the status of the Talkbox
	 *         Configurator
	 */
	private JPanel statusPanel() {

		// main panel
		JPanel statusPanel = new JPanel(new CardLayout());
		statusPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),
				BorderFactory.createTitledBorder("Log")));
		statusPanel.setPreferredSize(new Dimension(this.width - 20, 60));

		// message label
		status.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		statusPanel.add(status);

		return statusPanel;
	}

	/**
	 * Returns the TalkBox demo panel, which is where the modifications for the main
	 * simulator take place
	 * 
	 * @return - the TalkBox demo panel
	 */
	private JPanel tbDemoPanel() {

		// outer panel added to help with padding
		JPanel containerPanel = new JPanel(new CardLayout());
		containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

		// main inner panel
		this.innerPanel.setBackground(Color.WHITE);
		this.innerPanel.setBorder(BorderFactory.createTitledBorder("TalkBox Demo"));
		containerPanel.add(this.innerPanel);

		return containerPanel;
	}

	/*--------- BUTTON FUNCTIONALITY ---------*/

	/**
	 * Functionality for the Add button on the left button panel. Adds a
	 * TalkboxDemoButton to the inner JPanel
	 */
	private class AddDemoButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (demoInnerPanelCounter != 0) {
				TalkboxDemoButton tdb = new TalkboxDemoButton(innerPanel);
				demoButtons.add(tdb);
				demoInnerPanelCounter--;
				status.setText("Added button. " + demoInnerPanelCounter + " buttons remaining.");
				isSaved = false;
			} else {
				status.setText("Reached maximum button capacity.");
				((JButton) arg0.getSource()).setEnabled(false);
			}
		}
	}

	/**
	 * Save the demo buttons
	 */
	private class SaveButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			isSaved = true;
			try {
				FileOutputStream demoBtnData = new FileOutputStream("demoBtnData");
				ObjectOutputStream writeDemoBtns = new ObjectOutputStream(demoBtnData);
				writeDemoBtns.writeObject(demoButtons);
				writeDemoBtns.close();
				demoBtnData.close();
				status.setText("Configuration saved!");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

	}

	/**
	 * Functionality for the Add button on the left button panel. Clears the Talkbox
	 * Demo Panel
	 */
	private class ClearDemoButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			int confirmClear = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear?", "Clear Message",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirmClear == JOptionPane.YES_OPTION) {

				isSaved = false;
				
				for (TalkboxDemoButton t : demoButtons) {
					if (t.getIconButton().getIcon() != null || t.getAudioButton().getIcon() != null) {
						JLabel renewIcon = new JLabel(t.getIconButton().getIcon());
						renewIcon.addMouseListener(new SelectIcon());
						renewIcon.setName(t.getIconButton().getName());
						TalkBoxConfigurator.iconPanel.add(renewIcon);
						
					}
				}

				innerPanel.removeAll();
				demoButtons.clear();
				demoInnerPanelCounter = 12;
				status.setText("TalkBox Demo cleared");
				innerPanel.updateUI();
				addB.setEnabled(true);
			}
		}
	}

	/**
	 * Play audio when the respective buttons in the audio tabs is clicked If single
	 * click, play audio; if double clicked, add audio
	 */
	private class PlayAudio extends MouseAdapter {

		private int clickCount = 0;
		java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);
		private AudioInputStream audioIn; // audio file
		private Clip audio; // audio clip

		@Override
		public void mouseClicked(MouseEvent e) {

			this.clickCount = e.getClickCount();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {

					JButton thisButton = (((JButton) e.getSource())); // button of audio file
					String buttonName = thisButton.getText();
					String panelName = thisButton.getParent().getName();
					File audioFile = new File(".//sounds/" + panelName + "/" + buttonName);

					// get audio file this buttons references
					try {
						audioIn = AudioSystem
								.getAudioInputStream(audioFile);
						audio = AudioSystem.getClip();
						audio.open(audioIn);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}

					// on single click play button, on double click store audio in desired button
					if (clickCount == 1) {

						status.setText(buttonName + " played");
						audio.start();

					} else if (clickCount == 2) {

						isSaved = false;
						
						try {
							BufferedImage soundImage = ImageIO.read(new File(".//icons/Sound.png"));
							Icon icon = new ImageIcon(
									soundImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
							lastFocusedButton.getAudioButton().setIcon(icon);
							lastFocusedButton.getAudioButton().setHorizontalTextPosition(JButton.CENTER);
							lastFocusedButton.getAudioButton().setVerticalTextPosition(JButton.BOTTOM);
							lastFocusedButton.getAudioButton().setText(thisButton.getText());
							TalkboxDemoButton.configPath = ".//sounds/" + panelName + "/" + buttonName;

						} catch (IOException e1) {
							e1.printStackTrace();
						}

						//status.setText(buttonName + " added");
					}
					clickCount = 0;
				}
			}, 300);
		}
	}

	/**
	 * Allow the focused button in the inner panel to have the desired icon. Remove
	 * the icon from it's container.
	 */
	public static class SelectIcon extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			// get icon clicked from icon tab
			JLabel img = ((JLabel) e.getSource());
			Icon icon = img.getIcon();
			JButton focusedButton = demoButtons.get(0).getIconButton();

			// search for focused button
			try {
				int i = 0;
				while (!focusedButton.hasFocus())
					focusedButton = demoButtons.get(i++).getIconButton();

			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				status.setText("Please choose an image button to have this icon"); // update status
				return;
			}
			
			isSaved = false;

			focusedButton.setText(""); // remove button text
			status.setText("Added " + img.getName()); // update status

			// if icon already present in focused button, place back in icon tab
			if (focusedButton.getIcon() != null) {
				JLabel renewIcon = new JLabel(focusedButton.getIcon());
				renewIcon.addMouseListener(new SelectIcon());
				renewIcon.setName(focusedButton.getName());
				TalkBoxConfigurator.iconPanel.add(renewIcon);
			}

			// update focused button icon and name
			focusedButton.setIcon(icon);
			focusedButton.setName(img.getName());

			// update icon tab
			Container parent = img.getParent();
			parent.remove(img);
			parent.revalidate();
			parent.repaint();

		}
	}
}