package generator.tables;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellRenderer implements TableCellRenderer {

	private static final Color HIGHLIGHTED = new Color(0, 255, 220);
	private static final Color DEFAULT = new Color(245, 245, 255);
	private static final Color SELECTED = new Color(120, 120, 255);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object txt, boolean isSelected, boolean hasFocus, int row,
			int column) {
		JLabel label = new JLabel();
		if (isSelected) {
			label.setBackground(SELECTED);
		} else {
			if (hasFocus) {
				label.setBackground(HIGHLIGHTED);
			} else {
				label.setBackground(DEFAULT);
			}
		}
		label.setText(txt.toString());
		label.setOpaque(true);
		return label;
	}
}
