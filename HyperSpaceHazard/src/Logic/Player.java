package Logic;

public class Player implements Fightable, Changeable {

	private double hitpoints;
	private double defense;
	private double offense;
	private double fuel;
	private String ship;
	private String commander;
	private int row;
	private int col;

	//constructor
	public Player(String difficulty, int skillset) {
		initializePlayer(difficulty, skillset);
	}

	//methods
	public void initializePlayer(String difficulty, int skillset) {
		/** This method initializes all the player attributes when given a certain difficulty and skillset */
		//make the variables
		double skillpoints = 0.0;
		double def = 0.0;
		double off = 0.0;

		//initialization logic for hp and fuel
		switch (difficulty) {
		case "easy":
			this.setHitpoints(200);
			this.setFuel(100);
			skillpoints = 20;
			break;
		case "normal":
			this.setHitpoints(150);
			this.setFuel(90);
			skillpoints = 15;
			break;
		case "hard":
			this.setHitpoints(100);
			this.setFuel(80);
			skillpoints = 10;
			break;
		}

		//initialization logic for offense and defense		
		double a = (double)(100 - skillset)/100;
		double b = 1 - a;
		def = a * skillpoints;
		off = b * skillpoints;
		this.setDefense(def);
		this.setOffense(off);
	}

	public double doesDamage() {
		/** this method returns the attack damage of the ship */
		return this.getOffense();
	}

	public void takesDamage(double attack) {
		/** this method resets the hitpoints of the ship after an enemy attack */
		double attackDamage;
		if (this.getDefense() >= attack) {
			attackDamage = 0;
		} else {
			attackDamage = attack - this.getDefense();
		}
		double result = this.hitpoints - attackDamage;
		if (result >= 0) {
			this.setHitpoints(result);
		} else {
			this.setHitpoints(0);
		}
	}

	public void increaseOffense(double experience) {
		/** this method increases the offensive capabilities of the player with given experience points */
		double increase;
		if (experience >= 0) {
			increase = this.getOffense() + experience;
			this.setOffense(increase);
		}
	}

	public void increaseDefense(double experience) {
		/** this method increases the defensive capabilities of the player with given experience points */
		double increase;
		if (experience >= 0) {
			increase = this.getDefense() + experience;
			this.setDefense(increase);
		}
	}

	public void decreaseDefense(double damage) {
		/** this method decreases the defensive capabilities of the player with given damage (e.g. due to star) */
		double decrease;
		if (damage < 0) {
			decrease = 0;
		} else {
			decrease = this.getDefense() - damage;
		}
		if (decrease < 0) {
			this.setDefense(0);
		} else {
			this.setDefense(decrease);
		}
	}

	public void increaseFuel(double fuel) {
		/** this method increases the fuel level of the player with a certain specified amount */
		double increase;
		if (fuel >= 0) {
			increase = this.getFuel() + fuel;
			this.setFuel(increase);
		}
	}

	public void decreaseFuel(double fuel) {
		/** this method reduces the fuel level of the player with a certain specified amount (moving/black hole) */
		double decrease;
		if (fuel < 0) {
			decrease = 0;
		} else {
			decrease = this.getFuel() - fuel;
		}
		if (decrease < 0) {
			this.setFuel(0);
		} else {
			this.setFuel(decrease);
		}
	}

	public void setCoordinates(int row, int col) {
		/** This method sets the coordinates for the player */
		this.setRowCoordinate(row);
		this.setColCoordinate(col);
	}

	//setter
	public void setHitpoints(double hitpoints) {
		this.hitpoints = hitpoints;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public void setOffense(double offense) {
		this.offense = offense;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public void setNameShip(String ship) {
		this.ship = ship;
	}

	public void setNameCommander(String commander) {
		this.commander = commander;
	}

	public void setRowCoordinate(int row) {
		this.row = row;
	}

	public void setColCoordinate(int col) {
		this.col = col;
	}

	//getter
	public double getHitpoints() {
		return this.hitpoints;
	}

	public double getDefense() {
		return this.defense;
	}

	public double getOffense() {
		return this.offense;
	}

	public double getFuel() {
		return this.fuel;
	}

	public String getNameShip() {
		return this.ship;
	}

	public String getNameCommander() {
		return this.commander;
	}

	public int getRowCoordinate() {
		return this.row;
	}

	public int getColCoordinate() {
		return this.col;
	}
}
