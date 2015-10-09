package generator.algorithms.models;

public class HeightInfo implements Comparable<HeightInfo> {
	public static final double THRESHOLD = 1;
	private double x;
	private double z;
	private double y;

	public HeightInfo(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}

	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof HeightInfo)) {
			return false;
		}
		HeightInfo obj = (HeightInfo) object;
		return Math.abs(obj.x - x) < THRESHOLD && Math.abs(obj.z - z) < THRESHOLD;
	}

	@Override
	public int compareTo(HeightInfo o) {
		double y2 = o.getY();
		if (y > y2) {
			return 1;
		} else if (y < y2) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[X: ").append(x).append(", Y: ").append(y).append(", Z: ").append(z).append("]");
		return str.toString();
	}
}
