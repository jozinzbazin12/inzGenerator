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

	private void correctPosition(BasicModelData obj, ModelInfo info, List<HeightInfo> positions) {
		PositionSettings pos = info.getPositionSettings();
		if (obj.getX() > pos.getMaxX()) {
			obj.setX(pos.getMaxX());
		}
		if (obj.getX() < pos.getMinX()) {
			obj.setX(pos.getMinX());
		}
		if (obj.getZ() > pos.getMaxZ()) {
			obj.setZ(pos.getMaxZ());
		}
		if (obj.getZ() < pos.getMinZ()) {
			obj.setZ(pos.getMinZ());
		}
		if (!positions.isEmpty()) {
			HeightInfo actual = new HeightInfo(obj.getX(), 0, obj.getZ());
			double minLength = Double.MAX_VALUE;
			HeightInfo nearest = new HeightInfo(Double.MAX_VALUE, 0, Double.MAX_VALUE);
			for (HeightInfo i : positions) {
				if (i.equals(actual)) {
					return;
				}
				double tempLenght = getLength(i.getX(), nearest.getX(), i.getZ(), nearest.getZ());
				if (minLength < 0.5) {
					break;
				}
				if (tempLenght < minLength) {
					minLength = tempLenght;
					nearest = i;
				}
			}
			obj.setX(nearest.getX() + randomizeDouble(-xRatio, xRatio));
			obj.setZ(nearest.getZ() + randomizeDouble(-zRatio, zRatio));
		}
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
				obj.setPosition(target.getX() + randomizeSquareDouble(minRange, maxRange),
						randomizeDouble(pos.getMinY(), pos.getMaxY()), target.getZ() + randomizeSquareDouble(minRange, maxRange));
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
