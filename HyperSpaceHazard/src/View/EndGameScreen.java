package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import Logic.HighScores;

public class EndGameScreen extends JDialog {

	private static final long serialVersionUID = -5664774431509895326L;

	private boolean done = false;

	private double currentscore;
	private String content;
	private String playerName = "";
	private String playerShip = "";

	private JLabel message = new JLabel("message");
	private JLabel commander = new JLabel("Enter your name commander...? ", JLabel.RIGHT);
	private JLabel ship = new JLabel("And your ships name...? ", JLabel.RIGHT);
	private JTextField inputCommander = new JTextField("");
	private JTextField inputShip = new JTextField("");

	private GenericButton save = new GenericButton("Save");

	//constructor
	public EndGameScreen(JFrame parent, String title, String content, double currentscore) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		this.setContent(content);
		this.setCurrentScore(currentscore);
		getContentPane().add(makeDisplay(), BorderLayout.NORTH);
		getContentPane().add(makeWritingFields(), BorderLayout.CENTER);
		getContentPane().add(makeButtons(), BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		pack();
		this.setVisible(true);
	}

	//methods
	public JPanel makeDisplay() {
		/** This method displays the content */
		JPanel display = new JPanel();
		message.setText(this.getContent());
		display.add(message);
		return display;
	}

	public JPanel makeWritingFields() {
		/** This method makes the fields in which the player can write his/her name */
		//make the panel
		JPanel inputFields = new JPanel();
		inputFields.setLayout(new GridLayout(2,2));
		inputFields.setBorder(BorderFactory.createEmptyBorder(32,32,32,32));

		//settings of the components
		inputCommander.addCaretListener(new textfieldListener());
		inputShip.addCaretListener(new textfieldListener());

		//build the panel and return result
		inputFields.add(commander);
		inputFields.add(inputCommander);
		inputFields.add(ship);
		inputFields.add(inputShip);
		return inputFields;
	}

	public JPanel makeButtons() {
		/** This method makes the buttons to execute commands */
		//make the panel
		JPanel buttonPanel = new JPanel();

		//set properties of the elements
		save.setEnabled(false);
		save.addActionListener(new buttonListener());

		//build the panel and return
		buttonPanel.add(save);
		return buttonPanel;
	}

	public void updateButton() {
		/** This method updates the button */
		if (playerName.isEmpty() || playerShip.isEmpty()) {
			save.setEnabled(false);
		} else if (!playerName.isEmpty() && !playerShip.isEmpty()) {
			save.setEnabled(true);
		}
	}

	//class for the caret listener and action listener
	private class textfieldListener implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent e) {
			//commander name
			String input1 = inputCommander.getText();
			input1.trim();
			setPlayerName(input1);

			//ship name
			String input2 = inputShip.getText();
			input2.trim();
			setPlayerShip(input2);

			//update the button
			updateButton();
		}
	}

	private class buttonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent f) {
			//write the highscore away
			HighScores scoreFile = new HighScores("src/Highscores.txt");
			scoreFile.writeScore(currentscore, playerShip, playerName);

			//dispose of the popup
			setBoolean(true);
			dispose();
		}
	}

	//setter
	public void setContent(String content) {
		this.content = content;
	}

	public void setPlayerName(String name) {
		this.playerName = name;
	}

	public void setPlayerShip(String ship) {
		this.playerShip = ship;
	}

	public void setCurrentScore(double currentscore) {
		this.currentscore = currentscore;
	}

	public void setBoolean(boolean value) {
		this.done = value;
	}

	//getter
	public String getContent() {
		return this.content;
	}

	public boolean getBoolean() {
		return this.done;
	}
}
