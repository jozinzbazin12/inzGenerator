package generator.models.result;

import generator.models.generation.GenerationModel;

import java.awt.Color;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneratedObject implements Comparable<GeneratedObject> {
	@XmlAttribute(name = "objectFile", required = true)
	private String objectFile;
	@XmlElement(name = "Settings", required = true)
	private BasicModelData basic;
	@XmlTransient
	private GenerationModel model;
	@XmlTransient
	private Color color;
	@XmlTransient
	private Color prevColor;

	public BasicModelData getBasic() {
		return basic;
	}

	public GeneratedObject(GenerationModel model, BasicModelData data) {
		this.model = model;
		objectFile = model.getName();
		basic = data;
		color = model.getColor();
		setPrevColor();
	}

	private void setPrevColor() {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		prevColor = new Color(255 - r, 255 - g, 255 - b);
	}

	public String getObjectFile() {
		return objectFile;
	}

	public GeneratedObject() {
	}

	public void setBasic(BasicModelData basic) {
		this.basic = basic;
	}

	public void setObjectFile(String objectFile) {
		this.objectFile = objectFile;
	}

	@Override
	public int compareTo(GeneratedObject o) {
		if (o.getObjectFile() == null || objectFile == null)
			return 0;
		return objectFile.compareTo(o.getObjectFile());
	}

	public GenerationModel getModel() {
		return model;
	}

	public void setModel(GenerationModel model) {
		this.model = model;
		color=model.getColor();
		setPrevColor();
	}

	public Color getColor() {
		return color;
	}

	public void swapColors() {
		Color x = color;
		color = prevColor;
		prevColor = x;
	}
}
