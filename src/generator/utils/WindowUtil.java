package generator.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import generator.Mediator;

public final class WindowUtil {

	public static void displayInfo(String messageKey) {
		displayInfo(PropertiesKeys.INFO, messageKey);
	}

	public static void displayInfo(String titleKey, String messageKey) {
		JOptionPane.showMessageDialog(new JFrame(), Mediator.getMessage(messageKey), Mediator.getMessage(titleKey),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayError(String titleKey, String messageKey) {
		JOptionPane.showMessageDialog(new JFrame(), Mediator.getMessage(messageKey), Mediator.getMessage(titleKey),
				JOptionPane.ERROR_MESSAGE);
	}

	public static void displayError(String messageKey) {
		displayError(PropertiesKeys.ERROR_WINDOW_TITLE, messageKey);
	}

	public static int displayConfirm(String titleKey, String messageKey) {
		return JOptionPane.showConfirmDialog(null, Mediator.getMessage(messageKey), Mediator.getMessage(titleKey),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public static int displayConfirm(String messageKey) {
		return displayConfirm(PropertiesKeys.WARNING, messageKey);
	}
}
