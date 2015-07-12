package generator.algorithms;

import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;

import java.util.ArrayList;
import java.util.List;

public class FullRandomAlgorithm extends Algorithm {

	public FullRandomAlgorithm(String name) {
		super(name);
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		ObjectInfo objInfo = info.getObjects().get(0);
		for (int i = 0; i < info.getCount(); i++) {
			BasicModelData obj = new BasicModelData();
			obj.setPosition(randomizeDouble(objInfo.getPositionSettings().getMinX(), objInfo.getPositionSettings().getMaxX()),
					randomizeDouble(objInfo.getPositionSettings().getMinY(), objInfo.getPositionSettings().getMaxY()),
					randomizeDouble(objInfo.getPositionSettings().getMinZ(), objInfo.getPositionSettings().getMaxZ()));
			
			obj.setRotation(randomizeDouble(objInfo.getRotationSettings().getMinX(), objInfo.getRotationSettings().getMaxX()),
					randomizeDouble(objInfo.getRotationSettings().getMinY(), objInfo.getRotationSettings().getMaxY()),
					randomizeDouble(objInfo.getRotationSettings().getMinZ(), objInfo.getRotationSettings().getMaxZ()));
			
			obj.setScale(randomizeDouble(objInfo.getScaleSettings().getMinX(), objInfo.getScaleSettings().getMaxX()),
					randomizeDouble(objInfo.getScaleSettings().getMinY(), objInfo.getScaleSettings().getMaxY()),
					randomizeDouble(objInfo.getScaleSettings().getMinZ(), objInfo.getScaleSettings().getMaxZ()));
			list.add(new GeneratedObject(info.getObjects().get(0).getModel(), obj));
		}
		return list;
	}

}
