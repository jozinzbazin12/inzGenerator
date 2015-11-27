package generator.models;

import javax.swing.JRadioButtonMenuItem;

public class PathTypeSetting extends JRadioButtonMenuItem {

	private static final long serialVersionUID = 1076446707858885610L;
	private String name;

	public PathTypeSetting(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
