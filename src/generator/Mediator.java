package generator;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generator.algorithms.Algorithm;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.result.GeneratedObject;
import generator.models.result.MapObject;
import generator.models.result.Material;
import generator.models.result.ResultObject;
import generator.models.result.Texture;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.panels.ThirdTabPanel;
import generator.utils.PropertiesKeys;
import generator.windows.MainWindow;
import generator.windows.ModelWindow;
import generator.windows.ObjectWindow;

public class Mediator {
	private static final String PROPERTIES_FILE = "config.properties";
	private static final String LANGUAGE_KEY = "language";
	private static MainWindow mainWindow;
	private static SecondTabPanel secondTabPanel;
	private static ThirdTabPanel thirdTabPanel;
	private static ResourceBundle messages;
	private static FirstTabPanel firstTabPanel;
	private static ResultObject resultObject = new ResultObject();
	private static Properties properties;
	private static Locale locale;
	private static ObjectWindow objectWindow;
	private static Map<String, ModelInfo> models = new HashMap<>();
	private static ModelWindow modelWindow;
	private static String lastPath = System.getProperty("user.home") + "/Desktop";

	public static void main(String[] args) throws IOException {
		properties = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(PROPERTIES_FILE);
			properties.load(input);
			locale = new Locale(properties.getProperty(LANGUAGE_KEY));
		} catch (IOException e) {
			createProperties();
		}

