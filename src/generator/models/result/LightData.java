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

	@XmlElement(name = "Ambient")
	private RGBA ambient;

	@XmlElement(name = "Diffuse")
	private RGBA diffuse;

	@XmlElement(name = "Specular")
	private RGBA specular;
	
	@XmlAttribute(name = "mode")
	private double mode;

	public RGBA getAmbient() {
		return ambient;
	}

	public RGBA getDiffuse() {
		return diffuse;
	}

	public double getMode() {
		return mode;
	}

	public RGBA getSpecular() {
		return specular;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setAmbient(RGBA ambient) {
		this.ambient = ambient;
	}

	public void setDiffuse(RGBA diffuse) {
		this.diffuse = diffuse;
	}

	public void setLight(RGBA ambient, RGBA diffuse, RGBA specular) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

	public void setPosition(double x, double y, double z, double mode) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}

	public void setSpecular(RGBA specular) {
		this.specular = specular;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
