package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import generator.models.MyFile;
import generator.models.MyFileAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Texture {

	@XmlJavaTypeAdapter(MyFileAdapter.class)
	@XmlAttribute(name = "path", required = true)
	private MyFile path;

	@XmlAttribute(name = "scale", required = true)
	private double scale;

	public Texture() {
	}

	public Texture(MyFile path, double scale) {
		super();
		this.path = path;
		this.scale = scale;
	}

	public String getPath() {
		return path.getAbsolutePath();
	}

	public double getScale() {
		return scale;
	}

	public void setPath(MyFile path) {
		this.path = path;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
