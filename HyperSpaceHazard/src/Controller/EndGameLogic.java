package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.HighscoreScreen;

public class EndGameLogic implements ActionListener {

	private GameController gc;

	//constructor
	public EndGameLogic(GameController gc) {
		this.setGameController(gc);
	}

	//action listener
	@Override
	public void actionPerformed(ActionEvent e) {
		/** This method reacts to hitting the startmenu, highscores and exit button in the main game */
		String keyCommand = e.getActionCommand();

		//react to hitting the highscores key
		if (keyCommand.equals("highscores")) {
			@SuppressWarnings("unused")
			HighscoreScreen scores = new HighscoreScreen(this.gc.getGameScreen(), "Current all-time highscores");
		}

		//react to hitting the exit key
		if (keyCommand.equals("exit")) {
			System.exit(0);
		}

		//react to hitting the startmenu key
		if (keyCommand.equals("startmenu")) {
			this.gc.showStart();
		}
	}

	//setter
	public void setGameController(GameController gc) {
		this.gc = gc;
	}

	//getter
	public GameController getGameController() {
		return this.gc;
	}
}
