package generator;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generator.algorithms.Algorithm;
import generator.models.MyFile;
import generator.models.generation.GenerationInfo;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.result.GeneratedObject;
import generator.models.result.MapObject;
import generator.models.result.Material;
import generator.models.result.ResultObject;
import generator.models.result.Texture;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.panels.ThirdTabPanel;
import generator.utils.PropertiesKeys;
import generator.utils.WindowUtil;
import generator.windows.MainWindow;
import generator.windows.ModelWindow;
import generator.windows.ObjectWindow;

public class Mediator {
	private static final String UNIX = "unix";
	private static final String LINUX = "linux";
	private static final String OS_NAME = "os.name";
	private static FirstTabPanel firstTabPanel;
	private static final String GENERATOR_MODELS_RESULT = "generator.models.result";
	private static final String LANGUAGE_KEY = "language";
	private static String lastPath;
	private static Locale locale;
	private static MainWindow mainWindow;
	private static ResourceBundle messages;
	private static Map<String, ModelInfo> models = new HashMap<>();
	private static ModelWindow modelWindow;
	private static ObjectWindow objectWindow;
	private static Properties properties;
	private static final String PROPERTIES_FILE = "config.properties";
	private static ResultObject resultObject = new ResultObject();
	private static SecondTabPanel secondTabPanel;
	private static ThirdTabPanel thirdTabPanel;
	private static Path root;
	private static final String XML = ".xml";
	private static boolean linux;

