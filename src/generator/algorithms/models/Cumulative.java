package generator.algorithms.models;

import java.util.List;

public class Cumulative {

	private double value;
	private List<HeightInfo> points;

	public Cumulative(double value, List<HeightInfo> points) {
		this.value = value;
		this.points = points;
	}

	public double getValue() {
		return value;
	}

	public List<HeightInfo> getPoints() {
		return points;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cumulative)) {
			return false;
		}
		return ((Cumulative) obj).value == value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public Cumulative(Cumulative c) {
		this.value = c.value;
		this.points = c.points;
	}
}
