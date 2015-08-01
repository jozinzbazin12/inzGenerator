package generator.models.generation;

import java.awt.Color;

public class GenerationModel implements Comparable<GenerationModel> {
	private static Color prevColor = Color.RED;
	private String name;
	private String path;
	private Color color;

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
	}

	public Color getColor() {
		return color;
	}

	public void setPrevColor(Color color) {
		this.color = color;
	}

	public GenerationModel(String name, String path) {
		this.name = name;
		this.path = path;
		nexColor();
	}

	private void nexColor() {
		color = new Color((prevColor.getRed() + 30) % 255, (prevColor.getGreen() + 60) * 2 % 255, (prevColor.getBlue() + 90) * 3 % 255);
		prevColor = color;
	}

	public void swapColors() {
		Color x = color;
		color = prevColor;
		prevColor = x;
	}

	@Override
	public int compareTo(GenerationModel o) {
		if (o == null || !(o instanceof GenerationModel)) {
			return -1;
		}
		return name.compareToIgnoreCase(o.getName());
	}
}
