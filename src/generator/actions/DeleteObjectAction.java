package generator.actions;

import generator.Mediator;
import generator.models.generation.ObjectListRow;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

public class DeleteObjectAction extends AbstractAction {

	private static final long serialVersionUID = -6698441658103769646L;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JButton)) return;
		
		Container parent = ((JButton)e.getSource()).getParent();
		Mediator.deleteObject(((ObjectListRow)parent).getIndex());
		
	}

	public DeleteObjectAction(String name){
		super(name);
	}
}
