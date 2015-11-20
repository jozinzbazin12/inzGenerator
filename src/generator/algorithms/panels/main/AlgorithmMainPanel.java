package generator.algorithms.panels.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import generator.Mediator;
import generator.actions.Action;
import generator.utils.CheckBox;
import generator.utils.Component;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import generator.utils.Spinner;

public class AlgorithmMainPanel extends JPanel {
	protected static Map<String, Component> arguments = new HashMap<>();
	private static double collisionEnabled;
	private static double collisonScale = 1;
	protected static final int MAX_POSITION = 10000;
	private static final long serialVersionUID = 3043925881010825869L;

	protected void setEnabled(JPanel p, boolean value) {
		for (java.awt.Component i : p.getComponents()) {
			i.setEnabled(value);
		}
	}

	public AlgorithmMainPanel() {
		setLayout(new GridLayout(13, 3, 5, 5));
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_MAIN_ARGUMENTS)));
		JPanel collisionScale = ComponentUtil.createSpinner(1, 32, Consts.WEB_SCALE,
				Mediator.getMessage(PropertiesKeys.WEB_SCALE), arguments, Mediator.getMessage(PropertiesKeys.WEB_SCALE_TOOLTIP));
		addListener(collisionScale);
		setEnabled(collisionScale, false);
		Action a = new Action(Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS)) {
			private static final long serialVersionUID = -2843015922845460404L;

			@Override
			public void actionPerformed(ActionEvent e) {
				collisionEnabled = ((CheckBox) e.getSource()).value();
				if (collisionEnabled == 1) {
					AlgorithmMainPanel.this.setEnabled(collisionScale, true);
				} else {
					AlgorithmMainPanel.this.setEnabled(collisionScale, false);
				}
			}
		};
		CheckBox collisions = new CheckBox(a, Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS_TOOLTIP));

		collisions.setAction(a);
		add(collisions);
		add(collisionScale);
	}

	private void addListener(JPanel collisionScale) {
		for (java.awt.Component c : collisionScale.getComponents()) {
			if (c instanceof Spinner) {
				Spinner spinner = (Spinner) c;
				spinner.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						collisonScale = spinner.value();
					}
				});
			}
		}

	}

	public static Map<String, Double> getArgs(Map<String, Double> args) {
		for (Map.Entry<String, Component> e : arguments.entrySet()) {
			Component spinner = e.getValue();
			String key = e.getKey();
			args.put(key, spinner.value());
		}
		args.put(Consts.COLLISIONS, collisionEnabled);
		args.put(Consts.WEB_SCALE, collisonScale);
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
