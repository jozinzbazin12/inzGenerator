package generator.models.generation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import generator.models.MyFile;
import generator.models.MyFileAdapter;
import generator.utils.Consts;

@XmlAccessorType(XmlAccessType.FIELD)
public class ModelInfo implements Comparable<ModelInfo> {
	@XmlTransient
	private Map<String, Double> args = new HashMap<>();
	@XmlTransient
	private BufferedImage mask;
	@XmlJavaTypeAdapter(MyFileAdapter.class)
	@XmlAttribute(name = "maskFile")
	private MyFile maskFile;
	@XmlAttribute(name = "maxCount")
	private int maxCount;

	@XmlAttribute(name = "minCount")
	private int minCount;
	@XmlTransient
	private GenerationModel model;

	@XmlElement(name = "Position")
	private PositionSettings positionSettings;
	@XmlElement(name = "Rotation")
	private RotationSettings rotationSettings;
	@XmlElement(name = "Scale")
	private ScaleSettings scaleSettings;

	public ModelInfo() {

	}

	public ModelInfo(GenerationModel model) {
		this.model = model;
		positionSettings = new PositionSettings();
		scaleSettings = new ScaleSettings();
		rotationSettings = new RotationSettings();
		fillArgs();
	}

	public ModelInfo(PositionSettings pos, RotationSettings rot, ScaleSettings scale, GenerationModel model) {
		positionSettings = pos;
		rotationSettings = rot;
		scaleSettings = scale;
		this.model = model;
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

	private void fillArgs() {
		setArg(Consts.MIN_Y_HEIGHT, 0D);
		setArg(Consts.MAX_Y_HEIGHT, 0D);
		setArg(Consts.MIN_AGGREGATION_RANGE, 0D);
		setArg(Consts.MAX_AGGREGATION_RANGE, 0D);
		setArg(Consts.AGGREGATION_CHANCE, 0D);
		setArg(Consts.START_MAX_X, 0D);
		setArg(Consts.START_MAX_Z, 0D);
		setArg(Consts.START_MIN_X, 0D);
		setArg(Consts.START_MIN_Z, 0D);
		setArg(Consts.MIN_PROPAGATION, 0D);
		setArg(Consts.MAX_PROPAGATION, 0D);
		setArg(Consts.RATE, 0D);
	}

	@XmlElement(name = "Args")
	public Map<String, Double> getArgs() {
		return args;
	}

	public BufferedImage getMask() {
		return mask;
	}

	public MyFile getMaskFile() {
		return maskFile;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getMinCount() {
		return minCount;
	}

	public GenerationModel getModel() {
		return model;
	}

	@XmlAttribute(name = "path")
	@XmlJavaTypeAdapter(MyFileAdapter.class)
	public MyFile getPath() {
		return model.getPath();
	}

	public PositionSettings getPositionSettings() {
		return positionSettings;
	}

	public RotationSettings getRotationSettings() {
		return rotationSettings;
	}

	public ScaleSettings getScaleSettings() {
		return scaleSettings;
	}

	private void setArg(String key, Double value) {
		if (!args.containsKey(key)) {
			args.put(key, value);
		}
	}

	public void setArgs(Map<String, Double> args) {
		this.args = args;
		fillArgs();
	}

	public void setMask(BufferedImage mask) {
		this.mask = mask;
	}

	public void setMaskFile(MyFile maskFile) {
		this.maskFile = maskFile;
		if (maskFile != null) {
			try {
				this.mask = ImageIO.read(maskFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public void setPath(MyFile path) {
		model = new GenerationModel(path);
	}

	public void setPositionSettings(PositionSettings positionSettings) {
		this.positionSettings = positionSettings;
	}

	public void setRotationSettings(RotationSettings rotationSettings) {
		this.rotationSettings = rotationSettings;
	}

	public void setScaleSettings(ScaleSettings scaleSettings) {
		this.scaleSettings = scaleSettings;
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
}
