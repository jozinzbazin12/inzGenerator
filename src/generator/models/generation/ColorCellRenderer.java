package generator.models.generation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row,
			int column) {
		JPanel panel = new JPanel();
		panel.setBackground((Color) color);
		return panel;
	}

}
