package generator.algorithms;

import java.util.ArrayList;
import java.util.List;

import generator.algorithms.models.HeightInfo;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

public class FullRandomAlgorithm extends Algorithm {

	public FullRandomAlgorithm(String name) {
		super(name, PropertiesKeys.FULL_RANDOM_ALGORITHM_HELP);
	}

	@Override
	protected List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();

		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			List<HeightInfo> positions = availableSpace(objInfo);

			for (int i = 0; i < count; i++) {
				BasicModelData obj = new BasicModelData();
				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				PositionSettings pos = objInfo.getPositionSettings();
				if (positions != null && !positions.isEmpty()) {
					setPosition(pos, positions, obj, randomizeInt(0, positions.size()));
				} else {
					obj.setPosition(randomizeDouble(pos.getMinX(), pos.getMaxX()), randomizeDouble(pos.getMinY(), pos.getMaxY()),
							randomizeDouble(pos.getMinZ(), pos.getMaxZ()));
					correctPosition(obj, pos, positions);
				}

				obj.setRelative(pos.isRelative());
				list.add(new GeneratedObject(objInfo.getModel(), obj));
			}
		}
		return list;
	}

}
