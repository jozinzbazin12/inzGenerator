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
	private List<ModelInfo> models;

	@XmlElement(name = "Args")
	private Map<String, Double> args = new HashMap<>();

	public List<ModelInfo> getModels() {
		return models;
	}

	public void setModels(List<ModelInfo> models) {
		this.models = models;
	}

	public Map<String, Double> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Double> args) {
		this.args = args;
	}

}
