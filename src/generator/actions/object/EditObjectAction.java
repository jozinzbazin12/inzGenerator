package generator.actions.object;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;
import generator.windows.ObjectWindow;

public class EditObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		List<GeneratedObject> generatedObjects = Mediator.getGeneratedObjects();
		if (!generatedObjects.isEmpty()) {
			String objectName = generatedObjects.size() > 1 ? "" : generatedObjects.get(0).getObjectName();
			new ObjectWindow(MessageFormat.format(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT), objectName), generatedObjects);
		}
	}

	public EditObjectAction(String name) {
		super(name);
	}
}
