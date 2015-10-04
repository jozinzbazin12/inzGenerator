package generator.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import generator.Mediator;
import generator.algorithms.panels.additional.EmptyPanel;
import generator.algorithms.panels.additional.HeightAlgorithmPanel;
import generator.algorithms.panels.main.AlgorithmMainPanel;
import generator.algorithms.panels.main.EmptyMainPanel;
import generator.algorithms.panels.main.RegularAlgorithmMainPanel;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.panels.AbstractPanel;
import generator.utils.PropertiesKeys;

public abstract class Algorithm {
	private Random rnd = new Random();
	private final String helpKey;
	private String name;
	private static final Map<Algorithm, AlgorithmMainPanel> MAIN_PANELS;
	private static final Map<Algorithm, AbstractPanel> ADDITIONAL_PANELS;

	static {
		EmptyPanel emptyPanel = new EmptyPanel();

		ADDITIONAL_PANELS = new HashMap<>();
		FullRandomAlgorithm randomAlgorithm = new FullRandomAlgorithm(Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM));
		HeightAlgorithm height = new HeightAlgorithm(Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM));
		RegularAlgorithm regularAlgorithm = new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM));

		ADDITIONAL_PANELS.put(randomAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(regularAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(height, new HeightAlgorithmPanel());

		EmptyMainPanel emptyMainPanel = new EmptyMainPanel();
		MAIN_PANELS = new HashMap<>();
		MAIN_PANELS.put(randomAlgorithm, emptyMainPanel);
		MAIN_PANELS.put(regularAlgorithm, new RegularAlgorithmMainPanel());
		MAIN_PANELS.put(height, emptyMainPanel);

	}

	protected int getCount(ModelInfo obj) {
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

	protected void setRotation(ModelInfo objInfo, BasicModelData obj) {
		obj.setRotation(randomizeDouble(objInfo.getRotationSettings().getMinX(), objInfo.getRotationSettings().getMaxX()),
				randomizeDouble(objInfo.getRotationSettings().getMinY(), objInfo.getRotationSettings().getMaxY()),
				randomizeDouble(objInfo.getRotationSettings().getMinZ(), objInfo.getRotationSettings().getMaxZ()));
	}

	protected void setScale(ModelInfo objInfo, BasicModelData obj) {
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
		if (min == max) {
			return max;
		}
		return min + (max - min) * rnd.nextDouble();
	}

	protected abstract List<GeneratedObject> generationMethod(GenerationInfo info);

	public List<GeneratedObject> generate(GenerationInfo info) {
		List<GeneratedObject> result = generationMethod(info);
		Mediator.updateModels(result);
		return result;
	}

	public String getHelp() {
		return Mediator.getMessage(helpKey);
	}

	public static Map<Algorithm, AlgorithmMainPanel> getMainPanels() {
		return MAIN_PANELS;
	}

	public static Map<Algorithm, AbstractPanel> getAdditionalPanels() {
		return ADDITIONAL_PANELS;
	}

}