		messages = ResourceBundle.getBundle(PropertiesKeys.PROPERTIES_FILE, locale);
		MainWindow mainWindow = new MainWindow(messages.getString(PropertiesKeys.WINDOW_NAME));
		Mediator.register(mainWindow);
		mainWindow.createWindow();
	}

	private static void createProperties() throws IOException {
		properties.setProperty(LANGUAGE_KEY, "pl");
		saveProperties();
	}

	private static void saveProperties() throws FileNotFoundException, IOException {
		OutputStream output = new FileOutputStream(PROPERTIES_FILE);
		properties.store(output, null);
	}

	public static void changeLocale(Locale locale) {
		properties.setProperty(LANGUAGE_KEY, locale.getLanguage());
		try {
			int dialogResult = JOptionPane.showConfirmDialog(null, getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING),
					getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
				saveProperties();
				mainWindow.dispose();
				main(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register(MainWindow mw) {
		mainWindow = mw;
	}

	public static void setMapFile(String imgName) {
		try {
			resultObject.getMapObject().setMapFileName(thirdTabPanel.addPreview(imgName).getAbsolutePath());
			firstTabPanel.setMapFileName(imgName);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), getMessage(PropertiesKeys.FILE_NOT_IMAGE),
					getMessage(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
		firstTabPanel.setMapHeight(String.valueOf(thirdTabPanel.getImageSize().height) + " px");
		firstTabPanel.setMapWidth(String.valueOf(thirdTabPanel.getImageSize().width) + " px");
	}

	public static void setTextureFile(String path) {
		try {
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

			texture.setPath(path);
			firstTabPanel.setTexturePath(path);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), getMessage(PropertiesKeys.FILE_NOT_IMAGE),
					getMessage(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
	}

	public static void saveXMLFile(String name) {
		JAXBContext context;
		MapObject mapObject = resultObject.getMapObject();
		mapObject.setBasic(firstTabPanel.getMapSettings());
		mapObject.setLightData(firstTabPanel.getLightSettings());
		mapObject.setMaterial(firstTabPanel.getMaterial());
		resultObject.getGenerationInfo().setModels(new ArrayList<>(models.values()));
		resultObject.getGenerationInfo().setArgs(secondTabPanel.getArgs());
		try {
			context = JAXBContext.newInstance("generator.models.result");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream os = new FileOutputStream(new File(name.endsWith(".xml") ? name : name + ".xml"));
			marshaller.marshal(resultObject, os);
			os.close();
			JOptionPane.showMessageDialog(new JFrame(), getMessage(PropertiesKeys.SAVE_XML_SUCCESS),
					getMessage(PropertiesKeys.SAVE_XML_SUCCESS), JOptionPane.INFORMATION_MESSAGE);
		} catch (JAXBException | IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), getMessage(PropertiesKeys.SAVE_XML_ERROR),
					getMessage(PropertiesKeys.SAVE_XML_ERROR), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void loadXMLFile(String name) {
		File file = new File(name);
		JAXBContext context;
		try {
			context = JAXBContext.newInstance("generator.models.result");
			Unmarshaller um = context.createUnmarshaller();
			resultObject = (ResultObject) um.unmarshal(file);
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(new JFrame(), getMessage(PropertiesKeys.LOAD_XML_ERROR),
					getMessage(PropertiesKeys.LOAD_XML_ERROR), JOptionPane.ERROR_MESSAGE);
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
				models.put(i.getModel().getPath(), i);
			}
		}
		for (GeneratedObject i : resultObject.getGeneratedObjects()) {
			ModelInfo objectInfo = models.get(i.getObjectPath());
			GenerationModel model;
			if (objectInfo != null) {
				model = objectInfo.getModel();
			} else {
				model = new GenerationModel(i.getObjectPath());
			}
			ModelInfo obj = new ModelInfo(model);
			if (objectInfo == null) {
				models.put(i.getObjectPath(), obj);
				i.setModel(model);
			} else {
				i.setModel(objectInfo.getModel());
			}
		}
		firstTabPanel.setMapObject(resultObject.getMapObject());
		updateModelsPanel();
		setMapFile(resultObject.getMapObject().getMapFileName());
		updateModels(resultObject.getGeneratedObjects());
		updateObjects();
	}

	public static void registerSecondTabPanel(SecondTabPanel secondPanel) {
		secondTabPanel = secondPanel;
	}

	public static void registerThirdTabPanel(ThirdTabPanel thridPanel) {
		thirdTabPanel = thridPanel;
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

	public static void registerFirstTabPanel(FirstTabPanel panel) {
		firstTabPanel = panel;
	}

	public static Algorithm getAlgorithm() {
		return secondTabPanel.getAlgorithm();
	}

	public static ResultObject getResultObject() {
		return resultObject;
	}

	public static void setResultObject(ResultObject resultObject) {
		Mediator.resultObject = resultObject;
	}

	public static void updateObjects() {
		thirdTabPanel.updateModels(resultObject.getGeneratedObjects());
		thirdTabPanel.revalidate();
	}

	public static double getGenerationInfoArguments(String key) {
		return (double) firstTabPanel.getArguments().get(key).getValue();
	}

	public static double getGeneratedObjectArguments(String key) {
		return (double) objectWindow.getArguments().get(key).getValue();
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void updateModels(List<GeneratedObject> objects) {
		thirdTabPanel.updateModels(objects);
	}

	public static void deleteObjects() {
		List<GeneratedObject> rows = thirdTabPanel.getSelectedRows();
		if (rows != null) {
			List<GeneratedObject> objects = resultObject.getGeneratedObjects();
			objects.removeAll(rows);
			thirdTabPanel.updateModels(objects);
			updateObjects();
		}
	}

	public static void deleteModels() {
		List<ModelInfo> selectedRows = secondTabPanel.getSelectedRows();
		for (ModelInfo i : selectedRows) {
			models.remove(i.getModel().getPath());
		}

		secondTabPanel.updateModels(models.values());
	}

	public static void registerObjectWindow(ObjectWindow window) {
		objectWindow = window;
	}

	public static void unregisterObjectWindow() {
		objectWindow = null;
	}

	public static List<GeneratedObject> getGeneratedObjects() {
		return thirdTabPanel.getSelectedRows();
	}

	public static void setClicked(GeneratedObject obj) {
		thirdTabPanel.click(obj);
	}

	public static void highlight(GeneratedObject obj) {
		thirdTabPanel.highlight(obj);
	}

	public static void unHighlight() {
		thirdTabPanel.unHighlight();
	}

	public static void refreshPreview() {
		thirdTabPanel.updateModels(resultObject.getGeneratedObjects());
		thirdTabPanel.refreshPreview();
	}

	public static Dimension getMapDimensions() {
		return thirdTabPanel.getImageSize();
	}

	public static void loadModel(String path) {
		if (models.get(path) == null) {
			GenerationModel model = new GenerationModel(path);
			ModelInfo obj = new ModelInfo(model);
			models.put(path, obj);
		}
	}

	public static void updateModelsPanel() {
		secondTabPanel.updateModels(models.values());
	}

	public static Map<String, ModelInfo> getModels() {
		return models;
	}

	public static void changeModelFileName(String path) {
		modelWindow.changeFile(path);
	}

	public static void registerModelWindow(ModelWindow mw) {
		modelWindow = mw;
	}

	public static String getLastPath() {
		return lastPath;
	}

	public static void setLastPath(String lastPath) {
		Mediator.lastPath = lastPath;
	}

	public static double getMapWidth() {
		return firstTabPanel.getMapSettings().getLengthX();
	}

	public static double getMapHeight() {
		return firstTabPanel.getMapSettings().getLengthZ();
	}

	public static double getMapMaxYSetting() {
		return firstTabPanel.getMapSettings().getLengthY();
	}

	public static List<ModelInfo> getSelectedModels() {
		return secondTabPanel.getSelectedRows();
	}

	public static Map<String, Double> getAlgorithmArgs() {
		return secondTabPanel.getArgs();
	}

	public static int find(int[] tab, int index) {
		for (int i : tab) {
			if (i == index) {
				return index;
			}
		}
		return -1;
	}

	public static double getMapHeightAt(int x, int y) {
		return thirdTabPanel.getMapHeight(x, y);
	}

	public static double getMapMaxY() {
		return thirdTabPanel.getMapMaxY();
	}
}
// TODO nienachodzenie obiekow
// TODO mapa prawodpodobienstwa dla kazdego obiektu, pozycja 1 obiektu i
// rozsiewanie, skupisko
// TODO generator na siatce refularnej
//TODO zapis i odczyt z paneli, static?
//TODO obsluga sciezek relatywnych
//TODO poprawic regularny