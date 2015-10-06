package generator.utils;

import javax.swing.JLabel;

public class Label extends JLabel {

	private static final long serialVersionUID = 3634769322724243951L;

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

	@Override
	public void setText(String text) {
		super.setText(text);
		setToolTipText(text);
	}

	private void init(String txt) {
		setToolTipText(txt);
	}
}
