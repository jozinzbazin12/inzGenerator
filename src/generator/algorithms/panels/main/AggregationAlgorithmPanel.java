package generator.algorithms.panels.main;

import java.awt.GridLayout;

import javax.swing.JSeparator;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class AggregationAlgorithmPanel extends AlgorithmMainPanel {

	private static final long serialVersionUID = 6273497875161411624L;

	public AggregationAlgorithmPanel() {
		setLayout(new GridLayout(13, 3, 5, 5));
		add(ComponentUtil.createSpinner(0, 100, Consts.AGGREGATION_CHANCE, Mediator.getMessage(PropertiesKeys.AGGREGATION_CHANCE),
				arguments));
		add(new JSeparator());
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(0, MAX_POSITION, Consts.MIN_AGGREGATION_RANGE, Consts.MAX_AGGREGATION_RANGE,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE), arguments));
	}
}
