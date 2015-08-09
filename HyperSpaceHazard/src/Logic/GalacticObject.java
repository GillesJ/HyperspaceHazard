package Logic;

import java.util.Random;

public class GalacticObject {

	//enumerate the values that a certain object in space can be
	public static enum SpaceObject {
		PLAYER,
		ASTEROID,
		ASTEROIDFIELD,
		BLACKHOLE,
		MOON,
		PLANET,
		STAR,
		MOTHERSHIP,
		SHIPLVL1,
		SHIPLVL2,
		SHIPLVL3,
		OPEN;
	}

	//all variables associated with an object in the galaxy
	SpaceObject spaceobject;
	private Integer row;
	private Integer column;
	private boolean marked;
	private boolean visible;

	//constructor (build a galactic object)
	public GalacticObject(int r, int c) {
		this.row = new Integer(r);
		this.column = new Integer(c);
		this.setMarked(false);	
		this.makeVisible(false);
		spaceobject = SpaceObject.OPEN;
	}

	//methods
	public void makeOpen() {
		/** Make this specific object open */
		this.spaceobject = SpaceObject.OPEN;
	}

	public void makePlayer() {
		/** Make this specific object a player */
		this.spaceobject = SpaceObject.PLAYER;
	}

	public void makeAsteroid() {
		/** Make this specific object an asteroid */
		this.spaceobject = SpaceObject.ASTEROID;
	}

	public void makeAsteroidField() {
		/** Make this specific object an asteroid field */
		this.spaceobject = SpaceObject.ASTEROIDFIELD;
	}

	public void makeBlackHole() {
		/** Make this specific object a black hole */
		this.spaceobject = SpaceObject.BLACKHOLE;
	}

	public void makeMoon() {
		/** Make this specific object a moon */
		this.spaceobject = SpaceObject.MOON;
	}

	public void makePlanet() {
		/** Make this specific object a planet */
		this.spaceobject = SpaceObject.PLANET;
	}

	public void makeStar() {
		/** Make this specific object a star */
		this.spaceobject = SpaceObject.STAR;
	}

	public void makeMotherShip() {
		/** Make this specific object an enemy mothership */
		this.spaceobject = SpaceObject.MOTHERSHIP;
	}

	public void makeEnemyShip(int lvl) {
		/** Make this specific object an enemy ship */
		//check the input
		int level;
		if (lvl < 0) {
			level = 0;
		} else if (lvl > 3) {
			level = 3;
		} else {
			level = lvl;
		}

		//assign a value to the spaceobject
		Random rand = new Random();
		int random = rand.nextInt(100);
		switch (level) {
		case 1:
			this.spaceobject = SpaceObject.SHIPLVL1;
			break;
		case 2:
			if (random < 50) {
				this.spaceobject = SpaceObject.SHIPLVL1;
			} else {
				this.spaceobject = SpaceObject.SHIPLVL2;
			}
			break;
		case 3:
			if (random < 33) {
				this.spaceobject = SpaceObject.SHIPLVL1;
			} else if (random < 66) {
				this.spaceobject = SpaceObject.SHIPLVL2;
			} else {
				this.spaceobject = SpaceObject.SHIPLVL3;
			}
			break;
		}
	}

	//setter

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public void makeVisible(boolean visible)  {
		this.visible = visible;
	}

	//getter
	public int getRow(){
		return this.row ; 
	}

	public int getColumn(){
		return this.column;
	}

	public boolean isMarked(){
		return this.marked;
	}

	public boolean isVisible(){
		return this.visible;
	}

	public boolean isOpen() {
		/** This method returns true when the object is open, else it returns false */
		if (this.spaceobject.equals(SpaceObject.OPEN)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPlayer() {
		/** This method returns true when the object is a player, else it returns false */
		if (this.spaceobject.equals(SpaceObject.PLAYER)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAsteroid() {
		/** This method returns true when the object is an asteroid, else it returns false */
		if (this.spaceobject.equals(SpaceObject.ASTEROID)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAsteroidField() {
		/** This method returns true when the object is an asteroid field, else it returns false */
		if (this.spaceobject.equals(SpaceObject.ASTEROIDFIELD)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBlackHole() {
		/** This method returns true when the object is a black hole, else it returns false */
		if (this.spaceobject.equals(SpaceObject.BLACKHOLE)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMoon() {
		/** This method returns true when the object is a moon, else it returns false */
		if (this.spaceobject.equals(SpaceObject.MOON)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPlanet() {
		/** This method returns true when the object is a planet, else it returns false */
		if (this.spaceobject.equals(SpaceObject.PLANET)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isStar() {
		/** This method returns true when the object is a star, else it returns false */
		if (this.spaceobject.equals(SpaceObject.STAR)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMotherShip() {
		/** This method returns true when the object is a player, else it returns false */
		if (this.spaceobject.equals(SpaceObject.MOTHERSHIP)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isShipLevel1() {
		/** This method returns true when the object is an enemy ship level 1, else it returns false */
		if (this.spaceobject.equals(SpaceObject.SHIPLVL1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isShipLevel2() {
		/** This method returns true when the object is an enemy ship level 2, else it returns false */
		if (this.spaceobject.equals(SpaceObject.SHIPLVL2)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isShipLevel3() {
		/** This method returns true when the object is an enemy ship level 3, else it returns false */
		if (this.spaceobject.equals(SpaceObject.SHIPLVL3)) {
			return true;
		} else {
			return false;
		}
	}
}
