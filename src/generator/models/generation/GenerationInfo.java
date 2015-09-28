package generator.models.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class GenerationInfo {
	@XmlElement(name = "Model")
	private List<ObjectInfo> objects;

	@XmlTransient
	// @XmlElement(name = "Args")
	private Map<String, Number> args = new HashMap<>();

	public List<ObjectInfo> getObjects() {
		return objects;
	}

	public void setObjects(List<ObjectInfo> objects) {
		this.objects = objects;
	}

	public Map<String, Number> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Number> args) {
		this.args = args;
	}

}
