package generator.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ObjFilter extends FileFilter {
	private static final String TYPE = "obj";
	private String description;

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
			if (f.getName().toLowerCase().endsWith(TYPE))
				return true;
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public ObjFilter(String description) {
		this.description = description;
	}
}
