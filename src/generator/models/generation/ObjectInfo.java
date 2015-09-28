package generator.models.generation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectInfo implements Comparable<ObjectInfo> {
	@XmlTransient
	private GenerationModel model;

	@XmlAttribute(name = "minCount")
	private int minCount;
	@XmlAttribute(name = "maxCount")
	private int maxCount;

	@XmlElement(name = "Position")
	private PositionSettings positionSettings;
	@XmlElement(name = "Scale")
	private ScaleSettings scaleSettings;
	@XmlElement(name = "Rotation")
	private RotationSettings rotationSettings;

	@XmlAttribute(name = "path")
	private String getPath() {
		return model.getPath();
	}

	@SuppressWarnings("unused")
	private void setPath(String path) {
		model = new GenerationModel(path);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(model.getName());
		str.append(" (");
		str.append(model.getPath());
		str.append("), ");
		str.append("<");
		str.append(minCount);
		str.append(", ");
		str.append(maxCount);
		str.append(">");
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

	public ObjectInfo() {
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
