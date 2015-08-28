package generator.actions;

import generator.Mediator;
import generator.utils.XMLChooser;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class SaveXMLAction extends AbstractAction {

	private static final long serialVersionUID = 7702791072284728221L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser c = new XMLChooser();
		String fileName=null;
		int rVal = c.showSaveDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(c.getSelectedFile().getParent());
			fileName = (c.getSelectedFile().getAbsolutePath());
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			fileName = null;
		}
		if(fileName!=null) {
			Mediator.saveXMLFile(fileName);
		}
	}

	public SaveXMLAction(String name) {
		super(name);
	}
}
