package generator.algorithms.panels.additional;

public class EmptyPanel extends AlgorithmAdditionalPanel {
	private static final long serialVersionUID = 2669370634555761512L;
	private static EmptyPanel instance;

	@Override
	protected AlgorithmAdditionalPanel getInstance() {
		return instance;
	}

	public EmptyPanel() {
		instance = this;
	}
}
