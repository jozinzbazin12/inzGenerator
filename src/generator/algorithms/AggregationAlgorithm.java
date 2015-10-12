package generator.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import generator.algorithms.models.HeightInfo;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class AggregationAlgorithm extends Algorithm {

	public AggregationAlgorithm(String name) {
		super(name, PropertiesKeys.AGGREGATION_ALGORITHM_HELP);
	}

	@Override
	protected List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		double minRange = info.getArgs().get(Consts.MIN_AGGREGATION_RANGE);
		double maxRange = info.getArgs().get(Consts.MAX_AGGREGATION_RANGE);
		double threshold = info.getArgs().get(Consts.AGGREGATION_CHANCE);

		Map<ModelInfo, List<HeightInfo>> heights = new HashMap<>();
		List<ModelInfo> allModels = new LinkedList<>();
		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			for (int i = 0; i < count; i++) {
				allModels.add(objInfo);
			}
			heights.put(objInfo, availableSpace(objInfo));
		}
		Collections.shuffle(allModels);

		for (ModelInfo objInfo : allModels) {
			double aggregation = randomizeDouble(0, 100);
			BasicModelData obj = new BasicModelData();
			PositionSettings pos = objInfo.getPositionSettings();
			List<HeightInfo> positions = heights.get(objInfo);
			if (aggregation <= threshold && !list.isEmpty()) {
				BasicModelData target = list.get(randomizeInt(0, list.size())).getBasic();
				obj.setPosition(target.getX() + randomizeRange(minRange, maxRange), randomizeDouble(pos.getMinY(), pos.getMaxY()),
						target.getZ() + randomizeRange(minRange, maxRange));
				correctPosition(obj, objInfo, positions);
			} else {
				if (positions.isEmpty()) {
					obj.setPosition(randomizeDouble(pos.getMinX(), pos.getMaxX()), randomizeDouble(pos.getMinY(), pos.getMaxY()),
							randomizeDouble(pos.getMinZ(), pos.getMaxZ()));
				} else {
					setPosition(pos, positions, obj, randomizeInt(0, positions.size()));
				}
			}
			obj.setRelative(pos.isRelative());
			setRotation(objInfo, obj);
			setScale(objInfo, obj);
			list.add(new GeneratedObject(objInfo.getModel(), obj));
		}
		return list;
	}

}
