package generator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
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

public class SpreadAlgorithm extends Algorithm {

	public SpreadAlgorithm(String name) {
		super(name, PropertiesKeys.AGGREGATION_ALGORITHM_HELP);
	}

	private void correctPosition(BasicModelData obj, ModelInfo info, List<HeightInfo> positions) {
		PositionSettings pos = info.getPositionSettings();
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
		correctPosition(obj, pos);
	}

	@Override
	protected List<GeneratedObject> generationMethod(GenerationInfo info) {
		Map<ModelInfo, List<GeneratedObject>> objects = new HashMap<>();
		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			List<HeightInfo> positions = availableSpace(objInfo);
			List<GeneratedObject> acutalList = new ArrayList<>();
			objects.put(objInfo, acutalList);
			double minRange = objInfo.getArgs().get(Consts.MIN_AGGREGATION_RANGE);
			double maxRange = objInfo.getArgs().get(Consts.MAX_AGGREGATION_RANGE);
			double threshold = objInfo.getArgs().get(Consts.AGGREGATION_CHANCE);

			for (int i = 0; i < count; i++) {
				double aggregation = randomizeDouble(0, 100);
				BasicModelData obj = new BasicModelData();
				PositionSettings pos = objInfo.getPositionSettings();
				if (aggregation <= threshold && !acutalList.isEmpty()) {
					BasicModelData target = acutalList.get(randomizeInt(0, acutalList.size())).getBasic();
					obj.setPosition(target.getX() + randomizeRange(minRange, maxRange),
							randomizeDouble(pos.getMinY(), pos.getMaxY()), target.getZ() + randomizeRange(minRange, maxRange));
					correctPosition(obj, objInfo, positions);
				} else {
					if (positions.isEmpty()) {
						obj.setPosition(randomizeDouble(pos.getMinX(), pos.getMaxX()),
								randomizeDouble(pos.getMinY(), pos.getMaxY()), randomizeDouble(pos.getMinZ(), pos.getMaxZ()));
					} else {
						setPosition(pos, positions, obj, randomizeInt(0, positions.size()));
					}
				}
				obj.setRelative(pos.isRelative());
				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				acutalList.add(new GeneratedObject(objInfo.getModel(), obj));
			}
		}

		List<GeneratedObject> result = new ArrayList<>();
		for (List<GeneratedObject> i : objects.values()) {
			result.addAll(i);
		}
		return result;
	}

}
