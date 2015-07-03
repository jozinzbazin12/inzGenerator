package generator.models.generation;

public class ScaleSettings {
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;
	
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
	
	public ScaleSettings(double minx, double maxx, double miny, double maxy, double minz,double maxz) {
		minX=minx;
		maxX=maxx;
		minY=miny;
		maxY=maxy;
		minZ=minz;
		maxZ=maxz;
	}
}
