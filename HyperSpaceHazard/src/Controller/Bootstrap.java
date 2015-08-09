package Controller;

import javax.swing.SwingUtilities;

public class Bootstrap {

	//main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartGame();
			}
		});
	}

	//start the game method
	public static void StartGame() {
		@SuppressWarnings("unused")
		GameController newGame = new GameController();
	}
}