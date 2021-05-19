package talkboxModels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	private static boolean isSelected = false;
	private static int count = 0;
	
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
		this.iconButton = createButton("Image");
		this.audioButton = createButton("Audio");
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		buttonPanel.add(this.iconButton);
		buttonPanel.add(this.audioButton);
		buttonPanel.updateUI();
		
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
		b.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 10, 10));
		b.setBackground(Color.WHITE);
		b.setPreferredSize(new Dimension(0, 30));
		b.setForeground(Color.LIGHT_GRAY);
		b.addMouseListener(new isSelected());
		
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
	
//	public void addImage(File iconFile) {
//		if (getFileExtension(iconFile) != "png") {
//			this.status = "Please insert .png files only";
//		} else {
//			
//			BufferedImage img = null;
//			try {
//				img = ImageIO.read(iconFile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			this.iconImage = new ImageIcon(img.getScaledInstance(0, 30, java.awt.Image.SCALE_SMOOTH));
//			
//			this.iconButton.setIcon(this.iconImage);
//		}
//	}
	
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
	
	private static class isSelected extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			count = e.getClickCount(); 
			JButton thisButton = (JButton) e.getSource();
			
			if (count == 1) {
				((JButton) e.getSource()).setBorder(BorderFactory.createDashedBorder(Color.BLUE, 10, 10));
				((JButton) e.getSource()).setSelected(true);
			}
			
			count = 0;
		}
	}
}