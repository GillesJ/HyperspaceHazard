package Logic;

public class BlackHole implements Slowable {
	private double fuelreduction;

	//constructor
	public BlackHole(double fuelreduction) {
		this.setFuelReduction(fuelreduction);
	}

	//methods
	public double reducesFuel() {
		double result = this.getFuelReduction();
		return result;
	}

	//setter
	public void setFuelReduction(double fuelreduction) {
		this.fuelreduction = fuelreduction;
	}

	//getter
	public double getFuelReduction() {
		return this.fuelreduction;
	}
}
