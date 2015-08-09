package View;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;

import Controller.WindowHandler;

public class StartScreen extends JFrame {

	private static final long serialVersionUID = -3049454687261249999L;

	private ActionListener al;
	private ChangeListener cl;

	private JTextArea textTop = new JTextArea("texttop");
	private JLabel labelMiddle = new JLabel("middlelabel");
	private GenericButton diff1 = new GenericButton("easy");
	private GenericButton diff2 = new GenericButton("normal");
	private GenericButton diff3 = new GenericButton("hard");
	private GenericButton start = new GenericButton("start");
	private GenericButton scores = new GenericButton("highscores");
	private GenericButton exit = new GenericButton("exit");

	//constructor
	public StartScreen(String title, ActionListener al, ChangeListener cl) {
		super(title);
		this.setActionListener(al);
		this.setChangeListener(cl);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowHandler());
		this.setResizable(true);
		makeStartScreen();
		pack();
		this.setLocationRelativeTo(null);
		pack(); //calling pack() twice solves some strange packing issues I was having
		this.setVisible(true);
	}

	//methods
	public void makeStartScreen() {
		/** This method draws the startscreen by defining a JFrame and filling it up with JPanels using the various methods */
		//using GridBagLayout allows for flexibility but good constraints need to be formulated
		getContentPane().setLayout(new GridBagLayout());
		// gridbagconstraints for top panel and add to frame
		GridBagConstraints gbcTop = new GridBagConstraints();
		gbcTop.gridy=0;
		gbcTop.fill = GridBagConstraints.BOTH;
		getContentPane().add(makeTopDisplay(), gbcTop);
		// gridbagconstraints for Difficulty selection panel and add to frame
		GridBagConstraints gbcDifslct = new GridBagConstraints();
		gbcDifslct.gridy=1;
		getContentPane().add(makeDifficultySelection(), gbcDifslct);
		// gridbagconstraints for MiddleDisplay and add to frame
		GridBagConstraints gbcMiddle = new GridBagConstraints();
		gbcMiddle.gridy=2;
		getContentPane().add(makeMiddleDisplay(), gbcMiddle);
		// gridbagconstraints for bottom selection panel and add to frame
		GridBagConstraints gbcBottom = new GridBagConstraints();
		gbcBottom.gridy=3;
		getContentPane().add(makeBottomSelection(), gbcBottom);
	}

	public JPanel makeTopDisplay() {
		/** This method makes the top display by simply adding a textlabel to the JPanel */
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,1));
		textTop.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		textTop.setText("Welcome to Hyperspace Hazard!\n\n"
				+ "The Alpha Centauri Conglomerate has received word from a rebel uprising in their mining territories."
				+ " Suppress this rebellion and DESTROY the rebel MOTHERSHIP.\n"
				+ "You will face many dangers. Good luck, captain!\n\n"
				+ "Select your difficulty level below and choose a skillpoint distribution on the slider");

		textTop.setOpaque(false);
		textTop.setLineWrap(true);
		textTop.setWrapStyleWord(true);
		textTop.setEditable(false);
		topPanel.add(textTop);
		return topPanel;
	}

	public JPanel makeDifficultySelection() {
		/** This method makes the difficulty selection panel by adding three buttons */
		//make the three buttons for difficulty selection as well as set their properties
		diff1.setText("Recruit");
		diff1.addActionListener(this.getActionListener());
		diff2.setText("Soldier");
		diff2.addActionListener(this.getActionListener());
		diff3.setText("Commander");
		diff3.addActionListener(this.getActionListener());

		//make the JPanel and add the buttons to the panel
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridLayout(1,3,10,0));
		difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		difficultyPanel.add(diff1);
		difficultyPanel.add(diff2);
		difficultyPanel.add(diff3);

		//return the result
		return difficultyPanel;
	}

	public JPanel makeMiddleDisplay() {
		/** This method makes the middle part of the start screen by adding a textlabel and a slider */
		//make the JPanel and add the textlabel and the slider
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(2,1,0,10));
		middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		labelMiddle.setText("Choose your skillpoints!");
		middlePanel.add(labelMiddle);
		middlePanel.add(makeSlider());

		//return the result
		return middlePanel;
	}

	public JPanel makeBottomSelection() {
		/** This method makes the bottom part of the start screen by adding the start, exit and highscores button */
		//make the three buttons and set their properties
		start.setText("Start");
		start.addActionListener(this.getActionListener());
		scores.setText("Highscores");
		scores.addActionListener(this.getActionListener());
		exit.setText("Exit");
		exit.addActionListener(this.getActionListener());

		//make the JPanel and add the buttons
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3,10,0));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		bottomPanel.add(start);
		bottomPanel.add(scores);
		bottomPanel.add(exit);

		//return the result
		return bottomPanel;
	}

	public JSlider makeSlider() {
		/** This method returns a slider with icon labels */
		//initiate the slider
		JSlider slider = new JSlider();
		slider.addChangeListener(this.getChangeListener());
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setValue(50);

		//add labels to the ticks
		Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		labelTable.put(0, new JLabel("Defense"));
		labelTable.put(50, new JLabel("Balanced"));
		labelTable.put(100, new JLabel("Offense"));

		//add the table to the slider and return the result
		slider.setLabelTable(labelTable);
		return slider;
	}

	//setter
	public void setActionListener(ActionListener al) {
		this.al = al;
	}

	public void setChangeListener(ChangeListener cl) {
		this.cl = cl;
	}

	//getter
	public ActionListener getActionListener() {
		return this.al;
	}

	public ChangeListener getChangeListener() {
		return this.cl;
	}
}
