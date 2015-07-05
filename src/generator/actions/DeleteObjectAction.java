package generator.actions;

import generator.Mediator;
import generator.models.generation.ObjectListRow;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class DeleteObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			Component parent = ((JPopupMenu) ((JMenuItem) e.getSource()).getParent()).getInvoker();
			Mediator.deleteObject(((ObjectListRow) parent));
		} else if (e.getSource() instanceof ObjectListRow && ObjectListRow.getClicked() != null) {
			Mediator.deleteObject(ObjectListRow.getClicked());
		}

	}

	public DeleteObjectAction(String name) {
		super(name);
	}
}
