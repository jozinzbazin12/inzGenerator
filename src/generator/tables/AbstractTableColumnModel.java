package generator.tables;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class AbstractTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = -4493102092656645393L;
	protected int index = 0;

	protected void createColumn(String msg, int minWidth, int maxWidth) {
		TableColumn col = new TableColumn(index++);
		col.setHeaderValue(msg);
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setCellRenderer(new CellRenderer());
		addColumn(col);
	}
}
