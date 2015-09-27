package generator.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox {

	private static final long serialVersionUID = 2049556437007670432L;

	private boolean modified = false;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (modified) {
			g.setColor(Color.red);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	private void addListener() {
		addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (hasFocus()) {
					modified = true;
				}
				getParent().repaint();
			}
		});
	}

	@Override
	public void setSelected(boolean b) {
		super.setSelected(b);
		modified = false;
	}

	public CheckBox() {
		super();
		addListener();
	}

	public CheckBox(Action a) {
		super(a);
		addListener();
	}

	public CheckBox(String text, boolean selected) {
		super(text, selected);
		addListener();
	}

	public CheckBox(String text) {
		super(text);
		addListener();
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

}