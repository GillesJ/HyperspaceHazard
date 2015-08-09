package Logic;

public class EnemyShip implements Fightable, Mineable {
	private double hitpoints;
	private double defense;
	private double offense;

	//constructor
	public EnemyShip(double hitpoints, double defense, double offense) {
		this.setHitpoints(hitpoints);
		this.setDefense(defense);
		this.setOffense(offense);
	}

	//methods
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
	public double minesXP() {
		/** this method returns some experience (equal to the defense divided by 2) if the ship has been defeated */
		double experience = 0;
		if (this.hitpoints == 0) {
			experience = this.defense / 2;
		}
		return experience;
	}
	public double minesFuel() {
		/** this method returns some experience (equal to the defense divided by 2) if the ship has been defeated */
		double fuel = 0;
		if (this.hitpoints == 0) {
			fuel = this.defense / 2;
		}
		return fuel;
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
}
