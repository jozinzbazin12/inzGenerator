package generator.tables;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellRenderer implements TableCellRenderer {

	private static final Color HIGHLIGHTED = new Color(200, 200, 220);
	private static final Color SELECTED = new Color(150, 150, 255);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object txt, boolean isSelected, boolean hasFocus, int row,
			int column) {
		ObjectFileTable tab = (ObjectFileTable) table;
		JLabel label = new JLabel();
		if (isSelected) {
			label.setBackground(SELECTED);
		} else {
			if (tab.getHighlighted() == row) {
				label.setBackground(HIGHLIGHTED);
			} else {
				label.setBackground(Color.white);
			}
		}
		label.setText(txt.toString());
		label.setOpaque(true);
		return label;
	}
}
