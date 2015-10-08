package generator.algorithms.panels.main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.utils.Spinner;

public class AlgorithmMainPanel extends JPanel {
	private static final long serialVersionUID = 3043925881010825869L;
	protected static Map<String, Spinner> arguments = new HashMap<>();
	protected static final int MAX_POSITION = 10000;

	public static Map<String, Double> getArgs(Map<String, Double> args) {
		for (Map.Entry<String, Spinner> e : arguments.entrySet()) {
			Spinner spinner = e.getValue();
			String key = e.getKey();
			args.put(key, (Double) spinner.getValue());
		}
		return args;
	}

	public static void setArgs(Map<String, Double> args) {
		for (Map.Entry<String, Spinner> i : arguments.entrySet()) {
			Double value = args.get(i.getKey());
			if (value != null) {
				i.getValue().setValue(value);
			}
		}
	}

	public AlgorithmMainPanel() {
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_MAIN_ARGUMENTS)));
	}
}
