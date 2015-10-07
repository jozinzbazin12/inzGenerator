package generator.actions;

import java.awt.event.ActionEvent;

public class ExitAction extends Action {

	private static final long serialVersionUID = 7785544546608983193L;

	public ExitAction(String message) {
		super(message);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		System.exit(0);
	}

}
