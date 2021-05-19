package talkboxModels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A helper class that holds the button and audio 
 * of the users choosing.
 * Goes in the Talkbox Demo Panel
 * 
 * @author Zohair Ahmed
 */ 
public class TalkboxDemoButton {
	
	/*---------GLOBAL VARIABLES---------*/
	private String status;
	private JButton iconButton; // icon button
	private JButton audioButton; // audio button
	
	//private Icon iconImage;
	//private Clip audioClip;
	
	/*---------CONSTRUCTORS---------*/
	
	/**
	 * This object has a panel, each panel has two buttons.
	 * One button is for the icon, and the other is for the audio
	 * 
	 * @param p - the JPanel where this constructor is added to
	 */
	public TalkboxDemoButton(JPanel p) {
		this.iconButton = createIconButton();
		this.audioButton = createAudioButton();
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		buttonPanel.add(this.iconButton);
		buttonPanel.add(this.audioButton);
		
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
		//b.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 10, 10));
		b.setBackground(Color.WHITE);
		b.setPreferredSize(new Dimension(0, 30));
		b.setForeground(Color.LIGHT_GRAY);
		b.isFocusable();
		
		return b;
	}
	
	/**
	 * Sets the icon button to have the certain actions
	 * 
	 * @return - the JButton for the icon
	 */
	private JButton createIconButton() {
		JButton iconButton = createButton("Icon");
		return iconButton;
	}
	
	/**
	 * Sets the audio button to have the certain actions
	 * 
	 * @return - the JButton for the audio
	 */
	private JButton createAudioButton() {
		JButton audioButton = createButton("Audio");
		return audioButton;
	}
	
	/*--------- ACCESSORS ---------*/
	
	/**
	 * Returns the status of the most recent talkbox demo button related action
	 * 
	 * @return - the status of the most recent talkbox demo button related action
	 */
	private String getStatus() {
		return this.status;
	}
	
	/**
	 * Returns the icon button
	 * 
	 * @return -  the icon button
	 */
	public JButton getIconButton() {
		return this.iconButton;
	}
	
	/**
	 * Returns the audio button
	 * 
	 * @return -  the audio button
	 */
	public JButton getAudioButton() {
		return this.audioButton;
	}
	
	/**
	 * A private helper method that returns the file extension of a file
	 * 
	 * @param file - the Path of the file
	 * 
	 * @return - the file extension
	 */
	private String getFileExtension(File file) {

		String fileName = file.getName();

		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		
		return "";

	}
}