package generator.models.generation;

public class ObjectInfo implements Comparable<ObjectInfo> {

	private PositionSettings positionSettings;
	private ScaleSettings scaleSettings;
	private RotationSettings rotationSettings;
	int count;
	private GenerationModel model;

	@Override
	public String toString() {
		return model.getName();
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

	public ObjectInfo(int count, PositionSettings pos, RotationSettings rot, ScaleSettings scale, GenerationModel model) {
		this.count = count;
		positionSettings = pos;
		rotationSettings = rot;
		scaleSettings = scale;
		this.model = model;
	}

	public ObjectInfo(GenerationModel model) {
		this.model = model;
		positionSettings = new PositionSettings();
		scaleSettings = new ScaleSettings();
		rotationSettings = new RotationSettings();
	}

	public GenerationModel getModel() {
		return model;
	}

	@Override
	public int compareTo(ObjectInfo o) {
		if (model == null) {
			return -1;
		}
		if (!(o instanceof ObjectInfo) || o.getModel() == null) {
			return -1;
		}
		return model.compareTo(o.getModel());
	}

	public void setPositionSettings(PositionSettings positionSettings) {
		this.positionSettings = positionSettings;
	}

	public void setScaleSettings(ScaleSettings scaleSettings) {
		this.scaleSettings = scaleSettings;
	}

	public void setRotationSettings(RotationSettings rotationSettings) {
		this.rotationSettings = rotationSettings;
	}
}
