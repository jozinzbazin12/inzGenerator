package generator.algorithms.models;

public class HeightInfo implements Comparable<HeightInfo> {
	private static double threshold = 1;
	private double x;
	private double y;
	private double z;
	private double sx;
	private double sz;

	public HeightInfo(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getRange() {
		return (sx + sz) / 2.0;
	}

	public static double getThreshold() {
		return threshold;
	}

	public static void setThreshold(double threshold) {
		HeightInfo.threshold = threshold;
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
	public boolean equals(Object object) {
		if (!(object instanceof HeightInfo)) {
			return false;
		}
		HeightInfo obj = (HeightInfo) object;
		return Math.sqrt(Math.pow(x - obj.x, 2) + Math.pow(z - obj.z, 2)) < threshold;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
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

	public void setZ(double z) {
		this.z = z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setSx(double sx) {
		this.sx = sx;
	}

	public void setSz(double sz) {
		this.sz = sz;
	}
}
