package generator.algorithms.models;

import java.util.List;

public class Cumulative {

	private List<HeightInfo> points;
	private double value;

	public Cumulative(Cumulative c) {
		this.value = c.value;
		this.points = c.points;
	}

	public Cumulative(double value, List<HeightInfo> points) {
		this.value = value;
		this.points = points;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cumulative)) {
			return false;
		}
		return ((Cumulative) obj).value == value;
	}

	public List<HeightInfo> getPoints() {
		return points;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
