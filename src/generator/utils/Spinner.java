package generator.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Spinner extends JSpinner implements Component {

	private static final long serialVersionUID = -2310738112100364004L;
	private boolean listen = false;
	private boolean listeningEnabled;
	private boolean modified = false;
	private boolean silent = false;

	public Spinner(boolean listen) {
		init(listen);
		setValue(0);
	}

	public Spinner(SpinnerNumberModel spinnerNumberModel) {
		super(spinnerNumberModel);
		init(false);
	}

	public Spinner(SpinnerNumberModel spinnerNumberModel, boolean listen) {
		super(spinnerNumberModel);
		init(listen);
	}

	private void init(boolean enableListening) {
		listeningEnabled = enableListening;
		if (enableListening) {
			addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					if (listen && !silent) {
						modified = true;
						getParent().repaint();
					}
				}
			});
			((JSpinner.DefaultEditor) getEditor()).getTextField().addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					listen = true;
				}

				@Override
				public void focusLost(FocusEvent e) {
				}
			});
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
		if (modified) {
			g.setColor(Color.red);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	@Override
	public void setValue(double value) {
		super.setValue(value);
	}

	@Override
	public double value() {
		return (double) getValue();
	}

}
