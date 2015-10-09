package generator.algorithms;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import generator.Mediator;
import generator.algorithms.models.HeightInfo;
import generator.algorithms.panels.additional.AlgorithmAdditionalPanel;
import generator.algorithms.panels.additional.EmptyPanel;
import generator.algorithms.panels.additional.HeightAlgorithmPanel;
import generator.algorithms.panels.main.AggregationAlgorithmPanel;
import generator.algorithms.panels.main.AlgorithmMainPanel;
import generator.algorithms.panels.main.EmptyMainPanel;
import generator.algorithms.panels.main.RegularAlgorithmMainPanel;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.panels.PreviewPanel;
import generator.utils.PropertiesKeys;

public abstract class Algorithm {
	private Random rnd = new Random();
	private final String helpKey;
	private String name;
	protected double xRatio;
	protected double zRatio;
	protected double yRatio;
	private static final Map<Algorithm, AlgorithmMainPanel> MAIN_PANELS;
	private static final Map<Algorithm, AlgorithmAdditionalPanel> ADDITIONAL_PANELS;

	static {
		EmptyPanel emptyPanel = new EmptyPanel();

		ADDITIONAL_PANELS = new HashMap<>();
		FullRandomAlgorithm randomAlgorithm = new FullRandomAlgorithm(Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM));
		HeightAlgorithm height = new HeightAlgorithm(Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM));
		RegularAlgorithm regularAlgorithm = new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM));
		AggregationAlgorithm aggregation = new AggregationAlgorithm(Mediator.getMessage(PropertiesKeys.AGGREGATION_ALGORITHM));

		ADDITIONAL_PANELS.put(randomAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(regularAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(height, new HeightAlgorithmPanel());
		ADDITIONAL_PANELS.put(aggregation, emptyPanel);

		EmptyMainPanel emptyMainPanel = new EmptyMainPanel();
		MAIN_PANELS = new HashMap<>();
		MAIN_PANELS.put(randomAlgorithm, emptyMainPanel);
		MAIN_PANELS.put(regularAlgorithm, new RegularAlgorithmMainPanel());
		MAIN_PANELS.put(height, emptyMainPanel);
		MAIN_PANELS.put(aggregation, new AggregationAlgorithmPanel());

	}

	protected double getLength(double x, double z, double x2, double z2) {
		return Math.sqrt(Math.pow((x - x2), 2) + Math.pow((z - z2), 2));
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

	protected double randomizeSquareDouble(double min, double max) {
		return max - randomizeDouble(min, 2 * max);
	}

	protected int randomizeInt(int min, int max) {
		return rnd.nextInt(min + max) + min;
	}

	protected List<HeightInfo> availableSpace(ModelInfo info) {
		List<HeightInfo> list = new LinkedList<>();
		BufferedImage mask = info.getMask();
		if (mask == null) {
			return list;
		}
		int width = mask.getWidth();
		int height = mask.getHeight();
		double xr = Mediator.getMapWidth() / width;
		double zr = Mediator.getMapHeight() / height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double y = PreviewPanel.getColor(mask, i, j);
				if (y >= 128) {
					double x = (i - width / 2) * xr;
					double z = (height / 2 - j) * zr;
					list.add(new HeightInfo(x, y, z));
				}
			}
		}
		return list;
	}

	protected abstract List<GeneratedObject> generationMethod(GenerationInfo info);

	public List<GeneratedObject> generate(GenerationInfo info) {
		xRatio = Mediator.getMapWidth() / Mediator.getMapDimensions().width;
		zRatio = Mediator.getMapHeight() / Mediator.getMapDimensions().height;
		yRatio = Mediator.getMapMaxYSetting() / Mediator.getMapMaxY();
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

	public static Map<Algorithm, AlgorithmAdditionalPanel> getAdditionalPanels() {
		return ADDITIONAL_PANELS;
	}

	protected void setPosition(PositionSettings pos, List<HeightInfo> heights, BasicModelData obj, int heightPos) {
		HeightInfo height = heights.get(heightPos);
		obj.setPosition(height.getX() + randomizeDouble(-xRatio, xRatio), randomizeDouble(pos.getMinY(), pos.getMaxY()),
				height.getZ() + randomizeDouble(-zRatio, zRatio));
	}

}
