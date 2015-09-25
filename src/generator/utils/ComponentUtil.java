package generator.utils;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public final class ComponentUtil {

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, JSpinner> arguments) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(defValue, min, max, 1));
		panel.add(attributelabel);
		panel.add(spinner);
		arguments.put(key, spinner);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, JSpinner> arguments) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner minSpinner = new JSpinner(new SpinnerNumberModel(0.0, min, max, 1));
		panel.add(attributelabel);
		JSpinner maxSpinner = new JSpinner(new SpinnerNumberModel(0.0, min, max, 1));
		panel.add(attributelabel);
		panel.add(minSpinner);
		panel.add(maxSpinner);
		arguments.put(key1, minSpinner);
		arguments.put(key2, maxSpinner);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, JSpinner> arguments) {
		return createSpinner(min, max, key, description, 0.0, arguments);
	}
}
