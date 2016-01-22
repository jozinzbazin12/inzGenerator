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
import generator.algorithms.Algorithm;
import generator.algorithms.RegularAlgorithm;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import gnerator.components.CheckBox;
import gnerator.components.Component;
import gnerator.components.Spinner;

public class AlgorithmMainPanel extends JPanel {
	protected static Map<String, Component> arguments = new HashMap<>();
	private static double collisionEnabled = 0;
	private static double collisonScale = 0;
	private static final long serialVersionUID = 3043925881010825869L;
	private JPanel collisionScale;
	private CheckBox collisions;

	protected Spinner getScale() {
		return (Spinner) collisionScale.getComponents()[1];
	}

	public void refresh(ActionEvent e) {
		if (e != null) {
			collisionEnabled = ((CheckBox) e.getSource()).value();
		}
		Spinner scale = getScale();
		if (collisionEnabled == 1) {
			collisions.setSelected(true);
			scale.setEnabled(true);
		} else {
			scale.setEnabled(false);
		}
		scale.setValue(collisonScale);
	}

	@Override
	public void repaint() {
		super.repaint();
	}

	public AlgorithmMainPanel() {
		setLayout(new GridLayout(13, 3, 5, 5));
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_MAIN_ARGUMENTS)));
		collisionScale = ComponentUtil.createSpinner(0, 1000, Consts.WEB_SCALE,
				Mediator.getMessage(PropertiesKeys.COLLISION_MARGIN), arguments,
				Mediator.getMessage(PropertiesKeys.COLLISION_MARGIN_TOOLTIP));
		addListener(collisionScale);
		getScale().setEnabled(false);
		Action a = new Action(Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS)) {
			private static final long serialVersionUID = -2843015922845460404L;

			@Override
			public void actionPerformed(ActionEvent e) {
				refresh(e);
			}
		};
		collisions = new CheckBox(a, Mediator.getMessage(PropertiesKeys.PREVENT_COLLISIONS_TOOLTIP));

		collisions.setAction(a);
		add(collisions);
		add(collisionScale);
	}

	private void addListener(JPanel collisionScale) {
		for (java.awt.Component c : collisionScale.getComponents()) {
			if (c instanceof Spinner) {
				final Spinner spinner = (Spinner) c;
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

	public void refreshPanel(Algorithm a) {
		refresh(null);
		Spinner scale = getScale();
		if (a instanceof RegularAlgorithm) {
			scale.setEnabled(false);
			collisions.setEnabled(false);
		}
	}

}
