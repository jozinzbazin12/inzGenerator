package generator.actions.model;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.generation.ObjectInfo;
import generator.utils.PropertiesKeys;
import generator.windows.ModelWindow;

public class EditModelAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		List<ObjectInfo> selectedRows = Mediator.getSelectedObjectFiles();
		if (!selectedRows.isEmpty()) {
			new ModelWindow(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT), selectedRows);
		}
	}

	public EditModelAction(String name) {
		super(name);
	}
}
