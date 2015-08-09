package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Controller.WindowHandler;
import Logic.GalacticObject;
import Logic.Galaxy;

public class GameScreen extends JFrame {

	private static final long serialVersionUID = -1686551525997523257L;

	private ActionListener endl;
	private ActionListener gl;

	// variables for the dimensions of gameboard
	private int boardWidth;
	private int boardLength;

	//menuPanel buttons
	private GenericButton start = new GenericButton("startmenu");
	private GenericButton scores = new GenericButton("highscores");
	private GenericButton exit = new GenericButton("exit");

	//statsPanel textfield and labels
	private JLabel scoreLabel = new JLabel("Total score: ", JLabel.RIGHT);
	private JTextField totalScoreDisplayed = new JTextField("");
	private JLabel hitpointsLabel = new JLabel("Hitpoints: ", JLabel.RIGHT); //TO DO: add a label after the textfield with "/100" later on
	private JTextField hitpointsDisplayed = new JTextField("");
	private JLabel fuelLabel = new JLabel("Fuel: ", JLabel.RIGHT); //TO DO: add a label after textfield with "/100"
	private JTextField fuelDisplayed = new JTextField("");
	private JLabel offenseLabel = new JLabel("Offense: ", JLabel.RIGHT);
	private JTextField offenseDisplayed = new JTextField("");
	private JLabel defenseLabel = new JLabel("Defense: ", JLabel.RIGHT);
	private JTextField defenseDisplayed = new JTextField("");
	private JLabel experienceLabel = new JLabel("Experience points: ", JLabel.RIGHT);
	private JTextField experienceDisplayed = new JTextField("");

	//buttongrid
	private JPanel boardPanel;
	private JButton[][] gridButton;
	//Imageicons for the gridbuttons
	private ImageIcon invisibleIcon;
	private ImageIcon openIcon;
	private ImageIcon playerIcon;
	private ImageIcon moonIcon;
	private ImageIcon blackhole;
	private ImageIcon enemyLVL1Icon;
	private ImageIcon enemyLVL2Icon;
	private ImageIcon enemyLVL3Icon;
	private ImageIcon mothershipIcon;
	private ImageIcon planetIcon;
	private ImageIcon starIcon;
	private ImageIcon asteroidIcon;
	private ImageIcon asteroidfieldIcon;

