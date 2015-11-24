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

			int minPropagation = objInfo.getArgs().get(Consts.MIN_PROPAGATION).intValue();
			int maxPropagation = objInfo.getArgs().get(Consts.MAX_PROPAGATION).intValue();

			PositionSettings pos = objInfo.getPositionSettings();
			setStartPos(objInfo, acutalList);

			for (int i = 0; i < count - 1; i++) {
				double aggregation = randomizeDouble(0, 100);
				BasicModelData obj = new BasicModelData();
				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				if (i < minPropagation - 1) {
					setStartPos(objInfo, acutalList);
					continue;
				}
				int maxpx = maxPropagation <= i ? maxPropagation : i;

				if (aggregation <= threshold) {
					BasicModelData target = acutalList.get(randomizeInt(i - maxpx, i - minPropagation)).getBasic();
					obj.setPosition(target.getX() + randomizeRange(minRange, maxRange),
							randomizeDouble(pos.getMinY(), pos.getMaxY()), target.getZ() + randomizeRange(minRange, maxRange));
					correctPosition(obj, pos, positions);
				} else {
					if (positions.isEmpty()) {
						obj.setPosition(randomizeDouble(pos.getMinX(), pos.getMaxX()),
								randomizeDouble(pos.getMinY(), pos.getMaxY()), randomizeDouble(pos.getMinZ(), pos.getMaxZ()));
						correctPosition(obj, pos, positions);
					} else {
						setPosition(pos, positions, obj, randomizeInt(0, positions.size()));
					}
				}
				obj.setRelative(pos.isRelative());
				GeneratedObject object = new GeneratedObject(objInfo.getModel(), obj);
				acutalList.add(object);
			}
		}

		List<GeneratedObject> result = new ArrayList<>();
		for (List<GeneratedObject> i : objects.values()) {
			result.addAll(i);
		}
		return result;
	}

	private void setStartPos(ModelInfo objInfo, List<GeneratedObject> acutalList) {
		double startMinx = objInfo.getArgs().get(Consts.START_MIN_X);
		double startMinz = objInfo.getArgs().get(Consts.START_MIN_Z);
		double startMaxx = objInfo.getArgs().get(Consts.START_MAX_X);
		double startMaxz = objInfo.getArgs().get(Consts.START_MAX_Z);

		BasicModelData basic = new BasicModelData();
		setRotation(objInfo, basic);
		setScale(objInfo, basic);
		PositionSettings pos = objInfo.getPositionSettings();
		basic.setPosition(randomizeDouble(startMinx, startMaxx), randomizeDouble(pos.getMinY(), pos.getMaxY()),
				randomizeDouble(startMinz, startMaxz));
		acutalList.add(new GeneratedObject(objInfo.getModel(), basic));
	}

}
