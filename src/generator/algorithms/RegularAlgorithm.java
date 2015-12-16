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

public class RegularAlgorithm extends Algorithm {

	public RegularAlgorithm(String name) {
		super(name, PropertiesKeys.REGULAR_ALGORITHM_HELP);
	}

	@Override
	protected List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		double minX = info.getArgs().get(Consts.MIN_X).doubleValue();
		double minZ = info.getArgs().get(Consts.MIN_Z).doubleValue();
		double actualMinX = minX;
		double actualMinZ = minZ;
		double maxX = info.getArgs().get(Consts.MAX_X).doubleValue();
		double maxZ = info.getArgs().get(Consts.MAX_Z).doubleValue();
		double width = maxZ - minZ;
		double height = maxX - minX;

		int count = 0;
		List<Integer> counts = new ArrayList<>();
		List<ModelInfo> allObjects = info.getModels();
		List<ModelInfo> objects = new ArrayList<>();
		for (ModelInfo obj : allObjects) {
			int tmp = getCount(obj);
			if (tmp > 0) {
				objects.add(obj);
				count += tmp;
				counts.add(tmp);
			}
		}

		double floor = Math.floor(Math.sqrt(count)) - 1;
		double dx = (width / floor);
		double dz = (height / floor);
		int actualCount = 0;
		int modelNumber = 0;
		ModelInfo objInfo;
		while (count > 0) {
			if (actualCount >= counts.get(modelNumber)) {
				modelNumber++;
				actualCount = 0;
			}
			objInfo = objects.get(modelNumber);
			actualCount++;

			BasicModelData obj = new BasicModelData();
			PositionSettings positionSettings = objInfo.getPositionSettings();
			obj.setPosition(actualMinX, randomizeDouble(positionSettings.getMinY(), positionSettings.getMaxY()), actualMinZ);
			obj.setRelative(positionSettings.isRelative());

			setRotation(objInfo, obj);
			setScale(objInfo, obj);
			list.add(new GeneratedObject(objInfo.getModel(), obj));
			count--;
			actualMinX += dx;

			if (actualMinX > maxX) {
				actualMinX = minX;
				actualMinZ += dz;
			}
		}
		return list;
	}

}
