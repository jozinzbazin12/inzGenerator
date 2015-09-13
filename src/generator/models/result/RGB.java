package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RGB {

	@XmlAttribute(name = "r")
	protected double r;

	@XmlAttribute(name = "g")
	protected double g;

	@XmlAttribute(name = "b")
	protected double b;

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public RGB(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public RGB() {
	}
}
