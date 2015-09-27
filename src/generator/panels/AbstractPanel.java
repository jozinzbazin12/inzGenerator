package generator.panels;

import java.util.Map;

import javax.swing.JPanel;

import generator.utils.Spinner;

public class AbstractPanel extends JPanel {
	private static final long serialVersionUID = -4327845537212042650L;
	protected static final int MAX_POSITION = 10000;

	protected Map<String, Spinner> arguments;

	protected double getValue(String key) {
		return (double) arguments.get(key).getValue();
	}
}
