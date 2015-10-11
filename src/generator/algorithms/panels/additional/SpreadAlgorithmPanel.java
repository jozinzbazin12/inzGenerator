package generator.algorithms.panels.additional;

import java.awt.GridLayout;

import javax.swing.JSeparator;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class SpreadAlgorithmPanel extends AlgorithmAdditionalPanel {

	private static final long serialVersionUID = 6273497875161411624L;

	public SpreadAlgorithmPanel() {
		setLayout(new GridLayout(10, 3, 5, 5));
		add(ComponentUtil.createDoubleLegendPanel());
		add(ComponentUtil.createSpinner(0, 100, Consts.AGGREGATION_CHANCE, Mediator.getMessage(PropertiesKeys.AGGREGATION_CHANCE),
				arguments, Mediator.getMessage(PropertiesKeys.AGGREGATION_CHANCE_TOOLTIP), true));
		add(new JSeparator());
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(0, MAX_POSITION, Consts.MIN_AGGREGATION_RANGE, Consts.MAX_AGGREGATION_RANGE,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE), arguments,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE_TOOLTIP), true));
	}

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return new SpreadAlgorithmPanel();
	}
}
