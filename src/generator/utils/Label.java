package generator.utils;

import javax.swing.JLabel;

public class Label extends JLabel {

	private static final long serialVersionUID = 3634769322724243951L;
	private boolean lockedTooltip = false;

	public Label() {
	}

	public Label(String txt) {
		super(txt);
		init(txt);
	}

	public Label(String txt, int center) {
		super(txt, center);
		setToolTipText(txt);
	}

	public Label(String text, String tooltip) {
		super(text);
		setTooltip(text, tooltip);
	}

	public Label(String message, String tooltip, int center) {
		this(message, center);
		setTooltip(message, tooltip);
	}

	private void init(String txt) {
		setToolTipText(txt);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		if (!lockedTooltip) {
			setToolTipText(text);
		}
	}

	private void setTooltip(String text, String tooltip) {
		if (tooltip == null) {
			setToolTipText(text);
		} else {
			setToolTipText(tooltip);
		}
		lockedTooltip = true;
	}
}
