package generator.utils;

import generator.Mediator;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class XMLChooser extends JFileChooser {

	private static final long serialVersionUID = 309017384708176503L;
	public XMLChooser() {
		super();
		String userDir = System.getProperty("user.home");
		File desktop = new File(userDir + "/Desktop");
		if (desktop != null)
			setCurrentDirectory(desktop);
		setAcceptAllFileFilterUsed(false);
		setFileFilter(new XMLFilter(Mediator.getMessage(PropertiesKeys.XML_FILES)));
		addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return Mediator.getMessage(PropertiesKeys.ALL_FILES);
			}

			@Override
			public boolean accept(File f) {
				return true;
			}
		});
	}

}