	//constructor
	public GameScreen(String title, int boardWidth, int boardLength, ActionListener gl, ActionListener endl) {
		super(title);
		this.setBoardWidth(boardWidth);
		this.setBoardLength(boardLength);
		this.setGameListener(gl);
		this.setEndGameListener(endl);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowHandler());
		this.setResizable(false);
		makeGameScreen();
		pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	//methods
	public void makeGameScreen() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(makeMenuPanel(), BorderLayout.NORTH);
		getContentPane().add(makeStatsPanel(), BorderLayout.EAST);
		getContentPane().add(makeGameBoard(), BorderLayout.CENTER);
	}

	public JPanel makeMenuPanel() {
		//make the three buttons and set their properties
		start.setText("Startmenu"); 
		start.addActionListener(this.getEndGameListener());
		scores.setText("Highscores");
		scores.addActionListener(this.getEndGameListener());
		exit.setText("Exit");
		exit.addActionListener(this.getEndGameListener());

		//make the JPanel and add the buttons
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,3,10,0));
		menuPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
		menuPanel.add(start);
		menuPanel.add(scores);
		menuPanel.add(exit);

		//return the result
		return menuPanel;
	}

	//game board methods
	public JPanel makeGameBoard() {
		/** This method makes a first version of the game board when the object is first called */

		//create a new JPanel
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(this.boardWidth,this.boardLength));

		//create ImageIcons
		try {
			invisibleIcon = new ImageIcon(ImageIO.read(
					new File("resources/invisible.png")));
			openIcon = new ImageIcon(ImageIO.read(
					new File("resources/open.png")));
			playerIcon = new ImageIcon(ImageIO.read(
					new File("resources/player.png")));
			moonIcon = new ImageIcon(ImageIO.read(
					new File("resources/moon.png")));
			blackhole = new ImageIcon(ImageIO.read(
					new File("resources/blackhole.png")));
			enemyLVL1Icon = new ImageIcon(ImageIO.read(
					new File("resources/enemy1.png")));
			enemyLVL2Icon = new ImageIcon(ImageIO.read(
					new File("resources/enemy2.png")));
			enemyLVL3Icon = new ImageIcon(ImageIO.read(
					new File("resources/enemy3.png")));
			mothershipIcon = new ImageIcon(ImageIO.read(
					new File("resources/mothership.png")));
			starIcon = new ImageIcon(ImageIO.read(
					new File("resources/star.png")));
			asteroidIcon = new ImageIcon(ImageIO.read(
					new File("resources/asteroid.png")));
			asteroidfieldIcon = new ImageIcon(ImageIO.read(
					new File("resources/asteroidfield.png")));
			planetIcon = new ImageIcon(ImageIO.read(
					new File("resources/planet.png")));
		}
		catch(IOException ex) {
			System.out.println("Image file error! Printing stack trace.");
			ex.printStackTrace();
		}

		//create a 2D array of JButtons
		gridButton = new JButton[this.boardWidth][this.boardLength];

		//for-loops to place buttons in gridButton
		for(int r = 0; r < this.boardWidth; r++) {
			for(int c = 0; c < this.boardLength; c++){

				// creates new button
				gridButton[r][c]=new JButton(r+":"+c);
				gridButton[r][c].setActionCommand(r+":"+c);
				gridButton[r][c].setIcon(invisibleIcon);
				gridButton[r][c].setText("");
				gridButton[r][c].setIcon(null);

				//format gridButton[r][c]
				Color space = new Color(44, 44, 64);
				gridButton[r][c].setBackground(space); //all buttons by default space purple background color
				gridButton[r][c].setPreferredSize(new Dimension(32, 32)); 
				gridButton[r][c].setMargin(new Insets(0, 0, 0, 0));
				gridButton[r][c].setBorder(null);
				gridButton[r][c].addActionListener(this.getGameListener());

				//adds button to panel
				boardPanel.add(gridButton[r][c]);
			}
		}
		//return boardPanel
		return boardPanel;
	}

	public void updateGameBoard(Galaxy board) {
		/** This method initializes the board when the galaxy is first created in the game logic and updates it throughout the game */
		Galaxy galaxyArray = board;

		for (int r = 0; r < this.boardWidth; r++) {
			for (int c = 0; c < this.boardLength; c++) {

				//make galactic object and extract info from Galaxy
				GalacticObject temporary = new GalacticObject(r,c);
				temporary = galaxyArray.getGalacticObject(r,c);

				//check the object and act accordingly
				if (temporary.isAsteroid() && temporary.isVisible()) {
					gridButton[r][c].setIcon(asteroidIcon);
				} else if (temporary.isAsteroidField() && temporary.isVisible()) {
					gridButton[r][c].setIcon(asteroidfieldIcon);
				} else if (temporary.isBlackHole() && temporary.isVisible()) {
					gridButton[r][c].setIcon(blackhole);
				} else if (temporary.isMoon() && temporary.isVisible()) {
					gridButton[r][c].setIcon(moonIcon);
				} else if (temporary.isPlanet() && temporary.isVisible()) {
					gridButton[r][c].setIcon(planetIcon);
				} else if (temporary.isStar() && temporary.isVisible()) {
					gridButton[r][c].setIcon(starIcon);
				} else if (temporary.isPlayer() && temporary.isVisible()) {
					gridButton[r][c].setIcon(playerIcon);
				} else if (temporary.isShipLevel1() && temporary.isVisible()) {
					gridButton[r][c].setIcon(enemyLVL1Icon);
				} else if (temporary.isShipLevel2() && temporary.isVisible()) {
					gridButton[r][c].setIcon(enemyLVL2Icon);
				} else if (temporary.isShipLevel3() && temporary.isVisible()) {
					gridButton[r][c].setIcon(enemyLVL3Icon);
				} else if (temporary.isMotherShip() && temporary.isVisible()) {
					gridButton[r][c].setIcon(mothershipIcon);
				} else if (temporary.isOpen() && temporary.isVisible()) {
					gridButton[r][c].setIcon(openIcon);
				} else {
					gridButton[r][c].setIcon(invisibleIcon);
				}
			}
		}	
	}

	//statspanel methods
	public JPanel makeStatsPanel() {
		/** This method makes the player stats panel where all the player stats are visible */
		// Don't let the user change their stats
		totalScoreDisplayed.setEditable(false);
		totalScoreDisplayed.addActionListener(this.getGameListener());
		hitpointsDisplayed.setEditable(false);
		hitpointsDisplayed.addActionListener(this.getGameListener());
		fuelDisplayed.setEditable(false);
		fuelDisplayed.addActionListener(this.getGameListener());
		offenseDisplayed.setEditable(false);
		offenseDisplayed.addActionListener(this.getGameListener());
		defenseDisplayed.setEditable(false);
		defenseDisplayed.addActionListener(this.getGameListener());
		experienceDisplayed.setEditable(false);
		experienceDisplayed.addActionListener(this.getGameListener());

		// create a new JPanel
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10)); //play around with border settings later
		statsPanel.setLayout(new GridLayout(6,2)); // if you want "Hitpoints:" "value" "/100" you'll need 3 collumns
		statsPanel.add(scoreLabel);
		statsPanel.add(totalScoreDisplayed);
		statsPanel.add(hitpointsLabel);
		statsPanel.add(hitpointsDisplayed);
		statsPanel.add(fuelLabel);
		statsPanel.add(fuelDisplayed);
		statsPanel.add(offenseLabel);
		statsPanel.add(offenseDisplayed);
		statsPanel.add(defenseLabel);
		statsPanel.add(defenseDisplayed);
		statsPanel.add(experienceLabel);
		statsPanel.add(experienceDisplayed);

		// return the panel
		return statsPanel;
	}

	public void updateStatsPanel(double[] stats) {
		/** This method updates the player stats during the game */
		//initialize
		String score = Double.toString(stats[0]);
		String hp = Double.toString(stats[1]);
		String fuel = Double.toString(stats[2]);
		String off = Double.toString(stats[3]);
		String def = Double.toString(stats[4]);
		String xp = Double.toString(stats[5]);

		//fill in the labels
		totalScoreDisplayed.setText(score);
		hitpointsDisplayed.setText(hp);
		fuelDisplayed.setText(fuel);
		offenseDisplayed.setText(off);
		defenseDisplayed.setText(def);
		experienceDisplayed.setText(xp);
	}

	//reset everything
	public void resetGameAndStats() {
		/** This method resets everything to the original settings */
		//reset the game board
		for (int y = 0; y < this.boardWidth; y++) {
			for (int x = 0; x < this.boardLength; x++) {
				gridButton[y][x].setText("");
			}
		}

		//reset the statistics panel
		totalScoreDisplayed.setText("");
		hitpointsDisplayed.setText("");
		fuelDisplayed.setText("");
		offenseDisplayed.setText("");
		defenseDisplayed.setText("");
		experienceDisplayed.setText("");
	}

	//setter
	public void setEndGameListener(ActionListener endl) {
		this.endl = endl;
	}

	public void setGameListener(ActionListener gl) {
		this.gl = gl;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength; 
	}

	//getter
	public ActionListener getEndGameListener() {
		return this.endl;
	}

	public ActionListener getGameListener() {
		return this.gl;
	}
}
