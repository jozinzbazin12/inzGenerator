package generator.models.generation;

public class ObjectInfo implements Comparable<ObjectInfo> {

	private PositionSettings positionSettings;
	private ScaleSettings scaleSettings;
	private RotationSettings rotationSettings;
	private int minCount;
	private int maxCount;
	private GenerationModel model;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Name: ");
		str.append(model.getName());
		str.append(", ");
		str.append(model.getPath());
		return str.toString();
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

	public ObjectInfo(PositionSettings pos, RotationSettings rot, ScaleSettings scale, GenerationModel model) {
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

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
}
