package generator.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

import generator.models.generation.ObjectFileListRow;

public class RowsSelectionAction extends AbstractAction {

	private static final long serialVersionUID = 3250389250569261195L;

	@Override
	public void actionPerformed(ActionEvent e) {
		ObjectFileListRow.toggleAll(((JCheckBox) e.getSource()).isSelected());

	}
}
