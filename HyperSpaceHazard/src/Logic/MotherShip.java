package Logic;

public class MotherShip implements Fightable {
	private double hitpoints;
	private double defense;
	private double offense;

	//constructor
	public MotherShip(double hitpoints, double defense, double offense) {
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
