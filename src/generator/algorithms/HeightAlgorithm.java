package generator.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import generator.algorithms.models.HeightInfo;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class HeightAlgorithm extends Algorithm {

	private List<List<HeightInfo>> includedPoints = new ArrayList<>();

	public HeightAlgorithm(String name) {
		super(name, PropertiesKeys.HEIGHT_ALGORITHM_HELP);
	}

	private int find(double v) {
		int size = includedPoints.size();
		int pos = size / 2;
		int lastPos = -1;
		int sectionSize = size / 4;
		double y = includedPoints.get(pos).get(0).getY();
		while (y != v) {
			if (y == v) {
				break;
			}
			if (y > v) {
				pos -= sectionSize;
			} else {
				pos += sectionSize;
			}

			if (pos >= includedPoints.size()) {
				pos = lastPos;
			}
			if (pos < 0) {
				pos = 0;
			}
			y = includedPoints.get(pos).get(0).getY();
			sectionSize = (int) Math.ceil(sectionSize / 2.0);
			if (sectionSize == 1) {
				break;
			}
			lastPos = pos;
		}
		return pos;
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();
		includedPoints = findHeights();
		for (ModelInfo objInfo : info.getModels()) {
			int count = getCount(objInfo);
			PositionSettings pos = objInfo.getPositionSettings();
			List<HeightInfo> heights = getHeights(objInfo);
			if (!heights.isEmpty()) {
				for (int i = 0; i < count; i++) {
					BasicModelData obj = new BasicModelData();
					setRotation(objInfo, obj);
					setScale(objInfo, obj);
					int heightPos = randomizeInt(0, heights.size());
					setPosition(pos, heights, obj, heightPos);
					obj.setRelative(pos.isRelative());
					list.add(new GeneratedObject(objInfo.getModel(), obj));
				}
			}
		}
		return list;
	}

	private List<HeightInfo> getHeights(ModelInfo info) {
		double min = info.getArgs().get(Consts.MIN_Y_HEIGHT);
		double max = info.getArgs().get(Consts.MAX_Y_HEIGHT);
		int minPos = find(min);
		int maxPos = find(max);
		if (maxPos < includedPoints.size() - 1) {
			maxPos++;
		} else if (minPos > 1) {
			minPos--;
		}

		List<List<HeightInfo>> list = includedPoints.subList(minPos, maxPos);
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
				result = result2;
			}
		} else {
			PositionSettings positionSettings = info.getPositionSettings();
			List<HeightInfo> result2 = new ArrayList<>();
			for (HeightInfo i : result) {
				if (i.getX() <= positionSettings.getMaxX() && i.getX() >= positionSettings.getMinX()
						&& i.getZ() <= positionSettings.getMaxZ() && i.getZ() >= positionSettings.getMinZ()) {
					result2.add(i);
				}
			}
			result = result2;
		}
		return result;
	}

}
