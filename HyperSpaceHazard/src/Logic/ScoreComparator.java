package Logic;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Integer> {
	private double scores[];

	//constructor
	public ScoreComparator(double[] array) {
		this.setScores(array);
	}

	//methods
	public int compare(Integer i, Integer j) {
		return Double.compare(this.scores[j], this.scores[i]);
	}

	//setter
	public void setScores(double[] array) {
		this.scores = array;
	}

	//getter
	public double[] getScores() {
		return this.scores;
	}
}
