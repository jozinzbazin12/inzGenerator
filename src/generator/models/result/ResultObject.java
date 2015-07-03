package generator.models.result;

import java.util.List;

public class ResultObject {
	
	private List<GeneratedObject> generatedObjects;
	
	public ResultObject( List<GeneratedObject> generatedObjects) {
		this.generatedObjects=generatedObjects;
	}

	public List<GeneratedObject> getGeneratedObjects() {
		return generatedObjects;
	}
}
