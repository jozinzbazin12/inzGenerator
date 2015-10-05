package generator.algorithms.panels.additional;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class HeightAlgorithmPanel extends AlgorithmAdditionalPanel {
	private static final long serialVersionUID = 1L;

	public HeightAlgorithmPanel() {
		setLayout(new GridLayout(10, 3, 0, 0));
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ADDITIONAL)));
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Y_HEIGHT, Consts.MAX_Y_HEIGHT,
				Mediator.getMessage(PropertiesKeys.HEIGHT_ALGORITHM_SETTINGS), arguments, true));

	}

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return new HeightAlgorithmPanel();
	}

}
