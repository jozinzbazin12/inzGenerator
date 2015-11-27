package generator.models.generation;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import generator.Mediator;

public class GenerationModel implements Comparable<GenerationModel> {
	private static Color prevColor = Color.RED;
	private Color color;
	private String name;
	private File path;
	private static boolean absolute = true;

	public GenerationModel(File path) {
		this.path = path;
		this.name = getFileNameFromPath();
		nexColor();
	}

	@Override
	public int compareTo(GenerationModel o) {
		if (o == null) {
			return -1;
		}
		return name.compareToIgnoreCase(o.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GenerationModel)) {
			return false;
		}
		GenerationModel gm = (GenerationModel) obj;
		return path.equals(gm.getAbsoultePath());
	}

	public Color getColor() {
		return color;
	}

	private String getFileNameFromPath() {
		return path.getName();
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		if (absolute) {
			return path.getAbsolutePath();
		}
		Path other = Paths.get(path.getAbsolutePath());
		Path root = Mediator.getRoot();
		try {
			return root.relativize(other).toString();
		} catch (Throwable t) {
			return path.getAbsolutePath();
		}
	}

	public String getAbsoultePath() {
		return path.getPath();
	}

	private void nexColor() {
		color = new Color((prevColor.getRed() + 30) % 255, (prevColor.getGreen() + 60) * 2 % 255,
				(prevColor.getBlue() + 90) * 3 % 255);
		prevColor = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(File path) {
		this.path = path;
		name = getFileNameFromPath();
	}

	public void setPrevColor(Color color) {
		this.color = color;
	}

	public void swapColors() {
		Color x = color;
		color = prevColor;
		prevColor = x;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(" (");
		str.append(path);
		str.append(")");
		return str.toString();
	}

	public static boolean isAbsolute() {
		return absolute;
	}

	public static void setAbsolute(boolean absolute) {
		GenerationModel.absolute = absolute;
	}
}
