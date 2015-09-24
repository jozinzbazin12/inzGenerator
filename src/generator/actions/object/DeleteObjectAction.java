package generator.actions.object;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import generator.Mediator;

public class DeleteObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		Mediator.deleteObject();
	}

	public DeleteObjectAction(String name) {
		super(name);
	}
}
