package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Logic.*;
import View.EndGameScreen;
import View.GenericPopUp;

public class GameLogic implements ActionListener {

	//class variables
	private GameController gc;
	private String difficulty = null;
	private int skillset;
	private int killcount;
	private int fleecount;

	//game elements and the galaxy
	private Player player;
	private Asteroid haley;
	private AsteroidField cuyperbelt;
	private BlackHole gargantua;
	private Moon titan;
	private Planet jupiter;
	private Star sirius;
	private MotherShip deathstar;
	private EnemyShip level1;
	private EnemyShip level2;
	private EnemyShip level3;

	private Galaxy galaxyBoard;

	//constructor
	public GameLogic(GameController gc, String difficulty, int skillset) {
		this.setGameController(gc);
		this.setDifficulty(difficulty);
		this.setSkillset(skillset);
	}

	//initalization methods
	public void updateSettings(String difficulty, int skillset) {
		/** This method updates the difficulty and the skillset after they have been chosen in the start screen
		 * Additionally, it sets the stats of the player and makes the gameboard */
		//initalize player and galaxy and game elements
		this.setDifficulty(difficulty);
		this.setSkillset(skillset);
		initializeGalaxy();
		initializePlayer();
		initializeGameElements();
		setPositions();

		//perform the updates
		updateStatsPanel(0);
		updateGameBoard();
	}

	private void initializeGalaxy() {
		/** This method initalizes the galaxy */
		galaxyBoard = new Galaxy(16, 32, difficulty);
		galaxyBoard.setGalaxy();
	}

	private void initializePlayer() {
		/** This method initializes the player */
		player = new Player(this.difficulty, this.skillset);
	}

	private void initializeGameElements() {
		/** This method initializes all the other elements of the game */
		//all these numbers are up for rebalancing if necessary
		haley = new Asteroid(10.0, 10.0);
		cuyperbelt = new AsteroidField(5.0);
		gargantua = new BlackHole(25.0);
		titan = new Moon(15.0, 15.0);
		jupiter = new Planet(20.0, 20.0);
		sirius = new Star(20.0);
		deathstar = new MotherShip(200.0, 20.0, 20.0);
		level1 = new EnemyShip(40.0, 5.0, 5.0);
		level2 = new EnemyShip(60.0, 10.0, 10.0);
		level3 = new EnemyShip(80.0, 15.0, 15.0);
	}

	public void setPositions() {
		/// This method initializes the position of the player and the position of the enemy mothership */
		//adapt the player class (care: the board counts from zero)
		int row = 2;
		int col = 2;
		player.setCoordinates(row, col);

		//adapt the galaxy board to reflext positions
		galaxyBoard.adaptGalaxy("player", row, col);
		galaxyBoard.adaptGalaxy("mothership", 14, 30);
		galaxyBoard.makeGalaxyVisible(row, col);
	}

	//game control methods
	public void checkGame() {
		// This methods constantly checks whether the game is lost or not */
		//check whether the game is lost or not
		boolean lost = false;
		boolean won = false;
		if ((player.getHitpoints() <= 0.0) || (player.getFuel() <= 0.0)) {
			lost = true;
		} else if (deathstar.getHitpoints() <= 0.0) {
			won = true;
		}

		//act appropriately
		if (lost && !won) {
			gameOver();
		} else if (won && !lost) {
			gameWon();
		}
	}

	public void gameOver() {
		// This method acts accordingly to a game over
		//Generate end-of-game screen
		String message = "Space has claimed another life. Better luck next time!";
		double endscore = calculateScore(killcount);
		EndGameScreen endscreen = new EndGameScreen(this.gc.getGameScreen(), "Lost the game", message, endscore);

		//end the game, reset the board
		if (endscreen.getBoolean()) {
			endscreen.dispose();
			this.gc.showStart();
		}
	}

	public void gameWon() {
		// This method acts accordingly to a won game
		//Generate end-of-game screen
		String message = "Congratulations, you have defeaten the enemy fleet!";
		double endscore = calculateScore(killcount);
		EndGameScreen endscreen = new EndGameScreen(this.gc.getGameScreen(), "Won the game", message, endscore);

		//end the game, reset the board
		if (endscreen.getBoolean()) {
			endscreen.dispose();
			this.gc.showStart();
		}
	}

