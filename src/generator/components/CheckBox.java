package generator.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox implements Component {

	private static final long serialVersionUID = 2049556437007670432L;
	private boolean listeningEnabled;
	private boolean modified = false;
	private boolean silent = false;
	private boolean displayChange = true;

	public CheckBox() {
		super();
	}

	public CheckBox(Action a, String tooltip) {
		super(a);
		setToolTipText(tooltip);
	}

	public CheckBox(String text) {
		super(text);
		init(text, false);
	}

	public CheckBox(String text, boolean listen) {
		super(text);
		init(text, false);
	}

	public CheckBox(String text, String tooltip) {
		super(text);
		init(text, tooltip, false);
	}

	public CheckBox(String text, String tooltip, boolean listen) {
		super(text);
		init(text, tooltip, listen);
	}

	private void addListener(boolean enableListening) {
		listeningEnabled = enableListening;
		if (enableListening) {
			addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (!silent) {
						modified = true;
					}
				}
			});
		}
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

	public boolean isListeningEnabled() {
		return listeningEnabled;
	}

	public boolean isModified() {
		return modified;
	}

	public boolean isSilent() {
		return silent;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (modified && displayChange) {
			g.setColor(Color.red);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	public void setListeningEnabled(boolean listeningEnabled) {
		this.listeningEnabled = listeningEnabled;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	@Override
	public void setValue(double value) {
		if (Math.abs(1 - value) < 0.5) {
			setSelected(true);
		} else {
			setSelected(false);
		}
	}

	@Override
	public double value() {
		boolean selected = isSelected();
		return selected ? 1 : 0;
	}

	public void setDisplayChange(boolean displayChange) {
		this.displayChange = displayChange;
	}
}