	public static void changeLocale(Locale locale) {
		properties.setProperty(LANGUAGE_KEY, locale.getLanguage());
		try {
			int dialogResult = WindowUtil.displayConfirm(PropertiesKeys.CHANGE_LANGUAGE_WARNING);
			if (dialogResult == JOptionPane.YES_OPTION) {
				saveProperties();
				mainWindow.dispose();
				main(null);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static void changeModelFileName(File path) {
		models.remove(modelWindow.getCurrentModel().getModel().getPath());
		models.put(path.getAbsolutePath(), modelWindow.changeFile(new MyFile(path)));
	}

	private static void createProperties() throws IOException {
		properties.setProperty(LANGUAGE_KEY, "pl");
		saveProperties();
	}

	public static int find(int[] tab, int index) {
		for (int i : tab) {
			if (i == index) {
				return index;
			}
		}
		return -1;
	}

	public static Algorithm getAlgorithm() {
		return secondTabPanel.getAlgorithm();
	}

	public static Map<String, Double> getAlgorithmArgs() {
		return secondTabPanel.getArgs();
	}

	public static double getGeneratedObjectArguments(String key) {
		return (double) objectWindow.getArguments().get(key).value();
	}

	public static List<GeneratedObject> getGeneratedObjects() {
		return thirdTabPanel.getSelectedRows();
	}

	public static double getGenerationInfoArguments(String key) {
		return (double) firstTabPanel.getArguments().get(key).value();
	}

	public static String getLastPath() {
		return lastPath;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static Dimension getMapDimensions() {
		return thirdTabPanel.getImageSize();
	}

	public static double getMapHeight() {
		return firstTabPanel.getMapSettings().getLengthZ();
	}

	public static double getMapHeightAt(int x, int y) {
		return thirdTabPanel.getMapHeight(x, y);
	}

	public static BufferedImage getMapImage() {
		return thirdTabPanel.getMap();
	}

	public static double getMapMaxY() {
		return thirdTabPanel.getMapMaxY();
	}

	public static double getMapMaxYSetting() {
		return firstTabPanel.getMapSettings().getLengthY();
	}

	public static double getMapWidth() {
		return firstTabPanel.getMapSettings().getLengthX();
	}

	public static String getMessage(String message) {
		String result;
		try {
			result = messages.getString(message);
		} catch (MissingResourceException e) {
			result = "???" + message;
		}
		return result;
	}

	public static Map<String, ModelInfo> getModels() {
		return models;
	}

	public static ResultObject getResultObject() {
		return resultObject;
	}

	public static void highlight(GeneratedObject obj) {
		thirdTabPanel.highlight(obj);
	}

	public static void loadModel(File path) {
		if (models.get(path.getAbsolutePath()) == null) {
			MyFile file = new MyFile(path);
			GenerationModel model = new GenerationModel(file);
			ModelInfo obj = new ModelInfo(model);
			setDefaultObjectSettings(obj);
			models.put(file.getAbsolutePath(), obj);
		} else {
			WindowUtil.displayError(PropertiesKeys.MODEL_ALREADY_EXISTS_ERROR);
		}
	}

	private static void setDefaultObjectSettings(ModelInfo obj) {
		PositionSettings pos = obj.getPositionSettings();
		double mapWidth = getMapWidth() / 2.0;
		pos.setMinX(-mapWidth);
		pos.setMaxX(mapWidth);
		double mapHeight = getMapHeight() / 2.0;
		pos.setMinZ(-mapHeight);
		pos.setMaxZ(mapHeight);

		RotationSettings rot = obj.getRotationSettings();
		rot.setMinY(-180);
		rot.setMaxY(180);
	}

	public static void loadXMLFile(File file) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(GENERATOR_MODELS_RESULT);
			Unmarshaller um = context.createUnmarshaller();
			resultObject = (ResultObject) um.unmarshal(file);
		} catch (JAXBException e) {
			WindowUtil.displayError(PropertiesKeys.LOAD_XML_ERROR);
			e.printStackTrace();
			return;
		}
		Map<String, Double> args = resultObject.getGenerationInfo().getArgs();
		if (args != null) {
			secondTabPanel.setArgs(args);
		}
		models.clear();
		List<ModelInfo> objects = resultObject.getGenerationInfo().getModels();
		if (objects != null) {
			for (ModelInfo i : objects) {
				models.put(i.getModel().getPath().getKey(), i);
			}
		}
		for (GeneratedObject i : resultObject.getGeneratedObjects()) {
			ModelInfo objectInfo = models.get(i.getObjectPath());
			GenerationModel model;
			if (objectInfo != null) {
				model = objectInfo.getModel();
			} else {
				model = new GenerationModel(new MyFile(i.getObjectPath()));
			}
			ModelInfo obj = new ModelInfo(model);
			if (objectInfo == null) {
				models.put(i.getObjectPath(), obj);
				i.setModel(model);
			} else {
				i.setModel(objectInfo.getModel());
			}
		}
		MapObject mapObject = resultObject.getMapObject();
		setMapFile(mapObject.getMapFileName());
		firstTabPanel.setMapObject(mapObject);
		firstTabPanel.setMapProperties(mapObject.getMapFileName().getAbsolutePath(), thirdTabPanel.getImageSize());
		secondTabPanel.updateModels(models.values());
		updateModels(resultObject.getGeneratedObjects());
		thirdTabPanel.updateObjects(resultObject.getGeneratedObjects());
	}

	private static BufferedImage openImage(File mapObject) throws IOException {
		BufferedImage image = ImageIO.read(mapObject);
		if (image == null) {
			throw new IOException();
		}
		return image;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		String name = System.getProperty(OS_NAME).toLowerCase();
		linux = name.contains(LINUX) || name.contains(UNIX);
		root = Paths.get("").toAbsolutePath();
		properties = new Properties();
		lastPath = root.toString();
		InputStream input;
		try {
			input = new FileInputStream(PROPERTIES_FILE);
			properties.load(input);
			locale = new Locale(properties.getProperty(LANGUAGE_KEY));
		} catch (IOException e) {
			createProperties();
			main(null);
		}

		messages = ResourceBundle.getBundle(PropertiesKeys.PROPERTIES_FILE, locale);
		MainWindow mainWindow = new MainWindow(messages.getString(PropertiesKeys.WINDOW_NAME));
		Mediator.register(mainWindow);
		Algorithm.init();
		mainWindow.createWindow();
	}

	public static void refreshPreview() {
		thirdTabPanel.updateObjects(resultObject.getGeneratedObjects());
		thirdTabPanel.refreshPreview();
	}

	public static void register(MainWindow mw) {
		mainWindow = mw;
	}

	public static void registerFirstTabPanel(FirstTabPanel panel) {
		firstTabPanel = panel;
	}

	public static void registerModelWindow(ModelWindow mw) {
		modelWindow = mw;
	}

	public static void registerObjectWindow(ObjectWindow window) {
		objectWindow = window;
	}

	public static void registerSecondTabPanel(SecondTabPanel secondPanel) {
		secondTabPanel = secondPanel;
	}

	public static void registerThirdTabPanel(ThirdTabPanel thridPanel) {
		thirdTabPanel = thridPanel;
	}

	private static void saveProperties() throws FileNotFoundException, IOException {
		OutputStream output = new FileOutputStream(PROPERTIES_FILE);
		properties.store(output, null);
	}

	public static void saveXMLFile(String name) {
		JAXBContext context;
		MapObject mapObject = resultObject.getMapObject();
		mapObject.setBasic(firstTabPanel.getMapSettings());
		mapObject.setLightData(firstTabPanel.getLightSettings());
		mapObject.setMaterial(firstTabPanel.getMaterial());
		GenerationInfo generationInfo = resultObject.getGenerationInfo();
		generationInfo.setModels(new ArrayList<>(models.values()));
		generationInfo.setArgs(secondTabPanel.getArgs());
		try {
			context = JAXBContext.newInstance(GENERATOR_MODELS_RESULT);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream os = new FileOutputStream(new File(name.endsWith(XML) ? name : name + XML));
			marshaller.marshal(resultObject, os);
			os.close();
			WindowUtil.displayInfo(PropertiesKeys.SAVE_XML_SUCCESS);
		} catch (JAXBException | IOException e) {
			WindowUtil.displayError(PropertiesKeys.SAVE_XML_ERROR);
			e.printStackTrace();
		}
	}

	public static void setClicked(GeneratedObject obj) {
		thirdTabPanel.click(obj);
	}

	public static void setLastPath(String lastPath) {
		Mediator.lastPath = lastPath;
	}

	public static Dimension setMapFile(MyFile file) {
		try {
			BufferedImage img = openImage(file);
			firstTabPanel.addPreview(img);
			thirdTabPanel.addPreview(img);
			String path = file.getAbsolutePath();
			resultObject.getMapObject().setMapFileName(path);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			WindowUtil.displayError(PropertiesKeys.FILE_NOT_IMAGE);
		}
		Dimension imageSize = thirdTabPanel.getImageSize();
		return imageSize;
	}

	public static void setResultObject(ResultObject resultObject) {
		Mediator.resultObject = resultObject;
	}

	public static void setTextureFile(String path) {
		MapObject mapObject = resultObject.getMapObject();
		Material material = mapObject.getMaterial();
		if (material == null) {
			material = new Material();
			mapObject.setMaterial(material);
		}
		Texture texture = material.getTexture();
		if (texture == null) {
			texture = new Texture();
			mapObject.getMaterial().setTexture(texture);
		}

		texture.setPath(new MyFile(path));

		mainWindow.getContentPane().revalidate();
	}

	public static void unHighlight() {
		thirdTabPanel.unHighlight();
	}

	public static void unregisterObjectWindow() {
		objectWindow = null;
	}

	public static void updateModels(List<GeneratedObject> objects) {
		thirdTabPanel.updateObjects(objects);
	}

	public static void updateObjects() {
		thirdTabPanel.updateObjects(resultObject.getGeneratedObjects());

	}

	public static Path getRoot() {
		return root;
	}

	public static boolean isLinux() {
		return linux;
	}
}