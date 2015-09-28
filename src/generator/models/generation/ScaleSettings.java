package generator.models.generation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ScaleSettings extends AbstractMinMax {
	@XmlAttribute(name = "equal")
	private boolean equal;

	public ScaleSettings(double minx, double maxx, double miny, double maxy, double minz, double maxz) {
		super(minx, maxx, miny, maxy, minz, maxz);
	}

	public ScaleSettings() {
	}

	public boolean isEqual() {
		return equal;
	}

	public void setEqual(boolean equal) {
		this.equal = equal;
	}

}
