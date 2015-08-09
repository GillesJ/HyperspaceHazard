package Logic;

/** The Mineable interface defines some methods that are applicable on all terrain tiles that can be mined for resources */

public interface Mineable {
	/** The minesXP method defines that the terrain tile can be mined for experience */
	public double minesXP();

	/** The minesFuel method defines that the terrain tile can be mined for fuel */
	public double minesFuel();
}
