package generator.algorithms.panels.main;

import java.text.MessageFormat;

import generator.Mediator;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class RegularAlgorithmMainPanel extends AlgorithmMainPanel {

	private static final long serialVersionUID = 3564236471602804053L;

	public RegularAlgorithmMainPanel() {
		add(ComponentUtil.createAtrributeLegendPanel());
		add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.MIN_X, Consts.MAX_X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments));
		add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.MIN_Z, Consts.MAX_Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments));
	}
}
