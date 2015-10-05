package generator.algorithms;

import java.util.ArrayList;
import java.util.List;

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

	private void correctPosition(BasicModelData obj, PositionSettings pos) {
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
	}

	@Override
	protected List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		double minRange = info.getArgs().get(Consts.MIN_AGGREGATION_RANGE);
		double maxRange = info.getArgs().get(Consts.MAX_AGGREGATION_RANGE);
		double threshold = info.getArgs().get(Consts.AGGREGATION_CHANCE);

		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			for (int i = 0; i < count; i++) {
				double aggregation = randomizeDouble(0, 100);
				BasicModelData obj = new BasicModelData();
				PositionSettings positionSettings = objInfo.getPositionSettings();
				if (aggregation <= threshold && !list.isEmpty()) {
					BasicModelData target = list.get(randomizeInt(0, list.size())).getBasic();

					obj.setPosition(target.getX() + randomizeSquareDouble(minRange, maxRange),
							randomizeDouble(positionSettings.getMinY(), positionSettings.getMaxY()),
							target.getZ() + randomizeSquareDouble(minRange, maxRange));
					correctPosition(obj, positionSettings);
				} else {
					obj.setPosition(randomizeDouble(positionSettings.getMinX(), positionSettings.getMaxX()),
							randomizeDouble(positionSettings.getMinY(), positionSettings.getMaxY()),
							randomizeDouble(positionSettings.getMinZ(), positionSettings.getMaxZ()));
				}
				obj.setRelative(positionSettings.isRelative());
				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				list.add(new GeneratedObject(objInfo.getModel(), obj));
			}
		}
		return list;
	}

}
