package generator.models.result;

import java.awt.Color;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import generator.models.generation.GenerationModel;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneratedObject implements Comparable<GeneratedObject> {
	@XmlElement(name = "Settings", required = true)
	private BasicModelData basic;
	@XmlTransient
	private Color color;
	@XmlTransient
	private GenerationModel model;
	@XmlAttribute(name = "objectFile", required = true)
	private String objectPath;
	@XmlTransient
	private Color prevColor;

	public GeneratedObject() {
	}

	public GeneratedObject(GenerationModel model, BasicModelData data) {
		this.model = model;
		basic = data;
		objectPath = model.getPath();
		color = model.getColor();
		setPrevColor();
	}

	@Override
	public int compareTo(GeneratedObject o) {
		return model.compareTo(o.getModel());
	}

	public BasicModelData getBasic() {
		return basic;
	}

	public Color getColor() {
		return color;
	}

	public GenerationModel getModel() {
		return model;
	}

	public Color getModelColor() {
		return model.getColor();
	}

	public String getObjectName() {
		return model.getName();
	}

	public String getObjectPath() {
		return objectPath;
	}

	public boolean overlap(GeneratedObject p) {
		BasicModelData basic2 = p.basic;
		return (Math.abs(basic.getX() - basic2.getX()) * 2 > (basic.getSx() + basic2.getSx()))
				&& (Math.abs(basic.getZ() - basic2.getZ()) * 2 > (basic.getSz() + basic2.getSz()));
	}

	public void setBasic(BasicModelData basic) {
		this.basic = basic;
	}

	public void setModel(GenerationModel model) {
		this.model = model;
		color = model.getColor();
		setPrevColor();
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	private void setPrevColor() {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		prevColor = new Color(255 - r, 255 - g, 255 - b);
	}

	public void swapColors() {
		Color x = color;
		color = prevColor;
		prevColor = x;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(model).append(", X:").append(basic.getX()).append(" ,Y:").append(basic.getY()).append(", Z:")
				.append(basic.getZ());
		return s.toString();
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof GeneratedObject)) {
//			return false;
//		}
//		return overlap((GeneratedObject) obj);
//	}
}
