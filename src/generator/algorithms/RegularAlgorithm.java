package generator.algorithms;

import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;

import java.util.ArrayList;
import java.util.List;

public class RegularAlgorithm extends Algorithm {

	public RegularAlgorithm(String name) {
		super(name);
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		PositionSettings positionSettings = info.getObjects().get(0).getPositionSettings();
		double width = positionSettings.getMaxZ() - positionSettings.getMinZ();
		double height = positionSettings.getMaxX() - positionSettings.getMinX();
		double ratio = width / height;

		ObjectInfo objInfo = info.getObjects().get(0);
		int i = info.getCount();
		double x = objInfo.getPositionSettings().getMinX();
		double z = objInfo.getPositionSettings().getMinZ();
		
		double dx=(width/Math.floor(Math.sqrt(i)))/ratio;
		double dz=(height/Math.floor(Math.sqrt(i)))*ratio;
		while (i >= 0) {
			BasicModelData obj = new BasicModelData();
			obj.setPosition(x, randomizeDouble(objInfo.getRotationSettings().getMinY(), objInfo.getRotationSettings().getMaxY()), z);

			obj.setRotation(randomizeDouble(objInfo.getRotationSettings().getMinX(), objInfo.getRotationSettings().getMaxX()),
					randomizeDouble(objInfo.getRotationSettings().getMinY(), objInfo.getRotationSettings().getMaxY()),
					randomizeDouble(objInfo.getRotationSettings().getMinZ(), objInfo.getRotationSettings().getMaxZ()));

			obj.setScale(randomizeDouble(objInfo.getScaleSettings().getMinX(), objInfo.getScaleSettings().getMaxX()),
					randomizeDouble(objInfo.getScaleSettings().getMinY(), objInfo.getScaleSettings().getMaxY()),
					randomizeDouble(objInfo.getScaleSettings().getMinZ(), objInfo.getScaleSettings().getMaxZ()));
			list.add(new GeneratedObject("test", obj));
			i--;
			x +=dx;
			
			if (x > objInfo.getPositionSettings().getMaxX()) {
				x = objInfo.getPositionSettings().getMinX();
				z +=dz;
			}
		}
		return list;
	}

}
