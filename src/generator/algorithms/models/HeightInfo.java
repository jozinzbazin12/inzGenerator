package generator.algorithms.models;

public class HeightInfo implements Comparable<HeightInfo> {
	private static double threshold = 1;
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
		return Math.sqrt(Math.pow(x - obj.x, 2) + Math.pow(z - obj.z, 2)) < threshold;
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
	public int hashCode() {
		return (int) (y / 16);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[X: ").append(x).append(", Y: ").append(y).append(", Z: ").append(z).append("]");
		return str.toString();
	}

	public static double getThreshold() {
		return threshold;
	}

	public static void setThreshold(double threshold) {
		HeightInfo.threshold = threshold;
	}
}
