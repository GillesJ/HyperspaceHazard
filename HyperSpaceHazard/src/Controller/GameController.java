package Controller;

import Logic.Galaxy;
import View.*;

public class GameController {

	private StartScreen start;
	private GameScreen game;
	private StartLogic sl;
	private GameLogic gl;
	private EndGameLogic endl;

	//constructor
	public GameController() {
		//initiate the listeners
		sl = new StartLogic(this, "not selected", 50);
		gl = new GameLogic(this, null, sl.getSkillset());
		endl = new EndGameLogic(this);

		//initiate the gui
		start = new StartScreen("Hyperspace Hazard - START", sl, sl);
		game = new GameScreen("Hyperspace Hazard", 16, 32, gl, endl);

		//set the visibility
		start.setVisible(true);
		game.setVisible(false);
	}

	//methods	
	public void showGame() {
		/** This method regulates switching from the naming screen to the actual game screen */
		//transfer information from startscreen to gamelogic
		gl.updateSettings(sl.getDifficulty(), sl.getSkillset());

		//regulate the visibility of the screens
		start.setVisible(false);
		game.setVisible(true);
	}

	public void showStart() {
		/** This method regulates switching from the game screen to the start screen */
		//reset the startscreen to its initial parameters
		sl.setDifficulty(null);
		sl.setSkillset(50);

		//reset the game screen
		resetGameBoard();

		//regulate the visibility of the screens
		start.setVisible(true);
		game.setVisible(false);
	}

	public void updateGameBoard(Galaxy galaxyBoard) {
		/** This method takes the updated board from the game logic and passes it on to the Game Screen */
		game.updateGameBoard(galaxyBoard);
	}

	public void updateStatsPanel(double[] stats) {
		/** This method takes the updated stats from the game logic and passes them on to the Game Screen */
		game.updateStatsPanel(stats);
	}

	public void resetGameBoard() {
		/** This method resets the whole game board and the stats */
		game.resetGameAndStats();
	}

	//getter
	public StartScreen getStartScreen() {
		return this.start;
	}

	public GameScreen getGameScreen() {
		return this.game;
	}
}
