package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Material {

	@XmlElement(name = "Ka")
	private RGB ambient;

	@XmlElement(name = "d")
	private double d;

	@XmlElement(name = "Kd")
	private RGB diffuse;

	@XmlElement(name = "Ns")
	private double ns;

	@XmlElement(name = "Ks")
	private RGB specular;

	@XmlElement(name = "Texture", required = true)
	private Texture texture;

	public RGB getAmbient() {
		return ambient;
	}

	public double getD() {
		return d;
	}

	public RGB getDiffuse() {
		return diffuse;
	}

	public double getNs() {
		return ns;
	}

	public RGB getSpecular() {
		return specular;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setAmbient(RGB ambient) {
		this.ambient = ambient;
	}

	public void setD(double d) {
		this.d = d;
	}

	public void setDiffuse(RGB diffuse) {
		this.diffuse = diffuse;
	}

	public void setNs(double ns) {
		this.ns = ns;
	}

	public void setSpecular(RGB specular) {
		this.specular = specular;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
