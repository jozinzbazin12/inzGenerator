package generator.actions;

import generator.Mediator;
import generator.utils.fileChoosers.ImageChooser;

public class LoadTextureAction extends AbstractLoadAction {

	private static final long serialVersionUID = 8159592318868440252L;

	public LoadTextureAction(String name) {
		super(name, new ImageChooser());
	}

	@Override
	protected void onSucess(String path) {
		Mediator.setTextureFile(path);
	}
}
