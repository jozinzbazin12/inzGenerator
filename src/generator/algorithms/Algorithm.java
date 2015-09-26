package generator.algorithms;

import java.util.List;
import java.util.Random;

import generator.Mediator;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;

public abstract class Algorithm {
	private Random rnd = new Random();
	private final String helpKey;
	private String name;

	protected int getCount(ObjectInfo obj) {
		int count = 0;
		int max = obj.getMaxCount();
		int min = obj.getMinCount();
		if (max > 0) {
			if (max == min) {
				count = max;
			} else {
				count = rnd.nextInt(max - min) + min + 1;
			}
		}
		return count;
	}

	protected void setRotation(ObjectInfo objInfo, BasicModelData obj) {
		obj.setRotation(randomizeDouble(objInfo.getRotationSettings().getMinX(), objInfo.getRotationSettings().getMaxX()),
				randomizeDouble(objInfo.getRotationSettings().getMinY(), objInfo.getRotationSettings().getMaxY()),
				randomizeDouble(objInfo.getRotationSettings().getMinZ(), objInfo.getRotationSettings().getMaxZ()));
	}

	protected void setScale(ObjectInfo objInfo, BasicModelData obj) {
		double scaleX = randomizeDouble(objInfo.getScaleSettings().getMinX(), objInfo.getScaleSettings().getMaxX());
		if (objInfo.getScaleSettings().isEqual()) {
			obj.setScale(scaleX, scaleX, scaleX);
		} else {
			obj.setScale(scaleX, randomizeDouble(objInfo.getScaleSettings().getMinY(), objInfo.getScaleSettings().getMaxY()),
					randomizeDouble(objInfo.getScaleSettings().getMinZ(), objInfo.getScaleSettings().getMaxZ()));
		}
	}

	public Algorithm(String name, String key) {
		this.name = name;
		helpKey = key;
	}

	@Override
	public String toString() {
		return name;
	}

	protected double randomizeDouble(double min, double max) {
		return min + (max - min) * rnd.nextDouble();
	}

	protected abstract List<GeneratedObject> generationMethod(GenerationInfo info);

	public List<GeneratedObject> generate(GenerationInfo info) {
		List<GeneratedObject> result = generationMethod(info);
		Mediator.updateObjectList(result);
		return result;
	}

	public String getHelp() {
		return Mediator.getMessage(helpKey);
	}

}
