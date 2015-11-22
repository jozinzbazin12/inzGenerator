package generator.algorithms;

import java.util.ArrayList;
import java.util.List;

import generator.Mediator;
import generator.algorithms.models.Cumulative;
import generator.algorithms.models.HeightInfo;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class MaskAlgorithm extends Algorithm {

	private static final int TAB_SIZE = 256;
	private List<List<HeightInfo>> heights;

	public MaskAlgorithm(String name) {
		super(name, PropertiesKeys.MASK_ALGORITHM_HELP);
	}

	private Cumulative findPosition(Cumulative[] cumulative, double value) {
		int i = 0;
		for (; i < TAB_SIZE - 1; i++) {
			if (cumulative[i].getValue() <= value && cumulative[i + 1].getValue() > value) {
				break;
			}
		}
		if (cumulative[i].getPoints() == null) {
			for (; i < TAB_SIZE; i++) {
				if (cumulative[i].getPoints() != null) {
					break;
				}
			}
		}
		return cumulative[i];
	}

	@Override
	public List<GeneratedObject> generationMethod(GenerationInfo info) {
		List<GeneratedObject> list = new ArrayList<GeneratedObject>();

		for (ModelInfo objInfo : info.getModels()) {
			if (objInfo.getMask() == null) {
				continue;
			}
			int count = getCount(objInfo);

			Cumulative[] cumulative = getCumulative(objInfo);

			for (int i = 0; i < count; i++) {
				BasicModelData obj = new BasicModelData();
				PositionSettings pos = objInfo.getPositionSettings();
				setRotation(objInfo, obj);
				setScale(objInfo, obj);
				double rand = randomizeDouble(0, 1);
				Cumulative position = findPosition(cumulative, rand);
				List<HeightInfo> points = position.getPoints();
				setPosition(pos, points, obj, randomizeInt(0, points.size()));

				obj.setRelative(pos.isRelative());

				list.add(new GeneratedObject(objInfo.getModel(), obj));
			}
		}
		return list;
	}

	private Cumulative[] getCumulative(ModelInfo info) {
		heights = findHeights(info.getMask(), Mediator.getMapWidth() / info.getMask().getWidth(),
				Mediator.getMapHeight() / info.getMask().getHeight());
		double total = 0;
		double rate = info.getArgs().get(Consts.RATE);
		Cumulative[] cumulative = new Cumulative[TAB_SIZE];
		for (List<HeightInfo> i : heights) {
			int height = (int) i.get(0).getY();
			if (height != 0) {
				double d = 1 + Math.pow(height, rate);
				total += d;
				cumulative[height] = new Cumulative(d, i);
			}
		}

		for (int i = 0; i < TAB_SIZE; i++) {
			if (cumulative[i] == null) {
				cumulative[i] = new Cumulative(0, null);
			}
			cumulative[i].setValue(cumulative[i].getValue() / total);
		}

		for (int i = 1; i < TAB_SIZE; i++) {
			cumulative[i].setValue(cumulative[i].getValue() + cumulative[i - 1].getValue());
		}
		return cumulative;
	}

}
