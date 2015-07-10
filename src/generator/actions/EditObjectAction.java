package generator.actions;

import generator.Mediator;
import generator.utils.PropertiesKeys;
import generator.windows.ObjectWindow;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class EditObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		new ObjectWindow(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT),Mediator.getGeneratedObject());
		
	}

	public EditObjectAction(String name){
		super(name);
	}
}
