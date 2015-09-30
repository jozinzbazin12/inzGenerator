package generator.algorithms;

import java.util.List;

import generator.models.generation.GenerationInfo;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

public class HeightAlgorithm extends Algorithm {

	public HeightAlgorithm(String name) {
		super(name, PropertiesKeys.HEIGHT_ALGORITHM_HELP);
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		return null;
	}

}
