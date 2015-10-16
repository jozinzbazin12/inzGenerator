package generator.algorithms.panels.main;

import javax.swing.JSeparator;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class AggregationAlgorithmPanel extends AlgorithmMainPanel {

	private static final long serialVersionUID = 6273497875161411624L;

	public AggregationAlgorithmPanel() {
		add(ComponentUtil.createDoubleLegendPanel());
		add(ComponentUtil.createSpinner(0, 100, Consts.AGGREGATION_CHANCE, Mediator.getMessage(PropertiesKeys.AGGREGATION_CHANCE),
				arguments, Mediator.getMessage(PropertiesKeys.AGGREGATION_CHANCE_TOOLTIP)));
		add(new JSeparator());
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(0, MAX_POSITION, Consts.MIN_AGGREGATION_RANGE, Consts.MAX_AGGREGATION_RANGE,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE), arguments,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE_TOOLTIP)));
	}
}
