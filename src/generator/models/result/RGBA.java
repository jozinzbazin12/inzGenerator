package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RGBA extends RGB {

	@XmlAttribute(name = "a")
	private double a;

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public RGBA(double r, double g, double b, double a) {
		super(r, g, b);
		this.a = a;
	}

	public RGBA() {
	}
}
