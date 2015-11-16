package generator.algorithms.panels.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import generator.Mediator;
import generator.actions.Action;
import generator.utils.CheckBox;
import generator.utils.Component;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class AlgorithmMainPanel extends JPanel {
	protected static Map<String, Component> arguments = new HashMap<>();
	private static double collisionEnabled;
	protected static final int MAX_POSITION = 10000;
	private static final long serialVersionUID = 3043925881010825869L;

	public AlgorithmMainPanel() {
		setLayout(new GridLayout(13, 3, 5, 5));
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_MAIN_ARGUMENTS)));
		Action a = new Action(Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS)) {
			private static final long serialVersionUID = -2843015922845460404L;

			@Override
			public void actionPerformed(ActionEvent e) {
				collisionEnabled = ((CheckBox) e.getSource()).value();
			}
		};
		CheckBox collisions = new CheckBox(a, Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS_TOOLTIP));

		collisions.setAction(a);
		add(collisions);
	}

	public static Map<String, Double> getArgs(Map<String, Double> args) {
		for (Map.Entry<String, Component> e : arguments.entrySet()) {
			Component spinner = e.getValue();
			String key = e.getKey();
			args.put(key, spinner.value());
		}
		args.put(Consts.COLLISIONS, collisionEnabled);
		return args;
	}

	public static void setArgs(Map<String, Double> args) {
		for (Map.Entry<String, Component> i : arguments.entrySet()) {
			Double value = args.get(i.getKey());
			if (value != null) {
				i.getValue().setValue(value);
			}
		}
	}
}
