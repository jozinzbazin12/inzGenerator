package generator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;

public abstract class AbstractLoadAction extends Action {

	private static final long serialVersionUID = 8390116511929514494L;

	protected JFileChooser chooser;

	public AbstractLoadAction(String name, JFileChooser chooser) {
		super(name);
		this.chooser = chooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File fileName = null;
		goToStart(chooser);
		int rVal = chooser.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(chooser.getSelectedFile().getParent());
			fileName = chooser.getSelectedFile();
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

	protected void onFail() {
		// Override me
	}

	protected abstract void onSucess(File path);

}
