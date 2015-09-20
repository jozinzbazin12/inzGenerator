package generator.actions.model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import generator.Mediator;

public class DeleteModelAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		Mediator.deleteModels();
	}

	public DeleteModelAction(String name) {
		super(name);
	}
}
