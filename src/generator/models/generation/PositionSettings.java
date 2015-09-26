package generator.models.generation;

public class PositionSettings extends AbstractMinMax {

	boolean relative;

	public PositionSettings(double minx, double maxx, double miny, double maxy, double minz, double maxz, boolean relative) {
		super(minx, maxx, miny, maxy, minz, maxz);
		this.relative = relative;
	}

	public PositionSettings() {
	}

	public boolean isRelative() {
		return relative;
	}

	public void setRelative(boolean relative) {
		this.relative = relative;
	}

}
