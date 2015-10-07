package generator.actions;

import generator.utils.fileChoosers.XMLChooser;

public abstract class LoadXMLAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;

	public LoadXMLAction(String name) {
		super(name, new XMLChooser());
	}
}
