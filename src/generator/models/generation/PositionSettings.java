package generator.models.generation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PositionSettings extends AbstractMinMax {

	@XmlAttribute(name = "relative")
	boolean relative;

	public PositionSettings(double minx, double maxx, double miny, double maxy, double minz, double maxz, boolean relative) {
		super(minx, maxx, miny, maxy, minz, maxz);
		this.relative = relative;
	}

	public PositionSettings() {
		relative = true;
	}

	public boolean isRelative() {
		return relative;
	}

	public void setRelative(boolean relative) {
		this.relative = relative;
	}

}
