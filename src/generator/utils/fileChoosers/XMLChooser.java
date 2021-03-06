package generator.utils.fileChoosers;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.utils.filters.XMLFilter;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class XMLChooser extends JFileChooser {

	private static final long serialVersionUID = 309017384708176503L;

	public XMLChooser() {
		super();
		setAcceptAllFileFilterUsed(false);
		setFileFilter(new XMLFilter(Mediator.getMessage(PropertiesKeys.XML_FILES)));
		addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return true;
			}

			@Override
			public String getDescription() {
				return Mediator.getMessage(PropertiesKeys.ALL_FILES);
			}
		});
	}

}
