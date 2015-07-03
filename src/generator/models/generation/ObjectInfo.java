package generator.models.generation;

public class ObjectInfo {

	private String objectName;
	private PositionSettings positionSettings;
	private ScaleSettings scaleSettings;
	private RotationSettings rotationSettings;
	int count;

	@Override
	public String toString() {
		return objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public PositionSettings getPositionSettings() {
		return positionSettings;
	}

	public ScaleSettings getScaleSettings() {
		return scaleSettings;
	}

	public RotationSettings getRotationSettings() {
		return rotationSettings;
	}

	public int getCount() {
		return count;
	}

	public ObjectInfo(int count, PositionSettings pos, RotationSettings rot, ScaleSettings scale) {
		this.count=count;
		positionSettings=pos;
		rotationSettings=rot;
		scaleSettings=scale;
	}
}
