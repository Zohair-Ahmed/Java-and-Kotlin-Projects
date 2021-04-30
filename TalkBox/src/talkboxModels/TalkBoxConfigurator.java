package talkboxModels;

import java.nio.file.Path;
import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

/**
 * Aids those with speech impediments to communicate using a 
 * sound generating device called the TalkBox
 * 
 * This class configures the TalkBox, meaning allows the user
 * to choose which buttons and their associated images can create
 * which sound
 * 
 * @author Zohair Ahmed
 */
public class TalkBoxConfigurator implements TalkBoxConfiguration {

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private int width = 300;
	private int height = 300;
	
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
		frame = new JFrame("TalkBox Configurator");
		frame.setSize(this.width, this.height);
		this.frame.setLocationRelativeTo(null); // center on screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[]", "[]"));
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
