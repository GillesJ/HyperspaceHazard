package Logic;

public class Asteroid implements Mineable {
	private double experience;
	private double fuel;

	//constructor
	public Asteroid(double experience, double fuel) {
		this.setExp(experience);
		this.setFuel(fuel);
	}

	//methods
	public double minesXP() {
		double result = this.getExp();
		return result;
	}
	public double minesFuel() {
		double result = this.getFuel();
		return result;
	}

	//setter
	public void setExp(double experience) {
		this.experience = experience;
	}
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	//getter
	public double getExp() {
		return this.experience;
	}
	public double getFuel() {
		return this.fuel;
	}
}
