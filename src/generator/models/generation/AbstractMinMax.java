package generator.models.generation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractMinMax {
	@XmlAttribute(name = "minX")
	private double minX;
	@XmlAttribute(name = "maxX")
	private double maxX;
	@XmlAttribute(name = "minY")
	private double minY;
	@XmlAttribute(name = "maxY")
	private double maxY;
	@XmlAttribute(name = "minZ")
	private double minZ;
	@XmlAttribute(name = "maxZ")
	private double maxZ;

	public AbstractMinMax(double minx, double maxx, double miny, double maxy, double minz, double maxz) {
		minX = minx;
		maxX = maxx;
		minY = miny;
		maxY = maxy;
		minZ = minz;
		maxZ = maxz;
	}

	public AbstractMinMax() {
	}

	public double getMinX() {
		return minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}
}
