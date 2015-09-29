package generator.actions.model;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.utils.fileChoosers.ObjChooser;

//TODO
public class LoadModelAction extends AbstractAction {

	private static final long serialVersionUID = 7702791072284728221L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser c = new ObjChooser(true);
		File[] files = null;
		int rVal = c.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(c.getSelectedFile().getParent());
			files = c.getSelectedFiles();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			files = null;
		}
		if (files != null && files.length > 0) {
			for (File i : files) {
				Mediator.loadModel(i.getAbsolutePath());
			}
		}
		Mediator.updateModelsPanel();
	}

	public LoadModelAction(String name) {
		super(name);
	}
}
