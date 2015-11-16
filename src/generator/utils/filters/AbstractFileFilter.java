package generator.utils.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public abstract class AbstractFileFilter extends FileFilter {

	protected String description;
	protected String[] extensionList;

	public AbstractFileFilter(String[] extensionList, String description) {
		this.extensionList = extensionList;
		this.description = description;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		for (String i : extensionList) {
			if (f.getName().toLowerCase().endsWith(i)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
