package generator.actions;

import generator.Mediator;
import generator.utils.fileChoosers.XMLChooser;

public class LoadXMLAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;

	public LoadXMLAction(String name) {
		super(name, new XMLChooser());
	}

	@Override
	protected void onSucess(String path) {
		Mediator.loadXMLFile(path);
	}
}
