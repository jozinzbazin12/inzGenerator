package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Texture {

	@XmlAttribute(name = "path", required = true)
	private String path;

	@XmlAttribute(name = "scale", required = true)
	private double scale;

	public Texture() {
	}

	public Texture(String path, double scale) {
		super();
		this.path = path;
		this.scale = scale;
	}

	public String getPath() {
		return path;
	}

	public double getScale() {
		return scale;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
