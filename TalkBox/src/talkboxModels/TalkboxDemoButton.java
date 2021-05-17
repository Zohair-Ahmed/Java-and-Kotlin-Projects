package talkboxModels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
	private JButton iconButton; // icon button
	private JButton audioButton; // audio button
	
	/*---------CONSTRUCTORS---------*/
	/**
	 * This object has a panel, each panel has two buttons.
	 * One button is for the icon, and the other is for the audio
	 * 
	 * @param p - the JPanel where this constructor is added to
	 */
	public TalkboxDemoButton(JPanel p) {
		this.iconButton = createButton("Image");
		this.audioButton = createButton("Audio");
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		buttonPanel.add(this.iconButton);
		buttonPanel.add(this.audioButton);
		
		buttonPanel.setMaximumSize(new Dimension(0, 30));
		buttonPanel.setMinimumSize(new Dimension(0, 30));
		buttonPanel.setPreferredSize(new Dimension(0, 30));
		
		p.add(buttonPanel);
	}
	
	/**
	 * Returns a JButton with the specified settings
	 * 
	 * @param title - title of button
	 * 
	 * @return - a JButton with the specified settings
	 */
	private JButton createButton(String title) {
		
		JButton b = new JButton(title);
		b.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
		b.setPreferredSize(new Dimension(0, 30));
		return b;
	}
}
