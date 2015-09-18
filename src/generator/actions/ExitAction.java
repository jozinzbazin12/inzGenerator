package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = 7785544546608983193L;

	public ExitAction(String message) {
		super(message);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
