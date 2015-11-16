package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BasicModelData {
	@XmlElement(name = "relative", required = true)
	private boolean isRelative;

	@XmlElement(name = "rotationX", required = true)
	private double rx;

	@XmlElement(name = "rotationY", required = true)
	private double ry;

	@XmlElement(name = "rotationZ", required = true)
	private double rz;

	@XmlElement(name = "scaleX", required = true)
	private double sx;

	@XmlElement(name = "scaleY", required = true)
	private double sy;

	@XmlElement(name = "scaleZ", required = true)
	private double sz;

	@XmlElement(name = "posX", required = true)
	private double x;

	@XmlElement(name = "posY", required = true)
	private double y;

	@XmlElement(name = "posZ", required = true)
	private double z;

	public double getRx() {
		return rx;
	}

	public double getRy() {
		return ry;
	}

	public double getRz() {
		return rz;
	}

	public double getSx() {
		return sx;
	}

	public double getSy() {
		return sy;
	}

	public double getSz() {
		return sz;
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

	public boolean isRelative() {
		return isRelative;
	}

	public void setPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setRelative(boolean isRelative) {
		this.isRelative = isRelative;
	}

	public void setRotation(double rx, double ry, double rz) {
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
	}

	public void setRx(double rx) {
		this.rx = rx;
	}

	public void setRy(double ry) {
		this.ry = ry;
	}

	public void setRz(double rz) {
		this.rz = rz;
	}

	public void setScale(double sx, double sy, double sz) {
		this.sx = sx;
		this.sy = sy;
		this.sz = sz;
	}

	public void setSx(double sx) {
		this.sx = sx;
	}

	public void setSy(double sy) {
		this.sy = sy;
	}

	public void setSz(double sz) {
		this.sz = sz;
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
