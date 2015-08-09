package Logic;

public class Star implements Lowersdef{
	private double damage;

	//constructor
	public Star(double damage) {
		this.setDamage(damage);
	}

	//methods
	public double lowersDefense() {
		double result = this.getDamage();
		return result;
	}

	//setter
	public void setDamage(double damage) {
		this.damage = damage;
	}

	//getter
	public double getDamage() {
		return this.damage;
	}
}
