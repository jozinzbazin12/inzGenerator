package generator.models;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import generator.Mediator;

public class MyFile extends File {

	public MyFile(String pathname) {
		super(getPath(new File(pathname)));
	}

	public MyFile(File file) {
		super(getPath(file));
	}

	private static final long serialVersionUID = 2538193977864406665L;
	private static boolean absolute = true;

	public static void setAbsolute(boolean absolute) {
		MyFile.absolute = absolute;
	}

	private static String getPath(File f) {
		if (f.isAbsolute()) {
			return f.getAbsolutePath();
		}
		String path = f.getPath();
		String root = Mediator.getRoot().toString();

		return root + path;
	}

	@Override
	public String getAbsolutePath() {
		String absolutePath = super.getAbsolutePath();
		if (absolute) {
			return absolutePath;
		}
		Path other = Paths.get(absolutePath);
		Path root = Mediator.getRoot();
		try {
			return root.relativize(other).toString();
		} catch (Throwable t) {
			t.printStackTrace();
			return absolutePath;
		}
	}

	public String getKey() {
		return super.getAbsolutePath();
	}

	@Override
	public String toString() {
		return getAbsolutePath();
	}

}
