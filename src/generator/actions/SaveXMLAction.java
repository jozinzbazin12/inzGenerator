package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.utils.fileChoosers.XMLChooser;

public abstract class SaveXMLAction extends AbstractLoadAction {

	private static final long serialVersionUID = 7702791072284728221L;
	private String fileName = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		int rVal = chooser.showSaveDialog(new JFrame());
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
		additionalAction();
	}

	public SaveXMLAction(String name) {
		super(name, new XMLChooser());
	}
}
