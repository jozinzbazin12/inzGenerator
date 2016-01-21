package generator.utils;

import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import generator.Mediator;

public final class WindowUtil {

	public static int displayConfirm(String messageKey) {
		return displayConfirm(PropertiesKeys.WARNING, messageKey);
	}

	public static int displayConfirm(String titleKey, String messageKey) {
		return JOptionPane.showConfirmDialog(null, Mediator.getMessage(messageKey), Mediator.getMessage(titleKey),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public static void displayError(String messageKey, Object... args) {
		displayError(PropertiesKeys.ERROR_WINDOW_TITLE, messageKey, args);
	}

	public static void displayError(String titleKey, String messageKey, Object... args) {
		JOptionPane.showMessageDialog(new JFrame(), MessageFormat.format(Mediator.getMessage(messageKey), args),
				Mediator.getMessage(titleKey), JOptionPane.ERROR_MESSAGE);
	}

	public static void displayInfo(String messageKey, Object... args) {
		displayInfo(PropertiesKeys.INFO, messageKey, args);
	}

	public static void displayInfo(String titleKey, String messageKey, Object... args) {
		JOptionPane.showMessageDialog(new JFrame(), MessageFormat.format(Mediator.getMessage(messageKey), args),
				Mediator.getMessage(titleKey), JOptionPane.INFORMATION_MESSAGE);
	}

	public static void setBorder(JPanel panel, String key) {
		Border title = BorderFactory.createTitledBorder(Mediator.getMessage(key));
		Border space = new EmptyBorder(1, 1, 1, 1);
		panel.setBorder(new CompoundBorder(title, space));
	}
}
