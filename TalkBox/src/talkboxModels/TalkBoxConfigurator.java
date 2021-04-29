package talkboxModels;

import java.nio.file.Path;

import javax.swing.*;

public class TalkBoxConfigurator implements TalkBoxConfiguration {

	private int numOfAudioButtons;
	private int numOfAudioSets;
	private int totalButtons;
	
	private int width = 1000;
	private int height = 750;

	public TalkBoxConfigurator() {
		JFrame frame = new JFrame("TalkBox Configurator");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // center on screen
//		JButton button = new JButton("Press");
//		frame.getContentPane().add(button); // Adds Button to content pane of frame
		frame.setVisible(true);
	}

	/*---------ACCESSORS---------*/
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
