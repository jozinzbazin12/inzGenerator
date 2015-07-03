package generator.models.generation;

public class PositionSettings {
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;
		
	boolean relative;

	public double getMinX() {
		return minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public boolean isRelative() {
		return relative;
	}
	
	public PositionSettings(double minx, double maxx, double miny, double maxy, double minz,double maxz, boolean relative) {
		minX=minx;
		maxX=maxx;
		minY=miny;
		maxY=maxy;
		minZ=minz;
		maxZ=maxz;
		this.relative=relative;
	}
}
