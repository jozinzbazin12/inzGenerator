package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Material {

	@XmlElement(name = "Texture", required = true)
	private Texture texture;

	@XmlElement(name = "Ka")
	private RGB ambient;

	@XmlElement(name = "Kd")
	private RGB diffuse;

	@XmlElement(name = "Ks")
	private RGB specular;

	@XmlElement(name = "Ns")
	private double ns;

	@XmlElement(name = "d")
	private double d;

	public RGB getAmbient() {
		return ambient;
	}

	public void setAmbient(RGB ambient) {
		this.ambient = ambient;
	}

	public RGB getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(RGB diffuse) {
		this.diffuse = diffuse;
	}

	public RGB getSpecular() {
		return specular;
	}

	public void setSpecular(RGB specular) {
		this.specular = specular;
	}

	public double getNs() {
		return ns;
	}

	public void setNs(double ns) {
		this.ns = ns;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
