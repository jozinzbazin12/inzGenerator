package generator.actions.object;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.windows.ObjectWindow;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class NewObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		new ObjectWindow(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));
	}

	public NewObjectAction(String name){
		super(name);
	}
}
