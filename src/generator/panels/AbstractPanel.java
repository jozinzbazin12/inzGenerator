package generator.panels;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import generator.utils.Spinner;

public abstract class AbstractPanel extends JPanel {
	private static final long serialVersionUID = -4327845537212042650L;
	protected static final int MAX_POSITION = 10000;
	protected Map<String, Spinner> arguments = new HashMap<>();

	public Map<String, Double> getArgs(Map<String, Double> args, boolean checkModified) {
		for (Map.Entry<String, Spinner> e : arguments.entrySet()) {
			Spinner spinner = e.getValue();
			String key = e.getKey();
			if (spinner.isModified() || !checkModified) {
				args.put(key, (Double) spinner.getValue());
			}
		}
		return args;
	}

	public Map<String, Double> getArgs() {
		return getArgs(new HashMap<>(), false);
	}

	public void setArgs(Map<String, Double> args) {
		for (Map.Entry<String, Spinner> i : arguments.entrySet()) {
			Double value = args.get(i.getKey());
			if (value != null) {
				Spinner spinner = i.getValue();
				spinner.setSilent(true);
				spinner.setValue(value);
				spinner.setSilent(false);
			}
		}
	}

	protected double getValue(String key) {
		return (double) arguments.get(key).getValue();
	}

	protected Map<String, Double> transform(Map<String, Spinner> map) {
		Map<String, Double> result = new HashMap<>();
		for (Map.Entry<String, Spinner> i : map.entrySet()) {
			result.put(i.getKey(), (Double) i.getValue().getValue());
		}
		return result;
	}

}
