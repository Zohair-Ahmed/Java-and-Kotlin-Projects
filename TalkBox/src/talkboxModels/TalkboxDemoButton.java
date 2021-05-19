package talkboxModels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
	private String status;
	private JButton iconButton; // icon button
	private JButton audioButton; // audio button
	private JButton removeButton; // audio button
	
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
		
		this.iconButton = createButton("Icon");
		this.audioButton = createButton("Audio");
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
	
	/*---------BUTTON FUNCTIONALITY---------*/
	
	/**
	 * Remove panel 
	 * @author Zohair
	 *
	 */
	private class RemoveDemo extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton remove = ((JButton) e.getSource());
			
			Container parent = remove.getParent().getParent();
			parent.remove(remove.getParent());
			iconButton = null;
			audioButton = null;
			removeButton = null;
			status = null;
			parent.revalidate();
			parent.repaint();
		}
	}
}