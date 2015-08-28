package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BasicMapData {
	@XmlElement(name = "lengthX", required = true)
	private double lengthX;

	@XmlElement(name = "lengthY", required = true)
	private double lengthY;

	@XmlElement(name = "lengthZ", required = true)
	private double lengthZ;

	public double getLengthX() {
		return lengthX;
	}

	public void setLengthX(double lengthX) {
		this.lengthX = lengthX;
	}

	public double getLengthY() {
		return lengthY;
	}

	public void setLengthY(double lengthY) {
		this.lengthY = lengthY;
	}

	public double getLengthZ() {
		return lengthZ;
	}

	public void setLengthZ(double lengthZ) {
		this.lengthZ = lengthZ;
	}

	public BasicMapData(double lengthX, double lengthY, double lengthZ) {
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
	}

	public BasicMapData() {
	}

	public void setLength(double lengthX, double lengthY, double lengthZ) {
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
	}

}
