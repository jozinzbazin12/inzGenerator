package generator.models.generation;

import java.awt.Color;

public class GenerationModel implements Comparable<GenerationModel> {
	private static Color prevColor = Color.RED;
	private String name;
	private String path;
	private Color color;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(" (");
		str.append(path);
		str.append(")");
		return str.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		name = getFileNameFromPath();
	}

	public Color getColor() {
		return color;
	}

	public void setPrevColor(Color color) {
		this.color = color;
	}

	public GenerationModel(String path) {
		this.path = path;
		this.name = getFileNameFromPath();
		nexColor();
	}

	private String getFileNameFromPath() {
		int index = path.lastIndexOf("\\");
		int index2 = path.lastIndexOf("/");
		return path.substring(Integer.max(index, index2) + 1);
	}

	private void nexColor() {
		color = new Color((prevColor.getRed() + 30) % 255, (prevColor.getGreen() + 60) * 2 % 255,
				(prevColor.getBlue() + 90) * 3 % 255);
		prevColor = color;
	}

	public void swapColors() {
		Color x = color;
		color = prevColor;
		prevColor = x;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GenerationModel)) {
			return false;
		}
		GenerationModel gm = (GenerationModel) obj;
		return path.equals(gm.getPath());
	}

	@Override
	public int compareTo(GenerationModel o) {
		if (o == null) {
			return -1;
		}
		return name.compareToIgnoreCase(o.getName());
	}
}
