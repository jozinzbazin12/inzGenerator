package generator.actions;

import generator.Mediator;
import generator.utils.fileChoosers.ImageChooser;

public class LoadMapAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;

	public LoadMapAction(String name) {
		super(name, new ImageChooser());
	}

	@Override
	protected void onSucess(String path) {
		Mediator.setMapFile(path);
	}
}
