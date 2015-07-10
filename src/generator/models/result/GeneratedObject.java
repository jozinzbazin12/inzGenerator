package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneratedObject implements Comparable<GeneratedObject> {
	@XmlAttribute(name = "objectFile", required = true)
	private String objectFile;
	@XmlElement(name = "Settings", required = true)
	private BasicModelData basic;

	public GeneratedObject(BasicModelData basic) {
		this.basic = basic;
	}

	public BasicModelData getBasic() {
		return basic;
	}

	public GeneratedObject(String name, BasicModelData data) {
		objectFile = name;
		basic = data;
	}

	public String getObjectFile() {
		return objectFile;
	}

	public GeneratedObject() {
	}

	public void setBasic(BasicModelData basic) {
		this.basic = basic;
	}

	public void setObjectFile(String objectFile) {
		this.objectFile = objectFile;
	}

	@Override
	public int compareTo(GeneratedObject o) {
		if (o.getObjectFile() == null || objectFile == null)
			return 0;
		return objectFile.compareTo(o.getObjectFile());
	}

}
