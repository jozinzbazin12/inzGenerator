package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class MapObject {

	@XmlAttribute(name = "mapFile", required = true)
	private String mapFileName;
	@XmlAttribute(name = "textureFile", required = true)
	private String textureFileName;
	@XmlAttribute(name = "lengthX")
	private double lengthX;
	@XmlAttribute(name = "lengthZ")
	private double lengthZ;

	public String getMapFileName() {
		return mapFileName;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}

	public String getTextureFileName() {
		return textureFileName;
	}

	public void setTextureFileName(String textureFileName) {
		this.textureFileName = textureFileName;
	}

	public double getLengthX() {
		return lengthX;
	}

	public void setLengthX(double lengthX) {
		this.lengthX = lengthX;
	}

	public double getLengthZ() {
		return lengthZ;
	}

	public void setLengthZ(double lengthZ) {
		this.lengthZ = lengthZ;
	}

}
