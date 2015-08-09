package Logic;

import java.util.Random;

public class Galaxy {

	private GalacticObject[][] galaxyArray;
	private Integer row;
	private Integer col;
	private String difficulty = null;

	//constructor
	public Galaxy(int rows, int columns, String difficulty) {
		this.setRows(rows);
		this.setColumns(columns);
		this.setDifficulty(difficulty);
	}

	//methods
	public void setGalaxy() {
		/** This method builds the galaxy according to a given difficulty */
		galaxyArray = new GalacticObject[this.row][this.col];

		//difficulty
		int level = initiateLevel();

		//construct the array
		Random rand = new Random();
		int random;
		for (int y = 0; y < this.row; y++) {
			for (int x = 0; x < this.col; x++) {
				GalacticObject obj = new GalacticObject(y,x);
				random = rand.nextInt(100);
				if (random < 5) {
					//asteroid (5 percent)
					obj.makeAsteroid();
				} else if (random < 8) {
					//asteroidfield (3 percent)
					obj.makeAsteroidField();
				} else if (random < 9) {
					//blackhole (1 percent)
					obj.makeBlackHole();
				} else if (random < 14) {
					//moon (5 percent)
					obj.makeMoon();
				} else if (random < 19) {
					//planet (5 percent)
					obj.makePlanet();
				} else if (random < 20) {
					//star (1 percent)
					obj.makeStar();
				} else if (random < 25) {
					//enemyship (5 percent)
					obj.makeEnemyShip(level);
				} else {
					//rest is open (= empty)
					obj.makeOpen();
				}
				galaxyArray[y][x] = obj;
			}
		}
	}

	public void adaptGalaxy(String type, int r, int c) {
		/** This method allows for adaptation of the board */
		//make the new object
		GalacticObject obj = new GalacticObject(r,c);
		obj = this.getGalacticObject(r,c);
		switch (type) {
		case "asteroid":
			obj.makeAsteroid();
			break;
		case "asteroidfield":
			obj.makeAsteroidField();
			break;
		case "blackhole":
			obj.makeBlackHole();
			break;
		case "moon":
			obj.makeMoon();
			break;
		case "planet":
			obj.makePlanet();
			break;
		case "star":
			obj.makeStar();
			break;
		case "mothership":
			obj.makeMotherShip();
			break;
		case "player":
			obj.makePlayer();
			break;
		case "marked":
			obj.setMarked(true);
			break;
		case "open":
			obj.makeOpen();
			break;
		case "visible":
			obj.makeVisible(true);
			break;
		default:
			obj.makeOpen();
		}

		//add the object to the board
		galaxyArray[r][c] = obj;
	}

	public void makeGalaxyVisible(int r, int c) {
		/** This method takes the current coordinates of the player and makes the objects around visible */
		//define the radius (surrounding coordinates)
		int r_low = r - 2;
		int r_high = r + 3;
		int c_low = c - 2;
		int c_high = c + 3;

		//reset all to invisible
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getColumns(); j++) {
				GalacticObject temporary = this.getGalacticObject(i, j);
				temporary.makeVisible(false);
			}
		}

		//set the objects around the player visible
		for (int i = r_low; i < r_high; i++) {
			for (int j = c_low; j < c_high; j++) {
				if ((this.goodLocation(i, j)) && !(((i == r_low) || (i == r_high - 1)) && ((j == c_low) || (j == c_high - 1)))) {
					//extract the galacticObject
					GalacticObject temporary = this.getGalacticObject(i, j);
					temporary.makeVisible(true);
				}
			}
		}
	}

	public int initiateLevel() {
		/** This method sets the level according to the difficulty */
		int level = 1;
		switch (this.difficulty) {
		case "easy":
			level = 1;
			break;
		case "normal":
			level = 2;
			break;
		case "hard":
			level = 3;
			break;
		}
		return level;
	}

	public GalacticObject getGalacticObject(int r,int c){
		/** This method returns the galactic object at location (r,c) */
		if (goodLocation(r,c)) {
			return galaxyArray[r][c];
		} else {
			return null;
		}
	}

	public boolean goodLocation(int r, int c) {
		/** This method returns true when the location is inside the borders of the board */
		if ((r < this.row) && (c < this.col) && (r > -1) && (c > -1)) {
			return true;
		} else {
			return false;
		}
	}


	//setter
	public void setRows(int rows) {
		this.row = rows;
	}

	public void setColumns(int columns) {
		this.col = columns;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	//getter
	public int getRows() {
		return this.row;
	}

	public int getColumns() {
		return this.col;
	}
}
