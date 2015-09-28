package generator.algorithms;

import java.util.ArrayList;
import java.util.List;

import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class RegularAlgorithm extends Algorithm {

	public RegularAlgorithm(String name) {
		super(name, PropertiesKeys.REGULAR_ALGORITHM_HELP);
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		double minX = info.getArgs().get(Consts.MIN_X).doubleValue();
		double minZ = info.getArgs().get(Consts.MIN_Z).doubleValue();
		double actualMinX = minX;
		double actualMinZ = minZ;
		double maxX = info.getArgs().get(Consts.MAX_X).doubleValue();
		double maxZ = info.getArgs().get(Consts.MAX_Z).doubleValue();
		double width = maxZ - minZ;
		double height = maxX - minX;
		double ratio = width / height;

		int count = 0;
		List<Integer> counts = new ArrayList<>();
		List<ObjectInfo> allObjects = info.getObjects();
		List<ObjectInfo> objects = new ArrayList<>();
		for (ObjectInfo obj : allObjects) {
			int tmp = getCount(obj);
			if (tmp > 0) {
				objects.add(obj);
				count += tmp;
				counts.add(tmp);
			}
		}

		double dx = (width / Math.floor(Math.sqrt(count))) / ratio;
		double dz = (height / Math.floor(Math.sqrt(count))) * ratio;
		int actualCount = 0;
		int modelNumber = 0;
		ObjectInfo objInfo;
		while (count > 0) {
			if (actualCount >= counts.get(modelNumber)) {
				modelNumber++;
				actualCount = 0;
			}
			objInfo = objects.get(modelNumber);
			actualCount++;

			BasicModelData obj = new BasicModelData();
			obj.setPosition(actualMinX,
					randomizeDouble(objInfo.getPositionSettings().getMinY(), objInfo.getPositionSettings().getMaxY()),
					actualMinZ);
			obj.setRelative(objInfo.getPositionSettings().isRelative());

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
