package generator.panels;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import gnerator.components.Component;
import gnerator.components.Spinner;

public abstract class AbstractPanel extends JPanel {
	private static final long serialVersionUID = -4327845537212042650L;
	protected Map<String, Component> arguments = new HashMap<>();

	public Map<String, Double> getArgs() {
		return getArgs(new HashMap<String, Double>(), false);
	}

	public Map<String, Double> getArgs(Map<String, Double> args, boolean checkModified) {
		for (Map.Entry<String, Component> e : arguments.entrySet()) {
			Component spinner = e.getValue();
			String key = e.getKey();
			if (spinner.isModified() || !checkModified) {
				args.put(key, (Double) spinner.value());
			}
		}
		return args;
	}

	protected double getValue(String key) {
		return (double) arguments.get(key).value();
	}

	public void setArgs(Map<String, Double> args) {
		for (Map.Entry<String, Component> i : arguments.entrySet()) {
			Double value = args.get(i.getKey());
			if (value != null) {
				Component spinner = i.getValue();
				spinner.setSilent(true);
				spinner.setValue(value);
				spinner.setSilent(false);
			}
		}
	}

	protected Map<String, Double> transform(Map<String, Spinner> map) {
		Map<String, Double> result = new HashMap<>();
		for (Map.Entry<String, Spinner> i : map.entrySet()) {
			result.put(i.getKey(), (Double) i.getValue().getValue());
		}
		return result;
	}

}
