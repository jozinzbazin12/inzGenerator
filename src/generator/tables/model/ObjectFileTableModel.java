package generator.tables.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import generator.models.generation.GenerationModel;
import generator.models.generation.ObjectInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;

public class ObjectFileTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 4537045252888015583L;

	private List<ObjectInfo> data;
	private Class<?>[] classes;

	@Override
	public void addRow(Object[] rowData) {
		int oldCount = getRowCount();
		ObjectInfo[] objectInfoData = (ObjectInfo[]) rowData;
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
			ObjectInfo objectInfo = data.get(row);
			PositionSettings pos = objectInfo.getPositionSettings();
			ScaleSettings sca = objectInfo.getScaleSettings();
			RotationSettings rot = objectInfo.getRotationSettings();
			GenerationModel model = objectInfo.getModel();
			switch (index) {
			case 0:
				return model.getColor();
			case 1:
				return model.getName();
			case 2:
				return model.getPath();
			case 3:
				return MessageFormat.format("{0} - {1}", objectInfo.getMinCount(), objectInfo.getMaxCount());
			case 4:
				return createCellValue(pos.getMinX(), pos.getMinY(), pos.getMinZ());
			case 5:
				return createCellValue(pos.getMaxX(), pos.getMaxY(), pos.getMaxZ());
			case 6:
				return createCellValue(sca.getMinX(), sca.getMinY(), sca.getMinZ());
			case 7:
				return createCellValue(sca.getMaxX(), sca.getMaxY(), sca.getMaxZ());
			case 8:
				return createCellValue(rot.getMinX(), rot.getMinY(), rot.getMinZ());
			case 9:
				return createCellValue(rot.getMaxX(), rot.getMaxY(), rot.getMaxZ());
			}
			throw new RuntimeException("No such column");
		} else {
			return null;
		}
	}

	public ObjectFileTableModel(Class<?>[] classes) {
		data = new ArrayList<>();
		this.classes = classes;
	}

}
