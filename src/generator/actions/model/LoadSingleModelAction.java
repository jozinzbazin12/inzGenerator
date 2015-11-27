package generator.actions.model;

import java.io.File;

import generator.Mediator;
import generator.actions.AbstractLoadAction;
import generator.utils.fileChoosers.ObjChooser;

public class LoadSingleModelAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;

	public LoadSingleModelAction(String name) {
		super(name, new ObjChooser(false));
	}

	@Override
	protected void onSucess(String path) {
		Mediator.changeModelFileName(new File(path));
	}
}
