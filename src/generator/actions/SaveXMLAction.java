package generator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.utils.fileChoosers.XMLChooser;

public abstract class SaveXMLAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;
	private File file = null;

	public SaveXMLAction(String name) {
		super(name, new XMLChooser());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int rVal = chooser.showSaveDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(chooser.getSelectedFile().getParent());
			file = chooser.getSelectedFile();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			file = null;
		}
		if (file != null) {
			onSucess(file);
		} else {
			onFail();
		}
		additionalAction();
	}
}
