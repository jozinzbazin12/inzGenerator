package generator.utils;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import generator.Mediator;

public final class ComponentUtil {

	public static JPanel createAtrributeLegendPanel() {
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 3));
		JLabel attributelabel = new JLabel(Mediator.getMessage(PropertiesKeys.ATTRIBUTE));
		legendPanel.add(attributelabel);
		JLabel minLabel = new JLabel(Mediator.getMessage(PropertiesKeys.MIN));
		legendPanel.add(attributelabel);
		JLabel maxLabel = new JLabel(Mediator.getMessage(PropertiesKeys.MAX));
		legendPanel.add(attributelabel);
		legendPanel.add(minLabel);
		legendPanel.add(maxLabel);
		return legendPanel;
	}

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, Spinner> arguments, boolean listen) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		Spinner spinner = new Spinner(new SpinnerNumberModel(defValue, min, max, 1), listen);
		panel.add(attributelabel);
		panel.add(spinner);
		arguments.put(key, spinner);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, Spinner> arguments, boolean listen) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		Spinner minSpinner = new Spinner(new SpinnerNumberModel(0.0, min, max, 1), listen);
		panel.add(attributelabel);
		Spinner maxSpinner = new Spinner(new SpinnerNumberModel(0.0, min, max, 1), listen);
		panel.add(attributelabel);
		panel.add(minSpinner);
		panel.add(maxSpinner);
		arguments.put(key1, minSpinner);
		arguments.put(key2, maxSpinner);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, Spinner> arguments) {
		return createSpinner(min, max, key1, key2, description, arguments, false);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, Spinner> arguments) {
		return createSpinner(min, max, key, description, 0.0, arguments);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, Spinner> arguments) {
		return createSpinner(min, max, key, description, defValue, arguments, false);
	}
}
