package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneratedObject {
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
}
