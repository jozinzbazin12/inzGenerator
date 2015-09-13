package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Material {

	@XmlElement(name = "Texture", required = true)
	private Texture texture;

	@XmlElement(name = "Ka")
	private RGBA ambient;

	@XmlElement(name = "Kd")
	private RGBA diffuse;

	@XmlElement(name = "Ks")
	private RGBA specular;

	@XmlElement(name = "Ns")
	private double ns;

	@XmlElement(name = "d")
	private double d;

	public RGBA getAmbient() {
		return ambient;
	}

	public void setAmbient(RGBA ambient) {
		this.ambient = ambient;
	}

	public RGBA getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(RGBA diffuse) {
		this.diffuse = diffuse;
	}

	public RGBA getSpecular() {
		return specular;
	}

	public void setSpecular(RGBA specular) {
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
