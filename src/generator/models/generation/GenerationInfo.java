package generator.models.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerationInfo {

	private List<ObjectInfo> objects;
	Map<String, Number> args = new HashMap<>();

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
