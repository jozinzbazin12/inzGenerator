package generator;

import generator.algorithms.Algorithm;
import generator.models.generation.GenerationModel;
import generator.models.generation.ObjectListRow;
import generator.models.result.GeneratedObject;
import generator.models.result.ResultObject;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.utils.PropertiesKeys;
import generator.windows.MainWindow;
import generator.windows.ObjectWindow;

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
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Mediator {
	private static final String PROPERTIES_FILE = "config.properties";
	private static final String LANGUAGE_KEY = "language";
	private static MainWindow mainWindow;
	private static SecondTabPanel secondTabPanel;
	private static ResourceBundle messages;
	private static FirstTabPanel firstTabPanel;
	private static ResultObject resultObject = new ResultObject();
	private static Properties properties;
	private static Locale locale;
	private static ObjectWindow objectWindow;
	private static Map<String, GenerationModel> models=new HashMap<>();

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
					Mediator.getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
				mainWindow.dispose();
				main(null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void register(MainWindow mw) {
		mainWindow = mw;
	}

	public static void setMapFileName(String imgName) {
		try {
			resultObject.getMapObject().setMapFileName(secondTabPanel.addPreview(imgName).getAbsolutePath());
			firstTabPanel.setMapFileName(imgName);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.FILE_NOT_IMAGE),
					messages.getString(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
		firstTabPanel.setMapHeight(String.valueOf(secondTabPanel.getImageSize().height) + " px");
		firstTabPanel.setMapWidth(String.valueOf(secondTabPanel.getImageSize().width) + " px");
	}

	public static void saveXMLFile(String name) {
		JAXBContext context;
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
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.SAVE_XML_ERROR), messages.getString(PropertiesKeys.SAVE_XML_ERROR),
					JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.LOAD_XML_ERROR), messages.getString(PropertiesKeys.LOAD_XML_ERROR),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		for(GeneratedObject i:resultObject.getGeneratedObjects()){
			if(models.get(i.getObjectFile())==null){
				GenerationModel model = new GenerationModel("dupa", i.getObjectFile());
				models.put(i.getObjectFile(), model);
				i.setModel(model);
			}
			else{
				i.setModel(models.get(i.getObjectFile()));
			}
		}
		setMapFileName(resultObject.getMapObject().getMapFileName());
		updateObjectList(resultObject.getGeneratedObjects());
		printOnPreview();
	}

	public static void registerSecondTabPanel(SecondTabPanel secp) {
		secondTabPanel = secp;

	}

	public static String getMessage(String message) {
		return messages.getString(message);
	}

	public static void registerFirstTabPanel(FirstTabPanel panel) {
		firstTabPanel = panel;

	}

	public static Algorithm getAlgorithm() {
		return firstTabPanel.getAlgorithm();

	}

	public static ResultObject getResultObject() {
		return resultObject;
	}

	public static void setResultObject(ResultObject resultObject) {
		Mediator.resultObject = resultObject;
	}

	public static void printOnPreview() {
		secondTabPanel.printOnPreview(resultObject.getGeneratedObjects());
		secondTabPanel.revalidate();
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
		secondTabPanel.updateObjectListPanel(objects);
	}
	
	public static void updateObjectList() {
		secondTabPanel.updateObjectListPanel(resultObject.getGeneratedObjects());
	}

	public static void deleteObject(ObjectListRow objectListRow) {
		resultObject.getGeneratedObjects().remove(objectListRow.getIndex());
		secondTabPanel.deleteObject(objectListRow);
		printOnPreview();
	}


	public static void registerObjectWindow(ObjectWindow window) {
		objectWindow = window;
	}

	public static void unregisterObjectWindow() {
		objectWindow = null;
	}

	public static GeneratedObject getGeneratedObject() {
		return secondTabPanel.getGeneratedObject();
	}

	public static void setClicked(GeneratedObject obj) {
		secondTabPanel.setClicked(obj);
	}

	public static void highlight(GeneratedObject obj) {
		secondTabPanel.highlight(obj);
		
	}

	public static void unHighlight() {
		secondTabPanel.unHighlight();		
	}

	public static void refreshObjects() {
		secondTabPanel.refreshObjects();
	}
	
	public static void refreshPreview(){
		secondTabPanel.refreshPreview();
	}
	public static Dimension getMapDimensions(){
		return secondTabPanel.getImageSize();
	}
}
