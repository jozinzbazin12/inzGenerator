package generator.utils;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import generator.Mediator;
import gnerator.components.Component;
import gnerator.components.Label;
import gnerator.components.Spinner;

public final class ComponentUtil {

	public static JPanel createAtrributeLegendPanel() {
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 3));
		Label attributelabel = new Label(Mediator.getMessage(PropertiesKeys.ATTRIBUTE),
				Mediator.getMessage(PropertiesKeys.ATTRIBUTE_TOOLTIP), SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		Label minLabel = new Label(Mediator.getMessage(PropertiesKeys.MIN), Mediator.getMessage(PropertiesKeys.MIN_TOOLTIP),
				SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		Label maxLabel = new Label(Mediator.getMessage(PropertiesKeys.MIN), Mediator.getMessage(PropertiesKeys.MAX_TOOLTIP),
				SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		legendPanel.add(minLabel);
		legendPanel.add(maxLabel);
		return legendPanel;
	}

	public static JPanel createDoubleLegendPanel() {
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 2));
		Label attributelabel = new Label(Mediator.getMessage(PropertiesKeys.ATTRIBUTE),
				Mediator.getMessage(PropertiesKeys.ATTRIBUTE_TOOLTIP), SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		Label valueLabel = new Label(Mediator.getMessage(PropertiesKeys.VALUE), Mediator.getMessage(PropertiesKeys.VALUE_TOOLTIP),
				SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		legendPanel.add(valueLabel);
		return legendPanel;
	}

	public static JPanel createLightSpinners(double min, double max, String keyR, String keyG, String keyB, String keyA,
			String description, String tooltip, double defValue, Map<String, Component> arguments) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 5));
		Label attributelabel = new Label(description, tooltip);
		panel.add(attributelabel);
		Spinner spinnerR = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		Spinner spinnerG = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		Spinner spinnerB = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		Spinner spinnerA = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		panel.add(attributelabel);
		panel.add(spinnerR);
		panel.add(spinnerG);
		panel.add(spinnerB);
		panel.add(spinnerA);
		arguments.put(keyR, spinnerR);
		arguments.put(keyG, spinnerG);
		arguments.put(keyB, spinnerB);
		arguments.put(keyA, spinnerA);
		return panel;
	}

	public static JPanel createMaterialSpinners(double min, double max, String keyR, String keyG, String keyB, String description,
			String tooltip, double defValue, Map<String, Component> arguments) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 4));
		Label attributelabel = new Label(description, tooltip);
		panel.add(attributelabel);
		Spinner spinnerR = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		Spinner spinnerG = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		Spinner spinnerB = new Spinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		panel.add(attributelabel);
		panel.add(spinnerR);
		panel.add(spinnerG);
		panel.add(spinnerB);
		arguments.put(keyR, spinnerR);
		arguments.put(keyG, spinnerG);
		arguments.put(keyB, spinnerB);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, Component> arguments) {
		return createSpinner(min, max, key, description, defValue, arguments, null, false);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, Component> arguments, String tooltip) {
		return createSpinner(min, max, key, description, defValue, arguments, tooltip, false);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, double defValue,
			Map<String, Component> arguments, String tooltip, boolean listen) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		Label attributelabel = new Label(description, tooltip);
		panel.add(attributelabel);
		Spinner spinner = new Spinner(new SpinnerNumberModel(defValue >= min ? defValue : min, min, max, 1), listen);
		panel.add(attributelabel);
		panel.add(spinner);
		arguments.put(key, spinner);
		return panel;
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, Component> arguments) {
		return createSpinner(min, max, key, description, 0.0, arguments);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, Component> arguments,
			boolean listen) {
		return createSpinner(min, max, key, description, 0.0, arguments, null, listen);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, Component> arguments,
			String tooltip) {
		return createSpinner(min, max, key, description, 0.0, arguments, tooltip, false);
	}

	public static JPanel createSpinner(double min, double max, String key, String description, Map<String, Component> arguments,
			String tooltip, boolean listen) {
		return createSpinner(min, max, key, description, 0.0, arguments, tooltip, listen);
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, Component> arguments) {
		return createSpinner(min, max, key1, key2, description, arguments, null, false);
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, Component> arguments, String tooltip) {
		return createSpinner(min, max, key1, key2, description, arguments, tooltip, false);
	}

	public static JPanel createSpinner(double min, double max, String key1, String key2, String description,
			Map<String, Component> arguments, String tooltip, boolean listen) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		Label attributelabel = new Label(description, tooltip);
		panel.add(attributelabel);
		final Spinner minSpinner = new Spinner(new SpinnerNumberModel(0.0, min, max, 1), listen);
		panel.add(attributelabel);
		final Spinner maxSpinner = new Spinner(new SpinnerNumberModel(0.0, min, max, 1), listen);
		minSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Number low = (Number) minSpinner.value();
				Number high = (Number) maxSpinner.value();
				if (high.floatValue() < low.floatValue() && !minSpinner.isSilent()) {
					maxSpinner.setValue(low);
					if (maxSpinner.isListeningEnabled()) {
						maxSpinner.setModified(true);
					}
				}
			}
		});
		maxSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Number low = (Number) minSpinner.value();
				Number high = (Number) maxSpinner.value();
				if (low.floatValue() > high.floatValue() && !maxSpinner.isSilent()) {
					minSpinner.setValue(high);
					if (maxSpinner.isListeningEnabled()) {
						minSpinner.setModified(true);
					}
				}
			}
		});
		panel.add(attributelabel);
		panel.add(minSpinner);
		panel.add(maxSpinner);
		arguments.put(key1, minSpinner);
		arguments.put(key2, maxSpinner);
		return panel;
	}

	public static JPanel createSpinner(int min, int max, String minCount, String maxCount, String message,
			Map<String, Component> arguments, boolean listen) {
		return createSpinner(min, max, minCount, maxCount, message, arguments, null, listen);
	}

}
