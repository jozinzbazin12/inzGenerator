package generator.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import generator.Mediator;
import generator.algorithms.models.HeightInfo;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class HeightAlgorithm extends Algorithm {

	private List<List<HeightInfo>> incudedPoints = new ArrayList<>();
	private double yRatio;

	public HeightAlgorithm(String name) {
		super(name, PropertiesKeys.HEIGHT_ALGORITHM_HELP);
	}

	private void findHeights(GenerationInfo info) {
		Map<Double, List<HeightInfo>> heights = new HashMap<>();
		for (int i = 0; i < Mediator.getMapDimensions().width; i++) {
			for (int j = 0; j < Mediator.getMapDimensions().height; j++) {
				double height = Mediator.getMapHeightAt(i, j);
				List<HeightInfo> list = heights.get(height);
				if (list == null) {
					list = new ArrayList<>();
					heights.put(height, list);
				}
				list.add(new HeightInfo(i, height, j));
			}
		}
		incudedPoints = new ArrayList<>(heights.values());
		incudedPoints.sort(new Comparator<List<HeightInfo>>() {

			@Override
			public int compare(List<HeightInfo> o1, List<HeightInfo> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});
	}

	private List<HeightInfo> getHeights(ModelInfo info) {
		double min = info.getArgs().get(Consts.MIN_Y_HEIGHT);
		double max = info.getArgs().get(Consts.MAX_Y_HEIGHT);
		int minPos = find(min / yRatio);
		int maxPos = find(max / yRatio);
		// double min2 = incudedPoints.get(minPos).get(0).getY();
		// double max2 = incudedPoints.get(maxPos).get(0).getY();
		// if (min2 - min > yRatio || max2 - max > yRatio) {
		// return null;
		// }
		if (maxPos < incudedPoints.size() - 1) {
			maxPos++;
		} else if (minPos > 1) {
			minPos--;
		}
		List<List<HeightInfo>> list = incudedPoints.subList(minPos, maxPos);
		List<HeightInfo> result = new ArrayList<>();
		for (List<HeightInfo> i : list) {
			result.addAll(i);
		}
		return result;
	}

	private int find(double v) {
		int size = incudedPoints.size();
		int pos = size / 2;
		int lastPos = -1;
		int sectionSize = size / 4;
		double y = incudedPoints.get(pos).get(0).getY();
		while (y != v) {
			if (y == v) {
				break;
			}
			if (y > v) {
				pos -= sectionSize;
			} else {
				pos += sectionSize;
			}

			if (pos >= incudedPoints.size()) {
				pos = lastPos;
			}
			if (pos < 0) {
				pos = 0;
			}
			y = incudedPoints.get(pos).get(0).getY();
			sectionSize /= 2;
			if (sectionSize == 0) {
				break;
			}
			lastPos = pos;
		}
		return pos;
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		double xRatio = Mediator.getMapWidth() / Mediator.getMapDimensions().width;
		double zRatio = Mediator.getMapHeight() / Mediator.getMapDimensions().height;
		yRatio = Mediator.getMapMaxYSetting() / Mediator.getMapMaxY();
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		findHeights(info);
		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			PositionSettings pos = objInfo.getPositionSettings();
			List<HeightInfo> heights = getHeights(objInfo);
			if (heights != null) {
				for (int i = 0; i < count; i++) {
					BasicModelData obj = new BasicModelData();
					int heightPos = (int) randomizeDouble(0, heights.size());
					if (heightPos > 0) {
						HeightInfo height = heights.get(heightPos);
						double x = (height.getX() - Mediator.getMapDimensions().width / 2) * xRatio;
						double z = (Mediator.getMapDimensions().height / 2 - height.getZ()) * zRatio;
						obj.setPosition(x + randomizeDouble(-xRatio, xRatio), randomizeDouble(pos.getMinY(), pos.getMaxY()),
								z + randomizeDouble(-zRatio, zRatio));
						obj.setRelative(pos.isRelative());
						setRotation(objInfo, obj);
						setScale(objInfo, obj);
						list.add(new GeneratedObject(objInfo.getModel(), obj));
					}
				}
			}
		}
		return list;
	}

}
