package generator.algorithms.panels.additional;

import generator.algorithms.Algorithm;
import generator.panels.AbstractPanel;

public abstract class AlgorithmAdditionalPanel extends AbstractPanel {
	private static final long serialVersionUID = -2004798707516089550L;

	protected abstract AlgorithmAdditionalPanel getInstance();

	public void delete(Algorithm a) {
		Algorithm.getAdditionalPanels().put(a, getInstance());
	}
}
