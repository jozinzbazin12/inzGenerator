package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import generator.Mediator;
import generator.utils.ImageChooser;

public class LoadTextureAction extends AbstractAction {

	private static final long serialVersionUID = 8159592318868440252L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser c = new ImageChooser();
		String fileName = null;
		int rVal = c.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			Mediator.setLastPath(c.getSelectedFile().getParent());
			fileName = (c.getSelectedFile().getAbsolutePath());
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			fileName = null;
		}
		if (fileName != null) {
			Mediator.setTextureFile(fileName);
		}
	}

	public LoadTextureAction(String name) {
		super(name);
	}
}
