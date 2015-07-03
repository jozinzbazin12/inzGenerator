package generator.actions;

import generator.Mediator;
import generator.utils.ImageChooser;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class LoadMapAction extends AbstractAction {

	
	private static final long serialVersionUID = 7702791072284728221L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser c = new ImageChooser();
		String fileName=null;
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(new JFrame());
		if (rVal == JFileChooser.APPROVE_OPTION) {
			fileName = (c.getSelectedFile().getAbsolutePath());
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			fileName = null;
		}
		if(fileName!=null) 
			{
			Mediator.setLoadMapName(fileName);
			Mediator.setPreview(fileName);
			}
	}

	public LoadMapAction(String name) {
		super(name);
	}
}
