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
		add(ComponentUtil.createSpinner(0, Consts.MAX_POSITION, Consts.MIN_AGGREGATION_RANGE, Consts.MAX_AGGREGATION_RANGE,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE), arguments,
				Mediator.getMessage(PropertiesKeys.AGGREGATION_RANGE_TOOLTIP), true));
		add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.START_MIN_X, Consts.START_MAX_X,
				Mediator.getMessage(PropertiesKeys.START_X_POSITION), arguments,
				Mediator.getMessage(PropertiesKeys.START_X_POSITION_TOOLTIP), true));
		add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.START_MIN_Z, Consts.START_MAX_Z,
				Mediator.getMessage(PropertiesKeys.START_Z_POSITION), arguments,
				Mediator.getMessage(PropertiesKeys.START_Z_POSITION_TOOLTIP), true));
		add(ComponentUtil.createSpinner(0, Consts.MAX_POSITION, Consts.MIN_PROPAGATION, Consts.MAX_PROPAGATION,
				Mediator.getMessage(PropertiesKeys.PROPAGATION), arguments,
				Mediator.getMessage(PropertiesKeys.PROPAGATION_TOOLTIP), true));
	}

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return new SpreadAlgorithmPanel();
	}
}
