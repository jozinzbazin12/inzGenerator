package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.windows.HelpWindow;

public class HelpAction extends AbstractAction {
	private static final long serialVersionUID = -3329802800495149443L;

	public HelpAction(String message) {
		super(message);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new HelpWindow(Mediator.getMessage(PropertiesKeys.HELP), Mediator.getAlgorithm().getHelp());
	}

}
