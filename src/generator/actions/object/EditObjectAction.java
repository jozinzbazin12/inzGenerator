package generator.actions.object;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;
import generator.windows.ObjectWindow;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class EditObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		GeneratedObject generatedObject = Mediator.getGeneratedObject();
		if (generatedObject != null) {
			new ObjectWindow(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT), generatedObject);
		}
	}

	public EditObjectAction(String name) {
		super(name);
	}
}
