package generator.actions.model;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.generation.ObjectFileListRow;

public class DeleteModelAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
			Set<ObjectFileListRow> clicked = ObjectFileListRow.getSelectedRows();
			Mediator.deleteObject(clicked);
	}

	public DeleteModelAction(String name) {
		super(name);
	}
}
