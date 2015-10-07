package generator.actions;

import generator.utils.fileChoosers.ImageChooser;

public abstract class LoadImageAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;

	public LoadImageAction(String name) {
		super(name, new ImageChooser());
	}

}
