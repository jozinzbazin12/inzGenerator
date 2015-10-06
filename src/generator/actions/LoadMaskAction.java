package generator.actions;

import generator.Mediator;
import generator.utils.fileChoosers.ImageChooser;

public class LoadMaskAction extends AbstractLoadAction {
	private static final long serialVersionUID = 1L;

	public LoadMaskAction(String name) {
		super(name, new ImageChooser());
	}

	@Override
	protected void onSucess(String path) {
		Mediator.setMask(path);
	}

}
