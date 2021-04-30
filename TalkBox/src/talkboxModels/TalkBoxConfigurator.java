package talkboxModels;

import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

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
	private JPanel mainPanel;
	private ArrayList<JButton> iconTabButtons;
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
		this.frame.setSize(this.width, this.height);
		this.frame.setPreferredSize(new Dimension(this.width, this.height));
		this.frame.setLocationByPlatform(true);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setMinimumSize(this.frame.getSize());

		// ICON AND AUDIO TABS
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Icons", addButtons(this.iconTabButtons));
		tabs.add("Audio", addButtons(this.audioTabButtons));
		tabs.setBackground(Color.BLUE);
		this.frame.getContentPane().add(BorderLayout.SOUTH, tabs);

		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private JScrollPane addButtons(ArrayList<JButton> buttons) {
		
		GridLayout grid = new GridLayout(0, 6);
		grid.setHgap(10);
		grid.setVgap(10);
		
		buttons = new ArrayList<JButton>();
		
		JPanel thisPanel = new JPanel();
		thisPanel.setLayout(grid);
		thisPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 1; i <= 40; i++) {
			JButton icon = new JButton("" + i);
			buttons.add(icon);
			thisPanel.add(icon);
		}
		JScrollPane icons = new JScrollPane(thisPanel);
		icons.setPreferredSize(new Dimension(this.width, 150));
		
		return icons;
		
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
