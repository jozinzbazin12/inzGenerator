package generator.tables;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;

public class ObjectTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 4537045252888015583L;

	private List<GeneratedObject> data;
	private Class<?>[] classes;

	@Override
	public void addRow(Object[] rowData) {
		int oldCount = getRowCount();
		GeneratedObject[] objectInfoData = (GeneratedObject[]) rowData;
		data = Arrays.asList(objectInfoData);
		fireTableRowsDeleted(0, oldCount);
		fireTableRowsInserted(0, data.size());
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
	public Class<?> getColumnClass(int columnIndex) {
		return classes[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getValue(rowIndex, columnIndex);
	}

	private String createCellValue(double x, double y, double z) {
		return MessageFormat.format("X: {0}, Y: {1}, Z: {2}", x, y, z);
	}

	private Object getValue(int row, int index) {
		if (data != null && data.size() > row) {
			GeneratedObject object = data.get(row);
			BasicModelData basic = object.getBasic();
			switch (index) {
			case 0:
				return object.getModelColor();
			case 1:
				return object.getObjectName();
			case 2:
				return createCellValue(basic.getX(), basic.getY(), basic.getZ());
			case 3:
				return createCellValue(basic.getSx(), basic.getSy(), basic.getSz());
			case 4:
				return createCellValue(basic.getRx(), basic.getRy(), basic.getRz());
			}
			throw new RuntimeException("No such column");
		} else {
			return null;
		}
	}

	public ObjectTableModel(Class<?>[] classes) {
		data = new ArrayList<>();
		this.classes = classes;
	}

}
