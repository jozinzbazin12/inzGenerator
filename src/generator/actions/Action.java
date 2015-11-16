package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class Action extends AbstractAction {

	private static final long serialVersionUID = -4143674573355824164L;

	public Action(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		additionalAction();
	}

	protected void additionalAction() {
		// Override me
	}
}
