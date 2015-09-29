package generator.algorithms;

import java.util.ArrayList;
import java.util.List;

import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

public class FullRandomAlgorithm extends Algorithm {

	public FullRandomAlgorithm(String name) {
		super(name, PropertiesKeys.FULL_RANDOM_ALGORITHM_HELP);
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();

		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			for (int i = 0; i < count; i++) {
				BasicModelData obj = new BasicModelData();
				obj.setPosition(randomizeDouble(objInfo.getPositionSettings().getMinX(), objInfo.getPositionSettings().getMaxX()),
						randomizeDouble(objInfo.getPositionSettings().getMinY(), objInfo.getPositionSettings().getMaxY()),
						randomizeDouble(objInfo.getPositionSettings().getMinZ(), objInfo.getPositionSettings().getMaxZ()));
				obj.setRelative(objInfo.getPositionSettings().isRelative());

				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				list.add(new GeneratedObject(objInfo.getModel(), obj));
			}
		}
		return list;
	}

}
