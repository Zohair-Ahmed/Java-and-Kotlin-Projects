package Talk_Box;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TalkBoxSimulator {

	public JFrame frame; // main frame
	private int width = 800; // width of main frame
	private int height = 800; // height of main frame
	private File audioFile = null;

	public static ArrayList<TalkboxDemoButton> demoButtons = new ArrayList<TalkboxDemoButton>(12);

	/*---------MAIN METHOD---------*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxSimulator window = new TalkBoxSimulator();
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
	 * The simulator runs the icons and audio for whatever was last specified in the
	 * configurator
	 */
	public TalkBoxSimulator() {
		initialize();
	}

	/*---------USER INTERFACE---------*/

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// MAIN FRAME
		this.frame = new JFrame("TalkBox Simulator");
		this.frame.setPreferredSize(new Dimension(this.width, this.height));
		this.frame.setLocationByPlatform(true);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setMinimumSize(this.frame.getSize());

		// BUTTONS PANEL
		JPanel btnsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		btnsPanel.setBorder(
				BorderFactory.createEmptyBorder(this.height / 10, this.width / 10, this.height / 10, this.width / 10));
		btnsPanel.setPreferredSize(new Dimension(3 * this.width / 4, 3 * this.height / 4));
		getDemoButtons();
		addDemoButtons(btnsPanel);

		this.frame.getContentPane().add(btnsPanel, BorderLayout.CENTER);

		this.frame.pack();
		this.frame.setVisible(true);
	}

	/**
	 * Deserialize the demo buttons created in the most recently saved configuration
	 */
	@SuppressWarnings("unchecked")
	private void getDemoButtons() {

		// deserialize configurator buttons
		try {
			FileInputStream fis = new FileInputStream("demoBtnData");
			ObjectInputStream ois = new ObjectInputStream(fis);

			TalkBoxSimulator.demoButtons = (ArrayList<TalkboxDemoButton>) ois.readObject();

			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}

	/**
	 * Add the demo buttons in the configuration
	 * 
	 * @param thisPnl - the panel to add the demo buttons
	 */
	private void addDemoButtons(JPanel thisPnl) {

		JButton btn;

		for (TalkboxDemoButton t : TalkBoxSimulator.demoButtons) {
			btn = t.getIconButton();
			btn.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent e) {

					AudioInputStream audioIn;
					audioFile = new File(t.getAudioPath());
					try {
						audioIn = AudioSystem.getAudioInputStream(audioFile);
						Clip audio = AudioSystem.getClip();
						audio.open(audioIn);
						audio.start();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
						ex.printStackTrace();
					}

				}

			});

			thisPnl.add(btn);
		}
	}
}