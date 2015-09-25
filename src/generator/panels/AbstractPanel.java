package generator.panels;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSpinner;

public class AbstractPanel extends JPanel {
	private static final long serialVersionUID = -4327845537212042650L;
	protected static final int MAX_POSITION = 10000;

	protected Map<String, JSpinner> arguments;

	protected double getValue(String key) {
		return (double) arguments.get(key).getValue();
	}
}
