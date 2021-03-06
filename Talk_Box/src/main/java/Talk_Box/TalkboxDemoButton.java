package Talk_Box;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A helper class that holds the button and audio of the users choosing. Goes in
 * the Talkbox Demo Panel
 * 
 * @author Zohair Ahmed
 */
public class TalkboxDemoButton implements Serializable {

	/*---------GLOBAL VARIABLES---------*/
	private static final long serialVersionUID = 1L; // (static fields are not serialized)
	private JButton iconButton; // icon button
	private JButton audioButton; // audio button
	private JButton removeButton; // remove button (transient means don't serialize)
	public static String configPath = ""; // gets path of audio file for the audio button
	private String thisAudioPath = ""; // sets THIS demo button to have audio file path in the static config path
	private File audioFile = null; // audio file
	/*---------CONSTRUCTORS---------*/

	public TalkboxDemoButton() {
	}

	/**
	 * This object has a panel, each panel has two buttons. One button is for the
	 * icon, and the other is for the audio
	 * 
	 * @param p - the JPanel where this constructor is added to
	 */
	public TalkboxDemoButton(JPanel p) {

		this.iconButton = createButton("Icon");
		this.iconButton.addMouseListener(new PlayAudio());

		this.audioButton = createButton("Audio");
		this.audioButton.grabFocus();
		this.audioButton.addFocusListener(new getLastAudioButton());

		this.removeButton = new JButton("Remove");
		this.removeButton.addMouseListener(new RemoveDemo());

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(this.iconButton, BorderLayout.WEST);
		buttonPanel.add(this.audioButton, BorderLayout.EAST);
		buttonPanel.add(this.removeButton, BorderLayout.SOUTH);

		p.add(buttonPanel);
		p.updateUI();
	}

	/*---------USER INTERFACE---------*/

	/**
	 * Returns a JButton with the specified settings
	 * 
	 * @param title - title of button
	 * 
	 * @return - a JButton with the specified settings
	 */
	private JButton createButton(String title) {

		JButton b = new JButton(title);
		b.setBackground(Color.WHITE);
		b.setForeground(Color.LIGHT_GRAY);
		b.isFocusable();
		b.setPreferredSize(new Dimension(135, 0));

		return b;
	}

	/*--------- ACCESSORS ---------*/
	/**
	 * Returns the icon button
	 * 
	 * @return - the icon button
	 */
	public JButton getIconButton() {
		return this.iconButton;
	}

	/**
	 * Returns the audio button
	 * 
	 * @return - the audio button
	 */
	public JButton getAudioButton() {
		return this.audioButton;
	}

	/**
	 * Returns the audio file
	 * 
	 * @return - the audio file
	 */
	public File getAudioFile() {
		return this.audioFile;
	}

	/**
	 * Returns the audio path
	 * 
	 * @return - the audio path
	 */
	public String getAudioPath() {
		return this.thisAudioPath;
	}

	/**
	 * The name of the icon button and the file that plays the audio
	 */
	@Override
	public String toString() {
		return "Icon: " + this.iconButton.getName() + ", Audio Clip: " + audioFile.getAbsolutePath();
	}

	/*--------- MUTATORS ---------*/

	/**
	 * Sets the icon button of this demo button
	 * 
	 * @param iconButton - the icon button
	 */
	public void setIconButton(JButton iconButton) {
		this.iconButton = iconButton;
	}

	/**
	 * Sets the audio button of this demo button
	 * 
	 * @param audioButton - the audio button
	 */
	public void setAudioButton(JButton audioButton) {
		this.audioButton = audioButton;
	}

	/**
	 * Sets the audio path of this demo button
	 * 
	 * @param audioPath - the audio path of this demo button
	 */
	public void setAudioPath(String audioPath) {
		this.thisAudioPath = audioPath;
	}

	/*--------- BUTTON FUNCTIONALITY ---------*/

	/**
	 * Remove panel that holds the icon button and audio button.
	 */
	private class RemoveDemo extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			JButton remove = ((JButton) e.getSource()); // the remove button

			// get icon currently in icon button and place it back in the icon tab
			ImageIcon removedIcon = (ImageIcon) iconButton.getIcon();
			JLabel renewIcon = new JLabel(removedIcon);
			renewIcon.addMouseListener(new TalkBoxConfigurator.SelectIcon());
			renewIcon.setName(iconButton.getName());
			TalkBoxConfigurator.iconPanel.add(renewIcon);
			TalkBoxConfigurator.status
					.setText("Removed button. " + ++TalkBoxConfigurator.demoInnerPanelCounter + " buttons remaining.");
			TalkBoxConfigurator.addB.setEnabled(true);

			// remove talkboxDemoButton from its ArrayList is the configurator
			int i = 0;
			TalkboxDemoButton removeDemoButton = TalkBoxConfigurator.demoButtons.get(i);
			while (!removeDemoButton.getIconButton().equals(iconButton)) {
				++i;
				removeDemoButton = TalkBoxConfigurator.demoButtons.get(i);
			}
			TalkBoxConfigurator.demoButtons.remove(i);
			TalkBoxConfigurator.isSaved = false;

			// remove the panel
			Container parent = remove.getParent().getParent();
			parent.remove(remove.getParent());

			TalkBoxConfigurator.iconPanel.revalidate();
			TalkBoxConfigurator.iconPanel.repaint();
			parent.revalidate();
			parent.repaint();

		}
	}

	/**
	 * Used for when the focused is lost when user clicks on one of the audio
	 * buttons in the audio tab
	 */
	public class getLastAudioButton implements FocusListener {

		@Override
		public void focusGained(FocusEvent arg0) {
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			TalkBoxConfigurator.lastFocusedButton.audioButton = ((JButton) arg0.getSource());
		}
	}

	/**
	 * If an audio is added, plays the audio whenever the icon button is clicked
	 */
	private class PlayAudio extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			AudioInputStream audioIn;
			Clip audio;

			// if no audio is selected
			if (audioButton.getIcon() == null) {
				TalkBoxConfigurator.status.setText("This Talkbox demo button has no audio.");
				return;
			}

			// if the button was clicked and a new sound file was being added
			if (!configPath.equals("")) {
				thisAudioPath = configPath;
				configPath = "";
				audioFile = new File(thisAudioPath);
			}

			// play audio if file exists
			try {
				audioIn = AudioSystem.getAudioInputStream(audioFile);
				audio = AudioSystem.getClip();
				audio.open(audioIn);
				audio.start();
				TalkBoxConfigurator.status.setText(getAudioButton().getText() + " previewed");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}
}