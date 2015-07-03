package generator.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {
	private String[] extensionList;
	private String description;

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		for (String i : extensionList)
			if (f.getName().toLowerCase().endsWith(i))
				return true;
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public ImageFilter(String[] extensionList, String description) {
		this.extensionList = extensionList;
		this.description = description;
	}
}
