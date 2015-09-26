package generator.models.generation;

public class ScaleSettings extends AbstractMinMax {
	private boolean equal;

	public ScaleSettings(double minx, double maxx, double miny, double maxy, double minz, double maxz) {
		super(minx, maxx, miny, maxy, minz, maxz);
	}

	public ScaleSettings() {
	}

	public boolean isEqual() {
		return equal;
	}

	public void setEqual(boolean equal) {
		this.equal = equal;
	}

}
