package generator.algorithms.panels.additional;

import java.awt.GridLayout;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class MaskAlgorithmPanel extends AlgorithmAdditionalPanel {
	private static final long serialVersionUID = 1L;

	public MaskAlgorithmPanel() {
		setLayout(new GridLayout(10, 3, 5, 5));
		add(ComponentUtil.createDoubleLegendPanel());
		add(ComponentUtil.createSpinner(0, Consts.MAX_POSITION, Consts.RATE, Mediator.getMessage(PropertiesKeys.COLOR_RATE),
				arguments, Mediator.getMessage(PropertiesKeys.COLOR_RATE_TOOLTIP), true));
	}

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return new MaskAlgorithmPanel();
	}

}
