package generator.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XMLFilter extends FileFilter {
	private static final String XML = "xml";
	private String description;

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
			if (f.getName().toLowerCase().endsWith(XML))
				return true;
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public XMLFilter(String description) {
		this.description = description;
	}
}
