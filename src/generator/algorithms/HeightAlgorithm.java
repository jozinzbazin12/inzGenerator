package generator.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
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

	public HeightAlgorithm(String name) {
		super(name, PropertiesKeys.HEIGHT_ALGORITHM_HELP);
	}

	private void findHeights(GenerationInfo info) {
		Map<Double, List<HeightInfo>> heights = new HashMap<>();
		int width = Mediator.getMapDimensions().width;
		int height = Mediator.getMapDimensions().height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double y = Mediator.getMapHeightAt(i, j);
				List<HeightInfo> list = heights.get(y);
				if (list == null) {
					list = new ArrayList<>();
					heights.put(y, list);
				}
				double x = (i - width / 2) * xRatio;
				double z = (height / 2 - j) * zRatio;
				list.add(new HeightInfo(x, y, z));
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
		if (info.getMask() != null) {
			List<HeightInfo> availableSpace = availableSpace(info);
			List<HeightInfo> result2 = new LinkedList<>();
			if (!availableSpace.isEmpty()) {
				for (HeightInfo i : result) {
					if (availableSpace.contains(i)) {
						result2.add(i);
					}
				}
				return result2;
			}
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
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		findHeights(info);
		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			PositionSettings pos = objInfo.getPositionSettings();
			List<HeightInfo> heights = getHeights(objInfo);
			if (!heights.isEmpty()) {
				for (int i = 0; i < count; i++) {
					BasicModelData obj = new BasicModelData();
					int heightPos = randomizeInt(0, heights.size());
					setPosition(pos, heights, obj, heightPos);
					obj.setRelative(pos.isRelative());
					setRotation(objInfo, obj);
					setScale(objInfo, obj);
					list.add(new GeneratedObject(objInfo.getModel(), obj));
				}
			}
		}
		return list;
	}

}
