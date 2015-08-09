package Logic;

public class AsteroidField {
	private double damage;

	//constructor
	public AsteroidField(double damage) {
		this.setDamage(damage);
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
