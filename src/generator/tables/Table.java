package generator.tables;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Table extends JTable {
	private int highlighted = -1;
	private static final long serialVersionUID = -8433216818192512700L;

	public Table(DefaultTableModel model, TableColumnModel columnModel, boolean multiselect) {
		super(model, columnModel);
		setFillsViewportHeight(true);
		setRowSelectionAllowed(true);
		if (multiselect) {
			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		setColumnSelectionAllowed(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		((DefaultTableCellRenderer) getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	public int getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(int highlighted) {
		this.highlighted = highlighted;
	}

}
