package generator.tables;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import generator.Mediator;
import generator.utils.PropertiesKeys;

public class AbstractTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = -4493102092656645393L;
	protected int index = 0;

	protected void createColorColumn() {
		TableColumn color = new TableColumn(index++);
		color.setHeaderValue(Mediator.getMessage(PropertiesKeys.COLOR));
		color.setMinWidth(10);
		color.setMaxWidth(35);
		color.setCellRenderer(new ColorCellRenderer());
		addColumn(color);
	}

	protected void createRelativeColumn() {
		TableColumn relative = new TableColumn(index++);
		relative.setHeaderValue(Mediator.getMessage(PropertiesKeys.RELATIVE));
		relative.setMinWidth(10);
		relative.setMaxWidth(50);
		relative.setCellRenderer(new CheckboxCellRenderer());
		addColumn(relative);
	}

	protected void createColumn(String msg, int minWidth, int maxWidth) {
		createColumn(msg, minWidth, maxWidth, 0);
	}

	protected void createColumn(String msg, int minWidth, int maxWidth, int preffered) {
		TableColumn col = new TableColumn(index++);
		col.setHeaderValue(msg);
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		if (preffered > 0) {
			col.setPreferredWidth(preffered);
		}
		col.setCellRenderer(new CellRenderer());
		addColumn(col);
	}
}
