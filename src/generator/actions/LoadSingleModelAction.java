package generator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.utils.ObjChooser;

public class LoadSingleModelAction extends AbstractAction {

	private static final long serialVersionUID = 7702791072284728221L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser c = new ObjChooser(false);
		File file = null;
		int rVal = c.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(c.getSelectedFile().getParent());
			file = c.getSelectedFile();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			file = null;
		}
		if (file!=null) {
				Mediator.changeObjectsFileName(file.getAbsolutePath());
		}
	}

	public LoadSingleModelAction(String name) {
		super(name);
	}
}
