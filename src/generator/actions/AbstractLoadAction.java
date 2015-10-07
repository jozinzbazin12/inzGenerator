package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;

public abstract class AbstractLoadAction extends Action {

	private static final long serialVersionUID = 8390116511929514494L;

	protected JFileChooser chooser;

	protected abstract void onSucess(String path);

	protected void onFail() {
		// Override me
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String fileName = null;
		int rVal = chooser.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(chooser.getSelectedFile().getParent());
			fileName = (chooser.getSelectedFile().getAbsolutePath());
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			fileName = null;
		}
		if (fileName != null) {
			onSucess(fileName);
		} else {
			onFail();
		}
		super.actionPerformed(e);
	}

	public AbstractLoadAction(String name, JFileChooser chooser) {
		super(name);
		this.chooser = chooser;
	}

}
