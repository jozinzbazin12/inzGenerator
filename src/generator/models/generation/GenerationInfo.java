package generator.models.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class GenerationInfo {
	@XmlElement(name = "Model")
	private List<ObjectInfo> objects;

	@XmlElement(name = "Args")
	private Map<String, Double> args = new HashMap<>();

	public List<ObjectInfo> getObjects() {
		return objects;
	}

	public void setObjects(List<ObjectInfo> objects) {
		this.objects = objects;
	}

	public Map<String, Double> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Double> args) {
		this.args = args;
	}

}