	//action listener
	@Override
	public void actionPerformed(ActionEvent e) {
		/** This method listens to the game and does all the necessary calculations */
		//extract the necessary information from the board
		String keyCommand = e.getActionCommand();
		String[] coordinates = keyCommand.split(":");
		int r_new = Integer.parseInt(coordinates[0]);
		int c_new = Integer.parseInt(coordinates[1]);
		GalacticObject temporary = galaxyBoard.getGalacticObject(r_new, c_new);

		//other information
		int r_old = player.getRowCoordinate();
		int c_old = player.getColCoordinate();
		boolean reachable = placeIsReachable(r_new, c_new, r_old, c_old);

		int[] surround = checkSurroundings(r_new, c_new);
		int asteroidfields = surround[0];
		int blackholes = surround[1];
		int stars = surround[2];

		Object[] mining = {"Experience","Fuel"};
		Object[] skillpoints = {"Offense","Defense"};

		killcount = 0;
		fleecount = 0;

		//place clicked is not reachable
		if (!reachable) {
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "You will have to travel closer first to reach this sector!");
		}

		//when player clicks on empty space
		if ((temporary.isOpen()) && (reachable)) {
			player.setCoordinates(r_new, c_new);
			player.decreaseFuel(2.0);
			galaxyBoard.adaptGalaxy("player", r_new, c_new);
			galaxyBoard.adaptGalaxy("open", r_old, c_old);
			galaxyBoard.makeGalaxyVisible(r_new, c_new);
		}		

