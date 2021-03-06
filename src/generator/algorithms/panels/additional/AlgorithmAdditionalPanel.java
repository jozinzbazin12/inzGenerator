package generator.algorithms.panels.additional;

import javax.swing.BorderFactory;

import generator.Mediator;
import generator.algorithms.Algorithm;
import generator.panels.AbstractPanel;
import generator.utils.PropertiesKeys;

public abstract class AlgorithmAdditionalPanel extends AbstractPanel {
	private static final long serialVersionUID = -2004798707516089550L;

	public AlgorithmAdditionalPanel() {
		setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_ADDITIONAL_ARGUMENTS)));
	}

	public void delete(Algorithm a) {
		Algorithm.getAdditionalPanels().put(a, getInstance());
	}

	protected abstract AlgorithmAdditionalPanel getInstance();
}
