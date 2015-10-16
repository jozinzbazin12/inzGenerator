package generator.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox implements Component {

	private static final long serialVersionUID = 2049556437007670432L;
	private boolean modified = false;
	private boolean listeningEnabled;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (modified) {
			g.setColor(Color.red);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	private void addListener(boolean enableListening) {
		listeningEnabled = enableListening;
		if (enableListening) {
			addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					modified = true;
				}
			});
		}
	}

	public CheckBox() {
		super();
	}

	public CheckBox(String text, boolean listen) {
		super(text);
		init(text, false);
	}

	public CheckBox(String text) {
		super(text);
		init(text, false);
	}

	public CheckBox(String text, String tooltip) {
		super(text);
		init(text, tooltip, false);
	}

	public CheckBox(Action a, String tooltip) {
		super(a);
		setToolTipText(tooltip);
	}

	public CheckBox(String text, String tooltip, boolean listen) {
		super(text);
		init(text, tooltip, listen);
	}

	private void init(String text, boolean listen) {
		init(text, (String) null, listen);
	}

	private void init(String text, String tooltip, boolean listen) {
		addListener(listen);
		if (tooltip != null) {
			setToolTipText(tooltip);
		} else {
			setToolTipText(text);
		}
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isSilent() {
		return true;
	}

	public void setSilent(boolean silent) {
	}

	public boolean isListeningEnabled() {
		return listeningEnabled;
	}

	public void setListeningEnabled(boolean listeningEnabled) {
		this.listeningEnabled = listeningEnabled;
	}

	@Override
	public double value() {
		boolean selected = isSelected();
		return selected ? 1 : 0;
	}

	@Override
	public void setValue(double value) {
		if (Math.abs(1 - value) < 0.5) {
			setSelected(true);
		} else {
			setSelected(false);
		}
	}
}
