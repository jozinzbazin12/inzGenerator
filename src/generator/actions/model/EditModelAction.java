package generator.actions.model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.generation.ObjectFileListRow;
import generator.models.generation.ObjectInfo;
import generator.utils.PropertiesKeys;
import generator.windows.ModelWindow;

public class EditModelAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		List<ObjectInfo> objects = new ArrayList<>();
		Set<ObjectFileListRow> selectedRows = ObjectFileListRow.getSelectedRows();
		for (ObjectFileListRow i : selectedRows) {
			objects.add(i.getObject());
		}
		if (!selectedRows.isEmpty()) {
			new ModelWindow(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT), objects);
		}
	}

	public EditModelAction(String name) {
		super(name);
	}
}
