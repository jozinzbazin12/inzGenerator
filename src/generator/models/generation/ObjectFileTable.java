package generator.models.generation;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ObjectFileTable extends JTable {

	private static final long serialVersionUID = -8433216818192512700L;

	public ObjectFileTable(DefaultTableModel model, TableColumnModel columnModel) {
		super(model, columnModel);
		setFillsViewportHeight(true);
		setRowSelectionAllowed(true);

		setColumnSelectionAllowed(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}
