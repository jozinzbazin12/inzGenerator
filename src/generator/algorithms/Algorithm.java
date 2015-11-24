package generator.algorithms;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import generator.Mediator;
import generator.algorithms.models.HeightInfo;
import generator.algorithms.models.TreeNode;
import generator.algorithms.panels.additional.AlgorithmAdditionalPanel;
import generator.algorithms.panels.additional.EmptyPanel;
import generator.algorithms.panels.additional.HeightAlgorithmPanel;
import generator.algorithms.panels.additional.MaskAlgorithmPanel;
import generator.algorithms.panels.additional.SpreadAlgorithmPanel;
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
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import generator.utils.WindowUtil;

public abstract class Algorithm implements Comparable<Algorithm> {
	private static final Map<Algorithm, AlgorithmAdditionalPanel> ADDITIONAL_PANELS;
	private static final Map<Algorithm, AlgorithmMainPanel> MAIN_PANELS;
	protected TreeNode collisionTree;
	private final String helpKey;
	private String name;
	private Random rnd = new Random();
	protected double xRatio;
	protected double yRatio;
	protected double zRatio;
	protected boolean collisions;
	protected int webFactor;
	private boolean collisionDetected = false;

	static {
		EmptyPanel emptyPanel = new EmptyPanel();

		ADDITIONAL_PANELS = new HashMap<>();
		Algorithm randomAlgorithm = new FullRandomAlgorithm(Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM));
		Algorithm height = new HeightAlgorithm(Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM));
		Algorithm regularAlgorithm = new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM));
		Algorithm aggregation = new AggregationAlgorithm(Mediator.getMessage(PropertiesKeys.AGGREGATION_ALGORITHM));
		Algorithm spread = new SpreadAlgorithm(Mediator.getMessage(PropertiesKeys.SPREAD_ALGORITHM));
		Algorithm mask = new MaskAlgorithm(Mediator.getMessage(PropertiesKeys.MASK_ALGORITHM));

		ADDITIONAL_PANELS.put(randomAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(regularAlgorithm, emptyPanel);
		ADDITIONAL_PANELS.put(height, new HeightAlgorithmPanel());
		ADDITIONAL_PANELS.put(aggregation, emptyPanel);
		ADDITIONAL_PANELS.put(spread, new SpreadAlgorithmPanel());
		ADDITIONAL_PANELS.put(mask, new MaskAlgorithmPanel());

		EmptyMainPanel emptyMainPanel = new EmptyMainPanel();
		MAIN_PANELS = new HashMap<>();
		MAIN_PANELS.put(randomAlgorithm, emptyMainPanel);
		MAIN_PANELS.put(regularAlgorithm, new RegularAlgorithmMainPanel());
		MAIN_PANELS.put(height, emptyMainPanel);
		MAIN_PANELS.put(aggregation, new AggregationAlgorithmPanel());
		MAIN_PANELS.put(spread, emptyMainPanel);
		MAIN_PANELS.put(mask, emptyMainPanel);

	}

	public Algorithm(String name, String key) {
		this.name = name;
		helpKey = key;
	}

	public static Map<Algorithm, AlgorithmAdditionalPanel> getAdditionalPanels() {
		return ADDITIONAL_PANELS;
	}

	public static Map<Algorithm, AlgorithmMainPanel> getMainPanels() {
		return MAIN_PANELS;
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
					double x = (width / 2 - i) * xr;
					double z = (height / 2 - j) * zr;
					list.add(new HeightInfo(x, y, z));
				}
			}
		}
		return list;
	}

	@Override
	public int compareTo(Algorithm o) {
		return name.compareTo(o.name);
	}

	protected void correctPosition(BasicModelData obj, ModelInfo info, List<HeightInfo> positions) {
		PositionSettings pos = info.getPositionSettings();
		double xVar = xRatio;
		double zVar = zRatio;
		double x = obj.getX();
		double z = obj.getZ();
		if (!positions.isEmpty() || collisionTree != null) {
			HeightInfo actual = new HeightInfo(obj.getX(), 0, obj.getZ());
			HeightInfo nearest = new HeightInfo(Double.MAX_VALUE, 0, Double.MAX_VALUE);
			if (!positions.isEmpty()) {
				nearest = findNearest(positions, actual, nearest);
				x = nearest.getX();
				z = nearest.getZ();
			}
			if (collisionTree != null) {
				actual.setSx(obj.getSx());
				actual.setSz(obj.getSz());
				TreeNode place = collisionTree.findPlace(actual);
				if (place != null) {
					if (place.getState() != TreeNode.State.EMPTY) {
						System.out.println("ss");
					}
					x = place.getMid()[0];
					z = place.getMid()[1];
					double nodeRange = place.getRange();
					xVar = 0;//nodeRange - actual.getSx();
					zVar =0;// nodeRange - actual.getSz();
					TreeNode.mark(place);
				} else {
					collisionDetected = true;
				}
			}
		}
		obj.setX(x + randomizeDouble(-xVar, xVar));
		obj.setZ(z + randomizeDouble(-zVar, zVar));
		correctPosition(obj, pos);
	}

	private HeightInfo findNearest(List<HeightInfo> positions, HeightInfo actual, HeightInfo nearest) {
		double minLength = Double.MAX_VALUE;
		Collections.shuffle(positions);
		for (HeightInfo i : positions) {
			if (i.equals(actual)) {
				return i;
			}
			double tempLenght = getLength(i.getX(), nearest.getX(), i.getZ(), nearest.getZ());
			if (minLength < 0.5) {
				break;
			}
			if (tempLenght < minLength) {
				minLength = tempLenght;
				nearest = i;
			}
		}
		return nearest;
	}

	protected void correctPosition(BasicModelData obj, PositionSettings pos) {
		if (obj.getX() > pos.getMaxX()) {
			obj.setX(pos.getMaxX() - randomizeDouble(0, xRatio));
		}
		if (obj.getX() < pos.getMinX()) {
			obj.setX(pos.getMinX() + randomizeDouble(0, xRatio));
		}
		if (obj.getZ() > pos.getMaxZ()) {
			obj.setZ(pos.getMaxZ() - randomizeDouble(0, zRatio));
		}
		if (obj.getZ() < pos.getMinZ()) {
			obj.setZ(pos.getMinZ() + randomizeDouble(0, zRatio));
		}
	}

	protected List<List<HeightInfo>> findHeights() {
		return findHeights(Mediator.getMapImage(), xRatio, zRatio);
	}

	protected List<List<HeightInfo>> findHeights(BufferedImage img, double dx, double dz) {
		Map<Double, List<HeightInfo>> heights = new HashMap<>();
		int width = img.getWidth();
		int height = img.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double y = PreviewPanel.getColor(img, i, j);
				List<HeightInfo> list = heights.get(y);
				if (list == null) {
					list = new ArrayList<>();
					heights.put(y, list);
				}
				double x = (width / 2 - i) * dx;
				double z = (height / 2 - j) * dz;
				list.add(new HeightInfo(x, y, z));
			}
		}
		List<List<HeightInfo>> points = new ArrayList<>(heights.values());
		points.sort(new Comparator<List<HeightInfo>>() {

			@Override
			public int compare(List<HeightInfo> o1, List<HeightInfo> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});
		return points;
	}

	public List<GeneratedObject> generate(GenerationInfo info) {
		int collisionsValue = info.getArgs().get(Consts.COLLISIONS).intValue();
		collisions = collisionsValue == 1 ? true : false;
		webFactor = 1;
		if (collisions) {
			webFactor = info.getArgs().get(Consts.WEB_SCALE).intValue() - 1;
			collisionTree = TreeNode.createTree(Mediator.getMapWidth(), Mediator.getMapHeight(),
					(short) (Math.log(Mediator.getMapDimensions().getWidth()) / Math.log(2) + webFactor));
		}
		xRatio = Mediator.getMapWidth() / Mediator.getMapDimensions().width;
		zRatio = Mediator.getMapHeight() / Mediator.getMapDimensions().height;
		yRatio = Mediator.getMapMaxYSetting() / Mediator.getMapMaxY();
		HeightInfo.setThreshold((xRatio + zRatio) / 2.0);

		List<GeneratedObject> result = generationMethod(info);
		Mediator.updateModels(result);
		collisionTree = null;
		if (collisionDetected) {
			collisionDetected = false;
			WindowUtil.displayError(PropertiesKeys.COLLISION);
		}
		return result;
	}

	protected abstract List<GeneratedObject> generationMethod(GenerationInfo info);

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

	public String getHelp() {
		return Mediator.getMessage(helpKey);
	}

	protected double getLength(double x, double z, double x2, double z2) {
		return Math.sqrt(Math.pow((x - x2), 2) + Math.pow((z - z2), 2));
	}

	protected double randomizeDouble(double min, double max) {
		if (min == max) {
			return max;
		}
		return min + (max - min) * rnd.nextDouble();
	}

	protected int randomizeInt(int min, int max) {
		if (min == max) {
			return min;
		}
		return rnd.nextInt(Math.abs(max - min)) + min;
	}

	protected double randomizeRange(double min, double max) {
		double min2 = min;
		double max2 = max;
		if (rnd.nextBoolean()) {
			min2 *= -1;
			max2 *= -1;
		}
		return randomizeDouble(min2, max2);
	}

	protected void setPosition(PositionSettings pos, List<HeightInfo> heights, BasicModelData obj, int heightPos) {
		HeightInfo height = heights.get(heightPos);
		obj.setPosition(height.getX() + randomizeDouble(-xRatio, xRatio), randomizeDouble(pos.getMinY(), pos.getMaxY()),
				height.getZ() + randomizeDouble(-zRatio, zRatio));
		correctPosition(obj, pos);
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

	@Override
	public String toString() {
		return name;
	}
}
