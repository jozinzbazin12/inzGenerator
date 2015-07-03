package generator;

import generator.algorithms.Algorithm;
import generator.models.result.ResultObject;
import generator.panels.BottomPanel;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.utils.PropertiesKeys;
import generator.windows.MainWindow;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Mediator {
	private static MainWindow mainWindow;
	private static BottomPanel bottomPanel;
	private static SecondTabPanel secondTabPanel;
	private static ResourceBundle messages;
	private static FirstTabPanel firstTabPanel;
	private static ResultObject resultObject;
	private static File mapFile;

	public static void main(String[] args) {
		messages = ResourceBundle.getBundle(PropertiesKeys.PROPERTIES_FILE);
		MainWindow mainWindow = new MainWindow(messages.getString(PropertiesKeys.WINDOW_NAME));
		Mediator.register(mainWindow);
		mainWindow.createWindow();
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
			mapFile=secondTabPanel.addPreview(imgName);
			mainWindow.getContentPane().revalidate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), messages.getString(PropertiesKeys.FILE_NOT_IMAGE),
					messages.getString(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
		}
		firstTabPanel.setMapHeight(String.valueOf(secondTabPanel.getImageSize().height)+" px");
		firstTabPanel.setMapWidth(String.valueOf(secondTabPanel.getImageSize().width)+" px");
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
}
