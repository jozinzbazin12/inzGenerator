package generator.utils.fileChoosers;

import java.io.File;

import javax.swing.JFileChooser;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.utils.filters.ObjFilter;

public class ObjChooser extends JFileChooser {

	private static final long serialVersionUID = 309017384708176503L;

	public ObjChooser(boolean multi) {
		super();
		File desktop = new File(Mediator.getLastPath());
		if (desktop != null) {
			setCurrentDirectory(desktop);
		}
		setAcceptAllFileFilterUsed(false);
		setMultiSelectionEnabled(multi);
		setFileFilter(new ObjFilter(Mediator.getMessage(PropertiesKeys.OBJ_FILES)));
	}

}
