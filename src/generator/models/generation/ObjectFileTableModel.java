package generator.models.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ObjectFileTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 4537045252888015583L;

	private List<ObjectInfo> data;
	private Class<?>[] classes;

	@Override
	public void addRow(Object[] rowData) {
		int rowCount = getRowCount();
		Collections.addAll(data, (ObjectInfo[]) rowData);
		fireTableRowsInserted(rowCount, rowCount + data.size());
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public int getRowCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return classes[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getValue(rowIndex, columnIndex);
	}

	private Object getValue(int row, int index) {
		GenerationModel model = data.get(row).getModel();
		switch (index) {
		case 0:
			return model.getColor();
		case 1:
			return model.getName();
		case 2:
			return model.getPath();
		}
		throw new RuntimeException("No such column");
	}

	public ObjectFileTableModel(Class<?>[] classes) {
		data = new ArrayList<>();
		this.classes = classes;
	}
}
