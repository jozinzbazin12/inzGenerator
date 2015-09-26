package generator.tables;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckboxCellRenderer extends CellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object txt, boolean isSelected, boolean hasFocus, int row,
			int column) {
		JCheckBox checkbox = new JCheckBox();
		checkbox.setSelected(Boolean.parseBoolean(txt.toString()));
		setColor(isSelected, row, table, checkbox);
		checkbox.setEnabled(false);
		return checkbox;
	}
}
