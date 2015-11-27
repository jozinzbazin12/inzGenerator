package generator.actions.model;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.actions.AbstractLoadAction;
import generator.utils.fileChoosers.ObjChooser;

public abstract class LoadModelAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;
	private File[] files;

	public LoadModelAction(String name) {
		super(name, new ObjChooser(true));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int rVal = chooser.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(chooser.getSelectedFile().getParent());
			files = chooser.getSelectedFiles();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			files = null;
		}
		if (files != null && files.length > 0) {
			for (File i : files) {
				onSucess(i);
			}
		} else {
			onFail();
		}
	}

	@Override
	protected void onSucess(File path) {
		// TODO Auto-generated method stub

	}
}
