package generator;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generator.algorithms.Algorithm;
import generator.models.generation.GenerationModel;
import generator.models.generation.ObjectFileListRow;
import generator.models.generation.ObjectInfo;
import generator.models.generation.ObjectListRow;
import generator.models.result.GeneratedObject;
import generator.models.result.ResultObject;
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
	private static Map<String, ObjectInfo> models = new HashMap<>();
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
			saveProperties();
			int dialogResult = JOptionPane.showConfirmDialog(null, Mediator.getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING),
					Mediator.getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING), JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
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

	public static void setMapFileName(String imgName) {
		try {
			resultObject.getMapObject().setMapFileName(thirdTabPanel.addPreview(imgName).getAbsolutePath());
			firstTabPanel.setMapFileName(imgName);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.FILE_NOT_IMAGE),
					messages.getString(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
		firstTabPanel.setMapHeight(String.valueOf(thirdTabPanel.getImageSize().height) + " px");
		firstTabPanel.setMapWidth(String.valueOf(thirdTabPanel.getImageSize().width) + " px");
	}

	public static void saveXMLFile(String name) {
		JAXBContext context;
		resultObject.getMapObject().setBasic(firstTabPanel.getMapSettings());
		resultObject.getMapObject().setLightData(firstTabPanel.getLightSettings());
		try {
			context = JAXBContext.newInstance("generator.models.result");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream os = new FileOutputStream(new File(name.endsWith(".xml") ? name : name + ".xml"));
			marshaller.marshal(resultObject, os);
			os.close();
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.SAVE_XML_SUCCESS),
					messages.getString(PropertiesKeys.SAVE_XML_SUCCESS), JOptionPane.INFORMATION_MESSAGE);
		} catch (JAXBException | IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.SAVE_XML_ERROR),
					messages.getString(PropertiesKeys.SAVE_XML_ERROR), JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.LOAD_XML_ERROR),
					messages.getString(PropertiesKeys.LOAD_XML_ERROR), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		models.clear();
		for (GeneratedObject i : resultObject.getGeneratedObjects()) {
			i.setObjectName(getFileNameFromPath(i.getObjectPath()));
			GenerationModel model = new GenerationModel(getFileNameFromPath(i.getObjectPath()), i.getObjectPath());
			ObjectInfo obj = new ObjectInfo(model);
			if (models.get(i.getObjectPath()) == null) {
				models.put(i.getObjectPath(), obj);
				i.setModel(model);
			} else {
				i.setModel(models.get(i.getObjectPath()).getModel());
			}
		}
		firstTabPanel.setArgumentValue(resultObject.getMapObject());
		updateModelsPanel();
		setMapFileName(resultObject.getMapObject().getMapFileName());
		updateObjectList(resultObject.getGeneratedObjects());
		printOnPreview();
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
			result = message;
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

	public static void printOnPreview() {
		thirdTabPanel.printOnPreview(resultObject.getGeneratedObjects());
		thirdTabPanel.revalidate();
	}

	public static double getGenerationInfoArguments(String key) {
		return (double) firstTabPanel.getArguments().get(key).getValue();
	}

	public static double getGeneratetObjectArguments(String key) {
		return (double) objectWindow.getArguments().get(key).getValue();
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void updateObjectList(List<GeneratedObject> objects) {
		thirdTabPanel.updateObjectListPanel(objects);
	}

	public static void updateObjectList() {
		thirdTabPanel.updateObjectListPanel(resultObject.getGeneratedObjects());
	}

	public static void deleteObject(ObjectListRow objectListRow) {
		resultObject.getGeneratedObjects().remove(objectListRow.getIndex());
		thirdTabPanel.deleteObject(objectListRow);
		printOnPreview();
	}

	public static void deleteObject(Set<ObjectFileListRow> clicked) {
		for (ObjectFileListRow i : clicked) {
			models.remove(i.getObject().getModel().getPath());
		}

		secondTabPanel.updateObjectFiles(models.values());

	}

	public static void registerObjectWindow(ObjectWindow window) {
		objectWindow = window;
	}

	public static void unregisterObjectWindow() {
		objectWindow = null;
	}

	public static GeneratedObject getGeneratedObject() {
		return thirdTabPanel.getGeneratedObject();
	}

	public static void setClicked(GeneratedObject obj) {
		thirdTabPanel.setClicked(obj);
	}

	public static void highlight(GeneratedObject obj) {
		thirdTabPanel.highlight(obj);

	}

	public static void unHighlight() {
		thirdTabPanel.unHighlight();
	}

	public static void refreshObjects() {
		thirdTabPanel.refreshObjects();
	}

	public static void refreshPreview() {
		thirdTabPanel.refreshPreview();
	}

	public static Dimension getMapDimensions() {
		return thirdTabPanel.getImageSize();
	}

	public static void loadObjectFile(String path) {
		if (models.get(path) == null) {
			GenerationModel model = new GenerationModel(getFileNameFromPath(path), path);
			ObjectInfo obj = new ObjectInfo(model);
			models.put(path, obj);
		}
	}

	public static void updateModelsPanel() {
		secondTabPanel.updateObjectFiles(models.values());
	}

	private static String getFileNameFromPath(String path) {
		return path.substring(path.lastIndexOf("\\") + 1);
	}

	public static Map<String, ObjectInfo> getModels() {
		return models;
	}

	public static void changeObjectsFileName(String path) {
		modelWindow.changeFile(path, getFileNameFromPath(path));
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

}
