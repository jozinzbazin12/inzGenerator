package generator;

import generator.algorithms.Algorithm;
import generator.models.result.ResultObject;
import generator.panels.BottomPanel;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.utils.PropertiesKeys;
import generator.windows.MainWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mediator {
	private static final String PROPERTIES_FILE = "config.properties";
	private static final String LANGUAGE_KEY = "language";
	private static MainWindow mainWindow;
	private static BottomPanel bottomPanel;
	private static SecondTabPanel secondTabPanel;
	private static ResourceBundle messages;
	private static FirstTabPanel firstTabPanel;
	private static ResultObject resultObject;
	private static File mapFile;
	private static Properties properties;
	private static Locale locale;

	public static void main(String[] args) throws IOException {
		properties = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(PROPERTIES_FILE);
			properties.load(input);
			locale=new Locale(properties.getProperty(LANGUAGE_KEY));
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
			int dialogResult = JOptionPane.showConfirmDialog(null, Mediator.getMessage(PropertiesKeys.CHANGE_LANGUAGE_WARNING), "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
				mainWindow.dispose();
				main(null);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void register(MainWindow mw) {
		mainWindow = mw;
	}

	public static void registerBottomPanel(BottomPanel bp) {
		bottomPanel = bp;

	}

	public static void setLoadMapName(String name) {
		firstTabPanel.setMapName(name);
	}

	public static void setSaveXMLName(String name) {
		bottomPanel.setXmlToSaveName(name);
	}

	public static void setLoadXMLFile(String name) {
		bottomPanel.setXmlName(name);

	}

	public static void setPreview(String imgName) {
		try {
			mapFile = secondTabPanel.addPreview(imgName);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.FILE_NOT_IMAGE),
					messages.getString(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
		firstTabPanel.setMapHeight(String.valueOf(secondTabPanel.getImageSize().height) + " px");
		firstTabPanel.setMapWidth(String.valueOf(secondTabPanel.getImageSize().width) + " px");
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
		secondTabPanel.repaint();
	}

	public static double getArgument(String key) {
		return (double) firstTabPanel.getArguments().get(key).getValue();
	}

	public static Locale getLocale() {
		return locale;
	}
	
}
