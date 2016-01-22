package generator.algorithms.panels.additional;

import java.awt.GridLayout;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class HeightAlgorithmPanel extends AlgorithmAdditionalPanel {
	private static final long serialVersionUID = 1L;

	public HeightAlgorithmPanel() {
		setLayout(new GridLayout(10, 3, 5, 5));
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.MIN_Y_HEIGHT, Consts.MAX_Y_HEIGHT,
				Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM_SETTINGS), arguments,
				Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM_SETTINGS_TOOLTIP), true));
	}

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return new HeightAlgorithmPanel();
	}

}
