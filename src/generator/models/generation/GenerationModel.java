package generator.models.generation;

import java.awt.Color;

import generator.models.MyFile;

public class GenerationModel implements Comparable<GenerationModel> {
	private static Color prevColor = Color.RED;
	private Color color;
	private String name;
	private MyFile path;

	public GenerationModel(MyFile path) {
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
		return path.equals(gm.getPath());
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

	public MyFile getPath() {
		return path;
	}
	
	public String getAbsolutePath() {
		return path.getAbsolutePath();
	}

	private void nexColor() {
		color = new Color((prevColor.getRed() + 30) % 255, (prevColor.getGreen() + 60) * 2 % 255,
				(prevColor.getBlue() + 90) * 3 % 255);
		prevColor = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(MyFile path) {
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

}
