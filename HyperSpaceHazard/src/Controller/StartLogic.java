package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import View.*;

public class StartLogic implements ActionListener, ChangeListener {

	private GameController gc;
	private String difficulty = null;
	private int skillset = 50;

	//constructor
	public StartLogic(GameController gc, String initialDifficulty, int initialSkills) {
		this.setGameController(gc);
	}

	//change listener
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int pct = (int)source.getValue();
			this.setSkillset(pct);
		}
	}

	//action listener
	@Override
	public void actionPerformed(ActionEvent f) {
		/** This method records the difficulty setting and the start, exit and highscores button */
		String keyCommand = f.getActionCommand();

		//react to hitting the difficulty setting keys
		if (keyCommand.equals("easy") || keyCommand.equals("normal") || keyCommand.equals("hard")) {
			this.setDifficulty(keyCommand);
		}

		//react to hitting the highscores key
		if (keyCommand.equals("highscores")) {
			@SuppressWarnings("unused")
			HighscoreScreen scores = new HighscoreScreen(this.gc.getStartScreen(), "Current all-time highscores");
		}

		//react to hitting the exit key
		if (keyCommand.equals("exit")) {
			System.exit(0);
		}

		//react to hitting the start key
		if ((keyCommand.equals("start")) && (this.getDifficulty() != null)) {
			this.gc.showGame();
		} else if ((keyCommand.equals("start")) && (this.getDifficulty() == null)) {
			String warningString = "Please select a difficulty";
			@SuppressWarnings("unused")
			GenericPopUp warning = new GenericPopUp(this.gc.getStartScreen(), "warning", warningString);
		}
	}

	//setter
	public void setGameController(GameController gc) {
		this.gc = gc;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public void setSkillset(int skillset) {
		this.skillset = skillset;
	}

	//getter
	public GameController getGameController() {
		return this.gc;
	}

	public String getDifficulty() {
		return this.difficulty;
	}

	public int getSkillset() {
		return this.skillset;
	}
}
