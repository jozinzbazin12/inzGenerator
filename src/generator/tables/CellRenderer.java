package generator.tables;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import generator.components.Label;

public class CellRenderer implements TableCellRenderer {

	private static final Color HIGHLIGHTED = new Color(200, 200, 220);
	private static final Color SELECTED = new Color(150, 150, 255);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object txt, boolean isSelected, boolean hasFocus, int row,
			int column) {
		Label label = new Label(txt.toString());
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		setColor(isSelected, row, table, label);
		label.setOpaque(true);
		return label;
	}

	protected void setColor(boolean isSelected, int row, JTable table, JComponent label) {
		Table tab = (Table) table;
		if (isSelected) {
			label.setBackground(SELECTED);
		} else {
			if (tab.getHighlighted() == row) {
				label.setBackground(HIGHLIGHTED);
			} else {
				label.setBackground(Color.white);
			}
		}
	}
}
