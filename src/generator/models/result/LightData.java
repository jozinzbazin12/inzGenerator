package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LightData {

	@XmlAttribute(name = "posX")
	private double x;

	@XmlAttribute(name = "posY")
	private double y;

	@XmlAttribute(name = "posZ")
	private double z;

	@XmlAttribute(name = "mode")
	private double mode;

	@XmlElement(name = "Ambient")
	private BasicLightData ambient;

	@XmlElement(name = "Diffuse")
	private BasicLightData diffuse;

	@XmlElement(name = "Specular")
	private BasicLightData specular;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public BasicLightData getAmbient() {
		return ambient;
	}

	public void setAmbient(BasicLightData ambient) {
		this.ambient = ambient;
	}

	public BasicLightData getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(BasicLightData diffuse) {
		this.diffuse = diffuse;
	}

	public BasicLightData getSpecular() {
		return specular;
	}

	public void setSpecular(BasicLightData specular) {
		this.specular = specular;
	}

	public void setLight(BasicLightData ambient, BasicLightData diffuse, BasicLightData specular) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public void setPosition(double x, double y, double z, double mode) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}

	public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

}
