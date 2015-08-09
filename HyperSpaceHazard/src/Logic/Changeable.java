package Logic;

/** The Increasable interface defines some methods that are applicable on all the player tiles */

public interface Changeable {
	/** The increaseOffense method indicates that the offensive capabilities of the player can be increased*/
	public void increaseOffense(double experience);

	/** The increaseDefense method indicates that the defensive capabilities of the player can be increased*/
	public void increaseDefense(double experience);

	/** The decreaseDefense method indicates that the defensive capabilities of the player can be increased*/
	public void decreaseDefense(double damage);

	/** The increaseFuel method indicates that the fuel level of the player can be increased*/
	public void increaseFuel(double fuel);

	/** The decreaseFuel method indicates that the fuel level of the player can be decreased*/
	public void decreaseFuel(double fuel);
}