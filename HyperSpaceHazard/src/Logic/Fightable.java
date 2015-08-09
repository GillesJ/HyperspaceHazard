package Logic;

/** The Fightable interface defines some methods that are applicable on all the opponent tiles */

public interface Fightable {
	/** The doesDamage method defines that the opponent tile can lowers the hitpoints of the player */
	public double doesDamage();

	/** The takesDamage method defines that the opponent tile can take damage and lowers its hitpoints after an enemy attack,
	it doesn't have any return type */
	public void takesDamage(double attack);
}