		//when player clicks on asteroid
		if (temporary.isAsteroid() && !temporary.isMarked() && reachable) {
			double xp = haley.minesXP();
			double fl = haley.minesFuel();

			//mine for xp or fuel
			int ans1 = JOptionPane.showOptionDialog(null, "Do you want to mine the asteroid for experience or fuel?", "Mining", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, mining, mining[0]);
			if (ans1 == 0) {
				//if mined for experience check whether spent on defense or offense
				int ans2 = JOptionPane.showOptionDialog(null, "Spend the experience on offense or defense?", "Level up", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, skillpoints, skillpoints[0]);
				if (ans2 == 0) {
					//increase the offense
					player.increaseOffense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				} else if (ans2 == 1) {
					//increase the defense
					player.increaseDefense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				}
			} else if (ans1 == 1) {
				//increase the fuel
				player.increaseFuel(fl);
				galaxyBoard.adaptGalaxy("marked", r_new, c_new);
			}
		} else if (temporary.isAsteroid() && temporary.isMarked() && reachable) {
			//cannot move to this location
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "This asteroid is already mined!");
		}

		//when player clicks on moon
		if (temporary.isMoon() && !temporary.isMarked() && reachable) {
			double xp = titan.minesXP();
			double fl = titan.minesFuel();

			//mine for xp or fuel
			int ans1 = JOptionPane.showOptionDialog(null, "Do you want to mine the moon for experience or fuel?", "Mining", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, mining, mining[0]);
			if (ans1 == 0) {
				//if mined for experience check whether spent on defense or offense
				int ans2 = JOptionPane.showOptionDialog(null, "Spend experience on offense or defense?", "Level up", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, skillpoints, skillpoints[0]);
				if (ans2 == 0) {
					//increase the offense
					player.increaseOffense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				} else if (ans2 == 1) {
					//increase the defense
					player.increaseDefense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				}
			} else if (ans1 == 1) {
				//increase the fuel
				player.increaseFuel(fl);
				galaxyBoard.adaptGalaxy("marked", r_new, c_new);
			}
		} else if (temporary.isMoon() && temporary.isMarked() && reachable) {
			//cannot move to this location
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "This moon is already mined!");
		}

		//when player clicks on planet
		if (temporary.isPlanet() && !temporary.isMarked() && reachable) {
			double xp = jupiter.minesXP();
			double fl = jupiter.minesFuel();

			//mine for xp or fuel
			int ans1 = JOptionPane.showOptionDialog(null, "Do you want to mine the planet for experience or fuel?", "Mining", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, mining, mining[0]);
			if (ans1 == 0) {
				//if mined for experience check whether spent on defense or offense
				int ans2 = JOptionPane.showOptionDialog(null, "Spend experience on offense or defense?", "Level up", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, skillpoints, skillpoints[0]);
				if (ans2 == 0) {
					//increase the offense
					player.increaseOffense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				} else if (ans2 == 1) {
					//increase the defense
					player.increaseDefense(xp);
					galaxyBoard.adaptGalaxy("marked", r_new, c_new);
				}
			} else if (ans1 == 1) {
				//increase the fuel
				player.increaseFuel(fl);
				galaxyBoard.adaptGalaxy("marked", r_new, c_new);
			}
		} else if (temporary.isPlanet() && temporary.isMarked() && reachable) {
			//cannot move to this location
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "This planet is already mined!");
		}

		//when player clicks on asteroid field or is near one
		if (temporary.isAsteroidField() && reachable) {
			double damage = cuyperbelt.getDamage();
			player.decreaseDefense(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "You are flying into an asteroid field! Your shields are sustaining damage!");
		}

		if ((asteroidfields > 0) && reachable) {
			//player is near an asteroid field and loses some defense
			double damage = cuyperbelt.getDamage();
			player.decreaseDefense(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "You are nearing an asteroid field, your shields are sustaining damage!");
		}

		//when player clicks on a star
		if (temporary.isStar() && reachable) {
			double damage = sirius.getDamage();
			player.decreaseDefense(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Captain, we are flying into a star! We will get torn apart by radiation!");
		}

		if ((stars > 0) && reachable) {
			//player is near an asteroid field and loses some defense
			double damage = sirius.getDamage();
			player.decreaseDefense(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "You're nearing a star, your shields are sustaining damage due to radiation!");
		}

		//when player clicks on black hole
		if (temporary.isBlackHole() && reachable) {
			double damage = gargantua.getFuelReduction();
			player.decreaseFuel(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Captain! We are closing in on a black hole, burn fuel to escape its gravitational pull!");
		}

		if ((blackholes > 0) && reachable) {
			//player is near an asteroid field and loses some defense
			double damage = gargantua.getFuelReduction();
			player.decreaseFuel(damage);
			@SuppressWarnings("unused")
			GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "You're nearing a blackhole, you're burning fuel to escape its gravitational pull!");
		}

		//when player clicks on enemy ship level 1
		if (temporary.isShipLevel1() && reachable) {
			double enemyDamage = level1.doesDamage();
			double playerDamage = player.doesDamage();

			//do you want to attack?
			int ans1 = JOptionPane.showConfirmDialog(null, "Enemy ship! Attack?", "Enemy fight", JOptionPane.YES_NO_OPTION);
			if (ans1 == JOptionPane.YES_OPTION) {
				//fight with the enemy
				boolean flee = false;
				boolean defeat = false;
				while (!flee && !defeat) {
					//damage is dealt
					level1.takesDamage(playerDamage);
					player.takesDamage(enemyDamage);
					updateStatsPanel(killcount);

					//message
					String message = "You attacked the enemy ship for " + playerDamage + "! This reduced its health to " + level1.getHitpoints() + "! Alas, you were attacked for " + enemyDamage + " damage!";
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Enemy fight", message);

					//are you or the enemy defeaten?
					if ((player.getHitpoints() <= 0.0) || (level1.getHitpoints() <= 0.0)) {
						defeat = true;
						flee = false;
					} else {
						//do you want to flee?
						int ans2 = JOptionPane.showConfirmDialog(null, "Continue the attack? Fleeing will give the enemy the chance to regain its strength!", "Enemy fight", JOptionPane.YES_NO_OPTION);
						if (ans2 == JOptionPane.YES_OPTION) {
							flee = false;
						} else {
							flee = true;
						}
						defeat = false;
					}
				}
				//adapt the galaxy, reset the ship
				if (level1.getHitpoints() <= 0.0) {
					galaxyBoard.adaptGalaxy("open", r_new, c_new);
				}
				level1.setHitpoints(40);
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Success", "An enemy has been defeaten!");
			} else if (ans1 == JOptionPane.NO_OPTION) {
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Succesfully fled!");
			}
		}

		//when player clicks on enemy ship level 2
		if (temporary.isShipLevel2() && reachable) {
			double enemyDamage = level2.doesDamage();
			double playerDamage = player.doesDamage();

			//do you want to attack?
			int ans1 = JOptionPane.showConfirmDialog(null, "Enemy ship! Attack?", "Enemy fight", JOptionPane.YES_NO_OPTION);
			if (ans1 == JOptionPane.YES_OPTION) {
				//fight with the enemy
				boolean flee = false;
				boolean defeat = false;
				while (!flee && !defeat) {
					//damage is dealt
					level2.takesDamage(playerDamage);
					player.takesDamage(enemyDamage);
					updateStatsPanel(killcount);

					//message
					String message = "You attacked the enemy ship for " + playerDamage + "! This reduced its health to " + level1.getHitpoints() + "! Alas, you were attacked for " + enemyDamage + " damage!";
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Enemy fight", message);

					//are you or the enemy defeaten?
					if ((player.getHitpoints() <= 0.0) || (level2.getHitpoints() <= 0.0)) {
						defeat = true;
						flee = false;
					} else {
						//do you want to flee?
						int ans2 = JOptionPane.showConfirmDialog(null, "Continue the attack? Fleeing will give the enemy the chance to regain its strength!", "Enemy fight", JOptionPane.YES_NO_OPTION);
						if (ans2 == JOptionPane.YES_OPTION) {
							flee = false;
						} else {
							flee = true;
						}
						defeat = false;
					}
				}
				//adapt the galaxy, reset the ship
				if (level2.getHitpoints() <= 0.0) {
					galaxyBoard.adaptGalaxy("open", r_new, c_new);
				}
				level2.setHitpoints(60);
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Success", "An enemy has been defeaten!");
			} else if (ans1 == JOptionPane.NO_OPTION) {
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Succesfully fled!");
			}
		}

		//when player clicks on enemy ship level 3
		if (temporary.isShipLevel3() && reachable) {
			double enemyDamage = level3.doesDamage();
			double playerDamage = player.doesDamage();

			//do you want to attack?
			int ans1 = JOptionPane.showConfirmDialog(null, "Enemy ship! Attack?", "Enemy fight", JOptionPane.YES_NO_OPTION);
			if (ans1 == JOptionPane.YES_OPTION) {
				//fight with the enemy
				boolean flee = false;
				boolean defeat = false;
				while (!flee && !defeat) {
					//damage is dealt
					level3.takesDamage(playerDamage);
					player.takesDamage(enemyDamage);
					updateStatsPanel(killcount);

					//message
					String message = "You attacked the enemy ship for " + playerDamage + "! This reduced its health to " + level1.getHitpoints() + "! Alas, you were attacked for " + enemyDamage + " damage!";
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Enemy fight", message);

					//are you or the enemy defeated?
					if ((player.getHitpoints() <= 0.0) || (level3.getHitpoints() <= 0.0)) {
						defeat = true;
						flee = false;
					} else {
						//do you want to attack or flee?
						int ans2 = JOptionPane.showConfirmDialog(null, "Continue the attack? Fleeing will give the enemy the chance to regain its strength!", "Enemy fight", JOptionPane.YES_NO_OPTION);
						if (ans2 == JOptionPane.YES_OPTION) {
							flee = false;
						} else {
							flee = true;
						}
						defeat = false;
					}
				}
				//adapt the galaxy, reset the ship
				if (level3.getHitpoints() <= 0.0) {
					galaxyBoard.adaptGalaxy("open", r_new, c_new);
				}
				level3.setHitpoints(60);
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Success", "You defeated the enemy ship!");
			} else if (ans1 == JOptionPane.NO_OPTION) {
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Succesfully fled!");
			}
		}

		//when player clicks on mother ship
		if (temporary.isMotherShip() && !temporary.isMarked() && reachable) {
			//first encounter with the mother ship
			int ans1 = JOptionPane.showConfirmDialog(null, "The enemy mothership! Attack?", "Boss fight", JOptionPane.YES_NO_OPTION);
			if ((ans1 == JOptionPane.YES_OPTION) || ((ans1 == JOptionPane.NO_OPTION) && (fleecount >= 1))) {
				//attack the mother ship or get caught by it
				if ((fleecount >= 1) && (ans1 == JOptionPane.NO_OPTION)) {
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Boss fight", "You can't flee anymore, the mothership caught you!");
				} else {
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Boss fight", "You are attacking the mothership! Fight well!");
				}
				boolean flee = false;
				boolean defeat = false;
				while (!flee && !defeat) {
					double enemyDamage = deathstar.doesDamage();
					double playerDamage = player.doesDamage();

					//damage is dealt
					deathstar.takesDamage(playerDamage);
					player.takesDamage(enemyDamage);
					updateStatsPanel(killcount);

					//message
					String message = "You attacked the mother ship for " + playerDamage + "! This reduced its health to " + deathstar.getHitpoints() + "! Alas, you were attacked for " + enemyDamage + " damage!";
					@SuppressWarnings("unused")
					GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Boss fight", message);

					//are you or the enemy defeaten?
					if ((player.getHitpoints() <= 0.0) || (deathstar.getHitpoints() <= 0.0)) {
						defeat = true;
						flee = false;
					} else {
						//do you want to flee?
						int ans2 = JOptionPane.showConfirmDialog(null, "Continue the attack?", "Boss fight", JOptionPane.YES_NO_OPTION);
						if (ans2 == JOptionPane.YES_OPTION) {
							flee = false;
						} else {
							flee = true;
							fleecount++;
						}
						defeat = false;
					}
				}				
			} else if (ans1 == JOptionPane.YES_OPTION && (fleecount < 1)) {
				//flee from the mother ship (succeeds only once)
				@SuppressWarnings("unused")
				GenericPopUp popup = new GenericPopUp(this.gc.getGameScreen(), "Warning", "Succesfully fled! Be carefull though, you can't always run!");
				fleecount++;
			}
		}

		//check the status of the game and update the board
		updateStatsPanel(killcount);
		updateGameBoard();
		checkGame();
	}

	//update methods
	public void updateGameBoard() {
		/** This method updates the game board on the game screen */
		this.gc.updateGameBoard(galaxyBoard);
	}

	public void updateStatsPanel(int killcount) {
		/** This method updates the stats panel of the player */
		double[] stats = new double[6];
		stats[0] = calculateScore(killcount);
		stats[1] = player.getHitpoints();
		stats[2] = player.getFuel();
		stats[3] = player.getOffense();
		stats[4] = player.getDefense();
		stats[5] = player.getOffense() + player.getDefense();
		this.gc.updateStatsPanel(stats);
	}

	//aid methods
	public double calculateScore(int killcount) {
		/** This method calculates the score so far and returns the result */
		double score = 0.0;
		score = (player.getFuel() + player.getDefense() + player.getOffense()) * (1 + (killcount / 10));
		return score;
	}

	public int[] checkSurroundings(int r, int c) {
		/** This method checks whether the player is in the neighbourhood of a dangerous galactic object */
		//surrounding coordinates
		int r_low = r - 1;
		int r_high = r + 2;
		int c_low = c - 1;
		int c_high = c + 2;

		//danger count
		int asteroidfields = 0;
		int blackholes = 0;
		int stars = 0;

		for (int i = r_low; i < r_high; i++) {
			for (int j = c_low; j < c_high; j++) {
				if (galaxyBoard.goodLocation(i, j)) {
					//extract the galacticObject
					GalacticObject temporary = galaxyBoard.getGalacticObject(i, j);

					//check this object
					if (temporary.isAsteroidField()) {
						asteroidfields++;
					}

					if (temporary.isBlackHole()) {
						blackholes++;
					}

					if (temporary.isStar()) {
						stars++;
					}
				}
			}
		}

		//build and return result
		int[] surround = new int[3];
		surround[0] = asteroidfields;
		surround[1] = blackholes;
		surround[2] = stars;
		return surround;
	}

	public boolean placeIsReachable(int r_new, int c_new, int r_old, int c_old) {
		/** This method checks whether the player can reach a certain place */
		boolean reachable = false;
		int r_pos = (r_new - r_old);
		int c_pos = (c_new - c_old);
		if ((Math.abs(r_pos) <= 1) && (Math.abs(c_pos) <= 1)) {
			reachable = true;
		}

		//return result
		return reachable;
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
