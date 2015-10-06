package generator.models.generation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import generator.utils.Consts;

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelInfo implements Comparable<ModelInfo> {
	@XmlTransient
	private GenerationModel model;
	@XmlTransient
	private BufferedImage mask;
	@XmlTransient
	private String maskFile;
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
	@XmlElement(name = "Args")
	private Map<String, Double> args = new HashMap<>();

	@XmlAttribute(name = "maskFile")
	public String getMaskFile() {
		return maskFile;
	}

	public void setMaskFile(String maskFile) {
		this.maskFile = maskFile;
		try {
			this.mask = ImageIO.read(new File(maskFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public ModelInfo(PositionSettings pos, RotationSettings rot, ScaleSettings scale, GenerationModel model) {
		positionSettings = pos;
		rotationSettings = rot;
		scaleSettings = scale;
		this.model = model;
	}

	public ModelInfo(GenerationModel model) {
		this.model = model;
		positionSettings = new PositionSettings();
		scaleSettings = new ScaleSettings();
		rotationSettings = new RotationSettings();
		fillArgs();
	}

	private void fillArgs() {
		args.put(Consts.MIN_Y_HEIGHT, 0D);
		args.put(Consts.MAX_Y_HEIGHT, 0D);
	}

	public ModelInfo() {

	}

	public GenerationModel getModel() {
		return model;
	}

	@Override
	public int compareTo(ModelInfo o) {
		if (model == null) {
			return -1;
		}
		if (!(o instanceof ModelInfo) || o.getModel() == null) {
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

	public Map<String, Double> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Double> args) {
		this.args = args;
	}

	public BufferedImage getMask() {
		return mask;
	}

	public void setMask(BufferedImage mask) {
		this.mask = mask;
	}
}
