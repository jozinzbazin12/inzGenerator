package generator.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import generator.Mediator;

public abstract class Action extends AbstractAction {

	private static final long serialVersionUID = -4143674573355824164L;

	public Action(String name) {
		super(name);
	}

	protected void goToStart(JFileChooser c) {
		File dir = new File(Mediator.getLastPath());
		if (dir != null) {
			c.setCurrentDirectory(dir);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		additionalAction();
	}

	protected void additionalAction() {
		// Override me
	}
}
